package com.example.Hbase.dao;

import com.alibaba.fastjson.JSON;

import com.example.Hbase.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * HBase 的基础Dao
 *
 * TODO : Annotation需要加上校验的逻辑
 *
 * @author lenovo
 *
 */
public abstract class BaseHbaseDao<T> {
	protected static Log logger = LogFactory.getLog(BaseHbaseDao.class);

	// 环境变量中传入测试用namespace的参数名
	private static final String TEST_NAMESPACE_PROPERTY = "hbase:test_ns";

	// 缓存hbase的连接
	private static Map<Configuration,Connection> hbaseConns = new ConcurrentHashMap<Configuration,Connection>();

	// 存放对象的HBase相关的Meta信息
	private static Map<Class<?>, HBaseObjectMeta> modelMetas = new ConcurrentHashMap<Class<?>, HBaseObjectMeta>();

	// spring定义的hbase操作类
//    @Resource
//    private HbaseTemplate hbaseTemplate;
    @Resource
    HbaseUtil hbaseUtil;

	// 设置namespace的名称
	private String namespace;

	private RowkeyCreator rowkeyCreator;

	/**
	 * 调用默认的构造函数
	 */
	public BaseHbaseDao() {
		// 获得环境变量里面namespace的值。如果非空，就设置
		if (System.getProperty(TEST_NAMESPACE_PROPERTY) != null) {
			namespace = System.getProperty(TEST_NAMESPACE_PROPERTY);

			if (logger.isInfoEnabled()) {
				logger.info(" Set hbase test namespace = " + namespace);
			}
		}
	}

//	public void setHbaseTemplate(HbaseTemplate hbaseTemplate) {
//		this.hbaseTemplate = hbaseTemplate;
//	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 保存在一批HBase对象
	 *

	 */
	public <V extends T> int batchSaveHBaseRecord(final List<V> hbaseModels) {
		if (hbaseModels == null || hbaseModels.size() == 0) {
			throw new RuntimeException("Empty hbaseModels");
		}

		HBaseObjectMeta hoMeta = getHBaseObjectMeta(hbaseModels.get(0)
				.getClass());

		if (logger.isDebugEnabled()) {
			logger.debug(String.format(
					" Batch insert to (%s) with (%d) record.",
					hoMeta.getFullTableName(namespace), hbaseModels.size()));
		}

        hbaseUtil.hbaseTemplate().execute(
				hoMeta.getFullTableName(namespace),
				(table) -> {
					List<Put> items = new ArrayList<Put>();

					// 根据rowKey定义一个put对象，可用作插入和更新
					RowkeyFieldMeta rfMeta = hoMeta.getRowkeyFieldMeta();

					for (Object hbaseModel : hbaseModels) {
						Put put = new Put(Bytes.toBytes(rfMeta
								.getRowkey(hbaseModel)));

						// 设置所有的Column
						for (ColumnFieldMeta cfMeta : hoMeta
								.getColumnFieldMetas()) {
							// 判断值不为空
							byte[] columnValue = cfMeta
									.getFieldValue(hbaseModel);
							if (columnValue != null) {
								put.addColumn(cfMeta.getCfName().getBytes(),
										Bytes.toBytes(cfMeta.getColumnName()),
										columnValue);

								items.add(put);
							}
						}

						if (logger.isDebugEnabled()) {
							logger.debug(String.format(" Add Put = %s",
									JSON.toJSONString(put)));
						}
					}

					// 批量加入到表
					table.put(items);

					return true;
				});

		if (logger.isDebugEnabled()) {
			logger.debug(String.format(" after batch insert (%s)",
					hoMeta.getFullTableName(namespace)));
		}

		return hbaseModels.size() ;
	}

	/**
	 * 保存一个HBase对象
	 *
	 * @param hbaseModel
	 */
	public <V extends T> void saveHBaseRecord(V hbaseModel) {
		List<T> list = new ArrayList<T>();
		list.add(hbaseModel);

		batchSaveHBaseRecord(list);
	}

	public void deleteHBaseRecord(Class<T> modelCls, final String rowkey) {
		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

        hbaseUtil.hbaseTemplate().execute(hoMeta.getFullTableName(namespace), (table) -> {
			List<Delete> list = new ArrayList<Delete>();
			list.add(new Delete(rowkey.getBytes()));
			table.delete(list);
			return true;
		});
	}

	/**
	 * 根据Rowkey查询记录
	 *
	 * @param modelCls
	 * @param rowKey
	 * @return
	 */
	public <V extends T> V findHBaseRecord(Class<V> modelCls, String rowKey) {
		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

		Get get = new Get(Bytes.toBytes(rowKey));
		Scan scan = new Scan(get);

		List<V> list = this.innerQueryHBaseRecords(hoMeta, scan);

		if (list.size() > 0) {
			// TODO : 添加日志
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据Rowkey的范围查询记录
	 *
	 * @param modelCls
	 * @param startRowkey
	 * @param stopRowkey
	 * @return
	 */
	public <V extends T> List<V> queryHBaseRecordsWithRange(Class<V> modelCls,
			String startRowkey, String stopRowkey) {
		if (logger.isDebugEnabled()) {
			logger.debug(String
					.format("queryHBaseRecordsWithRange(%s) startRow(%s) , stopRow(%s)",
							modelCls.toString() , startRowkey,
							stopRowkey));
		}

		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

		// 根据范围查询
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes(startRowkey));
		scan.setStopRow(Bytes.toBytes(stopRowkey));

		return this.innerQueryHBaseRecords(hoMeta, scan);
	}

	/**
	 * 根据字段的值过滤数据
	 *
	 * @param modelCls
	 * @param filterStrs
	 *            - 过滤字符串 .如果为空就是全表扫描
	 * @return public List<T> queryHBaseRecordsWithFilter(Class<?> modelCls,
	 *         Map<String, String> filterStrs) { HBaseObjectMeta hoMeta =
	 *         getHBaseObjectMeta(modelCls);
	 *
	 *         // 根据范围查询 Scan scan = new Scan();
	 *
	 *         if (filterStrs == null || filterStrs.size() == 0) { // TODO :
	 *         以后是否需要抛出异常 logger.warn(" Input emptry filterStr for " +
	 *         hoMeta.getFullTableName(namespace)); } else { FilterList filters
	 *         = new FilterList(); for (Map.Entry<String, String> entry :
	 *         filterStrs.entrySet()) { if (hoMeta.existColumn(entry.getKey()))
	 *         { filters.addFilter(new
	 *         SingleColumnValueFilter(Bytes.toBytes(entry .getKey()), null,
	 *         CompareOp.EQUAL, Bytes .toBytes(entry.getValue()))); } else {
	 *         throw new RuntimeException(String.format(
	 *         "Invalid column(%s) from hbase table (%s)", entry.getKey(),
	 *         hoMeta.getFullTableName(namespace))); } }
	 *         scan.setFilter(filters); }
	 *
	 *         return this.innerQueryHBaseRecords(hoMeta, scan); }
	 */

	/**
	 * 通用的查询接口
	 *
	 * @param modelCls
	 * @param scan
	 * @return
	 */
	public <V extends T> List<V> queryHBaseRecords(Class<V> modelCls, Scan scan) {
		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

		return innerQueryHBaseRecords(hoMeta, scan);
	}

	/**
	 * 根据rowkey前缀查找最后的一条记录。 找不到就返回空
	 *
	 * @param modelCls      - 返回类型- TODO :现在类型出错貌似没有检查

	 * @param startRow      - 开始的rowkey
	 * @param stopRow       - 结束的rowkey
	 * @return
	 */
	public <V extends T> V findLastHBaseRecordWithRowkeyPrefix(Class<V> modelCls,
			byte[] rowPrefix, byte[] startRow, byte[] stopRow) {
		if (logger.isDebugEnabled()) {
			logger.debug(String
					.format("findLastHBaseRecordWithRowkeyPrefix(%s) rowPrefix(%s) , startRow(%s) , stopRow(%s)",
							modelCls.toString() , new String(rowPrefix), new String(startRow),
							new String(stopRow)));
		}

		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

		// 创建一个查询
		Scan scan = new Scan();
		scan.setRowPrefixFilter(rowPrefix);
		scan.setReversed(true); // 反向排序
		scan.setStartRow(stopRow); // StartRow和StopRow需要反转来设置
		scan.setStopRow(startRow);
		scan.setMaxResultSize(1);

		// 执行查询
		List<V> rets = this.innerQueryHBaseRecords(hoMeta, scan);

		if (rets != null && rets.size() > 0) {
			return rets.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 临时写的函数 - 尝试hbase的count功能
	 * 实体对象有两种情况
	 * 1. 单个计数字段的对象
	 * 2. 对个计数对象的字段
	 * @param modelCls - 对象信息
	 * @param id       - rowkey字段的值

	 * @parma step     - 递增的步长 (步长为0就是返回当前值)
	 * @return
	 */
	public long incrementCounter(Class<T> modelCls, String id , String fieldName , int step){
		// 获得对象的类型信息
		HBaseObjectMeta hoMeta = getHBaseObjectMeta(modelCls);

		Table table = null ;
		try {
			Connection hbaseConn = getHbaseConnection(hbaseUtil.hbaseTemplate().getConfiguration()) ;

			// 获得表名
            table = hbaseConn.getTable(TableName.valueOf(hoMeta.getFullTableName(null))) ;

            // 获得字段描述
            ColumnFieldMeta cfm = hoMeta.getColumnFieldMetaByFieldName(fieldName) ;

            // TODO : 考虑是否要抛出异常
            if(cfm == null){
            	return 0 ;
            }

            return table.incrementColumnValue(Bytes.toBytes(id),Bytes.toBytes(cfm.getCfName()) , Bytes.toBytes(cfm.getColumnName()), step) ;
		} catch (IOException e) {
			logger.error("incrementAndGet" , e);

			return 0 ;
		} finally {
			if(table != null){
				try {
					table.close();
				} catch (IOException e) {
					logger.error("Close Hbase Table " , e);
				}
			}
		}
	}

	public RowkeyCreator getRowkeyCreator() {
		if (rowkeyCreator == null) {
			rowkeyCreator = createRowkeyCreator();
		}

		return rowkeyCreator;
	}

	/**
	 * 查询函数

	 * @param scan
	 */
	private <V extends T> List<V> innerQueryHBaseRecords(HBaseObjectMeta hoMeta, Scan scan) {
		List<V> list = hbaseUtil.hbaseTemplate().find(hoMeta.getFullTableName(namespace),
				scan, new RowMapper<V>() {
					@Override
					public V mapRow(Result result, int number) throws Exception {
						V obj = (V) hoMeta.getModelCls().newInstance();

						// 设置Rowkey
						hoMeta.getRowkeyFieldMeta().setRowkey(obj,
								new String(result.getRow()));

						// 设置column的值
						for (ColumnFieldMeta cfMeta : hoMeta
								.getColumnFieldMetas()) {
							cfMeta.setFieldValue(obj, result.getValue(
									Bytes.toBytes(cfMeta.getCfName()),
									Bytes.toBytes(cfMeta.getColumnName())));

						}

						// 返回对象
						return obj;
					}
				});
		return list;
	}

	/**
	 * 生成或者读取缓存的HBase对象的描述信息
	 *
	 * @param cls
	 * @return
	 */
	private static HBaseObjectMeta getHBaseObjectMeta(Class<?> cls) {
		HBaseObjectMeta modelMeta = modelMetas.get(cls);
		if (modelMeta == null) {
			modelMeta = new HBaseObjectMeta(cls);
			modelMetas.put(cls, modelMeta);
		}
		return modelMeta;
	}

	/**
	 *获得hbase连接
	 * @param config
	 * @return
	 * @throws IOException
	 */
	private static Connection getHbaseConnection (Configuration config) throws IOException{
		Connection hbaseConn = hbaseConns.get(config) ;

		// 提前做一下连接的检查
		if(hbaseConn != null && hbaseConn.isClosed()){
			// 需要关闭错误的连接
			hbaseConn.close();

			// 从缓存中清除
			hbaseConns.remove(config) ;
		}

        if(hbaseConn == null) {
			// 创建新的连接对象,简单的处理一下并发的问题
			hbaseConn = ConnectionFactory.createConnection(config) ;
			Connection oldConn = hbaseConns.put(config, hbaseConn) ;

			// 关闭老的连接(可能是因为并发的原因产生)
			if(oldConn != null && oldConn != hbaseConn){
				oldConn.close();
			}
		}

		return hbaseConn ;
	}

	/**
	 * 创建一个新的RowkeyCreator
	 *
	 * @return
	 */
	protected abstract RowkeyCreator createRowkeyCreator();
}

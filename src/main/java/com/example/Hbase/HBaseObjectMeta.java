package com.example.Hbase;


import com.example.Hbase.anotations.HBaseEntity;
import com.example.Hbase.anotations.HBaseRowkey;
import com.example.Hbase.anotations.HBaseColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HBaseObjectMeta {
	private static Logger log = Logger.getLogger(HBaseObjectMeta.class);

	// 表名
	private String tableName;

	// 主键字段的描述,TODO : 以后需要缓存
	private RowkeyFieldMeta rowkeyFieldMeta;

	// 属性字段的描述
	private List<ColumnFieldMeta> columnFieldMetas;

	// model对象的类型
	private Class<?> modelCls;

	/**
	 * 兼容之前的构造函数
	 * @param cls
	 */
	public HBaseObjectMeta(Class<?> cls) {
		this(cls , true) ; 
	}
	
	/**
	 * 构造函数
	 * @param cls       - 要解析的类
	 * @param recursion - 是否递归解析 - false : 表明是基类
	 */
	public HBaseObjectMeta(Class<?> cls,boolean recursion) {
		modelCls = cls;
		HBaseObjectMeta parentMeta = null ;  // 父类,可能需要回溯
		
		// 判断当前的实体类型(存在为空和非空两种情况)
		HBaseEntity entity = cls.getAnnotation(HBaseEntity.class);
		
		// 设置hbase表名
		if (entity != null && StringUtils.isNotEmpty(entity.tableName())) {
			tableName = entity.tableName();
		}
		
		// 考虑解析父类的Annotation : TODO. 后续需要考虑多重继承的问题.是否只允许两级
		if(recursion){
			// 检查父类的annotation
			HBaseEntity parentEntity = cls.getSuperclass().getAnnotation(HBaseEntity.class);
			
			// 获取父类的meta(需要和父类的配置进行合并)
			if(parentEntity != null){
			   // 目前只考虑继承一次	
			   parentMeta = new HBaseObjectMeta(cls.getSuperclass(),false);
			   
			   // 如果父类的表名为空，就用子类的表名
		       if(StringUtils.isNotEmpty(parentMeta.getTableName())){
		    	   // 如果子类中已经有表明,用父类覆盖但需要输出警告
		    	   if(StringUtils.isEmpty(tableName)) {
			          tableName = parentMeta.getTableName() ;
		    	   } else {
		    		  log.warn(" Exist table : " + tableName + " already!");
		    	   }
		       }		
			} 	
		}
		
		// 检查表名是否存在,父类不需要检查
		if(recursion){
			if(StringUtils.isEmpty(tableName)){
			   throw new RuntimeException(" Pls Set Hbase name with Annotation");
			}
			
			if (log.isDebugEnabled()) {
				log.debug(" table name = " + tableName);
			}			
		}

		if(parentMeta == null){
		    columnFieldMetas = new ArrayList<ColumnFieldMeta>();
		} else {
			// 用父类的属性填充
			columnFieldMetas = parentMeta.getColumnFieldMetas() ;
			rowkeyFieldMeta = parentMeta.getRowkeyFieldMeta() ;
		}

		// 分析所有的字段信息
		Field[] fields = cls.getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				if (field.isAnnotationPresent(HBaseRowkey.class)) {
					// 做个检查,子类不允许定义RK字段
					if(parentMeta != null){
						throw new RuntimeException (" Can not define rk field in child class!");
					}
					
					// 获得主键Meta信息
					if (log.isDebugEnabled()) {
						log.debug(" handled Rowkey Field : " + field.getName());
					}

					rowkeyFieldMeta = new RowkeyFieldMeta(field);
				} else if (field.isAnnotationPresent(HBaseColumn.class)) {
					// 获得属性信息
					if (log.isDebugEnabled()) {
						log.debug(" handle Column Field : " + field.getName()
								+ " - "
								+ field.isAnnotationPresent(HBaseColumn.class));
					}

					columnFieldMetas.add(new ColumnFieldMeta(field));
				}
			}
		}

		// 需要检查
		if (rowkeyFieldMeta == null) {
			throw new RuntimeException(" No valid rowkey field for " + cls);
		}

		// TODO : 是否需要抛出异常
		if (columnFieldMetas.size() == 0) {
			// log.warn(" No Valid Column for " + cls);
			throw new RuntimeException (" No Valid Column for " + cls);
		}
		
		// 检查是否有重复的Hbase字段名
		HashSet<String> fieldCheckingSet = new HashSet<String>();
		for(ColumnFieldMeta cfMeta : columnFieldMetas){
			// 拼装CF名称+Column名称
			String key = String.format("(%s)-(%s)",cfMeta.getCfName(),cfMeta.getColumnName()) ; 
			if(!fieldCheckingSet.add(key)){
				throw new RuntimeException ("Duplicated Field Definition of " + cls);
			}
		}
	}
    
	/**
	 * 只在内部使用
	 * @return
	 */
	protected String getTableName() {
		return tableName;
	}
	
	/**
	 * 获得完整的表名
	 * @param namespace
	 * @return
	 */
	public String getFullTableName (String namespace) {
		if(!StringUtils.isEmpty(namespace)){
			return namespace + ":" + tableName ; 
		} else {
			return tableName ; 
		}
	}

	public RowkeyFieldMeta getRowkeyFieldMeta() {
		return rowkeyFieldMeta;
	}

	public List<ColumnFieldMeta> getColumnFieldMetas() {
		return columnFieldMetas;
	}

	public Class<?> getModelCls() {
		return modelCls;
	}

	/**
	 * 判断是否对应对应的Column
	 * 
	 * @param columnName
	 * @return
	 */
	public boolean existColumn(String columnName) {
		for (ColumnFieldMeta meta : columnFieldMetas) {
			if (meta.getColumnName().equals(columnName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断是否Rowkey
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean isRowkey(String fieldName) {
		return rowkeyFieldMeta.getFieldName().equals(fieldName);
	}

	/**
	 * 从所有字段中查找指定列名的字段
	 * 
	 * @param columnName
	 * @return
	 */
	public ColumnFieldMeta getColumnFieldMetaByColumnName(String columnName) {
		for (ColumnFieldMeta meta : columnFieldMetas) {
			if (meta.getColumnName().equals(columnName)) {
				return meta;
			}
		}

		if (log.isDebugEnabled()) {
			log.debug(" Unknown field ; Column Name = " + columnName);
		}

		return null;
	}

	/**
	 * 从所有字段中查找指定列名的字段
	 * 
	 * @param columnName
	 * @return
	 */
	public ColumnFieldMeta getColumnFieldMetaByFieldName(String fieldName) {
		for (ColumnFieldMeta meta : columnFieldMetas) {
			if (meta.getFieldName().equals(fieldName)) {
				return meta;
			}
		}

        log.warn(" Unknown field ; Field Name = " + fieldName);

		return null;
	}
}

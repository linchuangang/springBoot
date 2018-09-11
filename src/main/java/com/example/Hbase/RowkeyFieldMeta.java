package com.example.Hbase;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * 存放数据列的字段Meta
 * 
 * @author panlei
 * 
 */
public class RowkeyFieldMeta {
	private static Logger log = Logger.getLogger(RowkeyFieldMeta.class);
	
	// 属性字段的名称
	private String fieldName;
	
	// 访问对象的字段(缓存的目的是为了性能)
	private Field field ; 
	
	/**
	 * 构造函数
	 * 
	 * @param field
	 */
	public RowkeyFieldMeta(Field field) {
		// Rowkey 必须是字符串类型
		if(field.getType() != String.class){
			throw new RuntimeException ( field.toString() + " must be String type!");
		}
		
		field.setAccessible(true);
		this.field = field;
		fieldName = field.getName();
				
		if(log.isInfoEnabled()){
			// TODO : output the class description
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public Field getField() {
		return field;
	}
	
	public String getRowkey (Object obj){
		try {
		    return field.get(obj).toString() ;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 设置对象的字段值
	 * @param obj
	 * @param rowkey - HBase返回的总是字符串
	 * @return
	 */
	public Object setRowkey(Object obj,String rowkey){
		try {
		    field.set(obj, rowkey);
		
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

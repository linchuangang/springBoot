package com.example.Hbase;


import com.example.Hbase.anotations.HBaseColumn;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * 存放数据列的字段Meta
 * 
 * @author panlei
 * 
 */
public class ColumnFieldMeta {
	private static Logger log = Logger.getLogger(ColumnFieldMeta.class);
	
	// 默认的ColumnFamily名称
	private final static String DEFAULT_COLUMN_FAMILY_NAME = "cf" ;
	
	// 属性字段的名称
	private String fieldName;

	// ColumnFamily的名称
	private String cfName ; 
	
	// HBase字段名称
	private String columnName;

	// 属性中的字段类型
	//private Class<?> fieldType;
	
	// 访问对象的字段(缓存的目的是为了性能)
	private Field field ; 

	/**
	 * 构造函数
	 * 
	 * @param field
	 */
	public ColumnFieldMeta(Field field) {
		field.setAccessible(true);
		this.field = field;
		HBaseColumn attrField = field.getAnnotation(HBaseColumn.class);
		fieldName = field.getName();
		if (!StringUtils.isEmpty(attrField.name())) {
			// TODO : 后续需要根据字段名做一些转换
			columnName = attrField.name();
		} else {
			columnName = fieldName;
		}
		
		if (!StringUtils.isEmpty(attrField.cf())) {
			// TODO : 后续需要根据字段名做一些转换
			cfName = attrField.cf();
		} else {
			cfName = DEFAULT_COLUMN_FAMILY_NAME; 
		}
				
		if(log.isInfoEnabled()){
			// TODO : output the class description
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getCfName() {
		return cfName;
	}

	public Field getField() {
		return field;
	}
	
	public byte[] getFieldValue (Object obj){
		try {
			Object value = field.get(obj) ;
			if(value != null){
				Class<?> type = field.getType()  ;
				if(type.isPrimitive()){
					if(type == Integer.TYPE){
						return Bytes.toBytes(field.getInt(obj)) ;
					} else if(type == Long.TYPE){
						return Bytes.toBytes(field.getLong(obj)) ;
					} 
				} else {
					if( type == Integer.class){
					    return Bytes.toBytes((Integer)field.get(obj)) ;
					} else if(type  == Long.class){
						return Bytes.toBytes((Long)field.get(obj)) ;
					} else if(type  == Double.class){
						return Bytes.toBytes((Double)field.get(obj)) ;
					} else if(type  == String.class){
						return Bytes.toBytes(field.get(obj).toString()) ;
					} 
				}
				
				throw new RuntimeException(" Unsupported Field Type : " + type);
			}
			
			return null ; 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 设置对象的字段值
	 * @param obj
	 * @param value - HBase返回的总是字符串
	 * @return
	 */
	public Object setFieldValue(Object obj,byte[] value){
		try {
			if(value != null) { 
				Class<?> type = field.getType() ;
				if(type.isPrimitive()){
					if(type == Integer.TYPE){
						field.setInt(obj, Bytes.toInt(value));
					} else if(type == Long.TYPE){
						field.setLong(obj, Bytes.toLong(value));
					} else {
						throw new RuntimeException(" Unsupported Field Type : " + type);
					}
				} else  {
					if(type == Integer.class){
						field.set(obj, Bytes.toInt(value));
					} else if(type == Long.class){
						field.set(obj, Bytes.toLong(value));
					} else if(type == Double.class){
						field.set(obj, Bytes.toDouble(value));
					} else if(type == String.class){
						field.set(obj, Bytes.toString(value));
					} else {
						throw new RuntimeException(" Unsupported Field Type : " + type);
					}		
				}
			}
		
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

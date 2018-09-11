package com.example.Hbase.anotations;

import java.lang.annotation.*;

/**
 * 定义hbase表的相关属性
 * @author lenovo
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseEntity {
   //hbase表名,不存在就使用类名的默认转换规则
   String tableName() default "" ; 	
}

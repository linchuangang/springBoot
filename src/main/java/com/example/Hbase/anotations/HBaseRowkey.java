package com.example.Hbase.anotations;

import java.lang.annotation.*;

/**
 * rowkey的定义
 * 
 * @author lenovo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseRowkey {
   // rowkey的格式,暂时没有用	
   String format() default "";
}

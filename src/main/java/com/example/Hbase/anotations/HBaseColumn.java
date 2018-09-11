package com.example.Hbase.anotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseColumn {
    // column family名称,默认使用cf
    String cf() default "cf" ;

    // 对应的字段名.为空的时候使用字段名转成小写
    String name() default "" ;
}


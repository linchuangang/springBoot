package com.example.Hbase.audit.model;


import com.example.Hbase.anotations.HBaseColumn;

/**
 * Created by Administrator on 2017/12/20.
 */
public class Audit extends AuditEntity {
    //操作前的对象
    @HBaseColumn(cf = "df", name = "oldo")
    private String oldObject;
    //操作后的对象
    @HBaseColumn(cf = "df", name = "newo")
    private String newObject;

    public String getOldObject() {
        return oldObject;
    }

    public void setOldObject(String oldObject) {
        this.oldObject = oldObject;
    }

    public String getNewObject() {
        return newObject;
    }

    public void setNewObject(String newObject) {
        this.newObject = newObject;
    }
}

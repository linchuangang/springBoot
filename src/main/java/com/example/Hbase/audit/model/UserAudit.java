package com.example.Hbase.audit.model;


import com.example.Hbase.anotations.HBaseEntity;
import com.example.Hbase.anotations.HBaseRowkey;
import com.example.Hbase.anotations.HBaseColumn;

/**
 * Created by Administrator on 2017/12/20.
 */
@HBaseEntity(tableName = "USER_AUDIT")
public class UserAudit {
    @HBaseRowkey
    private String id;
    @HBaseColumn(cf = "cf", name = "uid")
    private Integer userId;
    @HBaseColumn(cf = "cf", name = "fc")
    private String factoryCode;
    //AuditEntityid
    @HBaseColumn(cf = "cf", name = "aid")
    private String auditId;
    @HBaseColumn(cf = "cf", name = "ht")
    //操作时间
    private Long happenTime;

    public Long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Long happenTime) {
        this.happenTime = happenTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }
}

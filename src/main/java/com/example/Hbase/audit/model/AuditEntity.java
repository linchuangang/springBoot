package com.example.Hbase.audit.model;


import com.example.Hbase.anotations.HBaseEntity;
import com.example.Hbase.anotations.HBaseRowkey;
import com.example.Hbase.anotations.HBaseColumn;

/**
 * Created by Administrator on 2017/12/20.
 */
@HBaseEntity(tableName = "AUDIT")
public class AuditEntity {
    //rowkey
    @HBaseRowkey
    private String id;
    //操作对象id
    @HBaseColumn(cf = "cf", name = "oid")
    private Integer objectId;
    //操作类型
    @HBaseColumn(cf = "cf", name = "ot")
    private Integer operationType;
    //操作对象
    @HBaseColumn(cf = "cf", name = "at")
    private Integer auditType;
    //工厂编码
    @HBaseColumn(cf = "cf", name = "fc")
    private String factoryCode;
    //操作时间
    @HBaseColumn(cf = "cf", name = "ht")
    private Long happenTime;
    //用户id
    @HBaseColumn(cf = "cf", name = "uid")
    private Integer userId;
    @HBaseColumn(cf = "cf", name = "un")
    private String userName;
    //0 PC端，1 移动端
    @HBaseColumn(cf = "cf", name = "weixin")
    private Integer mobile;

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

package com.example.Hbase.audit.dto;

/**
 * Created by Administrator on 2017/12/26.
 */


import com.example.Hbase.enums.AuditTypeEnum;
import com.example.Hbase.enums.OperationTypeEnum;

/**
 * 审计参数dto--
 */
public class AuditDto {
    //操作来源 0 微信 1 pc
    Integer mobile;
    //工厂id
    Integer factoryId;
    //开始时间
    String startTime;
    //结束时间
    String endTime;
    //当前页
    Integer pageNum;
    //页大小
    Integer pageSize;
    //业务对象
    Integer auditType;
    //操作对象
    Integer operationType;
    //用户id
    Integer userId;
    //业务对象id
    Integer objectId;
    //工厂编码
    String factoryCode;
    //审计业务对象枚举
    AuditTypeEnum auditTypeEnum;
    //操作类型枚举
    OperationTypeEnum operationTypeEnum;
    //用户名
    String userName;
    //老对象
    String oldObject;
    //新对象
    String newObject;

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public AuditTypeEnum getAuditTypeEnum() {
        return auditTypeEnum;
    }

    public void setAuditTypeEnum(AuditTypeEnum auditTypeEnum) {
        this.auditTypeEnum = auditTypeEnum;
    }

    public OperationTypeEnum getOperationTypeEnum() {
        return operationTypeEnum;
    }

    public void setOperationTypeEnum(OperationTypeEnum operationTypeEnum) {
        this.operationTypeEnum = operationTypeEnum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

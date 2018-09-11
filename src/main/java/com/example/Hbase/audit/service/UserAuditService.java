package com.example.Hbase.audit.service;

import com.example.Hbase.audit.model.UserAudit;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */
public interface UserAuditService {
    List<UserAudit> ListByUserId(String factoryCode, Integer userId, Long startTime, Long endTime);
}

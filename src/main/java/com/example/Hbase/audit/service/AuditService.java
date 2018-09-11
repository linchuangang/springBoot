package com.example.Hbase.audit.service;


import com.example.Hbase.audit.dto.AuditDto;
import com.example.Hbase.audit.model.Audit;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */
public interface AuditService {

    /**
     * 存审计记录
     */
    void saveAudit(AuditDto auditDto);


    /**
     * 取审计记录
     *
     * @return
     */
    List<Audit> listAudits(AuditDto auditDto);

    /**
     * 根据操作类型获取记录
     * @return
     */
    List<Audit> listAuditsByOperationType(AuditDto auditDto);

    /**
     * 根据业务对象类型获取记录
     * @return
     */
    List<Audit> listAuditsByAuditType(AuditDto auditDto);

    /**
     * 根据rowKey查询
     *
     * @param rowKey
     * @return
     */
    Audit getAuditByRowKey(String rowKey);


}

package com.example.Hbase.audit.service.impl;


import com.example.Hbase.RowkeyCreator;
import com.example.Hbase.audit.dto.AuditDto;
import com.example.Hbase.audit.model.Audit;
import com.example.Hbase.audit.model.UserAudit;
import com.example.Hbase.audit.service.AuditService;
import com.example.Hbase.dao.BaseHbaseDao;
import com.google.common.base.Joiner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Administrator on 2017/12/20.
 */
@Service
public class AuditServiceImpl implements AuditService {
    private static Log logger = LogFactory.getLog(AuditServiceImpl.class);
    @Resource
    BaseHbaseDao<Audit> auditDao = null;
    @Resource
    BaseHbaseDao<UserAudit> userAuditDao = null;

    @Override
    public void saveAudit(AuditDto auditDto) {
        logger.info("保存...");
        try {
            RowkeyCreator creator = auditDao.getRowkeyCreator();
            //保存审计记录
            Audit audit = new Audit();
            audit.setId(creator.createRowkey(auditDto.getFactoryCode(), auditDto.getAuditTypeEnum().getCode(), auditDto.getOperationTypeEnum().getCode(), auditDto.getObjectId() + ""));
            audit.setAuditType(auditDto.getAuditTypeEnum().getKey());
            audit.setFactoryCode(auditDto.getFactoryCode());
            audit.setObjectId(auditDto.getObjectId());
            audit.setOperationType(auditDto.getOperationTypeEnum().getKey());
            audit.setOldObject(auditDto.getOldObject());
            audit.setNewObject(auditDto.getNewObject());
            audit.setHappenTime(System.currentTimeMillis());
            audit.setUserId(auditDto.getUserId());
            audit.setMobile(auditDto.getMobile());
            auditDao.saveHBaseRecord(audit);
            //保存用户记录
            UserAudit userAudit = new UserAudit();
            userAudit.setHappenTime(System.currentTimeMillis());
            userAudit.setId(creator.createRowkey(auditDto.getFactoryCode(), auditDto.getUserId() + ""));
            userAudit.setUserId(auditDto.getUserId());
            userAudit.setFactoryCode(auditDto.getFactoryCode());
            userAudit.setAuditId(creator.createRowkey(auditDto.getFactoryCode(), auditDto.getAuditTypeEnum().getCode(), auditDto.getOperationTypeEnum().getCode(), auditDto.getObjectId() + ""));
            userAuditDao.saveHBaseRecord(userAudit);
            logger.info("ok");
        } catch (Exception e) {
            logger.info("存审计记录出错" + e.getMessage(), e);
        }
    }

    @Override
    public List<Audit> listAudits(AuditDto auditDto) {
        logger.info("查询...");
        RowkeyCreator creator = auditDao.getRowkeyCreator();
        Scan scan = new Scan();
        String joiner = "#";
        try {
            scan.setTimeRange(Long.parseLong(auditDto.getStartTime()), Long.parseLong(auditDto.getEndTime()));
            //判断工厂编码
            if (auditDto.getFactoryCode() != null) {
                scan.setRowPrefixFilter(creator.getRowkeyPrefixBytes(auditDto.getFactoryCode()));
            }
            //业务类型
            if (auditDto.getFactoryCode() != null && auditDto.getAuditTypeEnum() != null) {
                scan.setRowPrefixFilter(creator.getRowkeyPrefixBytes(Joiner.on(joiner).join(auditDto.getFactoryCode(), auditDto.getAuditTypeEnum().getCode()).toString()));
            }
            //操作类型
            if (auditDto.getFactoryCode() != null && auditDto.getAuditTypeEnum() != null && auditDto.getOperationTypeEnum() != null) {
                scan.setRowPrefixFilter(creator.getRowkeyPrefixBytes(Joiner.on(joiner).join(auditDto.getFactoryCode(), auditDto.getAuditTypeEnum().getCode(), auditDto.getOperationTypeEnum().getCode()).toString()));
            }
            //业务对象id
            if (auditDto.getObjectId() != null && auditDto.getFactoryCode() != null && auditDto.getAuditTypeEnum() != null && auditDto.getOperationTypeEnum() != null) {
                scan.setRowPrefixFilter(creator.getRowkeyPrefixBytes(Joiner.on(joiner).join(auditDto.getFactoryCode(), auditDto.getAuditTypeEnum().getCode(), auditDto.getOperationTypeEnum().getCode(), auditDto.getObjectId()).toString()));
            }
            //scan.setMaxResultSize(100);
            List<Audit> auditList = auditDao.queryHBaseRecords(Audit.class, scan);
            if (auditList != null) {
                Collections.sort(auditList, new Comparator<Audit>() {
                    @Override
                    public int compare(Audit o1, Audit o2) {
                        return o2.getHappenTime().intValue() - o1.getHappenTime().intValue();
                    }
                });
            }
            return auditList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Audit> listAuditsByOperationType(AuditDto auditDto) {
        return null;
    }

    @Override
    public List<Audit> listAuditsByAuditType(AuditDto auditDto) {
        return null;
    }

    @Override
    public Audit getAuditByRowKey(String rowKey) {
        return auditDao.findHBaseRecord(Audit.class, rowKey);
    }

    public BaseHbaseDao<Audit> getAuditDao() {
        return auditDao;
    }

    public void setAuditDao(BaseHbaseDao<Audit> auditDao) {
        this.auditDao = auditDao;
    }

    public BaseHbaseDao<UserAudit> getUserAuditDao() {
        return userAuditDao;
    }

    public void setUserAuditDao(BaseHbaseDao<UserAudit> userAuditDao) {
        this.userAuditDao = userAuditDao;
    }

}

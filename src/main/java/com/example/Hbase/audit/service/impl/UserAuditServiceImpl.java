package com.example.Hbase.audit.service.impl;

import com.example.Hbase.RowkeyCreator;
import com.example.Hbase.audit.model.UserAudit;
import com.example.Hbase.audit.service.UserAuditService;
import com.example.Hbase.dao.BaseHbaseDao;
import com.google.common.base.Joiner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Scan;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */
public class UserAuditServiceImpl implements UserAuditService {
    private static Log logger = LogFactory.getLog(AuditServiceImpl.class);

    private BaseHbaseDao<UserAudit> userAuditDao = null;

    @Override
    public List<UserAudit> ListByUserId(String factoryCode, Integer userId, Long startTime, Long endTime) {
        RowkeyCreator rowkeyCreator = userAuditDao.getRowkeyCreator();
        Scan scan = new Scan();
        String join = "#";
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        }
        try {
            scan.setTimeRange(startTime, endTime);
            //业务类型
            if (factoryCode != null && userId != null) {
                scan.setRowPrefixFilter(rowkeyCreator.getRowkeyPrefixBytes(Joiner.on(join).join(factoryCode, userId).toString()));
            }
            List<UserAudit> list = userAuditDao.queryHBaseRecords(UserAudit.class, scan);
            if (list != null) {
                Collections.sort(list, new Comparator<UserAudit>() {
                    @Override
                    public int compare(UserAudit o1, UserAudit o2) {
                        return o2.getHappenTime().intValue() - o1.getHappenTime().intValue();
                    }
                });
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseHbaseDao<UserAudit> getUserAuditDao() {
        return userAuditDao;
    }

    public void setUserAuditDao(BaseHbaseDao<UserAudit> userAuditDao) {
        this.userAuditDao = userAuditDao;
    }
}

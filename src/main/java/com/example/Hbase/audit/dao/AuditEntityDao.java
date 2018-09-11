package com.example.Hbase.audit.dao;


import com.example.Hbase.RowkeyCreator;
import com.example.Hbase.audit.model.AuditEntity;
import com.example.Hbase.dao.BaseHbaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/12/25.
 */
@Repository
public class AuditEntityDao extends BaseHbaseDao<AuditEntity> {
    @Override
    protected RowkeyCreator createRowkeyCreator() {
        return new RowkeyCreator(RowkeyCreator.TimeAggregateType.second);
    }
}

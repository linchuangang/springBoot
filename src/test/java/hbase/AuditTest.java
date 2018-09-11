package hbase;

import com.alibaba.fastjson.JSON;
import com.example.DemoApplicationTests;
import com.example.Hbase.audit.dao.AuditDao;
import com.example.Hbase.audit.dto.AuditDto;
import com.example.Hbase.audit.model.Audit;
import com.example.Hbase.audit.service.AuditService;
import org.junit.Test;

import javax.annotation.Resource;

public class AuditTest extends DemoApplicationTests {

    @Resource
    AuditService auditService;

    @Test
    public void testAudit() {
        Audit audit=auditService.getAuditByRowKey("0769001#0001#1001#3106#1536317279");
        System.out.println(JSON.toJSONString(audit));
    }
}

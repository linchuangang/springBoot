package com.example.Hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

@Component
public class HbaseUtil {

    @Value("${hbase.zookeeper.quorum}")
    private String quorum;
    @Value("${hbase.zookeeper.port}")
    private String port;

    @Bean
    public HbaseTemplate hbaseTemplate() {
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", quorum);
        conf.set("hbase.zookeeper.port", port);
        hbaseTemplate.setConfiguration(conf);
        hbaseTemplate.setAutoFlush(true);
        return hbaseTemplate;
    }
}

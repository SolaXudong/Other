package com.xu.tt.pub.hbase;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTablePool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("deprecation")
// @Configuration
public class HBaseConfig_tmp {

	@Bean
	public HBaseAdmin hBaseAdmin(@Value("${hbase.zookeeper.quorum}") String quorum,
			@Value("${hbase.zookeeper.port}") String port) throws Exception {
		org.apache.hadoop.conf.Configuration conf = org.apache.hadoop.hbase.HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", quorum);
		conf.set("hbase.zookeeper.port", port);
		HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
		return hBaseAdmin;
	}

	@Bean
	public HTablePool hTablePool(@Value("${hbase.zookeeper.quorum}") String quorum,
			@Value("${hbase.zookeeper.port}") String port, @Value("${hbase.zookeeper.hbase-pool-size}") Integer size) {
		org.apache.hadoop.conf.Configuration conf = org.apache.hadoop.hbase.HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", quorum);
		conf.set("hbase.zookeeper.port", port);
		HTablePool hTablePool = new HTablePool(conf, size);
		return hTablePool;
	}

}

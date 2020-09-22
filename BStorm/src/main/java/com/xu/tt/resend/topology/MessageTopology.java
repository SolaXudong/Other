package com.xu.tt.resend.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import com.xu.tt.resend.bolt.SpliterBolt;
import com.xu.tt.resend.bolt.WriterBolt;
import com.xu.tt.resend.spout.MessageSpout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageTopology {

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new MessageSpout());
		builder.setBolt("split-bolt", new SpliterBolt()).shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriterBolt()).shuffleGrouping("split-bolt");
		// 本地配置
		Config config = new Config();
		config.setDebug(false);
		LocalCluster cluster = new LocalCluster();
		System.out.println(cluster);
		cluster.submitTopology("message", config, builder.createTopology());
		Thread.sleep(5000);
		cluster.killTopology("message");
		cluster.shutdown();
		log.info("######### cost : " + (System.currentTimeMillis() - cost) / 1000F + " s");
	}

}

package com.xu.tt.drpc.basic;

import java.util.LinkedList;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import lombok.extern.slf4j.Slf4j;

/**
 * This topology is a basic example of doing distributed RPC on top of Storm. It
 * implements a function that appends a "!" to any string you send the DRPC
 * function.
 * <p/>
 * See https://github.com/nathanmarz/storm/wiki/Distributed-RPC for more
 * information on doing distributed RPC on top of Storm.
 */
@Slf4j
public class BasicDRPCTopology {

	public static class ExclaimBolt extends BaseBasicBolt {
		public void execute(Tuple tuple, BasicOutputCollector collector) {
			String input = tuple.getString(1);
			log.info("========== " + tuple.getValue(0));
			collector.emit(new Values(tuple.getValue(0), input + "!"));
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id", "result"));
		}
	}

	public static class ExclaimBolt2 extends BaseBasicBolt {
		public void execute(Tuple tuple, BasicOutputCollector collector) {
			String input = tuple.getString(1);
			log.info("========== " + tuple.getValue(0));
			collector.emit(new Values(tuple.getValue(0), input + "!"));
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id", "result2"));
		}
	}

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		// 创建drpc实例
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("exclamation");
		// 添加bolt
		builder.addBolt(new ExclaimBolt(), 3);
		builder.addBolt(new ExclaimBolt2(), 3).shuffleGrouping();
		Config conf = new Config();
		if (args == null || args.length == 0) {
			log.info("########## windows");
			LocalDRPC drpc = new LocalDRPC();
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpc));

			LinkedList<String> rs = new LinkedList<String>();
			for (String word : new String[] { "hello", "goodbye" }) {
				rs.add(word + " | " + drpc.execute("exclamation", word));
			}

			cluster.shutdown();
			drpc.shutdown();
			log.info("########## Results: " + rs);
		} else {
			log.info("########## linux");
			conf.setNumWorkers(3);
			StormSubmitter.submitTopology(args[0], conf, builder.createRemoteTopology());
		}
		log.info("######### cost : " + (System.currentTimeMillis() - cost) / 1000F + " s");
	}

}

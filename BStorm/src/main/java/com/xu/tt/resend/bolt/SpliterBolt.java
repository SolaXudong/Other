package com.xu.tt.resend.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SpliterBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

	private OutputCollector collector;

	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	private boolean flag = false;

	public void execute(Tuple tuple) {
		try {
			String subjects = tuple.getStringByField("subjects");

//			if (!flag && subjects.equals("d1,d2")) {
//				flag = true;
//				int a = 1 / 0;
//			}

			String[] words = subjects.split(",");
			// List<String> list = new ArrayList<String>();
			// int index = 0;
			for (String word : words) {
				// 注意这里循环发送消息，要携带tuple对象，用于处理异常时重发策略
				collector.emit(tuple, new Values(word));
				// list.add(word);
				// index ++;
			}
			// collector.emit(tuple, new Values(list));
			collector.ack(tuple);
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(tuple);
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	public void cleanup() {

	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}

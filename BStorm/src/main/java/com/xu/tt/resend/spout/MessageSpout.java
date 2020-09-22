package com.xu.tt.resend.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class MessageSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;

	private int index = 0;

//	private String[] subjects = new String[] { "groovy,oeacnbase", "openfire,restful", "flume,activiti", "hadoop,hbase",
//			"spark,sqoop" };
	private String[] subjects = new String[] { "a1,a2", "b1,b2", "c1,c2", "d1,d2", "e1,e2" };

	private SpoutOutputCollector collector;

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		if (index < subjects.length) {
			String sub = subjects[index];
			// 发送信息参数1 为数值， 参数2为msgId
			collector.emit(new Values(sub), index);
			index++;
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("subjects"));
	}

	public void ack(Object msgId) {
		System.out.println("########## 【消息发送成功!!!】(msgId = " + msgId + ")");
	}

	public void fail(Object msgId) {
		System.out.println("########## 【消息发送失败!!!】(msgId = " + msgId + ")");
		collector.emit(new Values(subjects[(Integer) msgId]), msgId);
		System.out.println("########## 【已重新发送!!!】(msgId = " + msgId + ")");
	}

	public void close() {

	}

	public void activate() {

	}

	public void deactivate() {

	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}

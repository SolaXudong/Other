package com.xu.tt.resend.bolt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WriterBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

	private FileWriter writer;

	private OutputCollector collector;

	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		try {
			writer = new FileWriter("D:\\tt\\1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean flag = false;

	public void execute(Tuple tuple) {
		String word = tuple.getString(0);
		try {
			if (!flag && word.equals("e1")) {
				flag = true;
				int a = 1 / 0;
			}
			writer.write(word + " | " + 1);
			writer.write("\r\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(tuple);
			try {
				writer.write(word + " | " + 0);
				writer.write("\r\n");
				writer.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		collector.emit(tuple, new Values(word));
		collector.ack(tuple);
	}

	public void cleanup() {

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}

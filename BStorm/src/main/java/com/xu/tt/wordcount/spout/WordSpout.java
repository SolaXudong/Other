package com.xu.tt.wordcount.spout;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class WordSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;

	private SpoutOutputCollector collector;

	private int index = 0;

	private String[] sentences = { "my dog has fleas", "i like cold beverages", "the dog ate my homework",
			"don't have a cow man", "i don't think i like fleas" };

	public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		this.collector.emit(new Values(sentences[index]));
		index++;
		if (index >= sentences.length) {
			index = 0;
		}
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

	public void ack(Object msgId) {
	}

	public void fail(Object msgId) {
	}

	public void activate() {

	}

	public void close() {

	}

	public void deactivate() {

	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}

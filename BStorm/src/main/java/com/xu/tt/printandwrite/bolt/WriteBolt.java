package com.xu.tt.printandwrite.bolt;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WriteBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(WriteBolt.class);

	private FileWriter writer;

	public void execute(Tuple input, BasicOutputCollector collector) {
		// 获取上一个组件所声明的Field
		String text = Thread.currentThread().getName() + " " + input.getStringByField("write");
		String path = "D:\\tt\\" + this + ".txt";
		try {
//			if (writer == null) {
//				if (System.getProperty("os.name").equals("Windows 10")) {
//					writer = new FileWriter("D:\\tt\\" + this);
//				} else if (System.getProperty("os.name").equals("Windows 8.1")) {
//					writer = new FileWriter("D:\\tt\\" + this);
//				} else if (System.getProperty("os.name").equals("Windows 7")) {
//					writer = new FileWriter("D:\\tt\\" + this);
//				} else if (System.getProperty("os.name").equals("Linux")) {
//					System.out.println("----:" + System.getProperty("os.name"));
//					writer = new FileWriter("/home/work/tmp/" + this);
//				}
//			}
			log.info("【write】： 写入文件 " + text);
//			writer.write(text);
//			writer.write("\n");
//			writer.flush();

			text = text.concat("\r\n");
			FileUtils.writeStringToFile(new File(path), text, "utf-8", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}

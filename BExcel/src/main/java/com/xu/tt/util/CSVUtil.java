package com.xu.tt.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-10-28 00:19:44
 */
@Slf4j
public class CSVUtil {

	private List<Map<String, String>> recordList = new ArrayList<>();
	private long cost = 0;

	@Before
	public void init() {
		for (int i = 0; i < 5; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("name", "zhangsan");
			map.put("code", "001");
			recordList.add(map);
		}
		cost = System.currentTimeMillis();
	}

	@After
	public void end() {
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

	/**
	 * @tips LOOK
	 */
	@Test
	public void testRead() throws IOException {
		InputStream is = new FileInputStream("D:/tt/test.csv");
		InputStreamReader isr = new InputStreamReader(is, "GBK");
		Reader reader = new BufferedReader(isr);
		CSVParser parser = CSVFormat.EXCEL.withHeader("name", "age", "jia", "A", "B", "C", "D", "E", "F", "G")
				.parse(reader);
//        CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader("name", "age", "jia"));
		List<CSVRecord> list = parser.getRecords();
		for (CSVRecord r : list) {
			log.info("{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}", r.getRecordNumber(), r.get("name"), r.get("age"),
					r.get("jia"), r.get("A"), r.get("B"), r.get("C"), r.get("D"), r.get("E"), r.get("F"), r.get("G"));
		}
		parser.close();
	}

	/**
	 * @tips LOOK
	 */
	@Test
	public void testWrite() throws Exception {
		FileOutputStream fos = new FileOutputStream("D:/tt/test.csv");
		OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
		CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡", "A", "B", "C", "D", "E", "F", "G");
		CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
//		csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
		for (int i = 0; i < 1_0000; i++)
			csvPrinter.printRecord("张三_" + i, 20 + i, "湖北_" + i, "属性A_" + i, "属性B_" + i, "属性C_" + i, "属性D_" + i,
					"属性E_" + i, "属性F_" + i, "属性G_" + i);
		csvPrinter.flush();
		csvPrinter.close();
	}

	/**
	 * Parsing an Excel CSV File
	 */
	@Test
	public void testParse() throws Exception {
		Reader reader = new FileReader("D:/tt/test.csv");
		CSVParser parser = CSVFormat.EXCEL.parse(reader);
		for (CSVRecord record : parser.getRecords()) {
			log.info("{}", record);
		}
		parser.close();
	}

	/**
	 * Defining a header manually
	 */
	@Test
	public void testParseWithHeader() throws Exception {
		Reader reader = new FileReader("D:/tt/test.csv");
		CSVParser parser = CSVFormat.EXCEL.withHeader("id", "name", "code").parse(reader);
		for (CSVRecord record : parser.getRecords()) {
			System.out.println(record.get("id") + "," + record.get("name") + "," + record.get("code"));
		}
		parser.close();
	}

	/**
	 * Using an enum to define a header
	 */
	enum MyHeaderEnum {
		ID, NAME, CODE;
	}

	@Test
	public void testParseWithEnum() throws Exception {
		Reader reader = new FileReader("D:/tt/test.csv");
		CSVParser parser = CSVFormat.EXCEL.withHeader(MyHeaderEnum.class).parse(reader);
		for (CSVRecord record : parser.getRecords()) {
			System.out.println(record.get(MyHeaderEnum.ID) + "," + record.get(MyHeaderEnum.NAME) + ","
					+ record.get(MyHeaderEnum.CODE));
		}
		parser.close();
	}

	/**
	 * @tips LOOK
	 */
	@Test
	public void writeMuti() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CountDownLatch doneSignal = new CountDownLatch(2);
		executorService.submit(new exprotThread("D:/tt/1.csv", recordList, doneSignal));
		executorService.submit(new exprotThread("D:/tt/2.csv", recordList, doneSignal));
		doneSignal.await();
		System.out.println("Finish!!!");
	}

	class exprotThread implements Runnable {
		private String filename;
		private List<Map<String, String>> list;
		private CountDownLatch countDownLatch;

		public exprotThread(String filename, List<Map<String, String>> list, CountDownLatch countDownLatch) {
			this.filename = filename;
			this.list = list;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				CSVPrinter printer = new CSVPrinter(new FileWriter(filename),
						CSVFormat.EXCEL.withHeader("NAME", "CODE"));
				for (Map<String, String> map : list) {
					printer.printRecord(map.values());
				}
				printer.close();
				countDownLatch.countDown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

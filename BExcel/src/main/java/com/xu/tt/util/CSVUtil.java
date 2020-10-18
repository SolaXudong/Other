package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author XuDong 2020-10-18 12:04:39
 * @tips 参考：https://www.cnblogs.com/zjk1/p/10219702.html
 */
public class CSVUtil {

	private static String CHARSET = "GBK";

	/**
	 * @tips LOOK 导入
	 */
	public static List<LinkedHashMap<String, Object>> readCSV(String filePath) {
		ArrayList<LinkedHashMap<String, Object>> dataList = Lists.newArrayList();
		try {
			File file = new File(filePath);
			if (!file.exists())
				return dataList;
			try (FileInputStream fis = new FileInputStream(file);
					InputStreamReader reader = new InputStreamReader(fis, CHARSET);) {
				Iterable<CSVRecord> records = CSVFormat.RFC4180
//						.withHeader("姓名", "性别", "编号", "描述")
						.parse(reader);
				for (CSVRecord csvRecord : records) {
//					System.out.println(csvRecord.get("姓名") + "|" + csvRecord.get("性别") + "|" + csvRecord.get("编号") + "|"
//							+ csvRecord.get("描述"));
//					System.out.println(csvRecord.get(0) + "|" + csvRecord.get(1) + "|" + csvRecord.get(2) + "|"
//							+ csvRecord.get(3));
					LinkedHashMap<String, Object> map = Maps.newLinkedHashMap();
					for (int i = 0; i < csvRecord.size(); i++) {
						map.put(i + "", csvRecord.get(i));
					}
					dataList.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * @tips LOOK
	 * @tips CSV导出
	 */
	public static void writeCSV(String filePath, List<LinkedHashMap<String, Object>> list, String... header) {
		try {
			File file = new File(filePath);
			if (file.exists())
				FileUtils.forceDelete(file);
			FileUtils.touch(file);
			try {
				Appendable printWriter = new PrintWriter(file, CHARSET);
				CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader(header).print(printWriter);
				for (Map<String, Object> map : list) {
					csvPrinter.printRecord(map.values()); // value1, value2
				}
				csvPrinter.flush();
				csvPrinter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String filePath = "D:/tt/test.csv";
		/** 准备数据 */
		String[] headers = { "姓名", "性别", "年龄", "生日" };
		ArrayList<LinkedHashMap<String, Object>> dataList = Lists.newArrayList();
		for (int i = 0; i < 5; i++) {
			LinkedHashMap<String, Object> map = Maps.newLinkedHashMap();
			map.put("name", "zhangsan_" + i);
			map.put("sex", "男");
			map.put("age", i + 20);
			map.put("birth", LocalDateTime.now());
			dataList.add(map);
		}
		/** 导出 */
		CSVUtil.writeCSV(filePath, dataList, headers);
		/** 导入 */
		List<LinkedHashMap<String, Object>> rsList = CSVUtil.readCSV(filePath);
		rsList.stream().forEach(System.out::println);
		System.out.println("########## over");
	}

}

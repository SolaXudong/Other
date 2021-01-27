package com.xu.tt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-01-27 10:59:27
 * @tips 代码参考：https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/read/NoModelDataListener.java
 */
@Slf4j
public class ExcelReadByEasyExcel2 extends AnalysisEventListener<Map<Integer, String>> {

	/**
	 * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 10000;
	List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

	@Override
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
//		log.info("解析到一条数据:{}", JSON.toJSONString(data));
		list.add(data);
		if (list.size() >= BATCH_COUNT) {
			saveData();
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		if (list.size() > 0) {
			saveData();
		}
	}

	/**
	 * 加上存储数据库
	 */
	private void saveData() {
		log.info("{}条数据，开始存储数据库！", list.size());
	}

	public static void main(String[] args) {
		long cost = System.currentTimeMillis();

		String fileName = "tt.xlsx";
		String path = "D:/tt/" + fileName;
		for (int i = 1; i <= 1; i++) { // 21.291s
			EasyExcel.read(path, new ExcelReadByEasyExcel2()).sheet().doRead();
		}

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

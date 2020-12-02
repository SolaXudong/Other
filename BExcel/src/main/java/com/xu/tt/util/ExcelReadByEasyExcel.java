package com.xu.tt.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-03 01:15:18
 * @tips EasyExcel读取表格工具类
 * @tips API参考：https://www.yuque.com/easyexcel/doc/read
 * @tips 代码参考：https://blog.csdn.net/somdip/article/details/85243005
 */
@Slf4j
@SuppressWarnings("deprecation")
public class ExcelReadByEasyExcel {

	private static List<JSONObject> list = Lists.newArrayList(); // 数据
	private static List<String> tittle = Lists.newArrayList(); // 表头

	public static List<JSONObject> parse(String filepath) {
		long cost = System.currentTimeMillis();
		list.clear();
		tittle.clear();
		log.info("##### 解析表格开始……");
		try (InputStream inputStream = new FileInputStream(filepath)) {
			ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null,
					new AnalysisEventListener<List<String>>() {
						@Override
						public void invoke(List<String> obj, AnalysisContext context) {
							if (context.readRowHolder().getRowIndex() == 0) {
								tittle.addAll(obj);
							} else {
								JSONObject tmp = new JSONObject(true);
								for (int i = 0; i < obj.size(); i++)
									tmp.put(tittle.get(i), obj.get(i));
								list.add(tmp);
							}
						}

						@Override
						public void doAfterAllAnalysed(AnalysisContext context) {
						}
					});
			excelReader.readAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("##### 解析表格结束，cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
		return list;

	}

	public static void main(String[] args) {
		long cost = System.currentTimeMillis();

		/** 准备 */
		String fileName = "#案件导入-新.xlsx";
		String path = org.springframework.util.StringUtils
				.cleanPath(System.getProperty("user.dir") + "/src/main/java/com/xu/tt/util/") + fileName;
		{ // 测试数据量
//			fileName = "#案件导入-古京-1万条.xlsx";
//			fileName = "#案件导入-时光-10万条-97971.xlsx";
			path = "D:/tt/excel/案件导入/" + fileName;
		}
		/** 解析 */
		List<JSONObject> list = ExcelReadByEasyExcel.parse(path);
		log.info("##### parse-{}", list.size());
		System.out.println(list.get(0));
//		list.stream().forEach(System.out::println);
		/** 错误数据 */
		ArrayList<JSONObject> newList = Lists.newArrayList();
		for (JSONObject obj : list) {
			JSONObject newObj = new JSONObject(true);
			newObj.put("占位", "【" + RandomStringUtils.random(4, "0123456789") + "】");
			newObj.putAll(obj);
			newList.add(newObj);
		}

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");

		/** 写出 */
		cost = System.currentTimeMillis();
		log.info("##### 写出错误数据开始……");
		String outDir = path.split("\\.")[0] + "-错误数据.csv";
		tittle.add(0, "【失败原因】");
		CSVUtil.writeByStream(outDir, tittle, newList);
		log.info("##### 写出错误数据结束，cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

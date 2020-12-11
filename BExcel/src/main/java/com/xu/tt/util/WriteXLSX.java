package com.xu.tt.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-11 14:52:04
 * @tips 代码参考：http://poi.apache.org/components/spreadsheet/quick-guide.html#ReadWriteWorkbook
 * @tips Reading and Rewriting Workbooks
 */
@Slf4j
@SuppressWarnings("deprecation")
public class WriteXLSX {

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		/** 准备 */
		String fileName = "案件导入模板.xlsx";
		String path = "D:/tt/excel/#测试专用/" + fileName;
		/** 读一条模板 */
		List<JSONObject> list = Lists.newArrayList();
		List<String> tittle = Lists.newArrayList();
		try (OPCPackage p = OPCPackage.open(new File(path).getPath(), PackageAccess.READ)) {
			XLSX2CSV csv = new XLSX2CSV(p);
			list = csv.process();
			tittle = csv.getTittle();
		}
		JSONObject obj = list.get(0);
		/** 写出CSV */
//		log.info("##### 写出数据开始……");
//		String outDir = path.split("\\.")[0] + "-1万.csv";
//		int rowNum = 10000 + 1;
//		int colNum = tittle.size();
//		ArrayList<JSONObject> newList = Lists.newArrayList();
//		for (int i = 1; i < rowNum; i++) {
//			obj = (JSONObject) list.get(0).clone();
//			for (int j = 0; j < colNum; j++) {
//				if (j == 0 || j == 10 || j == 12 || j == 13)
//					obj.put(tittle.get(j), obj.getString(tittle.get(j)) + String.format("%05d", i));
//			}
//			newList.add(obj);
//			if ((i - 1) % (rowNum / 100) == 0)
//				log.info(new BigDecimal(i - 1).divide(new BigDecimal(rowNum - 1), 4, RoundingMode.HALF_DOWN)
//						.multiply(new BigDecimal(100)).setScale(2) + "%");
//		}
//		CSVUtil.writeByStream(outDir, tittle, newList);
		/** 写出Excel */
//		int rowNum = 1000 + 1;
//		int colNum = tittle.size();
//		String path2 = path.split("\\.")[0] + "-1千.xlsx";
//		ArrayList<Integer> allId = Lists.newArrayList();
//		try (InputStream inp = new FileInputStream(path)) {
//			for (int i = 1; i < rowNum; i++) {
//				allId.add(i);
//			}
//			Workbook wb = WorkbookFactory.create(inp);
//			try (OutputStream fileOut = new FileOutputStream(path2)) {
//				wb.write(fileOut);
//			}
//			Sheet sheet = wb.getSheetAt(0);
//			List<List<Integer>> allList = ListUtils.partition(allId, (rowNum - 1) / 10);
//			for (List<Integer> smallList : allList) {
//				for (int i : smallList) {
//					Row row = sheet.createRow(i);
//					for (int j = 0; j < colNum; j++) {
//						Cell cell = row.createCell(j);
//						cell.setCellType(CellType.STRING);
//						String val = obj.getString(tittle.get(j));
//						if (j == 0 || j == 10 || j == 12 || j == 13)
//							cell.setCellValue(val + String.format("%05d", i));
//						else
//							cell.setCellValue(val);
//					}
//				}
//				try (OutputStream fileOut = new FileOutputStream(path2)) {
//					wb.write(fileOut);
//				}
//			}
//		}
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-11 14:52:04
 * @tips 代码参考：http://poi.apache.org/components/spreadsheet/quick-guide.html#ReadWriteWorkbook
 */
@Slf4j
public class WriteXLSX {

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		/** 准备 */
		String fileName = "案件导入模板-造数据用.xlsx";
		String path = "D:/tt/excel/#重构/" + fileName;
		/** 读一条模板 */
		List<JSONObject> list = Lists.newArrayList();
		List<String> tittle = Lists.newArrayList();
		try (OPCPackage p = OPCPackage.open(new File(path).getPath(), PackageAccess.READ)) {
			ExcelReadByPOI2 csv = new ExcelReadByPOI2(p);
			list = csv.process();
			tittle = csv.getTittle();
		}
		JSONObject obj = list.get(0);
		int type = 2;
		/** 写出CSV */
		if (type == 1) {
			log.info("##### 写出数据开始……");
			String outDir = path.split("\\.")[0] + "-xx.csv";
			int rowNum = 10000;
			int colNum = tittle.size();
			ArrayList<JSONObject> newList = Lists.newArrayList();
			for (int i = 1; i <= rowNum; i++) { // ROW
				obj = (JSONObject) list.get(0).clone();
				for (int j = 0; j < colNum; j++) {
					if (j == 0 || j == 8 || j == 10 || j == 11)
						obj.put(tittle.get(j), obj.getString(tittle.get(j)) + String.format("%06d", i));
				}
				newList.add(obj);
				if ((i - 1) % (rowNum / 100) == 0)
					log.info(new BigDecimal(i - 1).divide(new BigDecimal(rowNum - 1), 4, RoundingMode.HALF_DOWN)
							.multiply(new BigDecimal(100)).setScale(2) + "%");
			}
			CSVUtil.writeByStream(outDir, tittle, newList, false);
		}
		/** 写出Excel */
		if (type == 2) {
			String path2 = path.split("\\.")[0] + "-xxx.xlsx";
			int rowNum = 1;
			int rowNumMax = 10;
			int colNum = tittle.size();
			try (InputStream is = new FileInputStream(path)) {
				Workbook wb = WorkbookFactory.create(is);
				Sheet sheet = wb.getSheetAt(0);
				int baseNum = 0;
				for (int i = rowNum; i <= rowNumMax; i++) { // ROW
					Row row = sheet.createRow(i);
					for (int j = 0; j < colNum; j++) { // COL
						Cell cell = row.createCell(j);
						String val = obj.getString(tittle.get(j));
						if (j == 0 || j == 8 || j == 10 || j == 11)
							cell.setCellValue(val.trim() + String.format("%06d", i + baseNum));
						else
							cell.setCellValue(val);
					}
				}
				try (OutputStream os = new FileOutputStream(path2)) {
					wb.write(os);
				}
			}
		}
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

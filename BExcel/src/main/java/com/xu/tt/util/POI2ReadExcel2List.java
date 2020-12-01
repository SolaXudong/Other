package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-09-30 09:05:57
 */
@Slf4j
public class POI2ReadExcel2List {

	public static void main(String[] args) {
		long cost = System.currentTimeMillis();

		String path = org.springframework.util.StringUtils
				.cleanPath(System.getProperty("user.dir") + "/src/main/java/com/xu/tt/util/");
		String fileName = "1.xlsx";
//		path = "";
//		fileName = "D:/tt/student学员.xlsx";
		File file = new File(path + fileName);
		if (file.exists()) {
			List<String[]> list = readData(file);
//			for (String[] record : list) {
//				for (String cell : record)
//					System.out.print(cell + "\t");
//				System.out.println();
//			}
			System.out.println(list.size());
		}

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

	public static List<String[]> readData(File file) {
		List<String[]> list = Lists.newArrayList();
		String[] arr = new String[12];
		XSSFWorkbook wb = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			int numberOfSheets = 1; // wb.getNumberOfSheets();
			for (int x = 0; x < numberOfSheets; x++) {
				XSSFSheet sheet = wb.getSheetAt(x);
				int columnNum = 0;
				if (sheet.getRow(0) != null) {
					columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
				}
				if (columnNum > 0) {
					for (Row row : sheet) { // 行循环
						String[] singleRow = new String[columnNum];
						int n = 0;
						for (int i = 0; i < columnNum; i++) { // 列循环
							Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							singleRow[n] = cell.getStringCellValue().trim();
							arr[i] = singleRow[n];
						}
						list.add(arr.clone());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}

}

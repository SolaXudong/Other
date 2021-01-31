package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-01-27 05:20:03
 * @tips 代码参考：https://github.com/ksfzhaohui/blog/blob/master/casestudy/Poi读取Excel引发的内存溢出.md
 */
@Slf4j
public class ReadByPOIByUserModel {

	public static void main(String[] args) {
		long cost = System.currentTimeMillis();
		String path = "D:/tt/tt.xlsx";
		System.out.println("start read");
		for (int i = 1; i <= 10; i++) { // 44.166s
			long cost2 = System.currentTimeMillis();
			try {
				Workbook wb = null;
				File file = new File(path);
				InputStream fis = new FileInputStream(file);
				wb = new XSSFWorkbook(fis);
				Sheet sheet = wb.getSheetAt(0);
				for (Row row : sheet) {
					for (Cell cell : row) {
//						System.out.println("row:" + row.getRowNum() + ",cell:" + cell.toString());
					}
				}
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("########## {}", (System.currentTimeMillis() - cost2) / 1000F + "s");
		}
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author XuDong 2020-09-30 09:05:57
 * @tips 参考：http://www.asfx.xyz/p/32f4673947bb4e96a010527c8ad3cac7
 */
@SuppressWarnings("deprecation")
public class POI2ReadExcel2Map {

	public static void main(String[] args) {
		String path = org.springframework.util.StringUtils
				.cleanPath(System.getProperty("user.dir") + "/src/main/java/com/xu/tt/util/");
		String fileName = "student学员.xlsx";
		File file = new File(path + fileName);
		if (file.exists()) {
			List<Map<Integer, Object>> list = readData(file);
			System.out.println(list);
		}
	}

	public static List<Map<Integer, Object>> readData(File file) {
		XSSFWorkbook wb = null;
		FileInputStream fis = null;
		List<Map<Integer, Object>> list = new ArrayList<>();
		try {
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			int numberOfSheets = wb.getNumberOfSheets();
			for (int x = 0; x < numberOfSheets; x++) {
				XSSFSheet sheet = wb.getSheetAt(x);
				int columnNum = 0;
				if (sheet.getRow(0) != null) {
					columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
				}
				if (columnNum > 0) {
					for (Row row : sheet) {// 行循环
						String[] singleRow = new String[columnNum];
						int n = 0;
						Map<Integer, Object> map = new HashMap<>();
						for (int i = 0; i < columnNum; i++) {// 列循环
							Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							switch (cell.getCellType()) {
							case BLANK:
								singleRow[n] = "";
//                                if (cell == null || cell.equals("") || cell.getCellType() == HSSFCell.BLANK) {
//                                    System.out.print("<Null>|");
//                                } else {
//                                    System.out.print(singleRow[n] + "|");
//                                }
								System.out.print(singleRow[n] + "|");
								map.put(i, singleRow[n]);
								break;
							case BOOLEAN:
								singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
								System.out.print(singleRow[n] + "|");
								map.put(i, singleRow[n]);
								break;
							// 数值
							case NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									SimpleDateFormat sdf = null;
									if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
											.getBuiltinFormat("h:mm")) {
										sdf = new SimpleDateFormat("HH:mm");
									} else {// 日期
										sdf = new SimpleDateFormat("yyyy-MM-dd");
									}
									Date date = cell.getDateCellValue();
									System.out.print(sdf.format(date) + "|");
									map.put(i, sdf.format(date));
								} else {
									cell.setCellType(CellType.STRING);
									String temp = cell.getStringCellValue();
									// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
									if (temp.indexOf(".") > -1) {
										singleRow[n] = String.valueOf(new Double(temp)).trim();
										System.out.print(singleRow[n] + "|");
									} else {
										singleRow[n] = temp.trim();
										System.out.print(singleRow[n] + "|");
									}
									map.put(i, singleRow[n]);
								}
								break;
							case STRING:
								singleRow[n] = cell.getStringCellValue().trim();
								System.out.print(singleRow[n] + "|");
								map.put(i, singleRow[n]);
								break;
							case ERROR:
								singleRow[n] = "";
								System.out.print(singleRow[n] + "|");
								map.put(i, singleRow[n]);
								break;
							case FORMULA:
								cell.setCellType(CellType.STRING);
								String temp = cell.getStringCellValue();
								// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
								if (temp.indexOf(".") > -1) {
									temp = String.valueOf(new Double(temp)).trim();
									Double cny = Double.parseDouble(temp);// 6.2041
									DecimalFormat df = new DecimalFormat("0.00");
									String CNY = df.format(cny);
									System.out.print(CNY + "|");
									map.put(i, CNY);
								} else {
									singleRow[n] = temp.trim();
									System.out.print(singleRow[n] + "|");
									map.put(i, singleRow[n]);
								}
							default:
								singleRow[n] = "";
								map.put(i, singleRow[n]);
								break;
							}
							n++;
						}
						System.out.println();
						list.add(map);
					}
					System.out.println(
							"===========================================================Sheet分割线===========================================================");
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

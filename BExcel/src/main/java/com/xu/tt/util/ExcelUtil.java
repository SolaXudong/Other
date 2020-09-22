package com.xu.tt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-09-22 10:31:28
 * @tips 参考：https://www.cnblogs.com/linjiqin/p/10975761.html
 */
@Slf4j
public class ExcelUtil {

	public static void main(String[] args) throws Exception {
		String path = org.springframework.util.StringUtils
				.cleanPath(System.getProperty("user.dir") + "/src/main/java/com/xu/tt/util/");
		String fileName = "1.xlsx";
		readExcel(path, fileName);
	}

//	private final static String EXCEL2003 = "xls";
//	private final static String EXCEL2007 = "xlsx";

	public static <T> List<T> readExcel(String path, String fileName) throws Exception {
		FileInputStream is = new FileInputStream(new File(path + fileName));
//		String fileName = file.getOriginalFilename();
//		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//			log.error("上传文件格式不正确");
//		}
		List<T> dataList = new ArrayList<>();
		Workbook workbook = null;
		try {
//			InputStream is = file.getInputStream();
			if (fileName.endsWith("xlsx"))
				workbook = new XSSFWorkbook(is);
			if (fileName.endsWith("xls"))
				workbook = new HSSFWorkbook(is);
			if (workbook != null) {
				// 默认读取第一个sheet
				Sheet sheet = workbook.getSheetAt(0);
				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
					log.info("########## row: {}", i);
					Row row = sheet.getRow(i);
					// 忽略空白行
					if (row == null)
						continue;
					// 首行 提取注解
					for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);
						String cellValue = getCellValue(cell);
						if (StringUtils.isNotEmpty(cellValue))
							log.info("########## col: {}, {}", j, cellValue);
					}
				}
			}
		} catch (Exception e) {
			log.error(String.format("parse excel exception!"), e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					log.error(String.format("parse excel exception!"), e);
				}
			}
		}
		return dataList;
	}

	private static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			} else {
				return new BigDecimal(cell.getNumericCellValue()).toString();
			}
		} else if (cell.getCellType() == CellType.STRING) {
			return StringUtils.trimToEmpty(cell.getStringCellValue());
		} else if (cell.getCellType() == CellType.FORMULA) {
			return StringUtils.trimToEmpty(cell.getCellFormula());
		} else if (cell.getCellType() == CellType.BLANK) {
			return "";
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == CellType.ERROR) {
			return "ERROR";
		} else {
			return cell.toString().trim();
		}
	}

	public static <T> void writeExcel(HttpServletResponse response, List<T> dataList, Class<T> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<Field> fieldList = Arrays.stream(fields).sorted(Comparator.comparing(field -> {
			int col = 0;
			return col;
		})).collect(Collectors.toList());
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		AtomicInteger ai = new AtomicInteger();
		{
			Row row = sheet.createRow(ai.getAndIncrement());
			AtomicInteger aj = new AtomicInteger();
			// 写入头部
			fieldList.forEach(field -> {
				Cell cell = row.createCell(aj.getAndIncrement());
				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				Font font = wb.createFont();
				font.setBold(false);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("");
			});
		}
		if (CollectionUtils.isNotEmpty(dataList)) {
			dataList.forEach(t -> {
				Row row1 = sheet.createRow(ai.getAndIncrement());
				AtomicInteger aj = new AtomicInteger();
				fieldList.forEach(field -> {
					Class<?> type = field.getType();
					Object value = "";
					try {
						value = field.get(t);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Cell cell = row1.createCell(aj.getAndIncrement());
					if (value != null) {
						if (type == Date.class) {
							cell.setCellValue(value.toString());
						} else {
							cell.setCellValue(value.toString());
						}
						cell.setCellValue(value.toString());
					}
				});
			});
		}
		// 冻结窗格
		wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
		// 生成excel文件
		buildExcelFile(".\\default.xlsx", wb);
	}

	/**
	 * @tips LOOK 生成excel文件
	 */
	private static void buildExcelFile(String path, Workbook wb) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			wb.write(new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

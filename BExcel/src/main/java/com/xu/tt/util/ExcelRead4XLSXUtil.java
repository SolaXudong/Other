package com.xu.tt.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-01 18:47:25
 * @tips 读取XLSX工具类
 */
@Slf4j
@SuppressWarnings("deprecation")
public class ExcelRead4XLSXUtil implements SheetContentsHandler {

	List<String> title = Lists.newArrayList(); // 表头
	List<String> originTitle = Lists.newArrayList(); // 原始表头
	List<String> originData = Lists.newArrayList(); // 原始数据
	JSONObject rowData = new JSONObject(true); // 每行数据
	int rowNum, colNum, readLine; // 行号, 列号, 读取的行数
	int dataStartRowNum = 1; // 数据行开始行号

	private String fileName;

	public CustomCallback callback;

	public ExcelRead4XLSXUtil(String fileName, CustomCallback callback) {
		this.fileName = fileName;
		this.callback = callback;
	}

	public interface CustomCallback {
		void readRowsSuccess(int rowNum, int readLine, JSONObject rowData, List<String> originData);
	}

	@Override
	public void startRow(int rowNum) {
		this.rowNum = rowNum;
		colNum = 0;
		originData = Lists.newArrayList();
		rowData = new JSONObject(true);
		readLine++;
	}

	@Override
	public void endRow(int rowNum) {
		if (this.callback != null && rowNum >= dataStartRowNum && !rowData.isEmpty())
			callback.readRowsSuccess(rowNum, readLine, rowData, originData);
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (rowNum == 0) {
			originTitle.add(formattedValue);
			title.add(formattedValue);
		} else {
			int thisCol = (new CellReference(cellReference)).getCol(); // 当前列下标
			if (colNum < thisCol) {
				for (int i = colNum; i < thisCol; i++)
					originData.add("");
				colNum = thisCol;
			}
			colNum++;
			originData.add(formattedValue + "\t");
			rowData.put(title.get(thisCol), formattedValue);
		}
	}

	public void parse() {
		OPCPackage pkg = null;
		InputStream sheetInputStream = null;
		try {
			pkg = OPCPackage.open(fileName, PackageAccess.READ);
			XSSFReader xssfReader = new XSSFReader(pkg);
			StylesTable styles = xssfReader.getStylesTable();
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
			sheetInputStream = xssfReader.getSheetsData().next();
			processSheet(styles, strings, sheetInputStream);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (sheetInputStream != null) {
				try {
					sheetInputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			if (pkg != null) {
				try {
					pkg.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream)
			throws SAXException, ParserConfigurationException, IOException {
		XMLReader sheetParser = org.apache.poi.ooxml.util.SAXHelper.newXMLReader();
		sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, this, false));
		sheetParser.parse(new InputSource(sheetInputStream));
	}

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();

		String dir = org.springframework.util.StringUtils
				.cleanPath(System.getProperty("user.dir") + "/src/main/java/com/xu/tt/util/");
//		dir = "D:/tt/excel/案件导入/";
		String fileName = "student学员.xlsx";
		String path = dir + fileName;
		ArrayList<JSONObject> list = Lists.newArrayList();
		new ExcelRead4XLSXUtil(path, new CustomCallback() {
			@Override
			public void readRowsSuccess(int rowNum, int readLine, JSONObject rowData, List<String> originData) {
				list.add(rowData);
			}
		}).parse();
		log.info("##### parse-{}", list.size());
		System.out.println(list.get(0));

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

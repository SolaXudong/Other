package com.xu.tt.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.Styles;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-10 16:41:11
 * @tips POI官网：http://poi.apache.org/components/spreadsheet/index.html
 * @tips 代码参考：https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/examples/xssf/eventusermodel/XLSX2CSV.java
 */
@Slf4j
public class XLSX2CSV {

	List<String> tittle = Lists.newArrayList(); // 表头
	List<JSONObject> list = Lists.newArrayList(); // 数据
	JSONObject row = new JSONObject(true); // 每行数据

	private class SheetToCSV implements SheetContentsHandler {
		private boolean firstCellOfRow;
		private int currentRow = -1;
		private int currentCol = -1;

		@Override
		public void startRow(int rowNum) {
			// Prepare for this row
			firstCellOfRow = true;
			currentRow = rowNum;
			currentCol = -1;
		}

		@Override
		public void endRow(int rowNum) {
			if (currentRow > 0)
				list.add(row);
		}

		@Override
		public void cell(String cellReference, String formattedValue, XSSFComment comment) {
			if (firstCellOfRow)
				firstCellOfRow = false;
			// gracefully handle missing CellRef here in a similar way as XSSFCell does
			if (cellReference == null)
				cellReference = new CellAddress(currentRow, currentCol).formatAsString();
			// Did we miss any cells?
			int thisCol = (new CellReference(cellReference)).getCol();
			currentCol = thisCol;
			// Number or string?
			if (currentRow == 0)
				tittle.add(formattedValue);
			if (currentRow > 0) {
				row.put(tittle.get(currentCol), formattedValue);
			}
		}
	}

	private final OPCPackage xlsxPackage;

	public XLSX2CSV(OPCPackage pkg) {
		this.xlsxPackage = pkg;
	}

	public void processSheet(Styles styles, SharedStrings strings, SheetContentsHandler sheetHandler,
			InputStream sheetInputStream) throws IOException, SAXException {
		DataFormatter formatter = new DataFormatter();
		InputSource sheetSource = new InputSource(sheetInputStream);
		try {
			XMLReader sheetParser = XMLHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		}
	}

	public List<JSONObject> process() throws IOException, OpenXML4JException, SAXException {
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		int index = 0;
		while (iter.hasNext()) {
			try (InputStream stream = iter.next()) {
				String sheetName = iter.getSheetName();
				log.info("##### 工作簿-{}-{}", index, sheetName);
				processSheet(styles, strings, new SheetToCSV(), stream);
			}
			++index;
		}
		return list;
	}

	public List<String> getTittle() {
		return tittle;
	}

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();

		String fileName = "案件导入-十万 - 副本.xlsx";
//		fileName = "#案件导入-时光-10万条-97971.xlsx";
		String path = "D:/tt/excel/#测试专用/" + fileName;

		List<JSONObject> list = Lists.newArrayList();
		// The package open is instantaneous, as it should be.
		try (OPCPackage p = OPCPackage.open(new File(path).getPath(), PackageAccess.READ)) {
			list = new XLSX2CSV(p).process();
		}
		log.info("##### parse-{}", list.size());
		System.out.println(list.get(0));

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}
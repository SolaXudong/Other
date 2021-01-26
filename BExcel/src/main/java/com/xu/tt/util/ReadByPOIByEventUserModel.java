package com.xu.tt.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-01-27 05:20:03
 */
@Slf4j
@Data
public class ReadByPOIByEventUserModel {

	private static Pattern pattern1 = Pattern.compile("[^0-9]");;
	private static Pattern pattern2 = Pattern.compile("[0-9]");;

	public static ArrayList<String> tittleList = Lists.newArrayList();
	public static ArrayList<JSONObject> dataList = Lists.newArrayList();
	public static JSONObject obj = new JSONObject(true);

	public static String k = "";
	public static String v = "";
	public static int index = -1;

	public void processOneSheet(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		InputStream sheet2 = r.getSheet("rId1");
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if (name.equals("c")) {
				{
					String k = pattern1.matcher(attributes.getValue("r")).replaceAll("");
					String v = pattern2.matcher(attributes.getValue("r")).replaceAll("");
					if ("1".equals(v)) {
						index = 1;
					} else {
						index++;
					}
				}
//				System.out.print("##### " + attributes.getValue("r") + " - ");
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			lastContents = "";
		}

		public void endElement(String uri, String localName, String name) throws SAXException {
			if (nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			if (name.equals("v")) {
//				System.out.println("$$$$$ " + lastContents);
				if (1 == index) {
				}
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
		}
	}

	public static void main(String[] args) throws Exception {
		String path = "D:/tt/tt.xlsx";
		System.out.println("start read");
		int count = 20;
		ExecutorService es = Executors.newFixedThreadPool(count);
		CountDownLatch cl = new CountDownLatch(count);
		for (int i = 0; i < count / 2; i++) {
			es.submit(() -> {
				try {
					long cost = System.currentTimeMillis();
					ReadByPOIByEventUserModel example = new ReadByPOIByEventUserModel();
					example.processOneSheet(path);
					log.info("########## POI cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			es.submit(() -> {
				try {
					long cost = System.currentTimeMillis();
					ExcelReadByEasyExcel.parse(path);
					log.info("########## Easy cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		cl.await();
		es.shutdown();
	}

}

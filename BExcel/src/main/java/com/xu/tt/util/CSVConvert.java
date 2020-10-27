package com.xu.tt.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-10-27 22:28:00
 * @tips 使用CVS模式解决XLSX文件，可以有效解决用户模式内存溢出的问题
 * @tips 代码参考：https://www.jianshu.com/p/5a183a05e4a1?from=singlemessage
 */
@Slf4j
public class CSVConvert {

	private OPCPackage xlsxPackage;
	private int minColumns;
	private PrintStream output;
	private String sheetName;

	public CSVConvert(OPCPackage pkg, PrintStream output, String sheetName, int minColumns) {
		this.xlsxPackage = pkg;
		this.output = output;
		this.minColumns = minColumns;
		this.sheetName = sheetName;
	}

	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER;
	}

	class MyXSSFSheetHandler extends DefaultHandler {
		private int isNotNullIndex;
		private StylesTable stylesTable;
		private ReadOnlySharedStringsTable sharedStringsTable;
		private final PrintStream output;
		private final int minColumnCount;
		private boolean vIsOpen;
		private xssfDataType nextDataType;
		private short formatIndex;
		private String formatString;
		private final DataFormatter formatter;
		private int thisColumn = -1;
		private int lastColumnNumber = -1;
		private StringBuffer value;
		private String[] record;
		private List<String[]> rows = new ArrayList<String[]>();
		private boolean isCellNull = false;

		public MyXSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols,
				PrintStream target) {
			this.stylesTable = styles;
			this.sharedStringsTable = strings;
			this.minColumnCount = cols;
			this.output = target;
			this.value = new StringBuffer();
			this.nextDataType = xssfDataType.NUMBER;
			this.formatter = new DataFormatter();
			record = new String[this.minColumnCount];
			rows.clear(); // 每次读取都清空行集合
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if ("inlineStr".equals(name) || "v".equals(name)) {
				vIsOpen = true;
				// Clear contents cache
				value.setLength(0);
			}
			// c => cell
			else if ("c".equals(name)) {
				// Get the cell reference
				String r = attributes.getValue("r");
				int firstDigit = -1;
				for (int c = 0; c < r.length(); ++c) {
					if (Character.isDigit(r.charAt(c))) {
						firstDigit = c;
						break;
					}
				}
				thisColumn = nameToColumn(r.substring(0, firstDigit));
				// Set up defaults.
				this.nextDataType = xssfDataType.NUMBER;
				this.formatIndex = -1;
				this.formatString = null;
				String cellType = attributes.getValue("t");
				String cellStyleStr = attributes.getValue("s");
				if ("b".equals(cellType))
					nextDataType = xssfDataType.BOOL;
				else if ("e".equals(cellType))
					nextDataType = xssfDataType.ERROR;
				else if ("inlineStr".equals(cellType))
					nextDataType = xssfDataType.INLINESTR;
				else if ("s".equals(cellType))
					nextDataType = xssfDataType.SSTINDEX;
				else if ("str".equals(cellType))
					nextDataType = xssfDataType.FORMULA;
				else if (cellStyleStr != null) {
					// It's a number, but almost certainly one
					// with a special style or format
					int styleIndex = Integer.parseInt(cellStyleStr);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					this.formatString = style.getDataFormatString();
					if (this.formatString == null)
						this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
				}
			}
		}

		public void endElement(String uri, String localName, String name) throws SAXException {
			String thisStr = null;
			// v => contents of a cell
			if ("v".equals(name)) {
				// Process the value contents as required.
				// Do now, as characters() may be called more than once
				switch (nextDataType) {
				case BOOL:
					char first = value.charAt(0);
					thisStr = first == '0' ? "FALSE" : "TRUE";
					break;
				case ERROR:
					thisStr = "\"ERROR:" + value.toString() + '"';
					break;
				case FORMULA:
					// A formula could result in a string value,
					// so always add double-quote characters.
					thisStr = '"' + value.toString() + '"';
					break;
				case INLINESTR:
					XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
					thisStr = '"' + rtsi.toString() + '"';
					break;
				case SSTINDEX:
					String sstIndex = value.toString();
					try {
						int idx = Integer.parseInt(sstIndex);
						XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getItemAt(idx).getString());
						thisStr = rtss.toString();
					} catch (NumberFormatException ex) {
						output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
					}
					break;
				case NUMBER:
					String n = value.toString();
					// 判断是否是日期格式
					if (DateUtil.isADateFormat(this.formatIndex, n)) {
						Double d = Double.parseDouble(n);
						Date date = DateUtil.getJavaDate(d);
						thisStr = formateDateToString(date);
					} else if (this.formatString != null)
						thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
								this.formatString);
					else
						thisStr = n;
					break;
				default:
					thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
					break;
				}
				// Output after we've seen the string contents
				// Emit commas for any fields that were missing on this row
				if (lastColumnNumber == -1)
					lastColumnNumber = 0;
				// 判断单元格的值是否为空
				if (thisStr == null || "".equals(isCellNull))
					isCellNull = true; // 设置单元格是否为空值
				record[thisColumn] = thisStr; // 添加
				// Update column
				if (thisColumn > -1)
					lastColumnNumber = thisColumn;
			} else if ("row".equals(name)) {
				// Print out any missing commas if needed
				if (minColumns > 0) {
					// Columns are 0 based
					if (lastColumnNumber == -1)
						lastColumnNumber = 0;
					if (isCellNull == false && record[isNotNullIndex] != null) { // 判断是否空行
						rows.add(record.clone()); // 添加
						isCellNull = false;
						for (int i = 0; i < record.length; i++)
							record[i] = null;
					}
				}
				lastColumnNumber = -1;
			}

		}

		public List<String[]> getRows() {
			return rows;
		}

		public void setRows(List<String[]> rows) {
			this.rows = rows;
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (vIsOpen)
				value.append(ch, start, length);
		}

		private int nameToColumn(String name) {
			int column = -1;
			for (int i = 0; i < name.length(); ++i) {
				int c = name.charAt(i);
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		private String formateDateToString(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化日期
			return sdf.format(date);
		}

	}

	public List<String[]> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
			InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {
		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		MyXSSFSheetHandler handler = new MyXSSFSheetHandler(styles, strings, this.minColumns, this.output); // 内部类
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
		return handler.getRows();
	}

	/**
	 * @tips 初始化这个处理程序将
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<String[]> process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		List<String[]> list = null;
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			String sheetNameTemp = iter.getSheetName();
			if (this.sheetName.equals(sheetNameTemp)) {
				list = processSheet(styles, strings, stream);
				stream.close();
			}
		}
		return list;
	}

	/**
	 * @tips 读取Excel
	 * @tips 文件路径，读取excel的sheet名称，固定列总数，最后一列的index
	 * @param path           文件路径
	 * @param sheetName      sheet名称
	 * @param minColumns     列总数（最后一列的下标）
	 * @param isNotNullIndex 判断那一列数据不为空（此列不为空，则整行数据有效）
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws OpenXML4JException
	 * @throws IOException
	 */
	public static List<String[]> readerExcel(String path, String sheetName, int minColumns)
			throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
		OPCPackage pkg = OPCPackage.open(path, PackageAccess.READ);
		CSVConvert xlsx2csv = new CSVConvert(pkg, System.out, sheetName, minColumns);
		List<String[]> list = xlsx2csv.process();
		pkg.close();
		return list;
	}

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();

//		List<String[]> list = CSVConvert.readerExcel("D:/tt/batch/案件模板-测试导入Sep-27.xlsx", "案件导入模板", 45);
		List<String[]> list = CSVConvert.readerExcel("D:/tt/student学员.xlsx", "VIP学员信息表", 12);
//		for (String[] record : list) {
//			for (String cell : record)
//				System.out.print(cell + "\t");
//			System.out.println();
//		}
		System.out.println(list.size());

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

package com.xu.tt.util.old;

import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.xu.tt.util.dto.XSTU;

import lombok.extern.slf4j.Slf4j;

/**
 * @see exportExcelTemplate
 * @author XuDong
 * @date 2016-08-25 18:57:13
 */
@Slf4j
public class POI2Write4XLS {

	/**
	 * @param outputStream 输出流
	 * @param fileName     文件名称
	 * @param sheetName    工作簿名称
	 * @param tips         首行标题-从左到右
	 * @param args         传入的对象集合-从左到右的属性
	 * @param list         传入的对象集合-支持不同对象 ^_^
	 * @see 注意：对象属性String,int,Integer,Date之外的属性需单独处理
	 */
	public static void apachePOI(ServletOutputStream outputStream, String fileName, String sheetName, String[] tips,
			String[] args, List<?> list) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet(sheetName);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
			// 创建一个颜色格式
			HSSFPalette customPalette = wb.getCustomPalette();
			customPalette.setColorAtIndex(IndexedColors.BLUE.getIndex(), (byte) 141, (byte) 180, (byte) 226); // 自定义颜色
			HSSFCellStyle style_header = wb.createCellStyle();
			style_header.setAlignment(HorizontalAlignment.CENTER); // 左右居中
			style_header.setFillForegroundColor(IndexedColors.BLUE.getIndex()); // 设置颜色
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 不知道是什么，不加颜色不出来
			// 字体加粗
			HSSFFont font = wb.createFont();
			font.setBold(true);
			style_header.setFont(font);
			// 绘制第一行
			for (int i = 0; i < tips.length; i++) {
				HSSFCell cell = row.createCell((int) i);
				cell.setCellValue(tips[i]);
				cell.setCellStyle(style_header);
			}
			// 利用内省拿到对象的get()方法，注入值
			for (int i = 0; i < list.size(); i++) {
				// 绘制行，每个对象占一行，循环注入属性
				HSSFRow trow = sheet.createRow((int) (i + 1));
				PropertyDescriptor pd = null;
				for (int j = 0; j < args.length; j++) {
					if (args[j] == null)
						continue;
					pd = new PropertyDescriptor(args[j], list.get(i).getClass());
					// 所有属性都转为String存入单元格，需加判断
					String name = null;
					if (pd.getPropertyType() != String.class) {
						if (pd.getPropertyType() == int.class || pd.getPropertyType() == Integer.class) {
							name = Integer.parseInt(pd.getReadMethod().invoke(list.get(i), null).toString()) + "";
						} else if (pd.getPropertyType() == Date.class) {
							Date time_pro = (Date) pd.getReadMethod().invoke(list.get(i), null);
							name = time_pro == null ? "" : getTime(time_pro);
						} else {
						}
					} else {
						name = (String) pd.getReadMethod().invoke(list.get(i), null);
					}
					// System.out.println(args[j]+"|"+name);
					HSSFCell cell = trow.createCell((int) j);
					cell.setCellValue(name);
					cell.setCellStyle(style);
					// 自适应宽度
					if (i == 0 && name != null) {
						int width = name.getBytes().length < 10 ? 10 : name.getBytes().length;
						// System.out.println(width+"|"+width*2*150);
						sheet.setColumnWidth(j, width * 2 * 150);
					}
				}
			}
			// 传空的流是我测试用的，^_^
			if (outputStream == null) {
				FileOutputStream fout = new FileOutputStream("D:/tt/" + fileName + ".xls");
				wb.write(fout);
				fout.close();
			} else {
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			// 错误处理
			e.printStackTrace();
			log.error("报表导出失败！");
		}
	}

	public static String getTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();

		// 准备数据源
		List<XSTU> list = new ArrayList<XSTU>();
		for (int i = 1; i <= 10; i++)
			list.add(XSTU.builder().centerName("中心" + i).trueName("徐东" + i).loginName("sola" + i + "@tedu.cn")
					.openTime(new Date()).build());
		String[] tips = { "学员姓名", "申请中心", "视频学习权限", "帐号", "版本", "开通阶段", "开通人员", "是否原脱产班学员", "是否原其他方向VIP学员",
				"投诉(时间/投诉内容)", "备注", "开通日期" };
		String[] params = { "trueName", "centerName", "isHomeCenter", "loginName", "version", "parts", "openStaff",
				"proper", "proper", "proper", "proper", "openTime" };
		// 测试
		apachePOI(null, "student学员", "VIP学员信息表", tips, params, list);

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}

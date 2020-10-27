package com.xu.tt.util;
//package com.xu.tt.util;
//
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import jxl.Workbook;
//import jxl.format.Alignment;
//import jxl.format.VerticalAlignment;
//import jxl.write.Label;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//public class JXL2Write {
//
//	public static int totalClassNum;// 全国班级数量
//	public static String[] Title = { "序号", "考试名称", "片区", "中心", "班级", "姓名", "分数", "班级排名", "全国排名" };
//	public static String[] ExamMonthTitle = { "序号", "场次", "片区", "中心", "班级", "项目经理", "班级人数", "有效参考人数", "平均分", "全国排名" };
//	public static String[] EvaluationDetailTittle = { "序号", "班级", "学员姓名", "第一题", "第二题", "第三题", "第四题", "第五题", "第六题",
//			"第七题", "第八题", "第九题", "第十题", "第十一题", "第十二题" };
//	public static String[] examFeedBackTitle = { "题号", "理解个数", "不理解个数" };
//
//	/**
//	 * @tips LOOK downWorkbook
//	 */
//	public static void downWorkbook(OutputStream os, List<JSONObject> list) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet = wbook.createSheet("班级成绩统计表", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			wsheet.mergeCells(0, 0, Title.length - 1, 0); // 作用是从(1,2)到(3,4)的单元格全部合并
//			Label label = null;// 用于写入文本内容到工作表中去..java
//			label = new Label(0, 0, "班级分数统计表", headerFormat);
//			wsheet.addCell(label);
//			for (int i = 0; i < JXL2Write.Title.length; i++) {
//				label = new Label(i, 1, JXL2Write.Title[i]); // 参数依次代表列数、行数、内容
//				wsheet.addCell(label);// 写入单元格
//			}
//			Exam sr = null;
//			Label label1 = null, label2 = null, label3 = null, label4 = null, label5 = null, label6 = null,
//					label7 = null, label8 = null, label9 = null;
//			if (list.size() > 0) {
//				for (int i = 0; i < list.size(); i++) {
//					sr = list.get(i);
//					label1 = new Label(0, i + 2, String.valueOf(i + 1));
//					label2 = new Label(1, i + 2, sr.getExamName());
//
//					label3 = new Label(2, i + 2, sr.getRegionName());// 大区的名字
//
//					label4 = new Label(3, i + 2, sr.getCenterName());
//					label5 = new Label(4, i + 2, sr.getClassName());
//					label6 = new Label(5, i + 2, sr.getStudentName());
//					label7 = new Label(6, i + 2, String.valueOf(sr.getStudentScore()));
//					label8 = new Label(7, i + 2, String.valueOf(sr.getClassOrder() + "/" + sr.getClassOrderNum()));
//					label9 = new Label(8, i + 2, String.valueOf(sr.getCenterOrder() + "/" + sr.getCenterOrderNum()));
//					// label9 = new Label(8, i + 2, sr.getReportRate());
//					wsheet.addCell(label1);
//					wsheet.addCell(label2);
//					wsheet.addCell(label3);
//					wsheet.addCell(label4);
//					wsheet.addCell(label5);
//					wsheet.addCell(label6);
//					wsheet.addCell(label7);
//					wsheet.addCell(label8);
//					wsheet.addCell(label9);
//					// wsheet.addCell(label9);
//				}
//			}
//			wbook.write();
//			wbook.close();
//			// os.close();
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	/**
//	 * @tips LOOK downExcelScore
//	 * @tips {"序号","场次","大区","中心","班级","项目经理","班级人数","有效参考人数","平均分","全国排名"}
//	 */
//	public static void downExcelScore(OutputStream os, List<JSONObject> list) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet = wbook.createSheet("班级排名报表", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			wsheet.mergeCells(0, 0, ExamMonthTitle.length - 1, 0); // 作用是从(1,2)到(3,4)的单元格全部合并
//			Label label = null;// 用于写入文本内容到工作表中去..java
//			label = new Label(0, 0, "班级排名报表", headerFormat);
//			wsheet.addCell(label);
//			for (int i = 0; i < JXL2Write.ExamMonthTitle.length; i++) {
//				label = new Label(i, 1, JXL2Write.ExamMonthTitle[i]); // 参数依次代表列数、行数、内容
//				wsheet.addCell(label);// 写入单元格
//			}
//			Exam sr = null;
//			Label label1 = null, label2 = null, label3 = null, label4 = null, label5 = null, label6 = null,
//					label7 = null, label8 = null, label9 = null, label10 = null;
//			for (int i = 0; i < list.size(); i++) {
//				sr = list.get(i);
//				label1 = new Label(0, i + 2, String.valueOf(i + 1));
//				label2 = new Label(1, i + 2, sr.getExamName());
//				label3 = new Label(2, i + 2, sr.getRegionName());// 大区
//				label4 = new Label(3, i + 2, sr.getCenterName());
//				label5 = new Label(4, i + 2, sr.getClassName());
//				label6 = new Label(5, i + 2, sr.getEmployeeName());
//				label7 = new Label(6, i + 2, String.valueOf(sr.getClassOrderNum()));
//				label8 = new Label(7, i + 2, String.valueOf(sr.getStudentNum()));
//				label9 = new Label(8, i + 2, String.valueOf(sr.getAverageScore()));
//				label10 = new Label(9, i + 2, String.valueOf(sr.getCenterOrder() + "/" + totalClassNum));
////				label9 = new Label(8, i + 2, sr.getReportRate());
//				wsheet.addCell(label1);
//				wsheet.addCell(label2);
//				wsheet.addCell(label3);
//				wsheet.addCell(label4);
//				wsheet.addCell(label5);
//				wsheet.addCell(label6);
//				wsheet.addCell(label7);
//				wsheet.addCell(label8);
//				wsheet.addCell(label9);
//				wsheet.addCell(label10);
//			}
//			wbook.write();
//			wbook.close();
//			// os.close();
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	/**
//	 * @tips LOOK downEvaluationDetail
//	 * @tips {"序号","班级","学员姓名","第一题","第二题","第三题","第四题","第五题","第六题","第七题","第八题","第九题","第十题","第十一题","第十二题"}
//	 */
//	public static void downEvaluationDetail(OutputStream os, List<JSONObject> list) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet = wbook.createSheet("班级测评详情报表", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			wsheet.mergeCells(0, 0, EvaluationDetailTittle.length - 1, 0); // 作用是从(1,2)到(3,4)的单元格全部合并
//			Label label = null;// 用于写入文本内容到工作表中去..java
//			label = new Label(0, 0, "班级测评详情", headerFormat);
//			wsheet.addCell(label);
//			for (int i = 0; i < JXL2Write.EvaluationDetailTittle.length; i++) {
//				label = new Label(i, 1, JXL2Write.EvaluationDetailTittle[i]); // 参数依次代表列数、行数、内容
//				wsheet.addCell(label);// 写入单元格
//			}
//			PMEvaluationDetail sr = null;
//			Label label1 = null, label2 = null, label3 = null, label4 = null, label5 = null, label6 = null,
//					label7 = null, label8 = null, label9 = null, label10 = null, label11 = null, label12 = null,
//					label13 = null, label14 = null, label15 = null;
//			for (int i = 0; i < list.size(); i++) {
//				sr = list.get(i);
//				label1 = new Label(0, i + 2, String.valueOf(i + 1));
//				label2 = new Label(1, i + 2, sr.getClassName());
//				label3 = new Label(2, i + 2, sr.getStudentName());
//				label4 = new Label(3, i + 2, sr.getStatisQuestion1Description());
//				label5 = new Label(4, i + 2, sr.getStatisQuestion2Description());
//				label6 = new Label(5, i + 2, sr.getStatisQuestion3Description());
//				label7 = new Label(6, i + 2, sr.getStatisQuestion4Description());
//				label8 = new Label(7, i + 2, sr.getStatisQuestion5Description());
//				label9 = new Label(8, i + 2, sr.getStatisQuestion6Description());
//				label10 = new Label(9, i + 2, sr.getStatisQuestion7Description());
//				label11 = new Label(10, i + 2, sr.getStatisQuestion8Description());
//				label12 = new Label(11, i + 2, sr.getStatisQuestion9Description());
//				label13 = new Label(12, i + 2, sr.getEvaluationText1());
//				label14 = new Label(13, i + 2, sr.getStatisQuestion10Description());
//				label15 = new Label(14, i + 2, sr.getEvaluationText2());
//				wsheet.addCell(label1);
//				wsheet.addCell(label2);
//				wsheet.addCell(label3);
//				wsheet.addCell(label4);
//				wsheet.addCell(label5);
//				wsheet.addCell(label6);
//				wsheet.addCell(label7);
//				wsheet.addCell(label8);
//				wsheet.addCell(label9);
//				wsheet.addCell(label10);
//				wsheet.addCell(label11);
//				wsheet.addCell(label12);
//				wsheet.addCell(label13);
//				wsheet.addCell(label14);
//				wsheet.addCell(label15);
//			}
//			wbook.write();
//			wbook.close();
////			os.close();
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	/**
//	 * @tips LOOK downStudentCount
//	 * @tips 统计VIP学员信息
//	 */
//	public static void downStudentCount(OutputStream os, Map<String, JSONObject> m) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet6 = wbook.createSheet("表5-完成总课时数", 0); // 工作表名称
//			WritableSheet wsheet5 = wbook.createSheet("表4-分日期登录次数", 0); // 工作表名称
//			WritableSheet wsheet4 = wbook.createSheet("表3-每月流失学员数", 0); // 工作表名称
//			WritableSheet wsheet3 = wbook.createSheet("表2-每月完成课时数", 0); // 工作表名称
//			WritableSheet wsheet2 = wbook.createSheet("表1-分方向登录次数", 0); // 工作表名称
//			WritableSheet wsheet1 = wbook.createSheet("学员统计", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			// 作用是从(1,2)到(3,4)的单元格全部合并
//			for (int i = 0; i < 11; i++) {
//				wsheet1.mergeCells(1, i, 4, i);
//			}
//			wsheet2.mergeCells(0, 0, 3, 0);
//			wsheet3.mergeCells(0, 0, 3, 0);
//			wsheet4.mergeCells(0, 0, 3, 0);
//			wsheet5.mergeCells(0, 0, 3, 0);
//			wsheet6.mergeCells(0, 0, 3, 0);
//			Label lab1 = null;
//			Label lab2 = null;
//			Label lab3 = null;
//			Label lab4 = null;
//			Label lab5 = null;
//			Label lab6 = null;
//			Label lab7 = null;
//			Label lab8 = null;
//			Label lab9 = null;
//			Label lab10 = null;
//			Label lab11 = null;
//			Label lab12 = null;
//			int count = 0;
//			Node node = null;
//			// 第一张表
//			Label label1 = new Label(0, 0, "1", headerFormat);
//			Label label2 = new Label(1, 0, "在线答疑数（已审核）");
//			Label label3 = new Label(5, 0, m.get("1").getCol1() == null ? "0" : m.get("1").getCol1(), headerFormat);
//			Label label4 = new Label(1, 1, "全部VIP数（去掉测试中心）");
//			Label label5 = new Label(5, 1, m.get("2").getCol1() == null ? "0" : m.get("2").getCol1(), headerFormat);
//			Label label6 = new Label(0, 3, "2", headerFormat);
//			Label label7 = new Label(1, 3, "分方向的用户每月登录次数");
//			Label label8 = new Label(5, 3, "详见表1", headerFormat);
//			Label label9 = new Label(1, 4, "每月完成的课时数");
//			Label label10 = new Label(5, 4, "详见表2", headerFormat);
//			Label label11 = new Label(0, 6, "3", headerFormat);
//			Label label12 = new Label(1, 6, "每月的毕业学员数（或减少的学员数）");
//			Label label13 = new Label(5, 6, "详见表3", headerFormat);
//			Label label14 = new Label(0, 8, "4", headerFormat);
//			Label label15 = new Label(1, 8, "每日分时段的在线用户数统计(百度统计)");
//			Label label16 = new Label(5, 8, "详见截图", headerFormat);
//			Label label17 = new Label(0, 10, "5", headerFormat);
//			Label label18 = new Label(1, 10, "每月分日期的用户登录次数的统计");
//			Label label19 = new Label(5, 10, "详见表4", headerFormat);
//			wsheet1.addCell(label1);
//			wsheet1.addCell(label2);
//			wsheet1.addCell(label3);
//			wsheet1.addCell(label4);
//			wsheet1.addCell(label5);
//			wsheet1.addCell(label6);
//			wsheet1.addCell(label7);
//			wsheet1.addCell(label8);
//			wsheet1.addCell(label9);
//			wsheet1.addCell(label10);
//			wsheet1.addCell(label11);
//			wsheet1.addCell(label12);
//			wsheet1.addCell(label13);
//			wsheet1.addCell(label14);
//			wsheet1.addCell(label15);
//			wsheet1.addCell(label16);
//			wsheet1.addCell(label17);
//			wsheet1.addCell(label18);
//			wsheet1.addCell(label19);
//			// 第二张表
//			Label label20 = new Label(0, 0, "分方向的用户每月登录次数", headerFormat);
//			Label label21 = new Label(0, 1, "方向", headerFormat);
//			Label label22 = new Label(1, 1, "年月", headerFormat);
//			Label label23 = new Label(2, 1, "登录次数", headerFormat);
//			Label labelCount2_1 = new Label(4, 1, "总计：", headerFormat);
//			wsheet2.addCell(label20);
//			wsheet2.addCell(label21);
//			wsheet2.addCell(label22);
//			wsheet2.addCell(label23);
//			wsheet2.addCell(labelCount2_1);
//			count = 0;
//			Map<String, Integer> lmap = new HashMap<String, Integer>();
//			for (int i = 0; i < m.get("3").getNodes().size(); i++) {
//				node = m.get("3").getNodes().get(i);
//				lab1 = new Label(0, i + 2, node.getCol1(), headerFormat);
//				lab2 = new Label(1, i + 2, node.getCol2(), headerFormat);
//				lab3 = new Label(2, i + 2, node.getCol3(), headerFormat);
//				count += Integer.parseInt(node.getCol3());
//				wsheet2.addCell(lab1);
//				wsheet2.addCell(lab2);
//				wsheet2.addCell(lab3);
//				if (lmap.containsKey(node.getCol1())) {
//					lmap.put(node.getCol1(), lmap.get(node.getCol1()) + Integer.parseInt(node.getCol3()));
//				} else {
//					lmap.put(node.getCol1(), Integer.parseInt(node.getCol3()));
//				}
//			}
//			Label labelCount2_2 = new Label(5, 1, count + "", headerFormat);
//			wsheet2.addCell(labelCount2_2);
//			int map_i = 2;
//			for (Map.Entry<String, Integer> e : lmap.entrySet()) {
//				Label labelCount2_3 = new Label(4, map_i, e.getKey(), headerFormat);
//				wsheet2.addCell(labelCount2_3);
//				Label labelCount2_4 = new Label(5, map_i, e.getValue() + "", headerFormat);
//				wsheet2.addCell(labelCount2_4);
//				map_i++;
//			}
//			// 第三张表
//			Label label24 = new Label(0, 0, "每月完成课时数", headerFormat);
//			Label label25_1 = new Label(0, 1, "方向", headerFormat);
//			Label label25_01 = new Label(1, 1, "中心", headerFormat);
//			Label label25_02 = new Label(2, 1, "班级", headerFormat);
//			Label label25 = new Label(3, 1, "姓名", headerFormat);
//			Label label25_03 = new Label(4, 1, "手机号", headerFormat);
//			Label label25_04 = new Label(5, 1, "QQ", headerFormat);
//			Label label26 = new Label(6, 1, "入学日期", headerFormat);
//			Label label26_1 = new Label(7, 1, "过期日期", headerFormat);
//			Label label27 = new Label(8, 1, "学习时间", headerFormat);
//			Label label28 = new Label(9, 1, "每月完成总课时数");
//			Label label28_1 = new Label(10, 1, "总课时数");
//			Label label28_2 = new Label(11, 1, "最后登录时间");
//			Label labelCount3_1 = new Label(13, 1, "行数总计：", headerFormat);
//			wsheet3.addCell(label24);
//			wsheet3.addCell(label25);
//			wsheet3.addCell(label25_1);
//			wsheet3.addCell(label26);
//			wsheet3.addCell(label27);
//			wsheet3.addCell(label28);
//			wsheet3.addCell(label25_01);
//			wsheet3.addCell(label25_02);
//			wsheet3.addCell(label25_03);
//			wsheet3.addCell(label25_04);
//			wsheet3.addCell(labelCount3_1);
//			wsheet3.addCell(label28_1);
//			wsheet3.addCell(label26_1);
//			wsheet3.addCell(label28_2);
//			count = 0;
//			for (int i = 0; i < m.get("4").getNodes().size(); i++) {
//				node = m.get("4").getNodes().get(i);
//				lab5 = new Label(0, i + 2, node.getCol5(), headerFormat);
//				lab6 = new Label(1, i + 2, node.getCol6(), headerFormat);
//				lab7 = new Label(2, i + 2, node.getCol7(), headerFormat);
//				lab1 = new Label(3, i + 2, node.getCol1(), headerFormat);
//				lab8 = new Label(4, i + 2, node.getCol8(), headerFormat);
//				lab9 = new Label(5, i + 2, node.getCol9(), headerFormat);
//				lab2 = new Label(6, i + 2, node.getCol2(), headerFormat);
//				lab11 = new Label(7, i + 2, node.getCol11(), headerFormat);
//				lab3 = new Label(8, i + 2, node.getCol3(), headerFormat);
//				lab4 = new Label(9, i + 2, node.getCol4());
//				lab12 = new Label(10, i + 2, node.getCol12());
//				lab10 = new Label(11, i + 2, node.getCol10());
//				count = i + 1;
//				wsheet3.addCell(lab1);
//				wsheet3.addCell(lab2);
//				wsheet3.addCell(lab3);
//				wsheet3.addCell(lab4);
//				wsheet3.addCell(lab5);
//				wsheet3.addCell(lab6);
//				wsheet3.addCell(lab7);
//				wsheet3.addCell(lab8);
//				wsheet3.addCell(lab9);
//				wsheet3.addCell(lab10);
//				wsheet3.addCell(lab11);
//				wsheet3.addCell(lab12);
//			}
//			Label labelCount3_2 = new Label(14, 1, count + "", headerFormat);
//			wsheet3.addCell(labelCount3_2);
//			// 第六张表
//			Label label5_1 = new Label(0, 0, "完成总课时数", headerFormat);
//			Label label5_2 = new Label(0, 1, "方向", headerFormat);
//			Label label5_3 = new Label(1, 1, "中心", headerFormat);
//			Label label5_4 = new Label(2, 1, "班级", headerFormat);
//			Label label5_5 = new Label(3, 1, "姓名", headerFormat);
//			Label label5_6 = new Label(4, 1, "手机号", headerFormat);
//			Label label5_7 = new Label(5, 1, "QQ", headerFormat);
//			Label label5_8 = new Label(6, 1, "入学日期", headerFormat);
//			Label label5_9 = new Label(7, 1, "过期日期", headerFormat);
//			Label label5_10 = new Label(8, 1, "学习时间", headerFormat);
//			Label label5_11 = new Label(9, 1, "完成总课时数");
//			Label label5_12 = new Label(10, 1, "总课时数");
//			Label label5_13 = new Label(11, 1, "最后登录时间");
//			Label label5_14 = new Label(13, 1, "行数总计：", headerFormat);
//			wsheet6.addCell(label5_1);
//			wsheet6.addCell(label5_2);
//			wsheet6.addCell(label5_3);
//			wsheet6.addCell(label5_4);
//			wsheet6.addCell(label5_5);
//			wsheet6.addCell(label5_6);
//			wsheet6.addCell(label5_7);
//			wsheet6.addCell(label5_8);
//			wsheet6.addCell(label5_9);
//			wsheet6.addCell(label5_10);
//			wsheet6.addCell(label5_11);
//			wsheet6.addCell(label5_12);
//			wsheet6.addCell(label5_13);
//			wsheet6.addCell(label5_14);
//			count = 0;
//			for (int i = 0; i < m.get("7").getNodes().size(); i++) {
//				node = m.get("7").getNodes().get(i);
//				lab5 = new Label(0, i + 2, node.getCol5(), headerFormat);
//				lab6 = new Label(1, i + 2, node.getCol6(), headerFormat);
//				lab7 = new Label(2, i + 2, node.getCol7(), headerFormat);
//				lab1 = new Label(3, i + 2, node.getCol1(), headerFormat);
//				lab8 = new Label(4, i + 2, node.getCol8(), headerFormat);
//				lab9 = new Label(5, i + 2, node.getCol9(), headerFormat);
//				lab2 = new Label(6, i + 2, node.getCol2(), headerFormat);
//				lab11 = new Label(7, i + 2, node.getCol11(), headerFormat);
//				lab3 = new Label(8, i + 2, node.getCol3(), headerFormat);
//				lab4 = new Label(9, i + 2, node.getCol4());
//				lab12 = new Label(10, i + 2, node.getCol12());
//				lab10 = new Label(11, i + 2, node.getCol10());
//				count = i + 1;
//				wsheet6.addCell(lab1);
//				wsheet6.addCell(lab2);
//				wsheet6.addCell(lab3);
//				wsheet6.addCell(lab4);
//				wsheet6.addCell(lab5);
//				wsheet6.addCell(lab6);
//				wsheet6.addCell(lab7);
//				wsheet6.addCell(lab8);
//				wsheet6.addCell(lab9);
//				wsheet6.addCell(lab10);
//				wsheet6.addCell(lab11);
//				wsheet6.addCell(lab12);
//			}
//			Label label5_15 = new Label(14, 1, count + "", headerFormat);
//			wsheet6.addCell(label5_15);
//			// 第四张表
//			Label label32 = new Label(0, 0, "每月的毕业学员数（或减少的学员数）", headerFormat);
//			Label label33 = new Label(0, 1, "年月", headerFormat);
//			Label label34 = new Label(1, 1, "方向", headerFormat);
//			Label label35 = new Label(2, 1, "总数", headerFormat);
//			Label label35_1 = new Label(3, 1, "转脱产", headerFormat);
//			Label label35_2 = new Label(4, 1, "跨方向转VIP", headerFormat);
//			Label label35_3 = new Label(5, 1, "册除", headerFormat);
//			Label label35_4 = new Label(6, 1, "休学退学", headerFormat);
//			Label labelCount4_1 = new Label(8, 1, "总计：", headerFormat);
//			Label label_explain = new Label(8, 0, "说明：本记录不包括脱产，所有VIP的 删除、转班、休学、退学", headerFormat);
//			wsheet4.addCell(label32);
//			wsheet4.addCell(label33);
//			wsheet4.addCell(label34);
//			wsheet4.addCell(label35);
//			wsheet4.addCell(label35_1);
//			wsheet4.addCell(label35_2);
//			wsheet4.addCell(label35_3);
//			wsheet4.addCell(label35_4);
//			wsheet4.addCell(labelCount4_1);
//			wsheet4.addCell(label_explain);
//			count = 0;
//			for (int i = 0; i < m.get("5").getNodes().size(); i++) {
//				node = m.get("5").getNodes().get(i);
//				lab1 = new Label(0, i + 2, node.getCol1(), headerFormat);
//				lab2 = new Label(1, i + 2, node.getCol3(), headerFormat);
//				lab3 = new Label(2, i + 2, node.getCol2(), headerFormat);
//				lab4 = new Label(3, i + 2, node.getCol5(), headerFormat);
//				lab5 = new Label(4, i + 2, node.getCol6(), headerFormat);
//				lab6 = new Label(5, i + 2, node.getCol7(), headerFormat);
//				lab7 = new Label(6, i + 2, node.getCol8(), headerFormat);
//				count += Integer.parseInt(node.getCol2());
//				wsheet4.addCell(lab1);
//				wsheet4.addCell(lab2);
//				wsheet4.addCell(lab3);
//				wsheet4.addCell(lab4);
//				wsheet4.addCell(lab5);
//				wsheet4.addCell(lab6);
//				wsheet4.addCell(lab7);
//			}
//			Label labelCount4_2 = new Label(9, 1, count + "", headerFormat);
//			wsheet4.addCell(labelCount4_2);
//			// 第五张表
//			Label label29 = new Label(0, 0, "每月分日期的用户登录次数的统计", headerFormat);
//			Label label30 = new Label(0, 1, "年月日", headerFormat);
//			Label label31 = new Label(1, 1, "登录次数", headerFormat);
//			Label labelCount5_1 = new Label(4, 1, "总计：", headerFormat);
//			wsheet5.addCell(label29);
//			wsheet5.addCell(label30);
//			wsheet5.addCell(label31);
//			wsheet5.addCell(labelCount5_1);
//			count = 0;
//			for (int i = 0; i < m.get("6").getNodes().size(); i++) {
//				node = m.get("6").getNodes().get(i);
//				lab1 = new Label(0, i + 2, node.getCol1(), headerFormat);
//				lab2 = new Label(1, i + 2, node.getCol2(), headerFormat);
//				count += Integer.parseInt(node.getCol2());
//				wsheet5.addCell(lab1);
//				wsheet5.addCell(lab2);
//			}
//			Label labelCount5_2 = new Label(5, 1, count + "", headerFormat);
//			wsheet5.addCell(labelCount5_2);
//
//			wbook.write();
//			wbook.close();
////			os.close();
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	/**
//	 * @tips LOOK downStudentLearningCount
//	 * @tips 统计VIP学员信息-个人学习历史
//	 */
//	public static void downStudentLearningCount(OutputStream os, Map<String, JSONObject> m) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet4 = wbook.createSheet("个人视频观看记录", 0); // 工作表名称
//			WritableSheet wsheet3 = wbook.createSheet("个人登录日志", 0); // 工作表名称
//			WritableSheet wsheet2 = wbook.createSheet("个人考试成绩", 0); // 工作表名称
//			WritableSheet wsheet1 = wbook.createSheet("个人测评", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			wsheet1.mergeCells(0, 0, 1, 0);
//			wsheet1.mergeCells(0, 1, 1, 1);
//			wsheet1.mergeCells(0, 2, 1, 2);
//			wsheet1.mergeCells(2, 0, 3, 0);
//			wsheet1.mergeCells(2, 1, 3, 1);
//			wsheet1.mergeCells(2, 2, 3, 2);
//			wsheet2.mergeCells(0, 0, 3, 0);
//			wsheet3.mergeCells(0, 0, 2, 0);
//			wsheet4.mergeCells(0, 0, 5, 0);
//			Label lab1 = null;
//			Label lab2 = null;
//			Label lab3 = null;
//			Label lab4 = null;
//			Label lab5 = null;
//			Label lab6 = null;
//			// 第一张表
//			Label label1 = new Label(0, 0, "学员姓名：", headerFormat);
//			Label label2 = new Label(0, 1, "登录帐号：", headerFormat);
//			Label label3 = new Label(0, 2, "入学时间：", headerFormat);
//			Label label4 = new Label(2, 0,
//					m.get("3").getNodes().size() == 0 ? "空" : m.get("3").getNodes().get(0).getCol3(), headerFormat);
//			Label label5 = new Label(2, 1,
//					m.get("3").getNodes().size() == 0 ? "空" : m.get("3").getNodes().get(0).getCol4(), headerFormat);
//			Label label6 = new Label(2, 2,
//					m.get("3").getNodes().size() == 0 ? "空" : m.get("3").getNodes().get(0).getCol2(), headerFormat);
//			Label label7 = new Label(0, 4, "测评时间：", headerFormat);
//			wsheet1.mergeCells(0, 4, 1, 4);
//			wsheet1.addCell(label1);
//			wsheet1.addCell(label2);
//			wsheet1.addCell(label3);
//			wsheet1.addCell(label4);
//			wsheet1.addCell(label5);
//			wsheet1.addCell(label6);
//			wsheet1.addCell(label7);
//			for (int i = 0; i < m.get("2").getNodes().size(); i++) {
//				lab1 = new Label(0, (5 + i), m.get("2").getNodes().get(i).getCol1(), headerFormat);
//				wsheet1.mergeCells(0, (5 + i), 1, (5 + i));
//				wsheet1.addCell(lab1);
//			}
//			// 第二张表
//			Label label8 = new Label(0, 0, "个人考试成绩", headerFormat);
//			Label label9 = new Label(0, 1, "考试时间", headerFormat);
//			Label label10 = new Label(2, 1, "考试名称", headerFormat);
//			Label label11 = new Label(3, 1, "分数", headerFormat);
//			wsheet2.mergeCells(0, 1, 1, 1);
//			wsheet2.addCell(label8);
//			wsheet2.addCell(label9);
//			wsheet2.addCell(label10);
//			wsheet2.addCell(label11);
//			for (int i = 0; i < m.get("1").getNodes().size(); i++) {
//				lab1 = new Label(0, i + 2, m.get("1").getNodes().get(i).getCol1(), headerFormat);
//				lab2 = new Label(2, i + 2, m.get("1").getNodes().get(i).getCol2(), headerFormat);
//				lab3 = new Label(3, i + 2, m.get("1").getNodes().get(i).getCol3(), headerFormat);
//				wsheet2.mergeCells(0, i + 2, 1, i + 2);
//				wsheet2.addCell(lab1);
//				wsheet2.addCell(lab2);
//				wsheet2.addCell(lab3);
//			}
//			// 第三张表
//			Label label12 = new Label(0, 0, "个人登录日志", headerFormat);
//			Label label13 = new Label(0, 1, "登录时间", headerFormat);
//			Label label_3 = new Label(4, 1, "注意：记录时间从2016-02-23开始，之前没有此功能", headerFormat);
//			wsheet3.mergeCells(4, 1, 8, 1);
//			wsheet3.mergeCells(0, 1, 1, 1);
//			wsheet3.addCell(label12);
//			wsheet3.addCell(label13);
//			wsheet3.addCell(label_3);
//			for (int i = 0; i < m.get("3").getNodes().size(); i++) {
//				wsheet3.mergeCells(0, i + 2, 1, i + 2);
//				lab1 = new Label(0, i + 2, m.get("3").getNodes().get(i).getCol1(), headerFormat);
//				wsheet3.addCell(lab1);
//			}
//			// 第四张表
//			Label label14 = new Label(0, 0, "个人视频观看记录", headerFormat);
//			Label label15 = new Label(0, 1, "观看时间", headerFormat);
//			Label label16 = new Label(2, 1, "观看时长", headerFormat);
//			Label label17 = new Label(3, 1, "版本", headerFormat);
//			Label label18 = new Label(4, 1, "阶段", headerFormat);
//			Label label19 = new Label(5, 1, "天", headerFormat);
//			Label label20 = new Label(6, 1, "视频名称", headerFormat);
//			Label label_4 = new Label(8, 1, "注意：记录时间从2016-02-18开始，之前没有此功能", headerFormat);
//			wsheet4.mergeCells(8, 1, 12, 1);
//			wsheet4.mergeCells(0, 1, 1, 1);
//			wsheet4.addCell(label14);
//			wsheet4.addCell(label15);
//			wsheet4.addCell(label16);
//			wsheet4.addCell(label17);
//			wsheet4.addCell(label18);
//			wsheet4.addCell(label19);
//			wsheet4.addCell(label20);
//			wsheet4.addCell(label_4);
//			for (int i = 0; i < m.get("4").getNodes().size(); i++) {
//				lab1 = new Label(0, i + 2, m.get("4").getNodes().get(i).getCol1(), headerFormat);
//				wsheet4.mergeCells(0, i + 2, 1, i + 2);
//				double temp = Double
//						.valueOf(DateUtils.getHourFromSecond(Long.valueOf(m.get("4").getNodes().get(i).getCol2())));
//				lab2 = new Label(2, i + 2, temp > (double) 0.01 ? temp + "小时" : "<0.01小时", headerFormat);
//				lab3 = new Label(3, i + 2, m.get("4").getNodes().get(i).getCol3(), headerFormat);
//				lab4 = new Label(4, i + 2, m.get("4").getNodes().get(i).getCol4(), headerFormat);
//				lab5 = new Label(5, i + 2, m.get("4").getNodes().get(i).getCol5(), headerFormat);
//				lab6 = new Label(6, i + 2, m.get("4").getNodes().get(i).getValue(), headerFormat);
//				wsheet4.addCell(lab1);
//				wsheet4.addCell(lab2);
//				wsheet4.addCell(lab3);
//				wsheet4.addCell(lab4);
//				wsheet4.addCell(lab5);
//				wsheet4.addCell(lab6);
//			}
//
//			wbook.write();
//			wbook.close();
////			os.close();
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	/**
//	 * @tips LOOK downExamFeedBack
//	 * @tips 试卷反馈报表
//	 */
//	public static void downExamFeedBack(OutputStream os, JSONArray objArr) throws Exception {
//		try {
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			WritableSheet wsheet = wbook.createSheet("系列班试卷反馈表", 0); // 工作表名称
//			WritableCellFormat headerFormat = new WritableCellFormat();
//			headerFormat.setAlignment(Alignment.CENTRE); // 水平居中对齐
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直方向居中对齐
//			wsheet.mergeCells(0, 0, examFeedBackTitle.length - 1, 0); // 作用是从(1,2)到(3,4)的单元格全部合并
//			Label label = null;// 用于写入文本内容到工作表中去..java
//			label = new Label(0, 0, "系列班试卷反馈表", headerFormat);
//			wsheet.addCell(label);
//			for (int i = 0; i < JXL2Write.examFeedBackTitle.length; i++) {
//				label = new Label(i, 1, JXL2Write.examFeedBackTitle[i]); // 参数依次代表列数、行数、内容
//				wsheet.addCell(label);// 写入单元格
//			}
//			Exam sr = null;
//			Label label1 = null, label2 = null, label3 = null, label4 = null, label5 = null, label6 = null,
//					label7 = null, label8 = null, label9 = null;
//			// 遍历
//			int i = 0;
//			for (i = 0; i < examReport.getExamQuestionNum(); i++) {
//				String understand = examReport.getUnderstand().get(i + 1 + "");
//				label1 = new Label(0, i + 2, String.valueOf(i + 1));
//				label2 = new Label(1, i + 2, understand);
//				label3 = new Label(2, i + 2, examReport.getQuery().get(i + 1 + ""));
//				wsheet.addCell(label1);
//				wsheet.addCell(label2);
//				wsheet.addCell(label3);
//			}
////			if(examReport.getExamQuestionNum()>0){
////				for (int i = 0; i < examReport.getExamQuestionNum(); i++) {
////					sr = list.get(i);
////					label1 = new Label(0, i + 2, String.valueOf(i + 1));
////					label2 = new Label(1, i + 2, sr.getExamName());
////					label3 = new Label(2, i + 2, sr.getCenterName());
////
////					wsheet.addCell(label1);
////					wsheet.addCell(label2);
////					wsheet.addCell(label3);
////
////				}
////			}
//			wbook.write();
//			wbook.close();
//			// os.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(e.getMessage());
//		}
//	}
//
//}

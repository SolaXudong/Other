package com.xu.tt.util;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XuDong 2020-09-30 07:41:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XSTU {

	/** 学员基本信息 */
	private Integer studentId; // 学员ID
	private String trueName; // 姓名
	private String loginName; // 帐号
	private Integer isVip; // 是否是VIP
	private Date leaveTime; // 流失时间
	private Integer type; // 流失类型(1-VIP转脱产;2-VIP跨方向转VIP;3-学员删除)
	private String remark; // 备注，流失信息。如：VIP UID 转VIP JSD；退费、休学、退学、毕业（账号到期）；转GSD1601_01班
	/** 学员班级方向信息 */
	private Integer classId; // 小班ID
	private String className; // 小班名字
	private String seriesClassName; // 系列班名字
	private Integer courseId; // 方向ID
	private String shortName; // 方向简称
	/** 学员信息表 */
	private String centerName; // 申请中心
	private String isHomeCenter; // 视频学习权限
	private String version; // 版本
	private String parts; // 开通阶段
	private Date openTime; // 开通日期
	private String openStaff; // 开通人员
	/** 补充属性 */
	private Integer count; // 数量
	private String proper; // 补充属性

}

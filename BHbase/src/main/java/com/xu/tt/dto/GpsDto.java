package com.xu.tt.dto;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsDto {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 设备编号（设备表ICCID）
	 */
	private String deviceid;

	/**
	 * 心跳间隔
	 */
	private Integer per;

	private Integer today;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 修改时间
	 */
	private Date updatetime;

	/**
	 * 0点到24点，期间，GPS拼接（经,纬;经,纬;...）
	 */

	private byte[] hour1;
	private byte[] hour2;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	/**
	 * @tips LOOK ==================== 扩展属性 ====================
	 */
	private Object msg;
	private Object time;
	private Object gpsStr;

}
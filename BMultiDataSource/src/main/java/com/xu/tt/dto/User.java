package com.xu.tt.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
	/**
	 * 主键： 1-xx 2-yy 3-zz
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	private Date birth;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_id")
	private Integer createId;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "update_id")
	private Integer updateId;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
package com.xu.tt.dto;

import java.util.Date;

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

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
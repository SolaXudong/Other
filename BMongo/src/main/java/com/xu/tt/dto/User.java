package com.xu.tt.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XuDong 2020-10-20 02:38:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = -1762192190125758757L;

	private Long id;
	private String name;
	private String pwd;
	private String idCard;
	private Date birth;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

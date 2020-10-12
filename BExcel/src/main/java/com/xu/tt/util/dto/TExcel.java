package com.xu.tt.util.dto;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XuDong 2020-10-12 10:17:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TExcel {

	private String uName;
	private String uIdno;
	private String uPhone;
	private String caseNo;
	private String productV;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

package com.xu.tt.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XuDong 2020-01-19 19:05:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log_user")
public class UserDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String password;

	private Integer age;

	@Column(name = "birth")
	@JsonFormat(timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	/**
	 * @tips LOOK ==================== 扩展属性 ====================
	 */

}

package com.xu.tt.dto;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @tips 用户信息表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "g_user")
public class GUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户账号
	 */
	private String userNm;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 分组编号
	 */
	private Integer groupId;

	/**
	 * 注册类型 0-验证码临时用户 1-手机 2-微信 3-微博 4-QQ
	 */
	private Integer regType;

	/**
	 * 来源0:赊购
	 */
	private Integer regSource;

	/**
	 * 注册IP
	 */
	private String regIp;

	/**
	 * 注册时间
	 */
	private LocalDateTime regDt;

	/**
	 * 是否禁用 1是 0否
	 */
	private Integer disabled;

	/**
	 * 登陆次数
	 */
	private Integer loginNum;

	/**
	 * 最后登陆时间
	 */
	private LocalDateTime lastLoginDt;

	/**
	 * 最后登陆IP
	 */
	private String lastLoginIp;

	private String loginToken;

	/**
	 * 1-已删 0-未删
	 */
	private Integer isDeleted;

	/**
	 * 是否允许app登录 1-是
	 */
	private Integer app;

	private Integer insId;

	private LocalDateTime insDt;

	private Integer updId;

	private LocalDateTime updDt;

	/**
	 * 渠道
	 */
	private String channel;

	private Integer isWhiteIp;

	private String whiteIp;

	private Integer passwordStrength;

}

package com.xu.tt.entity;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Builder;
import lombok.Data;

/**
 * @tips 用户信息表
 */
@Data
@Builder
public class GUser {

	@TableId(value = "id", type = IdType.AUTO)
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
	private Date regDt;

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
	private Date lastLoginDt;

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

	private Date insDt;

	private Integer updId;

	private Date updDt;

	/**
	 * 渠道
	 */
	private String channel;

	private Integer isWhiteIp;

	private String whiteIp;

	private Integer passwordStrength;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

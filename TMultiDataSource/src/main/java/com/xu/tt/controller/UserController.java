package com.xu.tt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xu.tt.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-14 03:26:51
 */
@Slf4j
@Controller
@RequestMapping("/u")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("/u1")
	public Object u1() throws Exception {
		log.info("########## /u1");
		Object json = JSONObject.toJSON(userService.getUserList1());
		return json;
	}

	@ResponseBody
	@RequestMapping("/u2")
	public Object u2() throws Exception {
		log.info("########## /u2");
		Object json = JSONObject.toJSON(userService.getUserList2());
		return json;
	}

}

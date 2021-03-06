package com.xu.tt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.tt.dto.User;
import com.xu.tt.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-02-06 22:08:30
 * @tips localhost:8080
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @tips LOOK
	 * @tips /getOne/1
	 */
	@ResponseBody
	@RequestMapping("/getOne/{id}")
	public Object getOne(@PathVariable("id") Integer id) {
		log.info("##### controller getOne");
		User user = userService.getOne(id);
		return user;
	}

	/**
	 * @tips LOOK
	 * @tips /findByIdBetween/1/3
	 */
	@ResponseBody
	@RequestMapping("/findByIdBetween/{min}/{max}")
	public Object findByIdBetween(@PathVariable("min") Integer min, @PathVariable("max") Integer max) {
		log.info("##### controller findByIdBetween");
		List<User> userList = userService.findByIdBetween(min, max);
		return userList;
	}

	/**
	 * @tips LOOK
	 * @tips /findUserByIdLt/3
	 */
	@ResponseBody
	@RequestMapping("/findUserByIdLt/{id}")
	public Object findUserByIdLt(@PathVariable("id") Integer id) {
		log.info("##### controller findUserByIdLt");
		List<User> userList = userService.findUserByIdLt(id);
		return userList;
	}

}

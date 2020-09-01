package com.xu.tt.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xu.tt.dao.UserDao;
import com.xu.tt.dto.UserDto;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @author XuDong 2020-01-18 06:29:49
 *         <p>
 *         http://localhost:8080/t?t=徐东
 */
@Slf4j
@RestController
public class LogController {

//	private static Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired(required = false)
	private UserDao userDao;

	@GetMapping("/t")
	public String get(String t) {
//		logger.debug("========== controller debug消息");
//		logger.info("========== controller info消息");
//		logger.warn("========== controller warn消息");
//		logger.error("========== controller error消息");
		log.debug("########## controller debug消息");
		log.info("########## controller info消息");
		log.warn("########## controller warn消息");
		log.error("########## controller error消息");
		Example ex = new Example(UserDto.class);
		ex.and().andIn("id", Arrays.asList("1,2,3".split(",")));
		List<UserDto> list = userDao.selectByExample(ex);
		log.info("########## {}", JSON.toJSON(list));
		return "收到了: " + t;
	}

}

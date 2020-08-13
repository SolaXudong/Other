package com.xu.tt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSONObject;
import com.xu.tt.service.UserService;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() throws Exception {
		System.out.println("test");
		System.out.println(JSONObject.toJSON(userService.getUserList()));
	}

}

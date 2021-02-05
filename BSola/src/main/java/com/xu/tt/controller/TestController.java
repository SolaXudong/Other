package com.xu.tt.controller;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xu.tt.service.TestService;

/**
 * @author XuDong 2021-02-05 02:24:44
 */
@Controller
@RequestMapping("/t")
public class TestController {

	@Autowired
	private TestService testService;
	@Autowired
	@Qualifier("testService1")
	private TestService testService1;
	@Autowired
	@Qualifier("testService2")
	private TestService testService2;

	@RequestMapping("/t1")
	public String t1(ModelMap map) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
		String str = resourceBundle.getString("logging.file.max-size");
		map.addAttribute("name", "Sola，徐，" + str + "");

		testService.sendMSG();
		testService1.sendMSG();
		testService2.sendMSG();

		return "hello";
	}

	@ResponseBody
	@RequestMapping("/t2")
	public Object t2() {
		JSONObject obj = JSONObject.parseObject("{'name':'徐',age:28}");
		return obj;
	}

}

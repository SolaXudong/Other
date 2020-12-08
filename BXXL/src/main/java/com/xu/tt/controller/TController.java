package com.xu.tt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author XuDong 2020-12-09 01:07:13
 */
@Controller
@RequestMapping("/t")
public class TController {

	@ResponseBody
	@RequestMapping("/t1")
	public Object t1() {
		return "##### xxl job executor running.";
	}

}

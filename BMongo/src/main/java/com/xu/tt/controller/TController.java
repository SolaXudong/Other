package com.xu.tt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-10-20 00:20:32
 */
@Slf4j
@Controller
public class TController {

	@ResponseBody
	@RequestMapping("/t")
	public String t() {
		log.info("########## t");
		long cost = System.currentTimeMillis();
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
		return "ok";
	}

}

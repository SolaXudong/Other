package com.xu.tt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

/**
 * @author XuDong 2020-11-18 21:47:21
 */
@Controller
public class TestController {

	@ApiOperation(value = "注册接口第1步")
	@ApiOperationSupport(order = 1)
	@ResponseBody
	@GetMapping("/step1")
	public Object getBody1() {
		return "1";
	}

	@ApiOperation(value = "注册接口第3步")
	@ApiOperationSupport(order = 3)
	@ResponseBody
	@GetMapping("/step3")
	public Object getBody3() {
		return "3";
	}

	@ApiOperation(value = "注册接口第2步")
	@ApiOperationSupport(order = 2)
	@ResponseBody
	@GetMapping("/step2")
	public Object getBody2() {
		return "2";
	}

}

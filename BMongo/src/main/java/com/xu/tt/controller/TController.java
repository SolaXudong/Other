package com.xu.tt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-10-20 00:20:32
 */
@Slf4j
@Controller
public class TController {

	@Autowired
	private ThreadPoolTaskExecutor exe;

	@ResponseBody
	@RequestMapping("/t")
	public WebAsyncTask<Object> t() {
		log.info("########## t");
		long cost = System.currentTimeMillis();
		WebAsyncTask<Object> asyncTask = new WebAsyncTask<Object>(10 * 1000L, exe, () -> {
			log.info("1-{}", Thread.currentThread().getName());
			log.info("start...");
			Thread.sleep(2000);
			log.info("end...");
			return new JSONObject();
		});
		asyncTask.onCompletion(() -> {
			log.info("2-{}", Thread.currentThread().getName());
			log.info("onCompletion...");
		});
		log.info("3-{}", Thread.currentThread().getName());
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
		return asyncTask;
	}

}

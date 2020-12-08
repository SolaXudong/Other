package com.xu.tt.pub.xxl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-09 01:22:00
 */
@Slf4j
@Component
public class MyJob extends IJobHandler {

	@Override
	@XxlJob("MyJob")
	public ReturnT<String> execute(String param) throws Exception {
		log.info("##### MyJob\t" + LocalDateTime.now());
		return SUCCESS;
	}

}

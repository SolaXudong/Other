package com.xu.tt.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.xu.tt.service.TestService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-02-05 10:59:16
 */
@Slf4j
@Service("testService1")
@Primary
public class TestServiceImpl1 implements TestService {

	@Override
	public void sendMSG() {
		log.info("##### sendMSG primary");
	}

}

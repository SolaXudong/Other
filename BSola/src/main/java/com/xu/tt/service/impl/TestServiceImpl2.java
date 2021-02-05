package com.xu.tt.service.impl;

import org.springframework.stereotype.Service;

import com.xu.tt.service.TestService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-02-05 11:00:35
 */
@Slf4j
@Service("testService2")
public class TestServiceImpl2 implements TestService {

	@Override
	public void sendMSG() {
		log.info("##### sendMSG second");
	}

}

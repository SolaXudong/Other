package com.xu.tt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class B_UserService {

	@Autowired
	UserService userService;

	public void transactionMulti() throws Exception {
		log.info("########## updates");
		userService.transactionMultiP1();
		userService.transactionMultiP2();
//		if (1 / 0 > 0) {
//		}
	}

}

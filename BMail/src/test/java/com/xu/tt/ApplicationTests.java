package com.xu.tt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xu.tt.pub.mail.SendMail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ApplicationTests {

	@Autowired
	private SendMail sendMail;

	@Test
	void testMail() throws Exception {
		log.info("#################### testMail");
		sendMail.send1();
//		sendMail.send2();
//		sendMail.send3();
//		sendMail.send4();
	}

}

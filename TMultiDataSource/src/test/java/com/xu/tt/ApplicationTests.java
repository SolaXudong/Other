package com.xu.tt;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSONObject;
import com.xu.tt.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserService userService;
	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;

	@Test
	public void testService() throws Exception {
		log.info("########## testService");
		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList()));
	}

	@Test
	public void testDataSource() throws Exception {
		log.info("########## testDataSource");
		Connection c1 = masterDataSource.getConnection();
		log.info("########## c1: {}", c1.getMetaData().getURL());
		Connection c2 = slaveDataSource.getConnection();
		log.info("########## c2: {}", c2.getMetaData().getURL());
	}

}

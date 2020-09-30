package com.xu.tt;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xu.tt.service.B_UserService;
import com.xu.tt.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private B_UserService b_userService;
	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;
	@Resource(name = "dynamicDataSource")
	private DataSource dynamicDataSource;

	@Test
	public void testService() throws Exception {
		log.info("########## 【test Service from different datasource】");
		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList()));
		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList1()));
		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList2()));
	}

	@Test
	public void testDataSource() throws Exception {
		log.info("########## 【test DataSource is ok】");
		Connection c1 = masterDataSource.getConnection();
		log.info("########## c1: {}", c1.getMetaData().getURL());
		Connection c2 = slaveDataSource.getConnection();
		log.info("########## c2: {}", c2.getMetaData().getURL());
		Connection c3 = dynamicDataSource.getConnection();
		log.info("########## c3: {}", c3.getMetaData().getURL());
	}

	@Test
	public void testQuerySQL() throws Exception {
		log.info("########## 【test QuerySQL】");
		JdbcTemplate jt1 = new JdbcTemplate(masterDataSource);
		JdbcTemplate jt2 = new JdbcTemplate(slaveDataSource);
		List<Map<String, Object>> l1 = ListUtils.emptyIfNull(jt1.queryForList("select id, name from user"));
		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
		jt1.execute("update user set name = \"" + LocalDateTime.now() + "\" where id = 2");
		l1 = ListUtils.emptyIfNull(jt1.queryForList("select id, name from user"));
		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
		List<Map<String, Object>> l2 = ListUtils.emptyIfNull(jt2.queryForList("select id, name from user"));
		log.info("########## l2: count-{}, data-{}", l2.size(), l2);
	}

	@Test
	public void testTransaction() throws Exception {
		log.info("########## 【test transaction】");
		userService.transaction();
//		b_userService.transactionMulti();
	}

}

package com.xu.tt;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-13 21:10:39
 */
@Slf4j
public class TT {

	public static void main(String[] args) throws Exception {
		log.info("tt");
		DataSource ds = DataSourceBuilder.create()
				//
				.driverClassName("com.mysql.cj.jdbc.Driver").username("root").password("shijie")
				//
				.url("jdbc:mysql://localhost:3306/test?serverTimezone=UTC")
				//
				.type(com.zaxxer.hikari.HikariDataSource.class).build();
		System.out.println(ds.getConnection());
		JdbcTemplate jt1 = new JdbcTemplate(ds);
		List<Map<String, Object>> l1 = ListUtils.emptyIfNull(jt1.queryForList("select id, name from user"));
		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
	}

	@Test
	public void test() {
		log.info("tt test");
	}

}

package com.xu.tt;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xu.tt.dao.UserDao;
import com.xu.tt.dao.UserNewMapper;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-13 21:10:39
 */
@Slf4j
public class TT {

	public static void main(String[] args) throws Exception {
		log.info("##### start");
		long cost = System.currentTimeMillis();

		t0();
//		t1();

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

	public static void t0() throws Exception {
		HikariDataSource datasource = new HikariDataSource();
		{
			datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
			datasource.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
			datasource.setUsername("root");
			datasource.setPassword("shijie");
		}
		Environment environment = new Environment("", new JdbcTransactionFactory(), datasource);
		Configuration config = new Configuration(environment);
		config.addMappers("com.xu.tt.dao");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(config);
		SqlSession session = factory.openSession();

		UserDao m1 = session.getMapper(UserDao.class);
		System.out.println(m1.selectObj());
		UserNewMapper m2 = session.getMapper(UserNewMapper.class);
//		System.out.println(m2.selectAll().size());
	}

	public static void t1() throws Exception {
		DataSource ds = DataSourceBuilder.create()
				//
				.driverClassName("com.mysql.cj.jdbc.Driver").username("root").password("shijie")
				//
				.url("jdbc:mysql://localhost:3306/test?serverTimezone=UTC")
				//
				.type(com.zaxxer.hikari.HikariDataSource.class).build();
		System.out.println(ds.getConnection());
		JdbcTemplate jt1 = new JdbcTemplate(ds);
		List<Map<String, Object>> l1 = ListUtils.emptyIfNull(jt1.queryForList("select * from user where id = 1"));
		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
	}

	@Test
	public void test() {
		log.info("tt test");
	}

}

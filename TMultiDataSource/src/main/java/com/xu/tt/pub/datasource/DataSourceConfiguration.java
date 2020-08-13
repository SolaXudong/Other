package com.xu.tt.pub.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-13 20:53:36
 */
@Slf4j
@Configuration
@EnableTransactionManagement // 开启事务
public class DataSourceConfiguration {

	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Primary
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		log.info("########## master : {}", masterDataSource);
		return masterDataSource;
	}

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() {
		DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		log.info("########## slave : {}", slaveDataSource);
		return slaveDataSource;
	}

}

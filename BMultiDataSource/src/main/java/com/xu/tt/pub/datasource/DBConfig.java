package com.xu.tt.pub.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-13 20:53:36
 */
@Slf4j
@Configuration
//@EnableTransactionManagement // 开启事务
//@AutoConfigureAfter(MainConfig.class)
public class DBConfig {

	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

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

	/**
	 * @tips SpringBoot会自动注入DataSource，在启动类注解上排除掉就好了
	 *       <p>
	 * @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	@DependsOn({ "masterDataSource", "slaveDataSource" })
	public DataSource dynamicDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
		DBRouting dynamicDataSource = new DBRouting();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put(DBContextHolder.DBType.MASTER, masterDataSource);
		map.put(DBContextHolder.DBType.SLAVE, slaveDataSource);
		// 默认数据源
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
		// 装入两个主从数据源
		dynamicDataSource.setTargetDataSources(map);
		log.info("########## dynamic : {}", dynamicDataSource);
		return dynamicDataSource;
	}

	class DBRouting extends AbstractRoutingDataSource {
		@Override
		protected Object determineCurrentLookupKey() {
			return DBContextHolder.getDataBaseType();
		}
	}

//	@Bean
//	@DependsOn({ "dynamicDataSource" })
//	public PlatformTransactionManager transactionManager(DataSource dynamicDataSource) throws Exception {
//		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
//		txManager.setDataSource(dynamicDataSource);
//		return txManager;
//	}

}

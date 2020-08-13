package com.xu.tt.pub.datasource;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.aspectj.apache.bcel.util.ClassLoaderRepository.SoftHashMap;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author XuDong 2020-08-14 01:07:03
 */
@Configuration
@AutoConfigureAfter({ DBConfig.class }) // 先注入DataSource，再注入SqlSessionFactory
public class DBSqlSessionFactory extends MybatisAutoConfiguration {

	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory SqlSessionFactory() throws Exception {
		return super.sqlSessionFactory(roundRobinDataSourceProxy());
	}

	public AbstractRoutingDataSource roundRobinDataSourceProxy() {
		DBRouting proxy = new DBRouting();
		SoftHashMap map = new ClassLoaderRepository.SoftHashMap();
		map.put(DBContextHolder.DataBaseType.MASTER, masterDataSource);
		map.put(DBContextHolder.DataBaseType.SLAVE, slaveDataSource);
		// 默认数据源
		proxy.setDefaultTargetDataSource(masterDataSource);
		// 装入两个主从数据源
		proxy.setTargetDataSources(map);
		return proxy;
	}

	public DBSqlSessionFactory(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
			ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
		super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
	}

}

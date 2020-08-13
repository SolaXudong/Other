package com.xu.tt.pub.datasource;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author XuDong 2020-08-14 01:07:03
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
public class MybatisConfiguration extends MybatisAutoConfiguration {

}

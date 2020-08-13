package com.xu.tt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author XuDong 2020-08-13 23:37:33
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.xu.tt.*" })
@MapperScan(basePackages = "com.xu.tt.dao")
public class MainConfig {

}

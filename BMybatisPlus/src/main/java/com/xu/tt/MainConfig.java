package com.xu.tt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.xu.tt.*" })
@MapperScan(basePackages = "com.xu.tt.mapper")
public class MainConfig {

}

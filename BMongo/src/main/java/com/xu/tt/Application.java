package com.xu.tt;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ThreadPoolTaskExecutor initExecutor() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(500);
		te.setKeepAliveSeconds((int) TimeUnit.SECONDS.toSeconds(60));
		te.setMaxPoolSize(1000);
		te.setQueueCapacity(75);
		te.setThreadNamePrefix("asyncTask-");
		return te;
	}

}

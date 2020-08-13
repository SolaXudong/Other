package com.xu.tt.pub.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-14 01:38:11
 */
@Slf4j
@Aspect
@Component
public class SlaveDBAOP implements Ordered {

	@Around("@annotation(slaveDB)") // slaveDB与下面参数名slaveDB对应
	public Object proceed(ProceedingJoinPoint point, SlaveDB slaveDB) throws Throwable {
		try {
			log.info("########## 切换从数据源");
			DBContextHolder.setDataBaseType(DBContextHolder.DataBaseType.SLAVE);
			Object result = point.proceed();
			return result;
		} finally {
			DBContextHolder.clearDataBaseType();
			log.info("########## 还原主数据源");
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

}

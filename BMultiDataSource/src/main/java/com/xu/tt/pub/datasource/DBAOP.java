package com.xu.tt.pub.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-14 01:38:11
 */
@Slf4j
@Aspect // 注解将一个java类定义为切面类
@Component
public class DBAOP implements Ordered {

//	@Before("execution(* com.xu.tt.service..*.*(..))") // 在切入点开始处切入内容
//	@After("execution(* com.xu.tt.service..*.*(..))") // 在切入点结尾处切入内容
	@Around("@annotation(routingDB)") // routingDB与下面参数名routingDB对应
	public Object proceed(ProceedingJoinPoint point, RoutingDB routingDB) throws Throwable {
		try {
			// 取类上的注解
			RoutingDB curClassDB = point.getTarget().getClass().getAnnotation(RoutingDB.class);
			// 取方法
			MethodSignature curMethod = (MethodSignature) point.getSignature();
			// 取方法上的注解
			RoutingDB curMethodDB = point.getTarget().getClass()
					.getMethod(curMethod.getName(), curMethod.getParameterTypes()).getAnnotation(RoutingDB.class);
			// 方法上的注解优先级高于类上的注解
			curMethodDB = curMethodDB == null ? curClassDB : curMethodDB;
			DBContextHolder.DBType dbType = curMethodDB != null && curMethodDB.value() != null ? curMethodDB.value()
					: DBContextHolder.DBType.MASTER;
			// 注解是主数据源就不切换了
			if (!dbType.name().equals(DBContextHolder.DBType.MASTER.name())) {
				// 切换从数据源
				log.info("########## 切换从数据源，{}", dbType.name());
				DBContextHolder.setDataBaseType(dbType);
				Object proceed = point.proceed();
				// 还原主数据源
				DBContextHolder.clearDataBaseType();
				log.info("########## 还原主数据源");
				return proceed;
			} else {
				return point.proceed();
			}
		} catch (Exception e) {
			log.info("########## 该注解只能用于方法");
			DBContextHolder.clearDataBaseType();
			return point.proceed();
		}
	}

	@Override
	public int getOrder() {
		return 0; // 负值，确保是第一个进行执行的
	}

}

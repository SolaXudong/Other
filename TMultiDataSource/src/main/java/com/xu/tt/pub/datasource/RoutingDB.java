package com.xu.tt.pub.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author XuDong 2020-08-14 01:36:26
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoutingDB {

	DBContextHolder.DBType value() default DBContextHolder.DBType.MASTER;

}

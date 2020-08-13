package com.xu.tt.pub.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author XuDong 2020-08-14 01:19:35
 */
public class DBRouting extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DBContextHolder.getDataBaseType();
	}

}

package com.xu.tt.pub.datasource;

/**
 * @author XuDong 2020-08-13 23:58:03
 */
public class DBContextHolder {

	public enum DBType {
		MASTER, SLAVE
	}

	private static final ThreadLocal<DBType> contextHolder = new ThreadLocal<>();

	public static void setDataBaseType(DBType dataBaseType) {
		contextHolder.set(dataBaseType);
	}

	public static DBType getDataBaseType() {
		return contextHolder.get() == null ? DBType.MASTER : contextHolder.get();
	}

	/**
	 * @tips 为了不影响下一个Thread操作
	 */
	public static void clearDataBaseType() {
		contextHolder.remove();
	}

}

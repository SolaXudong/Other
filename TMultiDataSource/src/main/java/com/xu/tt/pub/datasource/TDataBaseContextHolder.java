package com.xu.tt.pub.datasource;

/**
 * @author XuDong 2020-08-13 23:58:03
 */
public class TDataBaseContextHolder {

	public enum DataBaseType {
		MASTER, SLAVE
	}

	private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<>();

	public static void setContextholder(DataBaseType dataBaseType) {
		contextHolder.set(dataBaseType);
	}

	public static DataBaseType getContextholder() {
		return contextHolder.get() == null ? DataBaseType.MASTER : contextHolder.get();
	}

	/**
	 * @tips 为了不影响下一个Thread操作
	 */
	public static void clearDataBaseType() {
		contextHolder.remove();
	}

}

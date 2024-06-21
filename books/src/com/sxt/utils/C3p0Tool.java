package com.sxt.utils;

/**
 * C3p0宸ュ叿绫�
 * @author Administrator
 *
 */

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Tool {

	private static DataSource dataSource = new ComboPooledDataSource();
	
	/**
	 * 鑾峰彇鏁版嵁婧�
	 * @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 鑾峰彇杩炴帴
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}

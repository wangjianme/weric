package cn.grades.core;


import java.sql.Connection;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DSUtils {
	private static DataSource dataSource;
	static{
		dataSource = new ComboPooledDataSource();
	}
	public static DataSource getDataSource(){
		return dataSource;
	}
	@SuppressWarnings("unused")
	private static Connection getConnection(){
		Connection con = null;
		try{
			con = dataSource.getConnection();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return con;
	}
}

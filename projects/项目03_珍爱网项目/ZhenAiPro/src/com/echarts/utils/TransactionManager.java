package com.echarts.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static ThreadLocal<Connection> conn_tl=new ThreadLocal<Connection>(){
		protected Connection initialValue() {
			return DaoUtils.getConn();			
		};
	};
	private TransactionManager(){}
	//开启事物
	public static void startTran(){
		try {
			//conn_tl.get()获取本地线程对象
			conn_tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//提交事物
	public static Connection getConn(){
		return conn_tl.get();
	}
	//提交事物
	public static void commitTran(){
		try {
			conn_tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//回滚事物
	public static void rollbackTran(){
		try {
			conn_tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static void release(){
		conn_tl.remove();
	}
}

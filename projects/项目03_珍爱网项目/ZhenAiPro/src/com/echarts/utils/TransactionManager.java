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
	//��������
	public static void startTran(){
		try {
			//conn_tl.get()��ȡ�����̶߳���
			conn_tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//�ύ����
	public static Connection getConn(){
		return conn_tl.get();
	}
	//�ύ����
	public static void commitTran(){
		try {
			conn_tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//�ع�����
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

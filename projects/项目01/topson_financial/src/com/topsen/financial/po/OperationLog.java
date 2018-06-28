package com.topsen.financial.po;

import java.sql.Date;
import java.util.Calendar;

import com.topsen.financial.dao.impl.LogDAOImpl;
import com.topsen.financial.dao.inter.LogDAO;
public class OperationLog {
	private int logId;
	private String logType;
	private String tableName;
	private Emploee emp;
	private int year;
	private int month;
	private int day;
	private String time;
	private String logDetail;
	public String getLogDetail() {
		return logDetail;
	}
	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private String content;
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Emploee getEmp() {
		return emp;
	}
	public void setEmp(Emploee emp) {
		this.emp = emp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static OperationLog getNewLog(String logType,String tableName,String content,String logDetail){
		OperationLog log = new OperationLog();
		log.setContent(content);
		log.setLogDetail(logDetail);
		log.setLogType(logType);
		log.setTableName(tableName);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DATE);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String time = hour>=13 ? "обнГ":"ионГ";
		log.setYear(year);
		log.setMonth(month);
		log.setDay(day);
		log.setTime(time);
		return log;
	}
}

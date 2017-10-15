package com.myexam.pub;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 获取当前时间
 * @author wangjianme
 * @version 1.0,2010-6-18
 */
public class CurrentTime {
	private CurrentTime(){
	}
	private static SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取当时时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate(){
		return s.format(new Date());
	}
	/**
	 * 格式化时间
	 */
	public static String getDate(Date date){
		return s.format(date);
	}
	/**
	 * 根据提供的字符串返回时间对像
	 * yyyy-MM-dd HH:mm:ss
	 * @return Date
	 */
	public static Date getDate(String dateStr){
		Date date = null;
		try{
			date = s.parse(dateStr);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
}

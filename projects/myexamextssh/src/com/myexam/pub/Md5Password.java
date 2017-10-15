package com.myexam.pub;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Md5Password
 * @author wangjianme
 * @version 1.0,2010-6-8
 */
public class Md5Password {
	private static final String ENCODER = "myexam";
	private static Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	private Md5Password(){
		
	}
	/**
	 * Md5Password
	 * 接收用户名和密码进行加密运算
	 */
	public static String encodePwd(String userName,String password){
		String encodedPwd = md5.encodePassword(userName, password);
		return encodedPwd;
	}
	/**
	 * 只接收密码然后加密,使用固定的值对密码进行加密运算
	 * @param password
	 * @return
	 */
	public static String encodePwd(String password){
		return md5.encodePassword(ENCODER, password);
	}
	public static void main(String[] args) {
		String name = "myexam";//solt
		String pwd = "接收用户名和密码进行加密运算";
		pwd = encodePwd(name,pwd);
		System.err.println(pwd);
	}
}

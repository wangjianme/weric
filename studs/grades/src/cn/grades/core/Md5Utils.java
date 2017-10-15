package cn.grades.core;

import java.security.MessageDigest;
import java.util.UUID;

public class Md5Utils {
	public static String md5(String pwd, String salt) {
		String newPwd = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 对原密码进行算法
			byte[] bs = md.digest(pwd.getBytes());
			// 遍历字节
			for (byte b : bs) {
				int x = (b + salt.hashCode()) & 0xff;
				String hex = Integer.toHexString(x);
				if (hex.length() == 1) {
					hex += "0";
				}
				newPwd += hex;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newPwd;
	}

	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		System.err.println("uuid :"+uuid);//83fc9f3cca2f4487b0c478d55757e0b8
		String pwd = "1234";
		pwd = md5(pwd, uuid);
		System.err.println(pwd);//9df8b7f76eec69de1c52f7f44d5aec71
	}
}

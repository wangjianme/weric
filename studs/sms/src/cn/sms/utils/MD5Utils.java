package cn.sms.utils;

import java.security.MessageDigest;

public class MD5Utils {
	public static String password(String rawPwd, String salt) {
		String pwd2 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 进行加密
			byte[] bs = md.digest(rawPwd.getBytes());

			for (byte b : bs) {
				// 将b转成16进制的值
				int a = (b + salt.hashCode()) & 0xff;
				String hex = Integer.toHexString(a);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				pwd2 += hex;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return pwd2;

	}
}

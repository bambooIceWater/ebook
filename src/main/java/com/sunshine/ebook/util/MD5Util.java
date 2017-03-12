package com.sunshine.ebook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * @author limingguang
 * @date 2017年03月11日23:12:05
 */
public class MD5Util {

	/**
	 * 利用MD5进行加密
	 * @param str 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encoderByMd5(String str) {

		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			for (int i = 0; i < bytes.length; i++) {
				String s = Integer.toHexString(0xff & bytes[i]);
				if (s.length() == 1) {
					sb.append("0" + s);
				} else {
					sb.append(s);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 比较两个字符串是否相同
	 * @param passA 加密之前的字符串
	 * @param passB 加密后的字符串
	 * @return
	 */
	public static boolean compareString(String passA, String passB) {
		boolean flag = false;
		String newPassA = encoderByMd5(passA);
		if (newPassA.equals(passB)) {
			flag = true;
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.encoderByMd5("1"));
	}

}

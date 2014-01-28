package com.runyetech.find2.merchants.util;

import android.util.Base64;

public class StringToBase64 {
	/**
	 * BASE64加密字符串
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 加密后的BASE64编码
	 */
	public static String encrypt1(String str) {
		return android.util.Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
	}

	/**
	 * BASE64加密字符串
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 加密后的BASE64编码
	 */
	public static String encrypt2(String str) {
		return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
	}

	/**
	 * 解密BASE64编码
	 * 
	 * @param str
	 *            需要解密的BASE64编码字符串
	 * @return 解密后的字符串
	 */
	public static String decrypt(String str) {
		byte b[] = android.util.Base64.decode(str, Base64.DEFAULT);
		return new String(b);
	}
}

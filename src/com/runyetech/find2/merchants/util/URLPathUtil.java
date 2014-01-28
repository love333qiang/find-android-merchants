package com.runyetech.find2.merchants.util;

public class URLPathUtil {
	/** IP地址 */
	private static final String IP = "http://api.runyetech.com";
	/** 登录的地址 */
	public static final String LOGIN_URLPAHT = IP + "/app/action/login.php";
	/** 商户版的IP地址 */
	private static final String MHIP = "http://api.tyfind.com";
	/** 商户版的网址路径 */
	public static final String REGISTER_URLPATH = MHIP + "/merchants";
	/** 获取Token的路径 */
	public static final String TOKENT = MHIP + "/token";
}

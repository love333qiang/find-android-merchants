package com.runyetech.find2.merchants.util;

import android.util.Log;

public class LogInfoPrint {
	/**
	 * 正常日志输出
	 * 
	 * @param isPrint
	 *            是否输出
	 * @param logInfo
	 *            输入的日志信息
	 */
	public static void i(boolean isPrint, String logInfo) {
		if (isPrint) {
			Log.i("润叶科技", logInfo);
		}
	}

	/**
	 * 错误日志输出
	 * 
	 * @param isPrint
	 *            是否输出
	 * @param logInfo
	 *            输入的日志信息
	 */
	public static void e(boolean isPrint, String logInfo) {
		if (isPrint) {
			Log.e("润叶科技", logInfo);
		}
	}

	/**
	 * 警告日志输出
	 * 
	 * @param isPrint
	 *            是否输出
	 * @param logInfo
	 *            输入的日志信息
	 */
	public static void w(boolean isPrint, String logInfo) {
		if (isPrint) {
			Log.e("润叶科技", logInfo);
		}
	}
}

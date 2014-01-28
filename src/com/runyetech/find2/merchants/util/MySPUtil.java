package com.runyetech.find2.merchants.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPUtil {
	/**
	 * 判断程序是不是第一次运行，如果是第一次运行，就将默认的图片复制到SD卡中，否则不执行任何操作
	 * 
	 * @param context
	 *            上下文对象
	 * @param sp
	 *            SharedPreferences对象
	 */
	public static void copyPicFirstRun(Context context, SharedPreferences sp) {
		if (sp.getBoolean("ISFIRST", true)) {
			LogInfoPrint.i(true, "程序第一次运行");
			sp.edit().putBoolean("ISFIRST", false).commit();// 设置程序不是第一次运行
			// 复制默认图片到SD卡中
		} else {
			LogInfoPrint.i(true, "程序不是第一次运行……");
		}
	}
}

package com.runyetech.find2.merchants.util;

import android.content.Context;
import android.widget.Toast;

public class LogInfoToast {
	public static void showToast(boolean isShow, Context context, String toastInfo) {
		if (isShow) {
			Toast.makeText(context, toastInfo, Toast.LENGTH_LONG).show();
		}
	}
}

package com.runyetech.find2.merchants.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;

public class MyDatePicker {
	// 用来保存年月日：
	private int mYear;
	private int mMonth;
	private int mDay;
	// 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID：
	static final int DATE_DIALOG_ID = 0;

	private void test() {
		// 获得当前的日期：
		final Calendar currentDate = Calendar.getInstance();
		mYear = currentDate.get(Calendar.YEAR);
		mMonth = currentDate.get(Calendar.MONTH);
		mDay = currentDate.get(Calendar.DAY_OF_MONTH);
		// 设置文本的内容：
		String str = new StringBuilder().append(mYear).append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
				.append(mDay).append("日").toString();
	}

	// 需要定义弹出的DatePicker对话框的事件监听器：
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 设置文本的内容：
			String str = new StringBuilder().append(mYear).append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日").toString();
		}
	};

	/**
	 * 当Activity调用showDialog函数时会触发该函数的调用：
	 */
	protected Dialog onCreateDialog(int id, Context context) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(context, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
}

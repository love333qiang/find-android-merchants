package com.runyetech.find2.merchants.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.runyetech.find2.merchants.MainActivity;

public class MyDailog {

	public static void cretaeDialog(final Context context, String title, String msg) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				context.startActivity(new Intent(context, MainActivity.class));
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public static void cretaeItemDialog(final Activity instance, String title, String[] item) {
		AlertDialog.Builder builder = new Builder(instance);
		builder.setTitle(title);
		builder.setItems(item, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("您选择的是：" + which);
				if (which == 0) {
					PhotosUtil.album(instance, which);
				} else if (which == 1) {
					PhotosUtil.camera(instance, which);
				}
			}
		});
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
}

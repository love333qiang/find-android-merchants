package com.runyetech.find2.merchants.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * @author YangFan 对话框
 */
public class MyDailog {

	/**
	 * 创建一个选择图片方式的Dialog
	 * 
	 * @param instance
	 *            谁调用这个方法
	 * @param title
	 *            Dialog的标题
	 * @param item
	 *            Dialog中的数组选项
	 */
	public static void cretaeChoisePicItemDialog(final Activity instance, String title, String[] item) {
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

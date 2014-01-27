package com.runyetech.find2.merchants.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

@SuppressLint("SimpleDateFormat")
public class PhotosUtil {

	/**
	 * 启动相册,并选择照片并返回
	 * 
	 * @param instance
	 *            Activity的对象
	 */
	public static void album(Activity instance, int resultCode) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.putExtra("output", Uri.fromFile(getPhotoFilePath()));
		// intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 1);// 裁剪框比例
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 200);// 输出图片大小
		// intent.putExtra("outputY", 200);
		instance.startActivityForResult(intent, resultCode);
	}

	/**
	 * 选择系统相册中的图片后调用的回调函数
	 * 
	 * @param requestCode
	 *            请求标识
	 * @param resultCode
	 *            结果标识
	 * @param data
	 *            结果数据
	 * @param instance
	 *            调用者对象
	 * @return 得到的Bitmap图像
	 */
	public static Bitmap onActivityResultAlbum(int requestCode, int resultCode, Intent data, Activity instance) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			Uri uri = data.getData();
			LogInfoPrint.i(true, uri.toString());
			ContentResolver cr = instance.getContentResolver();
			try {
				Bitmap bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
				return bmp;
			} catch (FileNotFoundException e) {
				LogInfoPrint.e(true, "Error");
				return null;
			}
		}
		return null;
	}

	/**
	 * 启动相机,并拍照返回
	 * 
	 * @param instance
	 *            Activity的对象
	 */
	public static void camera(Activity instance, int resultCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// intent.putExtra("output", Uri.fromFile(getPhotoFilePath()));
		// intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 1);// 裁剪框比例
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 200);// 输出图片大小
		// intent.putExtra("outputY", 200);
		instance.startActivityForResult(intent, resultCode);
	}

	/**
	 * 调用相机拍摄图片后调用的回调函数
	 * 
	 * @param requestCode
	 *            请求标识
	 * @param resultCode
	 *            结果标识
	 * @param data
	 *            数据
	 * @param instance
	 *            调用者对象
	 * @return Bitmap图片
	 */
	public static Bitmap onActivityResultCamera(int requestCode, int resultCode, Intent data, Activity instance) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			Bundle bundle = data.getExtras();
			Bitmap bmp = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			return bmp;
		}
		return null;
	}

	/** 使用系统当前日期加以调整作为照片的名称 */
	private static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 获取图片的保存路径
	 * 
	 * @return 返回图片路径
	 */
	public static File getPhotoFilePath() {
		// SD卡中放置截图的文件夹的路径
		String pngpathdir = android.os.Environment.getExternalStorageDirectory().getPath() + "/RyTech";
		File file = new File(pngpathdir);
		if (Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())) {
			if (!file.exists())
				file.mkdir();
			String pngpath = pngpathdir + "/" + getPhotoFileName() + ".jpg";
			File dbfile = new File(pngpath);
			return dbfile;
		} else {
			return null;
		}
	}
}

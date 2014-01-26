package com.runyetech.find2.merchants.util;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class PhotosUtil {

	/** 在系统相册中选取 */
	public static final int ALBUM = 1;
	/** 调用系统相机拍照 */
	public static final int CAMERA = 2;

	/**
	 * 启动相机,并拍照返回
	 * 
	 * @param instance
	 *            Activity的对象
	 */
	public static void camera(Activity instance, int resultCode) {
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		instance.startActivityForResult(camera, resultCode);
	}

	/**
	 * 启动相册,并选择照片并返回
	 * 
	 * @param instance
	 *            Activity的对象
	 */
	public static void album(Activity instance, int resultCode) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		instance.startActivityForResult(intent, resultCode);
	}

	public static Bitmap onActivityResultAlbum(int requestCode, int resultCode, Intent data, Activity instance) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			Uri uri = data.getData();
			LogInfoPrint.i(true, uri.toString());
			ContentResolver cr = instance.getContentResolver();
			try {
				return BitmapFactory.decodeStream(cr.openInputStream(uri));
			} catch (FileNotFoundException e) {
				LogInfoPrint.e(true, "Error");
				return null;
			}
		}
		return null;
	}

	public static Bitmap onActivityResultCamera(int requestCode, int resultCode, Intent data, Activity instance) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			Bundle bundle = data.getExtras();
			return (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
		}
		return null;
	}
}

package com.runyetech.find2.merchants.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

public class Bitmap2Bytes {
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}

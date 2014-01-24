package com.runyetech.find2.merchants.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

/**
 * @author YangFan 获取经纬度
 * 
 */
public class LocationService {
	public static LocationInfo AllGet(Context context) {
		LocationInfo loInfo = null;
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			loInfo = GpsGet(context);
			if (loInfo == null) {
				loInfo = NetworkGet(context);
				if (loInfo == null) {
					TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
					int type = tm.getPhoneType();
					if (type == TelephonyManager.PHONE_TYPE_GSM) {
						loInfo = GsmGet(context);
					} else if (type == TelephonyManager.PHONE_TYPE_CDMA) {
						loInfo = CdmaGet(context);
					}
				}
			}
		} else {
			loInfo = NetworkGet(context);
			if (loInfo == null) {
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				int type = tm.getPhoneType();
				if (type == TelephonyManager.PHONE_TYPE_GSM) {
					loInfo = GsmGet(context);
				} else if (type == TelephonyManager.PHONE_TYPE_CDMA) {
					loInfo = CdmaGet(context);
				}
			}
		}
		return loInfo;
	}

	/**
	 * 通过GPS获取经纬度 适用于Gps
	 */
	public static LocationInfo GpsGet(Context context) {

		LocationInfo loInfo = null;
		double lat = 0.0;
		double lon = 0.0;
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {

			// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			// Provider被enable时触发此函数，比如GPS被打开
			@Override
			public void onProviderEnabled(String provider) {

			}

			// Provider被disable时触发此函数，比如GPS被关闭
			@Override
			public void onProviderDisabled(String provider) {

			}

			// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
			@Override
			public void onLocationChanged(Location location) {

			}

		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, locationListener);

		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			lat = location.getLatitude();
			lon = location.getLongitude();
			loInfo = new LocationInfo(lat, lon);
		}

		return loInfo;
	}

	/**
	 * 通过网络获取经纬度 适用于wifi和GPRS
	 */
	public static LocationInfo NetworkGet(Context context) {

		Log.e("tag", "NetWorkLocation");

		LocationInfo loInfo = null;
		double lat = 0.0;
		double lon = 0.0;
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {

			// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			// Provider被enable时触发此函数，比如GPS被打开
			@Override
			public void onProviderEnabled(String provider) {

			}

			// Provider被disable时触发此函数，比如GPS被关闭
			@Override
			public void onProviderDisabled(String provider) {

			}

			// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
			@Override
			public void onLocationChanged(Location location) {

			}

		};
		// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5f,
		// locationListener);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location != null) {
			Log.e("tag", "network!=null");
			lat = location.getLatitude();
			lon = location.getLongitude();
			loInfo = new LocationInfo(lat, lon);
		}

		return loInfo;

	}

	/**
	 * 通过CDMA获取经纬度 适用于中国电信
	 */
	public static LocationInfo CdmaGet(Context context) {
		LocationInfo loInfo = null;
		double lat = 0.0;
		double lon = 0.0;
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		// tm.getPhoneType()

		CdmaCellLocation ccl = (CdmaCellLocation) tm.getCellLocation();

		if (ccl != null) {
			lat = (double) ccl.getBaseStationLatitude() / 14400;
			lon = (double) ccl.getBaseStationLongitude() / 14400;
			loInfo = new LocationInfo(lat, lon);
		}

		return loInfo;

	}

	/**
	 * 通过基站获取经纬度 适用于中国移动和联通 3g
	 * 
	 */

	public static LocationInfo GsmGet(Context context) {
		LocationInfo loInfo = null;
		double lat = 0.0;
		double lon = 0.0;

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();

		int cid = gcl.getCid();
		int lac = gcl.getLac();
		int mcc = Integer.valueOf(tm.getNetworkOperator().substring(0, 3));
		int mnc = Integer.valueOf(tm.getNetworkOperator().substring(3, 5));
		try {
			Log.e("tag", "传送json  获取数据");

			// 组装JSON查询字符串
			JSONObject holder = new JSONObject();
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			// holder.put("address_language", "zh_CN");
			holder.put("request_address", true);

			JSONArray array = new JSONArray();
			JSONObject data = new JSONObject();
			data.put("cell_id", cid); // 25070
			data.put("location_area_code", lac);// 4474
			data.put("mobile_country_code", mcc);// 460
			data.put("mobile_network_code", mnc);// 0
			array.put(data);
			holder.put("cell_towers", array);

			// 创建连接，发送请求并接受回应
			DefaultHttpClient client = new DefaultHttpClient();

			HttpPost post = new HttpPost("http://www.google.com/loc/json");

			StringEntity se = new StringEntity(holder.toString());

			post.setEntity(se);
			HttpResponse resp = client.execute(post);

			HttpEntity entity = resp.getEntity();

			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String result = br.readLine();

			Log.e("GsmGet", result);

			while (result != null) {

				sb.append(result);
				result = br.readLine();
			}
			JSONObject jsonObject = new JSONObject(sb.toString());

			JSONObject jsonObject1 = new JSONObject(jsonObject.getString("location"));

			lat = Double.parseDouble(jsonObject1.getString("latitude"));
			lon = Double.parseDouble(jsonObject1.getString("longitude"));

			Log.e("json", lat + "");

			loInfo = new LocationInfo(lat, lon);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("tag", "gsm  出了异常");
		}
		return loInfo;
	}

}

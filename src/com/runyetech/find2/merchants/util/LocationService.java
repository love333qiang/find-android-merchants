package com.runyetech.find2.merchants.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService {

	public static Location getMyLocation(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				return location;
			}
		} else {
			LocationListener locationListener = new LocationListener() {
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				}

				public void onProviderEnabled(String provider) {
					// Provider被enable时触发此函数，比如GPS被打开
				}

				public void onProviderDisabled(String provider) {
					// Provider被disable时触发此函数，比如GPS被关闭
				}

				public void onLocationChanged(Location location) {
					// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
				}
			};
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				return location;
			}
		}
		return null;
	}
}

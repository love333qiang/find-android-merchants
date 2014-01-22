package com.runyetech.find2.merchants;

import org.json.JSONObject;

import android.app.Application;

public class Find2MerchantsApplication extends Application {

	public class MerchantInformation {
		String userName;
		String userPswd;
	}

	private static Find2MerchantsApplication mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	/** get the instance of Find2MerchantsApplication */
	public static Find2MerchantsApplication getInstance() {
		return mInstance;
	}

	public MerchantInformation getUserInformation() {
		MerchantInformation merchantinfo = new MerchantInformation();
		return merchantinfo;
	}

	public void saveUserInformation(MerchantInformation merchantInfo) {

	}

	public void saveUserInformation(JSONObject response) {

	}
}

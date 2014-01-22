package com.runyetech.find2.merchants.webservice;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Find2MerchantsWebService {
	
	private static class Find2MerchantsHolder{
		private static final Find2MerchantsWebService mInstance = new Find2MerchantsWebService();
	}
	
	public static Find2MerchantsWebService getInstance(){
		return Find2MerchantsHolder.mInstance;
	}
	
	//TODO: add webservice
	public void requestLogin(String userName, String password, JsonHttpResponseHandler handler){
		
	}
}

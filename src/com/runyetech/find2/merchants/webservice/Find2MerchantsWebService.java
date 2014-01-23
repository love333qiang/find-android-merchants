package com.runyetech.find2.merchants.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.runyetech.find2.merchants.util.URLPathUtil;

public class Find2MerchantsWebService {

	private static class Find2MerchantsHolder {
		private static final Find2MerchantsWebService mInstance = new Find2MerchantsWebService();
	}

	public static Find2MerchantsWebService getInstance() {
		return Find2MerchantsHolder.mInstance;
	}

	/**
	 * @param loginName
	 *            登录名
	 * @param loginPswd
	 *            密码
	 * @param handler
	 */
	public void requestLogin(String loginName, String loginPswd, JsonHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("email", loginName);
		params.add("password", loginPswd);
		client.post(URLPathUtil.LOGIN_URLPAHT, params, responseHandler);
	}
}

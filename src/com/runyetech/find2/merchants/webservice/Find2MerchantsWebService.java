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
	 *            回调接口，处理数据
	 */
	public void requestLogin(RequestParams params, JsonHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Authorization", "Basic YW5kcm9pZENsaWVudDpOZXB0dW5l'");
		client.post(URLPathUtil.LOGIN_URLPAHT, params, responseHandler);
	}

	/**
	 * 商户注册
	 * 
	 * @param params
	 *            商户注册时的参数列表
	 * @param responseHandler
	 *            回调接口，处理数据
	 */
	public void requestRegister(RequestParams params, JsonHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(URLPathUtil.REGISTER_URLPATH, params, responseHandler);
	}

	/**
	 * 添加商品
	 * 
	 * @param params
	 *            商品所有参数的参数列表
	 * @param responseHandler
	 *            回调接口，处理数据
	 */
	public void requestPublisherGoods(RequestParams params, JsonHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post("", params, responseHandler);
	}
}

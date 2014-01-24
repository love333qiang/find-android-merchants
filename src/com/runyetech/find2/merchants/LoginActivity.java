package com.runyetech.find2.merchants;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.runyetech.find2.merchants.util.LogInfoPrint;
import com.runyetech.find2.merchants.util.LogInfoToast;
import com.runyetech.find2.merchants.webservice.Find2MerchantsWebService;
import com.runyetech.find2_android_merchants.R;

/**
 * @author YangFan 登录界面
 * 
 */
public class LoginActivity extends Activity {

	/** 输入用户名 */
	private EditText editText_UserName;
	/** 输入用户密码 */
	private EditText editText_UserPswd;
	/** 点击：登录按钮 */
	private Button button_Login;
	/** 点击：注入按钮 */
	private Button button_Register;
	/** 按键监听类 */
	private ClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initUI();
	}

	/**
	 * 初始化控件
	 */
	private void initUI() {
		listener = new ClickListener();

		button_Login = (Button) findViewById(R.id.activity_login_button_Login);
		button_Login.setOnClickListener(listener);

		button_Register = (Button) findViewById(R.id.activity_login_button_Register);
		button_Register.setOnClickListener(listener);

		editText_UserName = (EditText) findViewById(R.id.activity_login_editText_UserName);
		editText_UserPswd = (EditText) findViewById(R.id.activity_login_editText_UserPswd);
	}

	/**
	 * @author YangFan 按键监听类
	 * 
	 */
	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_login_button_Login:
				button_Login_ClickListener();
				break;
			case R.id.activity_login_button_Register:
				button_Register_ClickListener();
				break;
			}
		}
	}

	/**
	 * 登录按钮的事件处理
	 */
	private void button_Login_ClickListener() {
		String userName, userPswd;
		userName = editText_UserName.getText().toString().trim();
		userPswd = editText_UserPswd.getText().toString().trim();
		if (userName.length() > 0 && userPswd.length() > 0) {
			doLogin(userName, userPswd);
		} else {
			LogInfoToast.showToast(true, this, "用户名或者密码不能为空");
		}
	}

	/**
	 * 注册按钮的事件处理
	 */
	private void button_Register_ClickListener() {
		startActivity(new Intent(this, RegisterActivity.class));
	}

	/**
	 * 执行登录
	 * 
	 * @param logingName
	 *            用户登录名
	 * @param loginPswd
	 *            用户密码
	 */
	private void doLogin(String logingName, String loginPswd) {
		Find2MerchantsWebService.getInstance().requestLogin(logingName, loginPswd, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, org.json.JSONObject response) {
				dealwithSuccessJsonResponse(response);
				LogInfoPrint.i(true, "登录成功");
			}

			@Override
			public void onFailure(java.lang.Throwable e, org.json.JSONObject errorResponse) {
				dealWithFailedJsonResponse(errorResponse);
				LogInfoPrint.w(true, "登录失败");
			}
		});
	}

	/**
	 * 登录成功的事件处理
	 * 
	 * @param response
	 */
	private void dealwithSuccessJsonResponse(JSONObject response) {
		try {
			if (response.getBoolean("succ")) {
				Find2MerchantsApplication.getInstance().saveUserInformation(response);
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(this, getString(R.string.activity_login_toast_login_error), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录失败的事件处理
	 * 
	 * @param response
	 */
	private void dealWithFailedJsonResponse(JSONObject response) {
		Toast.makeText(this, getString(R.string.activity_login_toast_network_error), Toast.LENGTH_SHORT).show();
	}
}

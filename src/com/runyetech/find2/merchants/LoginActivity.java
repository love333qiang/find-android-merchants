package com.runyetech.find2.merchants;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.runyetech.find2.merchants.webservice.Find2MerchantsWebService;
import com.runyetech.find2_android_merchants.R;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// add listeners
		addListeners();
	}

	private String getLoginName() {
		EditText textLoginName = (EditText) findViewById(R.id.activityloginemail);
		return textLoginName.getText().toString();
	}

	private String getLoginPassword() {
		EditText textpassword = (EditText) findViewById(R.id.activityloginpassword);
		return textpassword.getText().toString();
	}

	private void addListeners() {

		// add password input finished listener
		EditText passwordText = (EditText) findViewById(R.id.activityloginpassword);
		passwordText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					//
					String loginName = getLoginName();
					String loginPassword = getLoginPassword();
					if (loginName.length() > 0 && loginPassword.length() > 0) {
						doLogin(loginName, loginPassword);
						return true;
					}
				}
				return false;
			}
		});

		// add login btn listener
		findViewById(R.id.activityloginloginbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String loginname = getLoginName();
				String loginpassword = getLoginPassword();
				int resid = -1;
				if (loginname.length() <= 0) {
					resid = R.string.activity_login_toast_emptyusername;
				} else if (loginpassword.length() <= 0) {
					resid = R.string.activiry_login_toast_emptypassword;
				}

				if (resid != -1) {
					Toast.makeText(LoginActivity.this, getString(resid), Toast.LENGTH_SHORT).show();
					return;
				}

				// do log in
				doLogin(loginname, loginpassword);
			}
		});

		// add register btn
		findViewById(R.id.activityloginregisterbtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

				// add extra data to intent
				intent.putExtra("loginname", getLoginName());
				intent.putExtra("loginpassword", getLoginPassword());

				startActivity(intent);
			}
		});
	}

	private void doLogin(String loginname, String loginpassword) {
		Find2MerchantsWebService.getInstance().requestLogin(loginname, loginpassword, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, org.json.JSONObject response) {
				dealwithSuccessJsonResponse(response);
			}

			@Override
			public void onFailure(java.lang.Throwable e, org.json.JSONObject errorResponse) {
				dealWithFailedJsonResponse(errorResponse);
			}
		});
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dealWithFailedJsonResponse(JSONObject response) {
		Toast.makeText(this, getString(R.string.activity_login_toast_network_error), Toast.LENGTH_SHORT).show();
	}
}

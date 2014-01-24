package com.runyetech.find2.merchants;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.runyetech.find2.merchants.webservice.Find2MerchantsWebService;
import com.runyetech.find2_android_merchants.R;

/**
 * @author YangFan 商户注册界面
 * 
 */
public class RegisterActivity extends Activity {

	/** 第一个注册页面 */
	private LinearLayout layout_Register_First;
	/** 第二个注册页面 */
	private LinearLayout layout_Register_Second;
	/** 商户名称 */
	private EditText editText_NickName;
	/** 邮箱 */
	private EditText editText_Email;
	/** 手机、电话号码 */
	private EditText editText_PhoneFirst;
	/** 紧急联系人手机、电话号码 */
	private EditText editText_PhoneSceond;
	/** 密码 */
	private EditText editText_Pswd;
	/** 确认密码 */
	private EditText editText_VerifyPswd;
	/** 营销代码 */
	private EditText editText_MarketingCode;
	/** EditText数组，在点击下一步按钮判断值是否为空 */
	private EditText[] editText_FirstReg;
	/** 公司名称 */
	private EditText editText_CompayName;
	/** 企业名称 */
	private EditText editText_FirmName;
	/** 营业执照所在地 */
	private EditText editText_License_Address;
	/** 营业期限 */
	private EditText editText_Deadline;
	/** 店铺地址 */
	private EditText editText_MarketAddress;
	/** 营业执照副本扫描件 */
	private EditText editText_LicensePic;
	/** 提交注册按钮 */
	private Button button_Register_Submit;
	/** 按键事件监听代码 */
	private ClickListener listener;

	/** 注册页面的参数列表 */
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initUI();
	}

	/**
	 * 实例化界面组件
	 */
	private void initUI() {

		listener = new ClickListener();

		/** 第一个注册页面的控件的实例化，第一个注册页面默认显示 */
		layout_Register_First = (LinearLayout) findViewById(R.id.layout_Register_First);
		layout_Register_First.setVisibility(View.VISIBLE);

		editText_FirstReg = new EditText[13];

		editText_NickName = (EditText) findViewById(R.id.merchants_Register_NickName);
		editText_Email = (EditText) findViewById(R.id.merchants_Register_Email);
		editText_PhoneFirst = (EditText) findViewById(R.id.merchants_Register_Phone_First);
		editText_PhoneSceond = (EditText) findViewById(R.id.merchants_Register_Phone_Sceond);
		editText_Pswd = (EditText) findViewById(R.id.merchants_Register_Pswd);
		editText_VerifyPswd = (EditText) findViewById(R.id.merchants_Register_VerifyPswd);
		editText_MarketingCode = (EditText) findViewById(R.id.merchants_Register_MarketingCode);

		editText_FirstReg[0] = editText_NickName;
		editText_FirstReg[1] = editText_Email;
		editText_FirstReg[2] = editText_PhoneFirst;
		editText_FirstReg[3] = editText_PhoneSceond;
		editText_FirstReg[4] = editText_Pswd;
		editText_FirstReg[5] = editText_VerifyPswd;
		editText_FirstReg[6] = editText_MarketingCode;

		/** 第二个注册页面的控件的实例化 ，第二个注册页面默认不现实 */
		layout_Register_Second = (LinearLayout) findViewById(R.id.layout_Register_sceond);
		layout_Register_Second.setVisibility(View.VISIBLE);

		editText_CompayName = (EditText) findViewById(R.id.merchants_Register_ComPanyName);
		editText_FirmName = (EditText) findViewById(R.id.merchants_Register_FirmName);
		editText_License_Address = (EditText) findViewById(R.id.merchants_Register_License_Address);
		editText_Deadline = (EditText) findViewById(R.id.merchants_Register_Phone_BusinessDeadline);
		editText_MarketAddress = (EditText) findViewById(R.id.merchants_Register_MarketAddress);
		editText_LicensePic = (EditText) findViewById(R.id.merchants_Register_LicensePic);

		editText_FirstReg[7] = editText_CompayName;
		editText_FirstReg[8] = editText_FirmName;
		editText_FirstReg[9] = editText_License_Address;
		editText_FirstReg[10] = editText_Deadline;
		editText_FirstReg[11] = editText_MarketAddress;
		editText_FirstReg[12] = editText_LicensePic;

		button_Register_Submit = (Button) findViewById(R.id.register_Submin);
		button_Register_Submit.setOnClickListener(listener);
	}

	/**
	 * 提交注册
	 */
	private void click_Button_Register_Submit() {
		checkRegisterInfo(editText_FirstReg);
		doRegister();
	}

	/**
	 * 检查商户输入的信息是否为空
	 * 
	 * @param editText
	 *            输入信息的控件
	 */

	private void checkRegisterInfo(EditText[] editText) {
		params = new RequestParams();
		for (int i = 0; i < editText.length; i++) {
			String registerInfo = editText[i].getText().toString().trim();
			if (registerInfo.length() > 0) {
				params.add("i", registerInfo);
			} else {
				editText[i].setError("内容不能为空");
				return;
			}
		}
	}

	private void doRegister() {
		Find2MerchantsWebService.getInstance().requestRegister(params, new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, JSONObject response) {
				super.onSuccess(statusCode, response);
				startActivity(new Intent(RegisterActivity.this, MainActivity.class));
			}

			public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
				super.onFailure(statusCode, e, errorResponse);
			}
		});
	}

	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_Submin:
				click_Button_Register_Submit();
				break;
			}
		}
	}
}

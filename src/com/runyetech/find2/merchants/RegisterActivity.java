package com.runyetech.find2.merchants;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.runyetech.find2.merchants.util.MyDailog;
import com.runyetech.find2.merchants.util.PhotosUtil;
import com.runyetech.find2.merchants.webservice.Find2MerchantsWebService;
import com.runyetech.find2_android_merchants.R;

/**
 * @author YangFan 商户注册界面
 * 
 */
public class RegisterActivity extends Activity {
	/**
	 * EditText数组，在点击下一步按钮判断值是否为空
	 * 
	 * 0:商户昵称；1：邮箱；2：手机号码；3：紧急联系人号码；4：密码；5：确认密码；6：营销代码
	 * 
	 * 7：公司名称；8:企业名称；9：营业执照所在地；10：营业截止日期；11：店铺地址
	 * */
	private EditText[] editText_Register;
	/** 图像数组 0：商户头像；1：营业执照扫描件 */
	private ImageView[] imageView_Register;
	/** 选择商户头像的按钮 */
	private Button button_ChoiseUserAvatar;
	/** 选择营业执照的图片的按钮 */
	private Button button_ChooiseLincesePic;
	/** 提交注册按钮 */
	private Button button_Register_Submit;
	/** 按键事件监听代码 */
	private ClickListener listener;
	/** 要为图片数组中的第几个图片赋值 */
	private int whichPic;
	/** 保存日期选择控件的年月日值 */
	private int mYear, mMonth, mDay;
	/** 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID： */
	private static final int DATE_DIALOG_ID = 0;
	/** 注册页面的参数列表 */
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initUI();
	}

	/** 实例化界面组件 */
	private void initUI() {
		listener = new ClickListener();

		editText_Register = new EditText[12];
		imageView_Register = new ImageView[2];

		editText_Register[0] = (EditText) findViewById(R.id.merchants_Register_NickName);
		editText_Register[1] = (EditText) findViewById(R.id.merchants_Register_Email);
		editText_Register[2] = (EditText) findViewById(R.id.merchants_Register_Phone_First);
		editText_Register[3] = (EditText) findViewById(R.id.merchants_Register_Phone_Sceond);
		editText_Register[4] = (EditText) findViewById(R.id.merchants_Register_Pswd);
		editText_Register[5] = (EditText) findViewById(R.id.merchants_Register_VerifyPswd);
		editText_Register[6] = (EditText) findViewById(R.id.merchants_Register_MarketingCode);
		editText_Register[7] = (EditText) findViewById(R.id.merchants_Register_ComPanyName);
		editText_Register[8] = (EditText) findViewById(R.id.merchants_Register_FirmName);
		editText_Register[9] = (EditText) findViewById(R.id.merchants_Register_License_Address);
		editText_Register[10] = (EditText) findViewById(R.id.merchants_Register_BusinessDeadline);
		editText_Register[11] = (EditText) findViewById(R.id.merchants_Register_MarketAddress);

		imageView_Register[0] = (ImageView) findViewById(R.id.merchants_Register_UserAvater);
		imageView_Register[1] = (ImageView) findViewById(R.id.merchants_Register_LicensePic);

		button_ChoiseUserAvatar = (Button) findViewById(R.id.merchants_Register_ChooiseUserAvatar);
		button_ChoiseUserAvatar.setOnClickListener(listener);

		button_ChooiseLincesePic = (Button) findViewById(R.id.merchants_Register_ChooiseLicensePic);
		button_ChooiseLincesePic.setOnClickListener(listener);

		button_Register_Submit = (Button) findViewById(R.id.register_Submin);
		button_Register_Submit.setOnClickListener(listener);

		setDataPicker();
	}

	/** 提交注册:如果所有的都不为空，则弹出对话框 */
	private void click_Button_Register_Submit() {
		if (checkRegisterInfo(editText_Register)) {
			createRegisterMsgDialog();
		}
	}

	/** 设置DataPicker的初始时间 */
	private void setDataPicker() {
		final Calendar currentDate = Calendar.getInstance();// 获得当前的日期
		mYear = currentDate.get(Calendar.YEAR);
		mMonth = currentDate.get(Calendar.MONTH);
		mDay = currentDate.get(Calendar.DAY_OF_MONTH);
		StringBuilder sb = new StringBuilder();
		sb.append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日");
		editText_Register[10].setText(sb.toString());// 设置日期
	}

	/**
	 * 检查商户输入的信息是否为空
	 * 
	 * @param editText
	 *            输入信息的控件
	 */
	private boolean checkRegisterInfo(EditText[] editText) {
		params = new RequestParams();
		for (int i = 0; i < editText.length; i++) {
			String registerInfo = editText[i].getText().toString().trim();
			if (registerInfo.length() > 0) {
				params.add("i", registerInfo);
			} else {
				editText[i].setError("内容不能为空");
				return false;
			}
		}
		params.put("", imageView_Register[0].getDrawable());
		params.put("", imageView_Register[1].getDrawable());
		// params.put("", LocationService.getLocation(this).getLongitude());//
		// 经度
		// params.put("", LocationService.getLocation(this).getLatitude());// 纬度
		return true;
	}

	/** 注册 */
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

	/** 提交注册前的确认对话框 */
	private void createRegisterMsgDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("确认信息无误后，点击确定");
		builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				doRegister();
			}
		});
		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			Bitmap bmp = PhotosUtil.onActivityResultAlbum(requestCode, resultCode, data, this);
			imageView_Register[whichPic].setImageBitmap(bmp);
		} else if (requestCode == 1) {
			Bitmap bmp = PhotosUtil.onActivityResultCamera(requestCode, resultCode, data, this);
			imageView_Register[whichPic].setImageBitmap(bmp);
		}
	}

	/**
	 * @author YangFan 按键监听
	 * 
	 */
	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_Submin:
				click_Button_Register_Submit();
				break;
			case R.id.merchants_Register_ChooiseUserAvatar:
				whichPic = 0;
				createChoisePicTypeDialog();
				break;
			case R.id.merchants_Register_ChooiseLicensePic:
				whichPic = 1;
				createChoisePicTypeDialog();
				break;
			case R.id.merchants_Register_BusinessDeadline:
				showDialog(DATE_DIALOG_ID);// 弹出日期选择控件
				break;
			}
		}
	}

	/** 图片选取方式的对话框 */
	private void createChoisePicTypeDialog() {
		String title = getResources().getString(R.string.goods_choiseMsg);
		String[] type = getResources().getStringArray(R.array.goods_choisePicType);
		MyDailog.cretaeChoisePicItemDialog(RegisterActivity.this, title, type);
	}

	/** 需要定义弹出的DatePicker对话框的事件监听器： */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			StringBuilder sb = new StringBuilder();
			sb.append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日");
			editText_Register[10].setText(new StringBuilder());// 设置文本的内容：
		}
	};

	/** 当Activity调用showDialog函数时会触发该函数的调用 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
}

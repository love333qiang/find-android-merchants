package com.runyetech.find2.merchants;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.runyetech.find2.merchants.util.MyDailog;
import com.runyetech.find2.merchants.util.PhotosUtil;
import com.runyetech.find2_android_merchants.R;

public class GoodsAddActivity extends Activity {
	/**
	 * 输入商品信息的EditText数组
	 * 
	 * 0:商品的品牌 ;1：名称；；2：一口价
	 * 
	 * 3：原价；4：净含量；5：现有数量；6：描述
	 * */
	private EditText[] editText_GoodsInfo;
	/** 商品包装方式 */
	private TextView textView_goods_ParkType;
	/** 商品生产日期 */
	private TextView textView_goods_ProduceData;
	/** 商品的展示图片的数组 */
	private ImageView[] imageView_goodsPic;
	/** 选择商品图片的按钮数组 */
	private Button[] button_goodsChoiseGoodsPic;
	/** 是否有发票的RadioGroup */
	private RadioGroup radioGroup_goodsInvoice;
	/** 是否有保修的RadioGroup */
	private RadioGroup radioGroup_goodsRepair;
	/** 退换货承诺的CheckBox */
	private CheckBox checkBox_goodsPromise;
	/** 发布商品 */
	private Button button_goodsPubliser;
	/** 选择商品的生产时间 */
	private Button button_goodsProduceData;
	/** 是否有发票 */
	private Boolean isHasInvoice;
	/** 是否保修 */
	private Boolean isHasRepair;
	/** 商品所有参数 */
	private RequestParams params;
	/** API参数 */
	private String[] apiParams;
	/** 当前是给哪一个ImageView选取图片 */
	private int whichPic = 0;
	/** 按键事件监听类 */
	private ClickListener listener;
	/** 长按事件监听类 */
	private LongClickListener longListener;
	/** 选取状态变更的事件监听类 */
	private CheckedChangeListener checkedChangeListener;
	/** 保存日期选择控件的年月日值 */
	private int mYear, mMonth, mDay;
	/** 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID： */
	private static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsaddtest);

		initUI();
	}

	private void initUI() {

		isHasInvoice = true;
		isHasRepair = true;

		apiParams = new String[9];
		listener = new ClickListener();
		longListener = new LongClickListener();
		checkedChangeListener = new CheckedChangeListener();

		textView_goods_ParkType = (TextView) findViewById(R.id.goods_ParkType);
		textView_goods_ParkType.setOnClickListener(listener);

		textView_goods_ProduceData = (TextView) findViewById(R.id.goods_ProduceData);
		textView_goods_ProduceData.setOnClickListener(listener);

		editText_GoodsInfo = new EditText[9];
		editText_GoodsInfo[0] = (EditText) findViewById(R.id.goods_Brand);
		editText_GoodsInfo[1] = (EditText) findViewById(R.id.goods_Name);
		editText_GoodsInfo[2] = (EditText) findViewById(R.id.goods_FixedPrice);
		editText_GoodsInfo[3] = (EditText) findViewById(R.id.goods_OriginalPrice);
		editText_GoodsInfo[4] = (EditText) findViewById(R.id.goods_NetWeight);
		editText_GoodsInfo[5] = (EditText) findViewById(R.id.goods_Counts);
		editText_GoodsInfo[6] = (EditText) findViewById(R.id.goods_Direction);

		imageView_goodsPic = new ImageView[3];
		imageView_goodsPic[0] = (ImageView) findViewById(R.id.goods_pic_0);
		imageView_goodsPic[1] = (ImageView) findViewById(R.id.goods_pic_1);
		imageView_goodsPic[2] = (ImageView) findViewById(R.id.goods_pic_2);

		imageView_goodsPic[0].setOnLongClickListener(longListener);
		imageView_goodsPic[1].setOnLongClickListener(longListener);
		imageView_goodsPic[2].setOnLongClickListener(longListener);

		button_goodsProduceData = (Button) findViewById(R.id.button_goodsProduceData);
		button_goodsProduceData.setOnClickListener(listener);

		button_goodsChoiseGoodsPic = new Button[3];
		button_goodsChoiseGoodsPic[0] = (Button) findViewById(R.id.goods_ChoiseGoodsPic_0);
		button_goodsChoiseGoodsPic[0].setOnClickListener(listener);
		button_goodsChoiseGoodsPic[1] = (Button) findViewById(R.id.goods_ChoiseGoodsPic_1);
		button_goodsChoiseGoodsPic[1].setOnClickListener(listener);
		button_goodsChoiseGoodsPic[2] = (Button) findViewById(R.id.goods_ChoiseGoodsPic_2);
		button_goodsChoiseGoodsPic[2].setOnClickListener(listener);

		button_goodsPubliser = (Button) findViewById(R.id.goods_PublisherGoods);
		button_goodsPubliser.setOnClickListener(listener);

		radioGroup_goodsInvoice = (RadioGroup) findViewById(R.id.goods_Invoice);
		radioGroup_goodsInvoice.setOnCheckedChangeListener(checkedChangeListener);

		radioGroup_goodsRepair = (RadioGroup) findViewById(R.id.goods_Repair);
		radioGroup_goodsRepair.setOnCheckedChangeListener(checkedChangeListener);

		checkBox_goodsPromise = (CheckBox) findViewById(R.id.goods_Promise);

		// 获得当前的日期：
		final Calendar currentDate = Calendar.getInstance();
		mYear = currentDate.get(Calendar.YEAR);
		mMonth = currentDate.get(Calendar.MONTH);
		mDay = currentDate.get(Calendar.DAY_OF_MONTH);
		StringBuilder sb = new StringBuilder();
		sb.append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日");
		textView_goods_ProduceData.setText(sb.toString());
	}

	/**
	 * 检查所有信息是否输入有误
	 * 
	 * @return 是否输入正确，true：正确，false：错误
	 */
	private boolean checkGoodsInfo() {
		if (checkGoodsInfo(editText_GoodsInfo) && checkGoodsInfo(imageView_goodsPic)) {
			params.put("", isHasInvoice);
			params.put("", isHasRepair);
			params.put("", checkBox_goodsPromise.isChecked());
			return true;
		}
		return false;
	}

	/**
	 * 检查商品的输入信息
	 * 
	 * @param editText
	 *            商品信息输入框
	 * @return 是否符合要求
	 */
	private boolean checkGoodsInfo(EditText[] editText) {
		for (int i = 0; i < editText.length; i++) {
			String goodsInfo = editText[i].getText().toString().trim();
			if (goodsInfo.length() > 0) {
				params.put(apiParams[i], goodsInfo);
			} else {
				editText[i].setError("信息不能为空");
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查商品的图片
	 * 
	 * @param imageView
	 *            商品图片
	 * @return 是否符合要求
	 */
	private boolean checkGoodsInfo(ImageView[] imageView) {
		// 如果是默认的图片，就不去上传
		for (int i = 0; i < imageView.length; i++) {
			String temp_description = getResources().getString(R.string.goods_pic_defaultdescription);
			if (imageView[i].getContentDescription().equals(temp_description)) {
			} else {
				params.put("" + i, "");
			}
		}
		return false;
	}

	/** 图片选取方式的对话框 */
	private void createDialog() {
		String title = getResources().getString(R.string.goods_choiseMsg);
		String[] type = getResources().getStringArray(R.array.goods_choisePicType);
		MyDailog.cretaeChoisePicItemDialog(GoodsAddActivity.this, title, type);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap temp_goodsPic = null;
		if (requestCode == 0) {
			temp_goodsPic = PhotosUtil.onActivityResultAlbum(requestCode, resultCode, data, this);
			if (temp_goodsPic != null) {
				imageView_goodsPic[whichPic].setImageBitmap(temp_goodsPic);
				imageView_goodsPic[whichPic].setContentDescription("图库选取");
			}
		} else if (requestCode == 1) {
			temp_goodsPic = PhotosUtil.onActivityResultCamera(requestCode, resultCode, data, this);
			if (temp_goodsPic != null) {
				imageView_goodsPic[whichPic].setImageBitmap(temp_goodsPic);
				imageView_goodsPic[whichPic].setContentDescription("相机拍摄");
			}
		}
	}

	/**
	 * @author YangFan 控件单击的事件监听类
	 */
	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_goodsProduceData:
				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.goods_ProduceData:
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.goods_ChoiseGoodsPic_0:
				createDialog();
				whichPic = 0;
				break;
			case R.id.goods_ChoiseGoodsPic_1:
				createDialog();
				whichPic = 1;
				break;
			case R.id.goods_ChoiseGoodsPic_2:
				createDialog();
				whichPic = 2;
				break;
			case R.id.goods_PublisherGoods:
				params = new RequestParams();
				if (checkGoodsInfo()) {

				}
				break;
			}
		}
	}

	/**
	 * @author YangFan 商品显示图片长按的事件监听类
	 */
	class LongClickListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			switch (v.getId()) {
			case R.id.goods_pic_0:
				createDialog();
				whichPic = 0;
				break;
			case R.id.goods_pic_1:
				createDialog();
				whichPic = 1;
				break;
			case R.id.goods_pic_2:
				createDialog();
				whichPic = 2;
				break;
			}
			return false;
		}
	}

	/**
	 * @author YangFan RadioGroup的选取状态变更监听类RadioGroup的选取状态变更监听类
	 */
	class CheckedChangeListener implements OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (group.getId() == R.id.goods_Invoice) {
				if (checkedId == R.id.goods_HasInvoice) {
					isHasInvoice = true;
				} else if (checkedId == R.id.goods_NoInvoice) {
					isHasInvoice = false;
				}
				System.out.println("是否有发票：" + isHasInvoice);
			} else if (group.getId() == R.id.goods_Repair) {
				if (checkedId == R.id.goods_HasRepair) {
					isHasRepair = true;
				} else if (checkedId == R.id.goods_NoRepair) {
					isHasRepair = false;
				}
				System.out.println("是否有保修：" + isHasRepair);
			}
		}
	}

	/** 需要定义弹出的DatePicker对话框的事件监听器 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			StringBuilder sb = new StringBuilder();
			sb.append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日");
			textView_goods_ProduceData.setText(sb.toString());
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

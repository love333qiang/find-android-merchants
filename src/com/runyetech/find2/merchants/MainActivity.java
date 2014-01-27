package com.runyetech.find2.merchants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.runyetech.find2_android_merchants.R;

/**
 * @author YangFan
 * 
 *         商户登录成功以后的页面
 * 
 */
public class MainActivity extends Activity {

	/** 添加商品的按钮 */
	private Button button_AddGoods;
	/** 按键监听类 */
	private ClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
	}

	/**
	 * 控件初始化
	 */
	private void initUI() {
		listener = new ClickListener();

		button_AddGoods = (Button) findViewById(R.id.addGoods);
		button_AddGoods.setOnClickListener(listener);
	}

	/**
	 * @author YangFan 按键监听
	 * 
	 */
	private class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addGoods:
				Intent intent = new Intent(MainActivity.this, GoodsAddActivity.class);
				startActivity(intent);
				break;
			}
		}
	}
}

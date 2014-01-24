package com.runyetech.find2.merchants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.runyetech.find2_android_merchants.R;

/**
 * @author YangFan 添加商品
 * 
 */
public class GoodsCategoryActivity extends Activity {

	/** 搜索商品类目的关键字的输入框 */
	private EditText editText_SearchWords;
	/** 选择商品类目的关键字的输入框 */
	private EditText editText_ChoiseWords;
	/** 快速找到商品类目的按钮 */
	private Button button_SearchGo;
	/** 发布商品的按钮 */
	private Button button_PublisherGoods;

	/** 按键事件监听类 */
	private ClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodscategory);

		initUI();
	}

	private void initUI() {

		listener = new ClickListener();

		editText_ChoiseWords = (EditText) findViewById(R.id.addGoods_ChoiseWords);
		editText_SearchWords = (EditText) findViewById(R.id.addGoods_SearchWorlds);
		button_PublisherGoods = (Button) findViewById(R.id.addGoods_PublisherGoods);
		button_PublisherGoods.setOnClickListener(listener);
		button_SearchGo = (Button) findViewById(R.id.addGoods_SearchGo);
		button_SearchGo.setOnClickListener(listener);
	}

	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addGoods_PublisherGoods:
				Intent intent = new Intent(GoodsCategoryActivity.this, GoodsAddActivity.class);
				GoodsCategoryActivity.this.startActivity(intent);
				break;
			case R.id.addGoods_SearchGo:
				break;
			}
		}
	}
}

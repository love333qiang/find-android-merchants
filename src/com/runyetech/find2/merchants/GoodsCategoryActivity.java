package com.runyetech.find2.merchants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.runyetech.find2.merchants.util.LogInfoPrint;
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
	/** 选择商品分类的Pop */
	private PopupWindow window;
	/** 商品分类的数组 */
	private String[] item;
	/** 按键事件监听类 */
	private ClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodscategory);

		initUI();
	}

	/**
	 * 初始化UI布局控件
	 */
	private void initUI() {

		item = getResources().getStringArray(R.array.goods_category);

		listener = new ClickListener();

		editText_ChoiseWords = (EditText) findViewById(R.id.addGoods_ChoiseWords);
		editText_ChoiseWords.setOnClickListener(listener);
		editText_SearchWords = (EditText) findViewById(R.id.addGoods_SearchWorlds);
		button_PublisherGoods = (Button) findViewById(R.id.addGoods_PublisherGoods);
		button_PublisherGoods.setOnClickListener(listener);
		button_SearchGo = (Button) findViewById(R.id.addGoods_SearchGo);
		button_SearchGo.setOnClickListener(listener);
	}

	/**
	 * @author YangFan 按键监听类
	 * 
	 */
	class ClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addGoods_PublisherGoods:
				Intent intent = new Intent(GoodsCategoryActivity.this, GoodsAddActivity.class);
				GoodsCategoryActivity.this.startActivity(intent);
				break;
			case R.id.addGoods_SearchGo:
				break;
			case R.id.addGoods_ChoiseWords:
				createPopup(v);
				break;
			}
		}
	}

	/**
	 * 根据父控件的位置创建一个PopupWindow
	 * 
	 * @param parent
	 *            父控件
	 */
	@SuppressWarnings("deprecation")
	private void createPopup(View parent) {
		if (window == null) {
			LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = lay.inflate(R.layout.popup_goodscategory, null);
			v.setBackgroundColor(Color.BLUE);

			ListView listView_Catgegory = (ListView) v.findViewById(R.id.goods_category);
			listView_Catgegory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item));
			listView_Catgegory.setOnItemClickListener(new ItemClickListener());

			window = new PopupWindow(v, 200, 350);
			window.setFocusable(true);
			window.setBackgroundDrawable(new BitmapDrawable());
			window.setOutsideTouchable(true);
			window.update();
		}
		showPop(parent);
	}

	/**
	 * 显示或者隐藏PopUpWindow
	 * 
	 * 如果显示就隐藏了，如果隐藏就根据父控件的位置去显示
	 * 
	 * @param parent
	 *            父控件
	 */
	private void showPop(View parent) {
		if (window.isShowing()) {
			window.dismiss();
		} else {
			window.showAsDropDown(parent);
		}
	}

	/**
	 * @author YangFan ListView的Item的点击事件监听
	 */
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			editText_ChoiseWords.setText(item[position]);
			LogInfoPrint.i(true, "您当前选择的是：" + item[position]);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		window = null;// 退出是让PopUp为NULL；
	}
}

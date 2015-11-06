package com.mysteel.banksteel.view.activity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.OrderDetailData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 订单被取消了跳转进来的详细页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-27 下午6:55:40
 */
public class OrderStopActivity extends BaseActivity implements OnClickListener,
		IOrderTradeView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "OrderStopActivity";
	private LinearLayout ll_people_match;// 人工布局页面
	private CircleImageView circleimageview;// 人工头像
	private TextView tv_view_name;// 业务员姓名
	private TextView tv_view_times;// 被赞次数
	private View view01;// 业务员头像下的布局

	private TextView goodname;// 父品种
	private TextView tv_varieties;// 品种
	private TextView tv_material;// 材质
	private TextView tv_specifi;// 规格
	private TextView tv_brand;// 产地
	private TextView tv_city;// 求购城市
	private TextView tv_number;// 求购数量
	private TextView tv_date;// 底部时间显示
	private LinearLayout ll_img_callphone;// 电话图标

	private String orderId = "";
	private IOrderTrade orderTrade;
	private OrderDetailData OData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_cancle);
		Bundle b = getIntent().getExtras();
		orderId = b.getString("orderId");
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		tvRightText.setVisibility(View.GONE);
		tvTitle.setText("");// 标题显示时间
		ll_people_match = (LinearLayout) findViewById(R.id.ll_people_match);
		circleimageview = (CircleImageView) findViewById(R.id.circleimageview);
		tv_view_name = (TextView) findViewById(R.id.tv_view_name);
		tv_view_times = (TextView) findViewById(R.id.tv_view_times);
		view01 = findViewById(R.id.view01);
		goodname = (TextView) findViewById(R.id.goodname);
		tv_varieties = (TextView) findViewById(R.id.tv_varieties);
		tv_material = (TextView) findViewById(R.id.tv_material);
		tv_specifi = (TextView) findViewById(R.id.tv_specifi);
		tv_brand = (TextView) findViewById(R.id.tv_brand);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_date = (TextView) findViewById(R.id.tv_date);
		ll_img_callphone = (LinearLayout) findViewById(R.id.ll_img_callphone);
		ll_img_callphone.setOnClickListener(this);

		orderTrade = new OrderTradeImpl(this, this);
		getData();
	}

	/** 获取数据 */
	private void getData()
	{
		String url = RequestUrl.getInstance(this).getUrl_getOrderDetail(this,
				orderId);
		if (DEBUG)
		{
			Log.d(TAG, "请求地址：" + url);
		}
		orderTrade.getOrderDetail(url, Constants.INTERFACE_orderDetail);
	}

	/** 得到数据后，将控件填上数据 */
	private void editView()
	{
		if (OData == null || OData.getData() == null)
		{
			return;
		}
//		Order order = OData.getData().getOrder();
//		if ("1".equals(order.getSource()))
//		{// 成交来源 0-匹配成交 1-人工报价成交 2-买家自行入录
//			ll_people_match.setVisibility(View.VISIBLE);
//			view01.setVisibility(View.VISIBLE);
//			tv_view_name.setText(order.getManagerName());// 业务员姓名
//			tv_view_times.setText("已成交" + order.getDealCount() + "次");
//			showImageByNetworkImageView(order.getManagerPhoto(),
//					circleimageview);
//		}
//		Steel steel = Tools.getSteelMessage(order.getBreedId());// 根据子品种id获取图像资源
//		goodname.setText(steel.getName());
//		tv_varieties.setText(order.getBreed());// 品名
//		tv_material.setText(order.getMaterial());// 材质
//		tv_specifi.setText(order.getSpec());// 规格
//		tv_brand.setText(order.getBrand());// 产地
//		tv_city.setText(order.getCity());// 城市
//		tv_number.setText(order.getQty() + "吨");// 数量
//		tv_date.setText(order.getCancelFormateTime());// 订单取消时间
//		tvTitle.setText(order.getOrderTime());// 标题显示时间

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;
		case R.id.ll_img_callphone:// 电话图标的点击事件
//			if (!TextUtils
//					.isEmpty(OData.getData().getOrder().getManagerPhone()))
//			{
//				Tools.makeCall(this, OData.getData().getOrder()
//						.getManagerPhone());
//			} else
//			{
//				Tools.makeCall(this, "暂时未获取到业务员的联系方式!");
//			}
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		OData = (OrderDetailData) data;
		editView();
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	public void showImageByNetworkImageView(String url, NetworkImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				200);
		ImageCache imageCache = new ImageCache()
		{
			@Override
			public void putBitmap(String key, Bitmap value)
			{
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key)
			{
				return lruCache.get(key);
			}

		};
		ImageLoader imageLoader = new ImageLoader(
				BankSteelApplication.requestQueue, imageCache);
		imgView.setTag(url);

		imgView.setImageUrl(url, imageLoader);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
	}
}

package com.mysteel.banksteel.view.activity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.PublishBuyData;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的求购：待报价状态跳转进来的页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-28 上午10:07:06
 */
public class StayQuoteBuyActivity extends BaseActivity implements
		OnClickListener
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "StayQuoteBuyActivity";
	private CircleImageView circleimageview;// 人工头像
	private TextView tv_view_name;// 业务员姓名
	private TextView tv_view_times;// 被赞次数

	private TextView goodname;// 父品种
	private TextView tv_varieties;// 品种
	private TextView tv_material;// 材质
	private TextView tv_specifi;// 规格
	private TextView tv_brand;// 产地
	private TextView tv_city;// 求购城市
	private TextView tv_number;// 求购数量
	private TextView tv_times;// 底部时间显示
	private LinearLayout ll_img_callphone;// 电话图标

	private PublishBuyData data;
	private TextView tv_wait;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_buyquote);
		Bundle b = getIntent().getExtras();
		data = (PublishBuyData) b.getSerializable("data");
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		tvRightText.setVisibility(View.GONE);
		circleimageview = (CircleImageView) findViewById(R.id.circleimageview);
		tv_view_name = (TextView) findViewById(R.id.tv_view_name);
		tv_view_times = (TextView) findViewById(R.id.tv_view_times);
		goodname = (TextView) findViewById(R.id.goodname);
		tv_varieties = (TextView) findViewById(R.id.tv_varieties);
		tv_material = (TextView) findViewById(R.id.tv_material);
		tv_specifi = (TextView) findViewById(R.id.tv_specifi);
		tv_brand = (TextView) findViewById(R.id.tv_brand);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_times = (TextView) findViewById(R.id.tv_times);
		tv_wait = (TextView) findViewById(R.id.tv_wait);
		ll_img_callphone = (LinearLayout) findViewById(R.id.ll_img_callphone);
		ll_img_callphone.setOnClickListener(this);

		editView();
	}

	/** 更新页面显示的信息 */
	private void editView()
	{
		if (data == null)
		{
			if (DEBUG)
			{
				Log.d(TAG, "数据为空啦");
			}
			return;

		}
		if (DEBUG)
		{
			Log.d(TAG, "业务员头像：" + data.getRushManagerHeader());
			Log.d(TAG, "时间：" + data.getGapTime());
		}
		tv_view_name.setText(data.getRushManagerName());// 业务员姓名
		tv_view_times.setText("已成交" + data.getDealCount() + "次");
		showImageByNetworkImageView(data.getRushManagerHeader(),
				circleimageview);

		Steel steel = Tools.getSteelMessage(data.getBreedId());// 根据子品种id获取图像资源
		goodname.setText(steel.getName());
		tv_varieties.setText(data.getBreed());// 品名
		tv_material.setText(data.getMaterial());// 材质
		tv_specifi.setText(data.getSpec());// 规格
		tv_brand.setText(data.getBrand());// 产地
		tv_city.setText(data.getCity());// 城市
		tv_number.setText(data.getQty() + "吨");// 数量
		if ("刚刚".equals(data.getGapTime()))
		{
			tv_wait.setVisibility(View.GONE);
		} else
		{
			tv_wait.setVisibility(View.VISIBLE);
		}
		tv_times.setText(data.getGapTime());// 时间
		tvTitle.setText(data.getPublishTime());// 标题显示时间

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;
		case R.id.ll_img_callphone:// 电话点击事件
			if (!TextUtils.isEmpty(data.getRushManagerPhone()))
			{
				Tools.makeCall(this, data.getRushManagerPhone());
			} else
			{
				Tools.makeCall(this, "暂时未获取到业务员的联系方式!");
			}
			break;
		}
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
}

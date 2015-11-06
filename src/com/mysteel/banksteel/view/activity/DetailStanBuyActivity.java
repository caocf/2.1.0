package com.mysteel.banksteel.view.activity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DetailStanBuyData;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 求购详情中 已报价跳转进来的页面(有可能求购单已完成用Order无Quot对象信息，已报价状态用有Quot，无Order对象信息)
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-6-4 下午7:24:44
 */
public class DetailStanBuyActivity extends BaseActivity implements
		OnClickListener, IBuyView
{

	/**
	 * LOG SWITCHER.
	 */
	// private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	// private static final String TAG = "DetailStanBuyActivity";
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
	private TextView tv_baojia, tv_shuoming;// 报价,报价前的提示
	private LinearLayout ll_img_callphone, ll_layout_baojia;// 电话图标,报价整体布局

	private String stanBuyId = "";// id
	private String phoneNumber = "";// 业务员联系电话
	String url = "";
	IBuyCenter buyCenter;
	private DetailStanBuyData DetailData;
	private TextView tv_wait;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_buyquote);
		Bundle b = getIntent().getExtras();
		stanBuyId = b.getString("ID");
		phoneNumber = b.getString("phoneNumber");
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
		ll_img_callphone = (LinearLayout) findViewById(R.id.ll_img_callphone);
		ll_img_callphone.setOnClickListener(this);
		ll_layout_baojia = (LinearLayout) findViewById(R.id.ll_layout_baojia);
		ll_layout_baojia.setVisibility(View.VISIBLE);
		tv_baojia = (TextView) findViewById(R.id.tv_baojia);
		tv_wait = (TextView) findViewById(R.id.tv_wait);
		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);

		buyCenter = new BuyImpl(this);
		url = RequestUrl.getInstance(this).getUrl_getDetailStanBuy(this,
				stanBuyId);
		getData();
	}

	/** 拉取数据 */
	private void getData()
	{
		buyCenter.getDetailStanBuy(url, Constants.INTERFACE_detailStanBuy);
	}

	/** 更新页面显示的信息 */
	private void editView()
	{
		if (DetailData.getData().getManager() == null
				|| DetailData.getData().getStanBuy() == null)
		{
			return;
		}
		tv_view_name
				.setText(DetailData.getData().getManager().getManagerName());// 业务员姓名
		tv_view_times.setText("已成交"
				+ DetailData.getData().getManager().getDealCount() + "次");
		showImageByNetworkImageView(DetailData.getData().getManager()
				.getManagerPhoto(), circleimageview);

		Steel steel = Tools.getSteelMessage(DetailData.getData().getStanBuy()
				.getBreedId());
		// 根据子品种id获取图像资源
		goodname.setText(steel.getName());
		tv_varieties.setText(DetailData.getData().getStanBuy().getBreed());// 品名
		tv_material.setText(DetailData.getData().getStanBuy().getMaterial());// 材质
		tv_specifi.setText(DetailData.getData().getStanBuy().getSpec());// 规格
		tv_brand.setText(DetailData.getData().getStanBuy().getBrand());// 产地
		tv_city.setText(DetailData.getData().getStanBuy().getCity());// 城市
		tv_number.setText(DetailData.getData().getStanBuy().getQty() + "吨");// 数量
		if ("1".equals(DetailData.getData().getStanBuy().getStatus()))
		{// 已报价
			tv_baojia.setText(DetailData.getData().getQuots().getPrice()
					+ "元/吨");
		} else if ("2".equals(DetailData.getData().getStanBuy().getStatus()))
		{// 已成交
			tv_shuoming.setText("已成交:");
			tv_baojia.setText(DetailData.getData().getDealPrice() + "元/吨");
		}

		if ("刚刚".equals(DetailData.getData().getStanBuy().getGapTime()))
		{
			tv_wait.setVisibility(View.GONE);
		} else
		{
			tv_wait.setVisibility(View.VISIBLE);
		}
		tv_times.setText(DetailData.getData().getStanBuy().getGapTime());// 时间

		tvTitle.setText(DetailData.getData().getStanBuy().getPublishTime());// 标题显示时间
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
			if (!TextUtils.isEmpty(phoneNumber))
			{
				Tools.makeCall(this, phoneNumber);
			} else
			{
				Tools.makeCall(this, "暂时未获取到业务员的联系方式!");
			}
			break;
		}
	}

	public void showImageByNetworkImageView(String url, NetworkImageView imgView)
	{
		if (TextUtils.isEmpty(url))
		{
			return;
		}
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
	public void updateView(BaseData data)
	{
		DetailData = (DetailStanBuyData) data;
		editView();

	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		buyCenter.finishRequest();
	}
}

package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DetailStanBuyData;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 激光推送 新开的订单详情页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-6-10 下午4:46:34
 */
public class PushOrderDetailActivity extends BaseActivity implements
		OnClickListener, IBuyView
{

	/**
	 * LOG SWITCHER.
	 */
	// private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	// private static final String TAG = "PushOrderDetailActivity";
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

	private TextView tv_wait, tv_shuoming;
	/** 求购单Id */
	private String stanBuyId = "";
	/** buyImpl */
	private BuyImpl buyImpl;
	private String phoneNumber = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_buyquote);
		stanBuyId = getIntent().getStringExtra("stanBuyId");

		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		buyImpl = new BuyImpl(this);

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
		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);

		String url = RequestUrl.getInstance(this).getUrl_getDetailStanBuy(this,
				stanBuyId);
		buyImpl.getDetailStanBuy(url, Constants.INTERFACE_detailStanBuy);
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
		case R.id.btn_gotowatch:

			break;

		}
	}

	@Override
	public void updateView(BaseData data)
	{
		/** 此处已抢单，分为待报价和已报价两种状态 */
		DetailStanBuyData datas = (DetailStanBuyData) data;
		phoneNumber = datas.getData().getManager().getManagerPhone();
		tv_view_name.setText(datas.getData().getStanBuy().getRushManagerName());// 业务员姓名
		tv_view_times.setText("已成交"
				+ datas.getData().getManager().getDealCount() + "次");
		BitmapUtil.showImageByNetworkImageView(datas.getData().getManager()
				.getManagerPhoto(), circleimageview);

		Steel steel = Tools.getSteelMessage(datas.getData().getStanBuy()
				.getBreedId());// 根据子品种id获取图像资源
		goodname.setText(steel.getName());
		tv_varieties.setText(datas.getData().getStanBuy().getBreed());// 品名
		tv_material.setText(datas.getData().getStanBuy().getMaterial());// 材质
		tv_specifi.setText(datas.getData().getStanBuy().getSpec());// 规格
		tv_brand.setText(datas.getData().getStanBuy().getBrand());// 产地
		tv_city.setText(datas.getData().getStanBuy().getCity());// 城市
		if ("刚刚".equals(datas.getData().getStanBuy().getGapTime()))
		{
			tv_wait.setVisibility(View.GONE);
		} else
		{
			tv_wait.setVisibility(View.VISIBLE);
		}
		tv_times.setText(datas.getData().getStanBuy().getGapTime());// 时间
		tvTitle.setText(datas.getData().getStanBuy().getPublishTime());// 标题显示时间

		if ("0".equals(datas.getData().getStanBuy().getStatus()))// 待报价
		{
			tv_number.setText(datas.getData().getStanBuy().getQty() + "吨");//
		} else if ("1".equals(datas.getData().getStanBuy().getStatus()))// 已报价
		{
			tv_number.setText(datas.getData().getStanBuy().getQty() + "吨");//
		} else if ("2".equals(datas.getData().getStanBuy().getStatus()))// 已成交
		{
			tv_shuoming.setText("已成交:");
			tv_number.setText(datas.getData().getStanBuy().getStatus() + "元/吨");
		}
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (Tools.isBackground(PushOrderDetailActivity.this))// 在后台
		{
			startActivity(new Intent(PushOrderDetailActivity.this,
					WelcomeActivity.class));
		} else
		{
			startActivity(new Intent(this, MainActivity.class));
		}
		finish();
	}
}
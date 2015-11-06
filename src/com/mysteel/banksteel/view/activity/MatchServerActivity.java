package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 发布求购匹配资源后跳转的页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-6-2 上午10:27:19
 */
public class MatchServerActivity extends BaseActivity implements
		OnClickListener, IOrderTradeView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "MatchServerActivity";

	private String stanBuyId = "";// 需求单Id
	// private String name;//业务员姓名
	// private String phone;//业务电话
	String url = "";
	private LinearLayout ll_timer_layout, ll_people;// 倒计时的布局 底部显示的布局
	TextView tv_btn_peoplematch, tv_peoplecount;// 人工抢单按钮
	ImageView gifView;
	private Animation rotateAnimation = null;
	private IOrderTrade orderTrade;
	private int peopleCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matchfindfood);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvRightText.setVisibility(View.GONE);
		tvTitle.setText("人工抢单");
		backLayout.setOnClickListener(this);

		Bundle b = getIntent().getExtras();
		stanBuyId = b.getString("ID");// 获取发布求购成功后得到的需求单Id
		// TODO 业务员信息的显示
		// name = b.getString("name");
		// phone = b.getString("phone");
		orderTrade = new OrderTradeImpl(this, this);
		gifView = (ImageView) findViewById(R.id.img_gifview);
		tv_peoplecount = (TextView) findViewById(R.id.tv_peoplecount);
		rotateAnimation = AnimationUtils.loadAnimation(this,
				R.anim.animation_roate);
		LinearInterpolator lir = new LinearInterpolator();
		rotateAnimation.setInterpolator(lir);
		gifView.startAnimation(rotateAnimation);
		peopleCount = (int) Math.round(Math.random() * 50 + 50);// 产生50~100名业务员人数的随机数
		tv_peoplecount.setText(String.valueOf(peopleCount));
		if (DEBUG)
		{
			Log.d(TAG, "stanBuyId  === " + stanBuyId);
		}
		ll_timer_layout = (LinearLayout) findViewById(R.id.ll_timer_layout);
		ll_timer_layout.setVisibility(View.GONE);
		tv_btn_peoplematch = (TextView) findViewById(R.id.tv_btn_peoplematch);
		tv_btn_peoplematch.setVisibility(View.GONE);
		ll_people = (LinearLayout) findViewById(R.id.ll_people);
		ll_people.setVisibility(View.VISIBLE);

		url = RequestUrl.getInstance(this)
				.getUrl_getHumanServe(this, stanBuyId);
		orderTrade.getHumanServe(url, Constants.INTERFACE_humanServe);
	}

	@Override
	public void updateView(BaseData data)
	{

	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		return super.onKeyUp(keyCode, event);
	}
}

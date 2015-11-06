package com.mysteel.banksteel.view.activity;

import java.util.Random;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DetailStanBuyData;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的求购中跳转过来，进入人工倒计时页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-23 下午4:08:28
 */
public class HumanServeActivity extends BaseActivity implements
		OnClickListener, IOrderTradeView, IBuyView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "HumanServeActivity";

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

	/** 花费时间 */
	private TextView tvUseTime;
	/** 击败百分比的用户 */
	private TextView tvPercent;
	/** 头像 */
	private CircleImageView circleImageView;
	/** 姓名 */
	private TextView tvName;
	/** 交易次数 */
	private TextView tvCount;
	/** 去看看 */
	private Button button;
	/** 对话框 */
	private Dialog dialog;
	/** 广播 */
	private MyBroadcastReceiver receiver;
	/** buyImpl */
	private BuyImpl buyImpl;
	/** 我还要找其他钢材 */
	private TextView tv_finderOther;

	private String status = "";
	
	private TextView tv_tishi;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matchfindfood);
		receiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				Constants.ACTION_HUMANSERVE);
		registerReceiver(receiver, intentFilter);
		status = getIntent().getExtras().getString("status");
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		buyImpl = new BuyImpl(this);

		tvRightText.setText("取消");
		tvRightText.setOnClickListener(this);
		tvTitle.setText("人工抢单");
		backLayout.setOnClickListener(this);

		Bundle b = getIntent().getExtras();
		stanBuyId = b.getString("ID");// 获取发布求购成功后得到的需求单Id
		// 业务员信息的显示
		// name = b.getString("name");
		// phone = b.getString("phone");
		orderTrade = new OrderTradeImpl(this, this);
		gifView = (ImageView) findViewById(R.id.img_gifview);
		tv_peoplecount = (TextView) findViewById(R.id.tv_peoplecount);
		tv_tishi = (TextView) findViewById(R.id.tv_tishi);
		tv_tishi.setText("钢银助手正在通知业务员抢单，请稍等！");
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
		tv_finderOther = (TextView) findViewById(R.id.tv_findotherorder);
		tv_finderOther.setVisibility(View.VISIBLE);
		tv_finderOther.setOnClickListener(this);

		if (!TextUtils.isEmpty(status) && "yes".equals(status))
		{
			url = RequestUrl.getInstance(this).getUrl_getHumanServe(this,
					stanBuyId);
			orderTrade.getHumanServe(url, Constants.INTERFACE_humanServe);
		}
	}

	private void createDialog()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.dailog_ordersuccess, null);

		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setContentView(view);
		dialog.setCancelable(false);

		initDialogView(view);
		dialog.show();
	}

	private void initDialogView(View view)
	{
		tvUseTime = (TextView) view.findViewById(R.id.tv_use_time);
		tvPercent = (TextView) view.findViewById(R.id.tv_beat_percent);
		circleImageView = (CircleImageView) view
				.findViewById(R.id.circleimageview);
		tvName = (TextView) view.findViewById(R.id.tv_view_name);
		tvCount = (TextView) view.findViewById(R.id.tv_view_count);
		tvPercent.setText(getRandomNumber());

		button = (Button) view.findViewById(R.id.btn_gotowatch);
		button.setOnClickListener(this);
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("buy.detailStanBuy".equals(data.getCmd()))
		{
			DetailStanBuyData buyData = (DetailStanBuyData) data;
			if (buyData.getData().getManager() != null)
			{
				tvName.setText(buyData.getData().getManager().getManagerName());
				tvCount.setText("已成交"
						+ buyData.getData().getManager().getDealCount() + "次");
				tvUseTime.setText(buyData.getData().getStanBuy().getGapTime());
				BitmapUtil.showImageByNetworkImageView(buyData.getData()
						.getManager().getManagerPhoto(), circleImageView);
			}
		} else if ("buy.cancelStanBuy".equals(data.getCmd()))
		{// 取消求购单
			if ("true".equals(data.getStatus()))
			{
				Tools.showToast(this, "取消求购成功!");
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
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
			finish();
			break;

		case R.id.btn_gotowatch:// 去看看
			if (dialog != null)
			{
				dialog.setCancelable(true);
				dialog.dismiss();
				dialog = null;
			}
			Intent i = new Intent(this, PushOrderDetailActivity.class);
			i.putExtra("stanBuyId", stanBuyId);
			startActivity(i);
			finish();
			break;

		case R.id.tv_findotherorder:// 我还要找其他钢材
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;

		case R.id.tv_title_right_text:// 取消求购
			TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
					HumanServeActivity.this);
			alert.setMessage("取消求购");
			alert.setNegativeButton("再等等看",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
						}

					});
			alert.setPositiveButton("去意已决",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							url = RequestUrl.getInstance(
									HumanServeActivity.this)
									.getUrl_getCancelStanBuy(
											HumanServeActivity.this, stanBuyId);
							buyImpl.getCancelStanBuy(url,
									Constants.INTERFACE_cancelStanBuy);
							dialog.dismiss();
						}

					});
			alert.create().show();
			break;
		default:
			break;
		}

	}

	public class MyBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (Constants.ACTION_HUMANSERVE.equals(intent.getAction()))
			{
				createDialog();

				String stanBuyId = intent.getStringExtra("stanBuyId");
				String url = RequestUrl.getInstance(HumanServeActivity.this)
						.getUrl_getDetailStanBuy(HumanServeActivity.this,
								stanBuyId);
				buyImpl.getDetailStanBuy(url, Constants.INTERFACE_detailStanBuy);
			}
		}
	}

	private String getRandomNumber()
	{
		int number = 80 + new Random().nextInt(20);
		return number + "%";
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
		buyImpl.finishRequest();
		unregisterReceiver(receiver);
	}

}

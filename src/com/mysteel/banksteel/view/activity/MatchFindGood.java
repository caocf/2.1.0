package com.mysteel.banksteel.view.activity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DetailStanBuyData;
import com.mysteel.banksteel.entity.MatchBuyData;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * 匹配找货的页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-18 下午3:58:48
 */
public class MatchFindGood extends BaseActivity implements OnClickListener,
		IOrderTradeView, IBuyView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "MatchFindGood";

	private TextView tv_times, tv_count, tv_btn_people, tv_peoplecount;// 时间 和
																		// 数据条数,业务员人数

	private String stanBuyId = "";// 需求单Id

	private IOrderTrade orderTrade;
	private IBuyCenter buyCenterImpl;
	private LinearLayout llTimer, ll_people;// 倒计时 和 人工匹配的布局
	/** 我还要找其他钢材 */
	private TextView tv_finderOther;

	Timer timer;// 计时器
	Timer timerDialog;// 计时器对话框
	Timer timerMatch;// 匹配到数据后的计时器
	private int totalTime = 1000;
	private int recLen = 0;
	private int matchTime = 0;
	private boolean flagDialog = false;
	private String url = "";
	private String page = "1";
	private String size = "10";
	ImageView gifView;
	private Animation rotateAnimation = null;
	private int peopleCount = 0;
	private int matchCount = -1;// 匹配到的所有的资源数量，-1就是没有匹配到
	private boolean cancle = false;// 当点击取消求购的按钮后，没5秒提示继续匹配的通过这个值先不弹出

	// private MatchBuyData mbData;
	// private ArrayList<Datas> datas;

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
	private TextView tv_tishi;

	TimerTask task = new TimerTask()
	{
		public void run()
		{
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};

	TimerTask taskDialog = new TimerTask()
	{
		public void run()
		{
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);
		}
	};

	TimerTask taskMatch = new TimerTask()
	{
		public void run()
		{
			Message message = new Message();
			message.what = 3;
			handler.sendMessage(message);
		}
	};

	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				// if (recLen == 5)
				// {
				// timer.cancel();
				// recLen = 0;
				// showDialogHint();
				// } else
				// {
				recLen++;
				tv_times.setText(String.valueOf(recLen));
				// }

				break;
			case 2:
				if (!flagDialog && !cancle)
				{// 对话框不处在打开状态,或者还没有取消求购
					showDialogHint();
				}
				break;
			case 3:
				matchTime++;
				if (matchTime < 5)
				{
					tv_count.setText(String.valueOf(matchCount));
				} else if (matchTime == 5)
				{
					tv_count.setText(String.valueOf(matchCount));
					cancelTimer();
					Intent intent = new Intent(MatchFindGood.this,
							SelectProSourceActivity.class);
					intent.putExtra("ID", stanBuyId);
					intent.putExtra("status", "match");// 标识是匹配找货跳转的
					startActivity(intent);
					finish();
				}
				break;
			case 4:// 变标题
				tvTitle.setText("人工抢单");
				break;
			}
			super.handleMessage(msg);
		}

		/** 5秒后先提示，在处理 */
		private void showDialogHint()
		{
			flagDialog = true;
			TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
					MatchFindGood.this);
			alert.setMessage("取消系统匹配");
			alert.setNegativeButton("继续匹配",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
							flagDialog = false;
						}

					});
			alert.setPositiveButton("人工匹配",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							peopleMatch();
							dialog.dismiss();
							flagDialog = false;
							if (timerDialog != null)
							{
								timerDialog.cancel();
							}
						}

					});
			alert.create().show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matchfindfood);

		/** 注册广播 */
		receiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				Constants.ACTION_MATCHFINDGOOD);
		registerReceiver(receiver, intentFilter);

		initViews();
	}

	protected void initViews()
	{
		super.initViews();

		tvRightText.setText("取消");
		tvTitle.setText("匹配找货");

		Bundle b = getIntent().getExtras();
		stanBuyId = b.getString("ID");// 获取发布求购成功后得到的需求单Id

		tv_times = (TextView) findViewById(R.id.tv_second);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_peoplecount = (TextView) findViewById(R.id.tv_peoplecount);
		tv_btn_people = (TextView) findViewById(R.id.tv_btn_peoplematch);// 转人工抢单按钮
		llTimer = (LinearLayout) findViewById(R.id.ll_timer_layout);// 倒计时布局的view
		ll_people = (LinearLayout) findViewById(R.id.ll_people);// 人工匹配的view
		gifView = (ImageView) findViewById(R.id.img_gifview);
		tv_tishi = (TextView) findViewById(R.id.tv_tishi);
		rotateAnimation = AnimationUtils.loadAnimation(this,
				R.anim.animation_roate);
		LinearInterpolator lir = new LinearInterpolator();
		rotateAnimation.setInterpolator(lir);
		gifView.startAnimation(rotateAnimation);
		backLayout.setOnClickListener(this);
		tvRightText.setOnClickListener(this);
		tv_btn_people.setOnClickListener(this);

		tv_finderOther = (TextView) findViewById(R.id.tv_findotherorder);
		tv_finderOther.setOnClickListener(this);

		buyCenterImpl = new BuyImpl(this);
		orderTrade = new OrderTradeImpl(this, this);

		timer = new Timer(true);
		timer.schedule(task, totalTime, 1000);

		timerDialog = new Timer(true);
		timerDialog.schedule(taskDialog, 5000, 5000);

		peopleCount = (int) Math.round(Math.random() * 50 + 50);// 产生50~100名业务员人数的随机数
		tv_peoplecount.setText(String.valueOf(peopleCount));

		// Intent intent = new Intent(MatchFindGood.this,
		// SelectProSourceActivity.class);
		// intent.putExtra("ID", stanBuyId);
		// startActivity(intent);

		// String url = RequestUrl.getInstance(this).getUrl_getMatchBuy(this,
		// stanBuyId, page, size);
		String url = RequestUrl.getInstance(this).getUrl_getMatchBuy(this,
				stanBuyId, page, size);
		if (DEBUG)
		{
			Log.d(TAG, "5.1 资源匹配的" + url);
		}
		orderTrade.getMatchBuy(url, Constants.INTERFACE_matchBuy);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			// finish();
			/** 返回首页 */
			if (timer != null)
			{
				timer.cancel();// 关闭计时器
			}
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.tv_title_right_text:// 取消求购
			cancle = true;
			TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
					MatchFindGood.this);
			alert.setMessage("取消求购");
			alert.setNegativeButton("再等等看",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							cancle = false;
							dialog.dismiss();
						}

					});
			alert.setPositiveButton("去意已决",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							if (timer != null)
							{
								timer.cancel();// 关闭计时器
							}
							if (timerMatch != null)
							{
								timerMatch.cancel();
							}
							if (timerDialog != null)
							{
								timerDialog.cancel();
							}
							url = RequestUrl.getInstance(MatchFindGood.this)
									.getUrl_getCancelStanBuy(
											MatchFindGood.this, stanBuyId);
							buyCenterImpl.getCancelStanBuy(url,
									Constants.INTERFACE_cancelStanBuy);
							dialog.dismiss();
						}

					});
			alert.create().show();
			break;

		case R.id.tv_btn_peoplematch:// 转人工服务点击事件
			peopleMatch();
			break;

		case R.id.btn_gotowatch:
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

		case R.id.tv_findotherorder:
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

	/** 人工匹配 */
	public void peopleMatch()
	{
		cancelTimer();
		tv_tishi.setText("钢银助手正在通知业务员抢单，请稍等！");
		llTimer.setVisibility(View.GONE);
		tv_btn_people.setVisibility(View.GONE);
		ll_people.setVisibility(View.VISIBLE);
		tv_finderOther.setVisibility(View.VISIBLE);
		url = RequestUrl.getInstance(this)
				.getUrl_getHumanServe(this, stanBuyId);
		orderTrade.getHumanServe(url, Constants.INTERFACE_humanServe);
		Message message = new Message();
		message.what = 4;
		handler.sendMessage(message);
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("buy.cancelStanBuy".equals(data.getCmd()))
		{// 取消求购返回结果
			if ("true".equals(data.getStatus()))
			{
				Tools.showToast(this, "取消求购成功!");
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		} else if ("deal.matchBuy".equals(data.getCmd()))
		{
			MatchBuyData mbData = (MatchBuyData) data;
			if (mbData.getData() == null)
			{
				// Tools.showToast(this, "没有匹配到资源!");
			} else
			{
				timerDialog.cancel();
				matchCount = Integer.parseInt(mbData.getData().getCount());
				timerMatch = new Timer(true);
				timerMatch.schedule(taskMatch, totalTime, 1000);
			}
		} else if ("buy.detailStanBuy".equals(data.getCmd()))
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
		orderTrade.finishRequest();
		buyCenterImpl.finishRequest();
		unregisterReceiver(receiver);
		handler.removeCallbacks(task);
		handler.removeCallbacks(taskDialog);
		handler.removeCallbacks(taskMatch);
		handler.removeMessages(4);
		cancelTimer();
	}

	public void cancelTimer()
	{
		if (timer != null)
		{
			timer.cancel();// 关闭计时器
		}
		if (timerDialog != null)
		{
			timerDialog.cancel();
		}
		if (timerMatch != null)
		{
			timerMatch.cancel();
		}
	}

	public class MyBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (Constants.ACTION_MATCHFINDGOOD.equals(intent.getAction()))
			{
				createDialog();

				String stanById = intent.getStringExtra("stanBuyId");
				String url = RequestUrl.getInstance(MatchFindGood.this)
						.getUrl_getDetailStanBuy(MatchFindGood.this, stanById);
				buyCenterImpl.getDetailStanBuy(url,
						Constants.INTERFACE_detailStanBuy);
			}
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

	private String getRandomNumber()
	{
		int number = 80 + new Random().nextInt(20);
		return number + "%";
	}
}

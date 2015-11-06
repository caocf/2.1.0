package com.mysteel.banksteel.view.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.mysteel.banksteel.ao.impl.ScoreImp;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.ScoreConvert;
import com.mysteel.banksteel.entity.ScoreConvert.Data.Goods;
import com.mysteel.banksteel.entity.ScoreConvert.Data.Order;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.ConvertOrderAdapter;
import com.mysteel.banksteel.view.adapters.ScoreStoreAdapter;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;
import com.umeng.socialize.sso.UMSsoHandler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

/**
 * 积分商城 兑换订单
 * 
 * @author:huoguodong
 * @date：2015-5-9 上午10:45:39
 */
public class PresentsActivity extends BaseActivity implements IScoreView,
		IXListViewListener
{

	/** DEBUG. */
	private static final boolean DEBUG = true & BankSteelApplication.GLOBAL_DEBUG;
	/** TAG. */
	private static final String TAG = "PresentsActivity";
	private XListView lvScoreStore;
	private ScoreStoreAdapter adapter ;
	private ConvertOrderAdapter SSadapter;
	
	private String currentScreen; // 当前页面

	private ProgressBar progressBar;

	private ScoreImp iScoreImp;

	private ScoreConvert SCData;// 积分兑换列表数据
	private ArrayList<Goods> goods;

	private ArrayList<Order> order;

	private String page = "1";// 页数
	private String size = "10";// 每一页有多少条数据
	private int totalScore;// 拥有的总积分

	public int getTotalScore()
	{
		return totalScore;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presents);

		totalScore = getIntent().getIntExtra("SCORE", 0);
		currentScreen = getIntent().getStringExtra(Constants.NEXT_SCREEN_TAG);

		initViews();
	}

	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		lvScoreStore = (XListView) findViewById(R.id.lv_score_store);
		lvScoreStore.setXListViewListener(this);
		iScoreImp = new ScoreImp(this);
		SSadapter = new ConvertOrderAdapter(this, new ArrayList<Order>());
		adapter = new ScoreStoreAdapter(getLayoutInflater(),new ArrayList<Goods>());
		/***/
		if (null != lvScoreStore)
		{
			lvScoreStore.hideFoot();
		}
		backLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

	}

	
	@Override
	protected void onResume()
	{
		super.onResume();
		page = "1";
		String url = "";
		String scorerUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);// 查看我的积分url
		iScoreImp.getSearchScore(scorerUrl, Constants.INTERFACE_searchMyScore);
		if (MyScoreActivity.SCREEN_TAG_SCORE_STORE.equals(currentScreen))
		{
			tvTitle.setText("积分商城");
//			adapter = new ScoreStoreAdapter(getLayoutInflater(),new ArrayList<Goods>());
			lvScoreStore.setAdapter(adapter);
			url = RequestUrl.getInstance(this)
					.getUrl_pageGift(this, page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageGift);
		} else
		{
			tvTitle.setText("兑换订单");
//			SSadapter = new ConvertOrderAdapter(this, new ArrayList<Order>());
			lvScoreStore.setAdapter(SSadapter);
			url = RequestUrl.getInstance(this).getUrl_pageScoreOrder(this,
					page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageScoreOrder);
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		// TODO Auto-generated method stub
		if (MyScoreActivity.SCREEN_TAG_SCORE_STORE.equals(currentScreen))
		{
			SCData = (ScoreConvert) data;
			if (SCData.getData() == null || SCData.getData().getGoods() == null)
			{
				return;
			}
			if (DEBUG)
			{
				Log.d(TAG, "得到的数据有：" + SCData.getData().getGoods().size());
			}
			if ("1".equals(page))
			{
				goods = SCData.getData().getGoods();
			} else
			{
				goods.addAll(SCData.getData().getGoods());
			}

			adapter.reSetListView(goods);
		} else
		{
			SCData = (ScoreConvert) data;
			if (SCData.getData() == null
					|| SCData.getData().getOrders() == null)
			{
				return;
			}
			order = SCData.getData().getOrders();
			if ("1".equals(page))
			{
				order = SCData.getData().getOrders();
			} else
			{
				order.addAll(SCData.getData().getOrders());
			}

			SSadapter.reSetListView(order);
		}

		onLoad();
	}

	@Override
	public void isShowDialog(boolean flag)
	{
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	/** 下拉刷新 */
	@Override
	public void onRefresh()
	{
		page = "1";
		String url = "";
		if (MyScoreActivity.SCREEN_TAG_SCORE_STORE.equals(currentScreen))
		{// 积分商城
			url = RequestUrl.getInstance(this)
					.getUrl_pageGift(this, page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageGift);
		} else if (MyScoreActivity.SCREEN_TAG_EXCHANGE_RECORD
				.equals(currentScreen))
		{// 积分历史订单
			url = RequestUrl.getInstance(this).getUrl_pageScoreOrder(this,
					page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageScoreOrder);
		}

	}

	/** 上拉加载 */
	@Override
	public void onLoadMore()
	{
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(SCData.getData().getPagenum())
				|| TextUtils.isEmpty(SCData.getData().getPage()))
		{
			return;
		}

		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this,
					getResources().getString(R.string.Network_not_Connected));
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(SCData.getData()
				.getPagenum()))
		{
			// todo
			onLoad();
			Tools.showToast(this, "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";

		String url = "";
		if (MyScoreActivity.SCREEN_TAG_SCORE_STORE.equals(currentScreen))
		{// 积分商城
			url = RequestUrl.getInstance(this)
					.getUrl_pageGift(this, page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageGift);
		} else
		{// 积分历史订单
			url = RequestUrl.getInstance(this).getUrl_pageScoreOrder(this,
					page, size);
			iScoreImp.getScoreConvert(url, Constants.INTERFACE_pageScoreOrder);
		}

	}

	/**
	 * 更新listview头部刷新时间
	 */
	public void onLoad()
	{
		lvScoreStore.stopRefresh();
		lvScoreStore.stopLoadMore();
		lvScoreStore.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}

	@Override
	public void searchMyScore(SearchMyScoreData data)
	{
		totalScore = Integer.parseInt(data.getData().getTotalScore());
	}

	@Subscriber(tag = "updateScore")
	private void updateScore(String s)
	{
		String scorerUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);// 查看我的积分url
		iScoreImp.getSearchScore(scorerUrl, Constants.INTERFACE_searchMyScore);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (SSadapter != null)
		{
			SSadapter.finishScoreImp();
		}
		iScoreImp.finishRequest();
		if (!MyScoreActivity.SCREEN_TAG_SCORE_STORE.equals(currentScreen))
		{
			SSadapter.unregisterListener();
		}
	}

	@Override
	public void earnScore(EarnScoreData data)
	{
		// 分享成功后,获取积分协议调用成功,积分+2
		EventBus.getDefault().post("", "updateScore");
		Tools.showToast(this, "+2");
	}

	// @Override
	// public void sendEarnScore()
	// {
	// String url = RequestUrl.getInstance(this).getUrl_earnScore(
	// SharePreferenceUtil.getString(this,
	// Constants.PREFERENCES_USERID), "2", "推荐有礼");
	// scoreImp.getEarnScore(url, Constants.INTERFACE_earnScore);
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = SSadapter.getmController().getConfig()
				.getSsoHandler(requestCode);
		if (ssoHandler != null)
		{
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}

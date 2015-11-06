package com.mysteel.banksteel.view.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.mysteel.banksteel.ao.impl.ScoreImp;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.ScoreRecord;
import com.mysteel.banksteel.entity.ScoreRecord.Data.Records;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.MyScoreAdapter;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author 68 我的积分页面
 */
public class MyScoreActivity extends BaseActivity implements OnClickListener,
		IScoreView, IXListViewListener
{

	/** 花积分 */
	private Button useScore;
	/** 赚积分 */
	private Button earnScore;
	/** 我的积分 */
	private TextView myTotalScore;
	/** 今日赚取积分 */
	private TextView todayEarnScore;
	/** 已兑换订单 */
	private TextView tvExchangeOrder;
	/** 积分记录 */
	private XListView lvScore;
	private String page = "1";
	private String size = "10";
	/** 我的积分(总积分) */
	private int totalScore;

	public static final String SCREEN_TAG_SCORE_STORE = "score store"; // 积分商城
	public static final String SCREEN_TAG_EXCHANGE_RECORD = "exchange record"; // 兑换订单，兑换记录
	/** 积分功能模块实现类 */
	private ScoreImp iScoreImp;
	/** 积分记录适配器 */
	private MyScoreAdapter adapter;
	/** 进度条 */
	private ProgressBar progressBar;
	private ScoreRecord ScoreRecord;
	private ArrayList<Records> records;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myscore);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("我的积分");

		useScore = (Button) findViewById(R.id.btn_myscore_usescore);
		earnScore = (Button) findViewById(R.id.btn_myscore_earnscore);
		tvExchangeOrder = (TextView) findViewById(R.id.tv_exchange_order);
		myTotalScore = (TextView) findViewById(R.id.tv_myscore_totalscore);
		todayEarnScore = (TextView) findViewById(R.id.tv_myscore_earnscore);
		lvScore = (XListView) findViewById(R.id.lv_myscore_recordscore);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		lvScore.setXListViewListener(this);
		useScore.setOnClickListener(this);
		earnScore.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		tvExchangeOrder.setOnClickListener(this);
		adapter = new MyScoreAdapter(this);
		if (lvScore != null)
		{
			lvScore.hideFoot();
		}
		lvScore.setAdapter(adapter);
		iScoreImp = new ScoreImp(this);

		sendRequest();
	}

	private void sendRequest()
	{
		/** 查询积分URL */
		String searchUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);
		iScoreImp.getSearchScore(searchUrl, Constants.INTERFACE_searchMyScore);
		/** 历史积分记录url */
		String historyUrl = RequestUrl.getInstance(this)
				.getUrl_pageHistoryScoreLog(this, page, size);
		iScoreImp.getScoreRecord(historyUrl,
				Constants.INTERFACE_pageHistoryScoreLog);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		// 左侧菜单(退出)
		case R.id.menu_layout:
			finish();
			break;

		// 花积分
		case R.id.btn_myscore_usescore:
			intent = new Intent(this, PresentsActivity.class);
			intent.putExtra(Constants.NEXT_SCREEN_TAG, SCREEN_TAG_SCORE_STORE);
			intent.putExtra("SCORE", totalScore);
			startActivity(intent);
			break;

		case R.id.tv_exchange_order:
			intent = new Intent(this, PresentsActivity.class);
			intent.putExtra(Constants.NEXT_SCREEN_TAG,
					SCREEN_TAG_EXCHANGE_RECORD);
			startActivity(intent);

			break;
		// 赚积分
		case R.id.btn_myscore_earnscore:
			startActivity(new Intent(this, EarnScoreActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		ScoreRecord = (ScoreRecord) data;
		if (ScoreRecord.getData() == null
				|| ScoreRecord.getData().getRecords() == null)
		{
			return;
		}
		if ("1".equals(page))
		{
			records = ScoreRecord.getData().getRecords();
		} else
		{
			records.addAll(ScoreRecord.getData().getRecords());
		}
		adapter.reSetListView(records);
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

	@Override
	public void searchMyScore(SearchMyScoreData data)
	{
		totalScore = Integer.parseInt(data.getData().getTotalScore());
		myTotalScore.setText(data.getData().getTotalScore());
		todayEarnScore.setText(data.getData().getTodayScore());
	}

	private void onLoad()
	{
		lvScore.stopLoadMore();
		lvScore.stopRefresh();
		lvScore.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh()
	{
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_pageHistoryScoreLog(
				this, page, size);
		iScoreImp.getScoreRecord(url, Constants.INTERFACE_pageHistoryScoreLog);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMore()
	{
		if (TextUtils.isEmpty(ScoreRecord.getData().getPagenum())
				|| TextUtils.isEmpty(ScoreRecord.getData().getPage()))
		{
			return;
		}
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this,
					getResources().getString(R.string.Network_not_Connected));
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(ScoreRecord.getData()
				.getPagenum()))
		{
			onLoad();
			Tools.showToast(this, "已经到最后一页!");
			return;
		}

		page = Integer.parseInt(page) + 1 + "";
		String url = RequestUrl.getInstance(this).getUrl_pageHistoryScoreLog(
				this, page, size);
		iScoreImp.getScoreRecord(url, Constants.INTERFACE_pageHistoryScoreLog);
		Log.e("url", url);
	}

	@Subscriber(tag = "updateScore")
	private void updateScore(String s)
	{
		String searchUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);
		iScoreImp.getSearchScore(searchUrl, Constants.INTERFACE_searchMyScore);
	}

	@Override
	public void earnScore(EarnScoreData data)
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		iScoreImp.finishRequest();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		EventBus.getDefault().post("", "scoreChange");
	}
	
	@Subscriber(tag="scoreChange")
	private void scoreChange(String str)
	{
		sendRequest();
	}
}

package com.mysteel.banksteel.view.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.HistoryData;
import com.mysteel.banksteel.entity.HistoryData.Data.FastBuy;
import com.mysteel.banksteel.entity.HistoryData.Data.Pagination.Datas;
import com.mysteel.banksteel.entity.PagDatas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.MyBuyAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 我的求购
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-9 上午10:51:30
 */
public class MyBuyActivity extends BaseActivity implements OnClickListener,
		IBuyView, IXListViewListener
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "MyBuyActivity";

	private XListView listview;
	private MyBuyAdapter adapter;
	private ProgressBar progressBar;
	private IBuyCenter buyCenter;
	private String url = "";
	private String page = "1";
	private String size = "10";
	private HistoryData HYData;
	// private ArrayList<Datas> datas;
	private LinearLayout llNoBuy;
	private ArrayList<PagDatas> pData = new ArrayList<PagDatas>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mybuy);
		initView();
	}

	/** 初始化组件 */
	private void initView()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		tvTitle.setText("我的求购");

		llNoBuy = (LinearLayout) findViewById(R.id.ll_nullbuy);
		listview = (XListView) findViewById(R.id.lv_mybuy);
		adapter = new MyBuyAdapter(getLayoutInflater(), pData);
		listview.setAdapter(adapter);
		listview.setXListViewListener(this);
		if (null != listview)
		{
			listview.hideFoot();
		}
		buyCenter = new BuyImpl(this);

	}

	public void getData()
	{
		page = "1";
		url = RequestUrl.getInstance(this).getUrl_getHistoryStanBuy(this, page,
				size);
		buyCenter.getHistoryStanBuy(url, Constants.INTERFACE_historyStanBuy);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		HYData = (HistoryData) data;
		if (HYData.getData() == null
				|| HYData.getData().getPagination() == null)
		{
			return;
		}
		ArrayList<PagDatas> pDatas = new ArrayList<PagDatas>();

		/*
		 * 这里需要把FastBuys的数据转换成HYData.getData().getPagination().getDatas()
		 * 中的Datas,以便在MyBuyAdapter中一起展示
		 */
		if (HYData.getData().getFastBuys() != null)
		{// 当语音的数据不为0，也就是有语音的数据时
			if (DEBUG)
			{
				Log.d(TAG, "语音有：" + HYData.getData().getFastBuys().size());
			}
			ArrayList<FastBuy> fb = HYData.getData().getFastBuys();
			int size = fb.size();
			PagDatas pdataes = null;
			for (int i = 0; i < size; i++)
			{
				pdataes = new PagDatas();
				pdataes.setAudioFileUrl(fb.get(i).getAudioFileUrl());
				pdataes.setAudioTimes(fb.get(i).getAudioTimes());
				pdataes.setId(fb.get(i).getId());
				pdataes.setIp(fb.get(i).getIp());
				pdataes.setLastAccess(fb.get(i).getLastAccess());
				pdataes.setMemberId(fb.get(i).getMemberId());
				pdataes.setPhone(fb.get(i).getPhone());
				pdataes.setPublishTime(fb.get(i).getPublishTime());
				pdataes.setResource(fb.get(i).getResource());
				pdataes.setSerialVersionUID(fb.get(i).getSerialVersionUID());
				pdataes.setStatus(fb.get(i).getStatus());
				pdataes.setUserId(fb.get(i).getUserId());
				pdataes.setVersion(fb.get(i).getVersion());
				pdataes.setContent(fb.get(i).getContent());
				pDatas.add(pdataes);
			}

		}

		if (HYData.getData().getPagination().getDatas() == null)
		{
			llNoBuy.setVisibility(View.VISIBLE);
		} else
		{// 如果pagination 的数据不为空
			llNoBuy.setVisibility(View.GONE);
			int pagSize = HYData.getData().getPagination().getDatas().size();
			if (DEBUG)
			{
				Log.d(TAG, "求购的其他状态数据的条数：" + pagSize);
			}
			ArrayList<Datas> pagData = HYData.getData().getPagination()
					.getDatas();
			for (int i = 0; i < pagSize; i++)
			{
				PagDatas pdataes = new PagDatas();
				pdataes.setBrand(pagData.get(i).getBrand());
				pdataes.setBreed(pagData.get(i).getBreed());
				pdataes.setBreedId(pagData.get(i).getBreedId());
				pdataes.setCity(pagData.get(i).getCity());
				pdataes.setCompany(pagData.get(i).getCompany());
				pdataes.setContacter(pagData.get(i).getContacter());
				pdataes.setDueStatus(pagData.get(i).getDueStatus());
				pdataes.setDueTime(pagData.get(i).getDueTime());
				pdataes.setId(pagData.get(i).getId());
				pdataes.setLastAccess(pagData.get(i).getLastAccess());
				pdataes.setManual(pagData.get(i).getManual());
				pdataes.setMaterial(pagData.get(i).getMaterial());
				pdataes.setMemberId(pagData.get(i).getMemberId());
				pdataes.setNote(pagData.get(i).getNote());
				pdataes.setPhone(pagData.get(i).getPhone());
				pdataes.setPrice(pagData.get(i).getPrice());
				pdataes.setPublishTime(pagData.get(i).getPublishTime());
				pdataes.setQty(pagData.get(i).getQty());
				pdataes.setQuotNum(pagData.get(i).getQuotNum());
				pdataes.setQuotUserId(pagData.get(i).getQuotUserId());
				pdataes.setResource(pagData.get(i).getResource());
				pdataes.setRushManagerId(pagData.get(i).getRushManagerId());
				pdataes.setRushManagerName(pagData.get(i).getRushManagerName());
				pdataes.setRushStatus(pagData.get(i).getRushStatus());
				pdataes.setRushTime(pagData.get(i).getRushTime());
				pdataes.setSerialVersionUID(pagData.get(i)
						.getSerialVersionUID());
				pdataes.setSpec(pagData.get(i).getSpec());
				pdataes.setStatus(pagData.get(i).getStatus());
				pdataes.setUserId(pagData.get(i).getUserId());
				pdataes.setVersion(pagData.get(i).getVersion());
				pdataes.setRushManagerHeader(pagData.get(i)
						.getRushManagerHeader());
				pdataes.setDealCount(pagData.get(i).getDealCount());
				pdataes.setGapTime(pagData.get(i).getGapTime());
				pdataes.setSkipStatus(pagData.get(i).getSkipStatus());
				pdataes.setRushManagerPhone(pagData.get(i)
						.getRushManagerPhone());
				pDatas.add(pdataes);
			}
			if ("1".equals(page))
			{
				// datas = (ArrayList<Datas>) HYData.getData().getPagination()
				// .getDatas();
				pData = pDatas;
			} else
			{
				// datas.addAll((ArrayList<Datas>) HYData.getData()
				// .getPagination().getDatas());
				pData.addAll(pDatas);
			}
			adapter.reSetList(pData);
		}
		onLoad();
	}

	@Override
	public void isShowDialog(boolean flag)
	{
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onRefresh()
	{
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_getHistoryStanBuy(
				this, page, size);
		buyCenter.getHistoryStanBuy(url, Constants.INTERFACE_historyStanBuy);

	}

	@Override
	public void onLoadMore()
	{
		if (HYData.getData() == null
				|| HYData.getData().getPagination() == null
				|| HYData.getData().getPagination() == null)
		{
			return;
		}
		if (TextUtils.isEmpty(HYData.getData().getPagination().getPagenum())
				|| TextUtils
						.isEmpty(HYData.getData().getPagination().getPage()))
		{
			return;
		}

		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this,
					getResources().getString(R.string.Network_not_Connected));
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(HYData.getData()
				.getPagination().getPagenum()))
		{
			onLoad();
			Tools.showToast(this, "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";
		String url = RequestUrl.getInstance(this).getUrl_getHistoryStanBuy(
				this, page, size);
		buyCenter.getHistoryStanBuy(url, Constants.INTERFACE_historyStanBuy);
	}

	/**
	 * 更新listview头部刷新时间
	 */
	public void onLoad()
	{
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		buyCenter.finishRequest();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		getData();
	}
}

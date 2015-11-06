package com.mysteel.banksteel.view.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageOrderData;
import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.OrderAdapter;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

/**
 * 待评价fragment
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-5 上午9:51:05
 */
public class StayAssessFragment extends BaseFragment implements
		IXListViewListener, IOrderTradeView
{

	private XListView listview;
	private OrderAdapter adapter;
	private ArrayList<Datas> datas;
	private ProgressBar progressBar;
	private IOrderTrade orderTrade;
	private String page = "1";
	private String size = "10";
	private PageOrderData poData;
	private LinearLayout llNoOrder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_stay_assess, null);
		initView(view);
		return view;
	}

	private void initView(View view)
	{
		llNoOrder = (LinearLayout) view.findViewById(R.id.ll_nullorder);
		listview = (XListView) view.findViewById(R.id.xlistview);
		adapter = new OrderAdapter(getActivity().getLayoutInflater(), datas);
		progressBar = (ProgressBar) view.findViewById(R.id.pb_progressbar);
		listview.setAdapter(adapter);
		listview.setXListViewListener(this);
		if (null != listview)
		{
			listview.hideFoot();
		}
		orderTrade = new OrderTradeImpl(getActivity(), this);
	}

	@Override
	public void updateView(BaseData data)
	{
		poData = (PageOrderData) data;
		if (poData.getData().getDatas() == null)
		{
			llNoOrder.setVisibility(View.VISIBLE);
			adapter.reSetListVIew(poData.getData().getDatas());
		} else
		{
			llNoOrder.setVisibility(View.GONE);
			if ("1".equals(page))
			{
				datas = poData.getData().getDatas();
			} else
			{
				datas.addAll(poData.getData().getDatas());
			}
			adapter.reSetListVIew(datas);
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
		getData();
	}

	@Override
	public void onLoadMore()
	{
		if (TextUtils.isEmpty(poData.getData().getPagenum())
				|| TextUtils.isEmpty(poData.getData().getPage()))
		{
			return;
		}

		if (!Tools.isNetworkConnected(getActivity()))
		{
			Tools.showToast(getActivity(),
					getResources().getString(R.string.Network_not_Connected));
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(poData.getData()
				.getPagenum()))
		{
			onLoad();
			Tools.showToast(getActivity(), "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";

		String url = RequestUrl.getInstance(getActivity()).getUrl_getPageOrder(
				getActivity(), page, size, "0");
		orderTrade.getPageOrder(url, Constants.INTERFACE_pageOrder);

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
	public void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
	}

	/** 首次进来需要拉取的数据 */
	public void getData()
	{
		page = "1";
		String url = RequestUrl.getInstance(getActivity()).getUrl_getPageOrder(
				getActivity(), page, size, "0");
		orderTrade.getPageOrder(url, Constants.INTERFACE_pageOrder);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		page = "1";
		getData();
	}
}

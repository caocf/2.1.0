package com.mysteel.banksteel.ao.impl;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IResourceManager;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.PriceTrendData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 资源搜索
 * 
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:51:00
 */
public class ResourceManagerImpl implements IResourceManager {

	private Context context;
	private GetDataDAO<SearchResourceData> getSearchResourceDataDao;
	private GetDataDAO<SearchResourceData> getMatchResourceDataDao;
	private GetDataDAO<PriceTrendData> getPriceTrendDataDao;
	private GetDataDAO<SearchResourceData> getMinPriceDataDao;

	private IResourceManagerView interfaceView;
	public ArrayList<String> Tags = new ArrayList<String>();
	
	
	public ResourceManagerImpl(Context context)
	{
		this.context = context;
		interfaceView = (IResourceManagerView) context;
	}
	
	public ResourceManagerImpl(Context context,IResourceManagerView interfaceView)
	{
		this.context = context;
		this.interfaceView = interfaceView;
	}
	
	@Override
	public void getSearchResouce(String url, String request_tag) {
		if (getSearchResourceDataDao == null)
		{
			getSearchResourceDataDao = new GetDataDAO<SearchResourceData>(context,
					SearchResourceData.class, new DefaultAOCallBack<SearchResourceData>(
							interfaceView, context)
					{

						@Override
						public void dealWithSuccess(SearchResourceData obj) {
							interfaceView.searchResource(obj);
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getSearchResourceDataDao.getData(url, request_tag);
	}

	@Override
	public void getDetailResouce(String url, String request_tag) {
		if (getMinPriceDataDao == null)
		{
			getMinPriceDataDao = new GetDataDAO<SearchResourceData>(context,
					SearchResourceData.class, new DefaultAOCallBack<SearchResourceData>(
					interfaceView, context)
			{

				@Override
				public void dealWithSuccess(SearchResourceData obj) {
					interfaceView.matchResource(obj);
				}
			});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getMinPriceDataDao.getData(url, request_tag);
	}

	@Override
	public void getMatchResource(String url, String request_tag) {
		if (getMatchResourceDataDao == null)
		{
			getMatchResourceDataDao = new GetDataDAO<SearchResourceData>(context,
					SearchResourceData.class, new DefaultAOCallBack<SearchResourceData>(
							interfaceView, context)
					{

						@Override
						public void dealWithSuccess(SearchResourceData obj) {
							interfaceView.matchResource(obj);
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getMatchResourceDataDao.getData(url, request_tag);
	}


	@Override
	public void getPriceTrend(String url, String request_tag) {
		if (getPriceTrendDataDao == null)
		{
			getPriceTrendDataDao = new GetDataDAO<PriceTrendData>(context,
					PriceTrendData.class, new DefaultAOCallBack<PriceTrendData>(
					interfaceView, context)
			{
				@Override
				public void dealWithSuccess(PriceTrendData obj) {
					interfaceView.updateView(obj);
				}
			});
		}
		interfaceView.isShowDialog(false);
		setTagForRequest(request_tag);
		getPriceTrendDataDao.getData(url, request_tag);
	}
	
	/** 将当前的Request tag保存起来 */
	public void setTagForRequest(String tag)
	{
		if (!Tags.isEmpty())
		{
			Iterator<String> it = Tags.iterator();
			while (it.hasNext())
			{
				String sTag = it.next();
				if (!it.equals(tag))
				{// 若list中没有tag，添加
					if (!Tags.contains(sTag))
					{
						Tags.add(tag);
					}
				}
			}

		} else
		{
			Tags.add(tag);
		}
	}
	
	@Override
	public void finishRequest() {
		for (String tag : Tags)
		{
			BankSteelApplication.requestQueue.cancelAll(tag);
		}
	}

	

}

package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IAdvertManager;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.AdvertData;
import com.mysteel.banksteel.view.interfaceview.IAdvertManagerView;

public class AdvertManagerImpl implements IAdvertManager
{
	private Context context;
	private IAdvertManagerView interfaceView;
	private GetDataDAO<AdvertData> getAdvertData;
	public ArrayList<String> Tags = new ArrayList<String>();

	public AdvertManagerImpl(Context context)
	{
		this.context = context;
		interfaceView = (IAdvertManagerView) context;
	}

	public AdvertManagerImpl(Context context, IAdvertManagerView interfaceView)
	{
		this.context = context;
		this.interfaceView = interfaceView;
	}

	@Override
	public void getIAdvert(String url, String request_tag)
	{
		if (getAdvertData == null)
		{
			getAdvertData = new GetDataDAO<AdvertData>(context,
					AdvertData.class, new DefaultAOCallBack<AdvertData>(
							interfaceView, context)
					{
						@Override
						public void dealWithSuccess(AdvertData obj)
						{
							interfaceView.dealAdvert(obj);
						}
					});

		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getAdvertData.getData(url, request_tag);
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
	public void finishRequest()
	{
		for (String tag : Tags)
		{
			BankSteelApplication.requestQueue.cancelAll(tag);
		}
	}
}

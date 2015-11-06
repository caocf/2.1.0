package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IEvaluate;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.view.interfaceview.IEvaluateView;

/**
 * 
 * 评价接口实现类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-21 下午4:18:02
 */
public class EvaluateImpl implements IEvaluate
{

	private IEvaluateView viewInterface;
	private Context context;
	private GetDataDAO<BaseData> getBaseDao;
	public ArrayList<String> Tags = new ArrayList<String>();

	public EvaluateImpl(Context context)
	{
		this.context = context;
		this.viewInterface = (IEvaluateView) context;
	}

	@Override
	public void getEvaluate(String url, String request_tag)
	{
		if (getBaseDao == null)
		{
			getBaseDao = new GetDataDAO<BaseData>(context, BaseData.class,
					new DefaultAOCallBack<BaseData>(viewInterface, context)
					{

						@Override
						public void dealWithSuccess(BaseData obj)
						{
							viewInterface.updateView(obj);
							viewInterface.isShowDialog(false);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getBaseDao.getData(url, request_tag);
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

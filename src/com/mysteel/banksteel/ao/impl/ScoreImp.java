package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IScoreCenter;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.ScoreConvert;
import com.mysteel.banksteel.entity.ScoreRecord;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.view.interfaceview.IScoreView;

/**
 * 积分功能模块
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-14 下午5:05:19
 */
public class ScoreImp implements IScoreCenter
{
	private IScoreView viewInterface;
	private Context context;
	private GetDataDAO<BaseData> getScoreOrderDataDao;
	private GetDataDAO<ScoreRecord> getScoreDataDao;
	private GetDataDAO<ScoreConvert> getScoreConvertDataDao;
	private GetDataDAO<SearchMyScoreData> getScoreSearchDataDao;
	private GetDataDAO<EarnScoreData> getScoreEarnDataDao;
	public ArrayList<String> Tags = new ArrayList<String>();

	public ScoreImp(Context context)
	{
		this.context = context;
		viewInterface = (IScoreView) context;
	}

	public ScoreImp(Context context, IScoreView viewInterface)
	{
		this.context = context;
		this.viewInterface = viewInterface;
	}

	@Override
	public void getScoreRecord(String url, String request_tag)
	{
		if (getScoreDataDao == null)
		{
			getScoreDataDao = new GetDataDAO<ScoreRecord>(context,
					ScoreRecord.class, new DefaultAOCallBack<ScoreRecord>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(ScoreRecord obj)
						{
							// TODO Auto-generated method stub
							viewInterface.updateView(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getScoreDataDao.getData(url, request_tag);

	}

	@Override
	public void getScoreConvert(String url, String request_tag)
	{
		if (getScoreConvertDataDao == null)
		{
			getScoreConvertDataDao = new GetDataDAO<ScoreConvert>(context,
					ScoreConvert.class, new DefaultAOCallBack<ScoreConvert>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(ScoreConvert obj)
						{
							viewInterface.updateView(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getScoreConvertDataDao.getData(url, request_tag);
	}

	@Override
	public void getSearchScore(String url, String request_tag)
	{
		if (getScoreSearchDataDao == null)
		{
			getScoreSearchDataDao = new GetDataDAO<SearchMyScoreData>(context,
					SearchMyScoreData.class,
					new DefaultAOCallBack<SearchMyScoreData>(viewInterface,
							context)
					{

						@Override
						public void dealWithSuccess(SearchMyScoreData obj)
						{
							viewInterface.searchMyScore(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getScoreSearchDataDao.getData(url, request_tag);
	}

	@Override
	public void getScoreOrder(String url, String request_tag)
	{
		if (getScoreOrderDataDao == null)
		{
			getScoreOrderDataDao = new GetDataDAO<BaseData>(context,
					BaseData.class, new DefaultAOCallBack<BaseData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(BaseData obj)
						{
							viewInterface.updateView(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getScoreOrderDataDao.getData(url, request_tag);
	}

	@Override
	public void getEarnScore(String url, String request_tag)
	{
		if (getScoreEarnDataDao == null)
		{
			getScoreEarnDataDao = new GetDataDAO<EarnScoreData>(context,
					EarnScoreData.class, new DefaultAOCallBack<EarnScoreData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(EarnScoreData obj)
						{
							viewInterface.earnScore(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		getScoreEarnDataDao.getData(url, request_tag);
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

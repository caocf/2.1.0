package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.ISystemManage;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CheckUpdateData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;

/**
 * 系统管理实现类
 * 
 * @author 68
 * 
 */
public class SystemManagerImpl implements ISystemManage
{
	private Context context;
	private GetDataDAO<MessageCenterData> getMessageCenterData;
	private GetDataDAO<SignData> getSignData;
	private GetDataDAO<CheckUpdateData> getCheckUpdateData;
	private GetDataDAO<DeleteMessageData> getDeleteMessage;
	private ISystemManagerView interfaceView;
	public ArrayList<String> Tags = new ArrayList<String>();

	public SystemManagerImpl(Context context)
	{
		this.context = context;
		interfaceView = (ISystemManagerView) context;
	}
	
	public SystemManagerImpl(Context context,ISystemManagerView interfaceView)
	{
		this.context = context;
		this.interfaceView = interfaceView;
	}

	@Override
	public void checkUpDate(String url, String request_tag)
	{
		if (getCheckUpdateData == null)
		{
			getCheckUpdateData = new GetDataDAO<CheckUpdateData>(context,
					CheckUpdateData.class, new DefaultAOCallBack<CheckUpdateData>(
							interfaceView, context)
					{

						@Override
						public void dealWithSuccess(CheckUpdateData obj)
						{
							if ("sys.checkUpdate".equals(obj.getCmd()))// 7.3检查跟新
							{
								interfaceView.checkUpDate(obj);
							} else if ("sys.suggest".equals(obj.getCmd()))// 7.5意见反馈
							{
								interfaceView.getSuggest(obj);
							} else if ("sys.push".equals(obj.getCmd()))
							{// 7.6 是否推送
								interfaceView.getIsPush(obj);
							}
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getCheckUpdateData.getData(url, request_tag);
	}

	@Override
	public void getSign(String url, String request_tag)
	{
		if (getSignData == null)
		{
			getSignData = new GetDataDAO<SignData>(context, SignData.class,
					new DefaultAOCallBack<SignData>(interfaceView, context)
					{

						@Override
						public void dealWithSuccess(SignData obj)
						{
							if ("sys.sign".equals(obj.getCmd()))
							{
								// 7.4 签到送积分(sys.sign)
								interfaceView.getSign(obj);
							} else if ("sys.rule".equals(obj.getCmd()))
							{
								// 7.8 平台交易规则(sys.rule)
								interfaceView.getSign(obj);
							}
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getSignData.getData(url, request_tag);
	}

	@Override
	public void messageCenter(String url, String request_tag)
	{
		if (getMessageCenterData == null)
		{
			getMessageCenterData = new GetDataDAO<MessageCenterData>(context,
					MessageCenterData.class,
					new DefaultAOCallBack<MessageCenterData>(interfaceView,
							context)
					{

						@Override
						public void dealWithSuccess(MessageCenterData obj)
						{
							interfaceView.getHistoryMessage(obj);
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getMessageCenterData.getData(url, request_tag);
	}
	
	@Override
	public void deleteMessage(String url, String request_tag) 
	{
		if (getDeleteMessage == null)
		{
			getDeleteMessage = new GetDataDAO<DeleteMessageData>(context,
					DeleteMessageData.class,
					new DefaultAOCallBack<DeleteMessageData>(interfaceView,
							context)
					{

						@Override
						public void dealWithSuccess(DeleteMessageData obj)
						{
							interfaceView.getDeleteMessage(obj);
						}
					});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getDeleteMessage.getData(url, request_tag);
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

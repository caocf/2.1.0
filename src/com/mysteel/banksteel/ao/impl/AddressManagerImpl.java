package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IAddressManage;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.view.interfaceview.IAddressManageView;

/**
 * 地址管理实现类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 下午4:46:42
 */
public class AddressManagerImpl implements IAddressManage
{

	private IAddressManageView viewInterface;
	private Context context;

	private GetDataDAO<BaseData> getAddAddress;
	private GetDataDAO<AddressListData> addressListDataDao;
	private GetDataDAO<BaseData> defaultAddressDataDao;
	public ArrayList<String> Tags = new ArrayList<String>();

	public AddressManagerImpl(Context context)
	{
		this.context = context;
		viewInterface = (IAddressManageView) context;
	}

	@Override
	public void getAddAddress(String url, String request_tag)
	{
		if (getAddAddress == null)
		{
			getAddAddress = new GetDataDAO<BaseData>(context, BaseData.class,
					new DefaultAOCallBack<BaseData>(viewInterface, context)
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
		getAddAddress.getData(url, request_tag);

	}

	@Override
	public void setDefualtAddress(String url, String request_tag)
	{
		if (defaultAddressDataDao == null)
		{
			defaultAddressDataDao = new GetDataDAO<BaseData>(context,
					BaseData.class, new DefaultAOCallBack<BaseData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(BaseData obj)
						{
							if ("address.delAddress".equalsIgnoreCase(obj
									.getCmd()))
							{// 2.6:删除地址
								viewInterface.delAddress(obj);
							} else if ("address.setDefaultAddress"
									.equalsIgnoreCase(obj.getCmd()))
							{// 2.5:默认收货地址
								viewInterface.setDefaultAddress(obj);
							}
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		defaultAddressDataDao.getData(url, request_tag);
	}

	@Override
	public void getAddressList(String url, String request_tag)
	{
		if (addressListDataDao == null)
		{
			addressListDataDao = new GetDataDAO<AddressListData>(context,
					AddressListData.class,
					new DefaultAOCallBack<AddressListData>(viewInterface,
							context)
					{

						@Override
						public void dealWithSuccess(AddressListData obj)
						{
							viewInterface.setAddressList(obj);
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		addressListDataDao.getData(url, request_tag);
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

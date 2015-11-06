package com.mysteel.banksteel.ao;

import android.content.Context;
import android.text.TextUtils;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBaseViewInterface;

public abstract class DefaultAOCallBack<T> implements AOCallBack<T>
{
	private Context context;
	private IBaseViewInterface iViewInterface;

	public DefaultAOCallBack(IBaseViewInterface iViewInterface, Context context)
	{
		this.iViewInterface = iViewInterface;
		this.context = context;
	}

	@Override
	public void dealWithTrue(T obj)
	{
		iViewInterface.isShowDialog(false);
		dealWithSuccess(obj);
	}

	public abstract void dealWithSuccess(T obj);

	@Override
	public void dealWithFalse(String str)
	{
		iViewInterface.isShowDialog(false);
		if(!TextUtils.isEmpty(str))
		{
			Tools.showToast(context, str);
		}
	}

	@Override
	public void dealWithException(String error)
	{
		iViewInterface.isShowDialog(false);
		if(!TextUtils.isEmpty(error))
		{
			Tools.showToast(context, error);
		}
	}
}
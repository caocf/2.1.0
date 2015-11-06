package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.EventBus;

/**
 * 基本的Fragment
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-5 上午9:42:39
 */
public class BaseFragment extends Fragment
{

	protected String mTag;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * get tag
	 * 
	 * @return mTag
	 */
	public String getTags()
	{
		return mTag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		EventBus.getDefault().register(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	/** 键盘点击事件。 */
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}
}

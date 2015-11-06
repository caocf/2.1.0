package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-5 上午9:46:23
 */
public class SupportFragmentAdapter extends FragmentPagerAdapter
{
	/** fragments. */
	private ArrayList<Fragment> mFragments;

	public SupportFragmentAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments)
	{
		super(fm);
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return mFragments.get(arg0);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mFragments.size();
	}
}

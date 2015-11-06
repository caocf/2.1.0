package com.mysteel.banksteel.view.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.view.activity.MainActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 引导页的adapter
 * 
 * @author zoujian
 * 
 */
public class ViewPagersAdapter extends PagerAdapter
{

	private List<View> views;
	private Activity activity;
	private ImageView mStartWeiboImageButton;

	public ViewPagersAdapter(List<View> views, Activity activity)
	{
		this.views = views;
		this.activity = activity;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2)
	{
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0)
	{
	}

	// 获得当前界面数
	@Override
	public int getCount()
	{
		if (views != null)
		{
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1)
	{
		((ViewPager) arg0).addView(views.get(arg1), 0);
		if (arg1 == views.size() - 1)
		{

			mStartWeiboImageButton = (ImageView) arg0.findViewById(R.id.button_view);
			mStartWeiboImageButton.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					setGuided();
					goHome();

				}

			});

		}
		return views.get(arg1);
	}

	private void goHome()
	{
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 设置已经引导过了，下次启动不用再次引导
	 */
	private void setGuided()
	{
		SharePreferenceUtil.setValue(activity,
				Constants.PREFERENCES_WELCOME_FALST, true);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1)
	{
	}

	@Override
	public Parcelable saveState()
	{
		return null;
	}

	@Override
	public void startUpdate(View arg0)
	{
	}

}

package com.mysteel.banksteel.view.ui;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mysteel.banksteel.util.Tools;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-25 下午4:33:09
 */
public class MySlidingPaneLayout extends SlidingPaneLayout
{

	Context mContext;
	private int status = 1;//默认为关闭的

	public MySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mContext = context;
		init(context);
	}

	public MySlidingPaneLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		init(context);
	}

	public MySlidingPaneLayout(Context context)
	{
		super(context);
		mContext = context;
		init(context);
	}

	private void init(Context context)
	{
		EventBus.getDefault().register(this);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0)
	{
		if (Tools.px2dip(mContext, arg0.getY()) > 50
				&& Tools.px2dip(mContext, arg0.getY()) < 250 && status == 1)
		{
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Subscriber(tag = "MySlidingPaneLayout")
	public void getSlidingPaneLayoutCode(int code)
	{
		status = code;
	}

}

package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 自定义可以滑动的RelativeLayout, 类似于IOS的滑动删除页面效果，当我们要使用
 * 此功能的时候，需要将该Activity的顶层布局设置为SildingFinishLayout
 */
public class SildingFinishLayout extends RelativeLayout
{
	/**
	 * SildingFinishLayout布局的父布局
	 */
	private ViewGroup mParentView;
	private int mTouchSlop;
	private int downX;
	private int downY;
	private int tempX;
	private Scroller mScroller;
	/**
	 * SildingFinishLayout的宽度
	 */
	private int viewWidth;

	private boolean isSilding;

	private OnSildingFinishListener onSildingFinishListener;
	private boolean isFinish;

	public SildingFinishLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SildingFinishLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScroller = new Scroller(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{

		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			downX = tempX = (int) ev.getRawX();
			downY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getRawX();
			if (Math.abs(moveX - downX) > mTouchSlop
					&& Math.abs((int) ev.getRawY() - downY) < mTouchSlop)
			{
				return true;
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int deltaX = tempX - moveX;
			tempX = moveX;
			if (Math.abs(moveX - downX) > mTouchSlop
					&& Math.abs((int) event.getRawY() - downY) < mTouchSlop)
			{
				isSilding = true;
			}

			if (moveX - downX >= 0 && isSilding)
			{
				mParentView.scrollBy(deltaX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
			isSilding = false;
			if (mParentView.getScrollX() <= -viewWidth / 2)
			{
				isFinish = true;
				scrollRight();
			} else
			{
				scrollOrigin();
				isFinish = false;
			}
			break;
		}

		return true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			mParentView = (ViewGroup) this.getParent();
			viewWidth = this.getWidth();
		}
	}

	/**
	 * 设置OnSildingFinishListener, 在onSildingFinish()方法中finish Activity
	 * 
	 * @param onSildingFinishListener
	 */
	public void setOnSildingFinishListener(
			OnSildingFinishListener onSildingFinishListener)
	{
		this.onSildingFinishListener = onSildingFinishListener;
	}

	/**
	 * 滚动出界面
	 */
	private void scrollRight()
	{
		final int delta = (viewWidth + mParentView.getScrollX());
		mScroller.startScroll(mParentView.getScrollX(), 0, -delta + 1, 0,
				Math.abs(delta));
		postInvalidate();
	}

	/**
	 * 滚动到起始位置
	 */
	private void scrollOrigin()
	{
		int delta = mParentView.getScrollX();
		mScroller.startScroll(mParentView.getScrollX(), 0, -delta, 0,
				Math.abs(delta));
		postInvalidate();
	}

	/**
	 * Android框架提供了 computeScroll()方法去控制这个流程。
	 * 
	 */
	@Override
	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();

			if (mScroller.isFinished() && isFinish)
			{

				if (onSildingFinishListener != null)
				{
					onSildingFinishListener.onSildingFinish();
				} else
				{
					scrollOrigin();
					isFinish = false;
				}
			}
		}
	}

	public interface OnSildingFinishListener
	{
		public void onSildingFinish();
	}

}

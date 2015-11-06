package com.mysteel.banksteel.view.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.mysteel.banksteeltwo.R;

/**
 * Switch 开关
 * 
 * @author 68
 * 
 */
public class SwitchButton extends View
{

	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGTH = 45;
	public static final int PER_POST_TIME = 0;
	public static final int CLICK = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;

	private int mSelectBg;
	private int mUnSelectBg;
	private int mBorderColor;
	private int mSelectCirlceColor;
	private int mUnSelectCircleColor;
	private boolean isChecked;
	private Paint mPaint;
	private int mWidth;
	private int mHeight;
	private int mClickTimeout;
	private int mTouchSlop;
	private float mAnimMove;
	private final float VELOCITY = 350;
	private float firstDownX;
	private float firstDownY;
	private float lastDownX;
	private int mCirclePostion;
	private int mStartCirclePos;
	private int mEndCirclePos;
	private boolean isScroll;
	private int status;
	private Handler mHander;
	private AnimRunnable mAnim;

	private OnCheckChangeListener mOnCheckChangeListener;

	public interface OnCheckChangeListener
	{

		void OnCheck(SwitchButton switchButton, boolean isChecked);
	}

	public SwitchButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Resources res = getResources();
		int defaultSelectBg = res
				.getColor(R.color.default_switch_button_select_bg);
		int defaultUnSelectBg = res
				.getColor(R.color.default_switch_button_unselect_bg);
		int defaultBorderColor = res
				.getColor(R.color.default_switch_button_border_color);
		int defaultSelectCircleColor = res
				.getColor(R.color.default_switch_button_select_color);
		int defaultUnSelectCircleColor = res
				.getColor(R.color.default_switch_button_unselect_color);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SwitchButton);
		mSelectBg = a.getColor(R.styleable.SwitchButton_select_bg,
				defaultSelectBg);
		mUnSelectBg = a.getColor(R.styleable.SwitchButton_unselect_bg,
				defaultUnSelectBg);
		mBorderColor = a.getColor(R.styleable.SwitchButton_select_border_color,
				defaultBorderColor);
		mSelectCirlceColor = a.getColor(
				R.styleable.SwitchButton_select_cricle_color,
				defaultSelectCircleColor);
		mUnSelectCircleColor = a.getColor(
				R.styleable.SwitchButton_unselect_cricle_color,
				defaultUnSelectCircleColor);
		isChecked = a.getBoolean(R.styleable.SwitchButton_isChecked, false);
		a.recycle();
		initView(context);
	}

	private void initView(Context context)
	{

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final float density = getResources().getDisplayMetrics().density;
		mAnimMove = (int) (VELOCITY * density + 0.5f) / 100;
		mClickTimeout = ViewConfiguration.getPressedStateDuration()
				+ ViewConfiguration.getTapTimeout();
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mHander = new Handler();
		mAnim = new AnimRunnable();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		mPaint.setColor(mBorderColor);
		mPaint.setStyle(Paint.Style.FILL);
		RectF rect = new RectF(0, 0, mWidth, mHeight);
		canvas.drawRoundRect(rect, mHeight / 2, mHeight / 2, mPaint);
		mPaint.setColor(isChecked ? mSelectBg : mUnSelectBg);
		RectF innerRect = new RectF(1, 1, mWidth - 1, mHeight - 1);
		canvas.drawRoundRect(innerRect, mHeight / 2 - 1, mHeight / 2 - 1,
				mPaint);
		mPaint.setColor(isChecked ? mSelectCirlceColor : mUnSelectCircleColor);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(mCirclePostion, mHeight / 2, mHeight / 2 - 2, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{

		float x = event.getX();
		float y = event.getY();
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if (isScroll)
			{
				return false;
			}
			firstDownX = x;
			firstDownY = y;
			lastDownX = x;
			mCirclePostion = isChecked ? mEndCirclePos : mStartCirclePos;
			break;
		case MotionEvent.ACTION_MOVE:
			float delaX = x - lastDownX;
			setCirclePositon(delaX);
			lastDownX = x;
			isChecked = mCirclePostion < mWidth / 2 ? false : true;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			float totalX = x - firstDownX;
			float totalY = y - firstDownY;
			float time = event.getEventTime() - event.getDownTime();
			if (totalX < mTouchSlop && totalY < mTouchSlop
					&& time < mClickTimeout)
			{
				status = CLICK;
				startAutoScroll();
			} else
			{
				delaX = x - lastDownX;
				setCirclePositon(delaX);
				status = mCirclePostion < mWidth / 2 ? LEFT : RIGHT;
				startAutoScroll();
			}
			break;
		}
		return true;
	}

	private void startAutoScroll()
	{

		isScroll = true;
		mHander.postDelayed(mAnim, PER_POST_TIME);

	}

	class AnimRunnable implements Runnable
	{

		@Override
		public void run()
		{

			mHander.postDelayed(this, PER_POST_TIME);
			moveView();
		}

		private void moveView()
		{

			if ((status == CLICK && isChecked) || status == LEFT)
			{
				mCirclePostion -= mAnimMove;
				if (mCirclePostion < mHeight / 2)
				{
					mCirclePostion = mHeight / 2;
					stopView(false);
				}
			} else if ((status == CLICK && !isChecked) || status == RIGHT)
			{

				mCirclePostion += mAnimMove;
				if (mCirclePostion > mWidth - mHeight / 2)
				{
					mCirclePostion = mWidth - mHeight / 2;
					stopView(true);
				}
			}
			invalidate();

		}

		private void stopView(boolean endChecked)
		{

			mHander.removeCallbacks(mAnim);
			isScroll = false;
			isChecked = endChecked;

			if (mOnCheckChangeListener != null)
			{
				mOnCheckChangeListener.OnCheck(SwitchButton.this, isChecked);
			}
		}

	}

	private synchronized void setCirclePositon(float delaX)
	{

		int pos = (int) (mCirclePostion + delaX);
		if (pos < mHeight / 2)
		{
			mCirclePostion = mHeight / 2;
		} else if (pos > mWidth - mHeight / 2)
		{
			mCirclePostion = mWidth - mHeight / 2;
		} else
		{
			mCirclePostion = pos;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode == MeasureSpec.UNSPECIFIED
				|| widthMode == MeasureSpec.AT_MOST)
		{
			mWidth = DEFAULT_WIDTH;
		} else
		{
			mWidth = MeasureSpec.getSize(widthMeasureSpec);
		}
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode == MeasureSpec.UNSPECIFIED
				|| heightMode == MeasureSpec.AT_MOST)
		{
			mHeight = DEFAULT_HEIGTH;
		} else
		{
			mHeight = MeasureSpec.getSize(heightMeasureSpec);
		}
		mStartCirclePos = mHeight / 2;
		mEndCirclePos = mWidth - mHeight / 2;
		mCirclePostion = isChecked ? mEndCirclePos : mStartCirclePos;
		setMeasuredDimension(mWidth, mHeight);
	}

	public boolean isChecked()
	{
		return isChecked;
	}

	public void setChecked(boolean isChecked)
	{

		this.isChecked = isChecked;
		invalidate();
	}

	public void setOnCheckChangeListener(
			OnCheckChangeListener onCheckChangeListener)
	{

		this.mOnCheckChangeListener = onCheckChangeListener;
	}
}

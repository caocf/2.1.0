package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author:huoguodong
 * @date：2015-5-6 上午10:42:16
 */

public class LetterList extends View
{

	private OnTouchingLetterChangedListener listener;

	/** 所有的索引字母 */
	private String[] letter =
	{ "↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z", "#" };
	private int letterLength;

	private int choosePosition = -1; // 选中的字母位置
	private boolean isShowBkg = false; // 默认没有背景色
	private Paint paint = new Paint();

	/** 组件宽高 */
	private int width;
	private int height;
	private int letterHeight;

	private void caculateSize()
	{
		letterLength = letter.length;
		height = getHeight();
		width = getWidth();
		letterHeight = height / letterLength;
	}

	public LetterList(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public LetterList(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public LetterList(Context context)
	{
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (isShowBkg)
		{
			canvas.drawColor(Color.parseColor("#F0F0F0"));
		}
		caculateSize();
		for (int i = 0; i < letterLength; i++)
		{
			paint.setColor(Color.parseColor("#7C7C7C"));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(35);

			/*
			 * if (i == choosePosition) {
			 * paint.setColor(Color.parseColor("#3399ff"));
			 * paint.setFakeBoldText(true); }
			 */
			/** 字体位置x y 坐标 */
			float xPos = width / 2 - paint.measureText(letter[i]) / 2;
			float yPos = letterHeight * i + letterHeight;
			canvas.drawText(letter[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		final int action = event.getAction();
		final float y = event.getY(); // 点击位置Y坐标
		final int oldChoose = choosePosition;
		final int chooseIndex = (int) (y / getHeight() * letterLength);

		switch (action)
		{
		case MotionEvent.ACTION_DOWN:
			isShowBkg = true;
			if (oldChoose != chooseIndex && listener != null)
			{
				if (chooseIndex >= 0 && chooseIndex < letterLength)
				{
					listener.onTouchingLetterChanged(letter[chooseIndex]);
					choosePosition = chooseIndex;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != chooseIndex && listener != null)
			{
				if (chooseIndex >= 0 && chooseIndex < letterLength)
				{
					listener.onTouchingLetterChanged(letter[chooseIndex]);
					choosePosition = chooseIndex;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isShowBkg = false;
			choosePosition = -1;
			invalidate();
			listener.onTouchingLetterUp();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener listener)
	{
		this.listener = listener;
	}

	public interface OnTouchingLetterChangedListener
	{
		public void onTouchingLetterChanged(String s);

		public void onTouchingLetterUp();
	}
}

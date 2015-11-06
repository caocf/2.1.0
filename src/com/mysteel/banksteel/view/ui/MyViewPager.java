package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager设置是否可滑动
 * Created by zoujian on 15/8/3.
 */
public class MyViewPager extends ViewPager
{
    private boolean scrollble = false;

    public MyViewPager(Context context)
    {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (scrollble)
        {
            return super.onTouchEvent(ev);
        }
        return scrollble;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (scrollble)
        {
            return super.onInterceptTouchEvent(ev);
        }
        return scrollble;
    }

    public boolean isScrollble()
    {
        return scrollble;
    }

    public void setScrollble(boolean scrollble)
    {
        this.scrollble = scrollble;
    }
}

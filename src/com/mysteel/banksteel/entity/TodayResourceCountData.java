package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * Created by zoujian on 15/9/8.
 */
public class TodayResourceCountData extends BaseData
{
    private String todayResourceCount;


    public void setTodayResourceCount(String todayResourceCount)
    {
        this.todayResourceCount = todayResourceCount;
    }

    public String getTodayResourceCount()
    {
        if (TextUtils.isEmpty(todayResourceCount))
        {
            return "";
        }
        return todayResourceCount;
    }
}

package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 每日签到
 * 
 * @author 68
 * 
 */
public class SignData extends BaseData
{
	private static final long serialVersionUID = -4274499366375519426L;
	private String data;

	public String getData()
	{
		if (TextUtils.isEmpty(data))
		{
			return "";
		}
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

}

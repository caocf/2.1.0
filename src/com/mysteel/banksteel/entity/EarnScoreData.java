package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 68
 *	赚取积分协议
 */
public class EarnScoreData extends BaseData
{
	private static final long serialVersionUID = -7313258524165434786L;

	private String data; //用户总积分数

	public String getData()
	{
		if(TextUtils.isEmpty(data)){
			return "";
		}
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

}

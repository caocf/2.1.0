package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 68 注册entity
 */
public class RegisterData extends BaseData
{
	private static final long serialVersionUID = 1812495822793054247L;

	private String data;

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

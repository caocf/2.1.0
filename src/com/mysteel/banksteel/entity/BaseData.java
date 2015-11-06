package com.mysteel.banksteel.entity;

import java.io.Serializable;

import android.text.TextUtils;

public class BaseData implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String error;// 错误原因
	private String timestamp;// 时间戳
	private String cmd;
	private String status;// 返回状态

	public String getError()
	{
		if (TextUtils.isEmpty(error))
		{
			return "";
		}
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

}

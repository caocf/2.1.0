package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 68 修改个人信息,公司信息实体类
 */
public class EditInfoData extends BaseData
{

	private static final long serialVersionUID = 5793689092949523738L;

	private Data data;

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public class Data
	{
		private String score;
		private String msg;

		public String getScore()
		{
			if (TextUtils.isEmpty(score))
			{
				return "";
			}
			return score;
		}

		public void setScore(String score)
		{
			this.score = score;
		}

		public String getMsg()
		{
			if (TextUtils.isEmpty(msg))
			{
				return "";
			}
			return msg;
		}

		public void setMsg(String msg)
		{
			this.msg = msg;
		}

	}
}

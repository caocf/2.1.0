package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 上传头像
 * 
 * @author 68
 * 
 */
public class UploadAvatarData extends BaseData
{
	private static final long serialVersionUID = -6382172661890351301L;
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
		private String headerPhoto;
		private String score;
		private String msg;

		public String getHeaderPhoto()
		{
			if (TextUtils.isEmpty(headerPhoto))
			{
				return "";
			}
			return headerPhoto;
		}

		public void setHeaderPhoto(String headerPhoto)
		{
			this.headerPhoto = headerPhoto;
		}

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

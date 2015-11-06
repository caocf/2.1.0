package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-6-24 下午5:12:55
 */
@SuppressWarnings("serial")
public class CheckUpdateData extends BaseData
{

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
		private String download;
		private String ostype;
		private String version;

		public String getDownload()
		{
			if (TextUtils.isEmpty(download))
			{
				return "";
			}
			return download;
		}

		public void setDownload(String download)
		{
			this.download = download;
		}

		public String getOstype()
		{
			if (TextUtils.isEmpty(ostype))
			{
				return "";
			}
			return ostype;
		}

		public void setOstype(String ostype)
		{
			this.ostype = ostype;
		}

		public String getVersion()
		{
			if (TextUtils.isEmpty(version))
			{
				return "";
			}
			return version;
		}

		public void setVersion(String version)
		{
			this.version = version;
		}

	}
}

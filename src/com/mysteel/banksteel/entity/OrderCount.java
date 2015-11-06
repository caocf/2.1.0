package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-6-8 下午5:16:51
 */
public class OrderCount extends BaseData
{

	private static final long serialVersionUID = -8035601748894326417L;

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
		private String auditNotCount;// 审核不通过数量
		private String toUploadCount;// 待上传凭证数量

		public String getAuditNotCount()
		{
			if (TextUtils.isEmpty(auditNotCount))
			{
				return "";
			}
			return auditNotCount;
		}

		public void setAuditNotCount(String auditNotCount)
		{
			this.auditNotCount = auditNotCount;
		}

		public String getToUploadCount()
		{
			if (TextUtils.isEmpty(toUploadCount))
			{
				return "";
			}
			return toUploadCount;
		}

		public void setToUploadCount(String toUploadCount)
		{
			this.toUploadCount = toUploadCount;
		}

	}

}

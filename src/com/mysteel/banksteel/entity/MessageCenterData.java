package com.mysteel.banksteel.entity;

import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 消息中心数据
 * 
 * @author 68
 * 
 */
public class MessageCenterData extends BaseData
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7595621126385093131L;
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
		private String count;
		private ArrayList<Datas> datas;
		private String page;
		private String pagenum;
		private String size;

		public String getCount()
		{
			if(TextUtils.isEmpty(count)){
				return "";
			}
			return count;
		}

		public void setCount(String count)
		{
			this.count = count;
		}

		public ArrayList<Datas> getDatas()
		{	
			return datas;
		}

		public void setDatas(ArrayList<Datas> datas)
		{
			this.datas = datas;
		}

		public String getPage()
		{
			if(TextUtils.isEmpty(page)){
				return "";
			}
			return page;
		}

		public void setPage(String page)
		{
			this.page = page;
		}

		public String getPagenum()
		{
			if(TextUtils.isEmpty(pagenum)){
				return "";
			}
			return pagenum;
		}

		public void setPagenum(String pagenum)
		{
			this.pagenum = pagenum;
		}

		public String getSize()
		{
			if(TextUtils.isEmpty(size)){
				return "";
			}
			return size;
		}

		public void setSize(String size)
		{
			this.size = size;
		}

		public class Datas
		{
			private String content;
			private String createTime;
			private String id;
			private String lastAccess;
			private String sendeeMemberId;
			private String sendeeUserId;
			private String senderMemberId;
			private String senderUserId;
			private String serialVersionUID;
			private String type;
			private String version;
			private boolean isSelected; // 图片是否被选中


			public Datas(String content, String createTime)
			{
				this.content = content;
				this.createTime = createTime;
			}

			public String getContent()
			{
				if(TextUtils.isEmpty(content)){
					return "";
				}
				return content;
			}

			public void setContent(String content)
			{
				this.content = content;
			}

			public String getCreateTime()
			{
				if(TextUtils.isEmpty(createTime)){
					return "";
				}
				return createTime;
			}

			public void setCreateTime(String createTime)
			{
				this.createTime = createTime;
			}

			public String getId()
			{
				if(TextUtils.isEmpty(id)){
					return "";
				}
				return id;
			}

			public void setId(String id)
			{
				this.id = id;
			}

			public String getLastAccess()
			{
				if(TextUtils.isEmpty(lastAccess)){
					return "";
				}
				return lastAccess;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public String getSendeeMemberId()
			{
				if(TextUtils.isEmpty(sendeeMemberId)){
					return "";
				}
				return sendeeMemberId;
			}

			public void setSendeeMemberId(String sendeeMemberId)
			{
				this.sendeeMemberId = sendeeMemberId;
			}

			public String getSendeeUserId()
			{
				if(TextUtils.isEmpty(sendeeUserId)){
					return "";
				}
				return sendeeUserId;
			}

			public void setSendeeUserId(String sendeeUserId)
			{
				this.sendeeUserId = sendeeUserId;
			}

			public String getSenderMemberId()
			{
				if(TextUtils.isEmpty(senderMemberId)){
					return "";
				}
				return senderMemberId;
			}

			public void setSenderMemberId(String senderMemberId)
			{
				this.senderMemberId = senderMemberId;
			}

			public String getSenderUserId()
			{
				if(TextUtils.isEmpty(senderUserId)){
					return "";
				}
				return senderUserId;
			}

			public void setSenderUserId(String senderUserId)
			{
				this.senderUserId = senderUserId;
			}

			public String getSerialVersionUID()
			{
				if(TextUtils.isEmpty(serialVersionUID)){
					return "";
				}
				return serialVersionUID;
			}

			public void setSerialVersionUID(String serialVersionUID)
			{
				this.serialVersionUID = serialVersionUID;
			}

			public String getType()
			{
				if(TextUtils.isEmpty(type)){
					return "";
				}
				return type;
			}

			public void setType(String type)
			{
				this.type = type;
			}

			public String getVersion()
			{
				if(TextUtils.isEmpty(version)){
					return "";
				}
				return version;
			}

			public void setVersion(String version)
			{
				this.version = version;
			}
			
			public boolean getSelected() {
				return isSelected;
			}

			public void setSelected(boolean isSelected) {
				this.isSelected = isSelected;
			}

		}
	}
}

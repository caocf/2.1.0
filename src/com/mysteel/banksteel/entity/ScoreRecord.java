package com.mysteel.banksteel.entity;

import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 积分历史记录数据data
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-14 下午5:13:25
 */
public class ScoreRecord extends BaseData
{

	private static final long serialVersionUID = -7676616531301814775L;
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
		private String count;// 　　　(总条数)
		private String page;
		private String pagenum;// (总页数)
		private String size;
		private ArrayList<Records> records;

		public String getCount()
		{
			if (TextUtils.isEmpty(count))
			{
				return "";
			}
			return count;
		}

		public void setCount(String count)
		{
			this.count = count;
		}

		public String getPage()
		{
			if (TextUtils.isEmpty(page))
			{
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
			if (TextUtils.isEmpty(pagenum))
			{
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
			if (TextUtils.isEmpty(size))
			{
				return "";
			}
			return size;
		}

		public void setSize(String size)
		{
			this.size = size;
		}

		public ArrayList<Records> getRecords()
		{
			return records;
		}

		public void setRecords(ArrayList<Records> records)
		{
			this.records = records;
		}

		public class Records
		{
			private String createTime;// 创建时间
			private String id;
			private String score;// 金额,
			private String source;// 来源 0-网站 1 积分商城 2 钢银助手(PC) 4钢银助手(APP)
			private String sourceName;// 来源中文,
			private String status;// 状态 0-资源单 1-资源挂牌 2-求购单 3-订单 4-注册
									// 5-登录；6-关闭订单扣减获得积分 ; 7
									// -积分兑换；8-积分商城下单；9-积分订单用户关闭；10-积分订单管理员关闭；11-游戏消费积分；12游戏获得积分
			private String statusValue;// 状态中文

			public String getCreateTime()
			{
				if (TextUtils.isEmpty(createTime))
				{
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
				if (TextUtils.isEmpty(id))
				{
					return "";
				}
				return id;
			}

			public void setId(String id)
			{
				this.id = id;
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

			public String getSource()
			{
				if (TextUtils.isEmpty(source))
				{
					return "";
				}
				return source;
			}

			public void setSource(String source)
			{
				this.source = source;
			}

			public String getSourceName()
			{
				if (TextUtils.isEmpty(sourceName))
				{
					return "";
				}
				return sourceName;
			}

			public void setSourceName(String sourceName)
			{
				this.sourceName = sourceName;
			}

			public String getStatus()
			{
				if (TextUtils.isEmpty(status))
				{
					return "";
				}
				return status;
			}

			public void setStatus(String status)
			{
				this.status = status;
			}

			public String getStatusValue()
			{
				if (TextUtils.isEmpty(statusValue))
				{
					return "";
				}
				return statusValue;
			}

			public void setStatusValue(String statusValue)
			{
				this.statusValue = statusValue;
			}

		}
	}
}

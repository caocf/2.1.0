package com.mysteel.banksteel.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 积分兑换数据实体
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 上午9:35:30
 */
public class ScoreConvert extends BaseData
{

	private static final long serialVersionUID = 2860042577825473501L;
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
		private String page;
		private String pagenum;
		private String score;
		private String size;

		private ArrayList<Goods> goods;

		private ArrayList<Order> orders;

		public ArrayList<Order> getOrders()
		{
			return orders;
		}

		public void setOrders(ArrayList<Order> orders)
		{
			this.orders = orders;
		}

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

		public ArrayList<Goods> getGoods()
		{
			return goods;
		}

		public void setGoods(ArrayList<Goods> goods)
		{
			this.goods = goods;
		}

		public class Goods implements Serializable
		{
			private static final long serialVersionUID = -1807295656689580449L;
			private String goodsGangBeng;// 价格
			private String goodsName;// 名称
			private String goodsThumb;// 缩略图100*100
			private String id;// id
			private String number;// 库存

			public String getGoodsGangBeng()
			{
				if (TextUtils.isEmpty(goodsGangBeng))
				{
					return "";
				}
				return goodsGangBeng;
			}

			public void setGoodsGangBeng(String goodsGangBeng)
			{
				this.goodsGangBeng = goodsGangBeng;
			}

			public String getGoodsName()
			{
				if (TextUtils.isEmpty(goodsName))
				{
					return "";
				}
				return goodsName;
			}

			public void setGoodsName(String goodsName)
			{
				this.goodsName = goodsName;
			}

			public String getGoodsThumb()
			{
				if (TextUtils.isEmpty(goodsThumb))
				{
					return "";
				}
				return goodsThumb;
			}

			public void setGoodsThumb(String goodsThumb)
			{
				this.goodsThumb = goodsThumb;
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

			public String getNumber()
			{
				if (TextUtils.isEmpty(number))
				{
					return "";
				}
				return number;
			}

			public void setNumber(String number)
			{
				this.number = number;
			}

		}

		public class Order
		{
			private String id;
			private String consignee;// 收货人名称
			private String mobile;// 电话
			private String status;// 订单状态
			private String statusVaule;// 订单状态中文
			private String shippingStatus;// 配送状态
			private String shippingStatusValue;// 配送状态中文
			private String address;// 配送地址
			private String shippingTime;// 配送时间
			private String shippingName;// 物流名称
			private String shippingSn;// 物流单号
			private String gangBengAmount;// 使用钢镚数量
			private String goodsName;// 商品名称
			private String number;// 兑换的数量
			private String createTime;// 订单的时间
			private String goodsImg;// 商品图
			private String goodsId;//商品ID
			

			public String getGoodsId()
			{
				if (TextUtils.isEmpty(goodsId))
				{
					return "";
				}
				return goodsId;
			}

			public void setGoodsId(String goodsId)
			{
				this.goodsId = goodsId;
			}

			public String getGoodsImg()
			{
				if (TextUtils.isEmpty(goodsImg))
				{
					return "";
				}
				return goodsImg;
			}

			public void setGoodsImg(String goodsImg)
			{
				this.goodsImg = goodsImg;
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

			public String getConsignee()
			{
				if (TextUtils.isEmpty(consignee))
				{
					return "";
				}
				return consignee;
			}

			public void setConsignee(String consignee)
			{
				this.consignee = consignee;
			}

			public String getMobile()
			{
				if (TextUtils.isEmpty(mobile))
				{
					return "";
				}
				return mobile;
			}

			public void setMobile(String mobile)
			{
				this.mobile = mobile;
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

			public String getStatusVaule()
			{
				if (TextUtils.isEmpty(statusVaule))
				{
					return "";
				}
				return statusVaule;
			}

			public void setStatusVaule(String statusVaule)
			{
				this.statusVaule = statusVaule;
			}

			public String getShippingStatus()
			{
				if (TextUtils.isEmpty(shippingStatus))
				{
					return "";
				}
				return shippingStatus;
			}

			public void setShippingStatus(String shippingStatus)
			{
				this.shippingStatus = shippingStatus;
			}

			public String getShippingStatusValue()
			{
				if (TextUtils.isEmpty(shippingStatusValue))
				{
					return "";
				}
				return shippingStatusValue;
			}

			public void setShippingStatusValue(String shippingStatusValue)
			{
				this.shippingStatusValue = shippingStatusValue;
			}

			public String getAddress()
			{
				if (TextUtils.isEmpty(address))
				{
					return "";
				}
				return address;
			}

			public void setAddress(String address)
			{
				this.address = address;
			}

			public String getShippingTime()
			{
				if (TextUtils.isEmpty(shippingTime))
				{
					return "";
				}
				return shippingTime;
			}

			public void setShippingTime(String shippingTime)
			{
				this.shippingTime = shippingTime;
			}

			public String getShippingName()
			{
				if (TextUtils.isEmpty(shippingName))
				{
					return "";
				}
				return shippingName;
			}

			public void setShippingName(String shippingName)
			{
				this.shippingName = shippingName;
			}

			public String getShippingSn()
			{
				if (TextUtils.isEmpty(shippingSn))
				{
					return "";
				}
				return shippingSn;
			}

			public void setShippingSn(String shippingSn)
			{
				this.shippingSn = shippingSn;
			}

			public String getGangBengAmount()
			{
				if (TextUtils.isEmpty(gangBengAmount))
				{
					return "";
				}
				return gangBengAmount;
			}

			public void setGangBengAmount(String gangBengAmount)
			{
				this.gangBengAmount = gangBengAmount;
			}

			public String getGoodsName()
			{
				if (TextUtils.isEmpty(goodsName))
				{
					return "";
				}
				return goodsName;
			}

			public void setGoodsName(String goodsName)
			{
				this.goodsName = goodsName;
			}

			public String getNumber()
			{
				if (TextUtils.isEmpty(number))
				{
					return "";
				}
				return number;
			}

			public void setNumber(String number)
			{
				this.number = number;
			}

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

		}

	}
}

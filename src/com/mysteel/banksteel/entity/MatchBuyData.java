package com.mysteel.banksteel.entity;

import java.util.ArrayList;

import android.text.TextUtils;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-20 上午10:19:32
 */
public class MatchBuyData extends BaseData
{

	private static final long serialVersionUID = 1046071765058025746L;
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
		private String size;
		private ArrayList<Datas> datas;

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

		public ArrayList<Datas> getDatas()
		{
			return datas;
		}

		public void setDatas(ArrayList<Datas> datas)
		{
			this.datas = datas;
		}

		public class Datas
		{
			private String brand;// 产地/品牌
			private String breed;// 品名
			private String city;// 地区
			private String contacter;// 联系人
			private String id; // 匹配到的资源ID
			private String lastAccess;
			private String material;// 材质
			private String memberId;// 会员ID
			private String memberName;// 公司名称
			private String note;// 备注
			private String phone;// 联系电话
			private String price;// 求购价格
			private String publishTime;// 发布时间
			private String qty;// 数量
			private String resourceId;// 资源单ID 目前不用此ID 用的是上面的id
			private String serialVersionUID;
			private String spec;// 规格
			private String userId;// 用户ID
			private String version;
			private String warehouse;// 仓库
			private boolean flag;

			public boolean isFlag()
			{
				return flag;
			}

			public void setFlag(boolean flag)
			{
				this.flag = flag;
			}

			public String getBrand()
			{
				if (TextUtils.isEmpty(brand))
				{
					return "";
				}
				return brand;
			}

			public void setBrand(String brand)
			{
				this.brand = brand;
			}

			public String getBreed()
			{
				if (TextUtils.isEmpty(breed))
				{
					return "";
				}
				return breed;
			}

			public void setBreed(String breed)
			{
				this.breed = breed;
			}

			public String getCity()
			{
				if (TextUtils.isEmpty(city))
				{
					return "";
				}
				return city;
			}

			public void setCity(String city)
			{
				this.city = city;
			}

			public String getContacter()
			{
				if (TextUtils.isEmpty(contacter))
				{
					return "";
				}
				return contacter;
			}

			public void setContacter(String contacter)
			{
				this.contacter = contacter;
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

			public String getLastAccess()
			{
				if (TextUtils.isEmpty(lastAccess))
				{
					return "";
				}
				return lastAccess;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public String getMaterial()
			{
				if (TextUtils.isEmpty(material))
				{
					return "";
				}
				return material;
			}

			public void setMaterial(String material)
			{
				this.material = material;
			}

			public String getMemberId()
			{
				if (TextUtils.isEmpty(memberId))
				{
					return "";
				}
				return memberId;
			}

			public void setMemberId(String memberId)
			{
				this.memberId = memberId;
			}

			public String getMemberName()
			{
				if (TextUtils.isEmpty(memberName))
				{
					return "";
				}
				return memberName;
			}

			public void setMemberName(String memberName)
			{
				this.memberName = memberName;
			}

			public String getNote()
			{
				if (TextUtils.isEmpty(note))
				{
					return "";
				}
				return note;
			}

			public void setNote(String note)
			{
				this.note = note;
			}

			public String getPhone()
			{
				if (TextUtils.isEmpty(phone))
				{
					return "";
				}
				return phone;
			}

			public void setPhone(String phone)
			{
				this.phone = phone;
			}

			public String getPrice()
			{
				if (TextUtils.isEmpty(price))
				{
					return "";
				}
				return price;
			}

			public void setPrice(String price)
			{
				this.price = price;
			}

			public String getPublishTime()
			{
				if (TextUtils.isEmpty(publishTime))
				{
					return "";
				}
				return publishTime;
			}

			public void setPublishTime(String publishTime)
			{
				this.publishTime = publishTime;
			}

			public String getQty()
			{
				if (TextUtils.isEmpty(qty))
				{
					return "";
				}
				return qty;
			}

			public void setQty(String qty)
			{
				this.qty = qty;
			}

			public String getResourceId()
			{
				if (TextUtils.isEmpty(resourceId))
				{
					return "";
				}
				return resourceId;
			}

			public void setResourceId(String resourceId)
			{
				this.resourceId = resourceId;
			}

			public String getSerialVersionUID()
			{
				if (TextUtils.isEmpty(serialVersionUID))
				{
					return "";
				}
				return serialVersionUID;
			}

			public void setSerialVersionUID(String serialVersionUID)
			{
				this.serialVersionUID = serialVersionUID;
			}

			public String getSpec()
			{
				if (TextUtils.isEmpty(spec))
				{
					return "";
				}
				return spec;
			}

			public void setSpec(String spec)
			{
				this.spec = spec;
			}

			public String getUserId()
			{
				if (TextUtils.isEmpty(userId))
				{
					return "";
				}
				return userId;
			}

			public void setUserId(String userId)
			{
				this.userId = userId;
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

			public String getWarehouse()
			{
				if (TextUtils.isEmpty(warehouse))
				{
					return "";
				}
				return warehouse;
			}

			public void setWarehouse(String warehouse)
			{
				this.warehouse = warehouse;
			}

		}
	}
}

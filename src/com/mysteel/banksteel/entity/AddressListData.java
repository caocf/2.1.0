package com.mysteel.banksteel.entity;

import java.io.Serializable;
import java.util.List;

import android.text.TextUtils;

public class AddressListData extends BaseData
{

	private static final long serialVersionUID = 4108714505585984589L;
	private List<Data> data;

	public List<Data> getData()
	{
		return data;
	}

	public void setData(List<Data> data)
	{
		this.data = data;
	}

	@SuppressWarnings("serial")
	public class Data implements Serializable
	{

		private String address;// 街道
		private String city;// 市
		private String consignee;// 收货人
		private String district;// 区
		private String id;
		private String isDefault;
		private String lastAccess;
		private String mobile;// 电话
		private String phone;// 手机
		private String postcode;// 邮编
		private String province;// 省
		private String serialVersionUID;
		private String userId;

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

		public String getDistrict()
		{
			if (TextUtils.isEmpty(district))
			{
				return "";
			}
			return district;
		}

		public void setDistrict(String district)
		{
			this.district = district;
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

		public String getIsDefault()
		{
			if (TextUtils.isEmpty(isDefault))
			{
				return "";
			}
			return isDefault;
		}

		public void setIsDefault(String isDefault)
		{
			this.isDefault = isDefault;
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

		public String getPostcode()
		{
			if (TextUtils.isEmpty(postcode))
			{
				return "";
			}
			return postcode;
		}

		public void setPostcode(String postcode)
		{
			this.postcode = postcode;
		}

		public String getProvince()
		{
			if (TextUtils.isEmpty(province))
			{
				return "";
			}
			return province;
		}

		public void setProvince(String province)
		{
			this.province = province;
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

	}
}

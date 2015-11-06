package com.mysteel.banksteel.entity;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 
 * 首页下三条数据 与 我的求购中的数据结构不一样，又因为都要跳到同一个StayQuoteBuyActivity页面
 * 在值传递的方面有些难，为了避免新开页面，暂时采取直接抽取公共的字段，封装对象进行传递
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-28 下午4:27:06
 */
public class PublishBuyData implements Serializable
{
	private static final long serialVersionUID = 5804411390968049324L;

	/** 抢单人 */
	private String rushManagerName;
	/** 管理员交易次数 */
	private String dealCount;
	/** 管理员头像 */
	private String rushManagerHeader;
	/** 品种ID */
	private String breedId;
	/** 品名 */
	private String breed;
	/** 材质 */
	private String material;
	/** 规格 */
	private String spec;
	/** 产地品牌 */
	private String brand;
	/** 交货地 */
	private String city;
	/** 求购数量 */
	private String qty;
	/** 求购已发布的时间 */
	private String gapTime;
	/** 发布时间 */
	private String publishTime;
	/** 业务员联系号码 */
	private String rushManagerPhone;

	public String getRushManagerPhone()
	{
		if (TextUtils.isEmpty(rushManagerPhone))
		{
			return "";
		}
		return rushManagerPhone;
	}

	public void setRushManagerPhone(String rushManagerPhone)
	{
		this.rushManagerPhone = rushManagerPhone;
	}

	public String getRushManagerName()
	{
		if (TextUtils.isEmpty(rushManagerName))
		{
			return "";
		}
		return rushManagerName;
	}

	public void setRushManagerName(String rushManagerName)
	{
		this.rushManagerName = rushManagerName;
	}

	public String getDealCount()
	{
		if (TextUtils.isEmpty(dealCount))
		{
			return "";
		}
		return dealCount;
	}

	public void setDealCount(String dealCount)
	{
		this.dealCount = dealCount;
	}

	public String getRushManagerHeader()
	{
		if (TextUtils.isEmpty(rushManagerHeader))
		{
			return "";
		}
		return rushManagerHeader;
	}

	public void setRushManagerHeader(String rushManagerHeader)
	{
		this.rushManagerHeader = rushManagerHeader;
	}

	public String getBreedId()
	{
		if (TextUtils.isEmpty(breedId))
		{
			return "";
		}
		return breedId;
	}

	public void setBreedId(String breedId)
	{
		this.breedId = breedId;
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

	public String getGapTime()
	{
		if (TextUtils.isEmpty(gapTime))
		{
			return "";
		}
		return gapTime;
	}

	public void setGapTime(String gapTime)
	{
		this.gapTime = gapTime;
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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}

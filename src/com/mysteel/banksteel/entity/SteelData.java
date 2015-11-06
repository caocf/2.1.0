package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 求购:子品种数据实体类
 * 
 * @author:huoguodong
 * @date：2015-5-11 上午9:34:48
 */
public class SteelData extends BaseData
{
	private static final long serialVersionUID = 330081822833204147L;
	private String breedId; // 大分类品种Id
	private String name; // 大分类品种名称
	private List<SteelBean> sons; // 子分类钢材数据

	public String getBreedId()
	{
		if(TextUtils.isEmpty(breedId)){
			return "";
		}
		return breedId;
	}

	public void setBreedId(String breedId)
	{
		this.breedId = breedId;
	}

	public String getName()
	{
		if(TextUtils.isEmpty(name)){
			return "";
		}
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<SteelBean> getSons()
	{
		return sons;
	}

	public void setSons(List<SteelBean> sons)
	{
		this.sons = sons;
	}

	public class SteelBean implements Serializable
	{
		public SteelBean() {
			
		}
		
		public SteelBean(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		private String id; // 品种Id
		private String name; // 品种名称

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

		public String getName()
		{
			if(TextUtils.isEmpty(name)){
				return "";
			}
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}

package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 品种Id 品种名称 图片Id
 * 
 * @author:wushaoge
 * @date：2015-5-12 下午8:21:00
 */
public class Steel implements Serializable
{
	private static final long serialVersionUID = -1969424834741583607L;

	public String id; // 品种Id
	public String name; // 品种名称
	public int imageId = -1; // 图片Id

	public Steel()
	{
		super();
	}

	public Steel(String id, String name, int imageId)
	{
		super();
		this.id = id;
		this.name = name;
		this.imageId = imageId;
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

	public int getImageId()
	{
		return imageId;
	}

	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}
	
}

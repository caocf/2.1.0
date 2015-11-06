package com.mysteel.banksteel.entity;

import java.io.Serializable;

import android.text.TextUtils;

public class SteelType implements Serializable{
	

	private static final long serialVersionUID = -8180575301971792962L;

	public SteelType() {
		
	}
	
	public SteelType(String id, String name) {
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

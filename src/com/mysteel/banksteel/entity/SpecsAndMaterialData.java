package com.mysteel.banksteel.entity;

import java.util.List;

/**
 * 规格和材质公共的data实体类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-17 下午2:21:05
 */
public class SpecsAndMaterialData extends BaseData
{

	private static final long serialVersionUID = 2286110214548139272L;
	private List<String> data;

	public List<String> getData()
	{
		return data;
	}

	public void setData(List<String> data)
	{
		this.data = data;
	}
}

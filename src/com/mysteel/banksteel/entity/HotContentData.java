package com.mysteel.banksteel.entity;

import java.util.List;

/**
 * 热门内容实体类，热门城市，热门钢厂
 * 
 * @author:huoguodong
 * @date：2015-5-13 上午11:56:01
 */
public class HotContentData extends BaseData
{
	private static final long serialVersionUID = -6405146541427520682L;
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

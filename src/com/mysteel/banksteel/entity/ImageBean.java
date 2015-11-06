package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author:huoguodong
 * @date：2015-5-8 下午3:52:37
 */
public class ImageBean
{

	private String imagePath; // 图片地址
	private boolean isSelected; // 图片是否被选中

	public String getImagePath()
	{
		if (TextUtils.isEmpty(imagePath))
		{
			return "";
		}
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public boolean getIsSelected()
	{
		return isSelected;
	}

	public void setIsSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
}

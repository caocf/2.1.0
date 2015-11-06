package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.BaseData;

/**
 * 通用ViewInterface
 * 
 * @author zoujian
 * 
 * @创建时间：2015-1-20下午5:28:05
 */
public interface IBaseViewInterface
{

	/** 页面刷新 */
	void updateView(BaseData data);

	/**
	 * 页面加载提示
	 * 
	 * @param flag
	 *            true 显示进度提示，false 不显示进度提示
	 */
	void isShowDialog(boolean flag);

}

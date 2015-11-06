package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.SearchResourceData;

/**
 * 资源搜索
 * 
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:51:00
 */
public interface IResourceManagerView extends IBaseViewInterface{

	
	/**
	 * 资源筛选
	 * @param data
	 */
	void matchResource(SearchResourceData data);
	
	/**
	 * 资源搜索
	 * @param data
	 */
	void searchResource(SearchResourceData data);
	
	
}

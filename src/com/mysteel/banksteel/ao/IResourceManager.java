package com.mysteel.banksteel.ao;


/**
 * 资源搜索
 * 
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:51:00
 */
public interface IResourceManager extends IBaseAO
{
	
	
	/**
	 * 根据条件筛选
	 * @param url
	 * @param request_tag
	 */
	void getMatchResource(String url, String request_tag);
	
	
	/**
	 * 6.0 资源搜索
	 * @param url
	 */
	void getSearchResouce(String url, String request_tag);

	/**
	 * 6.0 资源搜索
	 * @param url
	 */
	void getDetailResouce(String url, String request_tag);


	/**
	 * 6.15指定时间区间钢材价格趋势（deal.getPriceTrend）
	 * @param url
	 */
	void getPriceTrend(String url, String request_tag);
}

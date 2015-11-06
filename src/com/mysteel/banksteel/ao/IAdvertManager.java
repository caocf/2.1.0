package com.mysteel.banksteel.ao;


/**
 * 协议8:广告位管理的接口
 * 
 * @author 68
 * 
 */
public interface IAdvertManager extends IBaseAO
{
	/**
	 * 8.1获取广告位数据
	 * @param url
	 */
	void getIAdvert(String url, String request_tag);
}

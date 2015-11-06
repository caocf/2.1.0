package com.mysteel.banksteel.ao;

/**
 * 地址管理的接口
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 下午4:45:20
 */
public interface IAddressManage extends IBaseAO
{

	/** 添加地址的链接 */
	void getAddAddress(String url, String request_tag);

	/**
	 * 获取地址列表信息
	 * 
	 * @param url
	 */
	public void getAddressList(String url, String request_tag);

	/**
	 * 设置默认地址
	 * 
	 * @param url
	 */
	public void setDefualtAddress(String url, String request_tag);
}

package com.mysteel.banksteel.ao;

/**
 * 系统管理的接口(7.x)
 * 
 * @author 68
 * 
 */
public interface ISystemManage extends IBaseAO
{
	/**
	 * 检查更新
	 * 
	 * @param url
	 */
	public void checkUpDate(String url, String request_tag);

	/**
	 * 消息中心
	 * 
	 * @param url
	 */
	public void messageCenter(String url, String request_tag);
	
	/**
	 * 删除消息
	 * 
	 * @param url
	 */
	public void deleteMessage(String url, String request_tag);

	/**
	 * 每日签到/平台交易规则
	 * 
	 * @param url
	 */
	public void getSign(String url, String request_tag);
}

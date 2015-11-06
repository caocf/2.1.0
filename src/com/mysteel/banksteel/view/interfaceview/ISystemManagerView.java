package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SignData;

/**
 * 系统管理视图view
 * 
 * @author 68
 * 
 */
public interface ISystemManagerView extends IBaseViewInterface
{
	/**
	 * 检查跟新
	 * 
	 * @param data
	 */
	public void checkUpDate(BaseData data);

	/**
	 * 消息中心,获取历史消息
	 */
	public void getHistoryMessage(MessageCenterData data);
	
	/**
	 * 消息中心,删除消息
	 */
	public void getDeleteMessage(DeleteMessageData data);
	

	/**
	 * 每日签到/平台交易规则
	 * 
	 * @param data
	 */
	public void getSign(SignData data);
	
	/**
	 * 意见反馈
	 * @param data
	 */
	public void getSuggest(BaseData data);
	
	/**
	 * 是否推送
	 * @param data
	 */
	public void getIsPush(BaseData data);
}

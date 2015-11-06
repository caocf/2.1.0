package com.mysteel.banksteel.ao;

/**
 * 订单 匹配拉取数据的方法接口
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-18 下午8:54:56
 */
public interface IOrderTrade extends IBaseAO
{

	/** 5.1 系统资源匹配链接 */
	void getMatchBuy(String url, String request_tag);

	/** 人工抢单,上传凭证 ,修改买家和卖家信息*/
	void getHumanServe(String url, String request_tag);

	/** 获取订单列表 */
	void getPageOrder(String url, String request_tag);

	/** 匹配提交撮合订单 */
	void getCreateOrder(String url, String request_tag);

	/** 订单详情 */
	void getOrderDetail(String url, String request_tag);
	
	/** 6.13 创建意向订单 */
	void getCreateIntentionOrder(String url, String request_tag);

	/**6.1获取指定时间区间资源总数（deal.todayResourceCount）*/
	void getTodayResourceCount(String url, String request_tag);

	/**用户信息统计(user.userDetailsCount)*/
	void getUserDetailsCount(String url, String request_tag);

	/**1.15注册环信（hxuser.register）*/
	void getHuanXinRegister(String url, String request_tag);

	/**5.17撮合订单（deal.consultOrder）*/
	void getConsultOrder(String url, String request_tag);

}

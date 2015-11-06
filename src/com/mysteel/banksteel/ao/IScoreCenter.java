package com.mysteel.banksteel.ao;

/**
 * 
 * 积分中心ao接口
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-14 下午5:04:10
 */
public interface IScoreCenter extends IBaseAO
{

	/** 获取积分历史记录 */
	void getScoreRecord(String url, String request_tag);

	/** 获取积分兑换列表 */
	void getScoreConvert(String url, String request_tag);

	/** 查看积分 */
	void getSearchScore(String url, String request_tag);

	/** 提交积分订单 */
	void getScoreOrder(String url, String request_tag);

	/** 赚取积分 */
	void getEarnScore(String url, String request_tag);

}

package com.mysteel.banksteel.ao;

/**
 * 评价接口的请求地址
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-21 下午4:17:13
 */
public interface IEvaluate extends IBaseAO
{

	/** 系统匹配 和 人工匹配的请求接口 */
	void getEvaluate(String url, String request_tag);
}

package com.mysteel.banksteel.ao;

/**
 * 
 * 求购接口
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-17 下午3:09:28
 */
public interface IBuyCenter extends IBaseAO
{

	/** 获取城市列表 或 品牌产地 */
	void getCityList(String url, String request_tag);

	/** 获取规格或材质 */
	void getSpecsAndMaterial(String url, String request_tag);

	/** 根据大分类品种id获取所属的子品种数据 */
	void getSteelData(String url, String request_tag);

	/** 发布标准求购 */
	void getPublishStanBuy(String url, String request_tag);

	/** 获取历史求购单列表 */
	void getHistoryStanBuy(String url, String request_tag);

	/** 取消求购订单,快捷求购 */
	void getCancelStanBuy(String url, String request_tag);
	
	/**获取首页下边3条数据*/
	void getUncancelStanBuy(String url, String request_tag);
	
	/**获取我的求购中已报价状态对应的数据*/
	void getDetailStanBuy(String url, String request_tag);
	
	/**获取首页我的订单是否有未提交凭证*/
	void getorderCount(String url, String request_tag);
	
	/**获取同城报价信息*/
	void getLocalQuotedBuy(String url, String request_tag);
	
	/**获取更多商机信息*/
	void getOpportunityMoreBuy(String url, String request_tag);
	
	/**获取买家报价信息列表*/
	void getStanQuotDetails(String url, String request_tag);

	/**9.2我的报价列表*/
	void getmyQuot(String url, String request_tag);

	/** 9.3 我要报价 */
	void getSellQuot(String url, String request_tag);

	/** 获取子地区 */
	void getSubAreaByCode(String url, String request_tag);
	/** 获取货物类型 */
	void getProductType(String url, String request_tag);

}

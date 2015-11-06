package com.mysteel.banksteel.ao;

/**
 * 物流接口
 *
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月15日14:03:59
 */
public interface ITransportManager extends IBaseAO {


    /** 10.1查询报价 */
    void getQueryTransportQuot(String url, String request_tag);

    /** 10.2 创建物流订单 */
    void getCreateTransportOrder(String url, String request_tag);

    /** 10.3 查看物流状态 */
    void getTransportDetails(String url, String request_tag);

    /** 10.4人工服务 */
    void getCustomerCare(String url, String request_tag);

    /** 10.5物流评价 */
    void getAppraise(String url, String request_tag);

    /**10.7 我的物流订单*/
    void getOrderByUserId(String url, String request_tag);

}

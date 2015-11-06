package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 首页买家和卖家订单中心数据、同城报价、更多商机数据
 * Created by zoujian on 15/9/8.
 */
public class UserDetailsCountData extends BaseData
{
    private DataEntity data;


    public void setData(DataEntity data)
    {
        this.data = data;
    }

    public DataEntity getData()
    {
        return data;
    }

    public class DataEntity
    {
        /**
         * userFastStanBuyCount : 0
         * resourceCount : 882866
         * areaResourceCount : 6928
         * sellEval : 0
         * buyAppraise : 0
         * userStanBuyCount : 0
         * stanCount : 33
         * transportEval : 0
         * fastStanCount : 16
         * sellAppraise : 0
         * areaStanCount : 8
         * sellSuccessOrder : 0
         * buySuccessOrder : 14
         * buyEval : 0
         * areaFastStanCount : 0
         */
        private String userFastStanBuyCount;//用户快捷求购量
        private String resourceCount;//资源总数量
        private String areaResourceCount;//同城资源量
        private String sellEval;//卖家身份评价量
        private String buyAppraise;//买家身份被评价
        private String userStanBuyCount;//用户标准求购量
        private String stanCount;//标准求购总数
        private String transportEval;//物流评价量
        private String fastStanCount;//快捷求购总数量
        private String sellAppraise;//卖家身份被评价
        private String areaStanCount;//同城标准求购量
        private String sellSuccessOrder;//卖家身份总成交量
        private String buySuccessOrder;//买家身份总成交量
        private String buyEval;//买家身份评价
        private String areaFastStanCount;//同城快捷求购量

        public void setUserFastStanBuyCount(String userFastStanBuyCount)
        {
            this.userFastStanBuyCount = userFastStanBuyCount;
        }

        public void setResourceCount(String resourceCount)
        {
            this.resourceCount = resourceCount;
        }

        public void setAreaResourceCount(String areaResourceCount)
        {
            this.areaResourceCount = areaResourceCount;
        }

        public void setSellEval(String sellEval)
        {
            this.sellEval = sellEval;
        }

        public void setBuyAppraise(String buyAppraise)
        {
            this.buyAppraise = buyAppraise;
        }

        public void setUserStanBuyCount(String userStanBuyCount)
        {
            this.userStanBuyCount = userStanBuyCount;
        }

        public void setStanCount(String stanCount)
        {
            this.stanCount = stanCount;
        }

        public void setTransportEval(String transportEval)
        {
            this.transportEval = transportEval;
        }

        public void setFastStanCount(String fastStanCount)
        {
            this.fastStanCount = fastStanCount;
        }

        public void setSellAppraise(String sellAppraise)
        {
            this.sellAppraise = sellAppraise;
        }

        public void setAreaStanCount(String areaStanCount)
        {
            this.areaStanCount = areaStanCount;
        }

        public void setSellSuccessOrder(String sellSuccessOrder)
        {
            this.sellSuccessOrder = sellSuccessOrder;
        }

        public void setBuySuccessOrder(String buySuccessOrder)
        {
            this.buySuccessOrder = buySuccessOrder;
        }

        public void setBuyEval(String buyEval)
        {
            this.buyEval = buyEval;
        }

        public void setAreaFastStanCount(String areaFastStanCount)
        {
            this.areaFastStanCount = areaFastStanCount;
        }

        public String getUserFastStanBuyCount()
        {
            if (TextUtils.isEmpty(userFastStanBuyCount))
            {
                return "";
            }
            return userFastStanBuyCount;
        }

        public String getResourceCount()
        {
            if (TextUtils.isEmpty(resourceCount))
            {
                return "";
            }
            return resourceCount;
        }

        public String getAreaResourceCount()
        {
            if (TextUtils.isEmpty(areaResourceCount))
            {
                return "";
            }
            return areaResourceCount;
        }

        public String getSellEval()
        {
            if (TextUtils.isEmpty(sellEval))
            {
                return "";
            }
            return sellEval;
        }

        public String getBuyAppraise()
        {
            if (TextUtils.isEmpty(buyAppraise))
            {
                return "";
            }
            return buyAppraise;
        }

        public String getUserStanBuyCount()
        {
            if (TextUtils.isEmpty(userStanBuyCount))
            {
                return "";
            }
            return userStanBuyCount;
        }

        public String getStanCount()
        {
            if (TextUtils.isEmpty(stanCount))
            {
                return "";
            }
            return stanCount;
        }

        public String getTransportEval()
        {
            if (TextUtils.isEmpty(transportEval))
            {
                return "";
            }
            return transportEval;
        }

        public String getFastStanCount()
        {
            if (TextUtils.isEmpty(fastStanCount))
            {
                return "";
            }
            return fastStanCount;
        }

        public String getSellAppraise()
        {
            if (TextUtils.isEmpty(sellAppraise))
            {
                return "";
            }
            return sellAppraise;
        }

        public String getAreaStanCount()
        {
            if (TextUtils.isEmpty(areaStanCount))
            {
                return "";
            }
            return areaStanCount;
        }

        public String getSellSuccessOrder()
        {
            if (TextUtils.isEmpty(sellSuccessOrder))
            {
                return "";
            }
            return sellSuccessOrder;
        }

        public String getBuySuccessOrder()
        {
            if (TextUtils.isEmpty(buySuccessOrder))
            {
                return "";
            }
            return buySuccessOrder;
        }

        public String getBuyEval()
        {
            if (TextUtils.isEmpty(buyEval))
            {
                return "";
            }
            return buyEval;
        }

        public String getAreaFastStanCount()
        {
            if (TextUtils.isEmpty(areaFastStanCount))
            {
                return "";
            }
            return areaFastStanCount;
        }
    }
}

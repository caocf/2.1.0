package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.util.List;

/**
 * 价格走势图
 *
 * @author:wushaoge
 * @date：2015-8-20 下午8:21:00
 */
public class PriceTrendData extends BaseData{

    private Data data;

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public class Data
    {
        private List<Datas> datas;
        public List<Datas> getDatas() {
            return datas;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public class Datas
        {
            private String avgPrice;
            private String orderDate;

            public String getAvgPrice() {
                if(TextUtils.isEmpty(avgPrice)){
                    return "";
                }
                return avgPrice;
            }

            public void setAvgPrice(String avgPrice) {
                this.avgPrice = avgPrice;
            }

            public String getOrderDate() {
                if(TextUtils.isEmpty(orderDate)){
                    return "";
                }
                return orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

        }

    }

}

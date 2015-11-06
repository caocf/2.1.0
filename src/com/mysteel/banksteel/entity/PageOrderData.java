package com.mysteel.banksteel.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 订单列表数据实体类
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-5-19 下午4:08:55
 */
public class PageOrderData extends BaseData
{

    private static final long serialVersionUID = -7649223604281629678L;
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
        private String count;
        private String page;
        private String size;
        private String pagenum;
        private ArrayList<Datas> datas;

        public String getCount()
        {
            if (TextUtils.isEmpty(count))
            {
                return "";
            }
            return count;
        }

        public void setCount(String count)
        {
            this.count = count;
        }

        public String getPage()
        {
            if (TextUtils.isEmpty(page))
            {
                return "";
            }
            return page;
        }

        public void setPage(String page)
        {
            this.page = page;
        }

        public String getSize()
        {
            if (TextUtils.isEmpty(size))
            {
                return "";
            }
            return size;
        }

        public void setSize(String size)
        {
            this.size = size;
        }

        public String getPagenum()
        {
            if (TextUtils.isEmpty(pagenum))
            {
                return "";
            }
            return pagenum;
        }

        public void setPagenum(String pagenum)
        {
            this.pagenum = pagenum;
        }

        public ArrayList<Datas> getDatas()
        {
            return datas;
        }

        public void setDatas(ArrayList<Datas> datas)
        {
            this.datas = datas;
        }

        @SuppressWarnings("serial")
        public class Datas implements Serializable
        {
            private String appraiseStatus;// 评价状态0-未评价 1 已评价
            private String auditDesc; // 不通过原因
            private String auditId;// 审核人员ID
            private String auditName;// 审核人员名字
            private String auditTime;// 审核时间
            private String brand;// 产地/品牌
            private String breed;// 品名
            private String breedId;// 品种ID
            private String buyMemberId;// 买家会员ID
            private String buyMemberName;// 买家会员名称
            private String buyPhone;// 买家联系电话
            private String buyUserId;// 买家用户ID
            private String buyUserName;// 买家用户名称
            private String city;// 交货地
            private String dealCount;
            private String feedbackAppraise;//反馈及时
            private String gapTime;
            private String id;// ID
            private String lastAccess;
            private String managerId;// 管理员ID
            private String managerName;// 管理员名称
            private String material;// 牌号
            private String note;//评价内容
            private String orderSource;// 求购来源:0-网站 1-会员中心 2 手机端 3-代成交
            private String orderTime;// 成交时间
            private String price;// 成交价格
            private String qty;// 成交数量
            private String quotMemberId;// 卖家会员ID
            private String quotMemberName;// 卖家会员名称
            private String quotPhone;// 卖家联系电话
            private String quotUserId;// 卖家用户ID
            private String quotUserName;// 卖家用户名称
            private String remitBillUrl1;// 汇款底单1
            private String remitBillUrl2;// 汇款底单2
            private String remitBillUrl3;// 汇款底单3
            private String remitBillUrl4;// 汇款底单4
            private String rushManagerHeader;
            private String rushManagerPhone;
            private String serialVersionUID;
            private String serviceAppraise;//质量满意
            private String source;// 成交来源 0-匹配成交 1-人工报价成交 2-买家自行入录
            private String sourceAppraise;//价格满意
            private String spec;// 规格
            private String stanBuyId;// 求购ID
            private String status;// 状态 0-待上传凭证 1-待审核 2-审核通过9-审核不通过99-关闭
            private String totalAppraise;// 评价总得分
            private String version;
            private String warehouse;// 仓库

            public String getFeedbackAppraise()
            {
                if (TextUtils.isEmpty(feedbackAppraise))
                {
                    return "";
                }
                return feedbackAppraise;
            }

            public void setFeedbackAppraise(String feedbackAppraise)
            {
                this.feedbackAppraise = feedbackAppraise;
            }

            public String getNote()
            {
                if (TextUtils.isEmpty(note))
                {
                    return "";
                }
                return note;
            }

            public void setNote(String note)
            {
                this.note = note;
            }

            public String getServiceAppraise()
            {
                if (TextUtils.isEmpty(serviceAppraise))
                {
                    return "";
                }
                return serviceAppraise;
            }

            public void setServiceAppraise(String serviceAppraise)
            {
                this.serviceAppraise = serviceAppraise;
            }

            public String getSourceAppraise()
            {
                if (TextUtils.isEmpty(sourceAppraise))
                {
                    return "";
                }
                return sourceAppraise;
            }

            public void setSourceAppraise(String sourceAppraise)
            {
                this.sourceAppraise = sourceAppraise;
            }

            public String getRushManagerHeader()
            {
                if (TextUtils.isEmpty(rushManagerHeader))
                {
                    return "";
                }
                return rushManagerHeader;
            }

            public void setRushManagerHeader(String rushManagerHeader)
            {
                this.rushManagerHeader = rushManagerHeader;
            }

            public String getRushManagerPhone()
            {
                if (TextUtils.isEmpty(rushManagerPhone))
                {
                    return "";
                }
                return rushManagerPhone;
            }

            public void setRushManagerPhone(String rushManagerPhone)
            {
                this.rushManagerPhone = rushManagerPhone;
            }

            public String getDealCount()
            {
                if (TextUtils.isEmpty(dealCount))
                {
                    return "";
                }
                return dealCount;
            }

            public void setDealCount(String dealCount)
            {
                this.dealCount = dealCount;
            }

            public String getGapTime()
            {
                if (TextUtils.isEmpty(gapTime))
                {
                    return "";
                }
                return gapTime;
            }

            public void setGapTime(String gapTime)
            {
                this.gapTime = gapTime;
            }


            public String getAppraiseStatus()
            {
                if (TextUtils.isEmpty(appraiseStatus))
                {
                    return "";
                }
                return appraiseStatus;
            }

            public void setAppraiseStatus(String appraiseStatus)
            {
                this.appraiseStatus = appraiseStatus;
            }

            public String getAuditDesc()
            {
                if (TextUtils.isEmpty(auditDesc))
                {
                    return "";
                }
                return auditDesc;
            }

            public void setAuditDesc(String auditDesc)
            {
                this.auditDesc = auditDesc;
            }

            public String getAuditId()
            {
                if (TextUtils.isEmpty(auditId))
                {
                    return "";
                }
                return auditId;
            }

            public void setAuditId(String auditId)
            {
                this.auditId = auditId;
            }

            public String getAuditName()
            {
                if (TextUtils.isEmpty(auditName))
                {
                    return "";
                }
                return auditName;
            }

            public void setAuditName(String auditName)
            {
                this.auditName = auditName;
            }

            public String getAuditTime()
            {
                if (TextUtils.isEmpty(auditTime))
                {
                    return "";
                }
                return auditTime;
            }

            public void setAuditTime(String auditTime)
            {
                this.auditTime = auditTime;
            }

            public String getBrand()
            {
                if (TextUtils.isEmpty(brand))
                {
                    return "";
                }
                return brand;
            }

            public void setBrand(String brand)
            {
                this.brand = brand;
            }

            public String getBreed()
            {
                if (TextUtils.isEmpty(breed))
                {
                    return "";
                }
                return breed;
            }

            public void setBreed(String breed)
            {
                this.breed = breed;
            }

            public String getBreedId()
            {
                if (TextUtils.isEmpty(breedId))
                {
                    return "";
                }
                return breedId;
            }

            public void setBreedId(String breedId)
            {
                this.breedId = breedId;
            }

            public String getBuyMemberId()
            {
                if (TextUtils.isEmpty(buyMemberId))
                {
                    return "";
                }
                return buyMemberId;
            }

            public void setBuyMemberId(String buyMemberId)
            {
                this.buyMemberId = buyMemberId;
            }

            public String getBuyMemberName()
            {
                if (TextUtils.isEmpty(buyMemberName))
                {
                    return "";
                }
                return buyMemberName;
            }

            public void setBuyMemberName(String buyMemberName)
            {
                this.buyMemberName = buyMemberName;
            }

            public String getBuyPhone()
            {
                if (TextUtils.isEmpty(buyPhone))
                {
                    return "";
                }
                return buyPhone;
            }

            public void setBuyPhone(String buyPhone)
            {
                this.buyPhone = buyPhone;
            }

            public String getBuyUserId()
            {
                if (TextUtils.isEmpty(buyUserId))
                {
                    return "";
                }
                return buyUserId;
            }

            public void setBuyUserId(String buyUserId)
            {
                this.buyUserId = buyUserId;
            }

            public String getBuyUserName()
            {
                if (TextUtils.isEmpty(buyUserName))
                {
                    return "";
                }
                return buyUserName;
            }

            public void setBuyUserName(String buyUserName)
            {
                this.buyUserName = buyUserName;
            }

            public String getCity()
            {
                if (TextUtils.isEmpty(city))
                {
                    return "";
                }
                return city;
            }

            public void setCity(String city)
            {
                this.city = city;
            }

            public String getId()
            {
                if (TextUtils.isEmpty(id))
                {
                    return "";
                }
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getLastAccess()
            {
                if (TextUtils.isEmpty(lastAccess))
                {
                    return "";
                }
                return lastAccess;
            }

            public void setLastAccess(String lastAccess)
            {
                this.lastAccess = lastAccess;
            }

            public String getManagerId()
            {
                if (TextUtils.isEmpty(managerId))
                {
                    return "";
                }
                return managerId;
            }

            public void setManagerId(String managerId)
            {
                this.managerId = managerId;
            }

            public String getManagerName()
            {
                if (TextUtils.isEmpty(managerName))
                {
                    return "";
                }
                return managerName;
            }

            public void setManagerName(String managerName)
            {
                this.managerName = managerName;
            }

            public String getMaterial()
            {
                if (TextUtils.isEmpty(material))
                {
                    return "";
                }
                return material;
            }

            public void setMaterial(String material)
            {
                this.material = material;
            }

            public String getOrderSource()
            {
                if (TextUtils.isEmpty(orderSource))
                {
                    return "";
                }
                return orderSource;
            }

            public void setOrderSource(String orderSource)
            {
                this.orderSource = orderSource;
            }

            public String getOrderTime()
            {
                if (TextUtils.isEmpty(orderTime))
                {
                    return "";
                }
                return orderTime;
            }

            public void setOrderTime(String orderTime)
            {
                this.orderTime = orderTime;
            }

            public String getPrice()
            {
                if (TextUtils.isEmpty(price))
                {
                    return "";
                }
                return price;
            }

            public void setPrice(String price)
            {
                this.price = price;
            }

            public String getQty()
            {
                if (TextUtils.isEmpty(qty))
                {
                    return "";
                }
                return qty;
            }

            public void setQty(String qty)
            {
                this.qty = qty;
            }

            public String getQuotMemberId()
            {
                if (TextUtils.isEmpty(quotMemberId))
                {
                    return "";
                }
                return quotMemberId;
            }

            public void setQuotMemberId(String quotMemberId)
            {
                this.quotMemberId = quotMemberId;
            }

            public String getQuotMemberName()
            {
                if (TextUtils.isEmpty(quotMemberName))
                {
                    return "";
                }
                return quotMemberName;
            }

            public void setQuotMemberName(String quotMemberName)
            {
                this.quotMemberName = quotMemberName;
            }

            public String getQuotPhone()
            {
                if (TextUtils.isEmpty(quotPhone))
                {
                    return "";
                }
                return quotPhone;
            }

            public void setQuotPhone(String quotPhone)
            {
                this.quotPhone = quotPhone;
            }

            public String getQuotUserId()
            {
                if (TextUtils.isEmpty(quotUserId))
                {
                    return "";
                }
                return quotUserId;
            }

            public void setQuotUserId(String quotUserId)
            {
                this.quotUserId = quotUserId;
            }

            public String getQuotUserName()
            {
                if (TextUtils.isEmpty(quotUserName))
                {
                    return "";
                }
                return quotUserName;
            }

            public void setQuotUserName(String quotUserName)
            {
                this.quotUserName = quotUserName;
            }

            public String getRemitBillUrl1()
            {
                if (TextUtils.isEmpty(remitBillUrl1))
                {
                    return "";
                }
                return remitBillUrl1;
            }

            public void setRemitBillUrl1(String remitBillUrl1)
            {
                this.remitBillUrl1 = remitBillUrl1;
            }

            public String getRemitBillUrl2()
            {
                if (TextUtils.isEmpty(remitBillUrl2))
                {
                    return "";
                }
                return remitBillUrl2;
            }

            public void setRemitBillUrl2(String remitBillUrl2)
            {
                this.remitBillUrl2 = remitBillUrl2;
            }

            public String getRemitBillUrl3()
            {
                if (TextUtils.isEmpty(remitBillUrl3))
                {
                    return "";
                }
                return remitBillUrl3;
            }

            public void setRemitBillUrl3(String remitBillUrl3)
            {
                this.remitBillUrl3 = remitBillUrl3;
            }

            public String getRemitBillUrl4()
            {
                if (TextUtils.isEmpty(remitBillUrl4))
                {
                    return "";
                }
                return remitBillUrl4;
            }

            public void setRemitBillUrl4(String remitBillUrl4)
            {
                this.remitBillUrl4 = remitBillUrl4;
            }

            public String getSerialVersionUID()
            {
                if (TextUtils.isEmpty(serialVersionUID))
                {
                    return "";
                }
                return serialVersionUID;
            }

            public void setSerialVersionUID(String serialVersionUID)
            {
                this.serialVersionUID = serialVersionUID;
            }

            public String getSource()
            {
                if (TextUtils.isEmpty(source))
                {
                    return "";
                }
                return source;
            }

            public void setSource(String source)
            {
                this.source = source;
            }

            public String getSpec()
            {
                if (TextUtils.isEmpty(spec))
                {
                    return "";
                }
                return spec;
            }

            public void setSpec(String spec)
            {
                this.spec = spec;
            }

            public String getStanBuyId()
            {
                if (TextUtils.isEmpty(stanBuyId))
                {
                    return "";
                }
                return stanBuyId;
            }

            public void setStanBuyId(String stanBuyId)
            {
                this.stanBuyId = stanBuyId;
            }

            public String getStatus()
            {
                if (TextUtils.isEmpty(status))
                {
                    return "";
                }
                return status;
            }

            public void setStatus(String status)
            {
                this.status = status;
            }

            public String getTotalAppraise()
            {
                if (TextUtils.isEmpty(totalAppraise))
                {
                    return "";
                }
                return totalAppraise;
            }

            public void setTotalAppraise(String totalAppraise)
            {
                this.totalAppraise = totalAppraise;
            }

            public String getVersion()
            {
                if (TextUtils.isEmpty(version))
                {
                    return "";
                }
                return version;
            }

            public void setVersion(String version)
            {
                this.version = version;
            }

            public String getWarehouse()
            {
                if (TextUtils.isEmpty(warehouse))
                {
                    return "";
                }
                return warehouse;
            }

            public void setWarehouse(String warehouse)
            {
                this.warehouse = warehouse;
            }
        }
    }
}

package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * Created by zoujian on 15/8/17.
 */
public class LogisticsOrderData extends BaseData
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
        private ArrayList<DatasEntity> datas;
        private String count;
        private String page;
        private String pagenum;
        private String size;

        public void setDatas(ArrayList<DatasEntity> datas)
        {
            this.datas = datas;
        }

        public void setCount(String count)
        {
            this.count = count;
        }

        public void setPage(String page)
        {
            this.page = page;
        }

        public void setPagenum(String pagenum)
        {
            this.pagenum = pagenum;
        }

        public void setSize(String size)
        {
            this.size = size;
        }

        public ArrayList<DatasEntity> getDatas()
        {
            return datas;
        }

        public String getCount()
        {
            return count;
        }

        public String getPage()
        {
            return page;
        }

        public String getPagenum()
        {
            return pagenum;
        }

        public String getSize()
        {
            return size;
        }

        public class DatasEntity implements Serializable
        {
            /**
             * unload : 2
             * createTime : 2015-08-06 19:40:07
             * breed : 钢卷
             * phone : 13873030960
             * qudanAddress : 惠山区
             * startProvince : null
             * steel56OrderId : 2015080609501
             * startAddress3 : null
             * startAddress2 : 逸仙路
             * endTime : 1399618992359
             * version : 0
             * startTime : 1399618992359
             * id : 13
             * userId : 3627
             * lastAccess : 0
             * serialVersionUID : 1
             * endProvince : null
             * note : 备注
             * startAreaCode : null
             * breedCode : null
             * startArea : null
             * invoiceType : 0
             * endCity : 无锡市
             * orderStatus : 10
             * endArea : null
             * weight2 : 50.0
             * endAddress1 : 宝山路
             * weight3 : 0.0
             * endAddress2 : 友谊路
             * startCity : 上海
             * endAddress3 : null
             * weight1 : 50.0
             * price : 90
             * startAddress1 : 长逸路
             * totalWeight : 100.0
             * zhixieType : 0
             * endAreaCode : null
             * load : 2
             */
            private String unload;
            private String createTime;
            private String breed;
            private String phone;
            private String qudanAddress;
            private String startProvince;
            private String steel56OrderId;
            private String startAddress3;
            private String startAddress2;
            private String endTime;
            private String version;
            private String startTime;
            private String id;
            private String userId;
            private String lastAccess;
            private String serialVersionUID;
            private String endProvince;
            private String note;
            private String startAreaCode;
            private String breedCode;
            private String startArea;
            private String invoiceType;
            private String endCity;
            private String orderStatus;
            private String endArea;
            private String weight2;
            private String endAddress1;
            private String weight3;
            private String endAddress2;
            private String startCity;
            private String endAddress3;
            private String weight1;
            private String price;
            private String startAddress1;
            private String totalWeight;
            private String zhixieType;
            private String endAreaCode;
            private String load;
            private String totalAppraise; //单次评价得分
            private String serviceAppraise; //服务满意度
            private String sourceAppraise; //运输质量
            private String feedbackAppraise; //到货及时性
            private String remark; //评价内容
            private String memberId;
            private String memberName;

            public String getMemberId() {
                if (TextUtils.isEmpty(memberId))
                {
                    return "";
                }
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getMemberName() {
                if (TextUtils.isEmpty(memberName))
                {
                    return "";
                }
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }
            public String getTotalAppraise() {
                if (TextUtils.isEmpty(totalAppraise))
                {
                    return "";
                }
                return totalAppraise;
            }

            public void setTotalAppraise(String totalAppraise) {
                this.totalAppraise = totalAppraise;
            }

            public String getServiceAppraise() {
                if (TextUtils.isEmpty(serviceAppraise))
                {
                    return "";
                }
                return serviceAppraise;
            }

            public void setServiceAppraise(String serviceAppraise) {
                this.serviceAppraise = serviceAppraise;
            }

            public String getSourceAppraise() {
                if (TextUtils.isEmpty(sourceAppraise))
                {
                    return "";
                }
                return sourceAppraise;
            }

            public void setSourceAppraise(String sourceAppraise) {
                this.sourceAppraise = sourceAppraise;
            }

            public String getFeedbackAppraise() {
                if (TextUtils.isEmpty(feedbackAppraise))
                {
                    return "";
                }
                return feedbackAppraise;
            }

            public void setFeedbackAppraise(String feedbackAppraise) {
                this.feedbackAppraise = feedbackAppraise;
            }

            public String getRemark() {
                if (TextUtils.isEmpty(remark))
                {
                    return "";
                }
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setUnload(String unload)
            {
                this.unload = unload;
            }

            public void setCreateTime(String createTime)
            {
                this.createTime = createTime;
            }

            public void setBreed(String breed)
            {
                this.breed = breed;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public void setQudanAddress(String qudanAddress)
            {
                this.qudanAddress = qudanAddress;
            }

            public void setStartProvince(String startProvince)
            {
                this.startProvince = startProvince;
            }

            public void setSteel56OrderId(String steel56OrderId)
            {
                this.steel56OrderId = steel56OrderId;
            }

            public void setStartAddress3(String startAddress3)
            {
                this.startAddress3 = startAddress3;
            }

            public void setStartAddress2(String startAddress2)
            {
                this.startAddress2 = startAddress2;
            }

            public void setEndTime(String endTime)
            {
                this.endTime = endTime;
            }

            public void setVersion(String version)
            {
                this.version = version;
            }

            public void setStartTime(String startTime)
            {
                this.startTime = startTime;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public void setUserId(String userId)
            {
                this.userId = userId;
            }

            public void setLastAccess(String lastAccess)
            {
                this.lastAccess = lastAccess;
            }

            public void setSerialVersionUID(String serialVersionUID)
            {
                this.serialVersionUID = serialVersionUID;
            }

            public void setEndProvince(String endProvince)
            {
                this.endProvince = endProvince;
            }

            public void setNote(String note)
            {
                this.note = note;
            }

            public void setStartAreaCode(String startAreaCode)
            {
                this.startAreaCode = startAreaCode;
            }

            public void setBreedCode(String breedCode)
            {
                this.breedCode = breedCode;
            }

            public void setStartArea(String startArea)
            {
                this.startArea = startArea;
            }

            public void setInvoiceType(String invoiceType)
            {
                this.invoiceType = invoiceType;
            }

            public void setEndCity(String endCity)
            {
                this.endCity = endCity;
            }

            public void setOrderStatus(String orderStatus)
            {
                this.orderStatus = orderStatus;
            }

            public void setEndArea(String endArea)
            {
                this.endArea = endArea;
            }

            public void setWeight2(String weight2)
            {
                this.weight2 = weight2;
            }

            public void setEndAddress1(String endAddress1)
            {
                this.endAddress1 = endAddress1;
            }

            public void setWeight3(String weight3)
            {
                this.weight3 = weight3;
            }

            public void setEndAddress2(String endAddress2)
            {
                this.endAddress2 = endAddress2;
            }

            public void setStartCity(String startCity)
            {
                this.startCity = startCity;
            }

            public void setEndAddress3(String endAddress3)
            {
                this.endAddress3 = endAddress3;
            }

            public void setWeight1(String weight1)
            {
                this.weight1 = weight1;
            }

            public void setPrice(String price)
            {
                this.price = price;
            }

            public void setStartAddress1(String startAddress1)
            {
                this.startAddress1 = startAddress1;
            }

            public void setTotalWeight(String totalWeight)
            {
                this.totalWeight = totalWeight;
            }

            public void setZhixieType(String zhixieType)
            {
                this.zhixieType = zhixieType;
            }

            public void setEndAreaCode(String endAreaCode)
            {
                this.endAreaCode = endAreaCode;
            }

            public void setLoad(String load)
            {
                this.load = load;
            }

            public String getUnload()
            {
                if (TextUtils.isEmpty(unload))
                {
                    return "";
                }
                return unload;
            }

            public String getCreateTime()
            {
                if (TextUtils.isEmpty(createTime))
                {
                    return "";
                }
                return createTime;
            }

            public String getBreed()
            {
                if (TextUtils.isEmpty(breed))
                {
                    return "";
                }
                return breed;
            }

            public String getPhone()
            {
                if (TextUtils.isEmpty(phone))
                {
                    return "";
                }
                return phone;
            }

            public String getQudanAddress()
            {
                if (TextUtils.isEmpty(qudanAddress))
                {
                    return "";
                }
                return qudanAddress;
            }

            public String getStartProvince()
            {
                if (TextUtils.isEmpty(startProvince))
                {
                    return "";
                }
                return startProvince;
            }

            public String getSteel56OrderId()
            {
                if (TextUtils.isEmpty(steel56OrderId))
                {
                    return "";
                }
                return steel56OrderId;
            }

            public String getStartAddress3()
            {
                if (TextUtils.isEmpty(startAddress3))
                {
                    return "";
                }
                return startAddress3;
            }

            public String getStartAddress2()
            {
                if (TextUtils.isEmpty(startAddress2))
                {
                    return "";
                }
                return startAddress2;
            }

            public String getEndTime()
            {
                if (TextUtils.isEmpty(endTime))
                {
                    return "";
                }
                return endTime;
            }

            public String getVersion()
            {
                if (TextUtils.isEmpty(version))
                {
                    return "";
                }
                return version;
            }

            public String getStartTime()
            {
                if (TextUtils.isEmpty(startTime))
                {
                    return "";
                }
                return startTime;
            }

            public String getId()
            {
                if (TextUtils.isEmpty(id))
                {
                    return "";
                }
                return id;
            }

            public String getUserId()
            {
                if (TextUtils.isEmpty(userId))
                {
                    return "";
                }
                return userId;
            }

            public String getLastAccess()
            {
                if (TextUtils.isEmpty(lastAccess))
                {
                    return "";
                }
                return lastAccess;
            }

            public String getSerialVersionUID()
            {
                if (TextUtils.isEmpty(serialVersionUID))
                {
                    return "";
                }
                return serialVersionUID;
            }

            public String getEndProvince()
            {
                if (TextUtils.isEmpty(endProvince))
                {
                    return "";
                }
                return endProvince;
            }

            public String getNote()
            {
                if (TextUtils.isEmpty(note))
                {
                    return "";
                }
                return note;
            }

            public String getStartAreaCode()
            {
                if (TextUtils.isEmpty(startAreaCode))
                {
                    return "";
                }
                return startAreaCode;
            }

            public String getBreedCode()
            {
                if (TextUtils.isEmpty(breedCode))
                {
                    return "";
                }
                return breedCode;
            }

            public String getStartArea()
            {
                if (TextUtils.isEmpty(startArea))
                {
                    return "";
                }
                return startArea;
            }

            public String getInvoiceType()
            {
                if (TextUtils.isEmpty(invoiceType))
                {
                    return "";
                }
                return invoiceType;
            }

            public String getEndCity()
            {
                if (TextUtils.isEmpty(endCity))
                {
                    return "";
                }
                return endCity;
            }

            public String getOrderStatus()
            {
                if (TextUtils.isEmpty(orderStatus))
                {
                    return "";
                }
                return orderStatus;
            }

            public String getEndArea()
            {
                if (TextUtils.isEmpty(endArea))
                {
                    return "";
                }
                return endArea;
            }

            public String getWeight2()
            {
                if (TextUtils.isEmpty(weight2))
                {
                    return "";
                }
                return weight2;
            }

            public String getEndAddress1()
            {
                if (TextUtils.isEmpty(endAddress1))
                {
                    return "";
                }
                return endAddress1;
            }

            public String getWeight3()
            {
                if (TextUtils.isEmpty(weight3))
                {
                    return "";
                }
                return weight3;
            }

            public String getEndAddress2()
            {
                if (TextUtils.isEmpty(endAddress2))
                {
                    return "";
                }
                return endAddress2;
            }

            public String getStartCity()
            {
                if (TextUtils.isEmpty(startCity))
                {
                    return "";
                }
                return startCity;
            }

            public String getEndAddress3()
            {
                if (TextUtils.isEmpty(endAddress3))
                {
                    return "";
                }
                return endAddress3;
            }

            public String getWeight1()
            {
                if (TextUtils.isEmpty(weight1))
                {
                    return "";
                }
                return weight1;
            }

            public String getPrice()
            {
                if (TextUtils.isEmpty(price))
                {
                    return "";
                }
                return price;
            }

            public String getStartAddress1()
            {
                if (TextUtils.isEmpty(startAddress1))
                {
                    return "";
                }
                return startAddress1;
            }

            public String getTotalWeight()
            {
                if (TextUtils.isEmpty(totalWeight))
                {
                    return "";
                }
                return totalWeight;
            }

            public String getZhixieType()
            {
                if (TextUtils.isEmpty(zhixieType))
                {
                    return "";
                }
                return zhixieType;
            }

            public String getEndAreaCode()
            {
                if (TextUtils.isEmpty(endAreaCode))
                {
                    return "";
                }
                return endAreaCode;
            }

            public String getLoad()
            {
                if (TextUtils.isEmpty(load))
                {
                    return "";
                }
                return load;
            }
        }
    }
}

package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 撮合订单的实体类
 * Created by zoujian on 15/10/26.
 */
public class ConsultOrderData extends BaseData
{

    private String count;
    private String page;
    private String pagenum;
    private String size;
    /**
     * appraiseStatus : null
     * audioFileUrl : http://192.168.100.115:8180null
     * audioTimes : null
     * auditDesc : null
     * auditId : null
     * auditName : null
     * auditTime : null
     * brand : 宝华
     * breed : 二级螺纹钢
     * breedId : 0101
     * buyMemberId : null
     * buyMemberName : null
     * buyPhone : null
     * buyType : 9
     * buyUserId : null
     * buyUserName : null
     * cause : null
     * city : 太原
     * company : 上海钢联
     * contacter : 张三丰
     * content : null
     * dueTime : 2015-09-11 20:08:45
     * id : 249562
     * ip : 192.168.38.12
     * managerId : null
     * managerName : null
     * manual : 1
     * material : HRB335
     * memberId : 210169
     * normTime : 2015-09-08 20:08:45
     * note :
     * orderSource : null
     * orderTime : 0
     * orderType : 0
     * phone : 15821398484
     * price : 0.0
     * publishTime : 2015-09-08 20:08:45
     * qty : 60.0
     * quotMemberId : null
     * quotMemberName : null
     * quotNum : 1
     * quotPhone : null
     * quotUserId : ,46892,
     * quotUserName : null
     * remitBillUrl1 :
     * remitBillUrl2 :
     * remitBillUrl3 :
     * remitBillUrl4 :
     * resource : 2
     * rushManagerId : 0
     * rushManagerName :
     * rushStatus : 0
     * rushTime : 0
     * source : null
     * spec : Φ18
     * stanBuyId : null
     * status : 1
     * userId : 46881
     * warehouse : null
     */

    private List<DatasEntity> datas;

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

    public void setDatas(List<DatasEntity> datas)
    {
        this.datas = datas;
    }

    public String getCount()
    {
        if (TextUtils.isEmpty(count))
        {
            return "";
        }
        return count;
    }

    public String getPage()
    {
        if (TextUtils.isEmpty(page))
        {
            return "";
        }
        return page;
    }

    public String getPagenum()
    {
        if (TextUtils.isEmpty(pagenum))
        {
            return "";
        }
        return pagenum;
    }

    public String getSize()
    {
        if (TextUtils.isEmpty(size))
        {
            return "";
        }
        return size;
    }

    public List<DatasEntity> getDatas()
    {
        return datas;
    }

    public static class DatasEntity implements Serializable
    {
        private String appraiseStatus;
        private String audioFileUrl;
        private String audioTimes;
        private String auditDesc;
        private String auditId;
        private String auditName;
        private String auditTime;
        private String brand;
        private String breed;
        private String breedId;
        private String buyMemberId;
        private String buyMemberName;
        private String buyPhone;
        private String buyType;
        private String buyUserId;
        private String buyUserName;
        private String cause;
        private String city;
        private String company;
        private String contacter;
        private String content;
        private String dueTime;
        private String id;
        private String ip;
        private String managerId;
        private String managerName;
        private String manual;
        private String material;
        private String memberId;
        private String normTime;
        private String note;
        private String orderSource;
        private String orderTime;
        private String orderType;
        private String phone;
        private String price;
        private String publishTime;
        private String qty;
        private String quotMemberId;
        private String quotMemberName;
        private String quotNum;
        private String quotPhone;
        private String quotUserId;
        private String quotUserName;
        private String remitBillUrl1;
        private String remitBillUrl2;
        private String remitBillUrl3;
        private String remitBillUrl4;
        private String resource;
        private String rushManagerId;
        private String rushManagerName;
        private String rushStatus;
        private String rushTime;
        private String source;
        private String spec;
        private String stanBuyId;
        private String status;
        private String userId;
        private String warehouse;
        private String totalAppraise;
        private String dueStatus;
        private String gapTime;
        private String countdown;
        private int jumpNum;

        public int getJumpNum()
        {
            return jumpNum;
        }

        public void setJumpNum(int jumpNum)
        {
            this.jumpNum = jumpNum;
        }

        public String getCountdown()
        {
            if(TextUtils.isEmpty(countdown))
            {
                return "";
            }
            return countdown;
        }

        public void setCountdown(String countdown)
        {
            this.countdown = countdown;
        }

        public String getGapTime()
        {
            if(TextUtils.isEmpty(gapTime))
            {
                return "";
            }
            return gapTime;
        }

        public void setGapTime(String gapTime)
        {
            this.gapTime = gapTime;
        }

        public void setDueStatus(String dueStatus)
        {
            this.dueStatus = dueStatus;
        }
        public void setTotalAppraise(String totalAppraise)
        {
            this.totalAppraise = totalAppraise;
        }

        public void setAppraiseStatus(String appraiseStatus)
        {
            this.appraiseStatus = appraiseStatus;
        }

        public void setAudioFileUrl(String audioFileUrl)
        {
            this.audioFileUrl = audioFileUrl;
        }

        public void setAudioTimes(String audioTimes)
        {
            this.audioTimes = audioTimes;
        }

        public void setAuditDesc(String auditDesc)
        {
            this.auditDesc = auditDesc;
        }

        public void setAuditId(String auditId)
        {
            this.auditId = auditId;
        }

        public void setAuditName(String auditName)
        {
            this.auditName = auditName;
        }

        public void setAuditTime(String auditTime)
        {
            this.auditTime = auditTime;
        }

        public void setBrand(String brand)
        {
            this.brand = brand;
        }

        public void setBreed(String breed)
        {
            this.breed = breed;
        }

        public void setBreedId(String breedId)
        {
            this.breedId = breedId;
        }

        public void setBuyMemberId(String buyMemberId)
        {
            this.buyMemberId = buyMemberId;
        }

        public void setBuyMemberName(String buyMemberName)
        {
            this.buyMemberName = buyMemberName;
        }

        public void setBuyPhone(String buyPhone)
        {
            this.buyPhone = buyPhone;
        }

        public void setBuyType(String buyType)
        {
            this.buyType = buyType;
        }

        public void setBuyUserId(String buyUserId)
        {
            this.buyUserId = buyUserId;
        }

        public void setBuyUserName(String buyUserName)
        {
            this.buyUserName = buyUserName;
        }

        public void setCause(String cause)
        {
            this.cause = cause;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public void setCompany(String company)
        {
            this.company = company;
        }

        public void setContacter(String contacter)
        {
            this.contacter = contacter;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public void setDueTime(String dueTime)
        {
            this.dueTime = dueTime;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public void setIp(String ip)
        {
            this.ip = ip;
        }

        public void setManagerId(String managerId)
        {
            this.managerId = managerId;
        }

        public void setManagerName(String managerName)
        {
            this.managerName = managerName;
        }

        public void setManual(String manual)
        {
            this.manual = manual;
        }

        public void setMaterial(String material)
        {
            this.material = material;
        }

        public void setMemberId(String memberId)
        {
            this.memberId = memberId;
        }

        public void setNormTime(String normTime)
        {
            this.normTime = normTime;
        }

        public void setNote(String note)
        {
            this.note = note;
        }

        public void setOrderSource(String orderSource)
        {
            this.orderSource = orderSource;
        }

        public void setOrderTime(String orderTime)
        {
            this.orderTime = orderTime;
        }

        public void setOrderType(String orderType)
        {
            this.orderType = orderType;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public void setPublishTime(String publishTime)
        {
            this.publishTime = publishTime;
        }

        public void setQty(String qty)
        {
            this.qty = qty;
        }

        public void setQuotMemberId(String quotMemberId)
        {
            this.quotMemberId = quotMemberId;
        }

        public void setQuotMemberName(String quotMemberName)
        {
            this.quotMemberName = quotMemberName;
        }

        public void setQuotNum(String quotNum)
        {
            this.quotNum = quotNum;
        }

        public void setQuotPhone(String quotPhone)
        {
            this.quotPhone = quotPhone;
        }

        public void setQuotUserId(String quotUserId)
        {
            this.quotUserId = quotUserId;
        }

        public void setQuotUserName(String quotUserName)
        {
            this.quotUserName = quotUserName;
        }

        public void setRemitBillUrl1(String remitBillUrl1)
        {
            this.remitBillUrl1 = remitBillUrl1;
        }

        public void setRemitBillUrl2(String remitBillUrl2)
        {
            this.remitBillUrl2 = remitBillUrl2;
        }

        public void setRemitBillUrl3(String remitBillUrl3)
        {
            this.remitBillUrl3 = remitBillUrl3;
        }

        public void setRemitBillUrl4(String remitBillUrl4)
        {
            this.remitBillUrl4 = remitBillUrl4;
        }

        public void setResource(String resource)
        {
            this.resource = resource;
        }

        public void setRushManagerId(String rushManagerId)
        {
            this.rushManagerId = rushManagerId;
        }

        public void setRushManagerName(String rushManagerName)
        {
            this.rushManagerName = rushManagerName;
        }

        public void setRushStatus(String rushStatus)
        {
            this.rushStatus = rushStatus;
        }

        public void setRushTime(String rushTime)
        {
            this.rushTime = rushTime;
        }

        public void setSource(String source)
        {
            this.source = source;
        }

        public void setSpec(String spec)
        {
            this.spec = spec;
        }

        public void setStanBuyId(String stanBuyId)
        {
            this.stanBuyId = stanBuyId;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public void setUserId(String userId)
        {
            this.userId = userId;
        }

        public void setWarehouse(String warehouse)
        {
            this.warehouse = warehouse;
        }

        public String getAppraiseStatus()
        {
            if (TextUtils.isEmpty(appraiseStatus))
            {
                return "";
            }
            return appraiseStatus;
        }

        public String getAudioFileUrl()
        {
            if (TextUtils.isEmpty(audioFileUrl))
            {
                return "";
            }
            return audioFileUrl;
        }

        public String getAudioTimes()
        {
            if (TextUtils.isEmpty(audioTimes))
            {
                return "";
            }
            return audioTimes;
        }

        public String getAuditDesc()
        {
            if (TextUtils.isEmpty(auditDesc))
            {
                return "";
            }
            return auditDesc;
        }

        public String getAuditId()
        {
            if (TextUtils.isEmpty(auditId))
            {
                return "";
            }
            return auditId;
        }

        public String getAuditName()
        {
            if (TextUtils.isEmpty(auditName))
            {
                return "";
            }
            return auditName;
        }

        public String getAuditTime()
        {
            if (TextUtils.isEmpty(auditTime))
            {
                return "";
            }
            return auditTime;
        }

        public String getBrand()
        {
            if (TextUtils.isEmpty(brand))
            {
                return "";
            }
            return brand;
        }

        public String getBreed()
        {
            if (TextUtils.isEmpty(breed))
            {
                return "";
            }
            return breed;
        }

        public String getBreedId()
        {
            if (TextUtils.isEmpty(breedId))
            {
                return "";
            }
            return breedId;
        }

        public String getBuyMemberId()
        {
            if (TextUtils.isEmpty(buyMemberId))
            {
                return "";
            }
            return buyMemberId;
        }

        public String getBuyMemberName()
        {
            if (TextUtils.isEmpty(buyMemberName))
            {
                return "";
            }
            return buyMemberName;
        }

        public String getBuyPhone()
        {
            if (TextUtils.isEmpty(buyPhone))
            {
                return "";
            }
            return buyPhone;
        }

        public String getBuyType()
        {
            if (TextUtils.isEmpty(buyType))
            {
                return "";
            }
            return buyType;
        }

        public String getBuyUserId()
        {
            if (TextUtils.isEmpty(buyUserId))
            {
                return "";
            }
            return buyUserId;
        }

        public String getBuyUserName()
        {
            if (TextUtils.isEmpty(buyUserName))
            {
                return "";
            }
            return buyUserName;
        }

        public String getCause()
        {
            if (TextUtils.isEmpty(cause))
            {
                return "";
            }
            return cause;
        }

        public String getCity()
        {
            if (TextUtils.isEmpty(city))
            {
                return "";
            }
            return city;
        }

        public String getCompany()
        {
            if (TextUtils.isEmpty(company))
            {
                return "";
            }
            return company;
        }

        public String getContacter()
        {
            if (TextUtils.isEmpty(contacter))
            {
                return "";
            }
            return contacter;
        }

        public String getContent()
        {
            if (TextUtils.isEmpty(content))
            {
                return "";
            }
            return content;
        }

        public String getDueTime()
        {
            if (TextUtils.isEmpty(dueTime))
            {
                return "";
            }
            return dueTime;
        }

        public String getId()
        {
            if (TextUtils.isEmpty(id))
            {
                return "";
            }
            return id;
        }

        public String getIp()
        {
            if (TextUtils.isEmpty(ip))
            {
                return "";
            }
            return ip;
        }

        public String getManagerId()
        {
            if (TextUtils.isEmpty(managerId))
            {
                return "";
            }
            return managerId;
        }

        public String getManagerName()
        {
            if (TextUtils.isEmpty(managerName))
            {
                return "";
            }
            return managerName;
        }

        public String getManual()
        {
            if (TextUtils.isEmpty(manual))
            {
                return "";
            }
            return manual;
        }

        public String getMaterial()
        {
            if (TextUtils.isEmpty(material))
            {
                return "";
            }
            return material;
        }

        public String getMemberId()
        {
            if (TextUtils.isEmpty(memberId))
            {
                return "";
            }
            return memberId;
        }

        public String getNormTime()
        {
            if (TextUtils.isEmpty(normTime))
            {
                return "";
            }
            return normTime;
        }

        public String getNote()
        {
            if (TextUtils.isEmpty(note))
            {
                return "";
            }
            return note;
        }

        public String getOrderSource()
        {
            if (TextUtils.isEmpty(orderSource))
            {
                return "";
            }
            return orderSource;
        }

        public String getOrderTime()
        {
            if (TextUtils.isEmpty(orderTime))
            {
                return "";
            }
            return orderTime;
        }

        public String getOrderType()
        {
            if (TextUtils.isEmpty(orderType))
            {
                return "";
            }
            return orderType;
        }

        public String getPhone()
        {
            if (TextUtils.isEmpty(phone))
            {
                return "";
            }
            return phone;
        }

        public String getPrice()
        {
            if (TextUtils.isEmpty(price))
            {
                return "";
            }
            return price;
        }

        public String getPublishTime()
        {
            if (TextUtils.isEmpty(publishTime))
            {
                return "";
            }
            return publishTime;
        }

        public String getQty()
        {
            if (TextUtils.isEmpty(qty))
            {
                return "";
            }
            return qty;
        }

        public String getQuotMemberId()
        {
            if (TextUtils.isEmpty(quotMemberId))
            {
                return "";
            }
            return quotMemberId;
        }

        public String getQuotMemberName()
        {
            if (TextUtils.isEmpty(quotMemberName))
            {
                return "";
            }
            return quotMemberName;
        }

        public String getQuotNum()
        {
            if (TextUtils.isEmpty(quotNum))
            {
                return "";
            }
            return quotNum;
        }

        public String getQuotPhone()
        {
            if (TextUtils.isEmpty(quotPhone))
            {
                return "";
            }
            return quotPhone;
        }

        public String getQuotUserId()
        {
            if (TextUtils.isEmpty(quotUserId))
            {
                return "";
            }
            return quotUserId;
        }

        public String getQuotUserName()
        {
            if (TextUtils.isEmpty(quotUserName))
            {
                return "";
            }
            return quotUserName;
        }

        public String getRemitBillUrl1()
        {
            if (TextUtils.isEmpty(remitBillUrl1))
            {
                return "";
            }
            return remitBillUrl1;
        }

        public String getRemitBillUrl2()
        {
            if (TextUtils.isEmpty(remitBillUrl2))
            {
                return "";
            }
            return remitBillUrl2;
        }

        public String getRemitBillUrl3()
        {
            if (TextUtils.isEmpty(remitBillUrl3))
            {
                return "";
            }
            return remitBillUrl3;
        }

        public String getRemitBillUrl4()
        {
            if (TextUtils.isEmpty(remitBillUrl4))
            {
                return "";
            }
            return remitBillUrl4;
        }

        public String getResource()
        {
            if (TextUtils.isEmpty(resource))
            {
                return "";
            }
            return resource;
        }

        public String getRushManagerId()
        {
            if (TextUtils.isEmpty(rushManagerId))
            {
                return "";
            }
            return rushManagerId;
        }

        public String getRushManagerName()
        {
            if (TextUtils.isEmpty(rushManagerName))
            {
                return "";
            }
            return rushManagerName;
        }

        public String getRushStatus()
        {
            if (TextUtils.isEmpty(rushStatus))
            {
                return "";
            }
            return rushStatus;
        }

        public String getRushTime()
        {
            if (TextUtils.isEmpty(rushTime))
            {
                return "";
            }
            return rushTime;
        }

        public String getSource()
        {
            if (TextUtils.isEmpty(source))
            {
                return "";
            }
            return source;
        }

        public String getSpec()
        {
            if (TextUtils.isEmpty(spec))
            {
                return "";
            }
            return spec;
        }

        public String getStanBuyId()
        {
            if (TextUtils.isEmpty(stanBuyId))
            {
                return "";
            }
            return stanBuyId;
        }

        public String getStatus()
        {
            if (TextUtils.isEmpty(status))
            {
                return "";
            }
            return status;
        }

        public String getUserId()
        {
            if (TextUtils.isEmpty(userId))
            {
                return "";
            }
            return userId;
        }

        public String getWarehouse()
        {
            if (TextUtils.isEmpty(warehouse))
            {
                return "";
            }
            return warehouse;
        }

        public String getDueStatus()
        {
            if (TextUtils.isEmpty(dueStatus))
            {
                return "";
            }
            return dueStatus;
        }

        public String getTotalAppraise()
        {
            if (TextUtils.isEmpty(totalAppraise))
            {
                return "";
            }
            return totalAppraise;
        }
    }
}

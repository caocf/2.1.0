package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 求购单实体类
 * Created by zoujian on 15/7/29.
 */
public class HistoryStanBuyData extends BaseData

{

//    private DataEntity data;
//
//
//    public void setData(DataEntity data)
//    {
//        this.data = data;
//    }
//
//    public DataEntity getData()
//    {
//        return data;
//    }
//
//    public class DataEntity
//    {
//        private PaginationEntity pagination;
//
//        public void setPagination(PaginationEntity pagination)
//        {
//            this.pagination = pagination;
//        }
//
//        public PaginationEntity getPagination()
//        {
//            return pagination;
//        }
//
//        public class PaginationEntity
//        {
//            private ArrayList<DatasEntity> datas;
//            private String count;
//            private String page;
//            private String pagenum;
//            private String size;
//
//            public void setDatas(ArrayList<DatasEntity> datas)
//            {
//                this.datas = datas;
//            }
//
//            public void setCount(String count)
//            {
//                this.count = count;
//            }
//
//            public void setPage(String page)
//            {
//                this.page = page;
//            }
//
//            public void setPagenum(String pagenum)
//            {
//                this.pagenum = pagenum;
//            }
//
//            public void setSize(String size)
//            {
//                this.size = size;
//            }
//
//            public List<DatasEntity> getDatas()
//            {
//                return datas;
//            }
//
//            public String getCount()
//            {
//                if (TextUtils.isEmpty(count))
//                {
//                    return "";
//                }
//                return count;
//            }
//
//            public String getPage()
//            {
//                if (TextUtils.isEmpty(page))
//                {
//                    return "";
//                }
//                return page;
//            }
//
//            public String getPagenum()
//            {
//                if (TextUtils.isEmpty(pagenum))
//                {
//                    return "";
//                }
//                return pagenum;
//            }
//
//            public String getSize()
//            {
//                if (TextUtils.isEmpty(size))
//                {
//                    return "";
//                }
//                return size;
//            }
//
//            public class DatasEntity
//            {
//                /**
//                 * spec : Φ14
//                 * breed : 二级螺纹钢
//                 * phone : 18616877614
//                 * cause : null
//                 * version : 0
//                 * city : 无锡
//                 * id : 249080
//                 * dueTime : 1436600082956
//                 * rushStatus : 0
//                 * rushManagerName :
//                 * userId : 46912
//                 * rushManagerId : 0
//                 * serialVersionUID : 1
//                 * lastAccess : 0
//                 * rushTime : 0
//                 * note :
//                 * dueStatus : 1
//                 * publishTime : 2015-07-08 15:34:42
//                 * status : 0
//                 * contacter :
//                 * breedId : 0101
//                 * resource : 2
//                 * qty : 50.0
//                 * quotNum : 0
//                 * material : HRB335E
//                 * manual : 1
//                 * price : 0.0
//                 * company : 将亟待解决实际上就是今生今世
//                 * quotUserId :
//                 * brand : 安徽贵航
//                 * memberId : 210199
//                 */
//                private String spec;
//                private String breed;
//                private String phone;
//                private String cause;
//                private String version;
//                private String city;
//                private String id;
//                private String dueTime;
//                private String rushStatus;
//                private String rushManagerName;
//                private String userId;
//                private String rushManagerId;
//                private String serialVersionUID;
//                private String lastAccess;
//                private String rushTime;
//                private String note;
//                private String dueStatus;
//                private String publishTime;
//                private String status;
//                private String contacter;
//                private String breedId;
//                private String resource;
//                private String qty;
//                private String quotNum;
//                private String material;
//                private String manual;
//                private String price;
//                private String company;
//                private String quotUserId;
//                private String brand;
//                private String memberId;
//
//                public void setSpec(String spec)
//                {
//                    this.spec = spec;
//                }
//
//                public void setBreed(String breed)
//                {
//                    this.breed = breed;
//                }
//
//                public void setPhone(String phone)
//                {
//                    this.phone = phone;
//                }
//
//                public void setCause(String cause)
//                {
//                    this.cause = cause;
//                }
//
//                public void setVersion(String version)
//                {
//                    this.version = version;
//                }
//
//                public void setCity(String city)
//                {
//                    this.city = city;
//                }
//
//                public void setId(String id)
//                {
//                    this.id = id;
//                }
//
//                public void setDueTime(String dueTime)
//                {
//                    this.dueTime = dueTime;
//                }
//
//                public void setRushStatus(String rushStatus)
//                {
//                    this.rushStatus = rushStatus;
//                }
//
//                public void setRushManagerName(String rushManagerName)
//                {
//                    this.rushManagerName = rushManagerName;
//                }
//
//                public void setUserId(String userId)
//                {
//                    this.userId = userId;
//                }
//
//                public void setRushManagerId(String rushManagerId)
//                {
//                    this.rushManagerId = rushManagerId;
//                }
//
//                public void setSerialVersionUID(String serialVersionUID)
//                {
//                    this.serialVersionUID = serialVersionUID;
//                }
//
//                public void setLastAccess(String lastAccess)
//                {
//                    this.lastAccess = lastAccess;
//                }
//
//                public void setRushTime(String rushTime)
//                {
//                    this.rushTime = rushTime;
//                }
//
//                public void setNote(String note)
//                {
//                    this.note = note;
//                }
//
//                public void setDueStatus(String dueStatus)
//                {
//                    this.dueStatus = dueStatus;
//                }
//
//                public void setPublishTime(String publishTime)
//                {
//                    this.publishTime = publishTime;
//                }
//
//                public void setStatus(String status)
//                {
//                    this.status = status;
//                }
//
//                public void setContacter(String contacter)
//                {
//                    this.contacter = contacter;
//                }
//
//                public void setBreedId(String breedId)
//                {
//                    this.breedId = breedId;
//                }
//
//                public void setResource(String resource)
//                {
//                    this.resource = resource;
//                }
//
//                public void setQty(String qty)
//                {
//                    this.qty = qty;
//                }
//
//                public void setQuotNum(String quotNum)
//                {
//                    this.quotNum = quotNum;
//                }
//
//                public void setMaterial(String material)
//                {
//                    this.material = material;
//                }
//
//                public void setManual(String manual)
//                {
//                    this.manual = manual;
//                }
//
//                public void setPrice(String price)
//                {
//                    this.price = price;
//                }
//
//                public void setCompany(String company)
//                {
//                    this.company = company;
//                }
//
//                public void setQuotUserId(String quotUserId)
//                {
//                    this.quotUserId = quotUserId;
//                }
//
//                public void setBrand(String brand)
//                {
//                    this.brand = brand;
//                }
//
//                public void setMemberId(String memberId)
//                {
//                    this.memberId = memberId;
//                }
//
//                public String getSpec()
//                {
//                    if (TextUtils.isEmpty(spec))
//                    {
//                        return "";
//                    }
//                    return spec;
//                }
//
//                public String getBreed()
//                {
//                    if (TextUtils.isEmpty(breed))
//                    {
//                        return "";
//                    }
//                    return breed;
//                }
//
//                public String getPhone()
//                {
//                    if (TextUtils.isEmpty(phone))
//                    {
//                        return "";
//                    }
//                    return phone;
//                }
//
//                public String getCause()
//                {
//                    if (TextUtils.isEmpty(cause))
//                    {
//                        return "";
//                    }
//                    return cause;
//                }
//
//                public String getVersion()
//                {
//                    if (TextUtils.isEmpty(version))
//                    {
//                        return "";
//                    }
//                    return version;
//                }
//
//                public String getCity()
//                {
//                    if (TextUtils.isEmpty(city))
//                    {
//                        return "";
//                    }
//                    return city;
//                }
//
//                public String getId()
//                {
//                    if (TextUtils.isEmpty(id))
//                    {
//                        return "";
//                    }
//                    return id;
//                }
//
//                public String getDueTime()
//                {
//                    if (TextUtils.isEmpty(dueTime))
//                    {
//                        return "";
//                    }
//                    return dueTime;
//                }
//
//                public String getRushStatus()
//                {
//                    if (TextUtils.isEmpty(rushStatus))
//                    {
//                        return "";
//                    }
//                    return rushStatus;
//                }
//
//                public String getRushManagerName()
//                {
//                    if (TextUtils.isEmpty(rushManagerName))
//                    {
//                        return "";
//                    }
//                    return rushManagerName;
//                }
//
//                public String getUserId()
//                {
//                    if (TextUtils.isEmpty(userId))
//                    {
//                        return "";
//                    }
//                    return userId;
//                }
//
//                public String getRushManagerId()
//                {
//                    if (TextUtils.isEmpty(rushManagerId))
//                    {
//                        return "";
//                    }
//                    return rushManagerId;
//                }
//
//                public String getSerialVersionUID()
//                {
//                    if (TextUtils.isEmpty(serialVersionUID))
//                    {
//                        return "";
//                    }
//                    return serialVersionUID;
//                }
//
//                public String getLastAccess()
//                {
//                    if (TextUtils.isEmpty(lastAccess))
//                    {
//                        return "";
//                    }
//                    return lastAccess;
//                }
//
//                public String getRushTime()
//                {
//                    if (TextUtils.isEmpty(rushTime))
//                    {
//                        return "";
//                    }
//                    return rushTime;
//                }
//
//                public String getNote()
//                {
//                    if (TextUtils.isEmpty(note))
//                    {
//                        return "";
//                    }
//                    return note;
//                }
//
//                public String getDueStatus()
//                {
//                    if (TextUtils.isEmpty(dueStatus))
//                    {
//                        return "";
//                    }
//                    return dueStatus;
//                }
//
//                public String getPublishTime()
//                {
//                    if (TextUtils.isEmpty(publishTime))
//                    {
//                        return "";
//                    }
//                    return publishTime;
//                }
//
//                public String getStatus()
//                {
//                    if (TextUtils.isEmpty(status))
//                    {
//                        return "";
//                    }
//                    return status;
//                }
//
//                public String getContacter()
//                {
//                    if (TextUtils.isEmpty(contacter))
//                    {
//                        return "";
//                    }
//                    return contacter;
//                }
//
//                public String getBreedId()
//                {
//                    if (TextUtils.isEmpty(breedId))
//                    {
//                        return "";
//                    }
//                    return breedId;
//                }
//
//                public String getResource()
//                {
//                    if (TextUtils.isEmpty(resource))
//                    {
//                        return "";
//                    }
//                    return resource;
//                }
//
//                public String getQty()
//                {
//                    if (TextUtils.isEmpty(qty))
//                    {
//                        return "";
//                    }
//                    return qty;
//                }
//
//                public String getQuotNum()
//                {
//                    if (TextUtils.isEmpty(quotNum))
//                    {
//                        return "";
//                    }
//                    return quotNum;
//                }
//
//                public String getMaterial()
//                {
//                    if (TextUtils.isEmpty(material))
//                    {
//                        return "";
//                    }
//                    return material;
//                }
//
//                public String getManual()
//                {
//                    if (TextUtils.isEmpty(manual))
//                    {
//                        return "";
//                    }
//                    return manual;
//                }
//
//                public String getPrice()
//                {
//                    if (TextUtils.isEmpty(price))
//                    {
//                        return "";
//                    }
//                    return price;
//                }
//
//                public String getCompany()
//                {
//                    if (TextUtils.isEmpty(company))
//                    {
//                        return "";
//                    }
//                    return company;
//                }
//
//                public String getQuotUserId()
//                {
//                    if (TextUtils.isEmpty(quotUserId))
//                    {
//                        return "";
//                    }
//                    return quotUserId;
//                }
//
//                public String getBrand()
//                {
//                    if (TextUtils.isEmpty(brand))
//                    {
//                        return "";
//                    }
//                    return brand;
//                }
//
//                public String getMemberId()
//                {
//                    if (TextUtils.isEmpty(memberId))
//                    {
//                        return "";
//                    }
//                    return memberId;
//                }
//            }
//        }
//    }

    private DataEntity data;

    public DataEntity getData()
    {
        return data;
    }

    public void setData(DataEntity data)
    {
        this.data = data;
    }

    public class DataEntity
    {
        private PaginationEntity pagination;

        public void setPagination(PaginationEntity pagination)
        {
            this.pagination = pagination;
        }

        public PaginationEntity getPagination()
        {
            return pagination;
        }

        public class PaginationEntity
        {
            private List<DatasEntity> datas;
            private String count;
            private String page;
            private String pagenum;
            private String size;

            public void setDatas(List<DatasEntity> datas)
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

            public List<DatasEntity> getDatas()
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
                private String spec;
                private String breed;
                private String phone;
                private String cause;
                private String version;
                private String city;
                private String dueTime;
                private String buyType;//
                private String id;
                private String audioTimes;//
                private String rushStatus;
                private String rushManagerName;
                private String userId;
                private String rushManagerId;
                private String serialVersionUID;
                private String lastAccess;
                private String rushTime;
                private String note;
                private String dueStatus;
                private String audioFileUrl;//
                private String publishTime;
                private String status;
                private String contacter;
                private String resource;
                private String breedId;
                private String qty;
                private String quotNum;
                private String material;
                private String ip;
                private String content;
                private String manual;
                private String price;
                private String quotUserId;
                private String company;
                private String brand;
                private String memberId;

                public void setSpec(String spec)
                {
                    this.spec = spec;
                }

                public void setBreed(String breed)
                {
                    this.breed = breed;
                }

                public void setPhone(String phone)
                {
                    this.phone = phone;
                }

                public void setCause(String cause)
                {
                    this.cause = cause;
                }

                public void setVersion(String version)
                {
                    this.version = version;
                }

                public void setCity(String city)
                {
                    this.city = city;
                }

                public void setDueTime(String dueTime)
                {
                    this.dueTime = dueTime;
                }

                public void setBuyType(String buyType)
                {
                    this.buyType = buyType;
                }

                public void setId(String id)
                {
                    this.id = id;
                }

                public void setAudioTimes(String audioTimes)
                {
                    this.audioTimes = audioTimes;
                }

                public void setRushStatus(String rushStatus)
                {
                    this.rushStatus = rushStatus;
                }

                public void setRushManagerName(String rushManagerName)
                {
                    this.rushManagerName = rushManagerName;
                }

                public void setUserId(String userId)
                {
                    this.userId = userId;
                }

                public void setRushManagerId(String rushManagerId)
                {
                    this.rushManagerId = rushManagerId;
                }

                public void setSerialVersionUID(String serialVersionUID)
                {
                    this.serialVersionUID = serialVersionUID;
                }

                public void setLastAccess(String lastAccess)
                {
                    this.lastAccess = lastAccess;
                }

                public void setRushTime(String rushTime)
                {
                    this.rushTime = rushTime;
                }

                public void setNote(String note)
                {
                    this.note = note;
                }

                public void setDueStatus(String dueStatus)
                {
                    this.dueStatus = dueStatus;
                }

                public void setAudioFileUrl(String audioFileUrl)
                {
                    this.audioFileUrl = audioFileUrl;
                }

                public void setPublishTime(String publishTime)
                {
                    this.publishTime = publishTime;
                }

                public void setStatus(String status)
                {
                    this.status = status;
                }

                public void setContacter(String contacter)
                {
                    this.contacter = contacter;
                }

                public void setResource(String resource)
                {
                    this.resource = resource;
                }

                public void setBreedId(String breedId)
                {
                    this.breedId = breedId;
                }

                public void setQty(String qty)
                {
                    this.qty = qty;
                }

                public void setQuotNum(String quotNum)
                {
                    this.quotNum = quotNum;
                }

                public void setMaterial(String material)
                {
                    this.material = material;
                }

                public void setIp(String ip)
                {
                    this.ip = ip;
                }

                public void setContent(String content)
                {
                    this.content = content;
                }

                public void setManual(String manual)
                {
                    this.manual = manual;
                }

                public void setPrice(String price)
                {
                    this.price = price;
                }

                public void setQuotUserId(String quotUserId)
                {
                    this.quotUserId = quotUserId;
                }

                public void setCompany(String company)
                {
                    this.company = company;
                }

                public void setBrand(String brand)
                {
                    this.brand = brand;
                }

                public void setMemberId(String memberId)
                {
                    this.memberId = memberId;
                }

                public String getSpec()
                {
                    if (TextUtils.isEmpty(spec))
                    {
                        return "";
                    }
                    return spec;
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

                public String getCause()
                {
                    if (TextUtils.isEmpty(cause))
                    {
                        return "";
                    }
                    return cause;
                }

                public String getVersion()
                {
                    if (TextUtils.isEmpty(version))
                    {
                        return "";
                    }
                    return version;
                }

                public String getCity()
                {
                    if (TextUtils.isEmpty(city))
                    {
                        return "";
                    }
                    return city;
                }

                public String getDueTime()
                {
                    if (TextUtils.isEmpty(dueTime))
                    {
                        return "";
                    }
                    return dueTime;
                }

                public String getBuyType()
                {
                    if (TextUtils.isEmpty(buyType))
                    {
                        return "";
                    }
                    return buyType;
                }

                public String getId()
                {
                    if (TextUtils.isEmpty(id))
                    {
                        return "";
                    }
                    return id;
                }

                public String getAudioTimes()
                {
                    if (TextUtils.isEmpty(audioTimes))
                    {
                        return "";
                    }
                    return audioTimes;
                }

                public String getRushStatus()
                {
                    if (TextUtils.isEmpty(rushStatus))
                    {
                        return "";
                    }
                    return rushStatus;
                }

                public String getRushManagerName()
                {
                    if (TextUtils.isEmpty(rushManagerName))
                    {
                        return "";
                    }
                    return rushManagerName;
                }

                public String getUserId()
                {
                    if (TextUtils.isEmpty(userId))
                    {
                        return "";
                    }
                    return userId;
                }

                public String getRushManagerId()
                {
                    if (TextUtils.isEmpty(rushManagerId))
                    {
                        return "";
                    }
                    return rushManagerId;
                }

                public String getSerialVersionUID()
                {
                    if (TextUtils.isEmpty(serialVersionUID))
                    {
                        return "";
                    }
                    return serialVersionUID;
                }

                public String getLastAccess()
                {
                    if (TextUtils.isEmpty(lastAccess))
                    {
                        return "";
                    }
                    return lastAccess;
                }

                public String getRushTime()
                {
                    if (TextUtils.isEmpty(rushTime))
                    {
                        return "";
                    }
                    return rushTime;
                }

                public String getNote()
                {
                    if (TextUtils.isEmpty(note))
                    {
                        return "";
                    }
                    return note;
                }

                public String getDueStatus()
                {
                    if (TextUtils.isEmpty(dueStatus))
                    {
                        return "";
                    }
                    return dueStatus;
                }

                public String getAudioFileUrl()
                {
                    if (TextUtils.isEmpty(audioFileUrl))
                    {
                        return "";
                    }
                    return audioFileUrl;
                }

                public String getPublishTime()
                {
                    if (TextUtils.isEmpty(publishTime))
                    {
                        return "";
                    }
                    return publishTime;
                }

                public String getStatus()
                {
                    if (TextUtils.isEmpty(status))
                    {
                        return "";
                    }
                    return status;
                }

                public String getContacter()
                {
                    if (TextUtils.isEmpty(contacter))
                    {
                        return "";
                    }
                    return contacter;
                }

                public String getResource()
                {
                    if (TextUtils.isEmpty(resource))
                    {
                        return "";
                    }
                    return resource;
                }

                public String getBreedId()
                {
                    if (TextUtils.isEmpty(breedId))
                    {
                        return "";
                    }
                    return breedId;
                }

                public String getQty()
                {
                    if (TextUtils.isEmpty(qty))
                    {
                        return "";
                    }
                    return qty;
                }

                public String getQuotNum()
                {
                    if (TextUtils.isEmpty(quotNum))
                    {
                        return "";
                    }
                    return quotNum;
                }

                public String getMaterial()
                {
                    if (TextUtils.isEmpty(material))
                    {
                        return "";
                    }
                    return material;
                }

                public String getIp()
                {
                    if (TextUtils.isEmpty(ip))
                    {
                        return "";
                    }
                    return ip;
                }

                public String getContent()
                {
                    if (TextUtils.isEmpty(content))
                    {
                        return "";
                    }
                    return content;
                }

                public String getManual()
                {
                    if (TextUtils.isEmpty(manual))
                    {
                        return "";
                    }
                    return manual;
                }

                public String getPrice()
                {
                    if (TextUtils.isEmpty(price))
                    {
                        return "";
                    }
                    return price;
                }

                public String getQuotUserId()
                {
                    if (TextUtils.isEmpty(quotUserId))
                    {
                        return "";
                    }
                    return quotUserId;
                }

                public String getCompany()
                {
                    if (TextUtils.isEmpty(company))
                    {
                        return "";
                    }
                    return company;
                }

                public String getBrand()
                {
                    if (TextUtils.isEmpty(brand))
                    {
                        return "";
                    }
                    return brand;
                }

                public String getMemberId()
                {
                    if (TextUtils.isEmpty(memberId))
                    {
                        return "";
                    }
                    return memberId;
                }
            }
        }
    }
}

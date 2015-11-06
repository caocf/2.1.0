package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 卖家报价实体类
 * Created by zoujian on 15/8/8.
 */
public class MyQuotData extends BaseData
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
             * spec : 3.75
             * buyPhone : 18616877614
             * breed : 翼缘板
             * quotState : 正常报价
             * userCity : 合肥市
             * buyMemberId : 210199
             * userPhoto : http://192.168.100.115:8180/assistant/upload/header/46892.jpg
             * quotMemberName : 手机APP注册15821398481
             * quotMemberId : 210180
             * buyUserId : 46912
             * warehouse :
             * type : 1
             * version : 0
             * city : 上海
             * userState : 安徽省
             * id : 243178
             * dueTime : 1439198887276
             * userCountry :
             * bargaining : 0
             * lastAccess : 1439016790869
             * serialVersionUID : 1
             * userArea : 瑶海区
             * dueStatues : 求购单有效
             * quotContact : 张无忌
             * status : 0
             * stanBuyId : 249238
             * manaerId : 0
             * qty : 56.0
             * buyMemberName : 将亟待解决实际上就是今生今世
             * material : SAPH400
             * buyUserName : 孔凡峰
             * price : 2000.0
             * quotTime : 2015-08-08 14:53:10
             * quotUserId : 46892
             * quotPhone : 15821398481
             * brand : 宝钢
             */
            private String spec;
            private String buyPhone;
            private String breed;
            private String quotState;
            private String userCity;
            private String buyMemberId;
            private String userPhoto;
            private String quotMemberName;
            private String quotMemberId;
            private String buyUserId;
            private String warehouse;
            private String type;
            private String version;
            private String city;
            private String userState;
            private String id;
            private String dueTime;
            private String userCountry;
            private String bargaining;
            private String lastAccess;
            private String serialVersionUID;
            private String userArea;
            private String dueStatues;
            private String quotContact;
            private String status;
            private String stanBuyId;
            private String manaerId;
            private String qty;
            private String buyMemberName;
            private String material;
            private String buyUserName;
            private String price;
            private String quotTime;
            private String quotUserId;
            private String quotPhone;
            private String brand;
            private String userProvince;
            private String note;

            public String getNote() {
                if(TextUtils.isEmpty(note))
                {
                    return "";
                }
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }


            public String getUserProvince()
            {
                if(TextUtils.isEmpty(userProvince))
                {
                    return "";
                }
                return userProvince;
            }

            public void setUserProvince(String userProvince)
            {
                this.userProvince = userProvince;
            }

            public void setSpec(String spec)
            {
                this.spec = spec;
            }

            public void setBuyPhone(String buyPhone)
            {
                this.buyPhone = buyPhone;
            }

            public void setBreed(String breed)
            {
                this.breed = breed;
            }

            public void setQuotState(String quotState)
            {
                this.quotState = quotState;
            }

            public void setUserCity(String userCity)
            {
                this.userCity = userCity;
            }

            public void setBuyMemberId(String buyMemberId)
            {
                this.buyMemberId = buyMemberId;
            }

            public void setUserPhoto(String userPhoto)
            {
                this.userPhoto = userPhoto;
            }

            public void setQuotMemberName(String quotMemberName)
            {
                this.quotMemberName = quotMemberName;
            }

            public void setQuotMemberId(String quotMemberId)
            {
                this.quotMemberId = quotMemberId;
            }

            public void setBuyUserId(String buyUserId)
            {
                this.buyUserId = buyUserId;
            }

            public void setWarehouse(String warehouse)
            {
                this.warehouse = warehouse;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public void setVersion(String version)
            {
                this.version = version;
            }

            public void setCity(String city)
            {
                this.city = city;
            }

            public void setUserState(String userState)
            {
                this.userState = userState;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public void setDueTime(String dueTime)
            {
                this.dueTime = dueTime;
            }

            public void setUserCountry(String userCountry)
            {
                this.userCountry = userCountry;
            }

            public void setBargaining(String bargaining)
            {
                this.bargaining = bargaining;
            }

            public void setLastAccess(String lastAccess)
            {
                this.lastAccess = lastAccess;
            }

            public void setSerialVersionUID(String serialVersionUID)
            {
                this.serialVersionUID = serialVersionUID;
            }

            public void setUserArea(String userArea)
            {
                this.userArea = userArea;
            }

            public void setDueStatues(String dueStatues)
            {
                this.dueStatues = dueStatues;
            }

            public void setQuotContact(String quotContact)
            {
                this.quotContact = quotContact;
            }

            public void setStatus(String status)
            {
                this.status = status;
            }

            public void setStanBuyId(String stanBuyId)
            {
                this.stanBuyId = stanBuyId;
            }

            public void setManaerId(String manaerId)
            {
                this.manaerId = manaerId;
            }

            public void setQty(String qty)
            {
                this.qty = qty;
            }

            public void setBuyMemberName(String buyMemberName)
            {
                this.buyMemberName = buyMemberName;
            }

            public void setMaterial(String material)
            {
                this.material = material;
            }

            public void setBuyUserName(String buyUserName)
            {
                this.buyUserName = buyUserName;
            }

            public void setPrice(String price)
            {
                this.price = price;
            }

            public void setQuotTime(String quotTime)
            {
                this.quotTime = quotTime;
            }

            public void setQuotUserId(String quotUserId)
            {
                this.quotUserId = quotUserId;
            }

            public void setQuotPhone(String quotPhone)
            {
                this.quotPhone = quotPhone;
            }

            public void setBrand(String brand)
            {
                this.brand = brand;
            }

            public String getSpec()
            {
                if (TextUtils.isEmpty(spec))
                {
                    return "";
                }
                return spec;
            }

            public String getBuyPhone()
            {
                if (TextUtils.isEmpty(buyPhone))
                {
                    return "";
                }
                return buyPhone;
            }

            public String getBreed()
            {
                if (TextUtils.isEmpty(breed))
                {
                    return "";
                }
                return breed;
            }

            public String getQuotState()
            {
                if (TextUtils.isEmpty(quotState))
                {
                    return "";
                }
                return quotState;
            }

            public String getUserCity()
            {
                if (TextUtils.isEmpty(userCity))
                {
                    return "";
                }
                return userCity;
            }

            public String getBuyMemberId()
            {
                if (TextUtils.isEmpty(buyMemberId))
                {
                    return "";
                }
                return buyMemberId;
            }

            public String getUserPhoto()
            {
                if (TextUtils.isEmpty(userPhoto))
                {
                    return "";
                }
                return userPhoto;
            }

            public String getQuotMemberName()
            {
                if (TextUtils.isEmpty(quotMemberName))
                {
                    return "";
                }
                return quotMemberName;
            }

            public String getQuotMemberId()
            {
                if (TextUtils.isEmpty(quotMemberId))
                {
                    return "";
                }
                return quotMemberId;
            }

            public String getBuyUserId()
            {
                if (TextUtils.isEmpty(buyUserId))
                {
                    return "";
                }
                return buyUserId;
            }

            public String getWarehouse()
            {
                if (TextUtils.isEmpty(warehouse))
                {
                    return "";
                }
                return warehouse;
            }

            public String getType()
            {
                if (TextUtils.isEmpty(type))
                {
                    return "";
                }
                return type;
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

            public String getUserState()
            {
                if (TextUtils.isEmpty(userState))
                {
                    return "";
                }
                return userState;
            }

            public String getId()
            {
                if (TextUtils.isEmpty(id))
                {
                    return "";
                }
                return id;
            }

            public String getDueTime()
            {
                if (TextUtils.isEmpty(dueTime))
                {
                    return "";
                }
                return dueTime;
            }

            public String getUserCountry()
            {
                if (TextUtils.isEmpty(userCountry))
                {
                    return "";
                }
                return userCountry;
            }

            public String getBargaining()
            {
                if (TextUtils.isEmpty(bargaining))
                {
                    return "";
                }
                return bargaining;
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

            public String getUserArea()
            {
                if (TextUtils.isEmpty(userArea))
                {
                    return "";
                }
                return userArea;
            }

            public String getDueStatues()
            {
                if (TextUtils.isEmpty(dueStatues))
                {
                    return "";
                }
                return dueStatues;
            }

            public String getQuotContact()
            {
                if (TextUtils.isEmpty(quotContact))
                {
                    return "";
                }
                return quotContact;
            }

            public String getStatus()
            {
                if (TextUtils.isEmpty(status))
                {
                    return "";
                }
                return status;
            }

            public String getStanBuyId()
            {
                if (TextUtils.isEmpty(stanBuyId))
                {
                    return "";
                }
                return stanBuyId;
            }

            public String getManaerId()
            {
                if (TextUtils.isEmpty(manaerId))
                {
                    return "";
                }
                return manaerId;
            }

            public String getQty()
            {
                if (TextUtils.isEmpty(qty))
                {
                    return "";
                }
                return qty;
            }

            public String getBuyMemberName()
            {
                if (TextUtils.isEmpty(buyMemberName))
                {
                    return "";
                }
                return buyMemberName;
            }

            public String getMaterial()
            {
                if (TextUtils.isEmpty(material))
                {
                    return "";
                }
                return material;
            }

            public String getBuyUserName()
            {
                if (TextUtils.isEmpty(buyUserName))
                {
                    return "";
                }
                return buyUserName;
            }

            public String getPrice()
            {
                if (TextUtils.isEmpty(price))
                {
                    return "";
                }
                return price;
            }

            public String getQuotTime()
            {
                if (TextUtils.isEmpty(quotTime))
                {
                    return "";
                }
                return quotTime;
            }

            public String getQuotUserId()
            {
                if (TextUtils.isEmpty(quotUserId))
                {
                    return "";
                }
                return quotUserId;
            }

            public String getQuotPhone()
            {
                if (TextUtils.isEmpty(quotPhone))
                {
                    return "";
                }
                return quotPhone;
            }

            public String getBrand()
            {
                if (TextUtils.isEmpty(brand))
                {
                    return "";
                }
                return brand;
            }
        }
    }
//    private DataEntity data;
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
//        private ArrayList<DatasEntity> datas;
//        private String count;
//        private String page;
//        private String pagenum;
//        private String size;
//
//        public void setDatas(ArrayList<DatasEntity> datas)
//        {
//            this.datas = datas;
//        }
//
//        public void setCount(String count)
//        {
//            this.count = count;
//        }
//
//        public void setPage(String page)
//        {
//            this.page = page;
//        }
//
//        public void setPagenum(String pagenum)
//        {
//            this.pagenum = pagenum;
//        }
//
//        public void setSize(String size)
//        {
//            this.size = size;
//        }
//
//        public ArrayList<DatasEntity> getDatas()
//        {
//            return datas;
//        }
//
//        public String getCount()
//        {
//            if (TextUtils.isEmpty(count))
//            {
//                return "";
//            }
//            return count;
//        }
//
//        public String getPage()
//        {
//            if (TextUtils.isEmpty(page))
//            {
//                return "";
//            }
//            return page;
//        }
//
//        public String getPagenum()
//        {
//            if (TextUtils.isEmpty(pagenum))
//            {
//                return "";
//            }
//            return pagenum;
//        }
//
//        public String getSize()
//        {
//            if (TextUtils.isEmpty(size))
//            {
//                return "";
//            }
//            return size;
//        }
//
//        public class DatasEntity implements Serializable
//        {
//            /**
//             * buyPhone : 18616877614
//             * spec : 14
//             * breed : 普中板
//             * quotState : 正常报价
//             * buyMemberId : 210199
//             * quotMemberName : 手机APP注册15821398481
//             * quotMemberId : 210180
//             * buyUserId : 46912
//             * warehouse :
//             * type : 0
//             * version : 0
//             * id : 243177
//             * bargaining : 0
//             * lastAccess : 1439003675829
//             * serialVersionUID : 1
//             * quotContact : 张无忌
//             * stanBuyId : 249239
//             * status : 0
//             * manaerId : 0
//             * material : S355J0
//             * buyMemberName : 将亟待解决实际上就是今生今世
//             * buyUserName : 孔凡峰
//             * price : 1000.0
//             * quotTime : 2015-08-08 11:14:35
//             * quotUserId : 46892
//             * quotPhone : 15821398481
//             * brand : 本钢
//             */
//            private String buyPhone;
//            private String spec;
//            private String breed;
//            private String quotState;
//            private String buyMemberId;
//            private String quotMemberName;
//            private String quotMemberId;
//            private String buyUserId;
//            private String warehouse;
//            private String type;
//            private String version;
//            private String id;
//            private String bargaining;
//            private String lastAccess;
//            private String serialVersionUID;
//            private String quotContact;
//            private String stanBuyId;
//            private String status;
//            private String manaerId;
//            private String material;
//            private String buyMemberName;
//            private String buyUserName;
//            private String price;
//            private String quotTime;
//            private String quotUserId;
//            private String quotPhone;
//            private String brand;
//            private String qty;
//            private String city;
//
//            public String getCity()
//            {
//                if(TextUtils.isEmpty(city))
//                {
//                    return "";
//                }
//                return city;
//            }
//
//            public void setCity(String city)
//            {
//                this.city = city;
//            }
//
//            public String getQty()
//            {
//                if(TextUtils.isEmpty(qty))
//                {
//                    return "";
//                }
//                return qty;
//            }
//
//            public void setQty(String qty)
//            {
//                this.qty = qty;
//            }
//
//            public void setBuyPhone(String buyPhone)
//            {
//                this.buyPhone = buyPhone;
//            }
//
//            public void setSpec(String spec)
//            {
//                this.spec = spec;
//            }
//
//            public void setBreed(String breed)
//            {
//                this.breed = breed;
//            }
//
//            public void setQuotState(String quotState)
//            {
//                this.quotState = quotState;
//            }
//
//            public void setBuyMemberId(String buyMemberId)
//            {
//                this.buyMemberId = buyMemberId;
//            }
//
//            public void setQuotMemberName(String quotMemberName)
//            {
//                this.quotMemberName = quotMemberName;
//            }
//
//            public void setQuotMemberId(String quotMemberId)
//            {
//                this.quotMemberId = quotMemberId;
//            }
//
//            public void setBuyUserId(String buyUserId)
//            {
//                this.buyUserId = buyUserId;
//            }
//
//            public void setWarehouse(String warehouse)
//            {
//                this.warehouse = warehouse;
//            }
//
//            public void setType(String type)
//            {
//                this.type = type;
//            }
//
//            public void setVersion(String version)
//            {
//                this.version = version;
//            }
//
//            public void setId(String id)
//            {
//                this.id = id;
//            }
//
//            public void setBargaining(String bargaining)
//            {
//                this.bargaining = bargaining;
//            }
//
//            public void setLastAccess(String lastAccess)
//            {
//                this.lastAccess = lastAccess;
//            }
//
//            public void setSerialVersionUID(String serialVersionUID)
//            {
//                this.serialVersionUID = serialVersionUID;
//            }
//
//            public void setQuotContact(String quotContact)
//            {
//                this.quotContact = quotContact;
//            }
//
//            public void setStanBuyId(String stanBuyId)
//            {
//                this.stanBuyId = stanBuyId;
//            }
//
//            public void setStatus(String status)
//            {
//                this.status = status;
//            }
//
//            public void setManaerId(String manaerId)
//            {
//                this.manaerId = manaerId;
//            }
//
//            public void setMaterial(String material)
//            {
//                this.material = material;
//            }
//
//            public void setBuyMemberName(String buyMemberName)
//            {
//                this.buyMemberName = buyMemberName;
//            }
//
//            public void setBuyUserName(String buyUserName)
//            {
//                this.buyUserName = buyUserName;
//            }
//
//            public void setPrice(String price)
//            {
//                this.price = price;
//            }
//
//            public void setQuotTime(String quotTime)
//            {
//                this.quotTime = quotTime;
//            }
//
//            public void setQuotUserId(String quotUserId)
//            {
//                this.quotUserId = quotUserId;
//            }
//
//            public void setQuotPhone(String quotPhone)
//            {
//                this.quotPhone = quotPhone;
//            }
//
//            public void setBrand(String brand)
//            {
//                this.brand = brand;
//            }
//
//            public String getBuyPhone()
//            {
//                if (TextUtils.isEmpty(buyPhone))
//                {
//                    return "";
//                }
//                return buyPhone;
//            }
//
//            public String getSpec()
//            {
//                if (TextUtils.isEmpty(spec))
//                {
//                    return "";
//                }
//                return spec;
//            }
//
//            public String getBreed()
//            {
//                if (TextUtils.isEmpty(breed))
//                {
//                    return "";
//                }
//                return breed;
//            }
//
//            public String getQuotState()
//            {
//                if (TextUtils.isEmpty(quotState))
//                {
//                    return "";
//                }
//                return quotState;
//            }
//
//            public String getBuyMemberId()
//            {
//                if (TextUtils.isEmpty(buyMemberId))
//                {
//                    return "";
//                }
//                return buyMemberId;
//            }
//
//            public String getQuotMemberName()
//            {
//                if (TextUtils.isEmpty(quotMemberName))
//                {
//                    return "";
//                }
//                return quotMemberName;
//            }
//
//            public String getQuotMemberId()
//            {
//                if (TextUtils.isEmpty(quotMemberId))
//                {
//                    return "";
//                }
//                return quotMemberId;
//            }
//
//            public String getBuyUserId()
//            {
//                if (TextUtils.isEmpty(buyUserId))
//                {
//                    return "";
//                }
//                return buyUserId;
//            }
//
//            public String getWarehouse()
//            {
//                if (TextUtils.isEmpty(warehouse))
//                {
//                    return "";
//                }
//                return warehouse;
//            }
//
//            public String getType()
//            {
//                if (TextUtils.isEmpty(type))
//                {
//                    return "";
//                }
//                return type;
//            }
//
//            public String getVersion()
//            {
//                if (TextUtils.isEmpty(version))
//                {
//                    return "";
//                }
//                return version;
//            }
//
//            public String getId()
//            {
//                if (TextUtils.isEmpty(id))
//                {
//                    return "";
//                }
//                return id;
//            }
//
//            public String getBargaining()
//            {
//                if (TextUtils.isEmpty(bargaining))
//                {
//                    return "";
//                }
//                return bargaining;
//            }
//
//            public String getLastAccess()
//            {
//                if (TextUtils.isEmpty(lastAccess))
//                {
//                    return "";
//                }
//                return lastAccess;
//            }
//
//            public String getSerialVersionUID()
//            {
//                if (TextUtils.isEmpty(serialVersionUID))
//                {
//                    return "";
//                }
//                return serialVersionUID;
//            }
//
//            public String getQuotContact()
//            {
//                if (TextUtils.isEmpty(quotContact))
//                {
//                    return "";
//                }
//                return quotContact;
//            }
//
//            public String getStanBuyId()
//            {
//                if (TextUtils.isEmpty(stanBuyId))
//                {
//                    return "";
//                }
//                return stanBuyId;
//            }
//
//            public String getStatus()
//            {
//                if (TextUtils.isEmpty(status))
//                {
//                    return "";
//                }
//                return status;
//            }
//
//            public String getManaerId()
//            {
//                if (TextUtils.isEmpty(manaerId))
//                {
//                    return "";
//                }
//                return manaerId;
//            }
//
//            public String getMaterial()
//            {
//                if (TextUtils.isEmpty(material))
//                {
//                    return "";
//                }
//                return material;
//            }
//
//            public String getBuyMemberName()
//            {
//                if (TextUtils.isEmpty(buyMemberName))
//                {
//                    return "";
//                }
//                return buyMemberName;
//            }
//
//            public String getBuyUserName()
//            {
//                if (TextUtils.isEmpty(buyUserName))
//                {
//                    return "";
//                }
//                return buyUserName;
//            }
//
//            public String getPrice()
//            {
//                if (TextUtils.isEmpty(price))
//                {
//                    return "";
//                }
//                return price;
//            }
//
//            public String getQuotTime()
//            {
//                if (TextUtils.isEmpty(quotTime))
//                {
//                    return "";
//                }
//                return quotTime;
//            }
//
//            public String getQuotUserId()
//            {
//                if (TextUtils.isEmpty(quotUserId))
//                {
//                    return "";
//                }
//                return quotUserId;
//            }
//
//            public String getQuotPhone()
//            {
//                if (TextUtils.isEmpty(quotPhone))
//                {
//                    return "";
//                }
//                return quotPhone;
//            }
//
//            public String getBrand()
//            {
//                if (TextUtils.isEmpty(brand))
//                {
//                    return "";
//                }
//                return brand;
//            }
//        }
//    }


}

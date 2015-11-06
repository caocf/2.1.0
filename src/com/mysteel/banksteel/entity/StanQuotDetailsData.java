package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 求购单报价的卖家信息
 * Created by zoujian on 15/8/5.
 */
public class StanQuotDetailsData extends BaseData
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
        private ArrayList<Datas> datas;
        private String count;
        private String page;
        private String pagenum;
        private String size;

        public void setDatas(ArrayList<Datas> datas)
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

        public ArrayList<Datas> getDatas()
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

        public class Datas implements Serializable
        {
            /**
             * buyPhone : 15821398481
             * spec : Φ12
             * breed : 三级螺纹钢
             * quotState : 正常报价
             * buyMemberId : 210180
             * quotMemberName : 广东汇马贸易有限公司
             * quotMemberId : 10040
             * buyUserId : 46892
             * warehouse : 武汉
             * type : 1
             * version : 0
             * id : 242893
             * bargaining : 0
             * lastAccess : 1434004994948
             * serialVersionUID : 1
             * quotContact : 孙广海
             * stanBuyId : 234262
             * status : 0
             * manaerId : 202024
             * material : HRB400E
             * note : 备注
             * buyMemberName : 上海钢联
             * buyUserName : 15821398481
             * price : 3000.0
             * quotTime : 2015-06-11 14:43:14
             * quotUserId : 123
             * quotPhone : 13632399870
             * brand : 安徽鸿泰
             */
            private String buyPhone;
            private String spec;
            private String breed;
            private String quotState;
            private String buyMemberId;
            private String quotMemberName;
            private String quotMemberId;
            private String buyUserId;
            private String warehouse;
            private String type;
            private String version;
            private String id;
            private String bargaining;
            private String lastAccess;
            private String serialVersionUID;
            private String quotContact;
            private String stanBuyId;
            private String status;
            private String manaerId;
            private String material;
            private String note;
            private String buyMemberName;
            private String buyUserName;
            private String price;
            private String quotTime;
            private String quotUserId;
            private String quotPhone;
            private String brand;
            private String attestation;
            private String city;
            private String dueStatues;
            private String dueTime;
            private String qty;
            private String userArea;
            private String userCity;
            private String userCountry;
            private String userPhoto;
            private String userProvince;

            public String getAttestation()
            {
                if (TextUtils.isEmpty(attestation))
                {
                    return "";
                }
                return attestation;
            }

            public void setAttestation(String attestation)
            {
                this.attestation = attestation;
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

            public String getDueStatues()
            {
                if (TextUtils.isEmpty(dueStatues))
                {
                    return "";
                }
                return dueStatues;
            }

            public void setDueStatues(String dueStatues)
            {
                this.dueStatues = dueStatues;
            }

            public String getDueTime()
            {
                if (TextUtils.isEmpty(dueTime))
                {
                    return "";
                }
                return dueTime;
            }

            public void setDueTime(String dueTime)
            {
                this.dueTime = dueTime;
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

            public String getUserArea()
            {
                if (TextUtils.isEmpty(userArea))
                {
                    return "";
                }
                return userArea;
            }

            public void setUserArea(String userArea)
            {
                this.userArea = userArea;
            }

            public String getUserCity()
            {
                if (TextUtils.isEmpty(userCity))
                {
                    return "";
                }
                return userCity;
            }

            public void setUserCity(String userCity)
            {
                this.userCity = userCity;
            }

            public String getUserCountry()
            {
                if (TextUtils.isEmpty(userCountry))
                {
                    return "";
                }
                return userCountry;
            }

            public void setUserCountry(String userCountry)
            {
                this.userCountry = userCountry;
            }

            public String getUserPhoto()
            {
                if (TextUtils.isEmpty(userPhoto))
                {
                    return "";
                }
                return userPhoto;
            }

            public void setUserPhoto(String userPhoto)
            {
                this.userPhoto = userPhoto;
            }

            public String getUserProvince()
            {
                if (TextUtils.isEmpty(userProvince))
                {
                    return "";
                }
                return userProvince;
            }

            public void setUserProvince(String userProvince)
            {
                this.userProvince = userProvince;
            }

            public void setBuyPhone(String buyPhone)
            {
                this.buyPhone = buyPhone;
            }

            public void setSpec(String spec)
            {
                this.spec = spec;
            }

            public void setBreed(String breed)
            {
                this.breed = breed;
            }

            public void setQuotState(String quotState)
            {
                this.quotState = quotState;
            }

            public void setBuyMemberId(String buyMemberId)
            {
                this.buyMemberId = buyMemberId;
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

            public void setId(String id)
            {
                this.id = id;
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

            public void setQuotContact(String quotContact)
            {
                this.quotContact = quotContact;
            }

            public void setStanBuyId(String stanBuyId)
            {
                this.stanBuyId = stanBuyId;
            }

            public void setStatus(String status)
            {
                this.status = status;
            }

            public void setManaerId(String manaerId)
            {
                this.manaerId = manaerId;
            }

            public void setMaterial(String material)
            {
                this.material = material;
            }

            public void setBuyMemberName(String buyMemberName)
            {
                this.buyMemberName = buyMemberName;
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

            public String getBuyPhone()
            {
                if (TextUtils.isEmpty(buyPhone))
                {
                    return "";
                }
                return buyPhone;
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

            public String getQuotState()
            {
                if (TextUtils.isEmpty(quotState))
                {
                    return "";
                }
                return quotState;
            }

            public String getBuyMemberId()
            {
                if (TextUtils.isEmpty(buyMemberId))
                {
                    return "";
                }
                return buyMemberId;
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

            public String getId()
            {
                if (TextUtils.isEmpty(id))
                {
                    return "";
                }
                return id;
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

            public String getQuotContact()
            {
                if (TextUtils.isEmpty(quotContact))
                {
                    return "";
                }
                return quotContact;
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

            public String getManaerId()
            {
                if (TextUtils.isEmpty(manaerId))
                {
                    return "";
                }
                return manaerId;
            }

            public String getMaterial()
            {
                if (TextUtils.isEmpty(material))
                {
                    return "";
                }
                return material;
            }

            public String getNote() {
                if (TextUtils.isEmpty(note))
                {
                    return "";
                }
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getBuyMemberName()
            {
                if (TextUtils.isEmpty(buyMemberName))
                {
                    return "";
                }
                return buyMemberName;
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
}

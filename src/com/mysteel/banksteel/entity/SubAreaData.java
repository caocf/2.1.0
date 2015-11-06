package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 根据市获取子区的实体类
 * Created by zoujian on 15/8/12.
 */
public class SubAreaData extends BaseData
{
    private ArrayList<DataEntity> data;

    public void setData(ArrayList<DataEntity> data)
    {
        this.data = data;
    }

    public ArrayList<DataEntity> getData()
    {
        return data;
    }

    public class DataEntity
    {
        /**
         * zipcode : 200001
         * code : 310101
         * sname : 黄浦
         * type : 4
         * version : 0
         * id : null
         * dddcode : 021
         * name : 黄浦区
         * lastAccess : 0
         * serialVersionUID : 1
         * parentCode : 310100
         * ensname :
         * enname : huangpu
         */
        private String zipcode;
        private String code;
        private String sname;
        private String type;
        private String version;
        private String id;
        private String dddcode;
        private String name;
        private String lastAccess;
        private String serialVersionUID;
        private String parentCode;
        private String ensname;
        private String enname;

        public void setZipcode(String zipcode)
        {
            this.zipcode = zipcode;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public void setSname(String sname)
        {
            this.sname = sname;
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

        public void setDddcode(String dddcode)
        {
            this.dddcode = dddcode;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setLastAccess(String lastAccess)
        {
            this.lastAccess = lastAccess;
        }

        public void setSerialVersionUID(String serialVersionUID)
        {
            this.serialVersionUID = serialVersionUID;
        }

        public void setParentCode(String parentCode)
        {
            this.parentCode = parentCode;
        }

        public void setEnsname(String ensname)
        {
            this.ensname = ensname;
        }

        public void setEnname(String enname)
        {
            this.enname = enname;
        }

        public String getZipcode()
        {
            if (TextUtils.isEmpty(zipcode))
            {
                return "";
            }
            return zipcode;
        }

        public String getCode()
        {
            if (TextUtils.isEmpty(code))
            {
                return "";
            }
            return code;
        }

        public String getSname()
        {
            if (TextUtils.isEmpty(sname))
            {
                return "";
            }
            return sname;
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

        public String getDddcode()
        {
            if (TextUtils.isEmpty(dddcode))
            {
                return "";
            }
            return dddcode;
        }

        public String getName()
        {
            if (TextUtils.isEmpty(name))
            {
                return "";
            }
            return name;
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

        public String getParentCode()
        {
            if (TextUtils.isEmpty(parentCode))
            {
                return "";
            }
            return parentCode;
        }

        public String getEnsname()
        {
            if (TextUtils.isEmpty(ensname))
            {
                return "";
            }
            return ensname;
        }

        public String getEnname()
        {
            if (TextUtils.isEmpty(enname))
            {
                return "";
            }
            return enname;
        }
    }
}

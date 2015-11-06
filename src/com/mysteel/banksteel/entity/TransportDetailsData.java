package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 物流跟踪页面
 * Created by zoujian on 15/8/24.
 */
public class TransportDetailsData extends BaseData
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
         * img2 : null
         * img1 : http://www.baidu.com
         * carId : 湘F:A7319
         * status : 10  10 到达装货点 20 提货完成 30 运输在途40 到达卸货点
         * steel56OrderId : 2015081910221
         * statusTime : 20150808114530123
         * version : 0
         * id : 10940
         * lastAccess : 0
         * serialVersionUID : 1
         * img3 : null
         * note : 备注
         * orderId : 52
         */
        private String img2;
        private String img1;
        private String carId;
        private String status;
        private String steel56OrderId;
        private String statusTime;
        private String version;
        private String id;
        private String lastAccess;
        private String serialVersionUID;
        private String img3;
        private String note;
        private String orderId;

        public void setImg2(String img2)
        {
            this.img2 = img2;
        }

        public void setImg1(String img1)
        {
            this.img1 = img1;
        }

        public void setCarId(String carId)
        {
            this.carId = carId;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public void setSteel56OrderId(String steel56OrderId)
        {
            this.steel56OrderId = steel56OrderId;
        }

        public void setStatusTime(String statusTime)
        {
            this.statusTime = statusTime;
        }

        public void setVersion(String version)
        {
            this.version = version;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public void setLastAccess(String lastAccess)
        {
            this.lastAccess = lastAccess;
        }

        public void setSerialVersionUID(String serialVersionUID)
        {
            this.serialVersionUID = serialVersionUID;
        }

        public void setImg3(String img3)
        {
            this.img3 = img3;
        }

        public void setNote(String note)
        {
            this.note = note;
        }

        public void setOrderId(String orderId)
        {
            this.orderId = orderId;
        }

        public String getImg2()
        {
            if (TextUtils.isEmpty(img2))
            {
                return "";
            }
            return img2;
        }

        public String getImg1()
        {
            if (TextUtils.isEmpty(img1))
            {
                return "";
            }
            return img1;
        }

        public String getCarId()
        {
            if (TextUtils.isEmpty(carId))
            {
                return "";
            }
            return carId;
        }

        public String getStatus()
        {
            if (TextUtils.isEmpty(status))
            {
                return "";
            }
            return status;
        }

        public String getSteel56OrderId()
        {
            if (TextUtils.isEmpty(steel56OrderId))
            {
                return "";
            }
            return steel56OrderId;
        }

        public String getStatusTime()
        {
            if (TextUtils.isEmpty(statusTime))
            {
                return "";
            }
            return statusTime;
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

        public String getImg3()
        {
            if (TextUtils.isEmpty(img3))
            {
                return "";
            }
            return img3;
        }

        public String getNote()
        {
            if (TextUtils.isEmpty(note))
            {
                return "";
            }
            return note;
        }

        public String getOrderId()
        {
            if (TextUtils.isEmpty(orderId))
            {
                return "";
            }
            return orderId;
        }
    }
}

package com.mysteel.banksteel.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流货物类型实体类
 * Created by zoujian on 15/8/12.
 */
public class FoodTypeData extends BaseData
{
    private ArrayList<String> breeds;


    public void setBreeds(ArrayList<String> breeds)
    {
        this.breeds = breeds;
    }


    public ArrayList<String> getBreeds()
    {
        return breeds;
    }

//    private ArrayList<ProductTypeEntity> productType;
//
//    public void setProductType(ArrayList<ProductTypeEntity> productType)
//    {
//        this.productType = productType;
//    }
//
//    public ArrayList<ProductTypeEntity> getProductType()
//    {
//        return productType;
//    }
//
//    public class ProductTypeEntity
//    {
//        private String productTypeId;
//        private String productTypeName;
//
//        public void setProductTypeId(String productTypeId)
//        {
//            this.productTypeId = productTypeId;
//        }
//
//        public void setProductTypeName(String productTypeName)
//        {
//            this.productTypeName = productTypeName;
//        }
//
//        public String getProductTypeId()
//        {
//            if (TextUtils.isEmpty(productTypeId))
//            {
//                return "";
//            }
//            return productTypeId;
//        }
//
//        public String getProductTypeName()
//        {
//            if (TextUtils.isEmpty(productTypeName))
//            {
//                return "";
//            }
//            return productTypeName;
//        }
//    }



}

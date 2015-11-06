package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.util.List;

/**
 * 业务范围
 *
 * @author:wushaoge
 * @date：2015年9月8日09:13:50
 */
public class BusinessScopesData extends BaseData {

    private BusinessScopes businessScopes;

    public BusinessScopes getBusinessScopes() {
        return businessScopes;
    }

    public void setBusinessScopes(BusinessScopes businessScopes) {
        this.businessScopes = businessScopes;
    }

    public class BusinessScopes{
        private List<Data> data;
        private String error;
        private String success;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
        public String getError() {
            if (TextUtils.isEmpty(error))
            {
                return "";
            }
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getSuccess() {
            if (TextUtils.isEmpty(success))
            {
                return "";
            }
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
        public class Data{
            private List<ChildData> businessScopes;
            private String memberType;
            private String memberTypeCode;

            public List<ChildData> getBusinessScopes() {
                return businessScopes;
            }

            public void setBusinessScopes(List<ChildData> businessScopes) {
                this.businessScopes = businessScopes;
            }

            public String getMemberType() {
                if (TextUtils.isEmpty(memberType))
                {
                    return "";
                }
                return memberType;
            }

            public void setMemberType(String memberType) {
                this.memberType = memberType;
            }

            public String getMemberTypeCode() {
                if (TextUtils.isEmpty(memberTypeCode))
                {
                    return "";
                }
                return memberTypeCode;
            }

            public void setMemberTypeCode(String memberTypeCode) {
                this.memberTypeCode = memberTypeCode;
            }

            public class ChildData{
                private String code;
                private String name;

                public String getCode() {
                    if (TextUtils.isEmpty(code))
                    {
                        return "";
                    }
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    if (TextUtils.isEmpty(name))
                    {
                        return "";
                    }
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }


        }



    }
}

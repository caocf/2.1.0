package com.mysteel.banksteel.entity;

import java.util.List;

/**
 * 用户个人中心
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:20:42
 */
public class ShowMoreUserInfo extends BaseData {

    /**
     * data : [{"address":"","area":"宝山区","city":"上海市","country":"","email":"","lastAccess":"1440655447330","managerId":"195763","memberId":"210199","mobile":"18616877614","name":"孔凡峰","phone":"","photo":"","registerTime":"1433146206590","sex":"男","state":"上海市","userId":"46912","username":""}]
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * address :
         * area : 宝山区
         * city : 上海市
         * country :
         * email :
         * lastAccess : 1440655447330
         * managerId : 195763
         * memberId : 210199
         * mobile : 18616877614
         * name : 孔凡峰
         * phone :
         * photo :
         * registerTime : 1433146206590
         * sex : 男
         * state : 上海市
         * userId : 46912
         * username :
         */

        private String address;
        private String area;
        private String city;
        private String country;
        private String email;
        private String lastAccess;
        private String managerId;
        private String memberId;
        private String mobile;
        private String name;
        private String phone;
        private String photo;
        private String registerTime;
        private String sex;
        private String state;
        private String userId;
        private String username;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setLastAccess(String lastAccess) {
            this.lastAccess = lastAccess;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public String getArea() {
            return area;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getEmail() {
            return email;
        }

        public String getLastAccess() {
            return lastAccess;
        }

        public String getManagerId() {
            return managerId;
        }

        public String getMemberId() {
            return memberId;
        }

        public String getMobile() {
            return mobile;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getPhoto() {
            return photo;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public String getSex() {
            return sex;
        }

        public String getState() {
            return state;
        }

        public String getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }
    }
}

package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 资源搜索
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:20:42
 */
public class SearchResourceData extends BaseData {
	
	
	private static final long serialVersionUID = 5793689092949523738L;
	
	private Data data;

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}
	
	public class Data
	{
		private String count;
		private ArrayList<Datas> datas;
		private String page;
		private String pagenum;
		private String size;

		public String getCount()
		{
			if(TextUtils.isEmpty(count)){
				return "";
			}
			return count;
		}

		public void setCount(String count)
		{
			this.count = count;
		}

		public ArrayList<Datas> getDatas()
		{	
			return datas;
		}

		public void setDatas(ArrayList<Datas> datas)
		{
			this.datas = datas;
		}

		public String getPage()
		{
			if(TextUtils.isEmpty(page)){
				return "";
			}
			return page;
		}

		public void setPage(String page)
		{
			this.page = page;
		}

		public String getPagenum()
		{
			if(TextUtils.isEmpty(pagenum)){
				return "";
			}
			return pagenum;
		}

		public void setPagenum(String pagenum)
		{
			this.pagenum = pagenum;
		}

		public String getSize()
		{
			if(TextUtils.isEmpty(size)){
				return "";
			}
			return size;
		}

		public void setSize(String size)
		{
			this.size = size;
		}

		public class Datas implements Serializable
		{
			private User user;
			private String attestation; //1认证 0未认证
			private String brank; 
			private String brand; 
			private String breedId; //品名ID
			private String breedName; //品名
			private String catalog;
			private String cityId;
			private String cityName;
			private String id;
			private String indexTime;
			private String material; //材质
			private String memberId; //会员ID
			private String note;
			private String phone; //联系电话
			private String price; //价格
			private String qty; //数量
			private String rise; 
			private String source; 
			private String spec; //规格
			private String uploadTime;
			private String userId;
			private String userName;
			private String warehouse; //仓库
			private String weight;
			private String minPrice;
			private String minPriceId;
			private boolean toogleItemFlag; //false关闭  true打开

			private String userCountry; //中国
			private String userProvince; //山东
			private String userCity; //德州
			private String userDistrict; //齐河县
			private String address; //该市物流园
			private String memberName; //契合明润金属物资有限公司
			private String userPhoto; //头像
			private String userPhone; //电话

			public String getUserPhone() {
				if (TextUtils.isEmpty(userPhone)) {
					return "";
				}
				return userPhone;
			}

			public void setUserPhone(String userPhone) {
				this.userPhone = userPhone;
			}

			public String getUserPhoto() {
				if (TextUtils.isEmpty(userPhoto)) {
					return "";
				}
				return userPhoto;
			}

			public void setUserPhoto(String userPhoto) {
				this.userPhoto = userPhoto;
			}
			public String getAddress() {
				if (TextUtils.isEmpty(address)) {
					return "";
				}
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getUserCity() {
				if (TextUtils.isEmpty(userCity)) {
					return "";
				}
				return userCity;
			}

			public void setUserCity(String userCity) {
				this.userCity = userCity;
			}

			public String getUserCountry() {
				if (TextUtils.isEmpty(userCountry)) {
					return "";
				}
				return userCountry;
			}

			public void setUserCountry(String userCountry) {
				this.userCountry = userCountry;
			}

			public String getUserDistrict() {
				if (TextUtils.isEmpty(userDistrict)) {
					return "";
				}
				return userDistrict;
			}

			public void setUserDistrict(String userDistrict) {
				this.userDistrict = userDistrict;
			}

			public String getUserProvince() {
				if (TextUtils.isEmpty(userProvince)) {
					return "";
				}
				return userProvince;
			}

			public void setUserProvince(String userProvince) {
				this.userProvince = userProvince;
			}


			public boolean isToogleItemFlag() {
				return toogleItemFlag;
			}

			public void setToogleItemFlag(boolean toogleItemFlag) {
				this.toogleItemFlag = toogleItemFlag;
			}

			public String getMinPrice() {
				if (TextUtils.isEmpty(minPrice)) {
					return "";
				}
				return minPrice;
			}

			public void setMinPrice(String minPrice) {
				this.minPrice = minPrice;
			}

			public String getMinPriceId() {
				if (TextUtils.isEmpty(minPriceId)) {
					return "";
				}
				return minPriceId;
			}

			public void setMinPriceId(String minPriceId) {
				this.minPriceId = minPriceId;
			}

			public String getBrank() {
				if (TextUtils.isEmpty(brank)) {
					return "";
				}
				return brank;
			}
			public void setBrank(String brank) {
				this.brank = brank;
			}
			public String getBreedId() {
				if (TextUtils.isEmpty(breedId)) {
					return "";
				}
				return breedId;
			}
			public void setBreedId(String breedId) {
				this.breedId = breedId;
			}
			public String getBreedName() {
				if (TextUtils.isEmpty(breedName)) {
					return "";
				}
				return breedName;
			}
			public void setBreedName(String breedName) {
				this.breedName = breedName;
			}
			public String getCatalog() {
				if (TextUtils.isEmpty(catalog)) {
					return "";
				}
				return catalog;
			}
			public void setCatalog(String catalog) {
				this.catalog = catalog;
			}
			public String getCityId() {
				if (TextUtils.isEmpty(cityId)) {
					return "";
				}
				return cityId;
			}
			public void setCityId(String cityId) {
				this.cityId = cityId;
			}
			public String getCityName() {
				if (TextUtils.isEmpty(cityName)) {
					return "";
				}
				return cityName;
			}
			public void setCityName(String cityName) {
				this.cityName = cityName;
			}
			public String getId() {
				if (TextUtils.isEmpty(id)) {
					return "";
				}
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getIndexTime() {
				if (TextUtils.isEmpty(indexTime)) {
					return "";
				}
				return indexTime;
			}
			public void setIndexTime(String indexTime) {
				this.indexTime = indexTime;
			}
			public String getMaterial() {
				if (TextUtils.isEmpty(material)) {
					return "";
				}
				return material;
			}
			public void setMaterial(String material) {
				this.material = material;
			}
			public String getMemberId() {
				if (TextUtils.isEmpty(memberId)) {
					return "";
				}
				return memberId;
			}
			public void setMemberId(String memberId) {
				this.memberId = memberId;
			}
			public String getMemberName() {
				if (TextUtils.isEmpty(memberName)) {
					return "";
				}
				return memberName;
			}
			public void setMemberName(String memberName) {
				this.memberName = memberName;
			}
			public String getNote() {
				if (TextUtils.isEmpty(note)) {
					return "";
				}
				return note;
			}
			public void setNote(String note) {
				this.note = note;
			}
			public String getPhone() {
				if (TextUtils.isEmpty(phone)) {
					return "";
				}
				return phone;
			}
			public void setPhone(String phone) {
				this.phone = phone;
			}
			public String getPrice() {
				if (TextUtils.isEmpty(price)) {
					return "";
				}
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			public String getQty() {
				if (TextUtils.isEmpty(qty)) {
					return "";
				}
				return qty;
			}
			public void setQty(String qty) {
				this.qty = qty;
			}
			public String getRise() {
				if (TextUtils.isEmpty(rise)) {
					return "";
				}
				return rise;
			}
			public void setRise(String rise) {
				this.rise = rise;
			}
			public String getSource() {
				if (TextUtils.isEmpty(source)) {
					return "";
				}
				return source;
			}
			public void setSource(String source) {
				this.source = source;
			}
			public String getSpec() {
				if (TextUtils.isEmpty(spec)) {
					return "";
				}
				return spec;
			}
			public void setSpec(String spec) {
				this.spec = spec;
			}
			public String getUploadTime() {
				if (TextUtils.isEmpty(uploadTime)) {
					return "";
				}
				return uploadTime;
			}
			public void setUploadTime(String uploadTime) {
				this.uploadTime = uploadTime;
			}
			public String getUserId() {
				if (TextUtils.isEmpty(userId)) {
					return "";
				}
				return userId;
			}
			public void setUserId(String userId) {
				this.userId = userId;
			}
			public String getUserName() {
				if (TextUtils.isEmpty(userName)) {
					return "";
				}
				return userName;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getWarehouse() {
				if (TextUtils.isEmpty(warehouse)) {
					return "";
				}
				return warehouse;
			}
			public void setWarehouse(String warehouse) {
				this.warehouse = warehouse;
			}
			public String getWeight() {
				if (TextUtils.isEmpty(weight)) {
					return "";
				}
				return weight;
			}
			public void setWeight(String weight) {
				this.weight = weight;
			} 
			public User getUser() {
				return user;
			}
			public void setUser(User user) {
				this.user = user;
			}
			public String getBrand() {
				if (TextUtils.isEmpty(brand)) {
					return "";
				}
				return brand;
			}
			public void setBrand(String brand) {
				this.brand = brand;
			}
			
			public String getAttestation() {
				if (TextUtils.isEmpty(attestation)) {
					return "";
				}
				return attestation;
			}
			public void setAttestation(String attestation) {
				this.attestation = attestation;
			}
			
			public class User implements Serializable
			{
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
				public String getAddress() {
					if (TextUtils.isEmpty(address)) {
						return "";
					}
					return address;
				}
				public void setAddress(String address) {
					this.address = address;
				}
				public String getArea() {
					if (TextUtils.isEmpty(area)) {
						return "";
					}
					return area;
				}
				public void setArea(String area) {
					this.area = area;
				}
				public String getCity() {
					if (TextUtils.isEmpty(city)) {
						return "";
					}
					return city;
				}
				public void setCity(String city) {
					this.city = city;
				}
				public String getCountry() {
					if (TextUtils.isEmpty(country)) {
						return "";
					}
					return country;
				}
				public void setCountry(String country) {
					this.country = country;
				}
				public String getEmail() {
					if (TextUtils.isEmpty(email)) {
						return "";
					}
					return email;
				}
				public void setEmail(String email) {
					this.email = email;
				}
				public String getLastAccess() {
					if (TextUtils.isEmpty(lastAccess)) {
						return "";
					}
					return lastAccess;
				}
				public void setLastAccess(String lastAccess) {
					this.lastAccess = lastAccess;
				}
				public String getManagerId() {
					if (TextUtils.isEmpty(managerId)) {
						return "";
					}
					return managerId;
				}
				public void setManagerId(String managerId) {
					this.managerId = managerId;
				}
				public String getMemberId() {
					if (TextUtils.isEmpty(memberId)) {
						return "";
					}
					return memberId;
				}
				public void setMemberId(String memberId) {
					this.memberId = memberId;
				}
				public String getMobile() {
					if (TextUtils.isEmpty(mobile)) {
						return "";
					}
					return mobile;
				}
				public void setMobile(String mobile) {
					this.mobile = mobile;
				}
				public String getName() {
					if (TextUtils.isEmpty(name)) {
						return "";
					}
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getPhone() {
					if (TextUtils.isEmpty(phone)) {
						return "";
					}
					return phone;
				}
				public void setPhone(String phone) {
					this.phone = phone;
				}
				public String getPhoto() {
					if (TextUtils.isEmpty(photo)) {
						return "";
					}
					return photo;
				}
				public void setPhoto(String photo) {
					this.photo = photo;
				}
				public String getRegisterTime() {
					if (TextUtils.isEmpty(registerTime)) {
						return "";
					}
					return registerTime;
				}
				public void setRegisterTime(String registerTime) {
					this.registerTime = registerTime;
				}
				public String getSex() {
					if (TextUtils.isEmpty(sex)) {
						return "";
					}
					return sex;
				}
				public void setSex(String sex) {
					this.sex = sex;
				}
				public String getState() {
					if (TextUtils.isEmpty(state)) {
						return "";
					}
					return state;
				}
				public void setState(String state) {
					this.state = state;
				}
				public String getUserId() {
					if (TextUtils.isEmpty(userId)) {
						return "";
					}
					return userId;
				}
				public void setUserId(String userId) {
					this.userId = userId;
				}
				public String getUsername() {
					if (TextUtils.isEmpty(username)) {
						return "";
					}
					return username;
				}
				public void setUsername(String username) {
					this.username = username;
				}
				
			}

		}
	}

	
	
	
}

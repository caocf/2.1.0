package com.mysteel.banksteel.entity;


import android.text.TextUtils;

import java.util.List;

/**
 * 用户个人中心
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月3日11:20:42
 */
public class ShowUserInfo extends BaseData{
		
	private Data data;
	
	
	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	public class Data{
		private FriendResume friendResume;
		private List<Appraise> appraise;
		private String count;
		private String page;
		private String pagenum;
		private String size;
		private FriendMemberBusiness friendMemberBusiness;

		public FriendMemberBusiness getFriendMemberBusiness() {
			return friendMemberBusiness;
		}

		public void setFriendMemberBusiness(FriendMemberBusiness friendMemberBusiness) {
			this.friendMemberBusiness = friendMemberBusiness;
		}

		public List<Appraise> getAppraise() {
			return appraise;
		}

		public void setAppraise(List<Appraise> appraise) {
			this.appraise = appraise;
		}
		public String getCount() {
			if (TextUtils.isEmpty(count)) {
				return "";
			}
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getPage() {
			if (TextUtils.isEmpty(page)) {
				return "";
			}
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}

		public String getPagenum() {
			if (TextUtils.isEmpty(pagenum)) {
				return "";
			}
			return pagenum;
		}

		public void setPagenum(String pagenum) {
			this.pagenum = pagenum;
		}

		public String getSize() {
			if (TextUtils.isEmpty(size)) {
				return "";
			}
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public class Appraise{
			private String appraiseType;
			private String createTime;
			private String feedbackAppraise;
			private String id;
			private String lastAccess;
			private String memberId;
			private String note;
			private String orderId;
			private String sellMemberId;
			private String sellUserId;
			private String serialVersionUID;
			private String serviceAppraise;
			private String sourceAppraise;
			private String status;
			private String steel56OrderId;
			private String totalAppraise;
			private String userId;
			private String version;
			public String getAppraiseType() {
				if (TextUtils.isEmpty(appraiseType)) {
					return "";
				}
				return appraiseType;
			}
			public void setAppraiseType(String appraiseType) {
				this.appraiseType = appraiseType;
			}
			public String getCreateTime() {
				if (TextUtils.isEmpty(createTime)) {
					return "";
				}
				return createTime;
			}
			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}
			public String getFeedbackAppraise() {
				if (TextUtils.isEmpty(feedbackAppraise)) {
					return "";
				}
				return feedbackAppraise;
			}
			public void setFeedbackAppraise(String feedbackAppraise) {
				this.feedbackAppraise = feedbackAppraise;
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
			public String getLastAccess() {
				if (TextUtils.isEmpty(lastAccess)) {
					return "";
				}
				return lastAccess;
			}
			public void setLastAccess(String lastAccess) {
				this.lastAccess = lastAccess;
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
			public String getNote() {
				if (TextUtils.isEmpty(note)) {
					return "";
				}
				return note;
			}
			public void setNote(String note) {
				this.note = note;
			}
			public String getOrderId() {
				if (TextUtils.isEmpty(orderId)) {
					return "";
				}
				return orderId;
			}
			public void setOrderId(String orderId) {
				this.orderId = orderId;
			}
			public String getSellMemberId() {
				if (TextUtils.isEmpty(sellMemberId)) {
					return "";
				}
				return sellMemberId;
			}
			public void setSellMemberId(String sellMemberId) {
				this.sellMemberId = sellMemberId;
			}
			public String getSellUserId() {
				if (TextUtils.isEmpty(sellUserId)) {
					return "";
				}
				return sellUserId;
			}
			public void setSellUserId(String sellUserId) {
				this.sellUserId = sellUserId;
			}
			public String getSerialVersionUID() {
				if (TextUtils.isEmpty(serialVersionUID)) {
					return "";
				}
				return serialVersionUID;
			}
			public void setSerialVersionUID(String serialVersionUID) {
				this.serialVersionUID = serialVersionUID;
			}
			public String getServiceAppraise() {
				if (TextUtils.isEmpty(serviceAppraise)) {
					return "";
				}
				return serviceAppraise;
			}
			public void setServiceAppraise(String serviceAppraise) {
				this.serviceAppraise = serviceAppraise;
			}
			public String getSourceAppraise() {
				if (TextUtils.isEmpty(sourceAppraise)) {
					return "";
				}
				return sourceAppraise;
			}
			public void setSourceAppraise(String sourceAppraise) {
				this.sourceAppraise = sourceAppraise;
			}
			public String getStatus() {
				if (TextUtils.isEmpty(status)) {
					return "";
				}
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public String getSteel56OrderId() {
				if (TextUtils.isEmpty(steel56OrderId)) {
					return "";
				}
				return steel56OrderId;
			}
			public void setSteel56OrderId(String steel56OrderId) {
				this.steel56OrderId = steel56OrderId;
			}
			public String getTotalAppraise() {
				if (TextUtils.isEmpty(totalAppraise)) {
					return "";
				}
				return totalAppraise;
			}
			public void setTotalAppraise(String totalAppraise) {
				this.totalAppraise = totalAppraise;
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
			public String getVersion() {
				if (TextUtils.isEmpty(version)) {
					return "";
				}
				return version;
			}
			public void setVersion(String version) {
				this.version = version;
			}

		}

		public FriendResume getFriendResume() {
			return friendResume;
		}

		public void setFriendResume(FriendResume friendResume) {
			this.friendResume = friendResume;
		}
		
		public class FriendResume{
			private String address;
			private String area;
			private String cacheId;
			private String city;
			private String country;
			private String department;
			private String email;
			private String emailabled;
			private String fax;
			private String grouped;
			private String iM;
			private String lastAccess;
			private String lastLoginIP;
			private String lastLoginTime;
			private String lastServiceTime;
			private String managerId;
			private String mastered;
			private String memberId;
			private String mobile;
			private String mobileabled;
			private String name;
			private String password;
			private String phone;
			private String postcode;
			private String qQ;
			private String qq;
			private String registerIP;
			private String registerMode;
			private String registerSource;
			private String registerTime;
			//private String roles;
			private String sex;
			private String skype;
			private String state;
			private String status;
			private String title;
			private String username;

			public String getPhoto() {
				if (TextUtils.isEmpty(photo)) {
					return "";
				}
				return photo;
			}

			public void setPhoto(String photo) {
				this.photo = photo;
			}

			public String getSex() {
				if (TextUtils.isEmpty(sex)) {
					return "";
				}
				return sex;
			}

			private String photo;

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
			public String getCacheId() {
				if (TextUtils.isEmpty(cacheId)) {
					return "";
				}
				return cacheId;
			}
			public void setCacheId(String cacheId) {
				this.cacheId = cacheId;
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
			public String getDepartment() {
				if (TextUtils.isEmpty(department)) {
					return "";
				}
				return department;
			}
			public void setDepartment(String department) {
				this.department = department;
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
			public String getEmailabled() {
				if (TextUtils.isEmpty(emailabled)) {
					return "";
				}
				return emailabled;
			}
			public void setEmailabled(String emailabled) {
				this.emailabled = emailabled;
			}
			public String getFax() {
				if (TextUtils.isEmpty(fax)) {
					return "";
				}
				return fax;
			}
			public void setFax(String fax) {
				this.fax = fax;
			}
			public String getGrouped() {
				if (TextUtils.isEmpty(grouped)) {
					return "";
				}
				return grouped;
			}
			public void setGrouped(String grouped) {
				this.grouped = grouped;
			}
			public String getiM() {
				if (TextUtils.isEmpty(iM)) {
					return "";
				}
				return iM;
			}
			public void setiM(String iM) {
				this.iM = iM;
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
			public String getLastLoginIP() {
				if (TextUtils.isEmpty(lastLoginIP)) {
					return "";
				}
				return lastLoginIP;
			}
			public void setLastLoginIP(String lastLoginIP) {
				this.lastLoginIP = lastLoginIP;
			}
			public String getLastLoginTime() {
				if (TextUtils.isEmpty(lastLoginTime)) {
					return "";
				}
				return lastLoginTime;
			}
			public void setLastLoginTime(String lastLoginTime) {
				this.lastLoginTime = lastLoginTime;
			}
			public String getLastServiceTime() {
				if (TextUtils.isEmpty(lastServiceTime)) {
					return "";
				}
				return lastServiceTime;
			}
			public void setLastServiceTime(String lastServiceTime) {
				this.lastServiceTime = lastServiceTime;
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
			public String getMastered() {
				if (TextUtils.isEmpty(mastered)) {
					return "";
				}
				return mastered;
			}
			public void setMastered(String mastered) {
				this.mastered = mastered;
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
			public String getMobileabled() {
				if (TextUtils.isEmpty(mobileabled)) {
					return "";
				}
				return mobileabled;
			}
			public void setMobileabled(String mobileabled) {
				this.mobileabled = mobileabled;
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
			public String getPassword() {
				if (TextUtils.isEmpty(password)) {
					return "";
				}
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
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
			public String getPostcode() {
				if (TextUtils.isEmpty(postcode)) {
					return "";
				}
				return postcode;
			}
			public void setPostcode(String postcode) {
				this.postcode = postcode;
			}
			public String getqQ() {
				if (TextUtils.isEmpty(qQ)) {
					return "";
				}
				return qQ;
			}
			public void setqQ(String qQ) {
				this.qQ = qQ;
			}
			public String getQq() {
				if (TextUtils.isEmpty(qq)) {
					return "";
				}
				return qq;
			}
			public void setQq(String qq) {
				this.qq = qq;
			}
			public String getRegisterIP() {
				if (TextUtils.isEmpty(registerIP)) {
					return "";
				}
				return registerIP;
			}
			public void setRegisterIP(String registerIP) {
				this.registerIP = registerIP;
			}
			public String getRegisterMode() {
				if (TextUtils.isEmpty(registerMode)) {
					return "";
				}
				return registerMode;
			}
			public void setRegisterMode(String registerMode) {
				this.registerMode = registerMode;
			}
			public String getRegisterSource() {
				if (TextUtils.isEmpty(registerSource)) {
					return "";
				}
				return registerSource;
			}
			public void setRegisterSource(String registerSource) {
				this.registerSource = registerSource;
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
			/*public String getRoles() {
				if (TextUtils.isEmpty(roles)) {
					return "";
				}
				return roles;
			}
			public void setRoles(String roles) {
				this.roles = roles;
			}
			public String getSex() {
				if (TextUtils.isEmpty(sex)) {
					return "";
				}
				return sex;
			}*/
			public void setSex(String sex) {
				this.sex = sex;
			}
			public String getSkype() {
				if (TextUtils.isEmpty(skype)) {
					return "";
				}
				return skype;
			}
			public void setSkype(String skype) {
				this.skype = skype;
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
			public String getStatus() {
				if (TextUtils.isEmpty(status)) {
					return "";
				}
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public String getTitle() {
				if (TextUtils.isEmpty(title)) {
					return "";
				}
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
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

		public class FriendMemberBusiness{
			private FriendMemberBusinessData data;
			private String error;
			private String success;

			public FriendMemberBusinessData getData() {
				return data;
			}

			public void setData(FriendMemberBusinessData data) {
				this.data = data;
			}

			public String getSuccess() {
				if (TextUtils.isEmpty(success)) {
					return "";
				}
				return success;
			}

			public void setSuccess(String success) {
				this.success = success;
			}

			public String getError() {
				if (TextUtils.isEmpty(error)) {
					return "";
				}
				return error;
			}

			public void setError(String error) {
				this.error = error;
			}

			public class FriendMemberBusinessData{
				private List<BusinessAreas> businessAreas;
				private List<BusinessScopes> businessScopes;
				private String memberId;
				private String memberType;
				private String memberTypeCode;

				public List<BusinessAreas> getBusinessAreas() {
					return businessAreas;
				}

				public void setBusinessAreas(List<BusinessAreas> businessAreas) {
					this.businessAreas = businessAreas;
				}

				public List<BusinessScopes> getBusinessScopes() {
					return businessScopes;
				}

				public void setBusinessScopes(List<BusinessScopes> businessScopes) {
					this.businessScopes = businessScopes;
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

				public String getMemberType() {
					if (TextUtils.isEmpty(memberType)) {
						return "";
					}
					return memberType;
				}

				public void setMemberType(String memberType) {
					this.memberType = memberType;
				}

				public String getMemberTypeCode() {
					if (TextUtils.isEmpty(memberTypeCode)) {
						return "";
					}
					return memberTypeCode;
				}

				public void setMemberTypeCode(String memberTypeCode) {
					this.memberTypeCode = memberTypeCode;
				}

				public class BusinessAreas{
					private String city;
					private String cityCode;
					private String district;
					private String districtCode;
					private String province;
					private String provinceCode;

					public String getCity() {
						if (TextUtils.isEmpty(city))
						{
							return "";
						}
						return city;
					}

					public void setCity(String city) {
						this.city = city;
					}

					public String getCityCode() {
						if (TextUtils.isEmpty(cityCode))
						{
							return "";
						}
						return cityCode;
					}

					public void setCityCode(String cityCode) {
						this.cityCode = cityCode;
					}

					public String getDistrict() {
						if (TextUtils.isEmpty(district))
						{
							return "";
						}
						return district;
					}

					public void setDistrict(String district) {
						this.district = district;
					}

					public String getDistrictCode() {
						if (TextUtils.isEmpty(districtCode))
						{
							return "";
						}
						return districtCode;
					}

					public void setDistrictCode(String districtCode) {
						this.districtCode = districtCode;
					}

					public String getProvince() {
						if (TextUtils.isEmpty(province))
						{
							return "";
						}
						return province;
					}

					public void setProvince(String province) {
						this.province = province;
					}

					public String getProvinceCode() {
						if (TextUtils.isEmpty(provinceCode))
						{
							return "";
						}
						return provinceCode;
					}

					public void setProvinceCode(String provinceCode) {
						this.provinceCode = provinceCode;
					}
				}
				public class BusinessScopes{
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
	
}

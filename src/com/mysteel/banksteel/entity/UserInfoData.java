package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-13 下午3:44:54
 */
public class UserInfoData extends BaseData
{

	private static final long serialVersionUID = 1L;
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
		private String level;// 等级 1,2,3,4,5(暂无)
		private String levelName;// 等级名称(暂无)
		private String loginToken;
		private String msg;
		private String score;
		private Member member;
		private User user;
		private ArrayList<StanBuys> stanBuys;// 首页我的求购下面3条数据
		private MemberBusiness memberBusiness; //业务区域范围

		public MemberBusiness getMemberBusiness() {
			return memberBusiness;
		}

		public void setMemberBusiness(MemberBusiness memberBusiness) {
			this.memberBusiness = memberBusiness;
		}
		
		public ArrayList<StanBuys> getStanBuys()
		{
			if( stanBuys == null){
				return stanBuys = new ArrayList<StanBuys>();
			}
			return stanBuys;
		}

		public void setStanBuys(ArrayList<StanBuys> stanBuys)
		{
			this.stanBuys = stanBuys;
		}

		public String getLevel()
		{
			if (TextUtils.isEmpty(level))
			{
				return "";
			}
			return level;
		}

		
		public String getLoginToken()
		{
			if (TextUtils.isEmpty(loginToken))
			{
				return "";
			}
			return loginToken;
		}

		public void setLoginToken(String loginToken)
		{
			this.loginToken = loginToken;
		}

		public void setLevel(String level)
		{
			this.level = level;
		}

		public String getLevelName()
		{
			if (TextUtils.isEmpty(levelName))
			{
				return "";
			}
			return levelName;
		}

		public void setLevelName(String levelName)
		{
			this.levelName = levelName;
		}

		public String getMsg()
		{
			if (TextUtils.isEmpty(msg))
			{
				return "";
			}
			return msg;
		}

		public void setMsg(String msg)
		{
			this.msg = msg;
		}

		public String getScore()
		{
			if (TextUtils.isEmpty(score))
			{
				return "";
			}
			return score;
		}

		public void setScore(String score)
		{
			this.score = score;
		}

		public Member getMember()
		{
			return member;
		}

		public void setMember(Member member)
		{
			this.member = member;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public class Member
		{
			private String area;// 地区
			private String city;// 城市
			private String country;// 国家
			private String lastAccess;//
			private String managerId;//
			private String memberId;// 会员代码
			private String name;
			private String registerTime;
			private String shortName;
			private String state;
			private String type;

			public String getArea()
			{
				if (TextUtils.isEmpty(area))
				{
					return "";
				}
				return area;
			}

			public void setArea(String area)
			{
				this.area = area;
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

			public String getCountry()
			{
				if (TextUtils.isEmpty(country))
				{
					return "";
				}
				return country;
			}

			public void setCountry(String country)
			{
				this.country = country;
			}

			public String getLastAccess()
			{
				if (TextUtils.isEmpty(lastAccess))
				{
					return "";
				}
				return lastAccess;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public String getManagerId()
			{
				if (TextUtils.isEmpty(managerId))
				{
					return "";
				}
				return managerId;
			}

			public void setManagerId(String managerId)
			{
				this.managerId = managerId;
			}

			public String getMemberId()
			{
				if (TextUtils.isEmpty(memberId))
				{
					return "";
				}
				return memberId;
			}

			public void setMemberId(String memberId)
			{
				this.memberId = memberId;
			}

			public String getName()
			{
				if (TextUtils.isEmpty(name))
				{
					return "";
				}
				return name;
			}

			public void setName(String name)
			{
				this.name = name;
			}

			public String getRegisterTime()
			{
				if (TextUtils.isEmpty(registerTime))
				{
					return "";
				}
				return registerTime;
			}

			public void setRegisterTime(String registerTime)
			{
				this.registerTime = registerTime;
			}

			public String getShortName()
			{
				if (TextUtils.isEmpty(shortName))
				{
					return "";
				}
				return shortName;
			}

			public void setShortName(String shortName)
			{
				this.shortName = shortName;
			}

			public String getState()
			{
				if (TextUtils.isEmpty(state))
				{
					return "";
				}
				return state;
			}

			public void setState(String state)
			{
				this.state = state;
			}

			public String getType()
			{
				if (TextUtils.isEmpty(type))
				{
					return "";
				}
				return type;
			}

			public void setType(String type)
			{
				this.type = type;
			}

		}

		public class MemberBusiness{
			private MemberData data;
			private String memberId;
			private String memberType;
			private String memberTypeCode;

			public MemberData getData() {
				return data;
			}

			public void setData(MemberData data) {
				this.data = data;
			}

			public String getMemberId() {
				if (TextUtils.isEmpty(memberId))
				{
					return "";
				}
				return memberId;
			}

			public void setMemberId(String memberId) {
				this.memberId = memberId;
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

			public class MemberData{
				private List<BusinessAreas> businessAreas;
				private List<BusinessScopes> businessScopes;
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
		public class User
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
			private String phone;// 手机号
			private String photo;// 头像地址
			private String registerTime;
			private String sex;
			private String state;
			private String userId;
			private String username;

			public String getAddress()
			{
				if (TextUtils.isEmpty(address))
				{
					return "";
				}
				return address;
			}

			public void setAddress(String address)
			{
				this.address = address;
			}

			public String getArea()
			{
				if (TextUtils.isEmpty(area))
				{
					return "";
				}
				return area;
			}

			public void setArea(String area)
			{
				this.area = area;
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

			public String getCountry()
			{
				if (TextUtils.isEmpty(country))
				{
					return "";
				}
				return country;
			}

			public void setCountry(String country)
			{
				this.country = country;
			}

			public String getEmail()
			{
				if (TextUtils.isEmpty(email))
				{
					return "";
				}
				return email;
			}

			public void setEmail(String email)
			{
				this.email = email;
			}

			public String getLastAccess()
			{
				if (TextUtils.isEmpty(lastAccess))
				{
					return "";
				}
				return lastAccess;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public String getManagerId()
			{
				if (TextUtils.isEmpty(managerId))
				{
					return "";
				}
				return managerId;
			}

			public void setManagerId(String managerId)
			{
				this.managerId = managerId;
			}

			public String getMemberId()
			{
				if (TextUtils.isEmpty(memberId))
				{
					return "";
				}
				return memberId;
			}

			public void setMemberId(String memberId)
			{
				this.memberId = memberId;
			}

			public String getMobile()
			{
				if (TextUtils.isEmpty(mobile))
				{
					return "";
				}
				return mobile;
			}

			public void setMobile(String mobile)
			{
				this.mobile = mobile;
			}

			public String getName()
			{
				if (TextUtils.isEmpty(name))
				{
					return "";
				}
				return name;
			}

			public void setName(String name)
			{
				this.name = name;
			}

			public String getPhone()
			{
				if (TextUtils.isEmpty(phone))
				{
					return "";
				}
				return phone;
			}

			public void setPhone(String phone)
			{
				this.phone = phone;
			}

			public String getPhoto()
			{
				if (TextUtils.isEmpty(photo))
				{
					return "";
				}
				return photo;
			}

			public void setPhoto(String photo)
			{
				this.photo = photo;
			}

			public String getRegisterTime()
			{
				if (TextUtils.isEmpty(registerTime))
				{
					return "";
				}
				return registerTime;
			}

			public void setRegisterTime(String registerTime)
			{
				this.registerTime = registerTime;
			}

			public String getSex()
			{
				if (TextUtils.isEmpty(sex))
				{
					return "";
				}
				return sex;
			}

			public void setSex(String sex)
			{
				this.sex = sex;
			}

			public String getState()
			{
				if (TextUtils.isEmpty(state))
				{
					return "";
				}
				return state;
			}

			public void setState(String state)
			{
				this.state = state;
			}

			public String getUserId()
			{
				if (TextUtils.isEmpty(userId))
				{
					return "";
				}
				return userId;
			}

			public void setUserId(String userId)
			{
				this.userId = userId;
			}

			public String getUsername()
			{
				if (TextUtils.isEmpty(username))
				{
					return "";
				}
				return username;
			}

			public void setUsername(String username)
			{
				this.username = username;
			}

		}

		public class StanBuys
		{
			private String brand;// 产地/品牌
			private String breed;// 品名
			private String breedId;// 品种ID
			private String city;// 交货地
			private String company;// 公司名称
			private String contacter;// 联系人
			private String dueTime;// 有效期
			private String id;
			private String lastAccess;
			private String manual;// 0-未要求人工服务 1-已要求人工服务
			private String material;// 材质
			private String memberId;// 会员代码
			private String note;// 备注
			private String phone;// 联系电话
			private String price;// 求购价格 (暂不显示)
			private String publishTime;// 发布时间
			private String qty;// 求购数量
			private String quotNum;// 报价数量
			private String quotUserId;// 报价人ID号，逗号隔开
			private String resource;// 来源:0-网站快捷需求 1-会员中心 2-手机端
			private String rushManagerId;// 抢单人Id
			private String rushManagerName;// 抢单人
			private String rushManagerPhone;
			private String rushStatus;// 抢单状态 0-待抢单 1-已抢单
			private String rushTime;// 抢单时间
			private String serialVersionUID;
			private String spec;// 规格
			private String status;// 状态 0-待报价 1-已报价 2-已成交 9-终止
			private String userId;// 用户ID
			private String version;// 版本
			/**用来自己判断页面跳转状态 0:待匹配 ，1：待抢单，2：待报价，3：已取消*/
			private int skipStatus = -1;
			
			
			
			public String getRushManagerPhone()
			{
				if (TextUtils.isEmpty(rushManagerPhone))
				{
					return "";
				}
				return rushManagerPhone;
			}

			public void setRushManagerPhone(String rushManagerPhone)
			{
				this.rushManagerPhone = rushManagerPhone;
			}

			public int getSkipStatus()
			{
				return skipStatus;
			}

			public void setSkipStatus(int skipStatus)
			{
				this.skipStatus = skipStatus;
			}

			public String getBrand()
			{
				if (TextUtils.isEmpty(brand))
				{
					return "";
				}
				return brand;
			}

			public void setBrand(String brand)
			{
				this.brand = brand;
			}

			public String getBreed()
			{
				if (TextUtils.isEmpty(breed))
				{
					return "";
				}
				return breed;
			}

			public void setBreed(String breed)
			{
				this.breed = breed;
			}

			public String getBreedId()
			{
				if (TextUtils.isEmpty(breedId))
				{
					return "";
				}
				return breedId;
			}

			public void setBreedId(String breedId)
			{
				this.breedId = breedId;
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

			public String getCompany()
			{
				if (TextUtils.isEmpty(company))
				{
					return "";
				}
				return company;
			}

			public void setCompany(String company)
			{
				this.company = company;
			}

			public String getContacter()
			{
				if (TextUtils.isEmpty(contacter))
				{
					return "";
				}
				return contacter;
			}

			public void setContacter(String contacter)
			{
				this.contacter = contacter;
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

			public String getId()
			{
				if (TextUtils.isEmpty(id))
				{
					return "";
				}
				return id;
			}

			public void setId(String id)
			{
				this.id = id;
			}

			public String getLastAccess()
			{
				if (TextUtils.isEmpty(lastAccess))
				{
					return "";
				}
				return lastAccess;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public String getManual()
			{
				if (TextUtils.isEmpty(manual))
				{
					return "";
				}
				return manual;
			}

			public void setManual(String manual)
			{
				this.manual = manual;
			}

			public String getMaterial()
			{
				if (TextUtils.isEmpty(material))
				{
					return "";
				}
				return material;
			}

			public void setMaterial(String material)
			{
				this.material = material;
			}

			public String getMemberId()
			{
				if (TextUtils.isEmpty(memberId))
				{
					return "";
				}
				return memberId;
			}

			public void setMemberId(String memberId)
			{
				this.memberId = memberId;
			}

			public String getNote()
			{
				if (TextUtils.isEmpty(note))
				{
					return "";
				}
				return note;
			}

			public void setNote(String note)
			{
				this.note = note;
			}

			public String getPhone()
			{
				if (TextUtils.isEmpty(phone))
				{
					return "";
				}
				return phone;
			}

			public void setPhone(String phone)
			{
				this.phone = phone;
			}

			public String getPrice()
			{
				if (TextUtils.isEmpty(price))
				{
					return "";
				}
				return price;
			}

			public void setPrice(String price)
			{
				this.price = price;
			}

			public String getPublishTime()
			{
				if (TextUtils.isEmpty(publishTime))
				{
					return "";
				}
				return publishTime;
			}

			public void setPublishTime(String publishTime)
			{
				this.publishTime = publishTime;
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

			public String getQuotNum()
			{
				if (TextUtils.isEmpty(quotNum))
				{
					return "";
				}
				return quotNum;
			}

			public void setQuotNum(String quotNum)
			{
				this.quotNum = quotNum;
			}

			public String getQuotUserId()
			{
				if (TextUtils.isEmpty(quotUserId))
				{
					return "";
				}
				return quotUserId;
			}

			public void setQuotUserId(String quotUserId)
			{
				this.quotUserId = quotUserId;
			}

			public String getResource()
			{
				if (TextUtils.isEmpty(resource))
				{
					return "";
				}
				return resource;
			}

			public void setResource(String resource)
			{
				this.resource = resource;
			}

			public String getRushManagerId()
			{
				if (TextUtils.isEmpty(rushManagerId))
				{
					return "";
				}
				return rushManagerId;
			}

			public void setRushManagerId(String rushManagerId)
			{
				this.rushManagerId = rushManagerId;
			}

			public String getRushManagerName()
			{
				if (TextUtils.isEmpty(rushManagerName))
				{
					return "";
				}
				return rushManagerName;
			}

			public void setRushManagerName(String rushManagerName)
			{
				this.rushManagerName = rushManagerName;
			}

			public String getRushStatus()
			{
				if (TextUtils.isEmpty(rushStatus))
				{
					return "";
				}
				return rushStatus;
			}

			public void setRushStatus(String rushStatus)
			{
				this.rushStatus = rushStatus;
			}

			public String getRushTime()
			{
				if (TextUtils.isEmpty(rushTime))
				{
					return "";
				}
				return rushTime;
			}

			public void setRushTime(String rushTime)
			{
				this.rushTime = rushTime;
			}

			public String getSerialVersionUID()
			{
				if (TextUtils.isEmpty(serialVersionUID))
				{
					return "";
				}
				return serialVersionUID;
			}

			public void setSerialVersionUID(String serialVersionUID)
			{
				this.serialVersionUID = serialVersionUID;
			}

			public String getSpec()
			{
				if (TextUtils.isEmpty(spec))
				{
					return "";
				}
				return spec;
			}

			public void setSpec(String spec)
			{
				this.spec = spec;
			}

			public String getStatus()
			{
				if (TextUtils.isEmpty(status))
				{
					return "";
				}
				return status;
			}

			public void setStatus(String status)
			{
				this.status = status;
			}

			public String getUserId()
			{
				if (TextUtils.isEmpty(userId))
				{
					return "";
				}
				return userId;
			}

			public void setUserId(String userId)
			{
				this.userId = userId;
			}

			public String getVersion()
			{
				if (TextUtils.isEmpty(version))
				{
					return "";
				}
				return version;
			}

			public void setVersion(String version)
			{
				this.version = version;
			}

		}

	}
}

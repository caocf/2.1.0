package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageCustomData extends BaseData{

	private PaginationEntity pagination;
	
	public void setPagination(PaginationEntity pagination)
    {
        this.pagination = pagination;
    }

    public PaginationEntity getPagination()
    {
        return pagination;
    }
	
	 public class PaginationEntity
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

         public List<DatasEntity> getDatas()
         {
             return datas;
         }

         public String getCount()
         {
             if (TextUtils.isEmpty(count))
             {
                 return "";
             }
             return count;
         }

         public String getPage()
         {
             if (TextUtils.isEmpty(page))
             {
                 return "";
             }
             return page;
         }

         public String getPagenum()
         {
             if (TextUtils.isEmpty(pagenum))
             {
                 return "";
             }
             return pagenum;
         }

         public String getSize()
         {
             if (TextUtils.isEmpty(size))
             {
                 return "";
             }
             return size;
         }
         
         public class DatasEntity  implements Serializable
         {
			/**
              * spec : Φ14
              * breed : 二级螺纹钢
              * phone : 18616877614
              * cause : null
              * version : 0
              * city : 无锡
              * id : 249080
              * dueTime : 1436600082956
              * rushStatus : 0
              * rushManagerName :
              * userId : 46912
              * rushManagerId : 0
              * serialVersionUID : 1
              * lastAccess : 0
              * rushTime : 0
              * note :
              * dueStatus : 1
              * publishTime : 2015-07-08 15:34:42
              * status : 0
              * contacter :
              * breedId : 0101
              * resource : 2
              * qty : 50.0
              * quotNum : 0
              * material : HRB335E
              * manual : 1
              * price : 0.0
              * company : 将亟待解决实际上就是今生今世
              * quotUserId :
              * brand : 安徽贵航
              * memberId : 210199
              * bargaining;
              * myprice;
              */
        	 private User user;
        	 private String attestation; //1认证 0未认证
             private String spec;
             private String breed;
             private String phone;
             private String cause;
             private String version;
             private String city;
             private String id;
             private String dueTime;
             private String rushStatus;
             private String rushManagerName;
             private String userId;
             private String rushManagerId;
             private String serialVersionUID;
             private String lastAccess;
             private String rushTime;
             private String note;
             private String dueStatus;
             private String publishTime;
             private String status;
             private String contacter;
             private String breedId;
             private String resource;
             private String qty;
             private String quotNum;
             private String quotNote;
             private String material;
             private String manual;
             private String price;
             private String company;
             private String quotUserId;
             private String brand;
             private String memberId;
             private String bargaining;
             private String myPrice;

             public void setSpec(String spec)
             {
                 this.spec = spec;
             }

             public void setBreed(String breed)
             {
                 this.breed = breed;
             }

             public void setPhone(String phone)
             {
                 this.phone = phone;
             }

             public void setCause(String cause)
             {
                 this.cause = cause;
             }

             public void setVersion(String version)
             {
                 this.version = version;
             }

             public void setCity(String city)
             {
                 this.city = city;
             }

             public void setId(String id)
             {
                 this.id = id;
             }

             public void setDueTime(String dueTime)
             {
                 this.dueTime = dueTime;
             }

             public void setRushStatus(String rushStatus)
             {
                 this.rushStatus = rushStatus;
             }

             public void setRushManagerName(String rushManagerName)
             {
                 this.rushManagerName = rushManagerName;
             }

             public void setUserId(String userId)
             {
                 this.userId = userId;
             }

             public void setRushManagerId(String rushManagerId)
             {
                 this.rushManagerId = rushManagerId;
             }

             public void setSerialVersionUID(String serialVersionUID)
             {
                 this.serialVersionUID = serialVersionUID;
             }

             public void setLastAccess(String lastAccess)
             {
                 this.lastAccess = lastAccess;
             }

             public void setRushTime(String rushTime)
             {
                 this.rushTime = rushTime;
             }

             public void setNote(String note)
             {
                 this.note = note;
             }

             public void setDueStatus(String dueStatus)
             {
                 this.dueStatus = dueStatus;
             }

             public void setPublishTime(String publishTime)
             {
                 this.publishTime = publishTime;
             }

             public void setStatus(String status)
             {
                 this.status = status;
             }

             public void setContacter(String contacter)
             {
                 this.contacter = contacter;
             }

             public void setBreedId(String breedId)
             {
                 this.breedId = breedId;
             }

             public void setResource(String resource)
             {
                 this.resource = resource;
             }

             public void setQty(String qty)
             {
                 this.qty = qty;
             }

             public void setQuotNum(String quotNum)
             {
                 this.quotNum = quotNum;
             }

             public void setMaterial(String material)
             {
                 this.material = material;
             }

             public void setManual(String manual)
             {
                 this.manual = manual;
             }

             public void setPrice(String price)
             {
                 this.price = price;
             }

             public void setCompany(String company)
             {
                 this.company = company;
             }

             public void setQuotUserId(String quotUserId)
             {
                 this.quotUserId = quotUserId;
             }

             public void setBrand(String brand)
             {
                 this.brand = brand;
             }

             public void setMemberId(String memberId)
             {
                 this.memberId = memberId;
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

             public String getPhone()
             {
                 if (TextUtils.isEmpty(phone))
                 {
                     return "";
                 }
                 return phone;
             }

             public String getCause()
             {
                 if (TextUtils.isEmpty(cause))
                 {
                     return "";
                 }
                 return cause;
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

             public String getRushStatus()
             {
                 if (TextUtils.isEmpty(rushStatus))
                 {
                     return "";
                 }
                 return rushStatus;
             }

             public String getRushManagerName()
             {
                 if (TextUtils.isEmpty(rushManagerName))
                 {
                     return "";
                 }
                 return rushManagerName;
             }

             public String getUserId()
             {
                 if (TextUtils.isEmpty(userId))
                 {
                     return "";
                 }
                 return userId;
             }

             public String getRushManagerId()
             {
                 if (TextUtils.isEmpty(rushManagerId))
                 {
                     return "";
                 }
                 return rushManagerId;
             }

             public String getSerialVersionUID()
             {
                 if (TextUtils.isEmpty(serialVersionUID))
                 {
                     return "";
                 }
                 return serialVersionUID;
             }

             public String getLastAccess()
             {
                 if (TextUtils.isEmpty(lastAccess))
                 {
                     return "";
                 }
                 return lastAccess;
             }

             public String getRushTime()
             {
                 if (TextUtils.isEmpty(rushTime))
                 {
                     return "";
                 }
                 return rushTime;
             }

             public String getNote()
             {
                 if (TextUtils.isEmpty(note))
                 {
                     return "";
                 }
                 return note;
             }

             public String getDueStatus()
             {
                 if (TextUtils.isEmpty(dueStatus))
                 {
                     return "";
                 }
                 return dueStatus;
             }

             public String getPublishTime()
             {
                 if (TextUtils.isEmpty(publishTime))
                 {
                     return "";
                 }
                 return publishTime;
             }

             public String getStatus()
             {
                 if (TextUtils.isEmpty(status))
                 {
                     return "";
                 }
                 return status;
             }

             public String getContacter()
             {
                 if (TextUtils.isEmpty(contacter))
                 {
                     return "";
                 }
                 return contacter;
             }

             public String getBreedId()
             {
                 if (TextUtils.isEmpty(breedId))
                 {
                     return "";
                 }
                 return breedId;
             }

             public String getResource()
             {
                 if (TextUtils.isEmpty(resource))
                 {
                     return "";
                 }
                 return resource;
             }

             public String getQty()
             {
                 if (TextUtils.isEmpty(qty))
                 {
                     return "";
                 }
                 return qty;
             }

             public String getQuotNum()
             {
                 if (TextUtils.isEmpty(quotNum))
                 {
                     return "";
                 }
                 return quotNum;
             }

             public String getQuotNote() {
                 if (TextUtils.isEmpty(quotNote))
                 {
                     return "";
                 }
                 return quotNote;
             }

             public void setQuotNote(String quotNote) {
                 this.quotNote = quotNote;
             }

             public String getMaterial()
             {
                 if (TextUtils.isEmpty(material))
                 {
                     return "";
                 }
                 return material;
             }

             public String getManual()
             {
                 if (TextUtils.isEmpty(manual))
                 {
                     return "";
                 }
                 return manual;
             }

             public String getPrice()
             {
                 if (TextUtils.isEmpty(price))
                 {
                     return "";
                 }
                 return price;
             }

             public String getCompany()
             {
                 if (TextUtils.isEmpty(company))
                 {
                     return "";
                 }
                 return company;
             }

             public String getQuotUserId()
             {
                 if (TextUtils.isEmpty(quotUserId))
                 {
                     return "";
                 }
                 return quotUserId;
             }

             public String getBrand()
             {
                 if (TextUtils.isEmpty(brand))
                 {
                     return "";
                 }
                 return brand;
             }

             public String getMemberId()
             {
                 if (TextUtils.isEmpty(memberId))
                 {
                     return "";
                 }
                 return memberId;
             }

			public String getBargaining() {
				if (TextUtils.isEmpty(bargaining)) {
					return "";
				}
				return bargaining;
			}

			public void setBargaining(String bargaining) {
				this.bargaining = bargaining;
			}

			public String getMyPrice() {
				if (TextUtils.isEmpty(myPrice)) {
					return "";
				}
				return myPrice;
			}

			public void setMyPrice(String myPrice) {
				this.myPrice = myPrice;
			}

			public User getUser() {
				return user;
			}

			public void setUser(User user) {
				this.user = user;
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

         }
         
         public class User  implements Serializable
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
				public String getPhoto() {
					if (TextUtils.isEmpty(photo)) {
						return "";
					}
					return photo;
				}
				public void setPhoto(String photo) {
					this.photo = photo;
				}
        	 
         }
         
     }
	
	
	
}

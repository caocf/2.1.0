package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 已报价数据实体类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-6-4 下午5:19:46
 */
public class DetailStanBuyData extends BaseData
{
	private static final long serialVersionUID = -2436353981106728870L;

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
		private String dealPrice;
		private Manager manager;
		private Quot quot;
		private StanBuy stanBuy;
		private Order order;

		public Order getOrder()
		{
			return order;
		}

		public void setOrder(Order order)
		{
			this.order = order;
		}

		public String getDealPrice()
		{
			if (TextUtils.isEmpty(dealPrice))
			{
				return "";
			}
			return dealPrice;
		}

		public void setDealPrice(String dealPrice)
		{
			this.dealPrice = dealPrice;
		}

		public Quot getQuot()
		{
			return quot;
		}

		public void setQuot(Quot quot)
		{
			this.quot = quot;
		}

		public Manager getManager()
		{
			return manager;
		}

		public void setManager(Manager manager)
		{
			this.manager = manager;
		}

		public Quot getQuots()
		{
			return quot;
		}

		public void setQuots(Quot quot)
		{
			this.quot = quot;
		}

		public StanBuy getStanBuy()
		{
			return stanBuy;
		}

		public void setStanBuy(StanBuy stanBuy)
		{
			this.stanBuy = stanBuy;
		}

		public class Manager
		{
			private String dealCount;// 交易次数
			private String managerId;// 管理员ID
			private String managerName;// 管理员名称
			private String managerPhoto;// 管理员头像
			private String managerPhone;// 管理员联系方式

			public String getManagerPhone()
			{
				if (TextUtils.isEmpty(managerPhone))
				{
					return "";
				}
				return managerPhone;
			}

			public void setManagerPhone(String managerPhone)
			{
				this.managerPhone = managerPhone;
			}

			public String getDealCount()
			{
				if (TextUtils.isEmpty(dealCount))
				{
					return "";
				}
				return dealCount;
			}

			public void setDealCount(String dealCount)
			{
				this.dealCount = dealCount;
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

			public String getManagerName()
			{
				if (TextUtils.isEmpty(managerName))
				{
					return "";
				}
				return managerName;
			}

			public void setManagerName(String managerName)
			{
				this.managerName = managerName;
			}

			public String getManagerPhoto()
			{
				if (TextUtils.isEmpty(managerPhoto))
				{
					return "";
				}
				return managerPhoto;
			}

			public void setManagerPhoto(String managerPhoto)
			{
				this.managerPhoto = managerPhoto;
			}

		}

		public class Quot
		{
			private String brand;
			private String breed;
			private String buyMemberId;
			private String buyMemberName;
			private String buyPhone;
			private String buyUserId;
			private String buyUserName;
			private String id;
			private String lastAccess;
			private String manaerId;
			private String material;
			private String price;
			private String quotContact;
			private String quotMemberId;
			private String quotMemberName;
			private String quotPhone;
			private String quotTime;
			private String quotUserId;
			private String serialVersionUID;
			private String spec;
			private String stanBuyId;
			private String type;
			private String version;
			private String warehouse;

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

			public String getBuyMemberId()
			{
				if (TextUtils.isEmpty(buyMemberId))
				{
					return "";
				}
				return buyMemberId;
			}

			public void setBuyMemberId(String buyMemberId)
			{
				this.buyMemberId = buyMemberId;
			}

			public String getBuyMemberName()
			{
				if (TextUtils.isEmpty(buyMemberName))
				{
					return "";
				}
				return buyMemberName;
			}

			public void setBuyMemberName(String buyMemberName)
			{
				this.buyMemberName = buyMemberName;
			}

			public String getBuyPhone()
			{
				if (TextUtils.isEmpty(buyPhone))
				{
					return "";
				}
				return buyPhone;
			}

			public void setBuyPhone(String buyPhone)
			{
				this.buyPhone = buyPhone;
			}

			public String getBuyUserId()
			{
				if (TextUtils.isEmpty(buyUserId))
				{
					return "";
				}
				return buyUserId;
			}

			public void setBuyUserId(String buyUserId)
			{
				this.buyUserId = buyUserId;
			}

			public String getBuyUserName()
			{
				if (TextUtils.isEmpty(buyUserName))
				{
					return "";
				}
				return buyUserName;
			}

			public void setBuyUserName(String buyUserName)
			{
				this.buyUserName = buyUserName;
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

			public String getManaerId()
			{
				if (TextUtils.isEmpty(manaerId))
				{
					return "";
				}
				return manaerId;
			}

			public void setManaerId(String manaerId)
			{
				this.manaerId = manaerId;
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

			public String getQuotContact()
			{
				if (TextUtils.isEmpty(quotContact))
				{
					return "";
				}
				return quotContact;
			}

			public void setQuotContact(String quotContact)
			{
				this.quotContact = quotContact;
			}

			public String getQuotMemberId()
			{
				if (TextUtils.isEmpty(quotMemberId))
				{
					return "";
				}
				return quotMemberId;
			}

			public void setQuotMemberId(String quotMemberId)
			{
				this.quotMemberId = quotMemberId;
			}

			public String getQuotMemberName()
			{
				if (TextUtils.isEmpty(quotMemberName))
				{
					return "";
				}
				return quotMemberName;
			}

			public void setQuotMemberName(String quotMemberName)
			{
				this.quotMemberName = quotMemberName;
			}

			public String getQuotPhone()
			{
				if (TextUtils.isEmpty(quotPhone))
				{
					return "";
				}
				return quotPhone;
			}

			public void setQuotPhone(String quotPhone)
			{
				this.quotPhone = quotPhone;
			}

			public String getQuotTime()
			{
				if (TextUtils.isEmpty(quotTime))
				{
					return "";
				}
				return quotTime;
			}

			public void setQuotTime(String quotTime)
			{
				this.quotTime = quotTime;
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

			public String getStanBuyId()
			{
				if (TextUtils.isEmpty(stanBuyId))
				{
					return "";
				}
				return stanBuyId;
			}

			public void setStanBuyId(String stanBuyId)
			{
				this.stanBuyId = stanBuyId;
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

			public String getWarehouse()
			{
				if (TextUtils.isEmpty(warehouse))
				{
					return "";
				}
				return warehouse;
			}

			public void setWarehouse(String warehouse)
			{
				this.warehouse = warehouse;
			}

		}

		public class StanBuy
		{
			private String brand;// 产地/品牌
			private String breed;// 品名
			private String breedId;// 品种ID
			private String city;
			private String company;
			private String contacter;
			private String dueTime;
			private String id;
			private String lastAccess;
			private String manual; // 0-未要求人工服务 1-已要求人工服务
			private String material;
			private String memberId;
			private String note;
			private String phone;
			private String price;
			private String publishTime;// 发布时间
			private String qty;
			private String quotNum;
			private String quotUserId;
			private String resource;
			private String rushManagerId;
			private String rushManagerName;
			private String rushStatus;// 抢单状态 0-待抢单 1-已抢单
			private String rushTime;
			private String serialVersionUID;
			private String spec;
			private String status;
			private String userId;
			private String version;
			private String gapTime;

			public String getGapTime()
			{
				if (TextUtils.isEmpty(gapTime))
				{
					return "";
				}
				return gapTime;
			}

			public void setGapTime(String gapTime)
			{
				this.gapTime = gapTime;
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

		public class Order
		{
			private String appraiseStatus;
			private String auditDesc;
			private String auditId;
			private String auditName;
			private String auditTime;
			private String brand;
			private String breed;
			private String breedId;
			private String buyMemberId;
			private String buyMemberName;
			private String buyPhone;
			private String buyUserId;
			private String buyUserName;
			private String city;
			private String id;
			private String lastAccess;
			private String managerId;
			private String managerName;
			private String material;
			private String orderSource;
			private String orderTime;
			private String price;
			private String qty;
			private String quotMemberId;
			private String quotMemberName;
			private String quotPhone;
			private String quotUserId;
			private String quotUserName;
			private String remitBillUrl1;
			private String remitBillUrl2;
			private String remitBillUrl3;
			private String remitBillUrl4;
			private String serialVersionUID;
			private String source;
			private String spec;
			private String stanBuyId;
			private String status;
			private String version;
			private String warehouse;

			public String getAppraiseStatus()
			{
				if (TextUtils.isEmpty(appraiseStatus))
				{
					return "";
				}
				return appraiseStatus;
			}

			public void setAppraiseStatus(String appraiseStatus)
			{
				this.appraiseStatus = appraiseStatus;
			}

			public String getAuditDesc()
			{
				if (TextUtils.isEmpty(auditDesc))
				{
					return "";
				}
				return auditDesc;
			}

			public void setAuditDesc(String auditDesc)
			{
				this.auditDesc = auditDesc;
			}

			public String getAuditId()
			{
				if (TextUtils.isEmpty(auditId))
				{
					return "";
				}
				return auditId;
			}

			public void setAuditId(String auditId)
			{
				this.auditId = auditId;
			}

			public String getAuditName()
			{
				if (TextUtils.isEmpty(auditName))
				{
					return "";
				}
				return auditName;
			}

			public void setAuditName(String auditName)
			{
				this.auditName = auditName;
			}

			public String getAuditTime()
			{
				if (TextUtils.isEmpty(auditTime))
				{
					return "";
				}
				return auditTime;
			}

			public void setAuditTime(String auditTime)
			{
				this.auditTime = auditTime;
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

			public String getBuyMemberId()
			{
				if (TextUtils.isEmpty(buyMemberId))
				{
					return "";
				}
				return buyMemberId;
			}

			public void setBuyMemberId(String buyMemberId)
			{
				this.buyMemberId = buyMemberId;
			}

			public String getBuyMemberName()
			{
				if (TextUtils.isEmpty(buyMemberName))
				{
					return "";
				}
				return buyMemberName;
			}

			public void setBuyMemberName(String buyMemberName)
			{
				this.buyMemberName = buyMemberName;
			}

			public String getBuyPhone()
			{
				if (TextUtils.isEmpty(buyPhone))
				{
					return "";
				}
				return buyPhone;
			}

			public void setBuyPhone(String buyPhone)
			{
				this.buyPhone = buyPhone;
			}

			public String getBuyUserId()
			{
				if (TextUtils.isEmpty(buyUserId))
				{
					return "";
				}
				return buyUserId;
			}

			public void setBuyUserId(String buyUserId)
			{
				this.buyUserId = buyUserId;
			}

			public String getBuyUserName()
			{
				if (TextUtils.isEmpty(buyUserName))
				{
					return "";
				}
				return buyUserName;
			}

			public void setBuyUserName(String buyUserName)
			{
				this.buyUserName = buyUserName;
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

			public String getManagerName()
			{
				if (TextUtils.isEmpty(managerName))
				{
					return "";
				}
				return managerName;
			}

			public void setManagerName(String managerName)
			{
				this.managerName = managerName;
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

			public String getOrderSource()
			{
				if (TextUtils.isEmpty(orderSource))
				{
					return "";
				}
				return orderSource;
			}

			public void setOrderSource(String orderSource)
			{
				this.orderSource = orderSource;
			}

			public String getOrderTime()
			{
				if (TextUtils.isEmpty(orderTime))
				{
					return "";
				}
				return orderTime;
			}

			public void setOrderTime(String orderTime)
			{
				this.orderTime = orderTime;
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

			public String getQuotMemberId()
			{
				if (TextUtils.isEmpty(quotMemberId))
				{
					return "";
				}
				return quotMemberId;
			}

			public void setQuotMemberId(String quotMemberId)
			{
				this.quotMemberId = quotMemberId;
			}

			public String getQuotMemberName()
			{
				if (TextUtils.isEmpty(quotMemberName))
				{
					return "";
				}
				return quotMemberName;
			}

			public void setQuotMemberName(String quotMemberName)
			{
				this.quotMemberName = quotMemberName;
			}

			public String getQuotPhone()
			{
				if (TextUtils.isEmpty(quotPhone))
				{
					return "";
				}
				return quotPhone;
			}

			public void setQuotPhone(String quotPhone)
			{
				this.quotPhone = quotPhone;
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

			public String getQuotUserName()
			{
				if (TextUtils.isEmpty(quotUserName))
				{
					return "";
				}
				return quotUserName;
			}

			public void setQuotUserName(String quotUserName)
			{
				this.quotUserName = quotUserName;
			}

			public String getRemitBillUrl1()
			{
				if (TextUtils.isEmpty(remitBillUrl1))
				{
					return "";
				}
				return remitBillUrl1;
			}

			public void setRemitBillUrl1(String remitBillUrl1)
			{
				this.remitBillUrl1 = remitBillUrl1;
			}

			public String getRemitBillUrl2()
			{
				if (TextUtils.isEmpty(remitBillUrl2))
				{
					return "";
				}
				return remitBillUrl2;
			}

			public void setRemitBillUrl2(String remitBillUrl2)
			{
				this.remitBillUrl2 = remitBillUrl2;
			}

			public String getRemitBillUrl3()
			{
				if (TextUtils.isEmpty(remitBillUrl3))
				{
					return "";
				}
				return remitBillUrl3;
			}

			public void setRemitBillUrl3(String remitBillUrl3)
			{
				this.remitBillUrl3 = remitBillUrl3;
			}

			public String getRemitBillUrl4()
			{
				if (TextUtils.isEmpty(remitBillUrl4))
				{
					return "";
				}
				return remitBillUrl4;
			}

			public void setRemitBillUrl4(String remitBillUrl4)
			{
				this.remitBillUrl4 = remitBillUrl4;
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

			public String getSource()
			{
				if (TextUtils.isEmpty(source))
				{
					return "";
				}
				return source;
			}

			public void setSource(String source)
			{
				this.source = source;
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

			public String getStanBuyId()
			{
				if (TextUtils.isEmpty(stanBuyId))
				{
					return "";
				}
				return stanBuyId;
			}

			public void setStanBuyId(String stanBuyId)
			{
				this.stanBuyId = stanBuyId;
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

			public String getWarehouse()
			{
				if (TextUtils.isEmpty(warehouse))
				{
					return "";
				}
				return warehouse;
			}

			public void setWarehouse(String warehouse)
			{
				this.warehouse = warehouse;
			}

		}

	}
}

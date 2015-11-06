package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-20 下午3:27:45
 */
public class OrderDetailData extends BaseData
{

	private static final long serialVersionUID = 8031224891236257631L;
	private DataEntity data;


	public void setData(DataEntity data)
	{
		this.data = data;
	}

	public DataEntity getData()
	{
		return data;
	}

	public class DataEntity
	{
		private String partnerUserArea;
		private OrderEntity order;
		private String partnerUserCountry;
		private String partnerUserProvince;
		private String partnerUserPhoto;
		private String partnerUserCity;
		private AppraiseEntity appraise;

		public void setPartnerUserArea(String partnerUserArea)
		{
			this.partnerUserArea = partnerUserArea;
		}

		public void setOrder(OrderEntity order)
		{
			this.order = order;
		}

		public void setPartnerUserCountry(String partnerUserCountry)
		{
			this.partnerUserCountry = partnerUserCountry;
		}

		public void setPartnerUserProvince(String partnerUserProvince)
		{
			this.partnerUserProvince = partnerUserProvince;
		}

		public void setPartnerUserPhoto(String partnerUserPhoto)
		{
			this.partnerUserPhoto = partnerUserPhoto;
		}

		public void setPartnerUserCity(String partnerUserCity)
		{
			this.partnerUserCity = partnerUserCity;
		}

		public void setAppraise(AppraiseEntity appraise)
		{
			this.appraise = appraise;
		}

		public String getPartnerUserArea()
		{
			if(TextUtils.isEmpty(partnerUserArea))
			{
				return "";
			}
			return partnerUserArea;
		}

		public OrderEntity getOrder()
		{

			return order;
		}

		public String getPartnerUserCountry()
		{
			if(TextUtils.isEmpty(partnerUserCountry))
			{
				return "";
			}
			return partnerUserCountry;
		}

		public String getPartnerUserProvince()
		{
			if(TextUtils.isEmpty(partnerUserProvince))
			{
				return "";
			}
			return partnerUserProvince;
		}

		public String getPartnerUserPhoto()
		{
			if(TextUtils.isEmpty(partnerUserPhoto))
			{
				return "";
			}
			return partnerUserPhoto;
		}

		public String getPartnerUserCity()
		{
			if(TextUtils.isEmpty(partnerUserCity))
			{
				return "";
			}
			return partnerUserCity;
		}

		public AppraiseEntity getAppraise()
		{

			return appraise;
		}

		public class OrderEntity
		{
			/**
			 * spec : Φ25
			 * buyPhone : 15821398484
			 * breed : 精轧螺纹钢
			 * auditDesc : null
			 * remitBillUrl4 :
			 * buyMemberId : 210169
			 * remitBillUrl3 :
			 * auditName : null
			 * quotMemberName : 手机APP注册15821398481
			 * quotMemberId : 210180
			 * buyUserId : 46881
			 * warehouse :
			 * version : 0
			 * city : 苏州
			 * id : 240329
			 * auditTime : 0
			 * remitBillUrl1 :
			 * remitBillUrl2 :
			 * serialVersionUID : 1
			 * lastAccess : 1441700484821
			 * appraiseStatus : 0
			 * status : 0
			 * stanBuyId : 249544
			 * orderSource : 2
			 * managerName : null
			 * breedId : 0104
			 * auditId : 0
			 * qty : 60.0
			 * orderTime : 2015-09-08 16:21:24
			 * buyMemberName : 上海钢联
			 * material : PSB540
			 * buyUserName : 张三丰
			 * price : 3000.0
			 * source : 0
			 * quotUserId : 46892
			 * managerId : 0
			 * quotPhone : 15821398481
			 * brand : 方大特钢
			 * quotUserName : 张无忌
			 */
			private String spec;
			private String buyPhone;
			private String breed;
			private String auditDesc;
			private String remitBillUrl4;
			private String buyMemberId;
			private String remitBillUrl3;
			private String auditName;
			private String quotMemberName;
			private String quotMemberId;
			private String buyUserId;
			private String warehouse;
			private String version;
			private String city;
			private String id;
			private String auditTime;
			private String remitBillUrl1;
			private String remitBillUrl2;
			private String serialVersionUID;
			private String lastAccess;
			private String appraiseStatus;
			private String status;
			private String stanBuyId;
			private String orderSource;
			private String managerName;
			private String breedId;
			private String auditId;
			private String qty;
			private String orderTime;
			private String buyMemberName;
			private String material;
			private String buyUserName;
			private String price;
			private String source;
			private String quotUserId;
			private String managerId;
			private String quotPhone;
			private String brand;
			private String quotUserName;

			public void setSpec(String spec)
			{
				this.spec = spec;
			}

			public void setBuyPhone(String buyPhone)
			{
				this.buyPhone = buyPhone;
			}

			public void setBreed(String breed)
			{
				this.breed = breed;
			}

			public void setAuditDesc(String auditDesc)
			{
				this.auditDesc = auditDesc;
			}

			public void setRemitBillUrl4(String remitBillUrl4)
			{
				this.remitBillUrl4 = remitBillUrl4;
			}

			public void setBuyMemberId(String buyMemberId)
			{
				this.buyMemberId = buyMemberId;
			}

			public void setRemitBillUrl3(String remitBillUrl3)
			{
				this.remitBillUrl3 = remitBillUrl3;
			}

			public void setAuditName(String auditName)
			{
				this.auditName = auditName;
			}

			public void setQuotMemberName(String quotMemberName)
			{
				this.quotMemberName = quotMemberName;
			}

			public void setQuotMemberId(String quotMemberId)
			{
				this.quotMemberId = quotMemberId;
			}

			public void setBuyUserId(String buyUserId)
			{
				this.buyUserId = buyUserId;
			}

			public void setWarehouse(String warehouse)
			{
				this.warehouse = warehouse;
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

			public void setAuditTime(String auditTime)
			{
				this.auditTime = auditTime;
			}

			public void setRemitBillUrl1(String remitBillUrl1)
			{
				this.remitBillUrl1 = remitBillUrl1;
			}

			public void setRemitBillUrl2(String remitBillUrl2)
			{
				this.remitBillUrl2 = remitBillUrl2;
			}

			public void setSerialVersionUID(String serialVersionUID)
			{
				this.serialVersionUID = serialVersionUID;
			}

			public void setLastAccess(String lastAccess)
			{
				this.lastAccess = lastAccess;
			}

			public void setAppraiseStatus(String appraiseStatus)
			{
				this.appraiseStatus = appraiseStatus;
			}

			public void setStatus(String status)
			{
				this.status = status;
			}

			public void setStanBuyId(String stanBuyId)
			{
				this.stanBuyId = stanBuyId;
			}

			public void setOrderSource(String orderSource)
			{
				this.orderSource = orderSource;
			}

			public void setManagerName(String managerName)
			{
				this.managerName = managerName;
			}

			public void setBreedId(String breedId)
			{
				this.breedId = breedId;
			}

			public void setAuditId(String auditId)
			{
				this.auditId = auditId;
			}

			public void setQty(String qty)
			{
				this.qty = qty;
			}

			public void setOrderTime(String orderTime)
			{
				this.orderTime = orderTime;
			}

			public void setBuyMemberName(String buyMemberName)
			{
				this.buyMemberName = buyMemberName;
			}

			public void setMaterial(String material)
			{
				this.material = material;
			}

			public void setBuyUserName(String buyUserName)
			{
				this.buyUserName = buyUserName;
			}

			public void setPrice(String price)
			{
				this.price = price;
			}

			public void setSource(String source)
			{
				this.source = source;
			}

			public void setQuotUserId(String quotUserId)
			{
				this.quotUserId = quotUserId;
			}

			public void setManagerId(String managerId)
			{
				this.managerId = managerId;
			}

			public void setQuotPhone(String quotPhone)
			{
				this.quotPhone = quotPhone;
			}

			public void setBrand(String brand)
			{
				this.brand = brand;
			}

			public void setQuotUserName(String quotUserName)
			{
				this.quotUserName = quotUserName;
			}

			public String getSpec()
			{
				if(TextUtils.isEmpty(spec))
				{
					return "";
				}
				return spec;
			}

			public String getBuyPhone()
			{
				if(TextUtils.isEmpty(buyPhone))
				{
					return "";
				}
				return buyPhone;
			}

			public String getBreed()
			{
				if(TextUtils.isEmpty(breed))
				{
					return "";
				}
				return breed;
			}

			public String getAuditDesc()
			{
				if(TextUtils.isEmpty(auditDesc))
				{
					return "";
				}
				return auditDesc;
			}

			public String getRemitBillUrl4()
			{
				if(TextUtils.isEmpty(remitBillUrl4))
				{
					return "";
				}
				return remitBillUrl4;
			}

			public String getBuyMemberId()
			{
				if(TextUtils.isEmpty(buyMemberId))
				{
					return "";
				}
				return buyMemberId;
			}

			public String getRemitBillUrl3()
			{
				if(TextUtils.isEmpty(remitBillUrl3))
				{
					return "";
				}
				return remitBillUrl3;
			}

			public String getAuditName()
			{
				if(TextUtils.isEmpty(auditName))
				{
					return "";
				}
				return auditName;
			}

			public String getQuotMemberName()
			{
				if(TextUtils.isEmpty(quotMemberName))
				{
					return "";
				}
				return quotMemberName;
			}

			public String getQuotMemberId()
			{
				if(TextUtils.isEmpty(quotMemberId))
				{
					return "";
				}
				return quotMemberId;
			}

			public String getBuyUserId()
			{
				if(TextUtils.isEmpty(buyUserId))
				{
					return "";
				}
				return buyUserId;
			}

			public String getWarehouse()
			{
				if(TextUtils.isEmpty(warehouse))
				{
					return "";
				}
				return warehouse;
			}

			public String getVersion()
			{
				if(TextUtils.isEmpty(version))
				{
					return "";
				}
				return version;
			}

			public String getCity()
			{
				if(TextUtils.isEmpty(city))
				{
					return "";
				}
				return city;
			}

			public String getId()
			{
				if(TextUtils.isEmpty(id))
				{
					return "";
				}
				return id;
			}

			public String getAuditTime()
			{
				if(TextUtils.isEmpty(auditTime))
				{
					return "";
				}
				return auditTime;
			}

			public String getRemitBillUrl1()
			{
				if(TextUtils.isEmpty(remitBillUrl1))
				{
					return "";
				}
				return remitBillUrl1;
			}

			public String getRemitBillUrl2()
			{
				if(TextUtils.isEmpty(remitBillUrl2))
				{
					return "";
				}
				return remitBillUrl2;
			}

			public String getSerialVersionUID()
			{
				if(TextUtils.isEmpty(serialVersionUID))
				{
					return "";
				}
				return serialVersionUID;
			}

			public String getLastAccess()
			{
				if(TextUtils.isEmpty(lastAccess))
				{
					return "";
				}
				return lastAccess;
			}

			public String getAppraiseStatus()
			{
				if(TextUtils.isEmpty(appraiseStatus))
				{
					return "";
				}
				return appraiseStatus;
			}

			public String getStatus()
			{
				if(TextUtils.isEmpty(status))
				{
					return "";
				}
				return status;
			}

			public String getStanBuyId()
			{
				if(TextUtils.isEmpty(stanBuyId))
				{
					return "";
				}
				return stanBuyId;
			}

			public String getOrderSource()
			{
				if(TextUtils.isEmpty(orderSource))
				{
					return "";
				}
				return orderSource;
			}

			public String getManagerName()
			{
				if(TextUtils.isEmpty(managerName))
				{
					return "";
				}
				return managerName;
			}

			public String getBreedId()
			{
				if(TextUtils.isEmpty(breedId))
				{
					return "";
				}
				return breedId;
			}

			public String getAuditId()
			{
				if(TextUtils.isEmpty(auditId))
				{
					return "";
				}
				return auditId;
			}

			public String getQty()
			{
				if(TextUtils.isEmpty(qty))
				{
					return "";
				}
				return qty;
			}

			public String getOrderTime()
			{
				if(TextUtils.isEmpty(orderTime))
				{
					return "";
				}
				return orderTime;
			}

			public String getBuyMemberName()
			{
				if(TextUtils.isEmpty(buyMemberName))
				{
					return "";
				}
				return buyMemberName;
			}

			public String getMaterial()
			{
				if(TextUtils.isEmpty(material))
				{
					return "";
				}
				return material;
			}

			public String getBuyUserName()
			{
				if(TextUtils.isEmpty(buyUserName))
				{
					return "";
				}
				return buyUserName;
			}

			public String getPrice()
			{
				if(TextUtils.isEmpty(price))
				{
					return "";
				}
				return price;
			}

			public String getSource()
			{
				if(TextUtils.isEmpty(source))
				{
					return "";
				}
				return source;
			}

			public String getQuotUserId()
			{
				if(TextUtils.isEmpty(quotUserId))
				{
					return "";
				}
				return quotUserId;
			}

			public String getManagerId()
			{
				if(TextUtils.isEmpty(managerId))
				{
					return "";
				}
				return managerId;
			}

			public String getQuotPhone()
			{
				if(TextUtils.isEmpty(quotPhone))
				{
					return "";
				}
				return quotPhone;
			}

			public String getBrand()
			{
				if(TextUtils.isEmpty(brand))
				{
					return "";
				}
				return brand;
			}

			public String getQuotUserName()
			{
				if(TextUtils.isEmpty(quotUserName))
				{
					return "";
				}
				return quotUserName;
			}
		}

		public class AppraiseEntity
		{
			private String sourceAppraise;
			private String totalAppraise;
			private String feedbackAppraise;
			private String serviceAppraise;
			private String note;

			public void setSourceAppraise(String sourceAppraise)
			{
				this.sourceAppraise = sourceAppraise;
			}

			public void setTotalAppraise(String totalAppraise)
			{
				this.totalAppraise = totalAppraise;
			}

			public void setFeedbackAppraise(String feedbackAppraise)
			{
				this.feedbackAppraise = feedbackAppraise;
			}

			public void setServiceAppraise(String serviceAppraise)
			{
				this.serviceAppraise = serviceAppraise;
			}

			public void setNote(String note)
			{
				this.note = note;
			}

			public String getSourceAppraise()
			{
				if(TextUtils.isEmpty(sourceAppraise))
				{
					return "";
				}
				return sourceAppraise;
			}

			public String getTotalAppraise()
			{
				if(TextUtils.isEmpty(totalAppraise))
				{
					return "";
				}
				return totalAppraise;
			}

			public String getFeedbackAppraise()
			{
				if(TextUtils.isEmpty(feedbackAppraise))
				{
					return "";
				}
				return feedbackAppraise;
			}

			public String getServiceAppraise()
			{
				if(TextUtils.isEmpty(serviceAppraise))
				{
					return "";
				}
				return serviceAppraise;
			}

			public String getNote()
			{
				if(TextUtils.isEmpty(note))
				{
					return "";
				}
				return note;
			}
		}
	}
//	private Data data;
//
//	public Data getData()
//	{
//		return data;
//	}
//
//	public void setData(Data data)
//	{
//		this.data = data;
//	}
//
//	public class Data
//	{
//		private Order order;
//		private Appraise appraise;
//
//		public Appraise getAppraise()
//		{
//			return appraise;
//		}
//
//		public void setAppraise(Appraise appraise)
//		{
//			this.appraise = appraise;
//		}
//
//		public Order getOrder()
//		{
//			if (order == null)
//			{
//				return order = new Order();
//			}
//			return order;
//		}
//
//		public void setOrder(Order order)
//		{
//			this.order = order;
//		}
//
//		public class Order
//		{
//			private String appraiseStatus;// 评价状态0-未评价 1 已评价
//			private String auditDesc;// 不通过原因
//			private String auditId;// 审核人员id
//			private String auditName;// 审核人员名字
//			private String auditTime;// 审核时间
//			private String brand;// 产地/品牌
//			private String breed;// 品名
//			private String breedId;// 品种Id
//			private String buyMemberId;// 买家会员ID
//			private String buyMemberName;// 买家会员名称
//			private String buyPhone;// 买家联系电话
//			private String buyUserId;// 买家用户ID
//			private String buyUserName;// 买家用户名称
//			private String city;// 交货地
//			private String id;// ID
//			private String lastAccess;
//			private String managerId;// 管理员ID
//			private String managerName;// 管理员名称
//			private String material;// 牌号
//			private String orderSource;// 求购来源:0-网站 1-会员中心 2 手机端 3-待成交
//			private String orderTime;// 成交时间
//			private String price;// 成交价格
//			private String qty;// 成交数量
//			private String quotMemberId;// 卖家会员ID
//			private String quotMemberName;// 卖家会员名称
//			private String quotPhone;// 卖家联系电话
//			private String quotUserId;// 卖家用户ID
//			private String quotUserName;// 卖家用户名称
//			private String remitBillUrl1;// 汇款底单1
//			private String remitBillUrl2;// 汇款底单2
//			private String remitBillUrl3;// 汇款底单3
//			private String remitBillUrl4;// 汇款底单4
//			private String serialVersionUID;
//			private String source;// 成交来源 0-匹配成交 1-人工报价成交 2-买家自行录入
//			private String spec;// 规格
//			private String stanBuyId;// 求购ID
//			private String status;// 状态 0-待上传凭证 1-待审核 2-审核通过9-审核不通过99-关闭
//			private String version;
//			private String warehouse;// 仓库
//			private String managerPhoto;// 业务员头像地址
//			private String dealCount;// 次数
//			private String gapTime;// 订单距现在已经生成的时间
//			private String cancelFormateTime;// 订单取消时间
//			private String managerPhone;// 业务员联系方式
//
//			public String getManagerPhone()
//			{
//				if (TextUtils.isEmpty(managerPhone))
//				{
//					return "";
//				}
//				return managerPhone;
//			}
//
//			public void setManagerPhone(String managerPhone)
//			{
//				this.managerPhone = managerPhone;
//			}
//
//			public String getCancelFormateTime()
//			{
//				if (TextUtils.isEmpty(cancelFormateTime))
//				{
//					return "";
//				}
//				return cancelFormateTime;
//			}
//
//			public void setCancelFormateTime(String cancelFormateTime)
//			{
//				this.cancelFormateTime = cancelFormateTime;
//			}
//
//			public String getGapTime()
//			{
//				if (TextUtils.isEmpty(gapTime))
//				{
//					return "";
//				}
//				return gapTime;
//			}
//
//			public void setGapTime(String gapTime)
//			{
//				this.gapTime = gapTime;
//			}
//
//			public String getManagerPhoto()
//			{
//				if (TextUtils.isEmpty(managerPhoto))
//				{
//					return "";
//				}
//				return managerPhoto;
//			}
//
//			public void setManagerPhoto(String managerPhoto)
//			{
//				this.managerPhoto = managerPhoto;
//			}
//
//			public String getDealCount()
//			{
//				if (TextUtils.isEmpty(dealCount))
//				{
//					return "";
//				}
//				return dealCount;
//			}
//
//			public void setDealCount(String dealCount)
//			{
//				this.dealCount = dealCount;
//			}
//
//			public String getAppraiseStatus()
//			{
//				if (TextUtils.isEmpty(appraiseStatus))
//				{
//					return "";
//				}
//				return appraiseStatus;
//			}
//
//			public void setAppraiseStatus(String appraiseStatus)
//			{
//				this.appraiseStatus = appraiseStatus;
//			}
//
//			public String getAuditDesc()
//			{
//				if (TextUtils.isEmpty(auditDesc))
//				{
//					return "";
//				}
//				return auditDesc;
//			}
//
//			public void setAuditDesc(String auditDesc)
//			{
//				this.auditDesc = auditDesc;
//			}
//
//			public String getAuditId()
//			{
//				if (TextUtils.isEmpty(auditId))
//				{
//					return "";
//				}
//				return auditId;
//			}
//
//			public void setAuditId(String auditId)
//			{
//				this.auditId = auditId;
//			}
//
//			public String getAuditName()
//			{
//				if (TextUtils.isEmpty(auditName))
//				{
//					return "";
//				}
//				return auditName;
//			}
//
//			public void setAuditName(String auditName)
//			{
//				this.auditName = auditName;
//			}
//
//			public String getAuditTime()
//			{
//				if (TextUtils.isEmpty(auditTime))
//				{
//					return "";
//				}
//				return auditTime;
//			}
//
//			public void setAuditTime(String auditTime)
//			{
//				this.auditTime = auditTime;
//			}
//
//			public String getBrand()
//			{
//				if (TextUtils.isEmpty(brand))
//				{
//					return "";
//				}
//				return brand;
//			}
//
//			public void setBrand(String brand)
//			{
//				this.brand = brand;
//			}
//
//			public String getBreed()
//			{
//				if (TextUtils.isEmpty(breed))
//				{
//					return "";
//				}
//				return breed;
//			}
//
//			public void setBreed(String breed)
//			{
//				this.breed = breed;
//			}
//
//			public String getBreedId()
//			{
//				if (TextUtils.isEmpty(breedId))
//				{
//					return "";
//				}
//				return breedId;
//			}
//
//			public void setBreedId(String breedId)
//			{
//				this.breedId = breedId;
//			}
//
//			public String getBuyMemberId()
//			{
//				if (TextUtils.isEmpty(buyMemberId))
//				{
//					return "";
//				}
//				return buyMemberId;
//			}
//
//			public void setBuyMemberId(String buyMemberId)
//			{
//				this.buyMemberId = buyMemberId;
//			}
//
//			public String getBuyMemberName()
//			{
//				if (TextUtils.isEmpty(buyMemberName))
//				{
//					return "";
//				}
//				return buyMemberName;
//			}
//
//			public void setBuyMemberName(String buyMemberName)
//			{
//				this.buyMemberName = buyMemberName;
//			}
//
//			public String getBuyPhone()
//			{
//				if (TextUtils.isEmpty(buyPhone))
//				{
//					return "";
//				}
//				return buyPhone;
//			}
//
//			public void setBuyPhone(String buyPhone)
//			{
//				this.buyPhone = buyPhone;
//			}
//
//			public String getBuyUserId()
//			{
//				if (TextUtils.isEmpty(buyUserId))
//				{
//					return "";
//				}
//				return buyUserId;
//			}
//
//			public void setBuyUserId(String buyUserId)
//			{
//				this.buyUserId = buyUserId;
//			}
//
//			public String getBuyUserName()
//			{
//				if (TextUtils.isEmpty(buyUserName))
//				{
//					return "";
//				}
//				return buyUserName;
//			}
//
//			public void setBuyUserName(String buyUserName)
//			{
//				this.buyUserName = buyUserName;
//			}
//
//			public String getCity()
//			{
//				if (TextUtils.isEmpty(city))
//				{
//					return "";
//				}
//				return city;
//			}
//
//			public void setCity(String city)
//			{
//				this.city = city;
//			}
//
//			public String getId()
//			{
//				if (TextUtils.isEmpty(id))
//				{
//					return "";
//				}
//				return id;
//			}
//
//			public void setId(String id)
//			{
//				this.id = id;
//			}
//
//			public String getLastAccess()
//			{
//				if (TextUtils.isEmpty(lastAccess))
//				{
//					return "";
//				}
//				return lastAccess;
//			}
//
//			public void setLastAccess(String lastAccess)
//			{
//				this.lastAccess = lastAccess;
//			}
//
//			public String getManagerId()
//			{
//				if (TextUtils.isEmpty(managerId))
//				{
//					return "";
//				}
//				return managerId;
//			}
//
//			public void setManagerId(String managerId)
//			{
//				this.managerId = managerId;
//			}
//
//			public String getManagerName()
//			{
//				if (TextUtils.isEmpty(managerName))
//				{
//					return "";
//				}
//				return managerName;
//			}
//
//			public void setManagerName(String managerName)
//			{
//				this.managerName = managerName;
//			}
//
//			public String getMaterial()
//			{
//				if (TextUtils.isEmpty(material))
//				{
//					return "";
//				}
//				return material;
//			}
//
//			public void setMaterial(String material)
//			{
//				this.material = material;
//			}
//
//			public String getOrderSource()
//			{
//				if (TextUtils.isEmpty(orderSource))
//				{
//					return "";
//				}
//				return orderSource;
//			}
//
//			public void setOrderSource(String orderSource)
//			{
//				this.orderSource = orderSource;
//			}
//
//			public String getOrderTime()
//			{
//				if (TextUtils.isEmpty(orderTime))
//				{
//					return "";
//				}
//				return orderTime;
//			}
//
//			public void setOrderTime(String orderTime)
//			{
//				this.orderTime = orderTime;
//			}
//
//			public String getPrice()
//			{
//				if (TextUtils.isEmpty(price))
//				{
//					return "";
//				}
//				return price;
//			}
//
//			public void setPrice(String price)
//			{
//				this.price = price;
//			}
//
//			public String getQty()
//			{
//				if (TextUtils.isEmpty(qty))
//				{
//					return "";
//				}
//				return qty;
//			}
//
//			public void setQty(String qty)
//			{
//				this.qty = qty;
//			}
//
//			public String getQuotMemberId()
//			{
//				if (TextUtils.isEmpty(quotMemberId))
//				{
//					return "";
//				}
//				return quotMemberId;
//			}
//
//			public void setQuotMemberId(String quotMemberId)
//			{
//				this.quotMemberId = quotMemberId;
//			}
//
//			public String getQuotMemberName()
//			{
//				if (TextUtils.isEmpty(quotMemberName))
//				{
//					return "";
//				}
//				return quotMemberName;
//			}
//
//			public void setQuotMemberName(String quotMemberName)
//			{
//				this.quotMemberName = quotMemberName;
//			}
//
//			public String getQuotPhone()
//			{
//				if (TextUtils.isEmpty(quotPhone))
//				{
//					return "";
//				}
//				return quotPhone;
//			}
//
//			public void setQuotPhone(String quotPhone)
//			{
//				this.quotPhone = quotPhone;
//			}
//
//			public String getQuotUserId()
//			{
//				if (TextUtils.isEmpty(quotUserId))
//				{
//					return "";
//				}
//				return quotUserId;
//			}
//
//			public void setQuotUserId(String quotUserId)
//			{
//				this.quotUserId = quotUserId;
//			}
//
//			public String getQuotUserName()
//			{
//				if (TextUtils.isEmpty(quotUserName))
//				{
//					return "";
//				}
//				return quotUserName;
//			}
//
//			public void setQuotUserName(String quotUserName)
//			{
//				this.quotUserName = quotUserName;
//			}
//
//			public String getRemitBillUrl1()
//			{
//				if (TextUtils.isEmpty(remitBillUrl1))
//				{
//					return "";
//				}
//				return remitBillUrl1;
//			}
//
//			public void setRemitBillUrl1(String remitBillUrl1)
//			{
//				this.remitBillUrl1 = remitBillUrl1;
//			}
//
//			public String getRemitBillUrl2()
//			{
//				if (TextUtils.isEmpty(remitBillUrl2))
//				{
//					return "";
//				}
//				return remitBillUrl2;
//			}
//
//			public void setRemitBillUrl2(String remitBillUrl2)
//			{
//				this.remitBillUrl2 = remitBillUrl2;
//			}
//
//			public String getRemitBillUrl3()
//			{
//				if (TextUtils.isEmpty(remitBillUrl3))
//				{
//					return "";
//				}
//				return remitBillUrl3;
//			}
//
//			public void setRemitBillUrl3(String remitBillUrl3)
//			{
//				this.remitBillUrl3 = remitBillUrl3;
//			}
//
//			public String getRemitBillUrl4()
//			{
//				if (TextUtils.isEmpty(remitBillUrl4))
//				{
//					return "";
//				}
//				return remitBillUrl4;
//			}
//
//			public void setRemitBillUrl4(String remitBillUrl4)
//			{
//				this.remitBillUrl4 = remitBillUrl4;
//			}
//
//			public String getSerialVersionUID()
//			{
//				if (TextUtils.isEmpty(serialVersionUID))
//				{
//					return "";
//				}
//				return serialVersionUID;
//			}
//
//			public void setSerialVersionUID(String serialVersionUID)
//			{
//				this.serialVersionUID = serialVersionUID;
//			}
//
//			public String getSource()
//			{
//				if (TextUtils.isEmpty(source))
//				{
//					return "";
//				}
//				return source;
//			}
//
//			public void setSource(String source)
//			{
//				this.source = source;
//			}
//
//			public String getSpec()
//			{
//				if (TextUtils.isEmpty(spec))
//				{
//					return "";
//				}
//				return spec;
//			}
//
//			public void setSpec(String spec)
//			{
//				this.spec = spec;
//			}
//
//			public String getStanBuyId()
//			{
//				if (TextUtils.isEmpty(stanBuyId))
//				{
//					return "";
//				}
//				return stanBuyId;
//			}
//
//			public void setStanBuyId(String stanBuyId)
//			{
//				this.stanBuyId = stanBuyId;
//			}
//
//			public String getStatus()
//			{
//				if (TextUtils.isEmpty(status))
//				{
//					return "";
//				}
//				return status;
//			}
//
//			public void setStatus(String status)
//			{
//				this.status = status;
//			}
//
//			public String getVersion()
//			{
//				if (TextUtils.isEmpty(version))
//				{
//					return "";
//				}
//				return version;
//			}
//
//			public void setVersion(String version)
//			{
//				this.version = version;
//			}
//
//			public String getWarehouse()
//			{
//				if (TextUtils.isEmpty(warehouse))
//				{
//					return "";
//				}
//				return warehouse;
//			}
//
//			public void setWarehouse(String warehouse)
//			{
//				this.warehouse = warehouse;
//			}
//
//		}
//
//		public class Appraise
//		{
//			private String formatTime;
//			private String note;// 评价内容
//			private String serviceAppraise;
//			private String time;
//			private String timelinessAppraise;// 及时性评价分数
//			private String totalAppraise;
//
//			public String getFormatTime()
//			{
//				if (TextUtils.isEmpty(formatTime))
//				{
//					return "";
//				}
//				return formatTime;
//			}
//
//			public void setFormatTime(String formatTime)
//			{
//				this.formatTime = formatTime;
//			}
//
//			public String getNote()
//			{
//				if (TextUtils.isEmpty(note))
//				{
//					return "";
//				}
//				return note;
//			}
//
//			public void setNote(String note)
//			{
//				this.note = note;
//			}
//
//			public String getServiceAppraise()
//			{
//				if (TextUtils.isEmpty(serviceAppraise))
//				{
//					return "";
//				}
//				return serviceAppraise;
//			}
//
//			public void setServiceAppraise(String serviceAppraise)
//			{
//				this.serviceAppraise = serviceAppraise;
//			}
//
//			public String getTime()
//			{
//				if (TextUtils.isEmpty(time))
//				{
//					return "";
//				}
//				return time;
//			}
//
//			public void setTime(String time)
//			{
//				this.time = time;
//			}
//
//			public String getTimelinessAppraise()
//			{
//				if (TextUtils.isEmpty(timelinessAppraise))
//				{
//					return "";
//				}
//				return timelinessAppraise;
//			}
//
//			public void setTimelinessAppraise(String timelinessAppraise)
//			{
//				this.timelinessAppraise = timelinessAppraise;
//			}
//
//			public String getTotalAppraise()
//			{
//				if (TextUtils.isEmpty(totalAppraise))
//				{
//					return "";
//				}
//				return totalAppraise;
//			}
//
//			public void setTotalAppraise(String totalAppraise)
//			{
//				this.totalAppraise = totalAppraise;
//			}
//
//		}
//	}



}

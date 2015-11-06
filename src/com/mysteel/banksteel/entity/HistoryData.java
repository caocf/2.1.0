package com.mysteel.banksteel.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 历史求购数据实体
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-19 上午10:21:50
 */
public class HistoryData extends BaseData
{

	private static final long serialVersionUID = -6723094154181672639L;

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
		private ArrayList<FastBuy> fastBuys;
		private Pagination pagination;

		public ArrayList<FastBuy> getFastBuys()

		{
			return fastBuys;
		}

		public void setFastBuys(ArrayList<FastBuy> fastBuys)
		{
			this.fastBuys = fastBuys;
		}

		public Pagination getPagination()
		{
			return pagination;
		}

		public void setPagination(Pagination pagination)
		{
			this.pagination = pagination;
		}

		public class FastBuy
		{
			private String audioFileUrl;// 音频文件路径
			private String audioTimes;// 音频播放时长（秒）
			private String content;// 内容
			private String id;// 主键
			private String ip;// 发布IP地址
			private String lastAccess;
			private String memberId;// 会员ID
			private String phone;// 手机号码
			private String publishTime; // 发布时间
			private String resource;// 来源 0-网站 1-手机端
			private String serialVersionUID;
			private String status;// 0-未规范 1-已规范
			private String userId;// 用户ID
			private String version;

			public String getAudioFileUrl()
			{
				if (TextUtils.isEmpty(audioFileUrl))
				{
					return "";
				}
				return audioFileUrl;
			}

			public void setAudioFileUrl(String audioFileUrl)
			{
				this.audioFileUrl = audioFileUrl;
			}

			public String getAudioTimes()
			{
				if (TextUtils.isEmpty(audioTimes))
				{
					return "";
				}
				return audioTimes;
			}

			public void setAudioTimes(String audioTimes)
			{
				this.audioTimes = audioTimes;
			}

			public String getContent()
			{
				if (TextUtils.isEmpty(content))
				{
					return "";
				}
				return content;
			}

			public void setContent(String content)
			{
				this.content = content;
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

			public String getIp()
			{
				if (TextUtils.isEmpty(ip))
				{
					return "";
				}
				return ip;
			}

			public void setIp(String ip)
			{
				this.ip = ip;
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

		public class Pagination
		{
			private String page;// 页数
			private String size;// 每页的条数
			private String pagenum;// 总页数
			private ArrayList<Datas> datas;

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
				if (TextUtils.isEmpty(page))
				{
					return "";
				}
				return page;
			}

			public void setPage(String page)
			{
				this.page = page;
			}

			public String getSize()
			{
				if (TextUtils.isEmpty(size))
				{
					return "";
				}
				return size;
			}

			public void setSize(String size)
			{
				this.size = size;
			}

			public String getPagenum()
			{
				if (TextUtils.isEmpty(pagenum))
				{
					return "";
				}
				return pagenum;
			}

			public void setPagenum(String pagenum)
			{
				this.pagenum = pagenum;
			}

			@SuppressWarnings("serial")
			public class Datas implements Serializable
			{
				/** 产地品牌 */
				private String brand;
				/** 品名 */
				private String breed;
				/** 品种ID */
				private String breedId;
				/** 交货地 */
				private String city;
				/** 公司名称 */
				private String company;
				/** 联系人 */
				private String contacter;
				/** 有效期 */
				private String dueTime;
				/** ID */
				private String id;
				/** */
				private String lastAccess;
				/** 0-未要求人工服务 1-已要求人工服务 */
				private String manual;
				/** 材质 */
				private String material;
				/** 会员代码 */
				private String memberId;
				/** 备注 */
				private String note;
				/** 联系电话 */
				private String phone;
				/** 求购价格 (暂不显示) */
				private String price;
				/** 发布时间 */
				private String publishTime;
				/** 求购数量 */
				private String qty;
				/** 报价数量 */
				private String quotNum;
				/** 报价人ID号，逗号隔开 */
				private String quotUserId;
				/** 来源:0-网站快捷需求 1-会员中心 2-手机端 */
				private String resource;
				/** 抢单人Id */
				private String rushManagerId;
				/** 抢单人 */
				private String rushManagerName;
				/** 业务员联系号码 */
				private String rushManagerPhone;
				/** 抢单状态 0-待抢单 1-已抢单 */
				private String rushStatus;
				/** 抢单时间 */
				private String rushTime;
				/** */
				private String serialVersionUID;
				/** 规格 */
				private String spec;
				/** 状态 0-待报价 1-已报价 2-已成交 9-终止 */
				private String status;
				/** 用户ID */
				private String userId;
				/** */
				private String version;
				/** 用来自己判断页面跳转状态 0:待匹配 ，1：待抢单，2：待报价，3：已取消 */
				private int skipStatus = -1;
				/** 管理员头像 */
				private String rushManagerHeader;
				/** 管理员交易次数 */
				private String dealCount;
				/** 求购已发布的时间 */
				private String gapTime;
				/** 0 求购单有效 1求购单过期 */
				private String dueStatus;

				/**
				 * 由于数据格式的问题，不得不把语音中fastBuy的结构包含进来，方便在MyBuyAdapter中使用同一个数据结构
				 * 注意，下面被注释的字段是已有的字段，而 MyBuyAdapter中只需要判断audioFileUrl（音频文件路径）有无
				 * 或者是下面任意一个上面data中没有的字段 ，都可以判断是语音的布局还是其他求购状态的布局
				 * */
				private String audioFileUrl;// 音频文件路径
				private String audioTimes;// 音频播放时长（秒）
				private String content;// 内容
				// private String id;// 主键
				private String ip;// 发布IP地址
				// private String lastAccess;
				// private String memberId;// 会员ID
				// private String phone;// 手机号码
				// private String publishTime; // 发布时间
				// private String resource;// 来源 0-网站 1-手机端
				// private String serialVersionUID;
				// private String status;// 0-未规范 1-已规范
				// private String userId;// 用户ID
				// private String version;

				/**************************************/

				public String getDueStatus()
				{
					if (TextUtils.isEmpty(dueStatus))
					{
						return "";
					}
					return dueStatus;
				}

				public String getAudioFileUrl()
				{
					if (TextUtils.isEmpty(audioFileUrl))
					{
						return "";
					}
					return audioFileUrl;
				}

				public void setAudioFileUrl(String audioFileUrl)
				{
					this.audioFileUrl = audioFileUrl;
				}

				public String getAudioTimes()
				{
					if (TextUtils.isEmpty(audioTimes))
					{
						return "";
					}
					return audioTimes;
				}

				public void setAudioTimes(String audioTimes)
				{
					this.audioTimes = audioTimes;
				}

				public String getContent()
				{
					if (TextUtils.isEmpty(content))
					{
						return "";
					}
					return content;
				}

				public void setContent(String content)
				{
					this.content = content;
				}

				public String getIp()
				{
					if (TextUtils.isEmpty(ip))
					{
						return "";
					}
					return ip;
				}

				public void setIp(String ip)
				{
					this.ip = ip;
				}

				public void setDueStatus(String dueStatus)
				{

					this.dueStatus = dueStatus;
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

				public String getRushManagerHeader()
				{
					if (TextUtils.isEmpty(rushManagerHeader))
					{
						return "";
					}
					return rushManagerHeader;
				}

				public void setRushManagerHeader(String rushManagerHeader)
				{
					this.rushManagerHeader = rushManagerHeader;
				}

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
}

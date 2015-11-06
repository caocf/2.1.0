package com.mysteel.banksteel.util;

import java.io.File;


/**
 * 静态常量值的存储类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-6 下午3:21:09
 */
public class Constants
{

	public static final String IMAGE_DATA = "imageData"; // 从SD卡扫描的图片地址
	public static final String SELECT_IMAGE_COUNT = "count"; // 选中的图片数量
	public static final String NEXT_SCREEN_TAG = "screenTag"; // 当多个页面共用一个activity时，该常量表示到达哪一个页面
	public static final String ACTION_HUMANSERVE = "action.humanserve";// 人工抢单界面广播的action
	public static final String ACTION_MATCHFINDGOOD = "action.matchfindgood";// 匹配找货界面广播的action

	/**
	 * SharedPreference
	 */
	public static final String USER_SHARED_PREFERENCES = "com.mysteel.banksteeltwo.userSession";// 全局缓存key
	public static final String PREFERENCES_DOMAIN = "com.mysteel.banksteeltwo.domain";// 域名的key
	public static final String PREFERENCES_CELLPHONE = "com.mysteel.banksteeltwo.cellphone";// 电话号码的key
	public static final String PREFERENCES_CODE = "com.mysteel.banksteeltwo.code";// 验证码的key
	public static final String PREFERENCES_USERID = "com.mysteel.banksteeltwo.userid"; // 用户id
	public static final String USER_PASSWARD = "com.mysteel.banksteeltwo.passward";// 密码
	public static final String USER_TOKEN = "com.mysteel.banksteeltwo.token";// token
	public static final String USER_NAME = "com.mysteel.banksteeltwo.name";// 姓名
	public static final String USER_USERNAME = "com.mysteel.banksteeltwo.username";// 用户姓名
	public static final String USER_MEMBER_NAME = "com.mysteel.banksteeltwo.membername";// MemberName(会员名/公司名)
	public static final String PREFERENCES_WELCOME_FALST = "com.mysteel.banksteeltwo.welcome";// 首次进来的欢迎页

	public static final String USER_MEMBERID = "com.mysteel.banksteeltwo.usermemberId";// 用户MEMBERID
	public static final String USER_SCORE = "com.mysteel.banksteeltwo.userscore";// 用户积分
	public static final String USER_SEX = "com.mysteel.banksteeltwo.usersex";// 性别
	public static final String USER_PROVINCE = "com.mysteel.banksteeltwo.userprovince";// 个人所在省
	public static final String USER_CITY = "com.mysteel.banksteeltwo.usercity";// 个人所在市
	public static final String USER_COUNTY = "com.mysteel.banksteeltwo.usercounty";// 个人所在县/区
	public static final String USER_MEMBER_TYPE = "com.mysteel.banksteeltwo.user.membertype";// 公司性质
	public static final String USER_MEMBER_PROVINCE = "com.mysteel.banksteeltwo.user.memberprovince";// 公司所在省
	public static final String USER_MEMBER_CITY = "com.mysteel.banksteeltwo.user.membercity";// 公司所在市
	public static final String USER_MEMBER_COUNTY = "com.mysteel.banksteeltwo.user.membercounty";// 公司所在县/区
	public static final String USER_IS_LOGIN = "com.mysteel.banksteeltwo.user.islogin";// 用户是否登录
	public static final String USER_PHTHO = "com.mysteel.banksteeltwo.user.photo";// 网络拉取用户头像的路径
	
	public static final String USER_SETTING_FLAG = "com.mysteel.banksteeltwo.user.flag";// 用户是否设置过提醒选择
	public static final String USER_SETTING_PINZHONG = "com.mysteel.banksteeltwo.user.varieties";// 用户选择的品类名称
	public static final String USER_SETTING_CITYS = "com.mysteel.banksteeltwo.user.citys";// 用户选择的三个城市名

	public static final String USER_SETTING_PINZHONG_DETAIL = "com.mysteel.banksteeltwo.user.varieties.detail";// 用户选择的品类名称+CODE
	public static final String USER_SETTING_CITYS_DETAIL = "com.mysteel.banksteeltwo.user.citys.detail";// 用户选择的三个城市名+CODE

	public static final String USER_SEARCH_HISTORY = "com.mysteel.banksteeltwo.user.searchhistory";// 用户搜索的历史记录
	public static final String PREFERENCES_HUANXIN_FALST = "com.mysteel.banksteeltwo.huanxin";// 首次进来 环信的登录

	public static final String USER_SETTING_LOCAL_ZHONGLEI = "com.mysteel.banksteeltwo.user.local_zhonglei";// 同城中用户选择的三个种类
	
	// public static final String USER_IMAGE_CACHE =
	// "com.mysteel.banksteeltwo.user.imagecache";// sd卡图片路径
	public static final String IS_PUSH = "com.mysteel.banksteeltwo.ispush";// 是否推送

	/** ActivityForResult请求码 */
	public static final int IMAGE_REQUEST_CODE = 10000;// 相册
	public static final int CAMERA_REQUEST_CODE = 10001;// 相机
	public static final int RESULT_REQUEST_CODE = 10002;// 剪辑相片后结果

	public static final int PERSONAL_ADDRESS_REQUEST_CODE = 10003;// 个人信息地址请求
	public static final int COMPANY_ADDRESS_REQUEST_CODE = 10004;// 公司信息地址请求
	public static final int ADD_ADDRESS_REQUEST_CODE = 10005;// 编辑地址页面地址请求

	public static final String ADD_ADDRESS = "add_address";// 添加地址
	public static final String EDIT_ADDRESS = "edit_address";// 编辑地址
	public static final String STEEL_CIRCLE = "steel_circle";// 钢圈
	public static final String TRANSACTION_RULE = "transaction_rule";// 交易规则
	public static final String HOMEPAGE = "home_page";// 消息中心最后一条数据跳转至www.banksteel.com
	public static final String JUMP_COMPANY_INFO = "jump_company_info";// 跳转到公司信息

	/** 剪切图片后图片的路径 */
	public static final String IMAGE_PATH = "/banksteel/banksteelImage/image/image.jpg";
	
	public static final String FileName = File.separator + "image.jpg";
	/** 上传成功后当前头像路径 */
	public static final String AVATAR_PATH = "/banksteel/banksteelImage/avatar" + FileName;
	/** 下载录音文件后保存的文件夹地址 */
	public static final String VOICE_FOLDER = "/banksteel/banksteelvoice";
	/** 录音保存文件夹名 */
	public static final String SOUND_FOLDER = "/banksteel/bankSteelSound/";

	/** 1.1登陆 */
	public static final String INTERFACE_Login = "user.login";
	/** 1.16获取好友资料及评价 */
	public static final String INTERFACE_FriendMaterial = "user.friendMaterial";
	/** 1.17获取业务范围 */
	public static final String INTERFACE_UserBusinessScopes = "user.userBusinessScopes";
	/** 1.18更改业务区域、业务范围、会员类型 */
	public static final String INTERFACE_ModifyMemberBusinessInfo = "user.modifyMemberBusinessInfo";
	/** 1.2 退出登录 */
	public static final String INTERFACE_Loginout = "user.loginOut";
	/** 1.3发送手机验证码 */
	public static final String INTERFACE_sendSmsValidate = "user.sendSmsValidate";
	/** 1.4短信验证码验证 */
	public static final String INTERFACE_validateSmsCode = "user.validateSmsCode";
	/** 1.5设置新密码 */
	public static final String INTERFACE_setNewPasswordByPhone = "user.setNewPasswordByPhone";
	/** 1.6重置密码 */
	public static final String INTERFACE_resetPassword = "user.resetPassword";
	/** 1.7注册 */
	public static final String INTERFACE_register = "user.register";
	/** 1.8上传头像 */
	public static final String INTERFACE_uploadHeader = "user.uploadHeader";
	/** 1.9修改个人信息 */
	public static final String INTERFACE_editUserInfo = "user.editUserInfo";
	/** 1.10修改公司信息 */
	public static final String INTERFACE_editMemberInfo = "user.editMemberInfo";
	/** 1.13修改绑定的手机号 */
	public static final String INTERFACE_updateBindMobile = "user.updateBindMobile";
	/** 1.14用户信息统计(user.userDetailsCount)*/
	public static final String INTERFACE_userDetailsCount = "user.userDetailsCount";
	/** 1.15注册环信（hxuser.register）*/
	public static final String INTERFACE_hxuserregister = "hxuser.register";
	/** 1.20手机号码获取用户信息（user.friendIntroduce）*/
	public static final String INTERFACE_friendIntroduce = "user.friendIntroduce";


	/** 2.1收货地址列表 */
	public static final String INTERFACE_addressList = "address.addressList";
	/** 2.2添加收货地址 */
	public static final String INTERFACE_addAddress = "address.addAddress";
	/** 2.3查找一条收货地址 */
	public static final String INTERFACE_findAddressById = "address.findAddressById";
	/** 2.4编辑收货地址 */
	public static final String INTERFACE_editAddress = "address.editAddress";
	/** 2.5设置默认收货地址 */
	public static final String INTERFACE_setDefaultAddress = "address.setDefaultAddress";
	/** 2.6删除收货地址 */
	public static final String INTERFACE_delAddress = "address.delAddress";

	/** 3.1赚取积分 */
	public static final String INTERFACE_earnScore = "score.earnScore";
	/** 3.2查看积分 */
	public static final String INTERFACE_searchMyScore = "score.searchMyScore";
	/** 3.3积分兑换列表 */
	public static final String INTERFACE_pageGift = "score.pageGift";
	/** 3.4提交积分订单 */
	public static final String INTERFACE_submitScoreOrder = "score.submitScoreOrder";
	/** 3.5积分兑换订单列表 */
	public static final String INTERFACE_pageScoreOrder = "score.pageScoreOrder";
	/** 3.6 历史积分记录 */
	public static final String INTERFACE_pageHistoryScoreLog = "score.pageHistoryScoreLog";

	/** 4.1 获取子品种 */
	public static final String INTERFACE_queryChildBreedsByParentId = "buy.queryChildBreedsByParentId";
	/** 4.2 获取城市 */
	public static final String INTERFACE_getCitys = "buy.getCitys";
	/** 4.3 热门城市 */
	public static final String INTERFACE_hotCity = "buy.hotCity";
	/** 4.4 根据品种ID获取规格 */
	public static final String INTERFACE_getSpecsByBreedId = "buy.getSpecsByBreedId";
	/** 4.5 根据品种ID获取材质 */
	public static final String INTERFACE_getMaterialByBreedId = "buy.getMaterialByBreedId";
	/** 4.6 根据品种ID获取钢厂 */
	public static final String INTERFACE_getFactorysByBreedId = "buy.getFactorysByBreedId";
	/** 4.7发布标准求购 */
	public static final String INTERFACE_publishStanBuy = "buy.publishStanBuy";
	/** 4.8 发布快捷求购 */
	public static final String INTERFACE_publishFastBuy = "buy.publishFastBuy";
	/** 4.9 发布语音求购 */
	public static final String INTERFACE_voicePublish = "buy.voicePublish";
	/** 4.10 历史求购单 */
	public static final String INTERFACE_historyStanBuy = "buy.historyStanBuy";
	/** 4.11 取消求购单 */
	public static final String INTERFACE_cancelStanBuy = "buy.cancelStanBuy";
	/** 4.12 未取消求购单 */
	public static final String INTERFACE_uncancelStanBuy = "buy.uncancelStanBuy";
	/** 4.13 求购单详情 */
	public static final String INTERFACE_detailStanBuy = "buy.detailStanBuy";
	/** 4.14 取消快件找货求购单 */
	public static final String INTERFACE_cancelFastBuy = "buy.cancelFastBuy";
	/** 4.15自定义求购列表 */
	public static final String INTERFACE_customList = "buy.customList";

	/** 5.1 资源匹配 */
	public static final String INTERFACE_matchBuy = "deal.matchBuy";
	/** 5.2 人工抢单 */
	public static final String INTERFACE_humanServe = "deal.humanServe";
	/** 5.3 匹配提交撮合订单 */
	public static final String INTERFACE_createOrder = "deal.createOrder";
	/** 5.4 上传凭证 */
	public static final String INTERFACE_uploadCert = "deal.uploadCert";
	/** 5.5 订单列表 */
	public static final String INTERFACE_pageOrder = "deal.pageOrder";
	/** 5.6 订单详情 */
	public static final String INTERFACE_orderDetail = "deal.orderDetail";
	/** 5.7 修改订单 */
	public static final String INTERFACE_editOrder = "deal.editOrder";
	/** 5.8 订单审核未通过数量及aa订单待上传凭证数量 */
	public static final String INTERFACE_orderCount = "deal.orderCount";

	/** 5.9资源匹配 */
	public static final String INTERFACE_matchResource = "deal.matchResource";
	/** 5.17 撮合订单 */
	public static final String INTERFACE_consultOrder = "deal.consultOrder";
	/** 6.0 资源搜索 */
	public static final String INTERFACE_searchResource = "deal.searchResource";
	/** 6.13 创建意向订单 */
	public static final String INTERFACE_createResourceOrder = "deal.createResourceOrder";
	/** 6.15指定时间区间钢材价格趋势 */
	public static final String INTERFACE_createPriceTrend = "deal.getPriceTrend";
	/** 6.1 自动匹配评价订单即评价卖家 */
	public static final String INTERFACE_evaluateSeller = "eval.evaluateSeller";
	/** 6.2 人工服务评价管理员 */
	public static final String INTERFACE_evaluateManager = "eval.evaluateManager";
	/** 6.3 获取所有评价业务员记录 */
	public static final String INTERFACE_pageEvalManager = "eval.pageEvalManager";
	/** 6.4 钢圈列表页面 */
	public static final String INTERFACE_pageEval = "eval.pageEval";

	/** 7.1关于我们 */
	public static final String INTERFACE_aboutUs = "sys.aboutUs";
	/** 7.2 使用帮助 */
	public static final String INTERFACE_help = "sys.help";
	/** 7.3 检查更新 */
	public static final String INTERFACE_checkUpdate = "sys.checkUpdate";
	/** 7.4 签到送积分 */
	public static final String INTERFACE_sign = "sys.sign";
	/** 7.5 反馈意见 */
	public static final String INTERFACE_suggest = "sys.suggest";
	/** 7.6是否推送 */
	public static final String INTERFACE_push = "sys.push";
	/** 7.7 历史消息 */
	public static final String INTERFACE_pageHistoryMessage = "sys.pageHistoryMessage";
	/** 7.8平台交易规则 */
	public static final String INTERFACE_rule = "sys.rule";
	/** 7.9 删除消息 */
	public static final String INTERFACE_deleteMessage = "sys.deleteMessage";
	/** 8.1 根据ID调取广告位 */
	public static final String INTERFACE_getAdvertById = "advert.getAdvertById";
	/** 9.1 求购报价详情 */
	public static final String INTERFACE_stanQuotDetails = "quot.stanQuotDetails";
	/** 9.2 我的报价列表 */
	public static final String INTERFACE_myQuot = "quot.myQuot";
	/** 9.3 我要报价 */
	public static final String INTERFACE_sellQuot = "quot.sellQuot";
	/** 11.2获取子地区 */
	public static final String INTERFACE_querySubAreaByCode = "area.querySubAreaByCode";
	/** 物流获取货物选择 */
	public static final String INTERFACE_queryTransportBreed = "transport.queryTransportBreed";
	/** 10.1查询报价 */
	public static final String INTERFACE_createTransportQuot = "transport.transportQuot";
	/** 10.2创建物流订单 */
	public static final String INTERFACE_createTransportOrder = "transport.createTransportOrder";
	/** 10.3查看物流状态 */
	public static final String INTERFACE_transportDetails = "transport.transportDetails";
	/** 10.4人工服务 */
	public static final String INTERFACE_customerCare = "transport.customerCare";
	/** 10.5物流评价 */
	public static final String INTERFACE_transportAppraise = "transport.transportAppraise";
	/** 10.7我的物流订单（transport.getOrderByUserId）*/
	public static final String INTERFACE_getOrderByUserId = "transport.getOrderByUserId";
	/** 6.1获取指定时间区间资源总数（deal.todayResourceCount）*/
	public static final String INTERFACE_todayResourceCount = "deal.todayResourceCount";
}

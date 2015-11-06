package com.mysteel.banksteel.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import java.util.Map;

/**
 * 请求地址配置类
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 下午1:46:38
 */
public class RequestUrl
{

    /**
     * singleton
     */
    private static RequestUrl sIntance;

    /** http 请求的域,注意，所有页面必须使用这一个常量 */
    /**
     * 邀请好友的Url地址
     */
    public static final String YAOQING = "http://t.cn/RzelOog";

    /**
     * api.assistant.banksteel.com 手机请求域名 quan.assistant.banksteel.com 圈子页面域名
     */
    // private static String DOMAIN =
    // "http://assistant.banksteel.com/assistant_spi";
    public static String DOMAIN_WEB = "http://www.banksteel.com";// 192.168.30.48:8080
//    public static String DOMAIN = "http://api2.assistant.banksteel.com";// http://192.168.100.115:8180 api2.assistant.banksteel.com
//    public static String DOMAIN2 = "http://192.168.100.115:8180"; //http://180.166.92.235:8280
    public static String DOMAIN = "http://192.168.100.115:8180"; //http://180.166.92.235:8280
    public static String httpUrl = "/assistant_spi";
    public static String uploadVoiceUrl = "/assistantFile_spi";
    public static String uploadImgUrl = "/assistantPhoto_spi";

    //    public static final String SMS_YAOQING = DOMAIN + "/index.htm";
    public static final String LogisticsURL = "http://www.bejoysoft.com/ttlp/getProductType.htm";

    public static final String YaoQing = DOMAIN + "/share/index.htm";

    /**
     * 功能介绍的url地址
     */
    public static final String FUNCTION_INTRODUCE = DOMAIN + "/intro.htm";
    /***/
    // public static String jifenURL = "http://192.168.100.118:8081";
    /**
     * 钢圈域名
     */
    //public static final String CIRCLE_DOMIN = "http://quan.assistant.banksteel.com/web/pageEval.html";// DOMAIN
    public static final String CIRCLE_DOMIN = DOMAIN + "/web/pageEvalResource.html";
    // +
    /**
     * 关于我们域名
     */
    public static final String ABOUTUS_DOMIN = DOMAIN + "/aboutus.htm";
    /**
     * 刚银电子商务网络服务协议
     */
    public static final String RULE_DOMIN = DOMAIN_WEB
            + "/help/sevrice_rule.html";
    /**
     * 积分兑换分享的链接
     */
    public static String JIFEN_SHARE = "http://www.banksteel.com/jifen/detail_m.html?gid=";

    private Context context;

    private static final String version ="5.0.0";

    private RequestUrl(Context context)
    {
        this.context = context;
        SharePreferenceUtil.initializeInstance(context);
        SharePreferenceUtil.getInstance().setDomain(context, DOMAIN);
    }

    /**
     * get instance.
     */
    public static RequestUrl getInstance(Context context)
    {
        if (null == sIntance)
        {
            synchronized (RequestUrl.class)
            {
                sIntance = new RequestUrl(context);
            }
        }
        return sIntance;
    }

    /**
     * 获取domain
     *
     * @return
     */
    public String getDomain(Context context)
    {
        String domain = SharePreferenceUtil.getInstance().getDomain(context);
        if (!TextUtils.isEmpty(domain))
        {
            return domain;
        }
        return DOMAIN;
    }

    public String getUploadImgUrl()
    {
        return uploadImgUrl;
    }

    /**
     * 1.3 发送手机验证码url
     *
     * @param phone
     *            电话号码
     * @return url
     */
    /*
     * public String getUrl_sendSmsValidate(String phone) { String url = DOMAIN
	 * + "?phone=" + phone + "&cmd=user.sendSmsValidate&" + "&timestamp=" +
	 * System.currentTimeMillis() + Tools.getAndroidMsg(context) + Constants.OS_TYPE_1 + "&codetype=1"; return
	 * url; }
	 */

    /**
     * 1.1 登陆
     *
     * @param phone
     * @param passwd
     * @return
     */
    public String getUrl_login(Context context, String phone, String passwd)
    {
        String machineCode = Tools.getDeviceInfo(context);// 手机的机器码
        String token = SharePreferenceUtil.getString(context,
                Constants.USER_TOKEN);// 本机的识别号
        return getDomain(context) + httpUrl + "?phone=" + phone + "&passwd="
                + passwd + "&cmd=user.login&ostype=1&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&machineCode=" + machineCode
                + "&token=" + token + "&softVersion="
                + Tools.getVersion(context);
    }

    /**
     * 1.16获取好友资料及评价（user.friendMaterial）
     *
     * @return url
     */
    public String getUrl_friendMaterial(Context context, String friendId, String friendPhone, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?"
                + "cmd=user.friendMaterial&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1&page=" + page + "&size=" + size;
        if (!TextUtils.isEmpty(userId))
        {
            url += "&userId=" + userId;
        }
        if (!TextUtils.isEmpty(friendId))
        {
            url += "&friendId=" + friendId;
        }
        if (!TextUtils.isEmpty(friendPhone))
        {
            url += "&friendPhone=" + friendPhone;
        }
        return url;
    }

    /**
     * 1.17获取业务范围（user.userBusinessScopes）
     *
     * @param context
     * @param memberType
     * @return
     */
    public String getUrl_businessScopes(Context context, String memberType)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=user.userBusinessScopes&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1&memberType=" + memberType;
        return url;
    }

    /**
     * 1.18更改业务区域、业务范围、会员类型（user.modifyMemberBusinessInfo）
     *
     * @param context
     * @return
     */
    public String getUrl_modifyMemberBusinessInfo(Context context, Map<String, String> map)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=user.modifyMemberBusinessInfo&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (!TextUtils.isEmpty(entry.getValue()))
            {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    /**
     * 1.2退出登录
     *
     * @return url
     */
    public String getUrl_loginOut(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=user.loginOut&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1";
        return url;
    }

    /**
     * 1.3 发送手机验证码url
     *
     * @param phone    电话号码
     * @param codetype 验证码类型(1-注册验证码提示 2-找回密码提示 3-修改绑定手机号)
     * @return url
     */
    public String getUrl_sendSmsValidate(String userId, String phone,
                                         String codetype)
    {
        String url = "";
        if (!TextUtils.isEmpty(userId))
        {
            url = DOMAIN + httpUrl + "?userId=" + userId + "&phone=" + phone
                    + "&cmd=user.sendSmsValidate" + "&timestamp="
                    + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" + "&codetype="
                    + codetype;
        } else
        {
            url = DOMAIN + httpUrl + "?phone=" + phone
                    + "&cmd=user.sendSmsValidate" + "&timestamp="
                    + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" + "&codetype="
                    + codetype;
        }
        return url;
    }

    /**
     * 1.4短信验证码验证
     *
     * @param phone
     * @param code
     * @return url
     */
    public String getUrl_validateSmsCode(String userId, String phone,
                                         String code)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&phone=" + phone
                + "&cmd=user.validateSmsCode&code=" + code + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 1.5设置新密码(忘记密码时)
     *
     * @param phone           电话号码
     * @param code            验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     * @return url
     */
    public String getUrl_setNewPasswordByPhone(String phone, String code,
                                               String password, String confirmPassword)
    {
        String url = DOMAIN + httpUrl + "?phone=" + phone + "&code=" + code
                + "&cmd=user.setNewPasswordByPhone&password=" + password
                + "&confirmPassword=" + confirmPassword + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 1.6 重置密码
     *
     * @param userId          用户id
     * @param oldPassword     旧密码
     * @param password        新密码
     * @param confirmPassword 确认新密码
     * @return url
     */
    public String getUrl_resetPassword(String userId, String oldPassword,
                                       String password, String confirmPassword)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&oldPassword="
                + oldPassword + "&password=" + password + "&confirmPassword="
                + confirmPassword + "&cmd=user.resetPassword&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 1.7注册请求的url
     *
     * @param phone    电话号码
     * @param password 密码
     * @param code     验证码
     * @return
     */
    public String getUrl_register(String phone, String password, String code)
    {
        String url = DOMAIN + httpUrl + "?phone=" + phone + "&password="
                + password + "&confirmPassword=" + password
                + "&cmd=user.register&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1&code=" + code;
        return url;
    }

    /**
     * 1.8上传头像
     *
     * @param userId 用户id
     * @return
     */
    public String getUrl_uploadHeader(String userId)
    {

        String url = DOMAIN + uploadImgUrl + "?userId=" + userId
                + "&cmd=user.uploadHeader&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 1.9 修改个人信息
     *
     * @param userId 用户id
     * @param name   昵称
     * @param sex    称谓/性别
     * @param state  省
     * @param city   城市
     * @param area   区
     * @return url
     */
    public String getUrl_editUserInfo(String userId, String name, String sex,
                                      String state, String city, String area)
    {

        String url = DOMAIN + httpUrl + "?userId=" + userId + "&name=" + name
                + "&sex=" + sex + "&state=" + state + "&city=" + city
                + "&area=" + area + "&cmd=user.editUserInfo&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 1.10:修改公司信息
     *
     * @param memberId   公司id
     * @param memberName 公司名称
     * @param state      省
     * @param city       市
     * @param area       区
     * @param type       公司性质
     * @param userId     用户id
     * @return url
     */
    public String getUrl_editMemberInfo(String memberId, String memberName,
                                        String state, String city, String area, String type, String userId)
    {

        String url = DOMAIN + httpUrl + "?memberId=" + memberId
                + "&memberName=" + memberName + "&state=" + state + "&city="
                + city + "&area=" + area + "&type=" + type
                + "&cmd=user.editMemberInfo&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1&userId=" + userId;
        return url;
    }

    /**
     * 1.13修改绑定的手机号(user.updateBindMobile)
     *
     * @param context   上下文
     * @param mobile    新手机号
     * @param code      验证码
     * @param password  密码
     * @param oldmobile 旧手机号
     * @return url
     */
    public String getUrl_updateBindMobile(Context context, String mobile,
                                          String code, String password, String oldmobile)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&mobile="
                + mobile + "&code=" + code + "&password=" + password
                + "&oldmobile=" + oldmobile
                + "&ostype=1&cmd=user.updateBindMobile&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }

    /**
     * 1.14用户信息统计(user.userDetailsCount)
     *
     * @param context
     * @param citys
     * @return
     */
    public String getUrl_UserDetailsCount(Context context, String citys, String catalogs)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&citys=" + citys
                + "&ostype=1&cmd=user.userDetailsCount&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        if(TextUtils.isEmpty(catalogs)){
            url += "&catalogs=" + catalogs;
        }
        return url;
    }

    /**
     * 1.15注册环信（hxuser.register）
     *
     * @param context
     * @param phone
     * @param source
     * @return
     */
    public String getUrl_Register(Context context, String phone, String source)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&phone=" + phone + "&source=" + source
                + "&ostype=1&cmd=hxuser.register&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }


    /**
     * 1.20手机号码获取用户信息（user.friendIntroduce）
     *
     * @param context
     * @return
     */
    public String getUrl_MoreUserInfo(Context context, String phones)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&phone=" + phones
                + "&ostype=1&cmd=user.friendIntroduce&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }


    /**
     * 2.1:收货地址列表(address.addressList)
     *
     * @param userId 用户id
     * @return url
     */
    public String getUrl_addressList(String userId)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=address.addressList&timestamp="
                + +System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 2.2添加收货地址
     *
     * @param context
     * @param contact   收货人
     * @param phone     联系电话
     * @param province  省
     * @param city      市
     * @param district  区
     * @param postcode  邮编
     * @param address   详细地址
     * @param isDefault 是否设置为默认 1- 默认地址 0-其他地址
     * @return url
     */
    public String getUrl_addAddress(Context context, String contact,
                                    String phone, String province, String city, String district,
                                    String postcode, String address, String isDefault)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId

        return getDomain(context) + httpUrl + "?userId=" + userId + "&contact="
                + contact + "&phone=" + phone + "&province=" + province
                + "&city=" + city + "&district=" + district + "&postcode="
                + postcode + "&address=" + address + "&isDefault=" + isDefault
                + "&cmd=address.addAddress&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 2.4编辑收货地址
     *
     * @param context
     * @param contact
     * @param addressId
     * @param phone
     * @param province
     * @param city
     * @param district
     * @param postcode
     * @param address
     * @param isDefault
     * @return
     */
    public String getUrl_editAddress(Context context, String contact,
                                     String addressId, String phone, String province, String city,
                                     String district, String postcode, String address, String isDefault)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId

        return getDomain(context) + httpUrl + "?userId=" + userId + "&contact="
                + contact + "&phone=" + phone + "&province=" + province
                + "&city=" + city + "&addressId=" + addressId + "&district="
                + district + "&postcode=" + postcode + "&address=" + address
                + "&isDefault=" + isDefault
                + "&cmd=address.editAddress&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 2.5设置默认收货地址(address.setDefaultAddress)
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return url
     */
    public String getUrl_setDefaultAddress(String userId, String addressId)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&addressId="
                + addressId + "&cmd=address.setDefaultAddress&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 2.6删除收货地址(address.delAddress)
     *
     * @param addressId 地址id
     * @return url
     */
    public String getUrl_delAddress(Context context, String addressId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&addressId="
                + addressId + "&cmd=address.delAddress&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 3.1赚取积分(score.earnScore)
     *
     * @param userId 用户Id
     * @param score  获取的积分数
     * @return url
     */
//    public String getUrl_earnScore(String userId, String score)
//    {
//        String url = DOMAIN + httpUrl + "?userId=" + userId + "&score=" + score
//                + "&note=分享&cmd=score.earnScore&ostype=1&timestamp="
//                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
//        return url;
//    }

    /**
     * 3.2查看积分(score.searchMyScore)
     *
     * @param context 上下文
     * @return url
     */
    public String getUrl_searchMyScore(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=score.searchMyScore&ostype=1&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }

    /**
     * 3.3积分兑换列表
     *
     * @param context
     * @param page
     * @param size
     * @return
     */
    public String getUrl_pageGift(Context context, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?userId=" + userId + "&page="
                + page + "&size=" + size + "&ostype=1"
                + "&cmd=score.pageGift&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context);
    }

    /**
     * 3.4提交积分订单(score.submitScoreOrder)
     *
     * @param memberId  会员Id
     * @param userId    用户Id
     * @param goods     货物(商品id-数量 (1234-23)
     * @param addressId 地址id
     * @param note      备注
     * @return url
     */
    public String getUrl_submitScoreOrder(String memberId, String userId,
                                          String goods, String addressId, String note)
    {
        String url = DOMAIN + httpUrl + "?memberId=" + memberId + "&userId="
                + userId + "&goods=" + goods + "&addressId=" + addressId
                + "&note=" + note
                + "&cmd=score.submitScoreOrder&ostype=1&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }

    /**
     * 3.5积分兑换订单列表(已兑换)
     *
     * @param context
     * @param page
     * @param size
     * @return
     */
    public String getUrl_pageScoreOrder(Context context, String page,
                                        String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId
        return getDomain(context) + httpUrl + "?page=" + page + "&size=" + size
                + "&memberId=" + memberId + "&userId=" + userId
                + "&cmd=score.pageScoreOrder&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 3.6 历史积分记录
     *
     * @param page
     * @param size
     * @return
     */
    public String getUrl_pageHistoryScoreLog(Context context, String page,
                                             String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId
        return getDomain(context) + httpUrl + "?memberId=" + memberId
                + "&userId=" + userId + "&page=" + page + "&size=" + size
                + "&cmd=score.pageHistoryScoreLog" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.1 获取子品种(buy.queryChildBreedsByParentId)
     *
     * @param context
     * @param breedId
     * @return
     */
    public String getUrl_queryChildBreedsByParentId(Context context,
                                                    String breedId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?userId=" + userId + "&breedId="
                + breedId + "&cmd=buy.queryChildBreedsByParentId"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.2 获取城市(buy.getCitys)
     *
     * @param context
     * @return
     */
    public String getUrl_getCitys(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&cmd=buy.getCitys" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.4 根据品种ID获取规格(buy.getSpecsByBreedId)
     *
     * @param context
     * @param breedId
     * @return
     */
    public String getUrl_getSpecsByBreedId(Context context, String breedId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId + "&breedId="
                + breedId + "&cmd=buy.getSpecsByBreedId" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.5 根据品种ID获取材质(buy.getMaterialByBreedId)
     *
     * @param context
     * @param breedId
     * @return
     */
    public String getUrl_getMaterialByBreedId(Context context, String breedId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId + "&breedId="
                + breedId + "&cmd=buy.getMaterialByBreedId" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.6 根据品种ID获取钢厂(buy.getFactorysByBreedId)
     *
     * @param context
     * @param breedId 品种id
     * @return
     */
    public String getUrl_getFactorysByBreedId(Context context, String breedId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId + "&breedId="
                + breedId + "&cmd=buy.getFactorysByBreedId" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.7发布标准求购(buy.publishStanBuy)
     *
     * @param context
     * @param categoryId 品种ID
     * @param category   品名
     * @param material   材质
     * @param spec       规格
     * @param brand      厂家
     * @param city       城市
     * @param qty        求购数量
     * @param contacter  联系人
     * @param phone      联系电话
     * @param resource   来源:0-网站快捷需求 1-会员中心 2-手机端
     * @param company    公司名称
     * @param validTime  有效期
     * @param note   	   备注
     * @return
     */
    public String getUrl_publishStanBuy(Context context, String categoryId,
                                        String category, String material, String spec, String brand,
                                        String city, String qty, String contacter, String phone,
                                        String resource, String company, String validTime,String note)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&memberId=" + memberId + "&categoryId=" + categoryId
                + "&category=" + category + "&material=" + material + "&spec="
                + spec + "&brand=" + brand + "&city=" + city + "&qty=" + qty 
                + "&contacter=" + contacter + "&phone=" + phone + "&resource=" + "&note=" + note
                + resource + "&validTime=" + validTime + "&company=" + company + "&cmd=buy.publishStanBuy"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.8 发布快捷求购(buy.publishFastBuy)
     *
     * @param phone    电话
     * @param content  找货需求文本
     * @param memberId 会员Id
     * @param userId   用户Id
     * @return URL
     */
    public String getUrl_getPublishFastBuy(String phone, String content,
                                           String memberId, String userId)
    {

        String url = DOMAIN + httpUrl + "?phone=" + phone + "&content="
                + content + "&memberId=" + memberId + "&userId=" + userId
                + "&resource=1&cmd=buy.publishFastBuy&ostype=1&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }

    /**
     * 4.9 发布语音求购(buy.voicePublish)
     *
     * @param phone      电话
     * @param audioTimes 录音时间
     * @param memberId   会员/公司Id
     * @param userId     用户id
     * @param content    求购文本
     * @return url
     */
    public String getUrl_getVoicePublish(String phone, String audioTimes,
                                         String memberId, String userId, String content)
    {
        String url = DOMAIN + uploadVoiceUrl + "?phone=" + phone
                + "&resource=1&audioTimes=" + audioTimes + "&memberId="
                + memberId + "&userId=" + userId + "&content=" + content
                + "&cmd=buy.voicePublish&ostype=1&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);

        return url;
    }

    /**
     * 4.10 历史求购单(buy.historyStanBuy)
     *
     * @param context
     * @param page
     * @param size
     * @return
     */
    public String getUrl_getHistoryStanBuy(Context context, String page,
                                           String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId + "&page="
                + page + "&size=" + size + "&cmd=buy.historyStanBuy"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.11 取消求购单(buy.cancelStanBuy)
     *
     * @param context
     * @param stanBuyId
     * @return
     */
    public String getUrl_getCancelStanBuy(Context context, String stanBuyId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?stanBuyId=" + stanBuyId
                + "&userId=" + userId + "&cmd=buy.cancelStanBuy"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.12 未取消求购单(buy.uncancelStanBuy)首页下边3条数据
     *
     * @param context
     * @return
     */
    public String getUrl_getUncancelStanBuy(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?count=" + "3" + "&userId="
                + userId + "&cmd=buy.uncancelStanBuy" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.13 求购单详情(buy.detailStanBuy)
     *
     * @param context
     * @param stanBuyId
     * @return
     */
    public String getUrl_getDetailStanBuy(Context context, String stanBuyId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?stanBuyId=" + stanBuyId
                + "&userId=" + userId + "&cmd=buy.detailStanBuy"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.14 取消求购单(buy.cancelFastBuy)
     *
     * @param context   上下文
     * @param fastBuyId 快捷找货ID
     * @return url
     */
    public String getUrl_getCancelFastBuy(Context context, String fastBuyId)
    {

        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&fastBuyId=" + fastBuyId + "&cmd=buy.cancelFastBuy"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 4.15自定义求购列表(buy.customList)
     *
     * @param context
     * @param breeds
     * @param citys
     * @param page
     * @param size
     * @return
     */
    public String getUrl_getCustomList(Context context, String catalog, String breeds, String citys, String ipCity, String page, String size)
    {

        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = getDomain(context) + httpUrl + "?userId=" + userId
                + "&cmd=buy.customList" + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1"
                + "&page=" + page + "&size=" + size;
        if (!TextUtils.isEmpty(catalog))
        {
            url += "&catalogs=" + catalog;
        }
        if (!TextUtils.isEmpty(breeds))
        {
            url += "&breeds=" + breeds;
        }
        if (!TextUtils.isEmpty(citys))
        {
            url += "&citys=" + citys;
        }
        if(!TextUtils.isEmpty(ipCity)){
            url += "&ipCity=" + ipCity;
        }
        return url;
    }

    /**
     * 4.15自定义求购列表(buy.customList)查看同类用
     *
     * @param context
     * @param catalog
     * @param breeds
     * @param citys
     * @param material
     * @param spec
     * @param brand
     * @param page
     * @param size
     * @return
     */
    public String getUrl_getCustomList(Context context, String catalog, String breeds, String citys, String material, String spec, String brand, String page, String size)
    {

        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = getDomain(context) + httpUrl + "?userId=" + userId
                + "&cmd=buy.customList" + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1"
                + "&page=" + page + "&size=" + size;
        if (!TextUtils.isEmpty(catalog))
        {
            url += "&catalogs=" + catalog;
        }
        if (!TextUtils.isEmpty(breeds))
        {
            url += "&breeds=" + breeds;
        }
        if (!TextUtils.isEmpty(citys))
        {
            url += "&citys=" + citys;
        }

        if (!TextUtils.isEmpty(material))
        {
            url += "&material=" + material;
        }
        if (!TextUtils.isEmpty(spec))
        {
            url += "&spec=" + spec;
        }
        if (!TextUtils.isEmpty(brand))
        {
            url += "&brand=" + brand;
        }


        return url;
    }

    /**
     * 5.1 资源匹配(deal.matchBuy)
     *
     * @param context
     * @param stanBuyId
     *            需求单Id
     * @param page
     * @param size
     * @return
     *//*
    public String getUrl_getMatchBuy(Context context, String stanBuyId,
			String page, String size)
	{
		String userId = SharePreferenceUtil.getString(context,
				Constants.PREFERENCES_USERID);// userId
		return getDomain(context) + httpUrl + "?stanBuyId=" + stanBuyId
				+ "&userId=" + userId + "&page=" + page + "&size=" + size
				+ "&cmd=deal.matchBuy" + "&timestamp="
				+ System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
	}*/

    /**
     * 5.1 资源匹配(deal.matchBuy)
     *
     * @param context
     * @param stanBuyId 需求单Id
     * @param page
     * @param size
     * @return
     */
    public String getUrl_getMatchBuy(Context context, String stanBuyId,
                                     String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?stanBuyId=" + stanBuyId
                + "&userId=" + userId + "&page=" + page + "&size=" + size
                + "&cmd=deal.matchBuy" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.2 人工抢单(deal.humanServe)
     *
     * @param context
     * @param stanBuyId
     * @return
     */
    public String getUrl_getHumanServe(Context context, String stanBuyId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?stanBuyId=" + stanBuyId
                + "&cmd=deal.humanServe" + "&timestamp=" + "&userId=" + userId
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.3 匹配提交撮合订单(deal.createOrder)
     *
     * @param context
     * @param stanBuyId      求购单Id
     * @param resourceDataId 资源详细Id
     * @param source         成交来源
     * @param orderSource    求购来源
     * @param qty            　　　成交数量
     * @param price          　　成交价格
     * @return
     */
    public String getUrl_getCreateOrder(Context context, String stanBuyId,
                                        String resourceDataId, String source, String orderSource,
                                        String qty, String price)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&stanBuyId=" + stanBuyId + "&resourceDataId="
                + resourceDataId + "&source=" + source + "&orderSource="
                + orderSource + "&qty=" + qty + "&price=" + price
                + "&cmd=deal.createOrder" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.4上传凭证(deal.uploadCert)
     *
     * @param context
     * @param orderId
     * @param qty
     * @param price
     * @return
     */
    public String getUrl_getUploadCert(Context context, String orderId,
                                       String qty, String price)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + uploadImgUrl + "?userId=" + userId
                + "&orderId=" + orderId + "&qty=" + qty + "&price=" + price
                + "&cmd=deal.uploadCert" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.5 订单列表(deal.pageOrder)（买家）
     *
     * @param context
     * @param page
     * @param size
     * @param appraise appraise 不传值表示查询所有订单 0-待评价 1-已评价
     * @return
     */
    public String getUrl_getPageOrder(Context context, String page,
                                      String size, String appraise)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId
        String url = "";
        if ("1".equals(appraise))//意向单
        {
            url = getDomain(context) + httpUrl + "?buyUserId=" + userId
                    + "&buyMemberId=" + memberId + "&page=" + page + "&size=" + size
                    + /*"&appraise=" + appraise + */"&cmd=deal.pageOrder"
                    + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" /*+ "&stanBuyId=0"*/;
        } else if ("2".equals(appraise))//完成订单
        {
            url = getDomain(context) + httpUrl + "?buyUserId=" + userId
                    + "&buyMemberId=" + memberId + "&page=" + page + "&size=" + size
                    + /*"&appraise=" + appraise +*/ "&cmd=deal.pageAppraiseOrder"
                    + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        }

        return url;
    }

    /**
     * 5.5.1 订单列表(deal.pageOrder)（卖家）
     *
     * @param context
     * @param page
     * @param size
     * @param appraise appraise 不传值表示查询所有订单 0-待评价 1-已评价
     * @return
     */
    public String getUrl_getSellPageOrder(Context context, String page,
                                          String size, String appraise)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId

        String url = "";
        if ("1".equals(appraise))//意向单
        {
            url = getDomain(context) + httpUrl + "?quotUserId=" + userId
                    + "&quotMemberId=" + memberId + "&page=" + page + "&size=" + size
                    + /*"&appraise=" + appraise +*/ "&cmd=deal.pageOrder"
                    + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" /*+ "&stanBuyId=0"*/;
        } else if ("2".equals(appraise))//完成订单
        {
            url = getDomain(context) + httpUrl + "?quotUserId=" + userId
                    + "&quotMemberId=" + memberId + "&page=" + page + "&size=" + size
                    + /*"&appraise=" + appraise + */"&cmd=deal.pageAppraiseOrder"
                    + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        }

        return url;
    }

    /**
     * 5.5.2 订单列表(deal.pageOrder)（买家意向单）
     *
     * @param context
     * @param page
     * @param size
     * @param appraise appraise 不传值表示查询所有订单 0-待评价 1-已评价
     * @return
     */
    public String getUrl_getBuyIntentPageOrder(Context context, String page,
                                               String size, String appraise)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId

        String url = "";
        if ("0".equals(appraise))
            url = getDomain(context) + httpUrl + "?quotUserId=" + userId
                    + "&quotMemberId=" + memberId + "&page=" + page + "&size=" + size
                    + "&appraise=" + appraise + "&cmd=deal.pageOrder"
                    + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" + "&stanBuyId=0";

        return url;
    }

    /**
     * 5.6 订单详情(deal.orderDetail)
     *
     * @param context
     * @param orderId
     * @return
     */
    public String getUrl_getOrderDetail(Context context, String orderId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?userId=" + userId + "&orderId="
                + orderId + "&cmd=deal.orderDetail" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.7 订单列表(deal.editOrder)
     *
     * @param context
     * @param orderId    订单Id
     * @param memberType 买家/卖家用户类型1 -买家 2-卖家
     * @param name       联系人名称
     * @param phone      联系电话
     * @param memberName 会员名称
     * @return
     */
    public String getUrl_getEditOrder(Context context, String orderId,
                                      String memberType, String name, String phone, String memberName)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        return getDomain(context) + httpUrl + "?userId=" + userId + "&orderId="
                + orderId + "&memberType=" + memberType + "&name=" + name
                + "&phone=" + phone + "&memberName=" + memberName
                + "&cmd=deal.editOrder" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 5.8 订单审核未通过数量及aa订单待上传凭证数量(deal.orderCount)
     *
     * @param context
     * @return
     */
    public String getUrl_orderCount(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&memberId=" + memberId + "&cmd=deal.orderCount"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";

    }

    /**
     * 5.13 创建意向订单（deal.createResourceOrder）
     *
     * @param context
     * @param source      成交来源  0-匹配成交  1-人工报价成交   2-买家自行入录
     * @param stanBuyId   订单id
     * @param orderSource 求购来源:0-网站 1-会员中心  2 手机端  3-代成交
     * @param qty
     * @param price
     * @return
     */
    public String getUrl_createResourceOrder(Context context, String source, String stanBuyId, String quotId,
                                             String orderSource, String qty, String price)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&source=" + source + "&stanBuyId=" + stanBuyId
                + "&cmd=deal.createOrder" + "&orderSource=" + orderSource + "&qty=" + qty
                + "&price=" + price + "&quotId=" + quotId
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }


    /**
     * 5.17 撮合订单
     * @param context
     * @param page 页数
     * @param size 数目
     * @return
     */
    public String getUrl_consultOrder(Context context, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?userId=" + userId
                + "&page=" + page + "&size=" + size
                + "&cmd=deal.consultOrder"  + "&timestamp=" + System.currentTimeMillis()
                + Tools.getAndroidMsg(context) + "&ostype=1" + "&version=" + version;
    }

    /**
     * 资源筛选
     *
     * @param context
     * @param dafenlei 大分类(暂时为空)
     * @param pinlei   品类
     * @param caizhi   材质
     * @param guige    规格
     * @param pinpai   品牌
     * @param cunhuodi 存货地
     * @param page
     * @param size
     * @return
     */
    public String getUrl_matchResource(Context context, String dafenlei, String pinlei, String caizhi, String guige, String pinpai, String cunhuodi, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = getDomain(context) + httpUrl + "?"
                + "cmd=deal.matchResource&page=" + page + "&size=" + size + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";

        if (!TextUtils.isEmpty(userId))
        {
            url += "&userId=" + userId;
        }
        if (!TextUtils.isEmpty(dafenlei))
        {
            url += "&catalog=" + dafenlei;
        }
        if (!TextUtils.isEmpty(pinlei))
        {
            url += "&breedName=" + pinlei;
        }
        if (!TextUtils.isEmpty(caizhi))
        {
            url += "&material=" + caizhi;
        }
        if (!TextUtils.isEmpty(guige))
        {
            url += "&spec=" + guige;
        }
        if (!TextUtils.isEmpty(pinpai))
        {
            url += "&brand=" + pinpai;
        }
        if (!TextUtils.isEmpty(cunhuodi))
        {
            url += "&warehouse=" + cunhuodi;
        }
        return url;
    }

    /**
     * 资源筛选
     *
     * @param context
     * @param dafenlei 大分类(暂时为空)
     * @param pinlei   品类
     * @param caizhi   材质
     * @param guige    规格
     * @param pinpai   品牌
     * @param cunhuodi 存货地
     * @param page
     * @param size
     * @return
     */
    public String getUrl_matchResource(Context context, String dafenlei, String pinlei, String caizhi, String guige, String pinpai, String cunhuodi, String sort, String order, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = getDomain(context) + httpUrl + "?"
                + "cmd=deal.matchResource&page=" + page + "&size=" + size + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        if (!TextUtils.isEmpty(userId))
        {
            url += "&userId=" + userId;
        }
        if (!TextUtils.isEmpty(dafenlei))
        {
            url += "&catalog=" + dafenlei;
        }
        if (!TextUtils.isEmpty(pinlei))
        {
            url += "&breedName=" + pinlei;
        }
        if (!TextUtils.isEmpty(caizhi))
        {
            url += "&material=" + caizhi;
        }
        if (!TextUtils.isEmpty(guige))
        {
            url += "&spec=" + guige;
        }
        if (!TextUtils.isEmpty(pinpai))
        {
            url += "&brand=" + pinpai;
        }
        if (!TextUtils.isEmpty(cunhuodi))
        {
            url += "&warehouse=" + cunhuodi;
        }
        if (!TextUtils.isEmpty(sort) && !TextUtils.isEmpty(order))
        {
            url += "&sort=" + sort + "&order=" + order;
        }
        return url;
    }


    /**
     * 6.0资源搜索（deal.searchResource）
     * @param context
     * @param keyWord 关键字
     * @param sort 排序
     * @param order 排序方式 asc或者desc
     * @param page
     * @param size
     * @return
     *//*
    public String getUrl_searchResource(Context context,String keyWord,String sort,String order,String page,String size)
	{
		String userId = SharePreferenceUtil.getString(context,
				Constants.PREFERENCES_USERID);// userId
		
		String url = getDomain(context) + httpUrl + "?userId=" + userId
													+ "&cmd=deal.searchResource&page=" + page +"&size=" + size+ "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
		if(!TextUtils.isEmpty(keyWord)){
			url += "&keyWord=" + keyWord;
		}
		if(!TextUtils.isEmpty(sort)&&!TextUtils.isEmpty(order)){
			url += "&sort=" + sort + "&order=" + order;
		}
		return url;
	}*/


    /**
     * 6.0资源搜索（deal.searchResource）
     *
     * @param context
     * @param keyWord 关键字
     * @param sort    排序
     * @param order   排序方式 asc或者desc
     * @param page
     * @param size
     * @return
     */
    public String getUrl_searchResource(Context context, String keyWord, String sort, String order, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId

        String url = getDomain(context) + httpUrl + "?"
                + "cmd=deal.searchResource&page=" + page + "&size=" + size + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        if (!TextUtils.isEmpty(userId))
        {
            url += "&userId=" + userId;
        }
        if (!TextUtils.isEmpty(keyWord))
        {
            url += "&keyWord=" + keyWord;
        }
        if (!TextUtils.isEmpty(sort) && !TextUtils.isEmpty(order))
        {
            url += "&sort=" + sort + "&order=" + order;
        }
        return url;
    }

    /**
     * 5.10资源搜索（deal.searchResource）多加了一个手机号
     *
     * @param context
     * @param keyWord 关键字
     * @param sort    排序
     * @param order   排序方式 asc或者desc
     * @param page
     * @param size
     * @return
     */
    public String getUrl_searchResource(Context context, String phone, String keyWord, String sort, String order, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId

        String url = DOMAIN + httpUrl + "?"
                + "cmd=deal.searchResource&page=" + page + "&size=" + size + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        if (!TextUtils.isEmpty(userId))
        {
            url += "&userId=" + userId;
        }
        if (!TextUtils.isEmpty(phone))
        {
            url += "&phone=" + phone;
        }
        if (!TextUtils.isEmpty(keyWord))
        {
            url += "&keyWord=" + keyWord;
        }
        if (!TextUtils.isEmpty(sort) && !TextUtils.isEmpty(order))
        {
            url += "&sort=" + sort + "&order=" + order;
        }
        return url;
    }

    /**
     * 根据ID搜索资源显示资源详情
     *
     * @param context
     * @param id
     * @return
     */
    public String getUrl_matchResource(Context context, String id)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId

        String url = getDomain(context) + httpUrl + "?"
                + "cmd=deal.matchResource&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1" + "&id=" + id;
        if(!TextUtils.isEmpty(userId)){
            url += "&userId=" + userId;
        }
        return url;
    }

    /**
     * 6.1 自动匹配评价订单即评价卖家(eval.evaluateSeller)
     *
     * @param context
     * @param orderId         订单ID
     * @param totalAppraise   总评价分数1-5
     * @param serviceAppraise 服务评价分数1-5 (质量满意)（卖家：合作愉快）
     * @param sourceAppraise  货源真实性评价分数1-5(价格满意)（卖家：到款及时） feedbackAppraise(反馈及时)（卖家：求购真实）
     * @param note            评价内容
     * @return
     */
    public String getUrl_getEvaluateSeller(Context context, String orderId,
                                           String totalAppraise, String serviceAppraise,
                                           String sourceAppraise, String feedbackAppraise, String note, String appraiseType)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?orderId=" + orderId
                + "&userId=" + userId + "&totalAppraise=" + totalAppraise
                + "&serviceAppraise=" + serviceAppraise + "&sourceAppraise="
                + sourceAppraise + "&feedbackAppraise=" + feedbackAppraise
                + "&note=" + note + "&appraiseType=" + appraiseType + "&cmd=eval.evaluateSeller"
                + "&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }

    /**
     * 6.15指定时间区间钢材价格趋势（deal.getPriceTrend）
     *
     * @param context
     * @param map
     * @return
     */
    public String getUrl_PriceTrend(Context context, Map<String, String> map)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&cmd=deal.getPriceTrend&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (!TextUtils.isEmpty(entry.getValue()))
            {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    /**
     * 6.2 人工服务评价管理员(eval.evaluateManager)
     *
     * @param context
     * @param orderId         订单ID
     * @param totalAppraise   总评价分数1-5
     * @param serviceAppraise 服务评价分数1-5
     * @param sourceAppraise  货源真实性评价分数1-5
     * @param note            评价内容
     * @return
     */
    public String getUrl_getEvaluateManager(Context context, String orderId,
                                            String totalAppraise, String serviceAppraise,
                                            String sourceAppraise, String note)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return getDomain(context) + httpUrl + "?orderId=" + orderId
                + "&userId=" + userId + "&totalAppraise=" + totalAppraise
                + "&serviceAppraise=" + serviceAppraise + "&sourceAppraise="
                + sourceAppraise + "&note=" + note
                + "&cmd=eval.evaluateManager" + "&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
    }


    /**
     * 创建意向订单
     *
     * @param context
     * @param resourceId
     * @param qty
     * @param price
     * @return
     */
    public String getUrl_CreateIntentionOrder(Context context, String resourceId, String qty, String price)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=deal.createResourceOrder&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1"
                + "&resourceId=" + resourceId + "&source=2&orderSource=2"
                + "&qty=" + qty + "&price=" + price;
        return url;
    }


    /**
     * 6.1获取指定时间区间资源总数（deal.todayResourceCount）
     *
     * @param context
     * @param startTime
     * @param endTIme
     * @return
     */
    public String getUrl_TodayResourceCount(Context context, String startTime, String endTIme)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=deal.todayResourceCount&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1"
                + "&startTime=" + startTime + "&endTIme=" + endTIme;
        return url;
    }

    /**
     * 7.3 检查更新(sys.checkUpdate)
     *
     * @param context 上下文
     * @return url
     */
    public String getUrl_checkUpdate(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String version;
        try
        {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e)
        {
            version = "2.0.0";
        }

        String url = DOMAIN + httpUrl + "?userId=" + userId + "&version="
                + version + "&cmd=sys.checkUpdate&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;

    }

    /**
     * 7.4 签到送积分(sys.sign)
     *
     * @param userId 用户id
     * @return url
     */
    public String getUrl_sign(String userId)
    {

        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&cmd=sys.sign&timestamp=" + System.currentTimeMillis() + Tools.getAndroidMsg(context)
                + "&ostype=1";
        return url;

    }

    /**
     * 7.5 反馈意见(sys.suggest)
     *
     * @param userId   用户id
     * @param memberId 会员id/公司id
     * @param phone    电话号码
     * @param name     联系人
     * @param note     反馈内容
     * @return url
     */
    public String getUrl_suggest(String userId, String memberId, String phone,
                                 String name, String note)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&memberId="
                + memberId + "&phone=" + phone + "&name=" + name + "&note="
                + note + "&cmd=sys.suggest&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }

    /**
     * 7.6是否推送(sys.push)
     *
     * @param userId 用户id
     * @param isPush 是否推送 0-不推送 1-推送
     * @return url
     */
    public String getUrl_push(String userId, String isPush)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&isPush="
                + isPush + "&cmd=sys.push&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }


    /**
     * 7.7 历史消息(sys.pageHistoryMessage)
     *
     * @param userId
     * @param page
     * @param size
     * @param type   0-系统消息   1-订单消息
     * @return
     */
    public String getUrl_pageHistoryMessage(String userId, String page,
                                            String size, String type)
    {
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&page=" + page
                + "&size=" + size + "&type=" + type + "&cmd=sys.pageHistoryMessage&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&ostype=1";
        return url;
    }


    /**
     * 7.8平台交易规则(sys.rule)
     *
     * @param context context
     * @return url
     */
    public String getUrl_getRule(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&ostype=1&cmd=sys.rule&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context);
        return url;
    }

    /**
     * 7.9删除消息(sys.deleteMessage)
     *
     * @param context context
     * @return url
     */
    public String getUrl_getDeleteMessage(Context context, String ids)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);
        String url = DOMAIN + httpUrl + "?userId=" + userId
                + "&ids=" + ids + "&ostype=1&cmd=sys.deleteMessage&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        return url;
    }

    /**
     * 8.1 根据ID调取广告位(advert.getAdvertById)
     *
     * @param advertId id
     * @return url
     */
    public String getUrl_getAdvertById(Context context, String advertId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&advertId="
                + advertId + "&ostype=1&cmd=advert.getAdvertById&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        return url;
    }

    /***
     * 9.1求购报价详情
     *
     * @param context
     * @return
     */
    public String getUrl_getStanQuotDetails(Context context, String stanBuyId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return DOMAIN + httpUrl + "?userId=" + userId + "&stanBuyId="
                + stanBuyId + "&ostype=1&cmd=quot.stanQuotDetails&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
    }

    /**
     * 我要报价
     *
     * @param context
     * @param stanBuyId
     * @param bargaining
     * @param price
     * @return
     */
    public String getUrl_getSellQuot(Context context, String stanBuyId, String bargaining, String price, String note)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&stanBuyId="
                + stanBuyId + "&ostype=1&cmd=quot.sellQuot&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&price=" + price + "&bargaining=" + bargaining + "&type=1";
        if(!TextUtils.isEmpty(note)){
            url += "&note="+note;
        }
        return url;
    }

    /**
     * 9.2我的报价列表
     *
     * @param context
     * @return
     */
    public String getUrl_getmyQuot(Context context, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return DOMAIN + httpUrl + "?userId=" + userId + "&page=" + page
                + "&size=" + size + "&ostype=1&cmd=quot.myQuot&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
    }

    /**
     * 11.1获取所有（国家/省份/市/区/县/街道）
     *
     * @param context type:province（所有省份）, country(国家)，city（市），district（区/县），town(镇/街道)
     * @return
     */
    public String getUrl_queryAreaByType(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?type=city" + "&ostype=1&cmd=area.queryAreaByType&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        if(!TextUtils.isEmpty(userId)){
            url += "&userId=" + userId;
        }
        return url;
    }

    /**
     * 11.2获取子地区（area.querySubAreaByCode）
     *
     * @return
     */
    public String getUrl_querySubAreaByCode(Context context, String code)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url =  DOMAIN + httpUrl + "?code="+code+"&ostype=1&cmd=area.querySubAreaByCode&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        if(!TextUtils.isEmpty(userId)){
            url += "&userId=" + userId;
        }
        return url;
    }

    /**
     * 10.1查询报价（transport.transportQuot）
     *
     * @return
     */
    public String getUrl_queryTransportQuot(Context context, Map<String, String> map)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = /*"http://180.166.92.235:8280" */ DOMAIN + httpUrl + "?cmd=transport.transportQuot&timestamp="
                //String url =  "http://180.166.92.235:8280" + httpUrl + "?userId=" + userId + "&cmd=transport.transportQuot&timestamp="
                + System.currentTimeMillis() + "?userId=" + userId + Tools.getAndroidMsg(context) + "&type=1&ostype=1";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (!TextUtils.isEmpty(entry.getValue()))
            {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }


    /**
     * 10.2创建物流订单（transport.createTransportOrder）
     *
     * @return
     */
    public String getUrl_createTransportOrder(Context context, Map<String, String> map)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&ostype=1&cmd=transport.createTransportOrder&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (!TextUtils.isEmpty(entry.getValue()))
            {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    /**
     * 10.2人工服务（transport.customerCare）
     *
     * @return
     */
    public String getUrl_CustomerCare(Context context, Map<String, String> map)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String url = DOMAIN + httpUrl + "?userId=" + userId + "&ostype=1&cmd=transport.customerCare&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&type=1";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (!TextUtils.isEmpty(entry.getValue()))
            {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    /**
     * 10.6获取运钢网品种(transport.queryTransportBreed)
     *
     * @return
     */
    public String getUrl_queryTransportBreed(Context context)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return /*"http://180.166.92.235:8280" */ DOMAIN + httpUrl + "?cmd=transport.queryTransportBreed&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&userId=" + userId + "&type=1&ostype=1";
    }

    /**
     * 10.3查看物流状态(transport.transportDetails)
     *
     * @return
     */
    public String getUrl_transportDetails(Context context, String orderId)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        return DOMAIN + httpUrl + "?cmd=transport.transportDetails&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&userId=" + userId
                + "&orderId=" + orderId + "&type=1&ostype=1";
    }

    /**
     * 10.7我的物流订单（transport.getOrderByUserId）
     */
    public String getUrl_getOrderByUserId(Context context, String page, String size)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId "3627"
        String url =  /*"http://180.166.92.235:8280" */ DOMAIN + httpUrl + "?cmd=transport.getOrderByUserId&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&page=" + page + "&size=" + size + "&type=1&ostype=1";
        if(!TextUtils.isEmpty(userId)){
            url += "&userId=" + userId;
        }
        return url;
    }

    /**
     * 10.5物流评价(transport.transportAppraise)
     */
    public String getUrl_getTransportAppraise(Context context, String assessDetail, String waybillId, String remark)
    {
        String userId = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_USERID);// userId
        String name = SharePreferenceUtil.getString(context, Constants.USER_NAME);
        String phone = SharePreferenceUtil.getString(context,
                Constants.PREFERENCES_CELLPHONE);
        String memberId = SharePreferenceUtil.getString(context,
                Constants.USER_MEMBERID);// memberId


        return DOMAIN + httpUrl + "?cmd=transport.transportAppraise&timestamp="
                + System.currentTimeMillis() + Tools.getAndroidMsg(context) + "&userId=" + userId + "&ostype=1&phone=" + phone + "&memberId=" + memberId + "&userName=" + name
                + "&assessDetail=" + assessDetail + "&waybillId=" + waybillId + "&remark=" + remark;
    }


}

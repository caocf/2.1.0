package com.mysteel.banksteel.ao;

/**
 * 注册
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-12 下午4:58:49
 */
public interface IUserCenter extends IBaseAO
{

	/** 请求链接 和 获取验证码 退出登录共用的接口 返回数据 */
	void getRegisterData(String url, String request_tag);

	/** 获取验证码 返回数据 */
	// void getSmsCode(String url);

	/** 登陆协议 */
	void getLoginData(String url, String request_tag);

	/**
	 * 修改个人信息,修改公司信息
	 * 
	 * @param url
	 */
	public void getEditInfo(String url, String request_tag);

	/**
	 * 点击头像查看个人信息
	 *
	 * @param url
	 */
	public void getShowUserInfo(String url, String request_tag);


	/**
	 * 根据手机号获取到多个用户信息
	 *
	 * @param url
	 */
	public void getShowMultipleUserInfo(String url, String request_tag);


	/**
	 * 获取业务范围
	 * @param url
	 * @param request_tag
	 */
	public void getBusinessScopes(String url, String request_tag);


	/**
	 * 更改业务区域、业务范围、会员类型（user.modifyMemberBusinessInfo）
	 * @param url
	 * @param request_tag
	 */
	public void getModifyMemberBusinessInfo(String url, String request_tag);

}

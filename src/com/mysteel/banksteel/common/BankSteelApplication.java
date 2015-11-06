package com.mysteel.banksteel.common;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.easemob.EMCallBack;
import com.mysteel.banksteel.huanxin.DemoHXSDKHelper;
import com.mysteel.banksteel.huanxin.User;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 上午9:45:51
 */
public class BankSteelApplication extends Application
{
	/**
	 * 全局开关，app发布时一定要置为false.
	 */
	public static final boolean GLOBAL_DEBUG = true;
	public static final boolean DEBUG = false & GLOBAL_DEBUG;
	public static RequestQueue requestQueue;
	private static BankSteelApplication instance;

	// login user name
	public final String PREF_USERNAME = "username";
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
	public static Context applicationContext;

	@Override
	public void onCreate()
	{
		super.onCreate();
		requestQueue = Volley.newRequestQueue(this);
		JPushInterface.setDebugMode(GLOBAL_DEBUG); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush

		applicationContext = this;
		instance = this;
		hxSDKHelper.onInit(applicationContext);
	}

	public static synchronized BankSteelApplication getInstance()
	{
		return instance;
	}

	/**
	 * 获取内存中好友user list
	 *
	 * @return
	 */
	public Map<String, User> getContactList() {
		return hxSDKHelper.getContactList();
	}

	/**
	 * 设置好友user list到内存中
	 *
	 * @param contactList
	 */
	public void setContactList(Map<String, User> contactList) {
		hxSDKHelper.setContactList(contactList);
	}

	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getUserName() {
		return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 *
	 * @param user
	 */
	public void setUserName(String username) {
		hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
		hxSDKHelper.logout(emCallBack);
	}
}

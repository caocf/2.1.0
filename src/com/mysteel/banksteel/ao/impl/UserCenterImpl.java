package com.mysteel.banksteel.ao.impl;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IUserCenter;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.BusinessScopesData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.RegisterData;
import com.mysteel.banksteel.entity.ShowMoreUserInfo;
import com.mysteel.banksteel.entity.ShowUserInfo;
import com.mysteel.banksteel.entity.UserInfoData;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;

import java.util.ArrayList;
import java.util.Iterator;

import org.simple.eventbus.EventBus;

/**
 * 注册实现类
 * 
 * @author 作者 zoujian
 * @param <T>
 * @date 创建时间：2015-5-12 下午5:08:00
 */
public class UserCenterImpl implements IUserCenter
{

	private IUserCenterView viewInterface;
	private Context context;
	private GetDataDAO<RegisterData> getRegisterDataDao;
	private GetDataDAO<UserInfoData> loginDataDao;
	private GetDataDAO<EditInfoData> editInfoDataDao;
	private GetDataDAO<ShowUserInfo> showUserInfoDataDao;
	private GetDataDAO<BusinessScopesData> businessScopesDataGetDataDao;
	private GetDataDAO<BaseData> scopesDataGetDataDao;
	private GetDataDAO<ShowMoreUserInfo> showMoreUserInfoDataDao;



	public ArrayList<String> Tags = new ArrayList<String>();

	public UserCenterImpl(Context context)
	{
		this.context = context;
		viewInterface = (IUserCenterView) context;
	}

	public UserCenterImpl(Context context, IUserCenterView viewInterface)
	{
		this.context = context;
		this.viewInterface = viewInterface;
		EventBus.getDefault().register(this);
	}

	@Override
	public void getRegisterData(String url, String request_tag)
	{
		if (getRegisterDataDao == null)
		{
			getRegisterDataDao = new GetDataDAO<RegisterData>(context,
					RegisterData.class, new DefaultAOCallBack<RegisterData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(RegisterData data)
						{
							String cmd = data.getCmd();// 返回数据中的cmd
														// 用来判断是哪个协议，做相应的处理
							if ("user.sendSmsValidate".equals(cmd))
							{// 1.3
								// 发送手机验证码
								viewInterface.openCountDown(60000, 1000);
							} else if ("user.register".equals(cmd))
							{// 1.7 注册
								viewInterface.updateView(data);
								Tools.showToast(context, "注册成功！");
							} else if ("user.loginOut".equalsIgnoreCase(cmd))
							{// 1.2退出登录
								viewInterface.updateView(data);
							} else if ("user.validateSmsCode"
									.equalsIgnoreCase(cmd))
							{// 1.4验证码验证
								viewInterface.updateView(data);
							} else if ("user.setNewPasswordByPhone"
									.equalsIgnoreCase(cmd))
							{// 1.5设置新密码
								viewInterface.updateView(data);
							} else if ("user.resetPassword"
									.equalsIgnoreCase(cmd))
							{// 1.6重置密码
								viewInterface.updateView(data);
							} else if ("user.updateBindMobile"
									.equalsIgnoreCase(cmd))
							{// 1.13 更换手机号
								viewInterface.updateView(data);
							}
						}

					});
		}
		viewInterface.isShowDialog(true);
		// Log.d("IRegisterImpl", "url === " + url);
		setTagForRequest(request_tag);
		getRegisterDataDao.getData(url, request_tag);
	}

	@Override
	public void getLoginData(String url, String request_tag)
	{
		if (loginDataDao == null)
		{
			loginDataDao = new GetDataDAO<UserInfoData>(context,
					UserInfoData.class, new DefaultAOCallBack<UserInfoData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(UserInfoData data)
						{
							viewInterface.updateView(data);
						}
						
						@Override
						public void dealWithFalse(String str)
						{
							Tools.showToast(context, str);
							Tools.clearCache(context);
							EventBus.getDefault().post("", "mainFragment_view");
						}
					});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		loginDataDao.getData(url, request_tag);
	}

	@Override
	public void getEditInfo(String url, String request_tag)
	{
		if (editInfoDataDao == null)
		{
			editInfoDataDao = new GetDataDAO<EditInfoData>(context,
					EditInfoData.class, new DefaultAOCallBack<EditInfoData>(
							viewInterface, context)
					{

						@Override
						public void dealWithSuccess(EditInfoData obj)
						{
							if ("user.editUserInfo".equalsIgnoreCase(obj // 1.9:修改个人信息
									.getCmd()))
							{
								viewInterface.upDatePersonal(obj);
							} else if ("user.editMemberInfo"
									.equalsIgnoreCase(obj.getCmd()))
							{ // 1.10:修改公司信息
								viewInterface.upDateCompany(obj);
							}
						}

					});

		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		editInfoDataDao.getData(url, request_tag);
	}

	@Override
	public void getShowUserInfo(String url, String request_tag) {
		if (showUserInfoDataDao == null)
		{
			showUserInfoDataDao = new GetDataDAO<ShowUserInfo>(context,
					ShowUserInfo.class, new DefaultAOCallBack<ShowUserInfo>(
					viewInterface, context)
			{
				@Override
				public void dealWithSuccess(ShowUserInfo data)
				{
					viewInterface.updateView(data);
				}
			});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		showUserInfoDataDao.getData(url, request_tag);
	}

	@Override
	public void getShowMultipleUserInfo(String url, String request_tag) {
		if (showMoreUserInfoDataDao == null)
		{
			showMoreUserInfoDataDao = new GetDataDAO<ShowMoreUserInfo>(context,
					ShowMoreUserInfo.class, new DefaultAOCallBack<ShowMoreUserInfo>(
					viewInterface, context)
			{
				@Override
				public void dealWithSuccess(ShowMoreUserInfo data)
				{
					viewInterface.updateView(data);
				}
			});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		showMoreUserInfoDataDao.getData(url, request_tag);
	}

	@Override
	public void getBusinessScopes(String url, String request_tag) {
		if (businessScopesDataGetDataDao == null)
		{
			businessScopesDataGetDataDao = new GetDataDAO<BusinessScopesData>(context,
					BusinessScopesData.class, new DefaultAOCallBack<BusinessScopesData>(
					viewInterface, context)
			{
				@Override
				public void dealWithSuccess(BusinessScopesData data)
				{
					viewInterface.updateView(data);
				}
			});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		businessScopesDataGetDataDao.getData(url, request_tag);
	}

	@Override
	public void getModifyMemberBusinessInfo(String url, String request_tag) {
		if (scopesDataGetDataDao == null)
		{
			scopesDataGetDataDao = new GetDataDAO<BaseData>(context,
					BaseData.class, new DefaultAOCallBack<BaseData>(
					viewInterface, context)
			{
				@Override
				public void dealWithSuccess(BaseData data)
				{
					viewInterface.updateView(data);
				}
			});
		}
		viewInterface.isShowDialog(true);
		setTagForRequest(request_tag);
		scopesDataGetDataDao.getData(url, request_tag);

	}

	/** 将当前的Request tag保存起来 */
	public void setTagForRequest(String tag)
	{
		if (!Tags.isEmpty())
		{
			Iterator<String> it = Tags.iterator();
			while (it.hasNext())
			{
				String sTag = it.next();
				if (!it.equals(tag))
				{// 若list中没有tag，添加
					if (!Tags.contains(sTag))
					{
						Tags.add(tag);
					}
				}
			}

		} else
		{
			Tags.add(tag);
		}

	}

	@Override
	public void finishRequest()
	{
		for (String tag : Tags)
		{
			BankSteelApplication.requestQueue.cancelAll(tag);
		}
		EventBus.getDefault().unregister(this);
	}
}

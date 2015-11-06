package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.EditInfoData;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-12 下午8:15:53
 */
public interface IUserCenterView extends IBaseViewInterface
{

	/** 开启倒计时 验证码 */
	void openCountDown(long millisInFuture, long countDownInterval);

	/**
	 * 更新个人信息
	 * 
	 * @param data
	 */
	public void upDatePersonal(EditInfoData data);

	/**
	 * 更新公司信息
	 * 
	 * @param data
	 */
	public void upDateCompany(EditInfoData data);
}

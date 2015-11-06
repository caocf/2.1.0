package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.BaseData;

/**
 * 地址管理的视图view
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 下午4:43:43
 */
public interface IAddressManageView extends IBaseViewInterface
{

	/**
	 * 设置地址列表
	 * 
	 * @param data
	 */
	public void setAddressList(AddressListData data);

	/**
	 * 设置默认地址
	 * 
	 * @param data
	 */
	public void setDefaultAddress(BaseData data);

	/**
	 * 删除地址
	 * 
	 * @param data
	 */
	public void delAddress(BaseData data);
}

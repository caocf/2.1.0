package com.mysteel.banksteel.view.interfaceview;

/**
 * listview
 * 
 * @author:huoguodong
 * @date：2015-5-13 上午10:03:17
 */
public interface IListViewInterface extends IBaseViewInterface
{

	/**
	 * 停止对listview的下拉刷新和上拉加载，在AO实现中调用，无论结果true false exception
	 */
	public void stopUpdate();
}

package com.mysteel.banksteel.view.interfaceview;

import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.SearchMyScoreData;

/**
 * 积分中心的view接口
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-14 下午5:02:56
 */
public interface IScoreView extends IBaseViewInterface
{

	/**
	 * 查询我的积分
	 */
	void searchMyScore(SearchMyScoreData data);
	
	/**
	 * 赚取积分
	 * @param data
	 */
	void earnScore(EarnScoreData data);
}

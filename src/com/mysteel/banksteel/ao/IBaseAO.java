package com.mysteel.banksteel.ao;


/**
 * 基本的接口类，对于很多页面共同需要实现的功能提取到这，以接口形式给出，不同的页面各自实现自己要实现的方法即可。
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 下午2:35:50
 */
public interface IBaseAO
{
	void finishRequest();
}

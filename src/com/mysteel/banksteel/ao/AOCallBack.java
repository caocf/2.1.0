package com.mysteel.banksteel.ao;

public interface AOCallBack<T>
{
	/**
	 * 处理result 为true逻辑
	 * 
	 * @param obj
	 */
	public void dealWithTrue(T obj);

	/**
	 * 处理result 为false逻辑
	 * 
	 * @param str
	 */
	public void dealWithFalse(String str);

	/**
	 * 处理运行异常(连接超时)
	 * 
	 * @param error
	 */
	public void dealWithException(String error);

}

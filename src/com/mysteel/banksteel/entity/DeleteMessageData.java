package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 删除消息
 * 
 * @author:huoguodong
 * @date：2015-5-10 下午5:46:14
 */
@SuppressWarnings("serial")
public class DeleteMessageData extends BaseData{

	private static final long serialVersionUID = -3503990355649573797L;
	
	private String data; //表示删除成功的id
	private String failIds; //表示删除失败的id
	
	public String getData() {
		if(TextUtils.isEmpty(data)){
			return "";
		}
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getFailIds() {
		if(TextUtils.isEmpty(failIds)){
			return "";
		}
		return failIds;
	}
	public void setFailIds(String failIds) {
		this.failIds = failIds;
	}
	
	
	
	
}

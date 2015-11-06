package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 绑定手机号页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-8 上午11:02:41
 */
public class BindPhoneNumActivity extends BaseActivity implements
		OnClickListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buidphone_num);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		tvTitle.setText("绑定手机号");
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;

		default:
			break;
		}
	}

}

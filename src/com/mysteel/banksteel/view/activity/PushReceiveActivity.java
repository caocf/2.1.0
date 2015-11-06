package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

/**
 * 推送接收的页面
 * 
 * @author zoujian
 * 
 */
public class PushReceiveActivity extends BaseActivity implements
		OnClickListener
{

	private TextView tv_content_titel;// 内容
	private String title = "";
	private String content = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (null != intent)
		{
			Bundle bundle = getIntent().getExtras();
			title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			content = bundle.getString(JPushInterface.EXTRA_ALERT);
		}
		setContentView(R.layout.activity_push_content);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		tvRightText.setVisibility(View.GONE);
		tvTitle.setText(title);
		tv_content_titel = (TextView) findViewById(R.id.tv_content_titel);
		tv_content_titel.setText(content);
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

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (Tools.isBackground(PushReceiveActivity.this))// 在后台
		{
			startActivity(new Intent(PushReceiveActivity.this,
					WelcomeActivity.class));
		}
	}

}

package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author 68 找回密码
 */
public class FindPasswordActivity extends BaseActivity implements
		OnClickListener
{

	private EditText etPhone;
	private Button btnNextStep;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		phone = getIntent().getStringExtra("PHONE");
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("找回密码");
		etPhone = (EditText) findViewById(R.id.et_findpassword_phone);

		btnNextStep = (Button) findViewById(R.id.btn_findpassword_nextstep);
		btnNextStep.setOnClickListener(this);
		backLayout.setOnClickListener(this);

		if (Tools.checkIsPhoneNumber(phone))
		{
			etPhone.setText(phone);
		}
		// 设置手机号码,隐藏中间4位
		// etPhone.setText(Tools.hiddenPhoneNumber(phone));

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			// 返回
			finish();
			break;

		case R.id.btn_findpassword_nextstep:// 下一步
			// 跳转到验证码验证页面
			phone = etPhone.getText().toString();
			if(!Tools.checkIsPhoneNumber(phone)){
				Tools.showToast(this, "请输入正确的手机号码");
				return;
			}
			Intent intent = new Intent(this, PhoneVerificationActivity.class);
			intent.putExtra("PHONE", phone);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}

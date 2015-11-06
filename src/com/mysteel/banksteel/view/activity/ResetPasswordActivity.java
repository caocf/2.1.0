package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * @author 68 重置密码
 */
public class ResetPasswordActivity extends SwipeBackActivity implements
		OnClickListener, IUserCenterView
{

	/** 完成按钮 */
	private Button completeBtn;
	/** 密码 */
	private EditText etPassword;
	/** 确认密码 */
	private EditText etConfirmPassword;
	/** 原始密码 */
	private EditText etOldPassword;
	/** 进度条 */
	private ProgressBar progressBar;
	/** UserCenterImpl */
	private UserCenterImpl userCenterImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("重置密码");

		completeBtn = (Button) findViewById(R.id.btn_resetpassword_complete);
		completeBtn.setOnClickListener(this);
		backLayout.setOnClickListener(this);

		etOldPassword = (EditText) findViewById(R.id.et_resetpassword_defualtpassword);
		etPassword = (EditText) findViewById(R.id.et_resetpassword_password);
		etConfirmPassword = (EditText) findViewById(R.id.et_resetpassword_comfirepassword);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		userCenterImpl = new UserCenterImpl(this);
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

		case R.id.btn_resetpassword_complete:
			// 重设密码提交
			loadResetPasswordData();
			break;

		default:
			break;
		}
	}

	/**
	 * 重置密码,拉取数据
	 */
	private void loadResetPasswordData()
	{
		// 判断网络
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "网络未连接");
			return;
		}
		String oldPassword = etOldPassword.getText().toString().trim();
		String password = etPassword.getText().toString().trim();
		String confirmPassword = etConfirmPassword.getText().toString().trim();
		// 判断密码
		if (!Tools.checkIsPassword(password))
		{
			Tools.showToast(this, "新密码输入格式错误");
			return;
		}
		if (!Tools.checkIsPassword(confirmPassword))
		{
			Tools.showToast(this, "确认新密码输入格式错误");
			return;
		}

		if (!password.equals(confirmPassword))
		{
			Tools.showToast(this, "两次确认密码不一致");
			return;
		}
		String url = RequestUrl.getInstance(this).getUrl_resetPassword(
				SharePreferenceUtil.getString(this,
						Constants.PREFERENCES_USERID), oldPassword, password,
				confirmPassword);
		userCenterImpl.getRegisterData(url, Constants.INTERFACE_resetPassword);
	}

	@Override
	public void updateView(BaseData data)
	{
		// 修改成功后保存一下密码
		if ("true".equals(data.getStatus()))
		{
			String password = etPassword.getText().toString().trim();
			if (!TextUtils.isEmpty(password))
			{
				SharePreferenceUtil.setValue(this, Constants.USER_PASSWARD,
						password);// 保存密码
			}
			startActivity(new Intent(this, UserSettingActivity.class));
			finish();

		} else
		{
			Tools.showToast(this, "重置密码失败");
		}

	}

	@Override
	public void isShowDialog(boolean flag)
	{
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void openCountDown(long millisInFuture, long countDownInterval)
	{
	}

	@Override
	public void upDatePersonal(EditInfoData data)
	{
	}

	@Override
	public void upDateCompany(EditInfoData data)
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		userCenterImpl.finishRequest();
	}
}

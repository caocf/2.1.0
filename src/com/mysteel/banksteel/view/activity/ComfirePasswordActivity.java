package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * @author 68 确认新密码
 */
public class ComfirePasswordActivity extends BaseActivity implements
		OnClickListener, IUserCenterView
{

	/** 新密码 */
	private EditText etPassword;
	/** 确认新密码 */
	private EditText etRepeatPassword;
	/** 完成 */
	private Button btnComplete;
	/** 进度对话框 */
	private ProgressBar progressBar;
	private UserCenterImpl userCenterImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comfire_newpassword);
		initViews();
	}

	@SuppressLint("CutPasteId")
	@Override
	protected void initViews()
	{
		super.initViews();

		tvTitle.setText("确认新密码");

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		etPassword = (EditText) findViewById(R.id.et_comfirepassword_password);
		etRepeatPassword = (EditText) findViewById(R.id.et_comfirepassword_repeatpassword);
		btnComplete = (Button) findViewById(R.id.btn_comfirepassword_complete);

		btnComplete.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		userCenterImpl = new UserCenterImpl(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			/** 返回 */
			finish();
			break;

		case R.id.btn_comfirepassword_complete:
			/** 完成按钮 */
			loadSetPasswordData();
			break;

		default:
			break;
		}
	}

	private void loadSetPasswordData()
	{
		// 判断网络
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "网络未连接");
			return;
		}

		// 检查密码,确认密码
		String password = etPassword.getText().toString().trim();
		String confirmPassword = etRepeatPassword.getText().toString().trim();
		if (!Tools.checkIsPassword(password))
		{
			Tools.showToast(this, "请检查密码是否规范");
			return;
		}
		if (!Tools.checkIsPassword(confirmPassword))
		{
			Tools.showToast(this, "请检查确认密码是否规范");
			return;
		}
		// String url =
		// RequestUrl.getInstance(this).getUrl_setNewPasswordByPhone(
		// mPhone, password, confirmPassword);
		// userCenterImpl.getRegisterData(url,
		// Constants.INTERFACE_setNewPasswordByPhone);
	}

	@Override
	public void updateView(BaseData data)
	{
		startActivity(new Intent(this, LoginActivity.class));
		finish();
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

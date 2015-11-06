package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author 68 手机验证
 */
public class PhoneVerificationActivity extends BaseActivity implements
		OnClickListener, IUserCenterView
{
	/** 发送验证码 */
	private TextView tvResend;
	/** 点击清空输入的验证码 */
	private ImageView ivDelete;
	/** 输入验证码 */
	private EditText etCode;
	/** 完成按钮 */
	private Button btnComplete;
	/** 进度条 */
	private ProgressBar progressBar;
	/** 用户Id */
	private String userId;
	/** UserCenterImpl */
	private UserCenterImpl userCenterImpl;
	/** 倒计时 */
	private MyCountDownTimer myCountDownTimer;
	private EditText etPassword;
	private EditText etRepeatPassword;
	/** 找回密码页面传递的电话号码 */
	private String mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_verification);
		mPhone = getIntent().getStringExtra("PHONE");

		userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("手机验证");

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		etCode = (EditText) findViewById(R.id.et_phoneverific_code);
		ivDelete = (ImageView) findViewById(R.id.iv_phoneverific_delete);
		tvResend = (TextView) findViewById(R.id.tv_phoneverific_resend);
		etPassword = (EditText) findViewById(R.id.et_comfirepassword_password);
		etRepeatPassword = (EditText) findViewById(R.id.et_comfirepassword_repeatpassword);

		btnComplete = (Button) findViewById(R.id.btn_phoneverific_complete);
		btnComplete.setOnClickListener(this);

		backLayout.setOnClickListener(this);
		ivDelete.setOnClickListener(this);
		tvResend.setOnClickListener(this);
		btnComplete.setOnClickListener(this);

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

		case R.id.iv_phoneverific_delete:
			// 输入验证码 清空
			etCode.setText("");
			break;

		case R.id.tv_phoneverific_resend:// 发送验证码
			// 判断网络
			if (!Tools.isNetworkConnected(this))
			{
				Tools.showToast(this, "请检查网络");
				return;
			}
			String url = RequestUrl.getInstance(this).getUrl_sendSmsValidate(
					userId, mPhone, "2");
			userCenterImpl.getRegisterData(url,
					Constants.INTERFACE_sendSmsValidate);
			break;

		case R.id.btn_phoneverific_complete:// 完成
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
			Tools.showToast(this, "请检查网络");
			return;
		}
		// 判断验证码位数
		String code = etCode.getText().toString();
		if (code.trim().length() != 4)
		{
			Tools.showToast(this, "请检查验证码位数");
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
		String pwUrl = RequestUrl.getInstance(this)
				.getUrl_setNewPasswordByPhone(mPhone, code, password,
						confirmPassword);
		userCenterImpl.getRegisterData(pwUrl,
				Constants.INTERFACE_setNewPasswordByPhone);
	}

	@Override
	public void updateView(BaseData data)
	{
		Tools.showToast(this, "修改密码成功");
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
		// 获取验证码成功回调
		etCode.setEnabled(true);
		myCountDownTimer = new MyCountDownTimer(millisInFuture,
				countDownInterval);
		myCountDownTimer.start();
	}

	/**
	 * 倒计时
	 */
	public class MyCountDownTimer extends CountDownTimer
	{

		public MyCountDownTimer(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			tvResend.setText(millisUntilFinished / 1000 + "秒后重新发送");
			tvResend.setClickable(false);
		}

		@Override
		public void onFinish()
		{
			tvResend.setText("获取验证码");
			tvResend.setClickable(true);
		}
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
		if (myCountDownTimer != null)
		{
			myCountDownTimer.cancel();
		}
	}
}

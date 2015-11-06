package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 更换手机号
 * 
 * @author:huoguodong
 * @date：2015-5-10 下午4:48:06
 */
public class ChangePhoneNumberActivity extends BaseActivity implements
		OnClickListener, IUserCenterView
{

	/** 旧手机号码 */
	private TextView tvOldPhone;
	/** 密码 */
	private EditText etPassword;
	/** 新手机号码 */
	private EditText etNewPhone;
	/** 验证码 */
	private EditText etCode;
	/** 获取验证码 */
	private TextView tvGetCode;
	/** 确定 */
	private Button btnComplete;
	private ProgressBar progressBar;
	private String oldPhone;
	private String newPhone;
	private UserCenterImpl userCenterImpl;
	private MyCountDownTimer myCountDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone_number);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		userCenterImpl = new UserCenterImpl(this);
		tvTitle.setText("更换手机号");
		oldPhone = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_CELLPHONE);
		tvOldPhone = (TextView) findViewById(R.id.tv_oldphone);
		tvOldPhone.setText(oldPhone);

		etPassword = (EditText) findViewById(R.id.et_password);
		etNewPhone = (EditText) findViewById(R.id.et_newphone);
		etCode = (EditText) findViewById(R.id.et_register_code);
		tvGetCode = (TextView) findViewById(R.id.tv_register_getcode);
		btnComplete = (Button) findViewById(R.id.btn_register_nextstep);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		backLayout.setOnClickListener(this);
		tvGetCode.setOnClickListener(this);
		btnComplete.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;

		case R.id.tv_register_getcode:
			sendCodeBiz();
			break;

		case R.id.btn_register_nextstep:
			BindPhoneBiz();
			break;
		default:
			break;
		}
	}

	private void BindPhoneBiz()
	{
		String code = etCode.getText().toString();
		String password = etPassword.getText().toString();
		if (TextUtils.isEmpty(password))
		{
			Tools.showToast(this, "请输入密码");
			return;
		}

		if (TextUtils.isEmpty(code))
		{
			Tools.showToast(this, "请输入验证码");
			return;
		}

		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "请检查网络");
			return;
		}
		String url = RequestUrl.getInstance(this).getUrl_updateBindMobile(this,
				newPhone, code, password, oldPhone);
		userCenterImpl.getRegisterData(url,
				Constants.INTERFACE_updateBindMobile);// 更换手机号请求
	}

	private void sendCodeBiz()
	{
		etCode.setEnabled(true);
		String userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		newPhone = etNewPhone.getText().toString();
		if (TextUtils.isEmpty(newPhone))
		{
			Tools.showToast(this, "请输入新手机号码");
			return;
		}
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "请检查网络");
			return;
		}

		String url = RequestUrl.getInstance(this).getUrl_sendSmsValidate(
				userId, newPhone, "3");
		userCenterImpl
				.getRegisterData(url, Constants.INTERFACE_sendSmsValidate);
	}

	@Override
	public void updateView(BaseData data)
	{
		// RegisterData data=(RegisterData) data;
		SharePreferenceUtil.setValue(this, Constants.PREFERENCES_CELLPHONE,
				newPhone);

		Tools.showToast(this, "更换成功");
		EventBus.getDefault().post(newPhone, "changeCellphone");
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
		/** 获取验证码成功,开启倒计时 */
		myCountDownTimer = new MyCountDownTimer(millisInFuture,
				countDownInterval);
		myCountDownTimer.start();
	}

	@Override
	public void upDatePersonal(EditInfoData data)
	{
	}

	@Override
	public void upDateCompany(EditInfoData data)
	{
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
			tvGetCode.setText(millisUntilFinished / 1000 + "秒重获验证码");
			tvGetCode.setClickable(false);
		}

		@Override
		public void onFinish()
		{
			tvGetCode.setText("获取验证码");
			tvGetCode.setClickable(true);
		}
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

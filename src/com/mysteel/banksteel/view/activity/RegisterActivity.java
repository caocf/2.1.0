package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 68 注册
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,
		IUserCenterView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;
	/** 手机号码 */
	private EditText etPhone;
	/** 验证码 */
	private EditText etCode;
	/** 获取验证码 */
	private TextView tvGetCode;
	/** 同意协议 */
	private TextView tvProtocol;
	/** 同意注册cb */
	private CheckBox cbAgree;
	/** 完成 */
	private Button btnComplete;
	/** 发送验证码时的手机号码 */
	private String mPhoneNumber;
	/** 密码框 */
	private EditText etPassword;
	private ProgressBar progressBar;

	private UserCenterImpl registerImpl;
	private MyCountDownTimer myCountDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("注册");

		etPassword = (EditText) findViewById(R.id.et_setpassword_password);
		btnComplete = (Button) findViewById(R.id.btn_setpassword_complete);
		etPhone = (EditText) findViewById(R.id.et_register_phone);
		etCode = (EditText) findViewById(R.id.et_register_code);
		etCode.setEnabled(false);
		tvGetCode = (TextView) findViewById(R.id.tv_register_getcode);
		tvProtocol = (TextView) findViewById(R.id.tv_register_protocol);
		cbAgree = (CheckBox) findViewById(R.id.cb_register_agree);
		cbAgree.setChecked(true);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		backLayout.setOnClickListener(this);
		tvGetCode.setOnClickListener(this);
		tvProtocol.setOnClickListener(this);
		btnComplete.setOnClickListener(this);

		etPhone.addTextChangedListener(new TextWatcher()
		{

			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			private static final int NUM = 11;// 手机号码最大位数

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				selectionStart = etPhone.getSelectionStart();
				selectionEnd = etPhone.getSelectionEnd();
				if (temp.length() > NUM)
				{
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					etPhone.setText(s);
					etPhone.setSelection(tempSelection);
				}
			}
		});

		registerImpl = new UserCenterImpl(RegisterActivity.this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;

		case R.id.tv_register_getcode:// 获取验证码
			getCodeData();
			break;

		case R.id.btn_setpassword_complete:// 注册
			getRegisterData();
			break;

		case R.id.tv_register_protocol:
			Intent i = new Intent(this, SteelCircleActivity.class);
			i.putExtra("DATA", Constants.TRANSACTION_RULE);
			startActivity(i);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取验证码数据
	 */
	private void getCodeData()
	{
		mPhoneNumber = etPhone.getText().toString();
		// 判断手机号码合法
		if (!Tools.checkIsPhoneNumber(mPhoneNumber))
		{
			Tools.showToast(this, "请检查手机号码是否正确");
			return;
		}
		// 判断网络
		if (!Tools.isNetworkConnected(this))
		{
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return;
		}
		String url = RequestUrl.getInstance(this).getUrl_sendSmsValidate("",
				mPhoneNumber, "1");
		registerImpl.getRegisterData(url, Constants.INTERFACE_sendSmsValidate);
	}

	/**
	 * 注册
	 */
	private void getRegisterData()
	{
		// checkbox选中
		if (!cbAgree.isChecked())
		{
			Tools.showToast(this, "请仔细阅读钢银电子商务协议并同意注册");
			return;
		}
		// 判断密码 、验证码合法
		String password = etPassword.getText().toString();
		String code = etCode.getText().toString();
		mPhoneNumber = etPhone.getText().toString();
		// 判断手机号码合法
		if (!Tools.checkIsPhoneNumber(mPhoneNumber))
		{
			Tools.showToast(this, "请检查手机号码是否正确");
			return;
		}

		if (!Tools.checkIsPassword(password))
		{
			Tools.showToast(this, "请检查密码是否输入正确");
			return;
		}
		if (code.trim().length() != 4)
		{
			Tools.showToast(this, "请输入正确的验证码");
			return;
		}
		/** 判断网络 */
		if (!Tools.isNetworkConnected(this))
		{
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return;
		}

		String url = RequestUrl.getInstance(this).getUrl_register(
				etPhone.getText().toString(), password, code);

		registerImpl.getRegisterData(url, Constants.INTERFACE_register);

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

			tvGetCode.setText(millisUntilFinished / 1000 + "秒后重新获取");
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
	public void updateView(BaseData data)
	{
		Tools.showToast(this, "注册成功");
		btnComplete.setClickable(true);
		startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
		if (DEBUG)
		{
			Log.d("TagRegister", "millisInFuture" + millisInFuture);
		}
		etCode.setEnabled(true);// 验证码发出后才允许用户输入验证码
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

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		registerImpl.finishRequest();
		if (myCountDownTimer != null)
		{
			myCountDownTimer.cancel();
		}
	}
}

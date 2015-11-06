package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.UserInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 登陆的页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-10 上午11:29:58
 */
public class LoginActivity extends SwipeBackActivity implements OnClickListener,
		IUserCenterView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "LoginActivity";
	private TextView tvlogin;// 登陆
	private TextView tvforget_pass;// 忘记密码
	private EditText etPhone;// 电话
	private EditText etPassword;// 密码

	private ProgressBar progressBar;
	private UserCenterImpl loginImpl;
	private boolean mIsfrist = false;

	private ImageView iv_phoneverific_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		backLayout.setOnClickListener(this);
		tvRightText.setText("注册");
		tvTitle.setText("钢银助手");
		tvlogin = (TextView) findViewById(R.id.tv_btn_login);
		tvforget_pass = (TextView) findViewById(R.id.tv_forget_pass);
		tvlogin.setOnClickListener(this);
		tvforget_pass.setOnClickListener(this);
		tvRightText.setOnClickListener(this);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		etPhone = (EditText) findViewById(R.id.edit_name);
		etPassword = (EditText) findViewById(R.id.edit_password);
		iv_phoneverific_delete = (ImageView) findViewById(R.id.iv_phoneverific_delete);
		iv_phoneverific_delete.setOnClickListener(this);

		loginImpl = new UserCenterImpl(LoginActivity.this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		etPhone.setText(SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_CELLPHONE));
		etPhone.setSelection(etPhone.getText().toString().length());
		etPassword.setText(SharePreferenceUtil.getString(this,
				Constants.USER_PASSWARD));

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;
		case R.id.tv_btn_login:// 登陆按钮的处理
			logining();
			break;

		case R.id.tv_forget_pass:// 忘记密码
			Intent intent = new Intent(this, FindPasswordActivity.class);
			String phone = etPhone.getText().toString();
			intent.putExtra("PHONE", phone);
			startActivity(intent);
			break;

		case R.id.tv_title_right_text:// 注册
			Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(i);
			break;
		case R.id.iv_phoneverific_delete:
			etPhone.setText("");
			break;
		default:
			break;
		}
	}

	/** 登陆的逻辑编写 */
	private void logining()
	{
		String phone = etPhone.getText().toString().trim();
		String password = etPassword.getText().toString().trim();
		if (!Tools.checkIsPhoneNumber(phone) || TextUtils.isEmpty(phone))
		{
			Tools.showToast(LoginActivity.this, "请填写正确的手机号！");
			return;
		}
		if (TextUtils.isEmpty(password))
		{
			Tools.showToast(LoginActivity.this, "请填写密码！");
			return;
		}

		String url = RequestUrl.getInstance(this).getUrl_login(this, phone,
				password);
		if (DEBUG)
		{
			Log.d(TAG, " 登陆的url = " + url);
		}

		loginImpl.getLoginData(url, Constants.INTERFACE_Login);
	}

	@Override
	public void updateView(BaseData data)
	{
		UserInfoData Udata = (UserInfoData) data;
		if (Udata.getData() == null || Udata.getData().getUser() == null)
		{
//			if (DEBUG)
//			{
//				Tools.showToast(this, "数据为空");
//			}
			return;
		}
		Tools.showToast(this, "登录成功!");
		if (DEBUG)
		{
			Log.d(TAG, "getmobile == " + Udata.getData().getUser().getMobile());
			Log.d(TAG, "getUserID == " + Udata.getData().getUser().getUserId());
			Log.d(TAG, "getname == " + Udata.getData().getUser().getName());
		}
		Tools.saveCache(this, Udata);
		String password = etPassword.getText().toString().trim();
		SharePreferenceUtil.setValue(this, Constants.USER_PASSWARD, password);// 保存密码
		EventBus.getDefault().post("", "mainFragment_loginsuccess");
		loginHuanXin();
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
		loginImpl.finishRequest();
	}

	private void loginHuanXin()
	{

		mIsfrist = SharePreferenceUtil.getBoolean(this, Constants.PREFERENCES_HUANXIN_FALST);
		if (!mIsfrist)
		{
			//先在这里处理
			final String username = SharePreferenceUtil.getString(this, Constants.PREFERENCES_CELLPHONE);
			EMChatManager.getInstance().login(username, "123456", new EMCallBack()
			{
				@Override
				public void onSuccess()
				{
					LogUtils.e("登录成功");
					// 登陆成功，保存用户名密码
					BankSteelApplication.getInstance().setUserName(username);
					BankSteelApplication.getInstance().setPassword("123456");
					SharePreferenceUtil.setValue(LoginActivity.this,Constants.PREFERENCES_HUANXIN_FALST, true);
				};

				@Override
				public void onProgress(int progress, String status)
				{
				}

				@Override
				public void onError(final int code, final String message)
				{

				}
			});
		}
	}
}

package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.impl.SystemManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author 68 意见反馈界面
 */
public class SuggestActivity extends SwipeBackActivity implements OnClickListener,
		ISystemManagerView
{

	/** 建议内容 */
	private EditText etSuggest;
	/** 联系电话 */
	private EditText etPhone;
	/** 还可以输入200字 */
	private TextView tvWordNumber;
	/** 提交按钮 */
	private Button btnSubmit;
	/** 进度条 */
	private ProgressBar progressBar;
	/** SystemManagerImpl */
	private SystemManagerImpl systemManagerImpl;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_suggest);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("我要吐槽");

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		etSuggest = (EditText) findViewById(R.id.et_suggest_content);
		tvWordNumber = (TextView) findViewById(R.id.tv_suggest_wordnumber);
		btnSubmit = (Button) findViewById(R.id.btn_suggest_submit);
		etPhone = (EditText) findViewById(R.id.et_suggest_phone);
		if (Tools.isLogin(this))
		{
			etPhone.setText(SharePreferenceUtil.getString(this,
					Constants.PREFERENCES_CELLPHONE));
		}
		btnSubmit.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		systemManagerImpl = new SystemManagerImpl(this);

		etSuggest.addTextChangedListener(new TextWatcher()
		{

			private int wordNumber;
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			private static final int NUM = 200;

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
				wordNumber = NUM - s.length();
				tvWordNumber.setText("还可以输入" + wordNumber + "字");
				selectionStart = etSuggest.getSelectionStart();
				selectionEnd = etSuggest.getSelectionEnd();
				if (temp.length() > NUM)
				{
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					etSuggest.setText(s);
					etSuggest.setSelection(tempSelection);
				}

			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;

		case R.id.btn_suggest_submit:
			// 提交
			if (Tools.isLogin(this))
			{
				submitSuggest();
			} else
			{
				startActivity(new Intent(this, LoginActivity.class));
			}

		default:
			break;
		}
	}

	private void submitSuggest()
	{
		String userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		String memberId = SharePreferenceUtil.getString(this,
				Constants.USER_MEMBERID);
		String name = SharePreferenceUtil.getString(this, Constants.USER_NAME);
		String phone = etPhone.getText().toString();
		String note = etSuggest.getText().toString();
		if (!Tools.checkIsPhoneNumber(phone))
		{
			Tools.showToast(this, "请输入正确的手机号");
			return;
		}
		if (TextUtils.isEmpty(etSuggest.getText().toString().trim()))
		{
			Tools.showToast(this, "我们真诚的愿请先填写您的意见再提交！");
			return;
		}
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "无网络连接");
			return;
		}

		String url = RequestUrl.getInstance(this).getUrl_suggest(userId,
				memberId, phone, name, note);
		systemManagerImpl.checkUpDate(url, Constants.INTERFACE_suggest);

	}

	@Override
	public void updateView(BaseData data)
	{
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
	public void getDeleteMessage(DeleteMessageData data) {
		
	}

	@Override
	public void checkUpDate(BaseData data)
	{

	}

	@Override
	public void getHistoryMessage(MessageCenterData data)
	{
	}

	@Override
	public void getSign(SignData data)
	{
	}

	@Override
	public void getSuggest(BaseData data)
	{
		Tools.showToast(this, "意见反馈成功");
		mHandler.postDelayed(finishRunable, 3000);

	}

	@Override
	public void getIsPush(BaseData data)
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		systemManagerImpl.finishRequest();
		mHandler.removeCallbacks(finishRunable);
	}

	Runnable finishRunable = new Runnable()
	{
		@Override
		public void run()
		{
			SuggestActivity.this.finish();
		}
	};
}

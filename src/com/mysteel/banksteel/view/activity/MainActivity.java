package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.UserInfoData;
import com.mysteel.banksteel.huanxin.DemoHXSDKHelper;
import com.mysteel.banksteel.huanxin.HXSDKHelper;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.fragments.LeftFragment;
import com.mysteel.banksteel.view.fragments.MainFragment;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 上午9:35:21
 */
public class MainActivity extends BaseActivity implements IUserCenterView,
		EMEventListener
{

	/** DEBUG. */
	// private static final boolean DEBUG = true &
	// BankSteelApplication.GLOBAL_DEBUG;
	/**
	 * TAG.
	 */
	// private static final String TAG = "MainActivity";

	private SlidingPaneLayout slidingPaneLayout;
	private LeftFragment leftFragment;
	private MainFragment mainFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;
	private UserCenterImpl loginImpl;
	private String password;
	private boolean mIsfrist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BankSteelApplication.getInstance();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.activity_slidingpane_main_layout);
		slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanellayout);
		leftFragment = new LeftFragment();
		mainFragment = new MainFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_leftmenu, leftFragment);
		transaction.replace(R.id.slidingpane_main, mainFragment);
		transaction.commit();
		maxMargin = displayMetrics.heightPixels / 10;
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener()
		{

			@Override
			public void onPanelSlide(View panel, float slideOffset)
			{
				int contentMargin = (int) (slideOffset * maxMargin);
				FrameLayout.LayoutParams contentParams = mainFragment
						.getCurrentViewParams();
				contentParams.setMargins(0, contentMargin, 0, contentMargin);
				mainFragment.setCurrentViewPararms(contentParams);

				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
						/ (float) displayMetrics.heightPixels;
				leftFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
				leftFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
				leftFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
				leftFragment.getCurrentView().setPivotY(
						displayMetrics.heightPixels / 2);
				leftFragment.getCurrentView().setAlpha(slideOffset);
			}

			@Override
			public void onPanelOpened(View panel)
			{
				EventBus.getDefault().post(0, "MySlidingPaneLayout");
			}

			@Override
			public void onPanelClosed(View panel)
			{
				EventBus.getDefault().post(1, "MySlidingPaneLayout");
			}
		});

		aotoLogin();
	}

	/**
	 * 自动登录
	 */
	private void aotoLogin()
	{
		String phone = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_CELLPHONE);
		password = SharePreferenceUtil.getString(this, Constants.USER_PASSWARD);
		loginImpl = new UserCenterImpl(this);
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password))
		{
			String url = RequestUrl.getInstance(this).getUrl_login(this, phone,
					password);
			// LogUtils.e(url);
			loginImpl.getLoginData(url, Constants.INTERFACE_Login);
		}
	}

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout getSlidingPaneLayout()
	{
		return slidingPaneLayout;
	}

	private long EXIT_START_TIME = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// return super.onKeyDown(keyCode, event);
		if (slidingPaneLayout.isOpen())
		{
			slidingPaneLayout.closePane();
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (System.currentTimeMillis() - EXIT_START_TIME > 2000)
			{
				EXIT_START_TIME = System.currentTimeMillis();
				Toast.makeText(
						MainActivity.this,
						getResources().getString(
								R.string.double_back_exit_toast),
						Toast.LENGTH_SHORT).show();
			} else
			{
				exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Subscriber(tag = "main_slidingPaneLayout")
	public void openOrClose(String str)
	{
		if (slidingPaneLayout.isOpen())
		{
			slidingPaneLayout.closePane();
		} else
		{
			slidingPaneLayout.openPane();
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("true".equals(data.getStatus()))
		{
			UserInfoData Udata = (UserInfoData) data;
			Tools.showToast(this, "登录成功!");
			Tools.saveCache(this, Udata);
			SharePreferenceUtil.setValue(this, Constants.USER_PASSWARD,
					password);// 保存密码
			loginHuanXin();
		}

	}

	@Override
	public void isShowDialog(boolean flag)
	{
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

	private void loginHuanXin()
	{

		mIsfrist = SharePreferenceUtil.getBoolean(this,
				Constants.PREFERENCES_HUANXIN_FALST);
		if (!mIsfrist)
		{
			// 先在这里处理
			final String username = SharePreferenceUtil.getString(this,
					Constants.PREFERENCES_CELLPHONE);
			// String username = "wushaoge";
			EMChatManager.getInstance().login(username, "123456",
					new EMCallBack()
					{
						@Override
						public void onSuccess()
						{
							LogUtils.e("登录成功");
							// 登陆成功，保存用户名密码
							BankSteelApplication.getInstance().setUserName(
									username);
							BankSteelApplication.getInstance().setPassword(
									"123456");

							// Intent i = new Intent(MainActivity.this,
							// HXMainActivity.class);
							// startActivity(i);
							SharePreferenceUtil.setValue(MainActivity.this,
									Constants.PREFERENCES_HUANXIN_FALST, true);
						}

						;

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

	@Override
	protected void onResume()
	{
		super.onResume();
		// unregister this event listener when this activity enters the
		// background
		DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper
				.getInstance();
		sdkHelper.pushActivity(this);

		// register the event listener when enter the foreground
		EMChatManager.getInstance().registerEventListener(
				this,
				new EMNotifierEvent.Event[]
				{ EMNotifierEvent.Event.EventNewMessage,
						EMNotifierEvent.Event.EventOfflineMessage,
						EMNotifierEvent.Event.EventConversationListChanged });
	}

	@Override
	public void onEvent(EMNotifierEvent event)
	{
		switch (event.getEvent())
		{
		case EventNewMessage: // 普通消息
		{
			EMMessage message = (EMMessage) event.getData();
			// 提示新消息
			HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

			refreshUI();
			break;
		}

		case EventOfflineMessage:
		{
			refreshUI();
			break;
		}

		case EventConversationListChanged:
		{
			refreshUI();
			break;
		}

		default:
			break;
		}
	}

	public void refreshUI()
	{
		EventBus.getDefault().post("", "updateUnreadLabel");
	}
}

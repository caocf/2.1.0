package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteeltwo.R;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 首次进来的欢迎页
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-6-12 下午2:58:57
 */
public class WelcomeActivity extends Activity
{

	private boolean mIsfrist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		initJpush();
		// 首次进来mIsfrist为false
		mIsfrist = SharePreferenceUtil.getBoolean(WelcomeActivity.this,
				Constants.PREFERENCES_WELCOME_FALST);

		Thread timer = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					sleep(2000);
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{// 在这里进行判断
					if (mIsfrist)
					{// mIsfrist = true
						Intent mainIntent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						startActivity(mainIntent);
					} else
					{// app首次登陆为false，需要进入引导页
						Intent mainIntent = new Intent(WelcomeActivity.this,
								GuideActivity.class);
						startActivity(mainIntent);
					}

					interrupt();
				}
			}
		};

		timer.start();
	}

	@Override
	protected void onResume()
	{
		JPushInterface.onResume(WelcomeActivity.this);
		super.onResume();

	}

	@Override
	protected void onPause()
	{
		super.onPause();
		JPushInterface.onPause(WelcomeActivity.this);
		finish();
	}

	/**
	 * 防止app在启动页面的时候突然按返回后，此页面退出，而程序主页面再出来的情况
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}

	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void initJpush()
	{
		JPushInterface.init(getApplicationContext());
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				this);
		builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
		builder.statusBarDrawable = R.drawable.icon;
		JPushInterface.setPushNotificationBuilder(1, builder);

	}
}

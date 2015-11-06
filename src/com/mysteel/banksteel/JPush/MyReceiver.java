package com.mysteel.banksteel.JPush;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.PushOrderDetailActivity;
import com.mysteel.banksteel.view.activity.PushReceiveActivity;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver
{
	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;
	private static final String TAG = "MyReceiverJPush";
	private String type = "";// 推送类型
	private String stanBuyId = "";// 待报价所需传递的id
	/** 人工抢单 */
	private String classTag = "";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "我需要的信息： " + intent.getAction() + ", extras: "
				+ printBundle(bundle));
		// jsonData = {"type":"1"}
		String jsonData = bundle.getString(JPushInterface.EXTRA_EXTRA);
		if (DEBUG)
		{
			Log.d(TAG, "返回的json数据信息：" + jsonData);
		}

		if (!TextUtils.isEmpty(jsonData))
		{
			try
			{
				JSONObject json = new JSONObject(jsonData);
				type = json.optString("type");
				if ("0".equals(type))
				{// 已抢单的状态
					stanBuyId = json.optString("stanBuyId");
					String className = Tools.getCurrentRunningActivityName();
					if (DEBUG)
					{
						Log.d(TAG, "抢单后的type：" + type);
						Log.d(TAG, "抢单后的stanBuyId：" + stanBuyId);
						Log.d(TAG,
								"className :"
										+ Tools.getCurrentRunningActivityName());
					}
					if (!TextUtils.isEmpty(className))
					{
						if (className.contains("HumanServeActivity"))
						{
							if (DEBUG)
							{
								Log.d(TAG, "在HumanServeActivity页面");
							}
							classTag = "HumanServeActivity";

							// 不为空就说明可能在当前页(也有程序在后台运行或者没有运行的情况)
							Intent i = new Intent();
							i.putExtra("stanBuyId", stanBuyId);
							i.setAction(Constants.ACTION_HUMANSERVE);
							context.sendBroadcast(i);

						} else if (className.contains("MatchFindGood"))
						{
							classTag = "MatchFindGood";
							Intent i = new Intent();
							i.putExtra("stanBuyId", stanBuyId);
							i.setAction(Constants.ACTION_MATCHFINDGOOD);
							context.sendBroadcast(i);
						}
						// 这种情况一般是app被home键，或者压根儿就没有启动会得到这个"com.android.launcher2.Launcher".equals(className)
						else
						// 其他的情况一并进入新页面处理
						{
							// TODO 其他的情况
							classTag = "other";
						}

					}
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction()))
		{
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			if (DEBUG)
			{
				Log.d(TAG, "[MyReceiver] 接收Registration Id : ============"
						+ regId);
			}

			// send the Registration Id to your server...

			if (!TextUtils.isEmpty(regId))
			{
				SharePreferenceUtil.setValue(context, Constants.USER_TOKEN,
						regId);// 保存token
			}

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction()))
		{
			if (DEBUG)
			{
				Log.d(TAG,
						"[MyReceiver] 接收到推送下来的自定义消息: "
								+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			}

			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction()))
		{
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			if (DEBUG)
			{
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			}

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction()))
		{
			if (DEBUG)
			{
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			}

			// 打开自定义的Activity
			Intent i = null;
			if ("0".equals(type))
			{// 抢单过来的推送，但是不在当前页的情况

				if (!TextUtils.isEmpty(classTag))
				{// 不为空就说明可能在当前页(也有程序在后台运行或者没有运行的情况)
					if ("other".equals(classTag))
					{
						i = new Intent(context, PushOrderDetailActivity.class);
						i.putExtra("stanBuyId", stanBuyId);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(i);
					}
				}

			} else
			{ // 1:业务员报价，2：订单通过审核，3：订单未通过审核，4：意见反馈
				i = new Intent(context, PushReceiveActivity.class);
				i.putExtras(bundle);
				// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);
			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction()))
		{
			if (DEBUG)
			{
				Log.d(TAG,
						"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
								+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			}

			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction()))
		{
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			if (DEBUG)
			{
				Log.w(TAG, "[MyReceiver]" + intent.getAction()
						+ " connected state change to " + connected);
			}

		} else
		{
			if (DEBUG)
			{
				Log.d(TAG,
						"[MyReceiver] Unhandled intent - " + intent.getAction());
			}

		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle)
	{
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet())
		{
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID))
			{
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE))
			{
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else
			{
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle)
	{
		/*
		 * if (MainActivity.isForeground) { String message =
		 * bundle.getString(JPushInterface.EXTRA_MESSAGE); String extras =
		 * bundle.getString(JPushInterface.EXTRA_EXTRA); Intent msgIntent = new
		 * Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
		 * msgIntent.putExtra(MainActivity.KEY_MESSAGE, message); if
		 * (!ExampleUtil.isEmpty(extras)) { try { JSONObject extraJson = new
		 * JSONObject(extras); if (null != extraJson && extraJson.length() > 0)
		 * { msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras); } } catch
		 * (JSONException e) {
		 * 
		 * }
		 * 
		 * } context.sendBroadcast(msgIntent); }
		 */
	}

}

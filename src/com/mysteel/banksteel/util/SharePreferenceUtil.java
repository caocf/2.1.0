package com.mysteel.banksteel.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 缓存工具类
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-4-29 下午8:55:42
 */
public class SharePreferenceUtil
{

	private static SharedPreferences sp;

	private static SharePreferenceUtil mInstance;

	private SharePreferenceUtil(Context context)
	{
		sp = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES,
				Context.MODE_PRIVATE);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return 是否保存成功
	 */
	public static boolean setValue(Context context, String key, Object value)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		Editor edit = sp.edit();
		if (value instanceof String)
		{
			return edit.putString(key, (String) value).commit();
		} else if (value instanceof Boolean)
		{
			return edit.putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Float)
		{
			return edit.putFloat(key, (Float) value).commit();
		} else if (value instanceof Integer)
		{
			return edit.putInt(key, (Integer) value).commit();
		} else if (value instanceof Long)
		{
			return edit.putLong(key, (Long) value).commit();
		} else if (value instanceof Set)
		{
			new IllegalArgumentException("Value can not be Set object!");
			return false;
		}
		return false;
	}

	public static boolean getBoolean(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, false);
	}

	public static boolean getBooleanDefaultTrue(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, true);
	}

	public static String getString(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getString(key, "");
	}

	public static Float getFloat(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getFloat(key, 0f);
	}

	public static int getInt(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getInt(key, 0);
	}

	public static long getLong(Context context, String key)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}
		return sp.getLong(key, 0);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 *            Context
	 */
	public static synchronized void initializeInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new SharePreferenceUtil(context);
		}
	}

	/**
	 * 获取实例
	 * 
	 * @return sharedPreference 实例
	 */
	public static synchronized SharePreferenceUtil getInstance()
	{
		if (mInstance == null)
		{
			throw new IllegalStateException(
					SharePreferenceUtil.class.getSimpleName()
							+ "is not initialized, call initializeInstance() method first");
		}

		return mInstance;
	}

	/**
	 * 保存DOMAIN
	 * 
	 * @param domain
	 *            String
	 */
	public void setDomain(Context context, String domain)
	{
		setValue(context, Constants.PREFERENCES_DOMAIN, domain);
	}

	/**
	 * 获取 domain
	 * 
	 * @return domain String
	 */
	public String getDomain(Context context)
	{
		return getString(context, Constants.PREFERENCES_DOMAIN);
	}

	public static void clearPreFerence(Context context)
	{
		if (sp == null)
		{
			sp = context.getSharedPreferences(
					Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		}

		Editor editor = sp.edit();
		editor.clear();
		editor.commit();

	}
}

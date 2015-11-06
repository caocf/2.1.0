package com.mysteel.banksteel.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 图片加载
 * 
 * @author:huoguodong
 * @date：2015-4-22 上午10:59:46
 */
public class ImageLoader
{

	private LruCache<String, Bitmap> mMermoryCachre;
	private ExecutorService mEService;
	private static ImageLoader mLoader;

	private ImageLoader()
	{
		int maxSize = (int) Runtime.getRuntime().maxMemory() / 4;
		mMermoryCachre = new LruCache<String, Bitmap>(maxSize)
		{
			@Override
			protected int sizeOf(String key, Bitmap value)
			{
				return value.getRowBytes() * value.getHeight();
			}
		};
		getThreadPool();
	};

	public static ImageLoader getInstance()
	{
		synchronized (ImageLoader.class)
		{
			if (mLoader == null)
			{
				mLoader = new ImageLoader();
			}
			return mLoader;
		}
	}

	private void getThreadPool()
	{
		mEService = Executors.newFixedThreadPool(2);
	}

	@SuppressLint("HandlerLeak")
	public Bitmap loadImage(final String path, final OnCallBackListener listener)
	{
		Bitmap bitmap = getFormCache(path);
		final Handler handle = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				Bitmap bitmap = (Bitmap) msg.obj;
				if (listener != null)
				{
					listener.setBitmap(bitmap, path);
				}
				super.handleMessage(msg);
			}
		};
		if (bitmap == null)
		{
			mEService.execute(new Thread()
			{
				@Override
				public void run()
				{
					Bitmap bitmap = BitmapUtil.getThumbImage(path);
					Message msg = new Message();
					msg.obj = bitmap;
					handle.sendMessage(msg);
					addtoCache(path, bitmap);
				}
			});
			return null;
		} else
		{
			return bitmap;
		}
	}

	private Bitmap getFormCache(String path)
	{
		return mMermoryCachre.get(path);
	}

	public void cancleTask()
	{
		if (mEService != null)
		{
			mEService.shutdown();
		}
	}

	private void addtoCache(String path, Bitmap bitmap)
	{
		if (bitmap != null)
		{
			mMermoryCachre.put(path, bitmap);
		}
	}

	public interface OnCallBackListener
	{
		public void setBitmap(Bitmap bitmap, String url);
	}
}

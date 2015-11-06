package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyLoadImageView
{
	private ImageLoader mImageLoader = null;
	private BitmapCache mBitmapCache;

	private ImageLoader.ImageListener imageLoader_listener;
	private RequestQueue requestQueue;

	public VolleyLoadImageView(Context context, ImageView imageView,
			int defaultResourceId, int errorReSourceId)
	{
		imageLoader_listener = ImageLoader.getImageListener(imageView,
				defaultResourceId, errorReSourceId);
		mBitmapCache = new BitmapCache();
		requestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(requestQueue, mBitmapCache);
	}

	public ImageLoader getmImageLoader()
	{
		return mImageLoader;
	}

	public void setmImageLoader(ImageLoader mImageLoader)
	{
		this.mImageLoader = mImageLoader;
	}

	public ImageLoader.ImageListener getImageLoader_listener()
	{
		return imageLoader_listener;
	}

	public void setImageLoader_listener(
			ImageLoader.ImageListener imageLoader_listener)
	{
		this.imageLoader_listener = imageLoader_listener;
	}

	class BitmapCache implements ImageLoader.ImageCache
	{
		private LruCache<String, Bitmap> mCache;
		private int sizeValue;

		public BitmapCache()
		{
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize)
			{
				@Override
				protected int sizeOf(String key, Bitmap value)
				{
					sizeValue = value.getRowBytes() * value.getHeight();
					return sizeValue;
				}

			};
		}

		@Override
		public Bitmap getBitmap(String url)
		{
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap)
		{
			mCache.put(url, bitmap);
		}
	}

}

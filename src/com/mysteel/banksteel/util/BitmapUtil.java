package com.mysteel.banksteel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteeltwo.R;

/**
 * 压缩图片
 * 
 * @author:huoguodong
 * @date：2015-5-8 下午3:52:37
 */
public class BitmapUtil
{

	private final static int size = 200;

	public static Bitmap getThumbImage(String path)
	{
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, option);
		option.inSampleSize = getScale(option);
		option.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, option);
	}

	private static int getScale(Options options)
	{
		int inSampleSize = 1;

		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;
		if (bitmapWidth > size || bitmapHeight > size)
		{
			while (true)
			{
				if (bitmapWidth / 2 < size && bitmapHeight / 2 < size)
					break;
				bitmapWidth = bitmapWidth / 2;
				bitmapHeight = bitmapHeight / 2;
				inSampleSize = inSampleSize * 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * 下载图片
	 * 
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void loadImage(Context context, String url, final ImageView iv)
	{
		RequestQueue queue = Volley.newRequestQueue(context);// 获取requestQueue对象
		ImageRequest request = new ImageRequest(url,
				new Response.Listener<Bitmap>()
				{

					@Override
					public void onResponse(Bitmap response)
					{
						iv.setImageBitmap(response);
						save(response, Tools.AVATAR_PATH);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{
						iv.setImageResource(R.drawable.nophoto);
					}
				});
		queue.add(request);
	}

	/**
	 * 把bitmap对象 进行jpeg格式压缩 并且输出到具体路径
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void save(Bitmap bitmap, String path)
	{
//		Log.i("info", "saveFile:" + path);
		File file = new File(path);
		// 若父目录不存在 则创建父目录
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
				// 把bitmap输出到该文件中
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		// 把bitmap输出到该文件中
		try
		{
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void showImageByNetworkImageView(String url,
			NetworkImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				200);
		ImageCache imageCache = new ImageCache()
		{
			@Override
			public void putBitmap(String key, Bitmap value)
			{
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key)
			{
				return lruCache.get(key);
			}

		};
		ImageLoader imageLoader = new ImageLoader(
				BankSteelApplication.requestQueue, imageCache);
		imgView.setTag(url);
		imgView.setImageUrl(url, imageLoader);
	}
}

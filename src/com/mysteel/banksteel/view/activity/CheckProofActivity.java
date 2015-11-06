package com.mysteel.banksteel.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.huanxin.PhotoView;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

/**
 * 查看凭证页面
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-22 下午1:32:32
 */
public class CheckProofActivity extends BaseActivity implements OnClickListener
{

	private PhotoView img_checkProof;
	private String imgUrl;
	@SuppressWarnings("unused")
//	private PhotoViewAttacher mAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkproof);
		initView();
	}

	protected void initView()
	{
		super.initViews();
		Bundle b = getIntent().getExtras();
		imgUrl = b.getString("imgUrl");
		tvTitle.setText("凭证图片");
		backLayout.setOnClickListener(this);
		tvRightText.setVisibility(View.GONE);

		img_checkProof = (PhotoView) findViewById(R.id.img_checkproof);
		if (!TextUtils.isEmpty(imgUrl))
		{
			loadImage(imgUrl, img_checkProof);
		} else
		{
			Tools.showToast(this, "沒有凭证图片，请先上传凭证！");
		}
//		mAttacher = new PhotoViewAttacher(img_checkProof);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;
		default:
			break;
		}
	}
	
	private void loadImage(String imageUrl, ImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
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
		ImageListener listener = ImageLoader.getImageListener(imgView,
				R.drawable.pictures_no, R.drawable.pictures_no);
		imageLoader.get(imageUrl, listener);
	}

	public void showImageByNetworkImageView(String url, NetworkImageView imgView)
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

package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 关于我们页面
 * 
 * @author:huoguodong
 * @date：2015-5-10 上午11:44:26
 */
public class AboutUsActivity extends SwipeBackActivity implements OnClickListener
{
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		initViews();
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("关于我们");
		backLayout.setOnClickListener(this);

		mWebView = (WebView) findViewById(R.id.wv_aboutus);
		mWebView.getSettings().setSupportMultipleWindows(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		// mWebView.getSettings().setSupportZoom(true);
		// mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new WebViewClient()
		{
			// 点击超链接的时候重新在原来进程上加载URL
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}

			// webview加载完成
			@Override
			public void onPageFinished(WebView view, String url)
			{
			}
		});
		mWebView.loadUrl(RequestUrl.ABOUTUS_DOMIN);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 左侧菜单(返回上页)
		case R.id.menu_layout:
			if (mWebView.canGoBack())
			{
				mWebView.goBack();
			}else{
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
		{
			mWebView.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

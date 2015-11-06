package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 功能介绍的页面
 *
 */
public class FunctionAndIntroduceActivity extends BaseActivity implements
		OnClickListener
{
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_introduce);
		initViews();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("功能介绍");
		backLayout.setOnClickListener(this);

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setUseWideViewPort(true);// 无限缩放
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportMultipleWindows(true);
		webView.getSettings().setAppCacheMaxSize(8*1024*1024);   //缓存最多可以有8M
		webView.setWebViewClient(new WebViewClient()
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
		webView.loadUrl(RequestUrl.FUNCTION_INTRODUCE);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			if (webView.canGoBack())
			{
				webView.goBack();
			} else
			{
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed()
	{
		if (webView.canGoBack())
		{
			webView.goBack();
		} else
		{
			finish();
		}
	}
}

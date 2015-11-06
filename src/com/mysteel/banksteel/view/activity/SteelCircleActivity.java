package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import com.mysteel.banksteel.ao.impl.SystemManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.StringUtils;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;
import com.mysteel.banksteeltwo.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @author 68 钢圈/网络交易规则 双界面
 */
public class SteelCircleActivity extends BaseShareActivity implements
		OnClickListener, IScoreView, ISystemManagerView
{

	/** webView */
	private WebView mWebView;
	/** 判断上一个页面的数据 */
	private String forwordPage;
	/** 进度条 */
	private ProgressBar progressBar;
	/** SystemManagerImpl */
	private SystemManagerImpl SystemManagerImpl;
	private UMSocialService mController;
	private MyPostListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steelcircle);
		forwordPage = getIntent().getStringExtra("DATA");
		initViews();
	}

	@SuppressLint("SetJavaScriptEnabled")
	protected void initViews()
	{
		super.initViews();

		SystemManagerImpl = new SystemManagerImpl(this);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		backLayout.setOnClickListener(this);
		mWebView = (WebView) findViewById(R.id.wv_steelcircle);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportMultipleWindows(true);
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

		if ((!StringUtils.isEmpty(forwordPage))
				&& Constants.STEEL_CIRCLE.equals(forwordPage))
		{
			tvTitle.setText("钢圈");
			tvRightText.setText("分享");
			rightLayout.setOnClickListener(this);
			mWebView.loadUrl(RequestUrl.CIRCLE_DOMIN);
			listener = new MyPostListener();
		} else if ((!StringUtils.isEmpty(forwordPage))
				&& Constants.TRANSACTION_RULE.equals(forwordPage))
		{
			tvTitle.setText("钢银交易协议");
			String url = RequestUrl.getInstance(this).getUrl_getRule(this);
			SystemManagerImpl.getSign(url, Constants.INTERFACE_rule);
		} else if ((!StringUtils.isEmpty(forwordPage))
				&& Constants.HOMEPAGE.equals(forwordPage))
		{
			tvTitle.setText("钢银首页");
			mWebView.loadUrl(RequestUrl.DOMAIN_WEB);
		}
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
			} else
			{
				finish();
			}
			break;
		// 右侧菜单(分享)
		case R.id.share_layout:
			if (Tools.isLogin(this))
			{
				shareToFriends();
			} else
			{
				Intent i = new Intent(this, LoginActivity.class);
				startActivity(i);
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

	@Override
	public void isShowDialog(boolean flag)
	{
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void searchMyScore(SearchMyScoreData data)
	{
	}

	@Override
	public void earnScore(EarnScoreData data)
	{
		// 分享成功后,获取积分协议调用成功,积分+2
//		EventBus.getDefault().post("", "scoreChange");
//		Tools.showToast(this, "+2");
	}

	@Override
	public void getSign(SignData data)
	{
		String url = data.getData();
		mWebView.loadUrl(url);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		SystemManagerImpl.finishRequest();
		if (mController!=null&&listener != null)
		{
			mController.unregisterListener(listener);
		}
	}
	
	@Override
	public void getDeleteMessage(DeleteMessageData data) {
		
	}

	@Override
	public void checkUpDate(BaseData data)
	{
	}

	@Override
	public void getHistoryMessage(MessageCenterData data)
	{
	}

	@Override
	public void getSuggest(BaseData data)
	{
	}

	@Override
	public void getIsPush(BaseData data)
	{
	}

	@Override
	public void updateView(BaseData data)
	{
	}

	/**
	 * 友盟分享至各平台
	 */
	private void shareToFriends()
	{
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 启动分享
		addUmengSDK();
		// 弹出分享面板
		mController.openShare(this, false);
	}

	private void addUmengSDK()
	{
		// 首先在您的Activity中添加如下成员变量
		mController.setShareContent("com.umeng.share");

		/** 设置分享面板上显示的平台 */
		mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.QQ,
				SHARE_MEDIA.SMS, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);

		/** 支持短信分享 */
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
		SmsShareContent smsContent = new SmsShareContent();
		smsContent.setShareContent(getResources().getString(
				R.string.share_gangquan_content)
				+ RequestUrl.CIRCLE_DOMIN);
		// smsContent.setShareImage(new UMImage(this, R.drawable.icon));
		mController.setShareMedia(smsContent);

		String appID = "wx2df3aa443f064c4d";
		String appSecret = "b4934655607d0f38df86b9d815d6e602";
		/** 添加微信平台分享 */
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(getResources().getString(
				R.string.share_gangquan_content));
		weixinContent.setTitle(getResources().getString(
				R.string.share_gangquan_title));
		weixinContent.setTargetUrl(RequestUrl.CIRCLE_DOMIN);
		weixinContent.setShareImage(new UMImage(this, R.drawable.icon_share));
		mController.setShareMedia(weixinContent);

		/*** 添加微信朋友圈分享 */
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(getResources().getString(
				R.string.share_gangquan_content));
		circleMedia.setTitle(getResources().getString(
				R.string.share_gangquan_title));
		circleMedia.setShareImage(new UMImage(this, R.drawable.icon_share));
		circleMedia.setTargetUrl(RequestUrl.CIRCLE_DOMIN);
		mController.setShareMedia(circleMedia);

		/** 支持QQ分享 */
		String appId = "1104594561";// 开发者在QQ互联申请的APP
		String appkey = "YPfZ0rEJY5Bp1q2k";// 开发者在QQ互联申请的APP kEY
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appkey);
		qqSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(getResources().getString(
				R.string.share_gangquan_content));
		qqShareContent.setTitle(getResources().getString(
				R.string.share_gangquan_title));
		qqShareContent.setShareImage(new UMImage(this, R.drawable.icon_share));
		qqShareContent.setTargetUrl(RequestUrl.CIRCLE_DOMIN);
		mController.setShareMedia(qqShareContent);

		/** 支持QQ空间分享 */
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
				appkey);
		qZoneSsoHandler.addToSocialSDK();

		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(getResources().getString(
				R.string.share_gangquan_content));
		qzone.setTargetUrl(RequestUrl.CIRCLE_DOMIN);
		qzone.setTitle(getResources().getString(R.string.share_gangquan_title));
		qzone.setShareImage(new UMImage(this, R.drawable.icon_share));
		mController.setShareMedia(qzone);

		/** 支持新浪微博分享 */
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		SinaShareContent sina = new SinaShareContent();
		sina.setTitle(getResources().getString(R.string.share_gangquan_title));
		sina.setShareContent(getResources().getString(
				R.string.share_gangquan_content)
				+ RequestUrl.CIRCLE_DOMIN);
		sina.setTargetUrl(RequestUrl.CIRCLE_DOMIN);
		sina.setShareImage(new UMImage(this, R.drawable.icon_share));
		mController.setShareMedia(sina);

		/** 关闭友盟分享的Toast提示 */
		mController.getConfig().closeToast();

		/** 注册分享回调 */
		mController.registerListener(listener);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null)
		{
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	
}

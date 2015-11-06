package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 分享钢银助手app页面
 * 
 * @author:huoguodong
 * @date：2015-5-9 下午4:34:36
 */
public class ShareAppActivity extends BaseShareActivity implements
		OnClickListener, IScoreView
{
	/** 分享 */
	private LinearLayout llShare;
	/** 进度条 */
	private ProgressBar progressBar;
	private UMSocialService mController;
	private ImageView img_share;
	private MyPostListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_app);

		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("分享钢银助手APP");

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		llShare = (LinearLayout) findViewById(R.id.ll_shareimmediate);
		backLayout.setOnClickListener(this);
		llShare.setOnClickListener(this);
		img_share = (ImageView) findViewById(R.id.img_share);
		img_share.setImageResource(R.drawable.share_img);

		listener = new MyPostListener();
	}

	@Override
	public void updateView(BaseData data)
	{
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
		EventBus.getDefault().post("", "scoreChange");
		Tools.showToast(this, "积分+2");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (mController != null && listener != null)
		{
			mController.unregisterListener(listener);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;

		case R.id.ll_shareimmediate:
			if (Tools.isLogin(ShareAppActivity.this))
			{
				shareToFriends();
			} else
			{
				startActivity(new Intent(ShareAppActivity.this,
						LoginActivity.class));
			}
			break;

		default:
			break;
		}
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
		// 首先在您的Activity中添加如下成员变量,表示默认分享内容
		mController.setShareContent("扫描二维码上android app下载最新版钢银助手 "
				+ RequestUrl.YAOQING);

		/** 设置分享面板上显示的平台 */
		mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.QQ,
				SHARE_MEDIA.SMS, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);

		/** 支持短信分享 */
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
		SmsShareContent smsContent = new SmsShareContent();
		smsContent.setShareContent("【钢银交易平台】钢银助手客户端安装地址 " + RequestUrl.YaoQing);
		mController.setShareMedia(smsContent);

		/** 添加微信平台分享 */
		String appID = "wx2df3aa443f064c4d";
		String appSecret = "b4934655607d0f38df86b9d815d6e602";
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(getResources().getString(
				R.string.share_yaoqing_content));
		weixinContent.setTitle(getResources().getString(
				R.string.share_yaoqing_title));
		weixinContent.setTargetUrl(RequestUrl.YaoQing);
		weixinContent.setShareImage(new UMImage(this, R.drawable.icon_share));
		mController.setShareMedia(weixinContent);

		/*** 添加微信朋友圈分享 */
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(getResources().getString(
				R.string.share_yaoqing_content));
		circleMedia.setTitle(getResources().getString(
				R.string.share_yaoqing_title));
		circleMedia.setShareImage(new UMImage(this, R.drawable.icon_share));
		circleMedia.setTargetUrl(RequestUrl.YaoQing);
		mController.setShareMedia(circleMedia);

		/** 支持QQ分享 */
		String appId = "1104594561";// 开发者在QQ互联申请的APP
		String appkey = "YPfZ0rEJY5Bp1q2k";// 开发者在QQ互联申请的APP KEY
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appkey);
		qqSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(getResources().getString(
				R.string.share_yaoqing_content));
		qqShareContent.setTitle(getResources().getString(
				R.string.share_yaoqing_title));
		qqShareContent.setShareImage(new UMImage(this, R.drawable.icon_share));
		qqShareContent.setTargetUrl(RequestUrl.YaoQing);
		mController.setShareMedia(qqShareContent);

		/** 支持QQ空间分享 */
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
				appkey);
		qZoneSsoHandler.addToSocialSDK();

		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(getResources().getString(
				R.string.share_yaoqing_content));
		qzone.setTargetUrl(RequestUrl.YaoQing);
		qzone.setTitle(getResources().getString(R.string.share_yaoqing_title));
		qzone.setShareImage(new UMImage(this, R.drawable.icon_share));
		mController.setShareMedia(qzone);

		/** 支持新浪微博分享 */
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		SinaShareContent sina = new SinaShareContent();
		sina.setTitle(getResources().getString(R.string.share_yaoqing_title));
		sina.setShareContent("赶紧下载钢银助手，买钢材so easy，老板再也不用担心我买贵的钢材！"
				+ RequestUrl.YAOQING);
		sina.setTargetUrl(RequestUrl.YaoQing);
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

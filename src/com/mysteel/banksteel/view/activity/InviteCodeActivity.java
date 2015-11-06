package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import org.simple.eventbus.EventBus;

/**
 * 邀请码
 *
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class InviteCodeActivity extends BaseShareActivity implements OnClickListener, IScoreView
{

	private Context mContext;

	private EditText et_invitecode; //邀请码
	private Button btn_ok; //确定兑换
	private TextView tv_invite_code; //我的邀请码
	private Button btn_send_invite; //发送邀请
	private TextView tv_user_score; //已邀请X位好友,已获得X积分
	private UMSocialService mController;
	private MyPostListener listener;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_code);
		mContext = this;
		initViews();
		initData();
	}


	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("邀请好友");
		backLayout.setOnClickListener(this);

		et_invitecode = (EditText)this.findViewById(R.id.et_invitecode);
		btn_ok = (Button)this.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		tv_invite_code = (TextView)this.findViewById(R.id.tv_invite_code);
		btn_send_invite = (Button)this.findViewById(R.id.btn_send_invite);
		btn_send_invite.setOnClickListener(this);
		tv_user_score = (TextView)this.findViewById(R.id.tv_user_score);
		listener = new MyPostListener();

	}

	private void initData()
	{

	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.menu_layout:
				// 返回菜单
				finish();
				break;

			case R.id.btn_send_invite://分享按钮事件
				if (Tools.isLogin(InviteCodeActivity.this))
				{
					shareToFriends();
				} else
				{
					startActivity(new Intent(InviteCodeActivity.this,
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
		smsContent.setShareContent("【钢银交易平台】钢银助手客户端安装地址 " + RequestUrl.YAOQING);
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
		weixinContent.setShareImage(new UMImage(this, R.drawable.icon));
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
		circleMedia.setShareImage(new UMImage(this, R.drawable.icon));
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
		qqShareContent.setShareImage(new UMImage(this, R.drawable.icon));
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
		qzone.setShareImage(new UMImage(this, R.drawable.icon));
		mController.setShareMedia(qzone);

		/** 支持新浪微博分享 */
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		SinaShareContent sina = new SinaShareContent();
		sina.setTitle(getResources().getString(R.string.share_yaoqing_title));
		sina.setShareContent("赶紧下载钢银助手，买钢材so easy，老板再也不用担心我买贵的钢材！"
				+ RequestUrl.YAOQING);
		sina.setTargetUrl(RequestUrl.YaoQing);
		sina.setShareImage(new UMImage(this, R.drawable.icon));
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
	public void searchMyScore(SearchMyScoreData data)
	{

	}

	@Override
	public void earnScore(EarnScoreData data)
	{
//        EventBus.getDefault().post("", "scoreChange");
//        Tools.showToast(this, "积分+2");
	}

	@Override
	public void updateView(BaseData data)
	{

	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}
}


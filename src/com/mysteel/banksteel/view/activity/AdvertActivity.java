package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author 68 广告界面
 */
public class AdvertActivity extends BaseShareActivity implements OnClickListener
{
    private String mUrl;
    private WebView mWebView;
    private String mTitle = "";
    private UMSocialService mController;
    private MyPostListener listener;
    private String shareTitle = "";
    private String shareContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        String content = getIntent().getStringExtra("TITLE");
        String [] split = content.split("\\-");
        if(split.length == 3)
        {
            mTitle = split[0];
            shareTitle = split[1];
            shareContent = split[2];
        }else
        {
            mTitle = "钢银助手";
        }
        mUrl = getIntent().getStringExtra("URL");
        initViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews()
    {
        super.initViews();

        tvTitle.setText(mTitle);
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText.setText("分享");
        backLayout.setOnClickListener(this);
        tvRightText.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.wv_advert);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
//		mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);// 无限缩放
        mWebView.setInitialScale(70);// 初始缩放值
        mWebView.setHorizontalScrollbarOverlay(true);
        //mWebView.getSettings().setAppCacheMaxSize(8*1024*1024);   //缓存最多可以有8M
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
        mWebView.loadUrl(mUrl);

        listener = new MyPostListener();

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

            case R.id.tv_title_right_text://分享事件
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
//        smsContent.setShareContent(getResources().getString(
//                R.string.share_gangquan_content)
//                + RequestUrl.CIRCLE_DOMIN);
        smsContent.setShareContent(shareContent);
        smsContent.setShareImage(new UMImage(this, R.drawable.icon_share));
        smsContent.setShareContent(mUrl);
        mController.setShareMedia(smsContent);

        String appID = "wx2df3aa443f064c4d";
        String appSecret = "b4934655607d0f38df86b9d815d6e602";
        /** 添加微信平台分享 */
        UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
        wxHandler.addToSocialSDK();

        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        weixinContent.setShareContent(getResources().getString(
//                R.string.share_gangquan_content));
//        weixinContent.setTitle(getResources().getString(
//                R.string.share_gangquan_title));
        weixinContent.setShareContent(shareContent);
        weixinContent.setTitle(shareTitle);
        weixinContent.setTargetUrl(mUrl);
        weixinContent.setShareImage(new UMImage(this, R.drawable.icon_share));
        mController.setShareMedia(weixinContent);

        /*** 添加微信朋友圈分享 */
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(getResources().getString(
//                R.string.share_gangquan_content));
//        circleMedia.setTitle(getResources().getString(
//                R.string.share_gangquan_title));

        circleMedia.setShareContent(shareContent);
        circleMedia.setTitle(shareTitle);
        circleMedia.setShareImage(new UMImage(this, R.drawable.icon_share));
        circleMedia.setTargetUrl(mUrl);
        mController.setShareMedia(circleMedia);

        /** 支持QQ分享 */
        String appId = "1104594561";// 开发者在QQ互联申请的APP
        String appkey = "YPfZ0rEJY5Bp1q2k";// 开发者在QQ互联申请的APP kEY
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appkey);
        qqSsoHandler.addToSocialSDK();

        QQShareContent qqShareContent = new QQShareContent();
//        qqShareContent.setShareContent(getResources().getString(
//                R.string.share_gangquan_content));
//        qqShareContent.setTitle(getResources().getString(
//                R.string.share_gangquan_title));
        qqShareContent.setShareContent(shareContent);
        qqShareContent.setTitle(shareTitle);
        qqShareContent.setShareImage(new UMImage(this, R.drawable.icon_share));
        qqShareContent.setTargetUrl(mUrl);
        mController.setShareMedia(qqShareContent);

        /** 支持QQ空间分享 */
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
                appkey);
        qZoneSsoHandler.addToSocialSDK();

        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(shareContent);
        qzone.setTargetUrl(mUrl);
        qzone.setTitle(shareTitle);
        qzone.setShareImage(new UMImage(this, R.drawable.icon_share));
        mController.setShareMedia(qzone);

        /** 支持新浪微博分享 */
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        SinaShareContent sina = new SinaShareContent();
        sina.setTitle(shareTitle);
        sina.setShareContent(shareContent);
        sina.setTargetUrl(mUrl);
        sina.setShareImage(new UMImage(this, R.drawable.icon_share));
        mController.setShareMedia(sina);

        /** 关闭友盟分享的Toast提示 */
        mController.getConfig().closeToast();

        /** 注册分享回调 */
        mController.registerListener(listener);
    }

    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
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
}

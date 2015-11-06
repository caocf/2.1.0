package com.mysteel.banksteel.util;

import android.app.Activity;

import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 自定义分享内容
 * 
 * @author 68
 */
public class CustomShare
{

	/**
	 * 自定义分享内容
	 * 
	 * @param activity
	 * 
	 * @param controller
	 *            UMSocialService,在activity中直接传mController,
	 * 
	 * @param listener
	 *            SnsPostListener,在activity中直接传mSnsPostListener,
	 *            该activity必须继承BaseShareActivity,并重写sendEarnScore方法
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param url
	 *            目标url
	 * @param image
	 *            图片
	 */
	public static void customeShareContent(Activity activity,
			UMSocialService controller, SnsPostListener listener, String title,
			String content, String url, int image)
	{
		if (listener == null)
		{
			controller.openShare(activity, false);
		} else
		{
			controller.openShare(activity, listener);
		}

		/** QQ分享 */
		QQShareContent qqShareContent = new QQShareContent();
		// 设置分享文字
		qqShareContent.setShareContent(content);
		// 设置分享title
		qqShareContent.setTitle(title);
		// 设置分享图片
		qqShareContent.setShareImage(new UMImage(activity, image));
		// 设置点击分享内容的跳转链接
		qqShareContent.setTargetUrl(url);
		controller.setShareMedia(qqShareContent);

		/** QQ空间 */
		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(content);
		// 设置点击消息的跳转URL
		qzone.setTargetUrl(url);
		// 设置分享内容的标题
		qzone.setTitle(title);
		// 设置分享图片
		qzone.setShareImage(new UMImage(activity, image));
		controller.setShareMedia(qzone);

		/** 新浪微博 */
		SinaShareContent sina = new SinaShareContent();
		// 设置分享文字
		sina.setShareContent(content);
		// 设置点击消息的跳转URL
		sina.setTargetUrl(url);
		// 设置分享内容的标题
		sina.setTitle(title);
		// 设置分享图片
		sina.setShareImage(new UMImage(activity, image));
		controller.setShareMedia(sina);

		/** 微信分享 */
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置title
		weixinContent.setTitle(title);
		// if (content != null)// ==null,则为纯图片分享
		// {
		weixinContent.setShareContent(content);
		// }
		// 设置分享内容跳转URL
		weixinContent.setTargetUrl(url);
		// 设置分享图片
		// if (image != null)// ==null,则为纯文本分享
		// {
		weixinContent.setShareImage(new UMImage(activity, image));
		// }
		controller.setShareMedia(weixinContent);

		/** 微信朋友圈分享 */
		CircleShareContent circleMedia = new CircleShareContent();
		// if (content != null)
		// {
		circleMedia.setShareContent(content);
		// }
		// 设置朋友圈title
		circleMedia.setTitle(title);
		// if (image != null)
		// {
		circleMedia.setShareImage(new UMImage(activity, image));
		// }
		circleMedia.setTargetUrl(url);
		controller.setShareMedia(circleMedia);
	}
}

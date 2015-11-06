package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import org.simple.eventbus.EventBus;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mysteel.banksteel.ao.impl.ScoreImp;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.ScoreConvert.Data.Order;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteeltwo.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 已兑换订单adapter
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 下午1:56:50
 */
public class ConvertOrderAdapter extends BaseAdapter implements IScoreView
{

	private LayoutInflater inflater;
	private Activity activity;
	private ArrayList<Order> data;
	private UMSocialService mController;
	private ScoreImp scoreImp;
	// private static final String DOMAIN = "http://192.168.100.118:8081";
	/** 分享的图片路劲 */
	private String shareImg;
	/** 分享的商品名称 */
	private String shareName;
	/** 分享商品的id */
	private String shareId = "";
	private MyPostListener listener=new MyPostListener();

	public void unregisterListener()
	{
		if (mController != null && listener != null)
		{
			mController.unregisterListener(listener);
		}
	}

	public void finishScoreImp()
	{
		scoreImp.finishRequest();
	}

	public synchronized UMSocialService getmController()
	{
		return mController;
	}

	public ConvertOrderAdapter(Activity activity, ArrayList<Order> data)
	{
		this.activity = activity;
		this.data = data;
		inflater = LayoutInflater.from(activity);
		scoreImp = new ScoreImp(activity, this);
	}

	public void reSetListView(ArrayList<Order> data)
	{
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (data == null)
		{
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder holder = null;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.listview_presents_item,
					null);
			holder = new Holder();
			holder.imPrePicture = (ImageView) convertView
					.findViewById(R.id.im_present_picture);
			holder.tvPreName = (TextView) convertView
					.findViewById(R.id.tv_present_name);
			holder.tvScore = (TextView) convertView
					.findViewById(R.id.tv_score_value);
			holder.llThird = (LinearLayout) convertView
					.findViewById(R.id.ll_third);
			holder.tvPreCount = (TextView) convertView
					.findViewById(R.id.tv_present_count);
			holder.tvReceiveMan = (TextView) convertView
					.findViewById(R.id.tv_receive_man);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_address);
			holder.btnExchange = (Button) convertView
					.findViewById(R.id.btn_exchange);
			holder.rlLastLine = (RelativeLayout) convertView
					.findViewById(R.id.rl_exchange_date);
			holder.tvExchangeDate = (TextView) convertView
					.findViewById(R.id.tv_exchange_date);
			holder.rlShare = (RelativeLayout) convertView
					.findViewById(R.id.rl_exchange_present_share);

			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}

		final Order odata = data.get(position);
		holder.tvPreName.setText(odata.getGoodsName());// 商品名称
		holder.tvScore.setText(odata.getGangBengAmount());// 积分 或者 钢镚
		holder.tvReceiveMan.setText(odata.getConsignee() + "  "
				+ odata.getMobile());// 收件人+ 号码
		holder.tvAddress.setText(odata.getAddress());// 地址
		holder.tvExchangeDate.setText(odata.getCreateTime());// 订单时间

		// String imageUrl = RequestUrl.jifenURL + odata.getGoodsImg();
		String imageUrl = odata.getGoodsImg();

		loadImage(imageUrl, holder.imPrePicture);

		holder.rlShare.setTag(position);
		holder.rlShare.setOnClickListener(new OnShareClickListener());
		return convertView;
	}

	public class Holder
	{
		public ImageView imPrePicture;
		public TextView tvPreName;
		public TextView tvScore;

		public LinearLayout llThird;
		public TextView tvPreCount;
		public TextView tvReceiveMan;
		public TextView tvAddress;
		public Button btnExchange;

		public RelativeLayout rlLastLine;
		public TextView tvExchangeDate;
		public RelativeLayout rlShare;
	}

	/**
	 * 用于分享
	 * 
	 * @author 68
	 * 
	 */
	public class OnShareClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			int position = (Integer) v.getTag();
			// shareImg = RequestUrl.jifenURL +
			// data.get(position).getGoodsImg();
			shareImg = data.get(position).getGoodsImg();
			shareName = data.get(position).getGoodsName();
			shareId = data.get(position).getGoodsId();
			shareToFriends();
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

	/**
	 * 友盟分享至各平台
	 */
	private void shareToFriends()
	{
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 启动分享
		addUmengSDK();
		// 弹出分享面板
		mController.openShare(activity, false);
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
		/*
		 * SmsHandler smsHandler = new SmsHandler();
		 * smsHandler.addToSocialSDK(); SmsShareContent smsContent=new
		 * SmsShareContent();
		 * smsContent.setShareContent(inflater.getContext().getResources
		 * ().getString(R.string.share_jifen_content) + shareName);
		 * smsContent.setShareImage(new UMImage(activity, shareImg));
		 * mController.setShareMedia(smsContent);
		 */

		/** 添加微信平台分享 */
		String appID = "wx2df3aa443f064c4d";
		String appSecret = "b4934655607d0f38df86b9d815d6e602";
		UMWXHandler wxHandler = new UMWXHandler(activity, appID, appSecret);
		wxHandler.addToSocialSDK();

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(inflater.getContext().getResources()
				.getString(R.string.share_jifen_content)
				+ shareName);
		weixinContent.setTitle(inflater.getContext().getResources()
				.getString(R.string.share_jifen_title));
		weixinContent.setTargetUrl(RequestUrl.JIFEN_SHARE + shareId);
		weixinContent.setShareImage(new UMImage(activity, shareImg));
		mController.setShareMedia(weixinContent);

		/*** 添加微信朋友圈分享 */
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appID,
				appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(inflater.getContext().getResources()
				.getString(R.string.share_jifen_content)
				+ shareName);
		circleMedia.setTitle(inflater.getContext().getResources()
				.getString(R.string.share_jifen_title));
		circleMedia.setShareImage(new UMImage(activity, shareImg));
		circleMedia.setTargetUrl(RequestUrl.JIFEN_SHARE + shareId);
		mController.setShareMedia(circleMedia);

		/** 支持QQ分享 */
		String appId = "1104594561";// 开发者在QQ互联申请的APP
		String appkey = "YPfZ0rEJY5Bp1q2k";// 开发者在QQ互联申请的APP kEY
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId,
				appkey);
		qqSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(inflater.getContext().getResources()
				.getString(R.string.share_jifen_content)
				+ shareName);
		qqShareContent.setTitle(inflater.getContext().getResources()
				.getString(R.string.share_jifen_title));
		qqShareContent.setShareImage(new UMImage(activity, shareImg));
		qqShareContent.setTargetUrl(RequestUrl.JIFEN_SHARE + shareId);
		mController.setShareMedia(qqShareContent);

		/** 支持QQ空间分享 */
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId,
				appkey);
		qZoneSsoHandler.addToSocialSDK();

		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(inflater.getContext().getResources()
				.getString(R.string.share_jifen_content)
				+ shareName);
		qzone.setTargetUrl(RequestUrl.JIFEN_SHARE + shareId);
		qzone.setTitle(inflater.getContext().getResources()
				.getString(R.string.share_jifen_title));
		qzone.setShareImage(new UMImage(activity, shareImg));
		mController.setShareMedia(qzone);

		/** 支持新浪微博分享 */
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		SinaShareContent sina = new SinaShareContent();
		sina.setTitle(inflater.getContext().getResources()
				.getString(R.string.share_jifen_title));
		sina.setShareContent(inflater.getContext().getResources()
				.getString(R.string.share_jifen_content)
				+ shareName);
		sina.setTargetUrl(RequestUrl.JIFEN_SHARE + shareId);
		sina.setShareImage(new UMImage(activity, shareImg));
		mController.setShareMedia(sina);
		/** 关闭友盟分享的Toast提示 */
		mController.getConfig().closeToast();

		// 注册分享回调
		mController.registerListener(listener);
	}

	public class MyPostListener implements SocializeListeners.SnsPostListener
	{
		@Override
		public void onStart()
		{
		}

		@Override
		public void onComplete(SHARE_MEDIA share_media, int eCode,
				SocializeEntity socializeEntity)
		{
			if (share_media == SHARE_MEDIA.SMS)
			{// 短信分享
				return;
			}
			if (eCode == 200)
			{
				Tools.showToast(activity, "分享成功");
//				String url = RequestUrl.getInstance(activity).getUrl_earnScore(
//						SharePreferenceUtil.getString(activity,
//								Constants.PREFERENCES_USERID), "2");
//				scoreImp.getEarnScore(url, Constants.INTERFACE_earnScore);
			} else
			{
				String eMsg = "";
				if (eCode != 40000)
				{
					Tools.showToast(activity, "分享失败[" + eCode + "] " + eMsg);
				}
			}
		}
	}

	@Override
	public void updateView(BaseData data)
	{
	}

	@Override
	public void isShowDialog(boolean flag)
	{
	}

	@Override
	public void searchMyScore(SearchMyScoreData data)
	{
	}

	@Override
	public void earnScore(EarnScoreData data)
	{
		EventBus.getDefault().post("", "scoreChange");
		Tools.showToast(activity, "+2");
	}

}

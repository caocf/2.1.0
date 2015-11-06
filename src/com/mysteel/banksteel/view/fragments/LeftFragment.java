package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ScoreImp;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ApplyGoodsActivity;
import com.mysteel.banksteel.view.activity.HXMainActivity;
import com.mysteel.banksteel.view.activity.LoginActivity;
import com.mysteel.banksteel.view.activity.LogisticsActivity;
import com.mysteel.banksteel.view.activity.MsgCenterActivity;
import com.mysteel.banksteel.view.activity.MyInfoActivity;
import com.mysteel.banksteel.view.activity.MyScoreActivity;
import com.mysteel.banksteel.view.activity.ShareAppActivity;
import com.mysteel.banksteel.view.activity.UserSettingActivity;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;

/**
 * 左菜单的片段
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-25 下午7:58:21
 */
public class LeftFragment extends BaseFragment implements OnClickListener,
		IScoreView
{

	private View currentView;
	private ImageView imgHeard;

	private RelativeLayout menu_header;
	/** 名称  */
	private TextView tv_name;
	/** 是否为VIP */
	private TextView tv_novip;
	private ImageView iv_isvip;
	/** 我的积分  */
	private TextView tv_myScore;
	private LinearLayout ll_menu_integral;
	/** 商城服务 */
	private LinearLayout ll_mall_service;
	/** 我的评价  */
	private TextView tv_my_evaluation;
	private LinearLayout ll_my_evaluation;
	/** 邀请好友  */
	private TextView tv_menu_invite;
	private LinearLayout ll_menu_invite;
	/** 消息中心 */
	private TextView tv_menu_message;
	private LinearLayout ll_menu_message;
	/** 商情互动 */
	private TextView tv_merchants_interactive;
	private LinearLayout ll_merchants_interactive;
	private ImageView iv_online;

	/** 物流服务*/
	private TextView tv_logistics_service;
	private LinearLayout ll_logistics_service;
	/** 系统设置 */
	private TextView menu_setting;
	private LinearLayout ll_menu_setting;
	/** 联系客服 */
	private TextView menu_account;
	private LinearLayout ll_menu_account;
	
	/** ScoreImp */
	private ScoreImp scoreImp;

	/***/
	// private String avatarPath;

	public View getCurrentView()
	{
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// avatarPath = Environment.getExternalStorageDirectory().getPath()
		// + Constants.AVATAR_PATH;
		currentView = inflater.inflate(R.layout.fragment_leftmenu, container,
				false);
		EventBus.getDefault().register(this);
		initView();
		return currentView;
	}

	private void initView()
	{

		scoreImp = new ScoreImp(getActivity(), this);

		menu_header = (RelativeLayout) currentView.findViewById(R.id.menu_header);
		menu_header.setOnClickListener(this);

		imgHeard = (ImageView) currentView
				.findViewById(R.id.circleimageview);// 圆形头像
		imgHeard.setOnClickListener(this);
		
		tv_name = (TextView) currentView.findViewById(R.id.tv_name);// 姓名
		tv_name.setOnClickListener(this);
		tv_novip = (TextView) currentView.findViewById(R.id.tv_novip);// 不是VIP
		iv_isvip = (ImageView) currentView.findViewById(R.id.iv_isvip);// VIP
		
//		tv_myScore = (TextView) currentView.findViewById(R.id.tv_menu_integral); //我的积分
//		tv_myScore.setOnClickListener(this);
		ll_menu_integral = (LinearLayout) currentView.findViewById(R.id.ll_menu_integral); //我的积分
		ll_menu_integral.setOnClickListener(this);

		ll_mall_service = (LinearLayout) currentView.findViewById(R.id.ll_mall_service); //商城服务
		ll_mall_service.setOnClickListener(this);
		
//		tv_my_evaluation = (TextView) currentView.findViewById(R.id.tv_my_evaluation);// 我的评价
//		tv_my_evaluation.setOnClickListener(this);
		ll_my_evaluation = (LinearLayout) currentView.findViewById(R.id.ll_my_evaluation); //我的评价
		ll_menu_integral.setOnClickListener(this);
		
//		tv_menu_invite = (TextView) currentView.findViewById(R.id.tv_menu_invite);// 邀请好友
//		tv_menu_invite.setOnClickListener(this);
		ll_menu_invite = (LinearLayout) currentView.findViewById(R.id.ll_menu_invite); //邀请好友
		ll_menu_invite.setOnClickListener(this);
		
//		tv_menu_message = (TextView) currentView.findViewById(R.id.tv_menu_message);// 消息中心
//		tv_menu_message.setOnClickListener(this);
		ll_menu_message = (LinearLayout) currentView.findViewById(R.id.ll_menu_message); //消息中心
		ll_menu_message.setOnClickListener(this);
		
//		tv_merchants_interactive = (TextView) currentView.findViewById(R.id.tv_merchants_interactive);// 商情互动
//		tv_merchants_interactive.setOnClickListener(this);
		ll_merchants_interactive = (LinearLayout) currentView.findViewById(R.id.ll_merchants_interactive); //商情互动
		ll_merchants_interactive.setOnClickListener(this);
		iv_online = (ImageView) currentView.findViewById(R.id.iv_online); //商情互动的红点
		
//		tv_logistics_service = (TextView) currentView.findViewById(R.id.tv_logistics_service);// 物流服务
//		tv_logistics_service.setOnClickListener(this);
		ll_logistics_service = (LinearLayout) currentView.findViewById(R.id.ll_logistics_service); //物流服务
		ll_logistics_service.setOnClickListener(this);
		
//		menu_setting = (TextView) currentView.findViewById(R.id.menu_setting);// 系统设置
//		menu_setting.setOnClickListener(this);
		ll_menu_setting = (LinearLayout) currentView.findViewById(R.id.ll_menu_setting); //系统设置
		ll_menu_setting.setOnClickListener(this);
		
//		menu_account = (TextView) currentView.findViewById(R.id.menu_account);// 联系客服
//		menu_account.setOnClickListener(this);
		ll_menu_account = (LinearLayout) currentView.findViewById(R.id.ll_menu_account); //联系客服
		ll_menu_account.setOnClickListener(this);

		if (Tools.isLogin(getActivity()))
		{
//			getMyScore();
		} else
		{
			tv_name.setText("未登录");
			imgHeard.setImageResource(R.drawable.nophoto);
		}
	}

	/** 获取积分 */
//	public void getMyScore()
//	{
//		setAvatar();
//		String url = RequestUrl.getInstance(getActivity())
//				.getUrl_searchMyScore(getActivity());
//		scoreImp.getSearchScore(url, Constants.INTERFACE_searchMyScore);
//	}

	/**
	 * 登录成功,修改头像栏数据
	 */
	@Subscriber(tag = "LOGIN")
	public void loginToDo(String str)
	{
		String name = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_NAME);
//		String mobile = SharePreferenceUtil.getString(getActivity(),
//				Constants.PREFERENCES_CELLPHONE);
		tv_name.setText(name);
//		tv_totalScore.setVisibility(View.VISIBLE);
//		tv_totalScore.setText(Tools.hiddenPhoneNumber(mobile));

		setAvatar();
//		getMyScore();
	}

	/**
	 * 登录成功,修改头像栏数据
	 */
	@Subscriber(tag = "nameChange")
	public void changeName(String str)
	{
		String name = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_NAME);
		tv_name.setText(name);
	}

	@Override
	public void onClick(View v)
	{
		Intent i = new Intent(getActivity(), LoginActivity.class);
		switch (v.getId())
		{
		case R.id.circleimageview:// 圆形头像
		case R.id.tv_name: //名字
		case R.id.menu_header:
			if (Tools.isLogin(getActivity()))
			{
				i = new Intent(getActivity(), MyInfoActivity.class);
			}
			startActivity(i);
			break;
		case R.id.ll_mall_service: //商城服务
			i = new Intent(getActivity(), ApplyGoodsActivity.class);
			startActivity(i);
			break;
		case R.id.ll_menu_integral:// 我的积分
			if (Tools.isLogin(getActivity()))
			{
				i = new Intent(getActivity(), MyScoreActivity.class);
			}
			startActivity(i);
			break;
		case R.id.ll_my_evaluation:// 我的评价
			if (Tools.isLogin(getActivity()))
			{
				i = new Intent(getActivity(), MyScoreActivity.class);
			}
			startActivity(i);
			break;
		case R.id.ll_menu_invite:// 邀请好友
//			i = new Intent(getActivity(), InviteCodeActivity.class);
			i = new Intent(getActivity(), ShareAppActivity.class);
			startActivity(i);
			break;
		case R.id.ll_menu_message:// 消息中心
			if (Tools.isLogin(getActivity()))
			{
				i = new Intent(getActivity(), MsgCenterActivity.class);
			}
			startActivity(i);
			break;
		case R.id.ll_merchants_interactive:// 商情互动
			startHuanXinMain();
			break;
		case R.id.ll_logistics_service:// 物流服务
			i = new Intent(getActivity(), LogisticsActivity.class);
			startActivity(i);
			break;
		case R.id.ll_menu_setting:// 系统设置
			i = new Intent(getActivity(), UserSettingActivity.class);
			startActivity(i);
			break;
		case R.id.ll_menu_account:// 联系客服
			Tools.makeCall(getActivity(),
					getResources().getString(R.string.cellphone));
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUnreadLabel();
	}


	@Subscriber(tag = "updateUnreadLabel")
	public void updateUnreadLabel(String str)
	{
		updateUnreadLabel();
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		int count = getUnreadMsgCountTotal();
		if (count > 0) {
//			unreadLabel.setText(String.valueOf(count));
//			unreadLabel.setVisibility(View.VISIBLE);
			iv_online.setImageResource(R.drawable.left_menu_hudong1);
		} else {
			iv_online.setImageResource(R.drawable.left_menu_hudong);
		}
	}

	/**
	 * 获取未读消息数
	 *
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		for(EMConversation conversation:EMChatManager.getInstance().getAllConversations().values()){
			if(conversation.getType() == EMConversation.EMConversationType.ChatRoom)
				chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal-chatroomUnreadMsgCount;
	}


	/**
	 * 商情互动
	 */
	private void startHuanXinMain(){
		if (Tools.isLogin(getActivity()))
		{
			//先在这里处理
			/*String username = SharePreferenceUtil.getString(getActivity(), Constants.PREFERENCES_CELLPHONE);
			EMChatManager.getInstance().login(username, "123456", new EMCallBack() {
				@Override
				public void onSuccess() {
						LogUtils.e("登录成功");
						// 登陆成功，保存用户名密码
						BankSteelApplication.getInstance().setUserName(username);
						BankSteelApplication.getInstance().setPassword("123456");

						Intent i = new Intent(getActivity(), HXMainActivity.class);
						getActivity().startActivity(i);
				};

				@Override
				public void onProgress(int progress, String status) {
				}

				@Override
				public void onError(final int code, final String message) {

				}
			});*/
			Intent i = new Intent(getActivity(), HXMainActivity.class);
			getActivity().startActivity(i);
		}else{
			Intent i = new Intent(getActivity(), LoginActivity.class);
			startActivity(i);
		}

	}

	/**
	 * 退出登陆,清空头像栏数据
	 */
	@Subscriber(tag = "LOGINOUT")
	public void loginOutToDo(String str)
	{
		tv_name.setText("未登录");
//		tv_totalScore.setVisibility(View.GONE);
		imgHeard.setImageResource(R.drawable.nophoto);
	}

	/** 上传相片成功,更改主页头像 */
	@Subscriber(tag = "upLoadImgSuccess")
	private void reLogin(String url)
	{
		setAvatar();
	}

	/** 积分有变化 */
	@Subscriber(tag = "scoreChange")
	private void scoreChange(String str)
	{
//		getMyScore();
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
		String name = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_NAME);
		tv_name.setText(name);
//		tv_totalScore.setVisibility(View.VISIBLE);
//		tv_totalScore.setText(data.getData().getTotalScore() + "积分");

	}

	@Override
	public void earnScore(EarnScoreData data)
	{
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		scoreImp.finishRequest();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * 已登录,设置头像
	 */
	private void setAvatar()
	{

		/** 从本地sd卡拿头像 */
		// File file=new File(Tools.AVATAR_PATH);
		// if (!file.exists() && !file.isDirectory())
		// {
		// // file.mkdir();
		// } else
		// {
		// Bitmap bitmap = BitmapFactory.decodeFile(Tools.AVATAR_PATH);
		// if (bitmap != null)
		// {
		// imgHeard.setImageBitmap(bitmap);
		// return;
		// }
		// }

		/** 从网络url中拿图片 */
		String photo = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_PHTHO);
		LogUtils.e("用户头像" + photo);
		if (!TextUtils.isEmpty(photo))
		{
			BitmapUtil.loadImage(getActivity(), photo, imgHeard);
			return;
		} else
		{// 首次注册进来 photo 肯定是没有的
			File file = new File(Tools.AVATAR_PATH);
			if (file.exists())
			{// 若本地有这个路径
				Bitmap bitmap = BitmapFactory.decodeFile(Tools.AVATAR_PATH);
				if (bitmap != null)
				{
					imgHeard.setImageBitmap(bitmap);
					return;
				}
			}

		}

		/** 否则设为默认图片 */
		imgHeard.setImageResource(R.drawable.nophoto);
	}
}

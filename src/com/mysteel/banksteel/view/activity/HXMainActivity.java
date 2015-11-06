package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.ShowMoreUserInfo;
import com.mysteel.banksteel.huanxin.DemoHXSDKHelper;
import com.mysteel.banksteel.huanxin.HXSDKHelper;
import com.mysteel.banksteel.huanxin.InviteMessgeDao;
import com.mysteel.banksteel.huanxin.adapter.ChatAllHistoryAdapter;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * 商情互动
 * 
 * @author:wushaoge
 * @date：2015年8月18日09:35:10
 */
public class HXMainActivity extends SwipeBackActivity implements
		OnClickListener, EMEventListener, IUserCenterView
{

	private Context mContext;

	private UserCenterImpl userCenterImpl;

	private InputMethodManager inputMethodManager;
	private ListView listView;
	private ChatAllHistoryAdapter adapter;
	private EditText query;
	private ImageButton clearSearch;
	public RelativeLayout errorItem;

	public TextView errorText;
	private boolean hidden;
	private List<EMConversation> conversationList = new ArrayList<EMConversation>();

	private MyConnectionListener connectionListener = null;

	List<String> userPhoneList = new ArrayList<String>(); // 用户手机号
	List<String> userNameList = new ArrayList<String>(); // 用户姓名
	List<String> userPhotoList = new ArrayList<String>(); // 用户头像

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_conversation_history);
		mContext = this;
		userCenterImpl = new UserCenterImpl(this);
		init();
		initViews();
		initData();
	}

	private void init()
	{
		// 注册一个监听连接状态的listener
		connectionListener = new MyConnectionListener();
		EMChatManager.getInstance().addConnectionListener(connectionListener);

	}

	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("商情互动");
		backLayout.setOnClickListener(this);

		inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		errorItem = (RelativeLayout) this.findViewById(R.id.rl_error_item);
		errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);

		conversationList.addAll(loadConversationsWithRecentChat());
		listView = (ListView) this.findViewById(R.id.list);
		adapter = new ChatAllHistoryAdapter(this, 1, conversationList);
		// 设置adapter
		listView.setAdapter(adapter);

		final String st2 = getResources().getString(
				R.string.Cant_chat_with_yourself);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				EMConversation conversation = adapter.getItem(position);
				String username = conversation.getUserName();
				if (username.equals(BankSteelApplication.getInstance()
						.getUserName()))
					Toast.makeText(mContext, st2, Toast.LENGTH_LONG).show();
				else
				{
					// 进入聊天页面
					Intent intent = new Intent(mContext, ChatActivity.class);
					if (conversation.isGroup())
					{
						if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
						{
							// it is group chat
							intent.putExtra("chatType",
									ChatActivity.CHATTYPE_CHATROOM);
							intent.putExtra("groupId", username);
						} else
						{
							// it is group chat
							intent.putExtra("chatType",
									ChatActivity.CHATTYPE_GROUP);
							intent.putExtra("groupId", username);
						}

					} else
					{
						// it is single chat
						intent.putExtra("userId", username);// 手机号
						if (userNameList.size() >= 0 && !TextUtils.isEmpty(userNameList
										.get(position)))
						{
							intent.putExtra("userName",userNameList.get(position));// 姓名
						}
					}
					startActivity(intent);
				}
			}
		});
		// 注册上下文菜单
		registerForContextMenu(listView);

		listView.setOnTouchListener(new View.OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// 隐藏软键盘
				hideSoftKeyboard();
				return false;
			}

		});
		// 搜索框
		query = (EditText) this.findViewById(R.id.query);
		String strSearch = getResources().getString(R.string.search);
		query.setHint(strSearch);
		// 搜索框中清除button
		clearSearch = (ImageButton) this.findViewById(R.id.search_clear);
		query.addTextChangedListener(new TextWatcher()
		{
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				adapter.getFilter().filter(s);
				if (s.length() > 0)
				{
					clearSearch.setVisibility(View.VISIBLE);
				} else
				{
					clearSearch.setVisibility(View.INVISIBLE);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			public void afterTextChanged(Editable s)
			{
			}
		});
		clearSearch.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				query.getText().clear();
				hideSoftKeyboard();
			}
		});

		notifyResetData();
	}

	/**
	 * 刷新头像和姓名
	 */
	public void notifyResetData()
	{
		for (EMConversation emc : conversationList)
		{
			userPhoneList.add(emc.getUserName());
		}
		if (userPhoneList.size() > 0)
		{
			String phone = "";
			for (String s : userPhoneList)
			{
				phone += s + ",";
			}
			phone = phone.substring(0, phone.length() - 1);
			String url = RequestUrl.getInstance(this).getUrl_MoreUserInfo(
					mContext, phone);
			LogUtils.e(url);
			userCenterImpl.getShowMultipleUserInfo(url,
					Constants.INTERFACE_friendIntroduce);
		}
	}

	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v,
			android.view.ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		// if(((AdapterContextMenuInfo)menuInfo).position > 0){ m,
		this.getMenuInflater().inflate(R.menu.delete_message, menu);
		// }
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		boolean handled = false;
		boolean deleteMessage = false;
		if (item.getItemId() == R.id.delete_message)
		{
			deleteMessage = true;
			handled = true;
		} else if (item.getItemId() == R.id.delete_conversation)
		{
			deleteMessage = false;
			handled = true;
		}
		EMConversation tobeDeleteCons = adapter
				.getItem(((AdapterView.AdapterContextMenuInfo) item
						.getMenuInfo()).position);
		// 删除此会话
		EMChatManager.getInstance().deleteConversation(
				tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(),
				deleteMessage);
		InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(this);
		inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
		adapter.remove(tobeDeleteCons);
		adapter.notifyDataSetChanged();

		// 更新消息未读数
		updateUnreadLabel();

		return handled ? true : super.onContextItemSelected(item);
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel()
	{
		int count = getUnreadMsgCountTotal();
		adapter.setData();
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal()
	{
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		for (EMConversation conversation : EMChatManager.getInstance()
				.getAllConversations().values())
		{
			if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
				chatroomUnreadMsgCount = chatroomUnreadMsgCount
						+ conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal - chatroomUnreadMsgCount;
	}

	void hideSoftKeyboard()
	{
		if (this.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
		{
			if (this.getCurrentFocus() != null)
				inputMethodManager.hideSoftInputFromWindow(this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 获取所有会话
	 * 
	 * @return +
	 */
	private List<EMConversation> loadConversationsWithRecentChat()
	{
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager
				.getInstance().getAllConversations();
		// 过滤掉messages size为0的conversation
		/**
		 * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
		 * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
		 */
		List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
		synchronized (conversations)
		{
			for (EMConversation conversation : conversations.values())
			{
				if (conversation.getAllMessages().size() != 0)
				{
					// if(conversation.getType() !=
					// EMConversationType.ChatRoom){
					sortList.add(new Pair<Long, EMConversation>(conversation
							.getLastMessage().getMsgTime(), conversation));
					// }
				}
			}
		}
		try
		{
			// Internal is TimSort algorithm, has bug
			sortConversationByLastChatTime(sortList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		List<EMConversation> list = new ArrayList<EMConversation>();
		for (Pair<Long, EMConversation> sortItem : sortList)
		{
			list.add(sortItem.second);
		}
		return list;
	}

	/**
	 * 根据最后一条消息的时间排序
	 * 
	 */
	private void sortConversationByLastChatTime(
			List<Pair<Long, EMConversation>> conversationList)
	{
		Collections.sort(conversationList,
				new Comparator<Pair<Long, EMConversation>>()
				{
					@Override
					public int compare(final Pair<Long, EMConversation> con1,
							final Pair<Long, EMConversation> con2)
					{

						if (con1.first == con2.first)
						{
							return 0;
						} else if (con2.first > con1.first)
						{
							return 1;
						} else
						{
							return -1;
						}
					}

				});
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onEvent(EMNotifierEvent event)
	{
		switch (event.getEvent())
		{
		case EventNewMessage: // 普通消息
		{
			EMMessage message = (EMMessage) event.getData();

			// 提示新消息
			HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

			refreshUI();
			break;
		}

		case EventOfflineMessage:
		{
			refreshUI();
			break;
		}

		case EventConversationListChanged:
		{
			refreshUI();
			break;
		}

		default:
			break;
		}
	}

	/**
	 * 连接监听listener
	 * 
	 */
	public class MyConnectionListener implements EMConnectionListener
	{

		@Override
		public void onConnected()
		{
			boolean groupSynced = HXSDKHelper.getInstance()
					.isGroupsSyncedWithServer();
			boolean contactSynced = HXSDKHelper.getInstance()
					.isContactsSyncedWithServer();

			// in case group and contact were already synced, we supposed to
			// notify sdk we are ready to receive the events
			if (groupSynced && contactSynced)
			{
				new Thread()
				{
					@Override
					public void run()
					{
						HXSDKHelper.getInstance().notifyForRecevingEvents();
					}
				}.start();
			} else
			{
				if (!groupSynced)
				{
					asyncFetchGroupsFromServer();
				}

				if (!contactSynced)
				{
					asyncFetchContactsFromServer();
				}

				if (!HXSDKHelper.getInstance().isBlackListSyncedWithServer())
				{
					asyncFetchBlackListFromServer();
				}
			}

			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					// chatHistoryFragment.errorItem.setVisibility(View.GONE);
				}

			});
		}

		@Override
		public void onDisconnected(final int error)
		{
			final String st1 = getResources().getString(
					R.string.can_not_connect_chat_server_connection);
			final String st2 = getResources().getString(
					R.string.the_current_network);
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					if (error == EMError.USER_REMOVED)
					{
						// 显示帐号已经被移除
						// showAccountRemovedDialog();
					} else if (error == EMError.CONNECTION_CONFLICT)
					{
						// 显示帐号在其他设备登陆dialog
						// showConflictDialog();
					} else
					{
						// chatHistoryFragment.errorItem.setVisibility(View.VISIBLE);
						// if (NetUtils.hasNetwork(MainActivity.this))
						// chatHistoryFragment.errorText.setText(st1);
						// else
						// chatHistoryFragment.errorText.setText(st2);

					}
				}

			});
		}
	}

	static void asyncFetchGroupsFromServer()
	{
		HXSDKHelper.getInstance().asyncFetchGroupsFromServer(new EMCallBack()
		{

			@Override
			public void onSuccess()
			{
				HXSDKHelper.getInstance().noitifyGroupSyncListeners(true);

				if (HXSDKHelper.getInstance().isContactsSyncedWithServer())
				{
					HXSDKHelper.getInstance().notifyForRecevingEvents();
				}
			}

			@Override
			public void onError(int code, String message)
			{
				HXSDKHelper.getInstance().noitifyGroupSyncListeners(false);
			}

			@Override
			public void onProgress(int progress, String status)
			{

			}

		});
	}

	static void asyncFetchContactsFromServer()
	{
		HXSDKHelper.getInstance().asyncFetchContactsFromServer(
				new EMValueCallBack<List<String>>()
				{

					@Override
					public void onSuccess(List<String> usernames)
					{
						Context context = HXSDKHelper.getInstance()
								.getAppContext();

						/*
						 * System.out.println("----------------"+usernames.toString
						 * ()); EMLog.d("roster", "contacts size: " +
						 * usernames.size()); Map<String, User> userlist = new
						 * HashMap<String, User>(); for (String username :
						 * usernames) { User user = new User();
						 * user.setUsername(username); setUserHearder(username,
						 * user); userlist.put(username, user); } //
						 * 添加user"申请与通知" User newFriends = new User();
						 * newFriends
						 * .setUsername(Constant.NEW_FRIENDS_USERNAME); String
						 * strChat =
						 * context.getString(R.string.Application_and_notify);
						 * newFriends.setNick(strChat);
						 * 
						 * userlist.put(Constant.NEW_FRIENDS_USERNAME,
						 * newFriends); // 添加"群聊" User groupUser = new User();
						 * String strGroup =
						 * context.getString(R.string.group_chat);
						 * groupUser.setUsername(Constant.GROUP_USERNAME);
						 * groupUser.setNick(strGroup); groupUser.setHeader("");
						 * userlist.put(Constant.GROUP_USERNAME, groupUser);
						 * 
						 * // 添加"聊天室" User chatRoomItem = new User(); String
						 * strChatRoom = context.getString(R.string.chat_room);
						 * chatRoomItem.setUsername(Constant.CHAT_ROOM);
						 * chatRoomItem.setNick(strChatRoom);
						 * chatRoomItem.setHeader("");
						 * userlist.put(Constant.CHAT_ROOM, chatRoomItem);
						 * 
						 * // 存入内存
						 * DemoApplication.getInstance().setContactList(userlist
						 * ); // 存入db UserDao dao = new UserDao(context);
						 * List<User> users = new
						 * ArrayList<User>(userlist.values());
						 * dao.saveContactList(users);
						 */

						HXSDKHelper.getInstance().notifyContactsSyncListener(
								true);

						if (HXSDKHelper.getInstance()
								.isGroupsSyncedWithServer())
						{
							HXSDKHelper.getInstance().notifyForRecevingEvents();
						}

					}

					@Override
					public void onError(int error, String errorMsg)
					{
						HXSDKHelper.getInstance().notifyContactsSyncListener(
								false);
					}

				});
	}

	static void asyncFetchBlackListFromServer()
	{
		HXSDKHelper.getInstance().asyncFetchBlackListFromServer(
				new EMValueCallBack<List<String>>()
				{

					@Override
					public void onSuccess(List<String> value)
					{
						EMContactManager.getInstance().saveBlackList(value);
						HXSDKHelper.getInstance().notifyBlackListSyncListener(
								true);
					}

					@Override
					public void onError(int error, String errorMsg)
					{
						HXSDKHelper.getInstance().notifyBlackListSyncListener(
								false);
					}

				});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		refreshUI();
		// unregister this event listener when this activity enters the
		// background
		DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper
				.getInstance();
		sdkHelper.pushActivity(this);

		// register the event listener when enter the foreground
		EMChatManager.getInstance().registerEventListener(
				this,
				new EMNotifierEvent.Event[]
				{ EMNotifierEvent.Event.EventNewMessage,
						EMNotifierEvent.Event.EventOfflineMessage,
						EMNotifierEvent.Event.EventConversationListChanged });
	}

	private void refreshUI()
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				conversationList.clear();
				conversationList.addAll(loadConversationsWithRecentChat());
				if (adapter != null)
					adapter.notifyDataSetChanged();
			}
		});
	}

	private void initData()
	{

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			// 返回菜单
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void openCountDown(long millisInFuture, long countDownInterval)
	{

	}

	@Override
	public void upDatePersonal(EditInfoData data)
	{

	}

	@Override
	public void upDateCompany(EditInfoData data)
	{

	}

	@Override
	public void updateView(BaseData data)
	{
		ShowMoreUserInfo showMoreUserInfo = (ShowMoreUserInfo) data;
		List<ShowMoreUserInfo.DataEntity> moreUserInfoList = showMoreUserInfo
				.getData();
		for (ShowMoreUserInfo.DataEntity dataEntity : moreUserInfoList)
		{
			userNameList.add(dataEntity.getName());
			userPhotoList.add(dataEntity.getPhoto());
		}
		// 更新adapter
		adapter.setData(userNameList, userPhotoList);
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}
}

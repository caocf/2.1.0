package com.mysteel.banksteel.view.activity;

/**
 * @author 68 消息中心
 */
public class MessageCenterActivity extends BaseActivity/* implements
		OnClickListener, ISystemManagerView, IXListViewListener*/
{/*

	private XListView lvMessage;
	*//** 进度条 *//*
	private ProgressBar progressBar;
	private ArrayList<Datas> datas;
	private MessageCenterData messageCenterData;
	private String userId;
	private String page = "1";
	private String size = "10";
	private SystemManagerImpl systemManagerImpl;
	private MessageCenterAdapter messageCenterAdapter;
	private ArrayList<Datas> oneData = new ArrayList<MessageCenterData.Data.Datas>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_center);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("消息中心");
		backLayout.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		lvMessage = (XListView) findViewById(R.id.lv_messagecenter);
		lvMessage.setXListViewListener(this);
		if (lvMessage != null)
		{
			lvMessage.hideFoot();
		}

		messageCenterAdapter = new MessageCenterAdapter(this);
		lvMessage.setAdapter(messageCenterAdapter);

		getMessage();
	}

	*//**
	 * 拉取消息中心数据
	 *//*
	private void getMessage()
	{
		systemManagerImpl = new SystemManagerImpl(this);
		userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		String url = RequestUrl.getInstance(this).getUrl_pageHistoryMessage(
				userId, page, size);
		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);

	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.menu_layout)
		{
			finish();
		}
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
	public void checkUpDate(BaseData data)
	{
	}

	@Override
	public void getHistoryMessage(MessageCenterData data)
	{
		messageCenterData = data;

		if (messageCenterData.getData().getDatas() != null)
		{
			if ("1".equals(page))
			{
				datas = messageCenterData.getData().getDatas();
			} else
			{
				datas.addAll(messageCenterData.getData().getDatas());
			}
		}
		
		*//** 如果是最后一页数据，在最后加一条新数据 *//*
		if (messageCenterData.getData().getPagenum().equals(page))
		{
			Datas d = new MessageCenterData().new Data().new Datas(
					getResources().getString(R.string.lastmessage),
					getResources().getString(R.string.welcome));
			d.setType("LASTMESSAGE");
			datas.add(datas.size(), d);
			messageCenterAdapter.reSetListView(datas);
		} else if ("0".equals(messageCenterData.getData().getCount()))
		{
			Datas d = new MessageCenterData().new Data().new Datas(
					getResources().getString(R.string.lastmessage),
					getResources().getString(R.string.welcome));
			d.setType("LASTMESSAGE");
			if (oneData.size() == 0)
			{
				oneData.add(d);
			}
			messageCenterAdapter.reSetListView(oneData);
		}
		onLoad();
	}

	@Override
	public void getSign(SignData data)
	{
	}

	*//**
	 * 下拉刷新
	 *//*
	@Override
	public void onRefresh()
	{
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_pageHistoryMessage(
				userId, page, size);
		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);
	}

	*//**
	 * 上拉加载
	 *//*
	@Override
	public void onLoadMore()
	{
		if (TextUtils.isEmpty(messageCenterData.getData().getPagenum())
				|| TextUtils.isEmpty(messageCenterData.getData().getPage()))
		{
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(messageCenterData
				.getData().getPagenum()))
		{
			onLoad();
			Tools.showToast(this, "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";

		String url = RequestUrl.getInstance(this).getUrl_pageHistoryMessage(
				userId, page, size);

		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);
	}

	private void onLoad()
	{
		lvMessage.stopRefresh();
		lvMessage.stopLoadMore();
		lvMessage.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
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
	protected void onDestroy()
	{
		super.onDestroy();
		systemManagerImpl.finishRequest();
	}
*/}

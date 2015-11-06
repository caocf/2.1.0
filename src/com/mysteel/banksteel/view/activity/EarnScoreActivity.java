package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import com.mysteel.banksteel.ao.impl.SystemManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.EarnScoreAdapter;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * @author 68 赚积分
 */
public class EarnScoreActivity extends BaseActivity implements OnClickListener,
		ISystemManagerView
{

	/** 新手上路的listview */
	private ListView lvRookie;
	/** 推荐有礼 */
	private LinearLayout llRecommend;
	/** 每日签到 */
	private LinearLayout llSign;
	/** 进度对话框 */
	private ProgressBar progressBar;
	private SystemManagerImpl systemManagerImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_earnscore);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		systemManagerImpl = new SystemManagerImpl(this);
		tvTitle.setText("赚积分");
		backLayout.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		lvRookie = (ListView) findViewById(R.id.lv_earnscore_ncvioe);
		lvRookie.setAdapter(new EarnScoreAdapter(this));
		lvRookie.setOnItemClickListener(new myListItemClickListener());

		llSign = (LinearLayout) findViewById(R.id.ll_sign);
		llRecommend = (LinearLayout) findViewById(R.id.ll_recommend);
		llSign.setOnClickListener(this);
		llRecommend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			EventBus.getDefault().post("", "updateScore");
			finish();
			break;

		case R.id.ll_sign:
			// 每日签到
//			String userId = SharePreferenceUtil.getString(this,
//					Constants.PREFERENCES_USERID);
//			String url = RequestUrl.getInstance(this).getUrl_sign(userId);
			if (!Tools.isNetworkConnected(this))
			{
				Tools.showToast(this, "请检查网络");
				return;
			}

//			systemManagerImpl.getSign(url, Constants.INTERFACE_sign);

			break;

		case R.id.ll_recommend:
			// 推荐有礼
			startActivity(new Intent(this, ShareAppActivity.class));
			break;

		default:
			break;
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
	}

	@Override
	public void getSign(SignData data)
	{
		String message = data.getData();
		Tools.showToast(this, message);
	}

	public class myListItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Intent intent = new Intent(EarnScoreActivity.this,
					MyInfoActivity.class);
			switch (position)
			{
			case 0:// 个人信息
				// intent.putExtra(Constants.JUMP_COMPANY_INFO, false);
				// startActivity(intent);
				// break;
			case 1:// 绑定手机
			case 2:// 设置头像
				startActivity(intent);
				break;
			case 3:// 完成订单
				Intent i = new Intent(EarnScoreActivity.this,
						MainActivity.class);
				EventBus.getDefault().post("", "main_slidingPaneLayout");
				startActivity(i);
				break;
			default:
				break;
			}

		}

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
	public void getDeleteMessage(DeleteMessageData data) 
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		systemManagerImpl.finishRequest();
	}

	@Override
	public void onBackPressed()
	{
		EventBus.getDefault().post("", "updateScore");
		finish();
	}

	
}

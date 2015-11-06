package com.mysteel.banksteel.view.activity;

import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.mysteel.banksteel.ao.impl.AddressManagerImpl;
import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.AddressListData.Data;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.view.adapters.AddressListAdapter;
import com.mysteel.banksteel.view.interfaceview.IAddressManageView;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteel.view.ui.TwoButtonDialog.Builder;
import com.mysteel.banksteeltwo.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 地址设置
 * 
 * @author 68 获取地址列表 若为空,显示"你还没有地址哦" 若有值,设置到listview中
 */
public class AddressListActivity extends BaseActivity implements
		OnClickListener, IAddressManageView
{
	/** 地址列表listview */
	private ListView lvAddress;
	/** 添加地址 */
	private TextView tvAdd;
	/** 进度条 */
	private ProgressBar progressBar;
	/** 没有地址时 */
	private LinearLayout noAddressLl;
	/** 用户id */
	private String userId;
	/** 前一个页面的标志 */
	private String forwardActvity;
	/** AddressManagerImpl */
	private AddressManagerImpl addressManagerImpl;
	private AddressListAdapter addressListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setaddress);
		forwardActvity = getIntent().getStringExtra(Constants.NEXT_SCREEN_TAG);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("地址设置");
		tvRightText.setText("添加");
		backLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		lvAddress = (ListView) findViewById(R.id.lv_setaddress_address);
		noAddressLl = (LinearLayout) findViewById(R.id.ll_address_content);
		addressManagerImpl = new AddressManagerImpl(this);

		userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		String url = RequestUrl.getInstance(this).getUrl_addressList(userId);
		addressManagerImpl.getAddressList(url, Constants.INTERFACE_addressList);
		requestAddressList();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;
		case R.id.share_layout:
		case R.id.tv_setaddress_add:
			// (没有地址页面)加一个
			Intent i = new Intent(this, EditorAddressActivity.class);
			i.putExtra("TAG_ADDRESS", Constants.ADD_ADDRESS);
			i.putExtra(Constants.NEXT_SCREEN_TAG, forwardActvity);
			startActivity(i);
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
	public void setDefaultAddress(BaseData data)
	{
		// 重新发起地址列表请求,更新界面
		requestAddressList();
	}

	@Override
	public void setAddressList(AddressListData data)
	{
		// 设置地址列表数据
		AddressListData addressData = (AddressListData) data;
		final List<Data> listData = addressData.getData();

		/** 判断有无数据,有则显示在listview中,无数据则隐藏lisetview,显示没有地址页面 */
		if (listData == null || listData.size() == 0)
		{
			noAddressLl.setVisibility(View.VISIBLE);
			lvAddress.setVisibility(View.GONE);

			tvAdd = (TextView) findViewById(R.id.tv_setaddress_add);
			tvAdd.setOnClickListener(this);

		} else
		{
			// 有地址
			tvTitle.setText("地址列表");

			noAddressLl.setVisibility(View.GONE);
			lvAddress.setVisibility(View.VISIBLE);
			addressListAdapter = new AddressListAdapter(this, listData);
			lvAddress.setAdapter(addressListAdapter);
			if ("FillOrderActivity".equals(forwardActvity))
			{
				/** 从填写订单界面过来 */
				lvAddress.setClickable(true);
				lvAddress.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							final int position, long id)
					{
						TwoButtonDialog.Builder builder = new Builder(
								AddressListActivity.this);
						builder.setMessage("是否设为收货地址?");
						builder.setPositiveButton("取消", new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								dialog.dismiss();
							}
						});
						
						builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
						{
							
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								// 设为收货地址,使用eventBus通知填写订单界面
								EventBus.getDefault().post(
										listData.get(position), "orderAddress");
								dialog.dismiss();
								finish();
							}
						});
						builder.create().show();
					}
				});
			} else if ("UserSettingActivity".equals(forwardActvity))
			{
				/** 从设置界面过来 */
				lvAddress.setClickable(false);
			}
		}
	}

	@Override
	public void delAddress(BaseData data)
	{
		// 重新发起地址列表请求,更新界面
		requestAddressList();
	}

	private void requestAddressList()
	{
		String url = RequestUrl.getInstance(this).getUrl_addressList(userId);
		addressManagerImpl.getAddressList(url, Constants.INTERFACE_addressList);
	}

	@Subscriber(tag = "addressChanged")
	private void addressChanged(String str)
	{
		requestAddressList();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		addressManagerImpl.finishRequest();
		if (addressListAdapter != null)
		{
			addressListAdapter.addressFinishRequest();
		}
	}
}

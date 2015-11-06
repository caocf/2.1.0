package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.mysteel.banksteel.ao.impl.AddressManagerImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.AddressListData.Data;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IAddressManageView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 添加 或者 编辑地址
 * 
 * @author 68
 * 
 */
public class EditorAddressActivity extends BaseActivity implements
		OnClickListener, IAddressManageView
{

	/** DEBUG. */
	private static final boolean DEBUG = true & BankSteelApplication.GLOBAL_DEBUG;
	/** TAG. */
	private static final String TAG = "EditorAddressActivity";
	/** 收货人 */
	private EditText etName;
	/** 详细地址 */
	private EditText etDetailAddress;
	/** 邮编 */
	private EditText etPostcode;
	/** 手机号码 */
	private EditText etPhone;
	/** 完成按钮 */
	private Button btnComplete;
	/** 进度条 */
	private ProgressBar progressBar;
	private AddressManagerImpl addressImpl;
	private String tagTitle;
	private String addressId;// 编辑地址需要用到
	private String province;// 省
	private String city;// 市
	private String county;// 区
	private TextView tvAddress;// 省市区
	/** Handler */
	Handler mHandler = new Handler();

	/** 获取当前位置 */
	private ImageView ivLocation;
	private LocationManagerProxy mLocationManagerProxy;
	private AMapLocationListener mListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_editor);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		Bundle b = getIntent().getExtras();
		tagTitle = b.getString("TAG_ADDRESS");

		etName = (EditText) findViewById(R.id.et_editoraddress_name);// 收货人
		etPhone = (EditText) findViewById(R.id.et_editoraddress_phone);// 手机号码
		etDetailAddress = (EditText) findViewById(R.id.et_editoraddress_detailaddress);// 详细地址
		etPostcode = (EditText) findViewById(R.id.et_editpostcode);// 邮编
		tvAddress = (TextView) findViewById(R.id.tv_editAddress_address);// 省市区
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		btnComplete = (Button) findViewById(R.id.btn_editoraddress_complete);// 确认完成的按钮
		ivLocation = (ImageView) findViewById(R.id.get_location);// 定位

		btnComplete.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		tvAddress.setOnClickListener(this);
		ivLocation.setOnClickListener(this);

		tvTitle.setText("添加地址");// 默认为添加地址
		if (Constants.ADD_ADDRESS.equals(tagTitle))
		{
		} else if (Constants.EDIT_ADDRESS.equals(tagTitle))
		{
			tvTitle.setText("编辑地址");
			AddressListData.Data data = (Data) b.getSerializable("DATA");
			if (DEBUG)
			{
				Log.d(TAG, "data.getCity() : " + data.getCity());
			}
			editView(data);
		}

		etPhone.addTextChangedListener(new TextWatcher()
		{
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			private static final int NUM = 11;// 手机号码最大位数

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				selectionStart = etPhone.getSelectionStart();
				selectionEnd = etPhone.getSelectionEnd();
				if (temp.length() > NUM)
				{
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					etPhone.setText(s);
					etPhone.setSelection(tempSelection);
				}
			}
		});

		addressImpl = new AddressManagerImpl(this);
		mListener = new MyLocationListener();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(false);

	}

	/** 编辑过来的，将数据填写到当前页面 */
	private void editView(Data data)
	{
		addressId = data.getId();
		etName.setText(data.getConsignee());
		etPhone.setText(data.getMobile());
		etPostcode.setText(data.getPostcode());
		etDetailAddress.setText(data.getAddress());

		province = data.getProvince();
		city = data.getCity();
		county = data.getDistrict();
		tvAddress.setText(province + city + county);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.menu_layout:
			finish();
			break;

		case R.id.btn_editoraddress_complete:// 确认完成的按钮
			commit();
			break;

		case R.id.tv_editAddress_address:
			// 跳转到选择城市页面
			Intent intent = new Intent(this, CityPickActivity.class);
			startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE);
			break;

		case R.id.get_location:// 定位
			getLocation();
			break;

		default:
			break;
		}
	}

	/**
	 * 确认提交
	 */
	private void commit()
	{
		String contact = etName.getText().toString().trim();// 收货人
		String phone = etPhone.getText().toString().trim();// 手机号码
		String postcode = etPostcode.getText().toString().trim();// 邮编
		String detailAddress = etDetailAddress.getText().toString().trim();// 详细地址

		if (TextUtils.isEmpty(contact))
		{
			Tools.showToast(this, "请填写收货人！");
			return;
		}
		if (TextUtils.isEmpty(phone) || !Tools.checkIsPhoneNumber(phone))
		{
			Tools.showToast(this, "请填写正确格式的手机号码！");
			return;
		}

		if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)
				|| TextUtils.isEmpty(county))
		{
			Tools.showToast(this, "请填写省市区！");
			return;
		}

		if (TextUtils.isEmpty(postcode))
		{
			Tools.showToast(this, "请填写邮编码！");
			return;
		}

		if (TextUtils.isEmpty(detailAddress))
		{
			Tools.showToast(this, "请填写详细地址！");
			return;
		}

		String url = "";
		if (Constants.ADD_ADDRESS.equals(tagTitle))
		{
			// TODO 默认地址这一项有待确认
			url = RequestUrl.getInstance(this)
					.getUrl_addAddress(this, contact, phone, province, city,
							county, postcode, detailAddress, "0");

		} else if (Constants.EDIT_ADDRESS.equals(tagTitle))
		{
			// url = RequestUrl.getInstance(this).getUrl_editAddress(this,
			// Tools.EncodetoGBK(contact), addressId,phone,
			// Tools.EncodetoGBK(province),
			// Tools.EncodetoGBK(city), Tools.EncodetoGBK(district), postcode,
			// Tools.EncodetoGBK(detailAddress), "1");
			url = RequestUrl.getInstance(this).getUrl_editAddress(this,
					contact, addressId, phone, province, city, county,
					postcode, detailAddress, "0");

		}
		addressImpl.getAddAddress(url, Constants.INTERFACE_editAddress);

	}

	@Override
	public void updateView(BaseData data)
	{
		if (Constants.ADD_ADDRESS.equals(tagTitle))
		{
			Tools.showToast(this, "添加地址成功！");
		} else if (Constants.EDIT_ADDRESS.equals(tagTitle))
		{
			Tools.showToast(this, "编辑地址成功！");
		}
		EventBus.getDefault().post("", "addressChanged");
		mHandler.postDelayed(finishRunnable, 1000);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.ADD_ADDRESS_REQUEST_CODE
				&& resultCode == RESULT_OK)
		{
			province = data.getStringExtra("PROVINCE");
			city = data.getStringExtra("CITY");
			county = data.getStringExtra("COUNTY");
			tvAddress.setText(province + city + county);
		}
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
	public void setAddressList(AddressListData data)
	{
	}

	@Override
	public void setDefaultAddress(BaseData data)
	{
	}

	@Override
	public void delAddress(BaseData data)
	{
	}

	/** 销毁当前页面 */
	public Runnable finishRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			EditorAddressActivity.this.finish();
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause()
	{
		super.onPause();
		// 销毁定位
		if (mLocationManagerProxy != null)
		{
			mLocationManagerProxy.removeUpdates(mListener);
			mLocationManagerProxy.destory();
		}
		mLocationManagerProxy = null;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		addressImpl.finishRequest();
		mHandler.removeCallbacks(finishRunnable);
	}

	/**
	 * 定位,获取省市区
	 */
	private void getLocation()
	{
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, -1,
				Integer.MAX_VALUE * 1000L, mListener);
		// provider：有三种定位Provider供用户选择，
		// 分别是:LocationManagerProxy.GPS_PROVIDER，代表使用手机GPS定位；
		// LocationManagerProxy.NETWORK_PROVIDER，代表使用手机网络定位；
		// LocationProviderProxy.AMapNetwork，代表高德网络定位服务，混合定位。
		// minTime：位置变化的通知时间，单位为毫秒。如果为-1，定位只定位一次。
		// minDistance:位置变化通知距离，单位为米。
		// listener:定位监听者。
	}

	public class MyLocationListener implements AMapLocationListener
	{

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}

		@Override
		public void onProviderEnabled(String provider)
		{
		}

		@Override
		public void onProviderDisabled(String provider)
		{
		}

		@Override
		public void onLocationChanged(Location location)
		{
		}

		@Override
		public void onLocationChanged(AMapLocation amapLocation)
		{
			int errorCode = amapLocation.getAMapException().getErrorCode();
			if (amapLocation != null && errorCode == 0)
			{
				// 定位成功回调信息，设置相关消息
				province = amapLocation.getProvince();
				city = amapLocation.getCity();
				county = amapLocation.getDistrict();
				if (province == null)
				{
					province = city;
				}
				tvAddress.setText(province + city + county);
			} else
			{
				if (errorCode == 22 || errorCode == 23)
				{
					Tools.showToast(EditorAddressActivity.this, "连接异常");
					return;
				}
				Tools.showToast(EditorAddressActivity.this, "未能获取到地址,请重试或者选择城市");
				// Log.e("EditorAddressActivity",
				// amapLocation.getAMapException()
				// .getErrorCode() + "");
			}
		}
	}
}

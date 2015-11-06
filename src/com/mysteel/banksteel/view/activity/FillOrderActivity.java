package com.mysteel.banksteel.view.activity;

import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.ao.impl.AddressManagerImpl;
import com.mysteel.banksteel.ao.impl.ScoreImp;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.AddressListData.Data;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EarnScoreData;
import com.mysteel.banksteel.entity.ScoreConvert.Data.Goods;
import com.mysteel.banksteel.entity.SearchMyScoreData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IAddressManageView;
import com.mysteel.banksteel.view.interfaceview.IScoreView;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 填写订单页面
 */
public class FillOrderActivity extends BaseActivity implements OnClickListener,
		IAddressManageView, IScoreView
{

	/** 昵称 */
	private TextView tvNickName;
	/** 有地址 */
	private RelativeLayout rlHaveAddress;
	/** 无地址 */
	private RelativeLayout rlNoAddress;
	/** 联系电话 */
	private TextView tvPhone;
	/** 省 */
	private TextView tvProvince;
	/** 市 */
	private TextView tvCity;
	/** 县 */
	private TextView tvCount;
	/** 区 */
	private TextView tvArea;
	/** +(增加一个数量) */
	private TextView tvAdd;
	/** -(减少一个数量) */
	private TextView tvReduce;
	/** 单个花费积分 */
	private TextView tvScorePerCost;
	/** 可用积分 */
	private TextView tvScoreCanUse;
	/** 商品详细名 */
	private TextView tvDetailName;
	/** 商品名 */
	private TextView tvName;
	/** 提交 */
	private Button btnSubmit;
	/** 总花费积分 */
	private TextView tvScoreTotalCost;
	/** 数量 */
	private TextView tvNumber;
	/** 单价 */
	private double mPerScore;
	/** 积分商城传递过来的数据 */
	private Goods good;
	/** 进度条 */
	private ProgressBar progressBar;
	/** 添加地址 */
	private ImageView ivAddAddress;
	/** 商品库存数量 */
	private int maxNumber;
	/** 商品图片 */
	private NetworkImageView goodPhoto;
	/** 商品备注 */
	private EditText etNote;
	/** 总积分 */
	private int TotalScore;
	private AddressListData.Data adressData;
	private ScoreImp scoreImp;
	private AddressManagerImpl addressManagerImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_fill);
		good = (Goods) getIntent().getExtras().get("DATA");
		TotalScore = getIntent().getIntExtra("SCORE", 0);
		initViews();
	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("填写订单");

		rlHaveAddress = (RelativeLayout) findViewById(R.id.rl_fillorder_havecontact);
		rlNoAddress = (RelativeLayout) findViewById(R.id.rl_fillorder_nocontact);
		ivAddAddress = (ImageView) findViewById(R.id.iv_addAddress);
		ivAddAddress.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		tvName = (TextView) findViewById(R.id.tv_fillorder_name);
		tvDetailName = (TextView) findViewById(R.id.tv_fillorder_detailname);
		tvScoreCanUse = (TextView) findViewById(R.id.tv_fillorder_score_canuse);
		tvScorePerCost = (TextView) findViewById(R.id.tv_fillorder_score_percost);
		tvReduce = (TextView) findViewById(R.id.tv_fillorder_reduce);
		tvAdd = (TextView) findViewById(R.id.tv_fillorder_add);
		tvScoreTotalCost = (TextView) findViewById(R.id.tv_fillorder_score_totalcost);
		etNote = (EditText) findViewById(R.id.et_orderNote);
		btnSubmit = (Button) findViewById(R.id.btn_fillorder_submit);
		tvNumber = (TextView) findViewById(R.id.et_fillorder_number);
		goodPhoto = (NetworkImageView) findViewById(R.id.iv_fillorder_photo);

		mPerScore = Integer.parseInt(good.getGoodsGangBeng());
		maxNumber = Integer.parseInt(good.getNumber());
		tvName.setText(good.getGoodsName());
		tvDetailName.setText("");// 暂时无数据 TODO
		tvScorePerCost.setText(good.getGoodsGangBeng() + "积分");
		tvScoreTotalCost.setText(good.getGoodsGangBeng() + "积分");
		showImageByNetworkImageView(good.getGoodsThumb(), goodPhoto);

		backLayout.setOnClickListener(this);
		rlHaveAddress.setOnClickListener(this);
		rlNoAddress.setOnClickListener(this);
		tvAdd.setOnClickListener(this);
		tvReduce.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);

		tvNumber.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				tvScoreTotalCost.setText((Integer.parseInt(s.toString().trim()) * mPerScore)
						+ "积分");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		tvNickName = (TextView) findViewById(R.id.tv_fillorder_nickname);
		tvPhone = (TextView) findViewById(R.id.tv_fillorder_phone);
		tvProvince = (TextView) findViewById(R.id.tv_fillorder_province);
		tvCity = (TextView) findViewById(R.id.tv_fillorder_city);
		tvCount = (TextView) findViewById(R.id.tv_fillorder_count);
		tvArea = (TextView) findViewById(R.id.tv_fillorder_area);
		scoreImp = new ScoreImp(this);
		addressManagerImpl = new AddressManagerImpl(this);

		String scorerUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);// 查看我的积分url
		scoreImp.getSearchScore(scorerUrl, Constants.INTERFACE_searchMyScore);

		String userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		String url = RequestUrl.getInstance(this).getUrl_addressList(userId);
		addressManagerImpl.getAddressList(url, Constants.INTERFACE_addressList);

	}

	/**
	 * @param list
	 *            拉取默认地址数据后需要更新的
	 */
	private void invalidate(List<Data> list)
	{
		for (Data data : list)
		{
			if ("1".equals(data.getIsDefault()))
			{
				// 有默认地址
				adressData = data;
				rlHaveAddress.setVisibility(View.VISIBLE);
				rlNoAddress.setVisibility(View.GONE);

				tvNickName.setText(data.getConsignee());
				tvPhone.setText(data.getMobile());
				tvProvince.setText(data.getProvince());
				tvCity.setText(data.getCity());
				tvCount.setText(data.getDistrict());
				tvArea.setText(data.getAddress());
				return;
			}
		}
		// 没有默认地址
		rlHaveAddress.setVisibility(View.GONE);
		rlNoAddress.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;

		case R.id.rl_fillorder_nocontact:
		case R.id.rl_fillorder_havecontact:
			// 有地址和没有地址
			Intent intent = new Intent(this, AddressListActivity.class);
			intent.putExtra(Constants.NEXT_SCREEN_TAG, "FillOrderActivity");
			startActivity(intent);
			break;

		case R.id.tv_fillorder_add:

			if ((getNumber() + 1) > maxNumber)
			{
				Tools.showToast(this, "库存不足");
				return;

			}
			if ((getNumber() + 1) > TotalScore / mPerScore)
			{
				Tools.showToast(this, "积分不足");
				return;
			}

			tvNumber.setText((getNumber() + 1) + "");
			break;

		case R.id.tv_fillorder_reduce:
			if (getNumber() > 1)
			{
				tvNumber.setText((getNumber() - 1) + "");
			} else
			{
				Toast.makeText(this, "商品数量不能少于1", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_fillorder_submit:
			// 提交订单
			submitOrderBiz();

			break;

		default:
			break;
		}
	}

	private void submitOrderBiz()
	{
		if (adressData == null)
		{
			Tools.showToast(this, "请添加收货地址");
			return;
		}
		String addressId = adressData.getId();
		String memberId = SharePreferenceUtil.getString(this,
				Constants.USER_MEMBERID);
		String userId = SharePreferenceUtil.getString(this,
				Constants.PREFERENCES_USERID);
		String goods = good.getId() + "-" + tvNumber.getText().toString();
		String note = etNote.getText().toString();

		/** 判断网络 */
		if (!Tools.isNetworkConnected(this))
		{
			Tools.showToast(this, "请检查网络连接");
			return;
		}
		final String url = RequestUrl.getInstance(this)
				.getUrl_submitScoreOrder(memberId, userId, goods, addressId,
						note);

		TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
				FillOrderActivity.this);
		alert.setMessage("我要去兑换");
		alert.setNegativeButton(
		// 确定
				"取消", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}

				});
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{// 取消

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						scoreImp.getScoreOrder(url,
								Constants.INTERFACE_submitScoreOrder);
						dialog.dismiss();
					}

				});
		alert.create().show();

	}

	/**
	 * 给控件设置图片
	 * 
	 * @param url
	 *            图片的url
	 * @param imgView
	 *            NetworkImageView控件
	 */
	public void showImageByNetworkImageView(String url, NetworkImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				200);
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
		imgView.setTag(url);

		imgView.setImageUrl(url, imageLoader);
	}

	private int getNumber()
	{
		return Integer.parseInt(tvNumber.getText().toString());
	}

	@Override
	public void updateView(BaseData data)
	{
		Tools.showToast(this, "订单提交成功");
		/** 刷新我的总积分 */
		String scorerUrl = RequestUrl.getInstance(this).getUrl_searchMyScore(
				this);// 查看我的积分url
		scoreImp.getSearchScore(scorerUrl, Constants.INTERFACE_searchMyScore);
		EventBus.getDefault().post("", "updateScore");// eventbus发布
		
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
		if (data.getData() == null || data.getData().size() <= 0)
		{
			// 没有地址
			rlHaveAddress.setVisibility(View.GONE);
			rlNoAddress.setVisibility(View.VISIBLE);
		} else
		{
			// 有地址
			invalidate(data.getData());// 更新界面
		}
	}

	@Override
	public void setDefaultAddress(BaseData data)
	{
	}

	@Override
	public void delAddress(BaseData data)
	{
	}

	/**
	 * 设置从SetAddressActivity返回的数据
	 * 当前订单地址
	 * 
	 * @param data
	 */
	@Subscriber(tag = "orderAddress")
	private void orderAddress(AddressListData.Data data)
	{
		adressData = data;
		rlHaveAddress.setVisibility(View.VISIBLE);
		rlNoAddress.setVisibility(View.GONE);

		tvNickName.setText(data.getConsignee());
		tvPhone.setText(data.getMobile());
		tvProvince.setText(data.getProvince());
		tvCity.setText(data.getCity());
		tvCount.setText(data.getDistrict());
		tvArea.setText(data.getAddress());
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		addressManagerImpl.finishRequest();
		scoreImp.finishRequest();
	}

	@Override
	public void searchMyScore(SearchMyScoreData data)

	{
		TotalScore = Integer.parseInt(data.getData().getTotalScore());
		tvScoreCanUse.setText(TotalScore + "积分");
	}

	@Override
	public void earnScore(EarnScoreData data)
	{
	}

}

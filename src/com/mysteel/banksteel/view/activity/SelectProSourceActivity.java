package com.mysteel.banksteel.view.activity;

import java.util.ArrayList;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.MatchBuyData;
import com.mysteel.banksteel.entity.MatchBuyData.Data.Datas;
import com.mysteel.banksteel.entity.RegisterData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.ProductSourceAdapter;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * 选择货源，生成订单
 * 
 * @author:huoguodong
 * @date：2015-5-7 上午8:58:04
 */
public class SelectProSourceActivity extends BaseActivity implements
		OnClickListener, IOrderTradeView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "SelectProSourceActivity";

	private ListView lvProSource;
	private ProductSourceAdapter adapter;
	private MatchBuyData mbData;
	private ArrayList<Datas> datas;
	private IOrderTrade orderTrade;
	private String page = "1";
	private String size = "10";
	private ProgressBar progressBar;

	private Button createOrder;
	private Button peopleFindGood;
	private RegisterData rdata;// 生成订单后返回的数据实体类和注册的可以共用
	private String stanBuyId = "";// 需求单Id
	private String status = "";// 判断是从哪个页面跳转来 首页下三条、我的求购页面，和匹配的页面

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_prosource);

		initViews();
	}

	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();

		Bundle b = getIntent().getExtras();
		stanBuyId = b.getString("ID");// 获取发布求购成功后得到的需求单Id
		status = b.getString("status");

		lvProSource = (ListView) findViewById(R.id.lv_product_source);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		adapter = new ProductSourceAdapter(this, new ArrayList<Datas>());
		lvProSource.setAdapter(adapter);

		createOrder = (Button) findViewById(R.id.btn_create_order);
		peopleFindGood = (Button) findViewById(R.id.btn_go_manual_screen);

		menuBtn.setVisibility(View.GONE);
		imBack.setImageResource(R.id.im_back);
		imBack.setVisibility(View.VISIBLE);
		tvTitle.setText(R.string.select_prosource);

		createOrder.setOnClickListener(this);
		peopleFindGood.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		orderTrade = new OrderTradeImpl(this, this);
		String url = RequestUrl.getInstance(this).getUrl_getMatchBuy(this,
				stanBuyId, page, size);
		// String url = RequestUrl.getInstance(this).getUrl_getMatchBuy(this,
		// "387", page, size);
		if (DEBUG)
		{
			Log.d(TAG, "5.1 资源匹配的" + url);
		}
		orderTrade.getMatchBuy(url, Constants.INTERFACE_matchBuy);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			if ("buy".endsWith(status) || "match".endsWith(status))
			{
				finish();
			} else
			{
				Intent i = new Intent(this, BuyDetailInfoActivity.class);
				startActivity(i);
			}
			break;
		case R.id.btn_create_order:// 生成订单
			final Datas createOrderData = adapter.getDatas();
			if (createOrderData == null)
			{
				Tools.showToast(this, "您暂时还没有选择需要生成的订单!");
				return;
			}
			if (DEBUG)
			{
				Log.d(TAG, "Brand" + createOrderData.getBrand());
				Log.d(TAG, "Breed" + createOrderData.getBreed());
				Log.d(TAG, "City" + createOrderData.getCity());
				Log.d(TAG, "Qty" + createOrderData.getQty());
				Log.d(TAG, "Material" + createOrderData.getMaterial());
				Log.d(TAG, "Spec" + createOrderData.getSpec());
				Log.d(TAG, "Price" + createOrderData.getPrice());
			}
			String message = "品名：" + createOrderData.getBreed() + "\n" + "材质："
					+ createOrderData.getMaterial() + "\n" + "规格："
					+ createOrderData.getSpec() + "\n" + "品牌和产地："
					+ createOrderData.getBrand() + "\n" + "交货地："
					+ createOrderData.getCity() + "\n" + "求购数："
					+ createOrderData.getQty() + "吨" + "\n" + "联系人："
					+ createOrderData.getContacter() + "\n" + "联系号码："
					+ createOrderData.getPhone();

			TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
					SelectProSourceActivity.this);
			alert.setMessage(message);
			alert.setNegativeButton(
					// 取消
					getResources().getString(R.string.cancel_publish),
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{

							dialog.dismiss();
						}

					});
			alert.setPositiveButton(getResources()
					.getString(R.string.make_sure),
					new DialogInterface.OnClickListener()
					{// 确定

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							String url = RequestUrl.getInstance(
									SelectProSourceActivity.this)
									.getUrl_getCreateOrder(
											SelectProSourceActivity.this,
											stanBuyId, createOrderData.getId(),
											"0", "2", createOrderData.getQty(),
											createOrderData.getPrice());
							orderTrade.getCreateOrder(url,
									Constants.INTERFACE_createOrder);
							dialog.dismiss();
						}

					});
			alert.create().show();

			break;
		case R.id.btn_go_manual_screen:// 人工找货
			// if ("buy".endsWith(status))
			// {
			Intent intent = new Intent(this, HumanServeActivity.class);
			intent.putExtra("ID", stanBuyId);
			intent.putExtra("status", "yes");// 需要请求转人工接口
			startActivity(intent);
			// } else
			// {
			// Intent intent = new Intent(this, HumanServeActivity.class);
			// intent.putExtra("ID", stanBuyId);
			// intent.putExtra("status", "no");// 需要请求转人工接口
			// startActivity(intent);
			// }
			finish();

			break;

		default:
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("deal.matchBuy".equals(data.getCmd()))
		{// 5.1 资源匹配
			mbData = (MatchBuyData) data;
			if (mbData == null || mbData.getData() == null
					|| mbData.getData().getDatas() == null)
			{
				Tools.showToast(this, "暂时没匹配到资源！");
				return;
			}
			datas = mbData.getData().getDatas();
			adapter.reSetListView(datas);
		} else if ("deal.createOrder".equals(data.getCmd()))
		{// 5.3生成订单
			rdata = (RegisterData) data;// 生成订单后会得到订单ID 直接跳转到系统匹配的页面
			Intent i = new Intent(this, OrderDetailsMatchActivity.class);
			i.putExtra("orderId", rdata.getData());
			i.putExtra("status", "-1");
			i.putExtra("matchStatus", "0");// 系统匹配为空 管理员ID（managerId）是没有的
			if (DEBUG)
			{
				Log.d(TAG, "rdata ====" + rdata.getData());
			}
			startActivity(i);
			finish();
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
	protected void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if ("buy".endsWith(status) || "match".endsWith(status))
		{
			finish();
		} else
		{
			Intent i = new Intent(this, BuyDetailInfoActivity.class);
			startActivity(i);
		}
		return super.onKeyUp(keyCode, event);
	}
}

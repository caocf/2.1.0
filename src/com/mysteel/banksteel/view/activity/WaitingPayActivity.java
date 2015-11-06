package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.GoodsDetailAdapter;
import com.mysteel.banksteel.view.ui.SwipeListView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 待付款
 * @author:wushaoge
 * @date：2015年10月27日15:32:50
 */

public class WaitingPayActivity extends SwipeBackActivity implements OnClickListener{

	private Context mContext;

	private ScrollView svMain;
	private LinearLayout llMain;
	private TextView tvOrderNum; //订单编号
	private TextView tvOrderDate; //订单日期
	private TextView tvBankName; //开户行
	private TextView tvOpenName; //户名
	private TextView tvBankCardnum; //账号
	private TextView tvPayCompanyName; //公司姓名
	private TextView tvWeightNum; //总重量
	private TextView tvShouldPayPrice; //应付$$
	private TextView tvPayDate; //最后付款时间
	private TextView tvTraderName; //交易员姓名
	private SwipeListView lvListview; //货物明细listview
	private GoodsDetailAdapter myAdapter;
	private LinearLayout llBottom;
	private Button btnPay; //立即付款
	private ProgressBar pbProgressbar;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting_pay);
		mContext = this;
		initViews();
		initData();
	}
	
	
	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("待收货");
		backLayout.setOnClickListener(this);

		svMain = (ScrollView) findViewById(R.id.sv_main);
		llMain = (LinearLayout) findViewById(R.id.ll_main);
		tvOrderNum = (TextView) findViewById(R.id.tv_order_num);
		tvOrderDate = (TextView) findViewById(R.id.tv_order_date);
		tvBankName = (TextView) findViewById(R.id.tv_bank_name);
		tvOpenName = (TextView) findViewById(R.id.tv_open_name);
		tvBankCardnum = (TextView) findViewById(R.id.tv_bank_cardnum);
		tvPayCompanyName = (TextView) findViewById(R.id.tv_pay_company_name);
		tvWeightNum = (TextView) findViewById(R.id.tv_weight_num);
		tvShouldPayPrice = (TextView) findViewById(R.id.tv_should_pay_price);
		tvPayDate = (TextView) findViewById(R.id.tv_pay_date);
		tvTraderName = (TextView) findViewById(R.id.tv_trader_name);
		lvListview = (SwipeListView) findViewById(R.id.lv_listview);

		llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
		btnPay = (Button) findViewById(R.id.btn_pay);
		pbProgressbar = (ProgressBar) findViewById(R.id.pb_progressbar);

		lvListview.setPullLoadEnable(false);
		lvListview.setPullRefreshEnable(false);
		lvListview.setisNeedSwipe(false);


	}

	private void initData()
	{
		myAdapter = new GoodsDetailAdapter(this.getLayoutInflater());
		lvListview.setAdapter(myAdapter);
		Tools.setListViewHeightBasedOnChildren(lvListview);

	}


	@Override
	public void onClick(View v) {
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
}


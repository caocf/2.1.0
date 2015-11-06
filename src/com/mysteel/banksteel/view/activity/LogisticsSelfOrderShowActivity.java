package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mysteel.banksteel.entity.LogisticsOrderData;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 查看自助下单内容(并不能修改)
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
@SuppressWarnings("unused")
public class LogisticsSelfOrderShowActivity extends SwipeBackActivity implements OnClickListener{

	private Context mContext;

	private LogisticsOrderData.DataEntity.DatasEntity datas;

	private ProgressBar progressBar;

	private TextView tv_from; //出发地
	private TextView tv_target; //目的地
	private TextView tv_type; //螺纹钢
	private TextView tv_num; //20吨

	private LinearLayout ll_show1; //装1卸1
	private EditText et_zhuang_lu1; //XX路
	private EditText et_zhuang_hao1; //XX号
	private EditText et_xie_lu1; //XX路
	private EditText et_xie_hao1; //XX号
	private EditText et_dunnum1; //重量XX吨

	private LinearLayout ll_show2; //装1卸1
	private EditText et_zhuang_lu2; //XX路
	private EditText et_zhuang_hao2; //XX号
	private EditText et_xie_lu2; //XX路
	private EditText et_xie_hao2; //XX号
	private EditText et_dunnum2; //重量XX吨

	private TextView tv_tihuo_date; //提货时间
	private TextView tv_daohuo_date; //到货时间
	private EditText et_remarks; //取单地址
	private EditText et_membername; //公司名称
	private EditText et_note; //备注要求

	private LinearLayout ll_invoice11; //11%增票
	private ImageView iv_invoice11;
	private TextView tv_invoice11;

	private LinearLayout ll_invoice6; //6%增票
	private ImageView iv_invoice6;
	private TextView tv_invoice6;

	private LinearLayout ll_daofu; //货到付款
	private ImageView iv_daofu;
	private TextView tv_daofu;

	private LinearLayout ll_need; //自卸:是
	private ImageView iv_need;
	private TextView tv_need;

	private LinearLayout ll_noneed; //自卸：否
	private ImageView iv_noneed;
	private TextView tv_noneed;

	private TextView tv_price; //我的价格
	private Button btn_submit; //确定下单

	private FapiaoType fapiaoType = FapiaoType.zengpiao11; //发票类型默认为%11增票
	private boolean flag = true; //true为需要自卸  false为不需要自卸
	private boolean loadFlag = false; //false为1装1卸 true代表多装多卸
	private String priceResult = ""; //最终的价格


	private static enum FapiaoType
	{
		zengpiao11,zengpiao6,daofu
	}

	private static enum FoodDate
	{
		start,end
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logistics_self_order);
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
		tvTitle.setText("订单详情");
		backLayout.setOnClickListener(this);

		datas = (LogisticsOrderData.DataEntity.DatasEntity)getIntent().getSerializableExtra("datas");

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		tv_from = (TextView) findViewById(R.id.tv_from);
		tv_target = (TextView) findViewById(R.id.tv_target);
		tv_type = (TextView) findViewById(R.id.tv_type);
		tv_num = (TextView) findViewById(R.id.tv_num);

		ll_show1 = (LinearLayout) findViewById(R.id.ll_show1);
		ll_show2 = (LinearLayout) findViewById(R.id.ll_show2);

		et_zhuang_lu1 = (EditText) findViewById(R.id.et_zhuang_lu1);
		et_zhuang_hao1 = (EditText) findViewById(R.id.et_zhuang_hao1);
		et_xie_lu1 = (EditText) findViewById(R.id.et_xie_lu1);
		et_xie_hao1 = (EditText) findViewById(R.id.et_xie_hao1);
		et_dunnum1 = (EditText) findViewById(R.id.et_dunnum1);

		et_zhuang_lu1.setFocusable(false);
		et_zhuang_hao1.setFocusable(false);
		et_xie_lu1.setFocusable(false);
		et_xie_hao1.setFocusable(false);
		et_dunnum1.setFocusable(false);


		et_zhuang_lu2 = (EditText) findViewById(R.id.et_zhuang_lu2);
		et_zhuang_hao2 = (EditText) findViewById(R.id.et_zhuang_hao2);
		et_xie_lu2 = (EditText) findViewById(R.id.et_xie_lu2);
		et_xie_hao2 = (EditText) findViewById(R.id.et_xie_hao2);
		et_dunnum2 = (EditText) findViewById(R.id.et_dunnum2);

		et_zhuang_lu2.setFocusable(false);
		et_zhuang_hao2.setFocusable(false);
		et_xie_lu2.setFocusable(false);
		et_xie_hao2.setFocusable(false);
		et_dunnum2.setFocusable(false);

		tv_tihuo_date = (TextView) findViewById(R.id.tv_tihuo_date);
		tv_daohuo_date = (TextView) findViewById(R.id.tv_daohuo_date);
		et_remarks = (EditText) findViewById(R.id.et_remarks);
		et_membername = (EditText) findViewById(R.id.et_membername);
		et_note = (EditText) findViewById(R.id.et_note);
		tv_tihuo_date.setOnClickListener(this);
		tv_daohuo_date.setOnClickListener(this);
		et_remarks.setFocusable(false);
		et_note.setFocusable(false);
		et_membername.setFocusable(false);

		ll_invoice11 = (LinearLayout) findViewById(R.id.ll_invoice11);
		iv_invoice11 = (ImageView) findViewById(R.id.iv_invoice11);
		tv_invoice11 = (TextView) findViewById(R.id.tv_invoice11);
		ll_invoice11.setOnClickListener(this);

		ll_invoice6 = (LinearLayout) findViewById(R.id.ll_invoice6);
		iv_invoice6 = (ImageView) findViewById(R.id.iv_invoice6);
		tv_invoice6 = (TextView) findViewById(R.id.tv_invoice6);
		ll_invoice6.setOnClickListener(this);

		ll_daofu = (LinearLayout) findViewById(R.id.ll_daofu);
		iv_daofu = (ImageView) findViewById(R.id.iv_daofu);
		tv_daofu = (TextView) findViewById(R.id.tv_daofu);
		ll_daofu.setOnClickListener(this);

		ll_need = (LinearLayout) findViewById(R.id.ll_need);
		iv_need = (ImageView) findViewById(R.id.iv_need);
		tv_need = (TextView) findViewById(R.id.tv_need);
		ll_need.setOnClickListener(this);

		ll_noneed = (LinearLayout) findViewById(R.id.ll_noneed);
		iv_noneed = (ImageView) findViewById(R.id.iv_noneed);
		tv_noneed = (TextView) findViewById(R.id.tv_noneed);
		ll_noneed.setOnClickListener(this);

		tv_price = (TextView) findViewById(R.id.tv_price);
		btn_submit = (Button) findViewById(R.id.btn_submit);
	}

	private void initData()
	{
		tv_from.setText(datas.getStartCity() + "  " + datas.getStartArea());
		tv_target.setText(datas.getEndCity()+"  "+datas.getEndArea());
		tv_type.setText(datas.getBreed());
		tv_num.setText(datas.getTotalWeight()+"吨");
		if(!datas.getLoad().equals("1")&&!datas.getUnload().equals("1")){
			ll_show2.setVisibility(View.VISIBLE);
		}else{
			ll_show2.setVisibility(View.GONE);
		}

		String zlu1 = "";
		String zhao1 = "";
		int strIndexOf1 = datas.getStartAddress1().lastIndexOf("路");
		if(strIndexOf1<=0){
			 zlu1 = datas.getStartAddress1();
			 zhao1 = "";
		}else{
			 zlu1 = datas.getStartAddress1().substring(0, strIndexOf1);
			 zhao1 = datas.getStartAddress1().substring(strIndexOf1+1,datas.getStartAddress1().length()-1);
		}
		et_zhuang_lu1.setText(zlu1);
		et_zhuang_hao1.setText(zhao1);

		String xlu1 = "";
		String xhao1 = "";
		int strIndexOf2 = datas.getEndAddress1().lastIndexOf("路");
		if(strIndexOf2<=0){
			xlu1 = datas.getEndAddress1();
			xhao1 = "";
		}else{
			xlu1 = datas.getEndAddress1().substring(0, strIndexOf2);
			xhao1 = datas.getEndAddress1().substring(strIndexOf2+1,datas.getEndAddress1().length()-1);
		}
		et_xie_lu1.setText(xlu1);
		et_xie_hao1.setText(xhao1);

		String zlu2 = "";
		String zhao2 = "";
		int strIndexOf3 = datas.getStartAddress2().lastIndexOf("路");
		if(strIndexOf3<=0){
			zlu2 = datas.getStartAddress2();
			zhao2 = "";
		}else{
			zlu2 = datas.getStartAddress2().substring(0, strIndexOf3);
			zhao2 = datas.getStartAddress2().substring(strIndexOf3+1,datas.getStartAddress2().length()-1);
		}
		et_zhuang_lu2.setText(zlu2);
		et_zhuang_hao2.setText(zhao2);

		String xlu2 = "";
		String xhao2 = "";
		int strIndexOf4 = datas.getEndAddress2().lastIndexOf("路");
		if(strIndexOf4<=0){
			xlu2 = datas.getEndAddress2();
			xhao2 = "";
		}else{
			xlu2 = datas.getEndAddress2().substring(0, strIndexOf4);
			xhao2 = datas.getEndAddress2().substring(strIndexOf4+1,datas.getEndAddress2().length()-1);
		}
		et_xie_lu2.setText(xlu2);
		et_xie_hao2.setText(xhao2);

		et_dunnum1.setText(datas.getWeight1());
		et_dunnum2.setText(datas.getWeight2());

		String startTime = DateUtil.getFormatDate("yyyy-M-d H",DateUtil.getDate("yyyy-MM-dd HH:mm:ss", datas.getStartTime()));
		tv_tihuo_date.setText(startTime);

		String endTime = DateUtil.getFormatDate("yyyy-M-d H",DateUtil.getDate("yyyy-MM-dd HH:mm:ss", datas.getEndTime()));
		tv_daohuo_date.setText(endTime);


		et_membername.setText(datas.getMemberName());
		et_remarks.setText(datas.getQudanAddress());
		et_note.setText(datas.getNote());

		fapiaoType = FapiaoType.zengpiao11;
		if(datas.getInvoiceType().equals("0")){
			fapiaoType = FapiaoType.zengpiao11;
		}
		if(datas.getInvoiceType().equals("1")){
			fapiaoType = FapiaoType.zengpiao6;
		}
		if(datas.getInvoiceType().equals("2")){
			fapiaoType = FapiaoType.daofu;
		}
		selFapiao(fapiaoType);


		if(datas.getZhixieType().equals("0")){ //0否
			selZixie(false);
		}else{
			selZixie(true);
		}

		tv_price.setText(datas.getPrice());
		btn_submit.setVisibility(View.GONE);

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


	/**
	 * 选择发票类型
	 * @param type
	 */
	private void selFapiao(FapiaoType type){
		iv_invoice11.setImageResource(R.drawable.logistics_unselect);
		tv_invoice11.setTextColor(mContext.getResources().getColor(R.color.font_black_one));

		iv_invoice6.setImageResource(R.drawable.logistics_unselect);
		tv_invoice6.setTextColor(mContext.getResources().getColor(R.color.font_black_one));

		iv_daofu.setImageResource(R.drawable.logistics_unselect);
		tv_daofu.setTextColor(mContext.getResources().getColor(R.color.font_black_one));

		if(type == FapiaoType.zengpiao11){
			iv_invoice11.setImageResource(R.drawable.logistics_select);
			tv_invoice11.setTextColor(mContext.getResources().getColor(R.color.main_imred));
		}

		if(type == FapiaoType.zengpiao6){
			iv_invoice6.setImageResource(R.drawable.logistics_select);
			tv_invoice6.setTextColor(mContext.getResources().getColor(R.color.main_imred));
		}

		if(type == FapiaoType.daofu){
			iv_daofu.setImageResource(R.drawable.logistics_select);
			tv_daofu.setTextColor(mContext.getResources().getColor(R.color.main_imred));
		}
		this.fapiaoType = type;
	}


	/**
	 * 是否需要自卸
	 * @param flag
	 */
	private void selZixie(boolean flag){
		iv_need.setImageResource(R.drawable.logistics_unselect);
		tv_need.setTextColor(mContext.getResources().getColor(R.color.font_black_one));

		iv_noneed.setImageResource(R.drawable.logistics_unselect);
		tv_noneed.setTextColor(mContext.getResources().getColor(R.color.font_black_one));

		if(flag){
			iv_need.setImageResource(R.drawable.logistics_select);
			tv_need.setTextColor(mContext.getResources().getColor(R.color.main_imred));
		}else{
			iv_noneed.setImageResource(R.drawable.logistics_select);
			tv_noneed.setTextColor(mContext.getResources().getColor(R.color.main_imred));
		}
		this.flag = flag;

	}


}



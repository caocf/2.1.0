package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.view.ui.DriveChangeBtnView;
import com.mysteel.banksteel.view.ui.DriveView;
import com.mysteel.banksteel.view.ui.IdentityView;
import com.mysteel.banksteel.view.ui.TakeDeliveryView;
import com.mysteel.banksteel.view.ui.TidanView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 提货申请界面
 * @author:wushaoge
 * @date：2015年10月26日09:22:11
 */

public class ApplyGoodsActivity extends SwipeBackActivity implements OnClickListener{

	private Context mContext;

	private ProgressBar pbProgressbar;
	private RelativeLayout rlParent;
	private ScrollView svMain;
	private LinearLayout llMain;
	private LinearLayout llTihuoDetail;
	private EditText etBuyersPhone; //买家手机号
	private TextView tvTihuoTypeTitle;
	private TextView tvTihuoType; //提货方式
	private EditText etRemarks; //备注信息
	private LinearLayout llDynamicView; //动态改变view
	private Button btn_tihuo; //提货按钮

	private TihuoType tihuoType = TihuoType.CHECHUAN;
	private String[] nums = new String[4];

	/**
	 * 凭买家提单提货
	 * 凭车船号提货
	 * 直接过户
	 * 凭身份证号提货
	 */
	public enum TihuoType{
		CHECHUAN, GUOHU, TIDAN, SHENFENZHENG
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_goods);
		mContext = this;
		initViews();
		initData();
	}



	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews(){
		super.initViews();

		tvTitle.setText("提货申请");
		backLayout.setOnClickListener(this);

		svMain = (ScrollView) findViewById(R.id.sv_main);
		llMain = (LinearLayout) findViewById(R.id.ll_main);
		llTihuoDetail = (LinearLayout) findViewById(R.id.ll_tihuo_detail);
		etBuyersPhone = (EditText) findViewById(R.id.et_buyers_phone);
		tvTihuoTypeTitle = (TextView) findViewById(R.id.tv_tihuo_type_title);
		tvTihuoType = (TextView) findViewById(R.id.tv_tihuo_type);
		tvTihuoType.setOnClickListener(this);
		etRemarks = (EditText) findViewById(R.id.et_remarks);
		llDynamicView = (LinearLayout) findViewById(R.id.ll_dynamic_view);
		pbProgressbar = (ProgressBar) findViewById(R.id.pb_progressbar);
		btn_tihuo = (Button) findViewById(R.id.btn_tihuo);
		btn_tihuo.setOnClickListener(this);
	}

	private void initData()
	{
		nums[0] = "凭车船号提货";
		nums[1] = "凭买家提单提货";
		nums[2] = "直接过户";
		nums[3] = "凭身份证号提货";

		//test
		for(int i = 0; i< 3; i++){
			TakeDeliveryView view = new TakeDeliveryView(mContext);
			llTihuoDetail.addView(view);
		}



	}


	/**
	 * 选择提货方式
	 */
	private void selLoadPop(){
		/*
			凭买家提单提货
			凭车船号提货
			直接过户
			凭身份证号提货
		*/
		LoadPopupWheel popupWheel = new LoadPopupWheel(this, new LoadPopupWheel.PopupValueLinstener() {
			@Override
			public void change(int firstValue) {
				popClick(firstValue);
			}
		});
		View rootView = getWindow().getDecorView();
		int cur = 0;
		switch (tihuoType){
			case CHECHUAN:
				 cur = 0;
				 break;
			case TIDAN:
				 cur = 1;
				 break;
			case GUOHU:
				 cur = 2;
				 break;
			case SHENFENZHENG:
				 cur = 3;
				 break;

		}
		popupWheel.setData(nums,cur,0);
		popupWheel.setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWheel.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

	}

	/**
	 * 选择模式
	 * @param firstValue
	 */
	private void popClick(int firstValue){
		int curTemp = firstValue - 1;
		tvTihuoType.setText(nums[curTemp]);
		TihuoType tihuoTypeTemp = tihuoType;
		if(curTemp == 0){
			tihuoTypeTemp = TihuoType.CHECHUAN;
		}
		if(curTemp == 1){
			tihuoTypeTemp = TihuoType.TIDAN;
		}
		if(curTemp == 2){
			tihuoTypeTemp = TihuoType.GUOHU;
		}
		if(curTemp == 3){
			tihuoTypeTemp = TihuoType.SHENFENZHENG;
		}
		if(!tihuoType.equals(tihuoTypeTemp)){
			tihuoType = tihuoTypeTemp;
			changeView(tihuoType);
		}
	}


	private void changeView(TihuoType tihuoType){
		llDynamicView.removeAllViews();
		if(tihuoType.equals(TihuoType.CHECHUAN)){ //默认为车船号提货  可以添加多个司机
			DriveChangeBtnView changeBtnView = new DriveChangeBtnView(mContext, true, new DriveChangeBtnView.IDrvieChange() {
				@Override
				public void addDrive() {
					DriveView driveView = new DriveView(mContext);
					llDynamicView.addView(driveView);
				}
				@Override
				public void delDrive() {
					int len = llDynamicView.getChildCount();
					int driveViewCount = 0;
					for (int i = 0; i < len; i++) {
						if (llDynamicView.getChildAt(i) instanceof DriveView) {
							driveViewCount += 1;
							DriveView viewTemp = (DriveView) llDynamicView.getChildAt(i);
							LogUtils.e("有" + driveViewCount + "个动态DriveView");
						}
					}
					if(driveViewCount>1){
						llDynamicView.removeViewAt(len-1);
					}
				}
			});
			llDynamicView.addView(changeBtnView);
			DriveView driveView = new DriveView(mContext);
			llDynamicView.addView(driveView);
		}

		if(tihuoType.equals(TihuoType.TIDAN)){
			TidanView tidanView = new TidanView(mContext);
			llDynamicView.addView(tidanView);
		}

		if(tihuoType.equals(TihuoType.SHENFENZHENG)){
			IdentityView idView = new IdentityView(mContext);
			llDynamicView.addView(idView);
		}

	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.menu_layout:
				// 返回菜单
				finish();
				break;
			case R.id.tv_tihuo_type:
				selLoadPop();
				break;
			case R.id.btn_tihuo:
				test();
				break;
			default:
				break;
		}
	}

	private void  test(){
		startActivity(new Intent(mContext,WaitingPayActivity.class));
		int len = llTihuoDetail.getChildCount();
		for (int i = 0; i < len; i++) {
			if (llTihuoDetail.getChildAt(i) instanceof TakeDeliveryView) {
				TakeDeliveryView viewTemp = (TakeDeliveryView) llTihuoDetail.getChildAt(i);
				viewTemp.getIscheck();
				int num = viewTemp.getWantNum();
				LogUtils.e("第" + i + "个动态view获得的可提数量为:" + num);
			}
		}
	}
}


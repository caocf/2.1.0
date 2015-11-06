package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.TransportManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.LogisticsSelfOrder;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自助下单
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
@SuppressWarnings("unused")
public class LogisticsSelfOrderActivity extends SwipeBackActivity implements OnClickListener,ITransportManagerView {

	private Context mContext;

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


    private String[] year = new String[2];
	private String[] month = new String[12];
	private String[] day = new String[31];
	private String[] hours = new String[24];

	private int curYearStart = 0; //当前选择的item
	private int curMonthStart = 0;
	private int curDayStart = 0;
	private int curHoursStart = 0;

	private String yearStartStr = ""; //默认选择的年份
	private String monthStartStr = ""; //默认选择的月份
	private String dayStartStr = ""; //默认选择的日期
	private String hourStartStr = ""; //默认选择的时

	private String showStartDateStr = ""; //显示的日期text
	private Date showStartDate; //日期

	private int curYearEnd = 0; //当前选择的item
	private int curMonthEnd = 0;
	private int curDayEnd = 0;
	private int curHoursEnd = 0;

	private String yearEndStr = ""; //默认选择的年份
	private String monthEndStr = ""; //默认选择的月份
	private String dayEndStr = ""; //默认选择的日期
	private String hourEndStr = ""; //默认选择的时

	private String showEndDateStr = ""; //显示的日期text
	private Date showEndDate; //日期

	private LogisticsSelfOrder data;

	private TransportManagerImpl transportManagerImpl;


	String zhuanglu1 = "";
	String zhuanghao1 = "";
	String xielu1 = "";
	String xiehao1 = "";
	String dunnum1 = "";
	String zhuanglu2 = "";
	String zhuanghao2 = "";
	String xielu2 = "";
	String xiehao2 = "";
	String dunnum2 = "";


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
		tvTitle.setText("自助下单");
		backLayout.setOnClickListener(this);

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

		et_zhuang_lu2 = (EditText) findViewById(R.id.et_zhuang_lu2);
		et_zhuang_hao2 = (EditText) findViewById(R.id.et_zhuang_hao2);
		et_xie_lu2 = (EditText) findViewById(R.id.et_xie_lu2);
		et_xie_hao2 = (EditText) findViewById(R.id.et_xie_hao2);
		et_dunnum2 = (EditText) findViewById(R.id.et_dunnum2);

		tv_tihuo_date = (TextView) findViewById(R.id.tv_tihuo_date);
		tv_daohuo_date = (TextView) findViewById(R.id.tv_daohuo_date);
		et_remarks = (EditText) findViewById(R.id.et_remarks);
		et_membername = (EditText) findViewById(R.id.et_membername);
		et_note = (EditText) findViewById(R.id.et_note);
		tv_tihuo_date.setOnClickListener(this);
		tv_daohuo_date.setOnClickListener(this);

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
		btn_submit.setOnClickListener(this);
	}

	private void initData()
	{
		transportManagerImpl = new TransportManagerImpl(mContext);

		data = (LogisticsSelfOrder)getIntent().getExtras().getSerializable("data");
		if(null==data){
			this.finish();
			return;
		}
		tv_from.setText(data.getStartAddress().replace("|", " "));
		tv_target.setText(data.getEndAddress().replace("|", " "));
		tv_type.setText(data.getFoodType());
		tv_num.setText(data.getFoodNum() + "吨");

		float price = Float.parseFloat(data.getTaxPrice11())+10;
		priceResult = price+"";
		tv_price.setText(priceResult);

		if(data.getLoad().equals("1")&&data.getUnload().equals("1")){
			ll_show2.setVisibility(View.GONE);
			loadFlag = false;
			et_dunnum1.setFocusable(false);
			et_dunnum1.setText(data.getFoodNum());
		}else{
			ll_show2.setVisibility(View.VISIBLE);
			loadFlag = true;

			et_dunnum1.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					float num2 = 0;
					if(!TextUtils.isEmpty(et_dunnum2.getText().toString())){
						try {
							num2 = Float.parseFloat(et_dunnum2.getText().toString());
						} catch (NumberFormatException e) {
							num2 = 0;
						}
					}
					float sTemp = 0;
					if(!TextUtils.isEmpty(s.toString())){
						sTemp = Float.parseFloat(s.toString());
					}
					if((sTemp+num2)==Float.parseFloat(data.getFoodNum())){
						return;
					}else{
						if(sTemp>Float.parseFloat(data.getFoodNum())){
							et_dunnum1.setText(data.getFoodNum());
							et_dunnum2.setText("0");
						}else{
							num2 = Float.parseFloat(data.getFoodNum()) - sTemp;
							et_dunnum2.setText(num2+"");
						}
					}
				}
			});

			et_dunnum2.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					float num1 = 0;
					if(!TextUtils.isEmpty(et_dunnum1.getText().toString())){
						try {
							num1 = Float.parseFloat(et_dunnum1.getText().toString());
						} catch (NumberFormatException e) {
							num1 = 0;
						}
					}
					float sTemp = 0;
					if(!TextUtils.isEmpty(s.toString())){
						sTemp = Float.parseFloat(s.toString());
					}
					if((sTemp+num1)==Float.parseFloat(data.getFoodNum())){
						return;
					}else{
						if(sTemp>Float.parseFloat(data.getFoodNum())){
							et_dunnum2.setText(data.getFoodNum());
							et_dunnum1.setText("0");
						}else{
							num1 = Float.parseFloat(data.getFoodNum()) - sTemp;
							et_dunnum1.setText(num1+"");
						}
					}
				}
			});
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
			case R.id.iv_exchange:
				// 交换
				getExChange();
				break;
			case R.id.ll_invoice11:
				selFapiao(FapiaoType.zengpiao11);
				break;
			case R.id.ll_invoice6:
				selFapiao(FapiaoType.zengpiao6);
				break;
			case R.id.ll_daofu:
				selFapiao(FapiaoType.daofu);
				break;
			case R.id.ll_need:
				selZixie(true);
				break;
			case R.id.ll_noneed:
				selZixie(false);
				break;
			case R.id.tv_tihuo_date:
				selDate(FoodDate.start);
				break;
			case R.id.tv_daohuo_date:
				selDate(FoodDate.end);
				break;
			case R.id.btn_submit:
				onClickSubmit();
				break;
			default:
				break;
		}
	}

	/**
	 * 提交订单
	 */
	private void onClickSubmit(){
		zhuanglu1 = et_zhuang_lu1.getText().toString();
		zhuanghao1 = et_zhuang_hao1.getText().toString();
		xielu1 = et_xie_lu1.getText().toString();
		xiehao1 = et_xie_hao1.getText().toString();
		dunnum1 = et_dunnum1.getText().toString();

		if(TextUtils.isEmpty(zhuanglu1)||TextUtils.isEmpty(zhuanghao1)||TextUtils.isEmpty(xielu1)||TextUtils.isEmpty(xiehao1)||TextUtils.isEmpty(dunnum1)){
			Tools.showToast(mContext,"详细信息不能为空");
			return;
		}


		if(loadFlag){
			zhuanglu2 = et_zhuang_lu2.getText().toString();
			zhuanghao2 = et_zhuang_hao2.getText().toString();
			xielu2 = et_xie_lu2.getText().toString();
			xiehao2 = et_xie_hao2.getText().toString();
			dunnum2 = et_dunnum2.getText().toString();

			if(TextUtils.isEmpty(zhuanglu2)||TextUtils.isEmpty(zhuanghao2)||TextUtils.isEmpty(xielu2)||TextUtils.isEmpty(xiehao2)||TextUtils.isEmpty(dunnum2)){
				Tools.showToast(mContext,"详细信息不能为空");
				return;
			}
		}
		if(showStartDate==null){
			Tools.showToast(mContext,"请选择提货时间!");
			return;
		}
		if(showEndDate==null){
			Tools.showToast(mContext,"请选择到货时间!");
			return;
		}

		LogUtils.e(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss",showStartDate));
		LogUtils.e(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss",showEndDate));

		long difDate1 = DateUtil.dateDiff(13,new Date(),showStartDate);
		if(difDate1<=0){
			Tools.showToast(mContext,"提货时间不能小于今天!");
			return;
		}

		long difDate2 = DateUtil.dateDiff(13, showStartDate, showEndDate);
		if(difDate2<=0){
			Tools.showToast(mContext,"到货时间不能小于提货时间!");
			return;
		}

		//取单地址
		String qudanStr = et_remarks.getText().toString();
		if(TextUtils.isEmpty(qudanStr)){
			Tools.showToast(mContext,"取单地址不能为空!");
			return;
		}

		//取单地址
		String memberNameStr = et_membername.getText().toString();
		if(TextUtils.isEmpty(memberNameStr)){
			Tools.showToast(mContext,"公司名称不能为空!");
			return;
		}

		ShowDialog dialog = new ShowDialog(mContext, "您确定提交订单吗?", new ShowDialog.ICallBack() {
			@Override
			public void requestOK() {
//				Tools.showToast(mContext,"提交成功!");
//				finish();
				CreateOrder();
			}
			@Override
			public void requestCancle() {

			}
		});
		dialog.show();
	}


	/**
	 * 创建订单
	 */
	private void CreateOrder(){
		//取单地址
		String qudanStr = et_remarks.getText().toString();
		//公司名称
		String memberNameStr = et_membername.getText().toString();
		//备注要求
		String noteStr = et_note.getText().toString();

		String name = SharePreferenceUtil.getString(this, Constants.USER_NAME);
		String phone = SharePreferenceUtil.getString(this,Constants.PREFERENCES_CELLPHONE);

		Map<String,String> map = new HashMap<String, String>();
		map.put("phone",phone);
		map.put("startCity", data.getStartAddress().split("\\|")[0]);
		map.put("startDistrict",data.getStartAddress().split("\\|")[1]);
		map.put("endCity", data.getEndAddress().split("\\|")[0]);
		map.put("endDistrict",data.getEndAddress().split("\\|")[1]);
		map.put("breed",data.getFoodType());
		if(flag){
			map.put("zixieType","10");
		}else{
			map.put("zixieType","0");
		}
		map.put("qudanAddress",qudanStr);
		String memberId = SharePreferenceUtil.getString(this,
				Constants.USER_MEMBERID);// memberId
		map.put("memberId",memberId);
		map.put("memberName",memberNameStr);
		map.put("startTime",DateUtil.getFormatDate("yyyyMMddHHmmss",showStartDate));
		map.put("endTime",DateUtil.getFormatDate("yyyyMMddHHmmss",showEndDate));
		map.put("note",noteStr);
		map.put("price",priceResult);

		zhuanglu1 += "路";
		zhuanghao1+= "号";
		xielu1 += "路";
		xiehao1 += "号";

		if(loadFlag){
			if(TextUtils.isEmpty(zhuanglu2)||TextUtils.isEmpty(zhuanghao2)||TextUtils.isEmpty(xielu2)||TextUtils.isEmpty(xiehao2)||TextUtils.isEmpty(dunnum2)){
				zhuanglu2 += "路";
				zhuanghao2+= "号";
				xielu2 += "路";
				xiehao2 += "号";
			}
		}

		map.put("startAddress1",zhuanglu1+zhuanghao1);
		map.put("startAddress2",zhuanglu2+zhuanghao2);
		map.put("endAddress1",xielu1+xiehao1);
		map.put("endAddress2",xielu2+xiehao2);
		map.put("userName",name);
		map.put("weight1",dunnum1);
		map.put("weight2",dunnum2);
		float totalWeight = Float.parseFloat(dunnum1);
		if(!TextUtils.isEmpty(dunnum2)){
			totalWeight += Float.parseFloat(dunnum2);
			if(totalWeight!= Float.parseFloat(data.getFoodNum())){
				Tools.showToast(mContext,"重量填写有误!");
				return;
			}
		}
		map.put("totalWeight",totalWeight+"");
		map.put("load",data.getLoad());
		map.put("unload",data.getUnload());
		String taxRate = "0.11";
		if(fapiaoType==FapiaoType.zengpiao11){
			taxRate = "0.11";
		}
		if(fapiaoType==FapiaoType.zengpiao6){
			taxRate = "0.06";
		}
		if(fapiaoType==FapiaoType.daofu){
			taxRate = "0";
		}
		map.put("taxRate",taxRate);
		float resultTotalPrice = Float.parseFloat(priceResult)*totalWeight;
		map.put("taxPrice",priceResult+"");
		map.put("favorablePrice",data.getDiscountPrice());
		String url = RequestUrl.getInstance(this).getUrl_createTransportOrder(this, map);
		LogUtils.e(url);
		transportManagerImpl.getCreateTransportOrder(url, Constants.INTERFACE_createTransportOrder);
	}

	/**
	 * 交换位置
	 */
	private void getExChange(){

	}

	/**
	 * 选择日期
	 */
	private void selDate(final FoodDate foodDate){
		DatePopupWheel popupWheel = new DatePopupWheel(mContext, new DatePopupWheel.PopupValueLinstener() {
			@Override
			public void change(int firstValue, int secondValue, int thirdValue, int fourValue) {
				//Tools.showToast(mContext,year[firstValue]+""+month[secondValue]+""+day[thirdValue]+""+hours[fourValue]);
				//转换成日期格式
				if(foodDate == FoodDate.start){
					curYearStart = firstValue;
					curMonthStart = secondValue;
					curDayStart = thirdValue;
					curHoursStart = fourValue;

					yearStartStr = year[firstValue]; //2015年
					int yearTemp = Integer.parseInt(yearStartStr.substring(0,yearStartStr.length()-1));

					monthStartStr = month[secondValue];
					int monthTemp = Integer.parseInt(monthStartStr.substring(0,monthStartStr.length()-1));

					dayStartStr = day[thirdValue];
					int dayTemp = Integer.parseInt(dayStartStr.substring(0,dayStartStr.length()-1));

					hourStartStr = hours[fourValue];
					int hourTemp = Integer.parseInt(hourStartStr.substring(0,hourStartStr.length()-3));

					//转化成显示格式和日期
					showStartDateStr = yearTemp+"-"+monthTemp+"-"+dayTemp+"    "+hourTemp;
					showStartDate = DateUtil.getDate("yyyy-MM-dd HH:mm:ss", yearTemp+"-"+monthTemp+"-"+dayTemp+" "+hourTemp+":00:00");
					tv_tihuo_date.setText(showStartDateStr);
				}else{
					curYearEnd = firstValue;
					curMonthEnd = secondValue;
					curDayEnd = thirdValue;
					curHoursEnd = fourValue;

					yearEndStr = year[firstValue]; //2015年
					int yearTemp = Integer.parseInt(yearEndStr.substring(0,yearEndStr.length()-1));

					monthEndStr = month[secondValue];
					int monthTemp = Integer.parseInt(monthEndStr.substring(0,monthEndStr.length()-1));

					dayEndStr = day[thirdValue];
					int dayTemp = Integer.parseInt(dayEndStr.substring(0,dayEndStr.length()-1));

					hourEndStr = hours[fourValue];
					int hourTemp = Integer.parseInt(hourEndStr.substring(0,hourEndStr.length()-3));

					//转化成显示格式和日期
					showEndDateStr = yearTemp+"-"+monthTemp+"-"+dayTemp+"    "+hourTemp;
					showEndDate = DateUtil.getDate("yyyy-MM-dd HH:mm:ss", yearTemp+"-"+monthTemp+"-"+dayTemp+" "+hourTemp+":00:00");
					tv_daohuo_date.setText(showEndDateStr);
				}
			}

			@Override
			public String[] changeData(int length) {
				day = new String[length];
				for (int i = 0; i < length; i++) {
					day[i] = i + 1 + "号";
				}
				return day;
			}
		});


		//年份初始化
		year[0] = DateUtil.getYear(new Date())+"年";
		year[1] = DateUtil.getYear(new Date())+1+"年";
		//月份初始化
		for (int i = 0; i < month.length; i++) {
			month[i] = i+1 + "月";
		}
		//日初始化
		int curDayCount = (int)DateUtil.getMonthDayCount(new Date());
		day = new String[curDayCount];
		for (int i = 0; i < curDayCount; i++) {
			day[i] = i + 1 + "号";
		}
		//小时初始化
		for (int i = 0; i < hours.length; i++) {
			hours[i] = i + ":00";
		}

		//初始化一些date值
		if(foodDate == FoodDate.start){
			if(TextUtils.isEmpty(yearStartStr)&&TextUtils.isEmpty(monthStartStr)&&TextUtils.isEmpty(dayStartStr)&&TextUtils.isEmpty(hourStartStr)){
				yearStartStr = DateUtil.getYear(new Date())+"年";
				monthStartStr = DateUtil.getMonth()+"月";
				dayStartStr = DateUtil.getDay()+"号";
				hourStartStr = DateUtil.getHour()+":00";
				//计算current位置
				for(int i = 0;i<year.length;i++){
					if(year[i].equals(yearStartStr)){
						curYearStart = i;
					}
				}
				for(int i = 0;i<month.length;i++){
					if(month[i].equals(monthStartStr)){
						curMonthStart = i;
					}
				}
				for(int i = 0;i<day.length;i++){
					if(day[i].equals(dayStartStr)){
						curDayStart = i;
					}
				}
				for(int i = 0;i<hours.length;i++){
					if(hours[i].equals(hourStartStr)){
						curHoursStart = i;
					}
				}
			}
		}else{
			if(TextUtils.isEmpty(yearEndStr)&&TextUtils.isEmpty(monthEndStr)&&TextUtils.isEmpty(dayEndStr)&&TextUtils.isEmpty(hourEndStr)){
					yearEndStr = DateUtil.getYear(new Date())+"年";
					monthEndStr = DateUtil.getMonth()+"月";
					dayEndStr = DateUtil.getDay()+"号";
					hourEndStr = DateUtil.getHour()+":00";
					//计算current位置
					for(int i = 0;i<year.length;i++){
						if(year[i].equals(yearEndStr)){
							curYearEnd = i;
						}
					}
					for(int i = 0;i<month.length;i++){
						if(month[i].equals(monthEndStr)){
							curMonthEnd = i;
						}
					}
					for(int i = 0;i<day.length;i++){
						if(day[i].equals(dayEndStr)){
							curDayEnd = i;
						}
					}
					for(int i = 0;i<hours.length;i++){
						if(hours[i].equals(hourEndStr)){
							curHoursEnd = i;
						}
					}
			}
		}

		View rootView = getWindow().getDecorView();
		if(foodDate == FoodDate.start){
			popupWheel.setData(year, curYearStart,0);
			popupWheel.setData(month, curMonthStart ,1);
			popupWheel.setData(day, curDayStart ,2);
			popupWheel.setData(hours, curHoursStart ,3);
		}else{
			popupWheel.setData(year, curYearEnd,0);
			popupWheel.setData(month, curMonthEnd, 1);
			popupWheel.setData(day, curDayEnd, 2);
			popupWheel.setData(hours, curHoursEnd, 3);
		}
		popupWheel.updateDayDatas();
		popupWheel.setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWheel.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
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

		changePrice();
	}

	/**
	 * 修改价格
	 */
	private void changePrice(){
		if(fapiaoType == FapiaoType.zengpiao11){
			priceResult = data.getTaxPrice11();
		}
		if(fapiaoType == FapiaoType.zengpiao6){
			priceResult = data.getTaxPrice6();
		}
		if(fapiaoType == FapiaoType.daofu){
			priceResult = data.getTaxPrice0();
		}

		if(flag){
			//priceResult = Float.parseFloat(priceResult)+10+"";
		}else{
			//priceResult = Float.parseFloat(priceResult)+"";
		}
		tv_price.setText(priceResult);
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

		changePrice();
	}

	@Override
	public void updateView(BaseData data) {
		if(data.getStatus().equals("true")){
			Tools.showToast(mContext,"下单成功!");
			EventBus.getDefault().post("","selforder");
			this.finish();
			return;
		}else{
			Tools.showToast(mContext,"下单失败，请重新下单或联系客服!");
			return;
		}
	}

	@Override
	public void isShowDialog(boolean flag) {
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
		if(transportManagerImpl != null)
		{
			transportManagerImpl.finishRequest();
		}
	}
}



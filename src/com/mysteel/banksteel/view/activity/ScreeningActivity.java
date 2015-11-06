package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.entity.SteelData.SteelBean;
import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;


/**
 * 筛选条件弹窗
 * 
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
public class ScreeningActivity extends BaseDialogActivity implements OnClickListener{

	private Context mContext;

	
	private LinearLayout ll_ok; //确定
	private LinearLayout ll_cancle; //取消
	
	private RelativeLayout rl_classification; //大分类
	private TextView tv_classification; 
	
	private RelativeLayout rl_category; //品类
	private TextView tv_category;
	
	private RelativeLayout rl_material; //材质
	private TextView tv_material;
	
	private RelativeLayout rl_specifications; //规格
	private TextView tv_specifications;
	
	private RelativeLayout rl_brand; //品牌/产地
	private TextView tv_brand;
	
	private RelativeLayout rl_manufacturer; //厂家
	private TextView tv_manufacturer;
	
	private RelativeLayout rl_warehouse; //存货地
	private TextView tv_warehouse;
	
	private LinearLayout ll_clearn; //清除内容

	/** 大分类选择 */
	private SteelType stype;
	/** 品类选择 */
	private SteelBean bean;
	/** 材质 */
	private String caizhi = "";
	/** 规格 */
	private String guige = "";
	/** 品牌 */
	private String brand = "";
	/** 存货地 */
	private String city = "";
	
	
	/** 品牌厂商 */
	public final static String SCREEN_TAG_BRAND = "brand";
	/** 存货地 */
	public final static String SCREEN_TAG_CITY = "city";
	
	public final static String CHILD_STEEL_ID = "childSteelId";
	
	private static String CHILDID = "childId";
	
	private int activityCloseEnterAnimation;
	private int activityCloseExitAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popwindow_screening);
		mContext = this;
		initViews();
		initData();
	}
	
	
	/**
	 * 初始化页面组件
	 */
//	@Override
	protected void initViews()
	{
		WindowManager m = getWindowManager();    
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高    
           
        LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值    
//      p.height = (int) (d.getHeight() * 0.8);   //高度设置为屏幕的1.0   
//      p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8   
        p.alpha = 1.0f; //设置本身透明度  
        p.dimAmount = 0.5f; //设置黑暗度  
        getWindow().setAttributes(p); //设置生效  
		getWindow().setGravity(Gravity.RIGHT|Gravity.TOP); //设置靠右对齐 
//		getWindow().setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画  
		
		/** 下边这段代码主要解决退出动画出现BUG的原因 START */
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});

		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      

		activityStyle.recycle();

		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

		activityStyle.recycle();
		/** 上边这段代码主要解决退出动画出现BUG的原因 END */
		
		ll_ok = (LinearLayout)findViewById(R.id.ll_ok);
		ll_ok.setOnClickListener(this);
		
		ll_cancle = (LinearLayout)findViewById(R.id.ll_cancle);
		ll_cancle.setOnClickListener(this);
		
		ll_clearn = (LinearLayout)findViewById(R.id.ll_clearn);
		ll_clearn.setOnClickListener(this);
		
		rl_classification = (RelativeLayout)findViewById(R.id.rl_classification);
		rl_classification.setOnClickListener(this);
		tv_classification = (TextView)findViewById(R.id.tv_classification);
		
		rl_category = (RelativeLayout)findViewById(R.id.rl_category);
		rl_category.setOnClickListener(this);
		tv_category = (TextView)findViewById(R.id.tv_category);
		
		rl_material = (RelativeLayout)findViewById(R.id.rl_material);
		rl_material.setOnClickListener(this);
		tv_material = (TextView)findViewById(R.id.tv_material);
		
		rl_specifications = (RelativeLayout)findViewById(R.id.rl_specifications);
		rl_specifications.setOnClickListener(this);
		tv_specifications = (TextView)findViewById(R.id.tv_specifications);
		
		rl_brand = (RelativeLayout)findViewById(R.id.rl_brand);
		rl_brand.setOnClickListener(this);
		tv_brand = (TextView)findViewById(R.id.tv_brand);
		
		rl_manufacturer = (RelativeLayout)findViewById(R.id.rl_manufacturer);
		rl_manufacturer.setOnClickListener(this);
		tv_manufacturer = (TextView)findViewById(R.id.tv_manufacturer);
		
		
		rl_warehouse = (RelativeLayout)findViewById(R.id.rl_warehouse);
		rl_warehouse.setOnClickListener(this);
		tv_warehouse = (TextView)findViewById(R.id.tv_warehouse);

	}
	
	private void initData()
	{
		//初始化数据
		stype = (SteelType)getIntent().getSerializableExtra("stype");
		bean = (SteelBean)getIntent().getSerializableExtra("bean");
		caizhi = getIntent().getStringExtra("caizhi");
		guige = getIntent().getStringExtra("guige");
		brand = getIntent().getStringExtra("brand");
		city = getIntent().getStringExtra("city");
		if(null==stype){
			stype = new SteelType("01", "建筑用钢");
		}
		if(null==bean){
			bean = new SteelData().new SteelBean("0101", "二级螺纹钢");
		}

		tv_classification.setText(stype.getName());
		tv_category.setText(bean.getName());
		if(!TextUtils.isEmpty(caizhi)){
			tv_material.setText(caizhi);
		}else{
			tv_material.setText("全部");
		}
		if(!TextUtils.isEmpty(guige)){
			tv_specifications.setText(guige);
		}else{
			tv_specifications.setText("全部");
		}

		if(!TextUtils.isEmpty(brand)){
			tv_brand.setText(brand);
		}else{
			tv_brand.setText("全部");
		}

		if(!TextUtils.isEmpty(city)){
			tv_warehouse.setText(city);
		}else{
			tv_warehouse.setText("全部");
		}
	}
	
	
	@Subscriber(tag = "screening1activity")
	private void getFenlei(SteelType stype)
	{
		if(null!=this.stype&&this.stype.getId()!=stype.getId()){
			//如果大分类改变了 就要清除其他数据
			bean = null;
			caizhi = "";
			guige = "";
			brand = "";
			city = "";
			
			tv_category.setText("全部");
			tv_material.setText("全部");
			tv_specifications.setText("全部");
			tv_brand.setText("全部");
			tv_warehouse.setText("全部");
		}
		this.stype = stype;
		tv_classification.setText(stype.getName());
	}
	
	@Subscriber(tag = "screening1activity_son")
	private void getFenlei_Son(SteelBean bean)
	{
		if(null!=bean){
			this.bean = bean;
			tv_category.setText(this.bean.getName());
		}
	}
	
	@Subscriber(tag = "screening2activity")
	private void getPinlei(SteelBean bean)
	{
		if(null!=this.bean&&this.bean.getId()!=bean.getId()){
			//如果品类改变了 就要清除其他数据
			caizhi = "";
			guige = "";
			brand = "";
			city = "";
			
			tv_material.setText("全部");
			tv_specifications.setText("全部");
			tv_brand.setText("全部");
			tv_warehouse.setText("全部");
		}
		this.bean = bean;
		tv_category.setText(bean.getName());
	}
	
	@Subscriber(tag = "screening3activity")
	private void getCaizhi(String caizhi)
	{
		this.caizhi = caizhi;
		if(!TextUtils.isEmpty(this.caizhi)){
			tv_material.setText(caizhi);
		}else{
			tv_material.setText("全部");
		}
	}
	
	@Subscriber(tag = "screening4activity")
	private void getGuige(String guige)
	{
		this.guige = guige;
		if(!TextUtils.isEmpty(this.guige)){
			tv_specifications.setText(guige);
		}else{
			tv_specifications.setText("全部");
		}
	}
	
	@Subscriber(tag = "screening5activity_brank")
	private void getBrand(String brand)
	{
		this.brand = brand;
		if(!TextUtils.isEmpty(this.brand)){
			tv_brand.setText(brand);
		}else{
			tv_brand.setText("全部");
		}
	}
	
	@Subscriber(tag = "screening5activity_city")
	private void getCity(String city)
	{
		this.city = city;
		if(!TextUtils.isEmpty(this.city)){
			tv_warehouse.setText(city);
		}else{
			tv_warehouse.setText("全部");
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.ll_ok:// 确定返回结束
				closeBack();
	            break;
			case R.id.ll_cancle:// 取消返回结束
				closeBackCancle();
	            break;    
			case R.id.ll_clearn: // 清除内容
				clearAll();
			    break;
			case R.id.rl_classification:// 大分类
				startActivity(new Intent(mContext, ScreeningFenleiActivity.class));
				break;
			case R.id.rl_category:// 品类
				selPinlei();
				break;
			case R.id.rl_material:// 材质
				selCaizhi();
				break;
			case R.id.rl_specifications:// 材质
				selGuige();
				break;
			case R.id.rl_brand:// 品牌产地
				selPinpai();
				break;
			case R.id.rl_warehouse:// 存货地
				setCity();
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * 确定后返回
	 */
	private void closeBack(){
		 String dafenleiTemp = "";
		 String pinleiTemp = "";
		 String caizhiTemp = "";
		 String guigeTemp = "";
		 String pinpaiTemp = "";
		 String cunhuodiTemp = "";
		 if(null!=stype){
			 dafenleiTemp = stype.getName();
		 }else{
			 Tools.showToast(mContext, "大分类不能为空!");
			 return;
		 }
		 if(null!=bean){
			 pinleiTemp = bean.getName();
		 }else{
			 Tools.showToast(mContext, "分类不能为空!");
			 return;
		 }
		 caizhiTemp = caizhi;
		 guigeTemp = guige;
		 pinpaiTemp = brand;
		 cunhuodiTemp = city;

		 EventBus.getDefault().post(stype, "dafenleitype");
		 EventBus.getDefault().post(bean, "pinleitype");
		 EventBus.getDefault().post(dafenleiTemp, "dafenlei");
		 EventBus.getDefault().post(pinleiTemp, "pinlei");
		 EventBus.getDefault().post(caizhiTemp, "caizhi");
		 EventBus.getDefault().post(guigeTemp, "guige");
		 EventBus.getDefault().post(pinpaiTemp, "pinpai");
		 EventBus.getDefault().post(cunhuodiTemp, "cunhuodi");
		 EventBus.getDefault().post("", "closeback");
         super.finish();
         overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
	
	/**
	 * 取消
	 */
	private void closeBackCancle(){
		super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
	
	/**
	 * 清除内容
	 */
	private void clearAll(){
		stype = new SteelType("01", "建筑用钢");
		bean = new SteelData().new SteelBean("0101", "二级螺纹钢");
		caizhi = "";
		guige = "";
		brand = "";
		city = "";
		
		tv_classification.setText(stype.getName());
		tv_category.setText(bean.getName());
		tv_material.setText("全部");
		tv_specifications.setText("全部");
		tv_brand.setText("全部");
		tv_warehouse.setText("全部");
		
	}
	
	/**
	 * 选择品类
	 */
	private void selPinlei(){
		if(null!=stype){
			Intent intent = new Intent(mContext, ScreeningPinleiActivity.class);
			intent.putExtra("parentSteel", stype);
			startActivity(intent);
		}else{
			Tools.showToast(mContext, "请选择大分类");
		}
	}
	
	/**
	 * 选择材质
	 */
	private void selCaizhi(){
		if(null!=bean){
			Intent intent = new Intent(mContext, ScreeningCaizhiActivity.class);
			intent.putExtra("childId", bean.getId());
			startActivity(intent);
		}else{
			Tools.showToast(mContext, "请选择品类");
		}
	}
	
	/**
	 * 选择规格
	 */
	private void selGuige(){
		if(null!=bean){
			Intent intent = new Intent(mContext, ScreeningGuigeActivity.class);
			intent.putExtra("childId", bean.getId());
			startActivity(intent);
		}else{
			Tools.showToast(mContext, "请选择品类");
		}
	}
	
	/**
	 * 选择品牌产地
	 */
	private void selPinpai(){
		if(null!=bean){
			Intent intent = new Intent(mContext, ScreeningPinpaiChandiActivity.class);
			intent.putExtra(Constants.NEXT_SCREEN_TAG, SCREEN_TAG_BRAND);
			intent.putExtra(CHILD_STEEL_ID, bean.getId());
			startActivity(intent);
		}else{
			Tools.showToast(mContext, "请选择品类");
		}
	}
	
	/**
	 * 选择存货地
	 */
	private void setCity(){
		Intent intent = new Intent(mContext, ScreeningPinpaiChandiActivity.class);
		intent.putExtra(Constants.NEXT_SCREEN_TAG, SCREEN_TAG_CITY);
		startActivity(intent);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
        	closeBackCancle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
}


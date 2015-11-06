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

import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;


/**
 * 更多商机的筛选条件弹窗
 * 
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
public class OpportunityScreeningActivity extends BaseDialogActivity implements OnClickListener{

	private Context mContext;

	
	private LinearLayout ll_ok;
	private LinearLayout ll_cancle; //取消

	private LinearLayout ll_clearn; //清除内容
	private RelativeLayout rl_classification; //大分类
	private TextView tv_classification; 
	
	private RelativeLayout rl_warehouse; //存货地
	private TextView tv_warehouse;

	/** 大分类选择 */
	private SteelType stype;
	/** 存货地 */
	private String city = "";
	/** 存货地 */
	public final static String SCREEN_TAG_CITY = "city";
	
	private int activityCloseEnterAnimation;
	private int activityCloseExitAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popwindow_screening_opportunity);
		mContext = this;
		initViews();
		initData();
	}
	
	
	/**
	 * 初始化页面组件
	 */
	//@Override
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
		
		
		rl_warehouse = (RelativeLayout)findViewById(R.id.rl_warehouse);
		rl_warehouse.setOnClickListener(this);
		tv_warehouse = (TextView)findViewById(R.id.tv_warehouse);

	}
	
	
	@Subscriber(tag = "screening1activity")
	private void getFenlei(SteelType stype)
	{
		if(null!=stype&&!TextUtils.isEmpty(stype.getId())){
			this.stype = stype;
			tv_classification.setText(this.stype.getName());
		}else{
			tv_classification.setText("全部");
		}
	}

	@Subscriber(tag = "screening1activity2")
	private void getFenlei2(String str)
	{
		this.stype = null;
		tv_classification.setText("全部");
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

	private void initData()
	{
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.ll_ok:// 返回结束
				closeBack();
	            break;
			case R.id.ll_cancle:// 取消返回结束
				closeBackCancle();
	            break;
			case R.id.ll_clearn:
				clearAll(); //清除内容
				break;
			case R.id.rl_classification:// 大分类
				setDafenlei();
				break;
			case R.id.rl_warehouse:// 存货地
				setCity();
				break;
			default:
				break;
		}
	}

	/**
	 * 清除内容
	 */
	private void clearAll(){
		this.stype = null;
		this.city = "";
		tv_classification.setText("全部");
		tv_warehouse.setText("全部");
	}

	/**
	 * 确定后返回
	 */
	private void closeBack(){
		String dafenleiTemp = "";
		String cunhuodiTemp = "";
		if(null!=stype){
			dafenleiTemp = stype.getId();
		}
		cunhuodiTemp = city;
		EventBus.getDefault().post(dafenleiTemp, "dafenlei");
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
	 * 选择大分类
	 */
	private void setDafenlei(){
		Intent intent = new Intent(mContext, ScreeningFenleiActivity.class);
		intent.putExtra("fromFLag", "opportunityscreeningactivity");
		startActivity(intent);
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


package com.mysteel.banksteel.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SearchResourceData.Data.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.ShowDialog.ICallBack;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;


/**
 * 创建意向订单
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
public class ResouceDetailSubmitActivity extends Activity implements OnClickListener, IOrderTradeView{

	private Context mContext;
	private Datas datas;
	private IOrderTrade orderTrade;
	
	private ProgressBar progressBar;
	private EditText et_buy_number; //输入的购买数量
	private EditText et_buy_price; //输入的购买价格
	private ImageView img_switch; //是否可议价
	private Button btn_submit; //提交
	private Button btn_cancle; //取消
	
	private boolean tag = false; //true为可议价  false为一口价
	
	private String qty = "";
	private String price = "";
	private int activityCloseEnterAnimation;
	private int activityCloseExitAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resources_detail_submit);
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
        p.width = (int) (d.getWidth() * 1);    //宽度设置为屏幕的0.8   
        p.alpha = 1.0f; //设置本身透明度  
        p.dimAmount = 0.5f; //设置黑暗度  
        getWindow().setAttributes(p); //设置生效  
		getWindow().setGravity(Gravity.BOTTOM); //设置靠右对齐 
		
		/** 下边这段代码主要解决退出动画出现BUG的原因 START */
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});

		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      

		activityStyle.recycle();

		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

		activityStyle.recycle();
		/** 上边这段代码主要解决退出动画出现BUG的原因 END */
		
		orderTrade = new OrderTradeImpl(this, this);
		
		datas = (Datas)getIntent().getSerializableExtra("datas");
		
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		et_buy_number = (EditText)findViewById(R.id.et_buy_number);
		et_buy_price = (EditText)findViewById(R.id.et_buy_price);
		img_switch = (ImageView)findViewById(R.id.img_switch);
		img_switch.setOnClickListener(this);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		btn_cancle = (Button)findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(this);

		et_buy_price.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().equals(datas.getPrice())){
					tag = false;
					img_switch.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_swich2));
				}else{
					tag = true;
					img_switch.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.keyijia));
				}
			}
		});

	}
	
	private void initData()
	{
		//初始化为可议价
		taggleSwitch();
	}
	
	


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btn_submit:// 提交
				submit();
	            break;
			case R.id.btn_cancle:// 取消
				closeBackCancle();
	            break;    
			case R.id.img_switch: // 是否可议价
				tag = !tag;
				taggleSwitch();
			    break;
			default:
				break;
		}
	}
	
	
	private void taggleSwitch(){
    	if(tag){
    		img_switch.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_swich3));
//    		et_buy_price.setFocusable(true);
//    		et_buy_price.setFocusableInTouchMode(true);
    		et_buy_price.requestFocus();
//    		et_buy_price.requestFocusFromTouch();
//    		et_buy_price.setSelection(et_buy_price.getText().toString().length());
			et_buy_price.setText("");
    	}else{
    		img_switch.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_swich2));
			et_buy_price.requestFocus();
			et_buy_price.setText(datas.getPrice());
    	}
    }
	
	/**
	 * 提交报价
	 */
	private void submit(){
		 qty = et_buy_number.getText().toString();
		 price = et_buy_price.getText().toString();
		 if(TextUtils.isEmpty(qty)){
			 Tools.showToast(mContext, "求购数量不能为空!");
			 return;
		 }
		 if(TextUtils.isEmpty(price)){
			 Tools.showToast(mContext, "购买价格不能为空!");
			 return;
		 }

		 if(Float.parseFloat(price)<=0){
			 Tools.showToast(mContext, "请输入正确的购买价格!");
			 return;
		 }
		 
		 if(Integer.parseInt(qty)<=0){
			 Tools.showToast(mContext, "请填写正确的求购数量!");
			 return;
		 }
		 if(Float.parseFloat(price)<=0){
			 Tools.showToast(mContext, "请填写正确的购买价格!");
			 return;
		 }


		 String content = "您确定以"+price+"元/吨"+"的价格购买"+qty+"吨"+datas.getBreedName()+"吗?";
		
		 ShowDialog dialog = new ShowDialog(mContext, content,new ICallBack() {
			@Override
			public void requestOK() {
				String url = RequestUrl.getInstance(mContext).getUrl_CreateIntentionOrder(mContext, datas.getId(), qty, price);
				LogUtils.e(url);
				orderTrade.getCreateIntentionOrder(url, Constants.INTERFACE_createResourceOrder);
			}
			
			@Override
			public void requestCancle() {

			}
		});
		dialog.show();
	}
	
	/**
	 * 取消
	 */
	private void closeBackCancle(String fromStr){
		EventBus.getDefault().post(qty, "qty");
		EventBus.getDefault().post(fromStr, "closeback");
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


	@Override
	public void updateView(BaseData data) {
		closeBackCancle(data.getStatus());
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
        	closeBackCancle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


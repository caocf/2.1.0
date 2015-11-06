package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.ShowDialog.ICallBack;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.Date;

/**
 * 同城和更多详情
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
public class SellSourceDetailActivity extends SwipeBackActivity implements View.OnClickListener, IBuyView
{
		
		private Context mContext;
		private DatasEntity datasEntity;

		private ProgressBar progressBar;
		
		private TextView tv_time; //求购信息的时间
		private TextView tv_partname; //品名
		private TextView tv_spec; //规格
		private TextView tv_material; //材质
		private TextView tv_origin; //品牌产地
		private TextView tv_delivery; //交货地
		private TextView tv_number; //求购数量
		private TextView tv_validity; //有效期
		private TextView tv_check; //查看同类资源信息

		private TextView tv_baojia_num; //已有几个人报价
		
		private RelativeLayout rl_layout_buyers; //买家信息
		private ImageView circle; //买家头像
		private TextView tv_name; //买家姓名
		private ImageView img_renzheng; //是否已认证
		private TextView tv_address; //公司地址
		private TextView tv_company; //公司名称
		private EditText et_speech_phonenumber; //我的报价
		private EditText et_additional_text; //备注信息
		private ImageView img_switch; //是否可议价
		private Button btn_tijiao; //提交报价
		
		private boolean tag = false; //true可议价 false一口价
		String price = ""; //我的报价
		String note = ""; //备注
		String bargaining = ""; //0-不可议价 1-可议价 
		
		
		private IBuyCenter buyCenterImpl;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_sellquote);
	        mContext = this;
	        initViews();
			initData();
	    }
	
	    protected void initViews()
	    {
	        super.initViews();
	        tvTitle.setText("求购单详情");
	        backLayout.setOnClickListener(this);
	        
	        datasEntity = (DatasEntity)getIntent().getSerializableExtra("datasEntity");
	        if(null==datasEntity){
	        	Tools.showToast(mContext, "资源读取错误");
	        	this.finish();
	        	return;
	        }
	        
	        progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
	        
	        tv_time = (TextView) findViewById(R.id.tv_time);
	        tv_partname = (TextView) findViewById(R.id.tv_partname);
	        tv_spec = (TextView) findViewById(R.id.tv_spec);
	        tv_material = (TextView) findViewById(R.id.tv_material);
	        tv_origin = (TextView) findViewById(R.id.tv_origin);
	        tv_delivery = (TextView) findViewById(R.id.tv_delivery);
	        tv_number = (TextView) findViewById(R.id.tv_number);
	        tv_validity = (TextView) findViewById(R.id.tv_validity);
	        tv_check = (TextView) findViewById(R.id.tv_check);
			tv_check.setOnClickListener(this);

			tv_baojia_num = (TextView) findViewById(R.id.tv_baojia_num);
	        
	        rl_layout_buyers = (RelativeLayout) findViewById(R.id.rl_layout_buyers);
	        circle = (ImageView) findViewById(R.id.circle);
			circle.setOnClickListener(this);
	        tv_name = (TextView) findViewById(R.id.tv_name);
	        img_renzheng = (ImageView) findViewById(R.id.img_renzheng);
	        tv_address = (TextView) findViewById(R.id.tv_address);
	        tv_company = (TextView) findViewById(R.id.tv_company);
	        et_speech_phonenumber = (EditText) findViewById(R.id.et_speech_phonenumber);
			et_additional_text = (EditText) findViewById(R.id.et_additional_text);
	        img_switch = (ImageView) findViewById(R.id.img_switch);
	        img_switch.setOnClickListener(this);
	        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
	        btn_tijiao.setOnClickListener(this);
	    }
	    
	    private void initData()
		{
			String nums = datasEntity.getQuotNum();
			nums = (TextUtils.isEmpty(nums)) ? "0" : nums;
			String people = "已有 " + "<font color='#69ccff'>" + nums + "</font>" + " 人报价";
			tv_baojia_num.setText(Html.fromHtml(people));

	    	//判断是否已报价过
	    	if(!datasEntity.getMyPrice().equals("0")){ //不为0说明已报价
	    		btn_tijiao.setText("您已报价");
	    		btn_tijiao.setBackgroundResource(R.drawable.findfoodup);
	    		et_speech_phonenumber.setText(datasEntity.getMyPrice());
	    		et_speech_phonenumber.setFocusable(false);
				et_additional_text.setText(datasEntity.getQuotNote());
				et_additional_text.setFocusable(false);
	    		img_switch.setClickable(false);
	    		btn_tijiao.setClickable(false);
	    		if(datasEntity.getBargaining().equals("0")){ //0不可议价
	    			tag = false;
		    		img_switch.setBackgroundResource(R.drawable.img_swich2);
	    		}else{
	    			tag = true;
		    		img_switch.setBackgroundResource(R.drawable.keyijia);
	    		}
	    		
	    	}else{
	    		btn_tijiao.setText("提交报价");
	    		btn_tijiao.setBackgroundResource(R.drawable.findfood);
	    		et_speech_phonenumber.setFocusable(true);
				et_additional_text.setFocusable(true);
	    		img_switch.setClickable(true);
	    		btn_tijiao.setClickable(true);
	    	}
	    	
	    	
	    	buyCenterImpl = new BuyImpl(this);
	    	String resultDate = "还剩";
//	    	long time = Long.parseLong(datasEntity.getDueTime());
//	        Date dateTemp = new Date(time);
			Date dateTemp = DateUtil.getDate("yyyy-MM-dd HH:mm:ss",datasEntity.getDueTime());
			long[] diffDate = DateUtil.dateDiffEx(new Date(), dateTemp);
			if(diffDate[0]!=0){
				resultDate += diffDate[0] + "天";
			}
			if(diffDate[1]!=0){
				resultDate += diffDate[1] + "小时";
			}
			if(diffDate[2]!=0){
				resultDate += diffDate[2] + "分";
			}
			//判断是否过期
			//if(diffDate[0]<=0&&diffDate[1]<=0&&diffDate[2]<=0){ ||if(datasEntity.getDueStatus().equals("1")){ //0求购单有效   1求购单过期 
			if(diffDate[0]<=0&&diffDate[1]<=0&&diffDate[2]<=0){ 
				rl_layout_buyers.setVisibility(View.GONE);
				tv_validity.setText("已过期");
			}else{
				rl_layout_buyers.setVisibility(View.VISIBLE);
				tv_validity.setText(resultDate);
			}
			tv_time.setText(datasEntity.getPublishTime());
			tv_partname.setText(datasEntity.getBreed()); 
			tv_spec.setText(datasEntity.getSpec());
			tv_material.setText(datasEntity.getMaterial());
			tv_origin.setText(datasEntity.getBrand());
			tv_delivery.setText(datasEntity.getCity());
			tv_number.setText(datasEntity.getQty() + "吨");
			
			if (!TextUtils.isEmpty(datasEntity.getUser().getPhoto()))
			{
				Tools.loadImage(datasEntity.getUser().getPhoto(),circle);
			}
			tv_name.setText(datasEntity.getUser().getName());
			//img_renzheng  是否认证
			tv_address.setText(datasEntity.getUser().getState()+"  "+datasEntity.getUser().getCity()+"  "+datasEntity.getUser().getArea());
			if(TextUtils.isEmpty(tv_address.getText().toString()))
			{
				tv_address.setText("未填写");
			}
			tv_company.setText(datasEntity.getCompany());
			
		}
	    
	    
	    /*@Subscriber(tag = "source_detail_item")
		private void setFenleiName(DatasEntity datasEntity)
		{
	    	this.datasEntity = datasEntity;
		}*/
	
	    @Override
	    public void onClick(View v)
	    {
	        switch (v.getId())
	        {
	            case R.id.menu_layout:// 返回
	                finish();
	                break;
	            case R.id.img_switch:// 是否可议价
	                taggleSwitch();
	                break;   
	            case R.id.btn_tijiao:// 提交报价
	            	subQuot();
	            	break;
				case R.id.tv_check: //查看同类资源
					sameResource();
					break;
				case R.id.circle:
					Intent iShow = new Intent(this, ShowUserInfoActivity.class);
					iShow.putExtra("friendId", datasEntity.getUser().getUserId());
					iShow.putExtra("friendPhone", datasEntity.getUser().getPhone());
					startActivity(iShow);
					break;
	        }
	    }


		/**
		 * 查看同类资源
		 */
		private void sameResource(){
			Intent i = new Intent(this, ResourceSameSellerActivity.class);
			i.putExtra("datas",datasEntity);
			i.putExtra("fromFlag", getIntent().getStringExtra("fromFlag"));
			startActivity(i);
		}
	    
	    /**
	     * 提交报价
	     */
	    private void subQuot(){
	    	price = et_speech_phonenumber.getText().toString();
			note = et_additional_text.getText().toString();
	    	bargaining = tag? "1":"0";
	    	String temp = tag? "可议价":"一口价";
	    	if(!TextUtils.isEmpty(price)){
	    		if(Integer.parseInt(price)>0){
	    			String tip = "您确定以" + price + "元/吨," + temp + "的方式提交报价吗?";
	    			ShowDialog dialog = new ShowDialog(mContext, tip, new ICallBack() {
						@Override
						public void requestOK() {
							//提交报价
			    			String url = RequestUrl.getInstance(mContext).getUrl_getSellQuot(mContext, datasEntity.getId(), bargaining, price, note);
			    			buyCenterImpl.getSellQuot(url, Constants.INTERFACE_getSpecsByBreedId);
						}
						
						@Override
						public void requestCancle() {
							
						}
					});
	    			dialog.show();
	    			
	    		}else{
	    			Tools.showToast(mContext, "请填写正确的报价!");
	    			return;
	    		}
	    	}else{
	    		Tools.showToast(mContext, "报价不能为空！");
	    		return;
	    	}
	    }
	    
	    private void taggleSwitch(){
	    	tag = !tag;
	    	if(tag){
	    		img_switch.setBackgroundResource(R.drawable.keyijia);
	    	}else{
	    		img_switch.setBackgroundResource(R.drawable.img_swich2);
	    	}
	    }

		@Override
		public void updateView(BaseData data) {
			if(data.getStatus().equals("true")){
				Tools.showToast(mContext, "提交成功!");
				EventBus.getDefault().post(true, "sellsourcedetailactivity");
				this.finish();
			}else{
				Tools.showToast(mContext, "提交失败!");
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
			buyCenterImpl.finishRequest();
		}
}

package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 卖家报价信息页面
 * Created by zoujian on 15/7/27.
 */
public class SellQuoteActivity extends SwipeBackActivity implements View.OnClickListener
{
		
		private Context mContext;
		private DatasEntity datasEntity;
		
		private TextView tv_time; //求购信息的时间
		private TextView tv_partname; //品名
		private TextView tv_spec; //规格
		private TextView tv_material; //材质
		private TextView tv_origin; //品牌产地
		private TextView tv_delivery; //交货地
		private TextView tv_number; //求购数量
		private TextView tv_validity; //有效期
		private TextView tv_check; //查看同类资源信息
		
		private RelativeLayout rl_layout_buyers; //买家信息
		private CircleImageView circle; //买家头像
		private TextView tv_name; //买家姓名
		private ImageView img_renzheng; //是否已认证
		private TextView tv_address; //公司地址
		private TextView tv_company; //公司名称
		private EditText et_speech_phonenumber; //我的报价
		private ImageView img_switch; //是否可议价
		private Button btn_tijiao; //提交报价
		
		
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
	        
	        tv_time = (TextView) findViewById(R.id.tv_time);
	        tv_partname = (TextView) findViewById(R.id.tv_partname);
	        tv_spec = (TextView) findViewById(R.id.tv_spec);
	        tv_material = (TextView) findViewById(R.id.tv_material);
	        tv_origin = (TextView) findViewById(R.id.tv_origin);
	        tv_delivery = (TextView) findViewById(R.id.tv_delivery);
	        tv_number = (TextView) findViewById(R.id.tv_number);
	        tv_validity = (TextView) findViewById(R.id.tv_validity);
	        tv_check = (TextView) findViewById(R.id.tv_check);
	        
	        rl_layout_buyers = (RelativeLayout) findViewById(R.id.rl_layout_buyers);
	        circle = (CircleImageView) findViewById(R.id.circle);
	        tv_name = (TextView) findViewById(R.id.tv_name);
	        img_renzheng = (ImageView) findViewById(R.id.img_renzheng);
	        tv_address = (TextView) findViewById(R.id.tv_address);
	        tv_company = (TextView) findViewById(R.id.tv_company);
	        et_speech_phonenumber = (EditText) findViewById(R.id.et_speech_phonenumber);
	        img_switch = (ImageView) findViewById(R.id.img_switch);
	        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
	        
	    }
	    
	    private void initData()
		{
	    	
	    	
		}
	
	    @Override
	    public void onClick(View v)
	    {
	        switch (v.getId())
	        {
	            case R.id.menu_layout:// 返回
	                finish();
	                break;
	        }
	    }
}

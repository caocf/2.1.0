package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.MyQuotData.DataEntity.DatasEntity;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import java.util.Date;

/**
 * 卖家报价单详情页面
 * Created by zoujian on 15/8/8.
 */
public class SellBaoJaoDetailsActivity extends SwipeBackActivity implements View.OnClickListener
{

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

    private RelativeLayout rl_layout_buyers; //买家信息
    private ImageView circle; //买家头像
    private TextView tv_name; //买家姓名
    private TextView tv_address; //公司地址
    private TextView tv_company; //公司名称
    private EditText et_speech_phonenumber; //我的报价
    private EditText et_additional_text; //我的备注
    private ImageView img_switch; //是否可议价
    private Button btn_tijiao; //提交报价

    private boolean tag = false; //true可议价 false一口价
    String price = ""; //我的报价
    String bargaining = ""; //0-不可议价 1-可议价
    private DatasEntity mData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellquote);
        Bundle b = getIntent().getExtras();
        mData = (DatasEntity) b.getSerializable("DATAS");
        initViews();
        changeView();
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("报价单详情");
        backLayout.setOnClickListener(this);

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

        rl_layout_buyers = (RelativeLayout) findViewById(R.id.rl_layout_buyers);
        circle = (ImageView) findViewById(R.id.circle);
        circle.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_company = (TextView) findViewById(R.id.tv_company);
        et_speech_phonenumber = (EditText) findViewById(R.id.et_speech_phonenumber);
        et_additional_text = (EditText) findViewById(R.id.et_additional_text);
        img_switch = (ImageView) findViewById(R.id.img_switch);
        img_switch.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setVisibility(View.GONE);
    }


    /**
     * 填充view的数据
     */
    private void changeView()
    {
        if (mData == null)
        {
            return;
        }
        tv_time.setText(mData.getQuotTime());
        tv_partname.setText(mData.getBreed());
        tv_spec.setText(mData.getSpec());
        tv_material.setText(mData.getMaterial());
        tv_origin.setText(mData.getBrand());
        tv_delivery.setText(mData.getCity());
        tv_number.setText(mData.getQty() + "吨");

        tv_name.setText(mData.getBuyUserName());
        et_speech_phonenumber.setText(mData.getPrice());
        et_speech_phonenumber.setFocusable(false);
        et_additional_text.setText(mData.getNote());
        et_additional_text.setFocusable(false);
        tv_company.setText(mData.getBuyMemberName());
        tv_address.setText(mData.getUserProvince() +" "+ mData.getUserCity() +" "+ mData.getUserArea());
        if(TextUtils.isEmpty(tv_address.getText().toString()))
        {
            tv_address.setText("未填写");
        }

        //bargaining  0-一口价 1-可议价
        if ("0".equals(mData.getBargaining()))
        {
            img_switch.setBackgroundResource(R.drawable.img_swich2);
        } else if ("1".equals(mData.getBargaining()))
        {
            img_switch.setBackgroundResource(R.drawable.keyijia);
        }
        if(!TextUtils.isEmpty(mData.getUserPhoto()))
        {
            Tools.loadImage(mData.getUserPhoto(),circle);
        }

        //0:正常报价 1:已成交报价 9:已过期
        if("0".equals(mData.getStatus()))
        {
//            tv_validity.setText(Tools.getDateToString(mData.getDueTime()));
//            tv_validity.setText(mData.getDueTime());

            String resultDate = "还剩";
//	    	long time = Long.parseLong(datasEntity.getDueTime());
//	        Date dateTemp = new Date(time);
            Date dateTemp = DateUtil.getDate("yyyy-MM-dd HH:mm:ss", mData.getDueTime());
            long[] diffDate = DateUtil.dateDiffEx(new Date(), dateTemp);
            if(diffDate[0]!=0){
                resultDate += diffDate[0] + "天";
            }
            if(diffDate[0]!=1){
                resultDate += diffDate[1] + "小时";
            }
            if(diffDate[0]!=2){
                resultDate += diffDate[2] + "分";
            }
            tv_validity.setText(resultDate);
        }else if("1".equals(mData.getStatus()))
        {

        }else if("9".equals(mData.getStatus()))
        {
            tv_validity.setText("已失效");
        }


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回
                finish();
                break;

            case R.id.tv_check:
                Intent i = new Intent(this, ResourceOrderSameSellerActivity.class);
                i.putExtra("datas",mData);
                startActivity(i);
                break;
            case R.id.circle:
                Intent iShow = new Intent(this, ShowUserInfoActivity.class);
                iShow.putExtra("friendId", mData.getBuyUserId());
                iShow.putExtra("friendPhone", mData.getBuyPhone());
                startActivity(iShow);
                break;
        }
    }
}

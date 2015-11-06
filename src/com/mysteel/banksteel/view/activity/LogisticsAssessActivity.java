package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.ITransportManager;
import com.mysteel.banksteel.ao.impl.TransportManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.LogisticsOrderData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 物流评价页面
 * Created by zoujian on 15/8/18.
 */
public class LogisticsAssessActivity extends SwipeBackActivity implements View.OnClickListener,ITransportManagerView
{

    Context mContext;

    private ITransportManager iTransportManager;

    private RatingBar rb_pingfen,rb_huo,rb_price;
    private EditText et_pingjia;
    private Button btn_submit;

    private ProgressBar progressBar;

    private String orderID = "";
    private LogisticsOrderData.DataEntity.DatasEntity datas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_assess);
        mContext = this;
        initViews();
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("物流评价");
        backLayout.setOnClickListener(this);
        iTransportManager = new TransportManagerImpl(this);
        orderID = getIntent().getStringExtra("orderID");
        datas = (LogisticsOrderData.DataEntity.DatasEntity)getIntent().getSerializableExtra("datas");

        progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        rb_pingfen = (RatingBar)findViewById(R.id.rb_pingfen);
        rb_huo = (RatingBar)findViewById(R.id.rb_huo);
        rb_price = (RatingBar)findViewById(R.id.rb_price);
        et_pingjia = (EditText)findViewById(R.id.et_pingjia);
        btn_submit = (Button)findViewById(R.id.btn_submit);

        LogUtils.e("订单ID"+datas.getId());
        LogUtils.e("订单ID"+orderID);

        //是否评价
        if(TextUtils.isEmpty(datas.getRemark())){ //未评价
            btn_submit.setOnClickListener(this);
            btn_submit.setVisibility(View.VISIBLE);
        }else{                                    //已评价
            if(!TextUtils.isEmpty(datas.getFeedbackAppraise())){
                rb_pingfen.setRating(Float.parseFloat(datas.getFeedbackAppraise()));
            }
            if(!TextUtils.isEmpty(datas.getSourceAppraise())){
                rb_huo.setRating(Float.parseFloat(datas.getSourceAppraise()));
            }
            if(!TextUtils.isEmpty(datas.getServiceAppraise())){
                rb_price.setRating(Float.parseFloat(datas.getServiceAppraise()));
            }
            et_pingjia.setText(datas.getRemark());
            et_pingjia.setFocusable(false);
            rb_pingfen.setFocusable(false);
            rb_huo.setFocusable(false);
            rb_price.setFocusable(false);
            btn_submit.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;
            case R.id.btn_submit:
                subAppraise();
                break;
            default:
                break;
        }
    }


    /**
     * 提交评价
     */
    private void subAppraise(){
        int start1 = (int)rb_pingfen.getRating();
        int start2 = (int)rb_huo.getRating();
        int start3 = (int)rb_price.getRating();
        int totalPj = (start1+start2+start3)/3;
        //String totalResult = String.format("%.1f", totalPj);

        String str1 = start1+",";
        String str2 = start2+",";
        String str3 = start3+",";
        String assessDetail = str1 + str2 + str3 + totalPj;

        String appraiseStr = et_pingjia.getText().toString();
        if(!TextUtils.isEmpty(appraiseStr)){
            if(appraiseStr.length()<=50){
                String url = RequestUrl.getInstance(mContext).getUrl_getTransportAppraise(mContext,assessDetail,orderID,appraiseStr);
                LogUtils.e(url);
                iTransportManager.getAppraise(url,Constants.INTERFACE_transportAppraise);
            }else{
                Tools.showToast(mContext,"评价不能超过50个字!");
            }
        }else{
            Tools.showToast(mContext,"评价不能为空!");
        }

    }


    @Override
    public void updateView(BaseData data) {
        Tools.showToast(mContext,"评价成功!");
        this.finish();
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
}

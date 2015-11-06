package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IEvaluate;
import com.mysteel.banksteel.ao.impl.EvaluateImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IEvaluateView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 买家评价弹框页面
 * Created by zoujian on 15/8/14.
 */
public class BuyAssessActivity extends BaseActivity implements View.OnClickListener, IEvaluateView
{
    RelativeLayout rl_layout_left;
    Button btn_cancle;
    private RatingBar rb_pingfen;
    private RatingBar rb_fankui;
    private RatingBar rb_price;
    private RatingBar rb_ziliang;
    private int totalScore;
    private int fankui;
    private int price;
    private int ziliang;
    private Button btn_submit;
    private EditText edit_view;

    private IEvaluate evaluate;
    private String note;
    private String orderId;
    private TextView tv_zf,tv_fk,tv_jg,tv_zl;
    private String mTag;//买家 和 卖家区分 0:买家，1:卖家
    private TextView tv_pre_fankui,tv_pre_jiage,tv_pre_zhiliang;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyassess);
        Bundle b = getIntent().getExtras();
        orderId = b.getString("orderId");
        mTag =b.getString("TAG");
        initView();
    }

    private void initView()
    {
        rl_layout_left = (RelativeLayout) findViewById(R.id.rl_layout_left);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancle.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rb_pingfen = (RatingBar) findViewById(R.id.rb_pingfen);
        rb_fankui = (RatingBar) findViewById(R.id.rb_fankui);
        rb_price = (RatingBar) findViewById(R.id.rb_price);
        rb_ziliang = (RatingBar) findViewById(R.id.rb_ziliang);
        edit_view = (EditText) findViewById(R.id.edit_view);
        evaluate = new EvaluateImpl(this);
        tv_zf = (TextView) findViewById(R.id.tv_zf);
        tv_fk = (TextView) findViewById(R.id.tv_fk);
        tv_jg = (TextView) findViewById(R.id.tv_jg);
        tv_zl = (TextView) findViewById(R.id.tv_zl);
        tv_pre_fankui = (TextView) findViewById(R.id.tv_pre_fankui);
        tv_pre_jiage = (TextView) findViewById(R.id.tv_pre_jiage);
        tv_pre_zhiliang = (TextView) findViewById(R.id.tv_pre_zhiliang);
        if("1".equals(mTag))//卖家
        {
            tv_pre_fankui.setText("求购真实：");
            tv_pre_jiage.setText("到款及时：");
            tv_pre_zhiliang.setText("合作愉快：");
        }

        rb_pingfen.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser)
            {
                totalScore = (int) rating;
                tv_zf.setText(rating + " 分");
            }
        });
        rb_pingfen.setRating(3);

        rb_fankui.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser)
            {
                fankui = (int) rating;
                tv_fk.setText(rating + " 分");
            }
        });
        rb_fankui.setRating(3);

        rb_price.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser)
            {
                price = (int) rating;
                tv_jg.setText(rating + " 分");
            }
        });
        rb_price.setRating(3);

        rb_ziliang.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser)
            {
                ziliang = (int) rating;
                tv_zl.setText(rating + " 分");
            }
        });
        rb_ziliang.setRating(3);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_cancle:
                finish();
                break;

            case R.id.btn_submit:
                assess();
                break;

        }
    }

    public void assess()
    {
        if (0 == totalScore)
        {
            Toast.makeText(this, "给个总分评价吧，亲！", Toast.LENGTH_LONG).show();
            return;
        }
        if (0 == fankui)
        {
            if("0".equals(mTag))//卖家
            {
                Toast.makeText(this, "给个反馈评价吧，亲！", Toast.LENGTH_LONG).show();
            }else if("1".equals(mTag))
            {
                Toast.makeText(this, "给个求购评价吧，亲！", Toast.LENGTH_LONG).show();
            }


            return;
        }
        if (0 == price)
        {
            if("0".equals(mTag))//卖家
            {
                Toast.makeText(this, "给个价格评价吧，亲！", Toast.LENGTH_LONG).show();
            }else if("1".equals(mTag))
            {
                Toast.makeText(this, "给个价到款及时性的评价吧，亲！", Toast.LENGTH_LONG).show();
            }

            return;
        }
        if (0 == ziliang)
        {
            if("0".equals(mTag))//卖家
            {
                Toast.makeText(this, "给个质量评价吧，亲！", Toast.LENGTH_LONG).show();
            }else if("1".equals(mTag))
            {
                Toast.makeText(this, "给个合作愉快评价吧，亲！", Toast.LENGTH_LONG).show();
            }

            return;
        }

        note = edit_view.getText().toString().trim().replaceAll(" ", "");

        if("1".equals(mTag))//卖家
        {
            String url = RequestUrl.getInstance(this).getUrl_getEvaluateSeller(this,
                    orderId, String.valueOf(totalScore),
                    String.valueOf(ziliang), String.valueOf(price), String.valueOf(fankui), note, "1");
            evaluate.getEvaluate(url, Constants.INTERFACE_evaluateSeller);
        }else if("0".equals(mTag))//买家
        {
            String url = RequestUrl.getInstance(this).getUrl_getEvaluateSeller(this,
                    orderId, String.valueOf(totalScore),
                    String.valueOf(ziliang), String.valueOf(price), String.valueOf(fankui), note, "0");
            evaluate.getEvaluate(url, Constants.INTERFACE_evaluateSeller);
        }

    }

    @Override
    public void updateView(BaseData data)
    {
        if ("true".equals(data.getStatus()))
        {
            if("0".equals(mTag))
            {
                Tools.showToast(this, "评价成功！");
                EventBus.getDefault().post("", "finish");
                EventBus.getDefault().post("", "refreshList");
                finish();
            }else if("1".equals(mTag))
            {
                Tools.showToast(this, "评价成功！");
                EventBus.getDefault().post("", "sellfinish");
                EventBus.getDefault().post("", "sellrefreshList");
                finish();
            }

        }
    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    /**
     * private float totalAppraise = 0F;                 // 单次评价得分
     * private float serviceAppraise = 0F;               // 服务评价分数 (质量满意)
     * private float sourceAppraise = 0F;                // 货源真实性评价分数(价格满意)
     * private float feedbackAppraise = 0F;			  // 反馈及时
     * private String note;                              // 评价描述
     */


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        evaluate.finishRequest();
    }
}

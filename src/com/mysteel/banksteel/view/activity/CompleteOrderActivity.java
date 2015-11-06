package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.OrderDetailData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 已完成订单的详情页面
 * Created by zoujian on 15/7/28.
 */
public class CompleteOrderActivity extends SwipeBackActivity implements View.OnClickListener, IOrderTradeView
{

    //品名,规格,材质,品牌产地,交货地,求购数量,单价,总价,成交时间
    private TextView tv_partname, tv_spec, tv_material, tv_origin, tv_delivery, tv_number, tv_validity, tv_total_price, tv_time;
    //卖家姓名,卖家公司
    private TextView tv_sell_name, tv_sell_company;
    private ImageView img_sell_phone, img_sell_message;//卖家电话，卖家聊天入口
    private ImageView sell_circle;//卖家头像
    private ImageView img_zf_start01, img_zf_start02, img_zf_start03, img_zf_start04, img_zf_start05;//总分
    private ImageView img_fk_start01, img_fk_start02, img_fk_start03, img_fk_start04, img_fk_start05;//反馈
    private ImageView img_jg_start01, img_jg_start02, img_jg_start03, img_jg_start04, img_jg_start05;// 价格
    private ImageView img_zl_start01, img_zl_start02, img_zl_start03, img_zl_start04, img_zl_start05;//质量
    private int num_zf, num_fk, num_jg, num_zl;
    private TextView tv_zongjifen, tv_pijia;
    private String mTag;// 0:买家 1:卖家
    private TextView tv_pre_fankui, tv_pre_jiage, tv_pre_zhiliang, tv_juese;
    private IOrderTrade orderTrade;
    private String orderId = "";
    private OrderDetailData data;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        Bundle b = getIntent().getExtras();
        orderId = b.getString("id");
        mTag = b.getString("tag");
        initViews();
        getData();
    }

    /**
     * 页面进来后拉取数据
     */
    public void getData()
    {
        String url = RequestUrl.getInstance(this).getUrl_getOrderDetail(this,
                orderId);
        orderTrade.getOrderDetail(url, Constants.INTERFACE_orderDetail);
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("已完成订单详情");
        backLayout.setOnClickListener(this);
        imRightImage.setVisibility(View.VISIBLE);
        imRightImage.setBackgroundResource(R.drawable.pinzheng);
        imRightImage.setOnClickListener(this);

        tv_partname = (TextView) findViewById(R.id.tv_partname);//品名
        tv_spec = (TextView) findViewById(R.id.tv_spec);//规格
        tv_material = (TextView) findViewById(R.id.tv_material);//材质
        tv_origin = (TextView) findViewById(R.id.tv_origin);//品牌产地
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);//交货地
        tv_number = (TextView) findViewById(R.id.tv_number);//求购数量
        tv_validity = (TextView) findViewById(R.id.tv_validity);//单价
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);//总价
        tv_time = (TextView) findViewById(R.id.tv_time);//成交时间
        tv_pijia = (TextView) findViewById(R.id.tv_pijia);//评价内容

        tv_sell_name = (TextView) findViewById(R.id.tv_sell_name);//卖家姓名
        tv_sell_company = (TextView) findViewById(R.id.tv_sell_company);//卖家公司

        img_sell_phone = (ImageView) findViewById(R.id.img_yixiang_phone);//卖家电话
        img_sell_phone.setOnClickListener(this);
        img_sell_message = (ImageView) findViewById(R.id.img_yixiang_message);//卖家即时聊天
        img_sell_message.setOnClickListener(this);
        sell_circle = (ImageView) findViewById(R.id.sell_circle);//卖家头像
        sell_circle.setOnClickListener(this);
        tv_zongjifen = (TextView) findViewById(R.id.tv_zongjifen);
        tv_juese = (TextView) findViewById(R.id.tv_juese);

        img_zf_start01 = (ImageView) findViewById(R.id.img_zf_start01);
        img_zf_start02 = (ImageView) findViewById(R.id.img_zf_start02);
        img_zf_start03 = (ImageView) findViewById(R.id.img_zf_start03);
        img_zf_start04 = (ImageView) findViewById(R.id.img_zf_start04);
        img_zf_start05 = (ImageView) findViewById(R.id.img_zf_start05);

        img_fk_start01 = (ImageView) findViewById(R.id.img_fk_start01);
        img_fk_start02 = (ImageView) findViewById(R.id.img_fk_start02);
        img_fk_start03 = (ImageView) findViewById(R.id.img_fk_start03);
        img_fk_start04 = (ImageView) findViewById(R.id.img_fk_start04);
        img_fk_start05 = (ImageView) findViewById(R.id.img_fk_start05);

        img_jg_start01 = (ImageView) findViewById(R.id.img_jg_start01);
        img_jg_start02 = (ImageView) findViewById(R.id.img_jg_start02);
        img_jg_start03 = (ImageView) findViewById(R.id.img_jg_start03);
        img_jg_start04 = (ImageView) findViewById(R.id.img_jg_start04);
        img_jg_start05 = (ImageView) findViewById(R.id.img_jg_start05);

        img_zl_start01 = (ImageView) findViewById(R.id.img_zl_start01);
        img_zl_start02 = (ImageView) findViewById(R.id.img_zl_start02);
        img_zl_start03 = (ImageView) findViewById(R.id.img_zl_start03);
        img_zl_start04 = (ImageView) findViewById(R.id.img_zl_start04);
        img_zl_start05 = (ImageView) findViewById(R.id.img_zl_start05);

        tv_pre_fankui = (TextView) findViewById(R.id.tv_pre_fankui);
        tv_pre_jiage = (TextView) findViewById(R.id.tv_pre_jiage);
        tv_pre_zhiliang = (TextView) findViewById(R.id.tv_pre_zhiliang);
        if ("1".equals(mTag))
        {
            tv_pre_fankui.setText("求购真实");
            tv_pre_jiage.setText("到款及时");
            tv_pre_zhiliang.setText("合作愉快");
            tv_juese.setText("买家信息");
        } else if ("0".equals(mTag))
        {
            tv_pre_fankui.setText("反馈及时");
            tv_pre_jiage.setText("价格合理");
            tv_pre_zhiliang.setText("质量满意");
            tv_juese.setText("卖家信息");
        }
        orderTrade = new OrderTradeImpl(this, this);

    }


    private void initLayout()
    {
        if (data != null && data.getData() != null && data.getData().getOrder() != null
                && data.getData().getAppraise() != null)
        {
            tv_partname.setText(data.getData().getOrder().getBreed());
            tv_spec.setText(data.getData().getOrder().getSpec());
            tv_material.setText(data.getData().getOrder().getMaterial());
            tv_origin.setText(data.getData().getOrder().getBrand());
            tv_delivery.setText(data.getData().getOrder().getCity());
            tv_number.setText(data.getData().getOrder().getQty() + "吨");
            tv_validity.setText(data.getData().getOrder().getPrice());

            if ("0".equals(mTag))//买家进来的 现实卖家信息
            {
                tv_sell_name.setText(data.getData().getOrder().getQuotUserName());
                tv_sell_company.setText(data.getData().getOrder().getQuotMemberName());
            } else if ("1".equals(mTag))//卖家进来的 现实买家信息
            {
                tv_sell_name.setText(data.getData().getOrder().getBuyUserName());
                tv_sell_company.setText(data.getData().getOrder().getBuyMemberName());
            }
            if (!TextUtils.isEmpty(data.getData().getOrder().getQty()) && !TextUtils.isEmpty(data.getData().getOrder().getPrice()))
            {
                tv_total_price.setText(Float.parseFloat(data.getData().getOrder().getQty()) * Float.parseFloat(data.getData().getOrder().getPrice()) + "");
            }

            tv_time.setText(data.getData().getOrder().getOrderTime());

            if (!TextUtils.isEmpty(data.getData().getPartnerUserPhoto()))
            {
                Tools.loadImage(data.getData().getPartnerUserPhoto(), sell_circle);//买家头像
            }

            num_zf = getStarts(data.getData().getAppraise().getTotalAppraise());// 总分
            num_fk = getStarts(data.getData().getAppraise().getFeedbackAppraise());// 反馈（求购真实）
            num_jg = getStarts(data.getData().getAppraise().getSourceAppraise());// 价格（到款及时）
            num_zl = getStarts(data.getData().getAppraise().getServiceAppraise());// 质量（合作愉快）
            tv_zongjifen.setText(data.getData().getAppraise().getTotalAppraise());
            tv_pijia.setText(data.getData().getAppraise().getNote());

            changeStarts(img_zf_start01, img_zf_start02, img_zf_start03, img_zf_start04, img_zf_start05, num_zf);
            changeStarts(img_fk_start01, img_fk_start02, img_fk_start03, img_fk_start04, img_fk_start05, num_fk);
            changeStarts(img_jg_start01, img_jg_start02, img_jg_start03, img_jg_start04, img_jg_start05, num_jg);
            changeStarts(img_zl_start01, img_zl_start02, img_zl_start03, img_zl_start04, img_zl_start05, num_zl);
        }

    }

    private void changeStarts(ImageView start1, ImageView start2, ImageView start3, ImageView start4, ImageView start5, int num)
    {

        switch (num)
        {
            case 0:
                start1.setBackgroundResource(R.drawable.start_up);
                start2.setBackgroundResource(R.drawable.start_up);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 1:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_up);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 2:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 3:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 4:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_down);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 5:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_down);
                start5.setBackgroundResource(R.drawable.start_down);
                break;
        }
    }

    private int getStarts(String stars)
    {
        float fstarts = 0;
        if (!TextUtils.isEmpty(stars))
        {
            fstarts = Float.parseFloat(stars);
            if (fstarts >= 4)
            {
                if (fstarts >= 4.5)
                {
                    fstarts = 5;
                } else
                {
                    fstarts = 4;
                }
            } else if (fstarts >= 3 && fstarts < 4)
            {
                if (fstarts >= 3.5)
                {
                    fstarts = 4;
                } else
                {
                    fstarts = 3;
                }
            } else if (fstarts >= 2 && fstarts < 3)
            {
                if (fstarts >= 2.5)
                {
                    fstarts = 3;
                } else
                {
                    fstarts = 2;
                }
            } else if (fstarts >= 1 && fstarts < 2)
            {
                if (fstarts >= 1.5)
                {
                    fstarts = 2;
                } else
                {
                    fstarts = 1;
                }
            } else
            {
                fstarts = 0;
            }

        }
        return (int) fstarts;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.menu_layout:// 返回
                finish();
                break;

            case R.id.img_yixiang_phone:
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    if (!TextUtils.isEmpty(data.getData().getOrder().getQuotPhone()))
                    {
                        if ("0".equals(mTag))//买家进来的 现实卖家信息
                        {
                            Tools.makeCall(this, data.getData().getOrder().getQuotPhone());
                        } else if ("1".equals(mTag))//卖家进来的 现实买家信息
                        {
                            Tools.makeCall(this, data.getData().getOrder().getBuyPhone());
                        }

                    }
                }
                break;

            case R.id.img_yixiang_message://及时聊天入口
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    if (!TextUtils.isEmpty(data.getData().getOrder().getQuotPhone()))
                    {
                        String url = RequestUrl.getInstance(this).getUrl_Register(this, data.getData().getOrder().getQuotPhone(), "");
                        orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
                    } else
                    {
                        Toast.makeText(this, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.sell_circle:
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    Intent iShow = new Intent(this, ShowUserInfoActivity.class);
                    if (mTag.equals("0"))
                    {
                        iShow.putExtra("friendId", data.getData().getOrder().getQuotUserId());
                        iShow.putExtra("friendPhone", data.getData().getOrder().getQuotPhone());
                    } else
                    {
                        iShow.putExtra("friendId", data.getData().getOrder().getBuyUserId());
                        iShow.putExtra("friendPhone", data.getData().getOrder().getBuyPhone());
                    }
                    startActivity(iShow);
                }
                break;

            case R.id.share_imgbtn://右上角图片点击事件
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    if (TextUtils.isEmpty(data.getData().getOrder().getRemitBillUrl1()))
                    {
                        return;
                    }
                    Intent i1 = new Intent(this, CheckProofActivity.class);
                    i1.putExtra("imgUrl", data.getData().getOrder().getRemitBillUrl1());
                    startActivity(i1);
                }
                break;
        }
    }

    @Override
    public void updateView(BaseData bdata)
    {
        if ("hxuser.register".equals(bdata.getCmd()))
        {
            if ((data != null && data.getData() != null && data.getData().getOrder() != null))
            {
                Intent i = new Intent(this, ChatActivity.class);
                if ("0".equals(mTag))//买家进来的 现实卖家信息
                {
                    i.putExtra("userId", data.getData().getOrder().getQuotPhone());
                    i.putExtra("userName", data.getData().getOrder().getQuotUserName());
                } else if ("1".equals(mTag))//卖家进来的 现实买家信息
                {
                    i.putExtra("userId", data.getData().getOrder().getBuyPhone());
                    i.putExtra("userName", data.getData().getOrder().getBuyUserName());
                }
                startActivity(i);
            }

        } else if ("deal.orderDetail".equals(bdata.getCmd()))
        {
            data = (OrderDetailData) bdata;
            if (data != null && data.getData() != null && data.getData().getOrder() != null && data.getData().getAppraise() != null)
            {
                initLayout();
            }
        }

    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        orderTrade.finishRequest();
    }

}

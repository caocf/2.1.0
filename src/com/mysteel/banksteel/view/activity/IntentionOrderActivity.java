package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.OrderDetailData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

/**
 * 意向单详情页面
 * Created by zoujian on 15/7/28.
 */
public class IntentionOrderActivity extends SwipeBackActivity implements View.OnClickListener, IOrderTradeView
{

    //    private Datas data;
    private Button btn_speech_findgoods;//底部按钮
    //品名,规格,材质,品牌产地,交货地,求购数量,单价,总价,成交时间
    private TextView tv_partname, tv_spec, tv_material, tv_origin, tv_delivery, tv_number, tv_validity, tv_total_price, tv_time;
    //卖家姓名,卖家公司，买家姓名，买家公司，买家电话
    private TextView tv_sell_name, tv_sell_company, tv_buy_name, tv_buy_company, tv_address/*tv_buy_phone*/;
    private ImageView img_yixiang_phone, img_yixiang_message;//卖家电话，卖家聊天入口
    private ImageView sell_circle, buy_circle;
    private TextView tv_close;
    private IOrderTrade orderTrade;
    private String orderId = "";
    private OrderDetailData data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intention);
        Bundle b = getIntent().getExtras();
        orderId = b.getString("id");
        initViews();
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("买家意向单详情");
        backLayout.setOnClickListener(this);
        imRightImage.setBackgroundResource(R.drawable.pinzheng);
        imRightImage.setOnClickListener(this);

        btn_speech_findgoods = (Button) findViewById(R.id.btn_speech_findgoods);
        btn_speech_findgoods.setOnClickListener(this);
        tv_partname = (TextView) findViewById(R.id.tv_partname);//品名
        tv_spec = (TextView) findViewById(R.id.tv_spec);//规格
        tv_material = (TextView) findViewById(R.id.tv_material);//材质
        tv_origin = (TextView) findViewById(R.id.tv_origin);//品牌产地
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);//交货地
        tv_number = (TextView) findViewById(R.id.tv_number);//求购数量
        tv_validity = (TextView) findViewById(R.id.tv_validity);//单价
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);//总价
        tv_time = (TextView) findViewById(R.id.tv_time);//成交时间

        tv_sell_name = (TextView) findViewById(R.id.tv_sell_name);//卖家姓名
        tv_sell_company = (TextView) findViewById(R.id.tv_sell_company);//卖家公司
        tv_buy_company = (TextView) findViewById(R.id.tv_buy_company);//买家公司
//        tv_buy_phone = (TextView) findViewById(R.id.tv_buy_phone);//买家电话
        tv_address = (TextView) findViewById(R.id.tv_address);//买家本人地址
        tv_buy_name = (TextView) findViewById(R.id.tv_buy_name);//买家姓名

        img_yixiang_phone = (ImageView) findViewById(R.id.img_yixiang_phone);//卖家电话
        img_yixiang_phone.setOnClickListener(this);
        img_yixiang_message = (ImageView) findViewById(R.id.img_yixiang_message);//卖家即时聊天
        img_yixiang_message.setOnClickListener(this);
        sell_circle = (ImageView) findViewById(R.id.sell_circle);//卖家头像
        sell_circle.setOnClickListener(this);
        buy_circle = (ImageView) findViewById(R.id.buy_circle);//买家头像
        tv_close = (TextView) findViewById(R.id.tv_close);
        orderTrade = new OrderTradeImpl(this, this);
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

    private void changeView()
    {
        if (data != null && data.getData() != null && data.getData().getOrder() != null)
        {
            if ("0".equals(data.getData().getOrder().getStatus()))
            {
                btn_speech_findgoods.setText("请上传凭证");
            } else if ("9".equals(data.getData().getOrder().getStatus()))
            {
                btn_speech_findgoods.setText("请重新上传凭证");
                imRightImage.setVisibility(View.VISIBLE);
            } else if ("1".equals(data.getData().getOrder().getStatus()))
            {
                imRightImage.setVisibility(View.VISIBLE);
                btn_speech_findgoods.setText("凭证审核中");
                btn_speech_findgoods.setBackgroundResource(R.drawable.findfoodup);
            } else if ("99".equals(data.getData().getOrder().getStatus()))
            {
//                btn_speech_findgoods.setText("意向单已关闭");
//                btn_speech_findgoods.setBackgroundResource(R.drawable.findfoodup);
                btn_speech_findgoods.setVisibility(View.GONE);
                tv_close.setVisibility(View.VISIBLE);
            } else if ("2".equals(data.getData().getOrder().getStatus()))
            {
                if ("0".equals(data.getData().getOrder().getAppraiseStatus()) || "2".equals(data.getData().getOrder().getAppraiseStatus()))
                {
                    imRightImage.setVisibility(View.VISIBLE);
                    btn_speech_findgoods.setText("发表评价");
                } else
                {
                    imRightImage.setVisibility(View.VISIBLE);
                    btn_speech_findgoods.setText("评价已完成");
                    btn_speech_findgoods.setBackgroundResource(R.drawable.findfoodup);
                }
            }

            tv_partname.setText(data.getData().getOrder().getBreed());
            tv_spec.setText(data.getData().getOrder().getSpec());
            tv_material.setText(data.getData().getOrder().getMaterial());
            tv_origin.setText(data.getData().getOrder().getBrand());
            tv_delivery.setText(data.getData().getOrder().getCity());
            tv_number.setText(data.getData().getOrder().getQty() + "吨");
            tv_validity.setText(data.getData().getOrder().getPrice());
            String province = SharePreferenceUtil.getString(this,
                    Constants.USER_PROVINCE);
            String city = SharePreferenceUtil
                    .getString(this, Constants.USER_CITY);
            String county = SharePreferenceUtil.getString(this,
                    Constants.USER_COUNTY);

            if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city) && TextUtils.isEmpty(county))
            {
                tv_address.setText("未填写");
            } else
            {
                tv_address.setText(province + " " + city + " " + county);
            }

            if (!TextUtils.isEmpty(data.getData().getOrder().getQty()) &&
                    !TextUtils.isEmpty(data.getData().getOrder().getPrice()))
            {
                tv_total_price.setText(Float.parseFloat(data.getData().getOrder().getQty()) *
                        Float.parseFloat(data.getData().getOrder().getPrice()) + "");
            }

            tv_sell_name.setText(data.getData().getOrder().getQuotUserName());
            tv_sell_company.setText(data.getData().getOrder().getQuotMemberName());
            String userMemberName = SharePreferenceUtil.getString(this, Constants.USER_MEMBER_NAME);
            tv_buy_company.setText(userMemberName);
//            tv_buy_phone.setText(data.getData().getOrder().getBuyPhone());
            tv_buy_name.setText(data.getData().getOrder().getBuyUserName());

            tv_time.setText(data.getData().getOrder().getOrderTime());

            if (!TextUtils.isEmpty(data.getData().getPartnerUserPhoto()))
            {
                Tools.loadImage(data.getData().getPartnerUserPhoto(), sell_circle);//买家头像
            }

            String photo = SharePreferenceUtil.getString(this,
                    Constants.USER_PHTHO);
            if(!TextUtils.isEmpty(photo))
            {
                Tools.loadImage(photo, buy_circle);//买家头像
            }

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
            case R.id.img_yixiang_phone:
                if ((data != null && data.getData() != null && data.getData().getOrder() != null) && (!TextUtils.isEmpty(data.getData().getOrder().getQuotPhone())))
                {
                    Tools.makeCall(this, data.getData().getOrder().getQuotPhone());
                }
                break;
            case R.id.sell_circle:
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    Intent iShow = new Intent(this, ShowUserInfoActivity.class);
                    iShow.putExtra("friendId", data.getData().getOrder().getQuotUserId());
                    iShow.putExtra("friendPhone", data.getData().getOrder().getQuotPhone());
                    startActivity(iShow);
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
            case R.id.btn_speech_findgoods:

                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    if (!TextUtils.isEmpty(data.getData().getOrder().getId()) && !TextUtils.isEmpty(data.getData().getOrder().getStatus()))
                    {
                        if ("0".equals(data.getData().getOrder().getStatus()) || "9".equals(data.getData().getOrder().getStatus()))
                        {//上传凭证
                            Intent i = new Intent(this, UploadCertificateActivity.class);
                            i.putExtra("orderId", data.getData().getOrder().getId());
                            i.putExtra("steel", data.getData().getOrder().getBreed() + " " + data.getData().getOrder().getMaterial() + " "
                                    + data.getData().getOrder().getSpec());
                            startActivity(i);
                        } else if ("2".equals(data.getData().getOrder().getStatus()) && !TextUtils.isEmpty(data.getData().getOrder().getAppraiseStatus()) &&
                                ("0".equals(data.getData().getOrder().getAppraiseStatus()) || "2".equals(data.getData().getOrder().getAppraiseStatus())))
                        {
                            Intent i = new Intent(this, BuyAssessActivity.class);
                            i.putExtra("orderId", data.getData().getOrder().getId());
                            i.putExtra("TAG", "0");
                            startActivity(i);
                        }
                    }
                }

                break;

            case R.id.share_imgbtn://右上角图片点击事件
                if ((data != null && data.getData() != null && data.getData().getOrder() != null))
                {
                    if (!"0".equals(data.getData().getOrder().getStatus()) || !"99".equals(data.getData().getOrder().getStatus()))
                    {
                        if (TextUtils.isEmpty(data.getData().getOrder().getRemitBillUrl1()))
                        {
                            return;
                        }
                        Intent i = new Intent(this, CheckProofActivity.class);
                        i.putExtra("imgUrl", data.getData().getOrder().getRemitBillUrl1());
                        startActivity(i);
                    }
                }
                break;
        }
    }

    @Subscriber(tag = "finish")
    public void finishActivity(String str)
    {
        finish();
    }

    @Override
    public void updateView(BaseData bdata)
    {
        if ("hxuser.register".equals(bdata.getCmd()))
        {
            if ((data != null && data.getData() != null && data.getData().getOrder() != null))
            {
                Intent i = new Intent(this, ChatActivity.class);
                i.putExtra("userId", data.getData().getOrder().getQuotPhone());
                i.putExtra("userName", data.getData().getOrder().getQuotUserName());
                startActivity(i);
            }
        } else if ("deal.orderDetail".equals(bdata.getCmd()))
        {
            data = (OrderDetailData) bdata;
            if (data != null && data.getData() != null && data.getData().getOrder() != null && data.getData().getAppraise() != null)
            {
                changeView();
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

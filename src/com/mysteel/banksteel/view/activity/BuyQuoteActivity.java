package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.StanQuotDetailsData.DataEntity.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.sweetalert.SweetAlertDialog;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 买家求购详情中 报价信息的详情页面
 * Created by zoujian on 15/8/7.
 */
public class BuyQuoteActivity extends SwipeBackActivity implements View.OnClickListener, IOrderTradeView
{

    private Datas data;
    private Button btn_tijiao;//底部按钮
    private String partname, spec, material, origin, delivery, number, validity, time, orderId, resource;
    //品名,规格,材质,品牌产地,交货地,求购数量,单价,查看同类资源,成交时间
    private TextView tv_partname, tv_spec, tv_material, tv_origin, tv_delivery, tv_number, tv_validity, tv_check, tv_time;
    //卖家姓名,卖家公司，买家姓名，买家公司，买家电话
    private TextView tv_sell_name, tv_sell_company;
    private ImageView img_yixiang_phone, img_yixiang_message;//卖家电话，卖家聊天入口
    private ImageView sell_circle;
    private EditText et_speech_phonenumber;
    private EditText et_additional_text;
    private IOrderTrade orderTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyquote);
        Bundle b = getIntent().getExtras();
        data = (Datas) b.getSerializable("DATAS");
        partname = b.getString("tv_partname");
        spec = b.getString("tv_spec");
        material = b.getString("tv_material");
        origin = b.getString("tv_origin");
        delivery = b.getString("tv_delivery");
        number = b.getString("tv_number");
        validity = b.getString("tv_validity");
        time = b.getString("tv_time");
        orderId = b.getString("orderId");
        resource = b.getString("resource");
        initViews();
        changeView();
    }

    private void changeView()
    {

        tv_partname.setText(partname);
        tv_spec.setText(spec);
        tv_material.setText(material);
        tv_origin.setText(origin);
        tv_delivery.setText(delivery);
//        tv_number.setText(number  + "吨");
        tv_number.setText(number);
        tv_validity.setText(validity);
        tv_time.setText(time);
        tv_sell_name.setText(data.getQuotContact());
        tv_sell_company.setText(data.getQuotMemberName());
        et_speech_phonenumber.setHint(data.getPrice());
        if (!TextUtils.isEmpty(data.getUserPhoto()))
        {
            Tools.loadImage(data.getUserPhoto(), sell_circle);//卖家头像
        }
        et_additional_text.setFocusable(false);
        if(!TextUtils.isEmpty(data.getNote())){
            et_additional_text.setText(data.getNote());
        }
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("报价详情");
        backLayout.setOnClickListener(this);

        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        tv_partname = (TextView) findViewById(R.id.tv_partname);//品名
        tv_spec = (TextView) findViewById(R.id.tv_spec);//规格
        tv_material = (TextView) findViewById(R.id.tv_material);//材质
        tv_origin = (TextView) findViewById(R.id.tv_origin);//品牌产地
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);//交货地
        tv_number = (TextView) findViewById(R.id.tv_number);//求购数量
        tv_validity = (TextView) findViewById(R.id.tv_validity);//单价
        tv_check = (TextView) findViewById(R.id.tv_check);//查看同类资源
        tv_check.setVisibility(View.GONE);
        tv_time = (TextView) findViewById(R.id.tv_time);//成交时间

        tv_sell_name = (TextView) findViewById(R.id.tv_sell_name);//卖家姓名
        tv_sell_company = (TextView) findViewById(R.id.tv_sell_company);//卖家公司

        img_yixiang_phone = (ImageView) findViewById(R.id.img_yixiang_phone);//卖家电话
        img_yixiang_phone.setOnClickListener(this);
        img_yixiang_message = (ImageView) findViewById(R.id.img_yixiang_message);//卖家即时聊天
        img_yixiang_message.setOnClickListener(this);
        sell_circle = (ImageView) findViewById(R.id.sell_circle);//卖家头像
        sell_circle.setOnClickListener(this);

        et_speech_phonenumber = (EditText) findViewById(R.id.et_speech_phonenumber);
        et_additional_text = (EditText) findViewById(R.id.et_additional_text);

        orderTrade = new OrderTradeImpl(this, this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回
                finish();
                break;

            case R.id.btn_tijiao://达成意向

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("达成意向")
                        .setContentText("确定达成意向吗?")
                        .setCancelText("取 消")
                        .setConfirmText("确 定")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                String price = et_speech_phonenumber.getText().toString();
                                if (TextUtils.isEmpty(et_speech_phonenumber.getText().toString()))
                                {
                                    price = data.getPrice();
                                } else
                                {
                                    price = et_speech_phonenumber.getText().toString();
                                }
                                String url1 = RequestUrl.getInstance(BuyQuoteActivity.this).getUrl_createResourceOrder(
                                        BuyQuoteActivity.this, "0", orderId, data.getId(), resource, data.getQty(), price);
                                orderTrade.getCreateOrder(url1, Constants.INTERFACE_pageOrder);
                            }
                        })
                        .show();

                break;
            case R.id.img_yixiang_phone://卖家联系电话
                if (!TextUtils.isEmpty(data.getQuotPhone()))
                {
                    Tools.makeCall(this, data.getQuotPhone());
                }
                break;

            case R.id.img_yixiang_message://及时聊天入口

                if (!TextUtils.isEmpty(data.getQuotPhone()))
                {
                    String url = RequestUrl.getInstance(this).getUrl_Register(this, data.getQuotPhone(), "");
                    orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
                } else
                {
                    Toast.makeText(this, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.sell_circle:
                Intent iShow = new Intent(this, ShowUserInfoActivity.class);
                iShow.putExtra("friendId", data.getQuotUserId());
                iShow.putExtra("friendPhone", data.getQuotPhone());
                startActivity(iShow);
                break;
        }
    }

    @Override
    public void updateView(BaseData bdata)
    {
        if ("deal.createOrder".equals(bdata.getCmd()))
        {
            Toast.makeText(this, "达成意向成功!", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post("", "BuyHasQuoteActivity_finish");
            EventBus.getDefault().post("", "refreshList");
            finish();
        } else if ("hxuser.register".equals(bdata.getCmd()))
        {
            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("userId", data.getQuotPhone());
            i.putExtra("userName", data.getQuotContact());
            startActivity(i);
        }
    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

}

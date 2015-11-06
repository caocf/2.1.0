package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.ao.impl.TransportManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.TransportDetailsData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;
import com.mysteel.banksteel.entity.TransportDetailsData.DataEntity;

import java.util.ArrayList;

/**
 * 物流跟踪页面
 * Created by zoujian on 15/8/18.
 */
public class LogisticsTrackActivity extends SwipeBackActivity implements View.OnClickListener, ITransportManagerView
{
    private TransportManagerImpl transportManagerImpl;
    private String id;
    private TransportDetailsData TData;
    private ArrayList<DataEntity> AData;
    private TextView tv_car, tv_yundan;
    private TextView tv_one_zt, tv_one_time, tv_two_zt, tv_two_time, tv_three_zt, tv_three_time, tv_four_zt, tv_four_time;
    private LinearLayout ll_logistics_layoutone, ll_logistics_layouttwo, ll_logistics_layoutthree, ll_logistics_layoutfour;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_track);
        Bundle b = getIntent().getExtras();
        id = b.getString("ID");
        initViews();
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("物流跟踪");
        backLayout.setOnClickListener(this);
        transportManagerImpl = new TransportManagerImpl(this);
        tv_car = (TextView) findViewById(R.id.tv_car);
        tv_yundan = (TextView) findViewById(R.id.tv_yundan);
        tv_one_zt = (TextView) findViewById(R.id.tv_one_zt);
        tv_one_time = (TextView) findViewById(R.id.tv_one_time);
        tv_two_zt = (TextView) findViewById(R.id.tv_two_zt);
        tv_two_time = (TextView) findViewById(R.id.tv_two_time);
        tv_three_zt = (TextView) findViewById(R.id.tv_three_zt);
        tv_three_time = (TextView) findViewById(R.id.tv_three_time);
        tv_four_zt = (TextView) findViewById(R.id.tv_four_zt);
        tv_four_time = (TextView) findViewById(R.id.tv_four_time);
        ll_logistics_layoutone = (LinearLayout) findViewById(R.id.ll_logistics_layoutone);
        ll_logistics_layouttwo = (LinearLayout) findViewById(R.id.ll_logistics_layouttwo);
        ll_logistics_layoutthree = (LinearLayout) findViewById(R.id.ll_logistics_layoutthree);
        ll_logistics_layoutfour = (LinearLayout) findViewById(R.id.ll_logistics_layoutfour);
        getData();

    }

    private void getData()
    {
        String url = RequestUrl.getInstance(this).getUrl_transportDetails(this, id);
//        String url = "http://192.168.100.115:8180/assistant_spi?cmd=transport.transportDetails" +
//                "&orderId=52&ostype=1" + Tools.getAndroidMsg(this) +
//                "&timestamp=1440380061381&userId=46866";
        transportManagerImpl.getTransportDetails(url, Constants.INTERFACE_transportDetails);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        TData = (TransportDetailsData) data;
        if (TData == null || TData.getData() == null)
        {
//            Toast.makeText(this, "size = 0", Toast.LENGTH_LONG).show();
            return;
        } else
        {
//            Toast.makeText(this, "size = " + TData.getData().size(), Toast.LENGTH_LONG).show();
            AData = TData.getData();
            ChangeView();
        }
    }

    /**
     * 10 到达装货点 20 提货完成 30 运输在途 40 到达卸货点
     */
    private void ChangeView()
    {
        if (TData.getData().size() == 0)
        {
            return;
        }
        int count = TData.getData().size();
        tv_car.setText(TData.getData().get(0).getCarId());
        tv_yundan.setText("运单号：" + TData.getData().get(0).getSteel56OrderId());
        switch (count)
        {
            case 1:
                ll_logistics_layoutone.setVisibility(View.VISIBLE);
                tv_one_zt.setText("到达装货点");
                tv_one_time.setText(TData.getData().get(0).getStatusTime());
                break;
            case 2:
                ll_logistics_layoutone.setVisibility(View.VISIBLE);
                ll_logistics_layouttwo.setVisibility(View.VISIBLE);
                for (int i = 0; i < 2; i++)
                {
                    if ("20".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_one_zt.setText("提货完成");
                        tv_one_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("10".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_two_zt.setText("到达装货点");
                        tv_two_time.setText(TData.getData().get(i).getStatusTime());
                    }
                }
                break;
            case 3:
                ll_logistics_layoutone.setVisibility(View.VISIBLE);
                ll_logistics_layouttwo.setVisibility(View.VISIBLE);
                ll_logistics_layoutthree.setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i++)
                {
                    if ("30".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_one_zt.setText("运输在途");
                        tv_one_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("20".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_two_zt.setText("提货完成");
                        tv_two_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("10".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_three_zt.setText("到达装货点");
                        tv_three_time.setText(TData.getData().get(i).getStatusTime());
                    }
                }
                break;
            case 4:
                ll_logistics_layoutone.setVisibility(View.VISIBLE);
                ll_logistics_layouttwo.setVisibility(View.VISIBLE);
                ll_logistics_layoutthree.setVisibility(View.VISIBLE);
                ll_logistics_layoutfour.setVisibility(View.VISIBLE);
                for (int i = 0; i < 4; i++)
                {
                    if ("40".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_one_zt.setText("到达卸货点");
                        tv_one_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("30".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_two_zt.setText("运输在途");
                        tv_two_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("20".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_three_zt.setText("提货完成");
                        tv_three_time.setText(TData.getData().get(i).getStatusTime());
                    } else if ("10".equals(TData.getData().get(i).getStatus()))
                    {
                        tv_four_zt.setText("到达装货点");
                        tv_four_time.setText(TData.getData().get(i).getStatusTime());
                    }
                }
                break;
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
        if (transportManagerImpl != null)
        {
            transportManagerImpl.finishRequest();
        }
    }
}

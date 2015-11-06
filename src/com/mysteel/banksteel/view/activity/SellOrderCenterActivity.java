package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.MyQuotData;
import com.mysteel.banksteel.entity.PageOrderData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SellBaoJaiAdapter;
import com.mysteel.banksteel.view.adapters.SellCompleteOrderAdapter;
import com.mysteel.banksteel.view.adapters.SellIntentionAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;
import com.mysteel.banksteel.entity.MyQuotData.DataEntity.DatasEntity;
import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 卖家订单中心页面 (没有与买家公用代码，考虑后面这两块会有改动，so，分开写了)
 * Created by zoujian on 15/8/7.
 */
public class SellOrderCenterActivity extends SwipeBackActivity implements View.OnClickListener,
        XListView.IXListViewListener, IBuyView, IOrderTradeView
{

    private ImageView back;
    private ImageView steel;
    private TextView title;
    private RelativeLayout layout_back;// 返回布局，触点更大

    private XListView listview;
    private RelativeLayout rl_qiugou, rl_yixiang, rl_wancheng;
    private TextView tv_qiugou, tv_yixiang, tv_wangcheng;
    private int tag = 0;//0:报价单 1:意向单 2:完成订单
    //    private WantBuyAdapter adapter;
//    private WantBuyIntentionAdapter ITAdapter;
//    private CompleteOrderAdapter comAdapter;
    private IBuyCenter buyCenter;
    private IOrderTrade orderTrade;

    private String url = "";
    private String page = "1";
    private String size = "10";

    //报价单数据
    private MyQuotData MQdata;
    private ArrayList<DatasEntity> baojiaData = new ArrayList<DatasEntity>();
    private SellBaoJaiAdapter BJAdapter;

    //我的意向单数据
    private PageOrderData poData;
    private ArrayList<Datas> intentionData = new ArrayList<Datas>();
    private SellIntentionAdapter ITAdapter;

    //已完成订单
    private PageOrderData clData;
    private ArrayList<Datas> completeData = new ArrayList<Datas>();
    private SellCompleteOrderAdapter comAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_order_centre);
        initView();
    }

    private void initView()
    {
        back = (ImageView) findViewById(R.id.menu_imgbtn);
        back.setBackgroundResource(R.drawable.back);
        layout_back = (RelativeLayout) findViewById(R.id.menu_layout);
        layout_back.setOnClickListener(this);
        steel = (ImageView) findViewById(R.id.share_imgbtn);
        steel.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("卖家订单中心");

        tv_qiugou = (TextView) findViewById(R.id.tv_qiugou);
        tv_qiugou.setText("报价单");
        tv_yixiang = (TextView) findViewById(R.id.tv_yixiang);
        tv_wangcheng = (TextView) findViewById(R.id.tv_wangcheng);

        rl_qiugou = (RelativeLayout) findViewById(R.id.rl_qiugou);
        rl_yixiang = (RelativeLayout) findViewById(R.id.rl_yixiang);
        rl_wancheng = (RelativeLayout) findViewById(R.id.rl_wancheng);
        rl_qiugou.setOnClickListener(this);
        rl_yixiang.setOnClickListener(this);
        rl_wancheng.setOnClickListener(this);
        buyCenter = new BuyImpl(this);
        orderTrade = new OrderTradeImpl(this, this);

        BJAdapter = new SellBaoJaiAdapter(getLayoutInflater(), baojiaData);
        ITAdapter = new SellIntentionAdapter(getLayoutInflater(), intentionData);
        comAdapter = new SellCompleteOrderAdapter(getLayoutInflater(), completeData);
//        adapter = new WantBuyAdapter(getLayoutInflater(), dataEntity);
//        ITAdapter = new WantBuyIntentionAdapter(this, intentionData);
//        comAdapter = new CompleteOrderAdapter(this,completeData);
        listview = (XListView) findViewById(R.id.lv_list);
        listview.setXListViewListener(this);
        listview.setAdapter(BJAdapter);
        if (null != listview)
        {
            listview.hideFoot();
        }
        getmyQuotData();
    }

    /**
     * 获取求购单协议
     */
    public void getmyQuotData()
    {
        page = "1";
        url = RequestUrl.getInstance(this).getUrl_getmyQuot(this, page,
                size);
        LogUtils.e(url);
        buyCenter.getmyQuot(url, Constants.INTERFACE_historyStanBuy);
    }

    /**
     * 获取意向单的协议 最后一个参数用来本地区分:意向单（1）和完成订单（2），在url中做路判断处理
     */
    private void getIntentionData()
    {
        page = "1";
        String url = RequestUrl.getInstance(this).getUrl_getSellPageOrder(
                this, page, size, "1");
        orderTrade.getPageOrder(url, Constants.INTERFACE_pageOrder);
    }

    /**
     * 获取已完成定单的协议 最后一个参数用来本地区分:意向单（1）和完成订单（2），在url中做路判断处理
     */
    private void getCompleteOrder()
    {
        page = "1";
        String url = RequestUrl.getInstance(this).getUrl_getSellPageOrder(
                this, page, size, "2");
        orderTrade.getPageOrder(url, Constants.INTERFACE_pageOrder);
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

            case R.id.rl_qiugou://求购单
                if (tag != 0)
                {
                    changeView(0);
                    getmyQuotData();
                    listview.setAdapter(BJAdapter);
                }

                break;

            case R.id.rl_yixiang://意向单
                if (tag != 1)
                {
                    changeView(1);
                    getIntentionData();
                    listview.setAdapter(ITAdapter);
                }

                break;

            case R.id.rl_wancheng://完成订单
                if (tag != 2)
                {
                    changeView(2);
                    getCompleteOrder();
                    listview.setAdapter(comAdapter);
                }
                break;
        }
    }

    /**
     * 修改头部标题状态
     *
     * @param num
     */
    private void changeView(int num)
    {
        tag = num;
        switch (num)
        {
            case 0:
                tv_qiugou.setBackgroundResource(R.drawable.current_down);
                tv_qiugou.setTextColor(getResources().getColor(R.color.main_imred));
                tv_yixiang.setBackgroundResource(R.drawable.default_up);
                tv_yixiang.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_wangcheng.setBackgroundResource(R.drawable.default_up);
                tv_wangcheng.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;

            case 1:
                tv_qiugou.setBackgroundResource(R.drawable.default_up);
                tv_qiugou.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_yixiang.setBackgroundResource(R.drawable.current_down);
                tv_yixiang.setTextColor(getResources().getColor(R.color.main_imred));
                tv_wangcheng.setBackgroundResource(R.drawable.default_up);
                tv_wangcheng.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;

            case 2:
                tv_qiugou.setBackgroundResource(R.drawable.default_up);
                tv_qiugou.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_yixiang.setBackgroundResource(R.drawable.default_up);
                tv_yixiang.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_wangcheng.setBackgroundResource(R.drawable.current_down);
                tv_wangcheng.setTextColor(getResources().getColor(R.color.main_imred));
                break;
        }
    }

    @Override
    public void onRefresh()
    {
        switch (tag)
        {
            case 0:
                getmyQuotData();
                break;
            case 1:
                getIntentionData();
                break;
            case 2:
                getCompleteOrder();
                break;
        }
    }

    @Override
    public void onLoadMore()
    {
        if (!Tools.isNetworkConnected(this))
        {
            Tools.showToast(this, getResources().getString(R.string.Network_not_Connected));
            return;
        }

        switch (tag)
        {
            case 0:
                if (MQdata.getData() == null
                        || MQdata.getData().getDatas() == null)
                {
                    return;
                }

                if (TextUtils.isEmpty(MQdata.getData().getPagenum())
                        || TextUtils.isEmpty(MQdata.getData().getPage()))
                {
                    return;
                }
                if (Integer.parseInt(page) >= Integer.parseInt(MQdata.getData().getPagenum()))
                {
                    onLoad();
                    Tools.showToast(this, "已经到最后一页!");
                    return;
                }

                page = Integer.parseInt(page) + 1 + "";
                url = RequestUrl.getInstance(this).getUrl_getHistoryStanBuy(this, page,
                        size);
                buyCenter.getHistoryStanBuy(url, Constants.INTERFACE_historyStanBuy);
                break;
            case 1:
                if (poData == null || poData.getData() == null || poData.getData().getDatas() == null)
                {
                    return;
                }

                if (TextUtils.isEmpty(poData.getData().getPagenum())
                        || TextUtils.isEmpty(poData.getData().getPage()))
                {
                    return;
                }
                if (Integer.parseInt(page) >= Integer.parseInt(poData.getData().getPagenum()))
                {
                    onLoad();
                    Tools.showToast(this, "已经到最后一页!");
                    return;
                }
                page = Integer.parseInt(page) + 1 + "0";
                String url1 = RequestUrl.getInstance(this).getUrl_getSellPageOrder(
                        this, page, size, "1");
                orderTrade.getPageOrder(url1, Constants.INTERFACE_pageOrder);
                break;

            case 2:
                if (clData == null || clData.getData() == null || clData.getData().getDatas() == null)
                {
                    return;
                }

                if (TextUtils.isEmpty(clData.getData().getPagenum())
                        || TextUtils.isEmpty(clData.getData().getPage()))
                {
                    return;
                }
                if (Integer.parseInt(page) >= Integer.parseInt(clData.getData().getPagenum()))
                {
                    onLoad();
                    Tools.showToast(this, "已经到最后一页!");
                    return;
                }
                page = Integer.parseInt(page) + 1 + "";
                String url2 = RequestUrl.getInstance(this).getUrl_getSellPageOrder(
                        this, page, size, "2");
                orderTrade.getPageOrder(url2, Constants.INTERFACE_pageOrder);
                break;
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("quot.myQuot".equals(data.getCmd()))
        {
            MQdata = (MyQuotData) data;
            MyQuotData Mydata = (MyQuotData) data;
            if (Mydata == null || Mydata.getData() == null || Mydata.getData().getDatas() == null)
            {
                BJAdapter.reSetList(null);
                onLoad();
                return;
            }
            ArrayList<DatasEntity> bjdata = MQdata.getData().getDatas();

            if ("1".equals(page))
            {
                baojiaData = bjdata;
            } else
            {
                baojiaData.addAll(bjdata);
            }
            BJAdapter.reSetList(baojiaData);

        } else if ("deal.pageOrder".equals(data.getCmd()))
        {
            poData = (PageOrderData) data;
            PageOrderData pdata = (PageOrderData) data;
            if (pdata == null || pdata.getData() == null || pdata.getData().getDatas() == null)
            {
                ITAdapter.reSetList(null);
                onLoad();
                return;
            }
            ArrayList<Datas> inData = poData.getData().getDatas();
            if ("1".equals(page))
            {
                intentionData = inData;
            } else
            {
                intentionData.addAll(inData);
            }
            ITAdapter.reSetList(intentionData);

        } else if ("deal.pageAppraiseOrder".equals(data.getCmd()))
        {
            clData = (PageOrderData) data;
            PageOrderData podata = (PageOrderData) data;
            if (podata == null || podata.getData() == null || podata.getData().getDatas() == null)
            {
                comAdapter.reSetList(null);
                onLoad();
                return;
            }
            ArrayList<Datas> cldata = clData.getData().getDatas();
            if ("1".equals(page))
            {
                completeData = cldata;
            } else
            {
                completeData.addAll(cldata);
            }
            comAdapter.reSetList(completeData);
        }

        onLoad();

    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        buyCenter.finishRequest();
        orderTrade.finishRequest();
    }

    /**
     * 更新listview头部刷新时间
     */
    public void onLoad()
    {
        listview.stopRefresh();
        listview.stopLoadMore();
        listview.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINESE).format(new Date()));
    }

    @Subscriber(tag = "sellrefreshList")
    public void RefreshList(String str)
    {
        onRefresh();
    }
}

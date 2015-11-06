package com.mysteel.banksteel.view.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.HistoryStanBuyData;
import com.mysteel.banksteel.entity.HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.entity.PageOrderData;
import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.CompleteOrderAdapter;
import com.mysteel.banksteel.view.adapters.WantBuyAdapter;
import com.mysteel.banksteel.view.adapters.WantBuyIntentionAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.SwipeListView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simple.eventbus.Subscriber;

/**
 * 订单中心页面
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-5-4 下午7:01:43
 */
public class OrderCentreActivity extends SwipeBackActivity implements
        OnClickListener, SwipeListView.IXListViewListener, IBuyView, IOrderTradeView
{

    private ImageView back;
    private ImageView steel;
    private TextView title;
    private RelativeLayout layout_back;// 返回布局，触点更大

    private SwipeListView listview;
    private RelativeLayout rl_qiugou, rl_yixiang, rl_wancheng;
    private TextView tv_qiugou, tv_yixiang, tv_wangcheng;
    private int tag = 0;//0:求购单 1:意向单 2:完成订单
    private WantBuyAdapter adapter;
    private WantBuyIntentionAdapter ITAdapter;
    private CompleteOrderAdapter comAdapter;
    private IBuyCenter buyCenter;

    private String url = "";
    private String page = "1";
    private String size = "10";

    //我的求购单数据
    private HistoryStanBuyData hsData;
    private ArrayList<DatasEntity> dataEntity = new ArrayList<DatasEntity>();

    //我的意向单数据
    private PageOrderData poData;
    private ArrayList<Datas> intentionData = new ArrayList<Datas>();

    //已完成订单
    private PageOrderData clData;
    private ArrayList<Datas> completeData = new ArrayList<Datas>();
    private IOrderTrade orderTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_centre);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView()
    {
        back = (ImageView) findViewById(R.id.menu_imgbtn);
        back.setBackgroundResource(R.drawable.back);
        layout_back = (RelativeLayout) findViewById(R.id.menu_layout);
        layout_back.setOnClickListener(this);
        steel = (ImageView) findViewById(R.id.share_imgbtn);
        steel.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("买家订单中心");

        rl_qiugou = (RelativeLayout) findViewById(R.id.rl_qiugou);
        rl_yixiang = (RelativeLayout) findViewById(R.id.rl_yixiang);
        rl_wancheng = (RelativeLayout) findViewById(R.id.rl_wancheng);
        rl_qiugou.setOnClickListener(this);
        rl_yixiang.setOnClickListener(this);
        rl_wancheng.setOnClickListener(this);

        tv_qiugou = (TextView) findViewById(R.id.tv_qiugou);
        tv_yixiang = (TextView) findViewById(R.id.tv_yixiang);
        tv_wangcheng = (TextView) findViewById(R.id.tv_wangcheng);
        listview = (SwipeListView) findViewById(R.id.lv_list);
        adapter = new WantBuyAdapter(getLayoutInflater(), dataEntity);
        ITAdapter = new WantBuyIntentionAdapter(this, intentionData);
        comAdapter = new CompleteOrderAdapter(this, completeData);
        listview.setAdapter(adapter);
        listview.setisNeedSwipe(false);
        listview.setXListViewListener(this);
        if (null != listview)
        {
            listview.hideFoot();
        }
        buyCenter = new BuyImpl(this);
        orderTrade = new OrderTradeImpl(this, this);
        getHistoryBuyData();
        tag = 0;


    }

    /**
     * 获取求购单协议
     */
    public void getHistoryBuyData()
    {
        page = "1";
        url = RequestUrl.getInstance(this).getUrl_getHistoryStanBuy(this, page,
                size);
        buyCenter.getHistoryStanBuy(url, Constants.INTERFACE_historyStanBuy);
    }

    /**
     * 获取意向单的协议 最后一个参数用来本地区分:意向单（1）和完成订单（2），在url中做路判断处理
     */
    private void getIntentionData()
    {
        page = "1";
        String url = RequestUrl.getInstance(this).getUrl_getPageOrder(
                this, page, size, "1");
        orderTrade.getPageOrder(url, Constants.INTERFACE_pageOrder);
    }

    /**
     * 获取已完成定单的协议 最后一个参数用来本地区分:意向单（1）和完成订单（2），在url中做路判断处理
     */
    private void getCompleteOrder()
    {
        page = "1";
        String url = RequestUrl.getInstance(this).getUrl_getPageOrder(
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
            case R.id.rl_qiugou://求购单
                if (tag != 0)
                {
                    changeView(0);
                    getHistoryBuyData();
                    listview.setAdapter(adapter);
                    listview.setisNeedSwipe(false);
                }

                break;

            case R.id.rl_yixiang://意向单
                if (tag != 1)
                {
                    changeView(1);
                    getIntentionData();
                    listview.setAdapter(ITAdapter);
                    listview.setisNeedSwipe(true);
                }

                break;

            case R.id.rl_wancheng://完成订单
                if (tag != 2)
                {
                    changeView(2);
                    getCompleteOrder();
                    listview.setAdapter(comAdapter);
                    listview.setisNeedSwipe(true);
                }
                break;

            default:
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

    @Subscriber(tag = "refreshList")
    public void RefreshList(String str)
    {
        onRefresh();
    }

    @Override
    public void onRefresh()
    {

        switch (tag)
        {
            case 0:
                getHistoryBuyData();
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
                if (hsData.getData() == null
                        || hsData.getData().getPagination() == null)
                {
                    return;
                }

                if (TextUtils.isEmpty(hsData.getData().getPagination().getPagenum())
                        || TextUtils
                        .isEmpty(hsData.getData().getPagination().getPage()))
                {
                    return;
                }
                if (Integer.parseInt(page) >= Integer.parseInt(hsData.getData()
                        .getPagination().getPagenum()))
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
                page = Integer.parseInt(page) + 1 + "";
                String url1 = RequestUrl.getInstance(this).getUrl_getPageOrder(
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
                String url2 = RequestUrl.getInstance(this).getUrl_getPageOrder(
                        this, page, size, "2");
                orderTrade.getPageOrder(url2, Constants.INTERFACE_pageOrder);
                break;
        }

    }


    @Override
    public void updateView(BaseData data)
    {
        if ("buy.historyStanBuy".equals(data.getCmd()))
        {
            hsData = (HistoryStanBuyData) data;
            HistoryStanBuyData Datas = (HistoryStanBuyData) data;
            if(Datas == null || Datas.getData().getPagination() == null){
                onLoad();
                adapter.reSetList(null);
                return;
            }
//            if (hsData.getData().getPagination() == null)
//            {
//                onLoad();
//                adapter.reSetList(null);
//                return;
//            }

            if (hsData.getData().getPagination().getDatas() != null)
            {
                ArrayList<DatasEntity> datas = (ArrayList<DatasEntity>) hsData.getData().getPagination().getDatas();
//                if (tag == 0)
//                {
                tag = 0;
                if ("1".equals(page))
                {
                    dataEntity = datas;
                } else
                {
                    dataEntity.addAll(datas);
                }
                adapter.reSetList(dataEntity);
//                }
            }
        } else if ("deal.pageOrder".equals(data.getCmd()))
        {
//            if (tag == 1)
//            {
            poData = (PageOrderData) data;
            PageOrderData poDatas = (PageOrderData) data;
            if (poDatas.getData() == null || poDatas.getData().getDatas() == null)
            {
                onLoad();
                ITAdapter.reSetList(null);
                return;
            }
            tag = 1;
            ArrayList<Datas> inData = poData.getData().getDatas();
            if ("1".equals(page))
            {
                intentionData = inData;
            } else
            {
                intentionData.addAll(inData);
            }
            ITAdapter.reSetList(intentionData);
//            } else if (tag == 2)
//            {
//                clData = (PageOrderData) data;
//                if (clData == null || clData.getData() == null || clData.getData().getDatas() == null)
//                {
//                    onLoad();
//                    return;
//                }
//                ArrayList<Datas> cldata = clData.getData().getDatas();
//                if ("1".equals(page))
//                {
//                    completeData = cldata;
//                } else
//                {
//                    completeData.addAll(cldata);
//                }
//                comAdapter.reSetList(completeData);
//            }
        } else if ("deal.pageAppraiseOrder".equals(data.getCmd()))
        {
            clData = (PageOrderData) data;
            PageOrderData clDatas = (PageOrderData) data;
            if (clDatas.getData() == null || clDatas.getData().getDatas() == null)
            {
                onLoad();
                comAdapter.reSetList(null);
                return;
            }
            tag = 2;
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
        ITAdapter.getOrderTrade().finishRequest();
        comAdapter.getOrderTrade().finishRequest();
    }
}

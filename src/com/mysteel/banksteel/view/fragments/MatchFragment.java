package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.ConsultOrderData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.MergeAdapter;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 撮合订单的片段
 * Created by zoujian on 15/10/26.
 */
public class MatchFragment extends BaseFragment implements XListView.IXListViewListener, IOrderTradeView
{

    private XListView listview;
    private MergeAdapter adapter;
    private ConsultOrderData COData;
    private ArrayList<ConsultOrderData.DatasEntity> mDatas;
    private IOrderTrade orderTrade;
    private String page = "1";
    private String size = "10";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_match, null);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    /**
     * 初始化组建
     */
    private void initView(View v)
    {
        listview = (XListView) v.findViewById(R.id.lv_list);
        adapter = new MergeAdapter(getActivity().getLayoutInflater(), mDatas);
        listview.setAdapter(adapter);
        listview.setXListViewListener(this);
        if (null != listview)
        {
            listview.hideFoot();
        }
        orderTrade = new OrderTradeImpl(getActivity(), this);
        getData();
    }

    private void getData()
    {
        page = "1";
        String url = RequestUrl.getInstance(getActivity()).getUrl_consultOrder(
                getActivity(), page, size);
        orderTrade.getConsultOrder(url, Constants.INTERFACE_consultOrder);
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    @Override
    public void onLoadMore()
    {
        if (COData == null || COData.getDatas() == null)
        {
            return;
        }

        if (TextUtils.isEmpty(COData.getPagenum())
                || TextUtils.isEmpty(COData.getPage()))
        {
            return;
        }
        if (Integer.parseInt(page) >= Integer.parseInt(COData.getPagenum()))
        {
            onLoad();
            Tools.showToast(getActivity(), "已经到最后一页!");
            return;
        }

        page = Integer.parseInt(page) + 1 + "";
        String url = RequestUrl.getInstance(getActivity()).getUrl_consultOrder(
                getActivity(), page, size);
        orderTrade.getConsultOrder(url, Constants.INTERFACE_consultOrder);
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("deal.consultOrder".equals(data.getCmd()))
        {
            COData = (ConsultOrderData) data;
            ConsultOrderData datas = (ConsultOrderData) data;
            if (datas == null || datas.getDatas() == null)
            {
                onLoad();
                adapter.reSetList(null);
                return;
            }
            if (COData != null || COData.getDatas() != null)
            {
                ArrayList<ConsultOrderData.DatasEntity> dataco = (ArrayList<ConsultOrderData.DatasEntity>) COData.getDatas();
                if ("1".equals(page))
                {
                    mDatas = dataco;
                } else
                {
                    mDatas.addAll(dataco);
                }
                adapter.reSetList(mDatas);
            }
            onLoad();
        }
    }

    @Override
    public void isShowDialog(boolean flag)
    {

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
}

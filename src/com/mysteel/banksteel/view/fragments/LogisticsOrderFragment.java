package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.ITransportManager;
import com.mysteel.banksteel.ao.impl.TransportManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.LogisticsOrderData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.LogisticsOrderAdapter;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;
import com.mysteel.banksteel.entity.LogisticsOrderData.DataEntity.DatasEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 物流：订单中心
 * Created by zoujian on 15/8/11.
 */
public class LogisticsOrderFragment extends BaseFragment implements XListView.IXListViewListener, ITransportManagerView
{

    private XListView listView;
    private ITransportManager transportManager;
    private String page = "1";
    private String size = "10";

    private LogisticsOrderData loData;
    private ArrayList<DatasEntity> logisticsData = new ArrayList<DatasEntity>();
    private LogisticsOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_logistics_order, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        listView = (XListView) view.findViewById(R.id.lv_list);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        listView.hideFoot();
        adapter = new LogisticsOrderAdapter(getActivity().getLayoutInflater(), logisticsData);
        listView.setAdapter(adapter);
        transportManager = new TransportManagerImpl(getActivity(), this);
    }

    public void getData()
    {
        page = "1";
        String url = RequestUrl.getInstance(getActivity()).getUrl_getOrderByUserId(
                getActivity(), page, size);
        LogUtils.e(url);
        transportManager.getOrderByUserId(url, Constants.INTERFACE_getOrderByUserId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Tools.isLogin(getActivity())){
            getData();
        }
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    @Override
    public void onLoadMore()
    {
        if (TextUtils.isEmpty(loData.getData().getPagenum())
                || TextUtils.isEmpty(loData.getData().getPage()))
        {
            return;
        }

        if (Integer.parseInt(page) >= Integer.parseInt(loData
                .getData().getPagenum()))
        {
            onLoad();
            Tools.showToast(getActivity(), "已经到最后一页!");
            return;
        }
        page = Integer.parseInt(page) + 1 + "";

        String url = RequestUrl.getInstance(getActivity()).getUrl_getOrderByUserId(
                getActivity(), page, size);
        transportManager.getOrderByUserId(url, Constants.INTERFACE_getOrderByUserId);
    }

    @Override
    public void updateView(BaseData data)
    {
        loData = (LogisticsOrderData) data;
        if (loData == null || loData.getData() == null || loData.getData().getDatas() == null)
        {
            onLoad();
            return;
        }
        ArrayList<DatasEntity> logisdata = loData.getData().getDatas();
        if ("1".equals(page))
        {
            logisticsData = logisdata;
        } else
        {
            logisticsData.addAll(logisdata);
        }
        adapter.reSetList(logisticsData);

        onLoad();
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
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINESE).format(new Date()));
    }
}

package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SubAreaData;
import com.mysteel.banksteel.entity.SubAreaData.DataEntity;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.adapters.AreaAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 区的列表
 * Created by zoujian on 15/8/11.
 */
public class AreaFragment extends BaseFragment implements XListView.IXListViewListener, IBuyView, AdapterView.OnItemClickListener
{

    private String cityCode;//传过来的带code的城市  大理白族自治州532900
    private XListView listView;
    private SubAreaData saData;
    private ArrayList<DataEntity> dataEntities = new ArrayList<DataEntity>();
    private AreaAdapter adapter;
    private IBuyCenter cityListImpl;
    private String mCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_area, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        listView = (XListView) view.findViewById(R.id.lv_list);
        listView.setPullLoadEnable(false); // 禁用上拉加载更多功能
        listView.setXListViewListener(this);
        adapter = new AreaAdapter(getActivity().getLayoutInflater(), dataEntities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        cityListImpl = new BuyImpl(getActivity(), this);
    }

    public void setCityData(String city)
    {
        cityCode = city;
        mCity = city.substring(0, (city.length() - 6));
//        Toast.makeText(getActivity(), "城市：" + mCity, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), "code：" + city.substring((city.length()-6),city.length()), Toast.LENGTH_SHORT).show();
        getData();
    }

    public void getData()
    {
        String url = RequestUrl.getInstance(getActivity()).getUrl_querySubAreaByCode(getActivity(), cityCode.substring((cityCode.length() - 6), cityCode.length()));
        cityListImpl.getSubAreaByCode(url, Constants.INTERFACE_querySubAreaByCode);
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    @Override
    public void onLoadMore()
    {

    }

    @Override
    public void updateView(BaseData data)
    {
        saData = (SubAreaData) data;
        if (saData == null || saData.getData() == null)
        {
            onLoad();
            return;
        }
        dataEntities = saData.getData();
        adapter.reSetList(dataEntities);
        listView.setAdapter(adapter);
        onLoad();

    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        cityListImpl.finishRequest();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (position <= 0 || dataEntities == null || position > dataEntities.size())
        {
            return;
        }
        String cityArea = mCity + "|" + dataEntities.get(position - 1).getName();
        EventBus.getDefault().post(cityArea, "getCityArea");
    }
}

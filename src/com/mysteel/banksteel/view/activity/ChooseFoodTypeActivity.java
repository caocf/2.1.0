package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.FoodTypeData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.adapters.ChooseFoodTypeAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 选择物流类型的页面
 * Created by zoujian on 15/8/12.
 */
public class ChooseFoodTypeActivity extends SwipeBackActivity implements View.OnClickListener,
        XListView.IXListViewListener, IBuyView,AdapterView.OnItemClickListener
{

    /**
     * LOG SWITCHER.
     */
    private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;
    private ChooseFoodTypeAdapter adapter;
    private FoodTypeData ftData;
    private ArrayList<String> productTypeEntities = new ArrayList<String>();
    private IBuyCenter cityListImpl;
    private XListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefoodtype);
        initViews();
        getData();
    }

    private void getData()
    {
//        String url = RequestUrl.LogisticsURL;
        String url = RequestUrl.getInstance(this).getUrl_queryTransportBreed(this);
        cityListImpl.getProductType(url, Constants.INTERFACE_queryTransportBreed);

    }

    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("货物类型");
        backLayout.setOnClickListener(this);
        listView = (XListView) findViewById(R.id.lv_list);
        listView.setPullLoadEnable(false); // 禁用上拉加载更多功能
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(this);
        adapter = new ChooseFoodTypeAdapter(getLayoutInflater(), productTypeEntities);
        listView.setAdapter(adapter);
        cityListImpl = new BuyImpl(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        ftData = (FoodTypeData) data;
        if(ftData == null || ftData.getBreeds() == null)
        {
            return;
        }
        productTypeEntities = ftData.getBreeds();
        if (DEBUG)
        {
            Log.d("Type", "数据:" + productTypeEntities.size());
        }
        adapter.reSetList(productTypeEntities);
        onLoad();

    }

    @Override
    public void isShowDialog(boolean flag)
    {

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
        if (position <= 0 || productTypeEntities.size() == 0 || position > productTypeEntities.size())
        {
            return;
        }
        String cityArea = productTypeEntities.get(position - 1);
        EventBus.getDefault().post(cityArea, "getFoodType");
        finish();
    }
}

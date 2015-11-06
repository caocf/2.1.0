package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ResourceManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.HistoryStanBuyData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SourceSearchAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 买家的同类资源
 *
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class ResourceSameBuyersActivity extends SwipeBackActivity implements
        IListViewInterface, XListView.IXListViewListener, IBuyView, OnClickListener, IResourceManagerView
{

    private Context mContext;

    private XListView xListView;
    private ProgressBar progressBar;

    private ResourceManagerImpl resourceManagerImpl;
    private SearchResourceData searchResourceData;
    private ArrayList<SearchResourceData.Data.Datas> datas;
    private SourceSearchAdapter adapter;

    private String dafenlei = "";
    private String pinlei = "";
    private String caizhi = "";
    private String guige = "";
    private String pinpai = "";
    private String cunhuodi = "";

    private String page = "1";
    private String size = "10";

    HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity datasEntity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_same);
        mContext = this;
        initViews();
        initData();
    }


    /**
     * 初始化页面组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("同类资源");
        backLayout.setOnClickListener(this);

        xListView = (XListView) findViewById(R.id.lv_listview);
        progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        xListView.setPullLoadEnable(false);
        xListView.setXListViewListener(this);

        datasEntity = (HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity) getIntent().getSerializableExtra("datas");
        if (null == datasEntity)
        {
//            datasEntity = new HistoryStanBuyData().new DataEntity().new PaginationEntity().new DatasEntity();
            pinlei = getIntent().getExtras().getString("pinlei");
            caizhi = getIntent().getExtras().getString("caizhi");
            guige = getIntent().getExtras().getString("guige");
            pinpai = getIntent().getExtras().getString("pinpai");
            cunhuodi = getIntent().getExtras().getString("cunhuodi");

        } else
        {
            pinlei = datasEntity.getBreed();
            caizhi = datasEntity.getMaterial();
            guige = datasEntity.getSpec();
            pinpai = datasEntity.getBrand();
            cunhuodi = datasEntity.getCity();
        }


        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                if (position <= 0 || position > datas.size())
                {
                    return;
                }
                Intent intent = new Intent(ResourceSameBuyersActivity.this,
                        ResouceDetailActivity.class);
                intent.putExtra("datas", datas.get(position - 1));
                startActivity(intent);
            }
        });
    }

    private void initData()
    {
        adapter = new SourceSearchAdapter(this,0);
        xListView.setAdapter(adapter);
        getRequestData();
    }

    /**
     * 拉取数据
     */
    private void getRequestData()
    {
        resourceManagerImpl = new ResourceManagerImpl(this);
        String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, "", pinlei, "", "", "", "", page, size);
        LogUtils.e(url);
        resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
    }

    @Subscriber(tag = "ResourceSameBuyersActivity_refresh")
    private void refreshActivity(String qty)
    {
        getRequestData();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:
                // 返回菜单
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void stopUpdate()
    {

    }

    @Override
    public void searchResource(SearchResourceData data)
    {

    }

    @Override
    public void updateView(BaseData data)
    {

    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh()
    {
        //String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi, page, size);
        String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, "", pinlei, "", "", "", "", page, size);
        resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore()
    {
        if (TextUtils.isEmpty(searchResourceData.getData().getPagenum())
                || TextUtils.isEmpty(searchResourceData.getData().getPage()))
        {
            return;
        }

        if (Integer.parseInt(page) >= Integer.parseInt(searchResourceData
                .getData().getPagenum()))
        {
            onLoad();
            Tools.showToast(this, "已经到最后一页!");
            return;
        }
        page = Integer.parseInt(page) + 1 + "";


        String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, "", pinlei, "", "", "", "", page, size);
        LogUtils.e(url);
        resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
    }

    @Override
    public void matchResource(SearchResourceData data)
    {
        searchResourceData = data;
        if (data.getData().getDatas() != null && data.getData().getDatas().size() > 0)
        {
            if ("1".equals(page))
            {
                datas = searchResourceData.getData().getDatas();
                xListView.setVisibility(View.VISIBLE);
                xListView.setPullRefreshEnable(true);
                xListView.setPullLoadEnable(true);
                xListView.setAdapter(adapter);
                adapter.reSetListView(datas);
            } else
            {
                datas.addAll(searchResourceData.getData().getDatas());
            }
            adapter.reSetListView(datas);
        } else
        {
            if ("1".equals(page))
            {
                searchResourceData = null;
                Tools.showToast(mContext, "暂无数据!");
                xListView.setPullRefreshEnable(true);
                xListView.setPullLoadEnable(false);
                xListView.setAdapter(adapter);
                adapter.reSetListView(new ArrayList<SearchResourceData.Data.Datas>());
            }
        }
        onLoad();
    }

    private void onLoad()
    {
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINESE).format(new Date()));
    }
}


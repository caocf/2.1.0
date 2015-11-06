package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mysteel.banksteel.view.adapters.ValidityAdapter;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 有效期选择
 * Created by zoujian on 15/8/24.
 */
public class ValidityActivity extends SwipeBackActivity implements View.OnClickListener, XListView.IXListViewListener
        , ListView.OnItemClickListener
{

    private XListView listView;
    private ValidityAdapter adapter;
    String[] mData = new String[]{"3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefoodtype);
        initViews();
    }

    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("有效期选择");
        backLayout.setOnClickListener(this);
        listView = (XListView) findViewById(R.id.lv_list);
        listView.setPullLoadEnable(false); // 禁用上拉加载更多功能
        listView.setPullRefreshEnable(false);
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(this);
        adapter = new ValidityAdapter(getLayoutInflater());
        listView.setAdapter(adapter);

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
    public void onRefresh()
    {

    }

    @Override
    public void onLoadMore()
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (position <= 0 || position > mData.length)
        {
            return;
        }
//        Toast.makeText(this, "有效期：" + mData[position - 1] + "天", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(mData[position - 1], "commit");
        finish();

    }
}

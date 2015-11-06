package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SupportFragmentAdapter;
import com.mysteel.banksteel.view.fragments.MallFragment;
import com.mysteel.banksteel.view.fragments.MatchFragment;
import com.mysteel.banksteel.view.ui.MyViewPager;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 买家订单中心变更为商城订单和撮合订单
 * Created by zoujian on 15/10/26.
 */
public class OrderCenterMergeActivity extends SwipeBackActivity implements View.OnClickListener
{

    private MallFragment mallFragment;
    private MatchFragment matchFragment;
    private MyViewPager viewPager;
    private SupportFragmentAdapter supportFragmentAdapter;

    private TextView tv_logistics_price, tv_myorder;
    private LinearLayout ll_xunjia_fd, ll_order_fd;
    private boolean tag = false;//true： 商城订单  false：撮合订单

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_centre_merge);
        initViews();
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("订单中心");

        backLayout.setOnClickListener(this);
        tv_logistics_price = (TextView) findViewById(R.id.tv_logistics_price);
        tv_myorder = (TextView) findViewById(R.id.tv_myorder);

        ll_xunjia_fd = (LinearLayout) findViewById(R.id.ll_xunjia_fd);
        ll_order_fd = (LinearLayout) findViewById(R.id.ll_order_fd);
        ll_xunjia_fd.setOnClickListener(this);
        ll_order_fd.setOnClickListener(this);

        viewPager = (MyViewPager) findViewById(R.id.pager);
        if (!Tools.isLogin(this))
        {
            viewPager.setScrollble(false);
        } else
        {
            viewPager.setScrollble(true);
        }

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        mallFragment = new MallFragment();

        matchFragment = new MatchFragment();
        fragments.add(mallFragment);
        fragments.add(matchFragment);
        supportFragmentAdapter = new SupportFragmentAdapter(
                getSupportFragmentManager(), fragments);
        viewPager.setAdapter(supportFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                refreshPos(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

            }
        });

    }

    private void refreshPos(int num)
    {
        if (num == 1)
        {
            if (!Tools.isLogin(this))
            {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                //this.finish();
            }
        }
        viewPager.setCurrentItem(num);
        switch (num)
        {
            case 0://商城订单
                tag = false;
                tv_logistics_price.setBackgroundResource(R.drawable.current_down);
                tv_logistics_price.setTextColor(getResources().getColor(R.color.main_imred));
                tv_myorder.setBackgroundResource(R.drawable.default_up);
                tv_myorder.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;
            case 1://撮合订单
                tag = true;
                tv_logistics_price.setBackgroundResource(R.drawable.default_up);
                tv_logistics_price.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_myorder.setBackgroundResource(R.drawable.current_down);
                tv_myorder.setTextColor(getResources().getColor(R.color.main_imred));
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;

            case R.id.ll_xunjia_fd://商城订单
                if (tag)
                {//
                    setViewVisible();
                    refreshPos(0);
                    tag = false;
                }
                break;

            case R.id.ll_order_fd://撮合订单
                if (!Tools.isLogin(this))
                {
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                } else
                {
                    if (!tag)
                    {//
                        setViewVisible();
                        refreshPos(1);
                        tag = true;
                    }
                }
                break;

            default:
                break;
        }
    }

    private void setViewVisible()
    {
        if (tag)
        {//商城订单
            tv_logistics_price.setBackgroundResource(R.drawable.current_down);
            tv_logistics_price.setTextColor(getResources().getColor(R.color.main_imred));
            tv_myorder.setBackgroundResource(R.drawable.default_up);
            tv_myorder.setTextColor(getResources().getColor(R.color.main_font_gray));
        } else
        {//撮合订单
            tv_logistics_price.setBackgroundResource(R.drawable.default_up);
            tv_logistics_price.setTextColor(getResources().getColor(R.color.main_font_gray));
            tv_myorder.setBackgroundResource(R.drawable.current_down);
            tv_myorder.setTextColor(getResources().getColor(R.color.main_imred));
        }
    }
}

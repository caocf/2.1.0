package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.view.adapters.SupportFragmentAdapter;
import com.mysteel.banksteel.view.fragments.AreaFragment;
import com.mysteel.banksteel.view.fragments.CityFragment;
import com.mysteel.banksteel.view.ui.MyViewPager;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * 物流页面选择城市activity
 * Created by zoujian on 15/8/11.
 */
public class LogisticsChooseCityActivity extends SwipeBackActivity implements View.OnClickListener
{

    private CityFragment cityFragment;
    private AreaFragment areaFragment;

    private MyViewPager viewPager;
    private SupportFragmentAdapter supportFragmentAdapter;

    private TextView tv_city, tv_area;
    private LinearLayout ll_layout_city, ll_layout_area;
    private boolean tag = false;//true：区  false：市
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistivs_choosecity);
        initViews();
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("选择城市");
        backLayout.setOnClickListener(this);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_area = (TextView) findViewById(R.id.tv_area);

        ll_layout_city = (LinearLayout) findViewById(R.id.ll_layout_city);
        ll_layout_area = (LinearLayout) findViewById(R.id.ll_layout_area);
        ll_layout_city.setOnClickListener(this);
        ll_layout_area.setOnClickListener(this);

        viewPager = (MyViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        cityFragment = new CityFragment();
        areaFragment = new AreaFragment();
        fragments.add(cityFragment);
        fragments.add(areaFragment);
        supportFragmentAdapter = new SupportFragmentAdapter(
                getSupportFragmentManager(), fragments);
        viewPager.setAdapter(supportFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                changeViewPager(arg0);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;
            case R.id.ll_layout_city://市的点击事件
                if (tag)//在区的位置上
                {
                    changeViewPager(0);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 获取city中的消息
     */
    @Subscriber(tag = "CityFragment_city")
    public void changeName(String str)
    {
        address = str;
//        Toast.makeText(this, "activity中得到地址：" + address, Toast.LENGTH_SHORT).show();
        areaFragment.setCityData(str);
        changeViewPager(1);
    }

    /**
     * 获取city中的消息
     */
    @Subscriber(tag = "getCityArea")
    public void finishActivity(String str)
    {
        finish();
    }

    private void changeViewPager(int num)
    {
        viewPager.setCurrentItem(num);
        switch (num)
        {
            case 0:
                viewPager.setScrollble(false);
                tag = false;
                tv_city.setBackgroundResource(R.drawable.current_down);
                tv_city.setTextColor(getResources().getColor(R.color.main_imred));
                tv_area.setBackgroundResource(R.drawable.default_up);
                tv_area.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;

            case 1:
                viewPager.setScrollble(true);
                tag = true;
                tv_city.setBackgroundResource(R.drawable.default_up);
                tv_city.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_area.setBackgroundResource(R.drawable.current_down);
                tv_area.setTextColor(getResources().getColor(R.color.main_imred));
                break;
        }

    }

    public String getCityAndCode()
    {
        return address;
    }


}

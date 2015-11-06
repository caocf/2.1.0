package com.mysteel.banksteel.view.activity;

import java.util.ArrayList;

import com.mysteel.banksteel.view.adapters.SupportFragmentAdapter;
import com.mysteel.banksteel.view.fragments.AccurateFindFoodFragment;
import com.mysteel.banksteel.view.fragments.VoiceFindFoodFragment;
import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * 我要找货页面
 * Created by zoujian on 15/7/23.
 */
public class FindFoodActivity extends BaseActivity implements View.OnClickListener
{

    private AccurateFindFoodFragment accurateFragment;
    private VoiceFindFoodFragment voiceFragment;
    private TextView tv_jingzhun, tv_yuying;//精准找货 或 语音找货
    private ViewPager viewPager;
    private boolean tag = false;//true：语音找货  false：精准找货
    private LinearLayout ll_jingzhun_fd, ll_yuying_fd;//精准找货 和 语音找货的布局

    private SupportFragmentAdapter supportFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findfood);
        initViews();

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText(R.string.woyaoqiugou);
        backLayout.setOnClickListener(this);
        tv_jingzhun = (TextView) findViewById(R.id.tv_jingzhun);
        tv_yuying = (TextView) findViewById(R.id.tv_yuying);

        ll_jingzhun_fd = (LinearLayout) findViewById(R.id.ll_jingzhun_fd);
        ll_yuying_fd = (LinearLayout) findViewById(R.id.ll_yuying_fd);
        ll_jingzhun_fd.setOnClickListener(this);
        ll_yuying_fd.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        accurateFragment = new AccurateFindFoodFragment();
        voiceFragment = new VoiceFindFoodFragment();
        fragments.add(accurateFragment);
        fragments.add(voiceFragment);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;

            case R.id.ll_jingzhun_fd://精准找货
                if (tag)
                {//
                    setViewVisible();
                    refreshPos(0);
                    tag = false;
                }
                break;

            case R.id.ll_yuying_fd://语音找货
                if (!tag)
                {//
                    setViewVisible();
                    refreshPos(1);
                    tag = true;
                }
                break;

            default:
                break;
        }
    }

    private void setViewVisible()
    {
        if (tag)
        {//精准找货
            tv_jingzhun.setBackgroundResource(R.drawable.current_down);
            tv_jingzhun.setTextColor(getResources().getColor(R.color.main_imred));
            tv_yuying.setBackgroundResource(R.drawable.default_up);
            tv_yuying.setTextColor(getResources().getColor(R.color.main_font_gray));
        } else
        {//语音找货
            tv_jingzhun.setBackgroundResource(R.drawable.default_up);
            tv_jingzhun.setTextColor(getResources().getColor(R.color.main_font_gray));
            tv_yuying.setBackgroundResource(R.drawable.current_down);
            tv_yuying.setTextColor(getResources().getColor(R.color.main_imred));
        }
    }

    private void refreshPos(int num)
    {
        viewPager.setCurrentItem(num);
        switch (num)
        {
            case 0://精准找货
                tag = false;
                tv_jingzhun.setBackgroundResource(R.drawable.current_down);
                tv_jingzhun.setTextColor(getResources().getColor(R.color.main_imred));
                tv_yuying.setBackgroundResource(R.drawable.default_up);
                tv_yuying.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;
            case 1://语音找货
                tag = true;
                tv_jingzhun.setBackgroundResource(R.drawable.default_up);
                tv_jingzhun.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_yuying.setBackgroundResource(R.drawable.current_down);
                tv_yuying.setTextColor(getResources().getColor(R.color.main_imred));
                break;
        }
    }
}

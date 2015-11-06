package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ResourceManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SupportFragmentAdapter;
import com.mysteel.banksteel.view.fragments.ChartFragment;
import com.mysteel.banksteel.view.fragments.SellerFragment;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;
import com.mysteel.banksteel.view.ui.Rotate3dAnimation;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.ShowDialog.ICallBack;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * 资源详情
 *
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class ResouceDetailActivity extends SwipeBackActivity implements View.OnClickListener,
        IResourceManagerView
{

    private Context mContext;
    private SearchResourceData.Data.Datas datas;
    private ResourceManagerImpl resourceManagerImpl;

    private ChartFragment chartFragment;
    private SellerFragment sellerFragment;

    private ProgressBar progressBar;
    private TextView tv_partname; //品名
    private TextView tv_spec; //规格
    private TextView tv_material; //材质
    private TextView tv_origin; //品牌产地
    private TextView tv_delivery; //交货地
    private TextView tv_number; //求购数量
    private TextView tv_curjiage; //当前价
    private TextView tv_jiage_unit; //元/吨
    private TextView tv_lowjiage; //最低价
    private ImageView iv_transport; //跳转到物流
    private LinearLayout ll_minprice; //最低价


    private boolean tag = false;//true：货主  false：走势图

    private LinearLayout ll_huozhu_fd, ll_chart_fd; //货主 走势图布局
    private TextView tv_huozhu, tv_chart; //货主 走势图
    private ViewPager viewPager;

    private Button btn_qiandingdan; //签订意向单

    private SupportFragmentAdapter supportFragmentAdapter;

    private String qty = ""; //吨数

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_detail);
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
        tvTitle.setText("资源详情");
        backLayout.setOnClickListener(this);
        resourceManagerImpl = new ResourceManagerImpl(this);

        datas = (SearchResourceData.Data.Datas) getIntent().getSerializableExtra("datas");

        progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        tv_partname = (TextView) findViewById(R.id.tv_partname);
        tv_spec = (TextView) findViewById(R.id.tv_spec);
        tv_material = (TextView) findViewById(R.id.tv_material);
        tv_origin = (TextView) findViewById(R.id.tv_origin);
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_curjiage = (TextView) findViewById(R.id.tv_curjiage);
        tv_jiage_unit = (TextView) findViewById(R.id.tv_jiage_unit);
        tv_lowjiage = (TextView) findViewById(R.id.tv_lowjiage);
        iv_transport = (ImageView) findViewById(R.id.iv_transport);
        iv_transport.setOnClickListener(this);
        ll_minprice = (LinearLayout) findViewById(R.id.ll_minprice);
        ll_minprice.setOnClickListener(this);

        //Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation()
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_self);
        applyRotation(0, 360);

        ll_huozhu_fd = (LinearLayout) findViewById(R.id.ll_huozhu_fd);
        ll_chart_fd = (LinearLayout) findViewById(R.id.ll_chart_fd);
        ll_huozhu_fd.setOnClickListener(this);
        ll_chart_fd.setOnClickListener(this);

        tv_huozhu = (TextView) findViewById(R.id.tv_huozhu);
        tv_chart = (TextView) findViewById(R.id.tv_chart);

        viewPager = (ViewPager) findViewById(R.id.pager);

        btn_qiandingdan = (Button) findViewById(R.id.btn_qiandingdan);
        btn_qiandingdan.setOnClickListener(this);

        showDetailData();

    }


    private void initData()
    {
        if (datas.getId().equals(datas.getMinPriceId()))
        {
            ll_minprice.setVisibility(View.INVISIBLE);
        } else
        {
            ll_minprice.setVisibility(View.VISIBLE);
        }

        tv_partname.setText(datas.getBreedName());
        tv_spec.setText(datas.getSpec());
        tv_material.setText(datas.getMaterial());
        tv_origin.setText(datas.getBrand());
        tv_delivery.setText(datas.getWarehouse());
        if (!TextUtils.isEmpty(datas.getCityName()))
        {
            tv_number.setText(datas.getCityName());
        }

        if(Float.parseFloat(datas.getPrice())<1000){
            tv_curjiage.setText("面议价");
            tv_jiage_unit.setVisibility(View.GONE);
        }else{
            tv_curjiage.setText(datas.getPrice());
            tv_jiage_unit.setVisibility(View.VISIBLE);
        }
        tv_lowjiage.setText(datas.getMinPrice());

        sellerFragment = new SellerFragment();
        Bundle sellerBundle = new Bundle();
        sellerBundle.putSerializable("sellerBundle", datas);
        sellerFragment.setArguments(sellerBundle);

        chartFragment = new ChartFragment();
        Bundle sellerBundle2 = new Bundle();
        sellerBundle2.putSerializable("chartBundle", datas);
        chartFragment.setArguments(sellerBundle2);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(sellerFragment);
        fragments.add(chartFragment);
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
        viewPager.setCurrentItem(num);
        switch (num)
        {
            case 0://货主
                tag = false;
                tv_huozhu.setBackgroundResource(R.drawable.current_down);
                tv_huozhu.setTextColor(getResources().getColor(R.color.main_imred));
                tv_chart.setBackgroundResource(R.drawable.default_up);
                tv_chart.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;
            case 1://走势图
                tag = true;
                tv_huozhu.setBackgroundResource(R.drawable.default_up);
                tv_huozhu.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_chart.setBackgroundResource(R.drawable.current_down);
                tv_chart.setTextColor(getResources().getColor(R.color.main_imred));
                break;
        }
    }

    @Subscriber(tag = "qty")
    private void getQtyClose(String qty)
    {
        this.qty = qty;
    }

    @Subscriber(tag = "closeback")
    private void getCloseBack(String str)
    {
        if (!TextUtils.isEmpty(str) && str.equals("true"))
        {
            ShowDialog dialog = new ShowDialog(mContext, "提交成功!是否需要物流服务?", new ICallBack()
            {

                @Override
                public void requestOK()
                {
                    forwordTransport();
                }

                @Override
                public void requestCancle()
                {
                    finish();
                    EventBus.getDefault().post("", "ResourceSameBuyersActivity_refresh");
                }
            });
            dialog.setcommitBtn("需要");
            dialog.setcancleBtn("不需要");
            dialog.show();
        } else
        {
            Tools.showToast(mContext, "提交失败,请重新提交或联系客服!");
            return;
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

            case R.id.ll_huozhu_fd://货主
                if (tag)
                {
                    refreshPos(0);
                    tag = false;
                }
                break;

            case R.id.ll_chart_fd://趋势图
                if (!tag)
                {
                    refreshPos(1);
                    tag = true;
                }
                break;
            case R.id.btn_qiandingdan: //签订意向单
                if (Tools.isLogin(mContext))
                {
                    Intent intent = new Intent(mContext, ResouceDetailSubmitActivity.class);
                    intent.putExtra("datas", datas);
                    mContext.startActivity(intent);
                } else
                {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                }
                break;
            case R.id.iv_transport:
                forwordImageTransport();
                break;
            case R.id.ll_minprice:
                showDetailData();
                break;
            default:
                break;
        }
    }

    /**
     * 获取资源详情
     */
    private void showDetailData()
    {
        String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, datas.getId());
        LogUtils.e(url);
        resourceManagerImpl.getDetailResouce(url, Constants.INTERFACE_matchResource);
    }

    /**
     * 跳转物流
     */
    private void forwordTransport()
    {
        Intent intent = new Intent(mContext, LogisticsActivity.class);
        intent.putExtra("qty", qty);
        intent.putExtra("breedName", datas.getBreedName());
        startActivity(intent);
    }


    /**
     * 点击图标跳转物流
     */
    private void forwordImageTransport()
    {
        Intent intent = new Intent(mContext, LogisticsFindFoodActivity.class);
        intent.putExtra("qty", qty);
        intent.putExtra("breedName", datas.getBreedName());
        startActivity(intent);
    }


    /**
     * 开启动画
     */
    private void applyRotation(float start, float end)
    {
        // 计算中心点
        final float centerX = iv_transport.getWidth() / 2.0f;
        final float centerY = iv_transport.getHeight() / 2.0f;

        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
                25, 15, 0f, true);
        rotation.reset();
        rotation.setDuration(3000);
        rotation.setFillAfter(true);
        rotation.setRepeatCount(Integer.MAX_VALUE);
        rotation.setInterpolator(new AccelerateInterpolator());
        iv_transport.startAnimation(rotation);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        applyRotation(0, 360);
    }

    @Override
    public void matchResource(SearchResourceData data)
    {
        if (data.getData().getDatas() != null && data.getData().getDatas().size() > 0)
        {
            this.datas = data.getData().getDatas().get(0);
            initData();
        }
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
        if (flag)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100);
        } else
        {
            progressBar.setVisibility(View.GONE);
        }
    }
}

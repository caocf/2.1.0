package com.mysteel.banksteel.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.ao.IAdvertManager;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.AdvertManagerImpl;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.AdvertData;
import com.mysteel.banksteel.entity.AdvertData.Data.Items;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.TodayResourceCountData;
import com.mysteel.banksteel.entity.UncancelData;
import com.mysteel.banksteel.entity.UserDetailsCountData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.AdvertActivity;
import com.mysteel.banksteel.view.activity.FindFoodActivity;
import com.mysteel.banksteel.view.activity.LocalQuotedPriceActivity;
import com.mysteel.banksteel.view.activity.LoginActivity;
import com.mysteel.banksteel.view.activity.MyInfoActivity;
import com.mysteel.banksteel.view.activity.OpportunityMoreActivity;
import com.mysteel.banksteel.view.activity.OrderCenterMergeActivity;
import com.mysteel.banksteel.view.activity.OrderCentreActivity;
import com.mysteel.banksteel.view.activity.RemindSettingActivity;
import com.mysteel.banksteel.view.activity.ResourceSearchActivity;
import com.mysteel.banksteel.view.activity.SellOrderCenterActivity;
import com.mysteel.banksteel.view.activity.SteelCircleActivity;
import com.mysteel.banksteel.view.interfaceview.IAdvertManagerView;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CustomGalleryView;
import com.mysteel.banksteel.view.ui.CustomGalleryView.CustomGalleryOnItemClickListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-5-25 下午7:30:20
 */
public class MainFragment extends BaseFragment implements OnClickListener,
        IBuyView, IAdvertManagerView, IOrderTradeView
{

    /**
     * DEBUG.
     */
    private static final boolean DEBUG = true & BankSteelApplication.GLOBAL_DEBUG;
    /**
     * TAG.
     */
    private static final String TAG = "MainFragment";
    /**
     * 间隔时间,每隔time秒图片跳动一次
     */
    private int time = 3000;
    /**
     * 左边侧滑菜单
     */
    private LinearLayout myBuy;

    /** 主页里边的listView:精准找货、语音找货、我的订单 */
//	private ListView listView;
//	private HomePageAdapter homePageAdapter;

//	private ListView publishListView;
//	private HomePublishBuyAdapter homePubBuyAdapter;

    /** 底部没有数据的提示布局 */
//	private LinearLayout layout_prompt;

    /**
     * 图片轮播的viewPager
     */
    private CustomGalleryView banner;
    /**
     * 图片轮播右下角的点
     */
    private LinearLayout llDots;

    /**
     * 当前viewpager选中的item
     */
    private View currentView;
    /**
     * progressBar
     */
    private ProgressBar progressBar;

    /**
     * title
     */
    public RelativeLayout backLayout, rl_head_layout; // 返回按钮区域
    public ImageView menuBtn; // 菜单按钮
    public ImageView imBack; // 返回
    public TextView tvTitle; // 标题
    public RelativeLayout rightLayout; // 右侧控制区域
    public TextView tvRightText; // 右侧文本
    public ImageView imRightImage; // 右侧图标
    public IBuyCenter buyCenter;
    public UncancelData unData;
    public IAdvertManager advertManager;
    private LinearLayout ll_buyorsell_layout;//买家和卖家的布局

    private TextView tv_buyer_layout, tv_sell_layout, tv_data;//买家和卖家的布局,今日资源数目布局

    //需要切换现实隐藏
    private RelativeLayout rl_ziyuan;//资源
    private LinearLayout ll_search_layout, ll_buy_layout, ll_order_layout;//资源搜索布局，我要找货布局，订单中心布局
    private View view01, view02;//线的布局

    private LinearLayout ll_all_layout;//买家整体布局
    private View view_include_layout;//卖家整体布局

    private LinearLayout ll_left_layout; //商城报价
    private LinearLayout ll_right_layout; //商城报价
    private LinearLayout ll_sell_order_layout;//卖家中心的订单中心

    // private ArrayList<PagDatas> pData = new ArrayList<PagDatas>();

    private boolean tag;//判断是在买家或者是在卖家的页面 true 为卖家 false为买家
    private IOrderTrade orderTrade;
    private UserDetailsCountData userData;
    private TextView tv_buy_order, tv_sell_order, tv_left_round, tv_right_round;//买家订单数，卖家订单数，同城资源数，更多资源数
    private ImageView img_order_menu, img_sell_order_menu;//买家订单中心图片，卖家订单中心图片

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private boolean isNet = true;
    private boolean isLoad = false;//图片是否已经下载

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    if(DEBUG)
                	{
                    	Log.d("MainFragment_Tag", "当前网络名称：" + name);
                	}
                    LoadData();
                    
                } else {
                	isNet = false;
                	if(DEBUG)
                	{
                		Log.d("MainFragment_Tag", "没有可用网络");
                	}
                }
            }
        }

        /**
         * 当首次进来无网络再次连上网的时候重新获取数据
         */
		private void LoadData()
		{
			if(!isNet)
			{
				getTodayResource();
				getuUserDetailsCount();
				if(!isLoad)
				{
					getAdvert();
				}
				EventBus.getDefault().post("", "LOGIN");
				isNet = true;
			}
		}
    };


    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        currentView = inflater.inflate(
                R.layout.fragment_slidingpane_main_layout, container, false);
        EventBus.getDefault().register(this);
        initView(currentView);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mReceiver, mFilter);

        return currentView;
    }
    
    private void initView(View currentView)
    {

        JPushInterface.init(getActivity().getApplicationContext());
        backLayout = (RelativeLayout) currentView
                .findViewById(R.id.menu_layout);
        menuBtn = (ImageView) currentView.findViewById(R.id.menu_imgbtn);
        imBack = (ImageView) currentView.findViewById(R.id.im_back);
        tvTitle = (TextView) currentView.findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        rightLayout = (RelativeLayout) currentView
                .findViewById(R.id.share_layout);
        rightLayout.setOnClickListener(this);
        tvRightText = (TextView) currentView
                .findViewById(R.id.tv_title_right_text);
        imRightImage = (ImageView) currentView.findViewById(R.id.share_imgbtn);
        menuBtn.setVisibility(View.VISIBLE);
        backLayout.setOnClickListener(this);
        imBack.setVisibility(View.GONE);
        imRightImage.setVisibility(View.VISIBLE);
//		myBuy = (LinearLayout) currentView.findViewById(R.id.layout_mybuy);// 我的求购
//		myBuy.setOnClickListener(this);
//		layout_prompt = (LinearLayout) currentView
//				.findViewById(R.id.layout_prompt);
        progressBar = (ProgressBar) currentView
                .findViewById(R.id.pb_progressbar);

        rl_head_layout = (RelativeLayout) currentView.findViewById(R.id.rl_head_layout);
        rl_head_layout.setBackgroundResource(R.color.shouye_head_color);

//		listView = (ListView) currentView.findViewById(R.id.list_item);
//		homePageAdapter = new HomePageAdapter(getActivity().getLayoutInflater());
//		listView.setAdapter(homePageAdapter);

        ll_buyorsell_layout = (LinearLayout) currentView.findViewById(R.id.ll_buyorsell_layout);//买家和卖家的布局
        ll_buyorsell_layout.setVisibility(View.VISIBLE);
        tv_sell_layout = (TextView) currentView.findViewById(R.id.tv_sell_layout);//卖家
        tv_buyer_layout = (TextView) currentView.findViewById(R.id.tv_buyer_layout);//买家
        tv_data = (TextView) currentView.findViewById(R.id.tv_data);
        tv_sell_layout.setOnClickListener(this);
        tv_buyer_layout.setOnClickListener(this);

        banner = (CustomGalleryView) currentView.findViewById(R.id.banner);
        llDots = (LinearLayout) currentView.findViewById(R.id.ll_dots);

        ll_all_layout = (LinearLayout) currentView.findViewById(R.id.ll_all_layout);

        rl_ziyuan = (RelativeLayout) currentView.findViewById(R.id.rl_ziyuan);
        ll_search_layout = (LinearLayout) currentView.findViewById(R.id.ll_search_layout);
        ll_buy_layout = (LinearLayout) currentView.findViewById(R.id.ll_buy_layout);
        ll_order_layout = (LinearLayout) currentView.findViewById(R.id.ll_order_layout);
        view01 = currentView.findViewById(R.id.view01);
        view02 = currentView.findViewById(R.id.view02);

        rl_ziyuan.setOnClickListener(this);
        ll_buy_layout.setOnClickListener(this);
        ll_search_layout.setOnClickListener(this);
        ll_order_layout.setOnClickListener(this);

        //卖家布局
        view_include_layout = currentView.findViewById(R.id.view_include_layout);
        ll_left_layout = (LinearLayout) currentView.findViewById(R.id.ll_left_layout);
        ll_right_layout = (LinearLayout) currentView.findViewById(R.id.ll_right_layout);
        ll_sell_order_layout = (LinearLayout) currentView.findViewById(R.id.ll_sell_order_layout);
        ll_left_layout.setOnClickListener(this);
        ll_right_layout.setOnClickListener(this);
        ll_sell_order_layout.setOnClickListener(this);

        tv_buy_order = (TextView) currentView.findViewById(R.id.tv_buy_order);
        tv_sell_order = (TextView) currentView.findViewById(R.id.tv_sell_order);
        tv_left_round = (TextView) currentView.findViewById(R.id.tv_left_round);
        tv_right_round = (TextView) currentView.findViewById(R.id.tv_right_round);
        img_order_menu = (ImageView) currentView.findViewById(R.id.img_order_menu);
        img_sell_order_menu = (ImageView) currentView.findViewById(R.id.img_sell_order_menu);

        advertManager = new AdvertManagerImpl(getActivity(), this);
        orderTrade = new OrderTradeImpl(getActivity(), this);
//        getTodayResource();
//        getuUserDetailsCount();
        getAdvert();
    }
    
    private void getAdvert()
    {
    	/** 广告位 */
        String url = RequestUrl.getInstance(getActivity())
                .getUrl_getAdvertById(getActivity(), "20");
        advertManager.getIAdvert(url, Constants.INTERFACE_getAdvertById);
    }

    public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams)
    {
        currentView.setLayoutParams(layoutParams);
    }

    public FrameLayout.LayoutParams getCurrentViewParams()
    {
        return (LayoutParams) currentView.getLayoutParams();
    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        switch (v.getId())
        {
            case R.id.layout_login:// 登陆
                if (Tools.isLogin(getActivity()))
                {
                    i = new Intent(getActivity(), MyInfoActivity.class);
                }
                startActivity(i);
                break;

            case R.id.ll_search_layout:// 搜索资源
            case R.id.rl_ziyuan:
                i = new Intent(getActivity(), ResourceSearchActivity.class);
                startActivity(i);
                break;
            case R.id.ll_buy_layout:// 我要求购
                i = new Intent(getActivity(), FindFoodActivity.class);
                startActivity(i);
                break;

            case R.id.share_layout:// 分享到钢圈
                i = new Intent(getActivity(), SteelCircleActivity.class);
                i.putExtra("DATA", Constants.STEEL_CIRCLE);
                startActivity(i);
                break;

            case R.id.menu_layout:
                EventBus.getDefault().post("", "main_slidingPaneLayout");
                break;
            case R.id.tv_sell_layout://卖家
//                if (!tag)
//                {
                    tv_sell_layout.setBackgroundResource(R.drawable.sell_down);
                    tv_sell_layout.setTextColor(getResources().getColor(R.color.font_white_one));
                    tv_buyer_layout.setBackgroundResource(R.drawable.buyers_up);
                    tv_buyer_layout.setTextColor(getResources().getColor(R.color.shouye_red_color));
                    tag = true;
//                }
                setViewVisible();
                break;

            case R.id.tv_buyer_layout://买家
//                if (tag)
//                {
                    tv_sell_layout.setBackgroundResource(R.drawable.sell_up);
                    tv_sell_layout.setTextColor(getResources().getColor(R.color.shouye_red_color));
                    tv_buyer_layout.setBackgroundResource(R.drawable.buyers_down);
                    tv_buyer_layout.setTextColor(getResources().getColor(R.color.font_white_one));
                    tag = false;
//                }
                setViewVisible();
                break;
            case R.id.ll_order_layout://订单中心
                if (Tools.isLogin(getActivity()))
                {
//                    i = new Intent(getActivity(), OrderCentreActivity.class);
                    i = new Intent(getActivity(), OrderCenterMergeActivity.class);
                }

                startActivity(i);
                break;
            case R.id.ll_left_layout://同城报价
                if (Tools.isLogin(getActivity()))
                {
                    //如果没有设置偏好 就要跳转
                    if (!SharePreferenceUtil.getBoolean(getActivity(), Constants.USER_SETTING_FLAG))
                    {
                        i = new Intent(getActivity(), RemindSettingActivity.class);
                    } else
                    {
                        i = new Intent(getActivity(), LocalQuotedPriceActivity.class);
                    }
                }
                startActivity(i);
                break;
            case R.id.ll_right_layout://更多商机
                if (Tools.isLogin(getActivity()))
                {
                    i = new Intent(getActivity(), OpportunityMoreActivity.class);
                }
                startActivity(i);
                break;
            case R.id.ll_sell_order_layout://卖家订单中心
                if (Tools.isLogin(getActivity()))
                {
                    i = new Intent(getActivity(), SellOrderCenterActivity.class);
                }
                startActivity(i);
                break;
            default:
                break;
        }

    }

    /**
     * 拉取今日资源数目
     */
    private void getTodayResource()
    {
        String url = RequestUrl.getInstance(getActivity()).getUrl_TodayResourceCount(getActivity(), "0", "0");
        orderTrade.getTodayResourceCount(url, Constants.INTERFACE_todayResourceCount);
    }

    /**
     * 拉取今日买家、卖家、以及同城 和 更多的数目
     */
    private void getuUserDetailsCount()
    {
        String pinzhongName = "";
        //pinzhongName
        String pinzhongNameTemp = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_LOCAL_ZHONGLEI);
        //重新处理一下
        if(!TextUtils.isEmpty(pinzhongNameTemp)){
            String[] attr = pinzhongNameTemp.split(",");
            for(String s:attr){
                if(s.equals("建筑用钢")){
                    pinzhongName += "01" +",";
                }
                if(s.equals("热轧板卷")){
                    pinzhongName += "02" +",";
                }
                if(s.equals("中厚板")){
                    pinzhongName += "03" +",";
                }
                if(s.equals("冷轧板卷")){
                    pinzhongName += "04" +",";
                }
                if(s.equals("涂镀")){
                    pinzhongName += "05" +",";
                }
                if(s.equals("管材")){
                    pinzhongName += "06" +",";
                }
                if(s.equals("型材")){
                    pinzhongName += "07" +",";
                }
                if(s.equals("其他钢材")){
                    pinzhongName += "08" +",";
                }
                if(s.equals("不锈钢")){
                    pinzhongName += "09" +",";
                }
                if(s.equals("优特钢")){
                    pinzhongName += "10" +",";
                }
                if(s.equals("钢坯")){
                    pinzhongName += "11" +",";
                }
                if(s.equals("品种钢")){
                    pinzhongName += "12" +",";
                }
            }
            if(!TextUtils.isEmpty(pinzhongName)){
                pinzhongName = pinzhongName.substring(0,pinzhongName.length()-1);
            }
        }
        String cities = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_CITYS);
        String url = RequestUrl.getInstance(getActivity()).getUrl_UserDetailsCount(getActivity(), cities, pinzhongName);
        orderTrade.getUserDetailsCount(url, Constants.INTERFACE_userDetailsCount);
//        Log.e("今日资源数:", url);
    }

    private void setViewVisible()
    {
        if (tag)
        {//卖家界面
            ll_all_layout.setVisibility(View.GONE);
            view_include_layout.setVisibility(View.VISIBLE);
        } else
        {//买家界面
            ll_all_layout.setVisibility(View.VISIBLE);
            view_include_layout.setVisibility(View.GONE);
        }

    }

    @Subscriber(tag = "main_data")
    private void reSetListView(String str)
    {
        if ("login".equals(str))
        {
//			getpublishListView();
        } else if ("loginOut".equals(str))
        {
//			layout_prompt.setVisibility(View.VISIBLE);
//			homePageAdapter.refreshList(false);
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
//        buyCenter.finishRequest();
        advertManager.finishRequest();
        orderTrade.finishRequest();
        EventBus.getDefault().unregister(this);
        getActivity().unregisterReceiver(mReceiver);

    }

    @Override
    public void updateView(BaseData data)
    {
        if ("deal.todayResourceCount".equals(data.getCmd()))//首页今日资源数量
        {
            TodayResourceCountData tadayData = (TodayResourceCountData) data;
            tv_data.setText(tadayData.getTodayResourceCount());
        } else if ("user.userDetailsCount".equals(data.getCmd()))//买家和卖家订单中心显示数字，同城和更多的显示数字
        {
            userData = (UserDetailsCountData) data;
            showShouYe();
        }
    }

    private void showShouYe()
    {
        int buyOrder = 0;
        int buySuccessOrder = 0;
        int userFastStanBuyCount = 0;
        int userStanBuyCount = 0;
        int buyEval = 0;

        int sellOrder = 0;
        int sellSuccessOrder = 0;
        int sellEval = 0;

        if (userData != null && userData.getData() != null)
        {
            //买家订单:buySuccessOrder + userFastStanBuyCount + userStanBuyCount - buyEval
            if (!TextUtils.isEmpty(userData.getData().getBuySuccessOrder()))
            {
                buySuccessOrder = Integer.parseInt(userData.getData().getBuySuccessOrder());
            }
            if (!TextUtils.isEmpty(userData.getData().getUserFastStanBuyCount()))
            {
                userFastStanBuyCount = Integer.parseInt(userData.getData().getUserFastStanBuyCount());
            }

            if (!TextUtils.isEmpty(userData.getData().getUserStanBuyCount()))
            {
                userStanBuyCount = Integer.parseInt(userData.getData().getUserStanBuyCount());
            }
            if (!TextUtils.isEmpty(userData.getData().getBuyEval()))
            {
                buyEval = Integer.parseInt(userData.getData().getBuyEval());
            }

            buyOrder = buySuccessOrder + userFastStanBuyCount + userStanBuyCount - buyEval;

            if (buyOrder <= 0)
            {
                tv_buy_order.setVisibility(View.GONE);
                img_order_menu.setBackgroundResource(R.drawable.order_shouye_nopoint);
            } else if (buyOrder > 0 && buyOrder < 100)
            {
                tv_buy_order.setVisibility(View.VISIBLE);
                tv_buy_order.setText(buyOrder + "");
                img_order_menu.setBackgroundResource(R.drawable.order_shouye_point);
            } else if (buyOrder > 100)
            {
                tv_buy_order.setVisibility(View.VISIBLE);
                tv_buy_order.setText("99+");
                img_order_menu.setBackgroundResource(R.drawable.order_shouye_point);
            }

            //卖家订单:sellSuccessOrder - sellEval
            if (!TextUtils.isEmpty(userData.getData().getSellSuccessOrder()))
            {
                sellSuccessOrder = Integer.parseInt(userData.getData().getSellSuccessOrder());
            }
            if (!TextUtils.isEmpty(userData.getData().getSellEval()))
            {
                sellEval = Integer.parseInt(userData.getData().getSellEval());
            }
            sellOrder = sellSuccessOrder - sellEval;
            if (sellOrder <= 0)
            {
                tv_sell_order.setVisibility(View.GONE);
                img_sell_order_menu.setBackgroundResource(R.drawable.order_shouye_nopoint);
            } else if (sellOrder > 0 && sellOrder < 100)
            {
                tv_sell_order.setVisibility(View.VISIBLE);
                tv_sell_order.setText(sellOrder + "");
                img_sell_order_menu.setBackgroundResource(R.drawable.order_shouye_point);
            } else if (sellOrder > 100)
            {
                tv_sell_order.setVisibility(View.VISIBLE);
                tv_sell_order.setText("99+");
                img_sell_order_menu.setBackgroundResource(R.drawable.order_shouye_point);
            }

            //同城
            tv_left_round.setText(userData.getData().getAreaStanCount());
            //更多
            tv_right_round.setText(userData.getData().getStanCount());
        }

    }

    /**
     * 退出登陆后 需要修改首页买家和卖家订单的数字显示
     */
    @Subscriber(tag = "mainFragment_view")
    private void reSetView(String str)
    {
        tv_buy_order.setVisibility(View.GONE);
        img_order_menu.setBackgroundResource(R.drawable.order_shouye_nopoint);
        tv_sell_order.setVisibility(View.GONE);
        img_sell_order_menu.setBackgroundResource(R.drawable.order_shouye_nopoint);
        //同城
        tv_left_round.setText("");
        //更多
        tv_right_round.setText("");
    }

    /**
     * 登录成功后 需要修改首页买家和卖家订单的数字显示
     */
    @Subscriber(tag = "Fragment_loginsuccess")
    private void reSetView_loginSuccess(String str)
    {
        getuUserDetailsCount();
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

    @Override
    public void dealAdvert(AdvertData data)
    {
        final List<Items> items = data.getData().getItems();
        /** 间隔时间 */
        time = Integer.parseInt(data.getData().getScrollSeconds()) * 1000;
        /** 图片 url数组 */
        String[] imageUrl = new String[items.size()];
        for (int i = 0; i < items.size(); i++)
        {
            imageUrl[i] = items.get(i).getPath();
        }
        /** 默认图片 */
        int[] adsId =
                {R.drawable.banner, R.drawable.left_menu_bg};
        banner.start(getActivity(), imageUrl, adsId, time, llDots,
                R.drawable.downpoint,
                R.drawable.uppoint);
        isLoad = true;
        banner.setCustomGalleryOnItemClickListener(new CustomGalleryOnItemClickListener()
        {

            @Override
            public void onItemClick(int curIndex)
            {
                Intent intent = new Intent(getActivity(), AdvertActivity.class);
                intent.putExtra("URL", items.get(curIndex).getUrl());
                intent.putExtra("TITLE", items.get(curIndex).getDepict());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getTodayResource();
        if (Tools.isLogin(getActivity()))
        {
            getuUserDetailsCount();
        }
        // if (banner != null)
        // {
        // banner.startTimer();
        // }
//		if (Tools.isLogin(getActivity()))
//		{
//			getpublishListView();
//		}
    }

}

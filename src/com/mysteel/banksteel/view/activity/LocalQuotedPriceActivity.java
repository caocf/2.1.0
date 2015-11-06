package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageCustomData;
import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.LocalPriceAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/** 
 * 同城报价
 * @author  作者 wushaoge 
 * @date 创建时间：2015年7月29日08:52:58  
 */
public class LocalQuotedPriceActivity extends SwipeBackActivity implements
OnClickListener, XListView.IXListViewListener, IBuyView{

		private Context mContext;
	
		private XListView xListView;
		private LocalPriceAdapter adapter;
		private ProgressBar progressBar;
	
		private String pinzhongName = ""; //选择的品种名称
		private String citys = ""; //选择的3个城市名
		private String page = "1";
		private String size = "10";
		 
		private BuyImpl buyImpl;
		private PageCustomData pageCustomData;
		private ArrayList<DatasEntity> datas;


		private String ipCity = "false";


		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_local_price);
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
			tvTitle.setText("同城报价");
			backLayout.setOnClickListener(this);
			tvRightText.setText("设置");
			tvRightText.setVisibility(View.VISIBLE);
			tvRightText.setOnClickListener(this);
			
			progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

			xListView = (XListView) findViewById(R.id.lv_listview);
			xListView.setPullLoadEnable(false);
			xListView.setXListViewListener(this);
			xListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					if (position <= 0 || position > datas.size()) {
						return;
					}
					Intent intent = new Intent(mContext,
							SellSourceDetailActivity.class);
					intent.putExtra("datasEntity", datas.get(position - 1));
					intent.putExtra("fromFlag", "localquot");
					LogUtils.e(position + "");
					startActivity(intent);
				}
			});
			/*xListView.setOnScrollListener(new OnScrollListener() {
	            
	            @Override
	            public void onScrollStateChanged(AbsListView view, int scrollState) {
	                   
	            }
	           
	            @Override
	            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	                    if(firstVisibleItem>0){
	                            //如果第一个可见的item的position大于0，显示按钮
	                	    	xListView.setPullLoadEnable(true);
	                    }else{
	                	    	xListView.setPullLoadEnable(false);
	                    }
	            }
			});*/

		}

		private void initData()
		{
			//pinzhongName
			String pinzhongNameTemp = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_LOCAL_ZHONGLEI);
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
			
			citys = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_CITYS);

			if(TextUtils.isEmpty(citys)){
				ipCity = "true";
			}else{
				ipCity = "false";
			}

			adapter = new LocalPriceAdapter(this,null);
			xListView.setAdapter(adapter);

			getRequestData();
		}

		@Subscriber(tag = "remindsettingactivity")
		private void remindSetting(String flag){
			pinzhongName = "";
			citys = "";
			page = "1";
			initData();
		}

		
		/**
		 * 拉取数据
		 */
		private void getRequestData(){
			buyImpl = new BuyImpl(this);
			String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, pinzhongName, "", citys, ipCity, page, size);
			LogUtils.e(url);
			buyImpl.getLocalQuotedBuy(url, Constants.INTERFACE_customList);
		}
		

		@Subscriber(tag = "sellsourcedetailactivity")
		private void setFenleiName(boolean flag)
		{
			if(flag){
				datas = new ArrayList<PageCustomData.PaginationEntity.DatasEntity>();
				String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, pinzhongName, "", citys, ipCity, page, size);
				buyImpl.getLocalQuotedBuy(url, Constants.INTERFACE_customList);
			}
		}
		
		@Override
		public void onClick(View v) {
	        switch (v.getId())
	        {
	            case R.id.menu_layout:// 返回结束
	                finish();
	                break;
	            case R.id.tv_title_right_text:// 设置
					mContext.startActivity(new Intent(mContext, RemindSettingActivity.class));
					break;
	            default:
	                break;
	        }
	    }

		/**
		 * 下拉刷新
		 */
		@Override
		public void onRefresh() {
			String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, pinzhongName, "", citys,ipCity, page, size);
			buyImpl.getLocalQuotedBuy(url, Constants.INTERFACE_customList);
		}


		@Override
		public void onLoadMore() {

			if (TextUtils.isEmpty(pageCustomData.getPagination().getPagenum())
					|| TextUtils.isEmpty(pageCustomData.getPagination().getPagenum()))
			{
				return;
			}

			if (Integer.parseInt(page) >= Integer.parseInt(pageCustomData.getPagination().getPagenum()))
			{
				onLoad();
				Tools.showToast(this, "已经到最后一页!");
				return;
			}
			page = Integer.parseInt(page) + 1 + "";

			String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, pinzhongName, "", citys,ipCity, page, size);
			buyImpl.getLocalQuotedBuy(url, Constants.INTERFACE_customList);
		}

		
		private void onLoad()
		{
			xListView.stopLoadMore();
			xListView.stopRefresh();
			xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
					Locale.CHINESE).format(new Date()));
		}


		@Override
		public void updateView(BaseData data) {
			PageCustomData temp = (PageCustomData)data;
			this.pageCustomData = (PageCustomData)data;

			if (temp.getPagination().getDatas()!=null&&temp.getPagination().getDatas().size()>0)
			{
				xListView.setPullLoadEnable(true);
				if (Integer.parseInt(pageCustomData.getPagination().getCount())<=10)
				{
					//xListView.hideFoot();
					xListView.setPullLoadEnable(false);
				}
				if ("1".equals(page))
				{
					datas = (ArrayList<DatasEntity>) pageCustomData.getPagination().getDatas();
				} else
				{
					datas.addAll(pageCustomData.getPagination().getDatas());
				}
				adapter.reSetListView(datas);
			}else{
				if ("1".equals(page))
				{
					xListView.setPullLoadEnable(false);
					Tools.showToast(mContext, "暂无数据!");
				}
			}
			onLoad();
		}


		@Override
		public void isShowDialog(boolean flag) {
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
		protected void onDestroy()
		{
			super.onDestroy();
			buyImpl.finishRequest();
		}
		
}

package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageCustomData;
import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.LocalPriceAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 更多商机
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class OpportunityMoreActivity extends SwipeBackActivity implements
		IListViewInterface, IXListViewListener, IBuyView,OnClickListener
{
	Context mContext;

	private XListView xListView;
	private ProgressBar progressBar;
	private LocalPriceAdapter adapter;
	
	private RelativeLayout rl_parent; //父类view
	
	private String page = "1";
	private String size = "10";
	private BuyImpl buyImpl;
	private PageCustomData pageCustomData;
	private ArrayList<DatasEntity> datas;
	
	private String dafenlei = "";
	private String cunhuodi = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opportunity_more);
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
		tvTitle.setText("更多商机");
		backLayout.setOnClickListener(this);
		
		imRightImage.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shaixuan_white));
		ViewGroup.LayoutParams pm = imRightImage.getLayoutParams();
		pm.width = Tools.dip2px(mContext,25);
		pm.height = Tools.dip2px(mContext,25);
		imRightImage.setLayoutParams(pm);
		imRightImage.setVisibility(View.VISIBLE);
		imRightImage.setOnClickListener(this);
		
		xListView = (XListView) findViewById(R.id.lv_listview);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
//		xListView.setPullLoadEnable(false);
		xListView.setXListViewListener(this);
		rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
		

		xListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (position <= 0 || position > datas.size()) {
					return;
				}
				Intent intent = new Intent(OpportunityMoreActivity.this,
						SellSourceDetailActivity.class);
				intent.putExtra("datasEntity", datas.get(position - 1));
				intent.putExtra("fromFlag", "opportunitymore");
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

		adapter = new LocalPriceAdapter(this,null);
		xListView.setAdapter(adapter);
		xListView.hideFoot();
		getRequestData();
	}
	
	/**
	 * 拉取数据
	 */
	private void getRequestData(){
		buyImpl = new BuyImpl(this);
		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, dafenlei, "", cunhuodi, "false", page, size);
		LogUtils.e(url);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.menu_layout:// 返回结束
	            finish();
	            break;
			case R.id.share_imgbtn:// 筛选
				startActivity(new Intent(mContext,OpportunityScreeningActivity.class));
				break;
			default:
				break;
		}
	}
	
	@Subscriber(tag = "dafenlei")
	private void getDafenlei(String dafenlei)
	{
		this.dafenlei = dafenlei;
	}
	
	@Subscriber(tag = "cunhuodi")
	private void getCunhuodi(String cunhuodi)
	{
		this.cunhuodi = cunhuodi;
	}
	
	@Subscriber(tag = "closeback")
	private void getCloseBack(String str)
	{
		//返回查找
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, dafenlei, "", cunhuodi,"false", page, size);
		LogUtils.e(url);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}

	@Override
	public void updateView(BaseData data)
	{
		PageCustomData temp = (PageCustomData)data;
		this.pageCustomData = (PageCustomData)data;

		if (temp.getPagination().getDatas()!=null&&temp.getPagination().getDatas().size()>0)
		{
//			xListView.setPullLoadEnable(true);

//			if (Integer.parseInt(pageCustomData.getPagination().getPagenum())<=10)
//			{
//				xListView.hideFoot();
////				xListView.setPullLoadEnable(false);
//			}
			if (Integer.parseInt(pageCustomData.getPagination().getCount())<=10)
			{
				//xListView.hideFoot();
				xListView.setPullLoadEnable(false);
			}

			if ("1".equals(page))
			{
				datas = (ArrayList<DatasEntity>) pageCustomData.getPagination().getDatas();
//				xListView.setVisibility(View.VISIBLE);
			} else
			{
				datas.addAll(pageCustomData.getPagination().getDatas());
			}
			adapter.reSetListView(datas);
		}else{
			if ("1".equals(page))
			{
				Tools.showToast(mContext, "暂无数据!");
//				xListView.setPullLoadEnable(false);
				adapter.reSetListView(new ArrayList<DatasEntity>());
			}
		}
		onLoad();
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
	public void onRefresh()
	{
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, dafenlei, "", cunhuodi,"false", page, size);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}

	@Override
	public void onLoadMore()
	{
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

		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, dafenlei, "", cunhuodi,"false", page, size);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}

	private void onLoad()
	{
		xListView.stopLoadMore();
		xListView.stopRefresh();
		xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}

	@Override
	public void stopUpdate()
	{

	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		buyImpl.finishRequest();
	}

	
}

package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ResourceManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SourceSearchAdapter;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 查看某个人的资源
 * 
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */

public class ResourceOtherActivity extends SwipeBackActivity implements OnClickListener,IListViewInterface, XListView.IXListViewListener,IResourceManagerView {

	private Context mContext;

	private XListView xListView;
	private ProgressBar progressBar;
	private SourceSearchAdapter adapter;

	private LinearLayout ll_back; //返回
	private RelativeLayout l_search; //搜索layout
	private EditText et_search; //搜索内容
	private TextView tv_title_right; //取消/搜索
	private boolean rightFlag = false;


	private ResourceManagerImpl resourceManagerImpl;
	private SearchResourceData searchResourceData;
	private ArrayList<SearchResourceData.Data.Datas> datas;

	private String friendPhone = "";

	private String page = "1";
	private String size = "10";
	private String keyWord = "";
	private String sort = "";
	private String order = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource_other);
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

		friendPhone = getIntent().getStringExtra("friendPhone");

		xListView = (XListView) findViewById(R.id.lv_listview);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		xListView.setPullLoadEnable(false);
		xListView.setXListViewListener(this);

		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		l_search = (RelativeLayout) findViewById(R.id.l_search);
		et_search = (EditText) findViewById(R.id.et_search);

		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setOnClickListener(this);

		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(s.toString())) {
					rightFlag = true;
					tv_title_right.setText("搜索");
				} else {
					rightFlag = false;
					tv_title_right.setText("取消");
				}

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
	private void getRequestData(){
		resourceManagerImpl = new ResourceManagerImpl(this);
		String url = RequestUrl.getInstance(this).getUrl_searchResource(this, friendPhone, keyWord, sort, order, page, size);
		LogUtils.e(url);
		resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.ll_back:
				// 返回菜单
				finish();
				break;
			case R.id.tv_title_right:
				rightSearch();
				break;
			default:
				break;
		}
	}

	/**
	 * 右侧搜索
	 */
	private void rightSearch(){
		if(rightFlag){
			search();
		}else{
			this.finish();
		}
	}


	/**
	 * 根据关键字查找
	 */
	public void search(){
		keyWord = et_search.getText().toString();
		resourceManagerImpl = new ResourceManagerImpl(this);
		String url = RequestUrl.getInstance(this).getUrl_searchResource(this, friendPhone, keyWord, sort, order, page, size);
		resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
	}

	@Override
	public void stopUpdate() {

	}

	@Override
	public void matchResource(SearchResourceData data) {

	}

	@Override
	public void searchResource(SearchResourceData data) {
			searchResourceData = data;

			if (data.getData().getDatas() != null && data.getData().getDatas().size()>0)
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
			}else{
				if ("1".equals(page))
				{
					Tools.showToast(mContext, "暂无数据");
					xListView.setPullRefreshEnable(true);
					xListView.setPullLoadEnable(false);
					xListView.setAdapter(adapter);
					adapter.reSetListView(new ArrayList<SearchResourceData.Data.Datas>());
					//xListView.setVisibility(View.GONE);
				}
			}
			onLoad();
	}

	@Override
	public void updateView(BaseData data) {

	}

	@Override
	public void isShowDialog(boolean flag) {
		if (flag)
		{
			if(page.equals("1")){
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(100);
			}
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		String url = RequestUrl.getInstance(this).getUrl_searchResource(this, friendPhone, keyWord, sort, order, page, size);
		resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMore() {
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

		String url = RequestUrl.getInstance(this).getUrl_searchResource(this, friendPhone, keyWord, sort, order, page, size);
		resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
	}


	private void onLoad()
	{
		xListView.stopLoadMore();
		xListView.stopRefresh();
		xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}



	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		resourceManagerImpl.finishRequest();
		adapter.getOrderTrade().finishRequest();
	}
}


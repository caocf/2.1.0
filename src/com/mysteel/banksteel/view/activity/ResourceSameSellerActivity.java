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
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageCustomData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.LocalPriceAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 卖家的同类资源
 * 
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class ResourceSameSellerActivity extends SwipeBackActivity implements
		IListViewInterface, XListView.IXListViewListener, IBuyView,OnClickListener
{

	private Context mContext;

	private XListView xListView;
	private ProgressBar progressBar;
	private LocalPriceAdapter adapter;

	private BuyImpl buyImpl;
	private PageCustomData pageCustomData;
	private ArrayList<PageCustomData.PaginationEntity.DatasEntity> datas;

	private String pinzhongName = ""; //选择的品种名称
	private String citys = ""; //选择的3个城市名
	private String pinlei = "";
	private String caizhi = "";
	private String guige = "";
	private String pinpai = "";
	private String cunhuodi = "";

	private String page = "1";
	private String size = "10";

	private String fromFlag = ""; //localquot 同城报价  opportunitymore更多商机
	private PageCustomData.PaginationEntity.DatasEntity datasEntity;

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

		xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (position <= 0 || position > datas.size()) {
					return;
				}
				Intent intent = new Intent(mContext,
						SellSourceDetailActivity.class);
				intent.putExtra("datasEntity", datas.get(position - 1));
				LogUtils.e(position + "");
				startActivity(intent);
			}
		});

		datasEntity = (PageCustomData.PaginationEntity.DatasEntity)getIntent().getSerializableExtra("datas");
		if(null == datasEntity){
			datasEntity = new PageCustomData().new PaginationEntity().new DatasEntity();
		}

		if("localquot".equals(fromFlag)){
			String pinzhongNameTemp = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_PINZHONG);
			//重新处理一下
			if(!TextUtils.isEmpty(pinzhongNameTemp)) {
				String[] attr = pinzhongNameTemp.split(",");
				for (String s : attr) {
					if (s.equals("建筑用钢")) {
						pinzhongName += "01" + ",";
					}
					if (s.equals("热轧板卷")) {
						pinzhongName += "02" + ",";
					}
					if (s.equals("中厚板")) {
						pinzhongName += "03" + ",";
					}
					if (s.equals("冷轧板卷")) {
						pinzhongName += "04" + ",";
					}
					if (s.equals("涂镀")) {
						pinzhongName += "05" + ",";
					}
					if (s.equals("管材")) {
						pinzhongName += "06" + ",";
					}
					if (s.equals("型材")) {
						pinzhongName += "07" + ",";
					}
					if (s.equals("其他钢材")) {
						pinzhongName += "08" + ",";
					}
					if (s.equals("不锈钢")) {
						pinzhongName += "09" + ",";
					}
					if (s.equals("优特钢")) {
						pinzhongName += "10" + ",";
					}
					if (s.equals("钢坯")) {
						pinzhongName += "11" + ",";
					}
					if (s.equals("品种钢")) {
						pinzhongName += "12" + ",";
					}
				}
				if (!TextUtils.isEmpty(pinzhongName)) {
					pinzhongName = pinzhongName.substring(0, pinzhongName.length() - 1);
				}
				citys = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_CITYS);
			}
		}
		pinlei = datasEntity.getBreed();
		caizhi = datasEntity.getMaterial();
		guige = datasEntity.getSpec();
		pinpai = datasEntity.getBrand();
		cunhuodi = datasEntity.getCity();

	}

	private void initData()
	{
		adapter = new LocalPriceAdapter(this,null);
		xListView.setAdapter(adapter);
		getRequestData();
	}

	/**
	 * 拉取数据
	 */
	private void getRequestData(){
		buyImpl = new BuyImpl(this);
		LogUtils.e("品类:"+pinlei);
		LogUtils.e("城市:"+cunhuodi);
		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, "", pinlei, citys, "", "", "", page, size);
		LogUtils.e(url);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}


	@Override
	public void onClick(View v) {
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
	public void stopUpdate() {

	}

	@Override
	public void updateView(BaseData data) {
		PageCustomData temp = (PageCustomData)data;
		this.pageCustomData = (PageCustomData)data;

		if (temp.getPagination().getDatas()!=null&&temp.getPagination().getDatas().size()>0)
		{
			if ("1".equals(page))
			{
				datas = (ArrayList<PageCustomData.PaginationEntity.DatasEntity>) pageCustomData.getPagination().getDatas();
				xListView.setVisibility(View.VISIBLE);
				xListView.setPullRefreshEnable(true);
				xListView.setPullLoadEnable(true);
			} else
			{
				datas.addAll(pageCustomData.getPagination().getDatas());
			}
			adapter.reSetListView(datas);
		}else{
			if ("1".equals(page))
			{
				Tools.showToast(mContext, "暂无数据!");
				xListView.setVisibility(View.VISIBLE);
				xListView.setPullRefreshEnable(true);
				xListView.setPullLoadEnable(false);
				adapter = new LocalPriceAdapter(this,null);
				xListView.setAdapter(adapter);
			}
		}
		onLoad();
	}

	@Override
	public void isShowDialog(boolean flag) {

	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		//String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, pinzhongName, pinlei, citys, caizhi, guige, cunhuodi, page, size);
		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, "", pinlei, citys, "", "", "", page, size);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}

	/**
	 * 上拉加载
	 */
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

		String url = RequestUrl.getInstance(this).getUrl_getCustomList(mContext, "", pinlei, citys, "", "", "", page, size);
		buyImpl.getOpportunityMoreBuy(url, Constants.INTERFACE_customList);
	}

	private void onLoad()
	{
		xListView.stopLoadMore();
		xListView.stopRefresh();
		xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}
}


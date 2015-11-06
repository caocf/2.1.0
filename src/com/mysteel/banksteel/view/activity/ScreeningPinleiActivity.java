package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.adapters.ScreeningPinleiAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 筛选条件之品类查找
 * 
 * @author:wushaoge
 * @date：2015年7月28日09:26:22
 */
public class ScreeningPinleiActivity extends BaseDialogActivity implements
IListViewInterface, IXListViewListener, IBuyView{

		private Context mContext;
		
		private SteelType stype;
	
		private LinearLayout ll_all; //选择全部 
		private LinearLayout ll_close; //返回
		private XListView lv_listview;
		private ProgressBar progressBar;
		
		private ScreeningPinleiAdapter adapter;
		
		private IBuyCenter steelListImpl;
		private SteelData steelData; // 子分类钢铁数据
		 
	
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pop_shaixuan1);
			mContext = this;
			
			
			stype = (SteelType) getIntent().getSerializableExtra(
					"parentSteel");
			stype = (stype != null) ? stype : (new SteelType());
			
			initViews();
			initData();
		}
		
		
		/**
		 * 初始化页面组件
		 */
		//@Override
		protected void initViews()
		{
			//super.initViews();
			WindowManager m = getWindowManager();    
	        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高    
	        LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值    
//	        p.height = (int) (d.getHeight() * 0.8);   //高度设置为屏幕的1.0   
//	        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8   
	        p.alpha = 1.0f; //设置本身透明度  
	        p.dimAmount = 0.5f; //设置黑暗度  
	        getWindow().setAttributes(p); //设置生效  
			getWindow().setGravity(Gravity.RIGHT); //设置靠右对齐 
			
			ll_all = (LinearLayout)findViewById(R.id.ll_all);
			ll_all.setVisibility(View.GONE);
			ll_close = (LinearLayout)findViewById(R.id.ll_close);
			lv_listview = (XListView)findViewById(R.id.lv_listview);
			lv_listview.setPullRefreshEnable(false); //下拉刷新
			lv_listview.setPullLoadEnable(false); //上啦刷新
			progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		}

		private void initData()
		{
			
			adapter = new ScreeningPinleiAdapter(mContext, null);
			lv_listview.setAdapter(adapter);
			
			steelListImpl = new BuyImpl(this);
			String url = RequestUrl.getInstance(this)
					.getUrl_queryChildBreedsByParentId(this, stype.getId());
			steelListImpl.getSteelData(url,Constants.INTERFACE_queryChildBreedsByParentId);
			
			ll_close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
			lv_listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					EventBus.getDefault().post(steelData.getSons().get(position-1), "screening2activity");
					finish();
				}
			});
			
		}


		@Override
		public void updateView(BaseData data) {
			steelData = (SteelData) data;
			adapter.updateData(steelData.getSons());
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
		public void onRefresh() {
			if (steelListImpl == null)
			{
				return;
			}
			String url = RequestUrl.getInstance(this)
					.getUrl_queryChildBreedsByParentId(this, stype.getId());
			steelListImpl.getSteelData(url,Constants.INTERFACE_queryChildBreedsByParentId);
			onLoad();
		}

		private void onLoad()
		{
			lv_listview.stopLoadMore();
			lv_listview.stopRefresh();
			lv_listview.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
					Locale.CHINESE).format(new Date()));
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void stopUpdate() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		protected void onDestroy()
		{
			super.onDestroy();
			steelListImpl.finishRequest();
		}
}

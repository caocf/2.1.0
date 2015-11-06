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
import com.mysteel.banksteel.entity.SpecsAndMaterialData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.ScreeningGuigeAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 筛选条件之规格查找
 * 
 * @author:wushaoge
 * @date：2015年7月28日09:26:22
 */
public class ScreeningGuigeActivity extends BaseDialogActivity implements
IListViewInterface, IXListViewListener, IBuyView{

		private Context mContext;
		
		private String childSteelId;// 子品种ID
	
		private LinearLayout ll_all; //选择全部 
		private LinearLayout ll_close; //返回
		private XListView lv_listview;
		private ProgressBar progressBar;
		
		private ScreeningGuigeAdapter adapter;
		
		private IBuyCenter buyCenterImpl;
		
		private List<String> list; 
	
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pop_shaixuan1);
			mContext = this;
			
			childSteelId = getIntent().getStringExtra("childId");// breedId
			
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
			
			buyCenterImpl = new BuyImpl(this);
			
			ll_all = (LinearLayout)findViewById(R.id.ll_all);
			ll_close = (LinearLayout)findViewById(R.id.ll_close);
			lv_listview = (XListView)findViewById(R.id.lv_listview);
			lv_listview.setPullRefreshEnable(false); //下拉刷新
			lv_listview.setPullLoadEnable(false); //上啦刷新
			progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		}

		private void initData()
		{
			
			adapter = new ScreeningGuigeAdapter(mContext, null);
			lv_listview.setAdapter(adapter);
			
			getMaterial();
			
			ll_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post("", "screening4activity");
					finish();
				}
			});
			
			ll_close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
			lv_listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					EventBus.getDefault().post(list.get(position-1), "screening4activity");
					finish();
				}
			});
			
		}
		
		/** 获取规格的方法 */
		private void getMaterial()
		{
			String url = RequestUrl.getInstance(this).getUrl_getSpecsByBreedId(
					this, childSteelId);
			buyCenterImpl.getSpecsAndMaterial(url,
					Constants.INTERFACE_getSpecsByBreedId);
		}


		@Override
		public void updateView(BaseData data) {
			SpecsAndMaterialData smData = (SpecsAndMaterialData) data;
			list = (ArrayList<String>) smData.getData();
			if (list == null || list.size() == 0){
				Tools.showToast(this, "暂时没有可选规格！");
			}else{
				adapter.updateData(list);
			}
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
			
		}


		@Override
		public void stopUpdate() {
			
		}
		
		@Override
		protected void onDestroy()
		{
			super.onDestroy();
			buyCenterImpl.finishRequest();
		}
}

package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.entity.SteelData.SteelBean;
import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.adapters.ScreeningFenleiAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选条件之大分类查找
 * 
 * @author:wushaoge
 * @date：2015年7月28日09:26:22
 */
public class ScreeningFenleiActivity extends BaseDialogActivity implements IBuyView{

		private Context mContext;
	
		private LinearLayout ll_all; //选择全部
		private LinearLayout ll_close; //返回
		private XListView lv_listview;
		private ScreeningFenleiAdapter adapter;
		
		private List<SteelType> list = new ArrayList<SteelType>();
		
		/** 在我们选择完大分类后把选择的大分类中的品类的第一个传回去 因为筛选条件中品类不能为空  */
		private IBuyCenter steelListImpl;
		private SteelData steelData; // 子分类钢铁数据
		
		private String fromFLag = ""; //判断是否从哪边跳过来
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pop_shaixuan1);
			mContext = this;
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
			
			fromFLag = getIntent().getStringExtra("fromFLag");
			
			ll_all = (LinearLayout)findViewById(R.id.ll_all);
			if(TextUtils.isEmpty(fromFLag)){
				ll_all.setVisibility(View.GONE);
			}else{
				ll_all.setVisibility(View.VISIBLE);
			}
			ll_close = (LinearLayout)findViewById(R.id.ll_close);
			lv_listview = (XListView)findViewById(R.id.lv_listview);
			lv_listview.setPullRefreshEnable(false); //下拉刷新
			lv_listview.setPullLoadEnable(false); //上啦刷新
			
			//list.add(new SteelType("00", "全部"));
			list.add(new SteelType("01", "建筑用钢"));
			list.add(new SteelType("02", "热轧板卷"));
			list.add(new SteelType("03", "中厚板"));
			list.add(new SteelType("04", "冷轧板卷"));
			list.add(new SteelType("05", "涂镀"));
			list.add(new SteelType("06", "管材"));
			list.add(new SteelType("07", "型材"));
			list.add(new SteelType("08", "其他钢材"));
			list.add(new SteelType("09", "不锈钢"));
			list.add(new SteelType("10", "优特钢"));
			list.add(new SteelType("11", "钢坯"));
			list.add(new SteelType("12", "品种钢"));
		}

		private void initData()
		{
			steelListImpl = new BuyImpl(this);
			
			adapter = new ScreeningFenleiAdapter(mContext, list);
			lv_listview.setAdapter(adapter);
			
			ll_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post("", "screening1activity2");
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
					EventBus.getDefault().post(list.get(position-1), "screening1activity");
					String url = RequestUrl.getInstance(mContext)
							.getUrl_queryChildBreedsByParentId(mContext, list.get(position-1).getId());
					steelListImpl.getSteelData(url,Constants.INTERFACE_queryChildBreedsByParentId);
				}
			});
			
		}


		@Override
		public void updateView(BaseData data) {
			SteelBean bean = null;
			steelData = (SteelData) data;
			if(steelData.getSons()!=null&&steelData.getSons().size()>0){
				bean = steelData.getSons().get(0);
			}
			EventBus.getDefault().post(bean, "screening1activity_son");
			finish();
		}


		@Override
		public void isShowDialog(boolean flag) {
			
		}
		
		@Override
		protected void onDestroy()
		{
			super.onDestroy();
			steelListImpl.finishRequest();
		}
}

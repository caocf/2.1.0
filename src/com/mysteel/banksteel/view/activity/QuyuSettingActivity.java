package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CitysData;
import com.mysteel.banksteel.entity.CitysData.CityBean;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.HotContentAdapter;
import com.mysteel.banksteel.view.adapters.LetterGroupAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.LetterList;
import com.mysteel.banksteel.view.ui.LetterList.OnTouchingLetterChangedListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 筛选条件之品牌产地查找
 * 
 * @author:wushaoge
 * @date：2015年7月28日09:26:22
 */
public class QuyuSettingActivity extends BaseActivity implements OnClickListener,
OnTouchingLetterChangedListener, IBuyView{

		private Context mContext;
		private LayoutInflater inflater;
		
		
		/** 热门内容视图 */
		private View hotContentView;
		private GridView gvHotContent;
		
		/** 所有城市 */
		private ListView lvBrandCity;
		private LetterList lvLetter;
		private TextView tvLetterTip;
		private ProgressBar progressBar;
		
		private HotContentAdapter hotContentAdapter;
		private LetterGroupAdapter letterGroupAdapter;
		
		
		private IBuyCenter cityListImpl;
		private CitysData citys; // 接受服务器响应的城市数据

		private Map<String, Integer> letterPositionMap = new HashMap<String, Integer>(); // 字母在list中的位置和字母的对应关系
		private List<String> dataList = new ArrayList<String>(); // 用于adapter适配的城市列表数据
		
		
		private TextView tv_city1;
		private TextView tv_city2;
		private TextView tv_city3;
		
		String city1 = "";
		String city2 = "";
		String city3 = "";
	
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_quyu_setting);
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
			tvTitle.setText("选择城市");
			backLayout.setOnClickListener(this);
			
			/** 加载热门内容视图 */
			inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			hotContentView = inflater.inflate(R.layout.hot_content, null);
			gvHotContent = (GridView) hotContentView
					.findViewById(R.id.gv_hot_content);
			
			cityListImpl = new BuyImpl(this);
			
			lvBrandCity = (ListView) findViewById(R.id.lv_brand_city);
			lvLetter = (LetterList) findViewById(R.id.lv_letter);
			tvLetterTip = (TextView) findViewById(R.id.tv_letter_tip);
			progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
			
			
			tv_city1 = (TextView) findViewById(R.id.tv_city1);
			tv_city2 = (TextView) findViewById(R.id.tv_city2);
			tv_city3 = (TextView) findViewById(R.id.tv_city3);
			
			tv_city1.setOnClickListener(this);
			tv_city2.setOnClickListener(this);
			tv_city3.setOnClickListener(this);
		}

		private void initData()
		{
			//获取用户选择的城市
			String selCitys = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_CITYS_DETAIL);
			if(!TextUtils.isEmpty(selCitys)){
				String[] attrStr = selCitys.split(",");
				if(attrStr.length==1){
					city1 = attrStr[0];
				}
				if(attrStr.length==2){
					city1 = attrStr[0];
					city2 = attrStr[1];
				}
				if(attrStr.length==3){
					city1 = attrStr[0];
					city2 = attrStr[1];
					city3 = attrStr[2];
				}
			}
			//test
//			city1 = "";
//			city2 = "";
//			city3 = "";

			isHide();
			
			lvLetter.setOnTouchingLetterChangedListener(this);
			
			/** 头部热门城市的点击事件 */
			gvHotContent.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					String citysTemp = citys.getHotCitys().get(position);
					selCity(citysTemp.substring(0,citysTemp.length()-6)+"市"+citysTemp.substring(citysTemp.length()-6,citysTemp.length()));
				}
			});
			
			/** list的点击事件 */
			lvBrandCity.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					if (Tools.isLetter(dataList.get(position - 1)))
					{
						return;
					} else
					{
						selCity(dataList.get(position - 1));
					}
				}
			});
			
			cityListImpl = new BuyImpl(this);

			// 页面不同，拼接不同的请求地址
			String url = "";
			// 城市
			url = RequestUrl.getInstance(this).getUrl_queryAreaByType(this);
			LogUtils.e(url);
			cityListImpl.getCityList(url, Constants.INTERFACE_getCitys);
			
		}
		
		
		/**
		 * 选择城市
		 */
		private void selCity(String cityName){
			if(cityName.equals(city1)||cityName.equals(city2)||cityName.equals(city3)){ //选择了相同城市
				return;
			}
			if(TextUtils.isEmpty(city1)&&TextUtils.isEmpty(city2)&&TextUtils.isEmpty(city3)){
				city1 = cityName;
			}else if(!TextUtils.isEmpty(city1)&&TextUtils.isEmpty(city2)&&TextUtils.isEmpty(city3)){
				city2 = cityName;
			}else if(!TextUtils.isEmpty(city1)&&!TextUtils.isEmpty(city2)&&TextUtils.isEmpty(city3)){
				city3 = cityName;
			}else if(!TextUtils.isEmpty(city1)&&!TextUtils.isEmpty(city2)&&!TextUtils.isEmpty(city3)){
				Tools.showToast(mContext, "最多只能选择3个城市哦！");
				return;
			}
			
			isHide();
		}
		
		
		/**
		 * 删除城市
		 * @param flag
		 */
		private void delCity(int flag){
			List<String> list = new ArrayList<String>();
			list.add(city1);
			list.add(city2);
			list.add(city3);
			if(flag==1){
				city1 = "";
				list.remove(0);
			}
			if(flag==2){
				city2 = "";
				list.remove(1);
			}
			if(flag==3){
				city3 = "";
				list.remove(2);
			}
			list.add("");
			for(int i = 0;i<list.size();i++){
				if(i == 0){
					city1 = list.get(i);
				}
				if(i == 1){
					city2 = list.get(i);
				}
				if(i == 2){
					city3 = list.get(i);
				}
			}
			isHide();
		}
		
		/**
		 * 显示隐藏
		 */
		private void isHide(){
			if(!TextUtils.isEmpty(city1)){
				tv_city1.setText(city1.substring(0,city1.length()-6));
				tv_city1.setVisibility(View.VISIBLE);
			}else{
				tv_city1.setVisibility(View.INVISIBLE);
			}
			 
			if(!TextUtils.isEmpty(city2)){
				tv_city2.setText(city2.substring(0,city2.length()-6));
				tv_city2.setVisibility(View.VISIBLE);
			}else{
				tv_city2.setVisibility(View.INVISIBLE);
			}
			
			if(!TextUtils.isEmpty(city3)){
				tv_city3.setText(city3.substring(0,city3.length()-6));
				tv_city3.setVisibility(View.VISIBLE);
			}else{
				tv_city3.setVisibility(View.INVISIBLE);
			}
		}
		
		

		@Override
		public void updateView(BaseData data) {
			citys = (CitysData) data;

			// 上面的热门内容
			hotContentAdapter = new HotContentAdapter(this, citys.getHotCitys(),true);
			gvHotContent.setAdapter(hotContentAdapter);
			addHotContentToList(citys.getHotCitys());

			// 下面的列表数据
			sort(); // 排序
			int position = 1;
			if (citys != null && citys.getData() != null)
			{
				for (int i = 0; i < citys.getData().size(); i++)
				{
					dataList.add(citys.getData().get(i).getPy()); // 添加字母到list中
					/**
					 * 钢厂返回的json数据字段是brands。城市返回的json数据字段是citys,所以这里需要判断，
					 * 根据页面不同取不同的数据。
					 */
					// 城市
					dataList.addAll(citys.getData().get(i).getCitys()); // 添加城市到list中
					letterPositionMap.put(citys.getData().get(i).getPy(), position);
					position = dataList.size() + 1;
				}
			}
			letterGroupAdapter = new LetterGroupAdapter(this, dataList,true);
			lvBrandCity.setAdapter(letterGroupAdapter);
		}
		
		/**
		 * 根据热门内容数量和item高度计算gridview的高度，并作为header添加到list中
		 * 如果要做修改，请参照布局文件中GridView垂直间距（20）和Item的高度（50）
		 * 
		 * @param list
		 */
		private void addHotContentToList(List<String> list)
		{
			int itemCount = 0;
			if (list != null)
			{
				itemCount = list.size();
				int rows = (itemCount + 3 - 1) / 3;
				int height = rows * 50 + (rows - 1) * 20;
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, Tools.dip2px(this, height));
				gvHotContent.setLayoutParams(params);
				lvBrandCity.addHeaderView(hotContentView);
			}
		}
		
		/**
		 * 对list按照拼音字母排序，服务器返回的数据ABCDE字母是乱序的。
		 */
		public void sort()
		{
			Collections.sort(citys.getData(), new Comparator<CityBean>()
			{
				@Override
				public int compare(CityBean lhs, CityBean rhs)
				{
					if (lhs.getPy() != null && rhs.getPy() != null)
					{
						return lhs.getPy().compareToIgnoreCase(rhs.getPy());
					} else
					{
						return 0;
					}
				}
			});
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
		public void onTouchingLetterChanged(String letter) {
			tvLetterTip.setText(letter);
			tvLetterTip.setVisibility(View.VISIBLE);
			if (!"☆".equals(letter) && letterPositionMap.get(letter) != null)
			{
				lvBrandCity.setSelection(letterPositionMap.get(letter));

			} else if ("☆".equals(letter))
			{
				lvBrandCity.setSelection(0);
			}
		}


		@Override
		public void onTouchingLetterUp() {
			tvLetterTip.setVisibility(View.GONE);
		}
		
		@Override
		protected void onDestroy()
		{
			super.onDestroy();
			cityListImpl.finishRequest();
		}


		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
				case R.id.menu_layout:
					closeBack();
					break;
				case R.id.tv_city1:
					delCity(1);
					break;
				case R.id.tv_city2:
					delCity(2);
					break;
				case R.id.tv_city3:
					delCity(3);
					break;
				default:
					break;
			}
		}
		
		private void closeBack(){
			List<String> list = new ArrayList<String>();
			list.add(city1);
			list.add(city2);
			list.add(city3);
			EventBus.getDefault().post(list, "quyusettingactivity");
			finish();
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK){
				closeBack();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}

}

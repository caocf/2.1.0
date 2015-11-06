package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.ResourceManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SearchResourceData;
import com.mysteel.banksteel.entity.SearchResourceData.Data.Datas;
import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.SourceSearchAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.interfaceview.IResourceManagerView;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.SwipeListView;
import com.mysteel.banksteel.view.ui.SwipeListView.IXListViewListener;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 搜索资源
 * 
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
@SuppressWarnings({ "unused","unchecked", "rawtypes" })
public class ResourceSearchActivity extends SwipeBackActivity implements
		IListViewInterface, IXListViewListener, IBuyView,OnClickListener,IResourceManagerView
{
	Context mContext;

	private SwipeListView xListView;
	private ProgressBar progressBar;
	private SourceSearchAdapter adapter;
	
	private RelativeLayout rl_parent; //父类view
	
	private LinearLayout ll_back; //返回
	private RelativeLayout l_search; //搜索layout
	private TextView tv_search; //搜索内容l
	
	private LinearLayout ll_sort_head;
	private TextView tv_zonghe; //综合
	private TextView tv_shijian; //时间
	private RelativeLayout rl_jiage; //价格
	private TextView tv_jiage; //价格
	private ImageView iv_jiantou; //价格箭头
	private TextView tv_xiaoliang; //销量
	private TextView tv_pingjia; //评价
	private RelativeLayout rl_screening; //筛选
	private ImageView iv_screening; //筛选
	
	private ImageView tv_zonghe_line;
	private ImageView tv_shijian_line;
	private ImageView tv_jiage_line;
	private ImageView tv_xiaoliang_line;
	private ImageView tv_pingjia_line;
	
	private boolean isCover = false; //价格遮罩
	
	
	private RelativeLayout rl_main_cover; //是否遮掩
	private LinearLayout ll_sort_main; //弹出框
	private LinearLayout ll_sort_hight; //从高到低排序
	private LinearLayout ll_sort_low; //从低到高排序
	
	private ResourceManagerImpl resourceManagerImpl;
	private SearchResourceData searchResourceData;
	private ArrayList<Datas> datas;
	
	private String dafenlei = "";
	private String pinlei = "";
	private String caizhi = "";
	private String guige = "";
	private String pinpai = "";
	private String cunhuodi = "";
	
	private String page = "1";
	private String size = "10";
	private String keyWord = "";
	private String sort = "";
	private String order = "";
	
	private boolean tag = true; //true表示默认的数据  false表示筛选后的

	/** 大分类选择 */
	private SteelType stype;
	/** 品类选择 */
	private SteelData.SteelBean bean;
	
	private static enum SelectTextViewType
	{
		zonghe, shijian, jiage, xiaoliang, pingjia
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resouce_search);
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

		xListView = (SwipeListView) findViewById(R.id.lv_listview);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		xListView.setPullLoadEnable(false);
		xListView.setXListViewListener(this);

		rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
		ll_back  = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		l_search = (RelativeLayout) findViewById(R.id.l_search);
		l_search.setOnClickListener(this);
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);
		ll_sort_head  = (LinearLayout) findViewById(R.id.ll_sort_head);
		tv_zonghe = (TextView) findViewById(R.id.tv_zonghe);
		tv_zonghe.setOnClickListener(this);
		tv_shijian = (TextView) findViewById(R.id.tv_shijian);
		tv_shijian.setOnClickListener(this);
		tv_jiage = (TextView) findViewById(R.id.tv_jiage);
		iv_jiantou = (ImageView) findViewById(R.id.iv_jiantou);
		
		rl_jiage = (RelativeLayout) findViewById(R.id.rl_jiage);
		rl_jiage.setOnClickListener(this);
		
		tv_xiaoliang = (TextView) findViewById(R.id.tv_xiaoliang);
		tv_xiaoliang.setOnClickListener(this);
		tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);
		tv_pingjia.setOnClickListener(this);
		rl_screening = (RelativeLayout) findViewById(R.id.rl_screening);
		//rl_screening.setOnClickListener(this);
		iv_screening = (ImageView) findViewById(R.id.iv_screening);
		iv_screening.setOnClickListener(this);
		
		tv_zonghe_line = (ImageView) findViewById(R.id.tv_zonghe_line);
		tv_shijian_line = (ImageView) findViewById(R.id.tv_shijian_line);
		tv_jiage_line = (ImageView) findViewById(R.id.tv_jiage_line);
		tv_xiaoliang_line = (ImageView) findViewById(R.id.tv_xiaoliang_line);
		tv_pingjia_line = (ImageView) findViewById(R.id.tv_pingjia_line);
		
		rl_main_cover = (RelativeLayout) findViewById(R.id.rl_main_cover);
		ll_sort_main = (LinearLayout) findViewById(R.id.ll_sort_main);
		ll_sort_hight = (LinearLayout) findViewById(R.id.ll_sort_hight);
		ll_sort_hight.setOnClickListener(this);
		ll_sort_low = (LinearLayout) findViewById(R.id.ll_sort_low);
		ll_sort_low.setOnClickListener(this);
		
		//初始化隐藏遮罩
		isCover();

		xListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
//				if (position <= 0 || position > datas.size()) {
//					return;
//				}
				if ((position - 2 ) < 0 || (position - 2 ) > datas.size())
		        {
		            return;
		        }
				Intent intent = new Intent(ResourceSearchActivity.this,
						ResouceDetailActivity.class);
				intent.putExtra("datas", datas.get(position - 2));
				startActivity(intent);
			}
		});
		
	}

	private void initData()
	{
		adapter = new SourceSearchAdapter(this,xListView.getRightViewWidth());
		xListView.setAdapter(adapter);
		xListView.setisNeedSwipe(true);
		getRequestData();
	}
	
	/**
	 * 拉取数据
	 */
	private void getRequestData(){
		tv_search.setText(keyWord);
		resourceManagerImpl = new ResourceManagerImpl(this);
		String url = RequestUrl.getInstance(this).getUrl_searchResource(this, keyWord, sort, order, page, size);
		LogUtils.e(url);
		resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.ll_back:// 返回结束
	            finish();
	            break;
			case R.id.tv_search: //search
				Intent intent = new Intent(ResourceSearchActivity.this,
	        			ResourceKeyWordActivity.class);
				intent.putExtra("tv_search",tv_search.getText().toString());
				startActivity(intent);
				break;
			case R.id.tv_zonghe:// 綜合
				isCover = false;
				//this.keyWord = "";
				changeTextColor(SelectTextViewType.zonghe);
				break;
			case R.id.tv_shijian:// 时间
				isCover = false;
				changeTextColor(SelectTextViewType.shijian);
				break;
			case R.id.rl_jiage:// 价格
				isCover = !isCover;
				changeTextColor(SelectTextViewType.jiage);
				break;
			case R.id.tv_xiaoliang:// 销量
				isCover = false;
				changeTextColor(SelectTextViewType.xiaoliang);
				break;
			case R.id.tv_pingjia:// 评价
				isCover = false;
				changeTextColor(SelectTextViewType.pingjia);
				break;
			case R.id.iv_screening:// 筛选
				isCover = false;
				showPopWindow(mContext,rl_parent);
				break;
			case R.id.ll_sort_hight: //从高到低排序
				isCover = false;
				//this.keyWord = "";
				priceSort(0);
				break;
			case R.id.ll_sort_low: //从高到低排序
				isCover = false;
				//this.keyWord = "";
				priceSort(1);
				break;
			default:
				break;
		}
		isCover();
	}


	@Subscriber(tag = "dafenleitype")
	private void getDafenleiType(SteelType stype)
	{
		this.stype = stype;
	}

	@Subscriber(tag = "pinleitype")
	private void getPinleiType(SteelData.SteelBean bean)
	{
		this.bean = bean;
	}

	@Subscriber(tag = "dafenlei")
	private void getDafenlei(String dafenlei)
	{
		this.dafenlei = dafenlei;
	}
	
	@Subscriber(tag = "pinlei")
	private void getPinlei(String pinlei)
	{
		this.pinlei = pinlei;
	}
	
	@Subscriber(tag = "caizhi")
	private void getCaizhi(String caizhi)
	{
		this.caizhi = caizhi;
	}
	
	@Subscriber(tag = "guige")
	private void getGuige(String guige)
	{
		this.guige = guige;
	}
	
	@Subscriber(tag = "pinpai")
	private void getPinpai(String pinpai)
	{
		this.pinpai = pinpai;
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
		isCover = false;
		reduction();
		this.keyWord = "";
		page = "1";
		String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi, page, size);
		LogUtils.e(url);
		resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
	}
	
	
	@Subscriber(tag = "resourceheywordactivity_keyword")
	private void getSearchFromKeyWord(String keyWord)
	{
		if(!TextUtils.isEmpty(keyWord)){
			isCover = false;
			isCover();
			reduction();

			tag = true;
			page = "1";
			this.keyWord = keyWord;
			getRequestData();
		}else{
			isCover = false;
			this.keyWord = "";
			changeTextColor(SelectTextViewType.zonghe);
		}

	}
	
	/**
	 * 把排序头部初始化样子
	 */
	private void reduction(){
		tv_zonghe.setTextColor(this.getResources().getColor(R.color.main_imred));
		tv_shijian.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_jiage.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_xiaoliang.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_pingjia.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		iv_jiantou.setVisibility(View.GONE);
		

		tv_zonghe_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
		tv_shijian_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_jiage_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_xiaoliang_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_pingjia_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
	}
	
	/**
	 * 是否显示遮罩
	 */
	private void isCover(){
		//设置价格的遮罩
		if(isCover){
			rl_main_cover.setBackgroundColor(this.getResources().getColor(R.color.translucent));
			ll_sort_main.setVisibility(View.VISIBLE);
			ll_sort_main.setClickable(true);
		}else{
			rl_main_cover.setBackgroundColor(this.getResources().getColor(R.color.font_white_one));
			ll_sort_main.setVisibility(View.GONE);
			ll_sort_main.setClickable(false);
		}

		//设置整个背景半透明遮罩
		/*if(isSXCover){
			rl_parent.setBackgroundColor(mContext.getResources().getColor(R.color.translucent));
			rl_main_cover.setBackgroundColor(this.getResources().getColor(R.color.translucent));
		}*/
	}
	
	
	/**
	 * 显示筛选
	 * @param context
	 * @param view
	 */
	private void showPopWindow(Context context,View view){
		Intent i = new Intent(context, ScreeningActivity.class);
		i.putExtra("stype",stype);
		i.putExtra("bean",bean);
		i.putExtra("caizhi", this.caizhi);
		i.putExtra("guige",this.guige);
		i.putExtra("brand",this.pinpai);
		i.putExtra("city", this.cunhuodi);
		startActivity(i);
	}
	
	
	/**
	 * 价格排序
	 * @param flag 0表示正序 1表示倒叙
	 */
	private void priceSort(int flag){
		sort = "price";
		page = "1";
		if(flag == 0){
			order = "desc";
		}else{
			order = "asc";
		}

		if(searchResourceData == null){
			tag = true;
		}

		if(tag){
			String url = RequestUrl.getInstance(this).getUrl_searchResource(this, keyWord, sort, order, page, size);
			LogUtils.e(url);
			resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
		}else{
			String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi,sort, order, page, size);
			LogUtils.e(url);
			resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
		}
	}
	
	
	/**
	 * 排序选择
	 * @param type
	 */
	private void changeTextColor(SelectTextViewType type){
		//this.keyWord = "";
		
		tv_zonghe.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_shijian.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_jiage.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_xiaoliang.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		tv_pingjia.setTextColor(this.getResources().getColor(R.color.main_font_gray));
		iv_jiantou.setVisibility(View.GONE);
		

		tv_zonghe_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_shijian_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_jiage_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_xiaoliang_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		tv_pingjia_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_xian));
		
		if(type==SelectTextViewType.zonghe){
			//tag = true;
			tv_zonghe.setTextColor(this.getResources().getColor(R.color.main_imred));
			tv_zonghe_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
			
			page = "1";
			sort = "";
			order = "";

			if(searchResourceData == null){
				tag = true;
			}

			if(tag){
				String url = RequestUrl.getInstance(this).getUrl_searchResource(this, keyWord, sort, order, page, size);
				resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
			}else{
				String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi, page, size);
				resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
			}

		}
		if(type==SelectTextViewType.shijian){
			tv_shijian.setTextColor(this.getResources().getColor(R.color.main_imred));
			tv_shijian_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
		}
		if(type==SelectTextViewType.jiage){
			tv_jiage.setTextColor(this.getResources().getColor(R.color.main_imred));
			iv_jiantou.setVisibility(View.VISIBLE);
			tv_jiage_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
		}
		if(type==SelectTextViewType.xiaoliang){
			tv_xiaoliang.setTextColor(this.getResources().getColor(R.color.main_imred));
			tv_xiaoliang_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
		}
		if(type==SelectTextViewType.pingjia){
			tv_pingjia.setTextColor(this.getResources().getColor(R.color.main_imred));
			tv_pingjia_line.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.screening_sanjiao));
		}
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
	public void onRefresh()
	{
		if(tag){
			String url = RequestUrl.getInstance(this).getUrl_searchResource(this, keyWord, sort, order, page, size);
			resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
		}else{
			String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi, page, size);
			resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
		}
		
	}

	
	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMore()
	{
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

		if(tag){
			String url = RequestUrl.getInstance(this).getUrl_searchResource(this, keyWord, sort, order, page, size);
			resourceManagerImpl.getSearchResouce(url, Constants.INTERFACE_searchResource);
		}else{
			String url = RequestUrl.getInstance(this).getUrl_matchResource(mContext, dafenlei, pinlei, caizhi, guige, pinpai, cunhuodi, page, size);
			resourceManagerImpl.getMatchResource(url, Constants.INTERFACE_matchResource);
		}
	}
	
	

	private void onLoad()
	{
		xListView.stopLoadMore();
		xListView.stopRefresh();
		xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
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
				adapter.reSetListView(new ArrayList<Datas>());
				//xListView.setVisibility(View.GONE);
			}
		}
		onLoad();
	}
	
	
	
	@Override
	public void matchResource(SearchResourceData data) {
		searchResourceData = data;
		tag = false;
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
				searchResourceData = null;
				//Tools.showToast(mContext, "搜索不到数据!");
				xListView.setPullRefreshEnable(true);
				xListView.setPullLoadEnable(false);
				xListView.setAdapter(adapter);
				adapter.reSetListView(new ArrayList<Datas>());

				showMatchDialog();
			}
		}
		
		onLoad();
	}

	/**
	 * 未匹配到资源弹窗
	 */
	private void showMatchDialog(){
		String dialogStr = "未匹配到资源\n";
		dialogStr += "品种:"+ pinlei + "\n";
		if(!TextUtils.isEmpty(caizhi)){
			dialogStr += "材质:"+ caizhi + "\n";
		}
		if(!TextUtils.isEmpty(guige)){
			dialogStr += "规格:"+ guige + "\n";
		}
		if(!TextUtils.isEmpty(pinpai)){
			dialogStr += "品牌:"+ pinpai + "\n";
		}
		if(!TextUtils.isEmpty(cunhuodi)){
			dialogStr += "存货地:"+ cunhuodi + "\n";
		}
		dialogStr += "\n是否需要发布求购？";
		ShowDialog dialog = new ShowDialog(mContext, dialogStr, new ShowDialog.ICallBack()
		{
			@Override
			public void requestOK()
			{
				if(null!=stype&&null!=bean){
					Intent intent = new Intent(mContext,BuyDetailInfoActivity.class);
					intent.putExtra(SelectTypeActivity.PARENT_STEEL, stype.getName());
					intent.putExtra(SelectTypeActivity.CHILD_STEEL,pinlei);
					intent.putExtra(SelectTypeActivity.CHILD_ID, stype.getId());
					intent.putExtra("material", caizhi);
					intent.putExtra("spec", guige);
					intent.putExtra("brand", pinpai);
					intent.putExtra("city", cunhuodi);
					startActivity(intent);
				}
			}
			@Override
			public void requestCancle()
			{

			}
		});
		dialog.setcommitBtn("确定");
		dialog.setcancleBtn("取消");
		dialog.show();
	}

	@Override
	public void stopUpdate()
	{

	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		resourceManagerImpl.finishRequest();
		adapter.getOrderTrade().finishRequest();
	}

	

	
}

package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.HotKeyWordContentAdapter;
import com.mysteel.banksteel.view.adapters.MyHistoryAdapter;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 根据关键字搜索
 * 
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class ResourceKeyWordActivity extends SwipeBackActivity implements OnClickListener
{
	Context mContext;

	private ProgressBar progressBar;
	private HotKeyWordContentAdapter keyWordAdapter; //热搜adapter
	private MyHistoryAdapter hisAdapter; //历史记录adapter
	
	private LinearLayout ll_back; //返回
	private RelativeLayout l_search; //搜索layout
	private EditText et_search; //搜索内容
	
	private GridView gv_hot_content; //热搜内容
	private ListView lv_keyword; //关键字列表
	private TextView tv_history_tag; //历史记录
	private TextView tv_show_more; //显示or收缩
	private TextView tv_clean_history; //清除历史记录
	
	private static List<String> keyWordList = new ArrayList<String>();
	private ArrayList<String> strList = new ArrayList<String>(); //搜索历史记录列表
	
	private ImageView iv_sousuo; //搜索
	private TextView tv_title_right; //取消/搜索
	private boolean rightFlag = false;
	
	private String keyWord = ""; //搜索内容
	private String historyStr = ""; //历史搜索记录
	
	private boolean tag = false; //false收缩   true显示
	
	static {
		keyWordList.add("螺纹钢");
		keyWordList.add("盘螺");
		keyWordList.add("线材");
		keyWordList.add("普卷");
		keyWordList.add("热轧开平板");
		keyWordList.add("低合金卷");
		keyWordList.add("花纹卷");
		keyWordList.add("冷轧卷");
		keyWordList.add("冷轧板");
		keyWordList.add("冷轧盒板");
		keyWordList.add("酸洗卷");
		keyWordList.add("镀锌卷");
		keyWordList.add("普板");
		keyWordList.add("低合金板");
		keyWordList.add("碳结板");
		keyWordList.add("容器板");
		keyWordList.add("H型钢");
		keyWordList.add("角钢");
		keyWordList.add("槽钢");
		keyWordList.add("工字钢");
		keyWordList.add("无缝管");
		keyWordList.add("螺旋管");
		keyWordList.add("焊管");
		keyWordList.add("方矩管");
		keyWordList.add("镀锌管");
		keyWordList.add("拉丝材");
		keyWordList.add("工业普圆");
		keyWordList.add("冷镦钢");
		keyWordList.add("合结钢");
		keyWordList.add("硬线");
		keyWordList.add("PC钢");
		keyWordList.add("热轧带钢");
		keyWordList.add("轧硬卷");

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource_keyword);
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

		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		l_search = (RelativeLayout) findViewById(R.id.l_search);
		
		et_search = (EditText) findViewById(R.id.et_search);
		iv_sousuo = (ImageView) findViewById(R.id.iv_sousuo);
		iv_sousuo.setOnClickListener(this);
		et_search.setText(getIntent().getStringExtra("tv_search"));

		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setOnClickListener(this);
		
		gv_hot_content = (GridView) findViewById(R.id.gv_hot_content);
		
		lv_keyword = (ListView) findViewById(R.id.lv_keyword);
		tv_history_tag = (TextView) findViewById(R.id.tv_history_tag);
		
		tv_show_more = (TextView) findViewById(R.id.tv_show_more);
		tv_show_more.setOnClickListener(this);
		
		tv_clean_history = (TextView) findViewById(R.id.tv_clean_history);
		tv_clean_history.setOnClickListener(this);

		if(!TextUtils.isEmpty(et_search.getText().toString())){
			rightFlag = true;
			tv_title_right.setText("搜索");
			et_search.setSelection(et_search.getText().toString().length());
		}else{
			rightFlag = false;
			tv_title_right.setText("取消");
		}
		
		lv_keyword.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				et_search.setText(strList.get(position));
				search();
			}
		});
		
		gv_hot_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				et_search.setText(keyWordList.get(position));
				search();
			}
		});

		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(!TextUtils.isEmpty(s.toString())){
					rightFlag = true;
					tv_title_right.setText("搜索");
				}else{
					rightFlag = false;
					tv_title_right.setText("取消");
				}

			}
		});
	}

	private void initData()
	{
		historyStr = SharePreferenceUtil.getString(mContext, Constants.USER_SEARCH_HISTORY);
		
		keyWordAdapter = new HotKeyWordContentAdapter(mContext, keyWordList);
		gv_hot_content.setAdapter(keyWordAdapter);
		
		//模拟数据
		if(!TextUtils.isEmpty(historyStr)){
			tv_clean_history.setVisibility(View.VISIBLE);
			String[] attrTemp = historyStr.split(",");
			for(String s:attrTemp){
				strList.add(s);
			}
			tv_history_tag.setVisibility(View.VISIBLE);
		}else{
			tv_clean_history.setVisibility(View.GONE);
			tv_history_tag.setVisibility(View.GONE);
		}
		
		showOrHidde();
		
		hisAdapter = new MyHistoryAdapter(mContext);
		hisAdapter.reSetListView(strList);
		lv_keyword.setAdapter(hisAdapter);
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.ll_back:// 返回结束
	            finish();
	            break;
			case R.id.tv_show_more: //显示or收缩
				showOrHidde();
				break;
			case R.id.iv_sousuo:// 搜索
				search();
				break;
			case R.id.tv_title_right:
				rightSearch();
				break;
			case R.id.tv_clean_history: //清除历史记录
				clearHistory();
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
			tv_title_right.setText("搜索");
			search();
		}else{
			tv_title_right.setText("取消");
			EventBus.getDefault().post("", "resourceheywordactivity_keyword");
			this.finish();
		}

	}
	
	
	/**
	 * 根据关键字查找
	 */
	public void search(){
		String searchTemp = et_search.getText().toString();
		if(!TextUtils.isEmpty(searchTemp)){
			LogUtils.e(searchTemp);
			keyWord = searchTemp;
			EventBus.getDefault().post(keyWord, "resourceheywordactivity_keyword");
			//保存到历史记录里面去
			if(!TextUtils.isEmpty(historyStr)){
				String[] attrSplit = historyStr.split(",");
				if(attrSplit.length<50){ //最多保存50条历史记录
					//查找搜索的内容是否已经存在
					if(!Arrays.asList(attrSplit).contains(keyWord)&&!keyWordList.contains(keyWord)){
						historyStr += "," + keyWord;
					}
				}
			}else{
				//查找搜索的内容是否已经存在
				if(!keyWordList.contains(keyWord)){
					historyStr += keyWord;
				}
			}
			SharePreferenceUtil.setValue(mContext, Constants.USER_SEARCH_HISTORY,
					historyStr);// 保存历史记录
			
			super.finish();
		}else{
			Tools.showToast(mContext, "搜索内容不能为空!");
		}
	}
	
	/**
	 * 显示or收缩
	 */
	private void showOrHidde(){
		if(tag){
			keyWordAdapter.reSetListView(keyWordList);
			tv_show_more.setText("收起");
		}else{
			keyWordAdapter.reSetListView(keyWordList.subList(0, 15));
			tv_show_more.setText("显示更多");
		}
		tag = !tag;
	}
	
	/**
	 * 清除历史记录
	 */
	private void clearHistory(){
		lv_keyword.setVisibility(View.GONE);
		tv_clean_history.setVisibility(View.GONE);
		SharePreferenceUtil.setValue(mContext, Constants.USER_SEARCH_HISTORY,"");//清除历史记录
		//清除完之后展开热搜内容
		tag = true;
		showOrHidde();
		tv_history_tag.setVisibility(View.GONE);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	
}

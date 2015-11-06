package com.mysteel.banksteel.view.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CitysData;
import com.mysteel.banksteel.entity.CitysData.CityBean;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.HotContentAdapter;
import com.mysteel.banksteel.view.adapters.LetterGroupAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.LetterList;
import com.mysteel.banksteel.view.ui.LetterList.OnTouchingLetterChangedListener;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

/**
 * 城市和品牌/产地 页面，两个页面共用一个activity
 * 
 * @author:huoguodong
 * @date：2015-5-5 下午8:22:42
 */
public class BrandCityActivity extends BaseActivity implements
		OnTouchingLetterChangedListener, IBuyView
{

	private LayoutInflater inflater;

	private EditText etSearch;
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

	private String currentScreen;

	private IBuyCenter cityListImpl;
	private CitysData citys; // 接受服务器响应的城市数据

	private Map<String, Integer> letterPositionMap = new HashMap<String, Integer>(); // 字母在list中的位置和字母的对应关系
	private List<String> dataList = new ArrayList<String>(); // 用于adapter适配的城市列表数据

	public static final String RETURN_DATA = "returnData";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material_brand_city);

		Intent intent = getIntent();
		currentScreen = intent.getStringExtra(Constants.NEXT_SCREEN_TAG);

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

		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		etSearch = (EditText) findViewById(R.id.et_search_value);
		/** 加载热门内容视图 */
		hotContentView = inflater.inflate(R.layout.hot_content, null);
		gvHotContent = (GridView) hotContentView
				.findViewById(R.id.gv_hot_content);

		lvBrandCity = (ListView) findViewById(R.id.lv_brand_city);
		lvLetter = (LetterList) findViewById(R.id.lv_letter);
		tvLetterTip = (TextView) findViewById(R.id.tv_letter_tip);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

		menuBtn.setVisibility(View.GONE);
		imBack.setVisibility(View.VISIBLE);
		if (BuyDetailInfoActivity.SCREEN_TAG_BRAND.equals(currentScreen))
		{
			tvTitle.setText(R.string.select_brand);
			etSearch.setHint(R.string.select_brand);
		} else
		{
			tvTitle.setText(R.string.select_city);
			etSearch.setHint(R.string.select_city);
		}

		lvLetter.setOnTouchingLetterChangedListener(this);
		backLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		/** 头部热门城市的点击事件 */
		gvHotContent.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Intent intent = new Intent();
				intent.putExtra(RETURN_DATA, citys.getHotCitys().get(position));
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		/** list的点击事件 */
		lvBrandCity.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				if (BuyDetailInfoActivity.SCREEN_TAG_BRAND
						.equals(currentScreen))
				{// 品牌 产地不需要头部
					if (Tools.isLetter(dataList.get(position)))
					{
						return;
					} else
					{
						Intent intent = new Intent();
						intent.putExtra(RETURN_DATA, dataList.get(position));
						setResult(RESULT_OK, intent);
						finish();
					}
				} else
				{
					if (Tools.isLetter(dataList.get(position - 1)))
					{
						return;
					} else
					{
						Intent intent = new Intent();
						intent.putExtra(RETURN_DATA, dataList.get(position - 1));
						setResult(RESULT_OK, intent);
						finish();
					}
				}

			}
		});
	}

	private void initData()
	{
		cityListImpl = new BuyImpl(this);

		// 页面不同，拼接不同的请求地址
		String url = "";
		if (BuyDetailInfoActivity.SCREEN_TAG_BRAND.equals(currentScreen))
		{
			// 钢厂
			url = RequestUrl.getInstance(this).getUrl_getFactorysByBreedId(
					this,
					getIntent().getStringExtra(
							BuyDetailInfoActivity.CHILD_STEEL_ID));
		} else
		{
			// 城市
			url = RequestUrl.getInstance(this).getUrl_getCitys(this);
		}
		cityListImpl.getCityList(url, Constants.INTERFACE_getCitys);
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
	 * 根据字母查询城市
	 */
	@Override
	public void onTouchingLetterChanged(String letter)
	{
		tvLetterTip.setText(letter);
		tvLetterTip.setVisibility(View.VISIBLE);
		if (!"☆".equals(letter) && letterPositionMap.get(letter) != null)
		{
			if (BuyDetailInfoActivity.SCREEN_TAG_BRAND.equals(currentScreen))
			{
				lvBrandCity.setSelection(letterPositionMap.get(letter) - 1);
			} else
			{
				lvBrandCity.setSelection(letterPositionMap.get(letter));
			}

		} else if ("☆".equals(letter))
		{
			lvBrandCity.setSelection(0);
		}
	}

	@Override
	public void onTouchingLetterUp()
	{
		tvLetterTip.setVisibility(View.GONE);
	}

	@Override
	public void updateView(BaseData data)
	{
		citys = (CitysData) data;

		// 上面的热门内容
		hotContentAdapter = new HotContentAdapter(this, citys.getHotCitys());
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
				if (BuyDetailInfoActivity.SCREEN_TAG_BRAND
						.equals(currentScreen))
				{
					// 钢厂
					dataList.addAll(citys.getData().get(i).getBrands()); // 添加城市到list中
				} else
				{
					// 城市
					dataList.addAll(citys.getData().get(i).getCitys()); // 添加城市到list中
				}
				letterPositionMap.put(citys.getData().get(i).getPy(), position);
				position = dataList.size() + 1;
			}
		}
		letterGroupAdapter = new LetterGroupAdapter(this, dataList);
		lvBrandCity.setAdapter(letterGroupAdapter);
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
	protected void onDestroy()
	{
		super.onDestroy();
		cityListImpl.finishRequest();
	}
}

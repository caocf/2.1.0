package com.mysteel.banksteel.view.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.adapters.ChildSteelAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 选择品种页面
 * 
 * @author:huoguodong
 * @date：2015-5-5 上午10:38:46
 */
public class SelectTypeActivity extends SwipeBackActivity implements
		IListViewInterface, IXListViewListener, IBuyView
{

	private ImageView ivSteelImage;
	private TextView tvSteelName;
	private XListView xListView;
	private ProgressBar progressBar;

	private IBuyCenter steelListImpl;
	private Steel steel; // 父分类钢铁
	private SteelData steelData; // 子分类钢铁数据
	private ChildSteelAdapter adapter;

	public final static String PARENT_STEEL = "parentSteel";
	public final static String CHILD_STEEL = "childSteel";
	public final static String CHILD_ID = "childId";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_type);

		steel = (Steel) getIntent().getSerializableExtra(
				PublishDemandActivity.PARENT_STEEL);
		steel = (steel != null) ? steel : (new Steel());
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

		ivSteelImage = (ImageView) findViewById(R.id.iv_selected_type_image);
		tvSteelName = (TextView) findViewById(R.id.tv_selected_type_name);
		xListView = (XListView) findViewById(R.id.lv_steel_stype);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		xListView.setPullLoadEnable(false); // 禁用上拉加载更多功能
		xListView.setXListViewListener(this);

		menuBtn.setVisibility(View.GONE);
		imBack.setVisibility(View.VISIBLE);
		tvTitle.setText(R.string.select_type);

		backLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		xListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Intent intent = new Intent(SelectTypeActivity.this,
						BuyDetailInfoActivity.class);
				intent.putExtra(PARENT_STEEL, steel.name);
				intent.putExtra(CHILD_STEEL,
						steelData.getSons().get(position - 1).getName());
				intent.putExtra(CHILD_ID, steelData.getSons().get(position - 1)
						.getId());
				intent.putExtra("material", "");
				intent.putExtra("spec", "");
				intent.putExtra("brand", "");
				intent.putExtra("city", "");
				intent.putExtra("qty", "");
				intent.putExtra("contacter", "");
				intent.putExtra("phone", "");
				startActivity(intent);
			}
		});
	}

	private void initData()
	{
		ivSteelImage.setImageResource(steel.imageId);
		tvSteelName.setText(steel.name);

		adapter = new ChildSteelAdapter(this, null);
		xListView.setAdapter(adapter);

		steelListImpl = new BuyImpl(this);
		String url = RequestUrl.getInstance(this)
				.getUrl_queryChildBreedsByParentId(this, steel.id);
		steelListImpl.getSteelData(url,Constants.INTERFACE_queryChildBreedsByParentId);
	}

	@Override
	public void updateView(BaseData data)
	{
		steelData = (SteelData) data;
		adapter.updateData(steelData.getSons());
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
		if (steelListImpl == null)
		{
			return;
		}
		String url = RequestUrl.getInstance(this)
				.getUrl_queryChildBreedsByParentId(this, steel.id);
		steelListImpl.getSteelData(url,Constants.INTERFACE_queryChildBreedsByParentId);
		onLoad();
	}

	@Override
	public void onLoadMore()
	{
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
		steelListImpl.finishRequest();
	}
}

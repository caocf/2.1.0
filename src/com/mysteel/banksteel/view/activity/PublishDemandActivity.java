package com.mysteel.banksteel.view.activity;

import java.util.List;

import com.mysteel.banksteel.ao.impl.AdvertManagerImpl;
import com.mysteel.banksteel.entity.AdvertData;
import com.mysteel.banksteel.entity.AdvertData.Data.Items;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IAdvertManagerView;
import com.mysteel.banksteel.view.ui.CustomGalleryView;
import com.mysteel.banksteel.view.ui.CustomGalleryView.CustomGalleryOnItemClickListener;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * 我要求购页面
 * 
 * @author:huoguodong
 * @date：2015-5-4 下午6:47:05
 */
public class PublishDemandActivity extends BaseActivity implements
		OnClickListener, IAdvertManagerView
{

	private View constructionSteel; // 建筑用钢
	private View hotRolledCoil; // 热轧卷板
	private View cutDeal; // 中厚板
	private View specificSteel; // 品种钢
	private View plateRoll; // 板卷
	private View proximateMatter; // 型材
	private View otherSteel; // 其他钢材
	private View stainlessSteel; // 不锈钢
	private View billet; // 钢坯
	private View alloyStructual; // 优特钢
	private View steeltube; // 管材
	private View coldCoating; // 冷镀

	// private static Map<String, String> parentNameIdMap;
	public final static String PARENT_STEEL = "parentSteel";

	/** 图片轮播右下角的点 */
	private LinearLayout llDots;
	/** 广告条 */
	private CustomGalleryView banner;
	/** AdvertManagerImpl */
	private AdvertManagerImpl advertManager;
	/** 间隔时间,每隔time秒图片跳动一次 */
	private int time = 3000;

	Steel steel = new Steel();

	/*
	 * static { if (parentNameIdMap != null) { parentNameIdMap = new
	 * HashMap<String, String>(); parentNameIdMap.put("建筑用钢", "01");
	 * parentNameIdMap.put("热轧板卷", "02"); parentNameIdMap.put("中厚板", "03");
	 * parentNameIdMap.put("冷轧板卷", "04"); parentNameIdMap.put("冷镀", "05");
	 * parentNameIdMap.put("管材", "06"); parentNameIdMap.put("型材", "07");
	 * parentNameIdMap.put("其他钢材", "08"); parentNameIdMap.put("不锈钢", "09");
	 * parentNameIdMap.put("优特钢", "10"); parentNameIdMap.put("钢坯", "11");
	 * parentNameIdMap.put("品种钢", "12"); } }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_to_buy);

		initViews();
	}

	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();

		constructionSteel = findViewById(R.id.ll_construction_steel);
		hotRolledCoil = findViewById(R.id.ll_hot_rolled_coil);
		cutDeal = findViewById(R.id.ll_cut_deal);
		specificSteel = findViewById(R.id.ll_specific_steel);
		plateRoll = findViewById(R.id.ll_plate_roll);
		proximateMatter = findViewById(R.id.ll_proximate_matter);
		otherSteel = findViewById(R.id.ll_other_steel);
		stainlessSteel = findViewById(R.id.ll_stainless_steel);
		billet = findViewById(R.id.ll_billet);
		alloyStructual = findViewById(R.id.ll_alloy_structual);
		steeltube = findViewById(R.id.ll_steeltube);
		coldCoating = findViewById(R.id.ll_cold_coating);
		banner = (CustomGalleryView) findViewById(R.id.banner);
		llDots = (LinearLayout) findViewById(R.id.ll_dots);

		menuBtn.setVisibility(View.GONE);
		imBack.setVisibility(View.VISIBLE);
		tvTitle.setText(R.string.woyaoqiugou);

		constructionSteel.setOnClickListener(this);
		hotRolledCoil.setOnClickListener(this);
		cutDeal.setOnClickListener(this);
		specificSteel.setOnClickListener(this);
		plateRoll.setOnClickListener(this);
		proximateMatter.setOnClickListener(this);
		otherSteel.setOnClickListener(this);
		stainlessSteel.setOnClickListener(this);
		billet.setOnClickListener(this);
		alloyStructual.setOnClickListener(this);
		steeltube.setOnClickListener(this);
		coldCoating.setOnClickListener(this);

		backLayout.setOnClickListener(this);

		/** 广告位 */
		advertManager = new AdvertManagerImpl(this);
		String url = RequestUrl.getInstance(this).getUrl_getAdvertById(this,
				"20");
		advertManager.getIAdvert(url, Constants.INTERFACE_getAdvertById);
	}

	@Override
	public void onClick(View v)
	{
		/*
		 * if (!Tools.isLogin(this)) { Intent i = new Intent(this,
		 * LoginActivity.class); startActivity(i); return; }
		 */
		Intent intent = new Intent(this, SelectTypeActivity.class);
//		Steel steel;
		switch (v.getId())
		{
		case R.id.ll_construction_steel:
//			steel = new Steel("01", "建筑用钢", R.drawable.jiancai);
			steel.setId("01");
			steel.setName("建筑用钢");
			steel.setImageId(R.drawable.jiancai);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_hot_rolled_coil:
//			steel = new Steel("02", "热轧板卷", R.drawable.rezhabanjuan);
			steel.setId("02");
			steel.setName("热轧板卷");
			steel.setImageId(R.drawable.rezhabanjuan);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_cut_deal:
//			steel = new Steel("03", "中厚板", R.drawable.zhonghouban);
			steel.setId("03");
			steel.setName("中厚板");
			steel.setImageId(R.drawable.zhonghouban);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_specific_steel:
//			steel = new Steel("12", "品种钢", R.drawable.pingzhonggang);
			steel.setId("12");
			steel.setName("品种钢");
			steel.setImageId(R.drawable.pingzhonggang);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_plate_roll:
//			steel = new Steel("04", "冷轧板卷", R.drawable.banjuan);
			steel.setId("04");
			steel.setName("冷轧板卷");
			steel.setImageId(R.drawable.banjuan);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_proximate_matter:
//			steel = new Steel("07", "型材", R.drawable.xigang);
			steel.setId("07");
			steel.setName("型材");
			steel.setImageId(R.drawable.xigang);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_other_steel:
//			steel = new Steel("08", "其他钢材", R.drawable.qitagangcai);
			steel.setId("08");
			steel.setName("其他钢材");
			steel.setImageId(R.drawable.qitagangcai);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_stainless_steel:
//			steel = new Steel("09", "不锈钢", R.drawable.buxiugang);
			steel.setId("09");
			steel.setName("不锈钢");
			steel.setImageId(R.drawable.buxiugang);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_billet:
//			steel = new Steel("11", "钢坯", R.drawable.gangpi);
			steel.setId("11");
			steel.setName("钢坯");
			steel.setImageId(R.drawable.gangpi);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_alloy_structual:
//			steel = new Steel("10", "优特钢", R.drawable.youtegang);
			steel.setId("10");
			steel.setName("优特钢");
			steel.setImageId(R.drawable.youtegang);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_steeltube:
//			steel = new Steel("06", "管材", R.drawable.guancai);
			steel.setId("06");
			steel.setName("管材");
			steel.setImageId(R.drawable.guancai);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.ll_cold_coating:
//			steel = new Steel("05", "涂镀", R.drawable.lengdu);
			steel.setId("05");
			steel.setName("涂镀");
			steel.setImageId(R.drawable.lengdu);
			intent.putExtra(PARENT_STEEL, steel);
			break;
		case R.id.menu_layout:
			finish();
			return;
		}
		startActivity(intent);
	}

	@Override
	public void updateView(BaseData data)
	{
	}

	@Override
	public void isShowDialog(boolean flag)
	{
	}

	@Override
	public void dealAdvert(AdvertData data)
	{

		time = Integer.parseInt(data.getData().getScrollSeconds()) * 1000;
		final List<Items> items = data.getData().getItems();
		String[] imageUrl = new String[items.size()];
		for (int i = 0; i < items.size(); i++)
		{
			imageUrl[i] = items.get(i).getPath();
		}
		int[] adsId =
		{ R.drawable.banner, R.drawable.left_menu_bg };
		banner.start(this, imageUrl, adsId, time, llDots,
				R.drawable.downpoint,
				R.drawable.uppoint);
		banner.setCustomGalleryOnItemClickListener(new CustomGalleryOnItemClickListener()
		{

			@Override
			public void onItemClick(int curIndex)
			{
				Intent intent = new Intent(PublishDemandActivity.this,
						AdvertActivity.class);
				intent.putExtra("URL", items.get(curIndex).getUrl());
				intent.putExtra("TITLE", items.get(curIndex).getDepict());
				startActivity(intent);
			}
		});
	}
}

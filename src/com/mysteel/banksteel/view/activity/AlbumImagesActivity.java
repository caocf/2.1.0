package com.mysteel.banksteel.view.activity;

import java.util.ArrayList;
import java.util.List;

import com.mysteel.banksteel.entity.ImageBean;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.ScanImages;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.AlbumImagesAdapter;
import com.mysteel.banksteel.view.adapters.AlbumImagesAdapter.OnCheckboxChangedListener;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * @author:huoguodong
 * @date 2015-4-22
 */
public class AlbumImagesActivity extends BaseActivity implements
		OnCheckboxChangedListener
{

	public static final int MOST_PICTURE_COUNT = 1; // 允许用户选择的最大图片数量

	private GridView gridView;
	private AlbumImagesAdapter adapter;

	private List<ImageBean> allImagesList; // 从SD卡中读取的图片的地址
	private static ArrayList<String> selectedImageList;

	private int count; // 用户选择的图片数量
	private Bundle bundle;

	private int columWidht; // GridView的列宽, 像素值

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images);

		columWidht = (Tools.getScreenWidth(this) - Tools.dip2px(
				this, 6)) / 3;

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
		gridView = (GridView) findViewById(R.id.gv_images);

		tvTitle.setText("选择图片");
		tvRightText.setText("完成");
		tvRightText.setTextColor(Color.WHITE);
		tvRightText.setBackgroundColor(Color.parseColor("#48BD23"));
		tvRightText.setVisibility(View.VISIBLE);

		/** 返回按钮点击事件 */
		backLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		/** 完成按钮点击事件 */
		rightLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				Bundle bundle = new Bundle();

				bundle.putStringArrayList(Constants.IMAGE_DATA,
						selectedImageList);
				intent.putExtras(bundle);

				setResult(RESULT_OK, intent);
				finish();
			}
		});

		/** 点击gridview item事件 */
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// view.setAlpha(0.5f);
			}
		});
	}

	private void initData()
	{

		/** receive image count sent by forward activity */
		bundle = getIntent().getExtras();
		count = bundle.getInt(Constants.SELECT_IMAGE_COUNT);

		/** 从SD卡扫描 */
		ScanImages scanImages = new ScanImages(getApplicationContext(),
				showImageHandler);
		allImagesList = scanImages.getImages();

		adapter = new AlbumImagesAdapter(AlbumImagesActivity.this, gridView,
				columWidht);
		gridView.setAdapter(adapter);
		adapter.setListener(this);
		adapter.updateData(allImagesList, count);
	}

	@SuppressLint("HandlerLeak")
	public Handler showImageHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == 200)
			{
				adapter.updateData(allImagesList, count);
			}
		}
	};

	@Override
	public void onCheck(ArrayList<String> selectedList)
	{
		selectedImageList = selectedList;
		if (count >= MOST_PICTURE_COUNT)
		{
			return;
		}
		tvRightText.setText("完成(" + selectedImageList.size() + "/"
				+ (MOST_PICTURE_COUNT - count) + ")");
	}

}

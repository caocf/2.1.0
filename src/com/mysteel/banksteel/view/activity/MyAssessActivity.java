package com.mysteel.banksteel.view.activity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.ao.IEvaluate;
import com.mysteel.banksteel.ao.impl.EvaluateImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IEvaluateView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的评价页面 分系统撮合和人工撮合
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-7 上午8:58:10
 */
public class MyAssessActivity extends BaseActivity implements OnClickListener,
		IEvaluateView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "MyAssessActivity";
	private ImageView back;
	private ImageView steel;
	private TextView title;
	private RelativeLayout layout_back;// 返回布局，触点更大
	private String matchStatus = "";// 区分是系统匹配(0)的 还是 人工匹配(1)的标记
	private String orderId = "";// 订单ID

	private View viewHuman;
	private RatingBar rb_total_score;// 总评分
	private RatingBar rb_goods_real;// 货源真实
	private RatingBar rb_price_real;// 价格真实
	private int totalScore;
	private int goodsReal;
	private int priceReal;
	private TextView tv_btnview;
	private EditText edit_note;// 编辑的内容

	private IEvaluate evaluate;

	/** 业务员的信息 */
	private TextView tv_view_name;
	private CircleImageView circleimageview;
	private TextView tv_view_times;
	private String name = "";
	private String dealCount = "";
	private String imgurl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myassess);
		Bundle b = getIntent().getExtras();
		name = b.getString("name");
		dealCount = b.getString("dealCount");
		imgurl = b.getString("imgurl");
		initView();
	}

	private void initView()
	{
		Bundle b = getIntent().getExtras();
		matchStatus = b.getString("matchStatus");// 人工匹配 还是 系统匹配标识
		orderId = b.getString("orderId");// 获取需求单Id

		back = (ImageView) findViewById(R.id.menu_imgbtn);
		back.setBackgroundResource(R.drawable.back);
		layout_back = (RelativeLayout) findViewById(R.id.menu_layout);
		layout_back.setOnClickListener(this);
		steel = (ImageView) findViewById(R.id.share_imgbtn);
		rb_total_score = (RatingBar) findViewById(R.id.rb_pingfen);
		rb_goods_real = (RatingBar) findViewById(R.id.rb_huo);
		rb_price_real = (RatingBar) findViewById(R.id.rb_price);
		tv_btnview = (TextView) findViewById(R.id.tv_btnview);
		edit_note = (EditText) findViewById(R.id.edit_view);
		tv_btnview.setOnClickListener(this);
		steel.setVisibility(View.GONE);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("我的评价");
		viewHuman = findViewById(R.id.saleman_include);
		tv_view_name = (TextView) findViewById(R.id.tv_view_name);
		circleimageview = (CircleImageView) findViewById(R.id.circleimageview);
		tv_view_times = (TextView) findViewById(R.id.tv_view_times);

		showView();
		evaluate = new EvaluateImpl(this);

		rb_total_score
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
				{

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser)
					{
						totalScore = (int) rating;
					}
				});
		rb_goods_real
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
				{

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser)
					{
						goodsReal = (int) rating;
					}
				});
		rb_price_real
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
				{

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser)
					{
						priceReal = (int) rating;
					}
				});

	}

	/** 系统和分工匹配显示不同 */
	private void showView()
	{
		if (!"0".equals(matchStatus))
		{// 人工匹配
			viewHuman.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(name))
			{
				tv_view_name.setText(name);
			}
			if (!TextUtils.isEmpty(dealCount))
			{
				tv_view_times.setText("已成交"+dealCount+"次");
			}
			if (!TextUtils.isEmpty(imgurl))
			{
				showImageByNetworkImageView(imgurl, circleimageview);
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回结束
			finish();
			break;
		case R.id.tv_btnview:// 发布评价
			if (DEBUG)
			{
				Log.d(TAG, "totalScore:" + totalScore);
				Log.d(TAG, "goodsReal:" + goodsReal);
				Log.d(TAG, "priceReal:" + priceReal);
			}
			publishAssess();
			break;
		default:
			break;
		}
	}

	/** 发布评价的实现 */
	private void publishAssess()
	{
		String url = "";
		String note = edit_note.getText().toString().trim();
		if (TextUtils.isEmpty(note))
		{
			Tools.showToast(this, "请您先给个评价再提交吧!");
			return;
		}
		if ("0".equals(matchStatus))
		{// 系统匹配
//			url = RequestUrl.getInstance(this).getUrl_getEvaluateSeller(this,
//					orderId, String.valueOf(totalScore),
//					String.valueOf(goodsReal), String.valueOf(priceReal), note);
//			evaluate.getEvaluate(url, Constants.INTERFACE_evaluateSeller);
		} else
		{// 人工匹配
//			url = RequestUrl.getInstance(this).getUrl_getEvaluateManager(this,
//					orderId, String.valueOf(totalScore),
//					String.valueOf(goodsReal), String.valueOf(priceReal), note);
//			evaluate.getEvaluate(url, Constants.INTERFACE_evaluateManager);
		}

	}

	@Override
	public void updateView(BaseData data)
	{
		if ("true".equals(data.getStatus()))
		{
			Tools.showToast(this, "评价成功！");
			finish();
		}
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	public void showImageByNetworkImageView(String url, NetworkImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				200);
		ImageCache imageCache = new ImageCache()
		{
			@Override
			public void putBitmap(String key, Bitmap value)
			{
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key)
			{
				return lruCache.get(key);
			}

		};
		ImageLoader imageLoader = new ImageLoader(
				BankSteelApplication.requestQueue, imageCache);
		imgView.setTag(url);

		imgView.setImageUrl(url, imageLoader);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		evaluate.finishRequest();
	}

}

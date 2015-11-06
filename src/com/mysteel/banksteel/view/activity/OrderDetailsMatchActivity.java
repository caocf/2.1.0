package com.mysteel.banksteel.view.activity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.OrderDetailData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 定单详情(系统匹配和人工匹配)
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-5 下午3:18:13
 */
public class OrderDetailsMatchActivity extends BaseActivity implements
		OnClickListener, IOrderTradeView
{

	/**
	 * LOG SWITCHER.
	 */
	private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

	/**
	 * TAG.
	 */
	private static final String TAG = "OrderDetailsMatchActivity";
	private ImageView back;
	private ImageView steel;
	private TextView title;
	private RelativeLayout layout_back;// 返回布局，触点更大
	private OrderDetailData OData;
	private IOrderTrade orderTrade;
	private TextView tv_sell_name, tv_sell_place, tv_play_name, tv_play_place,
			tv_steel, tv_money;

	private String orderId = "";// 订单ID
	private String imgUrl = "";// 查看凭证的图片地址 只有审核通过后才有
	/**
	 * 订单状态说明（根据这个状态值做不同的页面显示与隐藏，系统匹配和人工匹配都在这个页面处理） -1 ：
	 * 是发布求购匹配到货源生成订单，跳转进来后直接需要 上传凭证页面 0 ：订单列表跳转进来后需要 上传凭证 页面 1 ：订单列表跳转进来后，待审核
	 * 页面 21：订单列表跳转进来后，审核通过但未评价， 显示已审核 页面 22：订单列表跳转进来后，审核通过已评价， 显示 已评价 页面
	 * 9：订单列表跳转进来后，审核没通过， 显示 审核未通过 页面
	 */
	private String status = "";
	private String matchStatus = "";// 系统匹配和人工匹配的区别标识
	private String uploadMessage = "";// 传给上传凭证页面的钢材信息

	/** 头部带查看凭证的布局 */
	private LinearLayout ll_head_layout, ll_trade_detail_view, ll_has_evaluate;
	private ImageView img_breed;// 品种对应的图片
	private TextView tv_material_spec, tv_qty_money, tv_checkproof;
	private View view01, view02, view03, view05, view06;// 横线的布局
	private TextView tv_trade_view /* ,tv_trade_detail_view */;// 交易双方 和
																// 交易明细的布局
	// private LinearLayout ll_steel_layout;// 资源详细信息 和价格
	private TextView tv_upload, tv_commit, tv_prompt;// 上传凭证 ,提交按钮 ,提示信息
	private LinearLayout ll_people_match;// 人工匹配的布局
	private TextView tv_dealCount, tv_view_name;// 人工匹配中被赞的次数 和 用户姓名
	private CircleImageView img_grade;// 业务员头像
	// private LinearLayout ll_layout_play_img, ll_layout_sell_img;//
	// 买家和卖家有变三角图标布局
	private LinearLayout ll_play_layout, ll_sell_layout;// 买家和卖家整体布局
	private TextView tv_shuoming;// 底部已评价后的评价内容
	private ImageView img_stars01, img_stars02, img_stars03, img_stars04,
			img_stars05;// 底部评价星星
	private LinearLayout ll_img_callphone;// 电话图标

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_match_details);
		initView();

	}

	private void initView()
	{
		Bundle b = getIntent().getExtras();
		orderId = b.getString("orderId");// 获取发布求购成功后得到的需求单Id
		status = b.getString("status");// 订单的状态
		matchStatus = b.getString("matchStatus");// 是系统匹配 还是人工匹配

		back = (ImageView) findViewById(R.id.menu_imgbtn);
		layout_back = (RelativeLayout) findViewById(R.id.menu_layout);
		steel = (ImageView) findViewById(R.id.share_imgbtn);
		title = (TextView) findViewById(R.id.tv_title);

		ll_head_layout = (LinearLayout) findViewById(R.id.ll_layout_head);
		ll_trade_detail_view = (LinearLayout) findViewById(R.id.ll_trade_detail_view);// 底部交易明细布局
		ll_has_evaluate = (LinearLayout) findViewById(R.id.ll_has_evaluate);// 底部已评价的布局
		view01 = findViewById(R.id.view01);
		view02 = findViewById(R.id.view02);
		view03 = findViewById(R.id.view03);
		// view04 = findViewById(R.id.view04);
		view05 = findViewById(R.id.view05);
		view06 = findViewById(R.id.view06);
		tv_trade_view = (TextView) findViewById(R.id.trade_view);// 交易双方提示
		// tv_trade_detail_view = (TextView)
		// findViewById(R.id.trade_detail_view);// 交易明细提示
		// ll_steel_layout = (LinearLayout)
		// findViewById(R.id.ll_steel_layout);// 资源详细信息列表
		tv_prompt = (TextView) findViewById(R.id.tv_prompt);// 底部文字提示信息
		img_breed = (ImageView) findViewById(R.id.img_breed);
		tv_material_spec = (TextView) findViewById(R.id.tv_material_spec);
		tv_qty_money = (TextView) findViewById(R.id.tv_qty_money);
		tv_checkproof = (TextView) findViewById(R.id.tv_checkproof);// 查看凭证按钮
		ll_people_match = (LinearLayout) findViewById(R.id.ll_people_match);// 人工信息布局
		tv_dealCount = (TextView) findViewById(R.id.tv_view_times);// 被赞次数
		tv_view_name = (TextView) findViewById(R.id.tv_view_name);// 业务员姓名
		img_grade = (CircleImageView) findViewById(R.id.circleimageview);// 业务员头像
		tv_play_name = (TextView) findViewById(R.id.tv_play_name_phone);
		tv_play_place = (TextView) findViewById(R.id.tv_play_company);
		tv_sell_name = (TextView) findViewById(R.id.tv_sell_name_phone);
		tv_sell_place = (TextView) findViewById(R.id.tv_sell_company);
		tv_steel = (TextView) findViewById(R.id.tv_steel);
		tv_money = (TextView) findViewById(R.id.tv_money);
		tv_upload = (TextView) findViewById(R.id.tv_upload);// 上传凭证
		tv_commit = (TextView) findViewById(R.id.tv_commit);// 提交
		// ll_layout_play_img = (LinearLayout)
		// findViewById(R.id.ll_layout_play);
		// ll_layout_sell_img = (LinearLayout)
		// findViewById(R.id.ll_layout_sell);
		ll_play_layout = (LinearLayout) findViewById(R.id.ll_play_layout);
		ll_sell_layout = (LinearLayout) findViewById(R.id.ll_sell_layout);
		ll_play_layout.setOnClickListener(this);
		ll_sell_layout.setOnClickListener(this);
		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
		img_stars01 = (ImageView) findViewById(R.id.img_stars01);
		img_stars02 = (ImageView) findViewById(R.id.img_stars02);
		img_stars03 = (ImageView) findViewById(R.id.img_stars03);
		img_stars04 = (ImageView) findViewById(R.id.img_stars04);
		img_stars05 = (ImageView) findViewById(R.id.img_stars05);
		ll_img_callphone = (LinearLayout) findViewById(R.id.ll_img_callphone);
		ll_img_callphone.setOnClickListener(this);

		back.setBackgroundResource(R.drawable.back);
		layout_back.setOnClickListener(this);
		steel.setVisibility(View.GONE);
		title.setText("订单详情");
		tv_upload.setOnClickListener(this);
		tv_commit.setOnClickListener(this);
		tv_checkproof.setOnClickListener(this);
		showView();
		orderTrade = new OrderTradeImpl(this, this);

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		getData();
	}

	/** 页面进来后拉取数据 */
	public void getData()
	{
		String url = RequestUrl.getInstance(this).getUrl_getOrderDetail(this,
				orderId);
		orderTrade.getOrderDetail(url, Constants.INTERFACE_orderDetail);
	}

	/** 根据传过来的status来显示页面 */
	private void showView()
	{
		if ("0".equals(matchStatus))
		{
			ll_people_match.setVisibility(View.GONE);// 系统匹配 不显示头部业务员的信息
		} else
		{
			ll_people_match.setVisibility(View.VISIBLE);// 人工匹配 显示头部业务员的信息
			view06.setVisibility(View.VISIBLE);
		}

		if ("-1".equals(status) || "0".equals(status))
		{// 上传凭证
			tv_trade_view.setVisibility(View.VISIBLE);// 交易双方显示
			view01.setVisibility(View.GONE);
			view02.setVisibility(View.VISIBLE);
			view03.setVisibility(View.VISIBLE);// view03跟着交易明细布局走 同时隐藏 ，同时显示
			tv_commit.setVisibility(View.GONE);// 提交按钮隐藏
			// ll_layout_play_img.setVisibility(View.VISIBLE);
			// ll_layout_sell_img.setVisibility(View.VISIBLE);
		} else if ("1".equals(status) || "21".equals(status))
		{// 1是待审核 21已审核 未评价
			ll_head_layout.setVisibility(View.VISIBLE);// 头部显示
			ll_trade_detail_view.setVisibility(View.GONE);// 交易明细布局隐藏
			tv_prompt.setVisibility(View.VISIBLE);// 底部提示信息
			if ("1".equals(status))
			{
				tv_commit.setVisibility(View.GONE);// 提交按钮隐藏
				view03.setVisibility(View.VISIBLE);
				tv_prompt.setText("提示：订单凭证已经提交，等待审核！");
				tv_prompt.setTextColor(getResources().getColor(
						R.color.font_color));
			} else if ("21".equals(status))
			{
				tv_commit.setVisibility(View.VISIBLE);// 提交按钮显示 但是 组件内容改变
				tv_commit.setText("评价卖方，领取积分！");
				view03.setVisibility(View.VISIBLE);
				tv_commit.setBackgroundColor(getResources().getColor(
						R.color.orange));
				tv_prompt.setText("提示：订单已审核通过，待评价！");
				tv_prompt.setTextColor(getResources().getColor(
						R.color.font_color));
			}
		} else if ("22".equals(status))
		{// 已评价
			tv_commit.setVisibility(View.GONE);// 提交按钮隐藏
			tv_prompt.setVisibility(View.GONE);// 底部提示信息
			ll_trade_detail_view.setVisibility(View.GONE);// 交易明细布局隐藏
			ll_has_evaluate.setVisibility(View.VISIBLE);// 底部已评价的布局显示
			ll_head_layout.setVisibility(View.VISIBLE);// 头部显示
		} else if ("9".equals(status))
		{// 审核未通过
			tv_commit.setVisibility(View.GONE);// 提交按钮隐藏
			tv_prompt.setVisibility(View.VISIBLE);// 底部提示信息
			view01.setVisibility(View.GONE);
			view03.setVisibility(View.VISIBLE);
			view05.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v)
	{
		Intent i;
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回结束
			finish();
			break;
		case R.id.tv_upload:// 跳转到上传凭证页面
			i = new Intent(this, UploadCertificateActivity.class);
			i.putExtra("orderId", orderId);
			i.putExtra("steel", uploadMessage);
			startActivity(i);
			break;
		case R.id.tv_commit:
			if ("21".equals(status))
			{// 已审核 待评价
				i = new Intent(this, MyAssessActivity.class);
				i.putExtra("matchStatus", matchStatus);
				i.putExtra("orderId", orderId);
				i.putExtra("name", OData.getData().getOrder().getManagerName());// 业务员姓名
//				i.putExtra("dealCount", OData.getData().getOrder().getDealCount());// 业务员被赞次数
//				i.putExtra("imgurl", OData.getData().getOrder().getManagerPhoto());
				startActivity(i);
			}
			break;
		case R.id.tv_checkproof:// 查看凭证
			i = new Intent(this, CheckProofActivity.class);
			i.putExtra("imgUrl", imgUrl);
			startActivity(i);
			break;

		case R.id.ll_play_layout:// 买家布局点击事件（上传凭证可点）
			/*
			 * if ("-1".equals(status) || "0".equals(status)) { i = new
			 * Intent(this, EditOrderActivity.class); i.putExtra("name",
			 * OData.getData().getOrder().getBuyUserName()); i.putExtra("phone",
			 * OData.getData().getOrder().getBuyPhone()); i.putExtra("company",
			 * OData.getData().getOrder() .getBuyMemberName());
			 * i.putExtra("orderId", orderId); i.putExtra("memberType", "1");
			 * startActivity(i); }
			 */
			break;
		case R.id.ll_sell_layout:// 卖家布局点击事件（上传凭证可点）
			/*
			 * if ("-1".equals(status) || "0".equals(status)) { i = new
			 * Intent(this, EditOrderActivity.class); i.putExtra("name",
			 * OData.getData().getOrder().getQuotUserName());
			 * i.putExtra("phone", OData.getData().getOrder().getQuotPhone());
			 * i.putExtra("company", OData.getData().getOrder()
			 * .getQuotMemberName()); i.putExtra("orderId", orderId);
			 * i.putExtra("memberType", "2"); startActivity(i); }
			 */
			break;
		case R.id.ll_img_callphone:// 电话图标的点击事件
//			if (!TextUtils.isEmpty(OData.getData().getOrder().getManagerPhone()))
//			{
//				Tools.makeCall(this, OData.getData().getOrder().getManagerPhone());
//			} else
//			{
//				Tools.makeCall(this, "暂时未获取到业务员的联系方式!");
//			}
			break;
		default:
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		OData = (OrderDetailData) data;
		editView();
	}

	/** 编辑页面信息 */
	private void editView()
	{
//		if (OData == null || OData.getData() == null || OData.getData().getOrder() == null)
//		{
//			return;
//		}
//
//		Order order = OData.getData().getOrder();
//
//		if ("0".equals(order.getStatus()))
//		{
//			status = "0";
//		} else if ("1".equals(order.getStatus()))
//		{
//			status = "1";
//		} else if ("2".equals(order.getStatus()))
//		{
//			if ("0".equals(order.getAppraiseStatus()))
//			{// 还未评价 已审核
//				status = "21";
//			} else if ("1".equals(order.getAppraiseStatus()))
//			{// 已评价
//				status = "22";
//			}
//		} else if ("9".equals(order.getStatus()))
//		{
//			status = "9";
//		}
		showView();

//		if (DEBUG)
//		{
//			Log.d(TAG, "url地址：" + order.getRemitBillUrl1());
//			Log.d(TAG, "传过来的orderId：" + orderId);
//			Log.d(TAG, "数据返回得到的id：" + order.getId());
//		}
//		imgUrl = order.getRemitBillUrl1();
//		uploadMessage = order.getBreed() + " " + order.getMaterial() + " "
//				+ order.getSpec();
//		if ("-1".equals(status) || "0".equals(status) || "9".equals(status))
//		{// 上传凭证
//			tv_play_name.setText(order.getBuyUserName() + " "
//					+ order.getBuyPhone());
//			tv_play_place.setText(order.getBuyMemberName());
//			tv_sell_name.setText(order.getQuotUserName() + " "
//					+ order.getQuotPhone());
//			tv_sell_place.setText(order.getQuotMemberName());
//			tv_steel.setText(order.getBreed() + " " + order.getMaterial() + " "
//					+ order.getSpec() + " " + order.getQty() + "吨");
//			tv_money.setText("￥" + order.getPrice() + "元/吨");
//		} else if ("1".equals(status) || "21".equals(status)
//				|| "22".equals(status))
//		{// 1是待审核 21已审核 未评价
//			tv_material_spec.setText(order.getBreed() + " "
//					+ order.getMaterial() + " " + order.getSpec());
//			tv_qty_money.setText(order.getQty() + "吨" + "￥" + order.getPrice()
//					+ "元/吨");
//			tv_play_name.setText(order.getBuyUserName() + " "
//					+ order.getBuyPhone());
//			tv_play_place.setText(order.getBuyMemberName());
//			tv_sell_name.setText(order.getQuotUserName() + " "
//					+ order.getQuotPhone());
//			tv_sell_place.setText(order.getQuotMemberName());
//
//			Steel steel = Tools.getSteelMessage(order.getBreedId());// 根据子品种id获取图像资源
//			int imgResource = steel.getImageId();
//
//			if (imgResource != -1)
//			{
//				img_breed.setImageResource(imgResource);
//			}
//		}
//
//		if (!"0".equals(matchStatus))
//		{// 若是人工匹配的 显示业务员信息
//			tv_view_name.setText(order.getManagerName());
//			tv_dealCount.setText("已成交" + order.getDealCount() + "次");
//			showImageByNetworkImageView(order.getManagerPhoto(), img_grade);
//		}
//
//		if ("9".equals(status))
//		{// 审核未通过
//			tv_prompt.setText("提示：您的订单审核未通过，请重新上传凭证！" + "\n" + "原因："
//					+ order.getAuditDesc());
//		}
//
//		if ("22".equals(status))
//		{// 已评价，显示已评价的类容
//			Appraise aise = OData.getData().getAppraise();
//			if (aise == null)
//			{
//				return;
//			}
//			int starts = 0;
//			if (!TextUtils.isEmpty(aise.getTotalAppraise()))
//			{
//				float f = Float.parseFloat(aise.getTotalAppraise());
//				starts = (int) f;
//			}
//			changeStarts(starts, img_stars01, img_stars02, img_stars03,
//					img_stars04, img_stars05);
//
//			tv_shuoming.setText((TextUtils.isEmpty(aise.getNote())) ? "" : aise
//					.getNote());
//		}

	}

	private void changeStarts(int starts, ImageView img_start01,
			ImageView img_start02, ImageView img_start03,
			ImageView img_start04, ImageView img_start05)
	{
		switch (starts)
		{
		case 0:// 状态
			img_start01.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 1:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 2:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 3:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 4:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 5:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_down);
			break;
		default:
			break;
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
		orderTrade.finishRequest();
	}

}

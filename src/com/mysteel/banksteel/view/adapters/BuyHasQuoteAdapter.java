package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.toolbox.ImageLoader;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.StanQuotDetailsData.DataEntity.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ChatActivity;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteeltwo.R;

/**
 * 求购单 已报价adapter
 * <p>
 * Created by zoujian on 15/7/27.
 */
public class BuyHasQuoteAdapter extends BaseAdapter implements IOrderTradeView
{

	private Context mContext;
	private ArrayList<Datas> mData;
	// private SwipeLayout swipeLayout;
	private IOrderTrade orderTrade;
	private int listNum;

	public BuyHasQuoteAdapter(Context context, ArrayList<Datas> data)
	{
		this.mContext = context;
		this.mData = data;
		orderTrade = new OrderTradeImpl(mContext, this);
	}

	public void reSetList(ArrayList<Datas> data)
	{
		this.mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (mData == null)
		{
			return 0;
		}
		return mData.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	// @Override
	// public int getSwipeLayoutResourceId(int position)
	// {
	// return R.id.ll_layout_hasbuy;
	// }
	//
	// @Override
	// public View generateView(int position, ViewGroup parent)
	// {
	// View v =
	// LayoutInflater.from(mContext).inflate(R.layout.adapter_hasquote_item,
	// null);
	// swipeLayout = (SwipeLayout)
	// v.findViewById(getSwipeLayoutResourceId(position));
	// swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
	// swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
	// swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
	// {
	// @Override
	// public void onClose(SwipeLayout layout)
	// {
	//
	// }
	//
	// @Override
	// public void onUpdate(SwipeLayout layout, int leftOffset,
	// int topOffset)
	// {
	//
	// }
	//
	// @Override
	// public void onOpen(SwipeLayout layout)
	// {
	// // YoYo.with(Techniques.Tada).duration(500).delay(100)
	// // .playOn(layout.findViewById(R.id.trash));
	// }
	//
	// @Override
	// public void onHandRelease(SwipeLayout layout, float xvel, float yvel)
	// {
	//
	// }
	// });
	// return v;
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_hasquote_item, parent, false);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_company = (TextView) convertView.findViewById(R.id.tv_company);
			holder.tv_yijia = (TextView) convertView.findViewById(R.id.tv_yijia);
			holder.phone = (LinearLayout) convertView.findViewById(R.id.ll_item_phone);
			holder.shejiao = (LinearLayout) convertView.findViewById(R.id.ll_item_msg);
			holder.ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
			holder.item_right = (LinearLayout) convertView.findViewById(R.id.item_right);
			holder.ll_hasquotelayout_item = (LinearLayout) convertView.findViewById(R.id.ll_hasquotelayout_item);
			holder.ll_head_layout = (RelativeLayout) convertView.findViewById(R.id.rl_head_layout);// 头像整体布局
			holder.img_up = (ImageView) convertView.findViewById(R.id.img_up);
			holder.img_hasbuy_circle = (ImageView) convertView.findViewById(R.id.img_hasbuy_circle);
			holder.circleimage = (ImageView) convertView.findViewById(R.id.img_hasbuy_circle);
			convertView.setTag(holder);
		} else
		{// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}

		LinearLayout.LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		holder.ll_hasquotelayout_item.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(510,
				LayoutParams.MATCH_PARENT);
		holder.item_right.setLayoutParams(lp2);

		Datas data = mData.get(position);
		holder.tv_name.setText(data.getQuotContact());
		holder.tv_money.setText(data.getPrice());
		holder.tv_company.setText(data.getQuotMemberName());

		if (position % 2 == 0)
		{
			convertView.setBackgroundResource(R.color.font_white_one);
			holder.img_up.setBackgroundResource(R.drawable.white_tou);
		} else
		{
			convertView.setBackgroundResource(R.color.inside_gray_little);
			holder.img_up.setBackgroundResource(R.drawable.gray_tou);
		}

		if (!TextUtils.isEmpty(data.getUserPhoto()))
		{
			loadImage(data.getUserPhoto(), holder.img_hasbuy_circle);
		}
		// bargaining 0-不可议价 1-可议价
		if ("0".equals(data.getBargaining()))
		{
			holder.tv_yijia.setText("一口价");
			holder.tv_yijia.setTextColor(mContext.getResources().getColor(
					R.color.font_black_one));
		} else if ("1".equals(data.getBargaining()))
		{
			holder.tv_yijia.setText("可议价");
			holder.tv_yijia.setTextColor(mContext.getResources().getColor(
					R.color.main_imred));
		}

		final int num = position;
		holder.phone.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
				{
					Tools.makeCall(mContext, mData.get(num).getQuotPhone());
				} else
				{
					Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		holder.shejiao.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				listNum = num;
				if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
				{
					String url = RequestUrl.getInstance(mContext)
							.getUrl_Register(mContext,
									mData.get(num).getQuotPhone(), "");
					orderTrade.getHuanXinRegister(url,
							Constants.INTERFACE_hxuserregister);
				} else
				{
					Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		return convertView;
	}

	public class ViewHolder
	{
		TextView tv_name;
		TextView tv_state;
		TextView tv_money;
		TextView tv_company;
		TextView tv_yijia;
		LinearLayout phone;
		LinearLayout shejiao;
		LinearLayout ll_item_layout;
		LinearLayout item_right;
		LinearLayout ll_hasquotelayout_item;

		RelativeLayout ll_head_layout;
		ImageView circleimage;
		ImageView img_up;
		ImageView img_hasbuy_circle;
	}

	// @Override
	// public void fillValues(final int position, View convertView)
	// {
	// RelativeLayout ll_head_layout = (RelativeLayout)
	// convertView.findViewById(R.id.rl_head_layout);//头像整体布局
	// ImageView circleimage = (ImageView)
	// convertView.findViewById(R.id.img_hasbuy_circle);
	// TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);//姓名
	// // ImageView img_renzheng = (ImageView)
	// convertView.findViewById(R.id.img_renzheng);//认证图标
	// // TextView tv_renzheng = (TextView)
	// convertView.findViewById(R.id.tv_renzheng);
	// TextView tv_state = (TextView)
	// convertView.findViewById(R.id.tv_state);//已认证字
	// TextView tv_money = (TextView)
	// convertView.findViewById(R.id.tv_money);//价格
	// TextView tv_company = (TextView)
	// convertView.findViewById(R.id.tv_company);//公司信息
	// TextView tv_yijia = (TextView)
	// convertView.findViewById(R.id.tv_yijia);//可议价 一口价
	// LinearLayout phone = (LinearLayout)
	// convertView.findViewById(R.id.ll_item_phone);
	// LinearLayout shejiao = (LinearLayout)
	// convertView.findViewById(R.id.ll_item_msg);
	// ImageView img_up = (ImageView) convertView.findViewById(R.id.img_up);
	// ImageView img_hasbuy_circle = (ImageView)
	// convertView.findViewById(R.id.img_hasbuy_circle);
	// LinearLayout ll_hasquotelayout_item = (LinearLayout)
	// convertView.findViewById(R.id.ll_hasquotelayout_item);
	//
	// Datas data = mData.get(position);
	// tv_name.setText(data.getQuotContact());
	// tv_money.setText(data.getPrice());
	// tv_company.setText(data.getQuotMemberName());
	//
	//
	// if (position % 2 == 0)
	// {
	// convertView.setBackgroundResource(R.color.font_white_one);
	// img_up.setBackgroundResource(R.drawable.white_tou);
	// } else
	// {
	// convertView.setBackgroundResource(R.color.inside_gray_little);
	// img_up.setBackgroundResource(R.drawable.gray_tou);
	// }
	//
	// if (!TextUtils.isEmpty(data.getUserPhoto()))
	// {
	// loadImage(data.getUserPhoto(), img_hasbuy_circle);
	// }
	// //bargaining 0-不可议价 1-可议价
	// if ("0".equals(data.getBargaining()))
	// {
	// tv_yijia.setText("一口价");
	// tv_yijia.setTextColor(mContext.getResources().getColor(R.color.font_black_one));
	// } else if ("1".equals(data.getBargaining()))
	// {
	// tv_yijia.setText("可议价");
	// tv_yijia.setTextColor(mContext.getResources().getColor(R.color.main_imred));
	// }
	//
	// final int num = position;
	// phone.setOnClickListener(new View.OnClickListener()
	// {
	// @Override
	// public void onClick(View v)
	// {
	// if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
	// {
	// Tools.makeCall(mContext, mData.get(num).getQuotPhone());
	// } else
	// {
	// Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
	// }
	// }
	// });
	//
	// shejiao.setOnClickListener(new View.OnClickListener()
	// {
	// @Override
	// public void onClick(View v)
	// {
	// listNum = num;
	// if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
	// {
	// String url = RequestUrl.getInstance(mContext).getUrl_Register(mContext,
	// mData.get(num).getQuotPhone(), "");
	// orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
	// } else
	// {
	// Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// });
	//
	// // ll_hasquotelayout_item.setOnClickListener(new View.OnClickListener()
	// // {
	// // @Override
	// // public void onClick(View v)
	// // {
	// // Intent i = new Intent(mContext, BuyQuoteActivity.class);
	// // i.putExtra("DATAS",mData.get(num));
	// // mContext.startActivity(i);
	// // }
	// // });
	//
	//
	// }

	public ArrayList<Datas> getData()
	{
		return mData;
	}

	@Override
	public void updateView(BaseData data)
	{
		Intent i = new Intent(mContext, ChatActivity.class);
		i.putExtra("userId", mData.get(listNum).getQuotPhone());
		i.putExtra("userName", mData.get(listNum).getQuotContact());
		mContext.startActivity(i);
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	public IOrderTrade getOrderTrade()
	{
		return orderTrade;
	}

	private void loadImage(String imageUrl, ImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache()
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
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(
				imgView, R.drawable.nophoto, R.drawable.nophoto);
		imageLoader.get(imageUrl, listener);
	}

}

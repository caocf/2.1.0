package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.ScoreConvert.Data.Goods;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.FillOrderActivity;
import com.mysteel.banksteel.view.activity.PresentsActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 积分商城adapter
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-15 上午10:40:43
 */
public class ScoreStoreAdapter extends BaseAdapter
{

	private LayoutInflater inflater;

	private ArrayList<Goods> data;

	public ScoreStoreAdapter(LayoutInflater inflater, ArrayList<Goods> data)
	{
		this.inflater = inflater;
		this.data = data;
	}

	public void reSetListView(ArrayList<Goods> data)
	{
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (data == null)
		{
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder holder = null;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.listview_presents_item,
					null);
			holder = new Holder();
			holder.imPrePicture = (ImageView) convertView
					.findViewById(R.id.im_present_picture);
			holder.tvPreName = (TextView) convertView
					.findViewById(R.id.tv_present_name);
			holder.tvScore = (TextView) convertView
					.findViewById(R.id.tv_score_value);
			holder.llThird = (LinearLayout) convertView
					.findViewById(R.id.ll_third);
			holder.llfour = (LinearLayout) convertView
					.findViewById(R.id.ll_four);
			holder.llfive = (LinearLayout) convertView
					.findViewById(R.id.ll_five);
			holder.tvPreCount = (TextView) convertView
					.findViewById(R.id.tv_present_count);
			holder.tvReceiveMan = (TextView) convertView
					.findViewById(R.id.tv_receive_man);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_address);
			holder.btnExchange = (Button) convertView
					.findViewById(R.id.btn_exchange);
			holder.rlLastLine = (RelativeLayout) convertView
					.findViewById(R.id.rl_exchange_date);
			holder.tvExchangeDate = (TextView) convertView
					.findViewById(R.id.tv_exchange_date);
			holder.imShare = (ImageView) convertView
					.findViewById(R.id.im_exchange_present_share);

			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		holder.llThird.setVisibility(View.VISIBLE);
		holder.btnExchange.setVisibility(View.VISIBLE);
		holder.tvReceiveMan.setVisibility(View.GONE);
		holder.tvAddress.setVisibility(View.GONE);
		holder.rlLastLine.setVisibility(View.GONE);
		holder.llfour.setVisibility(View.GONE);
		holder.llfive.setVisibility(View.GONE);

		holder.btnExchange.setTag(position);
		holder.btnExchange.setOnClickListener(new ExchangeOnClickListener());

		Goods scData = data.get(position);
		holder.tvPreName.setText(scData.getGoodsName());// 商品名称
		holder.tvScore.setText(scData.getGoodsGangBeng());// 积分 或者 钢镚
		holder.tvPreCount.setText(scData.getNumber());// 库存量

//		loadImage("http://" + scData.getGoodsThumb(),
//				holder.imPrePicture);
		loadImage(scData.getGoodsThumb(),holder.imPrePicture);

		return convertView;
	}

	public class Holder
	{
		public ImageView imPrePicture;
		public TextView tvPreName;
		public TextView tvScore;

		public LinearLayout llThird;
		public LinearLayout llfour;
		public LinearLayout llfive;
		public TextView tvPreCount;
		public TextView tvReceiveMan;
		public TextView tvAddress;
		public Button btnExchange;

		public RelativeLayout rlLastLine;
		public TextView tvExchangeDate;
		public ImageView imShare;
	}

	private void loadImage(String imageUrl, ImageView imgView)
	{
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
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
		ImageListener listener = ImageLoader.getImageListener(imgView,
				R.drawable.pictures_no, R.drawable.pictures_no);
		imageLoader.get(imageUrl, listener);
	}

	public class ExchangeOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			int postion = (Integer) v.getTag();
			PresentsActivity activity = (PresentsActivity) inflater
					.getContext();

			if (Integer.parseInt(data.get(postion).getNumber()) == 0)
			{
				Tools.showToast(activity, "没有库存了,请选择其他商品");
				return;
			}
			int totalScore = activity.getTotalScore();// 总积分
			int currentCostScore = Integer.parseInt(data.get(postion)
					.getGoodsGangBeng());// 商品积分
			if (totalScore < currentCostScore)
			{
//				Tools.showToast(activity, "您当前积分拥有" + totalScore + "积分,您的积分不足");
				return;
			}
			Intent intent = new Intent(activity, FillOrderActivity.class);
			intent.putExtra("DATA", data.get(postion));
			intent.putExtra("SCORE", totalScore);
			activity.startActivity(intent);
		}

	}
}

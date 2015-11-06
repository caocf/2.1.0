package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.entity.MessageCenterData.Data.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.view.activity.SteelCircleActivity;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

public class MessageCenterAdapter extends BaseAdapter
{

	private Context mContext;
	private List<Datas> datas = new ArrayList<Datas>();

	public MessageCenterAdapter(Context mContext)
	{
		super();
		this.mContext = mContext;
	}

	public void reSetListView(ArrayList<Datas> datas)
	{
		if (datas == null || datas.size() == 0)
		{
			return;
		}
		this.datas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (datas.size() > 0)
		{
			return datas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return datas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_message_center, null);
			holder = new ViewHolder();
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.tv_message_date);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_message_content);
			holder.ivPhoto = (CircleImageView) convertView
					.findViewById(R.id.iv_message_photograph);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Datas data = datas.get(position);

		holder.tvDate.setText(data.getCreateTime());
		holder.tvContent.setText(data.getContent());
		holder.ivPhoto.setImageResource(R.drawable.nophoto);

		/**the last message 点击跳转到主页*/
		if ("LASTMESSAGE".equals(data.getType()))
		{
			holder.tvContent.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent i = new Intent(mContext, SteelCircleActivity.class);
					i.putExtra("DATA", Constants.HOMEPAGE);
					mContext.startActivity(i);
				}
			});
		}else{
			holder.tvContent.setClickable(false);
		}
		// String imageUrl = SharePreferenceUtil.getString(mContext,
		// Constants.USER_PHTHO);
		// loadImage(imageUrl, holder.ivPhoto);
		return convertView;
	}

	class ViewHolder
	{
		TextView tvDate;
		TextView tvContent;
		CircleImageView ivPhoto;
	}

	/*
	 * private void loadImage(String imageUrl, ImageView imgView) { final
	 * LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>( 20);
	 * ImageCache imageCache = new ImageCache() {
	 * 
	 * @Override public void putBitmap(String key, Bitmap value) {
	 * lruCache.put(key, value); }
	 * 
	 * @Override public Bitmap getBitmap(String key) { return lruCache.get(key);
	 * } }; ImageLoader imageLoader = new ImageLoader(
	 * BankSteelApplication.requestQueue, imageCache); ImageListener listener =
	 * ImageLoader.getImageListener(imgView, R.drawable.pictures_no,
	 * R.drawable.pictures_no); imageLoader.get(imageUrl, listener); }
	 */
}

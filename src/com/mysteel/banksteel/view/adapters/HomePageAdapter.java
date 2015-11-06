package com.mysteel.banksteel.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

/**
 * 首页布局中的adapter，主要包括精准找货、语音找货、我的订单
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-4 上午10:04:23
 */
public class HomePageAdapter extends BaseAdapter
{

	private LayoutInflater mInflater;
	private boolean flag = false;// 是否显示红点false:不显示，true:显示

	public HomePageAdapter(LayoutInflater inflater)
	{
		mInflater = inflater;
	}

	public void refreshList(boolean flag)
	{
		this.flag = flag;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return 3;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.homepage_item, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.menu_text);
			holder.img = (ImageView) convertView.findViewById(R.id.menu_imageView);
			holder.redPoint = (TextView) convertView.findViewById(R.id.tv_redpoint);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		switch (position)
		{
		case 0:
			holder.content.setText("精准找货");
			holder.img.setBackgroundResource(R.drawable.accurate_find_food);
			holder.redPoint.setVisibility(View.INVISIBLE);
			break;
		case 1:
			holder.content.setText("语音找货");
			holder.img.setBackgroundResource(R.drawable.voice_food);
			holder.redPoint.setVisibility(View.INVISIBLE);
			break;
		case 2:
			holder.content.setText("我的订单");
			holder.img.setBackgroundResource(R.drawable.my_order);
			if (flag)
			{
				holder.redPoint.setVisibility(View.VISIBLE);
			} else
			{
				holder.redPoint.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}

		return convertView;
	}

	public class ViewHolder
	{
		TextView content;// item的描述
		ImageView img;// item的图标
		TextView redPoint;
	}

}

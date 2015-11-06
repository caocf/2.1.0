package com.mysteel.banksteel.view.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

/**
 * 热门厂商和城市的adapter，用于gridview显示热点内容
 * 
 * @author:huoguodong
 * @date：2015-5-6 下午5:21:41
 */
public class HotContentAdapter extends BaseAdapter
{

	private List<String> hotList;
	private LayoutInflater inflater;
	private boolean mFlag = false;//物流进来后字符串需要截取的标识

	public HotContentAdapter(Context context, List<String> hotList)
	{
		this.hotList = hotList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public HotContentAdapter(Context context, List<String> hotList,boolean flag)
	{
		this.hotList = hotList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mFlag = flag;
	}

	@Override
	public int getCount()
	{
		if (hotList != null)
		{
			return hotList.size();
		}
		return 0;
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
		Holder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.hot_content_item, null);
			holder = new Holder();
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_hot_content_name);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		if(mFlag)
		{
			holder.tvName.setText(hotList.get(position).substring(0,hotList.get(position).length()-6));
		}else{
			holder.tvName.setText(hotList.get(position));
		}

		return convertView;
	}

	class Holder
	{
		public TextView tvName;
	}
}

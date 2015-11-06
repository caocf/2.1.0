package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteeltwo.R;

/**
 * 热搜关键字的adapter，用于gridview显示热搜内容
 * @author:wushaoge
 * @date：2015年8月5日19:35:25
 */
public class HotKeyWordContentAdapter extends BaseAdapter
{

	private List<String> hotList;
	private LayoutInflater inflater;

	public HotKeyWordContentAdapter(Context context, List<String> hotList)
	{
		this.hotList = hotList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void reSetListView(List<String> hotList)
	{
		this.hotList = hotList;
		this.notifyDataSetChanged();
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
			convertView = inflater.inflate(R.layout.hot_keyword_content_item, null);
			holder = new Holder();
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_hot_content_name);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		holder.tvName.setText(hotList.get(position));
		return convertView;
	}

	class Holder
	{
		public TextView tvName;
	}
}

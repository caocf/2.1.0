package com.mysteel.banksteel.view.adapters;

import java.util.List;

import com.mysteel.banksteel.entity.SteelType;
import com.mysteel.banksteeltwo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 筛选条件之大分类查找adapter
 * 
 * @author:wushaoge
 * @date：2015年7月28日09:26:22
 */
public class ScreeningFenleiAdapter  extends BaseAdapter
{

	private List<SteelType> list;
	private LayoutInflater inflater;
	private Context mContext;

	public ScreeningFenleiAdapter(Context context, List<SteelType> list)
	{
		this.list = list;
		this.mContext = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void updateData(List<SteelType> list)
	{
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (list != null)
		{
			return list.size();
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
		Holder holder = null;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.listviewsx_simple_item, null);
			holder = new Holder();
			holder.steelName = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		holder.steelName.setText(list.get(position).getName());
		if(position%2 == 0){
			holder.steelName.setBackgroundColor(mContext.getResources().getColor(R.color.listview_item_gray));
		}else{
			holder.steelName.setBackgroundColor(mContext.getResources().getColor(R.color.font_white_one));
		}
		return convertView;
	}

	class Holder
	{
		public TextView steelName;
	}
}
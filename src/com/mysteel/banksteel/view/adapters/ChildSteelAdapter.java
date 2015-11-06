package com.mysteel.banksteel.view.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.entity.SteelData.SteelBean;
import com.mysteel.banksteeltwo.R;

/**
 * @author:huoguodong
 * @date：2015-5-5 下午1:27:16
 */
public class ChildSteelAdapter extends BaseAdapter
{

	private List<SteelBean> steels;
	private LayoutInflater inflater;

	public ChildSteelAdapter(Context context, List<SteelBean> steels)
	{
		this.steels = steels;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void updateData(List<SteelBean> steels)
	{
		this.steels = steels;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (steels != null)
		{
			return steels.size();
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
			convertView = inflater.inflate(R.layout.listview_simple_item, null);
			holder = new Holder();
			holder.steelName = (TextView) convertView
					.findViewById(R.id.tv_selected_type_name);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		holder.steelName.setText(steels.get(position).getName());
		return convertView;
	}

	class Holder
	{
		public TextView steelName;
	}
}
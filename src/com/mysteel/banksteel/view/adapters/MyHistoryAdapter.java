package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.entity.ScoreRecord.Data.Records;
import com.mysteel.banksteeltwo.R;

/**
 * 搜索历史记录记录适配器
 *
 */
public class MyHistoryAdapter extends BaseAdapter
{

	private Context mContext;
	private ArrayList<String> mData = new ArrayList<String>();

	public MyHistoryAdapter(Context mcontext)
	{
		super();
		this.mContext = mcontext;
	}

	public void reSetListView(ArrayList<String> data)
	{
		if (data == null || data.size() == 0)
		{
			return;
		}
		mData = data;
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
		return mData.get(position);
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
					R.layout.listview_history_item, null);
			holder = new ViewHolder();
			holder.tv_history = (TextView) convertView
					.findViewById(R.id.tv_history);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_history.setText(mData.get(position));
		return convertView;
	}

	class ViewHolder
	{
		TextView tv_history;
	}

}

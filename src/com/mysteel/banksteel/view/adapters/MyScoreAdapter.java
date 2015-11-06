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
 * 积分记录适配器
 *
 */
public class MyScoreAdapter extends BaseAdapter
{

	private Context mContext;
	private ArrayList<Records> mData = new ArrayList<Records>();

	public MyScoreAdapter(Context mcontext)
	{
		super();
		this.mContext = mcontext;
	}

	public void reSetListView(ArrayList<Records> data)
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
					R.layout.item_myscore_recordscore, null);
			holder = new ViewHolder();
			holder.tvAction = (TextView) convertView
					.findViewById(R.id.tv_recordscore_action);
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.tv_recordscore_date);
			holder.tvScore = (TextView) convertView
					.findViewById(R.id.tv_recordscore_getscore);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Records data = mData.get(position);
		holder.tvAction.setText(data.getStatusValue());
		holder.tvDate.setText("(" + data.getCreateTime() + ")");
		if (Integer.parseInt(data.getScore()) > 0)
		{
			holder.tvScore.setText("+" + data.getScore());
		} else
		{
			holder.tvScore.setText(data.getScore());
		}
		return convertView;
	}

	class ViewHolder
	{
		TextView tvAction;
		TextView tvDate;
		TextView tvScore;
	}

}

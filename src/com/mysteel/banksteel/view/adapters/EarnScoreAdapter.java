package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

/**
 * @author 68 赚取积分界面新手上路的适配器
 */
public class EarnScoreAdapter extends BaseAdapter
{

	private static final String[] TAGNAME =
	{ "个人信息", "绑定手机", "设置头像", "完成订单" };
	private Activity Mactivity;
	private List<String> data = new ArrayList<String>();

	public EarnScoreAdapter(Activity activity)
	{
		this.Mactivity = activity;
		data = Arrays.asList(TAGNAME);
	}

	@Override
	public int getCount()
	{
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(Mactivity).inflate(
					R.layout.item_earnscore_ncvioe, null);
			holder = new ViewHolder();
			holder.tagNameTv = (TextView) convertView
					.findViewById(R.id.tv_ncvioe_tagname);
			holder.tv_ncvioe_earnscore = (TextView) convertView
					.findViewById(R.id.tv_ncvioe_earnscore);

			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tagNameTv.setText(data.get(position));
		if (position == data.size() - 1)
		{// 最后一个
			holder.tv_ncvioe_earnscore.setText("1吨/1积分");
		} else
		{
			holder.tv_ncvioe_earnscore.setText("10积分");
		}

		return convertView;
	}

	class ViewHolder
	{
		TextView tagNameTv;
		TextView tv_ncvioe_earnscore;
	}

}

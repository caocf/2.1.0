package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;

import com.mysteel.banksteel.entity.MessageCenterData.Data.Datas;
import com.mysteel.banksteel.entity.SteelData.SteelBean;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.view.activity.SteelCircleActivity;
import com.mysteel.banksteeltwo.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 信息中心adapter
 * @author:wushaoge
 * @date：2015年7月29日09:04:56
 */
public class MsgXitongAdapter extends BaseAdapter
{

	private Context mContext;
	
	private List<Datas> datas = new ArrayList<Datas>();
	boolean isEdit = false;

	public MsgXitongAdapter(Context context)
	{
		super();
		this.mContext = context;
	}

	public void reSetListView(ArrayList<Datas> datas,boolean isEdit)
	{
		if (datas == null || datas.size() == 0)
		{
			return;
		}
		this.datas = datas;
		this.isEdit = isEdit;
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
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		Holder holder = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_msg_item, null);
			holder = new Holder();
			holder.iv_item_ischeck = (ImageView) convertView
					.findViewById(R.id.iv_item_ischeck);
			holder.tv_date = (TextView) convertView
					.findViewById(R.id.tv_date);
			holder.tv_msg = (TextView) convertView
					.findViewById(R.id.tv_msg);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		
		Datas data = datas.get(position);
		
		holder.tv_date.setText(data.getCreateTime());
		holder.tv_msg.setText(data.getContent());
		
		if(isEdit){
			holder.iv_item_ischeck.setVisibility(View.VISIBLE);
		}else{
			holder.iv_item_ischeck.setVisibility(View.GONE);
		}
		
		if(data.getSelected()){
			holder.iv_item_ischeck.setImageResource(R.drawable.msg_check);
		}else{
			holder.iv_item_ischeck.setImageResource(R.drawable.msg_uncheck);
		}
		
		holder.iv_item_ischeck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(position, "msgXitongAdapter");
			}
		});
		return convertView;
	}

	class Holder
	{
		public ImageView iv_item_ischeck;
		public TextView tv_date;
		public TextView tv_msg;
	}
	
	
	
	
}
package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysteel.banksteel.entity.ShowUserInfo;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心评价adapter
 * @author:wushaoge
 * @date：2015年7月29日09:04:56
 */
public class ShowUserInfoAppraiseAdapter extends BaseAdapter
{

	private Context mContext;

	private List<ShowUserInfo.Data.Appraise> datas = new ArrayList<ShowUserInfo.Data.Appraise>();

	public ShowUserInfoAppraiseAdapter(Context context)
	{
		super();
		this.mContext = context;
	}

	public void reSetListView(List<ShowUserInfo.Data.Appraise> datas)
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_appraise_item, null);
			holder = new Holder();
			holder.img_com_start01 = (ImageView) convertView
					.findViewById(R.id.img_com_start01);
			holder.img_com_start02 = (ImageView) convertView
					.findViewById(R.id.img_com_start02);
			holder.img_com_start03 = (ImageView) convertView
					.findViewById(R.id.img_com_start03);
			holder.img_com_start04 = (ImageView) convertView
					.findViewById(R.id.img_com_start04);
			holder.img_com_start05 = (ImageView) convertView
					.findViewById(R.id.img_com_start05);
			holder.tv_detail = (TextView) convertView
					.findViewById(R.id.tv_detail);
			holder.tv_date = (TextView) convertView
					.findViewById(R.id.tv_date);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		ShowUserInfo.Data.Appraise data = datas.get(position);
		changeStart(holder,data.getTotalAppraise());
		holder.tv_detail.setText(data.getNote());
		holder.tv_date.setText(data.getCreateTime());
		return convertView;
	}

	class Holder
	{
		public ImageView img_com_start01;
		public ImageView img_com_start02;
		public ImageView img_com_start03;
		public ImageView img_com_start04;
		public ImageView img_com_start05;
		public TextView tv_detail;
		public TextView tv_date;
	}
	
	private void changeStart(Holder holder,String totalStart){
		if(!TextUtils.isEmpty(totalStart)){
//			int num = Integer.parseInt(totalStart);
			int num = (int)Float.parseFloat(totalStart);
			holder.img_com_start01.setBackgroundResource(R.drawable.start_up);
			holder.img_com_start02.setBackgroundResource(R.drawable.start_up);
			holder.img_com_start03.setBackgroundResource(R.drawable.start_up);
			holder.img_com_start04.setBackgroundResource(R.drawable.start_up);
			holder.img_com_start05.setBackgroundResource(R.drawable.start_up);
			if(num == 1){
				holder.img_com_start01.setBackgroundResource(R.drawable.start_down);
			}
			if(num == 2){
				holder.img_com_start01.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start02.setBackgroundResource(R.drawable.start_down);
			}
			if(num == 3){
				holder.img_com_start01.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start02.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start03.setBackgroundResource(R.drawable.start_down);
			}
			if(num == 4){
				holder.img_com_start01.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start02.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start03.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start04.setBackgroundResource(R.drawable.start_down);
			}
			if(num == 5){
				holder.img_com_start01.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start02.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start03.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start04.setBackgroundResource(R.drawable.start_down);
				holder.img_com_start05.setBackgroundResource(R.drawable.start_down);
			}
		}
	}
	
	
}
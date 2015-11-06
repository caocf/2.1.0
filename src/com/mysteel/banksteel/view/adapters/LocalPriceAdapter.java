package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PageCustomData.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * 同城报价adapter
 * @author:wushaoge
 * @date：2015年7月29日09:04:56
 */
public class LocalPriceAdapter extends BaseAdapter
{

	private ArrayList<DatasEntity> datas;
	private LayoutInflater inflater;
	private Context context;

	public LocalPriceAdapter(Context context, ArrayList<DatasEntity> datas)
	{
		this.context = context;
		this.datas = datas;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void reSetListView(ArrayList<DatasEntity> datas)
	{
		this.datas = datas;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (datas != null)
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder holder = null;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.lv_local_price_item, null);
			holder = new Holder();
			holder.ll_show1 = (LinearLayout) convertView.findViewById(R.id.ll_show1);
			holder.ll_show2 = (LinearLayout) convertView.findViewById(R.id.ll_show2);
			holder.tv_gangcai = (TextView) convertView.findViewById(R.id.tv_gangcai);
			holder.tv_caizhi = (TextView) convertView.findViewById(R.id.tv_caizhi);
			holder.tv_guige = (TextView) convertView.findViewById(R.id.tv_guige);
			holder.tv_pinpai = (TextView) convertView.findViewById(R.id.tv_pinpai);
			holder.tv_num_ton = (TextView) convertView.findViewById(R.id.tv_num_ton);
			holder.tv_didian = (TextView) convertView.findViewById(R.id.tv_didian);
			holder.tv_surplus_date = (TextView) convertView.findViewById(R.id.tv_surplus_date);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_bargain = (TextView) convertView.findViewById(R.id.tv_bargain);
			holder.tv_quote_num = (TextView) convertView.findViewById(R.id.tv_quote_num);
			holder.tv_qiangdan = (TextView) convertView.findViewById(R.id.tv_qiangdan);
			
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		
		DatasEntity dataTemp = datas.get(position);

		if(dataTemp.getMyPrice().equals("0")){
			holder.ll_show1.setVisibility(View.GONE);
			holder.ll_show2.setVisibility(View.VISIBLE);
		}else{
			holder.ll_show1.setVisibility(View.VISIBLE);
			holder.ll_show2.setVisibility(View.GONE);
			String num = "已有" + "<font color='#69ccff'>" + dataTemp.getQuotNum() + "</font>" + " 人报价";
			holder.tv_quote_num.setText(Html.fromHtml(num));
			if(dataTemp.getBargaining().equals("0")){
				holder.tv_bargain.setText("一口价");
				holder.tv_bargain.setTextColor(context.getResources().getColor(R.color.font_black_one));
			}else{
				holder.tv_bargain.setText("可议价");
				holder.tv_bargain.setTextColor(context.getResources().getColor(R.color.main_imred));
			}
			holder.tv_price.setText(dataTemp.getMyPrice());
		}
		holder.tv_gangcai.setText(dataTemp.getBreed());
		holder.tv_caizhi.setText(dataTemp.getMaterial());
		holder.tv_guige.setText(dataTemp.getSpec());
		holder.tv_pinpai.setText(dataTemp.getBrand());
		holder.tv_num_ton.setText(dataTemp.getQty()+"吨");
		holder.tv_didian.setText(dataTemp.getCity());
		String resultDate = "还剩";
		//long time = Long.parseLong(dataTemp.getDueTime());
        Date dateTemp = DateUtil.getDate("yyyy-MM-dd HH:mm:ss",dataTemp.getDueTime());
		long[] diffDate = DateUtil.dateDiffEx(new Date(), dateTemp);
		if(diffDate[0]!=0){
			resultDate += diffDate[0] + "天";
		}
		if(diffDate[1]!=0){
			resultDate += diffDate[1] + "小时";
		}
		if(diffDate[2]!=0){
			resultDate += diffDate[2] + "分";
		}
		if(diffDate[0]==0&&diffDate[1]==0&&diffDate[2]==0){
			holder.tv_surplus_date.setText("已过期");
			holder.tv_surplus_date.setTextColor(context.getResources().getColor(R.color.main_imred));
		}else{
			holder.tv_surplus_date.setText(resultDate);
			holder.tv_surplus_date.setTextColor(context.getResources().getColor(R.color.font_black_two));
		}
		return convertView;
	}

	class Holder
	{
		public LinearLayout ll_show1;
		public LinearLayout ll_show2;
		public TextView tv_gangcai;
		public TextView tv_caizhi;
		public TextView tv_guige;
		public TextView tv_pinpai;
		public TextView tv_num_ton;
		public TextView tv_didian;
		public TextView tv_surplus_date;
		public TextView tv_price;
		public TextView tv_bargain;
		public TextView tv_quote_num;
		public TextView tv_qiangdan;
	}
	
	
	
}
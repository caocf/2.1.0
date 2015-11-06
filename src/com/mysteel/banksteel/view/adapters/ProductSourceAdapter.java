package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.MatchBuyData.Data.Datas;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

/**
 * 选择货源adapter
 * 
 * @author:huoguodong
 * @date：2015-5-7 上午11:52:16
 */
public class ProductSourceAdapter extends BaseAdapter
{

	private LayoutInflater inflater;
	private ArrayList<Datas> data;
	private Datas createOrderData;

	public ProductSourceAdapter(Context context, ArrayList<Datas> datas)
	{
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = datas;
		initImage();
	}

	public void reSetListView(ArrayList<Datas> datas)
	{
		this.data = datas;
		initImage();
		notifyDataSetChanged();
	}

	public void initImage()
	{
		if (data == null)
		{
			return;
		}
		int count = data.size();
		for (int i = 0; i < count; i++)
		{
			data.get(i).setFlag(false);
		}

	}

	@Override
	public int getCount()
	{
		if (data == null)
		{
			return 0;
		}
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
		final Holder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(
					R.layout.listview_select_prosource_item, null);
			holder = new Holder();
			holder.llLayout = (LinearLayout) convertView
					.findViewById(R.id.ll_layout);
			holder.rbCheck = (ImageView) convertView
					.findViewById(R.id.rb_prosource_item_check);
			holder.tvType = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_type);
			holder.tvSize = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_size);
			holder.tvCity = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_city);
			holder.tvBrandNumber = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_brand);
			holder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_price);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_prosource_item_address);
			holder.imPhone = (ImageView) convertView
					.findViewById(R.id.im_prosource_item_phone);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}
		final Datas datas = data.get(position);

		holder.tvType.setText(datas.getBreed());
		holder.tvSize.setText(datas.getMaterial() + " " + datas.getSpec());
		holder.tvCity.setText(datas.getWarehouse());
		holder.tvBrandNumber.setText(datas.getCity() + "," + datas.getQty()
				+ "吨");
		holder.tvPrice.setText("￥" + datas.getPrice());
		holder.tvAddress.setText(datas.getContacter());

		final int num = position;
		holder.llLayout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (datas.isFlag())
				{
					holder.rbCheck
							.setBackgroundResource(R.drawable.unchoose_goods);
					data.get(num).setFlag(false);
					createOrderData = null;
				} else
				{
					holder.rbCheck
							.setBackgroundResource(R.drawable.choose_goods);
					createOrderData = data.get(num);
					int count = data.size();
					for (int i = 0; i < count; i++)
					{
						if (num != i)
						{
							data.get(i).setFlag(false);
						} else
						{
							data.get(i).setFlag(true);
						}
					}
					notifyDataSetChanged();
				}
			}
		});

		if (datas.isFlag())
		{
			holder.rbCheck.setBackgroundResource(R.drawable.choose_goods);
		} else
		{
			holder.rbCheck.setBackgroundResource(R.drawable.unchoose_goods);
		}
		
		//拨打电话
		holder.imPhone.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Tools.makeCall(inflater.getContext(),datas.getPhone());
			}
		});
		return convertView;
	}

	class Holder
	{
		public ImageView rbCheck;
		public TextView tvType;
		public TextView tvSize;
		public TextView tvCity;
		public TextView tvBrandNumber;
		public TextView tvPrice;
		public TextView tvAddress;
		public ImageView imPhone;
		public LinearLayout llLayout;
	}

	public Datas getDatas()
	{
		return createOrderData;
	}
}

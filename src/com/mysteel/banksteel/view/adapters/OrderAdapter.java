package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;
import com.mysteel.banksteel.view.activity.OrderDetailsMatchActivity;
import com.mysteel.banksteel.view.activity.OrderStopActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 订单中心的adapter
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-19 下午5:38:15
 */
public class OrderAdapter extends BaseAdapter
{

	private LayoutInflater mInflater;
	private ArrayList<Datas> data;

	public OrderAdapter(LayoutInflater inflater, ArrayList<Datas> data)
	{
		mInflater = inflater;
		this.data = data;
	}

	public void reSetListVIew(ArrayList<Datas> data)
	{
		this.data = data;
		notifyDataSetChanged();
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
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.activity_allorder_item,
					null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.brand = (TextView) convertView.findViewById(R.id.tv_brand);
			holder.btn = (TextView) convertView.findViewById(R.id.tv_btn);
			holder.place = (TextView) convertView.findViewById(R.id.tv_place);
			holder.rl_layout = (RelativeLayout) convertView
					.findViewById(R.id.rl_layout);
			holder.img_start01 = (ImageView) convertView
					.findViewById(R.id.iv_stars01);
			holder.img_start02 = (ImageView) convertView
					.findViewById(R.id.iv_stars02);
			holder.img_start03 = (ImageView) convertView
					.findViewById(R.id.iv_stars03);
			holder.img_start04 = (ImageView) convertView
					.findViewById(R.id.iv_stars04);
			holder.img_start05 = (ImageView) convertView
					.findViewById(R.id.iv_stars05);
			holder.manager = (TextView) convertView
					.findViewById(R.id.tv_manager);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final Context context = mInflater.getContext();
		final Datas datas = data.get(position);
		int starts = 0;
		if (!TextUtils.isEmpty(datas.getTotalAppraise()))
		{
			float f = Float.parseFloat(datas.getTotalAppraise());
			starts = (int) f;
		}
		changeStarts(starts, holder.img_start01, holder.img_start02,
				holder.img_start03, holder.img_start04, holder.img_start05);
		holder.time.setText(datas.getOrderTime());// 时间
		holder.brand.setText(datas.getBreed() + datas.getMaterial()
				+ datas.getSpec());
		holder.place.setText(datas.getBrand() + datas.getQty() + "吨" + " "
				+ datas.getWarehouse());

//		String source = datas.getSource();
//		if ("0".equals(source))
//		{
//			holder.manager.setText("(系统匹配 )");
//		} else
//		{
			if ("null".equals(datas.getManagerName()) || "".equals(datas.getManagerName()))
			{
				holder.manager.setText("(系统匹配 )");
			} else
			{
				holder.manager.setText("(钢银助手-" + datas.getManagerName()+")");
			}
//		}
		
		final String status = datas.getStatus();
		if ("0".equals(status))
		{// 待上传凭证
			holder.btn.setText("上传凭证");
			holder.btn.setBackgroundResource(R.drawable.green_btn);
		} else if ("1".equals(status))
		{// 待审核
			holder.btn.setText("待审核");
			holder.btn.setBackgroundResource(R.drawable.orange);
		} else if ("2".equals(status))
		{// 审核通过
			if ("0".equals(datas.getAppraiseStatus()))
			{// 0-未评价 1 已评价
				holder.btn.setText("已审核");
				holder.btn.setBackgroundResource(R.drawable.green_btn);
			} else if ("1".equals(datas.getAppraiseStatus()))
			{
				holder.btn.setText("已评价");
				holder.btn.setBackgroundResource(R.drawable.gray);
			}
		} else if ("9".equals(status))
		{// 审核不通过
			holder.btn.setText("审核未通过");
			holder.btn.setBackgroundResource(R.drawable.blue);
		} else if ("99".equals(status))
		{// 关闭
			holder.btn.setText("关闭");
			holder.btn.setBackgroundResource(R.drawable.gray);
		}

		holder.rl_layout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(context, OrderDetailsMatchActivity.class);
				if ("0".equals(status))
				{// 上传凭证
					i.putExtra("orderId", datas.getId());
					i.putExtra("matchStatus", datas.getManagerId());
					i.putExtra("status", "0");
					context.startActivity(i);
				} else if ("1".equals(status))
				{// 待审核
					i.putExtra("orderId", datas.getId());
					i.putExtra("matchStatus", datas.getManagerId());
					i.putExtra("status", "1");
					context.startActivity(i);
				} else if ("2".equals(status))
				{// 审核通过
					if ("0".equals(datas.getAppraiseStatus()))
					{// 还未评价 已审核
						i.putExtra("orderId", datas.getId());
						i.putExtra("matchStatus", datas.getManagerId());
						i.putExtra("status", "21");
						context.startActivity(i);
					} else if ("1".equals(datas.getAppraiseStatus()))
					{// 已评价
						i.putExtra("orderId", datas.getId());
						i.putExtra("matchStatus", datas.getManagerId());
						i.putExtra("status", "22");
						context.startActivity(i);
					}
				} else if ("9".equals(status))
				{// 审核不通过
					i.putExtra("orderId", datas.getId());
					i.putExtra("matchStatus", datas.getManagerId());
					i.putExtra("status", "9");
					context.startActivity(i);
				}

				if ("99".equals(status))
				{// 审核关闭
					Intent i1 = new Intent(context, OrderStopActivity.class);
					i1.putExtra("orderId", datas.getId());
					context.startActivity(i1);
				}

			}
		});

		return convertView;
	}

	private void changeStarts(int starts, ImageView img_start01,
			ImageView img_start02, ImageView img_start03,
			ImageView img_start04, ImageView img_start05)
	{
		switch (starts)
		{
		case 0:// 状态
			img_start01.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 1:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 2:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 3:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_up);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 4:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_up);
			break;
		case 5:
			img_start01.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start02.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start03.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start04.setBackgroundResource(R.drawable.singlestar_big_down);
			img_start05.setBackgroundResource(R.drawable.singlestar_big_down);
			break;
		default:
			break;
		}
	}

	public class ViewHolder
	{
		TextView time;// 时间
		TextView brand;// 语言待识别，或者是品牌材质规格等信息
		TextView btn;// 按钮
		TextView place;// 产地，吨位，库区
		TextView manager;// 管理员信息
		RelativeLayout rl_layout;
		ImageView img_start01, img_start02, img_start03, img_start04,
				img_start05;
	}

}

package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PagDatas;
import com.mysteel.banksteel.entity.PublishBuyData;
import com.mysteel.banksteel.view.activity.BuyDetailInfoActivity;
import com.mysteel.banksteel.view.activity.DetailStanBuyActivity;
import com.mysteel.banksteel.view.activity.HumanServeActivity;
import com.mysteel.banksteel.view.activity.SelectProSourceActivity;
import com.mysteel.banksteel.view.activity.StayQuoteBuyActivity;
import com.mysteel.banksteel.view.activity.VoiceRecognizeActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 我的求购adapter
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-9 上午11:39:14
 */
public class MyBuyAdapter extends BaseAdapter
{

	private LayoutInflater mInflater;
	private ArrayList<PagDatas> data;

	public MyBuyAdapter(LayoutInflater inflater, ArrayList<PagDatas> data)
	{
		mInflater = inflater;
		this.data = data;
	}

	public void reSetList(ArrayList<PagDatas> data)
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
			convertView = mInflater.inflate(R.layout.activity_mybuy_item, null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.brand = (TextView) convertView.findViewById(R.id.tv_brand);
			holder.btn = (TextView) convertView.findViewById(R.id.tv_btn);
			holder.place = (TextView) convertView.findViewById(R.id.tv_place);
			holder.ll_layout = (LinearLayout) convertView
					.findViewById(R.id.ll_layout);
			// holder.ll_btn = (LinearLayout)
			// convertView.findViewById(R.id.ll_btn);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		// Context context = mInflater.getContext();

		final PagDatas datas = data.get(position);

		if (TextUtils.isEmpty(datas.getBreedId()))
		{// 语音快捷求购
			holder.time.setText(datas.getPublishTime());// 时间
			holder.brand.setText("您的语音正在识别中...");
			holder.place.setText("请耐心等待!");
			holder.btn.setText("待识别");
			holder.btn.setBackgroundResource(R.drawable.green_btn);
			data.get(position).setSkipStatus(5);
		} else
		{
			holder.time.setText(datas.getPublishTime());// 时间
			holder.brand.setText(datas.getBreed() + "," + datas.getMaterial()
					+ " " + datas.getSpec());
			holder.place.setText(datas.getBrand() + " " + datas.getQty() + "吨"
					+ " " + datas.getCity());

			if ("9".equals(datas.getStatus()))// 状态 0-待报价 1-已报价 9-终止
			{
				holder.btn.setText("已取消");
				holder.btn.setBackgroundResource(R.drawable.gray);
				data.get(position).setSkipStatus(3);
			} else
			{
				if ("0".equals(datas.getDueStatus()))
				{// 0 求购单有效
					if ("0".equals(datas.getManual()))
					{// 0-未要求人工服务 1-已要求人工服务
						holder.btn.setText("待匹配");
						holder.btn.setBackgroundResource(R.drawable.blue);
						data.get(position).setSkipStatus(0);
					} else
					{
						if ("0".equals(datas.getRushStatus()))
						{// 抢单状态 0-待抢单 1-已抢单
							holder.btn.setText("待抢单");
							holder.btn.setBackgroundResource(R.drawable.orange);
							data.get(position).setSkipStatus(1);
						} else
						{// 为 1 ：已抢单
							if ("0".equals(datas.getStatus()))
							{// 状态 0-待报价 1-已报价 9-终止
								holder.btn.setText("待报价");
								holder.btn
										.setBackgroundResource(R.drawable.green_btn);
								data.get(position).setSkipStatus(2);
							} else if ("1".equals(datas.getStatus()))
							{
								holder.btn.setText("已报价");
								holder.btn
										.setBackgroundResource(R.drawable.red);
								data.get(position).setSkipStatus(4);
							}
						}
					}
				} else
				{// 1 求购单无效 为关闭状态
					holder.btn.setText("已取消");
					holder.btn.setBackgroundResource(R.drawable.gray);
					data.get(position).setSkipStatus(3);
				}
			}
		}

		final int num = position;

		holder.ll_layout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i;
				switch (data.get(num).getSkipStatus())
				{
				case 0:// 待匹配 SelectProSourceActivity生成订单页面
					i = new Intent(mInflater.getContext(),
							SelectProSourceActivity.class);
					i.putExtra("ID", data.get(num).getId());
					i.putExtra("status", "buy");
					mInflater.getContext().startActivity(i);
					break;
				case 1:// 待抢单
					i = new Intent(mInflater.getContext(),
							HumanServeActivity.class);
					i.putExtra("ID", data.get(num).getId());
					i.putExtra("status", "no");// 不需要请求转人工接口
					mInflater.getContext().startActivity(i);
					break;
				case 2:// 待报价
					PublishBuyData pbData = new PublishBuyData();
					pbData.setRushManagerName(datas.getRushManagerName());
					pbData.setDealCount(datas.getDealCount());
					pbData.setRushManagerHeader(datas.getRushManagerHeader());
					pbData.setBreedId(datas.getBreedId());
					pbData.setBreed(datas.getBreed());
					pbData.setMaterial(datas.getMaterial());
					pbData.setSpec(datas.getSpec());
					pbData.setBrand(datas.getBrand());
					pbData.setCity(datas.getCity());
					pbData.setQty(datas.getQty());
					pbData.setGapTime(datas.getGapTime());
					pbData.setPublishTime(datas.getPublishTime());
					pbData.setRushManagerPhone(datas.getRushManagerPhone());
					i = new Intent(mInflater.getContext(),
							StayQuoteBuyActivity.class);
					i.putExtra("data", pbData);
					mInflater.getContext().startActivity(i);
					break;
				case 3:// 已取消
					i = new Intent(mInflater.getContext(),
							BuyDetailInfoActivity.class);
					i.putExtra("parentSteel", "");// 传空
					i.putExtra("childSteel", data.get(num).getBreed());
					i.putExtra("childId", data.get(num).getBreedId());
					i.putExtra("material", data.get(num).getMaterial());
					i.putExtra("spec", data.get(num).getSpec());
					i.putExtra("brand", data.get(num).getBrand());
					i.putExtra("city", data.get(num).getCity());
					i.putExtra("qty", data.get(num).getQty());
					i.putExtra("contacter", data.get(num).getContacter());
					i.putExtra("phone", data.get(num).getPhone());
					mInflater.getContext().startActivity(i);
					break;
				case 4:// 已报价
						// Tools.commonDialogOneBtn(mInflater.getContext(), "",
						// "求购单正在转为订单，请稍后", "我知道了");
					i = new Intent(mInflater.getContext(),
							DetailStanBuyActivity.class);
					i.putExtra("ID", data.get(num).getId());
					i.putExtra("phoneNumber", data.get(num)
							.getRushManagerPhone());
					mInflater.getContext().startActivity(i);
					break;

				case 5:// 语音带识别
					i = new Intent(mInflater.getContext(),
							VoiceRecognizeActivity.class);
					i.putExtra("ID", data.get(num).getId());
					i.putExtra("DATE", data.get(num).getPublishTime());
					i.putExtra("URL", data.get(num).getAudioFileUrl());
					i.putExtra("CONTENT", data.get(num).getContent());
					i.putExtra("PHONE", data.get(num).getPhone());
					mInflater.getContext().startActivity(i);
					break;

				default:
					break;
				}
			}
		});

		return convertView;
	}

	public class ViewHolder
	{
		TextView time;// 时间
		TextView brand;// 语言待识别，或者是品牌材质规格等信息
		TextView btn;// 按钮
		TextView place;// 产地，吨位，库区
		LinearLayout ll_layout;
		// LinearLayout ll_btn;
	}

}

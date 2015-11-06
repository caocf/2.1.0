package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;
import com.mysteel.banksteel.view.activity.CompleteOrderActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 卖家已完成订单adapter
 * Created by zoujian on 15/8/10.
 */
public class SellCompleteOrderAdapter extends BaseAdapter
{

    private Context mContext;
    private ArrayList<Datas> mData;
    private LayoutInflater mInflater;

    public SellCompleteOrderAdapter(LayoutInflater inflater, ArrayList<Datas> intentionData)
    {
        mInflater = inflater;
        this.mContext = inflater.getContext();
        this.mData = intentionData;
    }

    public void reSetList(ArrayList<Datas> intentionData)
    {
        this.mData = intentionData;
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
            convertView = mInflater.inflate(R.layout.adapter_sell_complete_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
            holder.img_com_start01 = (ImageView) convertView.findViewById(R.id.img_com_start01);
            holder.img_com_start02 = (ImageView) convertView.findViewById(R.id.img_com_start02);
            holder.img_com_start03 = (ImageView) convertView.findViewById(R.id.img_com_start03);
            holder.img_com_start04 = (ImageView) convertView.findViewById(R.id.img_com_start04);
            holder.img_com_start05 = (ImageView) convertView.findViewById(R.id.img_com_start05);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final Datas datas = mData.get(position);
        holder.tv_name.setText(datas.getBreed() + "  " + datas.getMaterial() + "  " + datas.getSpec());
        holder.tv_brand.setText(datas.getBrand() + "  "+ datas.getQty() + "吨" + "  " + datas.getCity());
        holder.tv_time.setText(datas.getOrderTime());// 成交时间
        holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));

        String stars = datas.getTotalAppraise();
        if (!TextUtils.isEmpty(stars))
        {
            float fstarts = Float.parseFloat(stars);
            if (fstarts >= 4)
            {
                if (fstarts >= 4.5)
                {
                    fstarts = 5;
                } else
                {
                    fstarts = 4;
                }
            } else if (fstarts >= 3 && fstarts < 4)
            {
                if (fstarts >= 3.5)
                {
                    fstarts = 4;
                } else
                {
                    fstarts = 3;
                }
            } else if (fstarts >= 2 && fstarts < 3)
            {
                if (fstarts >= 2.5)
                {
                    fstarts = 3;
                } else
                {
                    fstarts = 2;
                }
            } else if (fstarts >= 1 && fstarts < 2)
            {
                if (fstarts >= 1.5)
                {
                    fstarts = 2;
                } else
                {
                    fstarts = 1;
                }
            } else
            {
                fstarts = 0;
            }

            changeStarts(holder.img_com_start01, holder.img_com_start02, holder.img_com_start03, holder.img_com_start04, holder.img_com_start05, (int) fstarts);
        } else
        {
            changeStarts(holder.img_com_start01, holder.img_com_start02, holder.img_com_start03, holder.img_com_start04, holder.img_com_start05, 0);
        }

        holder.ll_item_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, CompleteOrderActivity.class);
                i.putExtra("id", datas.getId());
                i.putExtra("tag", "1");//买家
                mContext.startActivity(i);
            }
        });
        return convertView;
    }

    public class ViewHolder
    {
        TextView tv_name;
        TextView tv_brand;
        TextView tv_time;
        LinearLayout ll_item_layout;
        ImageView img_com_start01;
        ImageView img_com_start02;
        ImageView img_com_start03;
        ImageView img_com_start04;
        ImageView img_com_start05;
    }

    private void changeStarts(ImageView start1, ImageView start2, ImageView start3, ImageView start4, ImageView start5, int num)
    {

        switch (num)
        {
            case 0:
                start1.setBackgroundResource(R.drawable.start_up);
                start2.setBackgroundResource(R.drawable.start_up);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 1:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_up);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 2:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_up);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 3:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_up);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 4:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_down);
                start5.setBackgroundResource(R.drawable.start_up);
                break;
            case 5:
                start1.setBackgroundResource(R.drawable.start_down);
                start2.setBackgroundResource(R.drawable.start_down);
                start3.setBackgroundResource(R.drawable.start_down);
                start4.setBackgroundResource(R.drawable.start_down);
                start5.setBackgroundResource(R.drawable.start_down);
                break;
        }
    }
}

package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.LogisticsOrderData.DataEntity.DatasEntity;
import com.mysteel.banksteel.view.activity.LogisticsAssessActivity;
import com.mysteel.banksteel.view.activity.LogisticsSelfOrderShowActivity;
import com.mysteel.banksteel.view.activity.LogisticsTrackActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 物流订单adapter
 * Created by zoujian on 15/8/17.
 */
public class LogisticsOrderAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DatasEntity> mData;

    public LogisticsOrderAdapter(LayoutInflater inflater, ArrayList<DatasEntity> data)
    {
        this.mContext = inflater.getContext();
        mInflater = inflater;
        this.mData = data;
    }

    public void reSetList(ArrayList<DatasEntity> data)
    {
        this.mData = data;
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.adapter_logistics_order_item, null);
            holder = new ViewHolder();
            holder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_yundan = (TextView) convertView.findViewById(R.id.tv_yundan);
            holder.tv_path = (TextView) convertView.findViewById(R.id.tv_path);
            holder.tv_breed = (TextView) convertView.findViewById(R.id.tv_breed);
            holder.tv_yaoqiu_time = (TextView) convertView.findViewById(R.id.tv_yaoqiu_time);
            holder.tv_jiage = (TextView) convertView.findViewById(R.id.tv_jiage);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.rl_logistics = (RelativeLayout) convertView.findViewById(R.id.rl_logistics);
            holder.rl_pinjia = (RelativeLayout) convertView.findViewById(R.id.rl_pinjia);
            holder.img_pinjia = (ImageView) convertView.findViewById(R.id.img_pinjia);
            holder.tv_pinjia = (TextView) convertView.findViewById(R.id.tv_pinjia);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_time.setText(mData.get(position).getCreateTime());
        holder.tv_yundan.setText(mData.get(position).getSteel56OrderId());

        holder.tv_breed.setText(mData.get(position).getBreed() + " " + mData.get(position).getTotalWeight() + "吨");
        holder.tv_yaoqiu_time.setText(mData.get(position).getStartTime());
        holder.tv_jiage.setText(mData.get(position).getPrice());
        holder.tv_path.setText(mData.get(position).getStartCity() + " " + mData.get(position).getStartArea()
                + "－" + mData.get(position).getEndCity() + " " + mData.get(position).getEndArea());

        //10 到达装货点20 提货完成 30 运输在途 40 到达卸货点
        if("10".equals(mData.get(position).getOrderStatus()))
        {
            holder.tv_status.setText("成交（到达装货点）");
        }else if("20".equals(mData.get(position).getOrderStatus()))
        {
            holder.tv_status.setText("成交（提货完成）");
        }else if("30".equals(mData.get(position).getOrderStatus()))
        {
            holder.tv_status.setText("成交（运输在途）");
        }else if("40".equals(mData.get(position).getOrderStatus()))
        {
            holder.tv_status.setText("成交（到达卸货点）");
        }

        final int num = position;
        holder.rl_logistics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, LogisticsTrackActivity.class);
                i.putExtra("ID", mData.get(num).getId());
                mContext.startActivity(i);
            }
        });

        if("40".equals(mData.get(position).getOrderStatus())){
            holder.rl_pinjia.setClickable(true);
            holder.img_pinjia.setImageResource(R.drawable.wuliupingjia2);
            holder.tv_pinjia.setTextColor(mContext.getResources().getColor(R.color.orange3));
            holder.rl_pinjia.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(mContext, LogisticsAssessActivity.class);
                    i.putExtra("orderID",mData.get(position).getSteel56OrderId());
                    i.putExtra("datas",mData.get(position));
                    mContext.startActivity(i);
                }
            });
        }else{
            holder.rl_pinjia.setClickable(false);
            holder.img_pinjia.setImageResource(R.drawable.wuliupingjia);
            holder.tv_pinjia.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
        }



        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, LogisticsSelfOrderShowActivity.class);
                i.putExtra("datas",mData.get(position));
                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        LinearLayout ll_main; //主要内容
        TextView tv_time;//创建时间
        TextView tv_yundan;//运单号
        TextView tv_path;//线路
        TextView tv_breed;//品种
        TextView tv_yaoqiu_time;//要求时间
        TextView tv_jiage;//价格
        TextView tv_status;//单号状态
        RelativeLayout rl_logistics;//物流跟踪
        RelativeLayout rl_pinjia;//物流评价
        ImageView img_pinjia; //评价图标
        TextView tv_pinjia; //评价文字

    }
}

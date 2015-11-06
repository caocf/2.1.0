package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.view.activity.SellBaoJaoDetailsActivity;
import com.mysteel.banksteeltwo.R;
import com.mysteel.banksteel.entity.MyQuotData.DataEntity.DatasEntity;

import java.util.ArrayList;

/**
 * 卖家订单中心：报价单adapter
 * Created by zoujian on 15/8/8.
 */
public class SellBaoJaiAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DatasEntity> mData;

    public SellBaoJaiAdapter(LayoutInflater inflater, ArrayList<DatasEntity> baojiaData)
    {
        this.mContext = inflater.getContext();
        mInflater = inflater;
        this.mData = baojiaData;
    }

    public void reSetList(ArrayList<DatasEntity> baojiaData)
    {
        this.mData = baojiaData;
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
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.adapter_mybuy_iten, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_yijia = (TextView) convertView.findViewById(R.id.tv_yijia);
            holder.ll_layout_wantbuy = (LinearLayout) convertView.findViewById(R.id.ll_layout_wantbuy);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final DatasEntity data = mData.get(position);
        holder.tv_yijia.setVisibility(View.VISIBLE);
        holder.tv_state.setText("我的报价");
        holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
        holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
        holder.tv_num.setText(data.getPrice() + "元/吨");
        holder.tv_time.setText(data.getQuotTime());

        //bargaining  0-不可议价 1-可议价
        if ("0".equals(data.getBargaining()))
        {
            holder.tv_yijia.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
            holder.tv_yijia.setText("一口价");
        } else if ("1".equals(data.getBargaining()))
        {
            holder.tv_yijia.setTextColor(mContext.getResources().getColor(R.color.main_imred));
            holder.tv_yijia.setText("可议价");
        }

        holder.ll_layout_wantbuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, SellBaoJaoDetailsActivity.class);
                i.putExtra("DATAS",data);
                mContext.startActivity(i);
            }
        });

        return convertView;
    }


    public class ViewHolder
    {
        TextView tv_name;//品名 规格
        TextView tv_state;//状态
        TextView tv_brand;//产地 重量 库区
        TextView tv_num;//份数
        TextView tv_time;//发布时间
        TextView tv_yijia;//一口价，可议价布局
        LinearLayout ll_layout_wantbuy;//整个布局
    }
}

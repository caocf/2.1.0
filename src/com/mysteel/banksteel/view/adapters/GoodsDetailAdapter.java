package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.entity.SubAreaData.DataEntity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 待付款中的的adapter
 * Created by wushaoge on 2015年10月28日09:24:17
 */
public class GoodsDetailAdapter extends BaseAdapter
{

    private ArrayList<DataEntity> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public GoodsDetailAdapter(LayoutInflater inflater)
    {
        this.mContext = inflater.getContext();
        mInflater = inflater;
    }

    public void reSetList(ArrayList<DataEntity> data)
    {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        if (mData == null)
        {
            return 3;
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
            convertView = mInflater.inflate(R.layout.item_goods_detail, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_material = (TextView) convertView.findViewById(R.id.tv_material);
            holder.tv_spec = (TextView) convertView.findViewById(R.id.tv_spec);
            holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holder.tv_ware = (TextView) convertView.findViewById(R.id.tv_ware);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder
    {
        TextView tv_name; //名字
        TextView tv_material; //材质
        TextView tv_spec; //规格
        TextView tv_weight; //重量
        TextView tv_ware; //仓库
    }
}

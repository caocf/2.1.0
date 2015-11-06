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
 * 物流选择市区的adapter
 * Created by zoujian on 15/8/12.
 */
public class AreaAdapter extends BaseAdapter
{

    private ArrayList<DataEntity> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public AreaAdapter(LayoutInflater inflater, ArrayList<DataEntity> data)
    {
        this.mContext = inflater.getContext();
        mInflater = inflater;
        this.mData = data;
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
            convertView = mInflater.inflate(R.layout.adapter_area_item, null);
            holder = new ViewHolder();
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        DataEntity data = mData.get(position);
        holder.tv_address.setText(data.getName());
        return convertView;
    }

    public class ViewHolder
    {
        TextView tv_address;//区名
    }
}

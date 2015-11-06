package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * Created by zoujian on 15/8/12.
 */
public class ChooseFoodTypeAdapter extends BaseAdapter
{
    private ArrayList<String> mData;
    private Context mContext;
    private LayoutInflater mInflater;


    public ChooseFoodTypeAdapter(LayoutInflater inflater, ArrayList<String> data)
    {
        this.mContext = inflater.getContext();
        mInflater = inflater;
        this.mData = data;
    }

    @Override
    public int getCount()
    {
        if(mData == null)
        {
            return 0;
        }
        return mData.size();
    }

    public void reSetList(ArrayList<String> data)
    {
        this.mData = data;
        notifyDataSetChanged();
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
        holder.tv_address.setText(mData.get(position));
        return convertView;
    }

    public class ViewHolder
    {
        TextView tv_address;//区名
    }
}

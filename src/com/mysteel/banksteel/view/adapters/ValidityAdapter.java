package com.mysteel.banksteel.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

/**
 * 有效期的选择
 * Created by zoujian on 15/8/24.
 */
public class ValidityAdapter extends BaseAdapter
{
    String[] mData = new String[]{"3", "4", "5", "6", "7", "8", "9", "10"};
    private LayoutInflater mInflater;

    public ValidityAdapter(LayoutInflater inflater)
    {
        mInflater = inflater;
    }

    @Override
    public int getCount()
    {
        return mData.length;
    }

    @Override
    public Object getItem(int position)
    {
        return mData[position];
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
        holder.tv_address.setText("有效期：" + mData[position] + "天");
        return convertView;
    }

    public class ViewHolder
    {
        TextView tv_address;
    }
}

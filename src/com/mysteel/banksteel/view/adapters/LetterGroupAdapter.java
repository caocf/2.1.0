package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

/**
 * @author:huoguodong
 * @date：2015-5-6 下午2:12:23
 */
public class LetterGroupAdapter extends BaseAdapter
{

    private LayoutInflater inflater;

    private List<String> dataList = new ArrayList<String>(); // 用于adapter适配的城市列表数据
    private boolean mFlag = false;//物流进来后字符串需要截取的标识

    public LetterGroupAdapter(Context context, List<String> dataList)
    {
        this.dataList = dataList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LetterGroupAdapter(Context context, List<String> dataList, boolean flag)
    {
        this.dataList = dataList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mFlag = flag;
    }


    @Override
    public int getCount()
    {
        if (dataList != null)
        {
            return dataList.size();
        }
        return 0;
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
        Holder holder = null;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_simple_item, null);
            holder = new Holder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.tv_selected_type_name);
            holder.tvLetter = (TextView) convertView
                    .findViewById(R.id.tv_item_letter);
            convertView.setTag(holder);
        } else
        {
            holder = (Holder) convertView.getTag();
        }
        if (Tools.isLetter(dataList.get(position)))
        {//是字母
            holder.tvName.setVisibility(View.GONE);
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(dataList.get(position));
        } else
        {
            holder.tvLetter.setVisibility(View.GONE);
            holder.tvName.setVisibility(View.VISIBLE);
            if(mFlag)
            {
                holder.tvName.setText(dataList.get(position).substring(0, dataList.get(position).length() - 6));
            }else
            {
                holder.tvName.setText(dataList.get(position));
            }

        }
        return convertView;
    }

    class Holder
    {
        public TextView tvLetter;
        public TextView tvName;
    }
}

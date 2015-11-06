package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.PageOrderData.Data.Datas;
import com.mysteel.banksteel.view.activity.IntentionOrderActivity;
import com.mysteel.banksteel.view.activity.SellIntentionDetailActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 卖家意向单adapter
 * Created by zoujian on 15/8/10.
 */
public class SellIntentionAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<Datas> mData;
    private LayoutInflater mInflater;

    public SellIntentionAdapter(LayoutInflater inflater, ArrayList<Datas> intentionData)
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
            convertView = mInflater.inflate(R.layout.adapter_sellintention_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.ll_layout_sellbuy = (LinearLayout) convertView.findViewById(R.id.ll_layout_sellbuy);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Datas datas = mData.get(position);

        holder.tv_name.setText(datas.getBreed() + "  " + datas.getMaterial() + "  " + datas.getSpec());
        holder.tv_brand.setText(datas.getBrand() + "  "+ datas.getQty() + "吨" + "  " + datas.getCity());
        holder.tv_time.setText(datas.getOrderTime());// 成交时间
        holder.tv_state.setVisibility(View.GONE);
        holder.tv_num.setTextSize(16);

        final String status = datas.getStatus();
        if ("0".equals(status))
        {// 待上传凭证
            holder.tv_num.setText("待上传凭证");
            holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
        } else if ("1".equals(status))
        {// 待审核
            holder.tv_num.setText("待审核");
            holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
        } else if ("2".equals(status))
        {// 审核通过 appraiseStatus 0:未评价 1:买家评论了卖家 2：卖家评论了买家 3:互评
            if ("0".equals(datas.getAppraiseStatus()) || "1".equals(datas.getAppraiseStatus()))
            {// 0-未评价 1 已评价
                holder.tv_num.setText("待评价");
                holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.main_imred));
            } else
            {
//                holder.tv_num.setText("已评价");
                holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
            }
        } else if ("9".equals(status))
        {// 审核不通过
            holder.tv_num.setText("审核未通过");
            holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
        } else if ("99".equals(status))
        {// 关闭
            holder.tv_num.setText("已关闭");
            holder.tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
        }

        final int num = position;
        holder.ll_layout_sellbuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, SellIntentionDetailActivity.class);
                i.putExtra("id", mData.get(num).getId());
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
        LinearLayout ll_layout_sellbuy;//整个布局
    }
}

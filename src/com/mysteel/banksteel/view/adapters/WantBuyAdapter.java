package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.view.activity.BuyAgainActivity;
import com.mysteel.banksteel.view.activity.BuyHasQuoteActivity;
import com.mysteel.banksteel.view.activity.VoiceRecognizeActivity;
import com.mysteel.banksteeltwo.R;
import com.mysteel.banksteel.entity.HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity;

import java.util.ArrayList;

/**
 * 求购单的adapter
 * Created by zoujian on 15/7/25.
 */
public class WantBuyAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DatasEntity> mData;

    public WantBuyAdapter(LayoutInflater inflater, ArrayList<DatasEntity> data)
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

//    @Override
//    public int getCount()
//    {
//        if (mData == null)
//        {
//            return 0;
//        }
//        return mData.size();
//    }
//
//    @Override
//    public Object getItem(int position)
//    {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position)
//    {
//        return position;
//    }
//
//    @Override
//    public int getSwipeLayoutResourceId(int position)
//    {
//        return R.id.ll_layout_wantbuy;
//    }
//
//    @Override
//    public View generateView(int position, ViewGroup parent)
//    {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_mybuy_item,
//                null);
//        SwipeLayout swipeLayout = (SwipeLayout) v
//                .findViewById(getSwipeLayoutResourceId(position));
//        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
//        {
//            @Override
//            public void onClose(SwipeLayout layout)
//            {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset,
//                                 int topOffset)
//            {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout)
//            {
////				YoYo.with(Techniques.Tada).duration(500).delay(100)
////						.playOn(layout.findViewById(R.id.trash));
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel)
//            {
//
//            }
//        });
//        return v;
//    }
//
//    @Override
//    public void fillValues(final int position, View convertView)
//    {
//
//        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
//        TextView tv_state = (TextView) convertView.findViewById(R.id.tv_state);
//        TextView tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
//        TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
//        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//        ImageView phone = (ImageView) convertView.findViewById(R.id.img_phone);
//        ImageView shejiao = (ImageView) convertView.findViewById(R.id.img_shejiao);
//
//        DatasEntity data = mData.get(position);
//
////        tv_name.setText("三级螺纹钢 HDR300E 50");
////        tv_state.setText("已收到报价");
////        tv_brand.setText("安丰 12.0吨 上海");
////        tv_num.setText("5份");
////        tv_time.setText("2015.07.25 15:10发布");
//
//        // 求购发布类型 buyType  0-快捷求购 1-语音求购 9-精准求购 quotNum是报价的数目
//        if("0".equals(data.getBuyType()))
//        {
//            tv_name.setText(data.getContent());
//            tv_brand.setText("文本解析中");
//            tv_time.setText(data.getPublishTime());
//            tv_num.setText(data.getQuotNum() + "份");
//        }else if("1".equals(data.getBuyType()))
//        {
//            tv_name.setText("您的求购内容正在解析中");
//            tv_brand.setText("请稍等");
//            tv_time.setText(data.getPublishTime());
//            tv_num.setText(data.getQuotNum() + "份");
//
//        }else if("9".equals(data.getBuyType()))
//        {
//            tv_name.setText(data.getBreed() + " " + data.getMaterial() + " " + data.getSpec());
//            tv_brand.setText(data.getBrand() + " " + data.getQty() + "吨" + " " + data.getCity());
//            tv_num.setText(data.getQuotNum() + "份");
//            tv_time.setText(data.getPublishTime());
//        }
//
//        if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))// 状态 0-待报价 1-已报价 9-终止   DueStatus 0 求购单有效 1无效
//        {
//            //已取消  main_blue  font_black_two
//            tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
//        }else
//        {
//            tv_time.setTextColor(mContext.getResources().getColor(R.color.main_blue));
//        }
//
//        phone.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Toast.makeText(mContext, "打电话 " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        shejiao.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Toast.makeText(mContext, "社交 " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

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
            holder.ll_layout_wantbuy = (LinearLayout) convertView.findViewById(R.id.ll_layout_wantbuy);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        final DatasEntity data = mData.get(position);

//        tv_name.setText("三级螺纹钢 HDR300E 50");
//        tv_state.setText("已收到报价");
//        tv_brand.setText("安丰 12.0吨 上海");
//        tv_num.setText("5份");
//        tv_time.setText("2015.07.25 15:10发布");

        // 求购发布类型 buyType  0-快捷求购 1-语音求购 9-精准求购 quotNum是报价的数目
        // buyType为0和1的时候判断status，status不为-1的时候为标准，否则，还是快捷求购
        if ("0".equals(data.getBuyType()))
        {
            if (!"-1".equals(data.getStatus()))
            {
                holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
                holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
                holder.tv_num.setText(data.getQuotNum() + "份");
                holder.tv_time.setText(data.getPublishTime());
            } else
            {
                holder.tv_name.setText(data.getContent());
                holder.tv_brand.setText("文本解析中");
                holder.tv_time.setText(data.getPublishTime());
                holder.tv_num.setText(data.getQuotNum() + "份");
            }
        } else if ("1".equals(data.getBuyType()))
        {
            if (!"-1".equals(data.getStatus()))
            {
                holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
                holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
                holder.tv_num.setText(data.getQuotNum() + "份");
                holder.tv_time.setText(data.getPublishTime());
            } else
            {
                holder.tv_name.setText("您的求购内容正在解析中");
                holder.tv_brand.setText("请稍等");
                holder.tv_time.setText(data.getPublishTime());
                holder.tv_num.setText(data.getQuotNum() + "份");
            }

        } else if ("9".equals(data.getBuyType()))
        {
            holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
            holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
            holder.tv_num.setText(data.getQuotNum() + "份");
            holder.tv_time.setText(data.getPublishTime());
        }

        if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))// 状态 0-待报价 1-已报价 9-终止   DueStatus 0 求购单有效 1无效
        {
            //已取消  main_blue  font_black_two
            holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
        } else
        {
            holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.main_blue));
        }

        final int num = position;
        holder.ll_layout_wantbuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = null;

                if ("9".equals(data.getBuyType()))//精准求购
                {
                    if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))
                    {//单子已经取消
                        i = new Intent(mContext, BuyAgainActivity.class);
                        i.putExtra("Datas", mData.get(num));
                        mContext.startActivity(i);
                    } else //单子在有效期内
                    {
                        i = new Intent(mContext, BuyHasQuoteActivity.class);
                        i.putExtra("Datas", mData.get(num));
                        mContext.startActivity(i);
                    }
                } else
                {
                    if (!"-1".equals(data.getStatus()))
                    {
                        if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))
                        {//单子已经取消
                            i = new Intent(mContext, BuyAgainActivity.class);
                            i.putExtra("Datas", mData.get(num));
                            mContext.startActivity(i);
                        } else //单子在有效期内
                        {
                            i = new Intent(mContext, BuyHasQuoteActivity.class);
                            i.putExtra("Datas", mData.get(num));
                            mContext.startActivity(i);
                        }
                    } else
                    {
                        i = new Intent(mInflater.getContext(), VoiceRecognizeActivity.class);
                        i.putExtra("ID", mData.get(num).getId());
                        i.putExtra("DATE", mData.get(num).getPublishTime());
                        i.putExtra("URL", mData.get(num).getAudioFileUrl());
                        i.putExtra("CONTENT", mData.get(num).getContent());
                        i.putExtra("PHONE", mData.get(num).getPhone());
                        i.putExtra("BUYTYPE", mData.get(num).getBuyType());
                        mInflater.getContext().startActivity(i);
                    }
                }

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
        LinearLayout ll_layout_wantbuy;//整个布局
    }
}

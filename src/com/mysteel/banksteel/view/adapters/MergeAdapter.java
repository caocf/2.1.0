package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.ConsultOrderData;
import com.mysteel.banksteel.view.activity.BuyAgainActivity;
import com.mysteel.banksteel.view.activity.BuyHasQuoteActivity;
import com.mysteel.banksteel.view.activity.CompleteOrderActivity;
import com.mysteel.banksteel.view.activity.IntentionOrderActivity;
import com.mysteel.banksteel.view.activity.VoiceRecognizeActivity;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;

/**
 * 撮合的适配器
 * Created by zoujian on 15/10/26.
 */
public class MergeAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ConsultOrderData.DatasEntity> datas;

    public MergeAdapter(LayoutInflater inflater, ArrayList<ConsultOrderData.DatasEntity> data)
    {
        this.mContext = inflater.getContext();
        this.datas = data;
        mInflater = inflater;
    }

    public void reSetList(ArrayList<ConsultOrderData.DatasEntity> data)
    {
        this.datas = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        if (datas == null)
        {
            return 0;
        }
        return datas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return datas.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_merge_iten, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.ll_layout_wantbuy = (LinearLayout) convertView.findViewById(R.id.ll_layout_wantbuy);
            holder.img_com_start01 = (ImageView) convertView.findViewById(R.id.img_com_start01);
            holder.img_com_start02 = (ImageView) convertView.findViewById(R.id.img_com_start02);
            holder.img_com_start03 = (ImageView) convertView.findViewById(R.id.img_com_start03);
            holder.img_com_start04 = (ImageView) convertView.findViewById(R.id.img_com_start04);
            holder.img_com_start05 = (ImageView) convertView.findViewById(R.id.img_com_start05);
            holder.ll_starts_layout = (LinearLayout) convertView.findViewById(R.id.ll_starts_layout);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        ConsultOrderData.DatasEntity data = datas.get(position);
        holder.ll_starts_layout.setVisibility(View.GONE);
        holder.tv_num.setVisibility(View.VISIBLE);

        //orderType 0:求购单 1:订单
        if ("0".equals(data.getOrderType()))
        {
            holder.tv_state.setVisibility(View.VISIBLE);
            // 求购发布类型 buyType  0-快捷求购 1-语音求购 9-精准求购 quotNum是报价的数目
            // buyType为0和1的时候判断status，status不为-1的时候为标准，否则，还是快捷求购
            if ("0".equals(data.getBuyType()))
            {
                if (!"-1".equals(data.getStatus()))
                {
                    holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
                    holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
                    holder.tv_num.setText(data.getQuotNum() + "份");
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.main_imred));
                    holder.tv_time.setText(data.getPublishTime());
                } else
                {
                    holder.tv_name.setText(data.getContent());
                    holder.tv_brand.setText("文本解析中");
                    holder.tv_time.setText(data.getPublishTime());
                    holder.tv_num.setText(data.getQuotNum() + "份");
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.main_imred));
                }
            } else if ("1".equals(data.getBuyType()))
            {
                if (!"-1".equals(data.getStatus()))
                {
                    holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
                    holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
                    holder.tv_num.setText(data.getQuotNum() + "份");
                    holder.tv_time.setText(data.getPublishTime());
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.main_imred));
                } else
                {
                    holder.tv_name.setText("您的求购内容正在解析中");
                    holder.tv_brand.setText("请稍等");
                    holder.tv_time.setText(data.getPublishTime());
                    holder.tv_num.setText(data.getQuotNum() + "份");
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.main_imred));
                }

            } else if ("9".equals(data.getBuyType()))
            {
                holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial() + "  " + data.getSpec());
                holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨" + "  " + data.getCity());
                holder.tv_num.setText(data.getQuotNum() + "份");
                holder.tv_time.setText(data.getPublishTime());
                holder.tv_num.setTextColor(mContext.getResources().getColor(
                        R.color.main_imred));
            }

            if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))// 状态 0-待报价 1-已报价 9-终止   DueStatus 0 求购单有效 1无效
            {
                //已取消  main_blue  font_black_two
                holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
            } else
            {
                holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.main_blue));
            }
        } else if ("1".equals(data.getOrderType()))
        {
            holder.tv_state.setVisibility(View.GONE);
            holder.tv_name.setText(data.getBreed() + "  " + data.getMaterial()
                    + "  " + data.getSpec());
            holder.tv_brand.setText(data.getBrand() + "  " + data.getQty() + "吨"
                    + "  " + data.getCity());
            holder.tv_time.setText(data.getOrderTime());//  成交时间
            holder.tv_state.setVisibility(View.GONE);
            holder.tv_num.setTextSize(16);

            if ("0".equals(data.getStatus()))
            {// 待上传凭证
                holder.tv_num.setText("请上传凭证");
                holder.tv_num.setTextColor(mContext.getResources().getColor(
                        R.color.main_imred));
            } else if ("1".equals(data.getStatus()))
            {// 待审核
                holder.tv_num.setText("待审核");
                holder.tv_num.setTextColor(mContext.getResources().getColor(
                        R.color.inside_font_gray_little));
            } else if ("2".equals(data.getStatus()))
            {// 审核通过 appraiseStatus 0:未评价 1:买家评论了卖家 2：卖家评论了买家 3:互评
                if ("0".equals(data.getAppraiseStatus())
                        || "2".equals(data.getAppraiseStatus()))
                {// 0-未评价 1 已评价
                    holder.tv_num.setText("待评价");
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.main_imred));
                } else if ("1".equals(data.getAppraiseStatus()) || "3".equals(data.getAppraiseStatus()))
                {
                    holder.tv_num.setVisibility(View.GONE);
                    holder.ll_starts_layout.setVisibility(View.VISIBLE);
                    holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
                    holder.tv_num.setText("已完成");
                    holder.tv_num.setTextColor(mContext.getResources().getColor(
                            R.color.inside_font_gray_little));

                    if (!"null".equals(data.getTotalAppraise()))
                    {
                        float fstarts = Float.parseFloat(data.getTotalAppraise());
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

                }

            } else if ("9".equals(data.getStatus()))
            {// 审核不通过
                holder.tv_num.setText("审核未通过");
                holder.tv_num.setTextColor(mContext.getResources().getColor(
                        R.color.inside_font_gray_little));
            } else if ("99".equals(data.getStatus()))
            {// 关闭
                holder.tv_num.setText("已关闭");
                holder.tv_num.setTextColor(mContext.getResources().getColor(
                        R.color.inside_font_gray_little));
            }
        }


        if ("0".equals(data.getOrderType()))//求购单
        {
            if ("9".equals(data.getBuyType()))//精准求购
            {
                if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))
                {//单子已经取消
                    datas.get(position).setJumpNum(1);
                } else //单子在有效期内
                {
                    datas.get(position).setJumpNum(2);
                }
            } else
            {
                if (!"-1".equals(data.getStatus()))
                {
                    if ("9".equals(data.getStatus()) || "1".equals(data.getDueStatus()))
                    {//单子已经取消
                        datas.get(position).setJumpNum(3);
                    } else //单子在有效期内
                    {
                        datas.get(position).setJumpNum(4);
                    }
                } else
                {
                    datas.get(position).setJumpNum(5);
                }
            }
        } else if ("1".equals(data.getOrderType()))//订单
        {
            datas.get(position).setJumpNum(6);
            if ("0".equals(data.getStatus()))
            {// 待上传凭证
            } else if ("1".equals(data.getStatus()))
            {// 待审核
            } else if ("2".equals(data.getStatus()))
            {// 审核通过 appraiseStatus 0:未评价 1:买家评论了卖家 2：卖家评论了买家 3:互评
                if ("0".equals(data.getAppraiseStatus())
                        || "2".equals(data.getAppraiseStatus()))
                {// 0-未评价 1 已评价
                } else if ("1".equals(data.getAppraiseStatus()) || "3".equals(data.getAppraiseStatus()))
                {
                    datas.get(position).setJumpNum(7);
                    // tv_num.setText("已评价");
                }
            } else if ("9".equals(data.getStatus()))
            {// 审核不通过
            } else if ("99".equals(data.getStatus()))
            {// 关闭
            }
        }

        final int num = position;

        holder.ll_layout_wantbuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i;
                switch (datas.get(num).getJumpNum())
                {
                    case 1://精准求购，单子已取消
                    case 3://单子已取消（标准化）
                        i = new Intent(mContext, BuyAgainActivity.class);
                        i.putExtra("Datas", datas.get(num));
                        mContext.startActivity(i);
                        break;
                    case 2://精准求购，单子在有效期内
                    case 4://单子在有效期内（标准化）
                        i = new Intent(mContext, BuyHasQuoteActivity.class);
                        i.putExtra("Datas", datas.get(num));
                        mContext.startActivity(i);
                        break;
//                    case 3://单子已取消（标准化）
//                        i = new Intent(mContext, BuyAgainActivity.class);
//                        i.putExtra("Datas", datas.get(num));
//                        mContext.startActivity(i);
//                        break;
//                    case 4://单子在有效期内（标准化）
//                        i = new Intent(mContext, BuyHasQuoteActivity.class);
//                        i.putExtra("Datas", datas.get(num));
//                        mContext.startActivity(i);
//                        break;
                    case 5://快捷求购
                        i = new Intent(mInflater.getContext(), VoiceRecognizeActivity.class);
                        i.putExtra("ID", datas.get(num).getId());
                        i.putExtra("DATE", datas.get(num).getPublishTime());
                        i.putExtra("URL", datas.get(num).getAudioFileUrl());
                        i.putExtra("CONTENT", datas.get(num).getContent());
                        i.putExtra("PHONE", datas.get(num).getPhone());
                        i.putExtra("BUYTYPE", datas.get(num).getBuyType());
                        mContext.startActivity(i);
                        break;
                    case 6://订单
                        i = new Intent(mContext, IntentionOrderActivity.class);
                        i.putExtra("id", datas.get(num).getId());
                        mContext.startActivity(i);
                        break;
                    case 7://已完成
                        i = new Intent(mContext, CompleteOrderActivity.class);
                        i.putExtra("id", datas.get(num).getId());
                        i.putExtra("tag", "0");//买家
                        mContext.startActivity(i);
                        break;
                }
            }
        });


        return convertView;
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

    public class ViewHolder
    {
        TextView tv_name;//品名 规格
        TextView tv_state;//状态
        TextView tv_brand;//产地 重量 库区
        TextView tv_num;//份数
        TextView tv_time;//发布时间
        LinearLayout ll_layout_wantbuy;//整个布局
        ImageView img_com_start01;
        ImageView img_com_start02;
        ImageView img_com_start03;
        ImageView img_com_start04;
        ImageView img_com_start05;
        LinearLayout ll_starts_layout;
    }
}

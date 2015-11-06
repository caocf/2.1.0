package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.PageOrderData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ChatActivity;
import com.mysteel.banksteel.view.activity.CompleteOrderActivity;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteeltwo.R;

/**
 * Created by zoujian on 15/8/1.
 */
public class CompleteOrderAdapter extends BaseAdapter implements IOrderTradeView
{

    private Context mContext;
    private ArrayList<PageOrderData.Data.Datas> mData;
//    private SwipeLayout swipeLayout;
    private IOrderTrade orderTrade;
    private int listNum;

    public CompleteOrderAdapter(Context context, ArrayList<PageOrderData.Data.Datas> intentionData)
    {
        this.mContext = context;
        this.mData = intentionData;
        orderTrade = new OrderTradeImpl(mContext, this);
    }

    public void reSetList(ArrayList<PageOrderData.Data.Datas> intentionData)
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
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

//    @Override
//    public int getSwipeLayoutResourceId(int position)
//    {
//        return R.id.ll_layout_complete;
//    }

//    @Override
//    public View generateView(int position, ViewGroup parent)
//    {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_complete_item,
//                null);
//        swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
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

//    @Override
//    public void fillValues(int position, View convertView)
//    {
//        PageOrderData.Data.Datas datas = mData.get(position);
//        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
////        TextView tv_state = (TextView) convertView.findViewById(R.id.tv_state);
//        TextView tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
////        TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
//        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//        LinearLayout phone = (LinearLayout) convertView.findViewById(R.id.ll_item_phone);
//        LinearLayout shejiao = (LinearLayout) convertView.findViewById(R.id.ll_item_msg);
//        LinearLayout ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
//        ImageView img_com_start01 = (ImageView) convertView.findViewById(R.id.img_com_start01);
//        ImageView img_com_start02 = (ImageView) convertView.findViewById(R.id.img_com_start02);
//        ImageView img_com_start03 = (ImageView) convertView.findViewById(R.id.img_com_start03);
//        ImageView img_com_start04 = (ImageView) convertView.findViewById(R.id.img_com_start04);
//        ImageView img_com_start05 = (ImageView) convertView.findViewById(R.id.img_com_start05);
//
//        tv_name.setText(datas.getBreed() + "  " + datas.getMaterial() + "  " + datas.getSpec());
//        tv_brand.setText(datas.getBrand() + "  "+ datas.getQty() + "吨" + "  " + datas.getCity());
//        tv_time.setText(datas.getOrderTime());// 成交时间
//        tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));
//
//        String stars = datas.getTotalAppraise();
//        if (!TextUtils.isEmpty(stars))
//        {
//            float fstarts = Float.parseFloat(stars);
//            if (fstarts >= 4)
//            {
//                if (fstarts >= 4.5)
//                {
//                    fstarts = 5;
//                } else
//                {
//                    fstarts = 4;
//                }
//            } else if (fstarts >= 3 && fstarts < 4)
//            {
//                if (fstarts >= 3.5)
//                {
//                    fstarts = 4;
//                } else
//                {
//                    fstarts = 3;
//                }
//            } else if (fstarts >= 2 && fstarts < 3)
//            {
//                if (fstarts >= 2.5)
//                {
//                    fstarts = 3;
//                } else
//                {
//                    fstarts = 2;
//                }
//            } else if (fstarts >= 1 && fstarts < 2)
//            {
//                if (fstarts >= 1.5)
//                {
//                    fstarts = 2;
//                } else
//                {
//                    fstarts = 1;
//                }
//            }else {
//                fstarts = 0;
//            }
//
//            changeStarts(img_com_start01, img_com_start02, img_com_start03, img_com_start04, img_com_start05, (int) fstarts);
//        } else
//        {
//            changeStarts(img_com_start01, img_com_start02, img_com_start03, img_com_start04, img_com_start05, 0);
//        }
//
//
////        tv_state.setVisibility(View.GONE);
////        tv_num.setTextSize(16);
////        tv_num.setText("请上传凭证");
//
//
////        final String status = datas.getStatus();
////        if ("0".equals(status))
////        {// 待上传凭证
////            tv_num.setText("上传凭证");
////            tv_num.setTextColor(mContext.getResources().getColor(R.color.main_imred));
////        } else if ("1".equals(status))
////        {// 待审核
////            tv_num.setText("待审核");
////            tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
////        } else if ("2".equals(status))
////        {// 审核通过
////            if ("0".equals(datas.getAppraiseStatus()))
////            {// 0-未评价 1 已评价
////                tv_num.setText("待评价");
////                tv_num.setTextColor(mContext.getResources().getColor(R.color.main_imred));
////            } else if ("1".equals(datas.getAppraiseStatus()))
////            {
////                tv_num.setText("已评价");
////                tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
////            }
////        } else if ("9".equals(status))
////        {// 审核不通过
////            tv_num.setText("审核未通过");
////            tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
////        } else if ("99".equals(status))
////        {// 关闭
////            tv_num.setText("已关闭");
////            tv_num.setTextColor(mContext.getResources().getColor(R.color.inside_font_gray_little));
////        }
//
//
//        final int num = position;
//        phone.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
////                Toast.makeText(mContext, "打电话 " + num, Toast.LENGTH_SHORT).show();
//                if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
//                {
//                    Tools.makeCall(mContext, mData.get(num).getQuotPhone());
//                } else
//                {
//                    Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        shejiao.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                listNum = num;
//                if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
//                {
//                    String url = RequestUrl.getInstance(mContext).getUrl_Register(mContext, mData.get(num).getQuotPhone(), "");
//                    orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
//                } else
//                {
//                    Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        ll_item_layout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent i = new Intent(mContext, CompleteOrderActivity.class);
//                i.putExtra("id", mData.get(num).getId());
//                i.putExtra("tag", "0");//买家
//                mContext.startActivity(i);
//            }
//        });
//
//    }


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

    @Override
    public void updateView(BaseData data)
    {
        Intent i = new Intent(mContext, ChatActivity.class);
        i.putExtra("userId", mData.get(listNum).getQuotPhone());
        i.putExtra("userName", mData.get(listNum).getQuotUserName());
        mContext.startActivity(i);
    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    public IOrderTrade getOrderTrade()
    {
        return orderTrade;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_complete_item, parent, false);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.phone = (LinearLayout) convertView.findViewById(R.id.ll_item_phone);
			holder.shejiao = (LinearLayout) convertView.findViewById(R.id.ll_item_msg);
			holder.item_right = (LinearLayout) convertView.findViewById(R.id.item_right);
			holder.ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
			holder.img_com_start01 = (ImageView) convertView.findViewById(R.id.img_com_start01);
			holder.img_com_start02 = (ImageView) convertView.findViewById(R.id.img_com_start02);
			holder.img_com_start03 = (ImageView) convertView.findViewById(R.id.img_com_start03);
			holder.img_com_start04 = (ImageView) convertView.findViewById(R.id.img_com_start04);
			holder.img_com_start05 = (ImageView) convertView.findViewById(R.id.img_com_start05);
			convertView.setTag(holder);
		} else
		{// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		
		LinearLayout.LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		holder.ll_item_layout.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(510,
				LayoutParams.MATCH_PARENT);
		holder.item_right.setLayoutParams(lp2);
		
		PageOrderData.Data.Datas datas = mData.get(position);
		holder.tv_name.setText(datas.getBreed() + "  " + datas.getMaterial() + "  " + datas.getSpec());
		holder.tv_brand.setText(datas.getBrand() + "  "+ datas.getQty() + "吨" + "  " + datas.getCity());
		holder.tv_time.setText(datas.getOrderTime());// 成交时间
		holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.font_black_two));

        String stars = datas.getTotalAppraise();
        if (!TextUtils.isEmpty(stars))
        {
            float fstarts = Float.parseFloat(stars);
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
            }else {
                fstarts = 0;
            }

            changeStarts(holder.img_com_start01, holder.img_com_start02, holder.img_com_start03, holder.img_com_start04, holder.img_com_start05, (int) fstarts);
        } else
        {
            changeStarts(holder.img_com_start01, holder.img_com_start02, holder.img_com_start03, holder.img_com_start04, holder.img_com_start05, 0);
        }
        
        final int num = position;
        holder.phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(mContext, "打电话 " + num, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
                {
                    Tools.makeCall(mContext, mData.get(num).getQuotPhone());
                } else
                {
                    Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.shejiao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listNum = num;
                if (!TextUtils.isEmpty(mData.get(num).getQuotPhone()))
                {
                    String url = RequestUrl.getInstance(mContext).getUrl_Register(mContext, mData.get(num).getQuotPhone(), "");
                    orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
                } else
                {
                    Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.ll_item_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, CompleteOrderActivity.class);
                i.putExtra("id", mData.get(num).getId());
                i.putExtra("tag", "0");//买家
                mContext.startActivity(i);
            }
        });

		return convertView;
	}
	
	public class ViewHolder
	{
		TextView tv_name;
		TextView tv_brand;
		TextView tv_time;
		LinearLayout phone;
		LinearLayout shejiao;
		LinearLayout ll_item_layout;
		LinearLayout item_right;
		ImageView img_com_start01;
		ImageView img_com_start02;
		ImageView img_com_start03;
		ImageView img_com_start04;
		ImageView img_com_start05;
	}
}

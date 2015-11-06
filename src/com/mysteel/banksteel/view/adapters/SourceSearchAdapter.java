package com.mysteel.banksteel.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.SearchResourceData.Data.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ChatActivity;
import com.mysteel.banksteel.view.activity.LoginActivity;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.List;

public class SourceSearchAdapter extends BaseAdapter implements IOrderTradeView
{

	@Override
	public void updateView(BaseData data)
	{
		if (Tools.isLogin(mContext))
		{
			Intent i = new Intent(mContext, ChatActivity.class);
			i.putExtra("userId", datas.get(listNum).getPhone());
			i.putExtra("userName", datas.get(listNum).getUserName());
			mContext.startActivity(i);
		} else
		{
			Intent i = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(i);
		}
	}
	private int mRightWidth = 0;

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	public interface ClickToggleItem
	{
		void selOpenItem(int position);
	}

	private Context mContext;

	private ClickToggleItem clickToggleItem;
	private IOrderTrade orderTrade;
	private int listNum;

	private List<Datas> datas = new ArrayList<Datas>();

	public SourceSearchAdapter(Context mContext, int rightWidth)
	{
		this.mContext = mContext;
		orderTrade = new OrderTradeImpl(mContext, this);
		this.mRightWidth = rightWidth;
	}

	public void reSetListView(ArrayList<Datas> datas)
	{
		this.datas = datas;
		notifyDataSetChanged();
	}

	public void setClickToggleItem(ClickToggleItem clickToggleItem)
	{
		this.clickToggleItem = clickToggleItem;
	}

	// @Override
	// public int getSwipeLayoutResourceId(int position) {
	// return R.id.swipe;
	// }

	// @Override
	// public View generateView(int position, ViewGroup parent) {
	// View v =
	// LayoutInflater.from(mContext).inflate(R.layout.listview_search_item,
	// null);
	// SwipeLayout swipeLayout = (SwipeLayout) v
	// .findViewById(getSwipeLayoutResourceId(position));
	// swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
	// swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
	//
	// // Datas datasTemp = datas.get(position);
	// // if(datasTemp.isToogleItemFlag()){
	// // swipeLayout.open();
	// // }else{
	// // swipeLayout.close();
	// // }
	// swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
	// @Override
	// public void onClose(SwipeLayout layout) {
	//
	// }
	//
	// @Override
	// public void onUpdate(SwipeLayout layout, int leftOffset,
	// int topOffset) {
	//
	// }
	//
	// @Override
	// public void onOpen(SwipeLayout layout) {
	// //LogUtils.e("打开啦啦啦");
	// //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
	// }
	//
	// @Override
	// public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
	//
	// }
	// });
	// return v;
	// }

	// @Override
	// public void fillValues(int position, View convertView) {
	// TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
	// TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
	// TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
	// TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
	// TextView tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
	// TextView tv_company = (TextView)
	// convertView.findViewById(R.id.tv_company);
	// TextView tv_sell = (TextView) convertView.findViewById(R.id.tv_sell);
	// tv_sell.setVisibility(View.GONE);
	// LinearLayout ll_item_phone = (LinearLayout)
	// convertView.findViewById(R.id.ll_item_phone);
	// LinearLayout ll_item_msg = (LinearLayout)
	// convertView.findViewById(R.id.ll_item_msg);
	//
	//
	// final Datas datasTemp = datas.get(position);
	//
	// tv_name.setText(datasTemp.getBreedName());
	// tv_type.setText(datasTemp.getMaterial());
	// tv_num.setText(datasTemp.getSpec());
	//
	// if(Float.parseFloat(datasTemp.getPrice())<1000){
	// tv_price.setText("面议价");
	// tv_unit.setVisibility(View.INVISIBLE);
	// }else{
	// tv_price.setText(datasTemp.getPrice());
	// tv_unit.setVisibility(View.VISIBLE);
	// }
	// tv_company.setText(datasTemp.getMemberName());
	//
	// ll_item_phone.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// if(!TextUtils.isEmpty(datasTemp.getPhone())){
	// Tools.makeCall(mContext, datasTemp.getPhone());
	// }else{
	// Tools.showToast(mContext, "货主尚未上传他的手机号!");
	// }
	// }
	// });
	//
	// listNum = position;
	// ll_item_msg.setOnClickListener(new OnClickListener()
	// {
	// @Override
	// public void onClick(View v)
	// {
	// if (!TextUtils.isEmpty(datasTemp.getPhone()))
	// {
	// String url = RequestUrl.getInstance(mContext).getUrl_Register(mContext,
	// datasTemp.getPhone(), "");
	// orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
	// } else
	// {
	// Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
	// }
	// }
	// });
	//
	// }

	@Override
	public int getCount()
	{
		if (datas.size() > 0)
		{
			return datas.size();
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
		return position;
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
					R.layout.listview_search_item, parent, false);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			holder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
			holder.tv_company = (TextView) convertView
					.findViewById(R.id.tv_company);
			holder.tv_sell = (TextView) convertView.findViewById(R.id.tv_sell);
			holder.ll_item_phone = (LinearLayout) convertView
					.findViewById(R.id.ll_item_phone);
			holder.ll_item_msg = (LinearLayout) convertView
					.findViewById(R.id.ll_item_msg);
			holder.item_left = (LinearLayout) convertView
					.findViewById(R.id.item_left);
			holder.item_right = (LinearLayout) convertView
					.findViewById(R.id.item_right);
			convertView.setTag(holder);
		} else
		{// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_sell.setVisibility(View.GONE);
		LinearLayout.LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		holder.item_left.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth,
				LayoutParams.MATCH_PARENT);
		holder.item_right.setLayoutParams(lp2);

		final Datas datasTemp = datas.get(position);

		holder.tv_name.setText(datasTemp.getBreedName());
		holder.tv_type.setText(datasTemp.getMaterial());
		holder.tv_num.setText(datasTemp.getSpec());

		if (Float.parseFloat(datasTemp.getPrice()) < 1000)
		{
			holder.tv_price.setText("面议价");
			holder.tv_unit.setVisibility(View.INVISIBLE);
		} else
		{
			holder.tv_price.setText(datasTemp.getPrice());
			holder.tv_unit.setVisibility(View.VISIBLE);
		}
		if(!TextUtils.isEmpty(datasTemp.getWarehouse())){
			holder.tv_company.setText(datasTemp.getWarehouse());
		}else{
			holder.tv_company.setText("厂提");
		}

		holder.ll_item_phone.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!TextUtils.isEmpty(datasTemp.getPhone()))
				{
					Tools.makeCall(mContext, datasTemp.getPhone());
				} else
				{
					Tools.showToast(mContext, "货主尚未上传他的手机号!");
				}
			}
		});

		listNum = position;
		holder.ll_item_msg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!TextUtils.isEmpty(datasTemp.getPhone()))
				{
					String url = RequestUrl
							.getInstance(mContext)
							.getUrl_Register(mContext, datasTemp.getPhone(), "");
					orderTrade.getHuanXinRegister(url,
							Constants.INTERFACE_hxuserregister);
				} else
				{
					Toast.makeText(mContext, "暂时获取不到卖家电话！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		return convertView;
	}

	public class ViewHolder
	{
		TextView tv_name;
		TextView tv_type;
		TextView tv_num;
		TextView tv_price;
		TextView tv_unit;
		TextView tv_company;
		TextView tv_sell;
		LinearLayout ll_item_phone;
		LinearLayout ll_item_msg;
		LinearLayout item_left;
		LinearLayout item_right;
	}
}

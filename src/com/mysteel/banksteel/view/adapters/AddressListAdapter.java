package com.mysteel.banksteel.view.adapters;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.ao.impl.AddressManagerImpl;
import com.mysteel.banksteel.entity.AddressListData;
import com.mysteel.banksteel.entity.AddressListData.Data;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.view.activity.EditorAddressActivity;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteel.view.ui.TwoButtonDialog.Builder;
import com.mysteel.banksteeltwo.R;

public class AddressListAdapter extends BaseAdapter
{

	private static Context mContext;
	private List<AddressListData.Data> listData;
	private static AddressManagerImpl addressManagerImpl;

	public void addressFinishRequest()
	{
		if (addressManagerImpl != null)
		{
			addressManagerImpl.finishRequest();
		}
		
	}

	public static synchronized void setAddressManagerImpl(
			AddressManagerImpl addressManagerImpl)
	{
		AddressListAdapter.addressManagerImpl = addressManagerImpl;
	}

	public AddressListAdapter(Context context, List<Data> listData)
	{
		mContext = context;
		this.listData = listData;
		addressManagerImpl = new AddressManagerImpl(mContext);
	}

	@Override
	public int getCount()
	{
		if (listData.size() != 0)
		{
			return listData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return listData.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_setaddress, null);
			holder = new ViewHolder();
			/** 初始化 */
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_address_name);
			holder.tvPhone = (TextView) convertView
					.findViewById(R.id.tv_address_phone);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_address_address);
			holder.ivIsDefault = (ImageView) convertView
					.findViewById(R.id.iv_address_selected);
			holder.tvDefaultAddress = (TextView) convertView
					.findViewById(R.id.tv_address_defualtaddress);
			holder.llModify = (LinearLayout) convertView
					.findViewById(R.id.ll_address_modify);
			holder.llDelete = (LinearLayout) convertView
					.findViewById(R.id.ll_address_delete);
			holder.llDefaultAddress = (LinearLayout) convertView
					.findViewById(R.id.ll_address_default);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		AddressListData.Data data = listData.get(position);

		/** 设置默认地址并监听 */
		holder.llDefaultAddress.setTag(data.getId());
		setDefaultAddress(holder, data);

		/** 设置昵称,电话 */
		holder.tvName.setText(data.getConsignee());
		holder.tvPhone.setText(data.getMobile());

		/** 设置详细地址 */
		String address = "";
		if (data.getProvince().equals(data.getCity()))
		{
			address = data.getCity() + data.getDistrict() + data.getAddress();
		} else
		{
			address = data.getProvince() + data.getCity() + data.getDistrict()
					+ data.getAddress();
		}
		holder.tvAddress.setText(address);

		/** 编辑地址,传整个data数据 */
		holder.llModify.setTag(data);
		holder.llModify.setOnClickListener(EditOnClickListener.getInstance());

		/** 删除地址,传AdresssId */
		holder.llDelete.setTag(data.getId());
		holder.llDelete.setOnClickListener(DeleteOnClickListener.getInstance());

		return convertView;
	}

	/**
	 * 判断并设置默认地址
	 * 
	 * @param holder
	 * @param data
	 */
	private void setDefaultAddress(ViewHolder holder, AddressListData.Data data)
	{
		if ("1".equals(data.getIsDefault()))
		{
			// 是默认地址
			holder.ivIsDefault
					.setBackgroundResource(R.drawable.address_selected);
			holder.tvDefaultAddress.setText("默认地址");
			holder.tvDefaultAddress.setTextColor(mContext.getResources()
					.getColor(R.color.font_blue));
		} else
		{
			// 不是默认地址
			holder.ivIsDefault
					.setBackgroundResource(R.drawable.address_noselected);
			holder.tvDefaultAddress.setText("设为默认地址");
			holder.tvDefaultAddress.setTextColor(mContext.getResources()
					.getColor(R.color.font_gray));

			// 设置tag并监听点击事件 ,把收货地址Id放入tag中
			holder.llDefaultAddress.setOnClickListener(DefaultOnClickListener
					.getInstance());
		}
	}

	class ViewHolder
	{
		private TextView tvName;// 昵称
		private TextView tvPhone;// 电话
		private TextView tvAddress;// 地址
		private ImageView ivIsDefault;// 默认地址图片
		private TextView tvDefaultAddress;// 地址时候默认
		private LinearLayout llModify;// 编辑地址
		private LinearLayout llDelete;// 删除地址
		private LinearLayout llDefaultAddress;// 设为默认地址
	}

	/**
	 * @author 68 设为默认地址的监听器
	 */
	static class DefaultOnClickListener implements OnClickListener
	{

		private static DefaultOnClickListener instance = null;

		private DefaultOnClickListener()
		{
		}

		/** 单例 */
		public static synchronized DefaultOnClickListener getInstance()
		{
			if (instance == null)
				instance = new DefaultOnClickListener();
			return instance;
		}

		@Override
		public void onClick(View view)
		{
			/** 执行 设置默认收货地址 协议 */
			String addressId = (String) ((LinearLayout) view).getTag();
			String userId = SharePreferenceUtil.getString(mContext,
					Constants.PREFERENCES_USERID);
			// 获得url
			String url = RequestUrl.getInstance(mContext)
					.getUrl_setDefaultAddress(userId, addressId);
			addressManagerImpl.setDefualtAddress(url,
					Constants.INTERFACE_setDefaultAddress);
		}

	}

	/**
	 * @author 68 编辑地址
	 */
	static class EditOnClickListener implements OnClickListener
	{

		private static EditOnClickListener instance = null;

		private EditOnClickListener()
		{
		}

		/** 单例 */
		public static synchronized EditOnClickListener getInstance()
		{
			if (instance == null)
				instance = new EditOnClickListener();
			return instance;
		}

		@Override
		public void onClick(View view)
		{
			/** 跳转到编辑地址页面,传递 AddressListData.Data 参数 */
			AddressListData.Data data = (AddressListData.Data) ((LinearLayout) view)
					.getTag();
			Intent intent = new Intent(mContext, EditorAddressActivity.class);
			intent.putExtra("DATA", data);
			intent.putExtra("TAG_ADDRESS", Constants.EDIT_ADDRESS);
			mContext.startActivity(intent);
		}

	}

	/**
	 * @author 68 删除地址
	 */
	static class DeleteOnClickListener implements OnClickListener
	{

		private static DeleteOnClickListener instance = null;

		private DeleteOnClickListener()
		{
		}

		/** 单例 */
		public static synchronized DeleteOnClickListener getInstance()
		{
			if (instance == null)
				instance = new DeleteOnClickListener();
			return instance;
		}

		@Override
		public void onClick(final View view)
		{
			
			TwoButtonDialog.Builder builder=new Builder(mContext);
			builder.setMessage("确认删除这条地址吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
					String addressId = (String) ((LinearLayout) view).getTag();
					String url = RequestUrl.getInstance(mContext).getUrl_delAddress(
							mContext, addressId);
					addressManagerImpl.setDefualtAddress(url,
							Constants.INTERFACE_delAddress);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}
}

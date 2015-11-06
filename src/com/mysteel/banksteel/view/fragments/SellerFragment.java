package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mysteel.banksteel.view.activity.ResourceOtherActivity;
import com.mysteel.banksteel.view.activity.ShowUserInfoActivity;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * @author 作者 wushaoge
 * @date 创建时间：2015年7月28日19:01:21
 */
public class SellerFragment extends BaseFragment implements OnClickListener, IOrderTradeView
{
	
	private View currentView;
	
	private CircleImageView circle; //买家头像
	private TextView tv_name; //买家姓名
	private ImageView img_renzheng; //是否已认证
	private TextView tv_norenzheng;
	private TextView tv_address; //公司地址
	private TextView tv_company; //公司名称
	private LinearLayout ll_other_source; //查看TA的资源

	
	private ImageView iv_phone; //拨打电话
	private ImageView iv_msg; //在线沟通
	
	private Datas datas;
	private IOrderTrade orderTrade;
	
	
	public View getCurrentView()
	{
		return currentView;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	currentView = inflater.inflate(R.layout.fragment_seller, container,
				false);
		EventBus.getDefault().register(this);
		initView();
		initData();
		return currentView;
    }
    
    private void initView()
	{
    	circle = (CircleImageView) currentView.findViewById(R.id.circle);
		circle.setOnClickListener(this);
    	tv_name = (TextView) currentView.findViewById(R.id.tv_name);
    	img_renzheng = (ImageView) currentView.findViewById(R.id.img_renzheng);
    	tv_norenzheng = (TextView) currentView.findViewById(R.id.tv_norenzheng);
    	tv_address = (TextView) currentView.findViewById(R.id.tv_address);
    	tv_company = (TextView) currentView.findViewById(R.id.tv_company);

		ll_other_source = (LinearLayout) currentView.findViewById(R.id.ll_other_source);
		ll_other_source.setOnClickListener(this);

    	iv_phone = (ImageView) currentView.findViewById(R.id.iv_phone);
    	iv_msg = (ImageView) currentView.findViewById(R.id.iv_msg);
    	iv_phone.setOnClickListener(this);
		iv_msg.setOnClickListener(this);
		orderTrade = new OrderTradeImpl(getActivity(), this);
	}
    
    private void initData()
   	{
    	Bundle bundle = getArguments();
    	datas = (Datas)bundle.getSerializable("sellerBundle");
    	
    	if (datas!=null){
    		if (!TextUtils.isEmpty(datas.getUserPhoto()))
    		{
//        		BitmapUtil.loadImage(getActivity(), datas.getUserPhoto(), circle);
        		Tools.loadImage(datas.getUserPhoto(),circle);
    		}
        	tv_name.setText(datas.getUserName());
        	//1认证   0未认证
        	/*if(datas.getAttestation().equals("1")){
        		img_renzheng.setVisibility(View.VISIBLE);
        		tv_norenzheng.setVisibility(View.GONE);
        	}else{
        		img_renzheng.setVisibility(View.GONE);
        		tv_norenzheng.setVisibility(View.VISIBLE);
        	}*/
    		tv_address.setText(datas.getUserProvince()+datas.getUserCity()+datas.getUserDistrict()+datas.getAddress());
			if(TextUtils.isEmpty(tv_address.getText()))
			{
				tv_address.setText("未填写");
			}
    		tv_company.setText(datas.getMemberName());
    	}else{
    		tv_name.setText("未填写");
    		tv_address.setText("未填写");
    		tv_company.setText("未填写");
    	}
   	}
    
    @Override
	public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_phone://拨打电话
            	callPhone();
                break;
            case R.id.iv_msg://在线沟通
				goutongOnline();
                break;
			case R.id.circle:
				Intent iShow = new Intent(getActivity(), LoginActivity.class);
				if (Tools.isLogin(getActivity()))
				{
					iShow = new Intent(getActivity(), ShowUserInfoActivity.class);
					iShow.putExtra("friendId", datas.getUserId());
					iShow.putExtra("friendPhone", datas.getPhone());
				}
				startActivity(iShow);
				break;
			case R.id.ll_other_source:
				Intent iShow2 = new Intent(getActivity(), ResourceOtherActivity.class);
				iShow2.putExtra("friendId", datas.getUserId());
				iShow2.putExtra("friendPhone", datas.getPhone());
				startActivity(iShow2);
				break;
        }
    }

	/**
	 * 拨打电话
	 */
    private void callPhone(){
    	if(!TextUtils.isEmpty(datas.getPhone())){
			Tools.makeCall(getActivity(),datas.getPhone());
    	}else{
    		Tools.showToast(getActivity(), "货主尚未上传他的手机号!");
    	}
    }

	/**
	 * 在线沟通
	 */
	private void goutongOnline(){

		if (!TextUtils.isEmpty(datas.getPhone()))
		{
			String url = RequestUrl.getInstance(getActivity()).getUrl_Register(getActivity(), datas.getPhone(), "");
			orderTrade.getHuanXinRegister(url, Constants.INTERFACE_hxuserregister);
		} else
		{
			Toast.makeText(getActivity(), "暂时获取不到卖家电话！", Toast.LENGTH_SHORT).show();
		}
	}
    
    @Override
	public void onDestroy()
	{
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void updateView(BaseData bdata)
	{

		if (Tools.isLogin(getActivity()))
		{
			Intent i = new Intent(getActivity(), ChatActivity.class);
			i.putExtra("userId", datas.getPhone());
			i.putExtra("userName", datas.getUserName());
			getActivity().startActivity(i);
		}else{
			Intent i = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(i);
		}
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}
}

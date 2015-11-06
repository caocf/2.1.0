package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.OrderTradeImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;
import com.mysteel.banksteeltwo.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 买家和卖家信息的修改
 * 
 * @author 作者 zoujian
 * @date 创建时间：2015-5-26 上午11:46:23
 */
public class EditOrderActivity extends BaseActivity implements OnClickListener,
		IOrderTradeView
{

	private String name;
	private String phone;
	private String company;
	private String memberType;
	private String orderId;

	private EditText et_buyname;
	private EditText et_contactphone;
	private EditText et_companyname;
	private Button btn_comfireorder;

	private IOrderTrade orderTrade;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editorder);
		Bundle b = getIntent().getExtras();
		name = b.getString("name");
		phone = b.getString("phone");
		company = b.getString("company");
		memberType = b.getString("memberType");
		orderId = b.getString("orderId");
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvRightText.setVisibility(View.GONE);
		tvTitle.setText("订单详情");
		backLayout.setOnClickListener(this);
		et_buyname = (EditText) findViewById(R.id.et_buyname);
		et_contactphone = (EditText) findViewById(R.id.et_contactphone);
		et_companyname = (EditText) findViewById(R.id.et_companyname);
		btn_comfireorder = (Button) findViewById(R.id.btn_comfireorder);
		btn_comfireorder.setOnClickListener(this);// 确认修改页面
		orderTrade = new OrderTradeImpl(this, this);
		showView();
	}

	private void showView()
	{
		if (!TextUtils.isEmpty(name))
		{
			et_buyname.setText(name);
		}
		if (!TextUtils.isEmpty(phone))
		{
			et_contactphone.setText(phone);
		}
		if (!TextUtils.isEmpty(company))
		{
			et_companyname.setText(company);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:// 返回
			finish();
			break;
		case R.id.btn_comfireorder:// 确认提交的按钮事件
			commit();
			break;
		default:
			break;
		}
	}

	/** 提交按钮的事件处理 */
	private void commit()
	{
		if (TextUtils.isEmpty(et_buyname.getText().toString().trim()))
		{
			Tools.showToast(this, "请填写姓名！");
			return;
		}

		if (TextUtils.isEmpty(et_contactphone.getText().toString().trim())
				|| !Tools.checkIsPhoneNumber(et_contactphone.getText()
						.toString().trim()))
		{
			Tools.showToast(this, "请填写正确的联系电话！");
			return;
		}
		if (TextUtils.isEmpty(et_companyname.getText().toString().trim()))
		{
			Tools.showToast(this, "请填写公司名称！");
			return;
		}
		String name = et_buyname.getText().toString().trim();
		String phone = et_contactphone.getText().toString().trim();
		String memberName = et_companyname.getText().toString().trim();

		String url = RequestUrl.getInstance(this).getUrl_getEditOrder(this,
				orderId, memberType, name, phone, memberName);
		orderTrade.getHumanServe(url, Constants.INTERFACE_editOrder);
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("true".equals(data.getStatus()))
		{
			Tools.showToast(this, "修改信息成功！");
			finish();
		}
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		orderTrade.finishRequest();
	}

}

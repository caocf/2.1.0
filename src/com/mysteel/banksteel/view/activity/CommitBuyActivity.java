package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteeltwo.R;

/**
 * 发布求购确认页面 Created by zoujian on 15/8/24.
 */
public class CommitBuyActivity extends BaseActivity implements
		View.OnClickListener, IBuyView
{

	private TextView tv_jieshao, tv_qty, tv_jiaohuodi, tv_man, tv_phone,
			tv_pinpai, tv_validity, tv_choose;
	private Button btn_cancle, btn_submit;
	LinearLayout ll_layout_youxiao;
	private String validTime = "3";
	private String parentSteel;
	private String childSteel;
	private String spec;
	private String material;
	private String childSteelId;
	private String qty;
	private String city;
	private String contacter;
	private String phone;
	private String brand;
	private String note;
	private IBuyCenter buyCenterImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit_buy);
		Bundle b = getIntent().getExtras();
		parentSteel = b.getString("parentSteel");
		childSteel = b.getString("childSteel");
		spec = b.getString("spec");
		material = b.getString("material");
		childSteelId = b.getString("childSteelId");
		qty = b.getString("qty");
		city = b.getString("city");
		contacter = b.getString("contacter");
		phone = b.getString("phone");
		brand = b.getString("brand");
		note = b.getString("note");
		initView();
		RefreshView();
	}

	private void RefreshView()
	{
		tv_jieshao.setText(parentSteel + "-" + childSteel + " " + spec + " "
				+ material);
		tv_qty.setText(qty);
		tv_pinpai.setText(brand);
		tv_jiaohuodi.setText(city);
		tv_man.setText(contacter);
		tv_phone.setText(phone);
	}

	private void initView()
	{
		tv_jieshao = (TextView) findViewById(R.id.tv_jieshao);
		tv_qty = (TextView) findViewById(R.id.tv_qty);
		tv_pinpai = (TextView) findViewById(R.id.tv_pinpai);
		tv_jiaohuodi = (TextView) findViewById(R.id.tv_jiaohuodi);
		tv_man = (TextView) findViewById(R.id.tv_man);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_validity = (TextView) findViewById(R.id.tv_validity);
		tv_choose = (TextView) findViewById(R.id.tv_choose);
		btn_cancle = (Button) findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(this);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		ll_layout_youxiao = (LinearLayout) findViewById(R.id.ll_layout_youxiao);
		ll_layout_youxiao.setOnClickListener(this);
		buyCenterImpl = new BuyImpl(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_cancle:// 取消
			finish();
			break;

		case R.id.btn_submit:// 确定
			String resource = "2";// 来源 来源:0-网站快捷需求 1-会员中心 2-手机端
			String company = SharePreferenceUtil.getString(this,
					Constants.USER_MEMBER_NAME);// 公司名称
			String url = RequestUrl.getInstance(this).getUrl_publishStanBuy(
					this, childSteelId, childSteel, material, spec, brand,
					city, qty, contacter, phone, resource, company, validTime,
					note);
			buyCenterImpl.getPublishStanBuy(url,
					Constants.INTERFACE_publishStanBuy);
			break;

		case R.id.ll_layout_youxiao:// 有效期
			Intent i = new Intent(this, ValidityActivity.class);
			startActivity(i);
			break;
		}
	}

	@Override
	public void updateView(BaseData data)
	{
		if ("buy.publishStanBuy".equals(data.getCmd()))
		{// 发布求购
			// RegisterData rdata = (RegisterData) data;
		// String id = rdata.getData();// 发布求购后返回的id
		// Intent intent = new Intent(this, MatchFindGood.class);
		// intent.putExtra("ID", id);
		// startActivity(intent);
			EventBus.getDefault().post("", "startActivity");
			finish();
		}
	}

	@Override
	public void isShowDialog(boolean flag)
	{

	}

	@Subscriber(tag = "commit")
	private void datasChange(String str)
	{
		if (tv_choose.getVisibility() == View.VISIBLE)
		{
			tv_choose.setVisibility(View.GONE);
		}
		tv_validity.setText(str + "天");
		validTime = str;
	}
}

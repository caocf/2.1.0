package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.ShowUserInfo;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.ShowUserInfoAppraiseAdapter;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 查看个人中心
 * 
 * @author:wushaoge
 * @date：2015年7月23日11:16:22
 */
public class ShowUserInfoActivity extends SwipeBackActivity implements OnClickListener,
		IListViewInterface, XListView.IXListViewListener,IUserCenterView {

	private Context mContext;
	private XListView xListView;
	private ShowUserInfoAppraiseAdapter adapter;
	private ProgressBar progressBar;

	private CircleImageView iv_personalinfo_avatar; //头像
	private LinearLayout ll_other_source; //查看TA的资源

	private TextView et_personalinfo_nickname; //姓名
	private TextView tv_personalinfo_city; //地区
	private TextView tv_companyinfo_quyu; //三个城市
	private TextView tv_companyinfo_fanwei; //三个种类

	private ShowUserInfo showUserInfo = null;

	private ShowUserInfo.Data.FriendResume friendResume;
	private List<ShowUserInfo.Data.Appraise> datas;


	String friendId = "";
	String friendPhone = "";

	private UserCenterImpl userCenterImpl;
	private String page = "1";
	private String size = "10";


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_userinfo);
		mContext = this;
		userCenterImpl = new UserCenterImpl(this);
		initViews();
		initData();
	}
	
	
	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("个人中心");
		backLayout.setOnClickListener(this);

		friendId = getIntent().getStringExtra("friendId");
		friendPhone = getIntent().getStringExtra("friendPhone");

		xListView = (XListView) findViewById(R.id.lv_listview);
		progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		xListView.setPullLoadEnable(false);
		xListView.setXListViewListener(this);

		iv_personalinfo_avatar = (CircleImageView) findViewById(R.id.iv_personalinfo_avatar);
		ll_other_source = (LinearLayout) findViewById(R.id.ll_other_source);
		ll_other_source.setOnClickListener(this);

		et_personalinfo_nickname  = (TextView) findViewById(R.id.et_personalinfo_nickname);
		tv_personalinfo_city  = (TextView) findViewById(R.id.tv_personalinfo_city);
		tv_companyinfo_quyu  = (TextView) findViewById(R.id.tv_companyinfo_quyu);
		tv_companyinfo_fanwei  = (TextView) findViewById(R.id.tv_companyinfo_fanwei);

	}

	private void initData()
	{
		adapter = new ShowUserInfoAppraiseAdapter(mContext);
		xListView.setAdapter(adapter);
		getRequestData();
	}

	/**
	 * 拉取数据
	 */
	private void getRequestData(){
		String url = RequestUrl.getInstance(this).getUrl_friendMaterial(mContext,friendId,friendPhone,page,size);
		LogUtils.e(url);
		userCenterImpl.getShowUserInfo(url, Constants.INTERFACE_FriendMaterial);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.menu_layout:
				// 返回菜单
				finish();
				break;
			case R.id.ll_other_source:
				Intent iShow = new Intent(this, ResourceOtherActivity.class);
				iShow.putExtra("friendPhone", friendPhone);
				startActivity(iShow);
				break;
			default:
				break;
		}
	}

	@Override
	public void stopUpdate() {

	}

	@Override
	public void updateView(BaseData data) {
		if(null!=data){
			if(null==showUserInfo){
				showUserInfo = (ShowUserInfo)data;
				//个人信息
				if(showUserInfo.getData()!=null&&showUserInfo.getData().getFriendResume()!=null){
					friendResume = showUserInfo.getData().getFriendResume();
					if (!TextUtils.isEmpty(friendResume.getPhoto())) {
//						BitmapUtil.loadImage(this, friendResume.getPhoto(), iv_personalinfo_avatar);
						Tools.loadImage(friendResume.getPhoto(),iv_personalinfo_avatar);
					}
					if (!TextUtils.isEmpty(friendResume.getName())) {
						et_personalinfo_nickname.setText(friendResume.getName());
					}

					String specificStr = showUserInfo.getData().getFriendResume().getState()+"-"+showUserInfo.getData().getFriendResume().getCity()+"-"+showUserInfo.getData().getFriendResume().getArea();
					tv_personalinfo_city.setText(specificStr);

					//3个城市
					String resultCity = "";
					if(showUserInfo.getData().getFriendMemberBusiness()!=null&&showUserInfo.getData().getFriendMemberBusiness().getData()!=null&&showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessAreas().size()>0){
						for(int i = 0;i<showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessAreas().size();i++){
							resultCity += showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessAreas().get(i).getCity()+",";
						}
					}
					if(!TextUtils.isEmpty(resultCity)){
						tv_companyinfo_quyu.setText(resultCity.substring(0, resultCity.length() - 1));
					}

					//3个种类
					String zhongleiCity = "";
					if(showUserInfo.getData().getFriendMemberBusiness()!=null&&showUserInfo.getData().getFriendMemberBusiness().getData()!=null&&showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessScopes().size()>0){
						for(int i = 0;i<showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessScopes().size();i++){
							zhongleiCity += showUserInfo.getData().getFriendMemberBusiness().getData().getBusinessScopes().get(i).getName()+",";
						}
					}
					if(!TextUtils.isEmpty(zhongleiCity)){
						tv_companyinfo_fanwei.setText(zhongleiCity.substring(0,zhongleiCity.length()-1));
					}

				}

			}
			//评价
			if(showUserInfo.getData()!=null&&showUserInfo.getData().getAppraise()!=null&&showUserInfo.getData().getAppraise().size()>0){
				if ("1".equals(page))
				{
					datas = showUserInfo.getData().getAppraise();
					xListView.setVisibility(View.VISIBLE);
					xListView.setPullRefreshEnable(true);
					xListView.setPullLoadEnable(true);
					xListView.setAdapter(adapter);
					adapter.reSetListView(datas);
				} else
				{
					datas.addAll(showUserInfo.getData().getAppraise());
				}
				adapter.reSetListView(datas);
			}else{
				xListView.setPullRefreshEnable(true);
				xListView.setPullLoadEnable(false);
				xListView.setAdapter(adapter);
				adapter.reSetListView(new ArrayList<ShowUserInfo.Data.Appraise>());
			}
		}
		onLoad();
	}

	@Override
	public void isShowDialog(boolean flag) {
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}


	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		String url = RequestUrl.getInstance(this).getUrl_friendMaterial(mContext, friendId,friendPhone, page, size);
		LogUtils.e(url);
		userCenterImpl.getShowUserInfo(url, Constants.INTERFACE_FriendMaterial);
	}

	@Override
	public void onLoadMore() {
		if (TextUtils.isEmpty(showUserInfo.getData().getPagenum())
				|| TextUtils.isEmpty(showUserInfo.getData().getPage()))
		{
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(showUserInfo.getData().getPagenum()))
		{
			onLoad();
			Tools.showToast(this, "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";
		String url = RequestUrl.getInstance(this).getUrl_friendMaterial(mContext, friendId,friendPhone, page, size);
		LogUtils.e(url);
		userCenterImpl.getShowUserInfo(url, Constants.INTERFACE_FriendMaterial);
	}

	private void onLoad()
	{
		xListView.stopLoadMore();
		xListView.stopRefresh();
		xListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}

	@Override
	public void openCountDown(long millisInFuture, long countDownInterval) {

	}

	@Override
	public void upDatePersonal(EditInfoData data) {

	}

	@Override
	public void upDateCompany(EditInfoData data) {

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		userCenterImpl.finishRequest();
	}

}


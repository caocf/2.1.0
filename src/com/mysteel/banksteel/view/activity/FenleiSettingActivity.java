package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.BusinessScopesData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.FenleiSettingAdapter;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择业务范围
 *
 * @author:wushaoge
 * @date：2015年9月8日09:13:50
 */
public class FenleiSettingActivity extends SwipeBackActivity implements OnClickListener,IUserCenterView {

	private Context mContext;


	private XListView lv_listview;
	private FenleiSettingAdapter adapter;
	
	//private List<SteelType> list = new ArrayList<SteelType>();
	private List<BusinessScopesData.BusinessScopes.Data.ChildData> list = new ArrayList<BusinessScopesData.BusinessScopes.Data.ChildData>();
	
	private TextView tv_fenlei1;
	private TextView tv_fenlei2;
	private TextView tv_fenlei3;
	
	private String fenlei1 = "";
	private String fenlei2 = "";
	private String fenlei3 = "";


	private String type = ""; //本身选择的type类型
	private String memberType = "9"; //1-经销商 2-终端采购用户 3-钢厂生产企业 4-次终端用户 9-其它

	private BusinessScopesData businessScopesData;

	private UserCenterImpl userCenterImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenlei_setting);
		mContext = this;
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
		tvTitle.setText("选择分类");
		backLayout.setOnClickListener(this);
		
		tv_fenlei1 = (TextView) findViewById(R.id.tv_fenlei1);
		tv_fenlei2 = (TextView) findViewById(R.id.tv_fenlei2);
		tv_fenlei3 = (TextView) findViewById(R.id.tv_fenlei3);
		tv_fenlei1.setOnClickListener(this);
		tv_fenlei2.setOnClickListener(this);
		tv_fenlei3.setOnClickListener(this);
		
		lv_listview = (XListView)findViewById(R.id.lv_listview);
		lv_listview.setPullRefreshEnable(false); //下拉刷新
		lv_listview.setPullLoadEnable(false); //上啦刷新

		memberType = getIntent().getStringExtra("memberType");

		/** 公司性质 */
		type = SharePreferenceUtil.getString(this,
				Constants.USER_MEMBER_TYPE);

//		list.add(new SteelType("01", "建筑用钢"));
//		list.add(new SteelType("02", "热轧板卷"));
//		list.add(new SteelType("03", "中厚板"));
//		list.add(new SteelType("04", "冷轧板卷"));
//		list.add(new SteelType("05", "涂镀"));
//		list.add(new SteelType("06", "管材"));
//		list.add(new SteelType("07", "型材"));
//		list.add(new SteelType("09", "不锈钢"));
//		list.add(new SteelType("10", "优特钢"));
//		list.add(new SteelType("11", "钢坯"));
//		list.add(new SteelType("12", "品种钢"));
//		list.add(new SteelType("08", "其他钢材"));

	}

	private void initData()
	{
		//获取用户选择的3个种类
		String selFenlei = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_PINZHONG_DETAIL);
		if(!TextUtils.isEmpty(selFenlei)){
			String[] attrStr = selFenlei.split(",");
			if(attrStr.length==1){
				fenlei1 = attrStr[0];
			}
			if(attrStr.length==2){
				fenlei1 = attrStr[0];
				fenlei2 = attrStr[1];
			}
			if(attrStr.length==3){
				fenlei1 = attrStr[0];
				fenlei2 = attrStr[1];
				fenlei3 = attrStr[2];
			}
		}

		//判断一下是否和之前选的一样
		if(!type.equals(memberType)){
			fenlei1 = "";
			fenlei2 = "";
			fenlei3 = "";
		}
		isHide();


		
		adapter = new FenleiSettingAdapter(mContext, list);
		lv_listview.setAdapter(adapter);

		//userCenterImpl
		userCenterImpl = new UserCenterImpl(mContext);
		String url = RequestUrl.getInstance(this).getUrl_businessScopes(this, memberType);
		LogUtils.e(url);
		userCenterImpl.getBusinessScopes(url, Constants.INTERFACE_UserBusinessScopes);

		lv_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selFenlei(list.get(position-1).getName()+"|"+list.get(position-1).getCode());
			}
		});
	}
	
	
	
	/**
	 * 选择分类
	 */
	private void selFenlei(String fenleiName){
		if(fenleiName.equals(fenlei1)||fenleiName.equals(fenlei2)||fenleiName.equals(fenlei3)){ //选择了相同种类
			return;
		}
		if(TextUtils.isEmpty(fenlei1)&&TextUtils.isEmpty(fenlei2)&&TextUtils.isEmpty(fenlei3)){
			fenlei1 = fenleiName;
		}else if(!TextUtils.isEmpty(fenlei1)&&TextUtils.isEmpty(fenlei2)&&TextUtils.isEmpty(fenlei3)){
			fenlei2 = fenleiName;
		}else if(!TextUtils.isEmpty(fenlei1)&&!TextUtils.isEmpty(fenlei2)&&TextUtils.isEmpty(fenlei3)){
			fenlei3 = fenleiName;
		}else if(!TextUtils.isEmpty(fenlei1)&&!TextUtils.isEmpty(fenlei2)&&!TextUtils.isEmpty(fenlei3)){
			Tools.showToast(mContext, "最多只能选择3个种类哦！");
			return;
		}
		isHide();
	}
	
	
	/**
	 * 删除分类
	 * @param flag
	 */
	private void delFenlei(int flag){
		List<String> list = new ArrayList<String>();
		list.add(fenlei1);
		list.add(fenlei2);
		list.add(fenlei3);
		if(flag==1){
			fenlei1 = "";
			list.remove(0);
		}
		if(flag==2){
			fenlei2 = "";
			list.remove(1);
		}
		if(flag==3){
			fenlei3 = "";
			list.remove(2);
		}
		list.add("");
		for(int i = 0;i<list.size();i++){
			if(i == 0){
				fenlei1 = list.get(i);
			}
			if(i == 1){
				fenlei2 = list.get(i);
			}
			if(i == 2){
				fenlei3 = list.get(i);
			}
		}
		isHide();
	}
	
	
	/**
	 * 显示隐藏
	 */
	private void isHide(){
		if(!TextUtils.isEmpty(fenlei1)){
			tv_fenlei1.setText(fenlei1.split("\\|")[0]);
			tv_fenlei1.setVisibility(View.VISIBLE);
		}else{
			tv_fenlei1.setVisibility(View.INVISIBLE);
		}
		 
		if(!TextUtils.isEmpty(fenlei2)){
			tv_fenlei2.setText(fenlei2.split("\\|")[0]);
			tv_fenlei2.setVisibility(View.VISIBLE);
		}else{
			tv_fenlei2.setVisibility(View.INVISIBLE);
		}
		
		if(!TextUtils.isEmpty(fenlei3)){
			tv_fenlei3.setText(fenlei3.split("\\|")[0]);
			tv_fenlei3.setVisibility(View.VISIBLE);
		}else{
			tv_fenlei3.setVisibility(View.INVISIBLE);
		}
	}
	


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.menu_layout:
				// 返回菜单
				closeBack();
				break;
			case R.id.tv_fenlei1:
				delFenlei(1);
				break;
			case R.id.tv_fenlei2:
				delFenlei(2);
				break;
			case R.id.tv_fenlei3:
				delFenlei(3);
				break;
			default:
				break;
		}
	}


	private void closeBack(){
		List<String> list = new ArrayList<String>();
		list.add(fenlei1);
		list.add(fenlei2);
		list.add(fenlei3);
		EventBus.getDefault().post(list, "fenleisettingactivity");
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			closeBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
	public void updateView(BaseData data) {
		businessScopesData = (BusinessScopesData)data;
		if(businessScopesData!=null&&businessScopesData.getBusinessScopes().getData()!=null&&businessScopesData.getBusinessScopes().getData().size()>0
				&&businessScopesData.getBusinessScopes().getData().get(0).getBusinessScopes()!=null&&businessScopesData.getBusinessScopes().getData().get(0).getBusinessScopes().size()>0
				){
			list = new ArrayList<BusinessScopesData.BusinessScopes.Data.ChildData>();
			for(int i = 0;i<businessScopesData.getBusinessScopes().getData().get(0).getBusinessScopes().size();i++){
				list.add(businessScopesData.getBusinessScopes().getData().get(0).getBusinessScopes().get(i));
			}
			adapter.updateData(list);
		}else{
			Tools.showToast(mContext,"暂无数据!");
		}
	}

	@Override
	public void isShowDialog(boolean flag) {

	}
}


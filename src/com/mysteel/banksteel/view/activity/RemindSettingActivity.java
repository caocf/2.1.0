package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IUserCenter;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.SwitchButton;
import com.mysteel.banksteel.view.ui.SwitchButton.OnCheckChangeListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 提醒设置
 * 
 * @author:wushaoge
 * @date：2015年7月27日16:11:41
 */
public class RemindSettingActivity extends SwipeBackActivity implements OnClickListener, OnCheckChangeListener, IUserCenterView {

		private Context mContext;
	
		private SwitchButton switchbutton_push; //消息推送
		private LinearLayout ll_main; //显示与隐藏的主体
		private RelativeLayout rl_pinzhong; //品种选择
		private TextView tv_pinzhong_name; //品种名称
		private RelativeLayout rl_quyu; //区域选择
		private TextView tv_quyu_name; //选择的3个城市
		private LinearLayout ll_bottom; //按钮是否隐藏
		private TextView tv_tiaoguo; //跳过
		private TextView tv_save; //保存

		private String pinzhongName = ""; //选择的品种名称
		//private String citys = ""; //选择的3个城市名
		
		private boolean isFirst = false; //是否第一次进入


		/** 公司性质 */
		private String type;
		private String resultCitys = ""; //最后选择的3个城市+code
		private String resultCitysTemp = ""; //最后选择的3个城市

		//private String resultFenlei = ""; //最后3个种类

		/** IUserCenter */
		private IUserCenter userCenter;
	 
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_remind_setting);
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
			tvTitle.setText("提醒设置");
			backLayout.setOnClickListener(this);

			userCenter = new UserCenterImpl(this);

			resultCitys = SharePreferenceUtil.getString(this, Constants.USER_SETTING_CITYS_DETAIL);
			resultCitysTemp = SharePreferenceUtil.getString(this, Constants.USER_SETTING_CITYS);
			
			switchbutton_push = (SwitchButton)findViewById(R.id.switchbutton_push);
			ll_main = (LinearLayout)findViewById(R.id.ll_main);
			rl_pinzhong = (RelativeLayout)findViewById(R.id.rl_pinzhong);
			tv_pinzhong_name = (TextView)findViewById(R.id.tv_pinzhong_name);
			rl_quyu = (RelativeLayout)findViewById(R.id.rl_quyu);
			tv_quyu_name = (TextView)findViewById(R.id.tv_quyu_name);
			ll_bottom = (LinearLayout)findViewById(R.id.ll_bottom);
			tv_tiaoguo = (TextView)findViewById(R.id.tv_tiaoguo);
			tv_save = (TextView)findViewById(R.id.tv_save);
			
			/** 首次应该设为true,允许推送 */
			switchbutton_push.setChecked(true);
			switchbutton_push.setOnCheckChangeListener(this);
			rl_pinzhong.setOnClickListener(this);
			rl_quyu.setOnClickListener(this);
			tv_tiaoguo.setOnClickListener(this);
			tv_save.setOnClickListener(this);

		}

		private void initData()
		{
			isFirst = SharePreferenceUtil.getBoolean(mContext, Constants.USER_SETTING_FLAG);
			isFirst = false;
			if(isFirst){
				ll_bottom.setVisibility(View.GONE);
			}else{
				ll_bottom.setVisibility(View.VISIBLE);
			}

			/** 公司性质 */
			type = SharePreferenceUtil.getString(this,
					Constants.USER_MEMBER_TYPE);
			if (TextUtils.isEmpty(type))
			{
				type = "1";
			}
			
			pinzhongName = SharePreferenceUtil.getString(mContext, Constants.USER_SETTING_LOCAL_ZHONGLEI);
			if(!TextUtils.isEmpty(pinzhongName)){
				tv_pinzhong_name.setText(pinzhongName);
			}else{
				tv_pinzhong_name.setText("请选择");
			}
			
			LogUtils.e("啊啊啊"+resultCitysTemp);
			if(!TextUtils.isEmpty(resultCitysTemp)){
				tv_quyu_name.setText(resultCitysTemp);
			}else{
				tv_quyu_name.setText("请选择");
			}
			
			//进来就说明已经设置过了
			SharePreferenceUtil.setValue(mContext, Constants.USER_SETTING_FLAG,true);
		}
		
		
		
		@Subscriber(tag = "fenleisettingactivity")
		private void setFenleiName(List<String> list)
		{
			pinzhongName = "";
			for(String s : list){
				if(!TextUtils.isEmpty(s)){
					pinzhongName += s+",";
				}
			}
			if(!TextUtils.isEmpty(pinzhongName)){
				pinzhongName = pinzhongName.substring(0, pinzhongName.length()-1);
				tv_pinzhong_name.setText(pinzhongName);
			}else{
				tv_pinzhong_name.setText("请选择");
			}
			
//			SharePreferenceUtil.setValue(mContext, Constants.USER_SETTING_LOCAL_ZHONGLEI,
//					resultFenlei);// 保存选择的品种名称
		}

		@Subscriber(tag = "quyusettingactivity")
		private void setCityName(List<String> list)
		{
			resultCitys = "";
			resultCitysTemp = "";
			for(String s : list){
				if(!TextUtils.isEmpty(s)){
					resultCitys += s+",";
					resultCitysTemp += s.substring(0,s.length()-6)+",";
				}
			}
			if(!TextUtils.isEmpty(resultCitys)&&!TextUtils.isEmpty(resultCitysTemp)){
				resultCitys = resultCitys.substring(0, resultCitys.length()-1);
				resultCitysTemp = resultCitysTemp.substring(0, resultCitysTemp.length()-1);
				tv_quyu_name.setText(resultCitysTemp);
			}else{
				tv_quyu_name.setText("请选择");
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
				case R.id.menu_layout: // 返回
					closeBack();
					break;
				case R.id.rl_pinzhong:// 选择品种
					mContext.startActivity(new Intent(mContext, LocalFenleiSettingActivity.class));
					break;
				case R.id.rl_quyu:// 选择区域
					mContext.startActivity(new Intent(mContext, QuyuSettingActivity.class));
					break;
				case R.id.tv_tiaoguo: //跳过
					saveData();
					break;
				case R.id.tv_save: //保存
					saveData();
					break;
				default:
					break;
			}
		}
		
		
		/**
		 * 返回
		 */
		private void closeBack(){
			mContext.startActivity(new Intent(mContext, LocalQuotedPriceActivity.class));
			finish();
		}
		
		/**
		 * 保存数据
		 */
		private void saveData(){
			SharePreferenceUtil.setValue(mContext, Constants.USER_SETTING_LOCAL_ZHONGLEI, pinzhongName);// 保存选择的品种名称

			Map<String,String> map = new HashMap<String,String>();
			map.put("memberType",type);

			if(!TextUtils.isEmpty(resultCitys)){
				String[] attr = resultCitys.split(",");
				if(attr.length>=1){
					map.put("cityCode1",attr[0].substring(attr[0].length()-6,attr[0].length()));
				}
				if(attr.length>=2){
					map.put("cityCode2",attr[1].substring(attr[1].length()-6,attr[1].length()));
				}
				if(attr.length>=3){
					map.put("cityCode3",attr[2].substring(attr[2].length()-6,attr[2].length()));
				}
			}


			String resultUserFenlei = SharePreferenceUtil.getString(this, Constants.USER_SETTING_PINZHONG_DETAIL);
			LogUtils.e("个人中心选择的分类"+resultUserFenlei);
			if(!TextUtils.isEmpty(resultUserFenlei)){
				String[] attr2 = resultUserFenlei.split(",");
				if(attr2.length>=1){
					map.put("scopeName1",attr2[0].split("\\|")[0]);
					map.put("scopeCode1",attr2[0].split("\\|")[1]);
				}
				if(attr2.length>=2){
					map.put("scopeName2",attr2[1].split("\\|")[0]);
					map.put("scopeCode2",attr2[1].split("\\|")[1]);
				}
				if(attr2.length>=3){
					map.put("scopeName3",attr2[2].split("\\|")[0]);
					map.put("scopeCode3",attr2[2].split("\\|")[1]);
				}
			}
			String url = RequestUrl.getInstance(this).getUrl_modifyMemberBusinessInfo(this, map);
			LogUtils.e(url);
			userCenter.getModifyMemberBusinessInfo(url, Constants.INTERFACE_ModifyMemberBusinessInfo);

		}

		@Override
		public void OnCheck(SwitchButton switchButton, boolean isChecked) {
			if (isChecked)
			{
				ll_main.setVisibility(View.VISIBLE);// 推送
			} else
			{
				ll_main.setVisibility(View.GONE);// 不推送
			}
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
		SharePreferenceUtil.setValue(this, Constants.USER_SETTING_CITYS_DETAIL,
				resultCitys); // 保存选择的城市名称+code
		SharePreferenceUtil.setValue(this, Constants.USER_SETTING_CITYS,
				resultCitysTemp); // 保存选择的城市名称

		EventBus.getDefault().post("", "remindsettingactivity");
		mContext.startActivity(new Intent(mContext, LocalQuotedPriceActivity.class));
		finish();
	}

	@Override
	public void isShowDialog(boolean flag) {

	}
}

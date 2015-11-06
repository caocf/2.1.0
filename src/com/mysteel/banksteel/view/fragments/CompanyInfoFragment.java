package com.mysteel.banksteel.view.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IUserCenter;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.StringUtils;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.CityPickActivity;
import com.mysteel.banksteel.view.activity.FenleiSettingActivity;
import com.mysteel.banksteel.view.activity.MyInfoActivity;
import com.mysteel.banksteel.view.activity.QuyuSettingActivity;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 68 公司信息片段
 */
public class CompanyInfoFragment extends BaseFragment implements
		OnClickListener, IUserCenterView
{
	private View mFragment;

	private ProgressBar progressBar;

	/** 公司名 */
	private EditText etName;
	/** 公司性质 */
	private TextView tvProperty;

	/** 公司性质 */
	private String type;
	/** 所在城市 */
	private TextView tvAddress;
	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String county;
	/** 保存按钮 */
	private Button btn_save;

	/** 区域设置 */
	private TextView tv_companyinfo_quyu;
	/** 三种品类 */
	private TextView tv_companyinfo_fanwei;

	/** IUserCenter */
	private IUserCenter userCenter;


	private String resultCitys = ""; //最后选择的3个城市+code
	private String resultCitysTemp = ""; //最后选择的3个城市

	private String resultFenlei = ""; //最后选择的3个分类+code
	private String resultFenleiTemp = ""; //最后选择的3个分类+code


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		userCenter.finishRequest();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mFragment = inflater.inflate(R.layout.fragment_companyinfo, container,
				false);
		initViews();
		return mFragment;
	}

	/**
	 * 保存按钮被点击,执行保存公司信息协议
	 * 
	 * @param str
	 */
	@Subscriber(tag = "1")
	public void save(String str)
	{
		// 获取数据
		String userId = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_USERID);
		String memberId = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBERID);
		String memberName = etName.getText().toString();
		// 判断非空
		if (StringUtils.isEmpty(memberName))
		{
			Tools.showToast(getActivity(), "公司名称不能为空");
			return;
		}
		if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city)
				|| StringUtils.isEmpty(county))
		{
			Tools.showToast(getActivity(), "请选择公司所在城市");
			return;
		}
		if (!Tools.isNetworkConnected(getActivity()))
		{
			Tools.showToast(getActivity(), "请检查网络");
			return;
		}

		//单独调用保存业务区域的接口
		LogUtils.e("3个城市="+resultCitys);
		LogUtils.e("3个分类="+resultFenlei);


//		if(!TextUtils.isEmpty(resultCitys)||!TextUtils.isEmpty(resultFenlei)){
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

			if(!TextUtils.isEmpty(resultFenlei)){
				String[] attr2 = resultFenlei.split(",");
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
			String url2 = RequestUrl.getInstance(getActivity()).getUrl_modifyMemberBusinessInfo(getActivity(), map);
			LogUtils.e(url2);
			userCenter.getModifyMemberBusinessInfo(url2, Constants.INTERFACE_ModifyMemberBusinessInfo);
//		}

		type = getTypeCode(tvProperty.getText().toString());
		String url = RequestUrl.getInstance(getActivity())
				.getUrl_editMemberInfo(memberId, memberName, province, city,
						county, type, userId);
		userCenter.getEditInfo(url, Constants.INTERFACE_editMemberInfo);
	}

	private void initViews()
	{
		userCenter = new UserCenterImpl(getActivity(), this);

		progressBar = (ProgressBar) mFragment.findViewById(R.id.pb_progressbar);
		/** 初始化公司详情 信息 */
		etName = (EditText) mFragment.findViewById(R.id.et_companyinfo_name);
		etName.setFocusable(false);
		tvProperty = (TextView) mFragment
				.findViewById(R.id.tv_companyinfo_property);
		tvAddress = (TextView) mFragment
				.findViewById(R.id.tv_companyinfo_address);

		tv_companyinfo_quyu = (TextView) mFragment.findViewById(R.id.tv_companyinfo_quyu);
		tv_companyinfo_fanwei = (TextView) mFragment.findViewById(R.id.tv_companyinfo_fanwei);

		btn_save = (Button) mFragment.findViewById(R.id.btn_save);



		resultCitys = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_CITYS_DETAIL);
		resultCitysTemp = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_CITYS);

		resultFenlei = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_PINZHONG_DETAIL);
		resultFenleiTemp = SharePreferenceUtil.getString(getActivity(), Constants.USER_SETTING_PINZHONG);

		/** 公司名称 */
		String name = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBER_NAME);
		if (TextUtils.isEmpty(name))
		{
			etName.setText("未填写");
		} else
		{
			etName.setText(name);
		}

		/** 公司性质 */
		type = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBER_TYPE);
		if (TextUtils.isEmpty(type))
		{
			type = "1";
			tvProperty.setText("经销商");// 默认经销商
		} else
		{
			tvProperty.setText(getTypeName(type));
		}

		/** 公司所在城市 */
		province = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBER_PROVINCE);
		city = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBER_CITY);
		county = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_MEMBER_COUNTY);
		if (TextUtils.isEmpty(province + city + county))
		{
			tvAddress.setText("未填写");
		} else
		{
			tvAddress.setText(province + "-" +city + "-" + county);
		}

		if(!TextUtils.isEmpty(resultFenleiTemp)){
			LogUtils.e(resultFenleiTemp);
			tv_companyinfo_fanwei.setText(resultFenleiTemp);
		}else{
			tv_companyinfo_fanwei.setText("请选择");
		}

		if(!TextUtils.isEmpty(resultCitysTemp)){
			LogUtils.e(resultCitysTemp);
			tv_companyinfo_quyu.setText(resultCitysTemp);
		}else{
			tv_companyinfo_quyu.setText("请选择");
		}

	}


	@Subscriber(tag = "fenleisettingactivity")
	private void setFenleiName(List<String> list)
	{
		resultFenlei = "";
		resultFenleiTemp = "";
		for(String s : list){
			if(!TextUtils.isEmpty(s)){
				resultFenlei += s+",";
				resultFenleiTemp += s.split("\\|")[0]+",";
			}
		}
		LogUtils.e(resultFenleiTemp);
		if(!TextUtils.isEmpty(resultFenlei)){
			resultFenlei = resultFenlei.substring(0, resultFenlei.length()-1);
			resultFenleiTemp = resultFenleiTemp.substring(0, resultFenleiTemp.length()-1);
			tv_companyinfo_fanwei.setText(resultFenleiTemp);
		}else{
			tv_companyinfo_fanwei.setText("请选择");
		}
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
			tv_companyinfo_quyu.setText(resultCitysTemp);
		}else{
			tv_companyinfo_quyu.setText("请选择");
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.et_companyinfo_name:
			// 公司名
			// Editable nameText = etName.getEditableText();
			// Selection.setSelection(nameText, nameText.length());
			break;

		case R.id.tv_companyinfo_property:
			// 公司性质
			// Editable propertyText = etProperty.getEditableText();
			// Selection.setSelection(propertyText, propertyText.length());
			editCompanyProperty();
			break;

		case R.id.tv_companyinfo_address:
			// 跳转到地址设置页面
			Intent intent = new Intent(getActivity(), CityPickActivity.class);
			startActivityForResult(intent,
					Constants.COMPANY_ADDRESS_REQUEST_CODE);
			break;

		case R.id.tv_companyinfo_quyu:
			getActivity().startActivity(new Intent(getActivity(), QuyuSettingActivity.class));
			break;
		case R.id.tv_companyinfo_fanwei:
			Intent i = new Intent(getActivity(), FenleiSettingActivity.class);
			i.putExtra("memberType",type);
			getActivity().startActivity(i);
			break;
		case R.id.btn_save:
			save("");
			break;

		default:
			break;
		}
	}

	/**
	 * 修改公司性质
	 */
	private void editCompanyProperty()
	{
		final String[] property = new String[]
		{ "经销商", "终端采购用户", "钢厂生产企业", "次终端用户", "其他" };

		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("选择公司性质").setItems(property,
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						tvProperty.setText(property[which]);
						type = getTypeCode(property[which]);
					}
				});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode != MyInfoActivity.RESULT_CANCELED
				&& requestCode == Constants.COMPANY_ADDRESS_REQUEST_CODE)
		{
			province = intent.getStringExtra("PROVINCE");
			city = intent.getStringExtra("CITY");
			county = intent.getStringExtra("COUNTY");
			tvAddress.setText(province + city + county);
		}
	}

	/**
	 * 得到公司性质代号
	 * 
	 * @param property
	 *            公司性质名称
	 */
	private String getTypeCode(String property)
	{
		// type 1-经销商 2-终端用户 3-钢厂 9-其它
		if ("经销商".equals(property.trim()))
		{
			return "1";
		} else if ("终端采购用户".equals(property.trim()))
		{
			return "2";
		} else if ("钢厂生产企业".equals(property.trim()))
		{
			return "3";
		} else if ("次终端用户".equals(property.trim()))
		{
			return "4";
		}else if ("其它".equals(property.trim()))
		{
			return "9";
		}
		return "9";


	}

	/**
	 * 得到公司性质名称
	 * 
	 * @param type
	 *            公司性质代号
	 * @return
	 */
	private String getTypeName(String type)
	{
		if ("1".equals(type.trim()))
		{
			return "经销商";
		} else if ("2".equals(type.trim()))
		{
			return "终端采购用户";
		} else if ("3".equals(type.trim()))
		{
			return "钢厂生产企业";
		} else if ("4".equals(type.trim()))
		{
			return "次终端用户";
		}  else if ("9".equals(type.trim()))
		{
			return "其它";
		}
		return "其他";
	}

	@Override
	public void updateView(BaseData data)
	{
		//if(!TextUtils.isEmpty(resultCitys)&&!TextUtils.isEmpty(resultCitysTemp)){
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_SETTING_CITYS_DETAIL,
					resultCitys); // 保存选择的城市名称+code
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_SETTING_CITYS,
					resultCitysTemp); // 保存选择的城市名称
		//}
//		SharePreferenceUtil.setValue(getActivity(), Constants.USER_PROVINCE,province);
//		SharePreferenceUtil.setValue(getActivity(), Constants.USER_CITY,city);
//		SharePreferenceUtil.setValue(getActivity(), Constants.USER_COUNTY,county);

		//if(!TextUtils.isEmpty(resultFenlei)&&!TextUtils.isEmpty(resultFenleiTemp)){
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_SETTING_PINZHONG_DETAIL,
					resultFenlei);// 保存选择的品种名称+code
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_SETTING_PINZHONG,
					resultFenleiTemp);// 保存选择的品种名称
		//}


	}

	@Override
	public void isShowDialog(boolean flag)
	{
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void openCountDown(long millisInFuture, long countDownInterval)
	{
	}

	@Override
	public void upDatePersonal(EditInfoData data)
	{
	}

	@Override
	public void upDateCompany(EditInfoData data)
	{
		/** 保存数据到本地 */
		String memberName = etName.getText().toString();
		SharePreferenceUtil.setValue(getActivity(), Constants.USER_MEMBER_NAME,
				memberName);
		SharePreferenceUtil.setValue(getActivity(), Constants.USER_MEMBER_TYPE,
				type);
		SharePreferenceUtil.setValue(getActivity(),
				Constants.USER_MEMBER_PROVINCE, province);
		SharePreferenceUtil.setValue(getActivity(), Constants.USER_MEMBER_CITY,
				city);
		SharePreferenceUtil.setValue(getActivity(),
				Constants.USER_MEMBER_COUNTY, county);

		if (data.getData() == null
				|| StringUtils.isEmpty(data.getData().getMsg()))
		{
			Tools.showToast(getActivity(), "修改成功");
		} else
		{
			Tools.showToast(getActivity(), "修改成功," + data.getData().getMsg());
			EventBus.getDefault().post("", "scoreChange");
		}
	}

	public void onRefreshRight(boolean flag){
		if(!flag){  //false表示可以编辑
			etName.setFocusable(true);
			etName.setFocusableInTouchMode(true);
			etName.requestFocus();
			etName.requestFocusFromTouch();
			etName.setSelection(etName.getText().toString().length());
			tvAddress.setOnClickListener(this);
			tvProperty.setOnClickListener(this);
			tv_companyinfo_quyu.setOnClickListener(this);
			tv_companyinfo_fanwei.setOnClickListener(this);
			btn_save.setOnClickListener(this);
			btn_save.setVisibility(View.VISIBLE);
		}else{  //true表示不可编辑
			etName.setFocusable(false);
			tvAddress.setOnClickListener(null);
			tvProperty.setOnClickListener(null);
			tv_companyinfo_quyu.setOnClickListener(null);
			tv_companyinfo_fanwei.setOnClickListener(null);
			btn_save.setOnClickListener(null);
			btn_save.setVisibility(View.GONE);
		}
	}
}

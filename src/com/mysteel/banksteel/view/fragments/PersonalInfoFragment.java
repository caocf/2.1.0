package com.mysteel.banksteel.view.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mysteel.banksteel.ao.AOCallBack;
import com.mysteel.banksteel.ao.IUserCenter;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.UploadAvatarData;
import com.mysteel.banksteel.util.BitmapUtil;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.StringUtils;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ChangePhoneNumberActivity;
import com.mysteel.banksteel.view.activity.CityPickActivity;
import com.mysteel.banksteel.view.activity.MyInfoActivity;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.CircleImageView;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息片段
 * 
 */
public class PersonalInfoFragment extends BaseFragment implements
		OnClickListener, IUserCenterView
{

	/** 拍照时相片的保存地址 */
	private Uri imageUri;

	/** 需要上传的图片文件 */
	private File upLoadfile;

	/** fragment实例 */
	private View mFragment;
	/** 用户头像 */
	private CircleImageView ivAvatar;
	/** 头像旁边的图片 */
//	private ImageView ivImage;
	/** 电话号码信息 */
	private TextView tvPhoneInfo;
	/** 称谓 */
	private TextView tvSex;
	/** 昵称 */
	private EditText etName;
	/** 城市 */
	private TextView tvAddress;
	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String county;
	/** 保存按钮 */
	private Button btn_save;

	private IUserCenter UserCenter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mFragment = inflater.inflate(R.layout.fragment_userinfo, container,
				false);
		initViews();
		return mFragment;
	}

	/**
	 * 更改电话号码成功,这里接收事件
	 * 
	 * @param newPhone
	 *            新电话号码
	 */
	@Subscriber(tag = "changeCellphone")
	public void changeCellphone(String newPhone)
	{
		tvPhoneInfo.setText(SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_CELLPHONE));
	}

	/**
	 * 保存按钮被点击,执行保存个人信息协议
	 * 
	 * @param str
	 */
	@Subscriber(tag = "0")
	public void save(String str)
	{
		/** 获取界面数据 */
		String userId = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_USERID);
		String name = etName.getText().toString().trim();
		String sex = tvSex.getText().toString().trim();
		// 判断非空
		if (name.trim().length() == 0)
		{
			Tools.showToast(getActivity(), "请输入昵称");
			return;
		}
		if (StringUtils.isEmpty(sex))
		{
			Tools.showToast(getActivity(), "请选择称谓");
			return;
		}
		if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city)
				|| StringUtils.isEmpty(county))
		{
			Tools.showToast(getActivity(), "请选择工作城市");
			return;
		}
		if (!Tools.isNetworkConnected(getActivity()))
		{
			Tools.showToast(getActivity(), "请检查网络");
			return;
		}

		/** 发送请求 */
		String url = RequestUrl.getInstance(getActivity()).getUrl_editUserInfo(
				userId, name, sex, province, city, county);
		UserCenter.getEditInfo(url, Constants.INTERFACE_editUserInfo);
	}

	private void initViews()
	{

		UserCenter = new UserCenterImpl(getActivity(), this);
		/** 头像栏组件初始化 */
		ivAvatar = (CircleImageView) mFragment
				.findViewById(R.id.iv_personalinfo_avatar);
//		ivImage = (ImageView) mFragment
//				.findViewById(R.id.iv_personalinfo_image);
//		ivImage.setOnClickListener(this);

		/** 设置头像数据 */
		setAvatar();

		/** 个人信息组件初始化 */
		etName = (EditText) mFragment
				.findViewById(R.id.et_personalinfo_nickname);
		etName.setFocusable(false);
		tvSex = (TextView) mFragment.findViewById(R.id.tv_personalinfo_sex);
		tvPhoneInfo = (TextView) mFragment
				.findViewById(R.id.tv_personalinfo_phoneinfo);
		tvAddress = (TextView) mFragment
				.findViewById(R.id.tv_personalinfo_city);

		btn_save = (Button) mFragment
				.findViewById(R.id.btn_save);

		/** 设置个人信息数据 */
		/** 昵称 */
		String name = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_NAME);
		if (StringUtils.isEmpty(name))
		{
			etName.setText("未填写");
		} else
		{
			etName.setText(name);
		}

		/** 称谓 */
		String sex = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_SEX);
		if (StringUtils.isEmpty(sex))
		{
			tvSex.setText("未填写");
		} else
		{
			tvSex.setText(sex);
		}

		/** 手机号码 */
		String phone = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_CELLPHONE);
		if (StringUtils.isEmpty(phone))
		{
			tvPhoneInfo.setText("未填写");
		} else
		{
			tvPhoneInfo.setText(phone);
		}

		/** 工作城市 */
		province = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_PROVINCE);
		city = SharePreferenceUtil
				.getString(getActivity(), Constants.USER_CITY);
		county = SharePreferenceUtil.getString(getActivity(),
				Constants.USER_COUNTY);

		if (StringUtils.isEmpty(province + city + county))
		{
			tvAddress.setText("未填写");
		} else
		{
			tvAddress.setText(province +"-"+ city +"-"+ county);
		}

	}

	/**
	 * 已登录,设置头像
	 */
	private void setAvatar()
	{
			/** 从网络url中拿图片 */
			String photo = SharePreferenceUtil.getString(getActivity(),
					Constants.USER_PHTHO);
			if (!TextUtils.isEmpty(photo))
			{
				BitmapUtil.loadImage(getActivity(), photo, ivAvatar);
				return;
			} else
			{// 首次注册进来 photo 肯定是没有的
				File file = new File(Tools.AVATAR_PATH);
				if (file.exists())
				{// 若本地有这个路径
					Bitmap bitmap = BitmapFactory.decodeFile(Tools.AVATAR_PATH);
					if (bitmap != null)
					{
						ivAvatar.setImageBitmap(bitmap);
						return;
					}
				}

			}
			/** 否则设为默认图片 */
			ivAvatar.setImageResource(R.drawable.nophoto);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.iv_personalinfo_avatar:
//		case R.id.iv_personalinfo_image:
			// 选择图片作为头像
			choicePhoto();
			break;

		case R.id.tv_personalinfo_sex:
			// 称谓
			editSexBiz();
			break;

		case R.id.tv_personalinfo_phoneinfo:
			// 手机号
			startActivity(new Intent(getActivity(),
					ChangePhoneNumberActivity.class));
			break;

		case R.id.et_personalinfo_nickname:
			// 昵称,点击光标移到末尾
			// Editable eText = etName.getText();
			// Selection.setSelection(eText, eText.length());
			break;
		case R.id.tv_personalinfo_city:
			// 跳转到选择城市界面
			Intent intent = new Intent(getActivity(), CityPickActivity.class);
			startActivityForResult(intent,
					Constants.PERSONAL_ADDRESS_REQUEST_CODE);
			break;
		case R.id.btn_save:
			save("");
			break;
		default:
			break;
		}
	}

	/**
	 * 修改称谓(性别)
	 */
	private void editSexBiz()
	{
		final String[] sex = new String[]
		{ "先生", "女士" };
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("选择称谓").setItems(sex,
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						tvSex.setText(sex[which]);
					}
				});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	/**
	 * 选择头像
	 */
	private void choicePhoto()
	{
		SetUri();
		String[] items = new String[]
		{ "选择本地图片", "拍照" };
		/** 创建对话框 */
		new AlertDialog.Builder(getActivity())
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch (which)
						{
						case 0:
							/** 打开相册选择图片 */
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									Constants.IMAGE_REQUEST_CODE);
							break;
						case 1:
							/** 打开相机拍照 */
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

							startActivityForResult(intent,
									Constants.CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 设置相机拍照后图片的地址名称
	 */
	private void SetUri()
	{
		/** 获取当前时间 */
		String currentDate = Tools.getCurrentDate("yyyyMMdd_hhMMss");

		/** 创建文件夹(sdCard/myImage) */
		File destDir = new File(Environment.getExternalStorageDirectory()
				+ "/myImage/");
		if (!destDir.exists())
		{
			destDir.mkdirs();
		}
		/** 以当前时间给image命名 */
		imageUri = Uri.fromFile(new File(destDir, currentDate + ".jpg"));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		// 结果码不等于取消时候
		if (resultCode != MyInfoActivity.RESULT_CANCELED)
		{

			switch (requestCode)
			{
			case Constants.IMAGE_REQUEST_CODE:
				/** 相册里的相片 */
				startPhotoZoom(intent.getData());
				break;

			case Constants.CAMERA_REQUEST_CODE:
				/** 相机拍摄的相片 */
				if (Tools.hasSdcard())
				{
					startPhotoZoom(imageUri);
				} else
				{
					Tools.showToast(getActivity(), "未找到存储卡，无法存储照片！");
				}
				break;

			case Constants.RESULT_REQUEST_CODE:
				/** 保存并设置头像 */
				if (intent != null)
				{
					getImageToView(intent);
				}
				break;

			case Constants.PERSONAL_ADDRESS_REQUEST_CODE:
				/** 获取到地址 */
				province = intent.getStringExtra("PROVINCE");
				city = intent.getStringExtra("CITY");
				county = intent.getStringExtra("COUNTY");
				tvAddress.setText(province + city + county);
			}
		}
	}

	/**
	 * 保存裁剪之后的图片
	 */
	private void getImageToView(Intent intent)
	{
		Bundle extras = intent.getExtras();

		if (extras != null)
		{
			Bitmap photo = extras.getParcelable("data");
			File file = new File(Tools.AVATAR_PATH);
			if (file.exists())
			{
				file.delete();
			}
			BitmapUtil.save(photo, Tools.AVATAR_PATH);
			upLoadImage(photo);
		}
	}

	/**
	 * 上传头像
	 */
	private void upLoadImage(final Bitmap photo)
	{
		upLoadfile = new File(Tools.AVATAR_PATH);
		/** key值 */
		String phone = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_CELLPHONE);
		/** 创建hashmap存放图片文件 */
		Map<String, File> imageMap = new HashMap<String, File>();
		imageMap.put(phone, upLoadfile);

		/** 服务器url */
		String userId = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_USERID);
		String url = RequestUrl.getInstance(getActivity()).getUrl_uploadHeader(
				userId);

		new GetDataDAO<UploadAvatarData>(getActivity(), UploadAvatarData.class,
				new AOCallBack<UploadAvatarData>()
				{

					@Override
					public void dealWithTrue(UploadAvatarData obj)
					{
						if ("true".equals(obj.getStatus()))
						{
							/** 保存头像到文件url */
							BitmapUtil.save(photo, Tools.AVATAR_PATH);
							/** 上传成功后给用户设置头像 */
							ivAvatar.setImageBitmap(photo);

							if (obj.getData().getMsg().trim().length() != 0)
							{
								/** 发布事件使主页面修改积分 */
//								EventBus.getDefault().post("", "scoreChange");
//								Tools.showToast(getActivity(), "上传成功" + obj.getData().getMsg());
								Tools.showToast(getActivity(), "上传成功");
							} else
							{
								Tools.showToast(getActivity(), "上传成功");
							}
							/** 发布事件使主页面修改头像 */
							EventBus.getDefault().post(
									obj.getData().getHeaderPhoto(),
									"upLoadImgSuccess");
							// SharePreferenceUtil.setValue(getActivity(),
							// Constants.USER_IMAGE_CACHE,
							// Tools.AVATAR_PATH);
						} else
						{
							Tools.showToast(getActivity(), "上传失败");

						}
					}

					@Override
					public void dealWithFalse(String str)
					{
					}

					@Override
					public void dealWithException(String error)
					{
					}
				}).uploadFile(url, null, imageMap,
				Constants.INTERFACE_uploadHeader);// 上传文件
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri)
	{

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", Tools.dip2px(getActivity(), 50));
		intent.putExtra("outputY", Tools.dip2px(getActivity(), 50));
		intent.putExtra("return-data", true);
		startActivityForResult(intent, Constants.RESULT_REQUEST_CODE);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		UserCenter.finishRequest();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void updateView(BaseData data)
	{
	}

	@Override
	public void isShowDialog(boolean flag)
	{
	}

	@Override
	public void openCountDown(long millisInFuture, long countDownInterval)
	{

	}

	@Override
	public void upDatePersonal(EditInfoData data)
	{

		if ("true".equals(data.getStatus()))
		{
			/** 保存数据 */
			String name = etName.getText().toString().trim();
			String sex = tvSex.getText().toString().trim();

			SharePreferenceUtil.setValue(getActivity(), Constants.USER_NAME,
					name);
			SharePreferenceUtil
					.setValue(getActivity(), Constants.USER_SEX, sex);
			SharePreferenceUtil.setValue(getActivity(),
					Constants.USER_PROVINCE, province);
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_CITY,
					city);
			SharePreferenceUtil.setValue(getActivity(), Constants.USER_COUNTY,
					county);

			if (data.getData() == null
					|| StringUtils.isEmpty(data.getData().getMsg()))
			{
				Tools.showToast(getActivity(), "修改成功");
			} else
			// 首次修改
			{
				Tools.showToast(getActivity(), "修改成功,"
						+ data.getData().getMsg());
				EventBus.getDefault().post("", "scoreChange");
			}
			EventBus.getDefault().post("", "nameChange");
		}

	}

	@Override
	public void upDateCompany(EditInfoData data)
	{
	}


	public void onRefreshRight(boolean flag){
		if(!flag){  //false表示可以编辑
			ivAvatar.setOnClickListener(this);
			tvSex.setOnClickListener(this);
			tvPhoneInfo.setOnClickListener(this);
			etName.setFocusable(true);
			etName.setFocusableInTouchMode(true);
			etName.requestFocus();
			etName.requestFocusFromTouch();
			etName.setSelection(etName.getText().toString().length());
			tvAddress.setOnClickListener(this);
			btn_save.setOnClickListener(this);
			btn_save.setVisibility(View.VISIBLE);
		}else{  //true表示不可编辑
			ivAvatar.setOnClickListener(null);
			tvSex.setOnClickListener(null);
			tvPhoneInfo.setOnClickListener(null);
			etName.setFocusable(false);
			tvAddress.setOnClickListener(null);
			btn_save.setOnClickListener(null);
			btn_save.setVisibility(View.GONE);
		}

	}
}

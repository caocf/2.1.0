package com.mysteel.banksteel.view.activity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mysteel.banksteel.ao.AOCallBack;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

/**
 * 上传凭证
 * 
 * @author:huoguodong
 * @date：2015-5-7 下午5:43:01
 */
public class UploadCertificateActivity extends SwipeBackActivity implements
		OnClickListener
{

	/** DEBUG. */
	private static final boolean DEBUG = true & BankSteelApplication.GLOBAL_DEBUG;
	/** TAG. */
	private static final String TAG = "UploadCertificateActivity";
	/** 相机区域 */
	private LinearLayout llOpenCamera;

	/** 翻页按钮 */
	private ImageView img_show;

	/** 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	public static final String PIC_PATH = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ String.valueOf(System.currentTimeMillis()) + ".jpg";
	private Uri imageUri;

	/** 照片的路径 */
	private String selectedImagePath = "";

	private Button btn_upload;
	private EditText et_qyt, et_price;
	private String orderId = "";

	/** 需要上传的图片文件 */
	private File upLoadfile;
	private TextView tv_product_name;// 钢品信息
	private String steelMessage = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_certificate);

		initViews();
	}

	/**
	 * 初始化页面组件
	 */
	@Override
	protected void initViews()
	{
		super.initViews();
		Bundle b = getIntent().getExtras();
		orderId = b.getString("orderId");// 获取发布求购成功后得到的需求单Id
		steelMessage = b.getString("steel");

		llOpenCamera = (LinearLayout) findViewById(R.id.ll_open_camera);
		img_show = (ImageView) findViewById(R.id.img_showimg);
		btn_upload = (Button) findViewById(R.id.btn_upload);
		et_qyt = (EditText) findViewById(R.id.et_count);
		et_price = (EditText) findViewById(R.id.et_unit_price);
		tv_product_name = (TextView) findViewById(R.id.tv_product_name);
		tv_product_name.setText(steelMessage);

		tvTitle.setText("上传凭证");

		llOpenCamera.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		btn_upload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.menu_layout:
			finish();
			break;
		case R.id.ll_open_camera:// 拍照或从本地选择照片
			showDialog();
			break;
		case R.id.btn_upload:// 上传提交按钮
			upLoadImage();
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("hiding")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED)
		{

			switch (requestCode)
			{
			case IMAGE_REQUEST_CODE:// 从相册返回时获得的图片在内存中的路径 选择相册

				selectedImagePath = getPath(data.getData());
				if (DEBUG)
				{
					Log.d(TAG, "本地----选择的文件路径为 : " + selectedImagePath);
				}
				ParcelFileDescriptor parcelFD = null;
				try
				{
					parcelFD = getContentResolver().openFileDescriptor(
							data.getData(), "r");
					FileDescriptor imageSource = parcelFD.getFileDescriptor();
					// Decode image size
					BitmapFactory.Options o = new BitmapFactory.Options();
					o.inJustDecodeBounds = true;
					BitmapFactory.decodeFileDescriptor(imageSource, null, o);

					// the new size we want to scale to
					final int REQUIRED_SIZE = 1024;

					// Find the correct scale value. It should be the power of
					// 2.
					int width_tmp = o.outWidth, height_tmp = o.outHeight;
					int scale = 1;
					while (true)
					{
						if (width_tmp < REQUIRED_SIZE
								&& height_tmp < REQUIRED_SIZE)
						{
							break;
						}
						width_tmp /= 2;
						height_tmp /= 2;
						scale *= 2;
					}

					// decode with inSampleSize
					BitmapFactory.Options o2 = new BitmapFactory.Options();
					o2.inSampleSize = scale;
					Bitmap bitmap = BitmapFactory.decodeFileDescriptor(
							imageSource, null, o2);

					img_show.setImageBitmap(bitmap);
					img_show.setScaleType(ScaleType.FIT_XY);
					compressImage(selectedImagePath, selectedImagePath, 80);

				} catch (FileNotFoundException e)
				{
					// handle errors
				} catch (IOException e)
				{
					// handle errors
				} finally
				{
					if (parcelFD != null)
						try
						{
							parcelFD.close();
						} catch (IOException e)
						{
							// ignored
						}
				}

				break;
			case CAMERA_REQUEST_CODE:// 拍照选择
				selectedImagePath = PIC_PATH;
				startPhotoZoom(imageUri);
				// Bitmap bitmap = BitmapFactory.decodeFile(PIC_PATH);
				// Log.d(TAG, PIC_PATH);
				// bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
				//
				// img_show.setImageBitmap(bitmap);
				//
				// selectedImagePath = PIC_PATH;
				//
				// try
				// {
				// compressImage(PIC_PATH, PIC_PATH, 80);
				// } catch (FileNotFoundException e)
				// {
				// e.printStackTrace();
				// }
				break;
			case RESULT_REQUEST_CODE:
				if (data != null)
				{
					getImageToView(data);
				}
				break;
			}

		}

	}

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
		intent.putExtra("outputX",
				Tools.dip2px(UploadCertificateActivity.this, 50));
		intent.putExtra("outputY",
				Tools.dip2px(UploadCertificateActivity.this, 50));
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	private void showDialog()
	{
		TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
				UploadCertificateActivity.this);
		alert.setMessage(getResources().getString(R.string.updata_photo));
		alert.setNegativeButton(
				// 本地取图片
				getResources().getString(R.string.take_photo_from_native),
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intentFromGallery = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intentFromGallery.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intentFromGallery,
								IMAGE_REQUEST_CODE);

						dialog.dismiss();
					}

				});
		alert.setPositiveButton(getResources().getString(R.string.take_photo),
				new DialogInterface.OnClickListener()
				{// 拍照

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intentFromCapture = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);

						File photo = new File(PIC_PATH);
						photo.getParentFile().mkdirs();
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(photo));
						startActivityForResult(intentFromCapture,
								CAMERA_REQUEST_CODE);
						imageUri = Uri.fromFile(photo);
						dialog.dismiss();

					}

				});
		alert.create().show();
	}

	/**
	 * 获取访问SD卡中图片路径
	 * 
	 * @return
	 */
	public String getPath(Uri uri)
	{
		String[] projection =
		{ MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/**
	 * 保存裁剪之后的图片数据并显示在当前页面
	 * 
	 * @param data
	 */
	private void getImageToView(Intent data)
	{
		Bundle extras = data.getExtras();
		if (extras != null)
		{
			Bitmap photo = extras.getParcelable("data");
			// @SuppressWarnings("deprecation")
			// Drawable drawable = new BitmapDrawable(photo);
			// img_show.setImageDrawable(drawable);
			img_show.setImageBitmap(photo);
			img_show.setScaleType(ScaleType.FIT_XY);
			try
			{
				compressImage(PIC_PATH, PIC_PATH, 80);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩图片，处理某些手机拍照角度旋转的问题
	 */
	public static String compressImage(String filePath, String fileName, int q)
			throws FileNotFoundException
	{
		Bitmap bm = getSmallBitmap(filePath);
		int degree = readPictureDegree(filePath);
		if (degree != 0)
		{// 旋转照片角度
			bm = rotateBitmap(bm, degree);
		}
		// File imageDir = SDCardUtils.getImageDir(context);
		File outputFile = new File(fileName);
		FileOutputStream out = new FileOutputStream(outputFile);
		bm.compress(Bitmap.CompressFormat.JPEG, q, out);
		return outputFile.getPath();
	}

	public static int readPictureDegree(String path)
	{
		int degree = 0;
		try
		{
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation)
			{
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, int degress)
	{
		if (bitmap != null)
		{
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath)
	{
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight)
	{
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth)
		{
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	private void upLoadImage()
	{
		String qty = et_qyt.getText().toString().trim();
		String price = et_price.getText().toString().trim();
		if (TextUtils.isEmpty(qty))
		{
			Tools.showToast(this, "请填写数量！");
			return;
		}
		if (TextUtils.isEmpty(price))
		{
			Tools.showToast(this, "请填写价格！");
			return;
		}

		if (TextUtils.isEmpty(selectedImagePath))
		{
			Tools.showToast(this, "请先拍照或者从本地选取一张图片！");
			return;
		}
		String url = RequestUrl.getInstance(this).getUrl_getUploadCert(this,
				orderId, qty, price);
		upLoadfile = new File(selectedImagePath);
		/** 创建hashmap存放图片文件 */
		Map<String, File> imageMap = new HashMap<String, File>();
		imageMap.put("uploadCert", upLoadfile);
		/** 服务器url */
		new GetDataDAO<BaseData>(this, BaseData.class,
				new AOCallBack<BaseData>()
				{

					@Override
					public void dealWithTrue(BaseData obj)
					{
						if ("true".equals(obj.getStatus()))
						{
							Tools.showToast(UploadCertificateActivity.this,
									"上传成功");
//							Intent intent = new Intent(
//									UploadCertificateActivity.this,
//									OrderCentreActivity.class);
//							startActivity(intent);
							EventBus.getDefault().post("", "finish");
							EventBus.getDefault().post("", "refreshList");
							finish();
						} else
						{
							Tools.showToast(UploadCertificateActivity.this,
									"上传失败");
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
				Constants.INTERFACE_uploadCert);// 上传文件
	}

}

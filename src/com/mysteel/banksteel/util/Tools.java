package com.mysteel.banksteel.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.entity.UserInfoData;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteel.view.ui.sweetalert.SweetAlertDialog;
import com.mysteel.banksteeltwo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.simple.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整个应用的工具类
 *
 * @author zoujian
 * @创建时间：2014-10-24下午2:20:19
 */
public class Tools
{
    /**
     * 头像保存到sdCard的绝对路径
     */
    public static final String AVATAR_PATH = Environment
            .getExternalStorageDirectory().getPath() + Constants.AVATAR_PATH;

    private static final boolean DEBUG = false;// youxiugai

    private static final String TAG = "Tools";

    private static Toast mToast;

    /**
     * 通用toast. 如果重复发出同一条msg，则将时间初始化，而不是重复弹出提示框
     */
    public static void showToast(Context context, String msg)
    {
        if (null == context)
        {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_custom_layout, null);
        if (mToast == null)
        {
            mToast = new Toast(context.getApplicationContext());
        }
        TextView textView = (TextView) layout.findViewById(R.id.custom_toast);
        textView.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(layout);
        mToast.show();

    }

    public static byte[] readStream(InputStream inputStream) throws Exception
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1)
        {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();
        return bout.toByteArray();

    }

    /**
     * 检测网络是否可用.
     */
    public static boolean isNetworkConnected(Context context)
    {
        boolean flag = false;
        if (null == context)
            return false;
        try
        {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (connectivity == null)
            {
                return false;
            }
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            flag = networkInfo != null && networkInfo.isAvailable();
        } catch (Exception e)
        {
            if (DEBUG)
            {
                Log.v(TAG, e.toString());
            }
            return false;
        }
        return flag;
    }

    /**
     * 编码转换
     */
    public static String EncodetoUTF8(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            try
            {
                return URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * MD5 加密
     *
     * @param str
     * @return
     */
    public static String getMD5Pass(String str)
    {

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查query是否是电话号码
     *
     * @return 是否是电话号码
     */
    public static boolean checkIsPhoneNumber(String num)
    {
        if (TextUtils.isEmpty(num))
        {
            return false;
        }
        return Patterns.PHONE.matcher(num).matches();
    }

    /**
     * 检查query是否是密码
     *
     * @param password
     * @return 是否是密码
     */
    public static boolean checkIsPassword(String password)
    {
        if (TextUtils.isEmpty(password))
        {
            return false;
        }
        return Patterns.PASSWORD.matcher(password).matches();
    }

    /**
     * make a call
     *
     * @param context
     */
    public static void makeCall(final Context context, final String cellphone)
    {
        try
        {
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
//                    + cellphone));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);


//            Intent in2 = new Intent();
//            in2.setAction(Intent.ACTION_CALL);
//            in2.setData(Uri.parse("tel:" + cellphone));
//            context.startActivity(in2);

            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("拨打电话")
                    .setContentText("确定要拨打" + cellphone + "吗?")
                    .setCancelText("取 消")
                    .setConfirmText("确 定")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            Uri uri = Uri.parse("tel:" + cellphone);
                            Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
                            context.startActivity(call);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        } catch (ActivityNotFoundException e)
        {
            if (DEBUG)
            {
                Log.e(TAG, e.toString());
            }
            // Toast.makeText(context, "抱歉，未找到打电话的应用",
            // Toast.LENGTH_SHORT).show();
            Tools.showToast(context, "抱歉，未找到打电话的应用");
        } catch (Exception e)
        {
            if (DEBUG)
            {
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * make a message
     *
     * @param context
     * @param text
     */
    public static void makeMessage(Context context, String phone, String text)
    {
        Uri smsToUri = Uri.parse("smsto:" + phone);// 联系人地址
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,
                smsToUri);
        mIntent.putExtra("sms_body", text);// 短信内容
        context.startActivity(mIntent);
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard()
    {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static String getUserSession(Context context, String tag)
    {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(tag, "");
    }

    /**
     * 修改用户session
     *
     * @param context   context
     * @param tag       需要修改的Tag
     * @param setString 修改后的TagString
     */
    public static void setUserSession(Context context, String tag,
                                      String setString)
    {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(tag, setString);
        editor.commit();
    }

    /**
     * 获取本机信息（友盟统计提供）
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context)
    {
        try
        {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id))
            {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id))
            {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取手机机器码
     *
     * @param context
     * @return
     */
    public static String getDeviceIn(Context context)
    {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String device_id = tm.getDeviceId();
        if (TextUtils.isEmpty(device_id))
        {
            device_id = "机器码未知";
        }

        return device_id;
    }

    /**
     * 获取手机的设备号
     *
     * @return
     */
    private static String getEquipment()
    {
        String equipment = android.os.Build.MODEL;
        if (TextUtils.isEmpty(equipment))
        {
            equipment = "设备未知";
        }
        return equipment;
    }

    /**
     * 拼接好的设备信息
     *
     * @param context
     * @return
     */
    public static String getAndroidMsg(Context context)
    {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String device_id = tm.getDeviceId();
        if (TextUtils.isEmpty(device_id))
        {
            device_id = "未知机器码";
        }

        String equipment = android.os.Build.MODEL;
        if (TextUtils.isEmpty(equipment))
        {
            equipment = "未知设备";
        }

        String url = "&imei=" + device_id.replaceAll(" ", "") + "&mobileModel=" + equipment.replaceAll(" ", "");
        return url;
    }

    /**
     * 隐藏手机号码中间四位
     *
     * @return
     */
    public static String hiddenPhoneNumber(String phone)
    {
        if (phone == null || phone.length() == 0)
        {
            return "";
        }
        if (phone.length() <= 7)
        {
            return phone;
        }
        String hiddenPhone = phone.substring(0, 3) + "****"
                + phone.substring(7);
        return hiddenPhone;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles)
    {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 根据子品种的id获取父品种信息
     *
     * @param breedId
     */
    public static Steel getSteelMessage(String breedId)
    {
        Steel steel = new Steel();
        if (breedId.length() <= 2)
        {
            return steel;
        }
        String id = breedId.substring(0, 2);
        if ("01".equals(id))
        {
            steel.setId("01");
            steel.setImageId(R.drawable.constructionsteel);
            steel.setName("建筑用钢");
        } else if ("02".equals(id))
        {
            steel.setId("02");
            steel.setImageId(R.drawable.hot_rolled_coil);
            steel.setName("热轧板卷");
        } else if ("03".equals(id))
        {
            steel.setId("03");
            steel.setImageId(R.drawable.cut_deal);
            steel.setName("中厚板");
        } else if ("04".equals(id))
        {
            steel.setId("04");
            steel.setImageId(R.drawable.plate_roll);
            steel.setName("冷轧板卷");
        } else if ("05".equals(id))
        {
            steel.setId("05");
            steel.setImageId(R.drawable.cold_coating);
            steel.setName("冷镀");
        } else if ("06".equals(id))
        {
            steel.setId("06");
            steel.setImageId(R.drawable.steeltube);
            steel.setName("管材");
        } else if ("07".equals(id))
        {
            steel.setId("07");
            steel.setImageId(R.drawable.proximate_matter);
            steel.setName("型材");
        } else if ("08".equals(id))
        {
            steel.setId("08");
            steel.setImageId(R.drawable.other_steel);
            steel.setName("其他钢材");
        } else if ("09".equals(id))
        {
            steel.setId("09");
            steel.setImageId(R.drawable.stainlesssteel);
            steel.setName("不锈钢");
        } else if ("10".equals(id))
        {
            steel.setId("10");
            steel.setImageId(R.drawable.alloy_structual);
            steel.setName("优特钢");
        } else if ("11".equals(id))
        {
            steel.setId("11");
            steel.setImageId(R.drawable.billet);
            steel.setName("钢坯");
        } else if ("12".equals(id))
        {
            steel.setId("12");
            steel.setImageId(R.drawable.specific_steel);
            steel.setName("品种钢");
        }

        return steel;
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean isLogin(Context contect)
    {
        return SharePreferenceUtil.getBoolean(contect, Constants.USER_IS_LOGIN);
    }

    /**
     * get current data time based on give pattern: yyyyMMdd_hhMMss
     *
     * @param pattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(String pattern)
    {
        String currentDate = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        currentDate = format.format(new Date());
        return currentDate;
    }

    /**
     * parse given data time based on give pattern: yyyyMMdd_hhMMss
     *
     * @param pattern
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String format(String pattern, Date date)
    {
        String currentDate = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (date != null)
        {
            currentDate = format.format(date);
        }
        return currentDate;
    }

    /**
     * 获取屏幕的宽度，像素值
     *
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity activity)
    {
        Point point = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay()
                .getSize(point);
        return point.x;

    }

    /**
     * 获取屏幕的高度，像素值
     *
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity activity)
    {
        Point point = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay()
                .getSize(point);
        return point.x;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断一个字符串是否是字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str)
    {
        boolean flag = false;
        if (str != null)
        {
            if ((str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
                    || (str.charAt(0) >= 'a' && str.charAt(0) <= 'z'))
            {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 通用dialog，一个键
     */
    public static void commonDialogOneBtn(Context context, String title,
                                          String content, String buttonName)
    {
        if (null == content)
        {
            return;
        }
        TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setPositiveButton(buttonName,
                new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }

                });
        alert.create().show();
    }

    /**
     * @return 获取正在运行的activity类名
     */
    public static String getCurrentRunningActivityName()
    {
        BankSteelApplication instance = BankSteelApplication.getInstance();
        ActivityManager am = (ActivityManager) instance
                .getSystemService(Context.ACTIVITY_SERVICE);
        String name = am.getRunningTasks(1).get(0).topActivity.getClassName();
        return name;
    }

    public static boolean iSHumanServeActivity()
    {
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[level].getClassName();
        return className.contains("HumanServeActivity");

    }

    public static boolean isBackground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses)
        {
            if (appProcess.processName.equals(context.getPackageName()))
            {
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    return true;
                } else
                {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 用户登录成功后保存缓存数据 (因为拿不到密码，注意这里没有保存密码，哪里调用该方法哪里保存)
     */
    public static void saveCache(Context context, UserInfoData Udata)
    {
        if (Udata.getData() == null)
        {
            return;
        }
        String mobile = Udata.getData().getUser().getMobile();
        String userId = Udata.getData().getUser().getUserId();

        String name = Udata.getData().getUser().getName();
        String userName = Udata.getData().getUser().getUsername();// score
        String userMemberId = Udata.getData().getUser().getMemberId();
        String score = Udata.getData().getScore();
        String memberName = Udata.getData().getMember().getName();//个人的公司信息
        String sex = Udata.getData().getUser().getSex();
        String province = Udata.getData().getUser().getState();
        String city = Udata.getData().getUser().getCity();
        String county = Udata.getData().getUser().getArea();
        String type = Udata.getData().getMember().getType();// 公司性质
        String memberProvince = Udata.getData().getMember().getState();
        String memberCity = Udata.getData().getMember().getCity();
        String memberCounty = Udata.getData().getMember().getArea();
        String photo = Udata.getData().getUser().getPhoto();

        SharePreferenceUtil.setValue(context, Constants.PREFERENCES_CELLPHONE,
                mobile);// 保存用户名
        SharePreferenceUtil.setValue(context, Constants.PREFERENCES_USERID,
                userId);// 用户userid
        SharePreferenceUtil.setValue(context, Constants.USER_NAME, name);// 名字
        SharePreferenceUtil
                .setValue(context, Constants.USER_USERNAME, userName);// 用户名
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBERID,
                userMemberId);// MemberId
        SharePreferenceUtil.setValue(context, Constants.USER_SCORE, score);// 积分
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_NAME,
                memberName);// 用户名
        SharePreferenceUtil.setValue(context, Constants.USER_SEX, sex);
        SharePreferenceUtil
                .setValue(context, Constants.USER_PROVINCE, province);
        SharePreferenceUtil.setValue(context, Constants.USER_CITY, city);
        SharePreferenceUtil.setValue(context, Constants.USER_COUNTY, county);
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_TYPE, type);
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_PROVINCE,
                memberProvince);
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_CITY,
                memberCity);
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_COUNTY,
                memberCounty);
        SharePreferenceUtil.setValue(context, Constants.USER_PHTHO, photo);
        SharePreferenceUtil.setValue(context, Constants.USER_IS_LOGIN, true);

        //保存业务区域 3个城市
        String citys = ""; //名字+code
        String citys2 = ""; //只有名字
        if (Udata.getData().getMemberBusiness().getData().getBusinessAreas() != null && Udata.getData().getMemberBusiness().getData().getBusinessAreas().size() > 0)
        {
            for (int i = 0; i < Udata.getData().getMemberBusiness().getData().getBusinessAreas().size(); i++)
            {
                citys += Udata.getData().getMemberBusiness().getData().getBusinessAreas().get(i).getCity() + Udata.getData().getMemberBusiness().getData().getBusinessAreas().get(i).getCityCode() + ",";
                citys2 += Udata.getData().getMemberBusiness().getData().getBusinessAreas().get(i).getCity() + ",";
            }
        }
        if (!TextUtils.isEmpty(citys))
        {
            citys = citys.substring(0, citys.length() - 1);
            citys2 = citys2.substring(0, citys2.length() - 1);
        }
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_CITYS_DETAIL,
                citys); // 保存选择的城市名称+code
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_CITYS,
                citys2); // 保存选择的城市名称


        //保存业务范围 3个种类
        String zhonglei = ""; //名字+code
        String zhonglei2 = ""; //只有名字
        if (Udata.getData().getMemberBusiness().getData().getBusinessScopes() != null && Udata.getData().getMemberBusiness().getData().getBusinessScopes().size() > 0)
        {
            for (int i = 0; i < Udata.getData().getMemberBusiness().getData().getBusinessScopes().size(); i++)
            {
                zhonglei += Udata.getData().getMemberBusiness().getData().getBusinessScopes().get(i).getName() + "|" + Udata.getData().getMemberBusiness().getData().getBusinessScopes().get(i).getCode() + ",";
                zhonglei2 += Udata.getData().getMemberBusiness().getData().getBusinessScopes().get(i).getName() + ",";
            }
        }
        if (!TextUtils.isEmpty(zhonglei))
        {
            zhonglei = zhonglei.substring(0, zhonglei.length() - 1);
            zhonglei2 = zhonglei2.substring(0, zhonglei2.length() - 1);
        }
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_PINZHONG_DETAIL,
                zhonglei);// 保存选择的品种名称+code
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_PINZHONG,
                zhonglei2);// 保存选择的品种名称

        /** 开启线程保存头像在本地 */
        new MyAsyncTask(context).execute(photo);
        EventBus.getDefault().post("login", "main_data");
        EventBus.getDefault().post("login", "showPush");
        EventBus.getDefault().post("login", "LOGIN");
    }

    /**
     * 退出登录数据清空(用户名不清除)
     */
    public static void clearCache(Context context)
    {
        // 退出登录后清空数据
        SharePreferenceUtil.setValue(context, Constants.PREFERENCES_USERID, "");// 用户userid
        SharePreferenceUtil.setValue(context, Constants.USER_NAME, "");// 名字
        // SharePreferenceUtil.setValue(context, Constants.USER_USERNAME, "");//
        // 用户名
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBERID, "");// MemberId
        SharePreferenceUtil.setValue(context, Constants.USER_SCORE, "");// 积分
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_NAME, "");// 用户名
        SharePreferenceUtil.setValue(context, Constants.USER_SEX, "");
        SharePreferenceUtil.setValue(context, Constants.USER_PROVINCE, "");
        SharePreferenceUtil.setValue(context, Constants.USER_CITY, "");
        SharePreferenceUtil.setValue(context, Constants.USER_COUNTY, "");
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_TYPE, "");
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_PROVINCE,
                "");
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_CITY, "");
        SharePreferenceUtil.setValue(context, Constants.USER_MEMBER_COUNTY, "");
        SharePreferenceUtil.setValue(context, Constants.USER_PHTHO, "");
        SharePreferenceUtil.setValue(context, Constants.USER_IS_LOGIN, false);
        SharePreferenceUtil.setValue(context, Constants.USER_PASSWARD, "");

        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_CITYS, "");
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_PINZHONG, "");
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_CITYS_DETAIL, "");
        SharePreferenceUtil.setValue(context, Constants.USER_SETTING_PINZHONG_DETAIL, "");

        SharePreferenceUtil.setValue(context, Constants.USER_SEARCH_HISTORY, "");


//        File file = new File(AVATAR_PATH);
//        if (file.exists())
//        {
//            file.delete();
//        }
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/banksteel");
        RecursionDeleteFile(file);
    }

    public static void RecursionDeleteFile(File file)
    {
        if (file.isFile())
        {
            file.delete();
            return;
        }
        if (file.isDirectory())
        {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0)
            {
                file.delete();
                return;
            }
            for (File f : childFile)
            {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static String getVersion(Context context)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e)
        {
            // 抛异常 默认版本号1.0
            return "1.0";
        }
    }

    /**
     * @param url     图片的url
     * @param context 上下文
     * @return 图片的bitmap
     */
    public static class MyAsyncTask extends AsyncTask<String, Void, Bitmap>
    {
        private Context context;

        public MyAsyncTask(Context context)
        {
            super();
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params)
        {
            /*
             * byte[] data = null; try { URL url = new URL(params[0]);
			 * HttpURLConnection conn = (HttpURLConnection) url
			 * .openConnection(); conn.setRequestMethod("GET");
			 * conn.setReadTimeout(5000);
			 * 
			 * InputStream input = conn.getInputStream(); ByteArrayOutputStream
			 * output = new ByteArrayOutputStream(); byte[] buffer = new
			 * byte[1024]; int len = 0; while ((len = input.read(buffer)) != -1)
			 * { output.write(buffer, 0, len); } data = output.toByteArray();
			 * input.close(); } catch (Exception e) { e.printStackTrace(); }
			 * Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
			 * data.length); return bitmap;
			 */
            ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                    .createDefault(context);

            ImageLoader.getInstance().init(configuration); // 使用ImageLoad必须进行初始化

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.nophoto)
                    .showImageOnFail(R.drawable.nophoto)
                    .resetViewBeforeLoading(true).cacheOnDisc(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300)).build();
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(params[0],
                    options);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            super.onPostExecute(result);
            if (result != null)
            {
                BitmapUtil.save(result, Tools.AVATAR_PATH);
            }
        }
    }

    /**
     * 拉取头像专用方法
     *
     * @param imageUrl
     * @param imgView
     */
    public static void loadImage(String imageUrl, ImageView imgView)
    {
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
                20);
        ImageCache imageCache = new ImageCache()
        {
            @Override
            public void putBitmap(String key, Bitmap value)
            {
                lruCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key)
            {
                return lruCache.get(key);
            }
        };
        com.android.volley.toolbox.ImageLoader imageLoader = new com.android.volley.toolbox.ImageLoader(
                BankSteelApplication.requestQueue, imageCache);
        com.android.volley.toolbox.ImageLoader.ImageListener listener = com.android.volley.toolbox.ImageLoader.getImageListener(imgView,
                R.drawable.nophoto, R.drawable.nophoto);
        imageLoader.get(imageUrl, listener);
    }


    /*时间戳转换成字符窜*/
    public static String getDateToString(String dueTime)
    {
        long time = Long.parseLong(dueTime);
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("dd天HH时mm分");
        return sf.format(d);
    }

    public static int getScreenWidth(Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenDensity(Context context)
    {
        return context.getResources().getDisplayMetrics().density;
    }

    static final Map<String, String> ENCRYPT_MAP;

    static
    {
        ENCRYPT_MAP = new HashMap<String, String>();
        ENCRYPT_MAP.put("0", "D");
        ENCRYPT_MAP.put("1", "E");
        ENCRYPT_MAP.put("2", "N");
        ENCRYPT_MAP.put("3", "G");
        ENCRYPT_MAP.put("4", "Y");
        ENCRYPT_MAP.put("5", "I");
        ENCRYPT_MAP.put("6", "Z");
        ENCRYPT_MAP.put("7", "X");
        ENCRYPT_MAP.put("8", "Q");
        ENCRYPT_MAP.put("9", "P");
    }

    public static String getCodeByUserId(String userId)
    {
        StringBuilder encrypt = new StringBuilder();
        for (int i = 0; i < userId.length(); i++)
        {
            char item = userId.charAt(i);
            encrypt.append(ENCRYPT_MAP.get(String.valueOf(item)));
        }
        if (encrypt.length() == 1)
        {
            encrypt.append("FBCAH");
        }
        if (encrypt.length() == 2)
        {
            encrypt.append("LKJM");
        }
        if (encrypt.length() == 3)
        {
            encrypt.append("ORS");
        }
        if (encrypt.length() == 4)
        {
            encrypt.append("TU");
        }
        if (encrypt.length() == 5)
        {
            encrypt.append("W");
        }
        return encrypt.toString();
    }

    public static  void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}

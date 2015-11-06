package com.mysteel.banksteel.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteeltwo.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;

import java.util.LinkedList;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 下午7:52:09
 */
public class BaseActivity extends FragmentActivity
{

    /**
     * DEBUG.
     */
    private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;
    /**
     * tag.
     */
    private static final String TAG = "BaseActivity";
    /**
     * 下一次Activity启动时，新Activity的进入动画
     */
    private static int nextEnterAnimWhenStarting;
    /**
     * 下一次Activity启动时，旧Activity的退出动画
     */
    private static int nextExitAnimWhenStarting;
    /**
     * 下一次Activity结束时，新Activity的进入动画
     */
    private static int nextEnterAnimWhenFinishing;
    /**
     * 下一次Activity结束时，旧Activity的退出动画
     */
    private static int nextExitAnimWhenFinishing;

    /**
     * 启动时，新Activity的进入动画
     */
    private int mEnterAnimWhenStarting;
    /**
     * 启动时，旧Activity的退出动画
     */
    private int mExitAnimWhenStarting;
    /**
     * 结束时，新Activity的进入动画
     */
    private int mEnterAnimWhenFinishing;
    /**
     * 结束时，旧Activity的退出动画
     */
    private int mExitAnimWhenFinishing;
    /**
     * 判断此activity是否已经结束
     */
    private boolean mIsStop = false;

    /**
     * title
     */
    public RelativeLayout backLayout; // 返回按钮区域
    public ImageView menuBtn; // 菜单按钮
    public ImageView imBack; // 返回
    public TextView tvTitle; // 标题
    public RelativeLayout rightLayout; // 右侧控制区域
    public TextView tvRightText; // 右侧文本
    public ImageView imRightImage; // 右侧图标
    public ImageView share_img_yixiang;//右侧 意向单单独用的图片

    /**
     * BaseActivity中初始化页面头部组件，包括：
     * 菜单按钮（menuBtn）、返回按钮区域（backLayout）、返回（imBack）、标题（
     * tvTitle）、右侧控制区（rightLayout）、 右侧文本（tvRightText）、右侧图标（imRightImage）
     * <strong>请在子Activity中继承该方法
     */
    protected void initViews()
    {
        backLayout = (RelativeLayout) findViewById(R.id.menu_layout);
        menuBtn = (ImageView) findViewById(R.id.menu_imgbtn);
        imBack = (ImageView) findViewById(R.id.im_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rightLayout = (RelativeLayout) findViewById(R.id.share_layout);
        tvRightText = (TextView) findViewById(R.id.tv_title_right_text);
        imRightImage = (ImageView) findViewById(R.id.share_imgbtn);
        share_img_yixiang = (ImageView) findViewById(R.id.share_img_yixiang);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);
        mLiveActivityNum++;
        String name = getComponentName().getClassName();
        name = name.replace(getPackageName(), "");
        if (DEBUG)
        {
            Log.v(TAG, name);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mIsStop = false;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
        mLiveActivityNum--;
        String name = getComponentName().getClassName();
        name = name.replace(getPackageName(), "");
        if (!isAppInForeground())
        {
        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mIsStop = true;
        if (!isAppInForeground())
        {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            addToTask(this);
            EventBus.getDefault().register(this);
            // 修改状态栏颜色，4.4+生效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                setTranslucentStatus();
//				setTranslucentStatus(this,true);
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
//			    tintManager.setNavigationBarTintEnabled(true);
                tintManager.setStatusBarTintResource(R.color.main_imred);//通知栏所需颜色
            }

            // 设置本次Activity切换动画
            if (nextEnterAnimWhenStarting != 0 || nextExitAnimWhenStarting != 0)
            {
                mEnterAnimWhenStarting = nextEnterAnimWhenStarting;
                mExitAnimWhenStarting = nextExitAnimWhenStarting;
            }

            if (nextEnterAnimWhenFinishing != 0
                    || nextExitAnimWhenFinishing != 0)
            {
                mEnterAnimWhenFinishing = nextEnterAnimWhenFinishing;
                mExitAnimWhenFinishing = nextExitAnimWhenFinishing;
            }
            setNextPendingTransition(0, 0, 0, 0);
//            overridePendingTransition(Animation.INFINITE, Animation.INFINITE);
        } catch (Exception e)
        {
            if (DEBUG)
            {
                Log.i(TAG, "初始化数据出现错误！");
            }
        }
    }

    // 获取手机状态栏高度
//    public int getStatusBarHeight()
//    {
//        Class<?> c = null;
//        Object obj = null;
//        Field field = null;
//        int x = 0, statusBarHeight = 0;
//        try
//        {
//            c = Class.forName("com.android.internal.R$dimen");
//            obj = c.newInstance();
//            field = c.getField("status_bar_height");
//            x = Integer.parseInt(field.get(obj).toString());
//            statusBarHeight = getResources().getDimensionPixelSize(x);
//        } catch (Exception e1)
//        {
//            e1.printStackTrace();
//        }
//        return statusBarHeight;
//    }

    @TargetApi(19)
    protected void setTranslucentStatus()
    {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    public Resources getResources()
    {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;

    }

    @Override
    protected void onDestroy()
    {
        // TODO 这个地方到时候可能会需要改动
        // Tools.cancelToast();
        removeFromTask(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取栈顶Activity
     *
     * @return Activity
     */
    public static Activity getTopActivity()
    {
        return mActivityStack.getLast();
    }

    /**
     * 将Activity加入栈管理队列中。
     *
     * @param activity Activity。
     */
    public static synchronized void addToTask(Activity activity)
    {
        // 移到顶端。
        mActivityStack.remove(activity);
        mActivityStack.add(activity);
    }

    /**
     * 获取当前有几个界面已打开
     *
     * @return
     */
    public static int getTaskActivities()
    {
        return mActivityStack.size();
    }

    /**
     * 从栈管理队列中移除该Activity。
     *
     * @param activity Activity。
     */
    public static synchronized void removeFromTask(Activity activity)
    {
        mActivityStack.remove(activity);
        if (mActivityStack.isEmpty())
        {
            if (sEmptyKillApp)
            {
                quitApp(activity.getApplicationContext());
            } else
            {
                sEmptyKillApp = true;
            }
        }
    }

    /**
     * 清除栈队列中除MainActivity以外的所有Activity。
     */
    public static synchronized void clearTaskExceptMain()
    {
        for (Activity activity : mActivityStack)
        {
            if (!(activity instanceof MainActivity))
            {
                activity.finish();
            }
        }
    }

    /**
     * 清除栈队列中的所有Activity。
     */
    public static synchronized void clearTask()
    {
        if (!mActivityStack.isEmpty())
        {
            sEmptyKillApp = false;
        }
        for (Activity activity : mActivityStack)
        {
            activity.finish();
        }
    }

    /**
     * 清除栈队列中除exceptActivity以外的所有Activity。与FLAG_ACTIVITY_CLEAR_TASK功能类似。
     *
     * @param exceptActivity exceptActivity。
     */
    public static synchronized void clearTaskExcept(Activity exceptActivity)
    {
        for (Activity activity : mActivityStack)
        {
            if (activity != exceptActivity)
            {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用。
     *
     * @param context Context.
     */
    public static void quitApp(Context context)
    {
        // if (isAppInForeground()) {
        // AlarmManager alarmManager =
        // (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Intent intent = new Intent(BaseActivity.ACTION_ALARM_DING_CALLBACK);
        // PendingIntent mPintent =
        // PendingIntent.getBroadcast(context.getApplicationContext(), 0,
        // intent, 0);
        // final int delayTime = 2000;
        // //用AlarmManager延迟2秒发送通知，以免进程过早被杀死导致广播无法收到。
        // alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() +
        // delayTime, mPintent);
        // }

        // android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 判断应用是否在前台。
     *
     * @return 如果在前台，返回true.
     */
    public static boolean isAppInForeground()
    {
        return mLiveActivityNum > 0;
    }

    /**
     * 判断应用程序是否刚刚回到前台。
     *
     * @return 如果是，返回true。
     */
    public static boolean isFirstIn()
    {
        return sIsFirstIn;
    }

    /**
     * 设置状态，标识应用程序是否刚刚回到前台。
     *
     * @param isFirstIn 应用程序是否刚刚回到前台。
     */
    public static void setFirstIn(boolean isFirstIn)
    {
        sIsFirstIn = isFirstIn;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        // <add by caohaitao 20120817 begin, fix bug SEARHBOX-1076:
        // 【android框三期】长按手机上的菜单键时，虚拟键盘会弹出一下，然后才弹出“更多”菜单
        // htc g11 , htcA9188
        if (keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress())
        {
            return true;
        }
        // add by caohaitao 20120817 end>
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置下一次Activity的切换动画，enter与exit同时为0时用默认
     *
     * @param enterAnimWhenStarting  下一次Activity启动时，新Activity的进入动画
     * @param exitAnimWhenStarting   下一次Activity启动时，旧Activity的退出动画
     * @param enterAnimWhenFinishing 下一次Activity结束时，新Activity的进入动画
     * @param exitAnimWhenFinishing  下一次Activity结束时，旧Activity的退出动画
     * @see Activity#overridePendingTransition(int, int)
     */
    public static void setNextPendingTransition(int enterAnimWhenStarting,
                                                int exitAnimWhenStarting, int enterAnimWhenFinishing,
                                                int exitAnimWhenFinishing)
    {
        BaseActivity.nextEnterAnimWhenStarting = enterAnimWhenStarting;
        BaseActivity.nextExitAnimWhenStarting = exitAnimWhenStarting;
        BaseActivity.nextEnterAnimWhenFinishing = enterAnimWhenFinishing;
        BaseActivity.nextExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

    /**
     * 设置本次的切换动画，enter与exit同时为0时用默认
     *
     * @param enterAnimWhenStarting  启动时，新Activity的进入动画
     * @param exitAnimWhenStarting   启动时，旧Activity的退出动画
     * @param enterAnimWhenFinishing 结束时，新Activity的进入动画
     * @param exitAnimWhenFinishing  结束时，旧Activity的退出动画
     * @see Activity#overridePendingTransition(int, int)
     */
    protected void setPendingTransition(int enterAnimWhenStarting,
                                        int exitAnimWhenStarting, int enterAnimWhenFinishing,
                                        int exitAnimWhenFinishing)
    {
        mEnterAnimWhenStarting = enterAnimWhenStarting;
        mExitAnimWhenStarting = exitAnimWhenStarting;
        mEnterAnimWhenFinishing = enterAnimWhenFinishing;
        mExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        // 添加Activity启动的动画
        if (mEnterAnimWhenStarting != 0 || mExitAnimWhenStarting != 0)
        {
            overridePendingTransition(mEnterAnimWhenStarting,
                    mExitAnimWhenStarting);
            mEnterAnimWhenStarting = 0;
            mExitAnimWhenStarting = 0;
        }
    }

    @Override
    public void finish()
    {
        super.finish();
        // 添加Activity退出的动画
        if (mEnterAnimWhenFinishing != 0 || mExitAnimWhenFinishing != 0)
        {
            overridePendingTransition(mEnterAnimWhenFinishing,
                    mExitAnimWhenFinishing);
            mEnterAnimWhenFinishing = 0;
            mExitAnimWhenFinishing = 0;
        }
        
//        this.overridePendingTransition(0, 0);
    }

    /**
     * 退出app
     */
    protected void exit()
    {
        // if (DEBUG) {
        for (int i = 0; i < mActivityStack.size(); i++)
        {
            if (DEBUG)
            {
                Log.v(TAG, "finish activity:"
                        + mActivityStack.get(i).getClass().getName());
            }
            mActivityStack.get(i).finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
        System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出

        // }
    }

    /**
     * 不能直接继承baseactivity的activity，在onresume是调用
     */
    public static void addLiveActivityNum()
    {
        mLiveActivityNum++;
    }

    /**
     * 不能直接继承baseactivity的activity，onpause时调用
     */
    public static void decLiveActivityNum()
    {
        mLiveActivityNum--;
    }

    /**
     * 窗口栈。
     */
    private static LinkedList<Activity> mActivityStack = new LinkedList<Activity>();

    /**
     * 当前活动的Activity数，用来判断该应用是否在前台运行。
     */
    private static int mLiveActivityNum = 0;

    /**
     * 是否刚刚回到前台。
     */
    private static boolean sIsFirstIn;

    /**
     * 当没有Activity时，是否杀死进程。
     */
    private static boolean sEmptyKillApp = true;

    /**
     * 判断当前activity是否在运行
     */
    public boolean isStop()
    {
        return mIsStop;
    }

    /**
     * 点击空白隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev))
            {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText))
        {
            int[] l =
                    {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom)
            {
                // 点击EditText的事件，忽略它。
                return false;
            } else
            {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token)
    {
        if (token != null)
        {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // private void addUmengShareSdk()
    // {
    // String appID = "wx2df3aa443f064c4d";
    // String appSecret = "b4934655607d0f38df86b9d815d6e602";
    // // 添加微信平台
    // UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
    // wxHandler.addToSocialSDK();
    // // 支持微信朋友圈
    // UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
    // wxCircleHandler.setToCircle(true);
    // wxCircleHandler.addToSocialSDK();
    //
    // String appId = "1104594561";// 开发者在QQ互联申请的APP
    // String appkey = "YPfZ0rEJY5Bp1q2k";// 开发者在QQ互联申请的APP kEY
    // // 添加QQ
    // UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appkey);
    // qqSsoHandler.addToSocialSDK();
    //
    // // 添加QQ空间
    // QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
    // appkey);
    // qZoneSsoHandler.addToSocialSDK();
    //
    // /*
    // * // 添加短信 SmsHandler smsHandler = new SmsHandler();
    // * smsHandler.addToSocialSDK();
    // */
    //
    // // 设置新浪SSO handler
    // // App Key：4202689285
    // // App Secret：a7b33c8fedf8c9b35aea3a22568fbbbe
    // mController.getConfig().setSsoHandler(new SinaSsoHandler());
    // mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
    //
    // // mController.registerListener(mSnsPostListener);
    // /*
    // * mController.postShare(this, SHARE_MEDIA.QQ, mSnsPostListener);
    // * mController.postShare(this, SHARE_MEDIA.QZONE, mSnsPostListener);
    // */
    // }
}

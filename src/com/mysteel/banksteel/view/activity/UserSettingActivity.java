package com.mysteel.banksteel.view.activity;

import org.simple.eventbus.EventBus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.easemob.EMCallBack;
import com.mysteel.banksteel.ao.impl.SystemManagerImpl;
import com.mysteel.banksteel.ao.impl.UserCenterImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CheckUpdateData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.EditInfoData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.util.UpdateManager;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;
import com.mysteel.banksteel.view.interfaceview.IUserCenterView;
import com.mysteel.banksteel.view.ui.SwitchButton;
import com.mysteel.banksteel.view.ui.SwitchButton.OnCheckChangeListener;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * @author 68 设置界面
 */
public class UserSettingActivity extends SwipeBackActivity implements
        OnClickListener, OnCheckChangeListener, IUserCenterView,
        ISystemManagerView
{

    /**
     * 地址设置
     */
    private LinearLayout llAddress;
    /**
     * 意见反馈
     */
    private LinearLayout llOption;
    /**
     * 关于我们
     */
    private LinearLayout llAboutUs;
    /**
     * 功能介绍
     */
    private LinearLayout llFunction;
    /**
     * 重置密码
     */
    private LinearLayout passwordLl;
    /**
     * 检查更新
     */
    private LinearLayout llCheckUpdate;
    /**
     * 退出登录
     */
    private Button btnLoginOut;
    /**
     * 进度条
     */
    private ProgressBar progressBar;
    /**
     * 推送组件
     */
    private SwitchButton switchButton;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 推送?
     */
    private String isPush;
    /**
     * SystemManagerImpl
     */
    private SystemManagerImpl systemManagerImpl;
    /**
     * UserCenterImpl
     */
    private UserCenterImpl userCenterImpl;
    /**
     * 未登录:隐藏,登陆:显示/推送栏
     */
    private LinearLayout llPush;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersetting);
        initViews();
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("设置");

        progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        llAddress = (LinearLayout) findViewById(R.id.ll_setting_address);
        passwordLl = (LinearLayout) findViewById(R.id.ll_setting_password);
//        llFunction = (LinearLayout) findViewById(R.id.ll_setting_function);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_setting_aboutus);
        llOption = (LinearLayout) findViewById(R.id.ll_setting_opinion);
        llCheckUpdate = (LinearLayout) findViewById(R.id.ll_setting_checkupdate);
        btnLoginOut = (Button) findViewById(R.id.btn_setting_loginout);
        switchButton = (SwitchButton) findViewById(R.id.switchbutton_push);
        /** 首次应该设为true,允许推送 */
        switchButton.setChecked(SharePreferenceUtil.getBooleanDefaultTrue(this,
                Constants.IS_PUSH));

        backLayout.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        passwordLl.setOnClickListener(this);
//        llFunction.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llOption.setOnClickListener(this);
        btnLoginOut.setOnClickListener(this);
        llCheckUpdate.setOnClickListener(this);
        switchButton.setOnCheckChangeListener(this);

        systemManagerImpl = new SystemManagerImpl(this);
        userCenterImpl = new UserCenterImpl(this);

        llPush = (LinearLayout) findViewById(R.id.ll_push);
        if (Tools.isLogin(this))
        {
//            llPush.setVisibility(View.VISIBLE);
            btnLoginOut.setVisibility(View.VISIBLE);
        } else
        {
//            llPush.setVisibility(View.GONE);
            btnLoginOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        switch (v.getId())
        {
            case R.id.menu_layout:
                // 返回菜单
                finish();
                break;

            case R.id.ll_setting_address:
                // 地址设置
                if (Tools.isLogin(this))
                {
                    intent = new Intent(this, AddressListActivity.class);
                    intent.putExtra(Constants.NEXT_SCREEN_TAG,
                            "UserSettingActivity");
                }
                startActivity(intent);
                break;

            case R.id.ll_setting_password:
                // 重置密码
                if (Tools.isLogin(this))
                {
                    intent = new Intent(this, ResetPasswordActivity.class);
                }
                startActivity(intent);
                break;

//            case R.id.ll_setting_function:
//                // 功能介绍
//                startActivity(new Intent(this, FunctionAndIntroduceActivity.class));
//                break;

            case R.id.ll_setting_aboutus:
                // 关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;

            case R.id.ll_setting_opinion:
                // 意见反馈改为了 欢迎吐槽
                startActivity(new Intent(this, SuggestActivity.class));
                break;

            case R.id.ll_setting_checkupdate:
                // 检查更新
                String updateUrl = RequestUrl.getInstance(this).getUrl_checkUpdate(
                        this);
                systemManagerImpl.checkUpDate(updateUrl, Constants.INTERFACE_checkUpdate);
                break;

            case R.id.btn_setting_loginout:
                // 退出
                if (Tools.isLogin(this))
                {
                    String url = RequestUrl.getInstance(this).getUrl_loginOut(this);
                    userCenterImpl.getRegisterData(url,
                            Constants.INTERFACE_Loginout);

                } else
                {
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("true".equals(data.getStatus()))
        {
            SharePreferenceUtil.setValue(this, Constants.USER_IS_LOGIN, false);
            Tools.clearCache(this);
            EventBus.getDefault().post("", "LOGINOUT");
            EventBus.getDefault().post("loginOut", "main_data");
            //退出环信
            BankSteelApplication.getInstance().logout(new EMCallBack()
            {
                @Override
                public void onSuccess()
                {
                    SharePreferenceUtil.setValue(UserSettingActivity.this,Constants.PREFERENCES_HUANXIN_FALST, false);
                }

                @Override
                public void onError(int i, String s)
                {

                }

                @Override
                public void onProgress(int i, String s)
                {

                }
            });
            EventBus.getDefault().post("", "mainFragment_view");
            startActivity(new Intent(this, LoginActivity.class));
            btnLoginOut.setVisibility(View.INVISIBLE);
            finish();
        }

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
    }

    @Override
    public void OnCheck(SwitchButton switchButton, boolean isChecked)
    {
//        if (isChecked)
//        {
//            isPush = "1";// 推送
//        } else
//        {
//            isPush = "0";// 不推送
//        }
//
//        if (userId == null)
//        {
//            userId = SharePreferenceUtil.getString(this,
//                    Constants.PREFERENCES_USERID);
//        }
//        String url = RequestUrl.getInstance(this).getUrl_push(userId, isPush);
//        systemManagerImpl.checkUpDate(url, Constants.INTERFACE_push);
    }

    @Override
    public void checkUpDate(BaseData data)
    {
        CheckUpdateData cUData = (CheckUpdateData) data;
        if (cUData.getData() != null)
        {
            String version = cUData.getData().getVersion();// 版本
            String downUrl = cUData.getData().getDownload();// 下载地址

            if (version.equals(Tools.getVersion(UserSettingActivity.this)))
            {
                Tools.showToast(this, "已是最新版本V" + version);
            } else
            {
                if (!TextUtils.isEmpty(downUrl))
                {
                    UpdateManager mUpdateManager = new UpdateManager(UserSettingActivity.this);
                    mUpdateManager.checkUpdateInfo(downUrl);
                }

            }
        }

    }

    @Override
    public void getDeleteMessage(DeleteMessageData data)
    {

    }

    @Override
    public void getHistoryMessage(MessageCenterData data)
    {
    }

    @Override
    public void getSign(SignData data)
    {
    }

    @Override
    public void getSuggest(BaseData data)
    {
    }

    @Override
    public void getIsPush(BaseData data)
    {
//        if ("0".equals(isPush))
//        {
//            SharePreferenceUtil.setValue(this, Constants.IS_PUSH, false);
//            JPushInterface.stopPush(getApplicationContext());
//            Tools.showToast(this, "您已取消推送！");
//        } else
//        {
//            SharePreferenceUtil.setValue(this, Constants.IS_PUSH, true);
//            JPushInterface.resumePush(getApplicationContext());
//            Tools.showToast(this, "您已打开推送");
//        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        systemManagerImpl.finishRequest();
        userCenterImpl.finishRequest();
    }

//    @Subscriber(tag = "showPush")
//    public void showPush(String str)
//    {
//        llPush.setVisibility(View.VISIBLE);
//    }
}

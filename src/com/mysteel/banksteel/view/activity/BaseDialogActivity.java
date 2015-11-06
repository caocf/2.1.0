package com.mysteel.banksteel.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.simple.eventbus.EventBus;

/**
 * @author 作者 zoujian
 * @date 创建时间：2015-4-28 下午7:52:09
 */
public class BaseDialogActivity extends FragmentActivity
{

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            EventBus.getDefault().register(this);

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
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        BaseDialogActivity.nextEnterAnimWhenStarting = enterAnimWhenStarting;
        BaseDialogActivity.nextExitAnimWhenStarting = exitAnimWhenStarting;
        BaseDialogActivity.nextEnterAnimWhenFinishing = enterAnimWhenFinishing;
        BaseDialogActivity.nextExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

}

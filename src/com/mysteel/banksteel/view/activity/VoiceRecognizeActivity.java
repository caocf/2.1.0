package com.mysteel.banksteel.view.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.TwoButtonDialog;
import com.mysteel.banksteeltwo.R;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 68 语音识别界面
 */
public class VoiceRecognizeActivity extends SwipeBackActivity implements
        OnClickListener, IBuyView
{
    /**
     * LOG SWITCHER.
     */
    // private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

    /**
     * TAG.
     */
    // private static final String TAG = "VoiceRecognizeActivity";
    /**
     * 声音文件的url
     */
    private String urlStr;
    /**
     * 声音文件完整路径
     */
    private String pathName;
    /**
     * SD卡路径
     */
    private String SDCard;
    /**
     * 播放器
     */
    private MediaPlayer player;
    /**
     * 下载状态,0:代表未下载,1:代表正在下载,2代表下载完成
     */
    private int downLoadStutas = 0;

    /**
     * 找货文本
     */
    private TextView etContent;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 内容
     */
    private String content;
    /**
     * 日期
     */
    private String date;
    /** 再听一次 */
    // private LinearLayout llRepeatLis;
    /**
     * 电话
     */
    private TextView etPhone;
    /**
     * 声音文件名
     */
    private String voiceName = "findorder";
    /**
     * 求购的类型
     */
    private String buyType;
    /**
     * 订单ID
     */
    private String fastBuyId;
    /**
     * BuyImpl
     */
    private BuyImpl buyImpl;
    /**
     * 再听一次全局触点
     */
    private RelativeLayout re_listen_layout;

    /**
     * mHandler
     */
    private Handler mHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                Tools.showToast(VoiceRecognizeActivity.this, "下载完成");
                downLoadStutas = 2;
                playMusic(pathName);
            }
            return false;
        }
    });
    /**
     * 下载声音文件的线程
     */
    Thread mDownloadThread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            downloadVoice();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicerecognize);

        fastBuyId = getIntent().getStringExtra("ID");
        date = getIntent().getStringExtra("DATE");
        urlStr = getIntent().getStringExtra("URL");
        content = getIntent().getStringExtra("CONTENT");
        phone = getIntent().getStringExtra("PHONE");
        buyType = getIntent().getStringExtra("BUYTYPE");
        initViews();
    }

    @Override
    protected void initViews()
    {
        super.initViews();
        buyImpl = new BuyImpl(this);
        /** 标题栏初始化 */
//		tvTitle.setText((TextUtils.isEmpty(date)) ? "" : date);
        tvTitle.setText("求购单详情");
        tvRightText.setVisibility(View.GONE);
        backLayout.setOnClickListener(this);
        rightLayout.setOnClickListener(this);

        /** 界面初始化 */
        etContent = (TextView) findViewById(R.id.et_orderdemand);
        etPhone = (TextView) findViewById(R.id.tv_phone);
        // llRepeatLis = (LinearLayout) findViewById(R.id.ll_repeatListen);
        if (!TextUtils.isEmpty(content))
        {
            etContent.setText(content);
        }else
        {
            etContent.setText("您未发布文字求购！");
        }

        etPhone.setText(phone);
        // llRepeatLis.setOnClickListener(this);
        re_listen_layout = (RelativeLayout) findViewById(R.id.re_listen_layout);
        re_listen_layout.setOnClickListener(this);

        /** 初始化文件名 */
        SDCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = voiceName + ".mp3";
        pathName = SDCard + Constants.VOICE_FOLDER + "/" + fileName;// 文件存储路径

        if (!TextUtils.isEmpty(buyType))
        {
            if ("0".equals(buyType))
            {//快捷求购
                re_listen_layout.setVisibility(View.GONE);
            } else if ("1".equals(buyType))
            {//语音求购
                /** 初始化声音播放器 */
                initMediaplayer();
            }
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:
                /** 返回 */
                finish();
                break;

            case R.id.re_listen_layout:
                /** 重听 */
                switch (downLoadStutas)
                {
                    /** 未下载 */
                    case 0:
                        downLoadStutas = 1;
                        mDownloadThread.start();
                        break;
                    /** 正在下载 */
                    case 1:
                        break;
                    /** 已下载成功 */
                    case 2:
                        if (player.isPlaying())
                        {
                            stopMusic();
                        } else
                        {
                            playMusic(pathName);
                        }
                        break;
                }

                break;

            case R.id.share_layout:
                /** 点取消时,弹出对话框 */
//                createDialog();
                break;

            default:
                break;
        }
    }

    private void createDialog()
    {
        TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(this);
        alert.setMessage("你确定要取消本次求购？");
        alert.setNegativeButton("去意已决", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int arg1)
            {
                dialog.dismiss();
                // 发送4.14请求
                String url = RequestUrl
                        .getInstance(VoiceRecognizeActivity.this)
                        .getUrl_getCancelFastBuy(VoiceRecognizeActivity.this,
                                fastBuyId);
                buyImpl.getCancelStanBuy(url, Constants.INTERFACE_cancelFastBuy);
            }
        });

        alert.setPositiveButton("继续求购", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int arg1)
            {
                dialog.dismiss();
            }
        });

        alert.create().show();
    }

    /**
     * 读取文件，并将文件保存到手机SDCard
     */
    private void downloadVoice()
    {
        OutputStream output = null;
        InputStream input = null;
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn.setDoOutput(true);
            // conn.setDoInput(true);
            // conn.setRequestMethod("GET");
            conn.connect();
            // 取得inputStream，并将流中的信息写入SDCard
            File file = new File(pathName);
            input = conn.getInputStream();
            if (!file.exists())
            {
                String dir = SDCard + Constants.VOICE_FOLDER;
                new File(dir).mkdir();// 新建文件夹
                file.createNewFile();// 新建文件
                // file.mkdirs();
            }
            output = new FileOutputStream(file);
            byte[] buffer = new byte[8 * 1024];
            int len = -1;
            while ((len = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, len);
            }
            output.flush();
            mHandler.sendEmptyMessage(0);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (output != null)
                {
                    output.close();
                    output = null;
                }
                if (input != null)
                {
                    input.close();
                    input = null;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化播放器
     */
    private void initMediaplayer()
    {
        if (player != null)
        {
            player.reset();
            player.release();
            player = null;
        }
        player = new MediaPlayer();
    }

    /**
     * 播放音乐
     */
    private void playMusic(String soundPath)
    {
        initMediaplayer();
        try
        {
            /* 重置多媒体 */
            player.reset();
			/* 读取mp3文件 */
            player.setDataSource(soundPath);
			/* 准备播放 */
            player.prepare();
			/* 开始播放 */
            player.start();
			/* 是否单曲循环 */
            player.setLooping(false);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    private void stopMusic()
    {
        if (player != null && player.isPlaying())
        {
            player.stop();
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeCallbacks(mDownloadThread);
    }

    @Override
    public void updateView(BaseData data)
    {
        Tools.showToast(this, "取消快捷找货成功");
        finish();
    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }
}

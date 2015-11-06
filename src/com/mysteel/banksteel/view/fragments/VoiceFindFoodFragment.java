package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.SoundMeter;
import com.mysteel.banksteel.util.StringUtils;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.LoginActivity;
import com.mysteel.banksteel.view.activity.OrderCentreActivity;
import com.mysteel.banksteel.view.activity.SuggestActivity;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteeltwo.R;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 语音找货页面编写
 * Created by zoujian on 15/7/23.
 */
public class VoiceFindFoodFragment extends BaseFragment implements View.OnClickListener, MediaPlayer.OnCompletionListener, IBuyView
{

    /**
     * 找货需求文本
     */
    private EditText etDemand;
    /**
     * 使用语音栏
     */
    private RelativeLayout rlUseVoice;
    /**
     * 使用语音找货图片
     */
    private ImageView ivUseVoice;
    /**
     * 使用语音找货文本
     */
    private TextView tvUseVoice;
    /**
     * 联系电话
     */
    private EditText etPhone;
    /**
     * 根据找货需求文本,立即找货
     */
    private Button btnFindGood;
    /**
     * 使用语音时弹出的popus
     */
    private LinearLayout speakingPopus;
    /**
     * 重听栏
     */
    private RelativeLayout rlRelisten;
    /**
     * 重听图片(动画效果)
     */
//    private ImageView ivRelisten;
    /**
     * 删除录音数据
     */
    private ImageView ivDelRelisten;
    /**
     * 显示时间的Chronometer
     */
    private Chronometer showTime;
    /**
     * handler
     */
    private Handler mHandler = new Handler();
    /**
     * 开始录音和结束录音时间
     */
    private long timeStart, timeEnd;
    /**
     * 测声器,录音
     */
    private SoundMeter mSensor;
    /**
     * 录音文件名字
     */
    private String voiceName;
    /**
     * 录音文件路径
     */
    private String path;
    /**
     * 正在录音显示的图片(动化效果)
     */
    private ImageView volume;
    /**
     * 设置响应时间0.3秒
     */
    private static final long POLL_INTERVAL = 300;
    /**
     * 设置录音的最长时间50秒
     */
    private static long Time = 50;
    /**
     * 剩余录时间
     */
    private long timeLeft = 0;
    /**
     * 播放录音图片(ivRelistener)的切换
     */
    private int position = 0;
    /**
     * Timer
     */
    private final Timer timer = new Timer();
    /**
     * TimerTask
     */
    private TimerTask task;
    /**
     * 进度条
     */
    private ProgressBar progressBar;
    /**
     * 播放器
     */
    private MediaPlayer player;
    /**
     * BuyImpl
     */
    private BuyImpl buyImpl;

    /**
     * 用于判断退出语音找货页面后是否删除文件,如果发送到后台就不删除(false),
     */
    private boolean isDel = true;
    /**
     * 标识符,等于1时表示可以开始录音,等于2是表示录音成功
     */
    private int flag = 1;
    /**
     * 录音时间,单位秒=(endTime-startTime)/1000
     */
    private float recoredTime;
    /**
     * 播放音乐时,ivRelisten每隔一秒切换一张背景图片
     */
    Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                if (player.isPlaying())
                {
                    changeImage();
                } else
                {
                    task.cancel();
//                    ivRelisten.setImageResource(R.drawable.voice_01);
                    rlRelisten.setClickable(true);
                }
                position++;
            }
            return false;
        }
    });

    /**
     * 语音图标
     */
    private ImageView img_voice_yuyin;
    /**
     * 语音的时长
     */
    private TextView tv_voice_leng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_speech_findgoods, null);
        initView(view);
        return view;
    }

    /**
     * 初始化组建
     *
     * @param view
     */
    private void initView(View view)
    {
        {

            progressBar = (ProgressBar) view.findViewById(R.id.pb_progressbar);
            etDemand = (EditText) view.findViewById(R.id.et_speech_demand);
            rlUseVoice = (RelativeLayout) view.findViewById(R.id.rl_speech_usevoice);
//		rlUseVoice = (RelativeLayout) findViewById(R.id.rl_speech_usevoice);
//		ivUseVoice = (ImageView) findViewById(R.id.iv_speech_usevoice);
//		tvUseVoice = (TextView) findViewById(R.id.tv_speech_usevoice);
            etPhone = (EditText) view.findViewById(R.id.et_speech_phonenumber);
            btnFindGood = (Button) view.findViewById(R.id.btn_speech_findgoods);
            speakingPopus = (LinearLayout) view.findViewById(R.id.ll_speaking_popup);
            rlRelisten = (RelativeLayout) view.findViewById(R.id.rl_speech_relisten);
//            ivRelisten = (ImageView) view.findViewById(R.id.iv_speech_relisten);
            ivDelRelisten = (ImageView) view.findViewById(R.id.iv_speech_del);
            showTime = (Chronometer) view.findViewById(R.id.timedown);
            volume = (ImageView) view.findViewById(R.id.volume);
            img_voice_yuyin = (ImageView) view.findViewById(R.id.img_voice_yuyin);
            tv_voice_leng = (TextView) view.findViewById(R.id.tv_voice_leng);

            if (Tools.isLogin(getActivity()))
            {
                etPhone.setText(SharePreferenceUtil.getString(getActivity(),
                        Constants.PREFERENCES_CELLPHONE));
            }
            ivDelRelisten.setOnClickListener(this);
            btnFindGood.setOnClickListener(this);

            buyImpl = new BuyImpl(getActivity(), this);
            mSensor = new SoundMeter();
            initMediaplayer();

            /** 设置找货需求文本200字符以内 */
            etDemand.addTextChangedListener(new TextWatcher()
            {
                private CharSequence temp;
                private int selectionStart;
                private int selectionEnd;
                /** 最多输入200个字符 */
                private static final int NUM = 200;

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count)
                {
                    temp = s;
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    selectionStart = etDemand.getSelectionStart();
                    selectionEnd = etDemand.getSelectionEnd();
                    if (temp.length() > NUM)
                    {
                        s.delete(selectionStart - 1, selectionEnd);
                        int tempSelection = selectionEnd;
                        etDemand.setText(s);
                        etDemand.setSelection(tempSelection);
                    }

                }
            });

            img_voice_yuyin.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {

                        if (TextUtils.isEmpty(SharePreferenceUtil.getString(getActivity(),
                                Constants.PREFERENCES_USERID)))
                        {
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            startActivity(i);
                            return false;
                        }
                        // 按下的时候:改变颜色和显示内容,弹出speakingPopus,计时录音
                        /** 重置开始录音的时间 */
                        img_voice_yuyin.setVisibility(View.INVISIBLE);
                        timeStart = 0;
                        changeUseVioce();
                        // rlRelisten.setVisibility(View.GONE);
                        if (flag == 1)
                        {
                            mHandler.post(startRunnable);// 开始线程
                        }

                    } else if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        // 手指抬起后:恢复使用语音栏的颜色和显示内容,隐藏speakingPopus,弹出重听栏,结束计时,保存录音文件
                        img_voice_yuyin.setVisibility(View.VISIBLE);
                        recoverUseVoice();
                        /** 移除startRunnable线程 */
                        mHandler.removeCallbacks(startRunnable);
                        /** 获得结束时间 */
                        timeEnd = System.currentTimeMillis();
                        showTime.stop();
                        /** 隐藏speakingPopus */
                        speakingPopus.setVisibility(View.GONE);
                        stop();
                        flag = 1;
                        if (timeStart != 0)// timestart=0,说明未开始录音
                        {
                            recoredTime = 1F * (timeEnd - timeStart) / 1000;
                            if (recoredTime > 50)
                            {
                                recoredTime = 50;
                            }
                            if (recoredTime < 3)
                            {
                                delFile(path);
                                rlRelisten.setVisibility(View.INVISIBLE);
                                Tools.showToast(getActivity(),
                                        "录音时间不足3秒");
                            } else if (recoredTime >= 3)
                            {
                                soundUse();
                            }
                        }
                    } else
                    {
                        flag = 1;
                    }
                    return true;// 触摸事件不继续分发,可监听手指抬起事件
                }
            });

        }
    }


//    private void crateFile()
//    {
//        if (Environment.getExternalStorageDirectory().exists())
//        {
//            path = Environment.getExternalStorageDirectory().getAbsolutePath()
//                    + Constants.SOUND_FOLDER + voiceName;
//        } else
//        {
//            path = getActivity().getApplicationContext().getCacheDir().getPath()
//                    + Constants.SOUND_FOLDER + voiceName;
//        }
//
//        File file = new File(path);
//
//        if (!file.exists()) {
//            try {
//                //按照指定的路径创建文件夹
//                file.mkdirs();
//            } catch (Exception e) {
//            }
//        }
//    }
    /**
     * 初始化计时器 计时器是通过widget.Chronometer来实现的
     *
     * @param total 最大时间
     */
    private void initTimer(long total)
    {
        this.timeLeft = total;
        showTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                if (timeLeft < 0)
                {
                    // 剩余时间<0,录音结束
                    Tools.showToast(getActivity(), "录音时间到");
                    showTime.stop();
                    // 录音停止
                    stop();
                    // 隐藏 speakingPopus
                    speakingPopus.setVisibility(View.GONE);
                    // showTime.setVisibility(View.GONE);

                    return;
                }
                timeLeft--;// 剩余时间递减
                showTime.setText(Time - timeLeft - 1 + "秒");// 显示时间
            }
        });
    }

    /**
     * 恢复使用语音栏的颜色和内容
     */
    protected void recoverUseVoice()
    {
//		rlUseVoice.setBackgroundResource(R.drawable.yellow_bg);
//		ivUseVoice.setImageResource(R.drawable.voice_stop);
//		tvUseVoice.setTextColor(getResources().getColor(R.color.orange));
    }

    /**
     * 改变使用语音栏的颜色和内容
     */
    protected void changeUseVioce()
    {
//		rlUseVoice.setBackgroundColor(getResources().getColor(
//				R.color.bg_green_one));
//		ivUseVoice.setImageResource(R.drawable.voice_start);
//		tvUseVoice
//				.setTextColor(getResources().getColor(R.color.font_white_one));
    }

    /**
     * 开始录音线程
     */
    private Runnable startRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            speakingPopus.setVisibility(View.VISIBLE);
            createFileName();
            initTimer(Time);
            timeStart = System.currentTimeMillis();
            showTime.start();
            mSensor.start(getActivity().getApplicationContext(), voiceName);// 开始测声录音
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
            flag = 2;
        }
    };

    private Runnable mPollTask = new Runnable()
    {

        public void run()
        {
            double amp = mSensor.getAmplitude();
            /** 根据振幅大小切换图片 */
            updateDisplay(amp);
            /** 每隔0.3秒获取一次声音振幅 */
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };

    /**
     * 销毁线程,停止录音
     */
    private void stop()
    {
        // mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        volume.setImageResource(R.drawable.amp1);
    }

    /**
     * 删除文件
     */
    private void delFile(String soundPath)
    {
        if (soundPath != null)
        {
            File file = new File(soundPath);
            if (file.exists())
            {
                file.delete();
            }
        }
    }

    private void soundUse()
    {
        // 判断sd卡上是否有声音文件，有就显示名称并播放
        File file = new File(path);

//        if (!file.exists()) {
//            try {
//                //按照指定的路径创建文件夹
//                new File(path).mkdir();// 新建文件夹
//                file.createNewFile();// 新建文件
//            } catch (Exception e) {
//            }
//        }
        if (file.exists())
        {
            rlRelisten.setVisibility(View.VISIBLE);
            DecimalFormat decimalFormat = new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String time = decimalFormat.format(recoredTime);//format 返回的是字符串
            tv_voice_leng.setText(time + "‘");
            // 点击声音文件播放声音
            rlRelisten.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    rlRelisten.setClickable(false);
                    try
                    {
                        position = 0;
                        /** 播放声音 */
                        playMusic(path);
                        if (task != null)
                        {
                            task.cancel();
                        }

                        task = new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        };
                        /** 每隔1秒执行一次该任务,给handler发消息 */
                        timer.schedule(task, 0, 1000);

                    } catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                        rlRelisten.setClickable(true);
                    } catch (SecurityException e)
                    {
                        e.printStackTrace();
                        rlRelisten.setClickable(true);
                    } catch (IllegalStateException e)
                    {
                        e.printStackTrace();
                        rlRelisten.setClickable(true);
                    }
                }
            });
        } else
        {
            rlRelisten.setVisibility(View.INVISIBLE);
        }
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
     * 销毁音乐
     */
    private void destoryMusic()
    {
        if (player != null)
        {
            if (player.isPlaying())
            {
                player.stop();
            }
            player.release();
            player = null;
        }
    }

    /**
     * 暂停播放
     */
    // private void pauseMusic()
    // {
    // if (player.isPlaying())
    // {// 正在播放
    // player.pause();// 暂停
    // } else
    // {// 没有播放
    // player.start();
    // }
    // }

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

    /**
     * 初始化录音文件名,以及文件保存路径 文件名以用户id开头
     */
    private void createFileName()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.CHINESE);
        String date = simpleDateFormat.format(new Date());
        voiceName = SharePreferenceUtil.getString(getActivity(),
                Constants.PREFERENCES_USERID) + "_" + date + ".amr";
        if (Environment.getExternalStorageDirectory().exists())
        {
            path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + Constants.SOUND_FOLDER + voiceName;
        } else
        {
            path = getActivity().getApplicationContext().getCacheDir().getPath()
                    + Constants.SOUND_FOLDER + voiceName;
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
     * 更新录音时的图片,不断切换
     *
     * @param signalEMA
     */
    private void updateDisplay(double signalEMA)
    {

        switch ((int) signalEMA)
        {
            case 0:
            case 1:
                volume.setImageResource(R.drawable.amp1);
                break;
            case 2:
                volume.setImageResource(R.drawable.amp2);
            case 3:
                volume.setImageResource(R.drawable.amp3);
                break;
            case 4:
                volume.setImageResource(R.drawable.amp4);
            case 5:
                volume.setImageResource(R.drawable.amp5);
                break;
            case 6:
            case 7:
                volume.setImageResource(R.drawable.amp6);
                break;
            case 8:
            case 9:
                volume.setImageResource(R.drawable.amp7);
                break;
            case 10:
            case 11:
                volume.setImageResource(R.drawable.amp8);
                break;
            default:
                volume.setImageResource(R.drawable.amp9);
                break;
        }
    }

    /**
     * 播放录音时候的动画
     */
    private void changeImage()
    {
        switch (position % 3)
        {
//            case 0:
//                ivRelisten.setImageResource(R.drawable.voice_03);
//                break;
//            case 1:
//                ivRelisten.setImageResource(R.drawable.voice_02);
//                break;
//            case 2:
//                ivRelisten.setImageResource(R.drawable.voice_01);
//                break;
//
//            default:
//                ivRelisten.setImageResource(R.drawable.voice_01);
//                break;
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        stopMusic();
        if (isDel)
        {
            delFile(path);
        }
        destoryMusic();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        stopMusic();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tv_title_right_text:
                // 意见反馈界面
                startActivity(new Intent(getActivity(), SuggestActivity.class));
                break;

            case R.id.iv_speech_del:
                // 删除录制的语音
                if (player != null && player.isPlaying())
                {
                    stopMusic();

                }
                rlRelisten.setVisibility(View.INVISIBLE);
                delFile(path);
                break;

            case R.id.btn_speech_findgoods:
                // 发布文本找货需求 ,快捷求购
                if (Tools.isLogin(getActivity()))
                {
//                    btnFindGood.setEnabled(false);
                    publishFindGoods();
                } else
                {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;

            default:
                break;
        }
    }

    /**
     * 找货:先判断有无语音,有语音则语音找货, 无语音,再判断有无文字,有文字,则为快捷找货 否则提示用户
     */
    private void publishFindGoods()
    {
        String phone = etPhone.getText().toString();
        String content = etDemand.getText().toString().trim();
        content = content.replace(" ", "");
        String memberId = SharePreferenceUtil.getString(getActivity(),
                Constants.USER_MEMBERID);
        String userId = SharePreferenceUtil.getString(getActivity(),
                Constants.PREFERENCES_USERID);

        /** 检查电话号码 */
        if (!Tools.checkIsPhoneNumber(phone))
        {
            Tools.showToast(getActivity(), "请输入正确的手机号码");
            return;
        }

        /** 检查网络 */
        if (!Tools.isNetworkConnected(getActivity()))
        {
            Tools.showToast(getActivity(), "请检查网络是否连接");
            return;
        }

        if (rlRelisten.getVisibility() == View.VISIBLE)
        {
            /** 语音找货 */
            String url = RequestUrl.getInstance(getActivity()).getUrl_getVoicePublish(
                    phone, recoredTime + "", memberId, userId, content);
            // upLoadVoice(url);
            testUpload(url);

        } else
        {
            /** 检查文本 */
            if (content.length() < 5)
            {
//                btnFindGood.setEnabled(true);
                if (StringUtils.isEmpty(content))
                {
                    Tools.showToast(getActivity(), "请录音找货,或者填写找货需求");
                    return;
                }
                Tools.showToast(getActivity(), "你提供的资料太少");
                return;
            }

            String url = RequestUrl.getInstance(getActivity()).getUrl_getPublishFastBuy(
                    phone, content, memberId, userId);

            buyImpl.getCancelStanBuy(url, Constants.INTERFACE_publishFastBuy);
        }

    }

    /**
     * 上传语音
     */

	/*
     * private void upLoadVoice(String url) { File upLoadfile = new File(path);
	 *//** key值 */
    /*
	 * String phone = SharePreferenceUtil.getString(this,
	 * Constants.PREFERENCES_CELLPHONE);
	 *//** 创建hashmap存放图片文件 */
	/*
	 * Map<String, File> voiceMap = new HashMap<String, File>();
	 * voiceMap.put(phone, upLoadfile);
	 *
	 * // Map<String, String> data = new HashMap<String, String>(); //
	 * data.put("content", etDemand.getText().toString().trim());
	 */

    /**
     * 服务器url
     */
	/*
	 *
	 * new GetDataDAO<BaseData>(this, BaseData.class, new AOCallBack<BaseData>()
	 * {
	 *
	 * @Override public void dealWithTrue(BaseData obj) { if
	 * ("true".equals(obj.getStatus())) {
	 * Tools.showToast(SpeechFindGoodsActivity.this, "语音找货发布成功,我们的业务员稍后会和您联系");
	 * isDel = false; mHandler.postDelayed(finishRunnable, 1000); } else {
	 * Tools.showToast(SpeechFindGoodsActivity.this, "语音找货发布失败"); } }
	 *
	 * @Override public void dealWithFalse(String str) { }
	 *
	 * @Override public void dealWithException(String error) { }
	 *
	 * }).uploadFile(url, null, voiceMap, Constants.INTERFACE_voicePublish);//
	 * 上传文件 }
	 */
    public void testUpload(String url)
    {
        RequestParams params = new RequestParams("UTF-8"); // 默认编码UTF-8

        // 添加文件
        params.addBodyParameter("file", new File(path));
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params,
                new RequestCallBack<String>()
                {

                    @Override
                    public void onStart()
                    {
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading)
                    {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo)
                    {
                        //快捷找货发布成功,我们的业务员稍后会和您联系
                        Tools.showToast(getActivity(),
                                "语音快捷找货发布成功");
                        Intent i = new Intent(getActivity(), OrderCentreActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg)
                    {
                        Tools.showToast(getActivity(), "上传失败！");
//                        btnFindGood.setEnabled(true);
                    }
                });
    }

    @Override
    public void updateView(BaseData data)
    {
        Tools.showToast(getActivity(), "快捷找货发布成功,我们的业务员稍后会和您联系");
        Intent i = new Intent(getActivity(), OrderCentreActivity.class);
        startActivity(i);
        getActivity().finish();
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
    public void onDestroy()
    {
        super.onDestroy();
        buyImpl.finishRequest();
        mHandler.removeCallbacks(startRunnable);
        mHandler.removeCallbacks(mPollTask);
        mHandler.removeCallbacks(task);
    }
}

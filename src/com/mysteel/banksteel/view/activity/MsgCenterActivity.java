package com.mysteel.banksteel.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.view.adapters.SupportFragmentAdapter;
import com.mysteel.banksteel.view.fragments.MsgDingdanFragment;
import com.mysteel.banksteel.view.fragments.MsgXitongFragment;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;



/**
 * 消息中心
 * @author:wushaoge
 * @date：2015年7月31日11:16:22
 */
public class MsgCenterActivity extends SwipeBackActivity implements View.OnClickListener
{

    private MsgXitongFragment msgXitongFragment;
    private MsgDingdanFragment msgDingdanFragment;
    private TextView tv_xitong, tv_dingdan;//系统消息 或 订单消息
    private ViewPager viewPager;
    private boolean tag = true;//true：系统消息  false：订单消息
    private LinearLayout ll_xitong_fd, ll_dingdan_fd;//系统消息货 和 订单消息的布局
    
    private boolean toggle = false; //右上角按钮切换功能 true表示编辑   false表示取消

    private SupportFragmentAdapter supportFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_center);
        initViews();
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("消息中心");
        backLayout.setOnClickListener(this);
        tv_xitong = (TextView) findViewById(R.id.tv_xitong);
        tv_dingdan = (TextView) findViewById(R.id.tv_dingdan);
        
        tvRightText.setOnClickListener(this);

        ll_xitong_fd = (LinearLayout) findViewById(R.id.ll_xitong_fd);
        ll_dingdan_fd = (LinearLayout) findViewById(R.id.ll_dingdan_fd);
        ll_xitong_fd.setOnClickListener(this);
        ll_dingdan_fd.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        msgXitongFragment = new MsgXitongFragment();
        msgDingdanFragment = new MsgDingdanFragment();
        fragments.add(msgXitongFragment);
        fragments.add(msgDingdanFragment);
        supportFragmentAdapter = new SupportFragmentAdapter(
                getSupportFragmentManager(), fragments);
        viewPager.setAdapter(supportFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                refreshPos(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

            }
        });
        refreshPos(0);

    }
    
    //是否显现编辑按钮
    @Subscriber(tag = "msgxitongfragment_righttop")
	private void setToggleOpen(boolean isRightTop)
	{
    	//toggle = false;
    	if(tag){
    		if(isRightTop){
    			//tvRightText.setText("编辑");
    			tvRightText.setVisibility(View.VISIBLE);
    		}else{
    			tvRightText.setVisibility(View.INVISIBLE);
    		}
    	}
	}
    
    //是否显现编辑按钮
    @Subscriber(tag = "msgdingdanfragment_righttop")
	private void setToggleOpen2(boolean isRightTop)
	{
    	//toggle = false;
    	if(!tag){
    		if(isRightTop){
    			//tvRightText.setText("编辑");
    			tvRightText.setVisibility(View.VISIBLE);
    		}else{
    			tvRightText.setVisibility(View.INVISIBLE);
    		}
    	}
	}
    
    //删除消息后是否为空
    @Subscriber(tag = "msgxitongfragment_del")
	private void setDelXitongRight(boolean flag)
	{
		if(flag){
			tvRightText.setVisibility(View.INVISIBLE);
		}
	}
    
    //删除消息后是否为空
    @Subscriber(tag = "msgdingdanfragment_del")
	private void setDelDingdanRight(boolean flag)
	{
		if(flag){
			tvRightText.setVisibility(View.INVISIBLE);
		}
	}
    
    //功能切换通知
    private void changeRightText(){
    	toggle = !toggle;
    	if(toggle){
    		tvRightText.setText("取消");
    		if(tag){
        		EventBus.getDefault().post(true, "msgxitongfragment_edit");
    		}else{
    			EventBus.getDefault().post(true, "msgdingdanfragment_edit");
    		}
    	}else{
    		tvRightText.setText("编辑");
    		if(tag){
    			EventBus.getDefault().post(false, "msgxitongfragment_edit");
    		}else{
    			EventBus.getDefault().post(false, "msgdingdanfragment_edit");
    		}
    	}
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回结束
                finish();
                break;

            case R.id.ll_xitong_fd://系统消息
            	tag = true;
                setViewVisible();
                refreshPos(0);
                break;
            case R.id.ll_dingdan_fd://订单消息
            	tag = false;
                setViewVisible();
                refreshPos(1);
                break;
            case R.id.tv_title_right_text://右上角编辑or取消
            	changeRightText();
                break;
            default:
                break;
        }
    }

    private void setViewVisible()
    {
        if (tag)
        {   //系统消息
            tv_xitong.setBackgroundResource(R.drawable.current_down);
            tv_xitong.setTextColor(getResources().getColor(R.color.main_imred));
            tv_dingdan.setBackgroundResource(R.drawable.default_up);
            tv_dingdan.setTextColor(getResources().getColor(R.color.main_font_gray));
        } else
        {   //订单消息
            tv_xitong.setBackgroundResource(R.drawable.default_up);
            tv_xitong.setTextColor(getResources().getColor(R.color.main_font_gray));
            tv_dingdan.setBackgroundResource(R.drawable.current_down);
            tv_dingdan.setTextColor(getResources().getColor(R.color.main_imred));
        }
    }

    private void refreshPos(int num)
    {
        viewPager.setCurrentItem(num);
        
        if(num == 0){
    		if(null!=msgXitongFragment.getDatas()&&msgXitongFragment.getDatas().size()>0){
    			tvRightText.setVisibility(View.VISIBLE);
    	    	tvRightText.setText("编辑");
    	        toggle = false;
    			EventBus.getDefault().post(false, "msgxitongfragment_edit");
        	}else{
        		tvRightText.setVisibility(View.INVISIBLE);
        	}
        }
        if(num == 1){
    		if(null!=msgDingdanFragment.getDatas()&&msgDingdanFragment.getDatas().size()>0){
    			tvRightText.setVisibility(View.VISIBLE);
    	    	tvRightText.setText("编辑");
    	    	toggle = false;
    			EventBus.getDefault().post(false, "msgdingdanfragment_edit");
        	}else{
        		tvRightText.setVisibility(View.INVISIBLE);
        	}
        }
        
        switch (num)
        {
            case 0://系统消息
                tag = true;
                tv_xitong.setBackgroundResource(R.drawable.current_down);
                tv_xitong.setTextColor(getResources().getColor(R.color.main_imred));
                tv_dingdan.setBackgroundResource(R.drawable.default_up);
                tv_dingdan.setTextColor(getResources().getColor(R.color.main_font_gray));
                break;
            case 1://订单消息
                tag = false;
                tv_xitong.setBackgroundResource(R.drawable.default_up);
                tv_xitong.setTextColor(getResources().getColor(R.color.main_font_gray));
                tv_dingdan.setBackgroundResource(R.drawable.current_down);
                tv_dingdan.setTextColor(getResources().getColor(R.color.main_imred));

                EventBus.getDefault().post(tag, "msgdingdanfragment_current");
                break;
        }
        
    }
}

package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.mysteel.banksteel.view.ui.wheel.ArrayWheelAdapter;
import com.mysteel.banksteel.view.ui.wheel.OnWheelScrollListener;
import com.mysteel.banksteel.view.ui.wheel.WheelView;
import com.mysteel.banksteeltwo.R;

/**
 * 日期选择
 * @author:wushaoge
 * @date：2015年8月13日16:11:41
 */
public class LoadPopupWheel extends PopupWindow {

    public interface PopupValueLinstener {
        void change(int firstValue);
    }
    private Context context;
    private WheelView[] wheels = new WheelView[1];
    private PopupValueLinstener valueChange ;
    private String[] num1; //年

    public LoadPopupWheel(Context context, PopupValueLinstener valueLinstener) {
        super(context);
        this.context = context;
        this.valueChange = valueLinstener;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_sel_load, null);
        wheels[0] = (WheelView) view.findViewById(R.id.z_wheel_first);

        wheels[0].addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });

        view.findViewById(R.id.z_wheel_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur1 = wheels[0].getCurrentItem();
                valueChange.change(cur1+1);
                LoadPopupWheel.this.dismiss();
            }
        });

        this.setContentView(view);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true); //设置PopupWindow可获得焦点
        this.setTouchable(true); //设置PopupWindow可触摸
        this.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
    }
    public void setWidthAndHeight(int w,int h){
        this.setWidth(w);
        this.setHeight(h);
    }

    public void setData(String[] nums, int curs,int which){
        wheels[which].setVisibility(View.VISIBLE);
        wheels[which].setViewAdapter(new ArrayWheelAdapter<String>(context, nums));
        wheels[which].setCurrentItem(curs);
    }



}

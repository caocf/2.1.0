package com.mysteel.banksteel.view.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.mysteel.banksteel.view.ui.wheel.ArrayWheelAdapter;
import com.mysteel.banksteel.view.ui.wheel.OnWheelChangedListener;
import com.mysteel.banksteel.view.ui.wheel.OnWheelScrollListener;
import com.mysteel.banksteel.view.ui.wheel.WheelView;
import com.mysteel.banksteeltwo.R;

/**
 * 日期选择
 * @author:wushaoge
 * @date：2015年8月13日16:11:41
 */
public class DatePopupWheel  extends PopupWindow {

    public interface PopupValueLinstener {
        void change(int firstValue,int secondValue,int thirdValue,int fourValue);
        String[] changeData(int length);
    }
    private Context context;
    private WheelView[] wheels = new WheelView[4];
    private PopupValueLinstener valueChange ;
    private String[] num1; //年
    private String[] num2; //月
    private String[] num3; //日
    private String[] num4; //时


    public DatePopupWheel(Context context,PopupValueLinstener valueLinstener) {
        super(context);
        this.context = context;
        this.valueChange = valueLinstener;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_date, null);
        wheels[0] = (WheelView) view.findViewById(R.id.z_wheel_first);
        wheels[1] = (WheelView) view.findViewById(R.id.z_wheel_second);
        wheels[2] = (WheelView) view.findViewById(R.id.z_wheel_third);
        wheels[3] = (WheelView) view.findViewById(R.id.z_wheel_four);
        wheels[0].setVisibility(View.GONE);
        wheels[1].setVisibility(View.GONE);
        wheels[2].setVisibility(View.GONE);
        wheels[3].setVisibility(View.GONE);

        wheels[1].addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        wheels[0].addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                updateDayDatas();
            }
        });

        wheels[1].addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                updateDayDatas();
            }
        });



        view.findViewById(R.id.z_wheel_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur1 = wheels[0].getCurrentItem();
                int cur2 = wheels[1].getCurrentItem();
                int cur3 = wheels[2].getCurrentItem();
                int cur4 = wheels[3].getCurrentItem();
                valueChange.change(cur1,cur2,cur3,cur4);
                DatePopupWheel.this.dismiss();
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
        if(which==1){
            num1 = nums;
        }
        if(which==2){
            num2 = nums;
        }
        if(which==3){
            num3 = nums;
        }
        if(which==4){
            num4 = nums;
        }
    }

    /**
     * 更新日期datas  每个月30天还是31天
     */
    public void updateDayDatas(){
        //拿到月份
        String monthStr = num1[wheels[1].getCurrentItem()]; //11月
        int month = Integer.parseInt(monthStr.substring(0,monthStr.length()-1));
        int length = 30; //默认为31天
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ){
            length = 31;
        }else{
            length = 30;
        }

        //首先拿到年份
        boolean isRun = false;
        String yearStr = num1[wheels[0].getCurrentItem()]; //2015年
        int year = Integer.parseInt(yearStr.substring(0,yearStr.length()-1));
        //判断是否为闰年
        if(((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)){
            isRun = true;
        }else{
            isRun = false;
        }

        //只有2月份需要特殊处理
        if(isRun&&month==2){
            length = 29;
        }else if(!isRun&&month==2){
            length = 28;
        }

        //初始化号
//        num3 = new String[length];
//        for (int i = 0; i < length; i++) {
//            num3[i] = i+1 + "号";
//        }

        num3 = valueChange.changeData(length);
        //赋值
        ArrayWheelAdapter adapter = new ArrayWheelAdapter<String>(context, num3);
        wheels[2].setViewAdapter(adapter);

        //获得当前选择的多少号
        int curDay = wheels[2].getCurrentItem();
        if(length>curDay){
            wheels[2].setCurrentItem(curDay);
        }else{
            wheels[2].setCurrentItem(length-1);
        }

    }

}

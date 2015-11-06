package com.mysteel.banksteel.view.ui.popwindow;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

public class LoadPopWindow {
	public interface SelectPopListener{
		public void request(String str);
	}

	private SelectPopListener listener;

	public void setListener(SelectPopListener listener){
		this.listener = listener;
	}

	private PopupWindow popupWindow;



	private LinearLayout ll_content1;
	private LinearLayout ll_content2;
	private LinearLayout ll_cancle;

	private TextView tv_content1;
	private TextView tv_content2;


	/**
	 * 弹窗
	 */
	public void showPopupWindow(final Context context,Activity activity){
		LayoutInflater mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View menuView = mLayoutInflater.inflate(R.layout.popwindow_load, null, true);
		popupWindow = new PopupWindow(menuView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);


		// 设置动画效果
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);

		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		//必须设置
		Drawable win_bg = context.getResources().getDrawable(R.color.font_white_one);
		popupWindow.setBackgroundDrawable(win_bg);

		popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		//弹出窗口消失方法
		popupWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
					popupWindow.setAnimationStyle(R.anim.anim_price_out);
					popupWindow.dismiss();
				}
				return false;
			}
		});

		ll_content1 = (LinearLayout)menuView.findViewById(R.id.ll_content1);
		ll_content2 = (LinearLayout)menuView.findViewById(R.id.ll_content2);
		ll_cancle = (LinearLayout)menuView.findViewById(R.id.ll_cancle);

		tv_content1 = (TextView)menuView.findViewById(R.id.tv_content1);
		tv_content2 = (TextView)menuView.findViewById(R.id.tv_content2);

		ll_content1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.request("1");
				popupWindow.dismiss();
			}
		});

		ll_content2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.request("2");
				popupWindow.dismiss();
			}
		});


		ll_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		//popupWindow.showAsDropDown(parent, 0, 0);
		View rootView = ((ViewGroup) (activity.getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
		popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0); //设置窗口显示位置

		popupWindow.update();
	}


	public void setData(String tvContent1,String tvContent2){
		tv_content1.setText(tvContent1);
		tv_content2.setText(tvContent2);
	}


}

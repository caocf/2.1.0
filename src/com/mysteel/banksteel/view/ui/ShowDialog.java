package com.mysteel.banksteel.view.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;
public class ShowDialog extends Dialog{
	
	    public interface ICallBack{
	    	public void requestOK();
	    	public void requestCancle();
	    }
	
		protected Context mContext;
		
		private TextView tv_content;
		private TextView tv_ok; 
		private TextView tv_cancle;
		private ICallBack callBack;
		
		
		public ShowDialog(Context context,String content,ICallBack callBack1) {
			super(context,R.style.dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.show_dialog);
			this.callBack = callBack1;
			
			this.setCanceledOnTouchOutside(true);
			
			tv_content = (TextView)findViewById(R.id.tv_content);
			tv_ok = (TextView)findViewById(R.id.tv_ok);
			tv_cancle = (TextView)findViewById(R.id.tv_cancle);
			
			tv_content.setText(content);
			
			tv_ok.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callBack.requestOK();
					dismiss();
				}
			});
			
			tv_cancle.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callBack.requestCancle();
					dismiss();
				}
			});
			
		}

	public void setcommitBtn(String str)
	{
		tv_ok.setText(str);
	}

	public void setcancleBtn(String str)
	{
		tv_cancle.setText(str);
	}
}

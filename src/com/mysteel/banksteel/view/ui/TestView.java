package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * Ìá»õµÄview
 * Created by wushaoge on 2015/10/26.
 */
public class TestView extends LinearLayout implements View.OnClickListener {

    public TestView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_tihuo_item, this, true);
        initViews();
    }


    private void initViews() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ischeck:
                
                break;

        }

    }



}

package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * 【提单编号】
 * Created by wushaoge on 2015/10/26.
 */
public class TidanView extends LinearLayout implements View.OnClickListener {

    private EditText etTidanNum; //提单编号
    private EditText etContactType; //联系方式(一般为手机号)


    public TidanView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_tidan_item, this, true);
        initViews();
    }

    private void initViews() {
        etTidanNum = (EditText) findViewById(R.id.et_tidan_num);
        etContactType = (EditText) findViewById(R.id.et_contact_type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    /**
     * 获取提单编号
     * @return
     */
    public String getTidanNum(){
        return etTidanNum.getText().toString();
    }

    /**
     * 获取提单编号
     * @return
     */
    public String getContactType(){
        return etContactType.getText().toString();
    }



}

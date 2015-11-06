package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * 【身份证】
 * Created by wushaoge on 2015/10/26.
 */
public class IdentityView extends LinearLayout implements View.OnClickListener {

    private EditText etShenfenzheng; //提单编号
    private EditText etContactType; //联系方式(一般为手机号)


    public IdentityView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_sfz_item, this, true);
        initViews();
    }


    private void initViews() {
        etShenfenzheng = (EditText) findViewById(R.id.et_shenfenzheng);
        etContactType = (EditText) findViewById(R.id.et_contact_type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    /**
     * 获取身份证
     * @return
     */
    public String getShenfenzheng(){
        return etShenfenzheng.getText().toString();
    }

    /**
     * 获取联系方式
     * @return
     */
    public String getContactType(){
        return etContactType.getText().toString();
    }



}

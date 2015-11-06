package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * �����֤��
 * Created by wushaoge on 2015/10/26.
 */
public class IdentityView extends LinearLayout implements View.OnClickListener {

    private EditText etShenfenzheng; //�ᵥ���
    private EditText etContactType; //��ϵ��ʽ(һ��Ϊ�ֻ���)


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
     * ��ȡ���֤
     * @return
     */
    public String getShenfenzheng(){
        return etShenfenzheng.getText().toString();
    }

    /**
     * ��ȡ��ϵ��ʽ
     * @return
     */
    public String getContactType(){
        return etContactType.getText().toString();
    }



}

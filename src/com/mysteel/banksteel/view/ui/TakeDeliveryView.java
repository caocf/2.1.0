package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteeltwo.R;

/**
 * �����view
 * Created by wushaoge on 2015/10/26.
 */
public class TakeDeliveryView extends LinearLayout implements View.OnClickListener {

    private ImageView ivIscheck; //�Ƿ�ѡ��
    private TextView tvName; //����
    private TextView tvMaterial; //����
    private TextView tvSpec; //���
    private TextView tvPrice; //�۸�
    private TextView tvWare; //�ֿ�
    private TextView tvYiti; //����
    private TextView tvReduce; //����
    private EditText etNum; //����
    private TextView tvAdd; //����

    private boolean isCheck = false;
    private String yitiNum = "0";
    private int ketiNum = 8; //�������

    public enum ClickType{
        DOWN,UP
    }

    public TakeDeliveryView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_tihuo_item, this, true);
        initViews();
    }


    private void initViews() {
        ivIscheck = (ImageView) findViewById(R.id.iv_ischeck);
        ivIscheck.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMaterial = (TextView) findViewById(R.id.tv_material);
        tvSpec = (TextView) findViewById(R.id.tv_spec);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvWare = (TextView) findViewById(R.id.tv_ware);
        tvYiti = (TextView) findViewById(R.id.tv_yiti);
        tvReduce = (TextView) findViewById(R.id.tv_reduce);
        tvReduce.setOnClickListener(this);
        etNum = (EditText) findViewById(R.id.et_num);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvAdd.setOnClickListener(this);

        etNum.setText(ketiNum+"");
        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    etNum.setText("0");
                }else{
                    if(Integer.parseInt(s.toString())>ketiNum){
                        etNum.setText(ketiNum+"");
                    }
                }

            }
        });

        setIvIscheck(isCheck);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ischeck:
                isCheck = !isCheck;
                setIvIscheck(isCheck);
                break;
            case R.id.tv_reduce:
                change(ClickType.DOWN);
                break;
            case R.id.tv_add:
                change(ClickType.UP);
                break;

        }

    }


    /**
     * ����ѡ��״̬
     * @param isCheck
     */
    public void setIvIscheck(boolean isCheck){
        if(isCheck){
            ivIscheck.setImageResource(R.drawable.msg_check);
        }else{
            ivIscheck.setImageResource(R.drawable.msg_uncheck);
        }
    }

    /**
     * ��ȡ״̬
     */
    public boolean getIscheck(){
        return isCheck;
    }


    /**
     * ��ȡ��ļ���
     */
    public int getWantNum(){
        int num = Integer.parseInt(etNum.getText().toString());
        return num;
    }




    private void change(ClickType type){
        int numTemp =  Integer.parseInt(etNum.getText().toString());
        if(type.equals(ClickType.DOWN)){
            numTemp -= 1;
        }else{
            numTemp += 1;
        }
        if(numTemp<=0){
            numTemp = 0;
        }
        if(numTemp>=ketiNum){
            numTemp = ketiNum;
        }
        etNum.setText(numTemp+"");
    }


}

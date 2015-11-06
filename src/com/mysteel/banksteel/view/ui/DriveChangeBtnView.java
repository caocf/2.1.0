package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * �Ӽ���ť
 * Created by wushaoge on 2015/10/26.
 */
public class DriveChangeBtnView extends LinearLayout implements View.OnClickListener {

    public interface IDrvieChange{
        void addDrive();
        void delDrive();
    }
    private LinearLayout llAdd; //����
    private LinearLayout llDelete; //ɾ��

    private IDrvieChange drvieChange;

    private boolean flag = false; //false����ʾɾ���Ĳ���

    public DriveChangeBtnView(Context context, boolean flag, IDrvieChange drvieChange) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_driver_change_btn_item, this, true);

        this.flag = flag;
        this.drvieChange = drvieChange;

        initViews();
    }

    private void initViews() {
        llAdd = (LinearLayout) findViewById(R.id.ll_add);
        llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        llAdd.setOnClickListener(this);
        llDelete.setOnClickListener(this);

        if(flag){
            llDelete.setVisibility(View.VISIBLE);
        }else{
            llDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_add:
                drvieChange.addDrive();
                break;
            case R.id.ll_delete:
                drvieChange.delDrive();
                break;
        }

    }


}

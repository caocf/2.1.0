package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * ��ƾ�����š�
 * Created by wushaoge on 2015/10/26.
 */
public class DriveView extends LinearLayout implements View.OnClickListener {

    public interface IDrvieChange{
        void addDrive();
        void delDrive();
    }

    private EditText etDriveName; //˾������
    private EditText etDrivePhone; //˾���ֻ�
    private EditText etDriveLicenseplate; //˾�����ƺ�

    public DriveView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_driver_item, this, true);

        initViews();
    }

    private void initViews() {
        etDriveName = (EditText) findViewById(R.id.et_drive_name);
        etDrivePhone = (EditText) findViewById(R.id.et_drive_phone);
        etDriveLicenseplate = (EditText) findViewById(R.id.et_drive_licenseplate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    /**
     * ���˾������
     * @return
     */
    public String getDriveName(){
        return etDriveName.getText().toString();
    }

    /**
     * ���˾���ֻ���
     * @return
     */
    public String getDrivePhone(){
        return etDrivePhone.getText().toString();
    }


    /**
     * ���˾�����ƺ�
     * @return
     */
    public String getDriveLicenseplate(){
        return etDriveLicenseplate.getText().toString();
    }

}

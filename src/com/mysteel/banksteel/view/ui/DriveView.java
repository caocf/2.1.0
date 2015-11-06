package com.mysteel.banksteel.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mysteel.banksteeltwo.R;

/**
 * 【凭车船号】
 * Created by wushaoge on 2015/10/26.
 */
public class DriveView extends LinearLayout implements View.OnClickListener {

    public interface IDrvieChange{
        void addDrive();
        void delDrive();
    }

    private EditText etDriveName; //司机姓名
    private EditText etDrivePhone; //司机手机
    private EditText etDriveLicenseplate; //司机车牌号

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
     * 获得司机姓名
     * @return
     */
    public String getDriveName(){
        return etDriveName.getText().toString();
    }

    /**
     * 获得司机手机号
     * @return
     */
    public String getDrivePhone(){
        return etDrivePhone.getText().toString();
    }


    /**
     * 获得司机车牌号
     * @return
     */
    public String getDriveLicenseplate(){
        return etDriveLicenseplate.getText().toString();
    }

}

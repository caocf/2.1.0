package com.mysteel.banksteel.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mysteel.banksteeltwo.R;

/**
 * Created by zoujian on 15/8/6.
 */
public class TextActivity extends BaseActivity
{

    RelativeLayout rl_layout_left;
    Button btn_cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        rl_layout_left = (RelativeLayout) findViewById(R.id.rl_layout_left);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        overridePendingTransition(R.anim.activity_close,0);
    }
}

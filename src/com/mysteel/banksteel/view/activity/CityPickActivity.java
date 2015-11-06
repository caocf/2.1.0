package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.view.ui.city.CityPicker;
import com.mysteel.banksteeltwo.R;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CityPickActivity extends BaseActivity implements OnClickListener
{
	private CityPicker cityPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_picker);
		initViews();

	}

	@Override
	protected void initViews()
	{
		super.initViews();
		tvRightText.setText("确定");
		tvTitle.setText("选择城市");
		tvRightText.setOnClickListener(this);
		backLayout.setOnClickListener(this);

		cityPicker = (CityPicker) findViewById(R.id.citypicker);
		setAnimator();

	}

	private void setAnimator()
	{
		int screenHeight = getResources().getDisplayMetrics().heightPixels;
		float currentY = cityPicker.getTranslationY();
		ObjectAnimator animator = ObjectAnimator.ofFloat(cityPicker,
				"translationY", currentY, -screenHeight / 2);
		animator.setDuration(1000);
		animator.start();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_title_right_text:
			Intent sIntent = new Intent();
			sIntent.putExtra("PROVINCE", cityPicker.getProvince());
			sIntent.putExtra("CITY", cityPicker.getCity());
			sIntent.putExtra("COUNTY", cityPicker.getCounty());
			setResult(RESULT_OK, sIntent);
			finish();
			break;

		case R.id.menu_layout:
			Intent fIntent = new Intent();
			setResult(RESULT_CANCELED, fIntent);
			finish();

		default:
			break;
		}
	}

}

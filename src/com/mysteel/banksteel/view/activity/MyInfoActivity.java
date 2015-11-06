package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.view.fragments.CompanyInfoFragment;
import com.mysteel.banksteel.view.fragments.PersonalInfoFragment;
import com.mysteel.banksteeltwo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的资料
 */
public class MyInfoActivity extends BaseActivity implements OnClickListener
{

	/** viewpager */
	private ViewPager viewPager;
	/** 适配器data */
	private List<Fragment> mFragments;
	/** fragmentManager */
	private FragmentManager mManager;
	/** 公司信息 */
	private TextView companyTv;
	/** 个人信息 */
	private TextView personalTv;

	private boolean toggleRight = true; //true代表编辑  false代表取消

	/************* 下划线相关. **************/
	/** 颜色线条 */
	private ImageView colorLine;
	private int mOffset;
	private int mCurrIndex = 0;
	/************* 下划线相关. **************/

	/** 屏幕宽度 */
	private int mScreenwidth;

	private static enum SelectedView
	{
		personalTv, companyTv
	}

	private PersonalInfoFragment personalInfoFragment;
	private CompanyInfoFragment companyInfoFragment;
	/** 保存 */
	private TextView tvSave;
	/** 加载页面完成后是否跳转到公司信息 Fragment */
	private boolean isJumpCompany;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		isJumpCompany = getIntent().getBooleanExtra(
				Constants.JUMP_COMPANY_INFO, false);
		initViews();
	}

	protected void initViews()
	{
		super.initViews();
		tvTitle.setText("我的资料");
		tvRightText.setText("编辑");

		personalTv = (TextView) findViewById(R.id.tv_myinfo_personal);
		companyTv = (TextView) findViewById(R.id.tv_myinfo_company);
		colorLine = (ImageView) findViewById(R.id.view_myinfo_linecolor);
		tvSave = (TextView) findViewById(R.id.tv_title_right_text);

		personalTv.setOnClickListener(this);
		companyTv.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);
		tvSave.setOnClickListener(this);

		/** 初始化下划线相关 */
		mScreenwidth = getResources().getDisplayMetrics().widthPixels;
		mOffset = (mScreenwidth - colorLine.getPaddingLeft() - colorLine
				.getPaddingRight()) / 2;
		ViewGroup.LayoutParams mparam = colorLine.getLayoutParams();
		mparam.width = mOffset;
		colorLine.setLayoutParams(mparam);

		initViewPager();
	}

	/**
	 * 初始化viewpager
	 */
	private void initViewPager()
	{
		viewPager = (ViewPager) findViewById(R.id.viewpager_myinfo);

		mFragments = new ArrayList<Fragment>();
		personalInfoFragment = new PersonalInfoFragment();
		companyInfoFragment = new CompanyInfoFragment();
		mFragments.add(personalInfoFragment);
		mFragments.add(companyInfoFragment);

		mManager = getSupportFragmentManager();
		viewPager.setAdapter(new MyViewPager(mManager));

		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				toggleRight = true;
				if (position == 0)
				{
					setSelectedColor(SelectedView.personalTv);
				} else if (position == 1)
				{
					setSelectedColor(SelectedView.companyTv);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
		if (isJumpCompany)
		{
			viewPager.setCurrentItem(1);
		}
	}

	class MyViewPager extends FragmentPagerAdapter
	{

		public MyViewPager(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			return mFragments.get(position);
		}

		@Override
		public int getCount()
		{
			return mFragments.size();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_title_right_text:
			clickRight();
			break;
		case R.id.menu_layout:
			// 左侧菜单
			finish();
			break;
		case R.id.tv_myinfo_personal:
			// 个人信息
			toggleRight = true;
			setSelectedColor(SelectedView.personalTv);
			viewPager.setCurrentItem(0);
			break;
		case R.id.tv_myinfo_company:
			// 公司信息
			toggleRight = true;
			setSelectedColor(SelectedView.companyTv);
			viewPager.setCurrentItem(1);
			break;

		default:
			break;
		}
	}


	/**
	 * 右上角点击
	 */
	private void clickRight(){
		int currentItem = viewPager.getCurrentItem();
		// 右侧菜单(保存接口)
		//EventBus.getDefault().post("aaa", "" + currentItem);
		toggleRight = !toggleRight;
		if(toggleRight){
			tvRightText.setText("编辑");
		}else{
			tvRightText.setText("取消");
		}

		if(currentItem==0){
			setSelectedColor(SelectedView.personalTv);
			personalInfoFragment.onRefreshRight(toggleRight);
		}else{
			setSelectedColor(SelectedView.companyTv);
			companyInfoFragment.onRefreshRight(toggleRight);
		}
	}

	/**
	 * 设置选中textview的字体颜色以及下划线颜色
	 */
	private void setSelectedColor(SelectedView view)
	{
		if(toggleRight){
			tvRightText.setText("编辑");
		}else{
			tvRightText.setText("取消");
		}
		personalInfoFragment.onRefreshRight(toggleRight);
		companyInfoFragment.onRefreshRight(toggleRight);

		personalTv.setTextColor(getResources().getColor(R.color.main_font_gray));
		companyTv.setTextColor(getResources().getColor(R.color.main_font_gray));
		personalTv.setBackgroundResource(R.drawable.default_up);
		companyTv.setBackgroundResource(R.drawable.default_up);
		if (view == SelectedView.personalTv)
		{
			personalTv.setTextColor(getResources().getColor(R.color.main_imred));
			personalTv.setBackgroundResource(R.drawable.current_down);
			//setUnderLine(0);
		} else if (view == SelectedView.companyTv)
		{
			companyTv.setTextColor(getResources().getColor(R.color.main_imred));
			companyTv.setBackgroundResource(R.drawable.current_down);
			//setUnderLine(1);
		}
	}

	/**
	 * 设置下划线
	 */
	private void setUnderLine(int pos)
	{
		Animation animation = new TranslateAnimation(mOffset * mCurrIndex
				+ colorLine.getPaddingLeft(), mOffset * pos
				+ colorLine.getPaddingLeft(), 0, 0);
		mCurrIndex = pos;
		animation.setFillAfter(true);
		animation.setDuration(300);
		colorLine.startAnimation(animation);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	}
}

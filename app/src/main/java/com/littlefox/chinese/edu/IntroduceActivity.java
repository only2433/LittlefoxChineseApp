package com.littlefox.chinese.edu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.IntroduceContentFragment;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroduceActivity extends BaseActivity
{
	@Nullable
	@BindView(R.id.top_margin_layout)
	ScalableLayout _RadioTopMarginLayout;
	
	@BindView(R.id.introduce_viewpager)
	ViewPager _ViewPager;
	
	@BindViews({R.id.introduce_indicator_0, R.id.introduce_indicator_1, R.id.introduce_indicator_2})
	List<ImageView> _IndicatorList;
	
	@BindView(R.id.introduce_login_button)
	ImageView _LoginButton;
	
	@BindView(R.id.introduce_login_button_text)
	TextView _LoginText;
	
	@BindView(R.id.introduce_sign_button)
	ImageView _SignButton;
	
	@BindView(R.id.introduce_sign_button_text)
	TextView _SignText;
	
	private static final int REQUEST_LOGIN_SUCCESS = 101;
	
	private static final int MAX_PAGE_SIZE = 3;
	
	private static final int PAGE_1 = 0;
	private static final int PAGE_2 = 1;
	private static final int PAGE_3 = 2;


	private ArrayList<Fragment> mFragmentList;
	private IntroduceItemPagerAdapter mIntroduceItemPagerAdapter;
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.introduce_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			if(Feature.HAVE_NAVIGATION_BAR || Feature.IS_MINIMUM_DISPLAY_SIZE)
			{
				setContentView(R.layout.introduce_main_small);
			}
			else
			{
				setContentView(R.layout.introduce_main);
			}
		}

		ButterKnife.bind(this);
		changeIndicator(PAGE_1);
		initFont();
		initText();
		initView();
	}
	
	private void initFont()
	{
		_LoginText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_SignText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		_LoginText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_login));
		_SignText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_sign));
	}
	
	private void initView()
	{
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			_RadioTopMarginLayout.setVisibility(View.VISIBLE);
		}
		mIntroduceItemPagerAdapter = new IntroduceItemPagerAdapter(getSupportFragmentManager());
		mIntroduceItemPagerAdapter.addFragment(PAGE_1);
		mIntroduceItemPagerAdapter.addFragment(PAGE_2);
		mIntroduceItemPagerAdapter.addFragment(PAGE_3);
		_ViewPager.setAdapter(mIntroduceItemPagerAdapter);
		_ViewPager.addOnPageChangeListener(mOnPageChangeListener);
	}
	
	private void changeIndicator(int index)
	{
		for(int i = 0 ; i < MAX_PAGE_SIZE; i++)
		{
			_IndicatorList.get(i).setImageResource(R.drawable.intro_ball_tablet);
		}
		switch(index)
		{
		case 0:
			_IndicatorList.get(PAGE_1).setImageResource(R.drawable.intro_ball_on_tablet);
			break;
		case 1:
			_IndicatorList.get(PAGE_2).setImageResource(R.drawable.intro_ball_on_tablet);
			break;
		case 2:
			_IndicatorList.get(PAGE_3).setImageResource(R.drawable.intro_ball_on_tablet);
			break;
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_INTRODUCE);
		Log.f("");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.f("");
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		Log.f("");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.f("");
	}
	
	@OnClick({R.id.introduce_login_button, R.id.introduce_sign_button})
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.introduce_login_button:
			Log.f("LOGIN enter");
			MainSystemFactory.getInstance().startActivityWithAnimationForResult(MainSystemFactory.MODE_LOGIN , REQUEST_LOGIN_SUCCESS);
			break;
		case R.id.introduce_sign_button:
			Log.f("SIGN enter");
			MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_USER_SIGN);
			break;
		}
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener()
	{
		@Override
		public void onPageScrollStateChanged(int position){}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2){}

		@Override
		public void onPageSelected(int position)
		{
			changeIndicator(position);
		}	
	};
	
	private class IntroduceItemPagerAdapter extends FragmentPagerAdapter
	{
		
		public IntroduceItemPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int type)
		{
			int introImageResource = -1;
			Fragment fragment = null;
			switch(type)
			{
			case PAGE_1:
				fragment = IntroduceContentFragment.getInstance();
				if(Feature.IS_LANGUAGE_ENG)
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img01_en_tablet : R.drawable.intro_img01_en;
				}
				else
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img01_tablet : R.drawable.intro_img01;
				}
				
				((IntroduceContentFragment)fragment).setResource(
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_title_1),
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_message_1), 
						introImageResource);
				mFragmentList.add(fragment);
				break;
			case PAGE_2:
				fragment = IntroduceContentFragment.getInstance();
				if(Feature.IS_LANGUAGE_ENG)
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img02_en_tablet : R.drawable.intro_img02_en;
				}
				else
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img02_tablet : R.drawable.intro_img02;
				}
			
				((IntroduceContentFragment)fragment).setResource(
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_title_2),
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_message_2), 
						introImageResource);
				mFragmentList.add(fragment);
				break;
			case PAGE_3:
				fragment = IntroduceContentFragment.getInstance();
				if(Feature.IS_LANGUAGE_ENG)
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img03_en_tablet : R.drawable.intro_img03_en;
				}
				else
				{
					introImageResource = Feature.IS_TABLET ? R.drawable.intro_img03_tablet : R.drawable.intro_img03;
				}
				((IntroduceContentFragment)fragment).setResource(
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_title_3),
						CommonUtils.getInstance(IntroduceActivity.this).getLanguageTypeString(R.array.introduce_message_3), 
						introImageResource);
				mFragmentList.add(fragment);
				break;
			}
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position)
		{
			return mFragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return mFragmentList.size();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    Log.i("onActivityResult(" + requestCode + "," + resultCode + "," + data);
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    switch(requestCode)
	    {
	    	case REQUEST_LOGIN_SUCCESS:
	    		if(resultCode == RESULT_OK)
	    		{
	    			finish();
	    		}
	    		break;
	    }
	}
}

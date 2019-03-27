package com.littlefox.chinese.edu;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.async.UserInformationSetAsync;
import com.littlefox.chinese.edu.async.UserPasswordChangeAsync;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.UserInformationConvertFragment;
import com.littlefox.chinese.edu.fragment.UserInformationShowFragment;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.extra.SwipeDisableViewPager;
import com.littlefox.library.view.scroller.FixedSpeedScroller;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInformationModificationActivity extends BaseActivity
{
	@BindView(R.id.myinfo_base_coordinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.myinfo_menu_title)
	TextView _TitleText;
	
	@BindView(R.id.myinfo_close)
	ImageView _CloseButton;
	
	@BindView(R.id.myinfo_viewpager)
	SwipeDisableViewPager _ViewPager;
	
	@BindView(R.id.myinfo_progress_wheel_layout)
	ScalableLayout _ProgressLayout;
	
	Handler mMainHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_VIEW_INIT:
				init();
				break;
			case MESSAGE_MODIFICATION_COMPLETE:
				_ViewPager.setCurrentItem(USER_INFORMATION_SHOW, true);
				break;
			case MESSAGE_USER_INFORMATION_CHANGE:
				requestUserInformationModification();
				break;
			case MESSAGE_PASSWORD_CHANGE:
				requestUserPasswordModification();
				break;
			case MESSAGE_FINISH:
				finish();
				break;
			}
		}
		
	};
	
	/**
	 * UserInfomation 의 Fragment와 통신하기 위한 Holder
	 * @author 정재현
	 *
	 */
	public interface UserInformationHolder
	{
		/**
		 * 비밀번호 변경
		 */
        int ACTION_PASSWORD_CHANGE 		= 0;
		/**
		 * 정보 변경 
		 */
        int ACTION_INFORMATION_CHANGE 	= 1;
		
		/**
		 * 결제
		 */
        int ACTION_PAYMENT						= 2;
		
		void setOnUserInformationListener(OnUserInformationListener onUserInformationListener);
	}
	
	public interface OnUserInformationListener
	{
		void executeAction(int type);
		void requestModification(int type, Object object);
		void cancelModification();
		void showMessage(String text, int color);
	}
	
	private static final int MESSAGE_VIEW_INIT 								= 0;
	private static final int MESSAGE_MODIFICATION_COMPLETE 		= 1;
	private static final int MESSAGE_USER_INFORMATION_CHANGE 	= 2;
	private static final int MESSAGE_PASSWORD_CHANGE					= 3;
	private static final int MESSAGE_FINISH										= 4;

	private static final int DURATION_ANIMATION = 500;
	private static final int DURATION_SHOW_NOTIFICATION = 1500;
	private static final int DURATION_VIEW_LOAD_START = 1000;

	private static final int USER_INFORMATION_SHOW 				= 0;
	private static final int USER_INFORMATION_MODIFICATION 	= 1;
	
	private ArrayList<Fragment> mUserInformationFragmentList;
	private UserInformationPagerAdapter mUserInformationPagerAdapter;
	private FixedSpeedScroller mFixedSpeedScroller;
	private UserBaseInformationObject mModificationUserInformation = null;
	private String mModificationUserPassword	= null;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.myinfo_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.myinfo_main);
		}
		
		ButterKnife.bind(this);
		
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		initFont();
		initText();
		_ProgressLayout.setVisibility(View.VISIBLE);
		mMainHandler.sendEmptyMessageDelayed(MESSAGE_VIEW_INIT, DURATION_VIEW_LOAD_START);
	}
	
	private void init()
	{
		_ProgressLayout.setVisibility(View.GONE);
		mUserInformationPagerAdapter = new UserInformationPagerAdapter(getSupportFragmentManager());
		mUserInformationPagerAdapter.addFragment(USER_INFORMATION_SHOW);
		mUserInformationPagerAdapter.addFragment(USER_INFORMATION_MODIFICATION);
		_ViewPager.setAdapter(mUserInformationPagerAdapter);
		_ViewPager.addOnPageChangeListener(onPageChangeListener);
		
		mFixedSpeedScroller = new FixedSpeedScroller(this, new LinearOutSlowInInterpolator());
		mFixedSpeedScroller.setDuration(DURATION_ANIMATION);
		
		try
		{
			Field scroller = ViewPager.class.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			scroller.set(_ViewPager, mFixedSpeedScroller);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void initFont()
	{
		_TitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		_TitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_my_info));
	}
	
	private void initTransition()
	{
		Slide transitionEnter = new Slide();
	    transitionEnter.setSlideEdge(Gravity.RIGHT);
	    transitionEnter.setInterpolator(new LinearOutSlowInInterpolator());
	    transitionEnter.setDuration(DURATION_ANIMATION);
	    transitionEnter.excludeTarget(android.R.id.statusBarBackground, true);
	    
	    Slide transitionExit = new Slide();
	    transitionExit.setSlideEdge(Gravity.RIGHT);
	    transitionExit.setDuration(DURATION_ANIMATION);
	    transitionExit.setInterpolator(new LinearOutSlowInInterpolator());
	    transitionExit.excludeTarget(android.R.id.statusBarBackground, true);
	    
		getWindow().setEnterTransition(transitionEnter);
		getWindow().setExitTransition(transitionExit);
		getWindow().setAllowEnterTransitionOverlap(true);
		getWindow().setAllowReturnTransitionOverlap(true);
		
		_BaseCoordinatorLayout.setTransitionGroup(true);
	}
	
	private void requestUserInformationModification()
	{
		UserInformationSetAsync async = new UserInformationSetAsync(this, mModificationUserInformation, onRequestListener);
		async.execute();
	}
	
	private void requestUserPasswordModification()
	{
		UserPasswordChangeAsync async = new UserPasswordChangeAsync(this, mModificationUserPassword, onRequestListener);
		async.execute();
	}
	
	private void settingInformationModification(int type)
	{
		((UserInformationConvertFragment)mUserInformationFragmentList.get(USER_INFORMATION_MODIFICATION)).setConvertType(type);
		_ViewPager.setCurrentItem(USER_INFORMATION_MODIFICATION, true);
	}
	
	private void showFailModificationMessage(String message)
	{
		CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, message, getResources().getColor(R.color.color_d8232a));
	}
	
	private void setResultModification(String type, Object object)
	{
		((UserInformationConvertFragment)mUserInformationFragmentList.get(USER_INFORMATION_MODIFICATION)).setModificationComplete();
		if(((BaseResult)object).getResult().equals(BaseResult.RESULT_OK))
		{
			if(type.equals(Common.ASYNC_CODE_USER_INFORMATION_SET))
			{
				UserTotalInformationObject totalUserInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
				totalUserInformationObject.setUserBaseInformation(mModificationUserInformation);
				CommonUtils.getInstance(this).setPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, totalUserInformationObject);
				((UserInformationShowFragment)mUserInformationFragmentList.get(USER_INFORMATION_SHOW)).settingViewInformation();
				CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_user_modification_success), getResources().getColor(R.color.color_00b980));
				
				mMainHandler.sendEmptyMessageDelayed(MESSAGE_MODIFICATION_COMPLETE, DURATION_SHOW_NOTIFICATION);
			}
			else if(type.equals(Common.ASYNC_CODE_USER_PASSWORD_CHANGE))
			{
				UserLoginObject loginObject = (UserLoginObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_LOGIN, UserLoginObject.class);
				loginObject.setUserPassword(mModificationUserPassword);
				CommonUtils.getInstance(this).setPreferenceObject(Common.PARAMS_USER_LOGIN, loginObject);
				CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_user_password_change_success), getResources().getColor(R.color.color_00b980));
				
				mMainHandler.sendEmptyMessageDelayed(MESSAGE_MODIFICATION_COMPLETE, DURATION_SHOW_NOTIFICATION);
			}
		}
		else
		{
			if(((BaseResult)object).isAuthenticationBroken())
			{
				finish();
				MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE , true);
			}
			else
			{
				showFailModificationMessage(((BaseResult)object).getMessage());
			}
			
		}
		
		
		
	}
	
	@Override
	public void onBackPressed() 
	{
		if(_ViewPager.getCurrentItem() == USER_INFORMATION_SHOW)
		{
			setResult(RESULT_OK);
			super.onBackPressed();
		}
		else if(_ViewPager.getCurrentItem() == USER_INFORMATION_MODIFICATION)
		{
			_ViewPager.setCurrentItem(USER_INFORMATION_SHOW, true);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_USER_INFO);
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

	
	@OnClick(R.id.myinfo_close)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.myinfo_close:
			super.onBackPressed();
			break;
		}
	}
	
	private class UserInformationPagerAdapter extends FragmentPagerAdapter
	{

		public UserInformationPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mUserInformationFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int type)
		{
			Fragment fragment = null;
			
			switch(type)
			{
			case USER_INFORMATION_SHOW:
				fragment = UserInformationShowFragment.getInstance();
				((UserInformationHolder)fragment).setOnUserInformationListener(onUserInfomationListener);
				break;
			case USER_INFORMATION_MODIFICATION:
				fragment = UserInformationConvertFragment.getInstance();
				((UserInformationHolder)fragment).setOnUserInformationListener(onUserInfomationListener);
				break;
			}
			
			mUserInformationFragmentList.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position)
		{
			return mUserInformationFragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return mUserInformationFragmentList.size();
		}
		
	}
	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener()
	{
		
		@Override
		public void onPageSelected(int position)
		{
			if(position == USER_INFORMATION_SHOW)
			{
				_CloseButton.setVisibility(View.VISIBLE);
			}
			else if(position == USER_INFORMATION_MODIFICATION)
			{
				_CloseButton.setVisibility(View.INVISIBLE);
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int position)
		{
		}
	};
	
	private OnUserInformationListener onUserInfomationListener = new OnUserInformationListener()
	{
		@Override
		public void requestModification(int type, Object object)
		{
			if(type == UserInformationHolder.ACTION_INFORMATION_CHANGE)
			{
				mModificationUserInformation = (UserBaseInformationObject) object;
				if(NetworkUtil.isConnectNetwork(UserInformationModificationActivity.this))
				{
					mMainHandler.sendEmptyMessage(MESSAGE_USER_INFORMATION_CHANGE);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
			}
			else if(type == UserInformationHolder.ACTION_PASSWORD_CHANGE)
			{
				mModificationUserPassword = (String) object;
				if(NetworkUtil.isConnectNetwork(UserInformationModificationActivity.this))
				{
					mMainHandler.sendEmptyMessage(MESSAGE_PASSWORD_CHANGE);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
			}
		}
		
		@Override
		public void cancelModification()
		{
			_ViewPager.setCurrentItem(USER_INFORMATION_SHOW, true);
		}
		
		@Override
		public void executeAction(int type)
		{
			if(type == UserInformationHolder.ACTION_PAYMENT)
			{
				mMainHandler.sendEmptyMessageDelayed(MESSAGE_FINISH, DURATION_ANIMATION);				
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_PAY_PAGE);
			}
			else
			{
				settingInformationModification(type);
			}
		}

		@Override
		public void showMessage(String text, int color)
		{
			CommonUtils.getInstance(UserInformationModificationActivity.this).showSnackMessage(_BaseCoordinatorLayout, text, color);
		}
	};
	
	private AsyncListener onRequestListener = new AsyncListener()
	{
		@Override
		public void onRunningStart(String code) { }

		@Override
		public void onRunningCanceled(String code) { }

		@Override
		public void onRunningProgress(String code, Integer progress) { }

		@Override
		public void onRunningAdvanceInformation(String code, Object object) { }

		@Override
		public void onErrorListener(String code, String message){}
		
		@Override
		public void onRunningEnd(String code, Object mObject)
		{
			setResultModification(code, mObject);
		}

	};
}

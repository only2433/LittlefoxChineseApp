package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.coroutines.UserSignCoroutine;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.SignInputInformationFragment;
import com.littlefox.chinese.edu.fragment.SignProvisionFragment;
import com.littlefox.chinese.edu.object.UserSignObject;
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

public class UserSignActivity extends BaseActivity
{
	@BindView(R.id.sign_base_coordinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.sign_menu_title)
	TextView _MainTitleText;
	
	@BindView(R.id.user_sign_close)
	ImageView _UserSignCloseButton;
	
	@BindView(R.id.user_sign_viewpager)
	SwipeDisableViewPager _ViewPager;
	
	@BindView(R.id.sign_progress_wheel_layout)
	ScalableLayout _ProgressWheelLayout;
	
	Handler mMainHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_INPUT_PAGE_ENTER:
				_ViewPager.setCurrentItem(TYPE_INPUT, true);
				break;
			case MESSAGE_VIEW_LOAD_START:
				init();
				break;
			case MESSAGE_FINISH:
				onBackPressed();
				break;
			case MESSAGE_USER_SIGN_CONFIRM:
				requestUserSignInformation(mUserSignObject);
				break;
			}
		}
	};
	
	public interface OnSignListener
	{
		void CheckSignProvision(boolean isAllCheck);
		void CheckUserSign(UserSignObject userSignObject);
		void ShowMessage(String text, int color);
		/**
		 * 원래 LoadingView는 Provision Fragment에 있엇으나 최신 기기 기종을 제외한 나머지 기기가 앱 애니메이션 이동 속도가 버벅여서 이동 후에 ViewPager 값을 세팅하기위해
		 */
        void onSignProvisionViewLoadComplete();
	}
	
	public interface UserSignCallback
	{
		void setOnSignListener(OnSignListener onSignListener);
		/**
		 * 서버통신으로 정보입력 정보를 보낸 후 통신이 끝난 후에 정보를 전달하기 위한 메소드
		 */
        void onUserSignCheckEnd();
	}
	
	private static final int MESSAGE_INPUT_PAGE_ENTER 		= 0;
	private static final int MESSAGE_VIEW_LOAD_START 		= 1;
	private static final int MESSAGE_FINISH						= 2;
	private static final int MESSAGE_USER_SIGN_CONFIRM		= 3;
	
	private static final int DURATION_USER_SIGN_COMPLETE = 1500;
	private static final int DURATION_VIEW_LOAD_START = 1000;
	private static final int DURATION_ANIMATION = 300;
	
	private static final int TYPE_SIGN 	= 0;
	private static final int TYPE_INPUT 	= 1;
	
	private ArrayList<Fragment> mUserSignFragmentList;
	private UserSignPagerAdapter mUserSignPagerAdapter;
	private FixedSpeedScroller mFixedSpeedScroller;
	private UserSignObject mUserSignObject = null;
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);

		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.user_sign_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.user_sign_main);
		}
		ButterKnife.bind(this);
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		initFont();
		initText();
		Log.f("");
		mMainHandler.sendEmptyMessageDelayed(MESSAGE_VIEW_LOAD_START, DURATION_VIEW_LOAD_START);
		_ProgressWheelLayout.setVisibility(View.VISIBLE);
	}
	
	private void init()
	{
		mUserSignPagerAdapter = new UserSignPagerAdapter(getSupportFragmentManager());
		mUserSignPagerAdapter.addFragment(TYPE_SIGN);
		mUserSignPagerAdapter.addFragment(TYPE_INPUT);
		_ViewPager.setAdapter(mUserSignPagerAdapter);
		
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
		_MainTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		_MainTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_user_sign));
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
	
	private void requestUserSignInformation(UserSignObject usersignObject)
	{
		//UserSignAsync userSignAsync = new UserSignAsync(this, usersignObject, mOnAsyncListener);
		//userSignAsync.execute();
		UserSignCoroutine coroutine = new UserSignCoroutine(this, mOnAsyncListener);
		coroutine.setData(usersignObject);
		coroutine.execute();
	}
	
	private void showCheckMessage(BaseResult result)
	{
		if(result.getResult().toLowerCase().equals(BaseResult.RESULT_OK))
		{
			Log.f("회원 가입이 성공 하였습니다.");
			GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_USERSIGN, Common.ANALYTICS_ACTION_USER_SIGN);
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_success_user_sign), getResources().getColor(R.color.color_00b980));
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_FINISH, DURATION_USER_SIGN_COMPLETE);
		}
		else
		{
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, result.getMessage(), getResources().getColor(R.color.color_d8232a));
		}
	}
	
	public void onBackPressed() 
	{
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_USER_SIGN);
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
	
	@Override
	public void finish()
	{
		super.finish();

		if(MainSystemFactory.getInstance().getAnimationTransitionMode() == MainSystemFactory.ANIMATION_TRANSITION)
		{
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		}
		
	}

	@OnClick(R.id.user_sign_close)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.user_sign_close:
			super.onBackPressed();
			break;
		}
	}
	
	private class UserSignPagerAdapter extends FragmentPagerAdapter
	{

		public UserSignPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mUserSignFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int type)
		{
			
			Fragment fragment = null;
			switch(type)
			{
			case TYPE_SIGN:
				fragment = SignProvisionFragment.getInstance(); 
				((UserSignCallback)fragment).setOnSignListener(mOnSignListener);
				mUserSignFragmentList.add(fragment);
				break;
			case TYPE_INPUT:
				fragment = SignInputInformationFragment.getInstance(); 
				((UserSignCallback)fragment).setOnSignListener(mOnSignListener);
				mUserSignFragmentList.add(fragment);
				break;
			}
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position)
		{
			return mUserSignFragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return mUserSignFragmentList.size();
		}
		
	}
	
	private OnSignListener mOnSignListener = new OnSignListener()
	{

		@Override
		public void CheckSignProvision(boolean isAllCheck)
		{
			if(isAllCheck)
			{
				mMainHandler.sendEmptyMessage(MESSAGE_INPUT_PAGE_ENTER);
			}
			else
			{
				CommonUtils.getInstance(UserSignActivity.this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(UserSignActivity.this).getLanguageTypeString(R.array.message_provision_warning), getResources().getColor(R.color.color_ffffff));
			}
			
		}

		@Override
		public void CheckUserSign(UserSignObject userSignObject)
		{
			mUserSignObject = userSignObject;
			
			if(NetworkUtil.isConnectNetwork(UserSignActivity.this))
			{
				requestUserSignInformation(mUserSignObject);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_USER_SIGN);
			}
			
		}

		@Override
		public void ShowMessage(String text, int color)
		{
			CommonUtils.getInstance(UserSignActivity.this).showSnackMessage(_BaseCoordinatorLayout, text, color);
		}

		@Override
		public void onSignProvisionViewLoadComplete()
		{
			try
			{
				_ProgressWheelLayout.setVisibility(View.GONE);
			}catch(Exception e)
			{
				
			}
			
		}

	};
	
	private AsyncListener mOnAsyncListener = new AsyncListener()
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
			((UserSignCallback)mUserSignFragmentList.get(TYPE_INPUT)).onUserSignCheckEnd();
			showCheckMessage((BaseResult) mObject);
		}

	};
	
}

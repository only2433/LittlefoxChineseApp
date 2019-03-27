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

import com.littlefox.chinese.edu.async.AddChildRequestAddAsync;
import com.littlefox.chinese.edu.async.AddChildRequestDeleteAsync;
import com.littlefox.chinese.edu.async.AddChildRequestModificationAsync;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.dialog.TempleteAlertDialog;
import com.littlefox.chinese.edu.dialog.listener.DialogListener;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.AddChildInformationConvertFragment;
import com.littlefox.chinese.edu.fragment.AddChildInformationShowFragment;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
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

public class AddChildInformationModificationActivity extends BaseActivity
{
	@BindView(R.id.addchild_base_coordinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.addchild_menu_title)
	TextView _TitleText;
	
	@BindView(R.id.addchild_close)
	ImageView _CloseButton;
	
	@BindView(R.id.addchild_viewpager)
	SwipeDisableViewPager _ViewPager;
	
	@BindView(R.id.addchild_progress_wheel_layout)
	ScalableLayout _ProgressLayout;
	
	private Handler mMainHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_VIEW_INIT:
				_ProgressLayout.setVisibility(View.GONE);
				init();
				break;
			case MESSAGE_MODIFICATION_COMPLETE:
				_ViewPager.setCurrentItem(ADDCHILD_INFORMATION_SHOW, true);
				break;
			}
		}
		
	};
	
	public interface AddChildHolder
	{
		int ACTION_ADD 					= 0;
		int ACTION_DELETE 				= 1;
		int ACTION_MODIFICATION 	= 2;
		void setOnAddChildListener(OnAddChildListener onAddChildListener);
	}
	
	public interface OnAddChildListener
	{
		/**
		 * 추가 사용자 추가, 삭제, 수정에 관한 액션상태를 전달하며, 해당 액션을 수행하는 추가사용자의 인덱스도 같이 전달한다.
		 * @param type 추가 사용자 액션 타입
		 * @param index 추사 사용자 인덱스
		 */
        void executeAction(int type, int index);
		void requestServer(UserBaseInformationObject object);
		void showMessage(String message, int color);
		void cancelModification();
	}
	
	/**추가 사용자 삭제에 관련된 팝업 */
	public static final int DIALOG_EVENT_ADDCHILD_DELETE										= 0x00;
	
	private static final int MESSAGE_VIEW_INIT 							= 0;
	private static final int MESSAGE_MODIFICATION_COMPLETE 	= 1;

	
	private static final int DURATION_ANIMATION 			= 300;
	private static final int DURATION_SHOW_NOTIFICATION 	= 2000;
	private static final int DURATION_VIEW_LOAD_START 		= 1000;
	
	private static final int ADDCHILD_INFORMATION_SHOW 				= 0;
	private static final int ADDCHILD_INFORMATION_MODIFICATION 	= 1;
	
	private AddChildInformationPagerAdapter mAddChildInformationPagerAdapter;
	private ArrayList<Fragment> mAddChildFragmentList;
	private int mCurrentRequestType = AddChildHolder.ACTION_ADD;
	private int mCurrentRequestChildIndex = -1;
	private UserBaseInformationObject mCurrentRequestChildInformationObject = null;
	private FixedSpeedScroller mFixedSpeedScroller;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.addchild_manage_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.addchild_manage_main);
		}
		
		ButterKnife.bind(this);
		Log.f("");
		_TitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
		/**
		 * 현재 사용자를 추가사용자로 변경해서 추가사용자 관리에 들어가면 추가사용자는 자신의 내용만 변경만 가능하다. 해당 추가사용자의 정보만 보여주고 수정만 가능하게 , 그래서 타이틀도 나의 정보로 변경
		 */
		UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		if(userTotalInformationObject.isBaseUserUse())
		{
			_TitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_add_user_manage));
		}
		else
		{
			_TitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_my_info));
		}
		
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		
		
		_ProgressLayout.setVisibility(View.VISIBLE);
		mMainHandler.sendEmptyMessageDelayed(MESSAGE_VIEW_INIT, DURATION_VIEW_LOAD_START);
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
	
	private void init()
	{
		mAddChildInformationPagerAdapter = new AddChildInformationPagerAdapter(getSupportFragmentManager());
		mAddChildInformationPagerAdapter.addFragment(ADDCHILD_INFORMATION_SHOW);
		mAddChildInformationPagerAdapter.addFragment(ADDCHILD_INFORMATION_MODIFICATION);
		_ViewPager.setAdapter(mAddChildInformationPagerAdapter);
		_ViewPager.addOnPageChangeListener(mOnPageChangeListener);
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
	
	@Override
	public void onBackPressed() 
	{
		if(_ViewPager.getCurrentItem() == ADDCHILD_INFORMATION_SHOW)
		{
			setResult(RESULT_OK);
			super.onBackPressed();
		}
		else if(_ViewPager.getCurrentItem() == ADDCHILD_INFORMATION_MODIFICATION)
		{
			_ViewPager.setCurrentItem(ADDCHILD_INFORMATION_SHOW, true);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_ADD_USER_INFO);
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
	
	@OnClick(R.id.addchild_close)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.addchild_close:
			super.onBackPressed();
			break;
		}
	}
	
	private void requestChildAddAsync()
	{
		AddChildRequestAddAsync async = new AddChildRequestAddAsync(this, mCurrentRequestChildInformationObject, mAsyncListener);
		async.execute();
	}
	
	private void requestChildModificationAsync()
	{
		UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		AddChildRequestModificationAsync async = new AddChildRequestModificationAsync(this, 
				userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex), 
				mCurrentRequestChildInformationObject, 
				mAsyncListener);
		async.execute();
	}
	
	private void requestChildDeleteAsync(UserBaseInformationObject requestDeleteUserObject)
	{
		AddChildRequestDeleteAsync async  = new AddChildRequestDeleteAsync(this, requestDeleteUserObject, mAsyncListener);
		async.execute();
	}
	
	private void showFailModificationMessage(String message)
	{
		CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, message, getResources().getColor(R.color.color_d8232a));
	} 
	
    private void showTempleteAlertDialog(int type, int buttonType, String message)
    {
    	TempleteAlertDialog dialog = new TempleteAlertDialog(this, message);
    	dialog.setDialogMessageSubType(type);
    	dialog.setButtonText(buttonType);
    	dialog.setDialogListener(mOnDialogListener);
    	dialog.show();
    }
	
	private void setSuccessResult()
	{
		((AddChildInformationConvertFragment)mAddChildFragmentList.get(ADDCHILD_INFORMATION_MODIFICATION)).setModificationComplete();
		UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		switch(mCurrentRequestType)
		{
		case AddChildHolder.ACTION_ADD:
			Log.f("ADD Success : " + mCurrentRequestChildInformationObject.getNickname());
			userTotalInformationObject.addUserChildObject(mCurrentRequestChildInformationObject);
			
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_addchild_add_success), getResources().getColor(R.color.color_00b980));
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_MODIFICATION_COMPLETE, DURATION_SHOW_NOTIFICATION);
			break;
		case AddChildHolder.ACTION_MODIFICATION:
			Log.f("MODIFICATION Success : " + mCurrentRequestChildInformationObject.getNickname());
			userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex).setName(mCurrentRequestChildInformationObject.getName());
			userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex).setNickname(mCurrentRequestChildInformationObject.getNickname());
			userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex).setBirthday(mCurrentRequestChildInformationObject.getBirthday());
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_addchild_modification_success), getResources().getColor(R.color.color_00b980));
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_MODIFICATION_COMPLETE, DURATION_SHOW_NOTIFICATION);
			break;
		case AddChildHolder.ACTION_DELETE:
			Log.f("DELETE Success : " + userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex).getNickname());
			userTotalInformationObject.removeUserChildObject(mCurrentRequestChildIndex);
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_addchild_delete_success), getResources().getColor(R.color.color_00b980));
			break;
		}
		CommonUtils.getInstance(this).setPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, userTotalInformationObject);
		((AddChildInformationShowFragment)mAddChildFragmentList.get(ADDCHILD_INFORMATION_SHOW)).nofityAddChildLayout();
	}
	
	private class AddChildInformationPagerAdapter extends FragmentPagerAdapter
	{

		public AddChildInformationPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mAddChildFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int type)
		{
			Fragment fragment = null;
			
			switch(type)
			{
			case ADDCHILD_INFORMATION_SHOW:
				fragment = AddChildInformationShowFragment.getInstance();
				((AddChildHolder)fragment).setOnAddChildListener(mOnAddChildListener);
				break;
			case ADDCHILD_INFORMATION_MODIFICATION:
				fragment = AddChildInformationConvertFragment.getInstance();
				((AddChildHolder)fragment).setOnAddChildListener(mOnAddChildListener);
				break;
			}
			mAddChildFragmentList.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position)
		{
			return mAddChildFragmentList.get(position);
		}

		@Override
		public int getCount()
		{			
			return mAddChildFragmentList.size();
		}
		
	}
	
	private DialogListener mOnDialogListener = new DialogListener()
	{
		@Override
		public void onItemClick(int messageType, Object sendObject){}

		@Override
		public void onItemClick(int messageButtonType, int messageType, Object sendObject)
		{
			switch(messageType)
			{
			case DIALOG_EVENT_ADDCHILD_DELETE:
				if(messageButtonType == FIRST_BUTTON_CLICK)
				{
					
				}
				else if(messageButtonType == SECOND_BUTTON_CLICK)
				{
					UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(AddChildInformationModificationActivity.this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
					if(NetworkUtil.isConnectNetwork(AddChildInformationModificationActivity.this))
					{
						requestChildDeleteAsync(userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex));
					}
					else
					{
						MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
					}
				}
				break;
			}
		}
		
	};
	
	private OnAddChildListener mOnAddChildListener = new OnAddChildListener()
	{
		
		@Override
		public void showMessage(String message, int color)
		{
			CommonUtils.getInstance(AddChildInformationModificationActivity.this).showSnackMessage(_BaseCoordinatorLayout, message, color);
		}
		
		@Override
		public void requestServer(UserBaseInformationObject object)
		{
			mCurrentRequestChildInformationObject = object;
			
			switch(mCurrentRequestType)
			{
			case AddChildHolder.ACTION_ADD:
				if(NetworkUtil.isConnectNetwork(AddChildInformationModificationActivity.this))
				{
					requestChildAddAsync();
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
				break;
			case AddChildHolder.ACTION_MODIFICATION:
				if(NetworkUtil.isConnectNetwork(AddChildInformationModificationActivity.this))
				{
					requestChildModificationAsync();
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
				break;
			}
		}
		
		@Override
		public void executeAction(int type, int index)
		{
			mCurrentRequestType = type;
			mCurrentRequestChildIndex = index;
			UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(AddChildInformationModificationActivity.this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
			switch(mCurrentRequestType)
			{
			case AddChildHolder.ACTION_ADD:
				((AddChildInformationConvertFragment)mAddChildFragmentList.get(ADDCHILD_INFORMATION_MODIFICATION)).addChildUser();
				_ViewPager.setCurrentItem(ADDCHILD_INFORMATION_MODIFICATION, true);
				break;
			case AddChildHolder.ACTION_MODIFICATION:
				((AddChildInformationConvertFragment)mAddChildFragmentList.get(ADDCHILD_INFORMATION_MODIFICATION))
				.convertCurrentUser(userTotalInformationObject.getChildInformationList().get(mCurrentRequestChildIndex));
				_ViewPager.setCurrentItem(ADDCHILD_INFORMATION_MODIFICATION, true);
				break;
			case AddChildHolder.ACTION_DELETE:
				showTempleteAlertDialog(DIALOG_EVENT_ADDCHILD_DELETE, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_2, CommonUtils.getInstance(AddChildInformationModificationActivity.this).getLanguageTypeString(R.array.message_child_delete));
				break;
			}
		}

		@Override
		public void cancelModification()
		{
			_ViewPager.setCurrentItem(ADDCHILD_INFORMATION_SHOW, true);
		}
	};
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener()
	{
		
		@Override
		public void onPageSelected(int position)
		{
			if(position == ADDCHILD_INFORMATION_SHOW)
			{
				Log.f("ADD_CHILD_INFORMATION_SHOW VIEW");
				_CloseButton.setVisibility(View.VISIBLE);
			}
			else if(position == ADDCHILD_INFORMATION_MODIFICATION)
			{
				Log.f("ADD_CHILD_INFORMATION_MODIFICATION VIEW");
				_CloseButton.setVisibility(View.INVISIBLE);
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0)
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	private AsyncListener mAsyncListener = new AsyncListener()
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
			if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
			{
				setSuccessResult();
			}
			else
			{
				if(((BaseResult)mObject).isAuthenticationBroken())
				{
					finish();
					MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE,true);
				}
				else
				{
					((AddChildInformationConvertFragment)mAddChildFragmentList.get(ADDCHILD_INFORMATION_MODIFICATION)).setModificationComplete();
					showFailModificationMessage(((BaseResult)mObject).getMessage());
				}

			}
		}


	};
}

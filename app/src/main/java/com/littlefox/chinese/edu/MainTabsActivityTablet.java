package com.littlefox.chinese.edu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.async.ChangeUserRequestAsync;
import com.littlefox.chinese.edu.billing.InAppPurchase;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.FileUtils;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.database.PlayedContentDBHelper;
import com.littlefox.chinese.edu.dialog.TempleteAlertDialog;
import com.littlefox.chinese.edu.dialog.UserSelectDialog;
import com.littlefox.chinese.edu.dialog.UserSelectDialog.OnSelectDialogListener;
import com.littlefox.chinese.edu.dialog.listener.DialogListener;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.MainSubHomeFragmentTablet;
import com.littlefox.chinese.edu.fragment.MainSubSongCategoryFragment;
import com.littlefox.chinese.edu.fragment.MainSubStoryFragment;
import com.littlefox.chinese.edu.fragment.MainSubStudyDataFragment;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.IACController;
import com.littlefox.chinese.edu.object.IACController.NeverWatchIACInformation;
import com.littlefox.chinese.edu.object.InAppInformation;
import com.littlefox.chinese.edu.object.PlayedContentInformation;
import com.littlefox.chinese.edu.object.SongCategoryObject;
import com.littlefox.chinese.edu.object.UserSelectInformation;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.object.result.InitAppResult;
import com.littlefox.chinese.edu.object.result.MainInformationResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.receiver.GooglePayCheckReceiver;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.scroller.FixedSpeedScroller;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTabsActivityTablet extends BaseActivity
{
	@BindView(R.id.main_draw_layout)
	android.support.v4.widget.DrawerLayout _MainBaseLayout;
	
	@BindView(R.id.main_content)
	android.support.design.widget.CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.main_tabs_base_layout)
	ScalableLayout _MainBaseTopBarLayout;
	
	@BindView(R.id.main_fragment_viewpager)
	ViewPager _ViewPager;
	
	@BindView(R.id.main_fragment_tablayout)
	TabLayout _TabLayout;
	
	@BindView(R.id.top_menu_title)
	TextView _MainTitleText;
	
	@BindView(R.id.top_menu_setting)
	ImageView _SettingButton;
	
	@BindView(R.id.navigation_base_layout)
	ScalableLayout _NavigationLayout;
	
	@BindView(R.id.navi_my_study_record_layout)
	ScalableLayout _NaviStudyRecordLayout;
	
	@BindView(R.id.navi_my_info_layout)
	ScalableLayout _NaviUserInfoLayout;
	
	@BindView(R.id.navi_add_user_manage_layout)
	ScalableLayout _NaviAddChildLayout;
	
	@BindView(R.id.navi_question_layout)
	ScalableLayout _NaviQuestionLayout;
	
	@BindView(R.id.navi_pay_layout)
	ScalableLayout _NaviPayLayout;
	
	@BindView(R.id.navi_content_present_layout)
	ScalableLayout _ContentPresentLayout;

	@BindView(R.id.navi_logout_layout)
	ScalableLayout _NaviLogoutLayout;
	
	@BindViews({R.id.navi_content_present_layout,  R.id.navi_my_study_record_layout,R.id.navi_my_info_layout,  R.id.navi_add_user_manage_layout, R.id.navi_question_layout, R.id.navi_pay_layout, R.id.navi_logout_layout})
	List<ScalableLayout> _NavigationViewUserList;
	
	@BindViews({R.id.navi_content_present_layout,  R.id.navi_my_study_record_layout,R.id.navi_my_info_layout, R.id.navi_question_layout, R.id.navi_pay_layout, R.id.navi_logout_layout})
	List<ScalableLayout> _NavigationViewAddChildList;
	
	@BindViews({R.id.navi_content_present_layout,  R.id.navi_my_study_record_layout,R.id.navi_my_info_layout,  R.id.navi_add_user_manage_layout, R.id.navi_question_layout, R.id.navi_logout_layout})
	List<ScalableLayout> _NavigationViewPaidUserList;
	
	@BindViews({R.id.navi_content_present_layout, R.id.navi_my_study_record_layout,R.id.navi_my_info_layout, R.id.navi_question_layout,  R.id.navi_logout_layout})
	List<ScalableLayout> _NavigationViewPaidAddChildList;
	
	@BindView(R.id.navi_user_base_layout)
	ScalableLayout _NaviLoginUserLayout;
	
	@BindView(R.id.navi_user_type_text)
	TextView _NaviLoginUserTypeText;
	
	@BindView(R.id.navi_user_name_text)
	TextView _NaviLoginUserNameText;
	
	@BindView(R.id.button_navi_logout)
	TextView _NaviLogoutButton;
	
	Handler mMainHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			Intent intent;
			switch(msg.what)
			{

			case MESSAGE_USER_SIGN_OPEN:
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_USER_SIGN);
				break;
			case MESSAGE_LOGOUT_MESSAGE:
				showSuccessMessage(CommonUtils.getInstance(MainTabsActivityTablet.this).getLanguageTypeString(R.array.message_logout_success));
				Message tempMsg = Message.obtain();
				tempMsg.what = MESSAGE_RESTART;
				tempMsg.obj = true;
				mMainHandler.sendMessageDelayed(tempMsg, DURATION_LOGOUT_ANIMATION);
				break;
			case MESSAGE_RESTART:
				finish();
				if((boolean) msg.obj == true)
				{
					MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE,  true);
				}
				else
				{
					MainSystemFactory.getInstance().resetSceneToLogin(MainSystemFactory.MODE_INTRO_LOADING,  true);
				}
				
				break;
			case MESSAGE_USER_INFO:
				UserTotalInformationObject userTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(MainTabsActivityTablet.this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
				if(userTotalInformationObject.isBaseUserUse())
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_USER_INFO,REQUEST_MODIFICATION_RENEW);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_ADD_USER_INFO,REQUEST_MODIFICATION_RENEW);
				}
				break;
			case MESSAGE_ADD_USER_INFO:
				MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_ADD_USER_INFO,REQUEST_MODIFICATION_RENEW);
				break;
			case MESSAGE_CHANGE_USER:
				_MainBaseLayout.closeDrawer(_NavigationLayout);
				requestChangeUser(msg.arg1);
				break;
			case MESSAGE_INQUIRE_DEVELOPER:
				if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
				{
					CommonUtils.getInstance(MainTabsActivityTablet.this).inquireForDeveloper();
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
				break;
			case MESSAGE_PAY_PAGE:
				MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_PAY_PAGE, REQUEST_PAY_COMPLETE);
				break;
			case MESSAGE_MOVE_STUDY_DATA:
				_TabLayout.getTabAt(PAGE_HOME).getCustomView().setSelected(false);
				_TabLayout.getTabAt(PAGE_STUDY_DATA).getCustomView().setSelected(true);
				_ViewPager.setCurrentItem(PAGE_STUDY_DATA, true);
				((MainSubStudyDataFragment)mFragmentList.get(PAGE_STUDY_DATA)).settingIntonationTab();
				break;
			case MESSAGE_STUDY_RECORD:
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_STUDY_RECORD);
				break;
			case MESSAGE_CONTENT_PRESENT:
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_CONTENT_PRESENT);
				break;
			case MESSAGE_DELETE_CONTENT_FILE:
				FileUtils.deleteAllFileInPath(Common.PATH_MP4_SAVE);
				break;
			}
		}
	};
	
	/** IAC 관련 다이얼로그 이벤트 Flag */
	public static final int DIALOG_EVENT_IAC															= 0x00;
	/**로그아웃 관련 체크 팝업 */
	public static final int DIALOG_EVENT_LOGOUT															= 0x01;


	private static final int MESSAGE_USER_SIGN_OPEN			= 1;
	private static final int MESSAGE_LOGOUT_MESSAGE			= 2;
	private static final int MESSAGE_RESTART				= 3;
	private static final int MESSAGE_USER_INFO				= 4;
	private static final int MESSAGE_ADD_USER_INFO			= 5;
	private static final int MESSAGE_CHANGE_USER			= 6;
	private static final int MESSAGE_INQUIRE_DEVELOPER		= 7;
	private static final int MESSAGE_PAY_PAGE				= 8;
	private static final int MESSAGE_MOVE_STUDY_DATA		= 9;
	private static final int MESSAGE_STUDY_RECORD			= 10;
	private static final int MESSAGE_CONTENT_PRESENT		= 11;
	private static final int MESSAGE_DELETE_CONTENT_FILE	= 12;
	
	private static final int PAGE_HOME 				= 0;
	private static final int PAGE_STORY 			= 1;
	private static final int PAGE_SONG				= 2;
	private static final int PAGE_STUDY_DATA 		= 3;
	
	private static final int TYPE_BASE_USER = 0; // 기본사용자
	private static final int TYPE_ADD_CHILD = 1; // 추가 사용자
	
	private static final int DURATION_NAVIGATION_ANIMATION = 300;
	private static final int DURATION_ANIMATION = 500;
	private static final int DURATION_LOGOUT_ANIMATION = 800;
	
	private static final int[] TAB_IMAGE_ICONS = { R.drawable.choice_top_bar_icon_home, R.drawable.choice_top_bar_icon_story, R.drawable.choice_top_bar_icon_song, R.drawable.choice_top_bar_icon_study_data};
	private static final int[] TITLE_STRINGS = {R.array.main_title_home, R.array.main_title_story, R.array.main_title_song, R.array.main_title_study_data};
	
	private static final int REQUEST_MODIFICATION_RENEW 	= 0;
	private static final int REQUEST_PAY_COMPLETE 			= 1;
	
	private  List<Fragment> mFragmentList;
	private MainSelectionPagerAdapter mMainSelectionPagerAdapter;
	private int mPagePosition = -1;
	
	private FragmentTransaction ft;
	private MainInformationResult mMainInformationResult = null;
	private IACController mIACController;
	private int mChangeIndex = -1;
	private InitAppResult mInitResult;
	private IACController mLocalIacController;
	private GooglePayCheckReceiver mGooglePayCheckReceiver;
	private FixedSpeedScroller mFixedSpeedScroller;
	private PlayedContentDBHelper mPlayedContentDBHelper;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_tabs_activity_tablet);
		ButterKnife.bind(this);
		
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

		Log.f("");
		initFont();
		initText();
		initInformation();
		_MainBaseLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		_MainBaseLayout.setFocusableInTouchMode(false);
		settingTabLayout();
		settingLoginUser();
		initIACInformation();
		_MainBaseLayout.closeDrawer(_NavigationLayout);
		Log.i("_MainBaseTopBarLayout size : " + _MainBaseTopBarLayout.getHeight() + " : visible :  " + _MainBaseTopBarLayout.getVisibility());
		
		initGooglepayCheckReceiver();
		_NavigationLayout.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return true;
			}
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		mPlayedContentDBHelper = PlayedContentDBHelper.getInstance(this);
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_MAIN);
		if(mGooglePayCheckReceiver.isReceiveEvent())
		{
			settingLoginUser();
			mGooglePayCheckReceiver.initReceiveEvent();
		}
		Log.f("");
		initTransition(false);
		
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

		mGooglePayCheckReceiver.unRegister(this);
		deleteAllContentVideoFile();
		makeAvailableStorageSizeToPlay();
		mPlayedContentDBHelper.release();
		Log.f("");
		Log.f("");
	}
	
	@Override
	public void onBackPressed() 
	{
		if(_MainBaseLayout.isDrawerOpen(_NavigationLayout))
		{
			_MainBaseLayout.closeDrawer(_NavigationLayout);
		}
		else
		{
			finish();
			PlayedContentDBHelper.getInstance(this).databaseClose();
		}
	}
	
	private void initFont()
	{
		_MainTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
		
		((TextView)findViewById(R.id.button_navi_logout)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_user_type_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_user_name_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_user_type_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_user_name_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_info_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_add_user_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_question)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_pay_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_study_record_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.navi_menu_content_present_text)).setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	

	
	private void initText()
	{
		_MainTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.main_title_home));
		
		((TextView)findViewById(R.id.navi_user_type_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_user_type));
		((TextView)findViewById(R.id.navi_menu_content_present_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_content_present));
		((TextView)findViewById(R.id.navi_menu_study_record_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_study_record));
		((TextView)findViewById(R.id.navi_menu_info_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_my_info));
		((TextView)findViewById(R.id.navi_menu_add_user_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_add_user_manage));
		((TextView)findViewById(R.id.navi_menu_question)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_question));
		((TextView)findViewById(R.id.navi_menu_pay_text)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.navi_pay));
		
		((TextView)findViewById(R.id.button_navi_logout)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_logout));
	}
	
	/**
	 * 플레이 하기 위한 최소 저장 공간을 만들고, 그 공간을 만들기위해 이전에 플레이 했던 파일 및 정보를 삭제한다. ( 플레이 하지 않는 순으로 삭제 )
	 */
	private void makeAvailableStorageSizeToPlay()
	{
		long availableStorageSize = 0L;
		ArrayList<PlayedContentInformation> playedList = mPlayedContentDBHelper.getPlayedContentList(PlayedContentDBHelper.KEY_RECENT_PLAY_TIME, PlayedContentDBHelper.ORDER_DESC);
		
		availableStorageSize = CommonUtils.getInstance(this).getAvailableStorageSize();
		if(availableStorageSize <= Common.MIN_PLAYED_STORAGE_SIZE)
		{
			if(playedList != null)
			{
				for(int i = 0 ; i < playedList.size(); i++)
				{
					mPlayedContentDBHelper.deletePlayedContent(playedList.get(i).getContentID());
				}
			}
			mMainHandler.sendEmptyMessage(MESSAGE_DELETE_CONTENT_FILE);
		}
	}
	

	private void initInformation()
	{
		String result = FileUtils.getStringFromFile(Common.PATH_APP_ROOT+Common.FILE_MAIN_INFO);
		mMainInformationResult = new Gson().fromJson(result, MainInformationResult.class);
	}
	
	private void initGooglepayCheckReceiver()
	{
		mGooglePayCheckReceiver = new GooglePayCheckReceiver();
		mGooglePayCheckReceiver.register(this);
	}
	
    private void initIACInformation()
    {
    	
    	mInitResult = getIntent().getParcelableExtra(Common.INTENT_INIT_APP_INFO);
		
		if(isVisibleIAC() == true)
		{
			showIACTempleteAlertDialog();
		}
    }
    
	private void showFailMessage(String message)
	{
		CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, message, getResources().getColor(R.color.color_d8232a));
	} 
	
	private void showSuccessMessage(String message)
	{
		CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, message, getResources().getColor(R.color.color_00b980));
	}
    
    private void showUserSelectDialog()
    {
    	UserTotalInformationObject userTotalInfo = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
    	ArrayList<UserSelectInformation> list = new ArrayList<UserSelectInformation>();
    	list.add(new UserSelectInformation(userTotalInfo.getBaseUserInformation().getNickname()));
    	Log.f("Base User : "+ userTotalInfo.getBaseUserInformation().getNickname());
    	for(int i = 0 ; i < userTotalInfo.getChildInformationList().size(); i++)
    	{
    		Log.f("Add User : "+ userTotalInfo.getChildInformationList().get(i).getNickname());
    		list.add(new UserSelectInformation(userTotalInfo.getChildInformationList().get(i).getNickname()));
    	}
    	
    	UserSelectDialog dialog = new UserSelectDialog(this, list);
    	dialog.setOnSelectDialogListener(mOnSelectUserDialogListener);
    	dialog.show();
    }
    
    private void showTempleteAlertDialog(int type, int buttonType, String message)
    {
    	TempleteAlertDialog dialog = new TempleteAlertDialog(this, message);
    	dialog.setDialogMessageSubType(type);
    	dialog.setButtonText(buttonType);
    	dialog.setDialogListener(mDialogListener);
    	dialog.show();
    }

    
    private void showIACTempleteAlertDialog()
    {
    	TempleteAlertDialog dialog = new TempleteAlertDialog(MainTabsActivityTablet.this, mInitResult.getIACInformationList().get(0).iac_title, mInitResult.getIACInformationList().get(0).iac_content);
    	dialog.setDialogMessageSubType(DIALOG_EVENT_IAC);
    	dialog.setIconResource(R.drawable.ic_launcher);
    	if(mInitResult.getIACInformationList().get(0).btn2_useyn.equals("N"))
    	{
    		dialog.setButtonText(mInitResult.getIACInformationList().get(0).btn1_text);
    	}
    	else
    	{
    		dialog.setButtonText(mInitResult.getIACInformationList().get(0).btn1_text, mInitResult.getIACInformationList().get(0).btn2_text);
    	}
    	dialog.setDialogListener(mDialogListener);
    	dialog.show();
    }
    
    
    /**
     * IAC가 있을 경우 시작시 화면에 표시
     * @return TRUE : IAC 정보가 있음</p>FALSE : IAC 정보가 없거나 보여주지않아도 되는 상황
     */
    private boolean isVisibleIAC()
    {
    	boolean result = false;
    	NeverWatchIACInformation object = null;
    	try
    	{
    		if(mInitResult != null)
        	{
    			mLocalIacController = (IACController) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_IAC_AWAKE_INFO, IACController.class);
    			if(mLocalIacController == null)
    			{
    				Log.i("");
    				mLocalIacController = new IACController();
    			}
    			
            	if(mInitResult.getIACInformationList().get(0).btn2_useyn.equals("Y"))
            	{
            		Log.i("mInitResult.getIACInformationList().get(0).btn2_mode : "+mInitResult.getIACInformationList().get(0).btn2_mode);
            		if(mInitResult.getIACInformationList().get(0).btn2_mode.equals(Common.IAC_AWAKE_CODE_ALWAYS_VISIBLE))
            		{
            			object = mLocalIacController.new NeverWatchIACInformation(mInitResult.getIACId(), System.currentTimeMillis(), Common.IAC_AWAKE_CODE_ALWAYS_VISIBLE);
            		}
            		else if(mInitResult.getIACInformationList().get(0).btn2_mode.equals(Common.IAC_AWAKE_CODE_SPECIAL_DATE_VISIBLE))
            		{
            			object = mLocalIacController.new NeverWatchIACInformation(mInitResult.getIACId(), System.currentTimeMillis(), Common.IAC_AWAKE_CODE_SPECIAL_DATE_VISIBLE, mInitResult.getIACInformationList().get(0).lating_date);
            		}
            		else if(mInitResult.getIACInformationList().get(0).btn2_mode.equals(Common.IAC_AWAKE_CODE_ONCE_VISIBLE))
            		{
            			object = mLocalIacController.new NeverWatchIACInformation(mInitResult.getIACId(), System.currentTimeMillis(), Common.IAC_AWAKE_CODE_ONCE_VISIBLE);
            		}
            	}
            	else
            	{
            		object = mLocalIacController.new NeverWatchIACInformation(mInitResult.getIACId(), System.currentTimeMillis(), Common.IAC_AWAKE_CODE_ONCE_VISIBLE);
            	}
            	
            	result =  mLocalIacController.isAwake(object);

        	}
    	}catch(NullPointerException e)
    	{
    		return false;
    	}
    	
    	
    	return result;
    }
    

    
    @Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
    	super.onWindowFocusChanged(hasFocus);
	}
    
    private void initTransition(boolean useExpolde)
    {
    	if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
    	{
    		if(useExpolde)
    		{
    			Explode explode = new Explode();
    			explode.setDuration(DURATION_NAVIGATION_ANIMATION);
    			explode.excludeTarget(android.R.id.statusBarBackground, true);
    			getWindow().setExitTransition(explode);
    			getWindow().setAllowEnterTransitionOverlap(true);
    			getWindow().setAllowReturnTransitionOverlap(true);
    		}
    		else
    		{
    			getWindow().setExitTransition(null);
    		}
    		
    	}
		
    }

	
	@OnClick({R.id.navi_content_present_layout, R.id.navi_my_study_record_layout, R.id.top_menu_setting, R.id.navi_my_info_layout,R.id.navi_add_user_manage_layout, R.id.button_navi_user_select, R.id.navi_question_layout, R.id.button_navi_logout, R.id.navi_pay_layout})
	public void onSettingClick(View view)
	{
		switch(view.getId())
		{
		case R.id.navi_content_present_layout:
			Log.f("Select Content Present");
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_CONTENT_PRESENT, DURATION_NAVIGATION_ANIMATION);
			break;
		case R.id.navi_my_study_record_layout:
			Log.f("Select Study Record");
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_STUDY_RECORD, DURATION_NAVIGATION_ANIMATION);
			break;
		case R.id.top_menu_setting:
			_MainBaseLayout.openDrawer(_NavigationLayout);
			break;
		case R.id.navi_my_info_layout:
			Log.f("Select My Information");
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_USER_INFO, DURATION_NAVIGATION_ANIMATION);
			break;
		case R.id.navi_add_user_manage_layout:
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_ADD_USER_INFO, DURATION_NAVIGATION_ANIMATION);
			break;
		case R.id.button_navi_user_select:
			Log.f("Show User Select Dialog");
			showUserSelectDialog();
			break;
		case R.id.navi_question_layout:
			Log.f("Show Question Request");
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_INQUIRE_DEVELOPER, DURATION_NAVIGATION_ANIMATION);
			break;
		case R.id.button_navi_logout:
			Log.f("Show Logout Dialog");
			showTempleteAlertDialog(DIALOG_EVENT_LOGOUT, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_2, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_logout));
			break;
		case R.id.navi_pay_layout:
			Log.f("Select Payment Page");
			_MainBaseLayout.closeDrawer(_NavigationLayout);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_PAY_PAGE, DURATION_NAVIGATION_ANIMATION);
			break;
		}
	}
    
	private void settingTabLayout()
	{
		mMainSelectionPagerAdapter = new MainSelectionPagerAdapter(getSupportFragmentManager());
		mMainSelectionPagerAdapter.addFragment(PAGE_HOME);
		mMainSelectionPagerAdapter.addFragment(PAGE_STORY);
		mMainSelectionPagerAdapter.addFragment(PAGE_SONG);
		mMainSelectionPagerAdapter.addFragment(PAGE_STUDY_DATA);
		_ViewPager.setAdapter(mMainSelectionPagerAdapter);
		_ViewPager.setOffscreenPageLimit(4);
		
		_ViewPager.addOnPageChangeListener(mOnPageChangeListener);
		
		
		_TabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		_TabLayout.setupWithViewPager(_ViewPager);
		

		for(int i = 0 ; i < mMainSelectionPagerAdapter.getCount(); i++)
		{
			ImageView image = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtils.getInstance(this).getPixel(50), CommonUtils.getInstance(this).getHeightPixel(50));
			image.setLayoutParams(params);
			image.setImageResource(TAB_IMAGE_ICONS[i]);
			_TabLayout.getTabAt(i).setCustomView(image);
		}

		_TabLayout.getTabAt(PAGE_HOME).getCustomView().setSelected(true);
	}
	
	   private void settingLoginUser()
	    {
	    	UserTotalInformationObject userTotalInfo = (UserTotalInformationObject) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
	    	
	    	if(userTotalInfo != null)
	    	{
	    		_NaviLoginUserLayout.setVisibility(View.VISIBLE);   
	    		_NaviLogoutLayout.setVisibility(View.VISIBLE);
	    		
	    		if(userTotalInfo.isBaseUserUse())
	        	{
	    			Log.f("Choice User : "+ userTotalInfo.getBaseUserInformation().getNickname());
	        		_NaviLoginUserTypeText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_base_user));
	        		_NaviLoginUserNameText.setText(userTotalInfo.getBaseUserInformation().getNickname());
	        		settingNavigationLayout(TYPE_BASE_USER);
	        	}
	        	else
	        	{
	        		Log.f("Choice User : "+ userTotalInfo.getCurrentUserNickname());
	        		_NaviLoginUserTypeText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_add_user));
	        		_NaviLoginUserNameText.setText(userTotalInfo.getCurrentUserNickname());
	        		settingNavigationLayout(TYPE_ADD_CHILD);
	        	}
	    	}
	    	else
	    	{
	    		//TODO : 앱을 종료 시켜야한다.
	    	}
	    }
	   
		/**
		 * 저장되어 있는 모든 파일을 삭제한다.
		 */
		private void deleteAllContentVideoFile()
		{
			ArrayList<PlayedContentInformation> playedList = mPlayedContentDBHelper.getPlayedContentList(PlayedContentDBHelper.KEY_RECENT_PLAY_TIME, PlayedContentDBHelper.ORDER_DESC);
			Log.i("Saved File list size : "+ playedList.size());
			for(int i = 0 ; i < playedList.size(); i++)
			{
				Log.i("Deleted File Path  : "+ playedList.get(i).getFilePath()+", Content ID : "+ playedList.get(i).getContentID());
				mPlayedContentDBHelper.deletePlayedContent(playedList.get(i).getContentID());
			}
			
			mMainHandler.sendEmptyMessage(MESSAGE_DELETE_CONTENT_FILE);
		}
	    
	
    /**
     * 사용자를 변경시에 요청 (사용자 정보를 변경하는 게 아니라 현재 사용자를 변경)
     * @param index -1 사용자, 0~ 추가 사용자
     */
    private void requestChangeUser(int index)
    {
		if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
		{
	    	mChangeIndex = index;
	    	ChangeUserRequestAsync async = new ChangeUserRequestAsync(this, index, mAsyncListener);
	    	async.execute();
		}
		else
		{
			MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
		}
    }
    
    
	/**
	 * 스토리 리스트에서 아이템을 추가 삭제 할 경우에 Snack Message를 보여주기 위한 메소드
	 * @param type
	 * @param addTitle
	 */
	private void showMessageToFavoriteItem(int type, String addTitle)
	{
		String[] message = new String[2];
		message[0] = addTitle;
		
		if(type == Common.FAVORITE_TYPE_ADD)
		{
			message[1] = " "+ CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_add_favorite);
		}else if(type == Common.FAVORITE_TYPE_DELETE)
		{
			message[1] = " "+CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_delete_favorite);
		}
		
		int[] color = { getResources().getColor(R.color.color_d8232a), getResources().getColor(R.color.color_ffffff)};
		
		Log.f("Favorite set : "+ message[0]+" " + message[1]);
		CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, message, color);
	}
	
	/**
	 * 중국어 첫걸음 리스트를 보여줄 정보를 리턴한다.
	 * @return
	 */
	private ContentListTitleObject getChineseStep1Content()
	{
		String step1contentID = Feature.IS_LANGUAGE_ENG ? Common.CHINESE_STEP_1_CONTENT_ID_EN : Common.CHINESE_STEP_1_CONTENT_ID;
		for(int i = 0; i < mMainInformationResult.getStoryCardResultList().size(); i++)
		{
			if(step1contentID.equals(mMainInformationResult.getStoryCardResultList().get(i).getStoryKeyId()))
			{
				return new ContentListTitleObject(
						mMainInformationResult.getStoryCardResultList().get(i).sm_id,
						mMainInformationResult.getStoryCardResultList().get(i).sm_id_en,
						mMainInformationResult.getStoryCardResultList().get(i).getSeriesStatus(),
						mMainInformationResult.getStoryCardResultList().get(i).getTitleBackground(), 
						mMainInformationResult.getStoryCardResultList().get(i).getStatusBarBackground(),
						mMainInformationResult.getStoryCardResultList().get(i).getTitleTextImageUrl(), 
						mMainInformationResult.getStoryCardResultList().get(i).getTitleThumbnail());
			}
		}
		
		return null;
	}
    
    /**
     * 사용자, 추가 사용자에 따라 네비게이션 메뉴 구성을 달리한다.
     * @param type 사용자의 구분
     */
    private void settingNavigationLayout(int type)
    {
    	final int LAYOUT_TOP 							= 182;
    	final int LAYOUT_LEFT 							= 40;
    	final int LAYOUT_HEIGHT 						= 184;
    	final int NAVIGATION_USER_MENU_SIZE 			= 7;
    	final int NAVIGATION_ADDCHILD_MENU_SIZE 		= 6;
    	final int NAVIGATION_PAID_USER_MENU_SIZE 		= 6;
    	final int NAVIGATION_PAID_ADDCHILD_MENU_SIZE 	= 5;
    	
    	InAppInformation inAppInformation = (InAppInformation) CommonUtils.getInstance(this).getPreferenceObject(Common.PARAMS_INAPP_INFO, InAppInformation.class);
    	
    	if(type == TYPE_BASE_USER)
    	{
    		_NaviAddChildLayout.setVisibility(View.VISIBLE);
    		
    		if(inAppInformation.getInAppType().equals(InAppPurchase.IN_APP_FREE_USER))
    		{
    			for(int i = 0 ; i < NAVIGATION_USER_MENU_SIZE; i++)
        		{
        			_NavigationLayout.moveChildView(_NavigationViewUserList.get(i), LAYOUT_LEFT, LAYOUT_TOP + LAYOUT_HEIGHT * i);
        		}
    		}
    		else
    		{
    			_NaviPayLayout.setVisibility(View.GONE);
    			for(int i = 0 ; i < NAVIGATION_PAID_USER_MENU_SIZE; i++)
        		{
        			_NavigationLayout.moveChildView(_NavigationViewPaidUserList.get(i), LAYOUT_LEFT, LAYOUT_TOP + LAYOUT_HEIGHT * i);
        		}
    		}
    		
    	}
    	else if(type == TYPE_ADD_CHILD)
    	{
    		_NaviAddChildLayout.setVisibility(View.GONE);
    		
    		if(inAppInformation.getInAppType().equals(InAppPurchase.IN_APP_FREE_USER))
    		{
    			for(int i = 0 ; i < NAVIGATION_ADDCHILD_MENU_SIZE; i++)
        		{
        			_NavigationLayout.moveChildView(_NavigationViewAddChildList.get(i), LAYOUT_LEFT, LAYOUT_TOP + LAYOUT_HEIGHT * i);
        		}
    		}
    		else
    		{
    			_NaviPayLayout.setVisibility(View.GONE);
    			for(int i = 0 ; i < NAVIGATION_PAID_ADDCHILD_MENU_SIZE; i++)
        		{
        			_NavigationLayout.moveChildView(_NavigationViewPaidAddChildList.get(i), LAYOUT_LEFT, LAYOUT_TOP + LAYOUT_HEIGHT * i);
        		}
    		}
    		
    	}
    }
    
	private void processActiviryResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
			case REQUEST_MODIFICATION_RENEW:
				settingLoginUser();
				break;
			case REQUEST_PAY_COMPLETE:
				settingLoginUser();
				showSuccessMessage(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_pay_complete));
				break;
			}
		}
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.f("onActivityResult(" + requestCode + "," + resultCode + "," + data);
		super.onActivityResult(requestCode, resultCode, data);
		processActiviryResult(requestCode, resultCode, data);
	}
    
	private class MainSelectionPagerAdapter extends FragmentPagerAdapter
	{
		public MainSelectionPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int position)
		{
			Fragment fragment = null;
			switch(position)
			{
			case PAGE_HOME:
				fragment = MainSubHomeFragmentTablet.getInstance();
				((MainSubHomeFragmentTablet)fragment).setHomeDataResult(mMainInformationResult.getHomeDataResult());
				break;

			case PAGE_STORY:
				fragment = MainSubStoryFragment.getInstance();
				((MainSubStoryFragment)fragment).setStoryCardResultList(mMainInformationResult.getStoryCardResultList());
				break;
				
			case PAGE_SONG:
				fragment  = MainSubSongCategoryFragment.getInstance();
				((MainSubSongCategoryFragment)fragment).setSongCategoryResultList(mMainInformationResult.getSongCardResult());
				break;
				
			case PAGE_STUDY_DATA:
				fragment = MainSubStudyDataFragment.getInstance();
				((MainSubStudyDataFragment) fragment).setStudyDataResult(mMainInformationResult.getStudyDataResult());
				break;
			}
			((MainHolder) fragment).setOnSubTabsEventListener(mOnMainSubTabsEventListener);
			mFragmentList.add(fragment);
			
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
		
		@Override
        public CharSequence getPageTitle(int position) 
		{
            return null;
        }
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener()
	{
		
		@Override
		public void onPageSelected(int position)
		{
			Log.f("Current Page Position : "+position);

			mPagePosition = position;
			_MainTitleText.setText(CommonUtils.getInstance(MainTabsActivityTablet.this).getLanguageTypeString(TITLE_STRINGS[position]));
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int position)
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
				if(code == Common.ASYNC_CODE_CHANGE_USER)
				{
					showSuccessMessage(CommonUtils.getInstance(MainTabsActivityTablet.this).getLanguageTypeString(R.array.message_change_user));
					UserTotalInformationObject userTotalInfo = (UserTotalInformationObject) CommonUtils.getInstance(MainTabsActivityTablet.this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
					userTotalInfo.setCurrentUserChildIndex(mChangeIndex);
					CommonUtils.getInstance(MainTabsActivityTablet.this).setPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, userTotalInfo);
					settingLoginUser();
				}
			}
			else
			{
				if(((BaseResult)mObject).isAuthenticationBroken())
				{
					finish();
					MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE , true);
				}
				else
				{
					showFailMessage(((BaseResult)mObject).getMessage());
				}		
			}
		}

	};
	
	private OnMainSubTabsEventListener mOnMainSubTabsEventListener = new OnMainSubTabsEventListener()
	{
		
		@Override
		public void onCreteContentList(ContentListTitleObject object)
		{
			/**
			 *  무료회원은 스토리 컨텐트 리스트 에서 플레이 이후에 결제 페이지에서 결제후 정보를 컨텐트 리스트가 받아서 정보를 리턴해 주기 위해 사용
			 */
			
			if (NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this) == false) 
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				return;
			}

			initTransition(true);
			if (Build.VERSION.SDK_INT >= Common.LOLLIPOP) 
			{
				if(Feature.IS_FREE_USER)
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_STORY_CONTENT, REQUEST_PAY_COMPLETE, object);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_STORY_CONTENT, object);
				}
				
			} 
			else 
			{
				if(Feature.IS_FREE_USER)
				{
					MainSystemFactory.getInstance().startActivityNoAnimationForResult(MainSystemFactory.MODE_STORY_CONTENT, REQUEST_PAY_COMPLETE, object);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_STORY_CONTENT, object);
				}	
			}
			
		}
		
		@Override
		public void onBannerClick(String moveUrl)
		{
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				Log.f("moveUrl : "+moveUrl);
				CommonUtils.getInstance(MainTabsActivityTablet.this).startLinkMove(moveUrl);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
		}

		@Override
		public void onStartQuiz(String contentID)
		{
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(MainTabsActivityTablet.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_QUIZ, contentID);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
		}

		@Override
		public void onSetFavorite(int type, String addTitle)
		{
			showMessageToFavoriteItem(type, addTitle);
			((MainSubHomeFragmentTablet)mFragmentList.get(PAGE_HOME)).notifyContentList();
		}

		@Override
		public void onPlayContent(ContentPlayObject contentPlayObject)
		{
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PLAYER, contentPlayObject);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			
		}

		@Override
		public void onSnackMessage(String message, int color)
		{
			CommonUtils.getInstance(MainTabsActivityTablet.this).showSnackMessage(_BaseCoordinatorLayout, message, color);
		}

		@Override
		public void onStartStudyData(String contentID)
		{
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(MainTabsActivityTablet.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_ORIGIN_DATA, contentID);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			
		}

		@Override
		public void onCreateChineseStep1Content()
		{
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_STORY_CONTENT, getChineseStep1Content());
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			
		}

		@Override
		public void onMoveStudyDataStep1List()
		{
			mMainHandler.sendEmptyMessage(MESSAGE_MOVE_STUDY_DATA);
		}

		@Override
		public void onStartStepLittlefoxIntroduce()
		{
			MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_STEP_LITTLEFOX_INTRODUCE);
		}

		@Override
		public void onStartStepStudyGuide()
		{
			MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_STEP_STUDY_GUIDE);
		}

		@Override
		public void onCreateSongList(SongCategoryObject object)
		{
			/**
			 *  무료회원은 스토리 컨텐트 리스트 에서 플레이 이후에 결제 페이지에서 결제후 정보를 컨텐트 리스트가 받아서 정보를 리턴해 주기 위해 사용
			 */
			
			if (NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this) == false) 
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				return;
			}

			initTransition(true);
			if (Build.VERSION.SDK_INT >= Common.LOLLIPOP) 
			{
				if(Feature.IS_FREE_USER)
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimationForResult(MainSystemFactory.MODE_SONG_CONTENT_LIST, REQUEST_PAY_COMPLETE, object);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_SONG_CONTENT_LIST, object);
				}
				
			} 
			else 
			{
				if(Feature.IS_FREE_USER)
				{
					MainSystemFactory.getInstance().startActivityNoAnimationForResult(MainSystemFactory.MODE_SONG_CONTENT_LIST, REQUEST_PAY_COMPLETE, object);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_SONG_CONTENT_LIST, object);
				}	
			}
		}

		@Override
		public void onAutobiography(String url)
		{
			if(NetworkUtil.isConnectNetwork(MainTabsActivityTablet.this))
			{
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_AUTOBIOGRAPHY, url);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			
		}
	};
	
	private DialogListener mDialogListener = new DialogListener()
	{
		@Override
		public void onItemClick(int messageType, Object sendObject)
		{
			Log.i("messageButtonType : "+messageType);
			
			if(messageType == DIALOG_EVENT_IAC)
			{
				CommonUtils.getInstance(MainTabsActivityTablet.this).startLinkMove(mInitResult.getIACInformationList().get(0).btn_link);
				mLocalIacController.setPositiveButtonClick();
				CommonUtils.getInstance(MainTabsActivityTablet.this).setPreferenceObject(Common.PARAMS_IAC_AWAKE_INFO, mLocalIacController);
			}
		}

		@Override
		public void onItemClick(int messageButtonType, int messageType, Object sendObject)
		{
			Log.i("messageButtonType : "+messageButtonType+" subMessageType : "+messageType);
			switch(messageType)
			{
			case DIALOG_EVENT_LOGOUT:
				if(messageButtonType == FIRST_BUTTON_CLICK)
				{
					//취소
				}
				else if(messageButtonType == SECOND_BUTTON_CLICK)
				{
					Log.f("Logout Success");
					_MainBaseLayout.closeDrawer(_NavigationLayout);
					mMainHandler.sendEmptyMessageDelayed(MESSAGE_LOGOUT_MESSAGE, DURATION_NAVIGATION_ANIMATION);
				}
				break;
			case DIALOG_EVENT_IAC:
				if(messageButtonType == FIRST_BUTTON_CLICK)
				{
					Log.f("IAC Link move");
					CommonUtils.getInstance(MainTabsActivityTablet.this).startLinkMove(mInitResult.getIACInformationList().get(0).btn_link);
					mLocalIacController.setPositiveButtonClick();
					CommonUtils.getInstance(MainTabsActivityTablet.this).setPreferenceObject(Common.PARAMS_IAC_AWAKE_INFO, mLocalIacController);
				}
				else if(messageButtonType == SECOND_BUTTON_CLICK)
				{
					Log.f("IAC Cancel");
					mLocalIacController.setNeverWatch();
					CommonUtils.getInstance(MainTabsActivityTablet.this).setPreferenceObject(Common.PARAMS_IAC_AWAKE_INFO, mLocalIacController);
				}
				break;

			}
		}
	};
	
	private OnSelectDialogListener mOnSelectUserDialogListener = new OnSelectDialogListener()
	{
		
		@Override
		public void onSelectUserIndex(int index)
		{
			Message msg = Message.obtain();
			msg.what = MESSAGE_CHANGE_USER;
			msg.arg1 = index;
			mMainHandler.sendMessageDelayed(msg, DURATION_ANIMATION);
		}
	};

}

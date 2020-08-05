package com.littlefox.chinese.edu.factory;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.littlefox.chinese.edu.AddChildInformationModificationActivity;
import com.littlefox.chinese.edu.AutobiographyActivity;
import com.littlefox.chinese.edu.ContentPresentActivity;
import com.littlefox.chinese.edu.IntroLoadingActivity;
import com.littlefox.chinese.edu.IntroduceActivity;
import com.littlefox.chinese.edu.LoginActivity;
import com.littlefox.chinese.edu.MainTabsActivity;
import com.littlefox.chinese.edu.MainTabsActivityTablet;
import com.littlefox.chinese.edu.NetworkErrorActivity;
import com.littlefox.chinese.edu.OriginDataInformationActivity;
import com.littlefox.chinese.edu.PayPageActivity;
import com.littlefox.chinese.edu.PlayerHlsActivity;
import com.littlefox.chinese.edu.QuizActivity;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.SongContentListActivity;
import com.littlefox.chinese.edu.StepLittlefoxChineseIntroduceActivity;
import com.littlefox.chinese.edu.StepStudyGuideActivity;
import com.littlefox.chinese.edu.StoryContentListActivity;
import com.littlefox.chinese.edu.StudyRecordActivity;
import com.littlefox.chinese.edu.UserInformationModificationActivity;
import com.littlefox.chinese.edu.UserSignActivity;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.SongCategoryObject;
import com.littlefox.chinese.edu.object.result.InitAppResult;
import com.littlefox.logmonitor.Log;


/**
 * 각 액티비티의 이동관련 싱글톤 클래스
 * @author 정재현
 *
 */
public class MainSystemFactory
{	
	
	/**
	 * 액티비티 의 모드
	 */
	public static final int MODE_INTRO_LOADING								= 98;
	public static final int MODE_INTRODUCE									= 99;
	public static final int MODE_MAIN										= 100;
	public static final int MODE_PLAYER										= 101;
	public static final int MODE_QUIZ										= 102;
	public static final int MODE_STORY_CONTENT								= 103;
	public static final int MODE_ORIGIN_DATA								= 104;
	public static final int MODE_USER_SIGN									= 105;
	public static final int MODE_LOGIN										= 106;
	public static final int MODE_USER_INFO									= 107;
	public static final int MODE_ADD_USER_INFO								= 108;
	public static final int MODE_PAY_PAGE									= 109;
	public static final int MODE_STEP_LITTLEFOX_INTRODUCE 					= 110;
	public static final int MODE_STEP_STUDY_GUIDE							= 111;
	public static final int MODE_STUDY_RECORD								= 112;
	public static final int MODE_CONTENT_PRESENT							= 113;
	public static final int MODE_SONG_CONTENT_LIST							= 114;
	public static final int MODE_AUTOBIOGRAPHY								= 115;
	public static final int MODE_NETWORK_ERROR								= 1100;
	
	public static final int ANIMATION_NOT_TRANSITION			= 0;
	public static final int ANIMATION_TRANSITION				= 1;
	public static final int ANIMATION_MATERIAL_TRANSITION 		= 2;
	

	private static MainSystemFactory sFragmentFactory = null;
	
	private Context mContext;
	private int mCurrentAnimationTransitionMode = ANIMATION_NOT_TRANSITION;
	private int mCurrentMode = MODE_MAIN;

	public static MainSystemFactory getInstance()
	{
		if(sFragmentFactory == null)
		{
			sFragmentFactory = new MainSystemFactory();
		}
		
		return sFragmentFactory;
	}

	
	/**
	 * 초기 액티비티 정보와 뷰를 세팅할 레이아웃을 지정
	 * @param context 액티비티의 컨텍스트
	 */
	public void setInit(Context context)
	{
		mContext 	= context; 
	}
	
	/**
	 *  액티비티를 생성한다.
	 * @param mode 각 화면의 모드를 의미
	 * @param object 전달할 오브젝트
	 * @param animationType 애니메이션 타입
	 * @param requestCode 전달 받을 코드
	 * @param addflag 인텐트 플래그
	 */
	private void startActivity(int mode , Object object , int animationType, int requestCode, int addflag)
	{
		Log.i("mode : "+ mode +", object : "+ object);
		mCurrentAnimationTransitionMode = animationType;
		Intent intent = null; 
		switch(mode)
		{
		case MODE_INTRO_LOADING:
			intent = new Intent(mContext, IntroLoadingActivity.class);
			break;
		case MODE_INTRODUCE:
			intent = new Intent(mContext, IntroduceActivity.class);
			break;
		case MODE_MAIN:
			if(Feature.IS_TABLET)
			{
				intent = new Intent(mContext, MainTabsActivityTablet.class);
			}
			else
			{
				intent = new Intent(mContext, MainTabsActivity.class);
			}
			
			if(object != null)
			{
				intent.putExtra(Common.INTENT_INIT_APP_INFO, (InitAppResult)object);
			}
			break;
		case MODE_PLAYER:
			intent = new Intent(mContext, PlayerHlsActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_PLAYER_PARAMS, (ContentPlayObject)object);
			}
			break;
		case MODE_QUIZ:
			intent = new Intent(mContext, QuizActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_QUIZ_PARAMS, (String)object);
			}
			break;
		case MODE_STORY_CONTENT:
			intent = new Intent(mContext, StoryContentListActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_STORY_CONTENT_PARAMS, (ContentListTitleObject)object);
			}
			break;
		case MODE_SONG_CONTENT_LIST:
			intent = new Intent(mContext, SongContentListActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_SONG_CONTENT_PARAMS, (SongCategoryObject)object);
			}
			break;
		case MODE_ORIGIN_DATA:
			intent = new Intent(mContext, OriginDataInformationActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_STUDY_DATA_PARAMS, (String)object);
			}
			break;
		case MODE_USER_SIGN:
			intent = new Intent(mContext, UserSignActivity.class);
			break;
		case MODE_LOGIN:
			intent = new Intent(mContext, LoginActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_STUDY_DATA_PARAMS, (String)object);
			}
			break;
		case MODE_USER_INFO:
			intent = new Intent(mContext,  UserInformationModificationActivity.class);
			break;
		case MODE_ADD_USER_INFO:
			intent = new Intent(mContext,  AddChildInformationModificationActivity.class);
			break;
		case MODE_PAY_PAGE:
			intent = new Intent(mContext,  PayPageActivity.class);
			break;
		case MODE_STEP_LITTLEFOX_INTRODUCE:
			intent = new Intent(mContext , StepLittlefoxChineseIntroduceActivity.class);
			break;
		case MODE_STEP_STUDY_GUIDE:
			intent = new Intent(mContext , StepStudyGuideActivity.class);
			break;
		case MODE_STUDY_RECORD:
			intent = new Intent(mContext, StudyRecordActivity.class);
			break;
		case MODE_CONTENT_PRESENT:
			intent = new Intent(mContext, ContentPresentActivity.class);
			break;
		case MODE_AUTOBIOGRAPHY:
			intent = new Intent(mContext, AutobiographyActivity.class);
			if(object != null)
			{
				intent.putExtra(Common.INTENT_AUTOBIOGRAPHY, (String)object);
			}
			break;
		case MODE_NETWORK_ERROR:
			intent = new Intent(mContext, NetworkErrorActivity.class);
			intent.putExtra(Common.INTENT_PREVIOUS_MODE, mCurrentMode);
			break;
		}
		
		if(addflag != -1)
		{
			intent.addFlags(addflag);
		}

		switch(animationType)
		{
		case ANIMATION_NOT_TRANSITION:
			if(requestCode == -1)
				mContext.startActivity(intent);
			else
				((Activity)mContext).startActivityForResult(intent, requestCode);
			break;
		case ANIMATION_TRANSITION:
			if(requestCode == -1)
				mContext.startActivity(intent);
			else
				((Activity)mContext).startActivityForResult(intent, requestCode);
			if(mode == MODE_NETWORK_ERROR)
			{
				((Activity)mContext).overridePendingTransition(R.anim.abc_slide_in_top, R.anim.not_animation);
			}
			else
			{
				((Activity)mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
			
			break;
		case ANIMATION_MATERIAL_TRANSITION:
			
			try
			{
				if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
				{
					if(requestCode == -1)
						ActivityCompat.startActivity(mContext, intent,
							ActivityOptions.makeSceneTransitionAnimation((Activity)mContext).toBundle());
					else
						ActivityCompat.startActivityForResult((Activity)mContext, intent, requestCode,
								ActivityOptions.makeSceneTransitionAnimation((Activity)mContext).toBundle());
				}
				else
				{
					if(requestCode == -1)
						mContext.startActivity(intent);
					else
						((Activity)mContext).startActivityForResult(intent, requestCode);
					((Activity)mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			}catch(NoSuchMethodError e)
			{
				if(requestCode == -1)
					mContext.startActivity(intent);
				else
					((Activity)mContext).startActivityForResult(intent, requestCode);
				((Activity)mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
			
			break;
		}
		
		

	}
	
	private void initScene(int activityType,  boolean withAnimation, boolean isLogin)
	{
		Intent intent = null;

		switch(activityType)
		{
		case MODE_INTRODUCE:
			intent = new Intent(mContext, IntroduceActivity.class);
			CommonUtils.getInstance(mContext).setSharedPreference(Common.PARAMS_IS_AUTO_LOGIN, false);
			CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_USER_LOGIN, null);
			break;
		case MODE_INTRO_LOADING:
			intent = new Intent(mContext, IntroLoadingActivity.class);
			if(isLogin)
			{
				intent.putExtra(Common.INTENT_LOGIN_COMPLETE, true);
			}
			break;
		}
		 

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		mContext.startActivity(intent);
		if(withAnimation)
		{
			((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		}
	}
	
	/**
	 * 특정 조건 이 만족하지 못하여, 초기 화면으로 보낼때 사용
	 * @param activityType 인트로 화면 또는 로딩화면 : 로딩화면으로 갈때는 로그인되어있는 상태에서 행동하는것이므로 사용자 정보를 삭제하지않는다.</p>
	 * @param withAnimation 애니메이션을 사용 할 지에 대한 여부 
	 */
	public void resetScene(int activityType,  boolean withAnimation)
	{
		initScene(activityType , withAnimation, false);
	}
	
	/**
	 * 특정 조건 이 만족하지 못하여, 초기 화면으로 보낼때 사용. 로그인이 성공했을 때 호출한다.
	 * @param activityType 인트로 화면 또는 로딩화면 : 로딩화면으로 갈때는 로그인되어있는 상태에서 행동하는것이므로 사용자 정보를 삭제하지않는다.</p>
	 * @param withAnimation 애니메이션을 사용 할 지에 대한 여부 
	 */
	public void resetSceneToLogin(int activityType,  boolean withAnimation)
	{
		initScene(activityType , withAnimation, true);
	}
	
	/** 
	 * 각 Scene Resume에 넣어야한다. 그래서 현재의 Scene이 무엇인지 System은 알아야한다.
	 * @param mode
	 */
	public void setCurrentMode(int mode)
	{
		mCurrentMode = mode;
	}
	
	/**
	 * 현재 실행중인 Scene
	 * @return
	 */
	public int getCurrentMode()
	{
		return mCurrentMode;
	}
	
	public int getAnimationTransitionMode()
	{
		return mCurrentAnimationTransitionMode;
	}
	
	
	
	/**
	 * 애니메이션 없이 액티비티를 생성
	 * @param mode 각 화면의 모드 
	 * @param object 전달할 오브젝트
	 * @param flags 인텐트 옵션 플래그
	 */
	public void startActivityNoAnimation(int mode, Object object, int flags)
	{
		startActivity(mode, object, ANIMATION_NOT_TRANSITION, -1, flags);
	}
	
	/**
	 * 애니메이션 없이 액티비티를 생성
	 * @param mode 각 화면의 모드 
	 * @param object 전달할 오브젝트
	 */
	public void startActivityNoAnimation(int mode, Object object)
	{
		startActivity(mode, object, ANIMATION_NOT_TRANSITION, -1, -1);
	}
	
	/**
	 * 애니메이션 없이 액티비티를 생성
	 * @param mode 각 화면의 모드 
	 * @param flags 인텐트 옵션 플래그
	 */
	public void startActivityNoAnimation(int mode, int flags)
	{
		startActivity(mode, null, ANIMATION_NOT_TRANSITION, -1, flags);
	}
	
	/**
	 * 애니메이션 없이 액티비티를 생성
	 * @param mode 각 화면의 모드 
	 */	
	public void startActivityNoAnimation(int mode)
	{
		startActivity(mode, null, ANIMATION_NOT_TRANSITION, -1, -1);
	}
	
	/**
	 * 애니메이션 없이 액티비티를 생성하며 결과 코드 값을 전달받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달 받을 코드
	 * @param object 전달 할 오브젝트
	 */
	public void startActivityNoAnimationForResult(int mode, int requestCode, Object object)
	{
		startActivity(mode, object, ANIMATION_NOT_TRANSITION, requestCode, -1);
	}
	
	/**
	 * 애니메이션 없이 액티비티를 생성하며 결과 코드 값을 전달받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달 받을 코드
	 */
	public void startActivityNoAnimationForResult(int mode, int requestCode)
	{
		startActivity(mode, null, ANIMATION_NOT_TRANSITION, requestCode, -1);
	}
	
	/**
	 * 머터리얼 애니메이션을 이용하여 액티비티 생성 ( LOLLIPOP 이상 지원 )
	 * @param mode 각 화면의 모드
	 * @param object 전달할 오브젝트
	 */
	public void startActivityWithMaterialAnimation(int mode, Object object)
	{
		startActivity(mode, object, ANIMATION_MATERIAL_TRANSITION, -1, -1);
	}

	/**
	 * 머터리얼 애니메이션을 이용하여 액티비티 생성 ( LOLLIPOP 이상 지원 )
	 * @param mode 각 화면의 모드
	 */
	public void startActivityWithMaterialAnimation(int mode)
	{
		startActivity(mode, null, ANIMATION_MATERIAL_TRANSITION, -1, -1);
	}
	
	/**
	 * 머터리얼 애니메이션을 이용하여 액티비티 생성 ( LOLLIPOP 이상 지원 ) 하며 결과 코드값을 전달 받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달받을 코드
	 * @param object 전달 할 오브젝트 
	 */
	public void startActivityWithMaterialAnimationForResult(int mode, int requestCode, Object object)
	{
		startActivity(mode, object, ANIMATION_MATERIAL_TRANSITION, requestCode, -1);
	}
	
	/**
	 * 머터리얼 애니메이션을 이용하여 액티비티 생성 ( LOLLIPOP 이상 지원 ) 하며 결과 코드값을 전달 받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달받을 코드
	 */
	public void startActivityWithMaterialAnimationForResult(int mode, int requestCode)
	{
		startActivity(mode, null, ANIMATION_MATERIAL_TRANSITION, requestCode, -1);
	}
	
	/**
	 * 애니메이션을 이용하여 액티비티 생성 
	 * @param mode 각 화면의 모드
	 * @param object 전달 할 오브젝트 
	 */
	public void startActivityWithAnimation(int mode, Object object)
	{
		startActivity(mode, object, ANIMATION_TRANSITION, -1, -1);
	}
	
	/**
	 * 애니메이션을 이용하여 액티비티 생성 
	 * @param mode 각 화면의 모드
	 */
	public void startActivityWithAnimation(int mode)
	{
		startActivity(mode, null, ANIMATION_TRANSITION, -1, -1);
	}
	
	/**
	 * 애니메이션을 이용하여 액티비티 생성 하며 결과 코드 값을 전달 받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달받을 코드
	 * @param object 전달할 오브젝트
	 */
	public void startActivityWithAnimationForResult(int mode, int requestCode, Object object)
	{
		startActivity(mode, object, ANIMATION_TRANSITION, requestCode, -1);
	}
	
	/**
	 * 애니메이션을 이용하여 액티비티 생성 하며 결과 코드 값을 전달 받는다.
	 * @param mode 각 화면의 모드
	 * @param requestCode 전달받을 코드
	 */
	public void startActivityWithAnimationForResult(int mode, int requestCode)
	{
		startActivity(mode, null, ANIMATION_TRANSITION, requestCode, -1);
	}
}

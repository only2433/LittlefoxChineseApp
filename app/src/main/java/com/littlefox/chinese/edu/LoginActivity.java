 package com.littlefox.chinese.edu;

 import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.CheckUserInput;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.coroutines.SearchUserInfoCoroutine;
import com.littlefox.chinese.edu.coroutines.SendEmailToSearchPasswordCoroutine;
import com.littlefox.chinese.edu.coroutines.UserLoginCoroutine;
import com.littlefox.chinese.edu.dialog.LoginInfoSearchDialog;
import com.littlefox.chinese.edu.dialog.LoginInfoSearchDialog.OnLoginListener;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.chinese.edu.object.result.InfoSearchResult;
import com.littlefox.chinese.edu.object.result.UserLoginResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.dialog.MaterialLoadingDialog;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

 public class LoginActivity extends BaseActivity
{
	@BindView(R.id.base_coodinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.title_base_layout)
	ScalableLayout _TopBaseLayout;
	
	@Nullable
	@BindView(R.id.top_margin_layout)
	ScalableLayout _RadioTopMarginLayout;
	
	@BindView(R.id.top_menu_title)
	TextView _TopBaseText;
	
	@BindView(R.id.login_id_edit)
	PaddingDrawableEditText  _IDEditTextView;
	
	@BindView(R.id.login_pw_edit)
	PaddingDrawableEditText  _PasswordEditTextView;
	
	@BindView(R.id.login_auto_login_text)
	TextView _AutoLoginText;
	
	@BindView(R.id.login_auto_check_image)
	ImageView _AutoLoginCheckImage;
	
	@BindView(R.id.login_id_pw_search_text)
	TextView _IDPasswordSearchText;
	
	@BindView(R.id.login_main_login_button)
	TextView _LoginButton;
	
	@BindView(R.id.login_already_have_id)
	TextView _TitleLoginAlreadyHaveText;
	
	@BindView(R.id.login_not_have_id)
	TextView _TitleLoginNotHaveIDText;
	
	@BindView(R.id.login_main_sign_button)
	TextView _SignButton;
	
	Handler mMainHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_LOGIN_INFO_SEARCH_OPEN:
				InfoSearchResult result = null;
				Bundle bundle = msg.getData();
				int type = bundle.getInt(BUNDLE_NAME_TYPE, LoginInfoSearchDialog.LOGIN_SEARCH_ID);
				try
				{
					result = bundle.getParcelable(BUNDLE_NAME_INFO);
				}catch(Exception e)
				{
					
				}
				if(result == null)
				{
					showLoginDialog(type);
				}
				else
				{
					showLoginDialog(type,result);
				}
				break;
			case MESSAGE_SEARCH_ID:
				requestSearchUserInfoAsync(Common.ASYNC_CODE_SEARCH_ID, (String) msg.obj);
				break;
			case MESSAGE_SEARCH_PASSWORD:
				requestSearchUserInfoAsync(Common.ASYNC_CODE_SEARCH_PASSWORD, (String) msg.obj);
				break;
			case MESSAGE_SEND_EMAIL:
				requestSendEmailToChangePasswordAsync((String) msg.obj);
				break;
			case MESSAGE_LOGINCOMPLETE:
				startIntroActivity();
				break;
			case MESSAGE_LOGIN_CHECK:
				checkLoginInformation();
				break;
			}
		}
	};

	private static final int DURATION_ANIMATION = 300;
	private static final int DURATION_LOGIN_CHECK_DELAY = 500;
	
	private static final int MESSAGE_LOGIN_INFO_SEARCH_OPEN	= 0;
	private static final int MESSAGE_SEARCH_ID						= 1;
	private static final int MESSAGE_SEARCH_PASSWORD			= 2;
	private static final int MESSAGE_SEND_EMAIL					= 3;
	private static final int MESSAGE_LOGINCOMPLETE				= 4;
	private static final int MESSAGE_LOGIN_CHECK					= 5;

	private static final String BUNDLE_NAME_TYPE ="type";
	private static final String BUNDLE_NAME_INFO ="info_object";
	
	private boolean isAutoLogin = false;
	private LoginInfoSearchDialog mLoginDialog = null;
	private MaterialLoadingDialog mLoadingDialog = null;
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.login_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.login_main);
		}
			
		ButterKnife.bind(this);
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		Log.f("");
		initView();
		initFont();
		initText();
	}
	
	private void initView()
	{
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			_RadioTopMarginLayout.setVisibility(View.VISIBLE);
		}
	}
	
	private void initFont()
	{
		_TopBaseText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_IDEditTextView.setTypeface(Font.getInstance(this).getRobotoMedium());
		_PasswordEditTextView.setTypeface(Font.getInstance(this).getRobotoMedium());
		_AutoLoginText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_IDPasswordSearchText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_LoginButton.setTypeface(Font.getInstance(this).getRobotoMedium());
		_TitleLoginAlreadyHaveText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_TitleLoginNotHaveIDText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_SignButton.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		_TopBaseText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_login));
		_TitleLoginAlreadyHaveText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_you_already_user));
		_IDEditTextView.setHint(CommonUtils.getInstance(this).getLanguageTypeString(R.array.login_id));
		_PasswordEditTextView.setHint(CommonUtils.getInstance(this).getLanguageTypeString(R.array.login_pw));
		_AutoLoginText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.login_auto));
		_IDPasswordSearchText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.login_id_pw_search));
		_LoginButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_login));
		_TitleLoginNotHaveIDText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_you_not_user));
		_SignButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_sign));
	}
	
	private void startIntroActivity()
	{
		setResult(RESULT_OK);
		finish();
		MainSystemFactory.getInstance().resetSceneToLogin(MainSystemFactory.MODE_INTRO_LOADING, true);
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
	
	public void onBackPressed() 
	{
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_LOGIN);
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
	
	@OnClick({R.id.top_menu_close, R.id.login_auto_login_text, R.id.login_auto_check_image, R.id.login_id_pw_search_text, R.id.login_main_login_button, R.id.login_main_sign_button})
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.top_menu_close:
			finish();
			MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE , true);
			break;
		case R.id.login_auto_login_text:
		case R.id.login_auto_check_image:
			isAutoLogin = !isAutoLogin;
			
			if(isAutoLogin)
			{
				_AutoLoginCheckImage.setImageResource(R.drawable.check_on);
			}
			else
			{
				_AutoLoginCheckImage.setImageResource(R.drawable.check_off);
			}
			
			
			
			break;
		case R.id.login_id_pw_search_text:
			_IDEditTextView.clearFocus();
			_PasswordEditTextView.clearFocus();
			showLoginDialog(LoginInfoSearchDialog.LOGIN_SEARCH_ID);
			break;
		case R.id.login_main_login_button:
			InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			mInputMethodManager.hideSoftInputFromWindow(_PasswordEditTextView.getWindowToken(), 0);
			mInputMethodManager.hideSoftInputFromWindow(_IDEditTextView.getWindowToken(), 0);
			mMainHandler.sendEmptyMessageDelayed(MESSAGE_LOGIN_CHECK, DURATION_LOGIN_CHECK_DELAY);
			break;
		case R.id.login_main_sign_button:
			MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_USER_SIGN);
			break;
		}
	}
	
	private void checkLoginInformation()
	{
		int result = getSuccessLoginCheckValue();
		Log.i("result : "+result);
		if( result == CheckUserInput.INPUT_SUCCESS)
		{
			if(NetworkUtil.isConnectNetwork(this))
			{
				requestUserLoginAsync(_IDEditTextView.getText().toString(), _PasswordEditTextView.getText().toString());
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			
		}
		else
		{
			CommonUtils.getInstance(this).showSnackMessage(_BaseCoordinatorLayout, CheckUserInput.getInstance(this).getErrorMessage(result), getResources().getColor(R.color.color_d8232a),Gravity.CENTER);
		}
	}
	
	private int getSuccessLoginCheckValue()
	{
		int result = CheckUserInput.getInstance(this)
				.checkIDData(_IDEditTextView.getText().toString())
				.checkPasswordData(_PasswordEditTextView.getText().toString(), _PasswordEditTextView.getText().toString())
				.getResultValue();
		
		return result;
	}
	
	
	private void showLoginDialog(int type)
	{
		showLoginDialog(type , null);
	}
	
	/**
	 * LoginDialog를 보여준다.
	 * @param type 다이얼로그의 타입
	 */
	private void showLoginDialog(int type, InfoSearchResult result)
	{
		hideLoginDialog();
		
		if(result  == null)
		{
			mLoginDialog = new LoginInfoSearchDialog(this, type);
		}
		else
		{
			mLoginDialog = new LoginInfoSearchDialog(this, type, result);
		}
		mLoginDialog.setOnLoginListener(mOnDialogLoginListener);

		mLoginDialog.show();
	}
	
	
	private void hideLoginDialog()
	{
		if(mLoginDialog != null)
		{
			mLoginDialog.dismiss();
			mLoginDialog = null;
		}
	}
	
	private void showLoadingDialog()
	{
		if(mLoadingDialog == null)
		{
			mLoadingDialog = new MaterialLoadingDialog(this, CommonUtils.getInstance(this).getPixel(150), getResources().getColor(R.color.color_d8232a));
		}
		mLoadingDialog.show();
	}
	
	private void hideLoadingDialog()
	{
		if(mLoadingDialog != null)
		{
			mLoadingDialog.dismiss();
			mLoadingDialog = null;
		}
	}
	
	private void requestUserLoginAsync(String userId, String userPassword)
	{
		//UserLoginAsync async = new UserLoginAsync(this, new UserLoginObject(userId, userPassword), mLoginAsyncListener);
		//async.execute();
		UserLoginObject data = new UserLoginObject(userId, userPassword);
		UserLoginCoroutine coroutine = new UserLoginCoroutine(this, mLoginAsyncListener);
		coroutine.setData(data);
		coroutine.execute();

	}
	
	private void requestSearchUserInfoAsync(String code, String searchInfo)
	{
		/*SearchUserInfoAsync async = new SearchUserInfoAsync(this, code, searchInfo, mLoginAsyncListener);
		async.execute();*/
		SearchUserInfoCoroutine coroutine = new SearchUserInfoCoroutine(this, code, mLoginAsyncListener);
		coroutine.setData(searchInfo);
		coroutine.execute();
	}
	
	private void requestSendEmailToChangePasswordAsync(String email)
	{
		/*SendEmailToSearchPasswordAsync async = new SendEmailToSearchPasswordAsync(this, email, mLoginAsyncListener);
		async.execute();*/
		SendEmailToSearchPasswordCoroutine coroutine = new SendEmailToSearchPasswordCoroutine(this, mLoginAsyncListener);
		coroutine.setData(email);
		coroutine.execute();
	}
	/**
	 * 로그인에 성공한 정보를 SharedPreference에 저장하여 사용
	 * @param result
	 */
	private void setSharedUserLoginObject(UserLoginResult result)
	{
		UserLoginObject object = new UserLoginObject(_IDEditTextView.getText().toString(), _PasswordEditTextView.getText().toString());
		CommonUtils.getInstance(this).setPreferenceObject(Common.PARAMS_USER_LOGIN, object);
		CommonUtils.getInstance(this).setPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, null); //이전에 저장해 둔 사용자 정보는 삭제하고 , 메인에서 정보를 받을때 갱신한다.
	}
	
	/**
	 * 로그인 , 아이디 찾기, 비밀번호 찾기, 패스워드 이메일로 보내기 에 관련된 처리 
	 * @param code 해당 행동의 Code
	 * @param object 서버로 받은 정보
	 */
	private void processLoginInformation(String code, Object object)
	{
		Bundle bundle = null;
		Message msg = null;
		
		if(((BaseResult)object).getResult().equals(BaseResult.RESULT_OK))
		{
			switch(code)
			{
			case Common.ASYNC_CODE_SEARCH_ID:
				bundle = new Bundle();
				bundle.putInt(BUNDLE_NAME_TYPE, LoginInfoSearchDialog.LOGIN_SEARCH_RESULT_ID_SUCCESS);
				bundle.putParcelable(BUNDLE_NAME_INFO, (InfoSearchResult)object);
				msg = Message.obtain();
				msg.what = MESSAGE_LOGIN_INFO_SEARCH_OPEN;
				msg.setData(bundle);
				mMainHandler.sendMessage(msg);
				break;
				
			case Common.ASYNC_CODE_SEARCH_PASSWORD:
				bundle = new Bundle();
				bundle.putInt(BUNDLE_NAME_TYPE, LoginInfoSearchDialog.LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS);
				bundle.putParcelable(BUNDLE_NAME_INFO, (InfoSearchResult)object);
				msg = Message.obtain();
				msg.what = MESSAGE_LOGIN_INFO_SEARCH_OPEN;
				msg.setData(bundle);
				mMainHandler.sendMessage(msg);
				break;	
				
			case Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD:
				CommonUtils.getInstance(LoginActivity.this).showSnackMessage(_BaseCoordinatorLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_success_password_to_email), getResources().getColor(R.color.color_00b980), Gravity.CENTER);
				break;
				
			case Common.ASYNC_CODE_USER_LOGIN:
				/**
				 * 유저 정보는 항상 저장해야 프로그램 내에 진입하여 사용 할 수 있다. 오토 로그인 관련 Preference 사용으로 자동로그인 유무를 체크 하기로 한다.
				 */
				if(isAutoLogin)
				{
					CommonUtils.getInstance(LoginActivity.this).setSharedPreference(Common.PARAMS_IS_AUTO_LOGIN, true);
				}
				else
				{
					CommonUtils.getInstance(LoginActivity.this).setSharedPreference(Common.PARAMS_IS_AUTO_LOGIN, false);
				}
				setSharedUserLoginObject((UserLoginResult) object);
				Log.f("로그인 되었습니다.");
				GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_LOGIN, Common.ANALYTICS_ACTION_USER_LOGIN);
				mMainHandler.sendEmptyMessageDelayed(MESSAGE_LOGINCOMPLETE, DURATION_ANIMATION);
				break;
				
			}
		}
		else
		{
			switch(code)
			{
			case Common.ASYNC_CODE_SEARCH_ID:
				bundle = new Bundle();
				bundle.putInt(BUNDLE_NAME_TYPE, LoginInfoSearchDialog.LOGIN_SEARCH_RESULT_ID_FAIL);
				bundle.putParcelable(BUNDLE_NAME_INFO, (InfoSearchResult)object);
				msg = Message.obtain();
				msg.what = MESSAGE_LOGIN_INFO_SEARCH_OPEN;
				msg.setData(bundle);
				mMainHandler.sendMessage(msg);
				break;
				
			case Common.ASYNC_CODE_SEARCH_PASSWORD:
				bundle = new Bundle();
				bundle.putInt(BUNDLE_NAME_TYPE, LoginInfoSearchDialog.LOGIN_SEARCH_RESULT_PASSWORD_FAIL);
				bundle.putParcelable(BUNDLE_NAME_INFO, (InfoSearchResult)object);
				msg = Message.obtain();
				msg.what = MESSAGE_LOGIN_INFO_SEARCH_OPEN;
				msg.setData(bundle);
				mMainHandler.sendMessage(msg);
				break;	
				
			case Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD:
				CommonUtils.getInstance(LoginActivity.this).showSnackMessage(_BaseCoordinatorLayout, ((BaseResult)object).getMessage(), getResources().getColor(R.color.color_d8232a),Gravity.CENTER);
				break;
				
			case Common.ASYNC_CODE_USER_LOGIN:
				CommonUtils.getInstance(LoginActivity.this).showSnackMessage(_BaseCoordinatorLayout, ((BaseResult)object).getMessage(), getResources().getColor(R.color.color_d8232a),Gravity.CENTER);
				break;				
			}
		}
	}
	
	private OnLoginListener mOnDialogLoginListener = new OnLoginListener()
	{	
		@Override
		public void sendEmail(String email)
		{
			Message msg = Message.obtain();
			msg.what = MESSAGE_SEND_EMAIL;
			msg.obj = email;
			mMainHandler.sendMessageDelayed(msg, DURATION_ANIMATION);
		}
	
		
		@Override
		public void executeSearchPassword(String id)
		{
			Message msg = Message.obtain();
			msg.what = MESSAGE_SEARCH_PASSWORD;
			msg.obj = id;
			mMainHandler.sendMessageDelayed(msg, DURATION_ANIMATION);	
		}
		
		@Override
		public void executeSearchID(String email)
		{
			Message msg = Message.obtain();
			msg.what = MESSAGE_SEARCH_ID;
			msg.obj = email;
			mMainHandler.sendMessageDelayed(msg, DURATION_ANIMATION);		
		}
		
	};
	
	private AsyncListener mLoginAsyncListener = new AsyncListener()
	{

		@Override
		public void onRunningStart(String code)
		{
			showLoadingDialog();
		}

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
			hideLoadingDialog();
			
			if(((BaseResult)mObject).result.equals(BaseResult.RESULT_OK))
			{
				processLoginInformation(code, mObject);
			}
			else
			{
				Toast.makeText(LoginActivity.this, ((BaseResult)mObject).getMessage(), Toast.LENGTH_LONG).show();
			}
			
			
		}
		

	};

	
}

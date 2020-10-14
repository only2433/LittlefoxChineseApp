package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.littlefox.chinese.edu.billing.BillingClientHelper;
import com.littlefox.chinese.edu.billing.IBillingClientListener;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.FileUtils;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.coroutines.InitCoroutine;
import com.littlefox.chinese.edu.coroutines.MainRequestCoroutine;
import com.littlefox.chinese.edu.coroutines.UserInformationRequestCoroutine;
import com.littlefox.chinese.edu.dialog.TempleteAlertDialog;
import com.littlefox.chinese.edu.dialog.listener.DialogListener;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.InAppInformation;
import com.littlefox.chinese.edu.object.UserPaymentStatus;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.object.result.InitAppResult;
import com.littlefox.chinese.edu.object.result.UserInformationResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.root.RootCheck;
import com.littlefox.library.system.version_check.MarketVersionCheckListener;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IntroLoadingActivity extends BaseActivity
{
	@BindView(R.id.intro_loading_logoview)
	ImageView _LogoImageView;

	@BindView(R.id.intro_loading_frameview)
	ImageView _FrameAnimationView;

	@BindView(R.id.intro_loading_progress_text)
	TextView _LoadinProgressText;

	@BindView(R.id.intro_loading_layout)
	ScalableLayout _LoadingBaseLayout;

	Handler mIntroHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case MESSAGE_INIT:
					if(RootCheck.checkRootingDevice() == true)
					{
						Toast.makeText(IntroLoadingActivity.this, CommonUtils.getInstance(IntroLoadingActivity.this).getLanguageTypeString(R.array.message_root_not_play), Toast.LENGTH_LONG).show();
						finish();
					}
					else
					{
						initSetting();
					}

					break;
				case MESSAGE_INAPP_INFO:
					initInAppPurchase();
					break;
				case MESSAGE_GET_INIT_APP_INFO:
					requestInitAppInformation();
					break;
				case MESSAGE_GET_USER_INFO:
					//서버의 초기정보 보내고 받아오는 부분
					requestUserInformation();
					break;
				case MESSAGE_GET_MAIN_INFO:
					requestMainInformation();
					break;
				case MESSAGE_START_MAIN:
					startMainActivity();
					break;
				case MESSAGE_FINISH:
					finish();
					break;
				case MESSAGE_ERROR_FINISH:
					Toast.makeText(IntroLoadingActivity.this, (CharSequence) msg.obj, Toast.LENGTH_LONG).show();
					finish();
					break;
			}

		}

	};

	private static final int MY_PERMISSION_REQUEST_STORAGE = 1001;

	/** 버젼체크 관련 팝업 */
	public static final int DIALOG_EVENT_UPDATE_APP					= 0x00;

	private static final int DURATION_CHECK = 1500;
	private static final int DURATION_ACTIVITY_CREATE = 1000;

	private static final int MESSAGE_INIT							= 0;
	private static final int MESSAGE_INAPP_INFO						= 1;
	private static final int MESSAGE_GET_INIT_APP_INFO				= 2;
	private static final int MESSAGE_GET_USER_INFO  				= 3;
	private static final int MESSAGE_GET_MAIN_INFO					= 4;
	private static final int MESSAGE_START_MAIN 					= 5;
	private static final int MESSAGE_FINISH							= 6;
	private static final int MESSAGE_ERROR_FINISH = 7;

	private static final int DURATION_START_MAIN 		= 1000;
	private static final int DURATION_FINISH			= 500;

	private InitAppResult mInitResult;
	//private InAppPurchase mInAppPurchase;
	private BillingClientHelper mBillingClientHelper;

	private AnimationDrawable mFrameAnimationDrawable;
	private int mCurrentProgress = -1;

	private static final String[] PROGRESS_PERCENT_LIST = {"0%", "50%" , "75%" , "100%"};
	private String mCurrentPaymentType = BillingClientHelper.IN_APP_FREE_USER;

	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Log.init(Common.LOG_FILE);
		Log.f("Start Application");
		checkTablet();
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.intro_loading_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.intro_loading_main);
		}

		ButterKnife.bind(this);

		if(Build.VERSION.SDK_INT >= Common.MALSHMALLOW)
		{
			checkPermission();
		}
		else
		{
			settingLogFile();
			init();
		}


	}

	private void init()
	{
		Log.f("");
		_LoadingBaseLayout.setVisibility(View.INVISIBLE);
		CommonUtils.getInstance(this).getWindowInfo();
		CommonUtils.getInstance(this).showDeviceInfo();

		initFeatureInformation();
		initFont();
		initText();
		initFrameView();

		try {
			FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
				@Override
				public void onSuccess(InstanceIdResult instanceIdResult)
				{
					Log.f("token : "+ instanceIdResult.getToken());
					CommonUtils.getInstance(IntroLoadingActivity.this).setSharedPreference(Common.PARAMS_FIREBASE_PUSH_TOKEN, instanceIdResult.getToken());

				}
			});
		} catch (Exception e) {
			Log.f("getMessage : " + e.getMessage());
		}


		mIntroHandler.sendEmptyMessageDelayed(MESSAGE_INIT, DURATION_CHECK);
	}


	/**
	 * Permission check.
	 */
	private void checkPermission()
	{
		if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
				|| checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
		{
			// Should we show an explanation?
			if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
			{
				// Explain to the user why we need to write the permission.
				Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
			}

			requestPermissions(new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE,
					android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSION_REQUEST_STORAGE);

			// MY_PERMISSION_REQUEST_STORAGE is an
			// app-defined int constant

		} else
		{
			// 다음 부분은 항상 허용일 경우에 해당이 됩니다.
			// writeFile();
			settingLogFile();
			init();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSION_REQUEST_STORAGE:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED
						&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {

					settingLogFile();
					init();
					// permission was granted, yay! do the
					// calendar task you need to do.

				} else {

					Log.d("Permission always deny");
					finish();
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				break;
		}
	}

	/**
	 * 자동로그인이 되어있거나, 로그인에 성공했다면 메인으로 진입, 아니라면, 인트로 화면으로
	 */
	private void initSetting()
	{
		boolean isLoginComplete = getIntent().getBooleanExtra(Common.INTENT_LOGIN_COMPLETE, false);
		boolean isAutoLogin = (boolean) CommonUtils.getInstance(this).getSharedPreference(Common.PARAMS_IS_AUTO_LOGIN, Common.TYPE_PARAMS_BOOLEAN);
		Log.f("isLoginComplete : "+isLoginComplete + " , isAutoLogin : " + isAutoLogin);
		if(isAutoLogin == false && isLoginComplete == false)
		{

			mIntroHandler.sendEmptyMessageDelayed(MESSAGE_FINISH, DURATION_FINISH);
			MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_INTRODUCE);
		}
		else
		{
			_LoadingBaseLayout.setVisibility(View.VISIBLE);
			updateProgress();
			mIntroHandler.sendEmptyMessage(MESSAGE_INAPP_INFO);
		}

	}

	private void initFont()
	{
		_LoadinProgressText.setTypeface(Font.getInstance(this).getRobotoBold());
	}

	private void initText()
	{
		_LoadinProgressText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.loading_progress));
	}

	private void initFrameView()
	{
		if(Feature.IS_LANGUAGE_ENG)
		{
			_LogoImageView.setImageResource(R.drawable.loading_logo_en);
		}
		else
		{
			_LogoImageView.setImageResource(R.drawable.loading_logo);
		}
		if(Feature.IS_TABLET)
		{
			_FrameAnimationView.setBackgroundResource(R.drawable.intro_loading_frame_animation_tablet);
		}
		else
		{
			_FrameAnimationView.setBackgroundResource(R.drawable.intro_loading_frame_animation);
		}

		mFrameAnimationDrawable = (AnimationDrawable)_FrameAnimationView.getBackground();
	}

	private void checkTablet()
	{
		if(CommonUtils.getInstance(this).isTablet())
		{
			Log.f("TABLET");
			Feature.IS_TABLET = true;
			Common.HTTP_HEADER = Common.HTTP_HEADER_APP_NAME_TABLET;
		}
		else
		{
			Log.f("PHONE");
			Feature.IS_TABLET = false;
			Common.HTTP_HEADER = Common.HTTP_HEADER_APP_NAME;
		}
	}

	private void initFeatureInformation()
	{
		Log.f("Locale.KOREA.toString() : "+ Locale.KOREA.toString()+", default : "+ Locale.getDefault().toString());
		Feature.IS_LANGUAGE_ENG = !Locale.getDefault().toString().contains(Locale.KOREA.toString());

		if(CommonUtils.getInstance(this).isHaveNavigationBar())
		{
			Log.f("HAVE NAVIGATION BAR");
			Feature.HAVE_NAVIGATION_BAR = true;
		}
		else
		{
			Log.f("NOT NAVIGATION BAR");
			Feature.HAVE_NAVIGATION_BAR = false;
		}

		if(CommonUtils.getInstance(this).isDisplayMinimumSize())
		{
			Log.f("MINIMUM DISPLAY SIZE");
			Feature.IS_MINIMUM_DISPLAY_SIZE = true;
		}
		else
		{
			Log.f("SUITABLE DISPLAY SIZE");
			Feature.IS_MINIMUM_DISPLAY_SIZE = false;
		}

		Log.i("비율 : " + (float)CommonUtils.getInstance(this).getDisplayWidthPixel() / (float)CommonUtils.getInstance(this).getDisplayHeightPixel());

		if((float)CommonUtils.getInstance(this).getDisplayWidthPixel() / (float)CommonUtils.getInstance(this).getDisplayHeightPixel() < Common.MINIMUM_TABLET_DISPLAY_RADIO)
		{
			Log.f("4 : 3 비율 ");
			Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY = true;
		}
		else
		{
			Log.f("16 : 9 비율 ");
			Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY = false;
		}

	}

	/**
	 * 시간대 자동 설정이 되어 있는 지 체크
	 * @return
	 */
	private boolean isAutoTimeSelected()
	{
		int autoTimeSelected = -1;
		//int autoTimeSelectedZone = -1;
		try
		{
			if(Build.VERSION.SDK_INT > Common.LOLLIPOP)
			{
				autoTimeSelected = Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME);
				//autoTimeSelectedZone = Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME_ZONE);
			}
			else
			{
				autoTimeSelected = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME);
				//autoTimeSelectedZone  = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME_ZONE);
			}

			if( autoTimeSelected == 1)
			{
				return true;
			}
			else
			{
				Log.f("AUTO_TIME : "+autoTimeSelected);
				return false;
			}
		}
		catch (SettingNotFoundException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 현재 사용자 시간 설정을 체크. 사용자가 임의의 시간을 설정했을때 해당 메소드 실행
	 * @return
	 */
	private boolean checkSystemTime()
	{
		final long SECOND = 1000L;
		if(isAutoTimeSelected() == false)
		{
			String serverTime = CommonUtils.getInstance(this).getTodayDate(Long.parseLong(mInitResult.getServerTime())* SECOND);
			String currentTime = CommonUtils.getInstance(this).getTodayDate(System.currentTimeMillis());

			if(serverTime.equals(currentTime) == false)
			{
				startAutoTimeActivity();
				return false;
			}

		}

		return true;
	}

	private void startAutoTimeActivity()
	{
		Intent intent=new Intent();
		intent.setComponent(new ComponentName("com.android.settings",
				"com.android.settings.DateTimeSettingsSetupWizard"));
		startActivity(intent);
		Toast.makeText(this, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_select_auto_time), Toast.LENGTH_LONG).show();
	}

	private void updateProgress()
	{
		mCurrentProgress++;
		Log.i("mCurrentProgress : "+mCurrentProgress);
		try
		{
			_LoadinProgressText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.loading_progress)+ PROGRESS_PERCENT_LIST[mCurrentProgress]);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		try
		{
			if(hasFocus)
			{
				mFrameAnimationDrawable.start();
			}
			else
			{
				mFrameAnimationDrawable.stop();
			}
		}catch(NullPointerException e)
		{

		}


	}

	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_INTRO_LOADING);
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
		mIntroHandler.removeCallbacksAndMessages(null);
		if(mBillingClientHelper != null)
		{
			mBillingClientHelper.release();
		}

		Log.f("");

	}

	private void initInAppPurchase()
	{
		Log.f("init app purchase");
		mBillingClientHelper = BillingClientHelper.getInstance();
		setupInAppPurchaseListener();
		mBillingClientHelper.init(this);
	}

	private void setupInAppPurchaseListener()
	{
		mBillingClientHelper.setOnBillingClientListener(new IBillingClientListener() {
			@Override
			public void inFailure(int status, String reason)
			{
				Log.f("status : "+status +", reason : "+ reason);
			}

			@Override
			public void onSkuDetailQueryFinished(String type)
			{

			}

			/*@Override
			public void onCheckPurchaseItemForInApp()
			{
				InAppInformation inappInformation;
				String payType = BillingClientHelper.IN_APP_FREE_USER;
				Purchase consumable = mBillingClientHelper.getPurchasedInAppItemResult(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH);
				if(consumable != null)
				{
					// 결제한지 1달이 지낫을때 Cosume시키고 무료회원으로 전환한다.
					payType = BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH;
					Log.f("consumable order ID : "+ consumable.getOrderId());
					Log.f("consumable getItemType : IN APP");
					Log.f("consumable getPurchaseState : "+ consumable.getPurchaseState());
					Log.f("consumable getPurchaseTime Real : "+ consumable.getPurchaseTime());
					Log.f("consumable getPurchaseTime : "+ CommonUtils.getInstance(IntroLoadingActivity.this).getDateTime(consumable.getPurchaseTime()));


					Log.f("Purchase : 1 Month");
					inappInformation = new InAppInformation(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH, consumable.getPurchaseTime(), CommonUtils.getInstance(IntroLoadingActivity.this).getAdded1Month(consumable.getPurchaseTime()));
					CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);

					if(CommonUtils.getInstance(IntroLoadingActivity.this).isOverPayDay(inappInformation.getInAppEndDay()))
					{
						Log.f("Purchase Over 30 days is ended. ==== Consumable");
						mBillingClientHelper.consumeItem(consumable);
						payType = BillingClientHelper.IN_APP_FREE_USER;
					}
				}

				if(payType.equals(BillingClientHelper.IN_APP_FREE_USER))
				{
					Log.f("Not Purchase : Free User");
					Feature.IS_FREE_USER = true;
					InAppInformation inappinformation = new InAppInformation();
					CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappinformation);
				}
				else
				{
					Feature.IS_FREE_USER = false;
					Log.f("Purchase : Paid User");

				}
				mIntroHandler.sendEmptyMessage(MESSAGE_GET_INIT_APP_INFO);
			} */

			@Override
			public void onCheckPurchaseItem()
			{
				InAppInformation inappInformation;

				Purchase consumable = mBillingClientHelper.getPurchasedInAppItemResult(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH);
				if(consumable != null)
				{

					mCurrentPaymentType = BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH;
					Log.f("consumable order ID : " + consumable.getOrderId());
					Log.f("consumable getItemType : IN APP");
					Log.f("consumable getPurchaseState : " + consumable.getPurchaseState());
					Log.f("consumable getPurchaseTime Real : " + consumable.getPurchaseTime());
					Log.f("consumable getPurchaseTime : " + CommonUtils.getInstance(IntroLoadingActivity.this).getDateTime(consumable.getPurchaseTime()));

					mBillingClientHelper.consumeItem(consumable);
				}

				Purchase subscription = mBillingClientHelper.getPurchasedSubsItemResult(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH);
				if(subscription != null)
				{
					mCurrentPaymentType = BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH;
					Log.f("subscription order ID : "+ subscription.getOrderId());
					Log.f("subscription getItemType : SUBS");
					Log.f("subscription getPurchaseState : "+ subscription.getPurchaseState());
					Log.f("subscription getPurchaseTime Real : "+ subscription.getPurchaseTime());
					Log.f("subscription getPurchaseTime : "+ CommonUtils.getInstance(IntroLoadingActivity.this).getDateTime(subscription.getPurchaseTime()));


					Log.f("Purchase : subscription");
					inappInformation = new InAppInformation(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH, subscription.getPurchaseTime(), CommonUtils.getInstance(IntroLoadingActivity.this).getAdded1Month(subscription.getPurchaseTime()));
					CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);

					if(mCurrentPaymentType.equals(BillingClientHelper.IN_APP_FREE_USER))
					{
						Log.f("Not Purchase : Free User");
						Feature.IS_FREE_USER = true;
						InAppInformation inappinformation = new InAppInformation();
						CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappinformation);
					}
					else
					{
						Feature.IS_FREE_USER = false;
						Log.f("Purchase : Paid User");
					}
				}
				mIntroHandler.sendEmptyMessage(MESSAGE_GET_INIT_APP_INFO);
			}

			@Override
			public void onPurchaseComplete(@Nullable Purchase purchaseItem) { }

			@Override
			public void onConsumeComplete(BillingResult billingResult, String purchaseToken) { }

			@Override
			public void onConfirmSubsComplete(BillingResult billingResult) { }
		});
	}



	private void settingLogFile()
	{
		long logfileSize = Log.getLogfileSize();
		Log.f("Log file Size : " + logfileSize);
		if(logfileSize > Common.MAXIMUM_LOG_FILE_SIZE || logfileSize == 0L)
		{
			Log.initWithDeleteFile(Common.LOG_FILE);
		}
	}

	private void startMainActivity()
	{
		MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_MAIN, mInitResult);
		finish();
	}

	private void showTempleteAlertDialog(int type, int buttonType, String message)
	{
		TempleteAlertDialog dialog = new TempleteAlertDialog(this, message);
		dialog.setDialogMessageSubType(type);
		dialog.setButtonText(buttonType);
		dialog.setDialogListener(mDialogListener);
		dialog.show();
	}


	private void requestInitAppInformation()
	{
		Log.f("");
		InitCoroutine initCoroutine = new InitCoroutine(this, mAsyncListener);
		initCoroutine.execute();
	}

	private void requestMainInformation()
	{
		Log.f("");
		MainRequestCoroutine mainRequestCoroutine = new MainRequestCoroutine(this, mAsyncListener);
		mainRequestCoroutine.execute();
	}

	private void requestUserInformation()
	{

		UserInformationRequestCoroutine coroutine = new UserInformationRequestCoroutine(this, mAsyncListener);
		coroutine.execute();
	}

	private MarketVersionCheckListener mMarketVersionCheckListener = new MarketVersionCheckListener()
	{

		@Override
		public void onFail(String exception)
		{
			Log.f("exception : "+exception);
			Message msg = Message.obtain();
			msg.what = MESSAGE_ERROR_FINISH;
			msg.obj = exception;
			mIntroHandler.sendMessage(msg);
		}

		@Override
		public void onSuccess(String marketVersion)
		{
			final int MAX_VERSION_STRING_COUNT = 10;
			Log.f("marketVersion : "+marketVersion+ " , Appversion : " + CommonUtils.getInstance(IntroLoadingActivity.this).getPackageVersionName(Common.PACKAGE_NAME));
			if(marketVersion.trim().equals("")  == false && CommonUtils.getInstance(IntroLoadingActivity.this).isAppVersionEqual(marketVersion) == false && marketVersion.length() < MAX_VERSION_STRING_COUNT)
			{
				showTempleteAlertDialog(DIALOG_EVENT_UPDATE_APP, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_2, CommonUtils.getInstance(IntroLoadingActivity.this).getLanguageTypeString(R.array.message_update_app));
			}
			else
			{
				mIntroHandler.sendEmptyMessage(MESSAGE_INAPP_INFO);
			}
		}

	};

	private DialogListener mDialogListener = new DialogListener()
	{

		@Override
		public void onItemClick(int messageType, Object sendObject)
		{
			/**
			 * 강제 업데이트
			 */
			if(messageType == DIALOG_EVENT_UPDATE_APP)
			{
				finish();
				CommonUtils.getInstance(IntroLoadingActivity.this).startLinkMove(Common.APP_LINK);
			}
		}

		@Override
		public void onItemClick(int messageButtonType, int messageType, Object sendObject)
		{
			/**
			 * 기본 업데이트
			 */
			if(messageType == DIALOG_EVENT_UPDATE_APP)
			{
				switch(messageButtonType)
				{
					case FIRST_BUTTON_CLICK:
						// TODO : 진행
						mIntroHandler.sendEmptyMessage(MESSAGE_GET_USER_INFO);
						break;
					case SECOND_BUTTON_CLICK:
						// TODO : 업데이트 하게 이동
						finish();
						CommonUtils.getInstance(IntroLoadingActivity.this).startLinkMove(Common.APP_LINK);
						break;
				}
			}
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
		public void onErrorListener(String code, String message)
		{
			Message msg = Message.obtain();
			msg.what = MESSAGE_ERROR_FINISH;
			msg.obj = message;
			mIntroHandler.sendMessage(msg);
		}

		@Override
		public void onRunningEnd(String code, Object mObject)
		{
			if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
			{
				if(code == Common.ASYNC_CODE_INIT_APP_INFO_REQUEST)
				{
					updateProgress();
					mInitResult = (InitAppResult) mObject;
					if(checkSystemTime())
					{
						if((CommonUtils.getInstance(IntroLoadingActivity.this).isAppVersionEqual(mInitResult.getAppVersion()) == false)
								&& (mInitResult.getAppVersion().equals("") == false))
						{
							switch(mInitResult.getUpdateType())
							{
								case Common.UPDATE_CRITICAL:
									showTempleteAlertDialog(DIALOG_EVENT_UPDATE_APP, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_1, CommonUtils.getInstance(IntroLoadingActivity.this).getLanguageTypeString(R.array.message_critical_update_app));
									break;
								case Common.UPDATE_NORMAL:
									showTempleteAlertDialog(DIALOG_EVENT_UPDATE_APP, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_2, CommonUtils.getInstance(IntroLoadingActivity.this).getLanguageTypeString(R.array.message_update_app));
									break;
							}
							return;
						}
						mIntroHandler.sendEmptyMessage(MESSAGE_GET_USER_INFO);

					}
					else
					{
						finish();
					}
				}
				else if(code == Common.ASYNC_CODE_USER_INFORMATION_REQUEST)
				{
					updateProgress();
					UserTotalInformationObject object = new UserTotalInformationObject(
							((UserInformationResult)mObject).getUserInformation(),
							((UserInformationResult)mObject).getChildInformationList(),
							((UserInformationResult)mObject).getPaymentStatus());

					if(mCurrentPaymentType.equals(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH) == false)
					{
						UserPaymentStatus status = object.getmUserPaymentStatus();

						if(status == null)
						{
							Log.f("Not Purchase : Free User");
							Feature.IS_FREE_USER = true;
							InAppInformation inappinformation = new InAppInformation();
							CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappinformation);
						}
						else
						{
							Feature.IS_FREE_USER = false;
							Log.f("Purchase : Paid User");
							InAppInformation inappInformation = new InAppInformation(
									BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH,
									Long.valueOf(status.getTransacctionDate()),
									Long.valueOf(status.getExpireDate()));
							CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);
						}
					}


					/**
					 * 로컬에 저장된 현재 사용자의 정보를 서버 정보에 세팅
					 */
					UserTotalInformationObject localUseObject = (UserTotalInformationObject) CommonUtils.getInstance(IntroLoadingActivity.this).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
					if(localUseObject != null)
					{
						object.setCurrentUserChildIndex(localUseObject.getCurrentUserIndex());
					}

					CommonUtils.getInstance(IntroLoadingActivity.this).setPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, object);
					mIntroHandler.sendEmptyMessage(MESSAGE_GET_MAIN_INFO);

				}
				else if(code == Common.ASYNC_CODE_MAIN_INFO_REQUEST)
				{
					updateProgress();
					FileUtils.writeFile(mObject, Common.PATH_APP_ROOT+Common.FILE_MAIN_INFO);
					mIntroHandler.sendEmptyMessageDelayed(MESSAGE_START_MAIN, DURATION_START_MAIN);
				}
			}
			else
			{
				if(code == Common.ASYNC_CODE_INIT_APP_INFO_REQUEST)
				{
					if(((BaseResult)mObject).isAuthenticationBroken())
					{
						finish();
						MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE, true);
						return;
					}
				}
				CommonUtils.getInstance(IntroLoadingActivity.this).finishApplication();
				Toast.makeText(IntroLoadingActivity.this, CommonUtils.getInstance(IntroLoadingActivity.this).getLanguageTypeString(R.array.message_network_error), Toast.LENGTH_LONG).show();
			}
		}
	};


}

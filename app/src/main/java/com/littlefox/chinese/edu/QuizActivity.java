package com.littlefox.chinese.edu;

import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.coroutines.FileDownloadCoroutine;
import com.littlefox.chinese.edu.coroutines.QuizInformationRequestCoroutine;
import com.littlefox.chinese.edu.coroutines.QuizSaveRecordCoroutine;
import com.littlefox.chinese.edu.dialog.TempleteAlertDialog;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.fragment.QuizIntroFragment;
import com.littlefox.chinese.edu.fragment.QuizPlayFragment;
import com.littlefox.chinese.edu.fragment.QuizResultFragment;
import com.littlefox.chinese.edu.object.QuizPictureInformation;
import com.littlefox.chinese.edu.object.QuizStudyRecordObject;
import com.littlefox.chinese.edu.object.QuizTextInformation;
import com.littlefox.chinese.edu.object.QuizUserInteractionObject;
import com.littlefox.chinese.edu.object.result.QuizPictureResult;
import com.littlefox.chinese.edu.object.result.QuizTextResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.object.result.base.QuizBaseObject;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.dialog.MaterialLoadingDialog;
import com.littlefox.library.view.extra.SwipeDisableViewPager;
import com.littlefox.library.view.scroller.FixedSpeedScroller;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends BaseActivity
{
	@BindView(R.id.main_layout)
	LinearLayout _BaseLayout;
	
	@BindView(R.id.quiz_close_button)
	ImageView _QuizCloseButton;
	
	@BindView(R.id.quiz_task_box_layout)
	ScalableLayout _QuizTaskBoxLayout;
	
	@BindView(R.id.quiz_task_question_image)
	ImageView _QuizQuestionImage;
	
	@BindView(R.id.quiz_timer_text)
	TextView _QuizTimerText;
	
	@BindView(R.id.quiz_count_text)
	TextView _QuizAnswerCountText;
	
	@BindView(R.id.quiz_base_fragment)
	SwipeDisableViewPager _QuizDisplayPager;
	
	@BindView(R.id.quiz_ani_icon)
	ImageView _AniAnswerView;
	
	@BindView(R.id.quiz_title)
	TextView _QuizTitleText;
	
	@BindView(R.id.quiz_ani_layout)
	ScalableLayout _AniAnswerLayout;
	
	public interface QuizCallback
	{
		void setOnQuizCommunicateListener(OnQuizCommunicateListener onQuizCommunicateListener);
	}
	
	public interface OnQuizCommunicateListener
	{
		void onPlayQuestionSound();
		void onChoiceItem(boolean isCorrect, QuizUserInteractionObject quizResultObject);
		void onNext();
		void onSaveStudyInformation();
		void onReplay();
	}
	
	Handler mQuizEventHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_REQUEST_FILE_DOWNLOAD:
				requestDownloadFile();
				break;
			case MESSAGE_FILE_DOWNLOAD_ERROR:
				errorDownload();
				break;
			case MESSAGE_REFRESH_TASK_BOX:
				setQuestionTaskInformation();
				break;
			case MESSAGE_PICTURE_QUESTION_SETTING:
				makePictureQuestion(PLAY_INIT);
				break;
			case MESSAGE_TEXT_QUESTION_SETTING:
				makeTextQuestion(PLAY_INIT);
				break;
			case MESSAGE_READY_TO_QUIZ:
				readyToPlay();
				break;
			case MESSAGE_READY_TO_REPLAY:
				readyToReplay();
				break;
			case MESSAGE_QUIZ_LIMIT_END:
				showTempleteAlertDialog(TempleteAlertDialog.DIALOG_EVENT_DEFAULT, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_1, CommonUtils.getInstance(QuizActivity.this).getLanguageTypeString(R.array.message_quiz_limit_end));
				break;
			case MESSAGE_PLAY_SOUND_QUIZ:
				playQuestion(msg.arg1);
				break;
			case MESSAGE_FINISH:
				finish();
				break;
			case MESSAGE_NOT_MATCH_ACCESS_TOKEN:
				MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE, false);
				break;
			case MESSAGE_QUIZ_RESULT_SOUND:
				playResultByQuizCorrect();
				break;
			}
		}
	};
	
	class QuizLimitPlayTask extends TimerTask
	{
		@Override
		public void run()
		{
			mQuizEventHandler.sendEmptyMessage(MESSAGE_REFRESH_TASK_BOX);
		}
	}


    private static final int PLAY_INIT 		= 0;
	private static final int PLAY_REPLAY 	= 1;
	
	private static final int DURATION_VIEW_INIT		= 1000;
	private static final int DURATION_TIMER			= 1000;
	private static final int DURATION_ANIMATION 	= 500;
	
	private static final int QUIZ_IMAGE_WIDTH 	= 479;
	private static final int QUIZ_IMAGE_HEIGHT 	= 361;
	
	private static final int QUIZ_INTRO 				= 0;
	private static final int QUIZ_PLAY					= 1;
	private static final int QUIZ_RESULT				= 2;
	
	private static final int MESSAGE_REQUEST_FILE_DOWNLOAD 			= 0;
	private static final int MESSAGE_FILE_DOWNLOAD_ERROR			= 1;
	private static final int MESSAGE_REFRESH_TASK_BOX				= 2;
	private static final int MESSAGE_PICTURE_QUESTION_SETTING 		= 3;
	private static final int MESSAGE_TEXT_QUESTION_SETTING			= 4;
	private static final int MESSAGE_READY_TO_QUIZ					= 5;
	private static final int MESSAGE_READY_TO_REPLAY				= 6;
	private static final int MESSAGE_QUIZ_LIMIT_END					= 7;
	private static final int MESSAGE_PLAY_SOUND_QUIZ				= 8;
	private static final int MESSAGE_FINISH							= 9;
	private static final int MESSAGE_NOT_MATCH_ACCESS_TOKEN			= 10;
	private static final int MESSAGE_QUIZ_RESULT_SOUND				= 11;
	
	private static final String MEDIA_BASE_PATH ="mp3/";
	private static final String MEDIA_EXCELLENT_PATH 	= MEDIA_BASE_PATH + "quiz_excellent.mp3";
	private static final String MEDIA_VERYGOOD_PATH 	= MEDIA_BASE_PATH + "quiz_verygood.mp3";
	private static final String MEDIA_GOODS_PATH 		= MEDIA_BASE_PATH + "quiz_good.mp3";
	private static final String MEDIA_POOL_PATH 		= MEDIA_BASE_PATH + "quiz_tryagain.mp3";
	private static final String MEDIA_CORRECT_PATH		= MEDIA_BASE_PATH + "quiz_correct.mp3";
	private static final String MEDIA_INCORRECT_PATH	= MEDIA_BASE_PATH + "quiz_incorrect.mp3";
	
	private ArrayList<Fragment> mQuizDisplayFragmentList = new ArrayList<Fragment>();
	
	private String mCurrentQuizType 	= "";
	private String mCurrentContentId 	= "";
	/**
	 * 현재 ViewPager의 Page Index
	 */
	private int mCurrentPageIndex = 0;
	/**
	 * 현재 퀴즈페이지의 Index
	 */
	private int mCurrentQuizPageIndex = -1;
	private int mCorrectAnswerCount = 0;
	private int mQuizLimitTime = 0;
	private MediaPlayer mQuizPassagePlayer;
	private MediaPlayer mQuizEffectPlayer;
	private Timer mQuizPlayTimer;
	private FixedSpeedScroller mFixedSpeedScroller;
	private QuizSelectionPagerAdapter mQuizSelectionPagerAdapter;
	private QuizPictureResult mQuizPictureQuestionResult;
	private QuizTextResult	mQuizTextResult;
	private QuizBaseObject mQuizBaseObject;
	private int mQuizPlayingCount = -1;
	private ArrayList<QuizUserInteractionObject> mQuizResultObjectList;
	private QuizStudyRecordObject mQuizRequestObject;
	private MaterialLoadingDialog mLoadingDialog = null;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_main);
		ButterKnife.bind(this);
		Log.f("");
		mCurrentContentId = getIntent().getStringExtra(Common.INTENT_QUIZ_PARAMS);
		init();
		requestQuizInformation();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_QUIZ);
		Log.f("");
		if(mCurrentQuizPageIndex != -1)
		{
			enableTimer(true);
		}
		
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.f("");
		if(mCurrentQuizPageIndex != -1)
		{
			enableTimer(false);
		}
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

		mQuizEventHandler.removeCallbacksAndMessages(null);
		stopMediaPlay();
		stopEffectPlay();
		Log.f("");
	}
	
	private void init()
	{
		GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_QUIZ, Common.ANALYTICS_ACTION_QUIZ, mCurrentContentId);
		mQuizResultObjectList = new ArrayList<QuizUserInteractionObject>();
		mQuizSelectionPagerAdapter = new QuizSelectionPagerAdapter(getSupportFragmentManager());
		mQuizSelectionPagerAdapter.addFragment(QUIZ_INTRO);
		_QuizDisplayPager.setAdapter(mQuizSelectionPagerAdapter);
		_QuizDisplayPager.addOnPageChangeListener(mOnPageChangeListener);
		mQuizPassagePlayer = new MediaPlayer();
		
		mFixedSpeedScroller = new FixedSpeedScroller(this, new LinearOutSlowInInterpolator());
		mFixedSpeedScroller.setDuration(DURATION_ANIMATION);
		
		try
		{
			Field scroller = ViewPager.class.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			scroller.set(_QuizDisplayPager, mFixedSpeedScroller);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			RelativeLayout.LayoutParams params = (LayoutParams) _QuizDisplayPager.getLayoutParams();
			params.topMargin = CommonUtils.getInstance(this).getPixel(80);
			_QuizDisplayPager.setLayoutParams(params);
			
			
			ScalableLayout.LayoutParams aniParams = _AniAnswerLayout.getChildLayoutParams(_AniAnswerView);
			_AniAnswerLayout.moveChildView(_AniAnswerView, aniParams.getScale_Left(), aniParams.getScale_Top()+80);
		}
		
		initFont();
		
		if(Feature.IS_LANGUAGE_ENG)
		{
			_QuizQuestionImage.setImageResource(R.drawable.icon_question_en);
		}
		else
		{
			_QuizQuestionImage.setImageResource(R.drawable.icon_question);
		}
	}
	
	private ScalableLayout getAddMarginLayout()
	{
		ScalableLayout scalableLayout = new ScalableLayout(this, 1920, 100);
		return scalableLayout;
	}
	
	private void initFont()
	{
		_QuizTitleText.setTypeface(Font.getInstance(this).getRobotoBold());
		_QuizTimerText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_QuizAnswerCountText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	/**
	 * UI 화면을 갱신하는 타이머를 동작시키거나 중지시킨다.
	 * @param isStart
	 */
	private void enableTimer(boolean isStart)
	{
		if(isStart)
		{
			if(mQuizPlayTimer == null)
			{
				mQuizPlayTimer = new Timer();
				mQuizPlayTimer.schedule(new QuizLimitPlayTask(), 0, DURATION_TIMER);
			}
		}
		else
		{
			if(mQuizPlayTimer != null)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			{
				mQuizPlayTimer.cancel();
				mQuizPlayTimer = null;
			}
		}
	}
	
	/**
	 * 퀴즈의 현재 상태를 계속 표시해주는 메소드 (남은시간, 몇개 맞췃는지)
	 */
	private void setQuestionTaskInformation()
	{
		mQuizLimitTime--;
		
		if(mQuizLimitTime >= 0)
		{
			_QuizTimerText.setText(CommonUtils.getInstance(this).getSecondTime(mQuizLimitTime));
		}
		else
		{
			mCurrentQuizPageIndex = -1;
			enableTimer(false);
			Log.f("Quiz End Not All Solved. Limit Time");
			_AniAnswerView.setVisibility(View.GONE);
			((QuizResultFragment)mQuizDisplayFragmentList.get(mQuizDisplayFragmentList.size() -1)).setResultInformation(mQuizPlayingCount,  mCorrectAnswerCount );
			_QuizDisplayPager.setCurrentItem(mQuizDisplayFragmentList.size() -1, true);
			mQuizEventHandler.sendEmptyMessageDelayed(MESSAGE_QUIZ_LIMIT_END, DURATION_ANIMATION);
			mQuizEventHandler.sendEmptyMessageDelayed(MESSAGE_QUIZ_RESULT_SOUND, DURATION_ANIMATION);
			
		}
	}
	
	private void stopMediaPlay()
	{
		if(mQuizPassagePlayer.isPlaying())
		{
			mQuizPassagePlayer.stop();
		}
	}
	
	private void playMediaPlay(String uri)
	{
		
		if(mQuizPassagePlayer.isPlaying())
		{
			return;
		}
		else
		{
			mQuizPassagePlayer.stop();
			mQuizPassagePlayer.release();
			mQuizPassagePlayer = null;
		}
		
		mQuizPassagePlayer = new MediaPlayer();
		mQuizPassagePlayer.reset();
		try
		{
			mQuizPassagePlayer.setDataSource(this, Uri.parse(uri));
			mQuizPassagePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mQuizPassagePlayer.prepare();
			mQuizPassagePlayer.start();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	

	
	private void playQuestion(int index)
	{
		if(mCurrentQuizType.equals(Common.QUIZ_CODE_PICTURE))
		{
			playMediaPlay(mQuizPictureQuestionResult.getQuestionList().get(index).getSoundUrl());
		}
		else if(mCurrentQuizType.equals(Common.QUIZ_CODE_SOUND_TEXT))
		{
			
		}
	}
	
	/**
	 * PlayFragment에 전달할 이미지 문제를 만드는 메소드. 각각의 Play Fragment는 하나의 문제로 간주한다.
	 * @param type PLAY_INIT : 퀴즈에 첫진입 , PLAY_REPLAY : 퀴즈를 재 풀기
	 */
	private void makePictureQuestion(int type)
	{		
		int maxQuestionCount = mQuizPictureQuestionResult.getQuestionList().size();
		int correctQuestionIndex = - 1;
		int randImageIndex = - 1;
		QuizPictureInformation mQuizPictureInformation;
		ArrayList<QuizPictureInformation> mPictureQuizList = new ArrayList<QuizPictureInformation>();

		Collections.shuffle(mQuizPictureQuestionResult.getQuestionList(), new Random(System.nanoTime()));
		
		Bitmap correctBitmap = BitmapFactory.decodeFile(Common.PATH_QUIZ_INFO + mQuizPictureQuestionResult.getCorrectImageFileName());
		ArrayList<Bitmap> mCorrectImageList = new ArrayList<Bitmap>();
		Bitmap incorrectBitmap = null;
		for(int i = 0 ; i < maxQuestionCount; i ++)
		{
			mCorrectImageList.add(Bitmap.createBitmap(correctBitmap, 0, i * QUIZ_IMAGE_HEIGHT, QUIZ_IMAGE_WIDTH, QUIZ_IMAGE_HEIGHT));
		}
		
		if (mQuizPictureQuestionResult.getInCorrectImageUrl().equals(""))
		{
			Log.f("maxQuestionCount : "+maxQuestionCount);
			for (int i = 0; i < maxQuestionCount; i++)
			{
				correctQuestionIndex = mQuizPictureQuestionResult.getQuestionList().get(i).getCorrectIndex();
				randImageIndex =  getRandomNumber(maxQuestionCount, correctQuestionIndex);
				mQuizPictureInformation = new QuizPictureInformation(i, mQuizPictureQuestionResult.getQuestionList().get(i).getTitle(), mCorrectImageList.get(correctQuestionIndex), mCorrectImageList.get(randImageIndex));
				mQuizPictureInformation.setRecordQuizValue(correctQuestionIndex, randImageIndex);
				mPictureQuizList.add(mQuizPictureInformation);
				
				Log.f("questionIndex : "+i);
				Log.f("correctQuestionIndex : "+correctQuestionIndex);
				Log.f("randImageIndex : "+randImageIndex);
				Log.f("title : "+mQuizPictureQuestionResult.getQuestionList().get(i).getTitle());
				
			}
		}
		else
		{
			incorrectBitmap = BitmapFactory.decodeFile(Common.PATH_QUIZ_INFO + mQuizPictureQuestionResult.getInCorrectImageFileName());
			
			Bitmap incorrectPieceBitmap = null;
			for (int i = 0; i < maxQuestionCount; i++)
			{
				correctQuestionIndex = mQuizPictureQuestionResult.getQuestionList().get(i).getCorrectIndex();
				
				if(mQuizPictureQuestionResult.getQuestionList().get(i).getInCorrectIndex() == -1)
				{
					randImageIndex = getRandomNumber(maxQuestionCount, correctQuestionIndex);
					Log.f("CorrectIndex : "+ correctQuestionIndex+", i : " + i +", randImageIndex : "+randImageIndex);
					mQuizPictureInformation = new QuizPictureInformation(i, mQuizPictureQuestionResult.getQuestionList().get(i).getTitle(),mCorrectImageList.get(correctQuestionIndex), mCorrectImageList.get(randImageIndex));
					mQuizPictureInformation.setRecordQuizValue(correctQuestionIndex, randImageIndex);
					mPictureQuizList.add(mQuizPictureInformation);
				}
				else
				{
					incorrectPieceBitmap = Bitmap.createBitmap(incorrectBitmap, 0, mQuizPictureQuestionResult.getQuestionList().get(i).getInCorrectIndex() * QUIZ_IMAGE_HEIGHT, QUIZ_IMAGE_WIDTH, QUIZ_IMAGE_HEIGHT);
					mQuizPictureInformation = new QuizPictureInformation(i, mQuizPictureQuestionResult.getQuestionList().get(i).getTitle(),mCorrectImageList.get(correctQuestionIndex), incorrectPieceBitmap);
					mQuizPictureInformation.setRecordQuizValue(correctQuestionIndex, mQuizPictureQuestionResult.getQuestionList().get(i).getInCorrectIndex());
					mPictureQuizList.add(mQuizPictureInformation);
				}
			}
			
			incorrectPieceBitmap = null;
		}
		
		
		// BITMAP RECYCLE
		correctBitmap = null;
		mCorrectImageList.clear();
		mCorrectImageList = null;
		incorrectBitmap = null;
		
		

		for(int i = 0 ; i < mPictureQuizList.size(); i++)
		{
			mQuizSelectionPagerAdapter.addFragment(QUIZ_PLAY, mPictureQuizList.get(i));
		}
		mQuizSelectionPagerAdapter.addFragment(QUIZ_RESULT);

		switch(type)
		{
		case PLAY_INIT:
			mQuizEventHandler.sendEmptyMessage(MESSAGE_READY_TO_QUIZ);
			break;
		case PLAY_REPLAY:
			mQuizEventHandler.sendEmptyMessage(MESSAGE_READY_TO_REPLAY);
			break;
		}
	}
	
	/**
	 * PlayFragment에 전달할 텍스트 문제를 만드는 메소드. 각각의 Play Fragment는 하나의 문제로 간주한다.
	 * @param type PLAY_INIT : 퀴즈에 첫진입 , PLAY_REPLAY : 퀴즈를 재 풀기
	 */
	private void makeTextQuestion(int type)
	{
		int maxQuestionCount = mQuizTextResult.getQuestionList().size();
		ArrayList<QuizTextInformation> mTextInformationList = new ArrayList<QuizTextInformation>();

		Collections.shuffle(mQuizTextResult.getQuestionList(), new Random(System.nanoTime()));
		
		for(int i = 0 ; i < maxQuestionCount; i++)
		{
			mTextInformationList.add(new QuizTextInformation(i, mQuizTextResult.getQuestionList().get(i).getQuestionIndex(), mQuizTextResult.getQuestionList().get(i)));
		}
		
		for(int i = 0 ; i < maxQuestionCount; i++)
		{
			mQuizSelectionPagerAdapter.addFragment(QUIZ_PLAY, mTextInformationList.get(i));
		}
		mQuizSelectionPagerAdapter.addFragment(QUIZ_RESULT);

		
		switch(type)
		{
		case PLAY_INIT:
			mQuizEventHandler.sendEmptyMessage(MESSAGE_READY_TO_QUIZ);
			break;
		case PLAY_REPLAY:
			mQuizEventHandler.sendEmptyMessage(MESSAGE_READY_TO_REPLAY);
			break;
		}
		
	}
	
	/**
	 * 특정 숫자를 제외한 랜덤 숫자를 부여한다.
	 * @param maxCount 0~ maxCount
	 * @param exceptNumber 제외할 숫자
	 * @return
	 */
	private int getRandomNumber(int maxCount, int exceptNumber)
	{
		int seedNum = -1;
		Random rand = new Random(System.nanoTime());
		do
		{
			seedNum = rand.nextInt(maxCount);
		}while(seedNum == exceptNumber);
		
		return seedNum;
	}
	
	private void requestQuizInformation()
	{
		//QuizInformationRequestAsync requestAsync = new QuizInformationRequestAsync(this, mCurrentContentId, mQuizRequestListener);
		//requestAsync.execute();
		QuizInformationRequestCoroutine coroutine = new QuizInformationRequestCoroutine(this, mQuizRequestListener);
		coroutine.setData(mCurrentContentId);
		coroutine.execute();

	}
	
	/**
	 * 이미지 파일을 받는 메소드
	 */
	private void requestDownloadFile()
	{
		ArrayList<String> downloadUrlList = new ArrayList<String>();
		ArrayList<String> fileSavePathList = new ArrayList<String>();
		downloadUrlList.add(mQuizPictureQuestionResult.getCorrectImageUrl());
		
		fileSavePathList.add(Common.PATH_QUIZ_INFO+mQuizPictureQuestionResult.getCorrectImageFileName());
		
		if(mQuizPictureQuestionResult.getInCorrectImageUrl().equals("") == false)
		{
			downloadUrlList.add(mQuizPictureQuestionResult.getInCorrectImageUrl());
			fileSavePathList.add(Common.PATH_QUIZ_INFO+mQuizPictureQuestionResult.getInCorrectImageFileName());
		}

		//FileDownloadAsync downloadAsync = new FileDownloadAsync(this, downloadUrlList, fileSavePathList , mFileDownloadListener);
		//downloadAsync.execute();
		FileDownloadCoroutine coroutine = new FileDownloadCoroutine(this, mFileDownloadListener);
		coroutine.setData(downloadUrlList, fileSavePathList);
		coroutine.execute();
	}
	
    private void showTempleteAlertDialog(int type, int buttonType, String message)
    {
    	TempleteAlertDialog dialog = new TempleteAlertDialog(this, message);
    	dialog.setDialogMessageSubType(type);
    	dialog.setButtonText(buttonType);
    	dialog.show();
    }
    
	private void showLoading()
	{
		if(mLoadingDialog == null)
		{
			mLoadingDialog = new MaterialLoadingDialog(this, CommonUtils.getInstance(this).getPixel(200), getResources().getColor(R.color.color_d8232a));
		}
		
		mLoadingDialog.show();
	}
	
	private void dismissLoading()
	{
		if(mLoadingDialog != null)
		{
			mLoadingDialog.dismiss();
		}
		
		mLoadingDialog = null;
		
	}
	
	/**
	 * 다운로드 실패시 메세지를 띠우고 종료
	 */
	private void errorDownload()
	{
		Toast.makeText(this, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_file_download_error), Toast.LENGTH_SHORT).show();
		finish();
	}
	
	private void readyToPlay()
	{
		((QuizIntroFragment)mQuizDisplayFragmentList.get(QUIZ_INTRO)).loadingComplete();
		initTaskBoxInformation();
	}
	
	private void readyToReplay()
	{
		stopEffectPlay();
		initTaskBoxInformation();
		setQuizPlayStatus();
	}
	
	private void initTaskBoxInformation() 
	{
		try
		{
			mCorrectAnswerCount = 0;
			mQuizLimitTime = mQuizBaseObject.getTimeLimit();
			_QuizTimerText.setTypeface(Font.getInstance(this).getRobotoMedium());
			_QuizAnswerCountText.setTypeface(Font.getInstance(this).getRobotoMedium());
			_QuizTimerText.setText(CommonUtils.getInstance(this).getSecondTime(mQuizLimitTime));
			_QuizAnswerCountText.setText(String.valueOf(mCorrectAnswerCount)+"/"+ String.valueOf(mQuizPlayingCount));
		}catch(NullPointerException e)
		{
			
		}

	}
	
	private void setQuizAnswerCountText()
	{
		_QuizAnswerCountText.setText(String.valueOf(mCorrectAnswerCount)+"/"+ String.valueOf(mQuizPlayingCount));
	}
	
	private void setQuizPlayStatus()
	{
		mCurrentQuizPageIndex++;
		Log.f("Quiz Page : "+mCurrentQuizPageIndex +" , Total Quiz Count : "+ mQuizPlayingCount +", Correct Count : "+mCorrectAnswerCount);
		if(mCurrentQuizPageIndex == 0 )
		{
			mQuizResultObjectList.clear();
			enableTimer(true);
		}
		else if(mCurrentQuizPageIndex == mQuizPlayingCount)
		{
			enableTimer(false);
			((QuizResultFragment)mQuizDisplayFragmentList.get(mQuizDisplayFragmentList.size() -1)).setResultInformation(mQuizPlayingCount,  mCorrectAnswerCount );
			mQuizEventHandler.sendEmptyMessageDelayed(MESSAGE_QUIZ_RESULT_SOUND, DURATION_ANIMATION);
			mCurrentQuizPageIndex = -1;
		}
		_AniAnswerView.setVisibility(View.GONE);
		_QuizDisplayPager.setCurrentItem(mCurrentPageIndex+1, true);
	}
	
	private void stopEffectPlay()
	{
		if(mQuizEffectPlayer != null)
		{
			mQuizEffectPlayer.stop();
			mQuizEffectPlayer.release();
			mQuizEffectPlayer = null;
		}
	}
	
	private void showAnswerAnimation(boolean isCorrect)
	{
		ObjectAnimator animatorRotation;
		_AniAnswerView.setVisibility(View.VISIBLE);
		if(isCorrect)
		{
			playEffectSound(MEDIA_CORRECT_PATH);
			if(Feature.IS_LANGUAGE_ENG)
			{
				_AniAnswerView.setImageResource(R.drawable.correct_img_en);
			}
			else
			{
				_AniAnswerView.setImageResource(R.drawable.correct_img);
			}
			
		}
		else
		{
			playEffectSound(MEDIA_INCORRECT_PATH);
			_AniAnswerView.setImageResource(R.drawable.incorrect_img);
		}
		_AniAnswerView.setRotationY(0f);
		animatorRotation = ObjectAnimator.ofFloat(_AniAnswerView, "rotationY", 360f);
		
		animatorRotation.setDuration(500);
		animatorRotation.start();
	}
	
	
	/**
	 * 퀴즈의 결과에 대한 음성을 들려준다. 맞춘 개수에 따라
	 */
	private void playResultByQuizCorrect()
	{
		switch(CommonUtils.getInstance(this).getMyGrade(mQuizPlayingCount, mCorrectAnswerCount))
		{
		case Common.GRADE_EXCELLENT:
			playEffectSound(MEDIA_EXCELLENT_PATH);
			break;
		case Common.GRADE_VERYGOOD:
			playEffectSound(MEDIA_VERYGOOD_PATH);
			break;
		case Common.GRADE_GOODS:
			playEffectSound(MEDIA_GOODS_PATH);
			break;
		case Common.GRADE_POOL:
			playEffectSound(MEDIA_POOL_PATH);
			break;
		}
	}
	
	private void playEffectSound(String type)
	{
		Log.f("type : "+type);
		AssetFileDescriptor afd;
		try
		{
			afd = getAssets().openFd(type);
			
			if(mQuizEffectPlayer != null)
			{
				mQuizEffectPlayer.stop();
				mQuizEffectPlayer.release();
				mQuizEffectPlayer = null;
			}
			
			mQuizEffectPlayer = new MediaPlayer();
			mQuizEffectPlayer.reset();
			mQuizEffectPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			afd.close();
			mQuizEffectPlayer.prepare();
			mQuizEffectPlayer.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void requestQuizSaveRecord()
	{
		showLoading();
		//QuizSaveRecordAsync async = new QuizSaveRecordAsync(this, mQuizRequestObject, mQuizRequestListener);
		//async.execute();
		QuizSaveRecordCoroutine coroutine = new QuizSaveRecordCoroutine(this, mQuizRequestListener);
		coroutine.setData(mQuizRequestObject);
		coroutine.execute();
	}
	
	@OnClick(R.id.quiz_close_button)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.quiz_close_button:
			Log.f("Quiz Close Button Click");
			finish();
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
			mCurrentPageIndex = position;
			Log.i("mCurrentQuizPageIndex : "+mCurrentQuizPageIndex);
			if(mCurrentQuizPageIndex == -1)
			{
				_QuizTaskBoxLayout.setVisibility(View.GONE);
			}
			else
			{
				_QuizTaskBoxLayout.setVisibility(View.VISIBLE);
				
				switch(mCurrentQuizType)
				{
				case Common.QUIZ_CODE_PICTURE:
					Message msg = Message.obtain();
					msg.what 	= MESSAGE_PLAY_SOUND_QUIZ;
					msg.arg1 	= mCurrentQuizPageIndex;
					mQuizEventHandler.sendMessageDelayed(msg, DURATION_ANIMATION);
					break;
				}
			}	
		}
		
	};
	
	private OnQuizCommunicateListener mOnQuizCommunicateListener = new OnQuizCommunicateListener()
	{
		
		@Override
		public void onPlayQuestionSound()
		{
			playQuestion(mCurrentQuizPageIndex);
		}

		
		@Override
		public void onNext()
		{
			stopMediaPlay();
			setQuizPlayStatus();
		}

		@Override
		public void onChoiceItem(boolean isCorrect,  QuizUserInteractionObject quizResultObject)
		{
			mQuizResultObjectList.add(quizResultObject);
			mCorrectAnswerCount = isCorrect == true ? mCorrectAnswerCount + 1 : mCorrectAnswerCount;
			setQuizAnswerCountText();
			showAnswerAnimation(isCorrect);
		}

		@Override
		public void onSaveStudyInformation()
		{
			if(mQuizLimitTime <= 0)
			{
			showTempleteAlertDialog(TempleteAlertDialog.DIALOG_EVENT_DEFAULT, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_1, CommonUtils.getInstance(QuizActivity.this).getLanguageTypeString(R.array.message_quiz_limit_not_save));
			}
			else
			{
				mQuizRequestObject = new QuizStudyRecordObject(mCurrentContentId, mQuizBaseObject.getQuizId(), String.valueOf(mQuizPlayingCount), String.valueOf(mCorrectAnswerCount), mQuizResultObjectList);
				requestQuizSaveRecord();
			}
			
		}

		@Override
		public void onReplay()
		{
			switch(mCurrentQuizType)
			{
			case Common.QUIZ_CODE_PICTURE:
				makePictureQuestion(PLAY_REPLAY);
				break;
			case Common.QUIZ_CODE_TEXT:
				makeTextQuestion(PLAY_REPLAY);
				break;
			}
		}

	
	};
	
	private AsyncListener mFileDownloadListener = new AsyncListener()
	{
		boolean isError = false;
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
			isError = true;
			mQuizEventHandler.sendEmptyMessage(MESSAGE_FILE_DOWNLOAD_ERROR);
		}
		
		@Override
		public void onRunningEnd(String code, Object mObject)
		{
			if (isError == false)
			{
				mQuizEventHandler.sendEmptyMessage(MESSAGE_PICTURE_QUESTION_SETTING);
			}

		}
	};
	
	private AsyncListener mQuizRequestListener = new AsyncListener()
	{

		@Override
		public void onRunningStart(String code) { }

		@Override
		public void onRunningCanceled(String code) { }

		@Override
		public void onRunningProgress(String code, Integer progress) { }

		@Override
		public void onRunningAdvanceInformation(String code, Object object)
		{
			if(code == Common.ASYNC_CODE_QUIZ_INFORMATION)
			{
				mCurrentQuizType = (String) object;
			}
		}

		@Override
		public void onErrorListener(String code, String message){}
		@Override
		public void onRunningEnd(String code, Object mObject)
		{
			if(code == Common.ASYNC_CODE_QUIZ_INFORMATION)
			{
				if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
				{
					mQuizBaseObject = (QuizBaseObject) mObject;
					Log.f("Content Quiz Id Question : "+mQuizBaseObject.getFCId());
					switch(mCurrentQuizType)
					{
					case Common.QUIZ_CODE_PICTURE:
						mQuizPictureQuestionResult = (QuizPictureResult) mObject;
						mQuizPlayingCount = mQuizPictureQuestionResult.getQuestionList().size();
						mQuizEventHandler.sendEmptyMessage(MESSAGE_REQUEST_FILE_DOWNLOAD);
						Log.f("Image Question - "+mQuizPictureQuestionResult.getName());
						break;
					case Common.QUIZ_CODE_SOUND_TEXT:
						break;
					case Common.QUIZ_CODE_TEXT:
						mQuizTextResult = (QuizTextResult)mObject;
						mQuizPlayingCount = mQuizTextResult.getQuestionList().size();
						mQuizEventHandler.sendEmptyMessage(MESSAGE_TEXT_QUESTION_SETTING);
						Log.f("Text Question - "+ mQuizTextResult.getName());
						break;
					}
					((QuizIntroFragment)mQuizDisplayFragmentList.get(QUIZ_INTRO)).setTitle(mQuizBaseObject.getName(), mQuizBaseObject.getSubName());
					
				}
				else
				{
					Toast.makeText(QuizActivity.this,((BaseResult)mObject).getMessage() , Toast.LENGTH_LONG).show();
					if(((BaseResult)mObject).isAuthenticationBroken())
					{
						mQuizEventHandler.sendEmptyMessageDelayed(MESSAGE_NOT_MATCH_ACCESS_TOKEN, DURATION_VIEW_INIT);
					}
					else
					{
						mQuizEventHandler.sendEmptyMessageDelayed(MESSAGE_FINISH, DURATION_VIEW_INIT);
					}
				}
				
			}
			else if(code == Common.ASYNC_CODE_QUIZ_SAVE_RECORD)
			{
				dismissLoading();
				
				if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
				{
					showTempleteAlertDialog(TempleteAlertDialog.DIALOG_EVENT_DEFAULT, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_1, CommonUtils.getInstance(QuizActivity.this).getLanguageTypeString(R.array.message_quiz_save_record_success));
				}
				else
				{
					showTempleteAlertDialog(TempleteAlertDialog.DIALOG_EVENT_DEFAULT, TempleteAlertDialog.DEFAULT_BUTTON_TYPE_1, ((BaseResult)mObject).getMessage());
				}
			}
			
			
		}

	};

	
	private class QuizSelectionPagerAdapter extends FragmentStatePagerAdapter
	{
		
		public QuizSelectionPagerAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
			mQuizDisplayFragmentList = new ArrayList<Fragment>();
		}
		
		public void addFragment(int type)
		{
			addFragment(type, null);
		}
		
		public void addFragment(int type, Object object)
		{
			Fragment fragment = null;
			
			switch(type)
			{
			case QUIZ_INTRO:
				fragment = QuizIntroFragment.getInstance();
				((QuizCallback)fragment).setOnQuizCommunicateListener(mOnQuizCommunicateListener);
				break;
			case QUIZ_PLAY:
				fragment = QuizPlayFragment.getInstance();
				if(object != null)
				{
					if(mCurrentQuizType.equals(Common.QUIZ_CODE_PICTURE))
					{
						((QuizPlayFragment)fragment).setQuestionItemObject(Common.QUIZ_CODE_PICTURE,object);
					}
					else if(mCurrentQuizType.equals(Common.QUIZ_CODE_TEXT))
					{
						((QuizPlayFragment)fragment).setQuestionItemObject(Common.QUIZ_CODE_TEXT,object);
					}
					else if(mCurrentQuizType.equals(Common.QUIZ_CODE_SOUND_TEXT))
					{
						
					}
				}
				((QuizCallback)fragment).setOnQuizCommunicateListener(mOnQuizCommunicateListener);
				break;
			case QUIZ_RESULT:
				fragment = QuizResultFragment.getInstance();
				((QuizCallback)fragment).setOnQuizCommunicateListener(mOnQuizCommunicateListener);
				break;
			}
			mQuizDisplayFragmentList.add(fragment);
			notifyDataSetChanged();
		}
		

		@Override
		public int getCount()
		{
			return mQuizDisplayFragmentList.size();
		}


		@Override
		public Fragment getItem(int position)
		{
			return mQuizDisplayFragmentList.get(position);
		}
		
		@Override
        public CharSequence getPageTitle(int position) 
		{
            return null;
        }
		
	}


	
}

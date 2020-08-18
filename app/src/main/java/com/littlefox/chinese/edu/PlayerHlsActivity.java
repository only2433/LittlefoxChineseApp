package com.littlefox.chinese.edu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.littlefox.chinese.edu.adapter.PlayerListAdapter;
import com.littlefox.chinese.edu.adapter.PlayerSpeedListAdapter;
import com.littlefox.chinese.edu.adapter.listener.PlayerEventListener;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.coroutines.AuthContentPlayCoroutine;
import com.littlefox.chinese.edu.coroutines.PlayerSaveRecordCoroutine;
import com.littlefox.chinese.edu.database.PlayedContentDBHelper;
import com.littlefox.chinese.edu.dialog.TempleteAlertDialog;
import com.littlefox.chinese.edu.dialog.listener.DialogListener;
import com.littlefox.chinese.edu.enumItem.PlayerStatus;
import com.littlefox.chinese.edu.enumItem.PlayerUserType;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.PlayerStudyRecordObject;
import com.littlefox.chinese.edu.object.result.AuthContentResult;
import com.littlefox.chinese.edu.object.result.CaptionDetailInformation;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.controller.FadeAnimationController;
import com.littlefox.library.view.controller.FadeAnimationInformation;
import com.littlefox.library.view.layoutmanager.LinearLayoutScrollerManager;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class PlayerHlsActivity extends BaseActivity
{
    @BindView(R.id._playerView)
    PlayerView _PlayerView;

    @BindView(R.id.player_background)
    ImageView _BackgroundDiscoverImage;

    @BindView(R.id.progress_wheel_layout)
    ScalableLayout _LoadingLayout;

    @BindView(R.id.player_top_base_layout)
    ScalableLayout _TopViewLayout;

    @BindView(R.id.player_top_title)
    TextView _TopTitleText;

    @BindView(R.id.player_subtitle_button)
    ImageView _TopCaptionSettingButton;

    @BindView(R.id.player_close_button)
    ImageView _TopCloseButton;

    @BindView(R.id.player_bottom_base_layout)
    ScalableLayout _BottomViewLayout;

    @BindView(R.id.player_current_play_time)
    TextView _CurrentPlayTimeText;

    @BindView(R.id.seekbar_play)
    SeekBar _ThumbSeekbar;

    @BindView(R.id.player_remain_play_time)
    TextView _RemainPlayTimeText;

    @BindView(R.id.player_lock_button)
    ImageView _LockButton;

    @BindView(R.id.player_play_button_layout)
    ScalableLayout _PlayButtonLayout;

    @BindView(R.id.player_prev_button)
    ImageView _PrevButton;

    @BindView(R.id.player_play_button)
    ImageView _PlayButton;

    @BindView(R.id.player_next_button)
    ImageView _NextButton;

    @BindView(R.id.player_preview_layout)
    ScalableLayout _PreviewLayout;

    @BindView(R.id.player_preview_title)
    TextView _PreviewProgressText;

    @BindView(R.id.preview_end_layout)
    RelativeLayout _BasePreviewEndLayout;

    @BindView(R.id.preview_end_message_sign_up)
    TextView _PreviewSignMessageText;

    @BindView(R.id._previewBackgroundRect)
    ImageView _PreviewPayButton;

    @BindView(R.id.preview_pay_text)
    TextView _PreviewPayButtonText;

    @BindView(R.id.play_end_layout)
    RelativeLayout _BasePlayEndLayout;

    @BindView(R.id.play_end_close_button)
    ImageView _PlayEndCloseButton;

    @BindView(R.id.play_end_base_layout)
    ScalableLayout _PlayEndButtonLayout;

    @BindView(R.id.play_end_replay_layout)
    ScalableLayout _PlayEndReplayButton;

    @BindView(R.id.play_end_replay_text)
    TextView _PlayEndReplayText;

    @BindView(R.id.play_end_quiz_layout)
    ScalableLayout _PlayEndQuizButton;

    @BindView(R.id.play_end_quiz_text)
    TextView _PlayEndQuizText;

    @BindView(R.id.play_end_remain_play_layout)
    ScalableLayout _PlayEndRemainButton;

    @BindView(R.id.play_end_remain_play_text)
    TextView _PlayEndRemainText;

    @BindView(R.id.play_end_recommand_layout)
    ScalableLayout _PlayEndRecommandViewLayout;

    @BindView(R.id.play_recommand_title)
    TextView _PlayEndRecommandTitleText;

    @BindView(R.id.play_recommand_thumbnail)
    ImageView _PlayEndRecommandThumbnailImage;

    @BindView(R.id.player_caption_layout)
    ScalableLayout _CaptionLayout;

    @BindView(R.id.player_caption_title)
    TextView _CaptionTitleText;

    @BindView(R.id._playerListBaseLayout)
    LinearLayout _PlayerListBaseLayout;

    @BindView(R.id._playerListView)
    RecyclerView _PlayerListView;

    @BindView(R.id._playerListButton)
    ImageView _PlayerListButton;

    @BindView(R.id._playerSpeedListBaseLayout)
    LinearLayout _PlayerSpeedListBaseLayout;

    @BindView(R.id._playerSpeedListView)
    RecyclerView _PlayerSpeedListView;

    @BindView(R.id._playerSpeedButton)
    ImageView _PlayerSpeedButton;

    @BindView(R.id._playerSpeedText)
    TextView _PlayerSpeedText;

    @BindView(R.id._playerOptionBackground)
    ImageView _PlayerOptionBackground;

    @BindView(R.id._playListTitleText)
    TextView _PlayListTitleText;

    @BindView(R.id._playSpeedListTitleText)
    TextView _PlaySpeedListTitleText;

    class WarningWatchMessageTask extends TimerTask
    {
        @Override
        public void run()
        {
            mCurrentWatchingTime += SECOND;
            if (mCurrentWatchingTime >= MAX_WARNING_WATCH_MOVIE_TIME) {
                mMainHandler.sendEmptyMessage(MESSAGE_LONGTIME_WATCH_WARNING);
            }
        }
    }

    class MovieLoadingExceptionTask extends TimerTask
    {
        @Override
        public void run()
        {
            if(mMovieLoadingTime > MAX_MOVIE_LOADING_TIME)
            {
                Log.f("File Loading Fail or Network Warning : "+ mCurrentPlayUrl);
                mMainHandler.sendEmptyMessage(MESSAGE_FAIL_MOVIE_LOADING);
                enableMovieLoadingCheckTimer(false);
            }
            else
            {
                mMovieLoadingTime++;
            }
        }
    }

    class StudyTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            mStudyTime++;
        }
    }

    class UiTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            if(Feature.IS_FREE_USER)
            {
                mMainHandler.sendEmptyMessage(MESSAGE_UPDATE_FREE_UI);
            }
            else
            {
                mMainHandler.sendEmptyMessage(MESSAGE_UPDATE_PAID_UI);
            }
        }
    }

    Handler mMainHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case MESSAGE_PROGRESS_UI:
                    updateUI();
                    break;
                case MESSAGE_LONGTIME_WATCH_WARNING:
                    enableTimer(false);
                    mPlayer.setPlayWhenReady(false);
                    setPlayIconStatus(PLAYER_PAUSE);

                    showTempleteAlertDialog(DIALOG_LONG_WATCH_TIME_WARNING,
                            TempleteAlertDialog.DEFAULT_BUTTON_TYPE_2,
                            CommonUtils.getInstance(PlayerHlsActivity.this).getLanguageTypeString(R.array.message_longtime_play_warning));

                    break;
                case MESSAGE_PREV_PLAY:
                    prepareVideo(VIDEO_PREV_PLAY);
                    mPlayerListAdapter.setCurrentPlayPosition(mCurrentPlayPosition);
                    break;
                case MESSAGE_NEXT_PLAY:
                    prepareVideo(VIDEO_NEXT_PLAY);
                    mPlayerListAdapter.setCurrentPlayPosition(mCurrentPlayPosition);
                    break;
                case MESSAGE_SELECT_PLAY:
                    prepareVideo(VIDEO_SELECT_PLAY);
                    break;
                case MESSAGE_LAYOUT_SETTING:
                    settingLayout(msg.arg1);
                    break;
                case MESSAGE_VIDEO_VISIBLE:
                    isBackgroundVisibleError = false;
                    _BackgroundDiscoverImage.setVisibility(View.GONE);
                    break;
                case MESSAGE_LOCK_MODE_READY:
                    mVibrator.vibrate(DURATION_PLAY);
                    setAnimationMenu(false);
                    isLockDisplay = (Boolean) msg.obj;
                    Message subMessage = Message.obtain();
                    subMessage.what = MESSAGE_LOCK_MODE_SET;
                    subMessage.obj = msg.obj;
                    mMainHandler.sendMessageDelayed(subMessage, DURATION_VIEW_ANIMATION);
                    break;
                case MESSAGE_LOCK_MODE_SET:
                    setLockModeUI();
                    setAnimationMenu(true);
                    break;
                case MESSAGE_UPDATE_PAID_UI:
                    updateUI();
                    break;
                case MESSAGE_UPDATE_FREE_UI:
                    updatePreviewUI();
                    break;
                case MESSAGE_QUIZ_START:
                    MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_QUIZ, mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).fc_id);
                    break;
                case MESSAGE_FINISH:
                    finish();
                    break;
                case MESSAGE_NOT_MATCH_ACCESS_TOKEN:
                    finish();
                    MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE, false);
                    break;
                case MESSAGE_AUTH_CONTENT_PLAY:
                    requestCurrentPlayVideoUrlInformation();
                    break;
                case MESSAGE_FAIL_MOVIE_LOADING:
                    finish();
                    Toast.makeText(PlayerHlsActivity.this, CommonUtils.getInstance(PlayerHlsActivity.this).getLanguageTypeString(R.array.message_movie_loading_fail), Toast.LENGTH_LONG).show();
                    break;
                case MESSAGE_CAPTION_LAYOUT_SETTING:
                    settingCaptionButton(true);
                    setCaptionIconStatus();
                    initCaptionLayoutView();
                    break;
            }
        }
    };

    private static final int SECOND 						= 1000;
    private static final int DURATION_LOCK_MODE 			= 2000;
    private static final int DURATION_PLAY 					= 500;
    private static final int DURATION_GONE_BACKGROUND 		= 1000;
    private static final int DUARAION_RESUME_MENU 			= 300;

    //1시간이 지나면 팝업을 띄워 확인 작업
    public static final int MAX_WARNING_WATCH_MOVIE_TIME = 60 * 60 * SECOND;

    private static final int VIDEO_INIT_PLAY 	= 0;
    private static final int VIDEO_PREV_PLAY	= 1;
    private static final int VIDEO_NEXT_PLAY 	= 2;
    private static final int VIDEO_SELECT_PLAY  = 3;

    private static final int MESSAGE_PROGRESS_UI 				= 0;
    private static final int MESSAGE_PREV_PLAY					= 1;
    private static final int MESSAGE_NEXT_PLAY					= 2;
    private static final int MESSAGE_SELECT_PLAY                = 3;
    private static final int MESSAGE_LAYOUT_SETTING 			= 6;
    private static final int MESSAGE_VIDEO_VISIBLE				= 7;
    private static final int MESSAGE_LOCK_MODE_READY			= 8;
    private static final int MESSAGE_LOCK_MODE_SET				= 9;
    private static final int MESSAGE_UPDATE_PAID_UI 			= 10;
    private static final int MESSAGE_UPDATE_FREE_UI				= 11;
    private static final int MESSAGE_QUIZ_START					= 12;
    private static final int MESSAGE_FINISH						= 13;
    private static final int MESSAGE_NOT_MATCH_ACCESS_TOKEN		= 14;
    private static final int MESSAGE_AUTH_CONTENT_PLAY			= 15;
    private static final int MESSAGE_FAIL_MOVIE_LOADING			= 16;
    private static final int MESSAGE_LONGTIME_WATCH_WARNING		= 17;
    private static final int MESSAGE_CAPTION_LAYOUT_SETTING		= 18;

    private static final int LAYOUT_TYPE_DEFAULT 				= 0;
    private static final int LAYOUT_TYPE_PREVIEW_PLAY 			= 1;
    private static final int LAYOUT_TYPE_PREVIEW_END 			= 2;
    private static final int LAYOUT_TYPE_PLAY_END 				= 3;

    private static final int DURATION_VIEW_ANIMATION 			= 500;
    private static final int DURATION_VIEW_INIT					= 1500;

    private static final int PLAYER_RESUME 	= 0;
    private static final int PLAYER_PAUSE	= 1;
    /**
     * 30초 이상이 되었는데도 재생을 못할시 종료시키긴 위한 타임. 1초마다 갱신
     */
    private static final int MAX_MOVIE_LOADING_TIME	= 30;
    private static final int DIALOG_LONG_WATCH_TIME_WARNING = 0x00;
    private static final String CAPTION = "caption";
    private static final float[] PLAY_SPEED_LIST = { 0.7f, 0.85f, 1.0f, 1.15f, 1.3f };
    private static final int DEFAULT_SPEED_INDEX = 2;

    private PlayerStatus mCurrentPlayerStatus = PlayerStatus.STOP;
    private PlayerUserType mCurrentPlayerUserType = PlayerUserType.PREVIEW;
    private ContentPlayObject mContentPlayObject;
    private FadeAnimationController mFadeAnimationController;
    private boolean isVideoLoadingComplete = false;
    private Timer mStudyRecordTimer = null;
    private Timer mUiCurrentTimer = null;
    private Timer mMovieLoadingCheckTimer = null;
    private Timer mWarningWatchTimer = null;
    private String mCurrentPlayUrl = "";
    private int mCurrentPlayPosition = 0;
    private int mFreeUserPreviewTime = -1;
    private int mCurrentLayoutType = LAYOUT_TYPE_DEFAULT;
    private boolean isCaptionPlay = true;
    private Vibrator mVibrator;
    private PlayedContentDBHelper mPlayedContentDBHelper;
    private String mCurrentPlayContentId = "";

    static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
    private ArrayList<CaptionDetailInformation> mCaptionInformationList = new ArrayList<CaptionDetailInformation>();
    private int mCurrentCaptionIndex = 0;
    private boolean isBackgroundVisibleError = false;
    /**
     * 30초 이상이 되었는데도 재생을 못할시 종료시키긴 위한 타임. 1초마다 갱신
     */
    private int mMovieLoadingTime = 0;

    /**
     * 학습시간, 1초마다 갱신되며, 종료되거나 새 영상을 볼때 갱신된다.
     */
    private int mStudyTime = 0;

    /**
     * 락버튼이 ON 이 되었는 지 , OFF 인지
     */
    private boolean isLockDisplay = false;
    private SimpleExoPlayer mPlayer;
    private boolean isVideoPrepared = false;
    private int mCurrentPlayDuration = 0;
    private AuthContentPlayCoroutine mAuthContentPlayCoroutine = null;
    private int mCurrentWatchingTime = 0;
    private PlayerListAdapter mPlayerListAdapter;
    private PlayerSpeedListAdapter mPlayerSpeedListAdapter;
    private int mCurrentPlaySpeedIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
        {
            Log.f("NOT SUPPORT DISPLAY");
            setContentView(R.layout.player_main_not_support_display_hls);
        }
        else
        {
            setContentView(R.layout.player_main_hls);
        }

        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.f("");

        mContentPlayObject = getIntent().getParcelableExtra(Common.INTENT_PLAYER_PARAMS);
        initFont();
        initText();
        init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.f("");
        MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_PLAYER);
        resumePlayer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.f("");
        pausePlayer();
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

        requestPlaySaveRecord();
        releaseAuthContentPlay();
        enableTimer(false);
        enableMovieLoadingCheckTimer(false);
        mMainHandler.removeCallbacksAndMessages(null);
        mPlayedContentDBHelper.release();
    }

    private void init()
    {
        mPlayedContentDBHelper = PlayedContentDBHelper.getInstance(this);
        mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        _LockButton.setOnTouchListener(mLockControlListener);
        _ThumbSeekbar.setOnSeekBarChangeListener(mSeekBarChangeListener);

        LayerDrawable layerDrawable = (LayerDrawable)getResources().getDrawable(R.drawable.seekbar_thumb);
        GradientDrawable rectDrawable = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id._thumbRect);
        GradientDrawable circleDrawable = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id._thumbCircle);
        rectDrawable.setSize(CommonUtils.getInstance(this).getPixel(45), CommonUtils.getInstance(this).getPixel(45));
        circleDrawable.setSize(CommonUtils.getInstance(this).getPixel(40), CommonUtils.getInstance(this).getPixel(40));

        _TopViewLayout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        _BottomViewLayout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        _PlayerListBaseLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        _PlayerSpeedListBaseLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        _PlayerView.setOnTouchListener(mMenuVisibleListener);
        registerFadeControllerView();
        initViewSetting();
        setupPlayVideo();
        prepareVideo(VIDEO_INIT_PLAY);
        initPlayListView();
        initPlaySpeedListView();
    }

    private void initViewSetting()
    {
        RelativeLayout.LayoutParams baseLayoutParams = new RelativeLayout.LayoutParams(CommonUtils.getInstance(this).getPixel(654), RelativeLayout.LayoutParams.MATCH_PARENT);
        baseLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        baseLayoutParams.removeRule(RelativeLayout.BELOW);
        _PlayerListBaseLayout.setLayoutParams(baseLayoutParams);
        _PlayerSpeedListBaseLayout.setLayoutParams(baseLayoutParams);

        if(Feature.IS_FREE_USER)
        {
            settingLayout(LAYOUT_TYPE_PREVIEW_PLAY);
        }
        else
        {
            showMenuWithoutAnimation(false);
            settingLayout(LAYOUT_TYPE_DEFAULT);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            _PlayerSpeedText.setVisibility(View.VISIBLE);
            _PlayerSpeedButton.setVisibility(View.VISIBLE);
            _BottomViewLayout.moveChildView(_ThumbSeekbar, 128, 0, 1298,46);
            _BottomViewLayout.moveChildView(_RemainPlayTimeText, 1424, 0, 94,150);
        }
        else
        {
            _PlayerSpeedText.setVisibility(View.GONE);
            _PlayerSpeedButton.setVisibility(View.GONE);
            _BottomViewLayout.moveChildView(_ThumbSeekbar, 148, 0, 1478,46);
            _BottomViewLayout.moveChildView(_RemainPlayTimeText, 1644, 0, 94,150);
        }
    }

    private void initFont()
    {
        _CurrentPlayTimeText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PreviewSignMessageText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PreviewProgressText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PreviewPayButtonText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _RemainPlayTimeText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _TopTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlayEndReplayText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlayEndQuizText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlayEndRemainText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlayEndRecommandTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _CaptionTitleText.setTypeface(Font.getInstance(this).getRobotoRegular());
        _PlayerSpeedText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlayListTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
        _PlaySpeedListTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
    }

    private void initText()
    {
        _PreviewProgressText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_preview));
        _PreviewSignMessageText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_preview_end));
        _PreviewPayButtonText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_pay_ing));
        _PlayEndReplayText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_replay));
        _PlayEndQuizText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_quiz_play));
        _PlayEndRemainText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_remain_all_play));
        _PlayEndRecommandTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_recommand_play));
        _PlayListTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_play_list));
        _PlaySpeedListTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_playing_speed));
    }

    private void initPlayListView()
    {
        mPlayerListAdapter = new PlayerListAdapter(this, mCurrentPlayPosition, mContentPlayObject.getPlayObjectList());
        mPlayerListAdapter.setOnPlayEventListener(mPlayerEventListener);
        _PlayerListView.setLayoutManager(new LinearLayoutScrollerManager(this));
        _PlayerListView.setAdapter(mPlayerListAdapter);
        forceScrollView(mCurrentPlayPosition);
    }

    private void initPlaySpeedListView()
    {
        Log.f("");
        mCurrentPlaySpeedIndex = (int) CommonUtils.getInstance(this).getSharedPreference(Common.PARAMS_PLAYER_SPEED_INDEX, Common.TYPE_PARAMS_INTEGER);
        if(mCurrentPlaySpeedIndex == -1)
        {
            mCurrentPlaySpeedIndex = DEFAULT_SPEED_INDEX;
        }
        mPlayerSpeedListAdapter = new PlayerSpeedListAdapter(this, mCurrentPlaySpeedIndex);
        mPlayerSpeedListAdapter.setPlayerEventListener(mPlayerEventListener);
        _PlayerSpeedListView.setLayoutManager(new LinearLayoutScrollerManager(this));
        _PlayerSpeedListView.setAdapter(mPlayerSpeedListAdapter);
    }

    private void forceScrollView(final int position)
    {
        Log.f("position : "+ position);
        _PlayerListView.post(new Runnable() {
            @Override
            public void run()
            {
                _PlayerListView.smoothScrollToPosition(position);
            }
        });
    }

    private void pausePlayer()
    {
        Log.f("status : "+mCurrentPlayerStatus);

        if(mCurrentPlayerStatus == PlayerStatus.COMPELTE)
        {
            return;
        }
        isLockDisplay = false;
        setLockModeUI();
        if(mPlayer != null && isPlaying())
        {
            mCurrentPlayDuration = (int) mPlayer.getCurrentPosition();
            mPlayer.setPlayWhenReady(false);
            setPlayIconStatus(PLAYER_PAUSE);
            enableTimer(false);
        }
        mCurrentPlayerStatus = PlayerStatus.PAUSE;
    }

    private void resumePlayer()
    {
        Log.f("status : "+mCurrentPlayerStatus);
        if(mCurrentPlayerStatus == PlayerStatus.PAUSE)
        {
            mPlayer.seekTo(mCurrentPlayDuration);
            mPlayer.setPlayWhenReady(true);
            enableTimer(true);
            setPlayIconStatus(PLAYER_RESUME);
            mCurrentPlayerStatus = PlayerStatus.PLAY;
        }
    }


    private void adjustVideoSpeed(int speendIndex)
    {
        PlaybackParameters params = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            params = new PlaybackParameters(PLAY_SPEED_LIST[speendIndex]);
            if(mPlayer != null)
            {
                mPlayer.setPlaybackParameters(params);
            }
        }
    }

    private void setupPlayVideo()
    {
        if(mPlayer == null)
        {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
            _PlayerView.setPlayer(mPlayer);
        }

        mPlayer.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) { }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
            {
                Log.f("playWhenReady : "+playWhenReady+", playbackState : "+playbackState);
                Log.f("Max Duration : "+mPlayer.getDuration());
                switch (playbackState)
                {
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_BUFFERING:
                        if(playWhenReady)
                        {
                            showLoading(true);
                            setPlayIconStatus(PLAYER_PAUSE);
                        }
                        break;
                    case Player.STATE_READY:
                        if(playWhenReady)
                        {
                            showLoading(false);
                            setPlayIconStatus(PLAYER_RESUME);
                        }
                        if(isVideoPrepared)
                        {
                            return;
                        }
                        isVideoPrepared = true;
                        setVideoPrepared();
                        break;
                    case Player.STATE_ENDED:
                        Log.f("Play Complete");
                        if(playWhenReady)
                        {
                            showLoading(false);
                        }
                        setVideoCompleted();
                        break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error)
            {
                Log.f("Play Error : "+ error.getMessage());
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

            @Override
            public void onSeekProcessed()
            {
                Log.f("Max Duration : "+mPlayer.getDuration());
            }
        });
    }


    private void setVideoPrepared()
    {
        isBackgroundVisibleError = true;
        enableMovieLoadingCheckTimer(false);
        Log.f("mCurrentPlayerStatus : "+mCurrentPlayerStatus);
        isVideoLoadingComplete = true;

        if (mCurrentPlayerStatus == PlayerStatus.COMPELTE)
        {
            return;
        }

        if(mCurrentPlayerStatus ==  PlayerStatus.PLAY)
        {
            setVideoInformation();
            setPlayIconStatus(PLAYER_RESUME);
        }
        //플레이 타이머 보여줌
        mPlayer.setPlayWhenReady(true);
        enableTimer(true);
        mPlayerListAdapter.setCurrentPlayPosition(mCurrentPlayPosition);
        mMainHandler.sendEmptyMessageDelayed(MESSAGE_VIDEO_VISIBLE, DURATION_GONE_BACKGROUND);
    }

    private void setVideoCompleted()
    {
        mCurrentPlayerStatus = PlayerStatus.COMPELTE;
        enableTimer(false);
        setAnimationMenu(false);
        requestPlaySaveRecord();

        //선택한리스트를 전부 보여주거나 한편을 보여줬을때만 띠운다. 그렇지 않고는 다음편을 플레이한다.
        //화면끝낫을때 레이아웃 보여줌
        //동화한편, 동요한편, 시리즈일때
        if(mContentPlayObject.getSelectedPosition() == -1 && mCurrentPlayPosition < mContentPlayObject.getPlayObjectList().size() -1)
        {
            mMainHandler.sendEmptyMessageDelayed(MESSAGE_NEXT_PLAY, DURATION_PLAY);
        }
        else
        {
            if(isMenuVisible())
            {
                Message msg = Message.obtain();
                msg.what = MESSAGE_LAYOUT_SETTING;
                msg.arg1 = LAYOUT_TYPE_PLAY_END;
                mMainHandler.sendMessageDelayed(msg, DURATION_VIEW_ANIMATION);
            }
            else
            {
                settingLayout(LAYOUT_TYPE_PLAY_END);
            }
            setPlayEndLayoutToStatus();
        }
    }

    private MediaSource buildMediaSource(Uri uri)
    {
        String userAgent = Util.getUserAgent(this, Common.PACKAGE_NAME);

        if(uri.getLastPathSegment().contains("mp4"))
        {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
        }
        else if(uri.getLastPathSegment().contains("m3u8"))
        {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
        }
        else
        {
            return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent)).createMediaSource(uri);
        }
    }

    private boolean isPlaying()
    {
        Log.f("playWhenReady : "+ mPlayer.getPlayWhenReady()+", state : "+ mPlayer.getPlaybackState());
        return mPlayer.getPlayWhenReady() && mPlayer.getPlaybackState() == Player.STATE_READY;
    }

    private void initCaptionInformationList()
    {
        if(mCaptionInformationList == null)
        {
            mCaptionInformationList = new ArrayList<CaptionDetailInformation>();
        }
        else
        {
            mCaptionInformationList.clear();
        }
    }

    private void setVideoInformation()
    {
        _TopTitleText.setText(mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).getTitle());
        _CurrentPlayTimeText.setText(CommonUtils.getInstance(this).getMillisecondTime(0));
        _RemainPlayTimeText.setText(CommonUtils.getInstance(this).getMillisecondTime((int) mPlayer.getDuration()));
        _ThumbSeekbar.setMax((int) (mPlayer.getDuration() / SECOND));
    }

    private void initCaptionLayoutView()
    {
        if(isCaptionPlay)
        {
            mFadeAnimationController.promptViewStatus(_CaptionLayout, true);
        }
        else
        {
            mFadeAnimationController.promptViewStatus(_CaptionLayout, false);
        }
    }

    private void setCaptionIconStatus()
    {
        if(isCaptionPlay)
        {
            _TopCaptionSettingButton.setImageResource(R.drawable.player_icon_subtitle_yellow);
        }
        else
        {
            _TopCaptionSettingButton.setImageResource(R.drawable.icon_subtitle_white);
        }
    }

    private void initTime()
    {
        _CurrentPlayTimeText.setText("00:00");
        _RemainPlayTimeText.setText("00:00");
    }

    /**
     * 구글 애널리틱스 Action 정보를 컨텐트 타입에 따라 달리 보낸다. Story, Song, Study Data
     * @param contentPlayObject
     * @return
     */
    private String getAnalyticsAction(ContentPlayObject contentPlayObject)
    {
        switch(contentPlayObject.getPlayItemType())
        {
            case Common.PLAY_TYPE_SERIES_STORY:
            case Common.PLAY_TYPE_SHORT_STORY:
                if(Feature.IS_FREE_USER)
                {
                    return Common.ANALYTICS_ACTION_PREVIEW_STORY;
                }
                else
                {
                    return Common.ANALYTICS_ACTION_STORY;
                }
            case Common.PLAY_TYPE_SONG:
                if(Feature.IS_FREE_USER)
                {
                    return Common.ANALYTICS_ACTION_PREVIEW_SONG;
                }
                else
                {
                    return Common.ANALYTICS_ACTION_SONG;
                }
            case Common.PLAY_TYPE_STUDY_DATA:
                if(Feature.IS_FREE_USER)
                {
                    return Common.ANALYTICS_ACTION_PREVIEW_STUDYDATA;
                }
                else
                {
                    return Common.ANALYTICS_ACTION_STUDYDATA;
                }
        }

        return "";
    }

    /**
     * 상태에 따라 레이아웃을 보여주는 메소드
     * @param type
     */
    private void settingLayout(int type)
    {
        Log.i("type : "+type);

        mCurrentLayoutType = type;
        _BasePreviewEndLayout.clearAnimation();
        _BasePlayEndLayout.clearAnimation();
        switch(type)
        {
            case LAYOUT_TYPE_DEFAULT:
                initTime();
                _BasePreviewEndLayout.setVisibility(View.GONE);
                _BasePlayEndLayout.setVisibility(View.GONE);
                _PlayerOptionBackground.setVisibility(View.GONE);
                break;
            case LAYOUT_TYPE_PREVIEW_PLAY:
                initTime();
                _TopCaptionSettingButton.setVisibility(View.INVISIBLE);
                _BasePreviewEndLayout.setVisibility(View.GONE);
                _BasePlayEndLayout.setVisibility(View.GONE);
                _BottomViewLayout.setVisibility(View.INVISIBLE);
                _PreviewLayout.setVisibility(View.VISIBLE);
                break;
            case LAYOUT_TYPE_PREVIEW_END:
                _BasePreviewEndLayout.setVisibility(View.VISIBLE);
                _BasePlayEndLayout.setVisibility(View.GONE);
                _BasePreviewEndLayout.startAnimation(CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, -CommonUtils.getInstance(this).getPixel(1080), 0, new LinearOutSlowInInterpolator()));
                _BasePreviewEndLayout.setOnTouchListener(new OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        return true;
                    }
                });
                break;
            case LAYOUT_TYPE_PLAY_END:
                _BasePreviewEndLayout.setVisibility(View.GONE);
                _BasePlayEndLayout.setVisibility(View.VISIBLE);
                _BasePlayEndLayout.startAnimation(CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, -CommonUtils.getInstance(this).getPixel(1080), 0, new LinearOutSlowInInterpolator()));
                _BasePlayEndLayout.setOnTouchListener(new OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        return true;
                    }
                });
                break;
        }
    }

    private void setPlayIconStatus(int type)
    {
        if(type == PLAYER_RESUME)
        {
            _PlayButton.setImageResource(R.drawable.selector_player_pause_button);
        }
        else if(type == PLAYER_PAUSE)
        {
            _PlayButton.setImageResource(R.drawable.selector_player_play_button);
        }
    }

    /**
     * 해당 컨텐츠 정보를 전달하여 비디오 정보를 요청한다.
     */
    private void requestCurrentPlayVideoUrlInformation()
    {
        int contentType = -1;
        mCurrentPlayContentId = isCaptionPlay == true ? mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).fc_id +"_caption" : mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).fc_id;

        if(mContentPlayObject.getPlayItemType() == Common.PLAY_TYPE_SONG)
        {
            contentType = Common.REQUEST_CONTENT_TYPE_SONG;
        }
        else
        {
            contentType = Common.REQUEST_CONTENT_TYPE_MOVIE;
        }

		mAuthContentPlayCoroutine = new AuthContentPlayCoroutine(this, mAsyncListener);
        mAuthContentPlayCoroutine.setData(
                Common.USER_TYPE_PAID,
                mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).fc_id,
                contentType);
        mAuthContentPlayCoroutine.execute();
    }


    private void showLoading(boolean isLoading)
    {
        if(isLoading)
        {
            _LoadingLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            _LoadingLayout.setVisibility(View.GONE);
        }
    }

    private void setLockModeUI()
    {
        if(isLockDisplay)
        {
            _LockButton.setImageResource(R.drawable.player_icon_lock);
            _TopCaptionSettingButton.setVisibility(View.INVISIBLE);
            _TopCloseButton.setVisibility(View.INVISIBLE);
            _CurrentPlayTimeText.setVisibility(View.INVISIBLE);
            _ThumbSeekbar.setVisibility(View.INVISIBLE);
            _RemainPlayTimeText.setVisibility(View.INVISIBLE);
            _PlayerListButton.setVisibility(View.INVISIBLE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                _PlayerSpeedButton.setVisibility(View.INVISIBLE);
                _PlayerSpeedText.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            _LockButton.setImageResource(R.drawable.player_icon_unlock);
            if(mCaptionInformationList.size() > 0)
                _TopCaptionSettingButton.setVisibility(View.VISIBLE);
            _TopCloseButton.setVisibility(View.VISIBLE);
            _CurrentPlayTimeText.setVisibility(View.VISIBLE);
            _ThumbSeekbar.setVisibility(View.VISIBLE);
            _RemainPlayTimeText.setVisibility(View.VISIBLE);
            _PlayerListButton.setVisibility(View.VISIBLE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                _PlayerSpeedButton.setVisibility(View.VISIBLE);
                _PlayerSpeedText.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 메뉴가 현재 보이는 상태인지 체크
     * @return
     */
    private boolean isMenuVisible()
    {
        Log.i("mCurrentLayoutType : " + mCurrentLayoutType+ " , _TopViewLayout  : "+ _TopViewLayout.getVisibility() + " , _BottomViewLayout.getVisibility() : "+_BottomViewLayout.getVisibility());
        Log.i("_PlayButtonLayout getVisibility: " + _PlayButtonLayout.getVisibility());
        if(mCurrentLayoutType == LAYOUT_TYPE_PREVIEW_PLAY)
        {
            return (_TopViewLayout.getVisibility()) == View.VISIBLE;
        }
        else
        {
            return (_TopViewLayout.getVisibility() == View.VISIBLE || _BottomViewLayout.getVisibility() == View.VISIBLE || _PlayButtonLayout.getVisibility() == View.VISIBLE);
        }
    }

    private boolean isCaptionVisible()
    {
        return _CaptionLayout.getVisibility() == View.VISIBLE;
    }

    /**
     * Top 과 Bottom 메뉴바를 내리거나 올린다.  프리뷰일때는 하단 과 플레이 중지 버튼의 애니메이션을 실행하지않는다.
     * @param isVisible TRUE : 메뉴를 보이게한다. FALSE : 메뉴를 가린다.
     */
    private void setAnimationMenu(boolean isVisible) {
        Log.i("isVisible : " + isVisible + ", isMenuVisible : " + isMenuVisible());
        if (isVisible)
        {
            mFadeAnimationController.startAnimation(_TopViewLayout, FadeAnimationController.TYPE_FADE_IN);
            if (mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
            {
                mFadeAnimationController.startAnimation(_PlayButtonLayout, FadeAnimationController.TYPE_FADE_IN);
                mFadeAnimationController.startAnimation(_BottomViewLayout, FadeAnimationController.TYPE_FADE_IN);
            }

        }
        else
         {
            mFadeAnimationController.startAnimation(_TopViewLayout, FadeAnimationController.TYPE_FADE_OUT);
            if (mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
            {
                mFadeAnimationController.startAnimation(_PlayButtonLayout, FadeAnimationController.TYPE_FADE_OUT);
                mFadeAnimationController.startAnimation(_BottomViewLayout, FadeAnimationController.TYPE_FADE_OUT);
            }
        }
    }

    /**
     * 자막 등장 사라짐의 애니메이션을 보여주는 메소드
     * @param isVisible
     */
    private void setAnimationCaption(boolean isVisible)
    {
        if(isVisible)
        {
            if(isCaptionVisible() == false)
            {
                mFadeAnimationController.startAnimation(_CaptionLayout, FadeAnimationController.TYPE_FADE_IN);
            }
        }
        else
        {
            if(isCaptionVisible())
            {
                mFadeAnimationController.startAnimation(_CaptionLayout, FadeAnimationController.TYPE_FADE_OUT);
            }
        }
    }

    private void showMenuWithoutAnimation(boolean isVisible)
    {
        if (isVisible)
        {
            _TopViewLayout.setVisibility(View.VISIBLE);
            if (mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
            {
                _PlayButtonLayout.setVisibility(View.VISIBLE);
                _BottomViewLayout.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            _TopViewLayout.setVisibility(View.GONE);
            if (mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
            {
                _PlayButtonLayout.setVisibility(View.GONE);
                _BottomViewLayout.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 현재 상태에 따라 PREV 버튼 , NEXT 버튼을 보이고 안보이는 처리를 한다.
     */
    private void settingPlayMenu()
    {
        if(Feature.IS_FREE_USER)
        {
            initPreviewText();
        }

        //전체 재생 , 선택한 리스트 재생 일때
        if(mContentPlayObject.getSelectedPosition() == -1)
        {
            _PrevButton.setVisibility(View.VISIBLE);
            _NextButton.setVisibility(View.VISIBLE);
            if(mCurrentPlayPosition == 0)
            {
                _PrevButton.setVisibility(View.GONE);
            }
            else if(mCurrentPlayPosition >= (mContentPlayObject.getPlayObjectList().size() - 1))
            {
                _NextButton.setVisibility(View.GONE);
            }
        }
        // 한편만 재생 할 때
        else
        {
            _PrevButton.setVisibility(View.GONE);
            _NextButton.setVisibility(View.GONE);
        }

        settingCaptionButton(false);
    }

    private void settingVideoSpeed()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            Log.f("Not Use Setting Speed OS : "+ Build.VERSION.SDK_INT);
            return;
        }
        Log.f("mContentPlayObject.getPlayItemType() : "+mContentPlayObject.getPlayItemType());
        mCurrentPlaySpeedIndex = (int) CommonUtils.getInstance(this).getSharedPreference(Common.PARAMS_PLAYER_SPEED_INDEX, Common.TYPE_PARAMS_INTEGER);
        if(mCurrentPlaySpeedIndex == -1)
        {
            mCurrentPlaySpeedIndex = DEFAULT_SPEED_INDEX;
        }

        if((mContentPlayObject.getPlayItemType() == Common.PLAY_TYPE_SONG)
            || (mContentPlayObject.getPlayItemType() == Common.PLAY_TYPE_STUDY_DATA))
        {
            adjustVideoSpeed(DEFAULT_SPEED_INDEX);
            setVideoSpeedText(DEFAULT_SPEED_INDEX);
            enableVideoSpeedButton(false);
        }
        else
        {
            adjustVideoSpeed(mCurrentPlaySpeedIndex);
            setVideoSpeedText(mCurrentPlaySpeedIndex);
            enableVideoSpeedButton(true);
        }
    }

    private void setVideoSpeedText(int speedIndex)
    {
        String[] data = getResources().getStringArray(R.array.text_list_speed);
        if(data[speedIndex].contains(("(Normal)")))
        {
            data[speedIndex] = data[speedIndex].replace("(Normal)", "");
        }
        _PlayerSpeedText.setText(data[speedIndex]);
    }

    private void enableVideoSpeedButton(Boolean isVisible)
    {
        if(isVisible)
        {
            _PlayerSpeedButton.setVisibility(View.VISIBLE);
            _PlayerSpeedText.setVisibility(View.VISIBLE);
        }
        else
        {
            _PlayerSpeedButton.setVisibility(View.GONE);
            _PlayerSpeedText.setVisibility(View.GONE);
        }
    }

    private void settingCaptionButton(boolean isCaptionDataHave)
    {
        if(isCaptionDataHave)
        {
            _TopCaptionSettingButton.setVisibility(View.VISIBLE);
        }
        else
        {
            _TopCaptionSettingButton.setVisibility(View.GONE);
        }
    }

    private void registerFadeControllerView()
    {
        final int CONTROLLER_VIEW_WIDTH = CommonUtils.getInstance(this).getPixel(654);
        final int CONTROLLER_VIEW_HEIGHT = CommonUtils.getInstance(this).getPixel(150);
        mFadeAnimationController = new FadeAnimationController(this);
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_TopViewLayout,
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, -CONTROLLER_VIEW_HEIGHT, 0),
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, 0, -CONTROLLER_VIEW_HEIGHT)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_BottomViewLayout,
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, CONTROLLER_VIEW_HEIGHT, 0),
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, 0, CONTROLLER_VIEW_HEIGHT)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_PlayButtonLayout,
                CommonUtils.getInstance(this).getAlphaAnimation(DURATION_VIEW_ANIMATION, 0.2f, 1.0f),
                CommonUtils.getInstance(this).getAlphaAnimation(DURATION_VIEW_ANIMATION, 1.0f, 0.2f)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_CaptionLayout,
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, CONTROLLER_VIEW_HEIGHT, 0),
                CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_VIEW_ANIMATION, 0, CONTROLLER_VIEW_HEIGHT)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_PlayerListBaseLayout,
                CommonUtils.getInstance(this).getTranslateXAnimation(DURATION_VIEW_ANIMATION, CONTROLLER_VIEW_WIDTH, 0),
                CommonUtils.getInstance(this).getTranslateXAnimation(DURATION_VIEW_ANIMATION, 0, CONTROLLER_VIEW_WIDTH)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_PlayerSpeedListBaseLayout,
                CommonUtils.getInstance(this).getTranslateXAnimation(DURATION_VIEW_ANIMATION, CONTROLLER_VIEW_WIDTH, 0),
                CommonUtils.getInstance(this).getTranslateXAnimation(DURATION_VIEW_ANIMATION, 0, CONTROLLER_VIEW_WIDTH)));
        mFadeAnimationController.addControlView(new FadeAnimationInformation(_PlayerOptionBackground,
                CommonUtils.getInstance(this).getAlphaAnimation(DURATION_VIEW_ANIMATION, 0.0f, 1.0f),
                CommonUtils.getInstance(this).getAlphaAnimation(DURATION_VIEW_ANIMATION, 1.0f, 0.0f)));
    }

    private void requestPlaySaveRecord()
    {
        PlayerStudyRecordObject object = new PlayerStudyRecordObject(mContentPlayObject.getPlayItemType(), mContentPlayObject.getSelectedPosition(), mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).fc_id, mStudyTime);
        PlayerSaveRecordCoroutine coroutine = new PlayerSaveRecordCoroutine(this, mAsyncListener);
        coroutine.setData(object);
        coroutine.execute();
        mStudyTime = 0;
    }

    private void releaseAuthContentPlay()
    {
        Log.f("");
        if(mAuthContentPlayCoroutine != null)
        {
            mAuthContentPlayCoroutine.cancel();
            mAuthContentPlayCoroutine = null;
        }
    }

    private void prepareVideo(int playType)
    {
        enableTimer(false);
        isVideoPrepared = false;
        mStudyTime = 0;
        if(isMenuVisible())
        {
            setAnimationMenu(false);
        }

        showLoading(true);
        isVideoLoadingComplete = false;
        _BackgroundDiscoverImage.setVisibility(View.VISIBLE);
        _CaptionTitleText.setText("");
        mCurrentCaptionIndex = 0;
        switch(playType)
        {
            case VIDEO_INIT_PLAY:
                mCurrentPlayPosition = mContentPlayObject.getSelectedPosition();
                if(mCurrentPlayPosition == -1)
                {
                    mCurrentPlayPosition = 0;
                }
                if(Feature.IS_FREE_USER)
                {
                    int currentObjectTotalPlayTime = Integer.valueOf(mContentPlayObject.getPlayObject(mCurrentPlayPosition).play_time);
                    mFreeUserPreviewTime = CommonUtils.getInstance(this).getPreviewTime(currentObjectTotalPlayTime);
                    Log.f("Free User Preview Time : " + mFreeUserPreviewTime);
                }
                break;
            case VIDEO_PREV_PLAY:
                mCurrentPlayPosition = getPrevPosition(mCurrentPlayPosition);
                break;
            case VIDEO_NEXT_PLAY:
                mCurrentPlayPosition = getNextPositionWhenSingle(mCurrentPlayPosition);
                break;
            case VIDEO_SELECT_PLAY:
                break;
        }
        settingPlayMenu();
        _ThumbSeekbar.setThumbOffset(CommonUtils.getInstance(this).getPixel(0));
        _ThumbSeekbar.setProgress(0);
        _ThumbSeekbar.setSecondaryProgress(0);

        settingVideoSpeed();
        requestCurrentPlayVideoUrlInformation();
        Log.f("Current Play Title : "+ mContentPlayObject.getPlayObjectList().get(mCurrentPlayPosition).getTitle());
    }

    private void startMovie()
    {
        mCurrentPlayerStatus = PlayerStatus.PLAY;
        enableMovieLoadingCheckTimer(true);
        GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_PLAYER,
                getAnalyticsAction(mContentPlayObject), mCurrentPlayContentId + Common.ANALYTICS_LABEL_PLAY);

        MediaSource source = buildMediaSource(Uri.parse(mCurrentPlayUrl));
        mPlayer.prepare(source, true, false);
        _PlayerView.requestFocus();
    }

    /**
     * 영상을 로딩이 되었는 지 체크하기 위한 타이머
     * @param isStart
     */
    private void enableMovieLoadingCheckTimer(boolean isStart)
    {
        if(isStart)
        {
            if(mMovieLoadingCheckTimer == null)
            {
                mMovieLoadingCheckTimer = new Timer();
                mMovieLoadingCheckTimer.schedule(new MovieLoadingExceptionTask(), 0, 1000);
            }
        }
        else
        {
            if(mMovieLoadingCheckTimer != null)
            {
                mMovieLoadingCheckTimer.cancel();
                mMovieLoadingCheckTimer = null;
            }
            mMovieLoadingTime = 0;
        }
    }

    /**
     * UI 화면을 갱신하는 타이머를 동작시키거나 중지시킨다.
     * @param isStart
     */
    private void enableTimer(boolean isStart)
    {
        if(isStart)
        {
            if(mUiCurrentTimer == null)
            {
                mUiCurrentTimer = new Timer();
                mUiCurrentTimer.schedule(new UiTimerTask(), 0, 100);
            }

            if(mStudyRecordTimer == null)
            {
                mStudyRecordTimer = new Timer();
                mStudyRecordTimer.schedule(new StudyTimerTask(), 0, SECOND);
            }

            if (mWarningWatchTimer == null) {
                mWarningWatchTimer = new Timer();
                mWarningWatchTimer.schedule(new WarningWatchMessageTask(), 0, SECOND);
            }
        }
        else
        {
            if(mUiCurrentTimer != null)
            {
                mUiCurrentTimer.cancel();
                mUiCurrentTimer = null;
            }

            if(mStudyRecordTimer != null)
            {
                mStudyRecordTimer.cancel();
                mStudyRecordTimer = null;
            }

            if(mWarningWatchTimer != null)
            {
                mWarningWatchTimer.cancel();
                mWarningWatchTimer = null;
            }
        }
    }

    private boolean isPlayListVisible()
    {
        if(_PlayerListBaseLayout.getVisibility() == View.VISIBLE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isPlaySpeedListVisible()
    {
        if(_PlayerSpeedListBaseLayout.getVisibility() == View.VISIBLE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isPlayOptionBackgroundVisible()
    {
        if(_PlayerOptionBackground.getVisibility() == View.VISIBLE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void enablePlayListAnimation(boolean isVisible)
    {
        if(mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
        {
            if(isVisible)
            {
                mFadeAnimationController.startAnimation(_PlayerListBaseLayout, FadeAnimationController.TYPE_FADE_IN);
            }
            else
            {
                mFadeAnimationController.startAnimation(_PlayerListBaseLayout, FadeAnimationController.TYPE_FADE_OUT);
            }
        }
    }

    private void enablePlaySpeedListAnimation(boolean isVisible)
    {
        if(mCurrentLayoutType == LAYOUT_TYPE_DEFAULT)
        {
            if(isVisible)
            {
                mFadeAnimationController.startAnimation(_PlayerSpeedListBaseLayout, FadeAnimationController.TYPE_FADE_IN);
            }
            else
            {
                mFadeAnimationController.startAnimation(_PlayerSpeedListBaseLayout, FadeAnimationController.TYPE_FADE_OUT);
            }
        }
    }

    private long getMillisecond(int second)
    {
        return second * SECOND;
    }
    /**
     * 프리로 보는 시간이 끝났다면 TRUE 아니면 FALSE 를 리턴
     * @return
     */
    private boolean isFreeUserLimitedTimeEnd()
    {
        if(((int)mPlayer.getCurrentPosition()/SECOND) >= mFreeUserPreviewTime)
        {
            return true;
        }
        return false;
    }

    private int getNextPositionWhenSingle(int position)
    {
        position++;
        if(position >= mContentPlayObject.getPlayObjectList().size())
        {
            position = 0;
        }
        return position;
    }

    private int getPrevPosition(int position)
    {
        position--;
        if(position < 0 )
        {
            position = 0;
        }
        return position;
    }

    /**
     * 상황에 맞게 플레이가 종료 될때 화면에 표시한다.
     */
    private void setPlayEndLayoutToStatus()
    {
        int nextPostion = getNextPositionWhenSingle(mCurrentPlayPosition);
        if(mContentPlayObject.getSelectedPosition() == -1)
        {
            _PlayEndButtonLayout.setVisibility(View.GONE);
            _PlayEndRecommandViewLayout.setVisibility(View.GONE);
            return;
        }

        Log.f("getPlayItemType : "+mContentPlayObject.getPlayItemType());
        Log.f("isQuizEmpty : "+mContentPlayObject.isQuizEmpty());
        Log.f("isRecommandItemEmpty : "+mContentPlayObject.isRecommandItemEmpty());
        Log.f("getSelectedPosition : "+ mCurrentPlayPosition);
        Log.f("getPlayObjectList size : "+ mContentPlayObject.getPlayObjectList().size());
        switch(mContentPlayObject.getPlayItemType())
        {
            case Common.PLAY_TYPE_SONG:
            case Common.PLAY_TYPE_STUDY_DATA:
                _PlayEndQuizButton.setVisibility(View.GONE);
                _PlayEndRemainButton.setVisibility(View.GONE);
                _PlayEndButtonLayout.moveChildView(_PlayEndReplayButton, 688, 0);
                if(mContentPlayObject.isRecommandItemEmpty() == false)
                {
                    _PlayEndRecommandViewLayout.setVisibility(View.VISIBLE);
                    if(mContentPlayObject.getPlayItemType() == Common.PLAY_TYPE_SONG)
                    {
                        _PlayEndRecommandTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_recommand_play_song));
                    }
                    else if(mContentPlayObject.getPlayItemType() == Common.PLAY_TYPE_STUDY_DATA)
                    {
                        _PlayEndRecommandTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_recommand_play));
                    }

                    Glide.with(this)
                            .load(mContentPlayObject.getPlayObjectList().get(nextPostion).getThumbnalUrl())
                            .transition(withCrossFade())
                            .into(_PlayEndRecommandThumbnailImage);

                }
                else
                {
                    _PlayEndRecommandViewLayout.setVisibility(View.GONE);
                }

                break;
            case Common.PLAY_TYPE_SERIES_STORY:
                _PlayEndButtonLayout.setVisibility(View.VISIBLE);
                _PlayEndRecommandViewLayout.setVisibility(View.GONE);

                boolean isLastMovie = mCurrentPlayPosition == (mContentPlayObject.getPlayObjectList().size() - 1);

                /**
                 * Default : 퀴즈가 있고 마지막이 아니다. : 퀴즈버튼, 다시보기 , 남은편 보기
                 퀴즈가 있고, 마지막일때 : 퀴즈버튼, 다시보기
                 퀴즈가 없고, 마지막일때 : 다시보기
                 퀴즈가 없고 마지막이 아닐때 : 다시 보기 , 남은편 보기
                 */
                if(mContentPlayObject.isQuizEmpty())
                {
                    _PlayEndQuizButton.setVisibility(View.GONE);
                    if(isLastMovie)
                    {
                        _PlayEndRemainButton.setVisibility(View.GONE);
                        _PlayEndButtonLayout.moveChildView(_PlayEndReplayButton, 688, 0);
                    }
                    else
                    {
                        _PlayEndButtonLayout.moveChildView(_PlayEndReplayButton, 388, 0);
                        _PlayEndButtonLayout.moveChildView(_PlayEndRemainButton, 968, 0);
                    }
                }
                else
                {
                    if(isLastMovie)
                    {
                        _PlayEndRemainButton.setVisibility(View.GONE);
                        _PlayEndButtonLayout.moveChildView(_PlayEndReplayButton, 388, 0);
                        _PlayEndButtonLayout.moveChildView(_PlayEndQuizButton, 968, 0);
                    }
                }

                break;
            case Common.PLAY_TYPE_SHORT_STORY:
                _PlayEndRemainButton.setVisibility(View.GONE);
                _PlayEndButtonLayout.moveChildView(_PlayEndReplayButton, 388, 0);
                _PlayEndButtonLayout.moveChildView(_PlayEndQuizButton, 968, 0);
                if(mContentPlayObject.isRecommandItemEmpty() == false)
                {
                    _PlayEndRecommandViewLayout.setVisibility(View.VISIBLE);
                    _PlayEndRecommandTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_recommand_play));

                    Glide.with(this)
                            .load(mContentPlayObject.getPlayObjectList().get(nextPostion).getThumbnalUrl())
                            .transition(withCrossFade())
                            .into(_PlayEndRecommandThumbnailImage);
                }
                else
                {
                    _PlayEndRecommandViewLayout.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void initPreviewText()
    {
        _PreviewProgressText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_preview));
    }

    private String getPreviewTextTime(int milisecondTime)
    {
        final int FREE_MAX_TIME = 60;
        int seconds  = 0;
        int totalSeconds = milisecondTime / 1000;

        if(totalSeconds == FREE_MAX_TIME)
        {
            seconds = 60;
        }
        else
        {
            seconds = totalSeconds % 60;
        }

        return String.valueOf(seconds);
    }

    private void updatePreviewUI()
    {
        try
        {
            if (isFreeUserLimitedTimeEnd() && isPlaying())
            {
                Log.f("Preview Play Time End.");

                enableTimer(false);
                if(isMenuVisible())
                {
                    setAnimationMenu(false);
                }

                mPlayer.setPlayWhenReady(false);
                mPlayer.stop(true);
                if (isMenuVisible())
                {
                    Message msg = Message.obtain();
                    msg.what = MESSAGE_LAYOUT_SETTING;
                    msg.arg1 = LAYOUT_TYPE_PREVIEW_END;
                    mMainHandler.sendMessageDelayed(msg, DURATION_VIEW_ANIMATION);
                } else {
                    settingLayout(LAYOUT_TYPE_PREVIEW_END);
                }
                return;
            }

            //미리보기 타이틀 갱신
            int remainPreviewTime = (int) (getMillisecond(mFreeUserPreviewTime) - mPlayer.getCurrentPosition());

            _PreviewProgressText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_preview) + getPreviewTextTime(remainPreviewTime) + CommonUtils.getInstance(this).getLanguageTypeString(R.array.second));

            if (isTimeForCaption() == true)
            {
                _CaptionTitleText.setText(Html.fromHtml(mCaptionInformationList.get(mCurrentCaptionIndex).getText()));
                mCurrentCaptionIndex++;
            }
        }catch (Exception e)
        {
            Log.f("message : "+ e.getMessage());
        }
    }

    private void updateUI()
    {
        _ThumbSeekbar.post(new Runnable()
        {
            @Override
            public void run() {
                try {
                    _ThumbSeekbar.setProgress((int) (mPlayer.getCurrentPosition() / SECOND));
                } catch (Exception e) { }

                _CurrentPlayTimeText.setText(CommonUtils.getInstance(PlayerHlsActivity.this).getMillisecondTime((int) mPlayer.getCurrentPosition()));

                if (isTimeForCaption() == true) {
                    _CaptionTitleText.setText(Html.fromHtml(mCaptionInformationList.get(mCurrentCaptionIndex).getText()));
                    mCurrentCaptionIndex++;
                }
            }
        });
    }

    /**
     * 캡션에 대한 정보 처리 타이밍인지 확인 하는 메소드
     * @return
     */
    private boolean isTimeForCaption()
    {
        try
        {
            if(mCurrentCaptionIndex >= mCaptionInformationList.size() || mCurrentCaptionIndex == -1 || mCaptionInformationList.size() <= 0)
            {
                return false;
            }

            float visibleTime = mCaptionInformationList.get(mCurrentCaptionIndex).getStartTime();

            if( visibleTime <= (float) mPlayer.getCurrentPosition())
            {
                return true;
            }

        }catch(ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
        return false;
    }

    private int getCaptionCurrentIndex()
    {
        float startTime = 0L;
        float endTime = 0L;

        if(mCaptionInformationList.size() <= 0)
        {
            return  -1;
        }

        startTime = mCaptionInformationList.get(0).getStartTime();
        if (startTime > (float) mPlayer.getCurrentPosition())
        {
            return 0;
        }

        /**
         * 데이터는 startTime 과  endTime 사이에 존재한다고 생각하여 그 중간에 있는 CaptionIndex를 가져와 리턴
         */
        for (int i = 0; i < mCaptionInformationList.size(); i++)
        {
            startTime 	= mCaptionInformationList.get(i).getStartTime();
            endTime		= mCaptionInformationList.get(i).getEndTime();
            if (startTime <= (float) mPlayer.getCurrentPosition() && endTime >= (float) mPlayer.getCurrentPosition())
            {
                return i;
            }
        }

        /**
         * 하지만 , 동요에서 간주는 데이터가 비어버려 데이터를 찾을 수 없으므로 그때는 startTime으로 다시 찾는다.
         */
        for (int i = 0; i < mCaptionInformationList.size(); i++)
        {
            startTime 	= mCaptionInformationList.get(i).getStartTime();
            if (startTime >= (float) mPlayer.getCurrentPosition())
            {
                return i;
            }
        }
        return -1;
    }

    private void showTempleteAlertDialog(int type, int buttonType, String message)
    {
        TempleteAlertDialog dialog = new TempleteAlertDialog(this, message);
        dialog.setDialogMessageSubType(type);
        dialog.setButtonText(buttonType);
        dialog.setDialogListener(mDialogListener);
        dialog.show();
    }

    private void enableBackgroudAnimation(boolean isVisible)
    {
        if(isVisible)
        {
            if(isPlayOptionBackgroundVisible() == false)
                mFadeAnimationController.startAnimation(_PlayerOptionBackground, FadeAnimationController.TYPE_FADE_IN);
        }
        else
        {
            if(isPlayOptionBackgroundVisible())
                mFadeAnimationController.startAnimation(_PlayerOptionBackground, FadeAnimationController.TYPE_FADE_OUT);
        }
    }


    @OnClick({R.id.play_end_replay_layout, R.id.play_end_quiz_layout, R.id.play_end_remain_play_layout,
            R.id.play_end_recommand_layout, R.id.preview_close_button, R.id.play_end_close_button,
            R.id._previewBackgroundRect})
    public void onDisplayButtonClick(View view)
    {
        if(mMainHandler.hasMessages(MESSAGE_SELECT_PLAY) || mMainHandler.hasMessages(MESSAGE_NEXT_PLAY))
        {
            return;
        }

        switch(view.getId())
        {
            case R.id.play_end_close_button:
                Log.f("Player End Page Main Close Button Click");
                finish();
                break;
            case R.id.preview_close_button:
                Log.f("Player Preview Page Close Button Click");
                finish();
                break;
            case R.id._previewBackgroundRect:
                GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_PLAYER, Common.ANALYTICS_ACTION_PAYMENT);
                finish();
                MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PAY_PAGE);
                break;
            //TODO : 추후 지원 예정
		/*case R.id.preview_youtube_layout:
			GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_PLAYER, Common.ANALYTICS_ACTION_YOUTUBE);
			finish();
			CommonUtils.getInstance(PlayerActivity.this).startLinkMove(Common.URL_LITTLEFOX_CHANNEL);
			break;*/
            case R.id.play_end_replay_layout:
                Log.f("Player End Page Replay Button Click");
                settingLayout(LAYOUT_TYPE_DEFAULT);
                mMainHandler.sendEmptyMessageDelayed(MESSAGE_SELECT_PLAY, DURATION_PLAY);
                break;
            case R.id.play_end_quiz_layout:
                Log.f("Player End Page Quiz Button Click");
                if(NetworkUtil.isConnectNetwork(PlayerHlsActivity.this))
                {
                    mMainHandler.sendEmptyMessage(MESSAGE_QUIZ_START);
                }
                else
                {
                    MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
                }
                //퀴즈 실행
                break;
            case R.id.play_end_remain_play_layout:
                // -1로 설정은 선택한게 없으며 전부 플레이 한다라고 설정하는 것이다. 현재 포지션부터 남은 편을 모두 보여주기위해 설정한것이다.
                Log.f("Player End Page Remain Item All Play Click");
                mContentPlayObject.setSelectPosition(-1);
                settingLayout(LAYOUT_TYPE_DEFAULT);
                mMainHandler.sendEmptyMessageDelayed(MESSAGE_NEXT_PLAY, DURATION_PLAY);
                break;
            case R.id.play_end_recommand_layout:
                Log.f("Player End Page Recommand Item Play Click");
                if(isMenuVisible())
                {
                    setAnimationMenu(false);
                }
                settingLayout(LAYOUT_TYPE_DEFAULT);
                mMainHandler.sendEmptyMessageDelayed(MESSAGE_NEXT_PLAY, DURATION_PLAY);
                break;
        }
    }

    @OnClick({R.id.player_close_button, R.id.player_next_button, R.id.player_play_button, R.id.player_prev_button,
            R.id.player_subtitle_button, R.id._playerListButton, R.id._playListCloseButtonRect, R.id._playSpeedListCloseButtonRect,
            R.id._playerSpeedButton, R.id._playerSpeedText})
    public void onPlayerButtonClick(View view)
    {
        if(mMainHandler.hasMessages(MESSAGE_PREV_PLAY) || mMainHandler.hasMessages(MESSAGE_NEXT_PLAY))
        {
            return;
        }

        switch(view.getId())
        {
            case R.id.player_close_button:
                Log.f("Player Main Close Button Click");
                finish();
                break;
            case R.id.player_next_button:
                Log.f("Player Next Button Click");
                requestPlaySaveRecord();
                if(isMenuVisible())
                {
                    setAnimationMenu(false);
                }

                settingLayout(LAYOUT_TYPE_DEFAULT);
                mMainHandler.sendEmptyMessageDelayed(MESSAGE_NEXT_PLAY, DURATION_PLAY);
                break;
            case R.id.player_prev_button:
                Log.f("Player Prev Button Click");
                requestPlaySaveRecord();
                if(isMenuVisible())
                {
                    setAnimationMenu(false);
                }
                settingLayout(LAYOUT_TYPE_DEFAULT);
                mMainHandler.sendEmptyMessageDelayed(MESSAGE_PREV_PLAY, DURATION_PLAY);
                break;
            case R.id.player_play_button:
                if(isPlaying())
                {
                    Log.f("Player Pause Button Click");
                    setPlayIconStatus(PLAYER_PAUSE);
                    mPlayer.setPlayWhenReady(false);
                    enableTimer(false);
                }
                else
                {
                    Log.f("Player Play Button Click");
                    setPlayIconStatus(PLAYER_RESUME);
                    mPlayer.setPlayWhenReady(true);
                    enableTimer(true);
                }
                break;
            case R.id.player_subtitle_button:
                isCaptionPlay = !isCaptionPlay;
                setCaptionIconStatus();
                if(isCaptionPlay)
                {
                    Log.f("Player Caption Layout Gone Click");
                    setAnimationCaption(true);
                }
                else
                {
                    Log.f("Player Caption Layout Visible Click");
                    setAnimationCaption(false);
                }
                break;
            case R.id._playerListButton:
                forceScrollView(mCurrentPlayPosition);
                setAnimationMenu(false);
                enablePlayListAnimation(true);
                break;
            case R.id._playerSpeedButton:
            case R.id._playerSpeedText:
                setAnimationMenu(false);
                enablePlaySpeedListAnimation(true);
                break;
            case R.id._playListCloseButtonRect:
                setAnimationMenu(true);
                enablePlayListAnimation(false);
                break;
            case R.id._playSpeedListCloseButtonRect:
                setAnimationMenu(true);
                enablePlaySpeedListAnimation(false);
                break;

        }
    }

    private OnTouchListener mLockControlListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                Message msg = Message.obtain();
                msg.what = MESSAGE_LOCK_MODE_READY;
                msg.obj = !isLockDisplay;
                mMainHandler.sendMessageDelayed(msg, DURATION_LOCK_MODE);

            }else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE)
            {
                if(mMainHandler.hasMessages(MESSAGE_LOCK_MODE_READY))
                {
                    mMainHandler.removeMessages(MESSAGE_LOCK_MODE_READY);
                }
            }
            return true;
        }
    };

    private OnTouchListener mMenuVisibleListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                if(isVideoLoadingComplete == false)
                {
                    return false;
                }

                if(mCurrentLayoutType != LAYOUT_TYPE_DEFAULT && mCurrentLayoutType != LAYOUT_TYPE_PREVIEW_PLAY)
                {
                    return false;
                }

                if(mFadeAnimationController.isAnimationing(_TopViewLayout) || mFadeAnimationController.isAnimationing(_BottomViewLayout) || mFadeAnimationController.isAnimationing(_PlayButtonLayout))
                {
                    return false;
                }

                if(isMenuVisible())
                {
                    setAnimationMenu(false);
                    enableBackgroudAnimation(false);
                }
                else
                {
                    setAnimationMenu(true);
                    if(isPlayListVisible())
                    {
                        enablePlayListAnimation(false);
                    }
                    if(isPlaySpeedListVisible())
                    {
                        enablePlaySpeedListAnimation(false);
                    }
                    enableBackgroudAnimation(true);
                }
            }
            return true;
        }
    };

    private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            mPlayer.seekTo(seekBar.getProgress() * SECOND);
            mCurrentCaptionIndex = getCaptionCurrentIndex();
            enableTimer(true);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            _CaptionTitleText.setText("");
            enableTimer(false);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){}
    };

    private PlayerEventListener mPlayerEventListener = new PlayerEventListener() {
        @Override
        public void onClickPlayItem(int index)
        {
            Log.f("index : "+index);
            mCurrentPlayPosition = index;
            requestPlaySaveRecord();
            enablePlayListAnimation(false);
            settingLayout(LAYOUT_TYPE_DEFAULT);
            mMainHandler.sendEmptyMessageDelayed(MESSAGE_SELECT_PLAY, DURATION_PLAY);
        }

        @Override
        public void onClickSpeedIndex(int index)
        {
            Log.f("index : "+index);
            CommonUtils.getInstance(PlayerHlsActivity.this).setSharedPreference(Common.PARAMS_PLAYER_SPEED_INDEX, index);
            mCurrentPlaySpeedIndex = index;
            settingVideoSpeed();
            enablePlaySpeedListAnimation(false);
            enableBackgroudAnimation(false);
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
            Log.f("code : "+code+", message : "+ message);
        }

        @Override
        public void onRunningEnd(String code, Object mObject)
        {
            switch(code)
            {
                case Common.ASYNC_CODE_PLAY_SAVE_RECORD:
                    if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
                    {
                        Log.f("send Play Study Record success");
                    }
                    else
                    {
                        Log.f("send Play Study Record fail");
                    }

                    break;
                case Common.ASYNC_CODE_AUTH_CONTENT_PLAY:
                    if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
                    {
                        if(((AuthContentResult)mObject).getCaptionDetailInformationList().size() > 0)
                        {
                            if(isLockDisplay == false)
                                mMainHandler.sendEmptyMessage(MESSAGE_CAPTION_LAYOUT_SETTING);
                            initCaptionInformationList();
                            mCaptionInformationList = ((AuthContentResult)mObject).getCaptionDetailInformationList();
                        }
                        else
                        {
                            if(mContentPlayObject.getPlayItemType() != Common.PLAY_TYPE_STUDY_DATA && mContentPlayObject.isCaptionEmpty() == false)
                            {
                                Log.f("Unable to load the caption file.");
                            }
                            initCaptionInformationList();
                        }
                        mCurrentPlayUrl = ((AuthContentResult)mObject).getVideoUrl();
                        startMovie();
                    }
                    else
                    {
                        Toast.makeText(PlayerHlsActivity.this, ((BaseResult)mObject).getMessage(), Toast.LENGTH_LONG).show();
                        if(((BaseResult)mObject).isAuthenticationBroken())
                        {
                            mMainHandler.sendEmptyMessageDelayed(MESSAGE_NOT_MATCH_ACCESS_TOKEN, DURATION_VIEW_INIT);
                        }
                        else
                        {
                            mMainHandler.sendEmptyMessageDelayed(MESSAGE_FINISH, DURATION_VIEW_INIT);
                        }
                    }
                    break;
            }
        }
    };

    private DialogListener mDialogListener = new DialogListener()
    {
        @Override
        public void onItemClick(int messageType, Object sendObject){}

        @Override
        public void onItemClick(int messageButtonType, int messageType, Object sendObject)
        {
            if(messageType == DIALOG_LONG_WATCH_TIME_WARNING)
            {
                switch(messageButtonType)
                {
                    case DialogListener.FIRST_BUTTON_CLICK:
                        Log.f("Warning watch movie End");
                        finish();
                        break;
                    case DialogListener.SECOND_BUTTON_CLICK:
                        Log.f("Warning watch movie Continue");
                        mCurrentWatchingTime = 0;
                        enableTimer(true);
                        mPlayer.setPlayWhenReady(true);
                        setPlayIconStatus(PLAYER_RESUME);
                        break;
                }
            }
        }
    };
}

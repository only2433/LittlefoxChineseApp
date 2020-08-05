package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.Explode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.coroutines.ContentListCoroutine;
import com.littlefox.chinese.edu.dialog.SeriesIntroductionInfoDialog;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.result.ContentListResult;
import com.littlefox.chinese.edu.object.result.ContentListResult.ContentItemObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.chinese.edu.view.itemdecoration.LineDividerItemDecoration;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.controller.FadeAnimationController;
import com.littlefox.library.view.controller.FadeAnimationInformation;
import com.littlefox.library.view.text.SeparateTextView;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class StoryContentListActivity extends BaseActivity
{
	@BindView(R.id.main_content)
	CoordinatorLayout _MainBaseLayout;
	
	@BindView(R.id.study_detail_appbar)
	AppBarLayout _AppBarLayout;
	
	@BindView(R.id.study_detail_collapsing_toolbar)
	CollapsingToolbarLayout _CollapsingToolbarLayout;
	
	@BindView(R.id.study_detail_back_image)
	ImageView _StudyContentListMainImageView;
	
	@BindView(R.id.study_detail_toolbar)
	Toolbar _Toolbar;
	
	@BindView(R.id.story_detail_list)
	RecyclerView _StudyContentListView;
	
	@BindView(R.id.story_detail_choice_button_layout)
	ScalableLayout _ChoicePlayButtonLayout;
	
	@BindView(R.id.story_detail_first_button)
	TextView _ChoiceFirstButton;
	
	@BindView(R.id.story_detail_second_button)
	TextView _ChoiceSecondButton;
	
	@BindView(R.id.progress_wheel_layout)
	ScalableLayout _ProgressWheelLayout;
	
	Handler mRequestInformationHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_SETTING_CONTENT_LIST:
				initContentInformationList();
				break;
			case MESSAGE_INIT_SETTING:
				requestContentListInformation();
				break;
			case MESSAGE_EXTENDS_VIEW:
				supportStartPostponedEnterTransition();
				break;
			case MESSAGE_START_PLAY_ALL:
				MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PLAYER, getPlayContentToPlay(-1));
				break;
			case MESSAGE_START_PLAY_SELECTED_LIST:
				MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PLAYER, getSelectPlayContent());
				break;
			case MESSAGE_START_PLAY_ONE_ITEM:
				if(Feature.IS_FREE_USER)
				{
					MainSystemFactory.getInstance().startActivityNoAnimationForResult(MainSystemFactory.MODE_PLAYER , REQUEST_PAY_STATUS, getPlayContentToPlay(mCurrentExecutePosition));
				}
				else
				{
					MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PLAYER ,getPlayContentToPlay(mCurrentExecutePosition));
				}
				break;
			case MESSAGE_START_QUIZ:
				MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_QUIZ, mContentItemList.get(mCurrentExecutePosition).fc_id);
				break;
			case MESSAGE_START_ORIGIN_DATA:
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().setExitTransition(null);
				}

				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_ORIGIN_DATA, mContentItemList.get(mCurrentExecutePosition).fc_id);
				break;
			case MESSAGE_INIT_SELECTED_ITEM:
				initContentSelectList(true);
				break;
			}
		}
	};
	
	Handler mBottomAnimationHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_BOTTOMVIEW_CHANGE:
				setBottomLayoutButtonTextToType();
				mFadeAnimationController.startAnimation(_ChoicePlayButtonLayout, FadeAnimationController.TYPE_FADE_IN);
				
				if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
				{
					initContentSelectList(false);
				}
				mStoryContentListAdapter.notifyDataSetChanged();
				break;
			}
		}
	};

	private static final int REQUEST_PAY_STATUS				= 1001;
	private static final int DURATION_ENTER_POSTPONE 		= 300;
	
	private static final int MESSAGE_BOTTOMVIEW_DEFAULT 	= 0;
	private static final int MESSAGE_BOTTOMVIEW_CHANGE 		= 1;
	
	private static final int MESSAGE_SETTING_CONTENT_LIST 				= 0;
	private static final int MESSAGE_INIT_SETTING 						= 1;
	private static final int MESSAGE_EXTENDS_VIEW 						= 2;
	private static final int MESSAGE_START_PLAY_ALL						= 3;
	private static final int MESSAGE_START_PLAY_SELECTED_LIST			= 4;
	private static final int MESSAGE_START_PLAY_ONE_ITEM				= 5;
	private static final int MESSAGE_START_QUIZ							= 6;
	private static final int MESSAGE_START_ORIGIN_DATA					= 7;
	private static final int MESSAGE_INIT_SELECTED_ITEM					= 8;

	
	private static final int DURATION_ANIMATION 			= 500;
	private static final int DURATION_INIT_ITEM	 			= 1000;
	/**
	 * 선택버튼 상태를 나타냄.  NORMAL 상태는 전체재생 과 선택을 할수 있는 상태, CHOICE 상태는 선택재생과 취소를 할 수 있다.
	 * @author 정재현
	 *
	 */
	enum SELECT_TYPE
	{
		NORMAL,
		CHOICE
	}
	private ImageView _MenuInfoImage;
	private FadeAnimationController mFadeAnimationController;
	private ArrayList<ContentItemObject>mContentItemList = new ArrayList<ContentItemObject>();
	private SELECT_TYPE mCurrentSelectType = SELECT_TYPE.NORMAL;
	private StoryContentListAdapter mStoryContentListAdapter;
	private ContentListTitleObject mContentListTitleObject;
	private String mFeatureSeriesId = "";
	private boolean isCaptionDataEmpty 	= false;
	private boolean isQuizEmpty			= false;
	private SeriesIntroductionInfoDialog mStoryContentInfoDialog;
	private int mCurrentBottomLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;
	private int mCurrentExecutePosition = -1;
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.story_content_list_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.story_content_list_main);
		}
		
		
		ButterKnife.bind(this);
		Log.f("");
		init();
	}
	
	private void init()
	{
		_ProgressWheelLayout.setVisibility(View.VISIBLE);
		_StudyContentListView.setVisibility(View.GONE);
		
		mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_EXTENDS_VIEW, DURATION_ENTER_POSTPONE);
		
		/**
		 * AppBar크기를 조절하기 위해 사용. ( ScalableLayout 적용이 안되서 ㅜㅜ)
		 */
		supportPostponeEnterTransition();
		settingAppBarLayoutHeight();
		
		mContentListTitleObject = getIntent().getParcelableExtra(Common.INTENT_STORY_CONTENT_PARAMS);
		
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			CommonUtils.getInstance(this).setStatusBar(Color.parseColor(mContentListTitleObject.getStatusBarBackground()));
		}
		settingToolbar();
		initFont();
		initText();
		setBottomLayoutButtonTextToType();
		registerFadeAnimationController();
		mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_INIT_SETTING , DURATION_ANIMATION);
	}
	
	/**
	 * 선택된 아이템 리스트를 초기화한다.
	 */
	private void initContentSelectList(boolean isNotify)
	{
		for(int i = 0 ; i < mContentItemList.size(); i++)
		{
			mContentItemList.get(i).setSelected(false);
		}
		
		if(isNotify)
		{
			mStoryContentListAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_STORY_CONTENT);
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
		mFadeAnimationController.unRegisterController();
		Log.f("");
	}
	
	/**
	 * 무료모드 일때, 미리보기를 보여주고, 결제 페이지로 이동해서 결제를 완료 했을때는, 메인페이지로 이동시켜서 결제가 완료 되었다는 것을 알려준다. 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.f("onActivityResult(" + requestCode + "," + resultCode + "," + data);
		super.onActivityResult(requestCode, resultCode, data);
		if (Feature.IS_FREE_USER == false)
		{
			switch (requestCode)
			{
			case REQUEST_PAY_STATUS:
				setResult(RESULT_OK);
				finish();
				break;
			}
		}
	}
	
	private void initTransition()
	{
		Explode explode = new Explode();
		explode.excludeTarget(android.R.id.statusBarBackground, true);
		explode.setDuration(DURATION_ENTER_POSTPONE);
		
		getWindow().setEnterTransition(explode);
		getWindow().setExitTransition(explode);
		getWindow().setAllowEnterTransitionOverlap(true);
		getWindow().setAllowReturnTransitionOverlap(true);
	}
	
	private void settingAppBarLayoutHeight()
	{
		final int DEFAULT_IMAGE_HEIGHT = Feature.IS_TABLET ? 292 : 500;
		CoordinatorLayout.LayoutParams params = (LayoutParams) _AppBarLayout.getLayoutParams();
		params.height = CommonUtils.getInstance(this).getHeightPixel(DEFAULT_IMAGE_HEIGHT);
		
		Log.i("params.height : "+params.height);
		_AppBarLayout.setLayoutParams(params);
	}
	
	private void initContentInformationList()
	{
		Log.f("mContentItemList size : " + mContentItemList.size());
		if(mFeatureSeriesId.equals("") == false)
		{
			_MenuInfoImage.setVisibility(View.VISIBLE);
		}
		_ProgressWheelLayout.setVisibility(View.GONE);
		_StudyContentListView.setVisibility(View.VISIBLE);
		
		int DEFAULT_LIST_ITEM_SIZE;
		if(Feature.IS_TABLET)
		{
			DEFAULT_LIST_ITEM_SIZE = CommonUtils.getInstance(this).getHeightPixel(140);
		}
		else
		{
			DEFAULT_LIST_ITEM_SIZE = CommonUtils.getInstance(this).getHeightPixel(220);
		}
		mStoryContentListAdapter = new StoryContentListAdapter(this);


		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		_StudyContentListView.addItemDecoration(new LineDividerItemDecoration(this, getResources().getColor(R.color.color_f6f5f6), CommonUtils.getInstance(this).getHeightPixel(2), DEFAULT_LIST_ITEM_SIZE));
		_StudyContentListView.setHasFixedSize(true);
		_StudyContentListView.setLayoutManager(linearLayoutManager);
		_StudyContentListView.setAdapter(mStoryContentListAdapter);

	}
	
	private void showFailMessage(String message)
	{
		Log.f("Fail message : " + message);
		CommonUtils.getInstance(this).showSnackMessage(_MainBaseLayout, message, getResources().getColor(R.color.color_d8232a));
	} 
	
	private void registerFadeAnimationController()
	{
		final int BOTTOM_LAYOUT_HEIGHT  = CommonUtils.getInstance(this).getHeightPixel(134);
		mCurrentBottomLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;
		
		mFadeAnimationController = new FadeAnimationController(this);
		mFadeAnimationController.addControlView(new FadeAnimationInformation(_ChoicePlayButtonLayout, 
				CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_ANIMATION,BOTTOM_LAYOUT_HEIGHT,0 ), 
				CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_ANIMATION,0,BOTTOM_LAYOUT_HEIGHT)));
	}
	
	private void initFont()
	{
		((TextView)findViewById(R.id.story_detail_first_button)).setTypeface(Font.getInstance(this).getRobotoMedium());
		((TextView)findViewById(R.id.story_detail_second_button)).setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		((TextView)findViewById(R.id.story_detail_first_button)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_all_play));
		((TextView)findViewById(R.id.story_detail_second_button)).setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice));
	}
	
	private void settingToolbar()
	{
		View customView;
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_CollapsingToolbarLayout.setContentScrimColor(Color.parseColor(mContentListTitleObject.getTitleBackground()));
		setSupportActionBar(_Toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		if(Feature.IS_TABLET)
			customView = inflater.inflate(R.layout.topbar_story_detail_menu_tablet, null);
		else
			customView = inflater.inflate(R.layout.topbar_story_detail_menu, null);
		
		getSupportActionBar().setCustomView(customView);

	    Toolbar parent =(Toolbar) customView.getParent();
	    parent.setContentInsetsAbsolute(0,0);
	    
		ImageView menuBackIcon = (ImageView)customView.findViewById(R.id.top_menu_back_click);
		menuBackIcon.setOnClickListener(mMenuItemListener);
		
		/**
		 * 서버 통신 후에 SeriesID 가 없을 경우 정보가 보여지지않는다.
		 */
		ImageView menuInfoIcon = (ImageView)customView.findViewById(R.id.top_menu_info);
		menuInfoIcon.setOnClickListener(mMenuItemListener);
		
		_MenuInfoImage = (ImageView)customView.findViewById(R.id.top_menu_info_image);
		_MenuInfoImage.setVisibility(View.GONE);
		
		ImageView menuTitleImage = (ImageView)customView.findViewById(R.id.top_menu_title_image);
		
		Glide.with(this)
				.load(mContentListTitleObject.getTitleTextImageUrl())
				.transition(withCrossFade())
				.into(menuTitleImage);

		RequestOptions options = new RequestOptions();
		options.override(Target.SIZE_ORIGINAL);

	    Glide.with(this)
				.load(mContentListTitleObject.getTitleThumbnail())
				.apply(options)
				.listener(new RequestListener<Drawable>() {

			@Override
			public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
				return false;
			}

			@Override
			public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
			{
				Log.f("");
				return false;
			}


		}).into(_StudyContentListMainImageView);

	   
	}
	
	private void showInfoDialog()
	{
		if(mFeatureSeriesId.equals(""))
		{
			return;
		}
		mStoryContentInfoDialog = new SeriesIntroductionInfoDialog(this, mFeatureSeriesId);
		mStoryContentInfoDialog.show();
	}
	
	/**
	 * 현재 선택된 타입에 따라 BottomView 버튼 텍스트를 바꾼다
	 */
	private void setBottomLayoutButtonTextToType()
	{
		if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
		{
			_ChoiceFirstButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_all_play));
			if(Feature.IS_TABLET)
			{
				_ChoiceSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice_play));
			}
			else
			{
				_ChoiceSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice));
			}
			
		}
		else if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_SELECTED)
		{
			_ChoiceFirstButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice_play));
			_ChoiceSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_cancel));
		}
	}
	
	private void requestContentListInformation()
	{
		Log.f("Content List Request ID : " + mContentListTitleObject.getStoryKeyId());
		//ContentListAsync async;
		//async = new ContentListAsync(this, mContentListTitleObject.getStoryKeyId(), mRequestListener);
		//async.execute();

		ContentListCoroutine coroutine = new ContentListCoroutine(this,mRequestListener);
		coroutine.setData(mContentListTitleObject.getStoryKeyId());
		coroutine.execute();
	}
	
	/**
	 * 선택된 플레이 리스트를 플레이어에서 플레이하기 위해 객체로 만들어 전달하기 위한 메소드 
	 * @return ContentPlayObject 플레이 될 정보
	 */
	private ContentPlayObject getSelectPlayContent()
	{
		ContentPlayObject contentPlayObject = null;

		if (mFeatureSeriesId.equals("") == false)
		{
			contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SERIES_STORY);
		} else
		{
			contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SHORT_STORY);
		}
		
		if(isCaptionDataEmpty)
			contentPlayObject.setCaptionEmpty();

		/**
		 * 시리즈 연재중인 리스트는 리스트가 거꾸로 표시 된다. 하지만 플레이어에서는 정상적으로 플레이 되어야 하므로 Reverse
		 * 하여 플레이어에게 넘길 리스트를 만든다.
		 */
		if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
		{
			Collections.reverse(mContentItemList);
		}
		
		for(int i = 0 ; i < mContentItemList.size(); i++)
		{
			if(mContentItemList.get(i).isSelected())
			{
				contentPlayObject.addPlayObject(mContentItemList.get(i));
			}
		}
		
		/**
		 * 플레이어에게 넘길 객체 생성 후 화면에 보이는 리스트는 연재중이면 reverse 형태로 보여야 하므로 다시 reverse 시킨다. 
		 */
		if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
		{
			Collections.reverse(mContentItemList);
		}
		
		return contentPlayObject;
	}
	
	/**
	 * 유저가 아이템을 선택을 했는지 확인
	 * @return
	 */
	private boolean isSelectedItemByUser()
	{
		for(int i = 0 ; i < mContentItemList.size(); i++)
		{
			if(mContentItemList.get(i).isSelected())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 플레이 리스트를 플레이어에서 플레이하기 위해 객체로 만들어 전달하기 위한 메소드 
	 * @param position -1 일 경우 전체를 플레이, 아닐시에 해당 포지션 객체만 플레이
	 * @return ContentPlayObject 플레이 될 정보
	 */
	private ContentPlayObject getPlayContentToPlay(int position)
	{
		ContentPlayObject contentPlayObject = null;

		if (mFeatureSeriesId.equals("") == false)
		{
			contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SERIES_STORY);
		} 
		else
		{
			contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SHORT_STORY);
		}

		if(isCaptionDataEmpty)
			contentPlayObject.setCaptionEmpty();
		
		int quizNotifyIndex = -1;
		quizNotifyIndex = position == -1 ? 0 : position;
		/**
		 * 시리즈 연재중인 리스트는 리스트가 거꾸로 표시 된다. 하지만 플레이어에서는 정상적으로 플레이 되어야 하므로 Reverse 하여 플레이어에게 넘길 리스트를 만든다.
		 */
		if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
		{
			Collections.reverse(mContentItemList);
			if(mContentItemList.get(mContentItemList.size() - (quizNotifyIndex +1)).isQuizUse().equals("N"))
			{
				contentPlayObject.setQuizEmpty();
			}
		}
		else
		{
			if(mContentItemList.get(quizNotifyIndex).isQuizUse().equals("N"))
			{
				contentPlayObject.setQuizEmpty();
			}
		}
		
		if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING) && position != -1)
		{
			contentPlayObject.setSelectPosition(mContentItemList.size() - (quizNotifyIndex +1));
		}
		else
		{
			contentPlayObject.setSelectPosition(position);
		}
		
		
		contentPlayObject.setPlayObjectList(new ArrayList<PlayObject>(mContentItemList));
		
		/**
		 * 플레이어에게 넘길 객체 생성 후 화면에 보이는 리스트는 연재중이면 reverse 형태로 보여야 하므로 다시 reverse 시킨다. 
		 */
		if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
		{
			Collections.reverse(mContentItemList);
		}
		return contentPlayObject;
	}
	

	
	/**
	 * 하단 바의 선택 뷰의 행동의 메소드 : 폰과 태블릿의 행동 패턴이 다르다.
	 * @param view
	 */
	private void selectPlay(View view)
	{
		switch(view.getId())
		{
		case R.id.story_detail_first_button:
			
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(StoryContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
			{
				if(NetworkUtil.isConnectNetwork(StoryContentListActivity.this))
				{
					Log.f("Content List All Play");
					GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_CONTENTLIST, Common.ANALYTICS_ACTION_STORY, Common.ANALYTICS_LABEL_AUTO_PLAY);
					mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_PLAY_ALL);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
			}
			else if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_SELECTED)
			{
				//부분을 보낸다
				if(isSelectedItemByUser() == false)
				{
					Log.f("Warning Not Selected Item");
					CommonUtils.getInstance(this).showSnackMessage(_MainBaseLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_selected_item_warning),getResources().getColor(R.color.color_ffffff));
					return;
				}
				
				if(NetworkUtil.isConnectNetwork(StoryContentListActivity.this))
				{
					Log.f("Content List Selected Items Play");
					GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_CONTENTLIST, Common.ANALYTICS_ACTION_STORY, Common.ANALYTICS_LABEL_AUTO_PLAY);
					mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_PLAY_SELECTED_LIST);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}
				
			}
			break;
		case R.id.story_detail_second_button:

			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(StoryContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			
			if(mFadeAnimationController.isAnimationing(_ChoicePlayButtonLayout))
			{
				return;
			}
			mCurrentBottomLayoutType = mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT ? Common.CHOICE_LAYOUT_TYPE_SELECTED : Common.CHOICE_LAYOUT_TYPE_DEFAULT;
			mFadeAnimationController.startAnimation(_ChoicePlayButtonLayout, FadeAnimationController.TYPE_FADE_OUT);
			mBottomAnimationHandler.sendEmptyMessageDelayed(MESSAGE_BOTTOMVIEW_CHANGE, DURATION_ANIMATION);
			break;
		}
	}
	
	/**
	 * 하단 바의 선택 뷰의 행동의 메소드 : 폰과 태블릿의 행동 패턴이 다르다.
	 * @param view
	 */
	private void selectPlayTablet(View view)
	{
		switch(view.getId())
		{
		case R.id.story_detail_first_button:
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(StoryContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
			{
				if(NetworkUtil.isConnectNetwork(StoryContentListActivity.this))
				{
					Log.f("Content List All Play");
					GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_CONTENTLIST, Common.ANALYTICS_ACTION_STORY, Common.ANALYTICS_LABEL_AUTO_PLAY);
					mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_PLAY_ALL);
					mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_INIT_SELECTED_ITEM, DURATION_INIT_ITEM);
				}
				else
				{
					MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
				}	
			}
			break;
		case R.id.story_detail_second_button:

			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(StoryContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			//부분을 보낸다
			if(isSelectedItemByUser() == false)
			{
				Log.i("Warning Not Selected Item");
				CommonUtils.getInstance(this).showSnackMessage(_MainBaseLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_selected_item_warning),getResources().getColor(R.color.color_ffffff), Gravity.CENTER);
				return;
			}
			Log.i("Content List Selected Items Play");
			if(NetworkUtil.isConnectNetwork(StoryContentListActivity.this))
			{
				GoogleAnalyticsHelper.getInstance(this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_CONTENTLIST, Common.ANALYTICS_ACTION_STORY, Common.ANALYTICS_LABEL_AUTO_PLAY);
				mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_PLAY_SELECTED_LIST);
				mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_INIT_SELECTED_ITEM, DURATION_INIT_ITEM);
			}
			else
			{
				MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
			}
			break;
		}
	}
	
	@OnClick({R.id.story_detail_first_button, R.id.story_detail_second_button})
	public void onItemSelected(View view)
	{
		if(Feature.IS_TABLET)
		{
			selectPlayTablet(view);
		}
		else
		{
			selectPlay(view);
		}
	}
	
	
	public class StoryContentListAdapter extends RecyclerView.Adapter<StoryContentListAdapter.ViewHolder>
	{
		private Context mContext;
		private final int INDEX_MARGIN_LEFT = Feature.IS_TABLET ? 270 : 292;
		private final int INDEX_MARGIN_TOP = Feature.IS_TABLET ? 56 : 80;

		public StoryContentListAdapter(Context context)
		{
			Log.f("");
			mContext = context;
		}
		


		@Override
		public int getItemCount()
		{
			return mContentItemList.size();
		}


		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int viewType)
		{
			Log.f("");

			View view;
			if(Feature.IS_TABLET)
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.story_content_list_item_tablet, container, false);
			else
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.story_content_list_item, container, false);
			return new ViewHolder(view);
		}

        @Override
		public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position)
		{
			changeLayoutToLastPosition(holder, position);

			String title = mContentItemList.get(position).getTitle();
			String title_sub = Feature.IS_LANGUAGE_ENG == true ? mContentItemList.get(position).getSubTitleEn() : mContentItemList.get(position).getSubTitleKo();
			
			try
			{
				if(title_sub.equals(""))
				{
					holder._ContentText.setText(title);
					holder._BaseLayout.moveChildView(holder._ContentIndexText, INDEX_MARGIN_LEFT, INDEX_MARGIN_TOP);
				}
				else
				{
					if(Feature.IS_TABLET)
					{
						holder._ContentText
						.setSeparateText(title, "\n"+title_sub)
						.setSeparateColor(mContext.getResources().getColor(R.color.color_403f4e), mContext.getResources().getColor(R.color.color_93939c))
						.setSeparateTextSize(CommonUtils.getInstance(StoryContentListActivity.this).getPixel(28), CommonUtils.getInstance(StoryContentListActivity.this).getPixel(24))
						.showView();
					}
					else
					{
						holder._ContentText
						.setSeparateText(title, "\n"+title_sub)
						.setSeparateColor(mContext.getResources().getColor(R.color.color_403f4e), mContext.getResources().getColor(R.color.color_93939c))
						.setSeparateTextSize(CommonUtils.getInstance(StoryContentListActivity.this).getPixel(42), CommonUtils.getInstance(StoryContentListActivity.this).getPixel(32))
						.showView();
					}
				}
				
			}catch(NullPointerException e)
			{
				
			}
			
			if(Feature.IS_TABLET)
			{
				holder._ItemCheckImage.setVisibility(View.VISIBLE);
			}
			else
			{
				if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
				{
					holder._ItemCheckImage.setVisibility(View.GONE);
				}
				else
				{
					holder._ItemCheckImage.setVisibility(View.VISIBLE);
				}	
			}
		
			
			if(mContentItemList.get(position).isSelected())
			{
				holder._ItemCheckImage.setImageResource(R.drawable.check_on);
			}
			else
			{
				holder._ItemCheckImage.setImageResource(R.drawable.check_off);
			}
			
			if(mFeatureSeriesId.equals("") == false)
			{
				if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
				{
					holder._ContentIndexText.setText(getIndexFromPosition(mContentItemList.size() - position));
				}
				else
				{
					holder._ContentIndexText.setText(getIndexFromPosition(position+1));
				}
				
			}
			else
			{
				holder._ContentIndexText.setVisibility(View.INVISIBLE);
			}
			
			Glide.with(mContext)
					.load(mContentItemList.get(position).image_url)
					.transition(withCrossFade())
					.into(holder._ThumnailImage);

			if(Feature.IS_TABLET)
			{
				holder._ItemCheckImage.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						if(Feature.IS_FREE_USER)
						{
							showFailMessage(CommonUtils.getInstance(StoryContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
							return;
						}
						
						boolean isSelected = mContentItemList.get(position).isSelected() == false;
						
						Log.f("Choice Item Position : "+ position +" , isSelected : "+ isSelected);
						mContentItemList.get(position).setSelected(isSelected);
						notifyItemChanged(position);
					}
				});
			}
			
			holder._ItemClickTerritory.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
					{
						Log.f("Content Item Play Position : "+ position);
						mCurrentExecutePosition = position;
						if(NetworkUtil.isConnectNetwork(mContext))
						{
							mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_PLAY_ONE_ITEM);
						}
						else
						{
							MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
						}
						
					}
					else
					{
						boolean isSelected = mContentItemList.get(position).isSelected() == false;
						
						Log.f("Choice Item Position : "+ position +" , isSelected : "+ isSelected);
						mContentItemList.get(position).setSelected(isSelected);
						notifyItemChanged(position);
					}
				}
			});
			
			if(mContentItemList.get(position).isFeatureUse().equals("N"))
			{
				holder._OriginTextButton.setVisibility(View.INVISIBLE);
			}
			
			if(mContentItemList.get(position).isQuizUse().equals("N"))
			{
				holder._QuizButton.setVisibility(View.INVISIBLE);
			}
			
			holder._OriginTextButton.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if(Feature.IS_FREE_USER)
					{
						showFailMessage(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_paid_user_available));
						return;
					}
					
					Log.f("Study Data Information Click FC ID : "+ mContentItemList.get(position).fc_id);
					mCurrentExecutePosition = position;
					if(NetworkUtil.isConnectNetwork(mContext))
					{
						mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_ORIGIN_DATA);
					}
					else
					{
						MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
					}
					
				}
			});
			
			holder._QuizButton.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Log.f("Play Quiz Click FC ID : "+ mContentItemList.get(position).fc_id);
					
					if(Feature.IS_FREE_USER)
					{
						showFailMessage(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_paid_user_available));
						return;
					}
					
					mCurrentExecutePosition = position;
					if(NetworkUtil.isConnectNetwork(mContext))
					{
						mRequestInformationHandler.sendEmptyMessage(MESSAGE_START_QUIZ);
					}
					else
					{
						MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
					}
				}
			});

		}


		
		/**
		 * 하단의 선택 레이아웃이 ListView 를 가려서 하나의 레이아웃을 가짜로 추가하여 표시한다. 그래서 제일 마지막 포지션일때는 INVISIBLE 하여 가려서 영역만 표시해준다.
		 * @param holder ViewHolder
		 * @param position 현재 포지션
		 */
		private void changeLayoutToLastPosition(ViewHolder holder, int position)
		{
			int lastPosition = mContentItemList.size() -1;
			if(lastPosition == position)
			{
				holder._FooterLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._FooterLayout.setVisibility(View.GONE);
			}
		}
		
		private String getIndexFromPosition(int position)
		{
			if(position < 10)
			{
				return "0"+String.valueOf(position);
			}
			else
			{
				return String.valueOf(position);
			}
		}

		public class ViewHolder extends RecyclerView.ViewHolder
		{
			@BindView(R.id.content_list_item_base_layout)
			ScalableLayout _BaseLayout;

			@BindView(R.id.content_list_item_footer_layout)
			ScalableLayout _FooterLayout;

			@BindView(R.id.content_list_item_thumbnail)
			ImageView _ThumnailImage;

			@BindView(R.id.content_list_item_check)
			ImageView _ItemCheckImage;

			@BindView(R.id.content_list_item_click_territory)
			ImageView _ItemClickTerritory;

			@BindView(R.id.content_list_item_index)
			TextView	_ContentIndexText;

			@BindView(R.id.content_list_item_content_text)
			SeparateTextView _ContentText;

			@BindView(R.id.content_list_item_quiz_button)
			ImageView _QuizButton;

			@BindView(R.id.content_list_item_origin_text_button)
			ImageView _OriginTextButton;

			public ViewHolder(View view)
			{
				super(view);
				ButterKnife.bind(this, view);
				initFont();
			}

			private void initFont()
			{
				_ContentIndexText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_ContentIndexText.setTextColor(Color.parseColor(mContentListTitleObject.getTitleBackground()));
				_ContentText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
		}
	}
	
	private OnClickListener mMenuItemListener = new View.OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.top_menu_back_click:
				onBackPressed();
				break;
			case R.id.top_menu_info:
				showInfoDialog();
				break;
			}
		}
	};
	
	private AsyncListener mRequestListener = new AsyncListener()
	{

		@Override
		public void onRunningStart(String code)
		{
			_ProgressWheelLayout.setVisibility(View.VISIBLE);
			_StudyContentListView.setVisibility(View.GONE);
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
			Log.f("");
			if(((BaseResult)mObject).getResult().equals(BaseResult.RESULT_OK))
			{
				mContentItemList 	= ((ContentListResult)mObject).getContentList();
				
				if(mContentListTitleObject.getSeriesStatus().equals(ContentListTitleObject.SERIES_ING))
				{
					Collections.reverse(mContentItemList);
				}
				mFeatureSeriesId 	= 	((ContentListResult)mObject).getSeriesID();
				isCaptionDataEmpty	=	((ContentListResult)mObject).isCaptionEmpty();
				
				mRequestInformationHandler.sendEmptyMessage(MESSAGE_SETTING_CONTENT_LIST);
			}
			else
			{
				if(((BaseResult)mObject).isAuthenticationBroken())
				{
					Toast.makeText(StoryContentListActivity.this, ((BaseResult)mObject).getMessage(), Toast.LENGTH_LONG).show();
					finish();
					
					MainSystemFactory.getInstance().resetScene(MainSystemFactory.MODE_INTRODUCE , true);
				}
			}
		}
	};
	
}

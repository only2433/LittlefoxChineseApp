package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.async.SongCategoryContentListAsync;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.SongCategoryObject;
import com.littlefox.chinese.edu.object.result.SongCardResult;
import com.littlefox.chinese.edu.object.result.SongContentListInformationResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.chinese.edu.view.itemdecoration.LineDividerItemDecoration;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.controller.FadeAnimationController;
import com.littlefox.library.view.controller.FadeAnimationInformation;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SongContentListActivity extends BaseActivity
{
	@BindView(R.id.main_content)
	CoordinatorLayout _MainContentBaseLayout;
	
	@BindView(R.id.title_base_layout)
	ScalableLayout _BaseLayout;
	
	@BindView(R.id.song_content_list_title)
	TextView _TitleText;
	
	@BindView(R.id.song_content_list_close)
	ImageView _CloseButton;
	
	@BindView(R.id.song_content_list_description)
	TextView _DescriptionText;
	
	@BindView(R.id.song_detail_list)
	RecyclerView _SongListView;
	
	@BindView(R.id.progress_wheel_layout)
	ScalableLayout _ProgressWheelLayout;
	
	@BindView(R.id.song_detail_choice_button_layout)
	ScalableLayout _BottomButtonLayout;
	
	@BindView(R.id.song_detail_first_button)
	TextView _BottomFirstButton;
	
	@BindView(R.id.song_detail_second_button)
	TextView _BottomSecondButton;
	
	Handler mBottomAnimationHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_BOTTOMVIEW_CHANGE:
				setBottomLayoutButtonTextToType();
				mFadeAnimationController.startAnimation(_BottomButtonLayout, FadeAnimationController.TYPE_FADE_IN);
				
				if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
				{
					initContentSelectList();
				}
				mSongCardListAdapter.notifyDataSetChanged();
				break;
			}
		}
	};
	
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
				requestSongContentList(mSongCategoryObject.getSMID());
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
					MainSystemFactory.getInstance().startActivityNoAnimationForResult(MainSystemFactory.MODE_PLAYER , REQUEST_PAY_COMPLETE, getPlayContentToPlay(mCurrentExecutePosition));
				}
				else
				{
					MainSystemFactory.getInstance().startActivityNoAnimation(MainSystemFactory.MODE_PLAYER ,getPlayContentToPlay(mCurrentExecutePosition));
				}
				

				break;
			case MESSAGE_START_ORIGIN_DATA:
				getWindow().setExitTransition(null);
				MainSystemFactory.getInstance().startActivityWithMaterialAnimation(MainSystemFactory.MODE_ORIGIN_DATA, mSongCardResultList.get(mCurrentExecutePosition).fc_id);
				break;
			case MESSAGE_INIT_SELECTED_ITEM:
				initSelectedItem();
				break;
			}
		}
		
	};
	
	private static final int REQUEST_PAY_COMPLETE			= 1001;
	private static final int DURATION_ENTER_POSTPONE 		= 300;
	private static final int DURATION_ANIMATION 			= 500;
	private static final int DURATION_INIT_ITEM	 			= 1000;
	
	private static final int MESSAGE_BOTTOMVIEW_DEFAULT 	= 0;
	private static final int MESSAGE_BOTTOMVIEW_CHANGE 		= 1;
	
	private static final int MESSAGE_SETTING_CONTENT_LIST 				= 0;
	private static final int MESSAGE_INIT_SETTING 						= 1;
	private static final int MESSAGE_EXTENDS_VIEW 						= 2;
	private static final int MESSAGE_START_PLAY_ALL						= 3;
	private static final int MESSAGE_START_PLAY_SELECTED_LIST			= 4;
	private static final int MESSAGE_START_PLAY_ONE_ITEM				= 5;
	private static final int MESSAGE_START_ORIGIN_DATA					= 7;
	private static final int MESSAGE_INIT_SELECTED_ITEM					= 8;
	
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
	
	private String mFeatureSeriesId = "";
	private int mCurrentBottomLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;
	private SongCategoryObject mSongCategoryObject;
	private ArrayList<SongCardResult> mSongCardResultList = new ArrayList<SongCardResult>();
	

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private SongCardListAdapter mSongCardListAdapter;
	private int mCurrentExecutePosition = -1;
	private FadeAnimationController mFadeAnimationController;
	
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
			setContentView(R.layout.song_content_list_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.song_content_list_main);
		}
		
		
		ButterKnife.bind(this);
		
		Log.f("");
		init();
	}

	private void init()
	{
		mSongCategoryObject = getIntent().getParcelableExtra(Common.INTENT_SONG_CONTENT_PARAMS);
		
		initFont();
		_ProgressWheelLayout.setVisibility(View.VISIBLE);
		_SongListView.setVisibility(View.GONE);
		
		mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_EXTENDS_VIEW, DURATION_ENTER_POSTPONE);
		supportPostponeEnterTransition();
		setBottomLayoutButtonTextToType();
		registerFadeAnimationController();
		
		mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_INIT_SETTING , DURATION_ANIMATION);
	}
	
	private void initFont()
	{
		_TitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_DescriptionText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_BottomFirstButton.setTypeface(Font.getInstance(this).getRobotoMedium());
		_BottomSecondButton.setTypeface(Font.getInstance(this).getRobotoMedium());
		_TitleText.setText(mSongCategoryObject.getTitle());
		_DescriptionText.setText(mSongCategoryObject.getDescription());
	
	}
	
	private void initContentInformationList()
	{
		_ProgressWheelLayout.setVisibility(View.GONE);
		_SongListView.setVisibility(View.VISIBLE);
		
		
		
		mSongCardListAdapter = new SongCardListAdapter(this);
		_SongListView.setLayoutManager(new LinearLayoutManager(this));
		_SongListView.addItemDecoration(new LineDividerItemDecoration(this, getResources().getColor(R.color.color_f6f5f6), CommonUtils.getInstance(this).getHeightPixel(2)));
		_SongListView.setHasFixedSize(true);
		_SongListView.setAdapter(mSongCardListAdapter);
	}
	
	private void initSelectedItem()
	{
		for(int  i = 0; i < mSongCardResultList.size(); i++)
		{
			mSongCardResultList.get(i).setSelected(false);
		}
		
		mSongCardListAdapter.notifyDataSetChanged();
	}
	
	private void showFailMessage(String message)
	{
		Log.f("Fail message : " + message);
		CommonUtils.getInstance(this).showSnackMessage(_MainContentBaseLayout, message, getResources().getColor(R.color.color_d8232a));
	} 

	
	/**
	 * 현재 선택된 타입에 따라 BottomView 버튼 텍스트를 바꾼다
	 */
	private void setBottomLayoutButtonTextToType()
	{
		if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
		{
			_BottomFirstButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_all_play));
			if(Feature.IS_TABLET)
			{
				_BottomSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice_play));
			}
			else
			{
				_BottomSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice));
			}
			
		}
		else if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_SELECTED)
		{
			_BottomFirstButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_choice_play));
			_BottomSecondButton.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_cancel));
		}
	}
	
	private void registerFadeAnimationController()
	{
		final int BOTTOM_LAYOUT_HEIGHT  = CommonUtils.getInstance(this).getHeightPixel(134);
		mCurrentBottomLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;
		
		mFadeAnimationController = new FadeAnimationController(this);
		mFadeAnimationController.addControlView(new FadeAnimationInformation(_BottomButtonLayout, 
				CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_ANIMATION,BOTTOM_LAYOUT_HEIGHT,0 ), 
				CommonUtils.getInstance(this).getTranslateYAnimation(DURATION_ANIMATION,0,BOTTOM_LAYOUT_HEIGHT)));
	}
	
	private void requestSongContentList(String smID)
	{
		SongCategoryContentListAsync async = new SongCategoryContentListAsync(this, smID, mAsyncListener);
		async.execute();
	}
	
	/**
	 * 선택된 플레이 리스트를 플레이어에서 플레이하기 위해 객체로 만들어 전달하기 위한 메소드 
	 * @return ContentPlayObject 플레이 될 정보
	 */
	private ContentPlayObject getSelectPlayContent()
	{
		ContentPlayObject contentPlayObject = null;
		contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		for(int i = 0 ; i < mSongCardResultList.size(); i++)
		{
			if(mSongCardResultList.get(i).isSelected() && mSongCardResultList.get(i).getId() != -1)
			{
				contentPlayObject.addPlayObject(mSongCardResultList.get(i));
			}
		}
		
		return contentPlayObject;
	}
	
	/**
	 * 플레이 리스트를 플레이어에서 플레이하기 위해 객체로 만들어 전달하기 위한 메소드 
	 * @param position -1 일 경우 전체를 플레이, 아닐시에 해당 포지션 객체만 플레이
	 * @return ContentPlayObject 플레이 될 정보
	 */
	private ContentPlayObject getPlayContentToPlay(int position)
	{
		ArrayList<PlayObject> playAvailableList = new ArrayList<PlayObject>();
		ContentPlayObject contentPlayObject = null;
		contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		
		contentPlayObject.setSelectPosition(position);
		
		for(int i = 0 ; i < mSongCardResultList.size(); i++)
		{
			Log.i("mSongCardResultList.get(i).getId() : "+mSongCardResultList.get(i).getId());
			if(mSongCardResultList.get(i).getId() != -1)
			{
				playAvailableList.add(mSongCardResultList.get(i));
			}
		}
		
		contentPlayObject.setPlayObjectList(new ArrayList<PlayObject>(playAvailableList));
		return contentPlayObject;
	}
	
	/**
	 * 선택된 아이템 리스트를 초기화한다.
	 */
	private void initContentSelectList()
	{
		for(int i = 0 ; i < mSongCardResultList.size(); i++)
		{
			mSongCardResultList.get(i).setSelected(false);
		}
	}
	
	
	/**
	 * 유저가 아이템을 선택을 했는지 확인
	 * @return
	 */
	private boolean isSelectedItemByUser()
	{
		for(int i = 0 ; i < mSongCardResultList.size(); i++)
		{
			if(mSongCardResultList.get(i).isSelected())
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_SONG_CONTENT_LIST);
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
			case REQUEST_PAY_COMPLETE:
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
	
	
	/**
	 * 하단 바의 선택 뷰의 행동의 메소드 : 폰과 태블릿의 행동 패턴이 다르다.
	 * @param view
	 */
	private void selectPlay(View view)
	{
		switch(view.getId())
		{
		case R.id.song_detail_first_button:
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
			{
				if(NetworkUtil.isConnectNetwork(SongContentListActivity.this))
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
					CommonUtils.getInstance(this).showSnackMessage(_MainContentBaseLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_selected_item_warning),getResources().getColor(R.color.color_ffffff));
					return;
				}
				
				if(NetworkUtil.isConnectNetwork(SongContentListActivity.this))
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
		case R.id.song_detail_second_button:

			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			
			if(mFadeAnimationController.isAnimationing(_BottomButtonLayout))
			{
				return;
			}
			mCurrentBottomLayoutType = mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT ? Common.CHOICE_LAYOUT_TYPE_SELECTED : Common.CHOICE_LAYOUT_TYPE_DEFAULT;
			mFadeAnimationController.startAnimation(_BottomButtonLayout, FadeAnimationController.TYPE_FADE_OUT);
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
		case R.id.song_detail_first_button:
			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			if(mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
			{
				if(NetworkUtil.isConnectNetwork(SongContentListActivity.this))
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
		case R.id.song_detail_second_button:

			if(Feature.IS_FREE_USER)
			{
				showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
				return;
			}
			//부분을 보낸다
			if(isSelectedItemByUser() == false)
			{
				Log.i("Warning Not Selected Item");
				CommonUtils.getInstance(this).showSnackMessage(_MainContentBaseLayout, CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_selected_item_warning),getResources().getColor(R.color.color_ffffff), Gravity.CENTER);
				return;
			}
			Log.i("Content List Selected Items Play");
			if(NetworkUtil.isConnectNetwork(SongContentListActivity.this))
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
	
	@OnClick({R.id.song_detail_first_button, R.id.song_detail_second_button})
	public void onItemSelected(View view)
	{
		Log.i("view ID " + view.getId());

		if(Feature.IS_TABLET)
		{
			selectPlayTablet(view);
		}
		else
		{
			selectPlay(view);
		}
	}
	
	@OnClick(R.id.song_content_list_close)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.song_content_list_close:
			super.onBackPressed();
			break;
		}
	}
	
	
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
				switch(code)
				{
				case Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST:
					mSongCardResultList = new ArrayList<SongCardResult>();
					mSongCardResultList = ((SongContentListInformationResult)mObject).getSongCardList();
					mRequestInformationHandler.sendEmptyMessageDelayed(MESSAGE_SETTING_CONTENT_LIST, DURATION_ANIMATION);
					break;
				}
			}
			else
			{
				
			}
		}

	};
	
	public class SongCardListAdapter extends RecyclerView.Adapter<SongCardListAdapter.ViewHolder>
	{
		private Context mContext;
		
		public SongCardListAdapter(Context context)
		{
			mContext = context;
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder
		{
			ScalableLayout _SectionLayout;
			ScalableLayout _ItemBaseLayout;
			ScalableLayout _FooterLayout;
			
			TextView _SectionTitleText;
			TextView _ItemTitleText;
			TextView _ItemSubTitleText;
			
			ImageView _ItemCheckButton;
			ImageView _ItemThumnailImage;
			ImageView _ItemClickTerritory;
			ImageView _ItemThumnailComingSoonCover;
			ImageView _ItemOriginButton;

			public ViewHolder(View view)
			{
				super(view);
				_SectionLayout 	= ButterKnife.findById(view, R.id.song_item_section_layout);
				_SectionTitleText 	= ButterKnife.findById(view, R.id.item_section_title);
				_ItemBaseLayout 	= ButterKnife.findById(view, R.id.song_list_item_base_layout);
				_FooterLayout		= ButterKnife.findById(view, R.id.song_item_footer_layout);				
				
				_ItemTitleText		= ButterKnife.findById(view, R.id.song_list_item_title);
				_ItemSubTitleText	= ButterKnife.findById(view, R.id.song_list_item_content_text);
				
				_ItemCheckButton		= ButterKnife.findById(view, R.id.song_list_item_check);
				_ItemClickTerritory		= ButterKnife.findById(view, R.id.song_list_item_click_territory);
				_ItemThumnailImage 		= ButterKnife.findById(view, R.id.song_list_item_thumbnail);
				_ItemThumnailComingSoonCover	= ButterKnife.findById(view, R.id.song_list_item_cover);
				_ItemOriginButton		= ButterKnife.findById(view, R.id.song_list_item_origin_text_button);
				initFont();
				initText();
			}
			
			private void initFont()
			{
				_SectionTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_ItemTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_ItemSubTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
			
			private void initText()
			{
				_SectionTitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.song_all_title));
			}
			
			/**
			 * 해당 포지션에 맞게 뷰를 보여준다 헤더, 아이템뷰 , 푸터
			 * @param position
			 */
			public void setVisibleLayout(int position)
			{
				if(position == mSongCardResultList.size() -1)
				{
					_SectionLayout.setVisibility(View.GONE);
					_ItemBaseLayout.setVisibility(View.VISIBLE);
					_FooterLayout.setVisibility(View.VISIBLE);
				}
				else
				{
					_SectionLayout.setVisibility(View.GONE);
					_ItemBaseLayout.setVisibility(View.VISIBLE);
					_FooterLayout.setVisibility(View.GONE);
				}
			}
			
		}

		@Override
		public int getItemCount()
		{
			return mSongCardResultList.size();
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position)
		{
			holder.setVisibleLayout(position);

			Glide.with(mContext)
					.load(mSongCardResultList.get(position).image_url)
					.transition(withCrossFade())
					.into(holder._ItemThumnailImage);

			if(mSongCardResultList.get(position).getId() == -1)
			{
				holder._ItemThumnailComingSoonCover.setVisibility(View.VISIBLE);
				holder._ItemOriginButton.setVisibility(View.GONE);

				Glide.with(mContext)
						.load(mSongCardResultList.get(position).getComingSoonImageUrl())
						.transition(withCrossFade())
						.into(holder._ItemThumnailComingSoonCover);

			}
			else
			{
				holder._ItemThumnailComingSoonCover.setVisibility(View.GONE);
				holder._ItemOriginButton.setVisibility(View.VISIBLE);
			}
			
			if((mCurrentBottomLayoutType == Common.CHOICE_LAYOUT_TYPE_SELECTED && mSongCardResultList.get(position).getId() != -1)
					|| (Feature.IS_TABLET  && mSongCardResultList.get(position).getId() != -1))
			{
				holder._ItemCheckButton.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._ItemCheckButton.setVisibility(View.GONE);
			}
			
			holder._ItemTitleText.setText(mSongCardResultList.get(position).getTitle());
			if(mSongCardResultList.get(position).getId() == -1)
			{
				try
				{
					StringBuilder builder = new StringBuilder(mSongCardResultList.get(position).getOpenDate());
					builder.insert(4,".").insert(7, ".");
					holder._ItemSubTitleText.setText(builder.toString());
				}catch(Exception e)
				{
					Log.f(e.getMessage());
				}
				
			}
			else
			{
				if(Feature.IS_LANGUAGE_ENG)
				{
					holder._ItemSubTitleText.setText(mSongCardResultList.get(position).getTitleEN());
				}
				else
				{
					holder._ItemSubTitleText.setText(mSongCardResultList.get(position).getTitleKO());
				}
				
			}
			
			
			if(mSongCardResultList.get(position).isSelected())
			{
				holder._ItemCheckButton.setImageResource(R.drawable.check_on);
			}
			else
			{
				holder._ItemCheckButton.setImageResource(R.drawable.check_off);
			}
			
			if(Feature.IS_TABLET)
			{
				holder._ItemCheckButton.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{	
						if(Feature.IS_FREE_USER)
						{
							showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
							return;
						}
						
						boolean isSelected = mSongCardResultList.get(position).isSelected() == false;
						
						Log.f("Choice Item Position : "+ position +" , isSelected : "+ isSelected);
						mSongCardResultList.get(position).setSelected(isSelected);
						notifyItemChanged(position);
					}
				});
			}
			
			holder._ItemOriginButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(Feature.IS_FREE_USER)
					{
						showFailMessage(CommonUtils.getInstance(SongContentListActivity.this).getLanguageTypeString(R.array.message_paid_user_available));
						return;
					}
					
					Log.f("Study Data Information Click FC ID : "+ mSongCardResultList.get(position).fc_id);
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
		
			holder._ItemClickTerritory.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(mSongCardResultList.get(position).getId() != -1)
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
							boolean isSelected = mSongCardResultList.get(position).isSelected() == false;
							
							Log.f("Choice Item Position : "+ position +" , isSelected : "+ isSelected);
							mSongCardResultList.get(position).setSelected(isSelected);
							notifyItemChanged(position);
						}
					}
					
				}
			});
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int position)
		{
			View view;
			if(Feature.IS_TABLET)
			{
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.song_main_list_item_tablet, container, false);
			}
			else
			{
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.song_main_list_item, container, false);
			}
			
			return new ViewHolder(view);
		}
	}
}

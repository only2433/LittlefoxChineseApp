package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.FeaturePlayObject;
import com.littlefox.chinese.edu.object.result.HomeDataResult;
import com.littlefox.chinese.edu.view.BannerLinkView;
import com.littlefox.chinese.edu.view.BannerLinkView.OnBannerClickListener;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubHomeFragment extends Fragment implements MainHolder
{
	@BindView(R.id.base_today_layout)
	ScalableLayout _BaseTodayLayout;
	
	@BindView(R.id.today_main_view)
	ImageView _TodayImageView;
	
	@BindView(R.id.home_button_nihao_china)
	ImageView _BasicNihaoButton;
	
	@BindView(R.id.home_button_step_china)
	ImageView _BasicStepButton;
	
	@BindViews({R.id.home_card_1_title_text, R.id.home_card_2_title_text})
	List<TextView> _FeatureTitleList;
	
	@BindViews({R.id.home_study_1_card_1_view, R.id.home_study_1_card_2_view, R.id.home_study_2_card_1_view, R.id.home_study_2_card_2_view})
	List<ImageView> _FeatureImageList;
	
	@BindViews({R.id.home_study_1_card_title_1, R.id.home_study_1_card_title_2, R.id.home_study_2_card_title_1, R.id.home_study_2_card_title_2})
	List<TextView> _FeatureSubTextList;

	
	@BindView(R.id.home_banner_view)
	BannerLinkView _BannerLinkView;
	
	@BindViews({R.id.today_monday_text, R.id.today_tuesday_text, R.id.today_wendsday_text, R.id.today_thursday_text, R.id.today_friday_text})
	List<TextView>_DayTextList;
	
	@BindViews({R.id.today_monday_focus, R.id.today_tuesday_focus, R.id.today_wendsday_focus, R.id.today_thursday_focus, R.id.today_friday_focus})
	List<ImageView> _DayFocusList;
	
	@BindViews({R.id.home_autobiography_information_text_1, R.id.home_autobiography_information_text_2, R.id.home_autobiography_information_text_3})
	List<TextView> _AutobiographyInformationTitleList;
	
	@BindViews({R.id.home_autobiography_information_name_1, R.id.home_autobiography_information_name_2, R.id.home_autobiography_information_name_3})
	List<TextView> _AutobiographyInformationSubtitleList;
	
	@BindViews({R.id.home_autobiography_image_1, R.id.home_autobiography_image_2, R.id.home_autobiography_image_3})
	List<ImageView> _AutobiographyInformationImageList;
	
	@BindView(R.id.home_autobiography_base_layout)
	ScalableLayout _AutobiographyBaseLayout;
	
	private static final String[] DAY_TEXT_KOR = {"월","화", "수", "목", "금"};
	private static final String[] DAY_TEXT_ENG = {"MON","TUE", "WED", "THU", "FRI"};
	/***
	 * 특정해상도 이하에서 이미지가 안보이는 문제 발생하여 수정하기 위해
	 */
	@BindViews({R.id.date_line_1 , R.id.date_line_2, R.id.date_line_3, R.id.date_line_4})
	List<ImageView> _ExceptionTodayLineList;
	
	public static int NEW_RELEASE_DAY_SIZE = 5;
	
	private static final int INDEX_MON_DAY 			= 0;
	private static final int INDEX_THES_DAY 		= 1;
	private static final int INDEX_WENDS_DAY 		= 2;
	private static final int INDEX_THURS_DAY 		= 3;
	private static final int INDEX_FRI_DAY 			= 4;
	
	private static final int FEATURE_SECTION_SIZE 	= 2;
	/**
	 * 해당 부분은 1080 이하 사이즈에서 Line 이 2Pixel은 보이지않아 , 그럴경우 4 Pixel 로 조절하기 위해 사용
	 */
	private static final int TODAY_LINE_HEIGHT = 124;
	private static final int TODAY_LINE_MARGIN_TOP = 587;
	private static final int TODAY_LINE_MARGIN_LEFT = 216;
	private static final int TODAY_LINE_EXCEPTION_WIDTH = 4;

	private Context mContext;
	private HomeDataResult mHomeDataResult;
	private OnMainSubTabsEventListener mOnMainSubTabsEventListener;

	
	public static MainSubHomeFragment getInstance()
	{
		return new MainSubHomeFragment();
	}
	
	
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}



	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_sub_home_fragment, container, false);
		ButterKnife.bind(this, view);

		initView();
		initFocusOnToday();
		initFeatureSeries();
		insertBannerInformationData();
		initFont(view);
		initText(view);
		initAutobiographyInformation();
		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}
	
	private void initView()
	{
		if(CommonUtils.getInstance(mContext).isDisplayMinimumSize())
		{
			processVisibleTodayLine();
		}
		
		if(Feature.IS_LANGUAGE_ENG)
		{
			_BasicStepButton.setImageResource(R.drawable.thumbnail_basics_en);
		}
		else
		{
			_BasicStepButton.setImageResource(R.drawable.thumbnail_basics);
		}
	}

	@OnClick({R.id.today_monday_text, R.id.today_tuesday_text, R.id.today_wendsday_text, R.id.today_thursday_text, R.id.today_friday_text, R.id.home_button_nihao_china, R.id.home_button_step_china
		,R.id.home_study_1_card_1_view, R.id.home_study_1_card_2_view, R.id.home_study_2_card_1_view, R.id.home_study_2_card_2_view, R.id.home_icon_introduce_littlefox, R.id.home_icon_guide_littlefox})
	public void onSelectedOnDay(View v)
	{
		switch(v.getId())
		{
		case R.id.today_monday_text:
			Log.f("Select INDEX_MON_DAY");
			setFocusOnDay(INDEX_MON_DAY);
			break;
		case R.id.today_tuesday_text:
			Log.f("Select INDEX_THES_DAY");
			setFocusOnDay(INDEX_THES_DAY);
			break;
		case R.id.today_wendsday_text:
			Log.f("Select INDEX_WENDS_DAY");
			setFocusOnDay(INDEX_WENDS_DAY);
			break;
		case R.id.today_thursday_text:
			Log.f("Select INDEX_THURS_DAY");
			setFocusOnDay(INDEX_THURS_DAY);
			break;
		case R.id.today_friday_text:
			Log.f("Select INDEX_FRI_DAY");
			setFocusOnDay(INDEX_FRI_DAY);
			break;
		case R.id.home_button_nihao_china:
			mOnMainSubTabsEventListener.onCreateChineseStep1Content();
			break;
		case R.id.home_button_step_china:
			mOnMainSubTabsEventListener.onMoveStudyDataStep1List();
			break;
		case R.id.home_study_1_card_1_view:
			GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																	Common.ANALYTICS_ACTION_CONTENTPLAY + " " +mHomeDataResult.getFeatureList().get(0).list.get(0).cont_name, 
																	mHomeDataResult.getFeatureList().get(0).list.get(0).getContentId() + Common.ANALYTICS_LABEL_PLAY);
			mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(mHomeDataResult.getFeatureList().get(0).list.get(0)));
			break;
		case R.id.home_study_1_card_2_view:
			GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																	Common.ANALYTICS_ACTION_CONTENTPLAY + " " +mHomeDataResult.getFeatureList().get(0).list.get(1).cont_name, 
																	mHomeDataResult.getFeatureList().get(0).list.get(1).getContentId() + Common.ANALYTICS_LABEL_PLAY);
			mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(mHomeDataResult.getFeatureList().get(0).list.get(1)));
			break;
		case R.id.home_study_2_card_1_view:
			GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																	Common.ANALYTICS_ACTION_CONTENTPLAY + " " +mHomeDataResult.getFeatureList().get(1).list.get(0).cont_name, 
																	mHomeDataResult.getFeatureList().get(1).list.get(0).getContentId() + Common.ANALYTICS_LABEL_PLAY);
			mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(mHomeDataResult.getFeatureList().get(1).list.get(0)));
			break;
		case R.id.home_study_2_card_2_view:
			GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																	Common.ANALYTICS_ACTION_CONTENTPLAY + " " +mHomeDataResult.getFeatureList().get(1).list.get(1).cont_name, 
																	mHomeDataResult.getFeatureList().get(1).list.get(1).getContentId() + Common.ANALYTICS_LABEL_PLAY);
			mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(mHomeDataResult.getFeatureList().get(1).list.get(1)));
			break;
		case R.id.home_icon_introduce_littlefox:
			mOnMainSubTabsEventListener.onStartStepLittlefoxIntroduce();
			break;
		case R.id.home_icon_guide_littlefox:
			mOnMainSubTabsEventListener.onStartStepStudyGuide();
			break;
		}
	}
	
	@OnClick(R.id.footer_link_logo_view)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.footer_link_logo_view:
			if(Feature.IS_LANGUAGE_ENG)
			{
				mOnMainSubTabsEventListener.onBannerClick(Common.WEBVIEW_URL_CHINESE_MAIN_EN);
			}
			else
			{
				mOnMainSubTabsEventListener.onBannerClick(Common.WEBVIEW_URL_CHINESE_MAIN_KO);
			}
			
			break;
		}
	}

	private void initFont(View base)
	{
		for(int i = 0 ; i < _DayTextList.size() ; i++)
		{
			_DayTextList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		}
		
		((TextView)base.findViewById(R.id.home_nihao_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_step_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_card_1_title_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_study_1_card_title_1)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_study_1_card_title_2)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		
		((TextView)base.findViewById(R.id.home_card_2_title_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_study_2_card_title_1)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_study_2_card_title_2)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_introduce_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_guide_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		((TextView)base.findViewById(R.id.home_autobiography_title_text)).setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText(View base)
	{
		((TextView)base.findViewById(R.id.home_nihao_text)).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.nihao_china));
		((TextView)base.findViewById(R.id.home_step_text)).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.step_china));


		((TextView)base.findViewById(R.id.home_introduce_text)).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_littlefox_chinese_introduce));
		((TextView)base.findViewById(R.id.home_guide_text)).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_study_guide));
		
		((TextView)base.findViewById(R.id.home_autobiography_title_text)).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_autobiography));
	}
	
	private void initAutobiographyInformation()
	{
		//TEST 
		
		if(Feature.IS_LANGUAGE_ENG)
		{
			_AutobiographyBaseLayout.setVisibility(View.GONE);
		}
		else
		{

			for(int i = 0 ; i < mHomeDataResult.getRecommendList().size(); i++)
			{
				_AutobiographyInformationTitleList.get(i).setText(mHomeDataResult.getRecommendList().get(i).title);
				_AutobiographyInformationSubtitleList.get(i).setText(mHomeDataResult.getRecommendList().get(i).subtitle);
				
				Glide.with(mContext)
						.load(mHomeDataResult.getRecommendList().get(i).image_url)
						.transition(withCrossFade())
						.into(_AutobiographyInformationImageList.get(i));
			}
		}
		
	}
	
	
	
	private void initFocusOnToday()
	{
		setFocusOnDay(getTodayIndex());
	}
	
	private int getTodayIndex()
	{
		Date todayDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todayDate);
		
		switch(calendar.get(Calendar.DAY_OF_WEEK))
		{
		case Calendar.MONDAY:
			return 0;
		case Calendar.TUESDAY:
			return 1;
		case Calendar.WEDNESDAY:
			return 2;
		case Calendar.THURSDAY:
			return 3;
		case Calendar.FRIDAY:
		case Calendar.SATURDAY:
		case Calendar.SUNDAY:
			return 4;
		}
		
		return 0;
	}
	

	private void initFeatureSeries()
	{
		int ITEM_COUNT = 0;
		
		for(int i = 0 ; i < FEATURE_SECTION_SIZE; i++)
		{
			_FeatureTitleList.get(i).setText(mHomeDataResult.getFeatureList().get(i).title);
			
			for(int j = 0; j < FEATURE_SECTION_SIZE; j++)
			{
				Glide.with(mContext)
						.load(mHomeDataResult.getFeatureList().get(i).list.get(j).image_url)
						.transition(withCrossFade())
						.into(_FeatureImageList.get(ITEM_COUNT));

				_FeatureSubTextList.get(ITEM_COUNT).setText(mHomeDataResult.getFeatureList().get(i).list.get(j).cont_name);
				
				ITEM_COUNT++;
			}
		}
	}
	
	/**
	 * 메인화면 플레이 정보 아이템을 플레이 가능한 객체로 리턴
	 * @param featurePlayObject  메인화면 플레이 정보 아이템
	 * @return
	 */
	private ContentPlayObject getMainPlayObject(FeaturePlayObject featurePlayObject)
	{
		ContentPlayObject result = null;
		
		if(featurePlayObject.getContentType().equals(Common.PLAY_TYPE_FEATURE_SERIES_STORY))
		{
			result = new ContentPlayObject(Common.PLAY_TYPE_SERIES_STORY);
		}
		else if(featurePlayObject.getContentType().equals(Common.PLAY_TYPE_FEATURE_SHORT_STORY))
		{
			result = new ContentPlayObject(Common.PLAY_TYPE_SHORT_STORY);
		}
		else
		{
			result = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		}
		
		result.setSelectPosition(0);
		result.setRecommandItemEmpty();
		result.addPlayObject(featurePlayObject);
		return result;
	}
	
	private void insertBannerInformationData()
	{
		_BannerLinkView.setBannerInfomation(mHomeDataResult.getBannerList());
		_BannerLinkView.setOnBannerClickListener(mOnBannerClickListener);
	}
	
	/**
	 * 특정해상도 이하에서 Today Line 이 안보이는 문제를 해결하기 위해
	 */
	private void processVisibleTodayLine()
	{
		int marginLeft = 0;
		for(int i = 0 ;  i < _ExceptionTodayLineList.size(); i++)
		{
			if(i == 0)
			{
				marginLeft = TODAY_LINE_MARGIN_LEFT - 1;
			}
			else
			{
				marginLeft = TODAY_LINE_MARGIN_LEFT;
			}
		
			_BaseTodayLayout.moveChildView(_ExceptionTodayLineList.get(i),  marginLeft + i * TODAY_LINE_MARGIN_LEFT, TODAY_LINE_MARGIN_TOP, TODAY_LINE_EXCEPTION_WIDTH, TODAY_LINE_HEIGHT);		
		}
	}
	
	private void setFocusOnDay(final int position)
	{
		for(int i = 0 ; i < NEW_RELEASE_DAY_SIZE; i++)
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_DayTextList.get(i).setText(DAY_TEXT_ENG[i]);
			}
			else
			{
				_DayTextList.get(i).setText(DAY_TEXT_KOR[i]);
			}
			if( i == position)
			{
				Glide.with(mContext)
						.load(mHomeDataResult.getNewReleaseList().get(i).image_url)
						.transition(withCrossFade())
						.into(_TodayImageView);

				_DayTextList.get(i).setTextColor(mContext.getResources().getColor(R.color.color_d8232a));
				_DayFocusList.get(i).setVisibility(View.VISIBLE);
				_TodayImageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.f("Today Story Item : " + mHomeDataResult.getNewReleaseList().get(position).getContentId() + " Play ");
						GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																				Common.ANALYTICS_ACTION_TODAYSTORY, 
																				mHomeDataResult.getNewReleaseList().get(position).getContentId()+ Common.ANALYTICS_LABEL_PLAY);
						mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(mHomeDataResult.getNewReleaseList().get(position)));
					}
				});
			}
			else
			{
				_DayTextList.get(i).setTextColor(mContext.getResources().getColor(R.color.color_000000));
				_DayFocusList.get(i).setVisibility(View.GONE);
			}
		}
	}

	
	public void setHomeDataResult(HomeDataResult homeDataResult)
	{
		mHomeDataResult = homeDataResult;
		NEW_RELEASE_DAY_SIZE = mHomeDataResult.getNewReleaseList().size();
	}
	
	@OnClick({R.id.home_autobiography_title_text, R.id.home_button_autobiography, R.id.home_autobiography_image_1, R.id.home_autobiography_image_2, R.id.home_autobiography_image_3})
	public void selectClick(View view)
	{
		GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, Common.ANALYTICS_ACTION_AUTOBIOGRAPHY);
		switch(view.getId())
		{
		case R.id.home_autobiography_title_text:
		case R.id.home_button_autobiography:
			mOnMainSubTabsEventListener.onAutobiography(Common.WEBVIEW_AUTOBIOGRAPHY);
			break;
		case R.id.home_autobiography_image_1:
			mOnMainSubTabsEventListener.onAutobiography(mHomeDataResult.getRecommendList().get(0).target_url);
			break;
		case R.id.home_autobiography_image_2:
			mOnMainSubTabsEventListener.onAutobiography(mHomeDataResult.getRecommendList().get(1).target_url);
			break;
		case R.id.home_autobiography_image_3:
			mOnMainSubTabsEventListener.onAutobiography(mHomeDataResult.getRecommendList().get(2).target_url);
			break;
		}
	}


	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnMainSubTabsEventListener = onSubTabsEventListener;
	}
	
	private OnBannerClickListener mOnBannerClickListener = new OnBannerClickListener()
	{
		
		@Override
		public void OnBannerItemClick(String url)
		{
			mOnMainSubTabsEventListener.onBannerClick(url);
		}
	};


}

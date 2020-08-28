package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.AutobiographyObject;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.FeaturePlayObject;
import com.littlefox.chinese.edu.object.HomeContentPlayObject;
import com.littlefox.chinese.edu.object.result.HomeDataResult;
import com.littlefox.chinese.edu.view.BannerLinkView;
import com.littlefox.chinese.edu.view.BannerLinkView.OnBannerClickListener;
import com.littlefox.chinese.edu.view.GridRecyclerView;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubHomeFragmentTablet extends Fragment implements MainHolder
{
	@BindView(R.id.home_info_fragment_layout)
	ScalableLayout _HomeInfoBaseLayout;
	
	@BindView(R.id.home_content_list_fragment_layout)
	ScalableLayout _HomeContentListBaseLayout;
	
	@BindView(R.id.home_info_main_image)
	ImageView _TodayTitleImage;
	
	@Nullable
	@BindViews({R.id.home_info_today_list_image1, R.id.home_info_today_list_image2, R.id.home_info_today_list_image3, R.id.home_info_today_list_image4, R.id.home_info_today_list_image5})
	List<ImageView> _TodayThumbnailItemImageList;
	
	@BindViews({R.id.home_info_today_list_cover1, R.id.home_info_today_list_cover2, R.id.home_info_today_list_cover3, R.id.home_info_today_list_cover4, R.id.home_info_today_list_cover5})
	List<ImageView> _TodayThumbnailItemCoverList;
	
	@BindViews({R.id.home_info_today_list_text1, R.id.home_info_today_list_text2, R.id.home_info_today_list_text3, R.id.home_info_today_list_text4, R.id.home_info_today_list_text5})
	List<TextView> _TodayThumbnailItemTextList;
	
	@BindViews({R.id.home_info_nihao_china, R.id.home_info_step_china, R.id.home_info_introduce_littlefox, R.id.home_info_guide_study})
	List<TextView> _TodayItemTitleTextList;
	
	@BindView(R.id.home_content_story_card_list_view)
	GridRecyclerView _ContentListGridView;
	
	@BindView(R.id.home_banner_view)
	BannerLinkView _BannerLinkView;
	
	@BindView(R.id.home_info_step_china_image)
	ImageView _BasicStepButton;

	private ArrayList<HomeContentPlayObject> mContentPlayObjectResultList = new ArrayList<HomeContentPlayObject>();
	
	private static final int[] TEXT_TITLE_LIST = {R.array.nihao_china, R.array.step_china, R.array.title_littlefox_chinese_introduce, R.array.title_study_guide};
	
	private static final String[] DAY_TEXT_KOR = {"월","화", "수", "목", "금"};
	private static final String[] DAY_TEXT_ENG = {"MON","TUE", "WED", "THU", "FRI"};
	
	private static final String[] MONTH_TEXT_ENG = {"Jan", "Feb", "Mar", "Apr", "May" , "Jun", "Jul" , "Aug", "Sep", "Oct" , "Nov" , "Dec" };
	private static final String[] WEEK_TEXT_ENG = {"1st" , "2nd" , "3rd" , "4th","5th","6th"};
	
	
	public static int NEW_RELEASE_DAY_SIZE = 5;
	
	private static final int INDEX_MON_DAY 		= 0;
	private static final int INDEX_THES_DAY 		= 1;
	private static final int INDEX_WENDS_DAY 		= 2;
	private static final int INDEX_THURS_DAY 		= 3;
	private static final int INDEX_FRI_DAY 			= 4;
	
	public static MainSubHomeFragmentTablet getInstance()
	{
		return new MainSubHomeFragmentTablet();
	}
	
	private Context mContext;
	private static HomeDataResult sHomeDataResult;
	private OnMainSubTabsEventListener mOnMainSubTabsEventListener;
	private HomeCardViewAdapter mHomeCardViewAdapter;
	private GridLayoutManager mGridLayoutManager;

	
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
		View view;
		if(Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			view = inflater.inflate(R.layout.main_sub_home_fragment_not_support_tablet_display, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.main_sub_home_fragment_tablet, container, false);
		}
		
		ButterKnife.bind(this, view);
		initView();
		initFont();
		initFocusOnToday();
		insertBannerInformationData();
		initContentListItem();
		initRecycleView();
		return view;
	}
	
	private void initView()
	{
		if(Feature.IS_LANGUAGE_ENG)
		{
			_BasicStepButton.setImageResource(R.drawable.thumbnail_basics_en);
		}
		else
		{
			_BasicStepButton.setImageResource(R.drawable.thumbnail_basics);
		}
	}
	
	private void initFocusOnToday()
	{
		final int FEATURE_ITEM_SIZE = 4;
		for (int i = 0; i < FEATURE_ITEM_SIZE; i++)
		{
			_TodayItemTitleTextList.get(i).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(TEXT_TITLE_LIST[i]));
		}
		
		for (int i = 0; i < NEW_RELEASE_DAY_SIZE; i++)
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_TodayThumbnailItemTextList.get(i).setText(DAY_TEXT_ENG[i]);
			}
			else
			{
				_TodayThumbnailItemTextList.get(i).setText(DAY_TEXT_KOR[i]);
			}
			
			
			
			if (Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY == false)
			{
				Glide.with(mContext)
						.load(sHomeDataResult.getNewReleaseList().get(i).image_url)
						.transition(withCrossFade())
						.into(_TodayThumbnailItemImageList.get(i));
			}
		}
		
		setFocusOnDay(getTodayIndex());
	}

	private void initRecycleView()
	{	
		mHomeCardViewAdapter = new HomeCardViewAdapter(mContext);
		mGridLayoutManager = new GridLayoutManager(mContext, 2);
		mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
		{
			@Override
			public int getSpanSize(int position)
			{
				if(mContentPlayObjectResultList.get(position).getSectionTitle().equals("") == false 
						|| mContentPlayObjectResultList.get(position).getCurrentContentType() == HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY)
				{
					return 2;
				}
				else
				{
					return 1;
				}
			}
		});
		
		_ContentListGridView.setLayoutManager(mGridLayoutManager);
		_ContentListGridView.setHasFixedSize(true);
		_ContentListGridView.addItemDecoration(new GridSpacingItemDecoration(2, CommonUtils.getInstance(mContext).getPixel(20)));
		_ContentListGridView.setAdapter(mHomeCardViewAdapter);
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
	
	private void initFont()
	{
		for(int i = 0 ; i < _TodayThumbnailItemTextList.size(); i++)
		{
			_TodayThumbnailItemTextList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		}
		
		for(int i = 0 ; i < _TodayItemTitleTextList.size(); i++)
		{
			_TodayItemTitleTextList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		}
		
	}
	
	private void initContentListItem()
	{
		int thumbnailDecorationIndex = 0;
		final int FEATURE_ITEM_SIZE = 2;
		mContentPlayObjectResultList.clear();

		if(Feature.IS_LANGUAGE_ENG)
		{
			mContentPlayObjectResultList.add(new HomeContentPlayObject(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_best_story), HomeContentPlayObject.CONTENT_BEST_STORY));
			for(int i = 0; i < sHomeDataResult.getBestStoryList().size(); i++)
			{
				ContentPlayObject  contentPlayObject = getMainPlayObject(sHomeDataResult.getBestStoryList().get(i));
				mContentPlayObjectResultList.add(new HomeContentPlayObject(contentPlayObject, HomeContentPlayObject.CONTENT_BEST_STORY));
			}
			
			mContentPlayObjectResultList.add(new HomeContentPlayObject(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_best_song), HomeContentPlayObject.CONTENT_BEST_SONG));
			for(int i = 0; i < sHomeDataResult.getBestSongList().size(); i++)
			{
				ContentPlayObject  contentPlayObject = getMainPlayObject(sHomeDataResult.getBestSongList().get(i));
				mContentPlayObjectResultList.add(new HomeContentPlayObject(contentPlayObject, HomeContentPlayObject.CONTENT_BEST_SONG));
			}
		}
		else
		{	
			mContentPlayObjectResultList.add(new HomeContentPlayObject(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_autobiography), HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY));

			for(int i = 0; i < sHomeDataResult.getRecommendList().size(); i++)
			{
				AutobiographyObject autobiographyObject = new AutobiographyObject(
						sHomeDataResult.getRecommendList().get(i).title,
						sHomeDataResult.getRecommendList().get(i).subtitle,
						sHomeDataResult.getRecommendList().get(i).target_url,
						sHomeDataResult.getRecommendList().get(i).image_url);
				
				mContentPlayObjectResultList.add(new HomeContentPlayObject(autobiographyObject, HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY));
			}

		}
		

		for(int i = 0; i < sHomeDataResult.getFeatureList().size(); i++)
		{
			Log.i(" i  : " + i + ", title : "+ sHomeDataResult.getFeatureList().get(i).title);
			mContentPlayObjectResultList.add(new HomeContentPlayObject(sHomeDataResult.getFeatureList().get(i).title, HomeContentPlayObject.CONTENT_DEFAULT));
			for(int  j = 0; j < FEATURE_ITEM_SIZE ; j++)
			{
				ContentPlayObject  contentPlayObject = getMainPlayObject(sHomeDataResult.getFeatureList().get(i).list.get(j));
				mContentPlayObjectResultList.add(new HomeContentPlayObject(contentPlayObject, HomeContentPlayObject.CONTENT_DEFAULT));
			}
		}
		
		
		for(int i = 0; i < mContentPlayObjectResultList.size(); i++)
		{
			if(mContentPlayObjectResultList.get(i).getSectionTitle().equals("") == false)
			{
				thumbnailDecorationIndex = 0;
				mContentPlayObjectResultList.get(i).setThumbnailIndex(thumbnailDecorationIndex);
			}
			else
			{
				mContentPlayObjectResultList.get(i).setThumbnailIndex(thumbnailDecorationIndex);
				thumbnailDecorationIndex++;
			}
		}
	}
	
	/**
	 * 메인 플레이 정보 아이템을 플레이 가능한 객체로 리턴
	 * @param featurePlayObject 메인화면 플레이 정보 아이템
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
	
	private void setFocusOnDay(final int position)
	{
		
		for(int i = 0 ; i < NEW_RELEASE_DAY_SIZE; i++)
		{
			if( i == position)
			{
				Glide.with(mContext)
				.load(sHomeDataResult.getNewReleaseList().get(i).image_url)
				.transition(withCrossFade())
				.into(_TodayTitleImage);
				
				_TodayTitleImage.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.f("Today Story Item : " + sHomeDataResult.getNewReleaseList().get(position).getContentId() + " Play ");
						GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																				Common.ANALYTICS_ACTION_TODAYSTORY, 
																				sHomeDataResult.getNewReleaseList().get(position).getContentId()+ Common.ANALYTICS_LABEL_PLAY);
						mOnMainSubTabsEventListener.onPlayContent(getMainPlayObject(sHomeDataResult.getNewReleaseList().get(position)));
					}
				});
				
				_TodayThumbnailItemTextList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.color_d8232a));
				_TodayThumbnailItemCoverList.get(i).setVisibility(View.VISIBLE);
			}
			else
			{
				_TodayThumbnailItemTextList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.color_4d4d4d));
				_TodayThumbnailItemCoverList.get(i).setVisibility(View.GONE);
			}
		}
	}
	
	private String getAnalyticsActionType(HomeContentPlayObject homeContentPlayObject)
	{
		switch(homeContentPlayObject.getCurrentContentType())
		{
		case HomeContentPlayObject.CONTENT_DEFAULT:
			return Common.ANALYTICS_ACTION_CONTENTPLAY + " " +homeContentPlayObject.getContentPlayObject().getPlayObject(0).cont_name;
		case HomeContentPlayObject.CONTENT_BEST_STORY:
			return Common.ANALYTICS_ACTION_FAVORITE_STORY;
		case HomeContentPlayObject.CONTENT_BEST_SONG:
			return Common.ANALYTICS_ACTION_FAVORITE_SONG;
		}
		return "";
	}
	
	private void insertBannerInformationData()
	{
		_BannerLinkView.setBannerInfomation(sHomeDataResult.getBannerList());
		_BannerLinkView.setOnBannerClickListener(mOnBannerClickListener);
	}
	
	private String getCurrentWeekDay()
	{
		long DAYS_7 = 7 * 24 * 60 * 60 * 1000L;
		long lastWeekday = System.currentTimeMillis() - DAYS_7;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(lastWeekday);
		
		if(Feature.IS_LANGUAGE_ENG)
		{
			return WEEK_TEXT_ENG[calendar.get(Calendar.WEEK_OF_MONTH) - 1]  + " week of " + MONTH_TEXT_ENG[calendar.get(Calendar.MONTH)];
		}
		
		return (calendar.get(Calendar.MONTH)+1) + CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.date_month) + " " + calendar.get(Calendar.WEEK_OF_MONTH) + CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.date_week);
	}

	@Optional
	@OnClick({R.id.home_info_nihao_china_image, R.id.home_info_step_china_image, R.id.home_info_introduce_littlefox_image, R.id.home_info_guide_study_image,
		R.id.home_info_today_list_image1, R.id.home_info_today_list_image2, R.id.home_info_today_list_image3, R.id.home_info_today_list_image4, R.id.home_info_today_list_image5,
		R.id.home_info_today_list_text1, R.id.home_info_today_list_text2, R.id.home_info_today_list_text3, R.id.home_info_today_list_text4, R.id.home_info_today_list_text5})
	public void onSelectedOnDay(View view)
	{
		switch(view.getId())
		{
		case R.id.home_info_nihao_china_image:
			mOnMainSubTabsEventListener.onCreateChineseStep1Content();
			break;
		case R.id.home_info_step_china_image:
			mOnMainSubTabsEventListener.onMoveStudyDataStep1List();
			break;
		case R.id.home_info_introduce_littlefox_image:
			mOnMainSubTabsEventListener.onStartStepLittlefoxIntroduce();
			break;
		case R.id.home_info_guide_study_image:
			mOnMainSubTabsEventListener.onStartStepStudyGuide();
			break;
		case R.id.home_info_today_list_text1:
		case R.id.home_info_today_list_image1:
			Log.f("Select INDEX_MON_DAY");
			setFocusOnDay(INDEX_MON_DAY);
			break;
		case R.id.home_info_today_list_text2:
		case R.id.home_info_today_list_image2:
			Log.f("Select INDEX_THES_DAY");
			setFocusOnDay(INDEX_THES_DAY);
			break;
		case R.id.home_info_today_list_text3:
		case R.id.home_info_today_list_image3:
			Log.f("Select INDEX_WENDS_DAY");
			setFocusOnDay(INDEX_WENDS_DAY);
			break;
		case R.id.home_info_today_list_text4:
		case R.id.home_info_today_list_image4:
			Log.f("Select INDEX_THURS_DAY");
			setFocusOnDay(INDEX_THURS_DAY);
			break;
		case R.id.home_info_today_list_text5:
		case R.id.home_info_today_list_image5:
			Log.f("Select INDEX_FRI_DAY");
			setFocusOnDay(INDEX_FRI_DAY);
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

	
	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnMainSubTabsEventListener = onSubTabsEventListener;
	}
	
	public void setHomeDataResult(HomeDataResult homeDataResult)
	{
		sHomeDataResult = homeDataResult;
		NEW_RELEASE_DAY_SIZE = sHomeDataResult.getNewReleaseList().size();
	}
	
	public void notifyContentList()
	{
		Log.f("");
		initContentListItem();
		mHomeCardViewAdapter.notifyDataSetChanged();
	}
	
	public class HomeCardViewAdapter extends RecyclerView.Adapter<HomeCardViewAdapter.ViewHolder>
	{
		public static final int LAYOUT_SECTION 			= 0;
		public static final int LAYOUT_ITEM				= 1;
		public static final int LAYOUT_AUTOBIOGRAPHY 	= 2;
		
		private Context mContext;
		
		public HomeCardViewAdapter(Context context)
		{
			mContext = context;
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder
		{
			@BindView(R.id.base_item_layout)
			ScalableLayout _BaseItemLayout;

			@BindView(R.id.home_item_card_thumbnail)
			ImageView _ThumbnailImage;

			@BindView(R.id.home_item_card_recommand_title)
			TextView _RecommandTitleText;

			@BindView(R.id.home_item_section_week)
			TextView _WeekTitleText;

			@BindView(R.id.base_item_section_layout)
			LinearLayout _BaseSectionLayout;

			@BindView(R.id.home_item_section_line_layout)
			ScalableLayout _SectionDivideLineLayout;

			@BindView(R.id.home_item_section_title)
			TextView _SectionTitleText;

			@BindView(R.id.base_autobiography_layout)
			ScalableLayout _BaseAutobiographyLayout;

			@BindView(R.id.home_item_autobiography_thumbnail)
			ImageView _AutobiographyThumbnailImage;

			@BindView(R.id.home_item_autobiography_title_text)
			TextView _AutobiographyTitleText;

			@BindView(R.id.home_item_autobiography_name_text)
			TextView _AutobiographyNameText;

			@BindView(R.id.home_button_autobiography)
			ImageView _AutobiographyButton;
			
			public ViewHolder(View view)
			{
				super(view);
				ButterKnife.bind(this, view);
				initFont();
			}

			private void initFont()
			{
				_RecommandTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_SectionTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_WeekTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_AutobiographyTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_AutobiographyNameText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
		}

		@Override
		public int getItemCount()
		{
			return mContentPlayObjectResultList.size();
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position)
		{
			if(mContentPlayObjectResultList.get(position).getSectionTitle().equals("") == false)
			{
				visibleLayout(holder, LAYOUT_SECTION);
				
				
				if(position == 0)
					visibleSectionLine(holder, true);
				else
					visibleSectionLine(holder, false);
				
				visibleSectionTypeLayout(holder, position);
				
				holder._SectionTitleText.setText(mContentPlayObjectResultList.get(position).getSectionTitle());
				
				if(mContentPlayObjectResultList.get(position).getCurrentContentType() == HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY)
				{
					holder._BaseSectionLayout.setOnClickListener(new OnClickListener()
					{
						
						@Override
						public void onClick(View v)
						{
							GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, Common.ANALYTICS_ACTION_AUTOBIOGRAPHY);
							mOnMainSubTabsEventListener.onAutobiography(Common.WEBVIEW_AUTOBIOGRAPHY);
						}
					});
				}
			}
			else
			{
				if(mContentPlayObjectResultList.get(position).getCurrentContentType() == HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY)
				{
					visibleLayout(holder, LAYOUT_AUTOBIOGRAPHY);
				}
				else
				{
					visibleLayout(holder, LAYOUT_ITEM);
				}
				
				if(mContentPlayObjectResultList.get(position).getCurrentContentType() == HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY)
				{
					Glide.with(mContext)
					.load(mContentPlayObjectResultList.get(position).getAutobiographyObject().image_url)
					.transition(withCrossFade())
					.into(holder._AutobiographyThumbnailImage);	
					
					holder._AutobiographyTitleText.setText(mContentPlayObjectResultList.get(position).getAutobiographyObject().title);
					holder._AutobiographyNameText.setText(mContentPlayObjectResultList.get(position).getAutobiographyObject().subtitle);
					
					holder._AutobiographyThumbnailImage.setOnClickListener(new OnClickListener()
					{
						
						@Override
						public void onClick(View v)
						{
							GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, Common.ANALYTICS_ACTION_AUTOBIOGRAPHY);
							mOnMainSubTabsEventListener.onAutobiography(mContentPlayObjectResultList.get(position).getAutobiographyObject().target_url);
						}
					});
				}
				else
				{	
					Glide.with(mContext)
					.load(mContentPlayObjectResultList.get(position).getContentPlayObject().getPlayObjectList().get(0).image_url)
					.transition(withCrossFade())
					.into(holder._ThumbnailImage);	
					
					
					holder._RecommandTitleText.setText(mContentPlayObjectResultList.get(position).getContentPlayObject().getPlayObjectList().get(0).cont_name);
					
					holder._ThumbnailImage.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							Log.i("Main List Item : "+mContentPlayObjectResultList.get(position).getContentPlayObject().getPlayObjectList().get(0).getTitle() + " Content Play");
							GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																					getAnalyticsActionType(mContentPlayObjectResultList.get(position)), 
																					mContentPlayObjectResultList.get(position).getContentPlayObject().getPlayObjectList().get(0).getContentId() + Common.ANALYTICS_LABEL_PLAY);
							mOnMainSubTabsEventListener.onPlayContent(mContentPlayObjectResultList.get(position).getContentPlayObject());
						}
					});
				}

				
			}
			
			
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int viewType)
		{
			View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_main_gridlist_item_tablet, container, false);
			return new ViewHolder(view);
		}
		
		/**
		 * 특정 상황에는 헤더를 명시하고 아닐때는 아이템을 보여주기 위해
		 * @param holder ViewHolder
		 * @param type 섹션인지 아닌지 구분
		 */
		private void visibleLayout(ViewHolder holder,int type)
		{
			switch(type)
			{
			case LAYOUT_SECTION:
				holder._BaseSectionLayout.setVisibility(View.VISIBLE);
				holder._BaseItemLayout.setVisibility(View.GONE);
				holder._BaseAutobiographyLayout.setVisibility(View.GONE);
				break;
			case LAYOUT_ITEM:
				holder._BaseSectionLayout.setVisibility(View.GONE);
				holder._BaseItemLayout.setVisibility(View.VISIBLE);
				holder._BaseAutobiographyLayout.setVisibility(View.GONE);
				break;
			case LAYOUT_AUTOBIOGRAPHY:
				holder._BaseSectionLayout.setVisibility(View.GONE);
				holder._BaseItemLayout.setVisibility(View.GONE);
				holder._BaseAutobiographyLayout.setVisibility(View.VISIBLE);
				break;
			}
		}
		
		private void visibleSectionTypeLayout(ViewHolder holder, int position)
		{
			switch(mContentPlayObjectResultList.get(position).getCurrentContentType())
			{
			case HomeContentPlayObject.CONTENT_DEFAULT:
				holder._WeekTitleText.setVisibility(View.GONE);
				holder._AutobiographyButton.setVisibility(View.GONE);
				break;
			case HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY:
				holder._AutobiographyButton.setVisibility(View.VISIBLE);
				holder._WeekTitleText.setVisibility(View.GONE);
				break;
			case HomeContentPlayObject.CONTENT_BEST_STORY:
			case HomeContentPlayObject.CONTENT_BEST_SONG:
				holder._WeekTitleText.setVisibility(View.VISIBLE);
				holder._WeekTitleText.setText(getCurrentWeekDay());
				holder._AutobiographyButton.setVisibility(View.GONE);
				break;
			}
			if(mContentPlayObjectResultList.get(position).getCurrentContentType() != HomeContentPlayObject.CONTENT_DEFAULT)
			{
				
			}
			else
			{
				
			}
		}

		/**
		 * 헤더에 라인을 표시하는 지 안하는 지의 구분
		 * @param holder ViewHolder
		 * @param isSectionlineVisible TRUE : 라인을 표시 , FALSE : 라인을 표시안함
		 */
		private void visibleSectionLine(ViewHolder holder,boolean isSectionlineVisible)
		{
			if(isSectionlineVisible)
			{
				holder._SectionDivideLineLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._SectionDivideLineLayout.setVisibility(View.GONE);
			}
		}
	}

	
	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
	{
		private int spanCount;
		private int horizontalSpacing = 0;
		
		public GridSpacingItemDecoration(int spanCount, int spacing)
		{
			this.spanCount = spanCount;
			
			horizontalSpacing = spacing;
		}



		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
		{

			int position = parent.getChildAdapterPosition(view); // item position
			int column = 0;

	        if(position < 0)
	        {
	        	return;
	        }
	        
	        if(mContentPlayObjectResultList.get(position).getSectionTitle().equals("") == false
	        		|| mContentPlayObjectResultList.get(position).getCurrentContentType() == HomeContentPlayObject.CONTENT_AUTOBIOGRAPHY)
			{	
				column = 0;
			}
			else
			{
				column = (mContentPlayObjectResultList.get(position).getThumbnailIndex()) % spanCount;
				outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount;
				outRect.right = (column + 1) * horizontalSpacing / spanCount;
			}
			
		}

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

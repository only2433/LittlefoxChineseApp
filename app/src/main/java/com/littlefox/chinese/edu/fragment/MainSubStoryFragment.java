package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.fragment.MainSubStoryFragment.StoryCardViewAdapter.ViewHolder;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.StoryFavoriteItemObject;
import com.littlefox.chinese.edu.object.result.StoryCardResult;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubStoryFragment extends Fragment implements MainHolder
{
	@BindView(R.id.story_card_list_view)
	RecyclerView _StoryCardRecycleView;
	
	Handler mNotifyChangeHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what == MESSAGE_NOTIFY_CHAGE)
				mStoryCardViewAdapter.notifyDataSetChanged();
		}
		
	};
	
	private static final int COLUME_PHONE = 2;
	private static final int COLUME_TABLET = 5;
	private static final int MESSAGE_NOTIFY_CHAGE = 0;
	
	private Context mContext;
	private OnMainSubTabsEventListener mOnSubTabsEventListener;
	private StoryCardViewAdapter mStoryCardViewAdapter;
	private GridLayoutManager mGridLayoutManager;
	private ArrayList<StoryCardResult> mFavoriteStoryCardResultList = new ArrayList<StoryCardResult>();
	private ArrayList<StoryCardResult> mItemStoryCardResultList = new ArrayList<StoryCardResult>();
	
	public static MainSubStoryFragment getInstance()
	{
		return new MainSubStoryFragment();
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
	{
		Log.f("");
		View view = inflater.inflate(R.layout.main_sub_story_fragment, container, false);
		ButterKnife.bind(this, view);
		initRecycleView();
		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, new StoryFavoriteItemObject(mFavoriteStoryCardResultList));
	}

	private void initRecycleView()
	{
		StoryFavoriteItemObject favoriteObject = (StoryFavoriteItemObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, StoryFavoriteItemObject.class);
		
		if(favoriteObject != null)
		{
			mFavoriteStoryCardResultList = favoriteObject.getFavoriteItemList();
		}

		if(mItemStoryCardResultList.size() <= 0)
		{
			return;
		}
		
		setInformationOfFavoriteItemByLanguage();
		initSetFavoriteCheck();
		
		if(mItemStoryCardResultList.get(0).getCurrentCardType() != StoryCardResult.TYPE_TITLE_STORY)
		{
			mItemStoryCardResultList.add(0, new StoryCardResult(StoryCardResult.TYPE_TITLE_STORY));
		}
		
		mStoryCardViewAdapter = new StoryCardViewAdapter(mContext);
		if(Feature.IS_TABLET)
		{
			mGridLayoutManager = new GridLayoutManager(mContext, 5);
		}
		else
		{
			mGridLayoutManager = new GridLayoutManager(mContext, 2);
		}
		
		mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
		{
			@Override
			public int getSpanSize(int position)
			{
				int itemType = position < mFavoriteStoryCardResultList.size() ? 
						mFavoriteStoryCardResultList.get(position).getCurrentCardType() : 
							mItemStoryCardResultList.get(position - mFavoriteStoryCardResultList.size()).getCurrentCardType();
				
				if(itemType == StoryCardResult.TYPE_LIST_DEFAULT)
				{
					return 1;
				}
				else
				{
					if(Feature.IS_TABLET)
					{
						return COLUME_TABLET;
					}
					else
					{
						return COLUME_PHONE;
					}
					
				}
			}
		});
		mStoryCardViewAdapter.setHasStableIds(true);
		_StoryCardRecycleView.setLayoutManager(mGridLayoutManager);
		_StoryCardRecycleView.setHasFixedSize(true);
		if(Feature.IS_TABLET)
		{
			_StoryCardRecycleView.addItemDecoration(new GridSpacingItemDecoration(COLUME_TABLET, CommonUtils.getInstance(mContext).getPixel(30)));
		}
		else
		{
			_StoryCardRecycleView.addItemDecoration(new GridSpacingItemDecoration(COLUME_PHONE));
		}
		
		_StoryCardRecycleView.setAdapter(mStoryCardViewAdapter);
	}
	
	/**
	 * Favoite List 정보를 ItemList에 세팅하여 화면에 갱신시키기 위한 메소드
	 */
	private void initSetFavoriteCheck()
	{
		try
		{
			for(int i = 0 ; i < mFavoriteStoryCardResultList.size() ; i++)
			{
				if(mFavoriteStoryCardResultList.get(i).getCurrentCardType() == StoryCardResult.TYPE_LIST_DEFAULT)
				{
					int favoriteIndex = 0;
					
					favoriteIndex = getPositionToItemList(mItemStoryCardResultList, mFavoriteStoryCardResultList.get(i).getStoryKeyId());
					Log.f("Story Favorite Title : "+mFavoriteStoryCardResultList.get(i).getTitle());
					mItemStoryCardResultList.get(favoriteIndex).setFavoriteItem(true);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 저장된 즐겨 찾기 아이템을 언어에 따라 텍스트를 변경하기 위해 사용
	 */
	private void setInformationOfFavoriteItemByLanguage()
	{
		String favoriteItemKeyId = "";
		String serverInformationItemKeyId = "";
		try
		{
			for (int i = 0; i < mFavoriteStoryCardResultList.size(); i++)
			{
				favoriteItemKeyId = mFavoriteStoryCardResultList.get(i).getStoryKeyId();
				Log.i("favoriteItemKeyId : " +favoriteItemKeyId);
				for (StoryCardResult item : mItemStoryCardResultList)
				{
					serverInformationItemKeyId = item.getStoryKeyId(); 
					
					Log.i("serverInformationItemKeyId : " +serverInformationItemKeyId);
					if(favoriteItemKeyId.equals(serverInformationItemKeyId))
					{
						Log.i("item.getTitle() : " +item.getTitle());
						mFavoriteStoryCardResultList.get(i).setTitle(item.getTitle());
						mFavoriteStoryCardResultList.get(i).setStoryCardImageUrl(item.getStoryCardImageUrl());
						mFavoriteStoryCardResultList.get(i).setTitleThumbnail(item.getTitleThumbnail());
						mFavoriteStoryCardResultList.get(i).setTitleTextImageUrl(item.getTitleTextImageUrl());
						break;
					}
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 아이템 리스트에서 즐겨찾기된 아이템을 찾아서 즐겨찾기리스트에서 삭제한다.
	 * @param position 아이템 리스트의 아이템 포지션
	 */
	private void deleteFavoriteItem(int position)
	{	
		int notifyPosition = -1;
		Log.i("postion : "+ position);
		if(position != -1)
		{
			//아이템이 한개 밖에 없을 경우 Section 까지 지우기 위해
			if(mFavoriteStoryCardResultList.size() <= 2)
			{
				notifyPosition = getPositionToItemList(mItemStoryCardResultList, mFavoriteStoryCardResultList.get(position).getStoryKeyId());
				mFavoriteStoryCardResultList.clear();
				mStoryCardViewAdapter.notifyItemRangeRemoved(0, 2);
				mNotifyChangeHandler.sendEmptyMessageDelayed(MESSAGE_NOTIFY_CHAGE, 500);
			}
			else
			{
				notifyPosition = mFavoriteStoryCardResultList.size() + getPositionToItemList(mItemStoryCardResultList, mFavoriteStoryCardResultList.get(position).getStoryKeyId()) -1;
				mFavoriteStoryCardResultList.remove(position);
				mStoryCardViewAdapter.notifyItemRemoved(position);
				mStoryCardViewAdapter.notifyItemMoved(position, position);
				mNotifyChangeHandler.sendEmptyMessageDelayed(MESSAGE_NOTIFY_CHAGE, 500);
			}
			CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, new StoryFavoriteItemObject(mFavoriteStoryCardResultList));
			Log.f("Delete : Favorite List Size : " + mFavoriteStoryCardResultList.size() + " , Story List Size : " + mItemStoryCardResultList.size());
		}
	}
	

	/**
	 *  즐겨찾기 리스트에 아이템을 추가하고 화면을 갱신한다.
	 * @param position 즐겨찾기한 아이템의 포지션
	 */
	private void insertFavoriteItem(int position)
	{
		GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_STORY, Common.ANALYTICS_ACTION_FAVORITE_ADD);
		if(mFavoriteStoryCardResultList.size() > 0)
		{
			//생성을 뒤부터가 아닌 앞부터 하게 생성. 바꿀 가능성도 존재
			mFavoriteStoryCardResultList.add(1,
					new StoryCardResult(mFavoriteStoryCardResultList.size(),mItemStoryCardResultList.get(position).sm_id,
							mItemStoryCardResultList.get(position).sm_id_en,
							mItemStoryCardResultList.get(position).getSeriesStatus(),
							mItemStoryCardResultList.get(position).getTitle(), 
							mItemStoryCardResultList.get(position).getStoryCardImageUrl(), 
							mItemStoryCardResultList.get(position).getCurrentLevel(),
							mItemStoryCardResultList.get(position).getTitleBackground(),
							mItemStoryCardResultList.get(position).getStatusBarBackground(),
							mItemStoryCardResultList.get(position).getTitleTextImageUrl(),
							mItemStoryCardResultList.get(position).getTitleThumbnail(),true));
			mStoryCardViewAdapter.notifyItemInserted(1);
			mStoryCardViewAdapter.notifyItemMoved(1,1);
			mNotifyChangeHandler.sendEmptyMessageDelayed(MESSAGE_NOTIFY_CHAGE, 500);
		}
		else
		{
			//아이템이 없을 경우 Section 까지 만들기 위해
			mFavoriteStoryCardResultList.add(new StoryCardResult(StoryCardResult.TYPE_TITLE_FAVORITE));
			mFavoriteStoryCardResultList.add(
					new StoryCardResult(1,mItemStoryCardResultList.get(position).sm_id,
							mItemStoryCardResultList.get(position).sm_id_en,
							mItemStoryCardResultList.get(position).getSeriesStatus(),
							mItemStoryCardResultList.get(position).getTitle(), 
							mItemStoryCardResultList.get(position).getStoryCardImageUrl(), 
							mItemStoryCardResultList.get(position).getCurrentLevel(),
							mItemStoryCardResultList.get(position).getTitleBackground(),
							mItemStoryCardResultList.get(position).getStatusBarBackground(),
							mItemStoryCardResultList.get(position).getTitleTextImageUrl(),
							mItemStoryCardResultList.get(position).getTitleThumbnail(),true));
			mStoryCardViewAdapter.notifyItemRangeInserted(0, 2);
		//	mStoryCardViewAdapter.notifyItemChanged(position +mFavoriteStoryCardResultList.size());
			mNotifyChangeHandler.sendEmptyMessageDelayed(MESSAGE_NOTIFY_CHAGE, 500);
		}
		
		Log.f("Insert : Favorite List Size : " + mFavoriteStoryCardResultList.size() + " , Story List Size : " + mItemStoryCardResultList.size());
		CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, new StoryFavoriteItemObject(mFavoriteStoryCardResultList));
		mOnSubTabsEventListener.onSetFavorite(Common.FAVORITE_TYPE_ADD, mItemStoryCardResultList.get(position).getTitle());
		
	}
	
	/**
	 * 리스트에 컨텐트 아이디로 해당 포지션을 찾는 메소드
	 * @param contentId 아이템 리스트에 있는 컨텐트 아이디
	 * @return 원하는 포지션
	 */
	private int getPositionToItemList(ArrayList<StoryCardResult> list , String contentId)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if( list.get(i).getStoryKeyId().equals(contentId))
			{
				return i;
			}
		}
		return -1;
	}
	
	public  class StoryCardViewAdapter extends RecyclerView.Adapter<StoryCardViewAdapter.ViewHolder>
	{
		private Context mContext;
		
		public StoryCardViewAdapter(Context context)
		{
			mContext = context;
		}

		public  class ViewHolder extends RecyclerView.ViewHolder
		{
			LinearLayout _SectionLayout;
			ScalableLayout _SectionLineLayout;
			ScalableLayout _ItemLayout;
			
			ImageView _ThumbnailImage;
			ImageView _FavoriteIcon;
			ImageView _FavoriteClickView;
			TextView _SectionTitle;
			TextView _LevelText;
			TextView _TitleText;
			
			
			public ViewHolder(View view)
			{
				super(view);
				_SectionLayout		= ButterKnife.findById(view, R.id.base_item_section_layout);
				_SectionLineLayout 	= ButterKnife.findById(view, R.id.base_item_section_line_layout);
				_ItemLayout			= ButterKnife.findById(view, R.id.base_item_layout);
				_ThumbnailImage 	= ButterKnife.findById(view, R.id.item_story_card_thumbnail);
				_FavoriteIcon			= ButterKnife.findById(view, R.id.item_story_card_favorite);
				_FavoriteClickView	= ButterKnife.findById(view, R.id.item_story_card_add_favorite);
				_TitleText				= ButterKnife.findById(view, R.id.item_story_card_title);
				_LevelText				= ButterKnife.findById(view, R.id.item_story_card_level);
				_SectionTitle			= ButterKnife.findById(view, R.id.item_section_title);
				initFont();
				initText();
			}
			
			private void initFont()
			{
				_TitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_LevelText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_SectionTitle.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
			
			private void initText()
			{
				_SectionTitle.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.story_favorite_title));
			}
			
		}
		
		@Override
		public int getItemCount()
		{
			return mFavoriteStoryCardResultList.size()+ mItemStoryCardResultList.size();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int viewType)
		{
			View view;
			if(Feature.IS_TABLET)
			{
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.story_main_gridlist_item_tablet, container, false);
			}	
			else
			{
				view = LayoutInflater.from(container.getContext()).inflate(R.layout.story_main_gridlist_item, container, false);
			}
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position)
		{
			
			if(position < mFavoriteStoryCardResultList.size())
			{
				if(mFavoriteStoryCardResultList.get(position).getCurrentCardType() == StoryCardResult.TYPE_TITLE_FAVORITE)
				{
					visibleLayout(holder, true);
					visibleSectionLine(holder, false);
					holder._SectionTitle.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.story_favorite_title));
				}
				else
				{
					visibleLayout(holder, false);
					if(Feature.IS_LANGUAGE_ENG)
					{
						holder._LevelText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level) + " " + mFavoriteStoryCardResultList.get(position).getCurrentLevel());
					}
					else
					{
						holder._LevelText.setText(mFavoriteStoryCardResultList.get(position).getCurrentLevel()+ " " +CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level));
					}
					
					holder._TitleText.setText(mFavoriteStoryCardResultList.get(position).getTitle());
					
					Glide.with(mContext)
					.load(mFavoriteStoryCardResultList.get(position).getStoryCardImageUrl())
					.transition(withCrossFade())
					.into(holder._ThumbnailImage);
					
					holder._FavoriteIcon.setImageResource(R.drawable.icon_bookmark_on);
					holder._FavoriteClickView.setOnClickListener(new OnClickListener()
					{
						
						@Override
						public void onClick(View v)
						{
							int itemPosition = getPositionToItemList(mItemStoryCardResultList, mFavoriteStoryCardResultList.get(position).getStoryKeyId());
							mItemStoryCardResultList.get(itemPosition).setFavoriteItem(false);
							deleteFavoriteItem(position);
							mOnSubTabsEventListener.onSetFavorite(Common.FAVORITE_TYPE_DELETE, mItemStoryCardResultList.get(itemPosition).getTitle());
						}
					});
				}
				
			}
			else
			{
				final int itemPosition = position - mFavoriteStoryCardResultList.size();
				if(mItemStoryCardResultList.get(itemPosition).getCurrentCardType() == StoryCardResult.TYPE_TITLE_STORY)
				{	
					visibleLayout(holder, true);
					
					if(mFavoriteStoryCardResultList.size() > 0)
					{
						visibleSectionLine(holder, true);
					}
					else
					{
						visibleSectionLine(holder, false);
					}
					holder._SectionTitle.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.story_all_title));
				}
				else
				{
					visibleLayout(holder, false);
					
					if(Feature.IS_LANGUAGE_ENG)
					{
						holder._LevelText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level) + " " +mItemStoryCardResultList.get(itemPosition).getCurrentLevel());
					}
					else
					{
						holder._LevelText.setText(mItemStoryCardResultList.get(itemPosition).getCurrentLevel()+ " " +CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level));
					}
					
					holder._TitleText.setText(mItemStoryCardResultList.get(itemPosition).getTitle());
					
					Glide.with(mContext)
					.load(mItemStoryCardResultList.get(itemPosition).getStoryCardImageUrl())
					.transition(withCrossFade())
					.into(holder._ThumbnailImage);
					
					if(mItemStoryCardResultList.get(itemPosition).isFavoriteItem())
					{
						holder._FavoriteIcon.setImageResource(R.drawable.icon_bookmark_on);
					}
					else
					{
						holder._FavoriteIcon.setImageResource(R.drawable.icon_bookmark);
					}
				}
				holder._FavoriteClickView.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						if(mItemStoryCardResultList.get(itemPosition).isFavoriteItem())
						{
							//아이템리스트에서 아이템을 클릭하여 즐겨찾기 아이템 삭제
							mItemStoryCardResultList.get(itemPosition).setFavoriteItem(false);
							int favoriteItemPosition = getPositionToItemList(mFavoriteStoryCardResultList, mItemStoryCardResultList.get(itemPosition).getStoryKeyId());
							deleteFavoriteItem(favoriteItemPosition);
							mOnSubTabsEventListener.onSetFavorite(Common.FAVORITE_TYPE_DELETE, mItemStoryCardResultList.get(itemPosition).getTitle());
						}
						else
						{
							mItemStoryCardResultList.get(itemPosition).setFavoriteItem(true);
							insertFavoriteItem(itemPosition);
						}
					}
				});
				
			
			}
			
			holder._ItemLayout.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if(position < mFavoriteStoryCardResultList.size())
					{
						Log.i("Favorite Story Item : "+mFavoriteStoryCardResultList.get(position).getTitle() + " Content List Open");
						mOnSubTabsEventListener.onCreteContentList(
								new ContentListTitleObject(mFavoriteStoryCardResultList.get(position).sm_id,
										mFavoriteStoryCardResultList.get(position).sm_id_en,
										mFavoriteStoryCardResultList.get(position).getSeriesStatus(),
										mFavoriteStoryCardResultList.get(position).getTitleBackground(),
										mFavoriteStoryCardResultList.get(position).getStatusBarBackground(),
										mFavoriteStoryCardResultList.get(position).getTitleTextImageUrl(), 
										mFavoriteStoryCardResultList.get(position).getTitleThumbnail()));
					}
					else
					{
						int requestPosition =  position - mFavoriteStoryCardResultList.size();
						Log.i("Story Item : "+mItemStoryCardResultList.get(requestPosition).getTitle() + " Content List Open");
						mOnSubTabsEventListener.onCreteContentList(
								new ContentListTitleObject(mItemStoryCardResultList.get(requestPosition).sm_id,
																mItemStoryCardResultList.get(requestPosition).sm_id_en,
																mItemStoryCardResultList.get(requestPosition).getSeriesStatus(),
																mItemStoryCardResultList.get(requestPosition).getTitleBackground(),
																mItemStoryCardResultList.get(requestPosition).getStatusBarBackground(),
																mItemStoryCardResultList.get(requestPosition).getTitleTextImageUrl(), 
																mItemStoryCardResultList.get(requestPosition).getTitleThumbnail()));
					}
				}
				
			});

			
		}
		
		
		
		
		@Override
		public long getItemId(int position)
		{
			StoryCardResult result;
			if(position < mFavoriteStoryCardResultList.size())
			{
				result  = mFavoriteStoryCardResultList.get(position);
				return (result.getStoryKeyId() + result.isFavoriteItem()).hashCode();
			}
			else
			{
				final int itemPosition = position - mFavoriteStoryCardResultList.size();
				result  = mItemStoryCardResultList.get(itemPosition);
				return (result.getStoryKeyId() + result.isFavoriteItem()).hashCode();
			}
		}

		/**
		 * 특정 상황에는 헤더를 명시하고 아닐때는 아이템을 보여주기 위해
		 * @param holder ViewHolder
		 * @param isSection 섹션인지 아닌지 구분
		 */
		private void visibleLayout(ViewHolder holder,boolean isSection)
		{
			if(isSection)
			{
				holder._SectionLayout.setVisibility(View.VISIBLE);
				holder._ItemLayout.setVisibility(View.GONE);
			}
			else
			{
				holder._SectionLayout.setVisibility(View.GONE);
				holder._ItemLayout.setVisibility(View.VISIBLE);
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
				holder._SectionLineLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._SectionLineLayout.setVisibility(View.GONE);
			}
		}
	}
	
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		Log.i("isVisibleToUser : "+isVisibleToUser);
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void setStoryCardResultList(ArrayList<StoryCardResult> list)
	{
		mItemStoryCardResultList = list;
	}

	
	/**
	 * GridView의 서로의 간격을 맞춰주고 Divider를 그리기 위해 사용
	 * @author 정재현
	 *
	 */
	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

	    private int spanCount;
	    private Paint mPaint;
	    private int spacing;
	    
	    public GridSpacingItemDecoration(int spanCount, int spacing) {
	    	this.spanCount = spanCount;
	        this.spacing = spacing; 
	    }
	    
	    public GridSpacingItemDecoration(int spanCount) {
	        this.spanCount = spanCount;
	        mPaint = new Paint();
	        mPaint.setColor(mContext.getResources().getColor(R.color.color_story_main_line_color));
	        mPaint.setAntiAlias(true);
	    }
	    
	    
		@Override
	    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
	        int position = parent.getChildAdapterPosition(view); // item position
	       ViewHolder holder =  (ViewHolder) parent.getChildViewHolder(view);
	        int column = 0;
	        
	        if(position < 0)
	        {
	        	return;
	        }
	        
	    	if(position < mFavoriteStoryCardResultList.size())
			{	
				if(mFavoriteStoryCardResultList.get(position).getCurrentCardType() == StoryCardResult.TYPE_TITLE_FAVORITE)
				{
					column = 0;
				}
				else
				{
					Log.i("position : "+position);
					 column = (position - 1) % spanCount;
					 
					 if(Feature.IS_TABLET)
					 {
						 setLocationGridItemTablet(outRect,column,position - 1);
					 }
					 else
					 {
						 setLocationGridItem(outRect,column);
					 }
					 
				}
			}
			else
			{
				
				int itemPosition = position - mFavoriteStoryCardResultList.size();
				
				if(mItemStoryCardResultList.get(itemPosition).getCurrentCardType() == StoryCardResult.TYPE_TITLE_STORY)
				{	
					column = 0;
				}
				else
				{
					column = (itemPosition -1)  % spanCount;
					 if(Feature.IS_TABLET)
					 {
						 setLocationGridItemTablet(outRect,column,itemPosition -1);
					 }
					 else
					 {
						 setLocationGridItem(outRect,column);
					 }
					
					
				}
			}
	    }
		
		private void setLocationGridItemTablet(Rect outRect, int column, int position)
		{
			outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = 0;
            }
            outRect.bottom = spacing; // item bottom
		}
	
		
		/**
		 * GridView 각각의 Item을 왼쪽 오른쪽 균형있게 표시하려고 사용
		 * @param outRect 그리는 Rect 영역
		 * @param column 1 : 왼쪽 , 0 : 오른쪽
		 */
		private void setLocationGridItem(Rect outRect, int column)
		{
			final int PADDING_20 = CommonUtils.getInstance(mContext).getPixel(20);
			final int PADDING_24 = CommonUtils.getInstance(mContext).getPixel(24);
			final int PADDING_26 = CommonUtils.getInstance(mContext).getPixel(26);

			if (column == 0) // 오른쪽
			{
				outRect.left = PADDING_24 / 2;
				outRect.bottom = PADDING_20;
				outRect.right = PADDING_26;
			}
			else if (column == 1) // 왼쪽
			{
				outRect.left = PADDING_26;
				outRect.bottom = PADDING_20;
				outRect.right = PADDING_24 / 2;
			}
		}
	}

	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnSubTabsEventListener = onSubTabsEventListener;
	}
	
}
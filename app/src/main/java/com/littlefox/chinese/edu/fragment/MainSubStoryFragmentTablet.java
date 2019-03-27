package com.littlefox.chinese.edu.fragment;

import android.content.Context;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.StoryFavoriteItemObject;
import com.littlefox.chinese.edu.object.result.StoryCardResult;
import com.littlefox.logmonitor.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubStoryFragmentTablet extends Fragment implements MainHolder
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
	
	private static final int MESSAGE_NOTIFY_CHAGE = 0;
	
	private Context mContext;
	private OnMainSubTabsEventListener mOnSubTabsEventListener;
	private StoryCardViewAdapter mStoryCardViewAdapter;
	private GridLayoutManager mGridLayoutManager;
	private ArrayList<StoryCardResult> mItemStoryCardResultList = new ArrayList<StoryCardResult>();
	private ArrayList<StoryCardResult> mFavoriteStoryCardResultList = new ArrayList<StoryCardResult>();
	
	public static MainSubStoryFragmentTablet getInstance()
	{
		return new MainSubStoryFragmentTablet();
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
		View view = inflater.inflate(R.layout.main_sub_story_fragment_tablet, container, false);
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
		mStoryCardViewAdapter = new StoryCardViewAdapter(mContext);
		mGridLayoutManager = new GridLayoutManager(mContext, 5);
		_StoryCardRecycleView.setLayoutManager(mGridLayoutManager);
		_StoryCardRecycleView.setHasFixedSize(true);
		_StoryCardRecycleView.addItemDecoration(new GridSpacingItemDecoration(5, CommonUtils.getInstance(mContext).getPixel(30), true));
		mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
		{
			@Override
			public int getSpanSize(int position)
			{
				return 1;
			}
		});
		_StoryCardRecycleView.setAdapter(mStoryCardViewAdapter);
	}
	
	
	/**
	 * 저장된 즐겨 찾기 아이템을 언어에 따라 텍스트를 변경하기 위해 사용
	 */
	private void setInformationOfFavoriteItemByLanguage()
	{
		String favoriteItemKeyId = "";
		String serverInformationItemKeyId = "";
		for (int i = 0; i < mFavoriteStoryCardResultList.size(); i++)
		{
			favoriteItemKeyId = mFavoriteStoryCardResultList.get(i).getStoryKeyId();
			for (StoryCardResult item : mItemStoryCardResultList)
			{
				serverInformationItemKeyId = item.getStoryKeyId(); 
				
				if(favoriteItemKeyId.equals(serverInformationItemKeyId))
				{
					mFavoriteStoryCardResultList.get(i).setTitle(item.getTitle());
					mFavoriteStoryCardResultList.get(i).setStoryCardImageUrl(item.getStoryCardImageUrl());
					mFavoriteStoryCardResultList.get(i).setTitleThumbnail(item.getTitleThumbnail());
					mFavoriteStoryCardResultList.get(i).setTitleTextImageUrl(item.getTitleTextImageUrl());
					break;
				}
			}
		}

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
					int favoriteIndex = getPositionToItemList(mItemStoryCardResultList, mFavoriteStoryCardResultList.get(i).getStoryKeyId());
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
				mFavoriteStoryCardResultList.clear();
			}
			else
			{
				int removePosition = getPositionToItemList(mFavoriteStoryCardResultList, mItemStoryCardResultList.get(position).getStoryKeyId());
				mFavoriteStoryCardResultList.remove(removePosition);
			}
			mStoryCardViewAdapter.notifyItemChanged(position);
			CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, new StoryFavoriteItemObject(mFavoriteStoryCardResultList));
			mOnSubTabsEventListener.onSetFavorite(Common.FAVORITE_TYPE_DELETE, mItemStoryCardResultList.get(position).getTitle());
			Log.f("Delete : Favorite List Size : " + mFavoriteStoryCardResultList.size() + " , Story List Size : " + mItemStoryCardResultList.size());
		}
	}
	

	/**
	 *  즐겨찾기 리스트에 아이템을 추가하고 화면을 갱신한다.
	 * @param position 즐겨찾기한 아이템의 포지션
	 */
	private void insertFavoriteItem(int position)
	{
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
			
		}
		mStoryCardViewAdapter.notifyItemChanged(position);
		CommonUtils.getInstance(mContext).setPreferenceObject(Common.PARAMS_STORY_CARD_INFORMATION, new StoryFavoriteItemObject(mFavoriteStoryCardResultList));
		mOnSubTabsEventListener.onSetFavorite(Common.FAVORITE_TYPE_ADD, mItemStoryCardResultList.get(position).getTitle());
		Log.f("Insert : Favorite List Size : " + mFavoriteStoryCardResultList.size() + " , Story List Size : " + mItemStoryCardResultList.size());
		
	}
	
	public class StoryCardViewAdapter extends RecyclerView.Adapter<StoryCardViewAdapter.ViewHolder>
	{
		private Context mContext;
		
		public StoryCardViewAdapter(Context context)
		{
			mContext = context;
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder
		{
			ImageView _ThumbnailImage;
			ImageView _FavoriteIcon;
			ImageView _FavoriteClickView;
			TextView _LevelText;
			TextView _TitleText;
			
			public ViewHolder(View view)
			{
				super(view);
				_ThumbnailImage 	= ButterKnife.findById(view, R.id.item_story_card_thumbnail);
				_FavoriteIcon			= ButterKnife.findById(view, R.id.item_story_card_favorite);
				_FavoriteClickView	= ButterKnife.findById(view, R.id.item_story_card_add_favorite);
				_TitleText				= ButterKnife.findById(view, R.id.item_story_card_title);
				_LevelText				= ButterKnife.findById(view, R.id.item_story_card_level);
				_TitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_LevelText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
		}
		
		@Override
		public int getItemCount()
		{
			return mItemStoryCardResultList.size();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int viewType)
		{
			View view = LayoutInflater.from(container.getContext()).inflate(R.layout.story_main_gridlist_item_tablet, container, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position)
		{
			
			if(Feature.IS_LANGUAGE_ENG)
			{
				holder._LevelText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level) +" " + mItemStoryCardResultList.get(position).getCurrentLevel());
			}
			else
			{
				holder._LevelText.setText(mItemStoryCardResultList.get(position).getCurrentLevel()+" " +CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.text_level));
			}
			
			holder._TitleText.setText(mItemStoryCardResultList.get(position).getTitle());
			
			Glide.with(mContext)
			.load(mItemStoryCardResultList.get(position).getStoryCardImageUrl())
			.transition(withCrossFade())
			.into(holder._ThumbnailImage);
			
			
			if(mItemStoryCardResultList.get(position).isFavoriteItem())
			{
				holder._FavoriteIcon.setImageResource(R.drawable.icon_bookmark_on);
			}
			else
			{
				holder._FavoriteIcon.setImageResource(R.drawable.icon_bookmark);
			}
			
			holder._ThumbnailImage.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Log.i("Story Item : "+mItemStoryCardResultList.get(position).getTitle() + " Content List Open");
					mOnSubTabsEventListener.onCreteContentList(
							new ContentListTitleObject(mItemStoryCardResultList.get(position).sm_id,
															mItemStoryCardResultList.get(position).sm_id_en,
															mItemStoryCardResultList.get(position).getSeriesStatus(),
															mItemStoryCardResultList.get(position).getTitleBackground(),
															mItemStoryCardResultList.get(position).getStatusBarBackground(),
															mItemStoryCardResultList.get(position).getTitleTextImageUrl(), 
															mItemStoryCardResultList.get(position).getTitleThumbnail()));
				}
			});
			
			holder._FavoriteIcon.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if(mItemStoryCardResultList.get(position).isFavoriteItem())
					{
						mItemStoryCardResultList.get(position).setFavoriteItem(false);
						deleteFavoriteItem(position);
					}
					else
					{
						mItemStoryCardResultList.get(position).setFavoriteItem(true);
						insertFavoriteItem(position);
					}
				}
			});
		}
	}
	
	/**
	 * GridView의 서로의 간격을 맞춰주고 Divider를 그리기 위해 사용
	 * @author 정재현
	 *
	 */
	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {


    private int spanCount;
    private int spacing;
    private boolean includeEdge;
 
	    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
	    	this.spanCount = spanCount;
	        this.spacing = spacing;
	        this.includeEdge = includeEdge;
	    }
	    
	    
		@Override
	    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
	        int position = parent.getChildAdapterPosition(view); // item position
	        int column = position % spanCount; // item column

	        
	        if(position < 0)
	        {
	        	return;
	        }

	        

	        if (includeEdge) {
	            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
	            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

	            if (position < spanCount) { // top edge
	                outRect.top = spacing;
	            }
	            outRect.bottom = spacing; // item bottom
	        } else {
	            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
	            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
	            if (position >= spanCount) {
	                outRect.top = spacing; // item top
	            }
	        }
	    }
		
	}

	public void setStoryCardResultList(ArrayList<StoryCardResult> list)
	{
		mItemStoryCardResultList = list;
	}
	
	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnSubTabsEventListener = onSubTabsEventListener;
	}

}

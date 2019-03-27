package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.result.SongCardResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.chinese.edu.view.itemdecoration.LineDividerItemDecoration;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubSongFragment extends Fragment implements MainHolder
{
	@BindView(R.id.song_card_list_view)
	RecyclerView _SongCardRecyclerView;
	
	private Context mContext;
	private SongCardListAdapter mSongCardListAdapter;
	private ArrayList<SongCardResult> mSongCardResultList = new ArrayList<SongCardResult>();
	private OnMainSubTabsEventListener mOnSubTabsEventListener;	
	private int mCurrentChoiceLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	
	public static MainSubSongFragment getInstance()
	{
		return new MainSubSongFragment();
	}
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_sub_song_fragment, container,	 false);
		ButterKnife.bind(this, view);

		initRecyclerView();
		return view;
	}
	


	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}
	
	private void initRecyclerView()
	{
		mSongCardListAdapter = new SongCardListAdapter(mContext);
		_SongCardRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		_SongCardRecyclerView.addItemDecoration(new LineDividerItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f6f5f6), CommonUtils.getInstance(mContext).getHeightPixel(2)));
		_SongCardRecyclerView.setHasFixedSize(true);
		_SongCardRecyclerView.setAdapter(mSongCardListAdapter);
	}
	
	/**
	 * 플레이할 포지션의 아이템만 플레이하기 위한 정보를 리턴한다. -1일 경우, 전부 플레이 , position 을 정해질 경우 해당아이템만 플레이 
	 * @param position
	 * @return
	 */
	private ContentPlayObject getPlayContentToPosition(int position)
	{
		ArrayList<PlayObject> requestList = new ArrayList<PlayObject>();
		ContentPlayObject contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		contentPlayObject.setSelectPosition(position);
		
		for(int i =  0 ; i < mSongCardResultList.size(); i ++)
		{
			if(mSongCardResultList.get(i).getId() != -1)
			{
				requestList.add(mSongCardResultList.get(i));
			}
		}
		
		contentPlayObject.setPlayObjectList(new ArrayList<PlayObject>(requestList));
		return contentPlayObject;
	}
	
	public void initSelectedLayoutView()
	{
		for(int i = 0; i < mSongCardResultList.size(); i++)
		{
			mSongCardResultList.get(i).setSelected(false);
		}
		mCurrentChoiceLayoutType = Common.CHOICE_LAYOUT_TYPE_DEFAULT;
		mSongCardListAdapter.notifyDataSetChanged();
	}
	
	public void changeItemSelectView(int type)
	{
		mCurrentChoiceLayoutType = type;
		
		if(mCurrentChoiceLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
		{
			for(int i = 0; i < mSongCardResultList.size(); i++)
			{
				mSongCardResultList.get(i).setSelected(false);
			}
		}
		mSongCardListAdapter.notifyDataSetChanged();
	}
	
	public void startSongAllPlay()
	{
		mOnSubTabsEventListener.onPlayContent(getPlayContentAll());
	}
	
	public void startSongSelectPlay()
	{
		mOnSubTabsEventListener.onPlayContent(getSelectedPlayContent());
	}
	
	/**
	 * 모든 송리스트를 리턴
	 * @return
	 */
	private ContentPlayObject getPlayContentAll()
	{
		return getPlayContentToPosition(-1);
	}
	
	/**
	 * 사용자가 선택한 아이템이 있는 지 확인
	 * @return
	 */
	public boolean isSelectedPlayItem()
	{
		for(int i = 0; i < mSongCardResultList.size(); i++)
		{
			if(mSongCardResultList.get(i).isSelected())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 선택한 리스트를 리턴
	 * @return
	 */
	private  ContentPlayObject getSelectedPlayContent()
	{
		ContentPlayObject contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		for(int i = 0; i < mSongCardResultList.size(); i++)
		{
			if(mSongCardResultList.get(i).isSelected())
			{
				contentPlayObject.addPlayObject(mSongCardResultList.get(i));
			}
		}
		Log.i("contentPlayObject size : "+contentPlayObject.getPlayObjectList().size());
		return contentPlayObject;
	}

	public void setSongCardResultList(ArrayList<SongCardResult> songCardResultList)
	{
		mSongCardResultList = songCardResultList;
	}

	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnSubTabsEventListener = onSubTabsEventListener;
	}
		
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
				_SectionLayout 		= ButterKnife.findById(view, R.id.song_item_section_layout);
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
				if(position == 0)
				{
					_SectionLayout.setVisibility(View.VISIBLE);
					_ItemBaseLayout.setVisibility(View.VISIBLE);
					_FooterLayout.setVisibility(View.GONE);
				}
				else if(position == mSongCardResultList.size() -1)
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
			
			if((mCurrentChoiceLayoutType == Common.CHOICE_LAYOUT_TYPE_SELECTED && mSongCardResultList.get(position).getId() != -1)
					|| (Feature.IS_TABLET  && mSongCardResultList.get(position).getId() != -1))
			{
				holder._ItemCheckButton.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._ItemCheckButton.setVisibility(View.GONE);
			}
			
		
			
			holder._ItemTitleText.setText(mSongCardResultList.get(position).getTitle());
			holder._ItemSubTitleText.setText(mSongCardResultList.get(position).getTitleKO());
			
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
							mOnSubTabsEventListener.onSnackMessage(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_paid_user_available), mContext.getResources().getColor(R.color.color_d8232a));
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
					//원문보기
					mOnSubTabsEventListener.onStartStudyData(mSongCardResultList.get(position).fc_id);
				}
			});
		
			holder._ItemClickTerritory.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if(mSongCardResultList.get(position).getId() != -1)
					{
						
						
						if(mCurrentChoiceLayoutType == Common.CHOICE_LAYOUT_TYPE_DEFAULT)
						{
							//그냥플레이
							Log.f("Song SELECT position : "+position +" play");
							mOnSubTabsEventListener.onPlayContent(getPlayContentToPosition(position));
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

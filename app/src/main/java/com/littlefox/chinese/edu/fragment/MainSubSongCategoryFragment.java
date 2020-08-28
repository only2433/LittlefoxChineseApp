package com.littlefox.chinese.edu.fragment;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.result.SongCategoryResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.library.view.text.SeparateTextView;
import com.littlefox.logmonitor.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubSongCategoryFragment extends Fragment implements MainHolder
{
	@BindView(R.id.song_category_base_layout)
	LinearLayout _BaseLayout;
	
	@BindView(R.id.song_category_title)
	TextView _TitleText;
	
	@BindViews({R.id.song_category_image_main , R.id.song_category_image_sub1 , R.id.song_category_image_sub2})
	List<ImageView> _SongImageList;
	
	@BindViews({R.id.button_song_category_main_origin , R.id.button_song_category_sub1_origin, R.id.button_song_category_sub2_origin})
	List<ImageView> _SongOriginButtonList;
	
	@BindViews({R.id.song_category_text_main, R.id.song_category_text_sub1, R.id.song_category_text_sub2})
	List<SeparateTextView> _MainSongTextList;	
	
	
	private Context mContext;

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private OnMainSubTabsEventListener mOnSubTabsEventListener;	
	private static SongCategoryResult sSongCategoryResult = null;
	
	
	public static MainSubSongCategoryFragment getInstance()
	{
		return new MainSubSongCategoryFragment();
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
		View view;
		if(Feature.IS_TABLET)
		{
			view = inflater.inflate(R.layout.main_sub_song_category_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.main_sub_song_category_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);

		initCategoryInformation();
		return view;
	}
	

	
	private void initCategoryInformation()
	{
		_TitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_TitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.main_title_song_category));
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int i = 0; i < sSongCategoryResult.getSongMainList().size(); i++)
		{
			final int position = i;

			Glide.with(mContext)
					.load(sSongCategoryResult.getSongMainList().get(i).image_url)
					.transition(withCrossFade())
					.into(_SongImageList.get(i));

			_SongImageList.get(position).setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					mOnSubTabsEventListener.onPlayContent(getSongMainPlayObject(sSongCategoryResult.getSongMainList().get(position)));
				}
			});
			
			_SongOriginButtonList.get(position).setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					mOnSubTabsEventListener.onStartStudyData(sSongCategoryResult.getSongMainList().get(position).fc_id);
				}
			});
			
			_MainSongTextList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
			
			if(Feature.IS_TABLET)
			{
				try
				{
					_MainSongTextList.get(i).setSeparateText(sSongCategoryResult.getSongMainList().get(i).getDescription(), "\n"+ sSongCategoryResult.getSongMainList().get(i).getTitle())
					.setSeparateColor(mContext.getResources().getColor(R.color.color_635009), mContext.getResources().getColor(R.color.color_333333))
					.setSeparateTextSize(CommonUtils.getInstance(mContext).getPixel(28), CommonUtils.getInstance(mContext).getPixel(32))
					.showView();
				}catch(Exception e)
				{
					Log.f("Error : "+ e.getMessage());
				}
				
			}
			else
			{
				_MainSongTextList.get(i).setText(sSongCategoryResult.getSongMainList().get(i).getDescription());
			}
			
		}
		
		for(int i = 0; i < sSongCategoryResult.getSongCategoryList().size(); i++)
		{
			final int position = i;
			View categoryView;
			if(Feature.IS_TABLET)
			{
				categoryView = inflater.inflate(R.layout.add_category_sub_layout_tablet, null);
			}
			else
			{
				categoryView = inflater.inflate(R.layout.add_category_sub_layout, null);
			}
			
			SeparateTextView titleView = (SeparateTextView)categoryView.findViewById(R.id.add_song_category_title);
			titleView.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			if(Feature.IS_TABLET)
			{
				titleView.setSeparateText(sSongCategoryResult.getSongCategoryList().get(i).getTitle(), "      "+ sSongCategoryResult.getSongCategoryList().get(i).getDescription())
						.setSeparateColor(mContext.getResources().getColor(R.color.color_ffffff), mContext.getResources().getColor(R.color.color_ffffff))
						.setSeparateTextSize(CommonUtils.getInstance(mContext).getPixel(38), CommonUtils.getInstance(mContext).getPixel(28))
						.showView();
			}
			else
			{
				titleView.setText(sSongCategoryResult.getSongCategoryList().get(i).getTitle());
			}
			
			ImageView button = (ImageView)categoryView.findViewById(R.id.button_add_song_category);
			button.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					mOnSubTabsEventListener.onCreateSongList(sSongCategoryResult.getSongCategoryList().get(position));
				}
			});
			_BaseLayout.addView(categoryView);
		}
	}
	
	/**
	 * 송 메인화면 플레이 정보 아이템을 플레이 가능한 객체로 리턴
	 * @param playObject
	 * @return
	 */
	private ContentPlayObject getSongMainPlayObject(PlayObject playObject)
	{
		ContentPlayObject result = null;
		result = new ContentPlayObject(Common.PLAY_TYPE_SONG);
		result.setSelectPosition(0);
		result.setRecommandItemEmpty();
		result.addPlayObject(playObject);
		return result;
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
	
	public void setSongCategoryResultList(SongCategoryResult result)
	{
		sSongCategoryResult = result;
	}
	
	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnSubTabsEventListener = onSubTabsEventListener;
	}


}

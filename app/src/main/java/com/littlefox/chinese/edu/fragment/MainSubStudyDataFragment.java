package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.listener.MainHolder;
import com.littlefox.chinese.edu.listener.OnMainSubTabsEventListener;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.StudyDataInformation;
import com.littlefox.chinese.edu.object.result.StudyDataItemResult;
import com.littlefox.chinese.edu.object.result.StudyDataResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.chinese.edu.view.itemdecoration.LineDividerItemDecoration;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainSubStudyDataFragment extends Fragment implements MainHolder
{
	@BindView(R.id.study_data_card_list_view)
	RecyclerView _RecyclerView;
	
	@BindView(R.id.study_data_choice_button_layout)
	ScalableLayout _ChoiceButtonLayout;
	
	@BindViews({R.id.study_data_first_text, R.id.study_data_second_text, R.id.study_data_third_text})
	List<TextView> _StudyDataTextViewList;
	
	@BindViews({R.id.study_data_first_button, R.id.study_data_second_button, R.id.study_data_third_button})
	List<ImageView> _StudyDataButtonList;
	
	private static final int[] TEXT_STUDY_DATE_TITLE = { R.array.study_data_intonation, R.array.study_data_syllable_back, R.array.study_data_syllable_front};
	
	private static  int DEFAULT_LIST_ITEM_SIZE = -1;

	private Context mContext;
	private StudyDataResult mStudyDataResult;
	private StudyDataViewAdapter mStudyDataViewAdapter;
	private OnMainSubTabsEventListener mOnSubTabsEventListener;
	private int mCurrentStudyType = Common.STUDY_TYPE_INTONATION;
	
	public static MainSubStudyDataFragment getInstance()
	{
		return new MainSubStudyDataFragment();
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
		View view;
		if(Feature.IS_TABLET)
		{
			view = inflater.inflate(R.layout.main_sub_study_data_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.main_sub_study_data_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);

		initView();
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
	public void onDestroyView()
	{
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	private void initView()
	{
		if(Feature.IS_TABLET)
		{
			DEFAULT_LIST_ITEM_SIZE = CommonUtils.getInstance(mContext).getHeightPixel(138);
		}
		else
		{
			DEFAULT_LIST_ITEM_SIZE = CommonUtils.getInstance(mContext).getHeightPixel(220);
		}
		
		initFont();
		initText();
		initRecyclerView();
		changeTabInformation(Common.STUDY_TYPE_INTONATION);
	}
	
	private void initFont()
	{
		for(int i = 0 ; i < _StudyDataTextViewList.size(); i++)
		{
			_StudyDataTextViewList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
		}
	}
	
	private void initText()
	{
		for(int i = 0 ; i < _StudyDataTextViewList.size(); i++)
		{
			_StudyDataTextViewList.get(i).setText(CommonUtils.getInstance(mContext).getLanguageTypeString(TEXT_STUDY_DATE_TITLE[i]));
		}
	}
	
	/**
	 * 성조 , 운모, 성모 버튼에 행동에 따라 리스트를 새로 만드는 메소드
	 * @param type 학습 타입
	 */
	private ArrayList<StudyDataInformation> makeStudyDataItemList(int type)
	{
		ArrayList<StudyDataInformation> resultList = new ArrayList<StudyDataInformation>();
		ArrayList<StudyDataItemResult> currentItemList = mStudyDataResult.getStudyDataList(type); 

		for(int i = 0; i < currentItemList.size(); i++)
		{
			resultList.add(new StudyDataInformation(currentItemList.get(i).title));
			for(PlayObject playObject : currentItemList.get(i).list)
			{
				resultList.add(new StudyDataInformation(playObject));
			}
		}
		
		return resultList;
	}
	/**
	 * 버튼 선택에 따라 버튼의 UI 를 변경해주며, 리스트를 새로 구성한다.
	 * @param studyType
	 */
	private void changeTabInformation(int studyType)
	{
		mCurrentStudyType = studyType;
		initFont();
		
		_StudyDataButtonList.get(Common.STUDY_TYPE_INTONATION).setBackgroundResource(R.drawable.material_tab_gray01);
		_StudyDataButtonList.get(Common.STUDY_TYPE_SYLLABLE_BACK).setBackgroundResource(R.drawable.material_tab_gray02);
		_StudyDataButtonList.get(Common.STUDY_TYPE_SYLLABLE_FRONT).setBackgroundResource(R.drawable.material_tab_gray03);
		
		for(int i = 0; i < _StudyDataButtonList.size(); i++)
		{
			if(i == studyType)
			{
				_StudyDataTextViewList.get(i).setTextColor(mContext.getResources().getColor(R.color.color_d8232a));
				_StudyDataTextViewList.get(i).setTypeface(Font.getInstance(mContext).getRobotoBold());
				if(i == Common.STUDY_TYPE_INTONATION)
				{
					_StudyDataButtonList.get(i).setBackgroundResource(R.drawable.material_tab_white01);
				}
				else if(i == Common.STUDY_TYPE_SYLLABLE_BACK)
				{
					_StudyDataButtonList.get(i).setBackgroundResource(R.drawable.material_tab_white02);
				}
				else if(i == Common.STUDY_TYPE_SYLLABLE_FRONT)
				{
					_StudyDataButtonList.get(i).setBackgroundResource(R.drawable.material_tab_white03);
				}
				
			}
			else
			{
				_StudyDataTextViewList.get(i).setTextColor(mContext.getResources().getColor(R.color.color_444444));
				_StudyDataTextViewList.get(i).setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}
		}

		
		initRecyclerView();
	}
	
	private void initRecyclerView()
	{
		mStudyDataViewAdapter = new StudyDataViewAdapter(mContext, makeStudyDataItemList(mCurrentStudyType));
		_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		_RecyclerView.addItemDecoration(new LineDividerItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f6f5f6), CommonUtils.getInstance(mContext).getHeightPixel(2),DEFAULT_LIST_ITEM_SIZE));
		_RecyclerView.setHasFixedSize(true);
		_RecyclerView.setAdapter(mStudyDataViewAdapter);
	}
	
	private ContentPlayObject getSelectedPlayObject(ArrayList<StudyDataInformation> list, int position)
	{
		int sectionCount = 0;
		ArrayList<PlayObject> requestList = new ArrayList<PlayObject>();
		
		Log.i("selectPosition : "+ position);
		for(int i = 0 ; i < list.size(); i++)
		{
			if(list.get(i).getCurrentType() != StudyDataInformation.TYPE_SECTION_TITLE)
			{
				requestList.add(list.get(i));
			}
			else
			{
				/**
				 * 현재 클릭한 포지션과 섹션
				 */
				if(i < position)
				{
					sectionCount++;
				}
				
			}
		}
		Log.i("sectionCount : "+ sectionCount);
		ContentPlayObject contentPlayObject = new ContentPlayObject(Common.PLAY_TYPE_STUDY_DATA);
		contentPlayObject.setSelectPosition(position - sectionCount);
		
		contentPlayObject.setPlayObjectList(new ArrayList<PlayObject>(requestList));
		return contentPlayObject;
	}
	
	@OnClick({R.id.study_data_first_button, R.id.study_data_second_button, R.id.study_data_third_button})
	public void OnChangeTab(View view)
	{
		switch(view.getId())
		{
		case R.id.study_data_first_button:
			Log.f("성조 리스트 탭");
			changeTabInformation(Common.STUDY_TYPE_INTONATION);
			break;
		case R.id.study_data_second_button:
			Log.f("운모 리스트 탭");
			changeTabInformation(Common.STUDY_TYPE_SYLLABLE_BACK);
			break;
		case R.id.study_data_third_button:
			Log.f("성모 리스트 탭");
			changeTabInformation(Common.STUDY_TYPE_SYLLABLE_FRONT);
			break;
		}
	}
	
	/**
	 * 강제로 Intonation Tab으로 리스트를 변경한다.
	 */
	public void settingIntonationTab()
	{
		Log.f("");
		changeTabInformation(Common.STUDY_TYPE_INTONATION);
	}
	
	public void setStudyDataResult(StudyDataResult studyDatResult)
	{
		mStudyDataResult = studyDatResult;
	}
	
	@Override
	public void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener)
	{
		mOnSubTabsEventListener = onSubTabsEventListener;
	}
	
	
	public class StudyDataViewAdapter extends RecyclerView.Adapter<StudyDataViewAdapter.ViewHolder>
	{
		private Context mContext;
		private ArrayList<StudyDataInformation> mStudyDataItemList;
		
		public StudyDataViewAdapter(Context context, ArrayList<StudyDataInformation> studyDataItemList)
		{
			mContext 							= context;
			mStudyDataItemList 					= studyDataItemList;
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder
		{
			@BindView(R.id.data_list_item_base_layout)
			ScalableLayout 	_BaseItemLayout;

			@BindView(R.id.data_list_item_thumbnail)
			ImageView 		_ItemThumbnailImage;

			@BindView(R.id.data_list_item_click_territory)
			ImageView 		_ItemClickTerritory;

			@BindView(R.id.data_list_item_title)
			TextView		_ItemTitleText;

			@BindView(R.id.data_quiz_button)
			ImageView 		_ItemQuizButton;

			@BindView(R.id.base_item_section_layout)
			LinearLayout 		_SectionLayout;

			@BindView(R.id.base_item_section_line_layout)
			ScalableLayout 		_SectionLineLayout;

			@BindView(R.id.item_section_title)
			TextView 			_SectionTitleText;

			public ViewHolder(View view)
			{
				super(view);
				ButterKnife.bind(this, view);
				initFont();
			}
			
			private void initFont()
			{
				_ItemTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
				_SectionTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			}

			
		}

		@Override
		public int getItemCount()
		{
			return mStudyDataItemList.size();
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup container, int position)
		{
			View view;
			if(Feature.IS_TABLET)
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.study_data_list_item_tablet, container , false);
			}
			else
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.study_data_list_item, container , false);
			}
			
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position)
		{
			if(mStudyDataItemList.get(position).getCurrentType() == StudyDataInformation.TYPE_SECTION_TITLE)
			{
				visibleLayout(holder, true);
				
				if(position == 0)
				{
					visibleSectionLine(holder, false);
				}
				else
				{
					visibleSectionLine(holder, true);
				}
				holder._SectionTitleText.setText(mStudyDataItemList.get(position).getSectionTitle());
			}
			else
			{
				visibleLayout(holder, false);

				Glide.with(mContext)
						.load(mStudyDataItemList.get(position).image_url)
						.transition(withCrossFade())
						.into(holder._ItemThumbnailImage);

				holder._ItemTitleText.setText(mStudyDataItemList.get(position).getTitle());
				holder._ItemQuizButton.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Log.f("Study Data Quiz : " +mStudyDataItemList.get(position).fc_id+" Start");
						mOnSubTabsEventListener.onStartQuiz(mStudyDataItemList.get(position).fc_id);
					}
				});
				
				holder._ItemClickTerritory.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Log.f("Study Data Item Position : "+position+" Play");
						mOnSubTabsEventListener.onPlayContent(getSelectedPlayObject(mStudyDataItemList, position));
					}
				});
			}
		}

		/**
		 * 특정 상황에는 헤더를 명시하고 아닐때는 아이템을 보여주기 위해
		 * @param holder ViewHolder
		 * @param isSection 섹션인지 아닌지 구분
		 */
		private void visibleLayout(ViewHolder holder, boolean isSection)
		{
			if(isSection)
			{
				holder._BaseItemLayout.setVisibility(View.GONE);
				holder._SectionLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				holder._BaseItemLayout.setVisibility(View.VISIBLE);
				holder._SectionLayout.setVisibility(View.GONE);
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

}


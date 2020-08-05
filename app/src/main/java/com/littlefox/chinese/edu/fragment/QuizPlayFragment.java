package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.QuizActivity.OnQuizCommunicateListener;
import com.littlefox.chinese.edu.QuizActivity.QuizCallback;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.QuizPictureInformation;
import com.littlefox.chinese.edu.object.QuizTextInformation;
import com.littlefox.chinese.edu.object.QuizUserInteractionObject;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizPlayFragment extends Fragment implements QuizCallback
{
	@BindView(R.id.question_index_text)
	TextView _QuestionIndexText;
	
	@BindView(R.id.question_title_text)
	TextView _QuestionTitleText;
	
	@BindView(R.id.question_image_layout)
	ScalableLayout _PictureQuestionTypeLayout;
	
	@BindView(R.id.question_text_layout)
	ScalableLayout _TextQuestionTypeLayout;
	
	@BindView(R.id.question_play_button)
	ImageView _PlaySoundButton;
	
	@BindView(R.id.image_index_first_image)
	ImageView _FirstPictureImage;
	
	@BindView(R.id.image_index_first_not_select_image)
	ImageView _FirstNotSelectImage;
	
	@BindView(R.id.image_index_second_image)
	ImageView _SecondPictureImage;
	
	@BindView(R.id.image_index_second_not_select_image)
	ImageView _SecondNotSelectImage;
	
	@BindView(R.id.question_next_button)
	ImageView _NextPlayButton;
	
    // Quiz 의 정답 위치를 나타내는 변수
	private static final int QUIZ_CORRECT_PICTURE_LEFT 	= 0;
	private static final int QUIZ_CORRECT_PICTURE_RIGHT 	= 1;
	
	private static final int QUIZ_CORRECT_TEXT_1			= 0;
	private static final int QUIZ_CORRECT_TEXT_2			= 1;
	private static final int QUIZ_CORRECT_TEXT_3			= 2;
	private static final int QUIZ_CORRECT_TEXT_4			= 3;
	
	private static final String PARAMS_TYPE = "type";
	private static final String PARAMS_OBJECT = "object";
	
	private static final String TEXT_TAG_BASE ="base";
	private static final String TEXT_TAG_CHECK ="check";
	
	private QuizPictureInformation mQuizPictureInformation;
	private QuizTextInformation mQuizTextInformation;
	private OnQuizCommunicateListener mOnQuizCommunicateListener;
	private String mCurrentQuestionType = "";
	private Context mContext;
	private boolean isQuestionEnd = false;
	
	public static QuizPlayFragment getInstance()
	{
		return new QuizPlayFragment();
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
		Log.i("");
		super.onCreate(savedInstanceState);
	}
	

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.quiz_play_fragment, container, false);
		ButterKnife.bind(this, view);
		init();
		return view;
	}

	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroyView()
	{
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	private void init()
	{
		initFont();

		
		if(mCurrentQuestionType.equals(Common.QUIZ_CODE_PICTURE))
		{
			_QuestionIndexText.setText(String.valueOf(mQuizPictureInformation.getQuizIndex()+"."));
			_QuestionTitleText.setText(mQuizPictureInformation.getTitle());
			_PictureQuestionTypeLayout.setVisibility(View.VISIBLE);
			_TextQuestionTypeLayout.setVisibility(View.GONE);
			setQuestionImage();
		}
		else
		{
			_QuestionIndexText.setText(String.valueOf(mQuizTextInformation.getQuizIndex()+"."));
			_QuestionTitleText.setText(mQuizTextInformation.getTitle());
			_PictureQuestionTypeLayout.setVisibility(View.GONE);
			_TextQuestionTypeLayout.setVisibility(View.VISIBLE);
			setQuestionText();
		}
		
		
		if(mCurrentQuestionType.equals(Common.QUIZ_CODE_TEXT))
		{
			_PlaySoundButton.setVisibility(View.GONE);
		}
		
		_NextPlayButton.setAlpha(0.3f);
	}
	
	private void initFont()
	{
		_QuestionIndexText.setTypeface(Font.getInstance(mContext).getRobotoBold());
		_QuestionTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void setQuestionImage()
	{
		mQuizPictureInformation.shuffle();
		_FirstPictureImage.setImageBitmap(mQuizPictureInformation.getImageInformationList().get(0).getImage());
		_SecondPictureImage.setImageBitmap(mQuizPictureInformation.getImageInformationList().get(1).getImage());
	}
	
	private void setQuestionText()
	{
		int[] indexResource = {R.drawable.icon_index_1, R.drawable.icon_index_2, R.drawable.icon_index_3, R.drawable.icon_index_4};
		final int MAX_EXAMPLE_COUNT = 4;
		int exampleMarginTop = 0;
		mQuizTextInformation.shuffle();
		
		if(mQuizTextInformation.getExampleList().size() < MAX_EXAMPLE_COUNT)
		{
			exampleMarginTop = 70;
		}
		
		
		for(int i = 0; i < mQuizTextInformation.getExampleList().size() ; i++)
		{
			ScalableLayout exampleBaseLayout = new ScalableLayout(mContext, 1640, 104);
			exampleBaseLayout.setTag(i);
			//exampleBaseLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_f6f6f6));
			
			/*ImageView lineImage = new ImageView(mContext);
			lineImage.setBackgroundColor(mContext.getResources().getColor(R.color.color_e1eaf2));
			exampleBaseLayout.addView(lineImage, 0, 118, 1640, 2);*/
			
			ImageView checkImage = new ImageView(mContext);
			checkImage.setTag(TEXT_TAG_CHECK);
			checkImage.setImageResource(R.drawable.icon_no_check);
			exampleBaseLayout.addView(checkImage, 62, 19, 69, 63);
			
			ImageView indexImage = new ImageView(mContext);
			indexImage.setImageResource(indexResource[i]);
			exampleBaseLayout.addView(indexImage, 160, 22, 60, 60);
			
			TextView examText	= new TextView(mContext);
			examText.setText(mQuizTextInformation.getExampleList().get(i).getExampleText());
			examText.setTextColor(mContext.getResources().getColor(R.color.color_5e626d));
			examText.setGravity(Gravity.CENTER_VERTICAL);
			exampleBaseLayout.addView(examText, 240, 0, 1500, 104);
			exampleBaseLayout.setScale_TextSize(examText, 46);
			
			exampleBaseLayout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					if(isQuestionEnd == true)
					{
						return;
					}
					isQuestionEnd = true;
					
					for(int i = 0; i < ((ViewGroup)v).getChildCount(); i++)
					{
						try
						{
							if(((ViewGroup) v).getChildAt(i).getTag().equals(TEXT_TAG_CHECK))
							{
								ImageView view = (ImageView) ((ScalableLayout)v).getChildAt(i);
								view.setImageResource(R.drawable.icon_check);
							}
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					
					}
					
					int selectIndex = (int) v.getTag();
					
					Log.f("User TEXT Select Item : " + selectIndex);
					if(mQuizTextInformation.getExampleList().get(selectIndex).isAnswer())
					{
						sendUserSelectTextInformation(true, selectIndex);
					}
					else
					{
						sendUserSelectTextInformation(false, selectIndex);
					}
					_NextPlayButton.setAlpha(1.0f);
				}
				
			});
			
			
			_TextQuestionTypeLayout.addView(exampleBaseLayout, 140, exampleMarginTop+ i * 118 , 1640, 118);
		}
		
		
	}
	
	/**
	 * 선택하지 않은 이미지를 불투명 처리한다.
	 * @param type
	 */
	private void visibleNotSelectImage(int type)
	{
		if(type == QUIZ_CORRECT_PICTURE_LEFT)
		{
			_SecondNotSelectImage.setVisibility(View.GONE);
			_SecondPictureImage.setAlpha(0.3f);
		}
		else if(type == QUIZ_CORRECT_PICTURE_RIGHT)
		{
			_FirstNotSelectImage.setVisibility(View.GONE);
			_FirstPictureImage.setAlpha(0.3f);
		}
	}
	
	@OnClick({R.id.image_index_first_image, R.id.image_index_second_image , R.id.question_next_button, R.id.question_play_button})
	public void onSelectImage(View view)
	{
		switch(view.getId())
		{
		case R.id.image_index_first_image:
			
			if(isQuestionEnd == true)
			{
				return;
			}
			isQuestionEnd = true;
			Log.f("User IMAGE Select Item  LEFT ");
			if(mQuizPictureInformation.getImageInformationList().get(QUIZ_CORRECT_PICTURE_LEFT).isAnswer())
			{
				
				sendUserSelectPictureInformation(true);
			}
			else
			{
				sendUserSelectPictureInformation(false);
			}
			visibleNotSelectImage(QUIZ_CORRECT_PICTURE_LEFT);
			_NextPlayButton.setAlpha(1.0f);
			break;
			
		case R.id.image_index_second_image:
			
			if(isQuestionEnd == true)
			{
				return;
			}
			isQuestionEnd = true;
			Log.f("User IMAGE Select Item  RIGHT ");
			if(mQuizPictureInformation.getImageInformationList().get(QUIZ_CORRECT_PICTURE_RIGHT).isAnswer())
			{
				sendUserSelectPictureInformation(true);
			}
			else
			{
				sendUserSelectPictureInformation(false);
			}
			visibleNotSelectImage(QUIZ_CORRECT_PICTURE_RIGHT);
			_NextPlayButton.setAlpha(1.0f);
			break;
		
		case R.id.question_next_button:
			if(isQuestionEnd == false)
			{
				return;
			}
			
			mOnQuizCommunicateListener.onNext();
			break;
		case R.id.question_play_button:
			mOnQuizCommunicateListener.onPlayQuestionSound();
			break;
		}
	}
	
	/**
	 * 선택한 텍스트 문제 정보를 전달
	 * @param isCorrect 
	 * @param selectIndex
	 */
	private void sendUserSelectTextInformation(boolean isCorrect, int selectIndex)
	{
		if(isCorrect)
		{
			mOnQuizCommunicateListener.onChoiceItem(true,
					new QuizUserInteractionObject(mQuizTextInformation.getRecordQuizIndex(), 
							mQuizTextInformation.getRecordCorrectIndex(), 
							mQuizTextInformation.getExampleList().get(selectIndex).getExampleIndex()));
		}
		else
		{
			mOnQuizCommunicateListener.onChoiceItem(false,
					new QuizUserInteractionObject(mQuizTextInformation.getRecordQuizIndex(), 
							mQuizTextInformation.getRecordCorrectIndex(), 
							mQuizTextInformation.getExampleList().get(selectIndex).getExampleIndex()));
		}
	}
	
	/**
	 * 선택한 이미지 문제 정보를 전달
	 * @param isCorrect
	 */
	private void sendUserSelectPictureInformation(boolean isCorrect)
	{
		if(isCorrect)
		{
			mOnQuizCommunicateListener.onChoiceItem(true , 
					new QuizUserInteractionObject(mQuizPictureInformation.getRecordQuizCorrectIndex(), 
							mQuizPictureInformation.getRecordQuizCorrectIndex(), 
							mQuizPictureInformation.getRecordQuizCorrectIndex()));
		}
		else
		{
			mOnQuizCommunicateListener.onChoiceItem(false,
					new QuizUserInteractionObject(mQuizPictureInformation.getRecordQuizCorrectIndex(), 
							mQuizPictureInformation.getRecordQuizCorrectIndex(), 
							mQuizPictureInformation.getRecordQuizInCorrectIndex()));
		}
	}
	 
	

	public void setQuestionItemObject(String type, Object object)
	{
		
		mCurrentQuestionType = type;
		Log.i("mCurrentQuestionType : "+mCurrentQuestionType);
		switch(mCurrentQuestionType)
		{
		case Common.QUIZ_CODE_PICTURE:
			mQuizPictureInformation = (QuizPictureInformation) object;
			break;
		case Common.QUIZ_CODE_TEXT:
			mQuizTextInformation = (QuizTextInformation)object;
			break;
		}
	}
	
	
	@Override
	public void setOnQuizCommunicateListener(OnQuizCommunicateListener onQuizCommunicateListener)
	{
		mOnQuizCommunicateListener = onQuizCommunicateListener;
	}

}

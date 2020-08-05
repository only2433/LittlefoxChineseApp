package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.QuizActivity.OnQuizCommunicateListener;
import com.littlefox.chinese.edu.QuizActivity.QuizCallback;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.logmonitor.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizResultFragment extends Fragment implements QuizCallback
{
	@BindView(R.id.quiz_title_correct_image)
	ImageView _CorrectIconImage;
	
	@BindView(R.id.quiz_result_correct_text)
	TextView _CorrectCountText;
	
	@BindView(R.id.quiz_result_incorrect_text)
	TextView _InCorrectCountText;
	
	@BindView(R.id.quiz_result_image)
	ImageView _QuizResultImage;
	
	@BindView(R.id.quiz_title_correct_text)
	TextView _QuizCorrectTitleText;
	
	@BindView(R.id.quiz_title_incorrect_text)
	TextView _QuizInCorrectTitleText;
	
	private Context mContext;
	private static QuizResultFragment sQuizResultFragment = null;
	private OnQuizCommunicateListener mOnQuizCommunicateListener;
	private int mQuizTotalCount = -1;
	private int mQuizCorrectCount = -1;
	
	public static QuizResultFragment getInstance()
	{
		return new QuizResultFragment();
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
		Log.i("");
		View view = inflater.inflate(R.layout.quiz_end_fragment, container, false);
		ButterKnife.bind(this, view);
		initFont();
		initResultView();
		return view;
	}
	
	
	
	@Override
	public void onStart()
	{
		Log.i("");
		super.onStart();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onResume()
	{
		Log.i("");
		super.onResume();
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

	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	private void initFont()
	{
		_CorrectCountText.setTypeface(Font.getInstance(mContext).getRobotoBold());
		_InCorrectCountText.setTypeface(Font.getInstance(mContext).getRobotoBold());
		_QuizCorrectTitleText.setTypeface(Font.getInstance(mContext).getRobotoBold());
		_QuizInCorrectTitleText.setTypeface(Font.getInstance(mContext).getRobotoBold());
	}
	
	private void initResultView()
	{
		if(Feature.IS_LANGUAGE_ENG)
		{
			_CorrectIconImage.setImageResource(R.drawable.icon_correct_en);
		}
		else
		{
			_CorrectIconImage.setImageResource(R.drawable.icon_correct);
		}
		_CorrectCountText.setText(String.valueOf(mQuizCorrectCount));
		_InCorrectCountText.setText(String.valueOf(mQuizTotalCount - mQuizCorrectCount));
		setResultTitleText(mQuizTotalCount, mQuizCorrectCount);
		Log.f("CorrectCount : "+ mQuizCorrectCount +" , InCorrectCount : "+ (mQuizTotalCount - mQuizCorrectCount));
	}
	
	@OnClick({R.id.quiz_save_button, R.id.quiz_replay_button})
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.quiz_save_button:
			mOnQuizCommunicateListener.onSaveStudyInformation();
			view.setEnabled(false);
			break;
		case R.id.quiz_replay_button:
			mOnQuizCommunicateListener.onReplay();
			view.setEnabled(false);
			break;
		}
	}
	
	public void setResultInformation(int quizCount, int correctCount)
	{
		mQuizTotalCount = quizCount;
		mQuizCorrectCount = correctCount;
		
		try
		{
			initResultView();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setResultTitleText(int quizCount, int correctCount)
	{
		switch(CommonUtils.getInstance(mContext).getMyGrade(quizCount, correctCount))
		{
		case Common.GRADE_EXCELLENT:
			_QuizResultImage.setImageResource(R.drawable.img_excellent);
			break;
		case Common.GRADE_VERYGOOD:
			_QuizResultImage.setImageResource(R.drawable.img_very_good);
			break;
		case Common.GRADE_GOODS:
			_QuizResultImage.setImageResource(R.drawable.img_good);
			break;
		case Common.GRADE_POOL:
			_QuizResultImage.setImageResource(R.drawable.img_try_again);
			break;
		}
	}
	
	@Override
	public void setOnQuizCommunicateListener(OnQuizCommunicateListener onQuizCommunicateListener)
	{
		mOnQuizCommunicateListener = onQuizCommunicateListener;
	}

}

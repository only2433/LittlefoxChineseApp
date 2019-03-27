package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.littlefox.chinese.edu.QuizActivity.OnQuizCommunicateListener;
import com.littlefox.chinese.edu.QuizActivity.QuizCallback;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.library.view.dialog.ProgressWheel;
import com.littlefox.library.view.text.SeparateTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizIntroFragment extends Fragment implements QuizCallback
{
	@BindView(R.id.quiz_main_title_text)
	SeparateTextView _MainTitleText;
	
	@BindView(R.id.quiz_intro_loading_layout)
	ProgressWheel _LoadingLayout;
	
	@BindView(R.id.quiz_intro_play_button)
	ImageView _QuizPlayButton;

	private Context mContext;
	private OnQuizCommunicateListener mOnQuizCommunicateListener;
	
	public static QuizIntroFragment getInstance()
	{
		return new QuizIntroFragment();
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
		View view = inflater.inflate(R.layout.quiz_intro_fragment, container, false);
		ButterKnife.bind(this, view);
		initFont();
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
	
	private void initFont()
	{
		_MainTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	public void setTitle(String title, String subtitle)
	{
		if(subtitle.equals(""))
		{
			_MainTitleText.setText(title);
		}
		else
		{
			_MainTitleText.setSeparateText(title, "\n" + subtitle)
			.setSeparateColor(getResources().getColor(R.color.color_333333), getResources().getColor(R.color.color_333333))
			.setSeparateTextSize(CommonUtils.getInstance(mContext).getPixel(72), CommonUtils.getInstance(mContext).getPixel(56))
			.showView();
		}
			
			
	}
	public void loadingComplete()
	{
		_LoadingLayout.setVisibility(View.GONE);
		_QuizPlayButton.setVisibility(View.VISIBLE);
	}
	
	@OnClick(R.id.quiz_intro_play_button)
	public void onSelectClick(View view)
	{
		mOnQuizCommunicateListener.onNext();
	}

	@Override
	public void setOnQuizCommunicateListener(OnQuizCommunicateListener onQuizCommunicateListener)
	{
		mOnQuizCommunicateListener = onQuizCommunicateListener;
	}
	
}

package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroduceContentFragment extends Fragment
{

	@BindView(R.id.introduce_title_text)
	TextView _MainTitleText;
	
	@BindView(R.id.introduce_message_text)
	TextView _MainMessageText;
	
	@BindView(R.id.introduce_image)
	ImageView _MainImage;
	
	private String mMainTitle;
	private String mMainMessage;
	private int mMainImageResource;
	
	private Context mContext;
	
	public static IntroduceContentFragment getInstance()
	{
		return new IntroduceContentFragment();
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
			view = inflater.inflate(R.layout.introduce_content_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.introduce_content, container, false);
		}
		
		ButterKnife.bind(this, view);
		initView();
		initFont();
		return view;
	}
	
	private void initView()
	{
		_MainTitleText.setText(mMainTitle);
		_MainMessageText.setText(mMainMessage);
		_MainImage.setImageResource(mMainImageResource);
	}
	
	private void initFont()
	{
		_MainTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_MainMessageText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
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
	
	public void setResource(String mainTitle, String mainMessage, int imageResource)
	{
		mMainTitle 				= mainTitle;
		mMainMessage 			= mainMessage;
		mMainImageResource = imageResource;
	}
}

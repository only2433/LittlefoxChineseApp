package com.littlefox.chinese.edu;


import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 원문보기 정보를 보여주는 액티비티
 * @author 정재현
 *
 */
public class OriginDataInformationActivity extends BaseActivity
{
	@BindView(R.id.top_menu_title)
	TextView _TitleText;
	
	@BindView(R.id.origin_data_close)
	ImageView _CloseButton;
	
	@BindView(R.id.origin_data_webview)
	WebView _StudyDataWebView;
	
	@BindView(R.id.origin_data_base)
	RelativeLayout _BaseLayout;
	
	@BindView(R.id.origin_data_progress_wheel_layout)
	ScalableLayout _ProgressWheelView;

	private static final int DURATION_ANIMATION = 300;
	
	private String mContentId = "";
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.study_data_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.study_data_main);
		}
		
		ButterKnife.bind(this);
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		_ProgressWheelView.setVisibility(View.VISIBLE);
		
		mContentId = getIntent().getStringExtra(Common.INTENT_STUDY_DATA_PARAMS);
		initWebView();
		
	}
	
	private void initWebView()
	{
		_StudyDataWebView.setWebViewClient(new StudyDataWebViewClient());
		_StudyDataWebView.getSettings().setJavaScriptEnabled(true);
		if(Feature.IS_LANGUAGE_ENG)
		{
			_StudyDataWebView.loadUrl(Common.WEBVIEW_STUDY_DATA_URI_EN+mContentId);
		}
		else
		{
			_StudyDataWebView.loadUrl(Common.WEBVIEW_STUDY_DATA_URI+mContentId);
		}
		
	}
	
	private void initTransition()
	{
		Slide transitionEnter = new Slide();
	    transitionEnter.setSlideEdge(Gravity.RIGHT);
	    transitionEnter.setInterpolator(new LinearOutSlowInInterpolator());
	    transitionEnter.setDuration(DURATION_ANIMATION);
	    transitionEnter.excludeTarget(android.R.id.statusBarBackground, true);
	    
	    Slide transitionExit = new Slide();
	    transitionExit.setSlideEdge(Gravity.RIGHT);
	    transitionExit.setDuration(DURATION_ANIMATION);
	    transitionExit.setInterpolator(new LinearOutSlowInInterpolator());
	    transitionExit.excludeTarget(android.R.id.statusBarBackground, true);
	    
		getWindow().setEnterTransition(transitionEnter);
		getWindow().setExitTransition(transitionExit);
		getWindow().setAllowEnterTransitionOverlap(true);
		getWindow().setAllowReturnTransitionOverlap(true);
		_BaseLayout.setTransitionGroup(true);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		
		super.onWindowFocusChanged(hasFocus);
	}



	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_ORIGIN_DATA);
		Log.f("");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.f("");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Log.f("");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.f("");
	}
	
	@Override
	public void finish()
	{
		super.finish();
	}
	
	@OnClick(R.id.origin_data_close)
	public void selectClick(View view)
	{
		super.onBackPressed();
	}
	
	class StudyDataWebViewClient extends WebViewClient
	{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url)
		{
			super.onPageFinished(view, url);
			Log.f("Page Loading Complete");
			_ProgressWheelView.setVisibility(View.GONE);
		}
		
	}
}

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
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepLittlefoxChineseIntroduceActivity extends BaseActivity
{
	@BindView(R.id.step_littlefox_title)
	TextView _TitleText;
	
	@BindView(R.id.step_littlefox_introduce_close)
	ImageView _CloseButton;
	
	@BindView(R.id.step_littlefox_introduce_webview)
	WebView _WebView;
	
	@BindView(R.id.step_littlefox_introduce_base)
	RelativeLayout _BaseLayout;
	
	@BindView(R.id.step_littlefox_introduce_progress_wheel_layout)
	ScalableLayout _ProgressWheelView;

	private static final int DURATION_ANIMATION = 300;

	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.step_littlefox_chinese_introduce_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.step_littlefox_chinese_introduce_main);
		}

		ButterKnife.bind(this);
		Log.f("");
		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		initFont();
		initText();
		_ProgressWheelView.setVisibility(View.VISIBLE);

		initWebView();
		
	}
	
	private void initWebView()
	{
		_WebView.setWebViewClient(new DataWebViewClient());
		_WebView.getSettings().setJavaScriptEnabled(true);
		
		if(Feature.IS_TABLET)
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_WebView.loadUrl(Common.WEBVIEW_LITTLEFOX_CHINESE_GUIDE_EN_TABLET);
			}
			else
			{
				_WebView.loadUrl(Common.WEBVIEW_LITTLEFOX_CHINESE_GUIDE_TABLET);
			}
		}
		else
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_WebView.loadUrl(Common.WEBVIEW_LITTLEFOX_CHINESE_GUIDE_EN);
			}
			else
			{
				_WebView.loadUrl(Common.WEBVIEW_LITTLEFOX_CHINESE_GUIDE);
			}
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
	
	private void initFont()
	{
		_TitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}
	
	private void initText()
	{
		_TitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_littlefox_chinese_introduce));
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
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_STEP_LITTLEFOX_INTRODUCE);
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

		if(MainSystemFactory.getInstance().getAnimationTransitionMode() == MainSystemFactory.ANIMATION_TRANSITION)
		{
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		}
		
	}
	
	@OnClick(R.id.step_littlefox_introduce_close)
	public void selectClick(View view)
	{
		super.onBackPressed();
	}
	
	class DataWebViewClient extends WebViewClient
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

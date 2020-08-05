package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
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

public class AutobiographyActivity extends BaseActivity
{
	@BindView(R.id.autobiography_base)
	RelativeLayout _BaseLayout;
	
	@BindView(R.id.autobiography_title)
	TextView _TitleText;
	
	@BindView(R.id.autobiography_webview)
	WebView _WebView;
	
	@BindView(R.id.autobiography_prev)
	ImageView _PrevButton;
	
	@BindView(R.id.autobiography_close)
	ImageView _CloseButton;
	
	@BindView(R.id.autobiography_progress_wheel_layout)
	ScalableLayout _ProgressWheelView;
	

	private static final int MESSAGE_ENTER_DETAIL_PAGE = 0;
	private static final int DURATION_ANIMATION = 300;
	private static final String BRIDGE_NAME = "WebViewResponseBridge";
	
	private String mWebViewUrl = "";
	
	
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.autobiography_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.autobiography_main);
		}
		
		ButterKnife.bind(this);
		Log.f("");

		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		initText();
		initFont();
		
		
		initWebView();
	}
	
    @Override
    public void onBackPressed()
    {
    	if(mWebViewUrl.equals(Common.WEBVIEW_AUTOBIOGRAPHY))
    	{
    		super.onBackPressed();
    	}
    	else
    	{
    		mWebViewUrl = Common.WEBVIEW_AUTOBIOGRAPHY;
    		_WebView.loadUrl(mWebViewUrl);
    	}
    }

	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_AUTOBIOGRAPHY);
		Log.f("");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
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
		_TitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_autobiography));
	}
	
	private void initWebView()
	{
		mWebViewUrl = getIntent().getStringExtra(Common.INTENT_AUTOBIOGRAPHY);
		_WebView.getSettings().setJavaScriptEnabled(true);
		_WebView.setWebViewClient(new DataWebViewClient());
		_WebView.loadUrl(mWebViewUrl);	
	}
	
	@OnClick({R.id.autobiography_prev, R.id.autobiography_close})
	public void selectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.autobiography_prev:
			mWebViewUrl = Common.WEBVIEW_AUTOBIOGRAPHY;
    		_WebView.loadUrl(mWebViewUrl);
			break;
		case R.id.autobiography_close:
			super.onBackPressed();
			break;
		}
	}
	
	class DataWebViewClient extends WebViewClient
	{	
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			_ProgressWheelView.setVisibility(View.VISIBLE);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url)
		{
			super.onPageFinished(view, url);
			Log.f("Page Loading Complete : "+url);
			mWebViewUrl = url;
			if(mWebViewUrl.contains(Common.WEBVIEW_AUTOBIOGRAPHY))
			{
				_PrevButton.setVisibility(View.INVISIBLE);
			}
			else
			{
				_PrevButton.setVisibility(View.VISIBLE);
			}
			_ProgressWheelView.setVisibility(View.GONE);
		}
		
	}
}

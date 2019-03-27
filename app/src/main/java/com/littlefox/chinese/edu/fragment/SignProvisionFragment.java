package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.UserSignActivity.OnSignListener;
import com.littlefox.chinese.edu.UserSignActivity.UserSignCallback;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignProvisionFragment extends Fragment implements UserSignCallback
{
	@BindView(R.id.sign_provision_webview_layout)
	ScalableLayout _WebViewBaseLayout;
	
	@BindView(R.id.sign_provision_webview)
	WebView _WebView;
	
	@BindView(R.id.sign_provision_next_button)
	TextView _NextButton;
	
	@BindView(R.id.sign_provision_base_layout)
	LinearLayout _BaseLayout;
	
	Handler mSignStatusHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_CHECK:
				if(mCheckResult.equals(RESULT_OK))
				{
					_NextButton.setBackgroundResource(R.drawable.selector_red_button);
				}
				else
				{
					_NextButton.setBackgroundResource(R.drawable.selector_gray_button);
				}
				break;
			}
		}
		
	};
	
	private static final int MESSAGE_CHECK = 0;

	private static final String RESULT_OK 	="true";
	private static final String RESULT_FAIL = "false";
	
	private static final String BRIDGE_NAME = "WebViewResponseBridge";

	private Context mContext;
	private OnSignListener mOnSignListener;
	private boolean isPageLoadComplete = false;
	private String mCheckResult = "";
	
	public static SignProvisionFragment getInstance()
	{
		return new SignProvisionFragment();
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
			view = inflater.inflate(R.layout.user_sign_provision_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.user_sign_provision_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);
		
		init();
		initFont();
		initText();
		return view;
	}
	
	private void init()
	{
		Log.i("CommonUtils.getInstance(mContext).getDisplayWidthPixel() : "+CommonUtils.getInstance(mContext).getDisplayWidthPixel());
		
		final int RADIO_SUPPORT_TABLET_WEBVIEW_SIZE = 1100;
		final int SMALL_WEB_VIEW_HEIGHT = 1380;
		
		if(Feature.IS_TABLET == true)
		{
			if(Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
			{
				_WebViewBaseLayout.setScaleHeight(RADIO_SUPPORT_TABLET_WEBVIEW_SIZE);
				_WebViewBaseLayout.moveChildView(_WebView, 0, 0, Common.TARGET_DISPLAY_TABLET_WIDTH , RADIO_SUPPORT_TABLET_WEBVIEW_SIZE);
			}
		}
		else
		{
			if(CommonUtils.getInstance(mContext).getDisplayWidthPixel() < CommonUtils.getInstance(mContext).getMinDisplayWidth() )
			{
				_WebViewBaseLayout.setScaleHeight(SMALL_WEB_VIEW_HEIGHT);
				_WebViewBaseLayout.moveChildView(_WebView, 0, 0, CommonUtils.getInstance(mContext).getMinDisplayWidth() , SMALL_WEB_VIEW_HEIGHT);
			}
		}
		
		
		isPageLoadComplete = false;
		_WebView.getSettings().setJavaScriptEnabled(true);
		_WebView.setWebViewClient(new SignWebViewClient());
		_WebView.addJavascriptInterface(new SignWebViewBridge(), BRIDGE_NAME);
		if(Feature.IS_LANGUAGE_ENG)
		{
			_WebView.loadUrl(Common.WEBVIEW_URL_USER_SIGN_MAIN_EN);
		}
		else
		{
			_WebView.loadUrl(Common.WEBVIEW_URL_USER_SIGN_MAIN);
		}
		
		_NextButton.setBackgroundResource(R.drawable.selector_gray_button);
		
	}
	
	private void initFont()
	{
		_NextButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_NextButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_next));
	}
	
	@OnClick(R.id.sign_provision_next_button)
	public void selectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.sign_provision_next_button:
			if(isPageLoadComplete == true)
			{
				if(mCheckResult.equals(RESULT_OK))
				{
					mOnSignListener.CheckSignProvision(true);
				}
				else
				{
					mOnSignListener.CheckSignProvision(false);
				}
			}
			break;
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
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
	
	class SignWebViewClient extends WebViewClient
	{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		

		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
		{
			super.onReceivedError(view, request, error);
		}



		@Override
		public void onPageFinished(WebView view, String url)
		{
			super.onPageFinished(view, url);
			Log.f("Page Loading Complete");
			isPageLoadComplete = true;
			mOnSignListener.onSignProvisionViewLoadComplete();
		}
		
	}

    class SignWebViewBridge
	{
		@JavascriptInterface
		public void setCheckAllow(String result)
		{
			Log.f("Result : "+result);
			
			mCheckResult = result;
			
			mSignStatusHandler.sendEmptyMessage(MESSAGE_CHECK);
		}
	}

	@Override
	public void setOnSignListener(OnSignListener onSignListener)
	{
		mOnSignListener = onSignListener;
	}

	@Override
	public void onUserSignCheckEnd()
	{
		// TODO Auto-generated method stub
		
	}

	
}

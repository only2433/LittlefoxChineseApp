package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.logmonitor.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkErrorActivity extends BaseActivity
{
	private static final int DURATION_ANIMATION = 300;
	
	@BindView(R.id.network_error_main_base_coordinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;
	
	@BindView(R.id.network_error_main_title_text)
	TextView _MainTitleText;
	
	@BindView(R.id.network_error_main_sub_text)
	TextView _SubTitleText;
	
	@BindView(R.id.network_error_main_button_text)
	TextView _RetryButtonText;
	
	@BindView(R.id.network_error_retry_button)
	ImageView _RetryButton;
	
	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		
		if(CommonUtils.getInstance(this).isTablet())
		{
			setContentView(R.layout.network_error_main_land);
		}
		else
		{
			int previousMode = getIntent().getIntExtra(Common.INTENT_PREVIOUS_MODE, 0);
			if(previousMode == MainSystemFactory.MODE_PLAYER || previousMode == MainSystemFactory.MODE_QUIZ)
			{
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				setContentView(R.layout.network_error_main_land);
			}
			else
			{
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				setContentView(R.layout.network_error_main);
			}
			
		}
		
		ButterKnife.bind(this);
		initText();
		initFont();
		initRetryButtonSelector();
		Log.f("");
	}
	
	/**
	 * 텍스트 색변경이 적용이 안되네  - -;; 그래서 터치이벤트에서 구현
	 */
	private void initRetryButtonSelector()
	{
		_RetryButton.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				Log.i("event Action : " + event.getAction());
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					_RetryButtonText.setTextColor(NetworkErrorActivity.this.getResources().getColor(R.color.color_ffffff));
					_RetryButton.setImageResource(R.drawable.btn_network_button_on);
					break;
				case MotionEvent.ACTION_OUTSIDE:
				case MotionEvent.ACTION_UP:
					_RetryButtonText.setTextColor(NetworkErrorActivity.this.getResources().getColor(R.color.color_e24c4d));
					_RetryButton.setImageResource(R.drawable.btn_network_button);
					break;
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_NETWORK_ERROR);
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
	public void onBackPressed() 
	{
		
	}
	
	private void initFont()
	{
		_MainTitleText.setTypeface(Font.getInstance(this).getRobotoBold());
		_SubTitleText.setTypeface(Font.getInstance(this).getRobotoBold());
		_RetryButtonText.setTypeface(Font.getInstance(this).getRobotoBold());
	}
	
	private void initText()
	{
		_MainTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_network_error_main));
		_SubTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.message_network_error_sub));
		_RetryButtonText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.button_renew));
	}
	
	@OnClick(R.id.network_error_retry_button)
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.network_error_retry_button:
			if(NetworkUtil.isConnectNetwork(NetworkErrorActivity.this))
			{
				Log.f("Network Success");
				setResult(RESULT_OK);
				finish();
				NetworkErrorActivity.this.overridePendingTransition(R.anim.not_animation, R.anim.abc_slide_out_top);	
			}
			else
			{
				Log.f("Network Fail");
			}
			break;
		}
	}
}

package com.littlefox.chinese.edu;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.receiver.NetworkConnectReceiver;
import com.littlefox.logmonitor.ExceptionCheckHandler;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
	private NetworkConnectReceiver mConnectReceiver;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionCheckHandler(this));
		MainSystemFactory.getInstance().setInit(this);
		mConnectReceiver = new NetworkConnectReceiver();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setInit(this);
		mConnectReceiver.register(this);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		mConnectReceiver.unregister(this);
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
		
		if(MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_INTRO_LOADING
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_INTRODUCE
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_MAIN
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_STORY_CONTENT
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_SONG_CONTENT_LIST
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_PLAYER
				&& MainSystemFactory.getInstance().getCurrentMode() != MainSystemFactory.MODE_QUIZ)
		{
			if(Build.VERSION.SDK_INT < Common.LOLLIPOP)
			{
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		}
		
		
	}
	
	
	
}

package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.CaptionTestResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

public class CaptionInformationAsync extends BaseAsync
{
	public CaptionInformationAsync(Context context, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_CAPTION_INFORMATION);
		setAsyncListener(asyncListener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if (isRunning == true)
		{
			return null;
		}
		CaptionTestResult result = null;
		synchronized (mSync)
		{
			isRunning = false;

			String response = NetworkUtil.requestServerPair(mContext, "http://api.player.smile9.littlefox.com/api_player/caption_test/C0006012", null, NetworkUtil.GET_METHOD);
			result = new Gson().fromJson(response, CaptionTestResult.class);
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

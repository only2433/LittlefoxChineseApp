package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.SeriesInfoResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import java.io.File;

/**
 * 해당 시리즈의 소개 정보를 가져오는 서버 통신 클래스
 * @author 정재현
 *
 */
public class SeriesIntroductionRequestAsync extends BaseAsync
{
	private String mSeriesId = "";
	public SeriesIntroductionRequestAsync(Context context, String seriesId, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_SERIES_INFO);
		setAsyncListener(asyncListener);
		mSeriesId = seriesId;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		SeriesInfoResult result = null;
		
		synchronized (mSync)
		{
			isRunning = false;
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_SERIES_INFO+ File.separator+mSeriesId, null, NetworkUtil.GET_METHOD);
				result = new Gson().fromJson(response, SeriesInfoResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_SERIES_INFO, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

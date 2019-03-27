package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.UserInformationResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 유저 정보를 호출하기 위한 AsyncTask
 * @author 정재현
 *
 */
public class UserInformationRequestAsync extends BaseAsync 
{

	public UserInformationRequestAsync(Context context, AsyncListener listener)
	{
		super(context, Common.ASYNC_CODE_USER_INFORMATION_REQUEST);
		setAsyncListener(listener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		
		UserInformationResult result = null;
		
		synchronized (mSync)
		{
			isRunning = false;
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_INFO, null, NetworkUtil.GET_METHOD);
				result = new Gson().fromJson(response, UserInformationResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_USER_INFORMATION_REQUEST, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

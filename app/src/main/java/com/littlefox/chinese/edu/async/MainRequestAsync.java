package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.MainInformationResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 메인 화면에 UX 초기 정보를 화면에 표시하기위해 호출하는 서버 통신 클래스
 * @author 정재현
 *
 */
public class MainRequestAsync extends BaseAsync
{

	public MainRequestAsync(Context context, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_MAIN_INFO_REQUEST);
		setAsyncListener(asyncListener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		
		if(isRunning == true)
		{
			return null;
		}
		MainInformationResult result = null;
		synchronized (mSync)
		{
			isRunning = false;
			String response = null;
			try
			{
				response = NetworkUtil.requestServerPair(mContext, Common.URI_MAIN, null, NetworkUtil.GET_METHOD);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_MAIN_INFO_REQUEST, CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_network_error));
			}
			
			
			try
			{
				result= new Gson().fromJson(response, MainInformationResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_MAIN_INFO_REQUEST, e.getMessage());
			}
			
		}
		
		return result;
	}


	@Override
	public void setData(Object... objects) {

	}
}

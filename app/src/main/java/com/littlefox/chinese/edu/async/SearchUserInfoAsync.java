package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.InfoSearchResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 해당 유저의 정보가 있는 지 체크하고 그에 대한 정보를 리턴 받는 서버 통신 클래스
 * @author 정재현
 *
 */
public class SearchUserInfoAsync extends BaseAsync
{
	private String mSearchInfo = "";
	public SearchUserInfoAsync(Context context, String code, String searchInfo, AsyncListener asyncListener)
	{
		super(context, code);
		setAsyncListener(asyncListener);
		mSearchInfo = searchInfo;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		InfoSearchResult result = null;

		synchronized (mSync)
		{
			isRunning = false;

			ContentValues list = new ContentValues();
			
			if(mCode == Common.ASYNC_CODE_SEARCH_ID)
			{
				list.put("email", mSearchInfo);
			}
			else if(mCode == Common.ASYNC_CODE_SEARCH_PASSWORD)
			{
				list.put("login_id", mSearchInfo);
			}
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_ID_PASSWORD_SEARCH, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, InfoSearchResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(mCode, e.getMessage());
			}
			
			
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

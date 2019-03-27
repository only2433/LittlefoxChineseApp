package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 비밀번호 정보를 유저의 이메일로 보내는 서버 통신 클래스
 * @author 정재현
 *
 */
public class SendEmailToSearchPasswordAsync extends BaseAsync
{
	String mEmail = "";
	public SendEmailToSearchPasswordAsync(Context context, String email, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD);
		setAsyncListener(asyncListener);
		mEmail = email;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		
		BaseResult result = null;
		synchronized (mSync)
		{
			isRunning = false;

			ContentValues list = new ContentValues();
			list.put("email", mEmail);
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_SEND_EMAIL_TO_CHANGE_PASSWORD, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD, e.getMessage());
			}

			
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

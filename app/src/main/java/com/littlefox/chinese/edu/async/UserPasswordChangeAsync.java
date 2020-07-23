package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

public class UserPasswordChangeAsync extends BaseAsync
{
	private String mNewPassword = "";
	public UserPasswordChangeAsync(Context context, String newPassword, AsyncListener listener)
	{
		super(context, Common.ASYNC_CODE_USER_PASSWORD_CHANGE);
		setAsyncListener(listener);
		mNewPassword = newPassword;
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

			try
			{
				ContentValues list = new ContentValues();
				list.put("login_pw", "");
				list.put("new_login_pw", mNewPassword);
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_PASSWORD_CHANGE, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_USER_PASSWORD_CHANGE, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

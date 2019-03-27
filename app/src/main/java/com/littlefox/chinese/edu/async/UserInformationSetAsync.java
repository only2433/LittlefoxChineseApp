package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import java.util.Locale;

public class UserInformationSetAsync extends BaseAsync 
{
	private UserBaseInformationObject mChangedUserInformationObject = null;
	public UserInformationSetAsync(Context context, UserBaseInformationObject changedUserInformationObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_USER_INFORMATION_SET);
		setAsyncListener(asyncListener);
		mChangedUserInformationObject = changedUserInformationObject;
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
			UserTotalInformationObject orininalUserInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
			isRunning = false;
			String response = null;

			ContentValues list = new ContentValues();
			list.put("locale", Locale.getDefault().toString());
			list.put("user_name", mChangedUserInformationObject.getName());
			list.put("user_nickname", mChangedUserInformationObject.getNickname());
			list.put("curr_user_nickname", orininalUserInformationObject.getBaseUserInformation().getNickname());
			list.put("user_birth", mChangedUserInformationObject.getBirthday());
			list.put("phone", mChangedUserInformationObject.getPhoneNumber());
			
			try
			{
				response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_INFO, list, NetworkUtil.PUT_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_USER_INFORMATION_SET, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

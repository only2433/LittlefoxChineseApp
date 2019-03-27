package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 추가 사용자 추가
 * @author 정재현
 *
 */
public class AddChildRequestAddAsync extends BaseAsync
{
	private UserBaseInformationObject mChildInformationObject = null;
	public AddChildRequestAddAsync(Context context, UserBaseInformationObject childInformationObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_ADD);
		mChildInformationObject = childInformationObject;
		setAsyncListener(asyncListener);
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
			list.put("user_name", mChildInformationObject.getName());
			list.put("user_nickname", mChildInformationObject.getNickname());
			list.put("user_birth", mChildInformationObject.getBirthday());
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_ADD_CHILD_ACCOUNT, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_ADD, e.getMessage());
			}
		}
		
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

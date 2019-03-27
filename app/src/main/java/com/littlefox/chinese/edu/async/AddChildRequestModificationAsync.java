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
 * 추가 사용자 정보 수정
 * @author 정재현
 *
 */
public class AddChildRequestModificationAsync extends BaseAsync
{

	/**
	 * 이전에 있었던 추가 사용자의 정보
	 */
	private UserBaseInformationObject mOriginChildInformationObject;
	
	/**
	 * 바뀔 추가 사용자의 정보
	 */
	private UserBaseInformationObject mModificationInformationObject;
	
	public AddChildRequestModificationAsync(Context context, UserBaseInformationObject originChildInformationObject,  UserBaseInformationObject modificationInformationObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_MODIFICATION);
		mOriginChildInformationObject = originChildInformationObject;
		mModificationInformationObject = modificationInformationObject;
		setAsyncListener(asyncListener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		
		BaseResult  result = null;
		
		synchronized (mSync)
		{
			isRunning = false;
			ContentValues list = new ContentValues();
			list.put("user_name", mModificationInformationObject.getName());
			list.put("user_nickname", mModificationInformationObject.getNickname());
			list.put("curr_user_name", mOriginChildInformationObject.getName());
			list.put("curr_user_nickname", mOriginChildInformationObject.getNickname());
			list.put("user_birth", mModificationInformationObject.getBirthday());
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_ADD_CHILD_ACCOUNT, list, NetworkUtil.PUT_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_MODIFICATION, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

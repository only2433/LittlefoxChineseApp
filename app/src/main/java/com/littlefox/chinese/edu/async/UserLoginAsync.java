package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.chinese.edu.object.result.UserLoginResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.common.CommonUtils;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;


/**
 *  사용자가 로그인 하기위해 사용하며, 해당 상황에 대한 정보를 리턴받는 행동을 하는 서버 통신 클래스
 * @author 정재현
 *
 */
public class UserLoginAsync extends BaseAsync
{
	private UserLoginObject mUserLoginObject = null;
	public UserLoginAsync(Context context, UserLoginObject userLoginObject, AsyncListener listener)
	{
		super(context, Common.ASYNC_CODE_USER_LOGIN);
		setAsyncListener(listener);
		mUserLoginObject = userLoginObject;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		UserLoginResult result = null;
		synchronized (mSync)
		{
			isRunning = false;
			try
			{
				ContentValues list = new ContentValues();
				list.put("login_id", mUserLoginObject.getUserID());
				list.put("login_pw", mUserLoginObject.getUserPassword());
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_LOGIN, list, NetworkUtil.POST_METHOD);
				
				result = new Gson().fromJson(response, UserLoginResult.class);
				
				
				if(result.getResult().equals(BaseResult.RESULT_OK))
				{
					CommonUtils.setSharedPreference(mContext, Common.PARAMS_ACCESS_TOKEN, result.getAccessToken());
					CommonUtils.setSharedPreference(mContext, Common.PARAMS_CLIENT_KEY, result.getClientKey());
					CommonUtils.setSharedPreference(mContext, Common.PARAMS_SERVER_KEY, result.getServerKey());
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_USER_LOGIN, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserSignObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

/**
 * 입력한 회원 정보를 가지고 회원가입을 위해 서버와 통신하는 클래스
 * @author 정재현
 *
 */
public class UserSignAsync extends BaseAsync
{
	private UserSignObject mUserSignObject;
	public UserSignAsync(Context context, UserSignObject usersignObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_USER_SIGN);
		setAsyncListener(asyncListener);
		mUserSignObject = usersignObject;
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
				list.put("email", mUserSignObject.getUserId());
				list.put("fu_nick", mUserSignObject.getNickname());
				list.put("login_pw", mUserSignObject.getPassword());
				list.put("birth_ymd", mUserSignObject.getBirthday());
				list.put("phone2", mUserSignObject.getPhoneNumber());
				list.put("fu_name", mUserSignObject.getName());

                String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_SIGN, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_USER_SIGN, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

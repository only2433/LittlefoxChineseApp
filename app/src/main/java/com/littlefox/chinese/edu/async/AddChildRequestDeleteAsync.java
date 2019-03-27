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

import java.io.File;
import java.net.URLEncoder;

/**
 * 추가 사용자 정보 삭제 
 * @author 정재현
 *
 */
public class AddChildRequestDeleteAsync extends BaseAsync
{

	private UserBaseInformationObject mChildInformationObject;
	public AddChildRequestDeleteAsync(Context context, UserBaseInformationObject userBaseInformationObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_DELETE);
		mChildInformationObject = userBaseInformationObject;
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
			try
			{
				/**
				 * HttpConnection 에서는 Delete가 Get 방식으로 지워지지않아서 Delete를 하지 못하여  HttpClient를 사용한다.
				 */
				ContentValues list = new ContentValues();
				list.put("user_name", mChildInformationObject.getName());
				list.put("user_nickname", mChildInformationObject.getNickname());
				list.put("user_birth", mChildInformationObject.getBirthday());
				String nickName = URLEncoder.encode(mChildInformationObject.getNickname(),"UTF-8");
				
				String response = NetworkUtil.requestServerPair(mContext, 
						Common.URI_ADD_CHILD_ACCOUNT+File.separator + nickName, 
						null, 
						NetworkUtil.DELETE_METHOD);
				
				
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_DELETE, e.getMessage());
			}
		}
		
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

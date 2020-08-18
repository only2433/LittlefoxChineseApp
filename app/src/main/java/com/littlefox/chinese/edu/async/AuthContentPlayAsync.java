package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.MainApplication;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.AuthContentResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

public class AuthContentPlayAsync extends BaseAsync
{
	private String mContentID = "";
	private int mContentType = -1;
	private String mUserType = "";
	public AuthContentPlayAsync(Context context, String userType , String contentID,  int contentType, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_AUTH_CONTENT_PLAY);
		setAsyncListener(asyncListener);
		mUserType = userType;
		mContentID = contentID;
		mContentType = contentType;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		AuthContentResult result = null;
		
		synchronized (mSync)
		{
			isRunning = false;
			ContentValues list = new ContentValues();
			list.put("client_key", (String) CommonUtils.getInstance(mContext).getSharedPreference(Common.PARAMS_CLIENT_KEY, Common.TYPE_PARAMS_STRING));
			list.put("server_key", (String) CommonUtils.getInstance(mContext).getSharedPreference(Common.PARAMS_SERVER_KEY, Common.TYPE_PARAMS_STRING));
			list.put("user_type",  mUserType);
			list.put("contents_type", String.valueOf(mContentType));
			list.put("contents_id", mContentID);
			list.put("display_resolution", MainApplication.sDisPlayMetrics.widthPixels +"*" +MainApplication.sDisPlayMetrics.heightPixels);
			list.put("network_type", NetworkUtil.getConnectivityStatus(mContext) == NetworkUtil.TYPE_WIFI ? "W" : "X");

			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_AUTH_CONTENT_PLAY, list, NetworkUtil.POST_METHOD, Common.API_VERSION_1_0_3);
				result = new Gson().fromJson(response, AuthContentResult.class);
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

package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.PlayerStudyRecordObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

public class PlayerSaveRecordAsync extends BaseAsync
{
	private PlayerStudyRecordObject mPlayerStudyRecordObject;
	public PlayerSaveRecordAsync(Context context, PlayerStudyRecordObject playerStudyRecordObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_PLAY_SAVE_RECORD);
		mPlayerStudyRecordObject = playerStudyRecordObject;
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
			list.put("record_type", String.valueOf(mPlayerStudyRecordObject.getRecordType()));
			list.put("play_type", String.valueOf(mPlayerStudyRecordObject.getPlayType()));
			list.put("content_id", String.valueOf(mPlayerStudyRecordObject.getContentId()));
			list.put("play_time_sec", String.valueOf(mPlayerStudyRecordObject.getPlayTime()));
			
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_PLAY_SAVE_RECORD, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_PLAY_SAVE_RECORD, e.getMessage());
			}

		}
		
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

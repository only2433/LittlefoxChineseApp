package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.SongContentListInformationResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import java.io.File;

public class SongCategoryContentListAsync extends BaseAsync
{
	private String mSmID = "";
	public SongCategoryContentListAsync(Context context, String smID, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST);
		mSmID = smID;
		setAsyncListener(asyncListener);
		
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		
		if(isRunning == true)
		{
			return null;
		}
		SongContentListInformationResult result = null;
		synchronized (mSync)
		{
			isRunning = false;
			String response = null;
			
			try
			{
				response = NetworkUtil.requestServerPair(mContext, Common.URI_SONG_CONTENT_LIST + File.separator + mSmID, null, NetworkUtil.GET_METHOD);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST, CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_network_error));
			}
			
			try
			{
				result = new Gson().fromJson(response, SongContentListInformationResult.class);	
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST, e.getMessage());
			}
			
			return result;
			
		}
	}

	@Override
	public void setData(Object... objects) {

	}
}

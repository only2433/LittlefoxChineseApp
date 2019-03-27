package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.ContentListResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import java.io.File;

/**
 * 해당 스토리의 관련된 리스트를 호출하는 서버 통신 클래스
 * @author 정재현
 *
 */
public class ContentListAsync extends BaseAsync
{
	private String storyKeyId = "";
	public ContentListAsync(Context context, String storyKeyId, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_CONTENT_LIST_REQUEST);
		setAsyncListener(asyncListener);
		this.storyKeyId = storyKeyId;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			
		}
		ContentListResult result = null;
		synchronized (mSync)
		{
			isRunning = false;
			String response = NetworkUtil.requestServerPair(mContext, Common.URI_CONTENT_LIST+File.separator+storyKeyId,null, NetworkUtil.GET_METHOD);
			try
			{
				result = new Gson().fromJson(response, ContentListResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_CONTENT_LIST_REQUEST, e.getMessage());
			}
			
		}
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

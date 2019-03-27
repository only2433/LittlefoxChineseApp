package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.QuizPictureResult;
import com.littlefox.chinese.edu.object.result.QuizTextResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 해당 컨텐트의 퀴즈 정보를 받아오기 위해 사용하는 서버 통신 클래스
 * @author 정재현
 *
 */
public class QuizInformationRequestAsync extends BaseAsync
{
	private String mContentId = "";
	public QuizInformationRequestAsync(Context context, String contentId, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_QUIZ_INFORMATION);
		mContentId = contentId;
		setAsyncListener(asyncListener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		synchronized (mSync)
		{
			isRunning = false;
			String response = NetworkUtil.requestServerPair(mContext, Common.URI_QUIZ + File.separator + mContentId, null, NetworkUtil.GET_METHOD);
			JSONObject jsonObject;
			
			try
			{
				jsonObject = new JSONObject(response);
				String quizType = jsonObject.getString("type");
				mAsyncListener.onRunningAdvanceInformation(Common.ASYNC_CODE_QUIZ_INFORMATION, quizType);
				switch(quizType)
				{
				case Common.QUIZ_CODE_PICTURE:
					QuizPictureResult resultPicture = new Gson().fromJson(response, QuizPictureResult.class);
					return resultPicture;
				case Common.QUIZ_CODE_TEXT:
					QuizTextResult resultText = new Gson().fromJson(response, QuizTextResult.class);
					return resultText;
				}
				
			}
			catch (JSONException e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_QUIZ_INFORMATION, e.getMessage());
				return CommonUtils.getInstance(mContext).getResult(false);
			}
			
			
		}
		return null;
	}

	@Override
	public void setData(Object... objects) {

	}
}

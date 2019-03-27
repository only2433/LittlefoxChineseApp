package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.QuizStudyRecordObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuizSaveRecordAsync extends BaseAsync
{
	private QuizStudyRecordObject mQuizRequestObject;
	public QuizSaveRecordAsync(Context context, QuizStudyRecordObject quizRequestObject, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_QUIZ_SAVE_RECORD);
		mQuizRequestObject = quizRequestObject;
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

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject;
			for(int i = 0 ; i < mQuizRequestObject.getQuizResultInformationList().size(); i++)
			{
				jsonObject = new JSONObject();
				try
				{
					jsonObject.put("question_no", String.valueOf(mQuizRequestObject.getQuizResultInformationList().get(i).getQuestionNumber()));
					jsonObject.put("chosen_no", String.valueOf(mQuizRequestObject.getQuizResultInformationList().get(i).getChosenNumber()));
					jsonObject.put("correct_no", String.valueOf(mQuizRequestObject.getQuizResultInformationList().get(i).getCorrectNumber()));
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonArray.put(jsonObject);
			}
			
			list.put("content_id", mQuizRequestObject.getContentId());
			list.put("result", jsonArray.toString());
			list.put("quiz_id", mQuizRequestObject.getQuizId());
			list.put("score", mQuizRequestObject.getCorrectCount());
			list.put("num_result", mQuizRequestObject.getQuizCount());
			try
			{
				String response = NetworkUtil.requestServerPair(mContext, Common.URI_QUIZ_SAVE_RECORD, list, NetworkUtil.POST_METHOD);
				result = new Gson().fromJson(response, BaseResult.class);
			}catch(Exception e)
			{
				e.printStackTrace();
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_QUIZ_SAVE_RECORD, e.getMessage());
			}
		}
		
		return result;
	}

	@Override
	public void setData(Object... objects) {

	}
}

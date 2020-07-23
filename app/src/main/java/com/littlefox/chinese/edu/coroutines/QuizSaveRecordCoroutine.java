package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.QuizStudyRecordObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuizSaveRecordCoroutine extends BaseCoroutine
{
    private QuizStudyRecordObject mQuizRequestObject;
    public QuizSaveRecordCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_QUIZ_SAVE_RECORD);
        setAsyncListener(asyncListener);
    }

    @NotNull
    @Override
    public Object doInBackground()
    {
        if(isRunning())
        {
            return null;
        }
        BaseResult result = null;

        synchronized (mSync)
        {
            setRunning(true);
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
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_QUIZ_SAVE_RECORD, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mQuizRequestObject = (QuizStudyRecordObject)objects[0];
    }
}

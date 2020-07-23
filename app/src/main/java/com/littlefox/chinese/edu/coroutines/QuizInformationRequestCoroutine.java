package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.QuizPictureResult;
import com.littlefox.chinese.edu.object.result.QuizTextResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class QuizInformationRequestCoroutine extends BaseCoroutine
{
    private String mContentId = "";
    public QuizInformationRequestCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_QUIZ_INFORMATION);
        setAsyncListener(listener);
    }

    @NotNull
    @Override
    public Object doInBackground()
    {
        if(isRunning())
        {
            return null;
        }
        synchronized (mSync)
        {
            setRunning(true);
            String response = NetworkUtil.requestServerPair(mContext, Common.URI_QUIZ + File.separator + mContentId, null, NetworkUtil.GET_METHOD);
            JSONObject jsonObject;

            try
            {
                jsonObject = new JSONObject(response);
                String quizType = jsonObject.getString("type");
                getAsyncListener().onRunningAdvanceInformation(Common.ASYNC_CODE_QUIZ_INFORMATION, quizType);
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
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_QUIZ_INFORMATION, e.getMessage());
                return CommonUtils.getInstance(mContext).getResult(false);
            }
        }
        return null;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mContentId = (String)objects[0];
    }
}

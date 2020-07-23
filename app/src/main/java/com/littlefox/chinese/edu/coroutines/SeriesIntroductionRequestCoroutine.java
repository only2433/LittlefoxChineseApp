package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.SeriesInfoResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SeriesIntroductionRequestCoroutine extends BaseCoroutine
{
    private String mSeriesId = "";
    public SeriesIntroductionRequestCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_SERIES_INFO);
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
        SeriesInfoResult result = null;

        synchronized (mSync)
        {
            setRunning(true);
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_SERIES_INFO+ File.separator+mSeriesId, null, NetworkUtil.GET_METHOD);
                result = new Gson().fromJson(response, SeriesInfoResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_SERIES_INFO, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mSeriesId = (String)objects[0];
    }
}

package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.CaptionTestResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class CaptionInformationCoroutine extends BaseCoroutine
{

    public CaptionInformationCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_CAPTION_INFORMATION);
        setAsyncListener(asyncListener);
    }

    @NotNull
    @Override
    public Object doInBackground()
    {
        if (isRunning())
        {
            return null;
        }
        CaptionTestResult result = null;
        synchronized (mSync)
        {
            setRunning(true);

            String response = NetworkUtil.requestServerPair(mContext, "http://api.player.smile9.littlefox.com/api_player/caption_test/C0006012", null, NetworkUtil.GET_METHOD);
            result = new Gson().fromJson(response, CaptionTestResult.class);
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects) {

    }
}

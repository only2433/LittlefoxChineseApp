package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.MainInformationResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;
import com.littlefox.logmonitor.Log;

import org.jetbrains.annotations.NotNull;

public class MainRequestCoroutine extends BaseCoroutine
{
    public MainRequestCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_MAIN_INFO_REQUEST);
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
        MainInformationResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            String response = null;
            try
            {
                response = NetworkUtil.requestServerPair(mContext, Common.URI_MAIN, null, NetworkUtil.GET_METHOD);
                Log.f("response : "+response);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_MAIN_INFO_REQUEST, CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_network_error));
            }

            try
            {
                result = new Gson().fromJson(response, MainInformationResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_MAIN_INFO_REQUEST, e.getMessage());
            }

        }

        return result;
    }

    @Override
    public void setData(@NotNull Object... objects) {

    }
}

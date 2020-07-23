package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.UserInformationResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class UserInformationRequestCoroutine extends BaseCoroutine
{
    public UserInformationRequestCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_USER_INFORMATION_REQUEST);
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

        UserInformationResult result = null;

        synchronized (mSync)
        {
            setRunning(true);
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_INFO, null, NetworkUtil.GET_METHOD);
                result = new Gson().fromJson(response, UserInformationResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_USER_INFORMATION_REQUEST, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects) {

    }
}

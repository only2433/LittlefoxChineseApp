package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class SendEmailToSearchPasswordCoroutine extends BaseCoroutine
{
    String mEmail = "";
    public SendEmailToSearchPasswordCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD);
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
            list.put("email", mEmail);
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_SEND_EMAIL_TO_CHANGE_PASSWORD, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mEmail = (String)objects[0];
    }
}

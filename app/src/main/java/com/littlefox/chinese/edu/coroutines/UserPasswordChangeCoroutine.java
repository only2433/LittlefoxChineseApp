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

public class UserPasswordChangeCoroutine extends BaseCoroutine
{
    private String mNewPassword = "";
    public UserPasswordChangeCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_USER_PASSWORD_CHANGE);
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
        BaseResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            try
            {
                ContentValues list = new ContentValues();
                list.put("login_pw", "");
                list.put("new_login_pw", mNewPassword);
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_PASSWORD_CHANGE, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_USER_PASSWORD_CHANGE, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mNewPassword = (String)objects[0];
    }
}

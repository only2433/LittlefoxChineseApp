package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.AuthContentResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class AuthContentPlayCoroutine extends BaseCoroutine
{
    private String mUserType = "";
    private String mContentID = "";
    private int mContentType = -1;
    public AuthContentPlayCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_AUTH_CONTENT_PLAY);
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
        AuthContentResult result = null;

        synchronized (mSync)
        {
            setRunning(true);
            ContentValues list = new ContentValues();
            list.put("client_key", (String) CommonUtils.getInstance(mContext).getSharedPreference(Common.PARAMS_CLIENT_KEY, Common.TYPE_PARAMS_STRING));
            list.put("server_key", (String) CommonUtils.getInstance(mContext).getSharedPreference(Common.PARAMS_SERVER_KEY, Common.TYPE_PARAMS_STRING));
            list.put("user_type",  mUserType);
            list.put("contents_type", String.valueOf(mContentType));
            list.put("contents_id", mContentID);
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_AUTH_CONTENT_PLAY, list, NetworkUtil.POST_METHOD, Common.API_VERSION_1_0_3);
                result = new Gson().fromJson(response, AuthContentResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_ADD, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mUserType = (String)objects[0];
        mContentID = (String)objects[1];
        mContentType = (int)objects[2];
    }
}

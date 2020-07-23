package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.ContentListResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ContentListCoroutine extends BaseCoroutine
{
    private String storyKeyId = "";
    public ContentListCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_CONTENT_LIST_REQUEST);
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
        ContentListResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            String response = NetworkUtil.requestServerPair(mContext, Common.URI_CONTENT_LIST+ File.separator+storyKeyId,null, NetworkUtil.GET_METHOD);
            try
            {
                result = new Gson().fromJson(response, ContentListResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_CONTENT_LIST_REQUEST, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        storyKeyId = (String)objects[0];
    }
}

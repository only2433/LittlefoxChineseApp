package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.InfoSearchResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class SearchUserInfoCoroutine extends BaseCoroutine
{
    private String mSearchInfo = "";
    public SearchUserInfoCoroutine(@NotNull Context context, @NotNull String code, AsyncListener asyncListener)
    {
        super(context, code);
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
        InfoSearchResult result = null;

        synchronized (mSync)
        {
            setRunning(true);

            ContentValues list = new ContentValues();

            if(mCode == Common.ASYNC_CODE_SEARCH_ID)
            {
                list.put("email", mSearchInfo);
            }
            else if(mCode == Common.ASYNC_CODE_SEARCH_PASSWORD)
            {
                list.put("login_id", mSearchInfo);
            }
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_ID_PASSWORD_SEARCH, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, InfoSearchResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(mCode, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mSearchInfo = (String)objects[0];
    }
}

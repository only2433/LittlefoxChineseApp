package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.SongContentListInformationResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SongCategoryContentListCoroutine extends BaseCoroutine
{
    private String mSmID = "";
    public SongCategoryContentListCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST);
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
        SongContentListInformationResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            String response = null;
            try
            {
                response = NetworkUtil.requestServerPair(mContext, Common.URI_SONG_CONTENT_LIST + File.separator + mSmID, null, NetworkUtil.GET_METHOD);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST, CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_network_error));
            }

            try
            {
                result = new Gson().fromJson(response, SongContentListInformationResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST, e.getMessage());
            }
            return result;
        }
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mSmID = (String)objects[0];
    }
}

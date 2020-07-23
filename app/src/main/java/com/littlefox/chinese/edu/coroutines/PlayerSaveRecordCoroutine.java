package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.PlayerStudyRecordObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class PlayerSaveRecordCoroutine extends BaseCoroutine
{
    private PlayerStudyRecordObject mPlayerStudyRecordObject;
    public PlayerSaveRecordCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_PLAY_SAVE_RECORD);
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
            list.put("record_type", String.valueOf(mPlayerStudyRecordObject.getRecordType()));
            list.put("play_type", String.valueOf(mPlayerStudyRecordObject.getPlayType()));
            list.put("content_id", String.valueOf(mPlayerStudyRecordObject.getContentId()));
            list.put("play_time_sec", String.valueOf(mPlayerStudyRecordObject.getPlayTime()));

            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_PLAY_SAVE_RECORD, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_PLAY_SAVE_RECORD, e.getMessage());
            }

        }

        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mPlayerStudyRecordObject = (PlayerStudyRecordObject)objects[0];
    }
}

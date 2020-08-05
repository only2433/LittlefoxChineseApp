package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class ChildRequestAddCoroutine extends BaseCoroutine
{
    private UserBaseInformationObject mChildInformationObject = null;
    public ChildRequestAddCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_ADD);
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
            ContentValues list = new ContentValues();
            list.put("user_name", mChildInformationObject.getName());
            list.put("user_nickname", mChildInformationObject.getNickname());
            list.put("user_birth", mChildInformationObject.getBirthday());
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_ADD_CHILD_ACCOUNT, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
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
        mChildInformationObject =  (UserBaseInformationObject)objects[0];
    }
}

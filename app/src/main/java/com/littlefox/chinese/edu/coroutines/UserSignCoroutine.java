package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserSignObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class UserSignCoroutine extends BaseCoroutine
{
    private UserSignObject mUserSignObject;
    public UserSignCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_USER_SIGN);
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
            try
            {
                ContentValues list = new ContentValues();
                list.put("email", mUserSignObject.getUserId());
                list.put("fu_nick", mUserSignObject.getNickname());
                list.put("login_pw", mUserSignObject.getPassword());
                list.put("birth_ymd", mUserSignObject.getBirthday());
                list.put("phone2", mUserSignObject.getPhoneNumber());
                list.put("fu_name", mUserSignObject.getName());

                String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_SIGN, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);

            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_USER_SIGN, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mUserSignObject = (UserSignObject)objects[0];
    }
}

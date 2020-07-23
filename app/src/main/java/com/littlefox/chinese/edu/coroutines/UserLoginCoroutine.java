package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.chinese.edu.object.result.UserLoginResult;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.common.CommonUtils;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class UserLoginCoroutine extends BaseCoroutine
{
    private UserLoginObject mUserLoginObject = null;
    public UserLoginCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_USER_LOGIN);
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
        UserLoginResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            try
            {
                ContentValues list = new ContentValues();
                list.put("login_id", mUserLoginObject.getUserID());
                list.put("login_pw", mUserLoginObject.getUserPassword());
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_USER_LOGIN, list, NetworkUtil.POST_METHOD);

                result = new Gson().fromJson(response, UserLoginResult.class);
                if(result.getResult().equals(BaseResult.RESULT_OK))
                {
                    CommonUtils.setSharedPreference(mContext, Common.PARAMS_ACCESS_TOKEN, result.getAccessToken());
                    CommonUtils.setSharedPreference(mContext, Common.PARAMS_CLIENT_KEY, result.getClientKey());
                    CommonUtils.setSharedPreference(mContext, Common.PARAMS_SERVER_KEY, result.getServerKey());
                }

            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_USER_LOGIN, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mUserLoginObject = (UserLoginObject)objects[0];
    }
}

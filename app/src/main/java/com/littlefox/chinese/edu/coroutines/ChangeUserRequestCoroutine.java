package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class ChangeUserRequestCoroutine extends BaseCoroutine
{

    private UserBaseInformationObject mChangeUserInformationObject;
    public ChangeUserRequestCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_CHANGE_USER);
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
            list.put("user_name", mChangeUserInformationObject.getName());
            list.put("user_nickname", mChangeUserInformationObject.getNickname());
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_CHANGE_USER, list, NetworkUtil.POST_METHOD);
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
        int index = (int)objects[0];
        UserTotalInformationObject userTotalObject = (UserTotalInformationObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);

        if(index == -1)
        {
            mChangeUserInformationObject = userTotalObject.getBaseUserInformation();
        }
        else
        {
            mChangeUserInformationObject = userTotalObject.getChildInformationList().get(index);
        }
    }
}

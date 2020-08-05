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

public class ChildRequestModificationCoroutine extends BaseCoroutine
{
    /**
     * 이전에 있었던 추가 사용자의 정보
     */
    private UserBaseInformationObject mOriginChildInformationObject;

    /**
     * 바뀔 추가 사용자의 정보
     */
    private UserBaseInformationObject mModificationInformationObject;

    public ChildRequestModificationCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_MODIFICATION);
        setAsyncListener(asyncListener);
    }

    @NotNull
    @Override
    public Object doInBackground()
    {
        if(isRunning() == true)
        {
            return null;
        }

        BaseResult result = null;

        synchronized (mSync)
        {
            setRunning(true);
            ContentValues list = new ContentValues();
            list.put("user_name", mModificationInformationObject.getName());
            list.put("user_nickname", mModificationInformationObject.getNickname());
            list.put("curr_user_name", mOriginChildInformationObject.getName());
            list.put("curr_user_nickname", mOriginChildInformationObject.getNickname());
            list.put("user_birth", mModificationInformationObject.getBirthday());
            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_ADD_CHILD_ACCOUNT, list, NetworkUtil.PUT_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_MODIFICATION, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mOriginChildInformationObject = (UserBaseInformationObject)objects[0];
        mModificationInformationObject = (UserBaseInformationObject)objects[1];
    }
}

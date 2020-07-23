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

import java.io.File;
import java.net.URLEncoder;

public class ChildRequestDeleteCoroutine extends BaseCoroutine
{
    private UserBaseInformationObject mChildInformationObject;
    public ChildRequestDeleteCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_CHILD_ACCOUNT_DELETE);
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

        BaseResult  result = null;

        synchronized (mSync)
        {
            setRunning(true);
            try
            {
                /**
                 * HttpConnection 에서는 Delete가 Get 방식으로 지워지지않아서 Delete를 하지 못하여  HttpClient를 사용한다.
                 */
                ContentValues list = new ContentValues();
                list.put("user_name", mChildInformationObject.getName());
                list.put("user_nickname", mChildInformationObject.getNickname());
                list.put("user_birth", mChildInformationObject.getBirthday());
                String nickName = URLEncoder.encode(mChildInformationObject.getNickname(),"UTF-8");

                String response = NetworkUtil.requestServerPair(mContext,
                        Common.URI_ADD_CHILD_ACCOUNT+ File.separator + nickName,
                        null,
                        NetworkUtil.DELETE_METHOD);


                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_CHILD_ACCOUNT_DELETE, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mChildInformationObject = (UserBaseInformationObject)objects[0];
    }
}

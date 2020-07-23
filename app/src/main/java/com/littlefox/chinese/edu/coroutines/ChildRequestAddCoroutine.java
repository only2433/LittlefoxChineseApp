package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.result.InitAppResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class ChildRequestAddCoroutine extends BaseCoroutine
{
    public ChildRequestAddCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_INIT_APP_INFO_REQUEST);
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
        InitAppResult result = null;
        synchronized (mSync)
        {
            setRunning(true);
            String appLanguage = Feature.IS_LANGUAGE_ENG ? "en" : "ko";

            ContentValues list = new ContentValues();
            list.put("dvc_name", android.os.Build.MODEL);
            list.put("device_id", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
            list.put("push_address", (String) CommonUtils.getInstance(mContext).getSharedPreference(Common.PARAMS_FIREBASE_PUSH_TOKEN, Common.TYPE_PARAMS_STRING));
            list.put("app_lang", appLanguage);
            String  response = NetworkUtil.requestServerPair(mContext, Common.URI_INIT_APP, list, NetworkUtil.POST_METHOD, Common.API_VERSION_1_0_3);
            result = new Gson().fromJson(response, InitAppResult.class);
        }
        return result;
    }

    @Override
    public void setData(@NotNull Object... objects) { }
}

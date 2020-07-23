package com.littlefox.chinese.edu.coroutines;

import android.content.ContentValues;
import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.PaymentInformation;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;

import org.jetbrains.annotations.NotNull;

public class SavePaymentCoroutine extends BaseCoroutine
{
    private PaymentInformation mPaymentInformation;
    public SavePaymentCoroutine(@NotNull Context context, AsyncListener listener)
    {
        super(context, Common.ASYNC_CODE_SAVE_PAYMENT);
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
            list.put("device_id", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
            list.put("product_id", mPaymentInformation.getProductID());
            list.put("transaction_id", mPaymentInformation.getTransactionID());
            list.put("transaction_date", String.valueOf(mPaymentInformation.getTransactionDate()));
            list.put("expire_date", String.valueOf(mPaymentInformation.getExpireDate()));

            try
            {
                String response = NetworkUtil.requestServerPair(mContext, Common.URI_SAVE_PAYMENT, list, NetworkUtil.POST_METHOD);
                result = new Gson().fromJson(response, BaseResult.class);
            }catch(Exception e)
            {
                e.printStackTrace();
                getAsyncListener().onErrorListener(Common.ASYNC_CODE_SAVE_PAYMENT, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mPaymentInformation = (PaymentInformation)objects[0];
    }
}

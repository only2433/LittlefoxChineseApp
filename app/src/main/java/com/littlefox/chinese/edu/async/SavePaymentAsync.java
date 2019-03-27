package com.littlefox.chinese.edu.async;

import android.content.ContentValues;
import android.content.Context;
import android.provider.Settings.Secure;

import com.google.gson.Gson;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.object.PaymentInformation;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;

public class SavePaymentAsync extends BaseAsync
{
	private PaymentInformation mPaymentInformation;
	public SavePaymentAsync(Context context, PaymentInformation payInformation, AsyncListener listener)
	{
		super(context, Common.ASYNC_CODE_SAVE_PAYMENT);
		setAsyncListener(listener);
		mPaymentInformation = payInformation;
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		if(isRunning == true)
		{
			return null;
		}
		BaseResult result = null;
		synchronized (mSync)
		{
			isRunning = false;
			ContentValues list = new ContentValues();
			list.put("device_id", Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID));
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
				mAsyncListener.onErrorListener(Common.ASYNC_CODE_SAVE_PAYMENT, e.getMessage());
			}
		}
		
		return result;	
	}

	@Override
	public void setData(Object... objects) {

	}
}

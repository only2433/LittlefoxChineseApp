package com.littlefox.chinese.edu.object;

import android.content.Context;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.billing.BillingClientHelper;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.logmonitor.Log;

/**
 * Preference에 저정하여 사용 ( 사용자의 Inapp 결제 정보 )
 * @author 정재현
 *
 */
public class InAppInformation
{
	private String inAppType = BillingClientHelper.IN_APP_FREE_USER;
	private long inAppStartDay = 0L;
	private long inAppEndDay = 0L;
	
	public InAppInformation()
	{
		inAppType = BillingClientHelper.IN_APP_FREE_USER;
		inAppStartDay = 0L;
		inAppEndDay = 0L;
	}
	public InAppInformation(String appType, long appStartDay, long appEndDay)
	{
		Log.f("appType : "+appType);
		Log.f("AppStartDay : "+appStartDay);
		Log.f("AppEndDay : "+appEndDay);
		
		inAppType 		= appType;
		inAppStartDay 	= appStartDay;
		inAppEndDay 	= appEndDay;
	}

	public String getInAppType()
	{
		return inAppType;
	}
	
	public String getInAppTitle(Context context)
	{
		if(inAppType.equals(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH))
		{
			return CommonUtils.getInstance(context).getLanguageTypeString(R.array.title_pay_1_month);
		}
		else if(inAppType.equals(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH))
		{
			return CommonUtils.getInstance(context).getLanguageTypeString(R.array.title_pay_subscription);
		}
		return "";
	}

	public long getInAppStartDay()
	{
		return inAppStartDay;
	}

	public long getInAppEndDay()
	{
		return inAppEndDay;
	}
	
	
}

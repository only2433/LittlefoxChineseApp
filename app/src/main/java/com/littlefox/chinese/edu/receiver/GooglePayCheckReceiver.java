package com.littlefox.chinese.edu.receiver;

import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Purchase;
import com.littlefox.chinese.edu.IntroLoadingActivity;
import com.littlefox.chinese.edu.billing.IBillingStatusListener;
import com.littlefox.chinese.edu.billing.InAppPurchase;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.InAppInformation;
import com.littlefox.logmonitor.Log;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 12시간 마다 구글 정보를 체크하는 Alarm Receiver
 * @author 정재현
 *
 */
public class GooglePayCheckReceiver extends BroadcastReceiver
{
	private AlarmManager mAlarmManager;
	private PendingIntent mPendingIntent;
	private InAppPurchase mInAppPurchase;
	private boolean isReceiveEvent = false;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.f("Current Mode : "+ MainSystemFactory.getInstance().getCurrentMode());
		
		/**
		 * 현재 결제 화면에 있다면 결제정보를 체크하지 않는다.
		 */
		if(MainSystemFactory.getInstance().getCurrentMode() == MainSystemFactory.MODE_PAY_PAGE)
		{
			return;
		}
		
		initInAppPurchase(context);
		isReceiveEvent = true;
	}
	
	public void register(Context context)
	{
		mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context,  GooglePayCheckReceiver.class);
		mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HALF_DAY , AlarmManager.INTERVAL_HALF_DAY , mPendingIntent);
		isReceiveEvent = false;
	}
	
	public void unRegister(Context context)
	{
		if(mAlarmManager != null)
		{
			mAlarmManager.cancel(mPendingIntent);
		}
		isReceiveEvent = false;
	}
	
	public void initReceiveEvent()
	{
		isReceiveEvent = false;
	}
	
	public boolean isReceiveEvent()
	{
		return isReceiveEvent;
	}
	
    private void initInAppPurchase(Context context)
    {
    	Log.f("init app purchase");
    	mInAppPurchase = InAppPurchase.getInstance();
    	mInAppPurchase.init(context);
    	setupInAppPurchaseListener(context);
    	mInAppPurchase.settingPurchaseInforamtionToGoogle();
    }
	  
    /**
     * 해당내용은 추후 INTRO로 전환한다. 결제를 제외한 취소, 소모 , 등을 여기서 작업
     */
    private void setupInAppPurchaseListener(final Context context)
    {
    	mInAppPurchase.setOnBillingStatusListener(new IBillingStatusListener()
		{
			
			@Override
			public void onQueryInventoryFinished(IabResult result)
			{
				try
				{
					InAppInformation inappInformation;
					String payType = InAppPurchase.IN_APP_FREE_USER;
					
					Purchase subscription = mInAppPurchase.getInventory().getPurchase(InAppPurchase.IN_APP_SUBSCRIPTION_1_MONTH);
					if(subscription != null)
					{
						payType = InAppPurchase.IN_APP_SUBSCRIPTION_1_MONTH;
						Log.f("subscription order ID : "+ subscription.getOrderId());
						Log.f("subscription getItemType : "+ subscription.getItemType());
						Log.f("subscription getPurchaseState : "+ subscription.getPurchaseState());
						Log.f("subscription getPurchaseTime Real : "+ subscription.getPurchaseTime());
						Log.f("subscription getPurchaseTime : "+ CommonUtils.getInstance(context).getDateTime(subscription.getPurchaseTime()));
						
						Log.f("Purchase : subscription");
						inappInformation = new InAppInformation(InAppPurchase.IN_APP_SUBSCRIPTION_1_MONTH, subscription.getPurchaseTime(), CommonUtils.getInstance(context).getAdded1Month(subscription.getPurchaseTime()));
						CommonUtils.getInstance(context).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);
					}
					
					Purchase consumable = mInAppPurchase.getInventory().getPurchase(InAppPurchase.IN_APP_CONSUMABLE_1_MONTH);
					if(consumable != null)
					{
						/**
						 * 결제한지 1달이 지낫을때 Cosume시키고 무료회원으로 전환한다.
						 */
						payType = InAppPurchase.IN_APP_CONSUMABLE_1_MONTH;
						Log.f("consumable order ID : "+ consumable.getOrderId());
						Log.f("consumable getItemType : "+ consumable.getItemType());
						Log.f("consumable getPurchaseState : "+ consumable.getPurchaseState());
						Log.f("consumable getPurchaseTime Real : "+ consumable.getPurchaseTime());
						Log.f("consumable getPurchaseTime : "+ CommonUtils.getInstance(context).getDateTime(consumable.getPurchaseTime()));
						
						Log.f("Purchase : 1 Month");
						inappInformation = new InAppInformation(InAppPurchase.IN_APP_CONSUMABLE_1_MONTH, consumable.getPurchaseTime(), CommonUtils.getInstance(context).getAdded1Month(consumable.getPurchaseTime()));
						CommonUtils.getInstance(context).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);
						
						if(CommonUtils.getInstance(context).isOverPayDay(inappInformation.getInAppEndDay()))
						{
							Log.f("Purchase Over 30 days is ended. ==== Consumable");
							mInAppPurchase.consumePurchase(consumable);
							payType = InAppPurchase.IN_APP_FREE_USER;
						}
					}
					
					consumable = mInAppPurchase.getInventory().getPurchase(InAppPurchase.IN_APP_CONSUMABLE_1_YEAR);
					if(consumable != null)
					{
						/**
						 * 결제한지 1년이 지낫을때 Cosume시키고 무료회원으로 전환한다.
						 */
						payType = InAppPurchase.IN_APP_CONSUMABLE_1_YEAR;
						Log.f("consumable order ID : "+ consumable.getOrderId());
						Log.f("consumable getItemType : "+ consumable.getItemType());
						Log.f("consumable getPurchaseState : "+ consumable.getPurchaseState());
						Log.f("consumable getPurchaseTime Real : "+ consumable.getPurchaseTime());
						Log.f("consumable getPurchaseTime : "+ CommonUtils.getInstance(context).getDateTime(consumable.getPurchaseTime()));

						Log.f("Purchase : 1 Year");
						inappInformation = new InAppInformation(InAppPurchase.IN_APP_CONSUMABLE_1_YEAR, consumable.getPurchaseTime(), CommonUtils.getInstance(context).getAdded1year(consumable.getPurchaseTime()));
						CommonUtils.getInstance(context).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappInformation);
						
						if(CommonUtils.getInstance(context).isOverPayDay(inappInformation.getInAppEndDay()))
						{
							Log.f("Purchase Over 1 Year is ended. ==== Consumable");
							mInAppPurchase.consumePurchase(consumable);
							payType = InAppPurchase.IN_APP_FREE_USER;
						}
					}
					
					if(payType.equals(InAppPurchase.IN_APP_FREE_USER))
					{
						Log.f("Not Purchase : Free User");
						InAppInformation inappinformation = new InAppInformation();
						CommonUtils.getInstance(context).setPreferenceObject(Common.PARAMS_INAPP_INFO, inappinformation);
					}

				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase purchase)
			{
				Log.f("onIabPurchaseFinished: "+ result.getMessage());
			}
			
			@Override
			public void onConsumeFinished(IabResult result)
			{
				Log.f("onConsumeFinished : "+ result.getMessage());
			}
			
			@Override
			public void inFailure(int status, String reason)
			{
				Log.f("status : "+status +", reason : "+ reason);
			}
			
			@Override
			public void OnIabSetupFinished(IabResult result)
			{

			}
		});
    }

}

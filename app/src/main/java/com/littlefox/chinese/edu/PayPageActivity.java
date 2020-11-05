package com.littlefox.chinese.edu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.billing.BillingClientHelper;
import com.littlefox.chinese.edu.billing.IBillingClientListener;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.chinese.edu.coroutines.SavePaymentCoroutine;
import com.littlefox.chinese.edu.factory.MainSystemFactory;
import com.littlefox.chinese.edu.object.InAppInformation;
import com.littlefox.chinese.edu.object.PaymentInformation;
import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.text.SeparateTextView;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayPageActivity extends BaseActivity
{
	@BindView(R.id.pay_main_base_coordinator_layout)
	CoordinatorLayout _BaseCoordinatorLayout;

	@BindView(R.id.pay_feature_image_layout)
	ScalableLayout _PayFeatureImageLayout;

	@BindView(R.id.pay_main_menu_title)
	TextView _PayTitleText;

	@BindView(R.id.pay_main_close)
	ImageView _CloseButton;

	@BindView(R.id.pay_information_message_image)
	ImageView _PayInformationMessageImage;

	@BindView(R.id.pay_layout_30days)
	ImageView _Pay30DaysButton;

	@BindView(R.id.pay_30days_message)
	TextView _Pay30DaysText;

	@BindView(R.id.pay_layout_subscription)
	ImageView _PaySubscriptionButton;

	@BindView(R.id.pay_subscription_message)
	TextView _PaySubscriptionText;

	@BindView(R.id.pay_price_subscription_text)
	SeparateTextView _PaySubscriptionPriceText;

	@BindView(R.id.pay_price_30days_text)
	SeparateTextView _Pay30DaysPriceText;

	@BindView(R.id.pay_device_warning_text)
	ImageView _PayDeviceWarningTextImageView;

	Handler mMainHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case MESSAGE_PURCHASE_COMPLETE:
					setResult(RESULT_OK);
					((Activity)PayPageActivity.this).onBackPressed();
					break;
				case MESSAGE_NETWORK_RETRY:
					processPayment();
					break;
			}
		}

	};

	private static final int TYPE_TABLET 					= 0;
	private static final int TYPE_NORMAL_PHONE 				= 1;

	private static final int MESSAGE_PURCHASE_COMPLETE 		= 100;
	private static final int MESSAGE_NETWORK_RETRY			= 101;

	private static final int PAY_ITEM_1MONTH 		= 0;
	private static final int PAY_ITEM_SUBSCRIPTION 	= 1;
	private static final int PAY_ITEM_1YEAR			= 2;

	private static final int DURATION_ANIMATION = 300;
	private static final int DURATION_PURCHASE_COMPLETE = 500;

	private BillingClientHelper mBillingClientHelper;
	private int mCurrentPayItemCode = -1;
	private Purchase mCurrentPurchaseItem = null;

	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);

		if(Feature.IS_TABLET)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.pay_page_main_tablet);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.pay_page_main);
		}



		ButterKnife.bind(this);

		if(Build.VERSION.SDK_INT >= Common.LOLLIPOP)
		{
			initTransition();
		}
		initView();
		initText();
		initFont();

		initInAppPurchase();
		Log.f("");
	}

	private void initView()
	{
		if(Feature.HAVE_NAVIGATION_BAR || (!Feature.IS_TABLET && Feature.IS_MINIMUM_DISPLAY_SIZE))
		{
			_PayFeatureImageLayout.setVisibility(View.GONE);
		}
		if(Feature.IS_TABLET)
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_Pay30DaysButton.setImageResource(R.drawable.payment_1month_en__tablet);
				_PaySubscriptionButton.setImageResource(R.drawable.payment_recurring_en__tablet);
				_PayInformationMessageImage.setImageResource(R.drawable.payment_text_en_tablet);
				_PayDeviceWarningTextImageView.setImageResource(R.drawable.payment_text_bottom_tablet_en);
			}
			else
			{
				_Pay30DaysButton.setImageResource(R.drawable.payment_1month_tablet);
				_PaySubscriptionButton.setImageResource(R.drawable.payment_recurring__tablet);
				_PayInformationMessageImage.setImageResource(R.drawable.payment_text_tablet);
				_PayDeviceWarningTextImageView.setImageResource(R.drawable.payment_text_bottom_tablet);
			}
		}
		else
		{
			if(Feature.IS_LANGUAGE_ENG)
			{
				_Pay30DaysButton.setImageResource(R.drawable.payment_1month_en);
				_PaySubscriptionButton.setImageResource(R.drawable.payment_recurring_en);
				_PayInformationMessageImage.setImageResource(R.drawable.payment_text_en);
				_PayDeviceWarningTextImageView.setImageResource(R.drawable.payment_text_bottom_en);
			}
			else
			{
				_Pay30DaysButton.setImageResource(R.drawable.payment_1month);
				_PaySubscriptionButton.setImageResource(R.drawable.payment_recurring);
				_PayInformationMessageImage.setImageResource(R.drawable.payment_text);
				_PayDeviceWarningTextImageView.setImageResource(R.drawable.payment_text_bottom);
			}
		}

	}

	private void initFont()
	{
		_PayTitleText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_Pay30DaysText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_PaySubscriptionText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_PaySubscriptionPriceText.setTypeface(Font.getInstance(this).getRobotoMedium());
		_Pay30DaysPriceText.setTypeface(Font.getInstance(this).getRobotoMedium());
	}

	private void initText()
	{
		_PayTitleText.setText(CommonUtils.getInstance(this).getLanguageTypeString(R.array.title_paid_main));
		if(Feature.IS_LANGUAGE_ENG)
		{
			_Pay30DaysText.setText(Html.fromHtml("Valid until " + "<b>"+ getPayFormattedDate(PAY_ITEM_1MONTH)+"</b>"));
			_PaySubscriptionText.setText(Html.fromHtml("Automatically renew every month on the <b>"+ getPayFormattedDate(PAY_ITEM_SUBSCRIPTION)+"</b>"));
		}
		else
		{
			_Pay30DaysText.setText(Html.fromHtml("오늘 결제하면 " + "<b>"+ getPayFormattedDate(PAY_ITEM_1MONTH)+"</b>까지 이용가능"));
			_PaySubscriptionText.setText(Html.fromHtml("오늘 결제하면 매월 <b>"+ getPayFormattedDate(PAY_ITEM_SUBSCRIPTION)+"</b> 자동 결제"));
		}

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		MainSystemFactory.getInstance().setCurrentMode(MainSystemFactory.MODE_PAY_PAGE);
		Log.f("");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.f("");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Log.f("");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mBillingClientHelper.release();
		Log.f("");
	}

	private void initTransition()
	{
		Slide transitionEnter = new Slide();
		transitionEnter.setSlideEdge(Gravity.RIGHT);
		transitionEnter.setInterpolator(new LinearOutSlowInInterpolator());
		transitionEnter.setDuration(DURATION_ANIMATION);
		transitionEnter.excludeTarget(android.R.id.statusBarBackground, true);

		Slide transitionExit = new Slide();
		transitionExit.setSlideEdge(Gravity.RIGHT);
		transitionExit.setDuration(DURATION_ANIMATION);
		transitionExit.setInterpolator(new LinearOutSlowInInterpolator());
		transitionExit.excludeTarget(android.R.id.statusBarBackground, true);

		getWindow().setEnterTransition(transitionEnter);
		getWindow().setExitTransition(transitionExit);
		getWindow().setAllowEnterTransitionOverlap(true);
		getWindow().setAllowReturnTransitionOverlap(true);
		_BaseCoordinatorLayout.setTransitionGroup(true);
	}

	private String getPayFormattedDate(int type)
	{
		Date date;
		String todayString = "";

		switch(type)
		{
			case PAY_ITEM_1MONTH:
				date = new Date(CommonUtils.getInstance(this).getAdded1Month(System.currentTimeMillis()));
				if(Feature.IS_LANGUAGE_ENG)
				{
					String day = new SimpleDateFormat("dd").format(date);
					day += CommonUtils.getInstance(this).getDayNumberSuffixToEN(Integer.parseInt(day));
					String month = new SimpleDateFormat("MMM").format(date);
					todayString = month + ". " + day;
				}
				else
				{
					todayString = new SimpleDateFormat("MM월 dd일").format(date);
				}

				break;
			case PAY_ITEM_SUBSCRIPTION:
				date = new Date(System.currentTimeMillis());
				if(Feature.IS_LANGUAGE_ENG)
				{
					todayString = new SimpleDateFormat("dd").format(date);
					todayString += CommonUtils.getInstance(this).getDayNumberSuffixToEN(Integer.parseInt(todayString));
				}
				else
				{
					todayString = new SimpleDateFormat("dd일").format(date);
				}

				break;
		}
		return todayString;
	}

	private PaymentInformation getPaymentInformation(String type, Purchase purchase)
	{
		long expireDate = 0L;
		switch(type)
		{
			case Common.PAYMENT_ID_30_DAYS:
			case Common.PAYMENT_ID_SUBSCRIPTION:
				expireDate = CommonUtils.getInstance(this).getAdded1Month(purchase.getPurchaseTime());
				break;
			case Common.PAYMENT_ID_365_DAYS:
				expireDate = CommonUtils.getInstance(this).getAdded1year(purchase.getPurchaseTime());
				break;
		}

		return new PaymentInformation(type, purchase.getOrderId(),purchase.getPurchaseTime(),expireDate);
	}

	private void sendPaymentInformationForServer(PaymentInformation paymentInformation)
	{
		SavePaymentCoroutine coroutine = new SavePaymentCoroutine(this, new AsyncListener() {
			@Override
			public void onRunningStart(String code) { }

			@Override
			public void onRunningEnd(String code, Object mObject)
			{
				BaseResult result = (BaseResult)mObject;

				if(result.code == BaseResult.SUCCESS_CODE_OK)
				{
					switch(mCurrentPayItemCode)
					{
						case PAY_ITEM_1MONTH:
							mBillingClientHelper.consumeItem(mCurrentPurchaseItem);
							break;
						case PAY_ITEM_SUBSCRIPTION:
							mBillingClientHelper.confirmSubsItem(mCurrentPurchaseItem);
							break;
					}
					Log.f("서버에게 결제정보 전달 성공");
					mMainHandler.sendEmptyMessageDelayed(MESSAGE_PURCHASE_COMPLETE, DURATION_PURCHASE_COMPLETE);
				}
				else
				{
					Log.f("서버에게 결제정보 전달 실패");
				}
			}

			@Override
			public void onRunningCanceled(String code) { }

			@Override
			public void onRunningProgress(String code, Integer progress) { }

			@Override
			public void onRunningAdvanceInformation(String code, Object object) { }

			@Override
			public void onErrorListener(String code, String message) { }
		});
		coroutine.setData(paymentInformation);
		coroutine.execute();
	}

	@OnClick({R.id.pay_main_close, R.id.pay_layout_30days, R.id.pay_layout_subscription})
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
			case R.id.pay_main_close:
				super.onBackPressed();
				return;
			case R.id.pay_layout_30days:
				mCurrentPayItemCode = PAY_ITEM_1MONTH;
				break;
			case R.id.pay_layout_subscription:
				mCurrentPayItemCode = PAY_ITEM_SUBSCRIPTION;
				break;
		}
		processPayment();
	}

	private void processPayment()
	{
		if(NetworkUtil.isConnectNetwork(PayPageActivity.this))
		{
			switch(mCurrentPayItemCode)
			{
				case PAY_ITEM_1MONTH:
					mBillingClientHelper.purchaseItem(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH);
					return;
				case PAY_ITEM_SUBSCRIPTION:
					mBillingClientHelper.purchaseItem(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH);
					return;
			}
		}
		MainSystemFactory.getInstance().startActivityWithAnimation(MainSystemFactory.MODE_NETWORK_ERROR);
	}

	private String[] getPriceInformationText(String price)
	{
		String[] priceInformation = new String[2];
		priceInformation[0] = price.substring(0, 1)+" ";
		priceInformation[1] = price.substring(1);
		return  priceInformation;
	}

	private void initInAppPurchase()
	{
		Log.f("init app purchase");
		mBillingClientHelper = BillingClientHelper.getInstance();
		setupInAppPurchaseListener();
		mBillingClientHelper.init(this);
	}

	private void setupInAppPurchaseListener()
	{
		mBillingClientHelper.setOnBillingClientListener(new IBillingClientListener()
		{
			@Override
			public void onSkuDetailQueryFinished(String type)
			{
				if(type.equals(BillingClient.SkuType.INAPP))
				{
					// INAPP
					Log.f("가격 한달 "+  mBillingClientHelper.getSkuDetailData(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH).getPrice());
					String[] consumableMonthText = getPriceInformationText(mBillingClientHelper.getSkuDetailData(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH).getPrice());

					if(Feature.IS_TABLET)
					{
						_Pay30DaysPriceText.setSeparateText(consumableMonthText[0], consumableMonthText[1])
								.setSeparateTextSize(CommonUtils.getInstance(PayPageActivity.this).getPixel(40), CommonUtils.getInstance(PayPageActivity.this).getPixel(70))
								.showView();
					}
					else
					{
						_Pay30DaysPriceText.setSeparateText(consumableMonthText[0], consumableMonthText[1])
								.setSeparateTextSize(CommonUtils.getInstance(PayPageActivity.this).getPixel(60), CommonUtils.getInstance(PayPageActivity.this).getPixel(100))
								.showView();
					}
				}
				else
				{
					// SUBS
					Log.f("가격 자동 "+  mBillingClientHelper.getSkuDetailData(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH).getPrice());

					String[] subscriptionMonthText = getPriceInformationText(mBillingClientHelper.getSkuDetailData(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH).getPrice());

					if(Feature.IS_TABLET)
					{
						_PaySubscriptionPriceText.setSeparateText(subscriptionMonthText[0], subscriptionMonthText[1])
								.setSeparateTextSize(CommonUtils.getInstance(PayPageActivity.this).getPixel(40), CommonUtils.getInstance(PayPageActivity.this).getPixel(70))
								.showView();
					}
					else
					{
						_PaySubscriptionPriceText.setSeparateText(subscriptionMonthText[0], subscriptionMonthText[1])
								.setSeparateTextSize(CommonUtils.getInstance(PayPageActivity.this).getPixel(60), CommonUtils.getInstance(PayPageActivity.this).getPixel(100))
								.showView();
					}
				}
			}

			@Override
			public void onCheckPurchaseItem() { }


			@Override
			public void onPurchaseComplete(@Nullable com.android.billingclient.api.Purchase purchaseItem)
			{
				Log.f("purchaseItem Json : "+ purchaseItem.getOriginalJson());
				InAppInformation mInAppInformation = null;
				mCurrentPurchaseItem = purchaseItem;
				switch(mCurrentPayItemCode)
				{
					case PAY_ITEM_1MONTH:
						Log.f("1Month Item Pay Complete");
						GoogleAnalyticsHelper.getInstance(PayPageActivity.this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_PAYMENT, Common.ANALYTICS_ACTION_PAY_1MONTH);
						Feature.IS_FREE_USER = false;
						mInAppInformation = new InAppInformation(BillingClientHelper.IN_APP_CONSUMABLE_1_MONTH, purchaseItem.getPurchaseTime(), CommonUtils.getInstance(PayPageActivity.this).getAdded1Month(purchaseItem.getPurchaseTime()));
						CommonUtils.getInstance(PayPageActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, mInAppInformation);
						sendPaymentInformationForServer(getPaymentInformation(Common.PAYMENT_ID_30_DAYS, purchaseItem));
						break;
					case PAY_ITEM_SUBSCRIPTION:
						Log.f("Subscrption Item Pay Complete");
						GoogleAnalyticsHelper.getInstance(PayPageActivity.this).sendCurrentEvent(Common.ANALYTICS_CATEGORY_PAYMENT, Common.ANALYTICS_ACTION_PAY_1MONTH_AUTO);
						Feature.IS_FREE_USER = false;
						mInAppInformation = new InAppInformation(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH, purchaseItem.getPurchaseTime(), CommonUtils.getInstance(PayPageActivity.this).getAdded1Month(purchaseItem.getPurchaseTime()));
						CommonUtils.getInstance(PayPageActivity.this).setPreferenceObject(Common.PARAMS_INAPP_INFO, mInAppInformation);
						sendPaymentInformationForServer(getPaymentInformation(Common.PAYMENT_ID_SUBSCRIPTION, purchaseItem));
						break;
				}

			}

			@Override
			public void onConsumeComplete(BillingResult billingResult, String purchaseToken) { }

			@Override
			public void onConfirmSubsComplete(BillingResult billingResult) { }

			@Override
			public void inFailure(int status, String reason)
			{
				Log.f("status : "+status +", reason : "+ reason);
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.i("onActivityResult(" + requestCode + "," + resultCode + "," + data);
	}
}

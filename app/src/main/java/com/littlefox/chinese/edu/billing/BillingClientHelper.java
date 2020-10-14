package com.littlefox.chinese.edu.billing;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.littlefox.chinese.edu.R;
import com.littlefox.logmonitor.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillingClientHelper implements PurchasesUpdatedListener, ConsumeResponseListener, AcknowledgePurchaseResponseListener
{
    public static final String IN_APP_FREE_USER					= "free_user";
    public static final String IN_APP_CONSUMABLE_1_MONTH 		= "consumable_1_month";
    public static final String IN_APP_SUBSCRIPTION_1_MONTH 		= "subscription_1_month_paid";


    public static final int STATUS_SET_UP_FINISHED          = 0;
    public static final int STATUS_SKU_QUERY_FINISHED       = 1;
    public static final int STATUS_PURCHASE_FINISHED        = 2;
    public static final int STATUS_CONSUME_FINISHED         = 3;
    public static final int STATUS_CONNECTION_DISCONNECTED  = 4;

    private static final String ERROR_MESSAGE_DEVELOPER_PAYLOAD = "Current Payload is Developer.";

    /**
     * 결제 관련 IN APP BILLING KEY
     */
    private static final String IN_APP_BILLING_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi3m641qzN11CKdwDHkWb+akiNX0/0EhL228SfJfib6ABQ2arL0J9migio4nDkiUwgu2sikZh1l5GpeJpB4Vecq79v+NrIMZyI3FxCoO4SZLxOQlRpW21WpW7TDNzIQBv/30d4TImflTRCROxvicVQYbQs83KTzHVgob7f21rK4AH6z85NrHb1PBxWy+EHKCZxV2hA+Emt2VV8JXJ/CwBd+CmF4NZo01pAgDvQeVCr9zBw1ZEgrPf8X6YX7mcy2+VPILxaNFnto+8kanS8afiukMk96MmqlpHlhVibPIA5Gecm50K8fGDhkS/ANXSnvvCldzmoV+v36PueIBV722F0wIDAQAB";

    /**
     * 구매 흐름에 관한 Request Code
     */
    public static final int REQUEST_CODE = 10001;
    private static BillingClientHelper sBillingClientHelper = null;
    /**
     * Developer 결제 테스트 계정인지 확인 하기 위해 사용
     */
    private String mPayLoad = null;
    private IBillingClientListener iIBillingClientListener;
    private Context mContext;
    /**
     * 현재 결제 하는 Sku
     */
    private String mCurrentPaySkuCode = "";
    private BillingClient mBillingClient = null;
    private List<SkuDetails> mResponseSkuDetailList = null;
    private Purchase.PurchasesResult mInAppPurchaseResult = null;
    private Purchase.PurchasesResult mSubsPurchaseResult = null;

    public static BillingClientHelper getInstance()
    {
        if(sBillingClientHelper == null)
        {
            sBillingClientHelper = new BillingClientHelper();
        }
        return sBillingClientHelper;
    }

    public void init(Context context)
    {
        mContext = context;
        mPayLoad = UUID.randomUUID().toString();
        mBillingClient = BillingClient.newBuilder(mContext).setListener(this).enablePendingPurchases().build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if(mBillingClient == null)
                {
                    iIBillingClientListener.inFailure(STATUS_SET_UP_FINISHED, mContext.getResources().getString(R.string.product_is_null));
                }

                try
                {
                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                    {
                        List<String> list = new ArrayList<String>();
                        list.add(IN_APP_CONSUMABLE_1_MONTH);
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(list).setType(BillingClient.SkuType.INAPP);
                        mBillingClient.querySkuDetailsAsync(params.build(), mSkuDetailsInAppResponseListener);

                        list.add(IN_APP_SUBSCRIPTION_1_MONTH);
                        params = SkuDetailsParams.newBuilder();
                        params.setSkusList(list).setType(BillingClient.SkuType.SUBS);
                        mBillingClient.querySkuDetailsAsync(params.build(), mSkuDetailsSubsResponseListener);

                        mInAppPurchaseResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
                        mSubsPurchaseResult = mBillingClient.queryPurchases(BillingClient.SkuType.SUBS);
                        iIBillingClientListener.onCheckPurchaseItem();
                    }
                    else
                    {
                        Log.f("구글 결제 서버와 연결 실패, 오류 코드 : "+ billingResult.getResponseCode());
                    }

                }
                catch (Exception e)
                {
                    iIBillingClientListener.inFailure(STATUS_SET_UP_FINISHED, e.getMessage());
                    Log.f("Error Message : " + e.getMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.f("구글 결제 서버와 접속이 끓어짐.");
            }
        });
    }


    public SkuDetails getSkuDetailData(String skuCode)
    {
        for(int i = 0; i < mResponseSkuDetailList.size(); i++)
        {
            if(mResponseSkuDetailList.get(i).getSku().equals(skuCode))
            {
                return mResponseSkuDetailList.get(i);
            }
        }
        return  null;
    }

    public SkuDetails getSkuDetailData(Purchase item)
    {
        for(int i = 0; i < mResponseSkuDetailList.size(); i++)
        {
            if(mResponseSkuDetailList.get(i).getSku().equals(item.getSku()))
            {
                return mResponseSkuDetailList.get(i);
            }
        }
        return  null;
    }

    public Purchase getPurchasedInAppItemResult(String skuCode)
    {
        for(Purchase data : mInAppPurchaseResult.getPurchasesList())
        {
            if(data.getSku().equals(skuCode))
            {
                return data;
            }
        }
        return null;
    }

    public Purchase getPurchasedSubsItemResult(String skuCode)
    {
        for(Purchase data : mSubsPurchaseResult.getPurchasesList())
        {
            if(data.getSku().equals(skuCode))
            {
                return data;
            }
        }
        return null;
    }


    private SkuDetailsResponseListener mSkuDetailsInAppResponseListener = new SkuDetailsResponseListener() {
        @Override
        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list)
        {
            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
            {
                if(list == null)
                {
                    Log.f("요청한 상품 항목을 찾을 수 없습니다.");
                }

                if(mResponseSkuDetailList == null)
                {
                    mResponseSkuDetailList = list;
                }
                else
                {
                    mResponseSkuDetailList.addAll(list);
                }

                iIBillingClientListener.onSkuDetailQueryFinished(BillingClient.SkuType.INAPP);
            }
            else
            {
                Log.f("결제 아이템 데이터 구글 서버에서 IN_APP ITEM 받아오기 실패, 오류코드 : "+ billingResult.getResponseCode());
                iIBillingClientListener.inFailure(STATUS_SKU_QUERY_FINISHED, billingResult.getDebugMessage());
            }
        }
    };


    private SkuDetailsResponseListener mSkuDetailsSubsResponseListener = new SkuDetailsResponseListener() {
        @Override
        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list)
        {
            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
            {
                if(list == null)
                {
                    Log.f("요청한 상품 항목을 찾을 수 없습니다.");
                }

                if(mResponseSkuDetailList == null)
                {
                    mResponseSkuDetailList = list;
                }
                else
                {
                    mResponseSkuDetailList.addAll(list);
                }

                SkuDetails item = getSkuDetailData(BillingClientHelper.IN_APP_SUBSCRIPTION_1_MONTH);
                iIBillingClientListener.onSkuDetailQueryFinished(BillingClient.SkuType.SUBS);
            }
            else
            {
                Log.f("결제 아이템 데이터 구글 서버에서 SUBS ITEM 받아오기 실패, 오류코드 : "+ billingResult.getResponseCode());
                iIBillingClientListener.inFailure(STATUS_SKU_QUERY_FINISHED, billingResult.getDebugMessage());
            }
        }
    };


    @Override
    public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String outToken)
    {
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
        {
            Log.f("결제 소비 성공, 소비한 아이템 토큰 : "+outToken);
            iIBillingClientListener.onConsumeComplete(billingResult, outToken);
        }
        else
        {
            Log.f("결제 소비 실패, 오류 코드 : "+ billingResult.getResponseCode());
        }
    }

    @Override
    public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult)
    {
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
        {
            Log.f("결제 구독 확인 성공");
            iIBillingClientListener.onConfirmSubsComplete(billingResult);
        }
        else
        {
            Log.f("결제 소비 실패, 오류 코드 : "+ billingResult.getResponseCode());
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list)
    {
        Log.f("Result Code : "+billingResult.getResponseCode());
        switch (billingResult.getResponseCode())
        {
            case BillingClient.BillingResponseCode.OK:
                if(list != null)
                {
                    for(Purchase purchase : list)
                    {
                        if(purchase.getSku().equals(mCurrentPaySkuCode))
                        {
                            Log.f("결제 성공, 결제 아이템 Sku Code : "+ purchase.getSku());
                            iIBillingClientListener.onPurchaseComplete(purchase);
                        }
                    }
                }
                break;
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                Log.f("이미 결제한 아이템 보유 중");
                iIBillingClientListener.inFailure(STATUS_PURCHASE_FINISHED, mContext.getResources().getString(R.string.product_already_paid));
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                Log.f("고객이 결제 도중 취소함.");
                iIBillingClientListener.inFailure(STATUS_PURCHASE_FINISHED, mContext.getResources().getString(R.string.product_user_canceled));
                break;
        }
    }

    public void setOnBillingClientListener(IBillingClientListener listener)
    {
        iIBillingClientListener = listener;
    }


    public void purchaseItem(String skuCode)
    {
        Log.f("skuCode : "+skuCode);
        mCurrentPaySkuCode = skuCode;
        BillingFlowParams params;
        BillingResult responseResult;
        SkuDetails data = getSkuDetailData(skuCode);
        if(mBillingClient == null || mResponseSkuDetailList == null || data == null)
        {
            iIBillingClientListener.inFailure(STATUS_SET_UP_FINISHED, mContext.getResources().getString(R.string.product_is_null));
            return;
        }
        params = BillingFlowParams.newBuilder().setSkuDetails(data).build();
        responseResult = mBillingClient.launchBillingFlow((AppCompatActivity)mContext, params);
    }

    public void consumeItem(Purchase consumeItem)
    {
        Log.f("skuCode : "+ consumeItem.getSku());
        ConsumeParams params = null;
        String purchaseToken = "";


        if(consumeItem == null)
        {
            //TODO: 소비할 아이템이 없다.
            Log.f("소비 할 아이템이 없음.");
            return;
        }
        purchaseToken = consumeItem.getPurchaseToken();
        params = ConsumeParams.newBuilder().setPurchaseToken(purchaseToken).build();
        mBillingClient.consumeAsync(params, this);
    }

    public void confirmSubsItem(Purchase purchase)
    {
        Log.f("purchase.getPurchaseState() : "+purchase.getPurchaseState()+", purchase.isAcknowledged() : "+purchase.isAcknowledged());
        if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
        {
            if(!purchase.isAcknowledged())
            {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams
                                .newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                mBillingClient.acknowledgePurchase(acknowledgePurchaseParams, this);
            }
        }
    }

    public void release()
    {
        Log.f("");
        if(mBillingClient != null && mBillingClient.isReady())
        {
            mBillingClient.endConnection();
        }
    }


}

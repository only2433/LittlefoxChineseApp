package com.littlefox.chinese.edu.billing;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;

public interface IBillingClientListener
{
    void inFailure(int status, String reason);

    void onSkuDetailQueryFinished(String type);

    void onCheckPurchaseItem();

    void onPurchaseComplete(@Nullable Purchase purchaseItem);

    void onConsumeComplete(BillingResult billingResult, String purchaseToken);

    void onConfirmSubsComplete(BillingResult billingResult);

}

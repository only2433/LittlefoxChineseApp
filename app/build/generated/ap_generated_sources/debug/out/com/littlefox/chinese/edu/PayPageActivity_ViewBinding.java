// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PayPageActivity_ViewBinding implements Unbinder {
  private PayPageActivity target;

  private View view7f0901ce;

  private View view7f0901cb;

  private View view7f0901cc;

  @UiThread
  public PayPageActivity_ViewBinding(PayPageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PayPageActivity_ViewBinding(final PayPageActivity target, View source) {
    this.target = target;

    View view;
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.pay_main_base_coordinator_layout, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._PayFeatureImageLayout = Utils.findRequiredViewAsType(source, R.id.pay_feature_image_layout, "field '_PayFeatureImageLayout'", ScalableLayout.class);
    target._PayTitleText = Utils.findRequiredViewAsType(source, R.id.pay_main_menu_title, "field '_PayTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.pay_main_close, "field '_CloseButton' and method 'onSelectClick'");
    target._CloseButton = Utils.castView(view, R.id.pay_main_close, "field '_CloseButton'", ImageView.class);
    view7f0901ce = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._PayInformationMessageImage = Utils.findRequiredViewAsType(source, R.id.pay_information_message_image, "field '_PayInformationMessageImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.pay_layout_30days, "field '_Pay30DaysButton' and method 'onSelectClick'");
    target._Pay30DaysButton = Utils.castView(view, R.id.pay_layout_30days, "field '_Pay30DaysButton'", ImageView.class);
    view7f0901cb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._Pay30DaysText = Utils.findRequiredViewAsType(source, R.id.pay_30days_message, "field '_Pay30DaysText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.pay_layout_subscription, "field '_PaySubscriptionButton' and method 'onSelectClick'");
    target._PaySubscriptionButton = Utils.castView(view, R.id.pay_layout_subscription, "field '_PaySubscriptionButton'", ImageView.class);
    view7f0901cc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._PaySubscriptionText = Utils.findRequiredViewAsType(source, R.id.pay_subscription_message, "field '_PaySubscriptionText'", TextView.class);
    target._PaySubscriptionPriceText = Utils.findRequiredViewAsType(source, R.id.pay_price_subscription_text, "field '_PaySubscriptionPriceText'", SeparateTextView.class);
    target._Pay30DaysPriceText = Utils.findRequiredViewAsType(source, R.id.pay_price_30days_text, "field '_Pay30DaysPriceText'", SeparateTextView.class);
    target._PayDeviceWarningTextImageView = Utils.findRequiredViewAsType(source, R.id.pay_device_warning_text, "field '_PayDeviceWarningTextImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PayPageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseCoordinatorLayout = null;
    target._PayFeatureImageLayout = null;
    target._PayTitleText = null;
    target._CloseButton = null;
    target._PayInformationMessageImage = null;
    target._Pay30DaysButton = null;
    target._Pay30DaysText = null;
    target._PaySubscriptionButton = null;
    target._PaySubscriptionText = null;
    target._PaySubscriptionPriceText = null;
    target._Pay30DaysPriceText = null;
    target._PayDeviceWarningTextImageView = null;

    view7f0901ce.setOnClickListener(null);
    view7f0901ce = null;
    view7f0901cb.setOnClickListener(null);
    view7f0901cb = null;
    view7f0901cc.setOnClickListener(null);
    view7f0901cc = null;
  }
}

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
import java.lang.IllegalStateException;
import java.lang.Override;

public class NetworkErrorActivity_ViewBinding implements Unbinder {
  private NetworkErrorActivity target;

  private View view7f0901ab;

  @UiThread
  public NetworkErrorActivity_ViewBinding(NetworkErrorActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NetworkErrorActivity_ViewBinding(final NetworkErrorActivity target, View source) {
    this.target = target;

    View view;
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.network_error_main_base_coordinator_layout, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._MainTitleText = Utils.findRequiredViewAsType(source, R.id.network_error_main_title_text, "field '_MainTitleText'", TextView.class);
    target._SubTitleText = Utils.findRequiredViewAsType(source, R.id.network_error_main_sub_text, "field '_SubTitleText'", TextView.class);
    target._RetryButtonText = Utils.findRequiredViewAsType(source, R.id.network_error_main_button_text, "field '_RetryButtonText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.network_error_retry_button, "field '_RetryButton' and method 'onSelectClick'");
    target._RetryButton = Utils.castView(view, R.id.network_error_retry_button, "field '_RetryButton'", ImageView.class);
    view7f0901ab = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NetworkErrorActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseCoordinatorLayout = null;
    target._MainTitleText = null;
    target._SubTitleText = null;
    target._RetryButtonText = null;
    target._RetryButton = null;

    view7f0901ab.setOnClickListener(null);
    view7f0901ab = null;
  }
}

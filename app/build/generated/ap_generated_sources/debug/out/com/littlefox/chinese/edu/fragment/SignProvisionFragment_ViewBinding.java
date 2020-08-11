// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignProvisionFragment_ViewBinding implements Unbinder {
  private SignProvisionFragment target;

  private View view7f090233;

  @UiThread
  public SignProvisionFragment_ViewBinding(final SignProvisionFragment target, View source) {
    this.target = target;

    View view;
    target._WebViewBaseLayout = Utils.findRequiredViewAsType(source, R.id.sign_provision_webview_layout, "field '_WebViewBaseLayout'", ScalableLayout.class);
    target._WebView = Utils.findRequiredViewAsType(source, R.id.sign_provision_webview, "field '_WebView'", WebView.class);
    view = Utils.findRequiredView(source, R.id.sign_provision_next_button, "field '_NextButton' and method 'selectClick'");
    target._NextButton = Utils.castView(view, R.id.sign_provision_next_button, "field '_NextButton'", TextView.class);
    view7f090233 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.sign_provision_base_layout, "field '_BaseLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignProvisionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._WebViewBaseLayout = null;
    target._WebView = null;
    target._NextButton = null;
    target._BaseLayout = null;

    view7f090233.setOnClickListener(null);
    view7f090233 = null;
  }
}

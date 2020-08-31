// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepLittlefoxChineseIntroduceActivity_ViewBinding implements Unbinder {
  private StepLittlefoxChineseIntroduceActivity target;

  private View view7f090271;

  @UiThread
  public StepLittlefoxChineseIntroduceActivity_ViewBinding(
      StepLittlefoxChineseIntroduceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StepLittlefoxChineseIntroduceActivity_ViewBinding(
      final StepLittlefoxChineseIntroduceActivity target, View source) {
    this.target = target;

    View view;
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.step_littlefox_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.step_littlefox_introduce_close, "field '_CloseButton' and method 'selectClick'");
    target._CloseButton = Utils.castView(view, R.id.step_littlefox_introduce_close, "field '_CloseButton'", ImageView.class);
    view7f090271 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._WebView = Utils.findRequiredViewAsType(source, R.id.step_littlefox_introduce_webview, "field '_WebView'", WebView.class);
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.step_littlefox_introduce_base, "field '_BaseLayout'", RelativeLayout.class);
    target._ProgressWheelView = Utils.findRequiredViewAsType(source, R.id.step_littlefox_introduce_progress_wheel_layout, "field '_ProgressWheelView'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StepLittlefoxChineseIntroduceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleText = null;
    target._CloseButton = null;
    target._WebView = null;
    target._BaseLayout = null;
    target._ProgressWheelView = null;

    view7f090271.setOnClickListener(null);
    view7f090271 = null;
  }
}

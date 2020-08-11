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

public class StepStudyGuideActivity_ViewBinding implements Unbinder {
  private StepStudyGuideActivity target;

  private View view7f090266;

  @UiThread
  public StepStudyGuideActivity_ViewBinding(StepStudyGuideActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StepStudyGuideActivity_ViewBinding(final StepStudyGuideActivity target, View source) {
    this.target = target;

    View view;
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.step_guide_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.step_guide_close, "field '_CloseButton' and method 'selectClick'");
    target._CloseButton = Utils.castView(view, R.id.step_guide_close, "field '_CloseButton'", ImageView.class);
    view7f090266 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._WebView = Utils.findRequiredViewAsType(source, R.id.step_guide_webview, "field '_WebView'", WebView.class);
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.step_guide_base, "field '_BaseLayout'", RelativeLayout.class);
    target._ProgressWheelView = Utils.findRequiredViewAsType(source, R.id.step_guide_progress_wheel_layout, "field '_ProgressWheelView'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StepStudyGuideActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleText = null;
    target._CloseButton = null;
    target._WebView = null;
    target._BaseLayout = null;
    target._ProgressWheelView = null;

    view7f090266.setOnClickListener(null);
    view7f090266 = null;
  }
}

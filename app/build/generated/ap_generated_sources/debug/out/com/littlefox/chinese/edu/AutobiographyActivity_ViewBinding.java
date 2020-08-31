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

public class AutobiographyActivity_ViewBinding implements Unbinder {
  private AutobiographyActivity target;

  private View view7f090062;

  private View view7f090061;

  @UiThread
  public AutobiographyActivity_ViewBinding(AutobiographyActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AutobiographyActivity_ViewBinding(final AutobiographyActivity target, View source) {
    this.target = target;

    View view;
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.autobiography_base, "field '_BaseLayout'", RelativeLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.autobiography_title, "field '_TitleText'", TextView.class);
    target._WebView = Utils.findRequiredViewAsType(source, R.id.autobiography_webview, "field '_WebView'", WebView.class);
    view = Utils.findRequiredView(source, R.id.autobiography_prev, "field '_PrevButton' and method 'selectClick'");
    target._PrevButton = Utils.castView(view, R.id.autobiography_prev, "field '_PrevButton'", ImageView.class);
    view7f090062 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.autobiography_close, "field '_CloseButton' and method 'selectClick'");
    target._CloseButton = Utils.castView(view, R.id.autobiography_close, "field '_CloseButton'", ImageView.class);
    view7f090061 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._ProgressWheelView = Utils.findRequiredViewAsType(source, R.id.autobiography_progress_wheel_layout, "field '_ProgressWheelView'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AutobiographyActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseLayout = null;
    target._TitleText = null;
    target._WebView = null;
    target._PrevButton = null;
    target._CloseButton = null;
    target._ProgressWheelView = null;

    view7f090062.setOnClickListener(null);
    view7f090062 = null;
    view7f090061.setOnClickListener(null);
    view7f090061 = null;
  }
}

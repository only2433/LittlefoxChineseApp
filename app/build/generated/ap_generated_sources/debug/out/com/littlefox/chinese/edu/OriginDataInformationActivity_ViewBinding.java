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

public class OriginDataInformationActivity_ViewBinding implements Unbinder {
  private OriginDataInformationActivity target;

  private View view7f0901b4;

  @UiThread
  public OriginDataInformationActivity_ViewBinding(OriginDataInformationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OriginDataInformationActivity_ViewBinding(final OriginDataInformationActivity target,
      View source) {
    this.target = target;

    View view;
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.top_menu_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.origin_data_close, "field '_CloseButton' and method 'selectClick'");
    target._CloseButton = Utils.castView(view, R.id.origin_data_close, "field '_CloseButton'", ImageView.class);
    view7f0901b4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._StudyDataWebView = Utils.findRequiredViewAsType(source, R.id.origin_data_webview, "field '_StudyDataWebView'", WebView.class);
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.origin_data_base, "field '_BaseLayout'", RelativeLayout.class);
    target._ProgressWheelView = Utils.findRequiredViewAsType(source, R.id.origin_data_progress_wheel_layout, "field '_ProgressWheelView'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OriginDataInformationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleText = null;
    target._CloseButton = null;
    target._StudyDataWebView = null;
    target._BaseLayout = null;
    target._ProgressWheelView = null;

    view7f0901b4.setOnClickListener(null);
    view7f0901b4 = null;
  }
}

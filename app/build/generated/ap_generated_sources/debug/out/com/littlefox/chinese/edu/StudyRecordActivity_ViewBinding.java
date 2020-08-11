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

public class StudyRecordActivity_ViewBinding implements Unbinder {
  private StudyRecordActivity target;

  private View view7f090284;

  @UiThread
  public StudyRecordActivity_ViewBinding(StudyRecordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StudyRecordActivity_ViewBinding(final StudyRecordActivity target, View source) {
    this.target = target;

    View view;
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.study_record_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.study_record_close, "field '_CloseButton' and method 'selectClick'");
    target._CloseButton = Utils.castView(view, R.id.study_record_close, "field '_CloseButton'", ImageView.class);
    view7f090284 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._WebView = Utils.findRequiredViewAsType(source, R.id.study_record_webview, "field '_WebView'", WebView.class);
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.study_record_base, "field '_BaseLayout'", RelativeLayout.class);
    target._ProgressWheelView = Utils.findRequiredViewAsType(source, R.id.study_record_progress_wheel_layout, "field '_ProgressWheelView'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StudyRecordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleText = null;
    target._CloseButton = null;
    target._WebView = null;
    target._BaseLayout = null;
    target._ProgressWheelView = null;

    view7f090284.setOnClickListener(null);
    view7f090284 = null;
  }
}

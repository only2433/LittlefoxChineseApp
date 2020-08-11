// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroLoadingActivity_ViewBinding implements Unbinder {
  private IntroLoadingActivity target;

  @UiThread
  public IntroLoadingActivity_ViewBinding(IntroLoadingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IntroLoadingActivity_ViewBinding(IntroLoadingActivity target, View source) {
    this.target = target;

    target._LogoImageView = Utils.findRequiredViewAsType(source, R.id.intro_loading_logoview, "field '_LogoImageView'", ImageView.class);
    target._FrameAnimationView = Utils.findRequiredViewAsType(source, R.id.intro_loading_frameview, "field '_FrameAnimationView'", ImageView.class);
    target._LoadinProgressText = Utils.findRequiredViewAsType(source, R.id.intro_loading_progress_text, "field '_LoadinProgressText'", TextView.class);
    target._LoadingBaseLayout = Utils.findRequiredViewAsType(source, R.id.intro_loading_layout, "field '_LoadingBaseLayout'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IntroLoadingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._LogoImageView = null;
    target._FrameAnimationView = null;
    target._LoadinProgressText = null;
    target._LoadingBaseLayout = null;
  }
}

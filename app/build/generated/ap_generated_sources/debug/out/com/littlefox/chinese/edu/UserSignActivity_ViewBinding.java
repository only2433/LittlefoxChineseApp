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
import com.littlefox.library.view.extra.SwipeDisableViewPager;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserSignActivity_ViewBinding implements Unbinder {
  private UserSignActivity target;

  private View view7f0902d7;

  @UiThread
  public UserSignActivity_ViewBinding(UserSignActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserSignActivity_ViewBinding(final UserSignActivity target, View source) {
    this.target = target;

    View view;
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.sign_base_coordinator_layout, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._MainTitleText = Utils.findRequiredViewAsType(source, R.id.sign_menu_title, "field '_MainTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.user_sign_close, "field '_UserSignCloseButton' and method 'onSelectClick'");
    target._UserSignCloseButton = Utils.castView(view, R.id.user_sign_close, "field '_UserSignCloseButton'", ImageView.class);
    view7f0902d7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._ViewPager = Utils.findRequiredViewAsType(source, R.id.user_sign_viewpager, "field '_ViewPager'", SwipeDisableViewPager.class);
    target._ProgressWheelLayout = Utils.findRequiredViewAsType(source, R.id.sign_progress_wheel_layout, "field '_ProgressWheelLayout'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserSignActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseCoordinatorLayout = null;
    target._MainTitleText = null;
    target._UserSignCloseButton = null;
    target._ViewPager = null;
    target._ProgressWheelLayout = null;

    view7f0902d7.setOnClickListener(null);
    view7f0902d7 = null;
  }
}

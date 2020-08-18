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

public class UserInformationModificationActivity_ViewBinding implements Unbinder {
  private UserInformationModificationActivity target;

  private View view7f090180;

  @UiThread
  public UserInformationModificationActivity_ViewBinding(
      UserInformationModificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserInformationModificationActivity_ViewBinding(
      final UserInformationModificationActivity target, View source) {
    this.target = target;

    View view;
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_base_coordinator_layout, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.myinfo_menu_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.myinfo_close, "field '_CloseButton' and method 'onSelectClick'");
    target._CloseButton = Utils.castView(view, R.id.myinfo_close, "field '_CloseButton'", ImageView.class);
    view7f090180 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._ViewPager = Utils.findRequiredViewAsType(source, R.id.myinfo_viewpager, "field '_ViewPager'", SwipeDisableViewPager.class);
    target._ProgressLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_progress_wheel_layout, "field '_ProgressLayout'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserInformationModificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseCoordinatorLayout = null;
    target._TitleText = null;
    target._CloseButton = null;
    target._ViewPager = null;
    target._ProgressLayout = null;

    view7f090180.setOnClickListener(null);
    view7f090180 = null;
  }
}

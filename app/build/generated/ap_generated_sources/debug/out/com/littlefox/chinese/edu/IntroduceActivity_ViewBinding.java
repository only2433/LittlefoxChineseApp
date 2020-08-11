// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroduceActivity_ViewBinding implements Unbinder {
  private IntroduceActivity target;

  private View view7f09012c;

  private View view7f09012f;

  @UiThread
  public IntroduceActivity_ViewBinding(IntroduceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IntroduceActivity_ViewBinding(final IntroduceActivity target, View source) {
    this.target = target;

    View view;
    target._RadioTopMarginLayout = Utils.findOptionalViewAsType(source, R.id.top_margin_layout, "field '_RadioTopMarginLayout'", ScalableLayout.class);
    target._ViewPager = Utils.findRequiredViewAsType(source, R.id.introduce_viewpager, "field '_ViewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.introduce_login_button, "field '_LoginButton' and method 'onSelectClick'");
    target._LoginButton = Utils.castView(view, R.id.introduce_login_button, "field '_LoginButton'", ImageView.class);
    view7f09012c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._LoginText = Utils.findRequiredViewAsType(source, R.id.introduce_login_button_text, "field '_LoginText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.introduce_sign_button, "field '_SignButton' and method 'onSelectClick'");
    target._SignButton = Utils.castView(view, R.id.introduce_sign_button, "field '_SignButton'", ImageView.class);
    view7f09012f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._SignText = Utils.findRequiredViewAsType(source, R.id.introduce_sign_button_text, "field '_SignText'", TextView.class);
    target._IndicatorList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.introduce_indicator_0, "field '_IndicatorList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.introduce_indicator_1, "field '_IndicatorList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.introduce_indicator_2, "field '_IndicatorList'", ImageView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    IntroduceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._RadioTopMarginLayout = null;
    target._ViewPager = null;
    target._LoginButton = null;
    target._LoginText = null;
    target._SignButton = null;
    target._SignText = null;
    target._IndicatorList = null;

    view7f09012c.setOnClickListener(null);
    view7f09012c = null;
    view7f09012f.setOnClickListener(null);
    view7f09012f = null;
  }
}

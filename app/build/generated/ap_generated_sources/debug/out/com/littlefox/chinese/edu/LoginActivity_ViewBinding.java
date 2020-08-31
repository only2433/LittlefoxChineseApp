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
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f090158;

  private View view7f090157;

  private View view7f09015a;

  private View view7f09015b;

  private View view7f09015c;

  private View view7f0902c0;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.base_coodinator_layout, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._TopBaseLayout = Utils.findRequiredViewAsType(source, R.id.title_base_layout, "field '_TopBaseLayout'", ScalableLayout.class);
    target._RadioTopMarginLayout = Utils.findOptionalViewAsType(source, R.id.top_margin_layout, "field '_RadioTopMarginLayout'", ScalableLayout.class);
    target._TopBaseText = Utils.findRequiredViewAsType(source, R.id.top_menu_title, "field '_TopBaseText'", TextView.class);
    target._IDEditTextView = Utils.findRequiredViewAsType(source, R.id.login_id_edit, "field '_IDEditTextView'", PaddingDrawableEditText.class);
    target._PasswordEditTextView = Utils.findRequiredViewAsType(source, R.id.login_pw_edit, "field '_PasswordEditTextView'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.login_auto_login_text, "field '_AutoLoginText' and method 'onSelectClick'");
    target._AutoLoginText = Utils.castView(view, R.id.login_auto_login_text, "field '_AutoLoginText'", TextView.class);
    view7f090158 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_auto_check_image, "field '_AutoLoginCheckImage' and method 'onSelectClick'");
    target._AutoLoginCheckImage = Utils.castView(view, R.id.login_auto_check_image, "field '_AutoLoginCheckImage'", ImageView.class);
    view7f090157 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_id_pw_search_text, "field '_IDPasswordSearchText' and method 'onSelectClick'");
    target._IDPasswordSearchText = Utils.castView(view, R.id.login_id_pw_search_text, "field '_IDPasswordSearchText'", TextView.class);
    view7f09015a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_main_login_button, "field '_LoginButton' and method 'onSelectClick'");
    target._LoginButton = Utils.castView(view, R.id.login_main_login_button, "field '_LoginButton'", TextView.class);
    view7f09015b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._TitleLoginAlreadyHaveText = Utils.findRequiredViewAsType(source, R.id.login_already_have_id, "field '_TitleLoginAlreadyHaveText'", TextView.class);
    target._TitleLoginNotHaveIDText = Utils.findRequiredViewAsType(source, R.id.login_not_have_id, "field '_TitleLoginNotHaveIDText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.login_main_sign_button, "field '_SignButton' and method 'onSelectClick'");
    target._SignButton = Utils.castView(view, R.id.login_main_sign_button, "field '_SignButton'", TextView.class);
    view7f09015c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.top_menu_close, "method 'onSelectClick'");
    view7f0902c0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseCoordinatorLayout = null;
    target._TopBaseLayout = null;
    target._RadioTopMarginLayout = null;
    target._TopBaseText = null;
    target._IDEditTextView = null;
    target._PasswordEditTextView = null;
    target._AutoLoginText = null;
    target._AutoLoginCheckImage = null;
    target._IDPasswordSearchText = null;
    target._LoginButton = null;
    target._TitleLoginAlreadyHaveText = null;
    target._TitleLoginNotHaveIDText = null;
    target._SignButton = null;

    view7f090158.setOnClickListener(null);
    view7f090158 = null;
    view7f090157.setOnClickListener(null);
    view7f090157 = null;
    view7f09015a.setOnClickListener(null);
    view7f09015a = null;
    view7f09015b.setOnClickListener(null);
    view7f09015b = null;
    view7f09015c.setOnClickListener(null);
    view7f09015c = null;
    view7f0902c0.setOnClickListener(null);
    view7f0902c0 = null;
  }
}

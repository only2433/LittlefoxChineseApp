// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginInfoSearchDialog_ViewBinding implements Unbinder {
  private LoginInfoSearchDialog target;

  private View view7f09015c;

  private View view7f09015e;

  private View view7f090161;

  private View view7f09015f;

  private View view7f090165;

  @UiThread
  public LoginInfoSearchDialog_ViewBinding(LoginInfoSearchDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginInfoSearchDialog_ViewBinding(final LoginInfoSearchDialog target, View source) {
    this.target = target;

    View view;
    target._TitleBaseLayout = Utils.findRequiredViewAsType(source, R.id.login_title_base_layout, "field '_TitleBaseLayout'", ScalableLayout.class);
    target._TitleLoginLayout = Utils.findRequiredViewAsType(source, R.id.login_title_main_layout, "field '_TitleLoginLayout'", ScalableLayout.class);
    target._TitleLoginText = Utils.findRequiredViewAsType(source, R.id.login_main_title_text, "field '_TitleLoginText'", TextView.class);
    target._TitleSearchLayout = Utils.findRequiredViewAsType(source, R.id.login_title_search_layout, "field '_TitleSearchLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.login_search_id_text, "field '_TitleSearchIDText' and method 'selectClick'");
    target._TitleSearchIDText = Utils.castView(view, R.id.login_search_id_text, "field '_TitleSearchIDText'", TextView.class);
    view7f09015c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_search_pw_text, "field '_TitleSearchPasswordText' and method 'selectClick'");
    target._TitleSearchPasswordText = Utils.castView(view, R.id.login_search_pw_text, "field '_TitleSearchPasswordText'", TextView.class);
    view7f09015e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._TitleSearchPointArrowImage = Utils.findRequiredViewAsType(source, R.id.login_search_index_arrow, "field '_TitleSearchPointArrowImage'", ImageView.class);
    target._LoginBodyType1Layout = Utils.findRequiredViewAsType(source, R.id.login_type_search_type1_layout, "field '_LoginBodyType1Layout'", ScalableLayout.class);
    target._LoginBodyType1MessageText = Utils.findRequiredViewAsType(source, R.id.login_search_type1_text, "field '_LoginBodyType1MessageText'", TextView.class);
    target._LoginBodyType1EditText = Utils.findRequiredViewAsType(source, R.id.login_search_type1_edit, "field '_LoginBodyType1EditText'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.login_search_type1_ok_button, "field '_LoginBodyType1OkButton' and method 'selectClick'");
    target._LoginBodyType1OkButton = Utils.castView(view, R.id.login_search_type1_ok_button, "field '_LoginBodyType1OkButton'", TextView.class);
    view7f090161 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_search_type1_cancel_button, "field '_LoginBodyType1CancelButton' and method 'selectClick'");
    target._LoginBodyType1CancelButton = Utils.castView(view, R.id.login_search_type1_cancel_button, "field '_LoginBodyType1CancelButton'", TextView.class);
    view7f09015f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._LoginBodyType2Layout = Utils.findRequiredViewAsType(source, R.id.login_type_search_type2_layout, "field '_LoginBodyType2Layout'", ScalableLayout.class);
    target._LoginBodyType2MessageText = Utils.findRequiredViewAsType(source, R.id.login_search_type2_text, "field '_LoginBodyType2MessageText'", TextView.class);
    target._LoginBodyType2FirstEditText = Utils.findRequiredViewAsType(source, R.id.login_search_type2_edit1, "field '_LoginBodyType2FirstEditText'", PaddingDrawableEditText.class);
    target._LoginBodyType2SecondEditText = Utils.findRequiredViewAsType(source, R.id.login_search_type2_edit2, "field '_LoginBodyType2SecondEditText'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.login_search_type2_ok_button, "field '_LoginBodyType2OkButton' and method 'selectClick'");
    target._LoginBodyType2OkButton = Utils.castView(view, R.id.login_search_type2_ok_button, "field '_LoginBodyType2OkButton'", TextView.class);
    view7f090165 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginInfoSearchDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleBaseLayout = null;
    target._TitleLoginLayout = null;
    target._TitleLoginText = null;
    target._TitleSearchLayout = null;
    target._TitleSearchIDText = null;
    target._TitleSearchPasswordText = null;
    target._TitleSearchPointArrowImage = null;
    target._LoginBodyType1Layout = null;
    target._LoginBodyType1MessageText = null;
    target._LoginBodyType1EditText = null;
    target._LoginBodyType1OkButton = null;
    target._LoginBodyType1CancelButton = null;
    target._LoginBodyType2Layout = null;
    target._LoginBodyType2MessageText = null;
    target._LoginBodyType2FirstEditText = null;
    target._LoginBodyType2SecondEditText = null;
    target._LoginBodyType2OkButton = null;

    view7f09015c.setOnClickListener(null);
    view7f09015c = null;
    view7f09015e.setOnClickListener(null);
    view7f09015e = null;
    view7f090161.setOnClickListener(null);
    view7f090161 = null;
    view7f09015f.setOnClickListener(null);
    view7f09015f = null;
    view7f090165.setOnClickListener(null);
    view7f090165 = null;
  }
}

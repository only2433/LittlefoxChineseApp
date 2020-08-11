// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserInformationConvertFragment_ViewBinding implements Unbinder {
  private UserInformationConvertFragment target;

  private View view7f09017c;

  private View view7f090181;

  private View view7f090182;

  private View view7f090187;

  private View view7f090188;

  @UiThread
  public UserInformationConvertFragment_ViewBinding(final UserInformationConvertFragment target,
      View source) {
    this.target = target;

    View view;
    target._RadioTopMarginLayout = Utils.findOptionalViewAsType(source, R.id.top_margin_layout, "field '_RadioTopMarginLayout'", ScalableLayout.class);
    target._InformationModificationLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_info_base_layout, "field '_InformationModificationLayout'", LinearLayout.class);
    target._InformationIDEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_id_edit, "field '_InformationIDEdit'", PaddingDrawableEditText.class);
    target._WarningNicknameText = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_warning_nickname_text, "field '_WarningNicknameText'", SeparateTextView.class);
    target._InformationNicknameEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_nickname_edit, "field '_InformationNicknameEdit'", PaddingDrawableEditText.class);
    target._WarningNameText = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_warning_name_text, "field '_WarningNameText'", TextView.class);
    target._InformationNameEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_name_edit, "field '_InformationNameEdit'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.myinfo_modification_calendar_text, "field '_InformationCalenderEdit' and method 'OnSelectClick'");
    target._InformationCalenderEdit = Utils.castView(view, R.id.myinfo_modification_calendar_text, "field '_InformationCalenderEdit'", PaddingDrawableEditText.class);
    view7f09017c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    target._InformationPhoneEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_phone_text, "field '_InformationPhoneEdit'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.myinfo_modification_info_cancel_button, "field '_InformationCancelButton' and method 'OnSelectClick'");
    target._InformationCancelButton = Utils.castView(view, R.id.myinfo_modification_info_cancel_button, "field '_InformationCancelButton'", TextView.class);
    view7f090181 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.myinfo_modification_info_confirm_button, "field '_InformationConfirmButton' and method 'OnSelectClick'");
    target._InformationConfirmButton = Utils.castView(view, R.id.myinfo_modification_info_confirm_button, "field '_InformationConfirmButton'", TextView.class);
    view7f090182 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    target._PasswordModificationLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_password_base_layout, "field '_PasswordModificationLayout'", LinearLayout.class);
    target._WarningPasswordText = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_warning_password_text, "field '_WarningPasswordText'", TextView.class);
    target._PasswordOriginEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_origin_password_edit, "field '_PasswordOriginEdit'", PaddingDrawableEditText.class);
    target._PasswordConvertEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_convert_password_edit, "field '_PasswordConvertEdit'", PaddingDrawableEditText.class);
    target._PasswordConvertConfirmEdit = Utils.findRequiredViewAsType(source, R.id.myinfo_modification_confirm_password_edit, "field '_PasswordConvertConfirmEdit'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.myinfo_modification_password_cancel_button, "field '_PasswordCancelButton' and method 'OnSelectClick'");
    target._PasswordCancelButton = Utils.castView(view, R.id.myinfo_modification_password_cancel_button, "field '_PasswordCancelButton'", TextView.class);
    view7f090187 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.myinfo_modification_password_confirm_button, "field '_PasswordConfirmButton' and method 'OnSelectClick'");
    target._PasswordConfirmButton = Utils.castView(view, R.id.myinfo_modification_password_confirm_button, "field '_PasswordConfirmButton'", TextView.class);
    view7f090188 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserInformationConvertFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._RadioTopMarginLayout = null;
    target._InformationModificationLayout = null;
    target._InformationIDEdit = null;
    target._WarningNicknameText = null;
    target._InformationNicknameEdit = null;
    target._WarningNameText = null;
    target._InformationNameEdit = null;
    target._InformationCalenderEdit = null;
    target._InformationPhoneEdit = null;
    target._InformationCancelButton = null;
    target._InformationConfirmButton = null;
    target._PasswordModificationLayout = null;
    target._WarningPasswordText = null;
    target._PasswordOriginEdit = null;
    target._PasswordConvertEdit = null;
    target._PasswordConvertConfirmEdit = null;
    target._PasswordCancelButton = null;
    target._PasswordConfirmButton = null;

    view7f09017c.setOnClickListener(null);
    view7f09017c = null;
    view7f090181.setOnClickListener(null);
    view7f090181 = null;
    view7f090182.setOnClickListener(null);
    view7f090182 = null;
    view7f090187.setOnClickListener(null);
    view7f090187 = null;
    view7f090188.setOnClickListener(null);
    view7f090188 = null;
  }
}

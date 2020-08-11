// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
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

public class UserInformationShowFragment_ViewBinding implements Unbinder {
  private UserInformationShowFragment target;

  private View view7f0901bf;

  private View view7f090177;

  private View view7f09017a;

  @UiThread
  public UserInformationShowFragment_ViewBinding(final UserInformationShowFragment target,
      View source) {
    this.target = target;

    View view;
    target._RadioTopMarginLayout = Utils.findOptionalViewAsType(source, R.id.top_margin_layout, "field '_RadioTopMarginLayout'", ScalableLayout.class);
    target._PayCompleteLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_pay_complete_layout, "field '_PayCompleteLayout'", ScalableLayout.class);
    target._PayCompleteTitleText = Utils.findRequiredViewAsType(source, R.id.pay_complete_title_text, "field '_PayCompleteTitleText'", TextView.class);
    target._PayCompleteDayText = Utils.findRequiredViewAsType(source, R.id.pay_complete_day_text, "field '_PayCompleteDayText'", TextView.class);
    target._PayNotYetLayout = Utils.findRequiredViewAsType(source, R.id.myinfo_pay_not_yet_layout, "field '_PayNotYetLayout'", ScalableLayout.class);
    target._PayNotYetTitleText = Utils.findRequiredViewAsType(source, R.id.pay_not_yet_title_text, "field '_PayNotYetTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.pay_enter_button, "field '_PayEnterButton' and method 'onSelectClick'");
    target._PayEnterButton = Utils.castView(view, R.id.pay_enter_button, "field '_PayEnterButton'", TextView.class);
    view7f0901bf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._UserIDEditText = Utils.findRequiredViewAsType(source, R.id.myinfo_id_edit, "field '_UserIDEditText'", PaddingDrawableEditText.class);
    target._UserNicknameEditText = Utils.findRequiredViewAsType(source, R.id.myinfo_nickname_edit, "field '_UserNicknameEditText'", PaddingDrawableEditText.class);
    target._UserNameEditText = Utils.findRequiredViewAsType(source, R.id.myinfo_name_edit, "field '_UserNameEditText'", PaddingDrawableEditText.class);
    target._UserCalendarEditText = Utils.findRequiredViewAsType(source, R.id.myinfo_calendar_text, "field '_UserCalendarEditText'", PaddingDrawableEditText.class);
    target._PhoneEditText = Utils.findRequiredViewAsType(source, R.id.myinfo_phone_text, "field '_PhoneEditText'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.myinfo_change_password_button, "field '_ChangePasswordButton' and method 'onSelectClick'");
    target._ChangePasswordButton = Utils.castView(view, R.id.myinfo_change_password_button, "field '_ChangePasswordButton'", TextView.class);
    view7f090177 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.myinfo_info_modification_button, "field '_InfoModificationButton' and method 'onSelectClick'");
    target._InfoModificationButton = Utils.castView(view, R.id.myinfo_info_modification_button, "field '_InfoModificationButton'", TextView.class);
    view7f09017a = view;
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
    UserInformationShowFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._RadioTopMarginLayout = null;
    target._PayCompleteLayout = null;
    target._PayCompleteTitleText = null;
    target._PayCompleteDayText = null;
    target._PayNotYetLayout = null;
    target._PayNotYetTitleText = null;
    target._PayEnterButton = null;
    target._UserIDEditText = null;
    target._UserNicknameEditText = null;
    target._UserNameEditText = null;
    target._UserCalendarEditText = null;
    target._PhoneEditText = null;
    target._ChangePasswordButton = null;
    target._InfoModificationButton = null;

    view7f0901bf.setOnClickListener(null);
    view7f0901bf = null;
    view7f090177.setOnClickListener(null);
    view7f090177 = null;
    view7f09017a.setOnClickListener(null);
    view7f09017a = null;
  }
}

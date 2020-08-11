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
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddChildInformationConvertFragment_ViewBinding implements Unbinder {
  private AddChildInformationConvertFragment target;

  private View view7f090039;

  private View view7f09003a;

  private View view7f09003e;

  @UiThread
  public AddChildInformationConvertFragment_ViewBinding(
      final AddChildInformationConvertFragment target, View source) {
    this.target = target;

    View view;
    target._RadioTopMarginLayout = Utils.findOptionalViewAsType(source, R.id.top_margin_layout, "field '_RadioTopMarginLayout'", ScalableLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.addchild_convert_child_titile, "field '_TitleText'", TextView.class);
    target._WarningNicknameText = Utils.findRequiredViewAsType(source, R.id.addchild_convert_warning_nickname_text, "field '_WarningNicknameText'", SeparateTextView.class);
    target._NicknameEdit = Utils.findRequiredViewAsType(source, R.id.addchild_convert_nickname_edit, "field '_NicknameEdit'", PaddingDrawableEditText.class);
    target._WarningNameText = Utils.findRequiredViewAsType(source, R.id.addchild_convert_warning_name_text, "field '_WarningNameText'", TextView.class);
    target._NameEdit = Utils.findRequiredViewAsType(source, R.id.addchild_convert_name_edit, "field '_NameEdit'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.addchild_convert_calendar_edit, "field '_CalendarEdit' and method 'OnSelectClick'");
    target._CalendarEdit = Utils.castView(view, R.id.addchild_convert_calendar_edit, "field '_CalendarEdit'", PaddingDrawableEditText.class);
    view7f090039 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.addchild_convert_cancel_button, "field '_CancelButton' and method 'OnSelectClick'");
    target._CancelButton = Utils.castView(view, R.id.addchild_convert_cancel_button, "field '_CancelButton'", TextView.class);
    view7f09003a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.addchild_convert_save_button, "field '_SaveButton' and method 'OnSelectClick'");
    target._SaveButton = Utils.castView(view, R.id.addchild_convert_save_button, "field '_SaveButton'", TextView.class);
    view7f09003e = view;
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
    AddChildInformationConvertFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._RadioTopMarginLayout = null;
    target._TitleText = null;
    target._WarningNicknameText = null;
    target._NicknameEdit = null;
    target._WarningNameText = null;
    target._NameEdit = null;
    target._CalendarEdit = null;
    target._CancelButton = null;
    target._SaveButton = null;

    view7f090039.setOnClickListener(null);
    view7f090039 = null;
    view7f09003a.setOnClickListener(null);
    view7f09003a = null;
    view7f09003e.setOnClickListener(null);
    view7f09003e = null;
  }
}

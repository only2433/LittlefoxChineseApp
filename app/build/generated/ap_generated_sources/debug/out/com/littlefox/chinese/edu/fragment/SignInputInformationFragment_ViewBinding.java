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
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignInputInformationFragment_ViewBinding implements Unbinder {
  private SignInputInformationFragment target;

  private View view7f090233;

  @UiThread
  public SignInputInformationFragment_ViewBinding(final SignInputInformationFragment target,
      View source) {
    this.target = target;

    View view;
    target._InputIDWarningText = Utils.findRequiredViewAsType(source, R.id.sign_warning_id_text, "field '_InputIDWarningText'", TextView.class);
    target._InputIDEditText = Utils.findRequiredViewAsType(source, R.id.sign_id_edit, "field '_InputIDEditText'", PaddingDrawableEditText.class);
    target._InputPasswordWarningText = Utils.findRequiredViewAsType(source, R.id.sign_warning_pw_text, "field '_InputPasswordWarningText'", TextView.class);
    target._InputPasswordEditText = Utils.findRequiredViewAsType(source, R.id.sign_pw_edit, "field '_InputPasswordEditText'", PaddingDrawableEditText.class);
    target._InputPasswordConfirmEditText = Utils.findRequiredViewAsType(source, R.id.sign_pw_confirm_edit, "field '_InputPasswordConfirmEditText'", PaddingDrawableEditText.class);
    target._InputNickNameWarningText = Utils.findRequiredViewAsType(source, R.id.sign_warning_nickname_text, "field '_InputNickNameWarningText'", TextView.class);
    target._InputNicknameEditText = Utils.findRequiredViewAsType(source, R.id.sign_nickname_edit, "field '_InputNicknameEditText'", PaddingDrawableEditText.class);
    view = Utils.findRequiredView(source, R.id.sign_confirm_button, "field '_SignCheckButton' and method 'selectClick'");
    target._SignCheckButton = Utils.castView(view, R.id.sign_confirm_button, "field '_SignCheckButton'", TextView.class);
    view7f090233 = view;
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
    SignInputInformationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._InputIDWarningText = null;
    target._InputIDEditText = null;
    target._InputPasswordWarningText = null;
    target._InputPasswordEditText = null;
    target._InputPasswordConfirmEditText = null;
    target._InputNickNameWarningText = null;
    target._InputNicknameEditText = null;
    target._SignCheckButton = null;

    view7f090233.setOnClickListener(null);
    view7f090233 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.dialog;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserSelectDialog_ViewBinding implements Unbinder {
  private UserSelectDialog target;

  @UiThread
  public UserSelectDialog_ViewBinding(UserSelectDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserSelectDialog_ViewBinding(UserSelectDialog target, View source) {
    this.target = target;

    target._BaseUserLayout = Utils.findRequiredViewAsType(source, R.id.user_select_base_layout, "field '_BaseUserLayout'", ScalableLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.user_select_title, "field '_TitleText'", TextView.class);
    target._AddListLayout = Utils.findRequiredViewAsType(source, R.id.add_list_layout, "field '_AddListLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserSelectDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseUserLayout = null;
    target._TitleText = null;
    target._AddListLayout = null;
  }
}

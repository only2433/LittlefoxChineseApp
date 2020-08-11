// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroduceContentFragment_ViewBinding implements Unbinder {
  private IntroduceContentFragment target;

  @UiThread
  public IntroduceContentFragment_ViewBinding(IntroduceContentFragment target, View source) {
    this.target = target;

    target._MainTitleText = Utils.findRequiredViewAsType(source, R.id.introduce_title_text, "field '_MainTitleText'", TextView.class);
    target._MainMessageText = Utils.findRequiredViewAsType(source, R.id.introduce_message_text, "field '_MainMessageText'", TextView.class);
    target._MainImage = Utils.findRequiredViewAsType(source, R.id.introduce_image, "field '_MainImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IntroduceContentFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._MainTitleText = null;
    target._MainMessageText = null;
    target._MainImage = null;
  }
}

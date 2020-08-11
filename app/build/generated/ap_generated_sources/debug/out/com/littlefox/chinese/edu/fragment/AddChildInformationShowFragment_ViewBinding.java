// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddChildInformationShowFragment_ViewBinding implements Unbinder {
  private AddChildInformationShowFragment target;

  private View view7f090033;

  @UiThread
  public AddChildInformationShowFragment_ViewBinding(final AddChildInformationShowFragment target,
      View source) {
    this.target = target;

    View view;
    target._TitleBaseLayout = Utils.findRequiredViewAsType(source, R.id.addchild_title_layout, "field '_TitleBaseLayout'", ScalableLayout.class);
    target._InformationTitleText = Utils.findRequiredViewAsType(source, R.id.addchild_message_text, "field '_InformationTitleText'", TextView.class);
    target._ScrollView = Utils.findRequiredViewAsType(source, R.id.addchild_scrollview, "field '_ScrollView'", ScrollView.class);
    target._AddChildBaseViewLayout = Utils.findRequiredViewAsType(source, R.id.addchild_base_layout, "field '_AddChildBaseViewLayout'", LinearLayout.class);
    target._AddChildAddBaseLayout = Utils.findRequiredViewAsType(source, R.id.addchild_add_button_base_layout, "field '_AddChildAddBaseLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.addchild_add_button, "field '_AddChildAddButton' and method 'OnSelectClick'");
    target._AddChildAddButton = Utils.castView(view, R.id.addchild_add_button, "field '_AddChildAddButton'", ImageView.class);
    view7f090033 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnSelectClick(p0);
      }
    });
    target._AddTitleText = Utils.findRequiredViewAsType(source, R.id.addchild_add_text, "field '_AddTitleText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddChildInformationShowFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._TitleBaseLayout = null;
    target._InformationTitleText = null;
    target._ScrollView = null;
    target._AddChildBaseViewLayout = null;
    target._AddChildAddBaseLayout = null;
    target._AddChildAddButton = null;
    target._AddTitleText = null;

    view7f090033.setOnClickListener(null);
    view7f090033 = null;
  }
}

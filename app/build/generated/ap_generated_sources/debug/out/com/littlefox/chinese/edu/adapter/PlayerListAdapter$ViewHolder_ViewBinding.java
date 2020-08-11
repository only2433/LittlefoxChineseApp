// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayerListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PlayerListAdapter.ViewHolder target;

  @UiThread
  public PlayerListAdapter$ViewHolder_ViewBinding(PlayerListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target._ItemBaseLayout = Utils.findRequiredViewAsType(source, R.id._itemBaseLayout, "field '_ItemBaseLayout'", ScalableLayout.class);
    target._ItemBackground = Utils.findRequiredViewAsType(source, R.id._itemBackground, "field '_ItemBackground'", ImageView.class);
    target._ItemTitleImage = Utils.findRequiredViewAsType(source, R.id._itemTitleImage, "field '_ItemTitleImage'", ImageView.class);
    target._ItemTitleText = Utils.findRequiredViewAsType(source, R.id._itemTitleText, "field '_ItemTitleText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._ItemBaseLayout = null;
    target._ItemBackground = null;
    target._ItemTitleImage = null;
    target._ItemTitleText = null;
  }
}

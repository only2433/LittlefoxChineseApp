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

public class PlayerSpeedListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PlayerSpeedListAdapter.ViewHolder target;

  @UiThread
  public PlayerSpeedListAdapter$ViewHolder_ViewBinding(PlayerSpeedListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target._ItemBaseLayout = Utils.findRequiredViewAsType(source, R.id._itemBaseLayout, "field '_ItemBaseLayout'", ScalableLayout.class);
    target._SelectIcon = Utils.findRequiredViewAsType(source, R.id._selectIcon, "field '_SelectIcon'", ImageView.class);
    target._SelectSpeedText = Utils.findRequiredViewAsType(source, R.id._selectSpeedText, "field '_SelectSpeedText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerSpeedListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._ItemBaseLayout = null;
    target._SelectIcon = null;
    target._SelectSpeedText = null;
  }
}

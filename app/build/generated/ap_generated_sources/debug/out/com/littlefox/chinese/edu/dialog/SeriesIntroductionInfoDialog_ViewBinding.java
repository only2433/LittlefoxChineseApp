// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.dialog;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SeriesIntroductionInfoDialog_ViewBinding implements Unbinder {
  private SeriesIntroductionInfoDialog target;

  @UiThread
  public SeriesIntroductionInfoDialog_ViewBinding(SeriesIntroductionInfoDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SeriesIntroductionInfoDialog_ViewBinding(SeriesIntroductionInfoDialog target,
      View source) {
    this.target = target;

    target._ProgressLayout = Utils.findRequiredViewAsType(source, R.id.progress_wheel_layout, "field '_ProgressLayout'", ScalableLayout.class);
    target._ItemBaseLayout = Utils.findRequiredViewAsType(source, R.id.info_item_base_layout, "field '_ItemBaseLayout'", ScalableLayout.class);
    target._ItemPeopleLayout = Utils.findRequiredViewAsType(source, R.id.info_item_people_layout, "field '_ItemPeopleLayout'", ScalableLayout.class);
    target._TitleSeparateView = Utils.findRequiredViewAsType(source, R.id.info_title_text, "field '_TitleSeparateView'", SeparateTextView.class);
    target._SeriesLevelTextView = Utils.findRequiredViewAsType(source, R.id.info_series_level, "field '_SeriesLevelTextView'", TextView.class);
    target._IntroductionTextView = Utils.findRequiredViewAsType(source, R.id.info_series_introduction, "field '_IntroductionTextView'", TextView.class);
    target._HorizontalScrollView = Utils.findRequiredViewAsType(source, R.id.horizontal_scroll_base, "field '_HorizontalScrollView'", HorizontalScrollView.class);
    target._AddPeopleLayout = Utils.findRequiredViewAsType(source, R.id.add_info_people_laytout, "field '_AddPeopleLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SeriesIntroductionInfoDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._ProgressLayout = null;
    target._ItemBaseLayout = null;
    target._ItemPeopleLayout = null;
    target._TitleSeparateView = null;
    target._SeriesLevelTextView = null;
    target._IntroductionTextView = null;
    target._HorizontalScrollView = null;
    target._AddPeopleLayout = null;
  }
}

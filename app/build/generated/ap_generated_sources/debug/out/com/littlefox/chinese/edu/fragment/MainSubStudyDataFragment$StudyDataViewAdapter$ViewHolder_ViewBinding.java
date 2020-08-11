// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
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

public class MainSubStudyDataFragment$StudyDataViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MainSubStudyDataFragment.StudyDataViewAdapter.ViewHolder target;

  @UiThread
  public MainSubStudyDataFragment$StudyDataViewAdapter$ViewHolder_ViewBinding(
      MainSubStudyDataFragment.StudyDataViewAdapter.ViewHolder target, View source) {
    this.target = target;

    target._BaseItemLayout = Utils.findRequiredViewAsType(source, R.id.data_list_item_base_layout, "field '_BaseItemLayout'", ScalableLayout.class);
    target._ItemThumbnailImage = Utils.findRequiredViewAsType(source, R.id.data_list_item_thumbnail, "field '_ItemThumbnailImage'", ImageView.class);
    target._ItemClickTerritory = Utils.findRequiredViewAsType(source, R.id.data_list_item_click_territory, "field '_ItemClickTerritory'", ImageView.class);
    target._ItemTitleText = Utils.findRequiredViewAsType(source, R.id.data_list_item_title, "field '_ItemTitleText'", TextView.class);
    target._ItemQuizButton = Utils.findRequiredViewAsType(source, R.id.data_quiz_button, "field '_ItemQuizButton'", ImageView.class);
    target._SectionLayout = Utils.findRequiredViewAsType(source, R.id.base_item_section_layout, "field '_SectionLayout'", LinearLayout.class);
    target._SectionLineLayout = Utils.findRequiredViewAsType(source, R.id.base_item_section_line_layout, "field '_SectionLineLayout'", ScalableLayout.class);
    target._SectionTitleText = Utils.findRequiredViewAsType(source, R.id.item_section_title, "field '_SectionTitleText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubStudyDataFragment.StudyDataViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseItemLayout = null;
    target._ItemThumbnailImage = null;
    target._ItemClickTerritory = null;
    target._ItemTitleText = null;
    target._ItemQuizButton = null;
    target._SectionLayout = null;
    target._SectionLineLayout = null;
    target._SectionTitleText = null;
  }
}

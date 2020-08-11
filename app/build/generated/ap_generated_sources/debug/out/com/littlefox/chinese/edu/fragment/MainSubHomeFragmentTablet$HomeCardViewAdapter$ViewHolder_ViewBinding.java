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

public class MainSubHomeFragmentTablet$HomeCardViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MainSubHomeFragmentTablet.HomeCardViewAdapter.ViewHolder target;

  @UiThread
  public MainSubHomeFragmentTablet$HomeCardViewAdapter$ViewHolder_ViewBinding(
      MainSubHomeFragmentTablet.HomeCardViewAdapter.ViewHolder target, View source) {
    this.target = target;

    target._BaseItemLayout = Utils.findRequiredViewAsType(source, R.id.base_item_layout, "field '_BaseItemLayout'", ScalableLayout.class);
    target._ThumbnailImage = Utils.findRequiredViewAsType(source, R.id.home_item_card_thumbnail, "field '_ThumbnailImage'", ImageView.class);
    target._RecommandTitleText = Utils.findRequiredViewAsType(source, R.id.home_item_card_recommand_title, "field '_RecommandTitleText'", TextView.class);
    target._WeekTitleText = Utils.findRequiredViewAsType(source, R.id.home_item_section_week, "field '_WeekTitleText'", TextView.class);
    target._BaseSectionLayout = Utils.findRequiredViewAsType(source, R.id.base_item_section_layout, "field '_BaseSectionLayout'", LinearLayout.class);
    target._SectionDivideLineLayout = Utils.findRequiredViewAsType(source, R.id.home_item_section_line_layout, "field '_SectionDivideLineLayout'", ScalableLayout.class);
    target._SectionTitleText = Utils.findRequiredViewAsType(source, R.id.home_item_section_title, "field '_SectionTitleText'", TextView.class);
    target._BaseAutobiographyLayout = Utils.findRequiredViewAsType(source, R.id.base_autobiography_layout, "field '_BaseAutobiographyLayout'", ScalableLayout.class);
    target._AutobiographyThumbnailImage = Utils.findRequiredViewAsType(source, R.id.home_item_autobiography_thumbnail, "field '_AutobiographyThumbnailImage'", ImageView.class);
    target._AutobiographyTitleText = Utils.findRequiredViewAsType(source, R.id.home_item_autobiography_title_text, "field '_AutobiographyTitleText'", TextView.class);
    target._AutobiographyNameText = Utils.findRequiredViewAsType(source, R.id.home_item_autobiography_name_text, "field '_AutobiographyNameText'", TextView.class);
    target._AutobiographyButton = Utils.findRequiredViewAsType(source, R.id.home_button_autobiography, "field '_AutobiographyButton'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubHomeFragmentTablet.HomeCardViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseItemLayout = null;
    target._ThumbnailImage = null;
    target._RecommandTitleText = null;
    target._WeekTitleText = null;
    target._BaseSectionLayout = null;
    target._SectionDivideLineLayout = null;
    target._SectionTitleText = null;
    target._BaseAutobiographyLayout = null;
    target._AutobiographyThumbnailImage = null;
    target._AutobiographyTitleText = null;
    target._AutobiographyNameText = null;
    target._AutobiographyButton = null;
  }
}

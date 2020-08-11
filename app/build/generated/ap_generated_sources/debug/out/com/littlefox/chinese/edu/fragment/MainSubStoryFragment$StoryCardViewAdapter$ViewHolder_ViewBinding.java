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

public class MainSubStoryFragment$StoryCardViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MainSubStoryFragment.StoryCardViewAdapter.ViewHolder target;

  @UiThread
  public MainSubStoryFragment$StoryCardViewAdapter$ViewHolder_ViewBinding(
      MainSubStoryFragment.StoryCardViewAdapter.ViewHolder target, View source) {
    this.target = target;

    target._SectionLayout = Utils.findRequiredViewAsType(source, R.id.base_item_section_layout, "field '_SectionLayout'", LinearLayout.class);
    target._SectionLineLayout = Utils.findRequiredViewAsType(source, R.id.base_item_section_line_layout, "field '_SectionLineLayout'", ScalableLayout.class);
    target._ItemLayout = Utils.findRequiredViewAsType(source, R.id.base_item_layout, "field '_ItemLayout'", ScalableLayout.class);
    target._ThumbnailImage = Utils.findRequiredViewAsType(source, R.id.item_story_card_thumbnail, "field '_ThumbnailImage'", ImageView.class);
    target._FavoriteIcon = Utils.findRequiredViewAsType(source, R.id.item_story_card_favorite, "field '_FavoriteIcon'", ImageView.class);
    target._FavoriteClickView = Utils.findRequiredViewAsType(source, R.id.item_story_card_add_favorite, "field '_FavoriteClickView'", ImageView.class);
    target._SectionTitle = Utils.findRequiredViewAsType(source, R.id.item_section_title, "field '_SectionTitle'", TextView.class);
    target._LevelText = Utils.findRequiredViewAsType(source, R.id.item_story_card_level, "field '_LevelText'", TextView.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.item_story_card_title, "field '_TitleText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubStoryFragment.StoryCardViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._SectionLayout = null;
    target._SectionLineLayout = null;
    target._ItemLayout = null;
    target._ThumbnailImage = null;
    target._FavoriteIcon = null;
    target._FavoriteClickView = null;
    target._SectionTitle = null;
    target._LevelText = null;
    target._TitleText = null;
  }
}

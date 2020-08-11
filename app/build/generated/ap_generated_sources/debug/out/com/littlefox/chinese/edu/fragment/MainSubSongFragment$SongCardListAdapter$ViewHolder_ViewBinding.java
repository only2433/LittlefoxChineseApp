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
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubSongFragment$SongCardListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MainSubSongFragment.SongCardListAdapter.ViewHolder target;

  @UiThread
  public MainSubSongFragment$SongCardListAdapter$ViewHolder_ViewBinding(
      MainSubSongFragment.SongCardListAdapter.ViewHolder target, View source) {
    this.target = target;

    target._SectionLayout = Utils.findRequiredViewAsType(source, R.id.song_item_section_layout, "field '_SectionLayout'", ScalableLayout.class);
    target._ItemBaseLayout = Utils.findRequiredViewAsType(source, R.id.song_list_item_base_layout, "field '_ItemBaseLayout'", ScalableLayout.class);
    target._FooterLayout = Utils.findRequiredViewAsType(source, R.id.song_item_footer_layout, "field '_FooterLayout'", ScalableLayout.class);
    target._SectionTitleText = Utils.findRequiredViewAsType(source, R.id.item_section_title, "field '_SectionTitleText'", TextView.class);
    target._ItemTitleText = Utils.findRequiredViewAsType(source, R.id.song_list_item_title, "field '_ItemTitleText'", TextView.class);
    target._ItemSubTitleText = Utils.findRequiredViewAsType(source, R.id.song_list_item_content_text, "field '_ItemSubTitleText'", TextView.class);
    target._ItemCheckButton = Utils.findRequiredViewAsType(source, R.id.song_list_item_check, "field '_ItemCheckButton'", ImageView.class);
    target._ItemThumnailImage = Utils.findRequiredViewAsType(source, R.id.song_list_item_thumbnail, "field '_ItemThumnailImage'", ImageView.class);
    target._ItemClickTerritory = Utils.findRequiredViewAsType(source, R.id.song_list_item_click_territory, "field '_ItemClickTerritory'", ImageView.class);
    target._ItemThumnailComingSoonCover = Utils.findRequiredViewAsType(source, R.id.song_list_item_cover, "field '_ItemThumnailComingSoonCover'", ImageView.class);
    target._ItemOriginButton = Utils.findRequiredViewAsType(source, R.id.song_list_item_origin_text_button, "field '_ItemOriginButton'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubSongFragment.SongCardListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._SectionLayout = null;
    target._ItemBaseLayout = null;
    target._FooterLayout = null;
    target._SectionTitleText = null;
    target._ItemTitleText = null;
    target._ItemSubTitleText = null;
    target._ItemCheckButton = null;
    target._ItemThumnailImage = null;
    target._ItemClickTerritory = null;
    target._ItemThumnailComingSoonCover = null;
    target._ItemOriginButton = null;
  }
}

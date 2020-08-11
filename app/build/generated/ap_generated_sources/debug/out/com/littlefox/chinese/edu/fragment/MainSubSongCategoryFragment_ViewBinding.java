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
import com.littlefox.library.view.text.SeparateTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubSongCategoryFragment_ViewBinding implements Unbinder {
  private MainSubSongCategoryFragment target;

  @UiThread
  public MainSubSongCategoryFragment_ViewBinding(MainSubSongCategoryFragment target, View source) {
    this.target = target;

    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.song_category_base_layout, "field '_BaseLayout'", LinearLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.song_category_title, "field '_TitleText'", TextView.class);
    target._SongImageList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.song_category_image_main, "field '_SongImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.song_category_image_sub1, "field '_SongImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.song_category_image_sub2, "field '_SongImageList'", ImageView.class));
    target._SongOriginButtonList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.button_song_category_main_origin, "field '_SongOriginButtonList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.button_song_category_sub1_origin, "field '_SongOriginButtonList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.button_song_category_sub2_origin, "field '_SongOriginButtonList'", ImageView.class));
    target._MainSongTextList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.song_category_text_main, "field '_MainSongTextList'", SeparateTextView.class), 
        Utils.findRequiredViewAsType(source, R.id.song_category_text_sub1, "field '_MainSongTextList'", SeparateTextView.class), 
        Utils.findRequiredViewAsType(source, R.id.song_category_text_sub2, "field '_MainSongTextList'", SeparateTextView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubSongCategoryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseLayout = null;
    target._TitleText = null;
    target._SongImageList = null;
    target._SongOriginButtonList = null;
    target._MainSongTextList = null;
  }
}

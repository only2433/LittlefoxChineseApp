// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubSongFragment_ViewBinding implements Unbinder {
  private MainSubSongFragment target;

  @UiThread
  public MainSubSongFragment_ViewBinding(MainSubSongFragment target, View source) {
    this.target = target;

    target._SongCardRecyclerView = Utils.findRequiredViewAsType(source, R.id.song_card_list_view, "field '_SongCardRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubSongFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._SongCardRecyclerView = null;
  }
}

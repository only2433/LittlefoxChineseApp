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

public class MainSubStoryFragmentTablet_ViewBinding implements Unbinder {
  private MainSubStoryFragmentTablet target;

  @UiThread
  public MainSubStoryFragmentTablet_ViewBinding(MainSubStoryFragmentTablet target, View source) {
    this.target = target;

    target._StoryCardRecycleView = Utils.findRequiredViewAsType(source, R.id.story_card_list_view, "field '_StoryCardRecycleView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubStoryFragmentTablet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._StoryCardRecycleView = null;
  }
}

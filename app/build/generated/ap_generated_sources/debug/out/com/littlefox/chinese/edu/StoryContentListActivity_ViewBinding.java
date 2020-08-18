// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StoryContentListActivity_ViewBinding implements Unbinder {
  private StoryContentListActivity target;

  private View view7f09027a;

  private View view7f09027d;

  @UiThread
  public StoryContentListActivity_ViewBinding(StoryContentListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StoryContentListActivity_ViewBinding(final StoryContentListActivity target, View source) {
    this.target = target;

    View view;
    target._MainBaseLayout = Utils.findRequiredViewAsType(source, R.id.main_content, "field '_MainBaseLayout'", CoordinatorLayout.class);
    target._AppBarLayout = Utils.findRequiredViewAsType(source, R.id.study_detail_appbar, "field '_AppBarLayout'", AppBarLayout.class);
    target._CollapsingToolbarLayout = Utils.findRequiredViewAsType(source, R.id.study_detail_collapsing_toolbar, "field '_CollapsingToolbarLayout'", CollapsingToolbarLayout.class);
    target._StudyContentListMainImageView = Utils.findRequiredViewAsType(source, R.id.study_detail_back_image, "field '_StudyContentListMainImageView'", ImageView.class);
    target._Toolbar = Utils.findRequiredViewAsType(source, R.id.study_detail_toolbar, "field '_Toolbar'", Toolbar.class);
    target._StudyContentListView = Utils.findRequiredViewAsType(source, R.id.story_detail_list, "field '_StudyContentListView'", RecyclerView.class);
    target._ChoicePlayButtonLayout = Utils.findRequiredViewAsType(source, R.id.story_detail_choice_button_layout, "field '_ChoicePlayButtonLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.story_detail_first_button, "field '_ChoiceFirstButton' and method 'onItemSelected'");
    target._ChoiceFirstButton = Utils.castView(view, R.id.story_detail_first_button, "field '_ChoiceFirstButton'", TextView.class);
    view7f09027a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.story_detail_second_button, "field '_ChoiceSecondButton' and method 'onItemSelected'");
    target._ChoiceSecondButton = Utils.castView(view, R.id.story_detail_second_button, "field '_ChoiceSecondButton'", TextView.class);
    view7f09027d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemSelected(p0);
      }
    });
    target._ProgressWheelLayout = Utils.findRequiredViewAsType(source, R.id.progress_wheel_layout, "field '_ProgressWheelLayout'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoryContentListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._MainBaseLayout = null;
    target._AppBarLayout = null;
    target._CollapsingToolbarLayout = null;
    target._StudyContentListMainImageView = null;
    target._Toolbar = null;
    target._StudyContentListView = null;
    target._ChoicePlayButtonLayout = null;
    target._ChoiceFirstButton = null;
    target._ChoiceSecondButton = null;
    target._ProgressWheelLayout = null;

    view7f09027a.setOnClickListener(null);
    view7f09027a = null;
    view7f09027d.setOnClickListener(null);
    view7f09027d = null;
  }
}

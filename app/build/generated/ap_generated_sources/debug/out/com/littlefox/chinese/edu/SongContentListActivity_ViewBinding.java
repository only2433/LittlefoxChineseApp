// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SongContentListActivity_ViewBinding implements Unbinder {
  private SongContentListActivity target;

  private View view7f090249;

  private View view7f09024d;

  private View view7f090251;

  @UiThread
  public SongContentListActivity_ViewBinding(SongContentListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SongContentListActivity_ViewBinding(final SongContentListActivity target, View source) {
    this.target = target;

    View view;
    target._MainContentBaseLayout = Utils.findRequiredViewAsType(source, R.id.main_content, "field '_MainContentBaseLayout'", CoordinatorLayout.class);
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.title_base_layout, "field '_BaseLayout'", ScalableLayout.class);
    target._TitleText = Utils.findRequiredViewAsType(source, R.id.song_content_list_title, "field '_TitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.song_content_list_close, "field '_CloseButton' and method 'onSelectClick'");
    target._CloseButton = Utils.castView(view, R.id.song_content_list_close, "field '_CloseButton'", ImageView.class);
    view7f090249 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._DescriptionText = Utils.findRequiredViewAsType(source, R.id.song_content_list_description, "field '_DescriptionText'", TextView.class);
    target._SongListView = Utils.findRequiredViewAsType(source, R.id.song_detail_list, "field '_SongListView'", RecyclerView.class);
    target._ProgressWheelLayout = Utils.findRequiredViewAsType(source, R.id.progress_wheel_layout, "field '_ProgressWheelLayout'", ScalableLayout.class);
    target._BottomButtonLayout = Utils.findRequiredViewAsType(source, R.id.song_detail_choice_button_layout, "field '_BottomButtonLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.song_detail_first_button, "field '_BottomFirstButton' and method 'onItemSelected'");
    target._BottomFirstButton = Utils.castView(view, R.id.song_detail_first_button, "field '_BottomFirstButton'", TextView.class);
    view7f09024d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.song_detail_second_button, "field '_BottomSecondButton' and method 'onItemSelected'");
    target._BottomSecondButton = Utils.castView(view, R.id.song_detail_second_button, "field '_BottomSecondButton'", TextView.class);
    view7f090251 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemSelected(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SongContentListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._MainContentBaseLayout = null;
    target._BaseLayout = null;
    target._TitleText = null;
    target._CloseButton = null;
    target._DescriptionText = null;
    target._SongListView = null;
    target._ProgressWheelLayout = null;
    target._BottomButtonLayout = null;
    target._BottomFirstButton = null;
    target._BottomSecondButton = null;

    view7f090249.setOnClickListener(null);
    view7f090249 = null;
    view7f09024d.setOnClickListener(null);
    view7f09024d = null;
    view7f090251.setOnClickListener(null);
    view7f090251 = null;
  }
}

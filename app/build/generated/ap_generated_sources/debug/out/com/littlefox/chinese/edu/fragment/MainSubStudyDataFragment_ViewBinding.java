// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubStudyDataFragment_ViewBinding implements Unbinder {
  private MainSubStudyDataFragment target;

  private View view7f090283;

  private View view7f090285;

  private View view7f090287;

  @UiThread
  public MainSubStudyDataFragment_ViewBinding(final MainSubStudyDataFragment target, View source) {
    this.target = target;

    View view;
    target._RecyclerView = Utils.findRequiredViewAsType(source, R.id.study_data_card_list_view, "field '_RecyclerView'", RecyclerView.class);
    target._ChoiceButtonLayout = Utils.findRequiredViewAsType(source, R.id.study_data_choice_button_layout, "field '_ChoiceButtonLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.study_data_first_button, "method 'OnChangeTab'");
    view7f090283 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnChangeTab(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.study_data_second_button, "method 'OnChangeTab'");
    view7f090285 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnChangeTab(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.study_data_third_button, "method 'OnChangeTab'");
    view7f090287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnChangeTab(p0);
      }
    });
    target._StudyDataTextViewList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.study_data_first_text, "field '_StudyDataTextViewList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.study_data_second_text, "field '_StudyDataTextViewList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.study_data_third_text, "field '_StudyDataTextViewList'", TextView.class));
    target._StudyDataButtonList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.study_data_first_button, "field '_StudyDataButtonList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.study_data_second_button, "field '_StudyDataButtonList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.study_data_third_button, "field '_StudyDataButtonList'", ImageView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubStudyDataFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._RecyclerView = null;
    target._ChoiceButtonLayout = null;
    target._StudyDataTextViewList = null;
    target._StudyDataButtonList = null;

    view7f090283.setOnClickListener(null);
    view7f090283 = null;
    view7f090285.setOnClickListener(null);
    view7f090285 = null;
    view7f090287.setOnClickListener(null);
    view7f090287 = null;
  }
}

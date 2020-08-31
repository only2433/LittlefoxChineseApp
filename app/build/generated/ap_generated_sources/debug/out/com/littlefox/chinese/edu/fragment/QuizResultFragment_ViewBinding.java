// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuizResultFragment_ViewBinding implements Unbinder {
  private QuizResultFragment target;

  private View view7f09020c;

  private View view7f090208;

  @UiThread
  public QuizResultFragment_ViewBinding(final QuizResultFragment target, View source) {
    this.target = target;

    View view;
    target._CorrectIconImage = Utils.findRequiredViewAsType(source, R.id.quiz_title_correct_image, "field '_CorrectIconImage'", ImageView.class);
    target._CorrectCountText = Utils.findRequiredViewAsType(source, R.id.quiz_result_correct_text, "field '_CorrectCountText'", TextView.class);
    target._InCorrectCountText = Utils.findRequiredViewAsType(source, R.id.quiz_result_incorrect_text, "field '_InCorrectCountText'", TextView.class);
    target._QuizResultImage = Utils.findRequiredViewAsType(source, R.id.quiz_result_image, "field '_QuizResultImage'", ImageView.class);
    target._QuizCorrectTitleText = Utils.findRequiredViewAsType(source, R.id.quiz_title_correct_text, "field '_QuizCorrectTitleText'", TextView.class);
    target._QuizInCorrectTitleText = Utils.findRequiredViewAsType(source, R.id.quiz_title_incorrect_text, "field '_QuizInCorrectTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.quiz_save_button, "method 'onSelectClick'");
    view7f09020c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.quiz_replay_button, "method 'onSelectClick'");
    view7f090208 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    QuizResultFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._CorrectIconImage = null;
    target._CorrectCountText = null;
    target._InCorrectCountText = null;
    target._QuizResultImage = null;
    target._QuizCorrectTitleText = null;
    target._QuizInCorrectTitleText = null;

    view7f09020c.setOnClickListener(null);
    view7f09020c = null;
    view7f090208.setOnClickListener(null);
    view7f090208 = null;
  }
}

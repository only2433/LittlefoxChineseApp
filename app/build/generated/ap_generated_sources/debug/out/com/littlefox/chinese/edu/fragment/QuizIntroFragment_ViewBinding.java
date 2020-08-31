// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu.fragment;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.chinese.edu.R;
import com.littlefox.library.view.dialog.ProgressWheel;
import com.littlefox.library.view.text.SeparateTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuizIntroFragment_ViewBinding implements Unbinder {
  private QuizIntroFragment target;

  private View view7f090206;

  @UiThread
  public QuizIntroFragment_ViewBinding(final QuizIntroFragment target, View source) {
    this.target = target;

    View view;
    target._MainTitleText = Utils.findRequiredViewAsType(source, R.id.quiz_main_title_text, "field '_MainTitleText'", SeparateTextView.class);
    target._LoadingLayout = Utils.findRequiredViewAsType(source, R.id.quiz_intro_loading_layout, "field '_LoadingLayout'", ProgressWheel.class);
    view = Utils.findRequiredView(source, R.id.quiz_intro_play_button, "field '_QuizPlayButton' and method 'onSelectClick'");
    target._QuizPlayButton = Utils.castView(view, R.id.quiz_intro_play_button, "field '_QuizPlayButton'", ImageView.class);
    view7f090206 = view;
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
    QuizIntroFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._MainTitleText = null;
    target._LoadingLayout = null;
    target._QuizPlayButton = null;

    view7f090206.setOnClickListener(null);
    view7f090206 = null;
  }
}

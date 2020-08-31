// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.littlefox.library.view.extra.SwipeDisableViewPager;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuizActivity_ViewBinding implements Unbinder {
  private QuizActivity target;

  private View view7f090207;

  @UiThread
  public QuizActivity_ViewBinding(QuizActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QuizActivity_ViewBinding(final QuizActivity target, View source) {
    this.target = target;

    View view;
    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.main_layout, "field '_BaseLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.quiz_close_button, "field '_QuizCloseButton' and method 'onSelectClick'");
    target._QuizCloseButton = Utils.castView(view, R.id.quiz_close_button, "field '_QuizCloseButton'", ImageView.class);
    view7f090207 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._QuizTaskBoxLayout = Utils.findRequiredViewAsType(source, R.id.quiz_task_box_layout, "field '_QuizTaskBoxLayout'", ScalableLayout.class);
    target._QuizQuestionImage = Utils.findRequiredViewAsType(source, R.id.quiz_task_question_image, "field '_QuizQuestionImage'", ImageView.class);
    target._QuizTimerText = Utils.findRequiredViewAsType(source, R.id.quiz_timer_text, "field '_QuizTimerText'", TextView.class);
    target._QuizAnswerCountText = Utils.findRequiredViewAsType(source, R.id.quiz_count_text, "field '_QuizAnswerCountText'", TextView.class);
    target._QuizDisplayPager = Utils.findRequiredViewAsType(source, R.id.quiz_base_fragment, "field '_QuizDisplayPager'", SwipeDisableViewPager.class);
    target._AniAnswerView = Utils.findRequiredViewAsType(source, R.id.quiz_ani_icon, "field '_AniAnswerView'", ImageView.class);
    target._QuizTitleText = Utils.findRequiredViewAsType(source, R.id.quiz_title, "field '_QuizTitleText'", TextView.class);
    target._AniAnswerLayout = Utils.findRequiredViewAsType(source, R.id.quiz_ani_layout, "field '_AniAnswerLayout'", ScalableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QuizActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseLayout = null;
    target._QuizCloseButton = null;
    target._QuizTaskBoxLayout = null;
    target._QuizQuestionImage = null;
    target._QuizTimerText = null;
    target._QuizAnswerCountText = null;
    target._QuizDisplayPager = null;
    target._AniAnswerView = null;
    target._QuizTitleText = null;
    target._AniAnswerLayout = null;

    view7f090207.setOnClickListener(null);
    view7f090207 = null;
  }
}

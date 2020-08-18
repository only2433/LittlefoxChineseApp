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
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuizPlayFragment_ViewBinding implements Unbinder {
  private QuizPlayFragment target;

  private View view7f0901fe;

  private View view7f09011d;

  private View view7f09011f;

  private View view7f0901fd;

  @UiThread
  public QuizPlayFragment_ViewBinding(final QuizPlayFragment target, View source) {
    this.target = target;

    View view;
    target._QuestionIndexText = Utils.findRequiredViewAsType(source, R.id.question_index_text, "field '_QuestionIndexText'", TextView.class);
    target._QuestionTitleText = Utils.findRequiredViewAsType(source, R.id.question_title_text, "field '_QuestionTitleText'", TextView.class);
    target._PictureQuestionTypeLayout = Utils.findRequiredViewAsType(source, R.id.question_image_layout, "field '_PictureQuestionTypeLayout'", ScalableLayout.class);
    target._TextQuestionTypeLayout = Utils.findRequiredViewAsType(source, R.id.question_text_layout, "field '_TextQuestionTypeLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.question_play_button, "field '_PlaySoundButton' and method 'onSelectImage'");
    target._PlaySoundButton = Utils.castView(view, R.id.question_play_button, "field '_PlaySoundButton'", ImageView.class);
    view7f0901fe = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectImage(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.image_index_first_image, "field '_FirstPictureImage' and method 'onSelectImage'");
    target._FirstPictureImage = Utils.castView(view, R.id.image_index_first_image, "field '_FirstPictureImage'", ImageView.class);
    view7f09011d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectImage(p0);
      }
    });
    target._FirstNotSelectImage = Utils.findRequiredViewAsType(source, R.id.image_index_first_not_select_image, "field '_FirstNotSelectImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.image_index_second_image, "field '_SecondPictureImage' and method 'onSelectImage'");
    target._SecondPictureImage = Utils.castView(view, R.id.image_index_second_image, "field '_SecondPictureImage'", ImageView.class);
    view7f09011f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectImage(p0);
      }
    });
    target._SecondNotSelectImage = Utils.findRequiredViewAsType(source, R.id.image_index_second_not_select_image, "field '_SecondNotSelectImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.question_next_button, "field '_NextPlayButton' and method 'onSelectImage'");
    target._NextPlayButton = Utils.castView(view, R.id.question_next_button, "field '_NextPlayButton'", ImageView.class);
    view7f0901fd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectImage(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    QuizPlayFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._QuestionIndexText = null;
    target._QuestionTitleText = null;
    target._PictureQuestionTypeLayout = null;
    target._TextQuestionTypeLayout = null;
    target._PlaySoundButton = null;
    target._FirstPictureImage = null;
    target._FirstNotSelectImage = null;
    target._SecondPictureImage = null;
    target._SecondNotSelectImage = null;
    target._NextPlayButton = null;

    view7f0901fe.setOnClickListener(null);
    view7f0901fe = null;
    view7f09011d.setOnClickListener(null);
    view7f09011d = null;
    view7f09011f.setOnClickListener(null);
    view7f09011f = null;
    view7f0901fd.setOnClickListener(null);
    view7f0901fd = null;
  }
}
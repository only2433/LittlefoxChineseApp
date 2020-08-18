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
import com.littlefox.chinese.edu.view.BannerLinkView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubHomeFragment_ViewBinding implements Unbinder {
  private MainSubHomeFragment target;

  private View view7f0900e0;

  private View view7f0900e1;

  private View view7f0902b2;

  private View view7f0902b6;

  private View view7f0902b8;

  private View view7f0902b4;

  private View view7f0902af;

  private View view7f09010d;

  private View view7f09010e;

  private View view7f090111;

  private View view7f090112;

  private View view7f0900e8;

  private View view7f0900e7;

  private View view7f0900cb;

  private View view7f0900dd;

  private View view7f0900df;

  private View view7f0900d4;

  private View view7f0900d5;

  private View view7f0900d6;

  @UiThread
  public MainSubHomeFragment_ViewBinding(final MainSubHomeFragment target, View source) {
    this.target = target;

    View view;
    target._BaseTodayLayout = Utils.findRequiredViewAsType(source, R.id.base_today_layout, "field '_BaseTodayLayout'", ScalableLayout.class);
    target._TodayImageView = Utils.findRequiredViewAsType(source, R.id.today_main_view, "field '_TodayImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.home_button_nihao_china, "field '_BasicNihaoButton' and method 'onSelectedOnDay'");
    target._BasicNihaoButton = Utils.castView(view, R.id.home_button_nihao_china, "field '_BasicNihaoButton'", ImageView.class);
    view7f0900e0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_button_step_china, "field '_BasicStepButton' and method 'onSelectedOnDay'");
    target._BasicStepButton = Utils.castView(view, R.id.home_button_step_china, "field '_BasicStepButton'", ImageView.class);
    view7f0900e1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    target._BannerLinkView = Utils.findRequiredViewAsType(source, R.id.home_banner_view, "field '_BannerLinkView'", BannerLinkView.class);
    target._AutobiographyBaseLayout = Utils.findRequiredViewAsType(source, R.id.home_autobiography_base_layout, "field '_AutobiographyBaseLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.today_monday_text, "method 'onSelectedOnDay'");
    view7f0902b2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_tuesday_text, "method 'onSelectedOnDay'");
    view7f0902b6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_wendsday_text, "method 'onSelectedOnDay'");
    view7f0902b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_thursday_text, "method 'onSelectedOnDay'");
    view7f0902b4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_friday_text, "method 'onSelectedOnDay'");
    view7f0902af = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_1_card_1_view, "method 'onSelectedOnDay'");
    view7f09010d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_1_card_2_view, "method 'onSelectedOnDay'");
    view7f09010e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_2_card_1_view, "method 'onSelectedOnDay'");
    view7f090111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_2_card_2_view, "method 'onSelectedOnDay'");
    view7f090112 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_icon_introduce_littlefox, "method 'onSelectedOnDay'");
    view7f0900e8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_icon_guide_littlefox, "method 'onSelectedOnDay'");
    view7f0900e7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_link_logo_view, "method 'onSelectClick'");
    view7f0900cb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_title_text, "method 'selectClick'");
    view7f0900dd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_button_autobiography, "method 'selectClick'");
    view7f0900df = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_1, "method 'selectClick'");
    view7f0900d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_2, "method 'selectClick'");
    view7f0900d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_3, "method 'selectClick'");
    view7f0900d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    target._FeatureTitleList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_card_1_title_text, "field '_FeatureTitleList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_card_2_title_text, "field '_FeatureTitleList'", TextView.class));
    target._FeatureImageList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_study_1_card_1_view, "field '_FeatureImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_1_card_2_view, "field '_FeatureImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_2_card_1_view, "field '_FeatureImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_2_card_2_view, "field '_FeatureImageList'", ImageView.class));
    target._FeatureSubTextList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_study_1_card_title_1, "field '_FeatureSubTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_1_card_title_2, "field '_FeatureSubTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_2_card_title_1, "field '_FeatureSubTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_study_2_card_title_2, "field '_FeatureSubTextList'", TextView.class));
    target._DayTextList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.today_monday_text, "field '_DayTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_tuesday_text, "field '_DayTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_wendsday_text, "field '_DayTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_thursday_text, "field '_DayTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_friday_text, "field '_DayTextList'", TextView.class));
    target._DayFocusList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.today_monday_focus, "field '_DayFocusList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_tuesday_focus, "field '_DayFocusList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_wendsday_focus, "field '_DayFocusList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_thursday_focus, "field '_DayFocusList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.today_friday_focus, "field '_DayFocusList'", ImageView.class));
    target._AutobiographyInformationTitleList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_text_1, "field '_AutobiographyInformationTitleList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_text_2, "field '_AutobiographyInformationTitleList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_text_3, "field '_AutobiographyInformationTitleList'", TextView.class));
    target._AutobiographyInformationSubtitleList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_name_1, "field '_AutobiographyInformationSubtitleList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_name_2, "field '_AutobiographyInformationSubtitleList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_information_name_3, "field '_AutobiographyInformationSubtitleList'", TextView.class));
    target._AutobiographyInformationImageList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_image_1, "field '_AutobiographyInformationImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_image_2, "field '_AutobiographyInformationImageList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_autobiography_image_3, "field '_AutobiographyInformationImageList'", ImageView.class));
    target._ExceptionTodayLineList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.date_line_1, "field '_ExceptionTodayLineList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.date_line_2, "field '_ExceptionTodayLineList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.date_line_3, "field '_ExceptionTodayLineList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.date_line_4, "field '_ExceptionTodayLineList'", ImageView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubHomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseTodayLayout = null;
    target._TodayImageView = null;
    target._BasicNihaoButton = null;
    target._BasicStepButton = null;
    target._BannerLinkView = null;
    target._AutobiographyBaseLayout = null;
    target._FeatureTitleList = null;
    target._FeatureImageList = null;
    target._FeatureSubTextList = null;
    target._DayTextList = null;
    target._DayFocusList = null;
    target._AutobiographyInformationTitleList = null;
    target._AutobiographyInformationSubtitleList = null;
    target._AutobiographyInformationImageList = null;
    target._ExceptionTodayLineList = null;

    view7f0900e0.setOnClickListener(null);
    view7f0900e0 = null;
    view7f0900e1.setOnClickListener(null);
    view7f0900e1 = null;
    view7f0902b2.setOnClickListener(null);
    view7f0902b2 = null;
    view7f0902b6.setOnClickListener(null);
    view7f0902b6 = null;
    view7f0902b8.setOnClickListener(null);
    view7f0902b8 = null;
    view7f0902b4.setOnClickListener(null);
    view7f0902b4 = null;
    view7f0902af.setOnClickListener(null);
    view7f0902af = null;
    view7f09010d.setOnClickListener(null);
    view7f09010d = null;
    view7f09010e.setOnClickListener(null);
    view7f09010e = null;
    view7f090111.setOnClickListener(null);
    view7f090111 = null;
    view7f090112.setOnClickListener(null);
    view7f090112 = null;
    view7f0900e8.setOnClickListener(null);
    view7f0900e8 = null;
    view7f0900e7.setOnClickListener(null);
    view7f0900e7 = null;
    view7f0900cb.setOnClickListener(null);
    view7f0900cb = null;
    view7f0900dd.setOnClickListener(null);
    view7f0900dd = null;
    view7f0900df.setOnClickListener(null);
    view7f0900df = null;
    view7f0900d4.setOnClickListener(null);
    view7f0900d4 = null;
    view7f0900d5.setOnClickListener(null);
    view7f0900d5 = null;
    view7f0900d6.setOnClickListener(null);
    view7f0900d6 = null;
  }
}

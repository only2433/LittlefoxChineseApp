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

  private View view7f0900d8;

  private View view7f0900d9;

  private View view7f0902aa;

  private View view7f0902ae;

  private View view7f0902b0;

  private View view7f0902ac;

  private View view7f0902a7;

  private View view7f090105;

  private View view7f090106;

  private View view7f090109;

  private View view7f09010a;

  private View view7f0900e0;

  private View view7f0900df;

  private View view7f0900c3;

  private View view7f0900d5;

  private View view7f0900d7;

  private View view7f0900cc;

  private View view7f0900cd;

  private View view7f0900ce;

  @UiThread
  public MainSubHomeFragment_ViewBinding(final MainSubHomeFragment target, View source) {
    this.target = target;

    View view;
    target._BaseTodayLayout = Utils.findRequiredViewAsType(source, R.id.base_today_layout, "field '_BaseTodayLayout'", ScalableLayout.class);
    target._TodayImageView = Utils.findRequiredViewAsType(source, R.id.today_main_view, "field '_TodayImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.home_button_nihao_china, "field '_BasicNihaoButton' and method 'onSelectedOnDay'");
    target._BasicNihaoButton = Utils.castView(view, R.id.home_button_nihao_china, "field '_BasicNihaoButton'", ImageView.class);
    view7f0900d8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_button_step_china, "field '_BasicStepButton' and method 'onSelectedOnDay'");
    target._BasicStepButton = Utils.castView(view, R.id.home_button_step_china, "field '_BasicStepButton'", ImageView.class);
    view7f0900d9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    target._BannerLinkView = Utils.findRequiredViewAsType(source, R.id.home_banner_view, "field '_BannerLinkView'", BannerLinkView.class);
    target._AutobiographyBaseLayout = Utils.findRequiredViewAsType(source, R.id.home_autobiography_base_layout, "field '_AutobiographyBaseLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.today_monday_text, "method 'onSelectedOnDay'");
    view7f0902aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_tuesday_text, "method 'onSelectedOnDay'");
    view7f0902ae = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_wendsday_text, "method 'onSelectedOnDay'");
    view7f0902b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_thursday_text, "method 'onSelectedOnDay'");
    view7f0902ac = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.today_friday_text, "method 'onSelectedOnDay'");
    view7f0902a7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_1_card_1_view, "method 'onSelectedOnDay'");
    view7f090105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_1_card_2_view, "method 'onSelectedOnDay'");
    view7f090106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_2_card_1_view, "method 'onSelectedOnDay'");
    view7f090109 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_study_2_card_2_view, "method 'onSelectedOnDay'");
    view7f09010a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_icon_introduce_littlefox, "method 'onSelectedOnDay'");
    view7f0900e0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_icon_guide_littlefox, "method 'onSelectedOnDay'");
    view7f0900df = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_link_logo_view, "method 'onSelectClick'");
    view7f0900c3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_title_text, "method 'selectClick'");
    view7f0900d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_button_autobiography, "method 'selectClick'");
    view7f0900d7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_1, "method 'selectClick'");
    view7f0900cc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_2, "method 'selectClick'");
    view7f0900cd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_autobiography_image_3, "method 'selectClick'");
    view7f0900ce = view;
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

    view7f0900d8.setOnClickListener(null);
    view7f0900d8 = null;
    view7f0900d9.setOnClickListener(null);
    view7f0900d9 = null;
    view7f0902aa.setOnClickListener(null);
    view7f0902aa = null;
    view7f0902ae.setOnClickListener(null);
    view7f0902ae = null;
    view7f0902b0.setOnClickListener(null);
    view7f0902b0 = null;
    view7f0902ac.setOnClickListener(null);
    view7f0902ac = null;
    view7f0902a7.setOnClickListener(null);
    view7f0902a7 = null;
    view7f090105.setOnClickListener(null);
    view7f090105 = null;
    view7f090106.setOnClickListener(null);
    view7f090106 = null;
    view7f090109.setOnClickListener(null);
    view7f090109 = null;
    view7f09010a.setOnClickListener(null);
    view7f09010a = null;
    view7f0900e0.setOnClickListener(null);
    view7f0900e0 = null;
    view7f0900df.setOnClickListener(null);
    view7f0900df = null;
    view7f0900c3.setOnClickListener(null);
    view7f0900c3 = null;
    view7f0900d5.setOnClickListener(null);
    view7f0900d5 = null;
    view7f0900d7.setOnClickListener(null);
    view7f0900d7 = null;
    view7f0900cc.setOnClickListener(null);
    view7f0900cc = null;
    view7f0900cd.setOnClickListener(null);
    view7f0900cd = null;
    view7f0900ce.setOnClickListener(null);
    view7f0900ce = null;
  }
}

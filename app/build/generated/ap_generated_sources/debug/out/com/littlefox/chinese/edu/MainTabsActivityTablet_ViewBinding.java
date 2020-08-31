// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainTabsActivityTablet_ViewBinding implements Unbinder {
  private MainTabsActivityTablet target;

  private View view7f0902c4;

  private View view7f0901ab;

  private View view7f0901aa;

  private View view7f0901a1;

  private View view7f0901ad;

  private View view7f0901ac;

  private View view7f0901a2;

  private View view7f090072;

  private View view7f090073;

  @UiThread
  public MainTabsActivityTablet_ViewBinding(MainTabsActivityTablet target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainTabsActivityTablet_ViewBinding(final MainTabsActivityTablet target, View source) {
    this.target = target;

    View view;
    target._MainBaseLayout = Utils.findRequiredViewAsType(source, R.id.main_draw_layout, "field '_MainBaseLayout'", DrawerLayout.class);
    target._BaseCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.main_content, "field '_BaseCoordinatorLayout'", CoordinatorLayout.class);
    target._MainBaseTopBarLayout = Utils.findRequiredViewAsType(source, R.id.main_tabs_base_layout, "field '_MainBaseTopBarLayout'", ScalableLayout.class);
    target._ViewPager = Utils.findRequiredViewAsType(source, R.id.main_fragment_viewpager, "field '_ViewPager'", ViewPager.class);
    target._TabLayout = Utils.findRequiredViewAsType(source, R.id.main_fragment_tablayout, "field '_TabLayout'", TabLayout.class);
    target._MainTitleText = Utils.findRequiredViewAsType(source, R.id.top_menu_title, "field '_MainTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.top_menu_setting, "field '_SettingButton' and method 'onSettingClick'");
    target._SettingButton = Utils.castView(view, R.id.top_menu_setting, "field '_SettingButton'", ImageView.class);
    view7f0902c4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    target._NavigationLayout = Utils.findRequiredViewAsType(source, R.id.navigation_base_layout, "field '_NavigationLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.navi_my_study_record_layout, "field '_NaviStudyRecordLayout' and method 'onSettingClick'");
    target._NaviStudyRecordLayout = Utils.castView(view, R.id.navi_my_study_record_layout, "field '_NaviStudyRecordLayout'", ScalableLayout.class);
    view7f0901ab = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.navi_my_info_layout, "field '_NaviUserInfoLayout' and method 'onSettingClick'");
    target._NaviUserInfoLayout = Utils.castView(view, R.id.navi_my_info_layout, "field '_NaviUserInfoLayout'", ScalableLayout.class);
    view7f0901aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.navi_add_user_manage_layout, "field '_NaviAddChildLayout' and method 'onSettingClick'");
    target._NaviAddChildLayout = Utils.castView(view, R.id.navi_add_user_manage_layout, "field '_NaviAddChildLayout'", ScalableLayout.class);
    view7f0901a1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.navi_question_layout, "field '_NaviQuestionLayout' and method 'onSettingClick'");
    target._NaviQuestionLayout = Utils.castView(view, R.id.navi_question_layout, "field '_NaviQuestionLayout'", ScalableLayout.class);
    view7f0901ad = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.navi_pay_layout, "field '_NaviPayLayout' and method 'onSettingClick'");
    target._NaviPayLayout = Utils.castView(view, R.id.navi_pay_layout, "field '_NaviPayLayout'", ScalableLayout.class);
    view7f0901ac = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.navi_content_present_layout, "field '_ContentPresentLayout' and method 'onSettingClick'");
    target._ContentPresentLayout = Utils.castView(view, R.id.navi_content_present_layout, "field '_ContentPresentLayout'", ScalableLayout.class);
    view7f0901a2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    target._NaviLogoutLayout = Utils.findRequiredViewAsType(source, R.id.navi_logout_layout, "field '_NaviLogoutLayout'", ScalableLayout.class);
    target._NaviLoginUserLayout = Utils.findRequiredViewAsType(source, R.id.navi_user_base_layout, "field '_NaviLoginUserLayout'", ScalableLayout.class);
    target._NaviLoginUserTypeText = Utils.findRequiredViewAsType(source, R.id.navi_user_type_text, "field '_NaviLoginUserTypeText'", TextView.class);
    target._NaviLoginUserNameText = Utils.findRequiredViewAsType(source, R.id.navi_user_name_text, "field '_NaviLoginUserNameText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.button_navi_logout, "field '_NaviLogoutButton' and method 'onSettingClick'");
    target._NaviLogoutButton = Utils.castView(view, R.id.button_navi_logout, "field '_NaviLogoutButton'", TextView.class);
    view7f090072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.button_navi_user_select, "method 'onSettingClick'");
    view7f090073 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick(p0);
      }
    });
    target._NavigationViewUserList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.navi_content_present_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_study_record_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_info_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_add_user_manage_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_question_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_pay_layout, "field '_NavigationViewUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_logout_layout, "field '_NavigationViewUserList'", ScalableLayout.class));
    target._NavigationViewAddChildList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.navi_content_present_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_study_record_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_info_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_question_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_pay_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_logout_layout, "field '_NavigationViewAddChildList'", ScalableLayout.class));
    target._NavigationViewPaidUserList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.navi_content_present_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_study_record_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_info_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_add_user_manage_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_question_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_logout_layout, "field '_NavigationViewPaidUserList'", ScalableLayout.class));
    target._NavigationViewPaidAddChildList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.navi_content_present_layout, "field '_NavigationViewPaidAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_study_record_layout, "field '_NavigationViewPaidAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_my_info_layout, "field '_NavigationViewPaidAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_question_layout, "field '_NavigationViewPaidAddChildList'", ScalableLayout.class), 
        Utils.findRequiredViewAsType(source, R.id.navi_logout_layout, "field '_NavigationViewPaidAddChildList'", ScalableLayout.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    MainTabsActivityTablet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._MainBaseLayout = null;
    target._BaseCoordinatorLayout = null;
    target._MainBaseTopBarLayout = null;
    target._ViewPager = null;
    target._TabLayout = null;
    target._MainTitleText = null;
    target._SettingButton = null;
    target._NavigationLayout = null;
    target._NaviStudyRecordLayout = null;
    target._NaviUserInfoLayout = null;
    target._NaviAddChildLayout = null;
    target._NaviQuestionLayout = null;
    target._NaviPayLayout = null;
    target._ContentPresentLayout = null;
    target._NaviLogoutLayout = null;
    target._NaviLoginUserLayout = null;
    target._NaviLoginUserTypeText = null;
    target._NaviLoginUserNameText = null;
    target._NaviLogoutButton = null;
    target._NavigationViewUserList = null;
    target._NavigationViewAddChildList = null;
    target._NavigationViewPaidUserList = null;
    target._NavigationViewPaidAddChildList = null;

    view7f0902c4.setOnClickListener(null);
    view7f0902c4 = null;
    view7f0901ab.setOnClickListener(null);
    view7f0901ab = null;
    view7f0901aa.setOnClickListener(null);
    view7f0901aa = null;
    view7f0901a1.setOnClickListener(null);
    view7f0901a1 = null;
    view7f0901ad.setOnClickListener(null);
    view7f0901ad = null;
    view7f0901ac.setOnClickListener(null);
    view7f0901ac = null;
    view7f0901a2.setOnClickListener(null);
    view7f0901a2 = null;
    view7f090072.setOnClickListener(null);
    view7f090072 = null;
    view7f090073.setOnClickListener(null);
    view7f090073 = null;
  }
}

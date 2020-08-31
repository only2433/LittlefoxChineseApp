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
import com.littlefox.chinese.edu.view.GridRecyclerView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainSubHomeFragmentTablet_ViewBinding implements Unbinder {
  private MainSubHomeFragmentTablet target;

  private View view7f0900f6;

  private View view7f0900f4;

  private View view7f0900f1;

  private View view7f0900ef;

  private View view7f0900fc;

  private View view7f0900fd;

  private View view7f0900fe;

  private View view7f0900ff;

  private View view7f090100;

  private View view7f090101;

  private View view7f090102;

  private View view7f090103;

  private View view7f090104;

  private View view7f090105;

  private View view7f0900cf;

  @UiThread
  public MainSubHomeFragmentTablet_ViewBinding(final MainSubHomeFragmentTablet target,
      View source) {
    this.target = target;

    View view;
    target._HomeInfoBaseLayout = Utils.findRequiredViewAsType(source, R.id.home_info_fragment_layout, "field '_HomeInfoBaseLayout'", ScalableLayout.class);
    target._HomeContentListBaseLayout = Utils.findRequiredViewAsType(source, R.id.home_content_list_fragment_layout, "field '_HomeContentListBaseLayout'", ScalableLayout.class);
    target._TodayTitleImage = Utils.findRequiredViewAsType(source, R.id.home_info_main_image, "field '_TodayTitleImage'", ImageView.class);
    target._ContentListGridView = Utils.findRequiredViewAsType(source, R.id.home_content_story_card_list_view, "field '_ContentListGridView'", GridRecyclerView.class);
    target._BannerLinkView = Utils.findRequiredViewAsType(source, R.id.home_banner_view, "field '_BannerLinkView'", BannerLinkView.class);
    view = Utils.findRequiredView(source, R.id.home_info_step_china_image, "field '_BasicStepButton'");
    target._BasicStepButton = Utils.castView(view, R.id.home_info_step_china_image, "field '_BasicStepButton'", ImageView.class);
    view7f0900f6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectedOnDay(p0);
      }
    });
    view = source.findViewById(R.id.home_info_nihao_china_image);
    if (view != null) {
      view7f0900f4 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_introduce_littlefox_image);
    if (view != null) {
      view7f0900f1 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_guide_study_image);
    if (view != null) {
      view7f0900ef = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_image1);
    if (view != null) {
      view7f0900fc = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_image2);
    if (view != null) {
      view7f0900fd = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_image3);
    if (view != null) {
      view7f0900fe = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_image4);
    if (view != null) {
      view7f0900ff = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_image5);
    if (view != null) {
      view7f090100 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_text1);
    if (view != null) {
      view7f090101 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_text2);
    if (view != null) {
      view7f090102 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_text3);
    if (view != null) {
      view7f090103 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_text4);
    if (view != null) {
      view7f090104 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = source.findViewById(R.id.home_info_today_list_text5);
    if (view != null) {
      view7f090105 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onSelectedOnDay(p0);
        }
      });
    }
    view = Utils.findRequiredView(source, R.id.footer_link_logo_view, "method 'onSelectClick'");
    view7f0900cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectClick(p0);
      }
    });
    target._TodayThumbnailItemImageList = Utils.listFilteringNull(
        Utils.findOptionalViewAsType(source, R.id.home_info_today_list_image1, "field '_TodayThumbnailItemImageList'", ImageView.class), 
        Utils.findOptionalViewAsType(source, R.id.home_info_today_list_image2, "field '_TodayThumbnailItemImageList'", ImageView.class), 
        Utils.findOptionalViewAsType(source, R.id.home_info_today_list_image3, "field '_TodayThumbnailItemImageList'", ImageView.class), 
        Utils.findOptionalViewAsType(source, R.id.home_info_today_list_image4, "field '_TodayThumbnailItemImageList'", ImageView.class), 
        Utils.findOptionalViewAsType(source, R.id.home_info_today_list_image5, "field '_TodayThumbnailItemImageList'", ImageView.class));
    target._TodayThumbnailItemCoverList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_cover1, "field '_TodayThumbnailItemCoverList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_cover2, "field '_TodayThumbnailItemCoverList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_cover3, "field '_TodayThumbnailItemCoverList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_cover4, "field '_TodayThumbnailItemCoverList'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_cover5, "field '_TodayThumbnailItemCoverList'", ImageView.class));
    target._TodayThumbnailItemTextList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_text1, "field '_TodayThumbnailItemTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_text2, "field '_TodayThumbnailItemTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_text3, "field '_TodayThumbnailItemTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_text4, "field '_TodayThumbnailItemTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_today_list_text5, "field '_TodayThumbnailItemTextList'", TextView.class));
    target._TodayItemTitleTextList = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.home_info_nihao_china, "field '_TodayItemTitleTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_step_china, "field '_TodayItemTitleTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_introduce_littlefox, "field '_TodayItemTitleTextList'", TextView.class), 
        Utils.findRequiredViewAsType(source, R.id.home_info_guide_study, "field '_TodayItemTitleTextList'", TextView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    MainSubHomeFragmentTablet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._HomeInfoBaseLayout = null;
    target._HomeContentListBaseLayout = null;
    target._TodayTitleImage = null;
    target._ContentListGridView = null;
    target._BannerLinkView = null;
    target._BasicStepButton = null;
    target._TodayThumbnailItemImageList = null;
    target._TodayThumbnailItemCoverList = null;
    target._TodayThumbnailItemTextList = null;
    target._TodayItemTitleTextList = null;

    view7f0900f6.setOnClickListener(null);
    view7f0900f6 = null;
    if (view7f0900f4 != null) {
      view7f0900f4.setOnClickListener(null);
      view7f0900f4 = null;
    }
    if (view7f0900f1 != null) {
      view7f0900f1.setOnClickListener(null);
      view7f0900f1 = null;
    }
    if (view7f0900ef != null) {
      view7f0900ef.setOnClickListener(null);
      view7f0900ef = null;
    }
    if (view7f0900fc != null) {
      view7f0900fc.setOnClickListener(null);
      view7f0900fc = null;
    }
    if (view7f0900fd != null) {
      view7f0900fd.setOnClickListener(null);
      view7f0900fd = null;
    }
    if (view7f0900fe != null) {
      view7f0900fe.setOnClickListener(null);
      view7f0900fe = null;
    }
    if (view7f0900ff != null) {
      view7f0900ff.setOnClickListener(null);
      view7f0900ff = null;
    }
    if (view7f090100 != null) {
      view7f090100.setOnClickListener(null);
      view7f090100 = null;
    }
    if (view7f090101 != null) {
      view7f090101.setOnClickListener(null);
      view7f090101 = null;
    }
    if (view7f090102 != null) {
      view7f090102.setOnClickListener(null);
      view7f090102 = null;
    }
    if (view7f090103 != null) {
      view7f090103.setOnClickListener(null);
      view7f090103 = null;
    }
    if (view7f090104 != null) {
      view7f090104.setOnClickListener(null);
      view7f090104 = null;
    }
    if (view7f090105 != null) {
      view7f090105.setOnClickListener(null);
      view7f090105 = null;
    }
    view7f0900cf.setOnClickListener(null);
    view7f0900cf = null;
  }
}

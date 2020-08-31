// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.exoplayer2.ui.PlayerView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayerHlsActivity_ViewBinding implements Unbinder {
  private PlayerHlsActivity target;

  private View view7f0901ee;

  private View view7f0901e5;

  private View view7f0901ea;

  private View view7f0901e8;

  private View view7f0901e7;

  private View view7f09001c;

  private View view7f0901d6;

  private View view7f0901dd;

  private View view7f0901d8;

  private View view7f0901db;

  private View view7f0901da;

  private View view7f090014;

  private View view7f090017;

  private View view7f09001a;

  private View view7f0901f1;

  private View view7f09000c;

  private View view7f090010;

  @UiThread
  public PlayerHlsActivity_ViewBinding(PlayerHlsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PlayerHlsActivity_ViewBinding(final PlayerHlsActivity target, View source) {
    this.target = target;

    View view;
    target._PlayerView = Utils.findRequiredViewAsType(source, R.id._playerView, "field '_PlayerView'", PlayerView.class);
    target._BackgroundDiscoverImage = Utils.findRequiredViewAsType(source, R.id.player_background, "field '_BackgroundDiscoverImage'", ImageView.class);
    target._LoadingLayout = Utils.findRequiredViewAsType(source, R.id.progress_wheel_layout, "field '_LoadingLayout'", ScalableLayout.class);
    target._TopViewLayout = Utils.findRequiredViewAsType(source, R.id.player_top_base_layout, "field '_TopViewLayout'", ScalableLayout.class);
    target._TopTitleText = Utils.findRequiredViewAsType(source, R.id.player_top_title, "field '_TopTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.player_subtitle_button, "field '_TopCaptionSettingButton' and method 'onPlayerButtonClick'");
    target._TopCaptionSettingButton = Utils.castView(view, R.id.player_subtitle_button, "field '_TopCaptionSettingButton'", ImageView.class);
    view7f0901ee = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_close_button, "field '_TopCloseButton' and method 'onPlayerButtonClick'");
    target._TopCloseButton = Utils.castView(view, R.id.player_close_button, "field '_TopCloseButton'", ImageView.class);
    view7f0901e5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    target._BottomViewLayout = Utils.findRequiredViewAsType(source, R.id.player_bottom_base_layout, "field '_BottomViewLayout'", ScalableLayout.class);
    target._CurrentPlayTimeText = Utils.findRequiredViewAsType(source, R.id.player_current_play_time, "field '_CurrentPlayTimeText'", TextView.class);
    target._ThumbSeekbar = Utils.findRequiredViewAsType(source, R.id.seekbar_play, "field '_ThumbSeekbar'", SeekBar.class);
    target._RemainPlayTimeText = Utils.findRequiredViewAsType(source, R.id.player_remain_play_time, "field '_RemainPlayTimeText'", TextView.class);
    target._PlayButtonLayout = Utils.findRequiredViewAsType(source, R.id.player_play_button_layout, "field '_PlayButtonLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.player_prev_button, "field '_PrevButton' and method 'onPlayerButtonClick'");
    target._PrevButton = Utils.castView(view, R.id.player_prev_button, "field '_PrevButton'", ImageView.class);
    view7f0901ea = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_play_button, "field '_PlayButton' and method 'onPlayerButtonClick'");
    target._PlayButton = Utils.castView(view, R.id.player_play_button, "field '_PlayButton'", ImageView.class);
    view7f0901e8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_next_button, "field '_NextButton' and method 'onPlayerButtonClick'");
    target._NextButton = Utils.castView(view, R.id.player_next_button, "field '_NextButton'", ImageView.class);
    view7f0901e7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    target._PreviewLayout = Utils.findRequiredViewAsType(source, R.id.player_preview_layout, "field '_PreviewLayout'", ScalableLayout.class);
    target._PreviewProgressText = Utils.findRequiredViewAsType(source, R.id.player_preview_title, "field '_PreviewProgressText'", TextView.class);
    target._BasePreviewEndLayout = Utils.findRequiredViewAsType(source, R.id.preview_end_layout, "field '_BasePreviewEndLayout'", RelativeLayout.class);
    target._PreviewSignMessageText = Utils.findRequiredViewAsType(source, R.id.preview_end_message_sign_up, "field '_PreviewSignMessageText'", TextView.class);
    view = Utils.findRequiredView(source, R.id._previewBackgroundRect, "field '_PreviewPayButton' and method 'onDisplayButtonClick'");
    target._PreviewPayButton = Utils.castView(view, R.id._previewBackgroundRect, "field '_PreviewPayButton'", ImageView.class);
    view7f09001c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PreviewPayButtonText = Utils.findRequiredViewAsType(source, R.id.preview_pay_text, "field '_PreviewPayButtonText'", TextView.class);
    target._BasePlayEndLayout = Utils.findRequiredViewAsType(source, R.id.play_end_layout, "field '_BasePlayEndLayout'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.play_end_close_button, "field '_PlayEndCloseButton' and method 'onDisplayButtonClick'");
    target._PlayEndCloseButton = Utils.castView(view, R.id.play_end_close_button, "field '_PlayEndCloseButton'", ImageView.class);
    view7f0901d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PlayEndButtonLayout = Utils.findRequiredViewAsType(source, R.id.play_end_base_layout, "field '_PlayEndButtonLayout'", ScalableLayout.class);
    view = Utils.findRequiredView(source, R.id.play_end_replay_layout, "field '_PlayEndReplayButton' and method 'onDisplayButtonClick'");
    target._PlayEndReplayButton = Utils.castView(view, R.id.play_end_replay_layout, "field '_PlayEndReplayButton'", ScalableLayout.class);
    view7f0901dd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PlayEndReplayText = Utils.findRequiredViewAsType(source, R.id.play_end_replay_text, "field '_PlayEndReplayText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.play_end_quiz_layout, "field '_PlayEndQuizButton' and method 'onDisplayButtonClick'");
    target._PlayEndQuizButton = Utils.castView(view, R.id.play_end_quiz_layout, "field '_PlayEndQuizButton'", ScalableLayout.class);
    view7f0901d8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PlayEndQuizText = Utils.findRequiredViewAsType(source, R.id.play_end_quiz_text, "field '_PlayEndQuizText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.play_end_remain_play_layout, "field '_PlayEndRemainButton' and method 'onDisplayButtonClick'");
    target._PlayEndRemainButton = Utils.castView(view, R.id.play_end_remain_play_layout, "field '_PlayEndRemainButton'", ScalableLayout.class);
    view7f0901db = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PlayEndRemainText = Utils.findRequiredViewAsType(source, R.id.play_end_remain_play_text, "field '_PlayEndRemainText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.play_end_recommand_layout, "field '_PlayEndRecommandViewLayout' and method 'onDisplayButtonClick'");
    target._PlayEndRecommandViewLayout = Utils.castView(view, R.id.play_end_recommand_layout, "field '_PlayEndRecommandViewLayout'", ScalableLayout.class);
    view7f0901da = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    target._PlayEndRecommandTitleText = Utils.findRequiredViewAsType(source, R.id.play_recommand_title, "field '_PlayEndRecommandTitleText'", TextView.class);
    target._PlayEndRecommandThumbnailImage = Utils.findRequiredViewAsType(source, R.id.play_recommand_thumbnail, "field '_PlayEndRecommandThumbnailImage'", ImageView.class);
    target._CaptionLayout = Utils.findRequiredViewAsType(source, R.id.player_caption_layout, "field '_CaptionLayout'", ScalableLayout.class);
    target._CaptionTitleText = Utils.findRequiredViewAsType(source, R.id.player_caption_title, "field '_CaptionTitleText'", TextView.class);
    target._PlayerListBaseLayout = Utils.findRequiredViewAsType(source, R.id._playerListBaseLayout, "field '_PlayerListBaseLayout'", LinearLayout.class);
    target._PlayerListView = Utils.findRequiredViewAsType(source, R.id._playerListView, "field '_PlayerListView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id._playerListButton, "field '_PlayerListButton' and method 'onPlayerButtonClick'");
    target._PlayerListButton = Utils.castView(view, R.id._playerListButton, "field '_PlayerListButton'", ImageView.class);
    view7f090014 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    target._PlayerSpeedListBaseLayout = Utils.findRequiredViewAsType(source, R.id._playerSpeedListBaseLayout, "field '_PlayerSpeedListBaseLayout'", LinearLayout.class);
    target._PlayerSpeedListView = Utils.findRequiredViewAsType(source, R.id._playerSpeedListView, "field '_PlayerSpeedListView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id._playerSpeedButton, "field '_PlayerSpeedButton' and method 'onPlayerButtonClick'");
    target._PlayerSpeedButton = Utils.castView(view, R.id._playerSpeedButton, "field '_PlayerSpeedButton'", ImageView.class);
    view7f090017 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id._playerSpeedText, "field '_PlayerSpeedText' and method 'onPlayerButtonClick'");
    target._PlayerSpeedText = Utils.castView(view, R.id._playerSpeedText, "field '_PlayerSpeedText'", TextView.class);
    view7f09001a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    target._PlayerOptionBackground = Utils.findRequiredViewAsType(source, R.id._playerOptionBackground, "field '_PlayerOptionBackground'", ImageView.class);
    target._PlayListTitleText = Utils.findRequiredViewAsType(source, R.id._playListTitleText, "field '_PlayListTitleText'", TextView.class);
    target._PlaySpeedListTitleText = Utils.findRequiredViewAsType(source, R.id._playSpeedListTitleText, "field '_PlaySpeedListTitleText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.preview_close_button, "method 'onDisplayButtonClick'");
    view7f0901f1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDisplayButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id._playListCloseButtonRect, "method 'onPlayerButtonClick'");
    view7f09000c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id._playSpeedListCloseButtonRect, "method 'onPlayerButtonClick'");
    view7f090010 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayerButtonClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerHlsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._PlayerView = null;
    target._BackgroundDiscoverImage = null;
    target._LoadingLayout = null;
    target._TopViewLayout = null;
    target._TopTitleText = null;
    target._TopCaptionSettingButton = null;
    target._TopCloseButton = null;
    target._BottomViewLayout = null;
    target._CurrentPlayTimeText = null;
    target._ThumbSeekbar = null;
    target._RemainPlayTimeText = null;
    target._PlayButtonLayout = null;
    target._PrevButton = null;
    target._PlayButton = null;
    target._NextButton = null;
    target._PreviewLayout = null;
    target._PreviewProgressText = null;
    target._BasePreviewEndLayout = null;
    target._PreviewSignMessageText = null;
    target._PreviewPayButton = null;
    target._PreviewPayButtonText = null;
    target._BasePlayEndLayout = null;
    target._PlayEndCloseButton = null;
    target._PlayEndButtonLayout = null;
    target._PlayEndReplayButton = null;
    target._PlayEndReplayText = null;
    target._PlayEndQuizButton = null;
    target._PlayEndQuizText = null;
    target._PlayEndRemainButton = null;
    target._PlayEndRemainText = null;
    target._PlayEndRecommandViewLayout = null;
    target._PlayEndRecommandTitleText = null;
    target._PlayEndRecommandThumbnailImage = null;
    target._CaptionLayout = null;
    target._CaptionTitleText = null;
    target._PlayerListBaseLayout = null;
    target._PlayerListView = null;
    target._PlayerListButton = null;
    target._PlayerSpeedListBaseLayout = null;
    target._PlayerSpeedListView = null;
    target._PlayerSpeedButton = null;
    target._PlayerSpeedText = null;
    target._PlayerOptionBackground = null;
    target._PlayListTitleText = null;
    target._PlaySpeedListTitleText = null;

    view7f0901ee.setOnClickListener(null);
    view7f0901ee = null;
    view7f0901e5.setOnClickListener(null);
    view7f0901e5 = null;
    view7f0901ea.setOnClickListener(null);
    view7f0901ea = null;
    view7f0901e8.setOnClickListener(null);
    view7f0901e8 = null;
    view7f0901e7.setOnClickListener(null);
    view7f0901e7 = null;
    view7f09001c.setOnClickListener(null);
    view7f09001c = null;
    view7f0901d6.setOnClickListener(null);
    view7f0901d6 = null;
    view7f0901dd.setOnClickListener(null);
    view7f0901dd = null;
    view7f0901d8.setOnClickListener(null);
    view7f0901d8 = null;
    view7f0901db.setOnClickListener(null);
    view7f0901db = null;
    view7f0901da.setOnClickListener(null);
    view7f0901da = null;
    view7f090014.setOnClickListener(null);
    view7f090014 = null;
    view7f090017.setOnClickListener(null);
    view7f090017 = null;
    view7f09001a.setOnClickListener(null);
    view7f09001a = null;
    view7f0901f1.setOnClickListener(null);
    view7f0901f1 = null;
    view7f09000c.setOnClickListener(null);
    view7f09000c = null;
    view7f090010.setOnClickListener(null);
    view7f090010 = null;
  }
}

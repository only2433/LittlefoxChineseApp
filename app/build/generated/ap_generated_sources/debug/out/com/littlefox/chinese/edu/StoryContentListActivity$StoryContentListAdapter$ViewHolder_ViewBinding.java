// Generated code from Butter Knife. Do not modify!
package com.littlefox.chinese.edu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StoryContentListActivity$StoryContentListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private StoryContentListActivity.StoryContentListAdapter.ViewHolder target;

  @UiThread
  public StoryContentListActivity$StoryContentListAdapter$ViewHolder_ViewBinding(
      StoryContentListActivity.StoryContentListAdapter.ViewHolder target, View source) {
    this.target = target;

    target._BaseLayout = Utils.findRequiredViewAsType(source, R.id.content_list_item_base_layout, "field '_BaseLayout'", ScalableLayout.class);
    target._FooterLayout = Utils.findRequiredViewAsType(source, R.id.content_list_item_footer_layout, "field '_FooterLayout'", ScalableLayout.class);
    target._ThumnailImage = Utils.findRequiredViewAsType(source, R.id.content_list_item_thumbnail, "field '_ThumnailImage'", ImageView.class);
    target._ItemCheckImage = Utils.findRequiredViewAsType(source, R.id.content_list_item_check, "field '_ItemCheckImage'", ImageView.class);
    target._ItemClickTerritory = Utils.findRequiredViewAsType(source, R.id.content_list_item_click_territory, "field '_ItemClickTerritory'", ImageView.class);
    target._ContentIndexText = Utils.findRequiredViewAsType(source, R.id.content_list_item_index, "field '_ContentIndexText'", TextView.class);
    target._ContentText = Utils.findRequiredViewAsType(source, R.id.content_list_item_content_text, "field '_ContentText'", SeparateTextView.class);
    target._QuizButton = Utils.findRequiredViewAsType(source, R.id.content_list_item_quiz_button, "field '_QuizButton'", ImageView.class);
    target._OriginTextButton = Utils.findRequiredViewAsType(source, R.id.content_list_item_origin_text_button, "field '_OriginTextButton'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoryContentListActivity.StoryContentListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._BaseLayout = null;
    target._FooterLayout = null;
    target._ThumnailImage = null;
    target._ItemCheckImage = null;
    target._ItemClickTerritory = null;
    target._ContentIndexText = null;
    target._ContentText = null;
    target._QuizButton = null;
    target._OriginTextButton = null;
  }
}

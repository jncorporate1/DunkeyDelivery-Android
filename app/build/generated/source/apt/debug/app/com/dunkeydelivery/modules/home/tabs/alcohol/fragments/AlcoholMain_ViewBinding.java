// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlcoholMain_ViewBinding implements Unbinder {
  private AlcoholMain target;

  private View view2131231287;

  @UiThread
  public AlcoholMain_ViewBinding(final AlcoholMain target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_add_item, "field 'tv_add_item' and method 'tvAddItemClicked'");
    target.tv_add_item = Utils.castView(view, R.id.tv_add_item, "field 'tv_add_item'", CustomTextView.class);
    view2131231287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tvAddItemClicked();
      }
    });
    target.tvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefresh'", SwipeRefreshLayout.class);
    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_refresh, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlcoholMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_add_item = null;
    target.tvSwipeRefresh = null;
    target.llContent = null;
    target.swipeRefreshLayout = null;

    view2131231287.setOnClickListener(null);
    view2131231287 = null;
  }
}

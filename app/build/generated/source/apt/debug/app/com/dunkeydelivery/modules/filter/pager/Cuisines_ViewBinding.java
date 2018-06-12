// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Cuisines_ViewBinding implements Unbinder {
  private Cuisines target;

  private View view2131231287;

  @UiThread
  public Cuisines_ViewBinding(final Cuisines target, View source) {
    this.target = target;

    View view;
    target.rv_cuisine = Utils.findRequiredViewAsType(source, R.id.rv_cuisines, "field 'rv_cuisine'", RecyclerView.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefreshLayout'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_add_item, "method 'refreshStore'");
    view2131231287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.refreshStore(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Cuisines target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_cuisine = null;
    target.rvSwipeRefresh = null;
    target.tvSwipeRefreshLayout = null;

    view2131231287.setOnClickListener(null);
    view2131231287 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.orders.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentOrders_ViewBinding implements Unbinder {
  private FragmentOrders target;

  @UiThread
  public FragmentOrders_ViewBinding(FragmentOrders target, View source) {
    this.target = target;

    target.rvSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.tvSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.tvNoResult = Utils.findRequiredViewAsType(source, R.id.tv_noresult, "field 'tvNoResult'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentOrders target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvSwipeRefreshLayout = null;
    target.tvSwipeRefreshLayout = null;
    target.recyclerView = null;
    target.tvNoResult = null;
  }
}

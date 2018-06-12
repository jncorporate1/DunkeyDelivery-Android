// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

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

public class StoreInfo_ViewBinding implements Unbinder {
  private StoreInfo target;

  @UiThread
  public StoreInfo_ViewBinding(StoreInfo target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.tvNewStreams = Utils.findRequiredViewAsType(source, R.id.tv_new_streams, "field 'tvNewStreams'", TextView.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreInfo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.tvNewStreams = null;
    target.rvSwipeRefresh = null;
    target.tvSwipeRefresh = null;
  }
}

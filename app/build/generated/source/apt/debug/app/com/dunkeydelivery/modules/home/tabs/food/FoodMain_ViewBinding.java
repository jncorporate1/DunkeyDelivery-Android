// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.food;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FoodMain_ViewBinding implements Unbinder {
  private FoodMain target;

  private View view2131231287;

  @UiThread
  public FoodMain_ViewBinding(final FoodMain target, View source) {
    this.target = target;

    View view;
    target.tvSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.contentSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.content_swipe_refresh, "field 'contentSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rvNearYou = Utils.findRequiredViewAsType(source, R.id.rv_near_you, "field 'rvNearYou'", RecyclerView.class);
    target.llPopular = Utils.findRequiredViewAsType(source, R.id.ll_popular, "field 'llPopular'", LinearLayout.class);
    target.tvNoresultNearby = Utils.findRequiredViewAsType(source, R.id.tv_noresult_nearby, "field 'tvNoresultNearby'", TextView.class);
    target.tvNearby = Utils.findRequiredViewAsType(source, R.id.tv_nearby, "field 'tvNearby'", TextView.class);
    target.tvPopular = Utils.findRequiredViewAsType(source, R.id.tv_popular, "field 'tvPopular'", TextView.class);
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'tvDistance'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_add_item, "field 'tv_add_item' and method 'tvAddItemClicked'");
    target.tv_add_item = Utils.castView(view, R.id.tv_add_item, "field 'tv_add_item'", TextView.class);
    view2131231287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tvAddItemClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FoodMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSwipeRefreshLayout = null;
    target.contentSwipeRefreshLayout = null;
    target.recyclerView = null;
    target.rvNearYou = null;
    target.llPopular = null;
    target.tvNoresultNearby = null;
    target.tvNearby = null;
    target.tvPopular = null;
    target.tvDistance = null;
    target.tv_add_item = null;

    view2131231287.setOnClickListener(null);
    view2131231287 = null;
  }
}

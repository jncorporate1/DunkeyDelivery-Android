// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.food.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StoreReviews_ViewBinding implements Unbinder {
  private StoreReviews target;

  @UiThread
  public StoreReviews_ViewBinding(StoreReviews target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvNoResult = Utils.findRequiredViewAsType(source, R.id.tv_noresult, "field 'tvNoResult'", TextView.class);
    target.tvDescription = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tvDescription'", TextView.class);
    target.ll_ratings = Utils.findRequiredViewAsType(source, R.id.ll_ratings, "field 'll_ratings'", LinearLayout.class);
    target.ratingbar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingbar'", RatingBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreReviews target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.rvSwipeRefresh = null;
    target.tvNoResult = null;
    target.tvDescription = null;
    target.ll_ratings = null;
    target.ratingbar = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.food;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StoreCategories_ViewBinding implements Unbinder {
  private StoreCategories target;

  @UiThread
  public StoreCategories_ViewBinding(StoreCategories target, View source) {
    this.target = target;

    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingBar'", RatingBar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvNewStreams = Utils.findRequiredViewAsType(source, R.id.tv_new_streams, "field 'tvNewStreams'", TextView.class);
    target.ibSearch = Utils.findRequiredViewAsType(source, R.id.ib_search, "field 'ibSearch'", ImageButton.class);
    target.ibReview = Utils.findRequiredViewAsType(source, R.id.ib_review, "field 'ibReview'", ImageButton.class);
    target.ibInfo = Utils.findRequiredViewAsType(source, R.id.ib_info, "field 'ibInfo'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreCategories target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ratingBar = null;
    target.recyclerView = null;
    target.rvSwipeRefresh = null;
    target.tvSwipeRefresh = null;
    target.tvNewStreams = null;
    target.ibSearch = null;
    target.ibReview = null;
    target.ibInfo = null;
  }
}

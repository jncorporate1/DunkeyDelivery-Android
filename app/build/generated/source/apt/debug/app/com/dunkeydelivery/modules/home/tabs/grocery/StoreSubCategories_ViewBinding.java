// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.grocery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.apmem.tools.layouts.FlowLayout;

public class StoreSubCategories_ViewBinding implements Unbinder {
  private StoreSubCategories target;

  @UiThread
  public StoreSubCategories_ViewBinding(StoreSubCategories target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvDeliveryFee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tvDeliveryFee'", TextView.class);
    target.tvMinOrder = Utils.findRequiredViewAsType(source, R.id.tv_subtitle1, "field 'tvMinOrder'", TextView.class);
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'tvDistance'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.flowLayout = Utils.findRequiredViewAsType(source, R.id.flow_layout, "field 'flowLayout'", FlowLayout.class);
    target.tvRate = Utils.findRequiredViewAsType(source, R.id.tv_rate, "field 'tvRate'", TextView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingBar'", RatingBar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefresh'", SwipeRefreshLayout.class);
    target.llTvCategoryLine = Utils.findRequiredViewAsType(source, R.id.ll_tv_category_line, "field 'llTvCategoryLine'", LinearLayout.class);
    target.ibSearch = Utils.findRequiredViewAsType(source, R.id.ib_search, "field 'ibSearch'", ImageButton.class);
    target.ibReview = Utils.findRequiredViewAsType(source, R.id.ib_review, "field 'ibReview'", ImageButton.class);
    target.ibInfo = Utils.findRequiredViewAsType(source, R.id.ib_info, "field 'ibInfo'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreSubCategories target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvDeliveryFee = null;
    target.tvMinOrder = null;
    target.tvDistance = null;
    target.tvTime = null;
    target.flowLayout = null;
    target.tvRate = null;
    target.ratingBar = null;
    target.recyclerView = null;
    target.rvSwipeRefresh = null;
    target.tvSwipeRefresh = null;
    target.llTvCategoryLine = null;
    target.ibSearch = null;
    target.ibReview = null;
    target.ibInfo = null;
  }
}

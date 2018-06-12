// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.food.pager.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewListAdapter$ItemTypeViewHolder_ViewBinding implements Unbinder {
  private ReviewListAdapter.ItemTypeViewHolder target;

  @UiThread
  public ReviewListAdapter$ItemTypeViewHolder_ViewBinding(ReviewListAdapter.ItemTypeViewHolder target,
      View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tv_date = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tv_date'", TextView.class);
    target.tvReviewCount = Utils.findRequiredViewAsType(source, R.id.tv_review_count, "field 'tvReviewCount'", TextView.class);
    target.tvReview = Utils.findRequiredViewAsType(source, R.id.tv_review, "field 'tvReview'", TextView.class);
    target.mParentFrame = Utils.findRequiredView(source, R.id.ll_root, "field 'mParentFrame'");
    target.iv_item = Utils.findRequiredViewAsType(source, R.id.iv_item, "field 'iv_item'", ImageView.class);
    target.ratingbar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingbar'", RatingBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewListAdapter.ItemTypeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tv_date = null;
    target.tvReviewCount = null;
    target.tvReview = null;
    target.mParentFrame = null;
    target.iv_item = null;
    target.ratingbar = null;
  }
}

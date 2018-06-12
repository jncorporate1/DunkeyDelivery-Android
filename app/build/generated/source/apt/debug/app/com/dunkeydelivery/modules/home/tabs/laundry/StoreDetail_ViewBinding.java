// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class StoreDetail_ViewBinding implements Unbinder {
  private StoreDetail target;

  @UiThread
  public StoreDetail_ViewBinding(StoreDetail target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvDeliveryFee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tvDeliveryFee'", TextView.class);
    target.tvMinOrder = Utils.findRequiredViewAsType(source, R.id.tv_subtitle1, "field 'tvMinOrder'", TextView.class);
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'tvDistance'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.flowLayout = Utils.findRequiredViewAsType(source, R.id.flow_layout, "field 'flowLayout'", FlowLayout.class);
    target.tvRate = Utils.findRequiredViewAsType(source, R.id.tv_rate, "field 'tvRate'", TextView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingBar'", RatingBar.class);
    target.ibSearch = Utils.findRequiredViewAsType(source, R.id.ib_search, "field 'ibSearch'", ImageButton.class);
    target.ibReview = Utils.findRequiredViewAsType(source, R.id.ib_review, "field 'ibReview'", ImageButton.class);
    target.ibInfo = Utils.findRequiredViewAsType(source, R.id.ib_info, "field 'ibInfo'", ImageButton.class);
    target.llWash = Utils.findRequiredViewAsType(source, R.id.ll_wash, "field 'llWash'", LinearLayout.class);
    target.llCleaning = Utils.findRequiredViewAsType(source, R.id.ll_cleaning, "field 'llCleaning'", LinearLayout.class);
    target.llTailoring = Utils.findRequiredViewAsType(source, R.id.ll_tailoring, "field 'llTailoring'", LinearLayout.class);
    target.llDate = Utils.findRequiredViewAsType(source, R.id.ll_date, "field 'llDate'", LinearLayout.class);
    target.llTime = Utils.findRequiredViewAsType(source, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", TextView.class);
    target.tvSelectTime = Utils.findRequiredViewAsType(source, R.id.tv_select_time, "field 'tvSelectTime'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreDetail target = this.target;
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
    target.ibSearch = null;
    target.ibReview = null;
    target.ibInfo = null;
    target.llWash = null;
    target.llCleaning = null;
    target.llTailoring = null;
    target.llDate = null;
    target.llTime = null;
    target.tvDate = null;
    target.tvSelectTime = null;
  }
}

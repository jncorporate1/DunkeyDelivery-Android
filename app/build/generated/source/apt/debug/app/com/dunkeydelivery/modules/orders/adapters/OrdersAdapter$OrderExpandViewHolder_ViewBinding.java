// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.orders.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.andexert.expandablelayout.library.ExpandableLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrdersAdapter$OrderExpandViewHolder_ViewBinding implements Unbinder {
  private OrdersAdapter.OrderExpandViewHolder target;

  @UiThread
  public OrdersAdapter$OrderExpandViewHolder_ViewBinding(OrdersAdapter.OrderExpandViewHolder target,
      View source) {
    this.target = target;

    target.tv_order_id = Utils.findRequiredViewAsType(source, R.id.tv_order_id, "field 'tv_order_id'", CustomTextView.class);
    target.ll_root = Utils.findRequiredViewAsType(source, R.id.ll_root, "field 'll_root'", LinearLayout.class);
    target.el = Utils.findRequiredViewAsType(source, R.id.el, "field 'el'", ExpandableLayout.class);
    target.recyclerViewSubOrder = Utils.findRequiredViewAsType(source, R.id.recyclerViewSubOrder, "field 'recyclerViewSubOrder'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrdersAdapter.OrderExpandViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_order_id = null;
    target.ll_root = null;
    target.el = null;
    target.recyclerViewSubOrder = null;
  }
}

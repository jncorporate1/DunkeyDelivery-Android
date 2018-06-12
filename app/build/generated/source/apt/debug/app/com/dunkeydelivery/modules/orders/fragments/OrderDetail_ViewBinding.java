// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.orders.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderDetail_ViewBinding implements Unbinder {
  private OrderDetail target;

  @UiThread
  public OrderDetail_ViewBinding(OrderDetail target, View source) {
    this.target = target;

    target.nsvOrderDetail = Utils.findRequiredViewAsType(source, R.id.nsvOrderDetail, "field 'nsvOrderDetail'", NestedScrollView.class);
    target.tv_order_date = Utils.findRequiredViewAsType(source, R.id.tv_order_date, "field 'tv_order_date'", CustomTextView.class);
    target.tv_order_date_value = Utils.findRequiredViewAsType(source, R.id.tv_order_date_value, "field 'tv_order_date_value'", CustomTextView.class);
    target.tv_subtotal = Utils.findRequiredViewAsType(source, R.id.tv_subtotal, "field 'tv_subtotal'", CustomTextView.class);
    target.tvTax = Utils.findRequiredViewAsType(source, R.id.tvTax, "field 'tvTax'", CustomTextView.class);
    target.tvTip = Utils.findRequiredViewAsType(source, R.id.tvTip, "field 'tvTip'", CustomTextView.class);
    target.tv_delivery_fee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tv_delivery_fee'", CustomTextView.class);
    target.tv_total = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tv_total'", CustomTextView.class);
    target.tvTipLabel = Utils.findRequiredViewAsType(source, R.id.tvTipLabel, "field 'tvTipLabel'", CustomTextView.class);
    target.tv_address = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tv_address'", CustomTextView.class);
    target.recyclerViewOrderProducts = Utils.findRequiredViewAsType(source, R.id.recyclerViewOrderProducts, "field 'recyclerViewOrderProducts'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nsvOrderDetail = null;
    target.tv_order_date = null;
    target.tv_order_date_value = null;
    target.tv_subtotal = null;
    target.tvTax = null;
    target.tvTip = null;
    target.tv_delivery_fee = null;
    target.tv_total = null;
    target.tvTipLabel = null;
    target.tv_address = null;
    target.recyclerViewOrderProducts = null;
  }
}

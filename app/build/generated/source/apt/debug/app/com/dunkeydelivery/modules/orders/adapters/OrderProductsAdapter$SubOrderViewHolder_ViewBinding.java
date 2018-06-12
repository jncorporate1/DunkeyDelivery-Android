// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.orders.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderProductsAdapter$SubOrderViewHolder_ViewBinding implements Unbinder {
  private OrderProductsAdapter.SubOrderViewHolder target;

  @UiThread
  public OrderProductsAdapter$SubOrderViewHolder_ViewBinding(OrderProductsAdapter.SubOrderViewHolder target,
      View source) {
    this.target = target;

    target.iv_item = Utils.findRequiredViewAsType(source, R.id.iv_item, "field 'iv_item'", ImageView.class);
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", CustomTextView.class);
    target.tv_price = Utils.findRequiredViewAsType(source, R.id.tv_price, "field 'tv_price'", CustomTextView.class);
    target.ll_quantity = Utils.findRequiredViewAsType(source, R.id.ll_quantity, "field 'll_quantity'", LinearLayout.class);
    target.tv_quantity = Utils.findRequiredViewAsType(source, R.id.tv_quantity, "field 'tv_quantity'", CustomTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderProductsAdapter.SubOrderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_item = null;
    target.tv_title = null;
    target.tv_price = null;
    target.ll_quantity = null;
    target.tv_quantity = null;
  }
}

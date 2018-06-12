// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.orders.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubOrderAdapter$SubOrderViewHolder_ViewBinding implements Unbinder {
  private SubOrderAdapter.SubOrderViewHolder target;

  @UiThread
  public SubOrderAdapter$SubOrderViewHolder_ViewBinding(SubOrderAdapter.SubOrderViewHolder target,
      View source) {
    this.target = target;

    target.imv_logo = Utils.findRequiredViewAsType(source, R.id.imv_logo, "field 'imv_logo'", ImageView.class);
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", CustomTextView.class);
    target.iv_view = Utils.findRequiredViewAsType(source, R.id.iv_view, "field 'iv_view'", ImageView.class);
    target.iv_delete = Utils.findRequiredViewAsType(source, R.id.iv_delete, "field 'iv_delete'", ImageView.class);
    target.tv_status = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'tv_status'", CustomTextView.class);
    target.rb_init = Utils.findRequiredViewAsType(source, R.id.rb_init, "field 'rb_init'", CustomRadioButton.class);
    target.rb_in_progress = Utils.findRequiredViewAsType(source, R.id.rb_in_progress, "field 'rb_in_progress'", CustomRadioButton.class);
    target.rb_shipped = Utils.findRequiredViewAsType(source, R.id.rb_shipped, "field 'rb_shipped'", CustomRadioButton.class);
    target.rb_delivered = Utils.findRequiredViewAsType(source, R.id.rb_delivered, "field 'rb_delivered'", CustomRadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubOrderAdapter.SubOrderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imv_logo = null;
    target.tv_title = null;
    target.iv_view = null;
    target.iv_delete = null;
    target.tv_status = null;
    target.rb_init = null;
    target.rb_in_progress = null;
    target.rb_shipped = null;
    target.rb_delivered = null;
  }
}

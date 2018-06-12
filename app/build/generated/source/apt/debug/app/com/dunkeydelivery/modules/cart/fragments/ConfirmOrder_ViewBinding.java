// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.cart.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConfirmOrder_ViewBinding implements Unbinder {
  private ConfirmOrder target;

  @UiThread
  public ConfirmOrder_ViewBinding(ConfirmOrder target, View source) {
    this.target = target;

    target.btnOrder = Utils.findRequiredViewAsType(source, R.id.btn_order, "field 'btnOrder'", Button.class);
    target.btnContinue = Utils.findRequiredViewAsType(source, R.id.btn_continue, "field 'btnContinue'", Button.class);
    target.tv_order_id = Utils.findRequiredViewAsType(source, R.id.tv_order_id, "field 'tv_order_id'", TextView.class);
    target.tv_order_date = Utils.findRequiredViewAsType(source, R.id.tv_order_date, "field 'tv_order_date'", TextView.class);
    target.tv_delivery_time = Utils.findRequiredViewAsType(source, R.id.tv_delivery_time, "field 'tv_delivery_time'", TextView.class);
    target.tv_payment_type = Utils.findRequiredViewAsType(source, R.id.tv_payment_type, "field 'tv_payment_type'", TextView.class);
    target.tv_tip = Utils.findRequiredViewAsType(source, R.id.tv_tip, "field 'tv_tip'", TextView.class);
    target.tv_tax = Utils.findRequiredViewAsType(source, R.id.tv_tax, "field 'tv_tax'", TextView.class);
    target.tv_tipLabel = Utils.findRequiredViewAsType(source, R.id.tv_tip_label, "field 'tv_tipLabel'", TextView.class);
    target.customTextView = Utils.findRequiredViewAsType(source, R.id.customTextView, "field 'customTextView'", TextView.class);
    target.tv_additional_notes = Utils.findRequiredViewAsType(source, R.id.tv_additional_notes, "field 'tv_additional_notes'", TextView.class);
    target.tv_address = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tv_address'", TextView.class);
    target.tv_subtotal = Utils.findRequiredViewAsType(source, R.id.tv_subtotal, "field 'tv_subtotal'", TextView.class);
    target.tv_delivery_fee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tv_delivery_fee'", TextView.class);
    target.tv_total = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tv_total'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ConfirmOrder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnOrder = null;
    target.btnContinue = null;
    target.tv_order_id = null;
    target.tv_order_date = null;
    target.tv_delivery_time = null;
    target.tv_payment_type = null;
    target.tv_tip = null;
    target.tv_tax = null;
    target.tv_tipLabel = null;
    target.customTextView = null;
    target.tv_additional_notes = null;
    target.tv_address = null;
    target.tv_subtotal = null;
    target.tv_delivery_fee = null;
    target.tv_total = null;
  }
}

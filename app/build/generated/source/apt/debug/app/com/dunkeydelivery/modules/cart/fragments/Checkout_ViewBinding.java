// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.cart.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomEditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Checkout_ViewBinding implements Unbinder {
  private Checkout target;

  @UiThread
  public Checkout_ViewBinding(Checkout target, View source) {
    this.target = target;

    target.rgPaymentMethod = Utils.findRequiredViewAsType(source, R.id.rg_method, "field 'rgPaymentMethod'", RadioGroup.class);
    target.rbCard = Utils.findRequiredViewAsType(source, R.id.rb_card, "field 'rbCard'", RadioButton.class);
    target.rbPayPal = Utils.findRequiredViewAsType(source, R.id.rb_paypal, "field 'rbPayPal'", RadioButton.class);
    target.rgFrequency = Utils.findRequiredViewAsType(source, R.id.rg_frequency, "field 'rgFrequency'", RadioGroup.class);
    target.rbOneTime = Utils.findRequiredViewAsType(source, R.id.rb_one_time, "field 'rbOneTime'", RadioButton.class);
    target.rbWeekly = Utils.findRequiredViewAsType(source, R.id.rb_weekly, "field 'rbWeekly'", RadioButton.class);
    target.rbMonthly = Utils.findRequiredViewAsType(source, R.id.rb_monthly, "field 'rbMonthly'", RadioButton.class);
    target.llDeliveryInformation = Utils.findRequiredViewAsType(source, R.id.ll_delivery_information, "field 'llDeliveryInformation'", LinearLayout.class);
    target.llDeliveryInfo = Utils.findRequiredViewAsType(source, R.id.ll_delivery_info, "field 'llDeliveryInfo'", LinearLayout.class);
    target.tvDeliveryInfo = Utils.findRequiredViewAsType(source, R.id.tv_delivery_info, "field 'tvDeliveryInfo'", TextView.class);
    target.ll_container = Utils.findRequiredViewAsType(source, R.id.ll_container, "field 'll_container'", LinearLayout.class);
    target.tvAddCard = Utils.findRequiredViewAsType(source, R.id.tv_add_creditcard, "field 'tvAddCard'", TextView.class);
    target.tvAddDeliveryInformation = Utils.findRequiredViewAsType(source, R.id.tv_add_delivery_information, "field 'tvAddDeliveryInformation'", TextView.class);
    target.ll_main_checkout = Utils.findRequiredViewAsType(source, R.id.ll_main_checkout, "field 'll_main_checkout'", LinearLayout.class);
    target.btnPlaceOrder = Utils.findRequiredViewAsType(source, R.id.btn_place_order, "field 'btnPlaceOrder'", Button.class);
    target.tv_total = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tv_total'", TextView.class);
    target.tv_delivery_fee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tv_delivery_fee'", TextView.class);
    target.tv_tip_fee = Utils.findRequiredViewAsType(source, R.id.tv_tip_fee, "field 'tv_tip_fee'", TextView.class);
    target.tv_tax_fee = Utils.findRequiredViewAsType(source, R.id.tv_tax_fee, "field 'tv_tax_fee'", TextView.class);
    target.tv_subtotal = Utils.findRequiredViewAsType(source, R.id.tv_subtotal, "field 'tv_subtotal'", TextView.class);
    target.et_instructions = Utils.findRequiredViewAsType(source, R.id.et_instructions, "field 'et_instructions'", CustomEditText.class);
    target.tv_points = Utils.findRequiredViewAsType(source, R.id.tv_points, "field 'tv_points'", TextView.class);
    target.tv_tip = Utils.findRequiredViewAsType(source, R.id.tv_tip, "field 'tv_tip'", TextView.class);
    target.tvCreditCardNumber = Utils.findRequiredViewAsType(source, R.id.tv_creditcardnumber, "field 'tvCreditCardNumber'", TextView.class);
    target.tvCreditCardExpiry = Utils.findRequiredViewAsType(source, R.id.tv_creditcardexpiry, "field 'tvCreditCardExpiry'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Checkout target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rgPaymentMethod = null;
    target.rbCard = null;
    target.rbPayPal = null;
    target.rgFrequency = null;
    target.rbOneTime = null;
    target.rbWeekly = null;
    target.rbMonthly = null;
    target.llDeliveryInformation = null;
    target.llDeliveryInfo = null;
    target.tvDeliveryInfo = null;
    target.ll_container = null;
    target.tvAddCard = null;
    target.tvAddDeliveryInformation = null;
    target.ll_main_checkout = null;
    target.btnPlaceOrder = null;
    target.tv_total = null;
    target.tv_delivery_fee = null;
    target.tv_tip_fee = null;
    target.tv_tax_fee = null;
    target.tv_subtotal = null;
    target.et_instructions = null;
    target.tv_points = null;
    target.tv_tip = null;
    target.tvCreditCardNumber = null;
    target.tvCreditCardExpiry = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.cart.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeliveryInformation_ViewBinding implements Unbinder {
  private DeliveryInformation target;

  @UiThread
  public DeliveryInformation_ViewBinding(DeliveryInformation target, View source) {
    this.target = target;

    target.tvAddNewAddress = Utils.findRequiredViewAsType(source, R.id.tv_add_address, "field 'tvAddNewAddress'", TextView.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.rg_address, "field 'radioGroup'", RadioGroup.class);
    target.rb1 = Utils.findRequiredViewAsType(source, R.id.rb1, "field 'rb1'", RadioButton.class);
    target.btnUpdate = Utils.findRequiredViewAsType(source, R.id.btn_update, "field 'btnUpdate'", Button.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeliveryInformation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAddNewAddress = null;
    target.radioGroup = null;
    target.rb1 = null;
    target.btnUpdate = null;
    target.rvSwipeRefresh = null;
  }
}

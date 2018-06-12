// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.cart.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreditCardInformation_ViewBinding implements Unbinder {
  private CreditCardInformation target;

  @UiThread
  public CreditCardInformation_ViewBinding(CreditCardInformation target, View source) {
    this.target = target;

    target.rgCreditCard = Utils.findRequiredViewAsType(source, R.id.rg_credit_card, "field 'rgCreditCard'", RadioGroup.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvAddCreditCard = Utils.findRequiredViewAsType(source, R.id.tv_add_credit_card, "field 'tvAddCreditCard'", TextView.class);
    target.btnUpdate = Utils.findRequiredViewAsType(source, R.id.btn_update, "field 'btnUpdate'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreditCardInformation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rgCreditCard = null;
    target.rvSwipeRefresh = null;
    target.tvAddCreditCard = null;
    target.btnUpdate = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.cart.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CartMain_ViewBinding implements Unbinder {
  private CartMain target;

  @UiThread
  public CartMain_ViewBinding(CartMain target, View source) {
    this.target = target;

    target.ibBack = Utils.findRequiredViewAsType(source, R.id.ib_back, "field 'ibBack'", ImageButton.class);
    target.btnGotoCheckout = Utils.findRequiredViewAsType(source, R.id.btn_go_to, "field 'btnGotoCheckout'", Button.class);
    target.tvAddMore = Utils.findRequiredViewAsType(source, R.id.tv_add_more, "field 'tvAddMore'", TextView.class);
    target.tv_add_item = Utils.findRequiredViewAsType(source, R.id.tv_add_item, "field 'tv_add_item'", TextView.class);
    target.ll_nocart = Utils.findRequiredViewAsType(source, R.id.ll_nocart, "field 'll_nocart'", LinearLayout.class);
    target.rl_cart = Utils.findRequiredViewAsType(source, R.id.rl_cart, "field 'rl_cart'", RelativeLayout.class);
    target.tv_point = Utils.findRequiredViewAsType(source, R.id.tv_points, "field 'tv_point'", TextView.class);
    target.ll_container = Utils.findRequiredViewAsType(source, R.id.ll_container, "field 'll_container'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CartMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ibBack = null;
    target.btnGotoCheckout = null;
    target.tvAddMore = null;
    target.tv_add_item = null;
    target.ll_nocart = null;
    target.rl_cart = null;
    target.tv_point = null;
    target.ll_container = null;
  }
}

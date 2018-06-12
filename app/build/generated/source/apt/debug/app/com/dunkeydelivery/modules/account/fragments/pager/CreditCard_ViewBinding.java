// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreditCard_ViewBinding implements Unbinder {
  private CreditCard target;

  @UiThread
  public CreditCard_ViewBinding(CreditCard target, View source) {
    this.target = target;

    target.tvAddCreditCard = Utils.findRequiredViewAsType(source, R.id.tv_add_credit_card, "field 'tvAddCreditCard'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rv_swipe_refresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rv_swipe_refresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreditCard target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAddCreditCard = null;
    target.recyclerView = null;
    target.rv_swipe_refresh = null;
  }
}

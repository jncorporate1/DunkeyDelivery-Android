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

public class AddressList_ViewBinding implements Unbinder {
  private AddressList target;

  @UiThread
  public AddressList_ViewBinding(AddressList target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rv_swipe_refresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rv_swipe_refresh'", SwipeRefreshLayout.class);
    target.tvAddNewAddress = Utils.findRequiredViewAsType(source, R.id.tv_add_new_Address, "field 'tvAddNewAddress'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddressList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.rv_swipe_refresh = null;
    target.tvAddNewAddress = null;
  }
}

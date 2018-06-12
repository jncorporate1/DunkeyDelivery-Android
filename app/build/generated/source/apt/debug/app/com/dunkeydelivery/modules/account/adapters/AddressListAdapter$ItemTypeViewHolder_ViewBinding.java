// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.daimajia.swipe.SwipeLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddressListAdapter$ItemTypeViewHolder_ViewBinding implements Unbinder {
  private AddressListAdapter.ItemTypeViewHolder target;

  @UiThread
  public AddressListAdapter$ItemTypeViewHolder_ViewBinding(AddressListAdapter.ItemTypeViewHolder target,
      View source) {
    this.target = target;

    target.mParentFrame = Utils.findRequiredView(source, R.id.ll_root, "field 'mParentFrame'");
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvSubTitle = Utils.findRequiredViewAsType(source, R.id.tv_subtitle, "field 'tvSubTitle'", TextView.class);
    target.swipeLayout = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipeLayout'", SwipeLayout.class);
    target.ibDelete = Utils.findRequiredViewAsType(source, R.id.ib_delete, "field 'ibDelete'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddressListAdapter.ItemTypeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mParentFrame = null;
    target.tvTitle = null;
    target.tvSubTitle = null;
    target.swipeLayout = null;
    target.ibDelete = null;
  }
}

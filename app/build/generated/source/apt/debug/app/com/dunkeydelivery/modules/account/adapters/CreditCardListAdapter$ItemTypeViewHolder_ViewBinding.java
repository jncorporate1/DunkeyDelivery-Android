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

public class CreditCardListAdapter$ItemTypeViewHolder_ViewBinding implements Unbinder {
  private CreditCardListAdapter.ItemTypeViewHolder target;

  @UiThread
  public CreditCardListAdapter$ItemTypeViewHolder_ViewBinding(CreditCardListAdapter.ItemTypeViewHolder target,
      View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvSubTitle = Utils.findRequiredViewAsType(source, R.id.tv_subtitle, "field 'tvSubTitle'", TextView.class);
    target.mParentFrame = Utils.findRequiredView(source, R.id.ll_root, "field 'mParentFrame'");
    target.ibDelete = Utils.findRequiredViewAsType(source, R.id.ib_delete, "field 'ibDelete'", ImageButton.class);
    target.swipeLayout = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipeLayout'", SwipeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreditCardListAdapter.ItemTypeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvSubTitle = null;
    target.mParentFrame = null;
    target.ibDelete = null;
    target.swipeLayout = null;
  }
}

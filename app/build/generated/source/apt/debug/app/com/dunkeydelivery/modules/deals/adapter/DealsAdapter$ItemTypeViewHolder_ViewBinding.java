// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.deals.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DealsAdapter$ItemTypeViewHolder_ViewBinding implements Unbinder {
  private DealsAdapter.ItemTypeViewHolder target;

  @UiThread
  public DealsAdapter$ItemTypeViewHolder_ViewBinding(DealsAdapter.ItemTypeViewHolder target,
      View source) {
    this.target = target;

    target.iv_promotion = Utils.findRequiredViewAsType(source, R.id.iv_promotion, "field 'iv_promotion'", ImageView.class);
    target.llDealsnPromotions = Utils.findRequiredViewAsType(source, R.id.ll_dealsnpromotions, "field 'llDealsnPromotions'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DealsAdapter.ItemTypeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_promotion = null;
    target.llDealsnPromotions = null;
  }
}

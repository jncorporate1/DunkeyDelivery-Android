// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlcoholSizes$ProductSizesViewHolder_ViewBinding implements Unbinder {
  private AlcoholSizes.ProductSizesViewHolder target;

  @UiThread
  public AlcoholSizes$ProductSizesViewHolder_ViewBinding(AlcoholSizes.ProductSizesViewHolder target,
      View source) {
    this.target = target;

    target.customRadioButton = Utils.findRequiredViewAsType(source, R.id.customRadioButton, "field 'customRadioButton'", CustomRadioButton.class);
    target.tvPrice = Utils.findRequiredViewAsType(source, R.id.tv_price, "field 'tvPrice'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlcoholSizes.ProductSizesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.customRadioButton = null;
    target.tvPrice = null;
  }
}

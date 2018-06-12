// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlcoholFilterSizes$AlcoholFilterSizesViewHolder_ViewBinding implements Unbinder {
  private AlcoholFilterSizes.AlcoholFilterSizesViewHolder target;

  @UiThread
  public AlcoholFilterSizes$AlcoholFilterSizesViewHolder_ViewBinding(AlcoholFilterSizes.AlcoholFilterSizesViewHolder target,
      View source) {
    this.target = target;

    target.customCheckBox = Utils.findRequiredViewAsType(source, R.id.customCheckBox, "field 'customCheckBox'", CustomCheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlcoholFilterSizes.AlcoholFilterSizesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.customCheckBox = null;
  }
}

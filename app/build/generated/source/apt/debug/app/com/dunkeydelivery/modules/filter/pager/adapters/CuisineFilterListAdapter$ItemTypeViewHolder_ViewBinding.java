// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RadioButton;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CuisineFilterListAdapter$ItemTypeViewHolder_ViewBinding implements Unbinder {
  private CuisineFilterListAdapter.ItemTypeViewHolder target;

  @UiThread
  public CuisineFilterListAdapter$ItemTypeViewHolder_ViewBinding(CuisineFilterListAdapter.ItemTypeViewHolder target,
      View source) {
    this.target = target;

    target.rb_cuisine = Utils.findRequiredViewAsType(source, R.id.rb_cuisine, "field 'rb_cuisine'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CuisineFilterListAdapter.ItemTypeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rb_cuisine = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.alcohol.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductList_ViewBinding implements Unbinder {
  private ProductList target;

  @UiThread
  public ProductList_ViewBinding(ProductList target, View source) {
    this.target = target;

    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.llSeeAll = Utils.findRequiredViewAsType(source, R.id.ll_see_all, "field 'llSeeAll'", LinearLayout.class);
    target.svCategory = Utils.findRequiredViewAsType(source, R.id.sv_category, "field 'svCategory'", ScrollView.class);
    target.llRelativeLayout = Utils.findRequiredViewAsType(source, R.id.ll_relativelayout, "field 'llRelativeLayout'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llContent = null;
    target.llSeeAll = null;
    target.svCategory = null;
    target.llRelativeLayout = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.alcohol.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllProductsViewPager_ViewBinding implements Unbinder {
  private AllProductsViewPager target;

  @UiThread
  public AllProductsViewPager_ViewBinding(AllProductsViewPager target, View source) {
    this.target = target;

    target.swipeRefresh1 = Utils.findRequiredViewAsType(source, R.id.swipe_refresh1, "field 'swipeRefresh1'", SwipeRefreshLayout.class);
    target.swipe_refresh2 = Utils.findRequiredViewAsType(source, R.id.swipe_refresh2, "field 'swipe_refresh2'", SwipeRefreshLayout.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllProductsViewPager target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefresh1 = null;
    target.swipe_refresh2 = null;
    target.tabLayout = null;
    target.viewPager = null;
  }
}

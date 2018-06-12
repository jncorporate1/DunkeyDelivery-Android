// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterViewPager_ViewBinding implements Unbinder {
  private FilterViewPager target;

  @UiThread
  public FilterViewPager_ViewBinding(FilterViewPager target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterViewPager target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
    target.viewPager = null;
  }
}

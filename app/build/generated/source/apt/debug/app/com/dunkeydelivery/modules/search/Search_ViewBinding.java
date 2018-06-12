// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.search;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Search_ViewBinding implements Unbinder {
  private Search target;

  @UiThread
  public Search_ViewBinding(Search target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.llSearch = Utils.findRequiredViewAsType(source, R.id.ll_search, "field 'llSearch'", LinearLayout.class);
    target.ibBack = Utils.findRequiredViewAsType(source, R.id.ib_back, "field 'ibBack'", ImageButton.class);
    target.ibCancel = Utils.findRequiredViewAsType(source, R.id.ib_cancel, "field 'ibCancel'", ImageButton.class);
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.et_search, "field 'etSearch'", EditText.class);
    target.rvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.rv_swipe_refresh, "field 'rvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.tv_swipe_refresh, "field 'tvSwipeRefresh'", SwipeRefreshLayout.class);
    target.tvNoResult = Utils.findRequiredViewAsType(source, R.id.tv_noresult, "field 'tvNoResult'", TextView.class);
    target.tvNewRecords = Utils.findRequiredViewAsType(source, R.id.tv_new_streams, "field 'tvNewRecords'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Search target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.llSearch = null;
    target.ibBack = null;
    target.ibCancel = null;
    target.etSearch = null;
    target.rvSwipeRefresh = null;
    target.tvSwipeRefresh = null;
    target.tvNoResult = null;
    target.tvNewRecords = null;
  }
}

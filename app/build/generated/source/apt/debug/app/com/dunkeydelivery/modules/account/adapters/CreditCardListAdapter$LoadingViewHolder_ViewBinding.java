// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreditCardListAdapter$LoadingViewHolder_ViewBinding implements Unbinder {
  private CreditCardListAdapter.LoadingViewHolder target;

  @UiThread
  public CreditCardListAdapter$LoadingViewHolder_ViewBinding(CreditCardListAdapter.LoadingViewHolder target,
      View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar1, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreditCardListAdapter.LoadingViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
  }
}

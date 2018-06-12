// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WashFold_ViewBinding implements Unbinder {
  private WashFold target;

  @UiThread
  public WashFold_ViewBinding(WashFold target, View source) {
    this.target = target;

    target.tv_description = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tv_description'", CustomTextView.class);
    target.btnAdd = Utils.findRequiredViewAsType(source, R.id.btn_add, "field 'btnAdd'", Button.class);
    target.etWeight = Utils.findRequiredViewAsType(source, R.id.et_weight, "field 'etWeight'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WashFold target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_description = null;
    target.btnAdd = null;
    target.etWeight = null;
  }
}

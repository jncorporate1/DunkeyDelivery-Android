// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DryCleaning_ViewBinding implements Unbinder {
  private DryCleaning target;

  @UiThread
  public DryCleaning_ViewBinding(DryCleaning target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.rg = Utils.findRequiredViewAsType(source, R.id.rg, "field 'rg'", RadioGroup.class);
    target.tvNote = Utils.findRequiredViewAsType(source, R.id.tv_note, "field 'tvNote'", TextView.class);
    target.tv_description = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tv_description'", CustomTextView.class);
    target.btnAdd = Utils.findRequiredViewAsType(source, R.id.btn_add, "field 'btnAdd'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DryCleaning target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.rg = null;
    target.tvNote = null;
    target.tv_description = null;
    target.btnAdd = null;
  }
}

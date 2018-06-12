// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Tailoring_ViewBinding implements Unbinder {
  private Tailoring target;

  @UiThread
  public Tailoring_ViewBinding(Tailoring target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.tv_description = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tv_description'", TextView.class);
    target.etInstructions = Utils.findRequiredViewAsType(source, R.id.et_instructions, "field 'etInstructions'", EditText.class);
    target.btnAdd = Utils.findRequiredViewAsType(source, R.id.btn_add, "field 'btnAdd'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Tailoring target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.tv_description = null;
    target.etInstructions = null;
    target.btnAdd = null;
  }
}

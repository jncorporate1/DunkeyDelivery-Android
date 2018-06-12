// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.utils;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DialogFragmentSheduling_ViewBinding implements Unbinder {
  private DialogFragmentSheduling target;

  @UiThread
  public DialogFragmentSheduling_ViewBinding(DialogFragmentSheduling target, View source) {
    this.target = target;

    target.rbASAP = Utils.findRequiredViewAsType(source, R.id.rb_asap, "field 'rbASAP'", RadioButton.class);
    target.rbToday = Utils.findRequiredViewAsType(source, R.id.rb_today, "field 'rbToday'", RadioButton.class);
    target.rbLater = Utils.findRequiredViewAsType(source, R.id.rb_later, "field 'rbLater'", RadioButton.class);
    target.lineOptions = Utils.findRequiredView(source, R.id.lineOptions, "field 'lineOptions'");
    target.dateLine = Utils.findRequiredView(source, R.id.dateLine, "field 'dateLine'");
    target.lineTime = Utils.findRequiredView(source, R.id.lineTime, "field 'lineTime'");
    target.llDate = Utils.findRequiredViewAsType(source, R.id.ll_date, "field 'llDate'", LinearLayout.class);
    target.llTime = Utils.findRequiredViewAsType(source, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", TextView.class);
    target.tvSelectTime = Utils.findRequiredViewAsType(source, R.id.tv_select_time, "field 'tvSelectTime'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DialogFragmentSheduling target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rbASAP = null;
    target.rbToday = null;
    target.rbLater = null;
    target.lineOptions = null;
    target.dateLine = null;
    target.lineTime = null;
    target.llDate = null;
    target.llTime = null;
    target.btnSubmit = null;
    target.tvDate = null;
    target.tvSelectTime = null;
  }
}

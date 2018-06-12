// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddNewCreditCard_ViewBinding implements Unbinder {
  private AddNewCreditCard target;

  @UiThread
  public AddNewCreditCard_ViewBinding(AddNewCreditCard target, View source) {
    this.target = target;

    target.etLabel = Utils.findRequiredViewAsType(source, R.id.et_label, "field 'etLabel'", EditText.class);
    target.cbDefault = Utils.findRequiredViewAsType(source, R.id.cb_default, "field 'cbDefault'", CheckBox.class);
    target.etCardNumber = Utils.findRequiredViewAsType(source, R.id.et_cc_number, "field 'etCardNumber'", EditText.class);
    target.etCvv = Utils.findRequiredViewAsType(source, R.id.et_cvv, "field 'etCvv'", EditText.class);
    target.etZip = Utils.findRequiredViewAsType(source, R.id.et_zipcode, "field 'etZip'", EditText.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", EditText.class);
    target.btnContinue = Utils.findRequiredViewAsType(source, R.id.btn_add_new_creditcard, "field 'btnContinue'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddNewCreditCard target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etLabel = null;
    target.cbDefault = null;
    target.etCardNumber = null;
    target.etCvv = null;
    target.etZip = null;
    target.tvDate = null;
    target.btnContinue = null;
  }
}

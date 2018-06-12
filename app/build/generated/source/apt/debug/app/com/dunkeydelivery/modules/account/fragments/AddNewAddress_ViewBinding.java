// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddNewAddress_ViewBinding implements Unbinder {
  private AddNewAddress target;

  @UiThread
  public AddNewAddress_ViewBinding(AddNewAddress target, View source) {
    this.target = target;

    target.etAddress = Utils.findRequiredViewAsType(source, R.id.et_address, "field 'etAddress'", EditText.class);
    target.etSuit = Utils.findRequiredViewAsType(source, R.id.et_suit, "field 'etSuit'", EditText.class);
    target.etCity = Utils.findRequiredViewAsType(source, R.id.et_city, "field 'etCity'", EditText.class);
    target.etSateCode = Utils.findRequiredViewAsType(source, R.id.et_state_code, "field 'etSateCode'", EditText.class);
    target.etZip = Utils.findRequiredViewAsType(source, R.id.et_zipcode, "field 'etZip'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.rg, "field 'radioGroup'", RadioGroup.class);
    target.rbHome = Utils.findRequiredViewAsType(source, R.id.rb_home, "field 'rbHome'", RadioButton.class);
    target.rbWork = Utils.findRequiredViewAsType(source, R.id.rb_work, "field 'rbWork'", RadioButton.class);
    target.rbCustom = Utils.findRequiredViewAsType(source, R.id.rb_custom, "field 'rbCustom'", RadioButton.class);
    target.btnContinueAddress = Utils.findRequiredViewAsType(source, R.id.btn_add_new_address, "field 'btnContinueAddress'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddNewAddress target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etAddress = null;
    target.etSuit = null;
    target.etCity = null;
    target.etSateCode = null;
    target.etZip = null;
    target.etPhone = null;
    target.radioGroup = null;
    target.rbHome = null;
    target.rbWork = null;
    target.rbCustom = null;
    target.btnContinueAddress = null;
  }
}

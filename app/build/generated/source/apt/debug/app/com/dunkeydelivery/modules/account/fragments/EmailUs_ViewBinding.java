// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EmailUs_ViewBinding implements Unbinder {
  private EmailUs target;

  @UiThread
  public EmailUs_ViewBinding(EmailUs target, View source) {
    this.target = target;

    target.tvReason = Utils.findRequiredViewAsType(source, R.id.tv_reason, "field 'tvReason'", TextView.class);
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.etMail = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'etMail'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etInstructions = Utils.findRequiredViewAsType(source, R.id.et_instructions, "field 'etInstructions'", EditText.class);
    target.btnSend = Utils.findRequiredViewAsType(source, R.id.btn_send, "field 'btnSend'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EmailUs target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvReason = null;
    target.etName = null;
    target.etMail = null;
    target.etPhone = null;
    target.etInstructions = null;
    target.btnSend = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChangePassword_ViewBinding implements Unbinder {
  private ChangePassword target;

  @UiThread
  public ChangePassword_ViewBinding(ChangePassword target, View source) {
    this.target = target;

    target.btnUpdate = Utils.findRequiredViewAsType(source, R.id.btn_update, "field 'btnUpdate'", Button.class);
    target.etNewPassword = Utils.findRequiredViewAsType(source, R.id.et_new_password, "field 'etNewPassword'", EditText.class);
    target.etConfirmPassword = Utils.findRequiredViewAsType(source, R.id.et_confirm_password, "field 'etConfirmPassword'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangePassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnUpdate = null;
    target.etNewPassword = null;
    target.etConfirmPassword = null;
  }
}

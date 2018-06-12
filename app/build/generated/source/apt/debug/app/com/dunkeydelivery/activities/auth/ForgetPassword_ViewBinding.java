// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.activities.auth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgetPassword_ViewBinding implements Unbinder {
  private ForgetPassword target;

  @UiThread
  public ForgetPassword_ViewBinding(ForgetPassword target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgetPassword_ViewBinding(ForgetPassword target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'etEmail'", EditText.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgetPassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.etEmail = null;
    target.btnSubmit = null;
  }
}

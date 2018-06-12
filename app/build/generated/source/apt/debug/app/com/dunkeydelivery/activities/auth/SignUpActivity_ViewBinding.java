// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.activities.auth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.imvUser = Utils.findRequiredViewAsType(source, R.id.imv_user, "field 'imvUser'", ImageView.class);
    target.etLastName = Utils.findRequiredViewAsType(source, R.id.et_last_name, "field 'etLastName'", EditText.class);
    target.etFirstName = Utils.findRequiredViewAsType(source, R.id.et_first_name, "field 'etFirstName'", EditText.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'etEmail'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", EditText.class);
    target.etConfirmPaswd = Utils.findRequiredViewAsType(source, R.id.et_confirm_password, "field 'etConfirmPaswd'", EditText.class);
    target.btnSignup = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field 'btnSignup'", Button.class);
    target.llGmailLogin = Utils.findRequiredViewAsType(source, R.id.ll_gmail_login, "field 'llGmailLogin'", LinearLayout.class);
    target.llFbLogin = Utils.findRequiredViewAsType(source, R.id.ll_fb_login, "field 'llFbLogin'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.imvUser = null;
    target.etLastName = null;
    target.etFirstName = null;
    target.etEmail = null;
    target.etPassword = null;
    target.etConfirmPaswd = null;
    target.btnSignup = null;
    target.llGmailLogin = null;
    target.llFbLogin = null;
  }
}

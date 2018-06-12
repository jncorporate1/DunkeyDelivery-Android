// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.activities.auth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.mEmailView = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'mEmailView'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", EditText.class);
    target.tvResetPswd = Utils.findRequiredViewAsType(source, R.id.tv_reset_paswd, "field 'tvResetPswd'", TextView.class);
    target.tvCreateAccount = Utils.findRequiredViewAsType(source, R.id.tv_create_account, "field 'tvCreateAccount'", TextView.class);
    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btn_login, "field 'btnLogin'", Button.class);
    target.llGmailLogin = Utils.findRequiredViewAsType(source, R.id.ll_gmail_login, "field 'llGmailLogin'", LinearLayout.class);
    target.llFbLogin = Utils.findRequiredViewAsType(source, R.id.ll_fb_login, "field 'llFbLogin'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEmailView = null;
    target.etPassword = null;
    target.tvResetPswd = null;
    target.tvCreateAccount = null;
    target.btnLogin = null;
    target.llGmailLogin = null;
    target.llFbLogin = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.fragments.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyAccount_ViewBinding implements Unbinder {
  private MyAccount target;

  @UiThread
  public MyAccount_ViewBinding(MyAccount target, View source) {
    this.target = target;

    target.tvChangePaswd = Utils.findRequiredViewAsType(source, R.id.tv_change_paswd, "field 'tvChangePaswd'", TextView.class);
    target.view_separator = Utils.findRequiredView(source, R.id.view_separator, "field 'view_separator'");
    target.tvFirstName = Utils.findRequiredViewAsType(source, R.id.tv_firstname, "field 'tvFirstName'", TextView.class);
    target.tvLastName = Utils.findRequiredViewAsType(source, R.id.tv_lastname, "field 'tvLastName'", TextView.class);
    target.tvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmail'", TextView.class);
    target.btnContinue = Utils.findRequiredViewAsType(source, R.id.btn_continue, "field 'btnContinue'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAccount target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvChangePaswd = null;
    target.view_separator = null;
    target.tvFirstName = null;
    target.tvLastName = null;
    target.tvEmail = null;
    target.btnContinue = null;
  }
}
